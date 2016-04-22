namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "QueryOrderForBossVo.thrift"

/**
 * Boss订单（交易）查询
 */
service OrderQueryForBossService extends BizMonitor.BizMonitorService {

	/**
	 * 团购交易查询
	 */
	QueryOrderForBossVo.GroupOrderListRes queryGroupOrderList(1:QueryOrderForBossVo.GroupOrderListReq req);
	
	/**
	 * 预售交易查询
	 */
	QueryOrderForBossVo.PresellOrderListRes queryPresellOrderList(1:QueryOrderForBossVo.PresellOrderListReq req);
	
	
}






