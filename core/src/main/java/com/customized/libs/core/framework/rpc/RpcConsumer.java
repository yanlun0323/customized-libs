package com.customized.libs.core.framework.rpc;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/14 16:18
 */
public class RpcConsumer {

    public static void main(String[] args) throws InterruptedException {
        UserService userService = RpcFramework.refer(UserService.class, "127.0.0.1", 1234);
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {
            String hello = userService.hello("World" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}
