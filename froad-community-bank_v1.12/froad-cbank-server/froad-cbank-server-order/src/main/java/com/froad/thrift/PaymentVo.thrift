/**
* 交易模块---支付VOthrift定义
*/
namespace java com.froad.thrift.vo.payment

include "BankCardVo.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"


/**
* model 定义 : 支付订单请求参数体
*/
struct DoPayOrdersVoReq{
	1:optional string clientId; //客户端Id
	2:optional string orderId; //聚合订单Id
	3:optional string pointOrgNo; //积分机构号
	4:optional string cashOrgNo; //现金机构号
	5:optional i32 payType; //支付类型
	6:optional i32 cashType; //现金支付类型
	7:optional double pointAmount; //积分值
	8:optional double cashAmount; //现金值
	9:optional string foilCardNum; //贴膜卡号码
}

/**
* model 定义 : 支付订单响应参数体
*/
struct DoPayOrdersVoRes{
	1:string resultCode; //接口结果码
	2:string resultDesc;  //结果码描述信息
	3:string resultForm  //HTML结构第三方支付表单
}

struct PaymentQueryPageVo{
	1:Common.PageVo pageVo;
	2:Common.ResultVo resultVo;
	3:list<PaymentQueryVo> paymentQueryVos;
}

struct PaymentQueryVo{
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
	27:PaymentType type; //0-结算 1-支付退款 2-用户支付 3-赠送积分 4-赠送积分退款(扣除已赠送的积分)
	28:string isDisposeException; //异常支付流水是否处理   0 - 默认    1 - 已处理
	29:string refundPointsBillNo; //退积分的refundPointsNo
	30:string paymentReason;
	31:Common.PageVo pageVo;
}

enum PaymentExceptionType{
	PAY_CASH_FAILD_AUTO_REFUND_POINT_FAILED = 10, //组合支付时：现金支付失败，自动退积分失败
	AUTO_PRESENT_FROAD_POINT = 11,//自动赠送积分失败
	REFUND_CASH_SUCCESS_REFUND_POINT_FAILED = 12,//组合退款：现金退款成功，积分退款失败
	REFUND_PRESENT_FROAD_POINT = 13,//自动赠送积分扣还失败
}

enum PaymentType{
	SETTLE_TO_MERCHANT = 0;
	MEMBER_PAYMENT_REFUND = 1;
	MEMBER_PAYMENT = 2;
	PRESENT_FROAD_POINT = 3;
	REFUND_PRESENT_FROAD_POINT = 4;

}

struct PaymentQueryExcetionVo{
	1:Common.PageVo pageVo;
	2:string clientId;
	3:PaymentExceptionType exceptionType;
	4:PaymentType type;
}