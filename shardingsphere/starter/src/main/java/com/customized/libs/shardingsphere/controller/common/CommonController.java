package com.customized.libs.shardingsphere.controller.common;

import com.customized.libs.shardingsphere.entity.common.CommResp;
import com.customized.libs.shardingsphere.entity.common.SDKConst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yan
 */
@RestController
public class CommonController extends BaseController {

    /**
     * 自定义Sentinel限流提示Page
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/sentinel/limit")
    public ResponseEntity<Object> ping() {
        CommResp commResp = new CommResp();
        commResp.setData("访问过于频繁，请稍后重试");
        commResp.setKey(SDKConst.CommonRespCode.ERROR);
        return this.getJsonResp(commResp, HttpStatus.TOO_MANY_REQUESTS);
    }
}
