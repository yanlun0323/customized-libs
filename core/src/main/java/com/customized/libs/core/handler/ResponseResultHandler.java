package com.customized.libs.core.handler;

import com.customized.libs.core.annotations.ResponseResult;
import com.customized.libs.core.config.SDKConst;
import com.customized.libs.core.model.CommResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.customized.libs.core.interceptor.ResponseResultInterceptor.RESPONSE_RESULT_ANN;

/**
 * @author yan
 */
@Slf4j
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        ResponseResult rra = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);
        return Objects.nonNull(rra);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType
            , Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("before body write interceptor ==> ");

        CommResp commResp = new CommResp();
        commResp.setKey(SDKConst.CommonRespCode.OK);
        commResp.setMsg("SUCCESS");
        commResp.setData(body);

        return commResp;
    }
}
