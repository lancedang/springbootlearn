package com.lance.flowdemo.dao;

import com.lance.flowdemo.entity.AdminFlowTemplateConfig;
import com.lance.flowdemo.entity.FlowTemplateQuery;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import java.util.List;

@DAO
public interface AdminFlowTemplateConfigDAO {

    String TABLE_NAME = " admin_flow_template_config ";
    String TABLE_NAME2 = " admin_flow_template ";

    String INSERT_COLUMNS = "tenant_id, `key`, default_initiator,bpm_reviewers,approve_level," +
            "create_time,update_time, create_by, update_by";
    String SELECT_COLUMNS = "id," + INSERT_COLUMNS;

    @ReturnGeneratedKeys
    @SQL("insert into " + TABLE_NAME + " (" + INSERT_COLUMNS + ") values(" +
            ":t.tenantId," +
            ":t.key," +
            ":t.defaultInitiator," +
            ":t.bpmReviewers," +
            ":t.approveLevel," +
            "unix_timestamp()*1000,unix_timestamp()*1000,:t.createBy, :t.updateBy)")
    Long save(@SQLParam("t") AdminFlowTemplateConfig adminFlowTemplateConfig);

    @SQL("select  a.*,b.name, b.title,b.url,b.url_param,b.table_data from " + TABLE_NAME + " a, " + TABLE_NAME2 + " b where a.`key` = b.`key` and" +
            " a.`key`=:key and a.tenant_id=:tenantId")
    AdminFlowTemplateConfig getByKeyAndTenantId(@SQLParam("key") String key, @SQLParam("tenantId") Long tenantId);

    @SQL("select  a.*,b.name, b.title, b.url,b.url_param,b.table_data from " + TABLE_NAME + " a, " + TABLE_NAME2 + " b where a.`key` = b.`key`  and a.tenant_id=:t.tenantId and b.status=0 " +
            " #if(:t.flowName!= null){ and b.name like :t.flowName} " +
            " order by a.id desc limit :offset,:size")
    List<AdminFlowTemplateConfig> getList(@SQLParam("t") FlowTemplateQuery flowTemplateQuery, @SQLParam("offset") int offset,
                                          @SQLParam("size") int size);

    @SQL("select  count(*) from " + TABLE_NAME + " a, " + TABLE_NAME2 + " b where a.`key` = b.`key`  and a.tenant_id=:t.tenantId " +
            " #if(:t.flowName!= null){ and b.name like :t.flowName} ")
    int getCount(@SQLParam("t") FlowTemplateQuery flowTemplateQuery);

    @SQL("update " + TABLE_NAME + " set  " +
            "bpm_reviewers=:t.bpmReviewers," +
            "approve_level=:t.approveLevel," +
            "update_by=:t.updateBy," +
            "update_time=unix_timestamp()*1000" +
            " where id=:t.id")
    int updateTemplate(@SQLParam("t") AdminFlowTemplateConfig adminFlowTemplateConfig);
}
