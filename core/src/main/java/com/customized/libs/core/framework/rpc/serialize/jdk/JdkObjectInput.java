package com.customized.libs.core.framework.rpc.serialize.jdk;

import com.alibaba.dubbo.common.utils.Assert;
import com.customized.libs.core.framework.rpc.serialize.ObjectInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 10:39
 */
public class JdkObjectInput implements ObjectInput {

    private final ObjectInputStream inputStream;

    public JdkObjectInput(InputStream is) throws IOException {
        this(new ObjectInputStream(is));
    }

    protected JdkObjectInput(ObjectInputStream is) {
        Assert.notNull(is, "input == null");
        this.inputStream = is;
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        return this.inputStream.readObject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return (T) readObject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        return (T) readObject();
    }

    @Override
    public boolean readBool() throws IOException {
        return false;
    }

    @Override
    public byte readByte() throws IOException {
        return 0;
    }

    @Override
    public short readShort() throws IOException {
        return 0;
    }

    @Override
    public int readInt() throws IOException {
        return 0;
    }

    @Override
    public long readLong() throws IOException {
        return 0;
    }

    @Override
    public float readFloat() throws IOException {
        return 0;
    }

    @Override
    public double readDouble() throws IOException {
        return 0;
    }

    @Override
    public String readUTF() throws IOException {
        return this.inputStream.readUTF();
    }

    @Override
    public byte[] readBytes() throws IOException {
        return new byte[0];
    }
}
