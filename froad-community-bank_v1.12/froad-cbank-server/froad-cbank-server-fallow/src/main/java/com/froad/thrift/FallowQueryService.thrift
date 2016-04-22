/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入Common.thrift文件 */
include "FallowQueryServiceVo.thrift"


namespace java com.froad.thrift.service


/**
 * FallowQueryService
 */
service FallowQueryService extends BizMonitor.BizMonitorService {

	
	/**
	 * 查询审核任务信息  -- 审核任务单列表接口
     * @param req 请求参数
     */
	FallowQueryServiceVo.GetTaskResVo getTaskList (1:FallowQueryServiceVo.GetTaskReqVo req);
	
	
	/**
	 * 根据业务(商户、门店、商品)ID查询审核流水号
     * @param req 请求参数
     */
	FallowQueryServiceVo.GetInstanceIdByAttrResVo getInstanceIdByAttr (1:FallowQueryServiceVo.GetInstanceIdByAttrReqVo req);
	
	/**
	 * 当前审核概要信息
     * @param req 请求参数
     */
	FallowQueryServiceVo.GetTaskOverviewResVo getTaskOverview (1:FallowQueryServiceVo.GetTaskOverviewReqVo req);
	
	/**
	 * 待审核查询接口[待办箱]（getPendAuitList）
     * @param req 请求参数
     */
	FallowQueryServiceVo.GetPendAuitListPageVo getPendAuitList (1:FallowQueryServiceVo.GetPendAuitListReqVo req, 2:Common.PageVo pageVo);
	
	
	/**
	 * 待审核数量查询
     * @param req 请求参数
     */
	FallowQueryServiceVo.GetPendAuitCountResVo getPendAuitCount (1:FallowQueryServiceVo.GetPendAuitCountReqVo req);
	
	
	
	/**
	 * 申请审核任务查询接口[申请箱](getApplyAuitList)
     * @param req 请求参数
     */
	FallowQueryServiceVo.GetApplyAuitListPageVo getApplyAuitList (1:FallowQueryServiceVo.GetApplyAuitListReqVo req, 2:Common.PageVo pageVo);
	
	
	/**
	 * 已办理审核信息查询接口[已办箱](getAlreadyAuitList)
     * @param req 请求参数
     */
	FallowQueryServiceVo.GetAlreadyAuitListPageVo getAlreadyAuitList (1:FallowQueryServiceVo.GetAlreadyAuitListReqVo req, 2:Common.PageVo pageVo);
	
}
