package com.customized.libs.shardingsphere.controller.common;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.customized.libs.shardingsphere.entity.common.CommResp;
import com.customized.libs.shardingsphere.entity.common.SDKConst;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author yan
 */

/**
 * 自定义BlockExceptionHandler（此处定义后sentinel.blockPage将失效）
 * <p>
 * com.alibaba.cloud.sentinel.SentinelWebAutoConfiguration#sentinelWebMvcConfig()
 * <p>
 * if (blockExceptionHandlerOptional.isPresent()) {
 * blockExceptionHandlerOptional
 * .ifPresent(sentinelWebMvcConfig::setBlockExceptionHandler);
 * }
 *
 * @author yan
 */
@Service
public class CustomBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        // Return 429 (Too Many Requests) by default.
        response.setStatus(429);
        response.setCharacterEncoding("UTF-8");
        // ContentType设置charset有效，但是setCharacterEncoding无效？？
        response.setContentType("application/json;charset=utf-8");

        StringBuffer url = request.getRequestURL();

        if ("GET".equals(request.getMethod()) && StringUtil.isNotBlank(request.getQueryString())) {
            url.append("?").append(request.getQueryString());
        }

        CommResp commResp = new CommResp();
        commResp.setData("访问过于频繁，请稍后重试");
        commResp.setKey(SDKConst.CommonRespCode.ERROR);

        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(commResp));
        out.flush();
        out.close();
    }

}