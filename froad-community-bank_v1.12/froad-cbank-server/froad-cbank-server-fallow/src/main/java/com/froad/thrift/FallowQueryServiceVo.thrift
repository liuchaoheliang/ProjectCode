/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo


/**
 * TaskListResVo对象
 */
struct TaskListResVo {
	/** 任务流水号*/
	1: optional string taskId,
	/** 创建时间*/
	2: optional i64 createTime,
	/** 审核时间*/
	3: optional i64 auditTime,
	/** 审核状态*/
	4: optional string auditState,
	/**操作人所属组织(说明:如果是个人操作则传空;如果是银行操作则传银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id;)*/
	5: optional string orgId,
	/** 审核人*/
	6: optional string auditor,
	/** 备注*/
	7: optional string remark
}


/**
 * getTask接口请求对象
 */
struct GetTaskReqVo {
	/** 源对象*/
	1: Common.OriginVo origin, 
	/** 审核流水号*/
	2: string instanceId
}


/**
 * getTask接口返回对象
 */
struct GetTaskResVo {
	1: optional Common.ResultVo result,
	2: list<TaskListResVo> taskListRes
}


/**
 * getInstanceIdByAttr  根据业务(商户、门店、商品)ID查询审核流水号  接口请求对象
 */
struct GetInstanceIdByAttrReqVo {
	/**源对象*/
	1: Common.OriginVo origin, 
	/** 业务数据ID根据类型传对应的(商户、门店、商品)Id */
	2: string bessId
}

/**
 * getInstanceIdByAttr 根据业务(商户、门店、商品)ID查询审核流水号 接口返回对象
 */
struct GetInstanceIdByAttrResVo {
	1: optional Common.ResultVo result,
	/** 审核流水号*/
	2: string instanceId,
	/** 审核类型*/
	3: string processTypeDetail
}


/**
 * getTaskOverview  当前审核概要信息  接口请求对象
 */
struct GetTaskOverviewReqVo {
	/** 源信息 */
	1: Common.OriginVo origin, 
	/** 审核流水号*/
	2: string instanceId
}

/**
 * getTaskOverview 当前审核概要信息 接口返回对象
 */
struct GetTaskOverviewResVo {
	1: optional Common.ResultVo result,
	/** 审核流水号*/
	2: optional string instanceId,
	/**审核状态:0:待审核； 1审核通过;2审核不通过*/
	3: optional string auditState,
	/** 创建人*/
	4: optional string creator,
	/** 创建时间*/
	5: optional i64 createTime,
	/**操作人所属组织(说明:如果是个人操作则传空;如果是银行操作则传银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id;)*/
	6: optional string orgId,
	/**业务Id */
	7: optional string bessId
}


/**
 *待审核查询接口[待办箱]（getPendAuitList）接口请求对象
 */
struct GetPendAuitListReqVo {
	/** 源信息 */
	1: Common.OriginVo origin, 
	/**开始时间*/
	2: optional i64 starTime,
	/**结束时间*/
	3: optional i64 endTime,
	/**业务json对象*/
	// 4: optional string bessData,
	/** 并且关系:key为限制条件 value为业务json对象 */
	4:optional map<Common.Restrictions, string> andBessData,
	
	/** 或者关系:key为限制条件 value为业务json对象 */
	5:optional map<Common.Restrictions, string> orBessData,
	/**流程类型:1-商户,2-门店,3-团购商品,4-预售商品*/
	6: string processType,
	/**类型详情:0-新增审核,1-更新审核*/
	7: optional string processTypeDetail,
	/**创建机构*/
	8: optional string userOrgCode
}

/**
 * 待审核查询接口[待办箱]（getPendAuitList） 接口返回对象
 */
struct GetPendAuitListPageVo {
	1: Common.ResultVo result,
	2: Common.PageVo page,
	3: optional list<AuditInstanceDetailVo> auditDetailList
}


/**
 *待审核数量查询接口[待办箱]（getPendAuitCount）接口请求对象
 */
struct GetPendAuitCountReqVo {
	/** 源信息 */
	1: Common.OriginVo origin, 
	/**开始时间*/
	2: optional i64 starTime,
	/**结束时间*/
	3: optional i64 endTime,
	///**业务json对象*/
	//4: optional string bessData,
	/** 并且关系:key为限制条件 value为业务json对象 */
	4:optional map<Common.Restrictions, string> andBessData,
	
	/** 或者关系:key为限制条件 value为业务json对象 */
	5:optional map<Common.Restrictions, string> orBessData,
	/**流程类型:1-商户,2-门店,3-团购商品,4-预售商品*/
	6: string processType,
	/**类型详情:0-新增审核,1-更新审核*/
	7: optional string processTypeDetail
}

