package com.customized.libs.core.utils;

class ClassB extends RealType<Integer> {

    public static void main(String[] args) {
        ClassB classB = new ClassB();
        Class realType = classB.getRealType();
        System.out.println(realType.getName());
    }
}

