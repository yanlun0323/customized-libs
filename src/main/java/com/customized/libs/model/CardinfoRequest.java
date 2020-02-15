package com.customized.libs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@ToString
public class CardinfoRequest implements Serializable {

    // 全链路请求唯一id
    private String requestId;

    // 业务参数
    private String bizContent;

    private String clazz;
    private String method;

    private String version;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("requestId", requestId);
        map.put("bizContent", bizContent);
        return map;
    }

    public CardinfoRequest() {
        this.requestId = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public CardinfoRequest(String requestId) {
        this.requestId = requestId;
    }

    public void copyFrom(CardinfoRequest cardinfoRequest) {
        setRequestId(cardinfoRequest.getRequestId());
        setBizContent(cardinfoRequest.getBizContent());
    }

    public static CardinfoRequest derive(CardinfoRequest request) {
        return new CardinfoRequest(request.getRequestId());
    }
}
