namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "SettlementVo.thrift"


/**
* 对外接口
*/
service SettlementService extends BizMonitor.BizMonitorService {
	
	/**
	 * 分页查询
	 */
	SettlementVo.SettlementPage queryByPage(1:SettlementVo.SettlementPage page);
	/**
	 * 分页导出结算记录成Excel
	 */
	Common.ExportResultRes exportSettlements(1:SettlementVo.SettlementPage page);
	
	//SettlementVo.SettlementPage querySettlementFailedByPage(1:SettlementVo.SettlementPage page);

	/**
	 * 查询记录
	 */
	list<SettlementVo.SettlementVo> queryList(1:SettlementVo.SettlementVoReq req);
	
	/**
	 *  更新
	 */
	Common.ResultVo updateSettleState(1:string id,2:string type,3:string remark);
	/**
	 *  获取详情
	 */
	SettlementVo.SettlementVo queryById(1:string id);
	/**
	 *  确认收货名优特惠结算接口
	 *  subOrderId:子订单ID
	 *  source:1-用户确认收货，2-系统确认收货
	 */
	Common.ResultVo specialSettle(1:string subOrderId,2:i32 source);
	
	
	/**
	 * 根据券id集合查询券的结算状态
	 */
	list<SettlementVo.SettlementVo> getTicketIdSettlementList(1:list<string> ticketIdList);
	
	
}

