package com.customized.libs.core.framework.rpc;

/**
 * @author yan
 * @version 1.0
 * @description TODO
 * @date 2022/6/14 16:20
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        UserService userService = name -> name;
        RpcFramework.export(userService, "hello");
        RpcFramework.doOpen(1234);
    }
}
