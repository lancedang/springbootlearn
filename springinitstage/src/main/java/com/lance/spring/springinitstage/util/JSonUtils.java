package com.lance.spring.springinitstage.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Provide JSon <--> POJO
 */
@Slf4j
public class JSonUtils {



    private static ObjectMapper mapper;
    public static ObjectMapper mapperInstance() {
        if (mapper == null) {
            synchronized (JSonUtils.class) {
                if (mapper == null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
                    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

                    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                    mapper = objectMapper;
                }
            }
        }

        return mapper;
    }

    public static String toJsonString(Object pojo) throws Exception {
        try {
            return mapperInstance().writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            log.error("JSonUtils.jsonToObject convert class {} to json failure", pojo.getClass().getName());
            throw new Exception("invalid convention");
        }
    }

    public static<T> T jsonToObject(String json, Class<T> c) throws Exception {

        T t;
        try {
            t = mapperInstance().readValue(json, c);
        } catch (IOException e){
            log.error("JSonUtils.jsonToObject convert {} to class {} failure", json, c.getName());
            throw new Exception("invalid convention");
        }
        return t;
    }
}

