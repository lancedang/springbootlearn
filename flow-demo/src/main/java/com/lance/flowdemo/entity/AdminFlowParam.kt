package com.lance.flowdemo.entity

import io.swagger.annotations.ApiModelProperty

data class FlowItemQuery (
    var userId: Long? = 0,
    var initiator: Long? = 0,
    var status: Int? = -1,
    var page: Int = 1,
    var size: Int = 10,
    var offset: Int = 0,
    var tenantId:Long=0L
)

data class FlowTemplateQuery (
    var flowName: String? = null,
    var page:Int = 1,
    var size:Int = 10,
    var offset:Int = 0,
    var tenantId:Long=0L
)

data class FlowApprove (
    @ApiModelProperty("机构id")
    var tenantId:Long = 0L,

    @ApiModelProperty("审批流id")
    var caseId:Long = 0L,

    @ApiModelProperty("审批结果")
    var result:Boolean = false,

    @ApiModelProperty("审批意见或拒绝原因")
    var comment:String = "",

    @ApiModelProperty("审批人 , 前端不需要设置")
    var approver:Long = 0L

    //@ApiModelProperty("审批附加文件列表")
    //var extraFileList:List<FileDO> = listOf<FileDO>()
)

data class FlowQuery (
    var tenantId:Long=0L,
    @ApiModelProperty("发起人")
    var initiator: Long? = 0,

    @ApiModelProperty("状态：0 待审批,-1：已审批")
    var status:Int = 0,

    @ApiModelProperty("复核事件")
    var flowKey: String? = null,

    @ApiModelProperty("复核事件名称")
    var name :String ?=null,

    @ApiModelProperty("创建时间-起")
    var fromTime:Long = 0L,

    @ApiModelProperty("创建时间-止")
    var toTime:Long = 0L,
    var page:Int = 1,
    var size:Int = 10,
    var offset:Int = 0
)

class CreateFlowParam {
    var tenantId=0L
    /** 必填*/
    var flowKey = ""
    /** 必填*/
    var bizNo = ""
    /** 发起人，选填。如果不传，则取模板中的默认发起人，
     * 如果没有配置默认发起人，则必传*/
    var initiator = 0L
    /***/
    var title = ""//模板标题
    var kvTable = mutableMapOf<String, String>()
    var kvComTable = mutableMapOf<String, String>()
    var iterableTable = mutableMapOf<String, List<Map<String, Any>>>()

    /** 如果配置了url参数，则必传*/
    var urlParam = mutableMapOf<String, String>()
    var hideColumns:List<String> = listOf() //需要隐藏的表单数据

    /**
     * 添加普通表单参数
     */
    fun putVariable(key: String, value: String) {
        this.kvTable[key] = value
    }

    /**
     * 添加对比表单参数
     */
    fun putVariable(key: String, oldV: String, newV: String) {
        this.kvComTable[key] = "$oldV|$newV"
    }

    /**
     * 添加循环表单参数
     */
    fun putIterable(key: String, value: List<Map<String, String>>) {
        this.iterableTable[key] = value
    }
}