/**
 * 待审核查询接口[待办箱]（getPendAuitList） 接口返回对象
 */
struct GetPendAuitCountResVo {
	1: Common.ResultVo result,
	/** 待审核数量 */
	2: optional i32 count
}


/**
 *申请审核任务查询接口[申请箱](getApplyAuitList)
 */
struct GetApplyAuitListReqVo {
	/** 源信息 */
	1: Common.OriginVo origin,
	/** 审核流水号 */
	2: optional string instanceId, 
	/**开始时间*/
	3: optional i64 starTime,
	/**结束时间*/
	4: optional i64 endTime,
	/**流程类型:1-商户,2-门店,3-团购商品,4-预售商品*/
	5: string processType,
	///**业务json对象*/
	//6: optional string bessData,
	/** 并且关系:key为限制条件 value为业务json对象 */
	6:optional map<Common.Restrictions, string> andBessData,
	
	/** 或者关系:key为限制条件 value为业务json对象 */
	7:optional map<Common.Restrictions, string> orBessData,
	/**类型详情:0-新增审核,1-更新审核*/
	8: optional string processTypeDetail,
	/**审核机构Code*/
	9: optional string auditOrgCode
}

/**
 * 申请审核任务查询接口[申请箱](getApplyAuitList)
 */
struct GetApplyAuitListPageVo {
	1: Common.ResultVo result,
	2: Common.PageVo page,
	3: list<AuditInstanceDetailVo> auditDetailList
}

/**
 * （AuditInstanceDetailVo） 
 */
struct AuditInstanceDetailVo {
	/**创建时间*/
	1: optional i64 createTime,
	/**审核流水号*/
	2: optional string instanceId,
	/**审核创建人信息*/
	3: optional ActorVo createActor,
	/**流程类型:1-商户,2-门店,3-团购商品,4-预售商品*/
	4: optional string processType,
	/**0-新增；1-更新*/
	5: optional string processTypeDetail,
	/**业务数据ID根据类型传对应的(商户、门店、商品)Id*/
	6: optional string bessId,
	/**业务对象 */
	7: optional string bessData,
	/**审核流水状态*/
	8: optional string instanceState,
	/**归档时间*/
	9: optional i64 finishTime,
	/**已执行过的审核人信息*/
	10: optional list<ActorVo> auditActor,
	/**下一审核人信息*/
	11: optional ActorVo nextActor,
	/** 所属商户的发展机构*/
	12: optional string orgCode,
	/**审核状态(0-待审核,1审核通过,2审核未通过)*/
	13: optional string auditState
}


struct ActorVo {
	/**操作人id*/
	1: optional string actorId,
	/**操作人用户名*/
	2: optional string actorUserName,
	/**操作人所属组织(说明:如果是个人操作则传空;如果是银行操作则传银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id;)*/
	3: optional string orgId,
	/**任务执行Id*/
	4: optional string taskId,
	/**审核状态(0-待审核,1审核通过,2审核未通过)*/
	5: optional string auditState
	
}



/**
 *已办理审核信息查询接口[已办箱](getAlreadyAuitList) 请求参数
 */
struct GetAlreadyAuitListReqVo {
	/** 源信息 */
	1: Common.OriginVo origin, 
	/**开始时间*/
	2: optional i64 starTime,
	/**结束时间*/
	3: optional i64 endTime,
	/**查询类型：1:查在途 2:查归档	*/
	4: optional string type,
	/**归档时间-开始时间	*/
	5: optional i64 finishStarTime,
	/**归档时间-结束时间	*/
	6: optional i64 finishEndTime,
	/**审核流水号*/
	7: optional string instanceId,
	/**审核状态*/
	8: optional string auditState,
	/**流程类型:1-商户,2-门店,3-团购商品,4-预售商品*/
	9: string processType,
	///**业务对象信息*/
	//10: optional string bessData,
	/** 并且关系:key为限制条件 value为业务json对象 */
	10:optional map<Common.Restrictions, string> andBessData,
	
	/** 或者关系:key为限制条件 value为业务json对象 */
	11:optional map<Common.Restrictions, string> orBessData,
	/**审核机构Code*/
	12: optional string auditOrgCode
}

/**
 * 已办理审核信息查询接口[已办箱](getAlreadyAuitList)  返回参数
 */
struct GetAlreadyAuitListPageVo {
	1: Common.ResultVo result,
	2: Common.PageVo page,
	3: list<AuditInstanceDetailVo> auditDetailList
}


