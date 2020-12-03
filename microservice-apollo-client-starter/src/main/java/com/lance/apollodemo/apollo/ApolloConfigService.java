package com.lance.apollodemo.apollo;

import lombok.Data;

import java.util.Map;

@Data
public class ApolloConfigService {
    private Map<String, String> configMap;

    public ApolloConfigService build(Map<String, String> map) {
        this.configMap = map;
        return this;
    }


}
