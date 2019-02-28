package com.lance.boot.actuator.actuatordemo.service;

import com.lance.boot.actuator.actuatordemo.util.SelfKairosdbUtil;
import org.kairosdb.client.Client;
import org.kairosdb.client.builder.MetricBuilder;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.TimeUnit;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.QueryResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelfKairosdbService {

    public void writeMetricToKairosdb() {
        Client client = SelfKairosdbUtil.getKairosdbClient();
        MetricBuilder builder = MetricBuilder.getInstance();
        builder.addMetric(this.getClass().getSimpleName() + ".test")
                .addTag("host", "server1")
                .addTag("customer", "Acme")
                .addDataPoint(System.currentTimeMillis(), 10)
                .addDataPoint(System.currentTimeMillis(), 30L);
        client.pushMetrics(builder);
    }

    public List<QueryResult> findMetricPoint() {
        Client client = SelfKairosdbUtil.getKairosdbClient();
        QueryBuilder builder = QueryBuilder.getInstance();
        builder.setStart(10, TimeUnit.MINUTES)
                .addMetric(this.getClass().getSimpleName() + ".test");
        QueryResponse response = client.query(builder);
        List<QueryResult> queryResults = response.getQueries();
        return queryResults;
    }
}
