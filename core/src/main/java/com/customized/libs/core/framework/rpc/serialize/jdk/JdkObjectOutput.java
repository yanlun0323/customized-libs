package com.customized.libs.core.framework.rpc.serialize.jdk;

import com.customized.libs.core.framework.rpc.serialize.ObjectOutput;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 10:40
 */
public class JdkObjectOutput implements ObjectOutput {

    private final ObjectOutputStream outputStream;

    public JdkObjectOutput(OutputStream os) throws IOException {
        this(new ObjectOutputStream(os));
    }

    protected JdkObjectOutput(ObjectOutputStream os) {
        this.outputStream = os;
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        this.outputStream.writeObject(obj);
    }

    @Override
    public void writeBool(boolean v) throws IOException {

    }

    @Override
    public void writeByte(byte v) throws IOException {

    }

    @Override
    public void writeShort(short v) throws IOException {

    }

    @Override
    public void writeInt(int v) throws IOException {

    }

    @Override
    public void writeLong(long v) throws IOException {

    }

    @Override
    public void writeFloat(float v) throws IOException {

    }

    @Override
    public void writeDouble(double v) throws IOException {

    }

    @Override
    public void writeUTF(String v) throws IOException {
        this.outputStream.writeUTF(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {

    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {

    }

    @Override
    public void flushBuffer() throws IOException {
        this.outputStream.flush();
    }
}
