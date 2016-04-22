namespace java com.froad.thrift.service

include "BizMonitor.thrift"
include "Common.thrift"

/**
 * Boss支付查询ResVo
 */
struct BossPaymentQueryPageVo{
	1:Common.PageVo pageVo;
	2:Common.ResultVo resultVo;
	3:list<BossPaymentQueryVo> paymentQueryVos;
}

struct BossPaymentQueryVo{
	1:i64 id; //主键id
	2:i64 createTime; //创建时间
	3:string paymentId; //
	4:string clientId; //客户端编号
	5:string orderId; //关联订单
	6:string billNo;  //bill支付Num 或者积分支付的payPointNo
	7:i32 paymentType; //支付类型  1-方付通积分 2-现金 3-银行积分
	8:double paymentValue; //支付额
	9:i32 paymentTypeDetails;//支付详细   --> 同cashType枚举  0 代表积分支付
	10:i32 step; //当前步骤  1-等待支付  2-开始支付中 3-支付请求发送完毕 4-明确支付结果
	11:bool isEnable; //是否有效
	12:string paymentStatus; //支付状态 1、等待支付 2、支付请求发送成功 3、支付请求发送异常  4、支付成功 5、支付失败 
	13:string paymentOrgNo; //支付机构号
	14:string fromAccountName; 
	15:string fromAccountNo;
	16:string toAccountName;
	17:string toAccountNo;
	18:string fromPhone;
	19:string toPhone;
	20:string fromUserName;
	21:string toUserName;
	22:string resultCode;
	23:string resultDesc;
	24:string remark;
	25:string autoRefund; //0 未发生自动退款 1 自动退款失败 2 自动退款成功
	26:i32 pointRate; //积分比例
	27:BossPaymentType type; //0-结算 1-支付退款 2-用户支付 3-赠送积分 4-赠送积分退款(扣除已赠送的积分)
	28:string isDisposeException; //异常支付流水是否处理   0 - 默认    1 - 已处理
	29:string refundPointsBillNo; //退积分的refundPointsNo
	30:string paymentReason;
	31:Common.PageVo pageVo;
}

enum BossPaymentExceptionType{
	PAY_CASH_FAILD_AUTO_REFUND_POINT_FAILED = 10, //组合支付时：现金支付失败，自动退积分失败
	AUTO_PRESENT_FROAD_POINT = 11,//自动赠送积分失败
	REFUND_CASH_SUCCESS_REFUND_POINT_FAILED = 12,//组合退款：现金退款成功，积分退款失败
	REFUND_PRESENT_FROAD_POINT = 13,//自动赠送积分扣还失败
}

enum BossPaymentType{
	SETTLE_TO_MERCHANT = 0;
	MEMBER_PAYMENT_REFUND = 1;
	MEMBER_PAYMENT = 2;
	PRESENT_FROAD_POINT = 3;
	REFUND_PRESENT_FROAD_POINT = 4;

}

struct BossPaymentQueryExceptionVo{
	1:Common.PageVo pageVo;
	2:string clientId;
	3:BossPaymentExceptionType exceptionType;
	4:BossPaymentType type;
	5:i64 createTimeStart;
	6:i64 createTimeEnd;
}



/**
*支付查询res
*/

struct BossQueryPaymentListRes{
	1:Common.PageVo pageVo;
	2:Common.ResultVo resultVo;
	3:list<BossQueryPaymentVo> paymentQueryVos;
}

/**
*支付查询res-vo
*/
struct BossQueryPaymentVo{
	1:i64 id; //主键id
	2:i64 createTime; //创建时间
	3:string paymentId; //
	4:string clientId; //客户端编号
	5:string orderId; //关联订单
	6:string billNo;  //bill支付Num 或者积分支付的payPointNo
	7:i32 paymentType; //支付类型  1-方付通积分 2-现金 3-银行积分
	8:double paymentValue; //支付额
	9:i32 paymentTypeDetails;//支付详细   --> 同cashType枚举  0 代表积分支付
	10:i32 step; //当前步骤  1-等待支付  2-开始支付中 3-支付请求发送完毕 4-明确支付结果
	11:bool isEnable; //是否有效
	12:string paymentStatus; //支付状态 1、等待支付 2、支付请求发送成功 3、支付请求发送异常  4、支付成功 5、支付失败 
	13:string paymentOrgNo; //支付机构号
	14:string fromAccountName; 
	15:string fromAccountNo;
	16:string toAccountName;
	17:string toAccountNo;
	18:string fromPhone;
	19:string toPhone;
	20:string fromUserName;
	21:string toUserName;
	22:string resultCode;
	23:string resultDesc;
	24:string remark;
	25:string autoRefund; //0 未发生自动退款 1 自动退款失败 2 自动退款成功
	26:i32 pointRate; //积分比例
	27:BossPaymentType type; //0-结算 1-支付退款 2-用户支付 3-赠送积分 4-赠送积分退款(扣除已赠送的积分)
	28:string isDisposeException; //异常支付流水是否处理   0 - 默认    1 - 已处理
	29:string refundPointsBillNo; //退积分的refundPointsNo
	30:string paymentReason;
	31:Common.PageVo pageVo;
}

/**
 * service定义 : 支付模块service
 */
service BossQueryPaymentService extends BizMonitor.BizMonitorService {
	
	/**
	 * 异常支付查询接口
	 */
	BossPaymentQueryPageVo queryPaymentOfException(1:BossPaymentQueryExceptionVo queryVo);
	
	/**
	*支付查询接口
	*/
	BossQueryPaymentListRes getPaymentList(1:BossQueryPaymentVo req);
	/**
	*支付查询报表导出接口
	*/
	Common.ExportResultRes exportPaymentList(1:BossQueryPaymentVo req);
	
}