package com.lance.rocketmqsdkdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lindejun
 * @Date: 2020-05-02 11:37
 */
@Slf4j
@Component
public class Redis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public final static String KEY_PREFIX = "scf:saas:cloud:lock:";

    /**
     * 锁等待，防止线程饥饿,毫秒
     */
    private static final int ACQUIRE_TIMEOUT = 1000;

    private static final String SET_IF_NOT_EXIST = "NX";
    // 时间单位是秒
    private static final String SET_TIME_SECONDS = "EX";
    //时间单位是毫秒
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final String LOCK_SUCCESS = "OK";

    public Set<String> getKeys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    public HashOperations<String, String, String> hash() {
        return stringRedisTemplate.opsForHash();
    }

    public SetOperations<String, String> set() {
        return stringRedisTemplate.opsForSet();
    }

    public ListOperations<String, String> list() {
        return stringRedisTemplate.opsForList();
    }

    public ZSetOperations<String, String> zset() {
        return stringRedisTemplate.opsForZSet();
    }

    public ValueOperations<String, String> string() {
        return stringRedisTemplate.opsForValue();
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    public void expire(String antiKey, long timeout, final TimeUnit unit) {
        stringRedisTemplate.expire(antiKey, timeout, unit);
    }

    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public long getQueueSize(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    public void lPush(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public String rPop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    public String bRPop(String key, long time, TimeUnit timeUnit) {
        return stringRedisTemplate.opsForList().rightPop(key, time, timeUnit);
    }

    public Long listSize(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    public void add(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void add(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void addMillis(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public Long getExpire(String key, TimeUnit timeUnit) {
        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    public String rPopLPush(String taskKey, String doingKey, long time, TimeUnit timeUnit) {
        return stringRedisTemplate.opsForList().rightPopAndLeftPush(taskKey, doingKey, time, timeUnit);
    }

    public void remove(String doingKey, long l, Object o) {
        stringRedisTemplate.opsForList().remove(doingKey, l, o);
    }

    public List<String> range(String doingKey, long l, long l1) {
        return stringRedisTemplate.opsForList().range(doingKey, l, l1);
    }


    public boolean releaseLock(String key, String value) {
        List<String> keys = Collections.singletonList(KEY_PREFIX + key);
        List<String> values = Collections.singletonList("" + value);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

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

    public long incr(String key) {
        return stringRedisTemplate.opsForValue().increment(key, 1);
    }

    public Long incr(String key, long seconds) {
        long result = stringRedisTemplate.opsForValue().increment(key, 1);

        if (result == 1) {
            stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }

        return result;
    }

    public Long decr(String key) {
        return stringRedisTemplate.opsForValue().decrement(key, 1);
    }

    public boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }


    /**
     * 加锁
     *
     * @param lockKey
     * @param second
     * @return
     */
    public String lock(String lockKey, long second) {
        try {
            long end = System.currentTimeMillis() + ACQUIRE_TIMEOUT;
            // 随机生成一个value
            String requireToken = UUID.randomUUID().toString();
            while (System.currentTimeMillis() < end) {
                Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, requireToken, second, TimeUnit.SECONDS);
                if (success) {
                    return requireToken;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            log.error("lock throw error. lockKey:{}, second:{}", lockKey, second, e);
        }

        return null;
    }
}
