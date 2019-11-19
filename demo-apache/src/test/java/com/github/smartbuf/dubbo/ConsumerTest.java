package com.github.smartbuf.dubbo;

import com.github.smartbuf.dubbo.utils.NetMonitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author sulin
 * @since 2019-11-17 16:17:12
 */
@Slf4j
public class ConsumerTest {

    private static final RegistryConfig registry = new RegistryConfig("localhost:2181", "zookeeper");

    @Test
    public void testTiny() {
        runConsumer(400000, userService -> userService.login("smartbuf", "it's the fastest"));
    }

    @Test
    public void testUser() {
        runConsumer(300000, userService -> userService.getUser(null));
    }

    @Test
    public void testPosts() {
        runConsumer(100000, userService -> userService.queryPost("hollywood"));
    }

    public void runConsumer(int times, Consumer<UserService> run) {
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
                    run.accept(userService);
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
