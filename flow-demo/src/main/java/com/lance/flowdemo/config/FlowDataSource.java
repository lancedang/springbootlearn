package com.lance.flowdemo.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
public class FlowDataSource {

    @Value("${dataSource.url}")
    private String url;

    @Value("${dataSource.pwd}")
    private String pwd;

    @Value("${dataSource.user}")
    private String user;

    @Bean(name = {"jade.dataSource.com.lance.flowdemo.dao"})
    @Primary
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pwd);

        basicDataSource.setValidationQuery("select 1");
        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setTestOnReturn(false);
        basicDataSource.setTestWhileIdle(true);
        basicDataSource.setTimeBetweenEvictionRunsMillis(3000000L);
        basicDataSource.setMinEvictableIdleTimeMillis(60000L);
        basicDataSource.setNumTestsPerEvictionRun(-1);
        basicDataSource.setInitialSize(20);
        basicDataSource.setMaxActive(50);
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMinIdle(10);
        basicDataSource.setMaxWait(100L);

        return basicDataSource;

    }

    @Bean
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate tpl = new TransactionTemplate(transactionManager);
        tpl.setIsolationLevelName("ISOLATION_REPEATABLE_READ");
        tpl.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        tpl.setTimeout(30);
        return tpl;
    }

}
