package com.customized.lib.web.controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.http.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path
public class WebIndexController {

    private static Logger logger = LoggerFactory.getLogger(WebIndexController.class);

    @GetRoute("/")
    public String index(Request request) {
        logger.warn(request.query("user", "default"));

        request.attribute("user", request.query("user", "Yan"));
        return "index.html";
    }
}
