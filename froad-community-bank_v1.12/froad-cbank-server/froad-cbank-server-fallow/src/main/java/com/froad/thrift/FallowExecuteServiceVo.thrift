/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo

/**
 * createInstance接口请求对象
 */
struct CreateInstanceReqVo {
	1: Common.OriginVo origin, 
	/**业务数据json对象*/
	2: string bessData,
	/**业务Id（门店、商品、商户）的Id*/
	3: string bessId
	/** 所属商户的发展机构 */
	4: string orgCode;
	/** 流程类型:1-商户,2-门店,3-团购商品,4-预售商品,5-名优特惠,6-在线积分兑换,7-网点礼品 */
	5: string processType;
	/** 类型详情:0-新增,1-更新 */
	6: string processTypeDetail;
}

/**
 * createInstance接口返回对象
 */
struct CreateInstanceResVo {
	1: optional Common.ResultVo result,
	/**流程ID*/
	2: optional string instanceId
}

/**
 * executeTask接口请求对象
 */
struct ExecuteTaskReqVo {
	1: Common.OriginVo origin,
	/** 审核实例id */
	2: string instanceId,	
	/** 审核任务id */
	3: string taskId,	
	/**业务Id（门店、商品、商户）的Id*/
	4: string bessId;
	/**审核状态:1审核通过;2审核不通过*/
	5: string auditState;
	/**审核备注*/
	6: optional string remark,
	/** 所属商户的发展机构 */
	7: string orgCode;
}

/**
 * executeTask接口返回对象
 */
struct ExecuteTaskResVo {
	1: optional Common.ResultVo result;
	3: optional string taskId,
}

