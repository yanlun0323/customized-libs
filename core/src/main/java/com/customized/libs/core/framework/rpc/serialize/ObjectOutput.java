package com.customized.libs.core.framework.rpc.serialize;

import java.io.IOException;

public interface ObjectOutput extends DataOutput {

    void writeObject(Object obj) throws IOException;
}
