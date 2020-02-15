package com.customized.libs.config;

import com.customized.libs.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yan
 */
@Service
public class WebInitialization {

    private static Map<String, Object> EMPTY_MAP = new HashMap<>(16);

    @Autowired
    private DispatchService dispatchService;

    @PostConstruct
    public void init() {
        this.dispatchService.dispatch(EMPTY_MAP);
    }
}
