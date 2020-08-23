package com.customized.libs.core.libs.memory.leak;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: jorian
 * @Date: 2019/8/14 21:47
 * <p>
 * https://blog.csdn.net/weixin_38023579/article/details/99630894
 * <p>
 * eden区发生gc，所以内存先增大，到一定程度会被回收变小，大量的内存被复制到老年代
 * @Description:内存增长模拟
 */
public class JconsoleMemory {
    public byte[] b1 = new byte[1024 * 512];

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main thread start");
        Thread.sleep(10000);
        allocate(10000);
    }

    public static void allocate(int n) {
        List<JconsoleMemory> jconsoleMemoryList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jconsoleMemoryList.add(new JconsoleMemory());
        }
    }

}