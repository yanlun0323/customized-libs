package com.customized.libs.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yan
 */
@Controller
public class ThymeleafPageController {

    @RequestMapping(value = "/templates/home", method = RequestMethod.GET)
    public String toIndex() {
        return "home";
    }
}
