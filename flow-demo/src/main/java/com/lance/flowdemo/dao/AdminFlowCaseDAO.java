package com.lance.flowdemo.dao;


import com.lance.flowdemo.entity.AdminFlowCase;
import com.lance.flowdemo.entity.FlowQuery;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import java.util.List;

@DAO
public interface AdminFlowCaseDAO {

    String TABLE_NAME = " admin_flow_case ";

    String INSERT_COLUMNS = "biz_no, flow_case_name, flow_key,initiator," +
            "`variables`,com_variables,ite_variables,status,approve_level," +
            "bpm_reviewer,url,actual_reviewer," +
            "create_time,update_time, tenant_id";

    String SELECT_COLUMNS = "id," + INSERT_COLUMNS;

    @ReturnGeneratedKeys
    @SQL("insert into " + TABLE_NAME + " (" + INSERT_COLUMNS + ") values(" +
            ":t.bizNo," +
            ":t.flowCaseName," +
            ":t.flowKey," +
            ":t.initiator," +
            ":t.variables," +
            ":t.comVariables," +
            ":t.iteVariables," +
            ":t.status," +
            ":t.approveLevel," +
            ":t.bpmReviewer," +
            ":t.url," +
            ":t.actualReviewer," +
            "unix_timestamp()*1000,unix_timestamp()*1000, :t.tenantId)")
    Long save(@SQLParam("t") AdminFlowCase adminFlowCase);


    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where id=:id")
    AdminFlowCase getById(@SQLParam("id") long id);

    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where tenant_id=:tenantId and id=:caseId")
    AdminFlowCase getByTenantIdAndCaseId(@SQLParam("tenantId") long tenantId, @SQLParam("caseId") long caseId);

    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where biz_no=:bizNo and flow_key =:flowKey")
    AdminFlowCase getByBizNoAndFlowKey(@SQLParam("bizNo") String bizNo, @SQLParam("flowKey") String flowKey);

    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where " +
            "tenant_id =:q.tenantId " +
            "#if(:q.name!=null && :q.name!=''){ and flow_case_name like :q.name }" +
            "#if(:q.flowKey>0){ and flow_key=:q.flowKey }" +
            "#if(:q.status>=0){ and status= :q.status } " +
            "#if(:q.status<0){ and status <> 0} " +
            "#if(:q.initiator>0){ and initiator=:q.initiator }" +
            "#if(:q.fromTime>0){ and create_time>=:q.fromTime }" +
            "#if(:q.toTime>0){ and create_time<=:q.toTime }" +
            " order by create_time desc limit :offset,:size")
    List<AdminFlowCase> query(@SQLParam("q") FlowQuery flowQuery, @SQLParam("offset") int offset,
                              @SQLParam("size") int size);

    @SQL("select count(1) from " + TABLE_NAME + " where " +
            "tenant_id =:q.tenantId " +
            "#if(:q.name!=null && :q.name!=''){ and flow_case_name like :q.name }" +
            "#if(:q.status>=0){ and status= :q.status } " +
            "#if(:q.status<0){ and status <> 0} " +
            "#if(:q.initiator>0){ and initiator=:q.initiator }" +
            "#if(:q.fromTime>0){ and create_time>=:q.fromTime }" +
            "#if(:q.toTime>0){ and create_time<=:q.toTime }")
    int getCount(@SQLParam("q") FlowQuery flowQuery);

    @SQL("update " + TABLE_NAME + " set " +
            " status=:t.status," +
            " actual_reviewer=:t.actualReviewer," +
            " update_time=unix_timestamp()*1000 " +
            " where id=:t.id")
    int updateFlowResult(@SQLParam("t") AdminFlowCase adminFlowCase);

    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where flow_key =:flowKey and status=:status and tenant_id=:tenantId  ")
    List<AdminFlowCase> getByKeyAndStatus(@SQLParam("flowKey") String flowKey, @SQLParam("status") int status, @SQLParam("tenantId") long tenantId);

    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where flow_key in(:flowKeyList)")
    List<AdminFlowCase> get4Task(@SQLParam("flowKeyList") List<String> flowKeyList);

    @SQL("update " + TABLE_NAME + " set ite_variables=:t.iteVariables where id=:t.id")
    int updateIteVariables(@SQLParam("t") AdminFlowCase adminFlowCase);
}
