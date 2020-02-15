package com.customized.libs.libs.clazzs;

public class A extends B {
    public int a = 100;

    public A() {
        super();
        System.out.println(a);
        a = 200;
    }

    public static void main(String[] args) {
        System.out.println(new A().a);
    }
}

class B {
    public B() {
        System.out.println(((A) this).a);
    }
}