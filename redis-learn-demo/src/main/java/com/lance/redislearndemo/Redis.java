package com.lance.redislearndemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Redis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String KEY_PREFIX = "lance:redis-learn-demo:lock:";

    public HashOperations<String, String, String> hashOperations() {
        return stringRedisTemplate.opsForHash();
    }

    public SetOperations<String, String> setOperations() {
        return stringRedisTemplate.opsForSet();
    }

    public ListOperations<String, String> listOperations() {
        return stringRedisTemplate.opsForList();
    }

    public ZSetOperations<String, String> zSetOperations() {
        return stringRedisTemplate.opsForZSet();
    }

    public ValueOperations<String, String> valueOperations() {
        return stringRedisTemplate.opsForValue();
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void deleteKeys(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }


    public void expire(String key, long timeout, TimeUnit unit) {
        stringRedisTemplate.expire(key, timeout, unit);
    }

    public SetOperations<String, String> set() {
        return stringRedisTemplate.opsForSet();
    }

    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public long getListSize(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    public void listLeftPush(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public String listRightPop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    public String listRightPopWithTime(String key, long time, TimeUnit timeUnit) {
        return stringRedisTemplate.opsForList().rightPop(key, time, timeUnit);
    }

    public void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setValueWithTime(String key, String value, long time, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public long getExpireTime(String key, TimeUnit timeUnit) {
        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    public boolean lock(String key, long time, TimeUnit timeUnit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + key, "1", time, timeUnit);
    }

    public boolean lockWithValue(String key, String value, long time, TimeUnit timeUnit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + key, value, time, timeUnit);
    }

    public boolean unLock(String key, String value) {
        List<String> keys = Collections.singletonList(KEY_PREFIX + key);
        List<String> values = Collections.singletonList(value);

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

/*        RedisCallback<Long> callback = (connection) ->{
            Object nativeConn = connection.getNativeConnection();
            if (nativeConn instanceof JedisCluster) {
                return (Long)((JedisCluster) nativeConn).eval(script, keys, values);
            } else if (nativeConn instanceof Jedis) {
                return (Long)((Jedis) nativeConn).eval(script, keys, values);
            }
            return 0L;
        };*/


        Long result = stringRedisTemplate.execute((RedisCallback<Long>) connection -> {
            Object nativeConnection = connection.getNativeConnection();
            if (nativeConnection instanceof JedisCluster) {
                return (Long) ((JedisCluster) nativeConnection).eval(script, keys, values);
            } else if (nativeConnection instanceof Jedis) {
                return (Long) ((Jedis) nativeConnection).eval(script, keys, values);
            }
            return 0L;
        });

        return Objects.nonNull(result) && result.longValue() == 1L;

    }

    public long incrementValue(String key) {
        return stringRedisTemplate.opsForValue().increment(key, 1);
    }

    public long descrementValue(String key) {
        return stringRedisTemplate.opsForValue().decrement(key, 1);
    }

    public void listRemove(String key, long count, Object object) {
        stringRedisTemplate.opsForList().remove(key, count, object);
    }

    public boolean setIfAbsent(String key, String value, long time, TimeUnit unit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, time, unit);
    }

}
