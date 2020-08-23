package com.customized.lib.web.controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;

import java.util.HashMap;
import java.util.Map;

@Path
public class UserController extends BaseController {

    @GetRoute("getUser")
    public void getUser(Request request, Response response) {
        String username = request.query("user").orElse(null);
        Map<String, Object> data = new HashMap<>(16);
        data.put("user", username);
        data.put("timestamp", System.currentTimeMillis());
        response.json(data);
    }

}
