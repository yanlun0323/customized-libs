package com.customized.libs.core.libs.dubbo.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.customized.libs.core.libs.dispatcher.Register;

/**
 * @author yan
 */
public class ExtensionLoaderTest {


    public static void main(String[] args) {
        Register register = ExtensionLoader.getExtensionLoader(Register.class).getExtension("VIPRegister");
        register.doRegister();
    }
}
