package com.lance.flowdemo.entity;

import lombok.Data;

@Data
public class AdminFlowTemplateConfig {
    private Long id;
    private Long tenantId;
    private String key;
    private Integer defaultInitiator;
    private Integer approveLevel;
    private String bpmReviewers;
    private Long createTime;
    private Long updateTime;
    private String createBy;
    private String updateBy;

    private String name;
    private String title;
    private String url;
    private String urlParam;
    private String tableData;
}