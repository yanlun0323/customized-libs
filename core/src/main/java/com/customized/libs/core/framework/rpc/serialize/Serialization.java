package com.customized.libs.core.framework.rpc.serialize;

import com.customized.libs.core.framework.rpc.annotation.SPI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 09:57
 */
@SPI("kryo")
public interface Serialization {

    /**
     * create serializer
     *
     * @param output
     * @return serializer
     * @throws IOException
     */
    ObjectOutput serialize(OutputStream output) throws IOException;

    /**
     * create deserializer
     *
     * @param input
     * @return deserializer
     * @throws IOException
     */
    ObjectInput deserialize(InputStream input) throws IOException;
}
