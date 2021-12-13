package com.customized.libs.springboot;

import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SM4;
import org.junit.Test;

import java.util.Base64;

public class HootUtilsTest {

    @Test
    public void test() {
        SM4 sm4 = new SM4("1111111111111111".getBytes());
        String s = sm4.encryptBase64("123456");
        System.out.println("SM4对称加密 ==> " + s);

        String priKey = "03D17E681355A0FC1CD549B38E1B6C82A48E8C9223AEE4763ACB27146A45F4C3";
        String pubKey = "504dd2cb96daa672de47377580c07d7c97e95b8223b8c5063f9b0f529b0bb696c36b9d935461fd1ca41f52df5f63dd51827ce0e911f9ed4bbc39069986de78c2";
        SM2 sm2 = new SM2(priKey, pubKey);
        String data = "123456";
        byte[] sign = sm2.sign(data.getBytes());
        System.out.println("SM2签名 ==> " + new String(Base64.getEncoder().encode(sign)));
    }
}
