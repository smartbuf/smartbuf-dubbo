package com.github.smartbuf.dubbo;

import com.alibaba.dubbo.config.*;
import com.github.smartbuf.dubbo.provider.UserServiceImpl;
import org.junit.Test;

/**
 * @author sulin
 * @since 2019-11-17 16:17:12
 */
public class UserServiceTest {

    private static final RegistryConfig registry = new RegistryConfig("localhost:2181", "zookeeper");

    @Test
    public void testProvider() throws InterruptedException {
        UserServiceImpl userService = new UserServiceImpl();

        ApplicationConfig application = new ApplicationConfig();
        application.setLogger("slf4j");
        application.setName("provider");

        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(12345);
        protocol.setThreads(40);
        protocol.setSerialization("smartbuf");

        ServiceConfig<UserService> service = new ServiceConfig<UserService>();
        service.setApplication(application);
        service.setRegistry(registry);
        service.setProtocol(protocol);
        service.setInterface(UserService.class);
        service.setRef(userService);
        service.setVersion("1.0.0");
        service.setSerialization("smartbuf");

        service.export();

        Thread.sleep(1000000);
    }

    @Test
    public void testConsumer() {
        ApplicationConfig application = new ApplicationConfig();
        application.setLogger("slf4j");
        application.setName("consumer");

        ReferenceConfig<UserService> reference = new ReferenceConfig<UserService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserService.class);
        reference.setVersion("1.0.0");

        UserService userService = reference.get();

        String token = userService.login("username", "test.123");
        assert token != null;

        System.out.println("\n\n" + token + "\n\n");
    }

}
