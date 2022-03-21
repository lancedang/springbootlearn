package com.lance.flowdemo.entity

import lombok.Data

@Data
data class AdminFlowCaseItem (
    var tenantId:Long = 0L,
    var id:Long = 0L,
    var caseId:Long = 0L,
    var flowCaseName:String = "",//流程名称
    var userId:Long = 0L ,//审批结点用户
    var initiator:Long = 0L,//流程发起人

    /**
     * @see com.xiaomi.mifi.scf.saas.enums.base.AdminFlowCaseStatusEnum
     */
    var status:Int = 0,
    var comment: String? = "",
    var approveTime:Long = 0L ,//审批时间

    /**实际审批人*/
    var approver:Long = 0L,
    var url:String = "" ,//审批跳转链接
    var updateTime:Long = 0L,
    var createTime:Long = 0L,
    var extraFileList:String? = null // 审批附加文件列表

)