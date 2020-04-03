package com.customized.libs.spring.expands.apollo.config;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.core.Ordered;
import org.w3c.dom.Element;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class NamespaceHandler extends NamespaceHandlerSupport {

    private static Logger logger = LoggerFactory.getLogger(NamespaceHandler.class);

    private static final Splitter NAMESPACE_SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    @Override
    public void init() {
        registerBeanDefinitionParser("config", new BeanParser());
    }

    static class BeanParser extends AbstractSingleBeanDefinitionParser {
        @Override
        protected Class<?> getBeanClass(Element element) {
            return ConfigPropertySourcesProcessor.class;
        }

        @Override
        protected boolean shouldGenerateId() {
            return true;
        }

        @Override
        protected void doParse(Element element, BeanDefinitionBuilder builder) {
            logger.warn("\r\n<[ Namespace Handler DoParser ]>\r\n");

            String namespaces = element.getAttribute("namespaces");
            //default to application
            if (Strings.isNullOrEmpty(namespaces)) {
                namespaces = ConfigConsts.NAMESPACE_APPLICATION;
            }

            int order = Ordered.LOWEST_PRECEDENCE;
            String orderAttribute = element.getAttribute("order");

            if (!Strings.isNullOrEmpty(orderAttribute)) {
                try {
                    order = Integer.parseInt(orderAttribute);
                } catch (Throwable ex) {
                    throw new IllegalArgumentException(
                            String.format("Invalid order: %s for namespaces: %s", orderAttribute, namespaces));
                }
            }
            PropertySourcesProcessor.addNamespaces(NAMESPACE_SPLITTER.splitToList(namespaces), order);
        }
    }
}