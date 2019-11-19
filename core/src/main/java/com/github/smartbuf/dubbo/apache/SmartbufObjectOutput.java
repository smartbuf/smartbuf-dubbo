package com.github.smartbuf.dubbo.apache;

import com.github.smartbuf.SmartBuf;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Smartbuf输出端数据编码(序列化)实现类
 *
 * @author sulin
 * @since 2019-11-15 14:47:04
 */
public class SmartbufObjectOutput implements ObjectOutput {

    private final URL          url;
    private final OutputStream outputStream;

    private final SmartBuf smartbuf;

    public SmartbufObjectOutput(URL url, OutputStream os) {
        this.url = url;
        this.outputStream = os;
        if (url == null) {
            smartbuf = new SmartBuf(false);
        } else {
            smartbuf = new SmartBuf(true);
        }
    }

    public void writeBool(boolean v) throws IOException {
        writeObject(v);
    }

    public void writeByte(byte v) throws IOException {
        writeObject(v);
    }

    public void writeShort(short v) throws IOException {
        writeObject(v);
    }

    public void writeInt(int v) throws IOException {
        writeObject(v);
    }

    public void writeLong(long v) throws IOException {
        writeObject(v);
    }

    public void writeFloat(float v) throws IOException {
        writeObject(v);
    }

    public void writeDouble(double v) throws IOException {
        writeObject(v);
    }

    public void writeUTF(String v) throws IOException {
        writeObject(v);
    }

    public void writeBytes(byte[] v) throws IOException {
        writeObject(v);
    }

    public void writeBytes(byte[] v, int off, int len) throws IOException {
        byte[] tmp = new byte[len];
        System.arraycopy(v, off, tmp, 0, len);
        writeObject(tmp);
    }

    public void writeObject(Object obj) throws IOException {
        smartbuf.write(obj, outputStream);

        outputStream.flush();
    }

    public void flushBuffer() throws IOException {
        outputStream.flush();
    }
}
