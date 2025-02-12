package com.lance.rocketmqsdkdemo.service;

import com.lance.rocketmqsdkdemo.config.Redis;
import com.xiaomi.mifi.scf.monitor.iface.utils.EnvUtils;
import io.prometheus.client.*;
import io.prometheus.client.exporter.PushGateway;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 功能描述: prometheus指标埋点 操作类
 */
@Slf4j
@Data
@Service
public class MonitorService {

    @Autowired
    private Redis redis;


    /**
     * prometheus 注册实例
     */
    private CollectorRegistry registry;


    String testKey = "test_key_mq_6";


    /**
     * 指标统一容器
     * <p>
     * counter: 计数器类
     * gauge: 仪表盘类
     * histogram: 直方图类
     * summary: 摘要类
     */
    private static final ConcurrentMap<String, Counter> counterMetricContainer = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Gauge> gaugeMetricContainer = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Histogram> histogramMetricContainer = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Summary> summaryMetricContainer = new ConcurrentHashMap<>();


    private static String url;

    private static String prometheusUrl;

    static {
        try {
            boolean production = true || EnvUtils.isProduction();
            if (production) {
                url = "10.128.192.43:9091";
                prometheusUrl = "http://monitor.scf.pt.xiaomi.com";
            } else {
                url = "10.241.55.206:9091";
                prometheusUrl = "http://monitor.scf.pt.xiaomi.com";
            }
        } catch (Exception e) {
            log.error("获取环境异常！");
            url = "10.128.192.43:9091";
            prometheusUrl = "http://monitor.scf.pt.xiaomi.com";
        }

    }

    private static final String DELIMITER = "@@";

    /**
     * prometheus 网关实例
     */
    private static PushGateway pushgateway = new PushGateway(url);

    /**
     * 项目埋点统一前缀
     */
    private static final String METRICS_PREFIX = "";

    /**
     * 推送gateway线程池
     */
    private ScheduledExecutorService executorService;


    @PostConstruct
    public void init() {
        //初始化registry
//        registry = new CollectorRegistry();
//        initMetric();
        executorService =
                new ScheduledThreadPoolExecutor(1, r -> new Thread(r, "prometheus-pushGateway-redis"));
        startPrometheusPushTask();

    }

    private void initMetric() {
        Long size = redis.set().size(testKey);
        log.info("指标集合中共有{}个key", size);
        //ScanOptions scanOptions = ScanOptions.scanOptions().count(size).match("out_service_*_request_total@@*").build();
        ScanOptions scanOptions = ScanOptions.scanOptions().count(size).match("sandbox_1_asset_repay_info_test*").build();
        Cursor<String> keys = redis.set().scan(testKey, scanOptions);
        if (keys != null) {
            int count = 0;
            while (keys.hasNext()) {
                //需要判断key是否还存在
                String key = keys.next();
                if (StringUtils.isBlank(redis.get(key))) {
                    redis.set().remove(testKey, key);
                    log.info("元素{}已过期，进行清理", key);
                    continue;
                }
                Map<String, String> tags = getTagsByKey(key);

                counterRegistry(key.split(DELIMITER)[0], tags);
                count++;
            }
            log.info("共推送{}条指标数据", count);
        }
    }

    private Map<String, String> getTagsByKey(String key) {
        String[] tags = key.split(DELIMITER);
        Map<String, String> tagMap = new HashMap<>();
        for (int i = 1; i < tags.length; i++) {
            String[] keyValue = tags[i].split("=");
            tagMap.put(keyValue[0], keyValue[1]);
        }
        return tagMap;
    }

    public void counter(String metricName, Map<String, String> tags) {
        log.info("counter metric {} add", metricName);
        try {
            String redisKey = getRedisKey(metricName, tags);
            //获取当前redis最新值

            //计数+1
            redis.incr(redisKey);
            redis.set().add(testKey, redisKey);

            redis.expire(redisKey, 3, TimeUnit.DAYS);
        } catch (Exception e) {
            log.error("count by redis error", e);
        }

    }

    public void counterRegistry(String metricName, Map<String, String> tags) {
        try {
            Set<String> keySet = tags.keySet();
            String[] keys = keySet.toArray(new String[0]);
            Counter counter = registerCounter(metricName, keys);
            Collection<String> values = tags.values();
            String[] tagValues = values.toArray(new String[0]);
            String redisKey = getRedisKey(metricName, tags);
            String redisValue = redis.get(redisKey);
            if (StringUtils.isNotBlank(redisValue)) {
                counter.labels(tagValues).inc(Double.valueOf(redisValue));
            } else {
                counter.labels(tagValues).inc(0);
            }
        } catch (Exception e) {
            log.error("monitor counterRegistry error!", e);
        }
    }

    private String getRedisKey(String metricName, Map<String, String> tags) {
        StringBuilder builder = new StringBuilder(metricName);
        for (Map.Entry<String, String> entry : tags.entrySet()) {
            String tag = entry.getKey() + "=" + entry.getValue();
            builder.append(DELIMITER);
            builder.append(tag);
        }
        return builder.toString();
    }

    private String getOldRedisKey(String metricName, String[] tagValues) {
        return metricName + "." + String.join(".", tagValues);
    }


    /**
     * 注册一个prometheus的监控指标, 并返回指标实例
     *
     * @param metricName 指标名称(只管按业务命名即可: 数字+下划线)
     * @param labelNames 所要监控的子指标名称,会按此进行分组统计
     * @return 注册好的counter实例
     */
    private Counter registerCounter(String metricName, String... labelNames) {
        Counter counter = counterMetricContainer.get(metricName);
        if (counter == null) {
            synchronized (counterMetricContainer) {
                counter = counterMetricContainer.get(metricName);
                if (counter == null) {
                    counter = Counter.build()
                            .name(METRICS_PREFIX + metricName)
                            .labelNames(labelNames)
                            .help(metricName + " counter")
                            .register(registry);
                    counterMetricContainer.put(metricName, counter);
                }
            }
        }
        return counter;
    }



    /**
     * 开启推送 gateway 线程
     */
    private void startPrometheusPushTask() {
        executorService.scheduleAtFixedRate(() -> {
            try {
                log.info("push gateway thread exec start");
                pushMetric();
            } catch (Exception e) {
                log.error("【prometheus】推送gateway失败: " + e.getMessage(), e);
            }
        }, 1, getPrometheusPushInterval(), TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                executorService.shutdown();
                log.info("monitor executorService shutdown");
            }
        }));
//        }
    }

    /**
     * 定时推送指标到PushGateway
     */
    private void pushMetric() throws IOException {

        counterMetricContainer.clear();
        registry = new CollectorRegistry();
        initMetric();
        pushgateway.pushAdd(registry, "push_job");
        log.info("push gateway thread exec end");

    }

    /**
     * 获取prometheus推送网关频率(单位:s)
     *
     * @return 频率如: 10(s)
     */
    private Integer getPrometheusPushInterval() {
        return 60;
    }

}