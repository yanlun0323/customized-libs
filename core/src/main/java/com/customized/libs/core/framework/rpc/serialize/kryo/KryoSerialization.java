package com.customized.libs.core.framework.rpc.serialize.kryo;

import com.customized.libs.core.framework.rpc.serialize.ObjectInput;
import com.customized.libs.core.framework.rpc.serialize.ObjectOutput;
import com.customized.libs.core.framework.rpc.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 10:47
 */
public class KryoSerialization implements Serialization {

    @Override
    public ObjectOutput serialize(OutputStream output) throws IOException {
        return null;
    }

    @Override
    public ObjectInput deserialize(InputStream input) throws IOException {
        return null;
    }
}
