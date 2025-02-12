package com.lance.learn.redisdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.jedis.pool.max-total}")
    private Integer maxTotal;
    @Value("${spring.redis.jedis.pool.max-wait-millis}")
    private Integer maxWaitMillis;
    @Value("${spring.redis.jedis.pool.min-evictable-idle-time-millis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.redis.jedis.pool.num-tests-per-eviction-run}")
    private Integer numTestsPerEvictionRun;
    @Value("${spring.redis.jedis.pool.time-between-eviction-runs-millis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${spring.redis.jedis.pool.test-on-borrow}")
    private Boolean testOnBorrow;
    @Value("${spring.redis.jedis.pool.test-while-idle}")
    private Boolean testWhileIdle;

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.cluster.max-redirects}")
    private Integer maxRedirects;


    @PostConstruct
    private void init() throws Exception {
        //password = scfKeyCenterManager.getPassword(password);
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 最小空闲数
        jedisPoolConfig.setMinIdle(minIdle);
        // 连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(maxTotal);
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        return jedisPoolConfig;
    }

    @Bean
    public JedisCluster getJedisCluster() {
        String[] nodes = clusterNodes.split(",");
        Set<HostAndPort> nodeSet = new HashSet<>();
        for (String node : nodes) {
            String[] arr = node.split(":");
            nodeSet.add(new HostAndPort(arr[0], Integer.parseInt(arr[1])));
        }
        return new JedisCluster(nodeSet, maxWaitMillis, maxWaitMillis, maxRedirects, password,
                jedisPoolConfig());
    }
}
