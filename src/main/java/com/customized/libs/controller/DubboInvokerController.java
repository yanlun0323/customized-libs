package com.customized.libs.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.customized.libs.config.SDKConst;
import com.customized.libs.exception.InfException;
import com.customized.libs.expand.components.SentinelRules;
import com.customized.libs.libs.dubbo.DubboInvoker;
import com.customized.libs.model.CardinfoRequest;
import com.customized.libs.model.CommResp;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(DubboInvokerController.class);

    private static final String FRAMEWORK_MODEL_REQUEST = "com.cardinfo.framework.model.CardinfoRequest";

    @Autowired
    private DubboInvoker dubboInvoker;

    /**
     * 注意：注解方式埋点不支持 private 方法。
     *
     * @param request
     * @return
     * @throws InfException
     */
    @SentinelResource(value = SentinelRules.DUBBO_INVOKER_RESOURCE, blockHandler = "dubboInvokerStandardBlockHandler")
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

    @SentinelResource(value = SentinelRules.DUBBO_INVOKER_NOARGS_RESOURCE, blockHandler = "dubboInvokerStandardBlockHandler")
    @RequestMapping(value = "invoker/noArgs", method = {RequestMethod.GET, RequestMethod.POST})
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


    @SentinelResource(value = SentinelRules.DUBBO_INVOKER_EMPTY_RESOURCE, blockHandler = "dubboInvokerEmptyBlockHandler")
    @RequestMapping(value = "invoker/empty", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<CommResp> dispatcherEmpty() {
        CommResp theResp = new CommResp();

        theResp.setKey(SDKConst.CommonRespCode.OK);
        theResp.setMsg("SUCCESS");

        return this.getJsonResp(theResp, HttpStatus.OK);
    }

    /**
     * blockHandler 函数，原方法调用被限流/降级/系统保护的时候调用
     *
     * @param ex
     */
    public ResponseEntity<CommResp> dubboInvokerEmptyBlockHandler(BlockException ex) {
        logger.error("<<< 方法调用被限流/降级/系统保护", ex);

        CommResp theResp = new CommResp();
        theResp.setKey(SDKConst.CommonRespCode.ERROR);
        theResp.setMsg("系统繁忙，请稍后重试");
        return this.getJsonResp(theResp, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<CommResp> dubboInvokerStandardBlockHandler(CardinfoRequest request, BlockException ex) {
        log.warn("BizRequest JSON ==> " + JSON.toJSONString(request));

        logger.error("<<< 方法调用被限流/降级/系统保护", ex);

        CommResp theResp = new CommResp();
        theResp.setKey(SDKConst.CommonRespCode.ERROR);
        theResp.setMsg("系统繁忙，请稍后重试");
        return this.getJsonResp(theResp, HttpStatus.FORBIDDEN);
    }
}
