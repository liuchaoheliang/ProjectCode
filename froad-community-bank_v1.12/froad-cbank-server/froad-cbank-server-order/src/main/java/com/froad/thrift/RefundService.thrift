namespace java com.froad.thrift.service


include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "RefundVo.thrift"


/**
* 退款对外接口
*/
service RefundService extends BizMonitor.BizMonitorService {
	/**
	* 订单退款接口
	*/
	RefundVo.RefundResponseVo doOrderRefund(1:RefundVo.RefundRequestVo refundRequestVo);
	
	/**
	* 查找退款详情接口
	*/
	RefundVo.RefundResponseVo getRefundDetail(1:RefundVo.RefundDetailRequestVo requestVo);
	
	/**
	* 获取退款记录列表
	*/
	RefundVo.RefundListResponseVo getRefundList(1:RefundVo.RefundListRequestVo refundListRequestVo);

	/**
	* 更新退款状态
	*/
	RefundVo.RefundStateResponseVo updateRefundState(1:RefundVo.RefundStateRequestVo refundStateRequestVo);
	
	/**
	* 券过期自动退款接口
	*/
	RefundVo.RefundTicketsResponseVo refundTickets(1:RefundVo.RefundTicketsRequestVo requestVo);
	
	/**
	* vip退款
	* option -- 1 校验
	* option -- 2 退款
	*/	
	RefundVo.RefundResponseVo doOrderRefundOfVIP(1:string orderId,2:string clientId,3:i64 memberCode,4:string option);
	
	/**
	* 用于boss管理平台（运维）发起退款
	* 该退款实际只能退款精品商城类型订单，!!!!!!<代替用户发起退款，调用用户退款接口----确认这样实现>!!!!!
	*/
	RefundVo.RefundResponseVo doRefundOfBoutiqueBoss(1:string subOrderId,2:string refundReason,3:string productId,4:i32 quantity,5:string clientId);
}