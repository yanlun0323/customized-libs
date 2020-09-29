package com.customized.libs.core.libs.tomcat;

import com.customized.libs.core.libs.tomcat.servlet.DemoServlet;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author yan
 */
@Slf4j
public class EmbeddedTomcatServer {

    public static void main(String[] args) throws LifecycleException, InterruptedException {

        // 获取目录的绝对路径
        String classpath = System.getProperty("user.dir");

        log.info("==> classpath: {}", classpath);

        // new tomcat实例
        Tomcat tomcat = new Tomcat();
        Connector connector = tomcat.getConnector();
        connector.setPort(8080);

        Host host = tomcat.getHost();
        // 此处可读取xml配置文件
        host.setName("localhost");
        host.setAppBase("webapps");

        // class加载
        Context context = tomcat.addContext(host, "/tomcat", classpath);
        if (context instanceof StandardContext) {
            // StandardContext standardContext = (StandardContext) context;
            // standardContext.setDefaultContextXml("");

            Wrapper wrapper = tomcat.addServlet("/tomcat", "DemoServlet", new DemoServlet());
            wrapper.addMapping("/servlet");
        }

        tomcat.start();
    }
}
