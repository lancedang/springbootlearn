package com.lance.learn.redisdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 */
@Service
public class RedisService {
	private static final String OK = "OK";
	private static final String NX = "NX";
	private static final String PX = "PX";
	private static final Long RELEASE_SUCCESS = 1L;

	@Autowired
	private JedisCluster jedisCluster;

	//timeout是锁字段失效时长，PX是毫秒，即timeout毫秒后失效，NX是key不存在时才加锁，OK是固定返回值
	public boolean addDistributeLock(String key, String value, long timeout) {
		return OK.equals(jedisCluster.set(key, value, NX, PX, timeout));
	}

	public boolean releaseDistributedLock(String lockKey, String requestId) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = this.jedisCluster.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
		return RELEASE_SUCCESS.equals(result);
	}

}
