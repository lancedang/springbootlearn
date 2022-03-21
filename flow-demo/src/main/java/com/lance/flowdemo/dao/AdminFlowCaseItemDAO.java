package com.lance.flowdemo.dao;


import com.lance.flowdemo.entity.AdminFlowCaseItem;
import com.lance.flowdemo.entity.FlowItemQuery;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import java.util.List;

@DAO
public interface AdminFlowCaseItemDAO {

    String TABLE_NAME = " admin_flow_case_item ";
    String INSERT_COLUMNS = "case_id,flow_case_name,user_id,initiator,url,status,comment,approver,approve_time," +
        "create_time,update_time,tenant_id";
    String SELECT_COLUMNS = "id," + INSERT_COLUMNS;

    @ReturnGeneratedKeys
    @SQL("insert into " + TABLE_NAME + " (" + INSERT_COLUMNS + ") values(" +
        ":t.caseId," +
        ":t.flowCaseName," +
        ":t.userId," +
        ":t.initiator," +
        ":t.url," +
        ":t.status," +
        ":t.comment," +
        ":t.approver," +
        ":t.approveTime," +
        "unix_timestamp()*1000,unix_timestamp()*1000," +
        ":t.tenantId)")
    Long save(@SQLParam("t") AdminFlowCaseItem adminFlowCaseItem);

    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where tenant_id =:t.tenantId " +
        "#if(:t.userId>0){ and user_id= :t.userId } " +
        "#if(:t.status>=0){ and status= :t.status } " +
        "#if(:t.status<0){ and status <> 0} " +
        "#if(:t.initiator>0){ and initiator= :t.initiator } " +
        "order by create_time desc limit :offset,:size")
    List<AdminFlowCaseItem> queryItem(@SQLParam("t") FlowItemQuery query, @SQLParam("offset") int offset,
                                      @SQLParam("size") int size);

    @SQL("select count(1) from " + TABLE_NAME + " where tenant_id =:t.tenantId " +
        "#if(:t.userId>0){ and user_id= :t.userId } " +
        "#if(:t.status>=0){ and status= :t.status } " +
        "#if(:t.status<0){ and status <> 0} " +
        "#if(:t.initiator>0){ and initiator= :t.initiator } ")
    int getCount(@SQLParam("t") FlowItemQuery query);

    @SQL("update " + TABLE_NAME + " set " +
        "status=:t.status," +
        "approver=:t.approver," +
        "approve_time=:t.approveTime," +
        "comment=:t.comment," +
        "update_time=unix_timestamp()*1000," +
        "extra_file_list=:t.extraFileList" +
        " where id=:t.id")
    int updateItemResult(@SQLParam("t") AdminFlowCaseItem adminFlowCaseItem);

    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where case_id=:caseId ")
    List<AdminFlowCaseItem> getByCaseId(@SQLParam("caseId") long caseId);
    @SQL("select " + SELECT_COLUMNS + " from " + TABLE_NAME + " where tenant_id=:tenantId and case_id=:caseId ")
    List<AdminFlowCaseItem> getByTenantIdAndCaseId(@SQLParam("tenantId") long tenantId, @SQLParam("caseId") long caseId);


    @SQL("update " + TABLE_NAME + " set status=2, approve_time=unix_timestamp()*1000, update_time=unix_timestamp()*1000 where case_id=:caseId and tenant_id=:tenantId ")
    int refuse(@SQLParam("tenantId") long tenantId, @SQLParam("caseId") long caseId);
}
