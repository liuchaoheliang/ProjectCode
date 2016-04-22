/**
* 交易模块---支付对外接口thrift定义
*/
namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "PaymentVo.thrift"


/**
* service定义 : 支付模块service
*/
service PaymentService extends BizMonitor.BizMonitorService {

	/**
	*	订单支付接口
	*/
	PaymentVo.DoPayOrdersVoRes doPayOrders(1:PaymentVo.DoPayOrdersVoReq doPayOrdersVoReq);
	
	/**
	* 通知支付/结算结果接口
	*/
	void noticePaymentResult(1:string xmlString);
	
	/**
	* 通知退款结果接口
	*/
	void noticeRefundResult(1:string xmlString);
		
	/**
	* 主动确认支付结果(包含支付、退款、结算)定时任务接口
	*/
	Common.ResultVo verifyPaymentResultForTask(1:string paymentId);
	
	
	PaymentVo.PaymentQueryPageVo queryPaymentForBoss(1:PaymentVo.PaymentQueryVo queryVo);
	
	
	PaymentVo.PaymentQueryPageVo queryPaymentForBossOfException(1:PaymentVo.PaymentQueryExcetionVo queryVo);
	
	Common.ResultVo verifyFoilCardNum(1:string mobileNum,2:string clientId);
}