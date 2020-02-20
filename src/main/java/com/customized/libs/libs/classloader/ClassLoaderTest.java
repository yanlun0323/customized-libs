package com.customized.libs.libs.classloader;

/**
 * VM参数增加-verbose:class可打印出classloader加载明细
 * @author yan
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        /**
         * 虽然项目内存在java.lang.String类的定义，
         * 但是由于java类加载器的双亲委派机制，这里并不会加载classpath下自定义的String.class
         */
        String str = "ABC";
        System.out.println(str);
    }
}
