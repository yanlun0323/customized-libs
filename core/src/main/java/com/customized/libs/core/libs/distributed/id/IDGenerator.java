package com.customized.libs.core.libs.distributed.id;

import com.customized.libs.core.utils.IdUtil;

public class IDGenerator {

    public static void main(String[] args) {

        System.out.println(65537 & 65535);

        int data = 2 << 2;
        System.out.println(data);

        System.out.println(1 | 2 | 3);


        System.out.println(1 << 16);
        System.out.println(1 << 5);

        System.out.println(IdUtil.nextId());
        System.out.println(IdUtil.nextId());
        System.out.println(IdUtil.nextId());
        System.out.println(IdUtil.nextId());
        System.out.println(IdUtil.nextId());
        System.out.println(IdUtil.nextId());
        System.out.println(IdUtil.nextId());
        System.out.println(IdUtil.nextId());
        System.out.println(IdUtil.nextId());
    }
}