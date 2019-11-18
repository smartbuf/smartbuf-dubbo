package com.github.smartbuf.dubbo;

import com.alibaba.dubbo.config.*;
import com.github.smartbuf.dubbo.provider.UserServiceImpl;
import com.github.smartbuf.dubbo.utils.NetMonitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sulin
 * @since 2019-11-17 16:17:12
 */
@Slf4j
public class UserServiceTest {

    private static final RegistryConfig registry = new RegistryConfig("localhost:2181", "zookeeper");

    private static final String serialization = "kryo";
    private static final int    times         = 1000 * 1000;

    @Test
    public void testProvider() {
        UserServiceImpl userService = new UserServiceImpl();

        ApplicationConfig application = new ApplicationConfig();
        application.setLogger("slf4j");
        application.setName("provider");
        application.setQosPort(RandomUtils.nextInt(20000, 30000));

        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(12345);
        protocol.setThreads(40);
        protocol.setSerialization(serialization);

        ServiceConfig<UserService> service = new ServiceConfig<>();
        service.setApplication(application);
        service.setRegistry(registry);
        service.setProtocol(protocol);
        service.setInterface(UserService.class);
        service.setRef(userService);
        service.setVersion("1.0.0");
        service.setSerialization(serialization);

        service.export();

        sleep(10000);
    }

    @Test
    public void testConsumer() {
        ApplicationConfig application = new ApplicationConfig();
        application.setLogger("slf4j");
        application.setName("consumer");
        application.setQosPort(RandomUtils.nextInt(30000, 40000));

        ReferenceConfig<UserService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserService.class);
        reference.setVersion("1.0.0");

        UserService userService = reference.get();
        sleep(1);

        System.out.println("\n\n\n");

        NetMonitor netMonitor = new NetMonitor();
        netMonitor.start();

        AtomicInteger counter = new AtomicInteger();
        new Thread(() -> {
            for (int i = 0; i < times; i++) {
                try {
                    userService.login("nike", "test.123");
                    userService.getUser(10001);
                    userService.getFriends(10001);
                } catch (Exception e) {
                    log.error("wtf! ", e);
                    sleep(1);
                    System.exit(-1);
                }
                counter.incrementAndGet();
            }
        }).start();

        int second = 0;
        int t;
        do {
            sleep(1);
            long bytesIn = netMonitor.getBytesIn();
            long bytesOut = netMonitor.getBytesOut();
            t = counter.get();
            System.out.printf("%d, %d, %d, %d, %d\n", second++, t, bytesIn + bytesOut, bytesIn, bytesOut);
        } while (t < times);

        netMonitor.stop();
        reference.destroy();

        sleep(3);
    }

    static void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException ignored) {
        }
    }

}
