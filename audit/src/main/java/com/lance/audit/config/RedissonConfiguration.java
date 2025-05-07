package com.lance.audit.config;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 熊彬
 * @version 1.0
 * @date 2021-08-16 13:33
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedissonConfiguration {

    private String host;
    private String port;
    private String password;
    private Cluster cluster = new Cluster();

    /**
     * spring:
     *   redis:
     *     redisson:
     *       pool:
     *         masterConnectionMinimumIdleSize: 2
     *         masterConnectionPoolSize: 10
     *         slaveConnectionMinimumIdleSize: 2
     *         slaveConnectionPoolSize: 10
     */
    private RedissonWrapper redisson = new RedissonWrapper();

    private static  final String CLUSTER_PATTERN = "redis://%s";
    private static  final String SINGLE_PATTERN = "redis://%s:%s";

    @Data
    static public class Cluster{
        private String nodes;
    }

    @Data
    static public class RedissonWrapper {
        private Pool pool = new Pool();
    }

    @Data
    static public class Pool {
        private int masterConnectionMinimumIdleSize = 1;
        private int masterConnectionPoolSize        = 10;
        private int slaveConnectionMinimumIdleSize  = 1;
        private int slaveConnectionPoolSize         = 10;
    }

    @Bean
    @Primary
    public RedissonClient redissonClient(){
        Config config = new Config();
        String nodes = cluster.getNodes();
        if(StringUtils.hasText(nodes)){
            //集群配置
            log.info("redis cluster, {}", cluster);
            log.info("redisson pool, {}", redisson.getPool());
            List<String> nodeAddress = Stream.of(nodes.split(",")).map(item -> String.format(CLUSTER_PATTERN, item))
                    .collect(Collectors.toList());
            config.useClusterServers()
                    .setPassword(password)
                    .setScanInterval(2000)
                    .setMasterConnectionMinimumIdleSize(redisson.getPool().getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(redisson.getPool().getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(redisson.getPool().getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(redisson.getPool().getSlaveConnectionPoolSize())
                    .addNodeAddress(nodeAddress.toArray(new String[nodeAddress.size()]));
            return Redisson.create(config);
        }
        //单机配置
        log.info("redis single server");
        config.useSingleServer()
                .setPassword(password)
                .setAddress(String.format(SINGLE_PATTERN,host,port));
        return Redisson.create(config);
    }

}
