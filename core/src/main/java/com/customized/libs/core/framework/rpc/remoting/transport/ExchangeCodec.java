package com.customized.libs.core.framework.rpc.remoting.transport;


import com.customized.libs.core.framework.rpc.remoting.exchange.Request;
import com.customized.libs.core.framework.rpc.remoting.exchange.Response;
import com.customized.libs.core.framework.rpc.serialize.ObjectOutput;
import com.customized.libs.core.framework.rpc.serialize.Serialization;
import com.customized.libs.core.framework.rpc.serialize.jdk.JdkSerialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/15 14:06
 */
public class ExchangeCodec extends TransportCodec {

    @Override
    public void encode(OutputStream output, Object msg) throws IOException {
        if (msg instanceof Request) {
            encodeRequestData(output, (Request) msg);
        } else if (msg instanceof Response) {
            encodeResponseData(output, (Response) msg);
        } else {
            super.encode(output, msg);
        }
    }

    @Override
    public Object decode(InputStream input) throws IOException {
        return super.decode(input);
    }

    protected void encodeRequestData(OutputStream outputStream, Request request) throws IOException {
        Serialization serialization = new JdkSerialization();
        ObjectOutput out = serialization.serialize(outputStream);
        out.writeObject(request);
    }

    protected void encodeResponseData(OutputStream outputStream, Response response) throws IOException {
        Serialization serialization = new JdkSerialization();
        ObjectOutput out = serialization.serialize(outputStream);
        out.writeObject(response);
    }
}
