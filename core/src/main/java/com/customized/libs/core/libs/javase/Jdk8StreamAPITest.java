package com.customized.libs.core.libs.javase;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.stream.Collectors;

public class Jdk8StreamAPITest {

    public static void main(String[] args) {
        Map<String, Set<RpcProtocol>> service = new HashMap<>();

        Set<RpcProtocol> objects = new HashSet<>();
        objects.add(new RpcProtocol("127", 9900, "queryUser"));
        service.put("127", objects);

        Set<RpcProtocol> objects2 = new HashSet<>();
        objects2.add(new RpcProtocol("128", 9901, "queryUser"));
        service.put("128", objects2);
        Set<RpcProtocol> set = service.entrySet().stream().map(Map.Entry::getValue).flatMap(Collection::stream).collect(Collectors.toSet());

        System.out.println(JSON.toJSONString(set));

        Map<String, List<RpcProtocol>> collect = service.entrySet().stream().map(Map.Entry::getValue).flatMap(Collection::stream)
                .collect(Collectors.groupingBy(RpcProtocol::getService));
        System.out.println(collect);
    }

    public static class RpcProtocol {
        private String host;
        private Integer port;

        private String service;

        public RpcProtocol(String host, Integer port, String servivce) {
            this.host = host;
            this.port = port;
            this.service = servivce;
        }

        public String getSrvAddr() {
            return String.format("%s:%s", this.host, this.port);
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }
    }
}
