include "Common.thrift"

/**
* 交易模块---支付对外接口thrift定义
*/
namespace java com.froad.thrift.service


/**
* service定义 : 支付模块service
*/
service PaymentService{
		
	/**
	* 主动确认支付结果(包含支付、退款、结算)定时任务接口
	*/
	Common.ResultVo verifyPaymentResultForTask(1:string paymentId)
}