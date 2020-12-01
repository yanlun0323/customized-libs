package com.customized.libs.core.libs.javase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * https://mp.weixin.qq.com/s/EBnM7QAOPjDk5bG3M0Mu-w
 *
 * @author yan
 */
public class JavaSyntacticSugar {

    /**
     * switch支持String
     *
     * @param key
     */
    public static void switchSugar(String key) {
        switch (key) {
            case "data":
                System.out.println("01");
                break;
            case "value":
                System.out.println("02");
                break;
            default:
                System.out.println("default");
                break;
        }
    }

    /**
     * 虚拟机中没有泛型，只有普通类和普通方法，所有泛型类的类型参数在编译时都会被擦除，泛型类并没有自己独有的Class类对象
     * <p>
     * JAVA泛型（在编译阶段做类型检测）
     * <p>
     * 编译阶段类型擦除
     * <p>
     * 1.将所有的泛型参数用其最左边界（最顶级的父类型）类型替换。
     * <p>
     * 2.移除所有的类型参数。
     */
    public static void genericType() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "hollis");
        map.put("wechat", "Hollis");
        map.put("blog", "www.hollischuang.com");
    }

    /**
     * 泛型擦除案例2
     *
     * @param xs
     * @param <A>
     * @return
     */
    public static <A extends Comparable<A>> A max(Collection<A> xs) {
        Iterator<A> xi = xs.iterator();
        A w = xi.next();
        while (xi.hasNext()) {
            A x = xi.next();
            if (w.compareTo(x) < 0)
                w = x;
        }
        return w;
    }

    /**
     * 装箱过程是通过调用包装器的valueOf方法实现的，而拆箱过程是通过调用包装器的 xxxValue方法实现的
     */
    public static void autoBoxing() {
        int i = 10;
        Integer n = i;
    }

    /**
     * variable arguments
     * <p>
     * 可变参数在被使用的时候，他首先会创建一个数组，数组的长度就是调用该方法是传递的实参的个数，
     * <p>
     * 然后再把参数值全部放到这个数组当中，然后再把这个数组作为参数传递到被调用的方法中。
     *
     * @param strs
     */
    public static void variableArguments(String... strs) {
        for (int i = 0; i < strs.length; i++) {
            System.out.println(strs[i]);
        }
    }

    public static void tryWithResource() {
        try (BufferedReader br = new BufferedReader(new FileReader("d:\\ hollischuang.xml"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // handle exception
        }
    }

    public enum t {
        SPRING, SUMMER;
    }

    public static void main(String[] args) {
        switchSugar("data");

        genericType();

        variableArguments("a", "b", "c");
    }
}
