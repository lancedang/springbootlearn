package com.lance.redislearndemo;

import org.junit.Test;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class RedisTest {

    @Test
    public void hashOperations() {
    }

    @Test
    public void setOperations() {
    }

    @Test
    public void listOperations() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties("self.properties");
        System.out.println(properties.getProperty("name"));
    }

    @Test
    public void zSetOperations() {
    }

    @Test
    public void valueOperations() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteKeys() {
    }

    @Test
    public void expire() {
    }

    @Test
    public void hasKey() {
    }

    @Test
    public void getValue() {
    }

    @Test
    public void getListSize() {
    }

    @Test
    public void listLeftPush() {
    }

    @Test
    public void listRightPop() {
    }

    @Test
    public void listRightPopWithTime() {
    }

    @Test
    public void setValue() {
    }

    @Test
    public void setValueWithTime() {
    }

    @Test
    public void getExpireTime() {
    }

    @Test
    public void lock() {
    }

    @Test
    public void lockWithValue() {
    }

    @Test
    public void unLock() {
    }

    @Test
    public void incrementValue() {

    }

    @Test
    public void listRemove() {
    }
}