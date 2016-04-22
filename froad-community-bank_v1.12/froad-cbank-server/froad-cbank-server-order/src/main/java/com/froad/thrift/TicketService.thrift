namespace java com.froad.thrift.service


include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "TicketVo.thrift"

/**
* 券模块对外接口
*/
service TicketService extends BizMonitor.BizMonitorService{
	/**
	* 验证券接口
	*/
	TicketVo.TicketVerifyResponseVo verifyTickets(1:TicketVo.TicketVerifyRequestVo requestVo);
	
	/**
	* 验证券接口(相同商品合并成一个)
	*/
	TicketVo.TicketVerifyOfMergerResponseVo verifyGroupTicketsOfMerger(1:TicketVo.TicketVerifyOfMergerRequestVo requestVo);
	
	/**
	* 查找券详细信息
	*/
	TicketVo.TicketDetailResponseVo getTicketDetails(1:TicketVo.TicketDetailRequestVo requestVo);	
	
	/**
	* 查找个人版券详细信息
	*/
	TicketVo.TicketProductResponseVo getTicketProductDetails(1:TicketVo.TicketDetailRequestVo requestVo);		
	
	/**
	* 延长券有效期
	*  endDate时间格式 - 类似System.currentTimeMillis()
	*/
	Common.ResultVo extendEndDate(1:string ticketId, 2:i64 endDate);
	
	/**
	* 延长指定商品id的券有效期
	*  endDate时间格式 - 类似System.currentTimeMillis()
	*/
	Common.ResultVo extendEndDateByProductId(1:string productId, 2:i64 endDate);
	
	/**
	* 获取券列表
	*/
	TicketVo.TicketListResponseVo getTicketList(1:TicketVo.TicketListRequestVo ticketListRequestVo);
	
	/**
	 * 导出券列表
	 */
	Common.ExportResultRes exportTickets(1:TicketVo.TicketListRequestVo ticketListRequestVo);
	
	/**
	* 获取商户验码记录
	*/
	TicketVo.VerifyTicketListByPageResponseVo getVerifyTicketListByPage(1:TicketVo.TicketListRequestVo ticketListRequestVo);
	
	/**
	 * 获取券列表(相同商品合并成一个)
	 */
	TicketVo.TicketListResponseVo getTicketListOfMerger(1:TicketVo.TicketListRequestVo ticketListRequestVo);
	
	/**
	* 1) resource = 3，根据券ID获取该会员所有有效券列表
	* 2) resource = 2，根据券ID获取该会员下所有有效团购券
	* 3) resource = 1，根据券ID获取券该会员下所有有效预售券
	*/
	TicketVo.TicketListResponseVo getRelatedTickets(1:TicketVo.TicketRelatedRequestVo requestVo);	
	
	/**
	 * 查询券信息
	 */
	list<TicketVo.TicketDetailVo> getTicketDetail(1: TicketVo.TicketDetailVo ticketDetailVo);
}