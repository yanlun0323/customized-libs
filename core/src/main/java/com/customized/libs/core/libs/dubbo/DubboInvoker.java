package com.customized.libs.core.libs.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.customized.libs.core.exception.InfException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yan
 */
@Component
@Slf4j
public class DubboInvoker {

    /**
     * ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接 缓存所有的reference
     */
    public static Map<String, ReferenceConfig<GenericService>> REFERENCE_MAP = new ConcurrentHashMap<>(64);

    @Resource
    private ApplicationConfig application = null;

    @Resource
    private RegistryConfig registry = null;

    /**
     * 构建远程服务对象
     *
     * @param clazz
     * @param version
     * @return
     */
    private GenericService buildGenericService(String clazz, String version) {
        String cacheKey = clazz + "_" + version;

        ReferenceConfig<GenericService> config;
        if (REFERENCE_MAP.containsKey(cacheKey)) {
            config = REFERENCE_MAP.get(cacheKey);
        } else {
            config = new ReferenceConfig<>();
            config.setApplication(application);
            // 多个注册中心可以用setRegistries()
            config.setRegistry(registry);
            config.setGeneric(true);
            config.setInterface(clazz);
            config.setVersion(version);
            REFERENCE_MAP.put(cacheKey, config);
        }

        GenericService genericService = config.get();
        if (genericService == null) {
            REFERENCE_MAP.remove(cacheKey);
        }
        return genericService;
    }

    public Object invoke(String clazz, String methodName, String[] parameterTypes
            , Object[] parameters, String version) throws InfException {

        GenericService service = this.buildGenericService(clazz, version);
        if (null != service) {
            return service.$invoke(methodName, parameterTypes, parameters);
        } else {
            log.info("find libs is failure , clazz:" + clazz + " version:" + version);
            String msg = "find libs is failure , clazz:" + clazz + " version:" + version;
            throw new InfException("SERVICE_UNAVAILABLE", msg, " ");
        }
    }
}
