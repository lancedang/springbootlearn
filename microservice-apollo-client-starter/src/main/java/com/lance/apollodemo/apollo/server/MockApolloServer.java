package com.lance.apollodemo.apollo.server;

import java.util.HashMap;
import java.util.Map;

public class MockApolloServer {

    private static final String DEFAULT_APPLICATION = "application";
    private static final String APPLICATION_1 = "application1";
    private static final String APPLICATION_2 = "application1";


    public static final Map<String, Map<String, String>> configMap = new HashMap<>();

    static {
        HashMap<String, String> tempMap = new HashMap<>();
        tempMap.put("name", "default-app");
        configMap.put(DEFAULT_APPLICATION, tempMap);

        tempMap = new HashMap<>();
        tempMap.put("name", "app1");
        configMap.put(APPLICATION_1, tempMap);

        tempMap = new HashMap<>();
        tempMap.put("name", "app2");
        configMap.put(APPLICATION_2, tempMap);
    }


    public static Map<String, String> getMap(String key) {
        return configMap.get(key);
    }
}
