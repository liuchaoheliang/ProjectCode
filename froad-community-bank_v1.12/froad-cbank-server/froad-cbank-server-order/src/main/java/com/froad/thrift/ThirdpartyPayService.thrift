namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "ThirdpartyPayVo.thrift"


service ThirdpartyPayService extends BizMonitor.BizMonitorService {
	
	Common.ResultVo cashCombineSettle(1:ThirdpartyPayVo.CombineSettleReq req);
	
	Common.ResultVo retryRefund(1:string refundId);
	
	Common.ResultVo refundPayCash(1:string paymentId);
	
	Common.ResultVo refundPayPoint(1:string paymentId);
	/**
	* 赠送积分外部接口， 
	* 0000 赠送成功  9999赠送失败  3015赠送请求异常
	*/
	ThirdpartyPayVo.ResultVo pointPresent(1:string clientId,2:string loginID,3:double value);
}
