package com.customized.libs.core.libs.spring.rest.template;

import com.customized.libs.core.utils.MapBuilder;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriTemplateHandler;

import java.net.URI;

/**
 * @author yan
 */
public class DefaultUriTemplateHandlerTest {

    public static void main(String[] args) {
        DefaultUriTemplateHandler templateHandler = new DefaultUriTemplateHandler();
        String uriTemplate = "http://localhost:8080/user/{0}";
        URI uri = templateHandler.expand(uriTemplate, "Yanlun");

        System.out.println(uri.toString());

        URI uri0 = templateHandler.expand(uriTemplate, MapBuilder.create("0", "Yanlun").get());

        System.out.println(uri0.toString());


        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        URI uri1 = defaultUriBuilderFactory.expand(uriTemplate, MapBuilder.create("0", "Yanlun").get());

        System.out.println(uri1.toString());
    }
}
