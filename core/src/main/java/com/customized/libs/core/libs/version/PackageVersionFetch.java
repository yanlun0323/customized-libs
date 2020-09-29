package com.customized.libs.core.libs.version;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.sofa.boot.env.EnvironmentCustomizer;
import org.springframework.boot.SpringBootVersion;

/**
 * @author yan
 */
public class PackageVersionFetch {

    /**
     * java.lang.Package#loadManifest(java.lang.String)
     * <p>
     * <p>
     * <p>
     * Manifest-Version: 1.0
     * Implementation-Title: log-trace-springmvc-adapter
     * Implementation-Version: 1.0.0-SNAPSHOT
     * Archiver-Version: Plexus Archiver
     * Built-By: yan
     * Implementation-Vendor-Id: com.customized.libs
     * Created-By: Apache Maven 3.3.9
     * Build-Jdk: 1.8.0_161
     * <p>
     *
     * <p>
     * <build>
     *   <plugins>
     *       <plugin>
     *              <groupId>org.apache.maven.plugins</groupId>
     *              <artifactId>maven-jar-plugin</artifactId>
     *              <configuration>
     *                      <archive>
     *                          <manifest>
     *                              <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
     *                          </manifest>
     *                      </archive>
     *              </configuration>
     *      </plugin>
     *  </plugins>
     * </build>
     *
     *
     * 加入addDefaultImplementationEntries配置即可在打包jarMETA-INF/MANIFEST.MF文件下生成版本相关的描述
     * @param args
     */
    public static void main(String[] args) {
        Package[] packages = Package.getPackages();
        for (Package aPackage : packages) {
            System.out.println(JSON.toJSONString(aPackage));
        }

        System.out.println(SpringBootVersion.getVersion());

        System.out.println(EnvironmentCustomizer.class.getPackage().getImplementationVersion());

        /**
         * META-INF/MANIFEST.MF内包含Implementation
         *
         * Implementation-Vendor-Id
         */
        Package aPackage = WordExportUtil.class.getPackage();

        System.out.println(aPackage.getImplementationVendor());
        System.out.println(aPackage.getImplementationTitle());
        System.out.println(aPackage.getImplementationVersion());
    }

    public static String getVersion() {
        Package pkg = SpringBootVersion.class.getPackage();
        return (pkg != null) ? pkg.getImplementationVersion() : null;
    }
}
