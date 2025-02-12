package com.lance.flowdemo.entity

import lombok.Data

@Data
data class AdminFlowCase (
    var tenantId:Long=0L,
    var id:Long = 0L,
    var bizNo: String? = null,
    var flowCaseName: String = "",//流程名称
    var flowKey: String = "",//流程相应模板的key
    var initiator:Long = -1L,//流程发起人 -1代表是系统发起
    var variables: String = "",//普通表单数据kv
    var comVariables: String = "",//对比表单kv
    var iteVariables: String = "",//循环表单kv
    /**
     * @see com.xiaomi.mifi.scf.saas.enums.base.AdminFlowCaseStatusEnum
     */
    var status:Int = 0,
    var approveLevel:Int = 0,//审批层级,传0默认采用模板全部审批层级
    var bpmReviewer: String = "", //配置的审批人 同一层用英文逗号分隔,不同层用英文分号分开
    var actualReviewer: String = "", //实际审批人  不同层用英文分号分开
    var url: String = "", //审批跳转链接
    var updateTime:Long = 0L,
    var createTime:Long = 0L,
    var hideColumns:String = "" //需要隐藏的表单数据
)

