package com.github.smartbuf.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.github.smartbuf.dubbo.impl.UserServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

/**
 * @author sulin
 * @since 2019-11-19 11:38:05
 */
public class ProviderTest {

    private static final RegistryConfig registry = new RegistryConfig("localhost:2181", "zookeeper");

    private static final String serialization = "smartbuf";
//    private static final String serialization = "fastjson";
//    private static final String serialization = "kryo";
//    private static final String serialization = "fst";
//    private static final String serialization = "hessian2";

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

        ConsumerTest.sleep(10000);
    }

}
