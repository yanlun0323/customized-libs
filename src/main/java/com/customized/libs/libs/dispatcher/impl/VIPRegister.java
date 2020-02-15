package com.customized.libs.libs.dispatcher.impl;

import com.customized.libs.libs.dispatcher.Register;

/**
 * @author yan
 */
public class VIPRegister implements Register {

    @Override
    public void doRegister() {
        System.out.println("VIPRegister =>> doRegister");
    }
}
