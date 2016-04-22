namespace java com.froad.thrift.vo.order

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/**
 * 团购交易查询req
 */
struct GroupOrderListReq {
	/**订单编号*/
	1:string orderId;
	
	/**手机号*/
	2:string phone;
	
	/**券号状态:0:待发送;1:已发送;2:已消费;3:已过期;4:已退款;5:退款中;6:退款失败;7:已过期退款;8:未发码退款;9:已过期退款中*/
	3:string ticketStatus;
	
	/**结算状态0:未结算;1:结算中;2:结算成功;3:结算失败;4:无效结算记录*/
	4:string settlementStatus;
	
	/**商品名称*/
	5:string productName;
	
	/**用户名称:登录账号*/
	6:string memberName;
	
	/**开始时间*/
	7:i64 startTime;
	
	/**结束时间*/
    8:i64 endTime;
    
    /**分页*/
    9:Common.PageVo pageVo;
}


/**
 * 团购交易查询res
 */
struct GroupOrderListRes {
	/**订单数据*/
	1:list<GroupOrderVo> orders;
	/**查询结果*/
	2:Common.ResultVo resultVo;	
	/**分页数据*/
	3:Common.PageVo pageVo;
}

/**
 * 团购交易查询res-list-vo
 */
struct GroupOrderVo {
	/**订单号*/
	1:string orderId;
	
	/**券号*/
	2:string ticketId;
	
	/**购买(支付)时间*/
	3:i64 paymentTime;

	/**门店名称*/
	4:string outletName;

	/**商品名称*/
	5:string productName;

	/**用户名称*/
	6:string memberName;

	/**券号状态*/
	7:string ticketStatus;
}


/**
 * 预售交易查询req
 */
struct PresellOrderListReq {
	/**订单编号*/
	1:string orderId;
	
	/**手机号*/
	2:string phone;
	
	/**券号状态:0:待发送;1:已发送;2:已消费;3:已过期;4:已退款;5:退款中;6:退款失败;7:已过期退款;8:未发码退款;9:已过期退款中*/
	3:string ticketStatus;
	
	/**结算状态0:未结算;1:结算中;2:结算成功;3:结算失败;4:无效结算记录*/
	4:string settlementStatus;
	
	/**商品名称*/
	5:string productName;
	
	/**用户名:提货人姓名*/
	6:string memberName;
	
	/**开始时间*/
	7:i64 startTime;
	
	/**结束时间*/
    8:i64 endTime;
    
    /**分页*/
    9:Common.PageVo pageVo;
}


/**
*预售交易查询res
*/
struct PresellOrderListRes {
	/**订单数据*/
	1:list<PresellOrderVo> orders;
	/**查询结果*/
	2:Common.ResultVo resultVo;
	/**分页数据*/
	3:Common.PageVo pageVo;
}

/**
*预售交易查询res-list-vo
*/
struct PresellOrderVo {
	/**订单号*/
	1:string orderId;
	
	/**券号*/
	2:string ticketId;
	
	/**购买(支付)时间*/
	3:i64 paymentTime;
	
	/**提货方式:0:送货上门;1:网点自提;2:配送或自提*/
	4:string deliveryType

	/**提货网点名称*/
	5:string outletName;
	
	/**商品名称*/
	6:string productName;

	/**用户名称*/
	7:string memberName;

	/**提货开始时间*/
	8:i64 deliveryStartTime;
	
	/**提货结束时间*/
	9:i64 deliveryEndTime;
	
	/**收货人*/
	10:string consigneeName;
	
	/**收货人电话*/
	11:string consigneePhone;
	
	/**收获地址*/
	12:string consigeeAddress;
	
	/**券号状态*/
	13:string ticketStatus;
	
}


/**
 * Boss订单（交易）查询
 */
service OrderQueryForBossService extends BizMonitor.BizMonitorService {

	/**
	 * 团购交易查询
	 */
	GroupOrderListRes queryGroupOrderList(1:GroupOrderListReq req);
	
	/**
	 * 预售交易查询
	 */
	PresellOrderListRes queryPresellOrderList(1:PresellOrderListReq req);
	
	
}






