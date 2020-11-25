package com.customized.libs.springboot.controller;

import com.customized.libs.springboot.service.BatchQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yan
 */
@RestController
@Api(value = "eureka-provider")
public class MultiRequestController {

    private final BatchQueryService queryService;

    public MultiRequestController(BatchQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/data/batch/{id}")
    public Object queryBatchById(@ApiParam(value = "ID") @PathVariable("id") String id) {
        return this.queryService.queryById(id);
    }
}
