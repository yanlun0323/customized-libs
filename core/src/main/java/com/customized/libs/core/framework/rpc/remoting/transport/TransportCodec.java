package com.customized.libs.core.framework.rpc.remoting.transport;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.customized.libs.core.framework.rpc.serialize.ObjectInput;
import com.customized.libs.core.framework.rpc.serialize.ObjectOutput;
import com.customized.libs.core.framework.rpc.serialize.jdk.JdkSerialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 11:31
 */
public class TransportCodec {

    void encodeServiceKey(OutputStream output, Object message) throws IOException {
        JdkSerialization serialization = new JdkSerialization();
        ObjectOutput objectOutput = serialization.serialize(output);
        objectOutput.writeUTF((String) message);
        objectOutput.flushBuffer();
    }

    void encode(OutputStream output, Object message) throws IOException {
        JdkSerialization serialization = new JdkSerialization();
        ObjectOutput objectOutput = serialization.serialize(output);
        objectOutput.writeObject(message);
        objectOutput.flushBuffer();
    }

    Object decode(InputStream input) throws IOException {
        JdkSerialization serialization = new JdkSerialization();
        ObjectInput objectInput = serialization.deserialize(input);
        return decodeData(objectInput);
    }

    public String decodeServiceKey(InputStream input) throws IOException {
        JdkSerialization serialization = new JdkSerialization();
        ObjectInput objectInput = serialization.deserialize(input);
        return objectInput.readUTF();
    }

    protected Object decodeData(ObjectInput input) throws IOException {
        try {
            return input.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("ClassNotFoundException: " + StringUtils.toString(e));
        }
    }
}
