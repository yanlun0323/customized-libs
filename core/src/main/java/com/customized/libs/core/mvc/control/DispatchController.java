package com.customized.libs.core.mvc.control;

import com.customized.libs.core.mvc.annotations.MvcController;
import com.customized.libs.core.mvc.annotations.MvcMapping;
import com.customized.libs.core.mvc.annotations.MvcRequestParam;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@MvcController
@Slf4j
public class DispatchController {

    @MvcMapping(name = "mvc/dispatcher")
    public String dispatch(@MvcRequestParam(name = "args0", defaultValue = "") String args, String username) {
        String rt = Arrays.asList(args, username).toString();
        log.warn("Dispatcher ==> " + rt);
        return rt;
    }

    @MvcMapping(name = "mvc/dispatcher2")
    public Object dispatch2(@MvcRequestParam(name = "args0",
            defaultValue = "Dispatcher") String args, String username) {
        List<String> rt = Arrays.asList(args, username);
        log.warn("Dispatcher2 ==> " + rt);
        return rt;
    }
}
