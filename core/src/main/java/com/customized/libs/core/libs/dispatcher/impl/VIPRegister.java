package com.customized.libs.core.libs.dispatcher.impl;

import com.customized.libs.core.libs.dispatcher.Register;

/**
 * @author yan
 */
public class VIPRegister implements Register {

    @Override
    public void doRegister() {
        System.out.println("VIPRegister =>> doRegister");
    }
}
