package com.github.smartbuf.dubbo;


import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.github.smartbuf.SmartBuf;
import com.github.smartbuf.reflect.TypeRef;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author sulin
 * @since 2019-11-15 14:47:28
 */
public class SmartbufObjectInput implements ObjectInput {

    private final URL         url;
    private final InputStream inputStream;

    private final SmartBuf smartbuf;

    public SmartbufObjectInput(URL url, InputStream inputStream) {
        this.url = url;
        this.inputStream = inputStream;

        if (url == null) {
            smartbuf = new SmartBuf(false);
        } else {
            smartbuf = new SmartBuf(true);
        }
    }

    public boolean readBool() throws IOException {
        return this.readObject(boolean.class);
    }

    public byte readByte() throws IOException {
        return this.readObject(byte.class);
    }

    public short readShort() throws IOException {
        return this.readObject(short.class);
    }

    public int readInt() throws IOException {
        return this.readObject(int.class);
    }

    public long readLong() throws IOException {
        return this.readObject(long.class);
    }

    public float readFloat() throws IOException {
        return this.readObject(float.class);
    }

    public double readDouble() throws IOException {
        return this.readObject(double.class);
    }

    public String readUTF() throws IOException {
        return this.readObject(String.class);
    }

    public byte[] readBytes() throws IOException {
        return this.readObject(byte[].class);
    }

    public Object readObject() throws IOException {
        return smartbuf.readObject(inputStream);
    }

    public <T> T readObject(Class<T> cls) throws IOException {
        return smartbuf.read(inputStream, cls);
    }

    public <T> T readObject(Class<T> cls, final Type type) throws IOException {
        return smartbuf.read(inputStream, new TypeRef<T>() {
            @Override
            public Type getType() {
                return type;
            }
        });
    }
}
