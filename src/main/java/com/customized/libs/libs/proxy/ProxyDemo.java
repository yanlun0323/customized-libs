package com.customized.libs.libs.proxy;

import com.customized.libs.libs.proxy.interfaces.ProxyIndex;
import com.customized.libs.libs.proxy.interfaces.ProxyIndexImpl;
import org.apache.commons.io.FileUtils;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @author yan
 */
public class ProxyDemo {

    public static void main(String[] args) throws IOException {

        // 代理对象生成
        ProxyIndex target = (ProxyIndex) Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(),
                new Class[]{ProxyIndex.class}, new ProxyInvocationHandler(new ProxyIndexImpl()));
        target.query();

        // 动态生成代理对象字节码
        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                "$Proxy$Demo", new Class[]{ProxyIndex.class}, 49);
        FileUtils.writeByteArrayToFile(new File("./libs/class/Proxy.class"), proxyClassFile);
    }
}
