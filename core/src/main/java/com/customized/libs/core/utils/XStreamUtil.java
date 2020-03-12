package com.customized.libs.core.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.springframework.util.StringUtils;

import java.io.Writer;

/**
 * Created by zhaoyang on 09/10/2016.
 */
public class XStreamUtil {

    public static <T> T fromXml(String xmlStr, Class<T> clazz) {
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
        xStream.setClassLoader(clazz.getClassLoader());
        xStream.processAnnotations(clazz);
        xStream.ignoreUnknownElements();
        return (T) xStream.fromXML(xmlStr);
    }

    public static String toXml(Object object) {
        return toXml(object, true, true);
    }

    public static String toXml(Object object, boolean withCDATA, boolean prettyFormat) {
        XStream xStream = new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                if (prettyFormat) {
                    return new PrettyPrintWriter(out) {
                        @Override
                        public String encodeNode(String name) {
                            return (new NoNameCoder()).encodeNode(name);
                        }

                        @Override
                        protected void writeText(QuickWriter writer, String text) {
                            if (!StringUtils.isEmpty(text)) {
                                if (withCDATA) writer.write("<![CDATA[");
                                writer.write(text);
                                if (withCDATA) writer.write("]]>");
                            } else {
                                writer.write(text);
                            }
                        }
                    };
                } else {
                    return new CompactWriter(out) {
                        @Override
                        public String encodeNode(String name) {
                            return (new NoNameCoder()).encodeNode(name);
                        }

                        @Override
                        protected void writeText(QuickWriter writer, String text) {
                            if (!StringUtils.isEmpty(text)) {
                                if (withCDATA) writer.write("<![CDATA[");
                                writer.write(text);
                                if (withCDATA) writer.write("]]>");
                            } else {
                                writer.write(text);
                            }
                        }
                    };
                }
            }
        });
        xStream.processAnnotations(object.getClass());
        xStream.aliasSystemAttribute(null, "class");
        return xStream.toXML(object);
    }

}
