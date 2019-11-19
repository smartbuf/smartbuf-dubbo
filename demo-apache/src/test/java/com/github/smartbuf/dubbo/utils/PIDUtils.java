package com.github.smartbuf.dubbo.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.junit.Test;

/**
 * 获取ProcessID的工具类，不支持windows
 *
 * @author sulin
 * @since 2019-11-18 11:42:39
 */
public class PIDUtils {

    public static int getPid() {
        return CLibrary.INSTANCE.getpid();
    }

    private interface CLibrary extends Library {
        CLibrary INSTANCE = Native.loadLibrary("c", CLibrary.class);

        int getpid();
    }

    @Test
    public void test() {
        System.out.println(getPid());
    }

}
