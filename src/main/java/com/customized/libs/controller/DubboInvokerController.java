package com.customized.libs.controller;

import com.alibaba.fastjson.JSON;
import com.customized.libs.config.SDKConst;
import com.customized.libs.exception.InfException;
import com.customized.libs.libs.dubbo.DubboInvoker;
import com.customized.libs.model.CardinfoRequest;
import com.customized.libs.model.CommResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yan
 */
@RestController
@RequestMapping("dubbo")
@Slf4j
public class DubboInvokerController extends BaseController {

    private static final String FRAMEWORK_MODEL_REQUEST = "com.cardinfo.framework.model.CardinfoRequest";

    @Autowired
    private DubboInvoker dubboInvoker;

    @RequestMapping(value = "invoker", method = RequestMethod.POST)
    public ResponseEntity<CommResp> dispatcher(@RequestBody CardinfoRequest request)
            throws InfException {
        log.warn("BizRequest JSON ==> " + JSON.toJSONString(request));

        Object data = dubboInvoker.invoke(request.getClazz(), request.getMethod(),
                new String[]{FRAMEWORK_MODEL_REQUEST},
                new String[]{request.getBizContent()},
                request.getVersion()
        );

        CommResp theResp = new CommResp();
        theResp.setKey(SDKConst.CommonRespCode.OK);
        theResp.setMsg("SUCCESS");
        theResp.setData(data);

        log.warn("BizResponse JSON ==> " + JSON.toJSONString(theResp));
        return this.getJsonResp(theResp, HttpStatus.OK);
    }

    @RequestMapping(value = "invoker/noArgs", method = RequestMethod.POST)
    public ResponseEntity<CommResp> dispatcherNoArgs(@RequestBody CardinfoRequest request)
            throws InfException {
        log.warn("NoArgs BizRequest JSON ==> " + JSON.toJSONString(request));

        Object data = dubboInvoker.invoke(request.getClazz(), request.getMethod(),
                new String[]{}, new String[]{},
                request.getVersion()
        );

        CommResp theResp = new CommResp();
        theResp.setKey(SDKConst.CommonRespCode.OK);
        theResp.setMsg("SUCCESS");
        theResp.setData(data);

        log.warn("NoArgs BizResponse JSON ==> " + JSON.toJSONString(theResp));
        return this.getJsonResp(theResp, HttpStatus.OK);
    }
}
