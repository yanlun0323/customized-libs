package com.customized.libs.core.libs.xmlparser;

import com.customized.libs.core.libs.xmlparser.entity.Body;
import com.customized.libs.core.libs.xmlparser.entity.BodyBase;
import com.customized.libs.core.libs.xmlparser.entity.Head;
import com.customized.libs.core.libs.xmlparser.entity.VasRequest;
import com.customized.libs.core.utils.XStreamUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.util.StringUtils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ParseXML {

    public static void main(String[] args) {

        Integer total = 50000;
        System.out.println(Integer.MAX_VALUE);
        long start = System.currentTimeMillis();

        for (int i = 0; i < total; i++) {
            //xstreamParse();
        }

        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();

        for (int i = 0; i < total; i++) {
            Map<String, Object> reqXml = new HashMap<>();
            reqXml.put("UUID", UUID.randomUUID().toString());
            reqXml.put("CheckCode", "XX");
            reqXml.put("NAmt", 10000);
            reqXml.put("NPrm", 100);
            reqXml.put("CAppNme", "APP");
            reqXml.put("CClntMrk", "MRK");
            String xmlString = templateParse("test.ftl", reqXml);
            //System.out.println(templateParse("test.ftl", reqXml));
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    public static void xstreamParse() {
        VasRequest vasRequest = new VasRequest();

        Head head = new Head();
        head.setCRequestType("CRequestType");
        head.setUUID(UUID.randomUUID().toString());

        Body body = new Body();
        BodyBase base = new BodyBase();
        body.setBase(base);
        base.setCProdNo("NO");
        base.setTAppTm("YYYYMMDD");
        vasRequest.setBody(body);
        vasRequest.setHead(head);
        String xmlString = XStreamUtil.toXml(vasRequest, false, false);
        //System.out.println(xmlString);
    }

    public static String templateParse(String templateName, Map<String, Object> reqXML) {
        try {
            Configuration cgf = new Configuration();
            StringWriter sw = new StringWriter();
            cgf.setClassForTemplateLoading(ParseXML.class, "/templates");

            Template template = cgf.getTemplate(templateName);
            template.process(reqXML, sw);

            String XmlString = sw.toString();
            if (!StringUtils.isEmpty(XmlString)) {
                //System.out.println(XmlString);
                XmlString = XmlString.replaceAll("[\r\n\t]", "");
            }
            return XmlString;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }
}
