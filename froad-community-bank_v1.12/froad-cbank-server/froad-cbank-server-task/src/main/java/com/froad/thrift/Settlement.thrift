namespace java com.froad.thrift.service

include "Common.thrift"

/**
* 对外接口
*/
service SettlementService{
	
	/**
	 *  确认收货名优特惠结算接口
	 *  subOrderId:子订单ID
	 *  source:1-用户确认收货，2-系统确认收货
	 */
	Common.ResultVo specialSettle(1:string subOrderId,2:i32 source);
	
}

