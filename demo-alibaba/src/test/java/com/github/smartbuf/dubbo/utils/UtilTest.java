package com.github.smartbuf.dubbo.utils;

import org.junit.Test;

/**
 * @author sulin
 * @since 2019-11-18 11:52:44
 */
public class UtilTest {

    @Test
    public void testNetMonitor() throws Exception {
        NetMonitor monitor = new NetMonitor();
        monitor.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("bytes_in : " + monitor.getBytesIn());
            System.out.println("bytes_out: " + monitor.getBytesOut());
            Thread.sleep(1000);
        }

        monitor.stop();
    }
}
