namespace java com.froad.thrift.service
include "BizMonitor.thrift"
include "Common.thrift"
include "VO.thrift"
include "Settlement.thrift"

/**
* 查询异常支付流水的条件实体
*/
struct BossQueryConditionVo{
	1:Common.PageVo pageVo;
	2:string clientId;
	3:string orderId;
	
	/**
	* 异常类型:1-支付异常;2-结算异常;3-退款异常;
	*/
	4:string exceptionType;
}

/**
* 分页查询结果
*/
struct BossPaymentPage{
	1:Common.PageVo pageVo;
	2:Common.ResultVo resultVo;
	3:list<BossPaymentVo> payments;
}

struct BossOrderVo {

	/**
	 * 订单号
	 */
	1: string orderId;
	
	/**
	 * 客户端ID
	 */
	2: string clientId;
	
	/**
	 * 创建时间
	 */
	3: i64 createTime;
	
	/**
	 * 会员ID
	 */
	4: i64 memberCode;
	
	/**
	 * 客户名称
	 */
	5: string memberName;
	
	/**
	 * 商户ID
	 */
	6: string merchantId;
	
	/**
	 * 商户名称
	 */
	7: string merchantName;
	
	/**
	 * 门店ID
	 */
	8: string outletId;
	
	/**
	 * 订单来源
	 */
	9: string createSource;
	
	/**
	 * 支付方式
	 */
	10: string paymentMethod;
	
	/**
	 * 订单状态
	 */
	11: string orderStatus;
	
	/**
	 * 支付时间
	 */
	12: i64 paymentTime;
	
	/**
	 * 总货币值
	 */
	13: double totalPrice;
	/**
	 * 方付通积分
	 */
	14: double fftPoints;
	/**
	 * 银行积分
	 */
	15: double bankPoints;

}

/**
* 支付对象
*/
struct BossPaymentVo{
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
	27:string isDisposeException; //异常支付流水是否处理   0 - 默认    1 - 已处理
	28:string refundPointsBillNo; //退积分的refundPointsNo
	29:string paymentReason;//0-结算 1-支付退款 2-用户支付 3-赠送积分 4-赠送积分退款(扣除已赠送的积分)
}

/**
* 退款对象
*/
struct BossRefundVo{
	/**
	* 退款id
	*/
	1:string refundId;
	
	/**
	* 退款状态
	*/
	2:string refundState;
	
	/**
	* 退款时间
	*/
	3:i64 createTime;
	
	/**
	 * 退还现金值
	 */
	4: double cash;
	
	/**
	 * 退还方付通积分
	 */
	5: double fftPoints;
	
	/**
	 * 退还银行积分
	 */
	6: double bankPoints;
	
	/**退款shopping信息*/
	7: BossRefundShoppingVo refundShoppingVo;
	
}

/**
*退款里面的shopping信息
*/
struct BossRefundShoppingVo{
	/**子订单ID*/
	1:string subOrderId;
	/**子订单类型*/
	2:string type;
	/**商户ID*/
	3:string merchantId;
	4:list<BossRefundProductVo> refundProductVo;
	5:string merchantName;
}

/**退款购物车商品信息*/
struct BossRefundProductVo{
	/**商品ID*/
	1:string productId;
	/**商品名*/
	2:string productName;
	3:i32 quantity;
	4:i32 price;
	5:i32 vipQuantity;
	6:i32 vipPrice;
}

/**
*结算表结构
*/
struct BossSettlementVo{
	/** 主键ID */
	1:string id,
		
	/** 结算ID */		
	2:string settlementId,	
	
	/** 创建时间 */
	3:i64 createTime,	
		
	/** 客户端ID */
	4:string clientId,	
		
	/** 订单ID */
	5:string orderId,	
		
	/** 子订单ID */
	6:string subOrderId,	
	
	/** 商户ID */
	7:string merchantId,	
	
	/** 门店ID */
	8:string outletId,		
	
	/** 结算类型(1-团购,2-名优特惠,3-面对面) */
	9:i32 type,		
			
	/** 支付ID */
	10:string paymentId,	
	
	/** 结算状态(0-未结算,1-结算中,2-结算成功,3-结算失败,4-无效结算记录) */
	11:string settleState,	
	
	/** 结算金额 */
	12:double money,	
		
	/** 备注 */
	13:string remark,		
	
	/** 商品名称 */
	14:string productName,	
	
	/** 商品ID */
	15:string productId,	
	
	/** 消费数量 */
	16:i32 productCount,	
	
	/** 结算卷ids */
	17:list<string> tickets,
    
    /** 商户名称 */
    18:string merchantName,	
	
	/** 门店名称 */
	19:string outletName,
	
	/** 结算账单号 */
	20:string billNo; 
}

struct BossSubOrder{
	1:string productId;//商品编号
	2:string productName;//商品名称
	3:i32 money;//商品单价
	4:i32 vipMoney;//商品VIP价格
	5:i32 quantity;//普通购买数量
	6:i32 vipQuantity;//VIP购买数量
}


/**
* 交易详情对象
*/
struct BossPaymentDetialVo{
	/** 订单信息 */
	1:BossOrderVo orderVo;
	
	
	/** 异常类型 */
	2:string exceptionType;
	/** 异常描述 */
	3:string exceptionDesc;
	/** 处理建议 */
	4:string proposal;
	
	/** 结算记录 */
	5:BossSettlementVo settlementVo;
	
	/** 支付记录 */
	6:BossPaymentVo payment;
	
	/** 退款记录 */
	7:BossRefundVo refund;
	
	8:list<BossSubOrder> subOrders;
}

service ExceptionOfPaymentService extends BizMonitor.BizMonitorService {


	/**
	* 异常订单列表(包含:支付异常,结算异常,退款异常 等查询)
	*/
	BossPaymentPage queryExceptionPaymentByPage(1:BossQueryConditionVo condition);
	
	/**
	* 查询结算异常
	*/
	//BossPaymentPage queryOfSettle(1:BossQueryConditionVo condition);

	/**
	* 查询结算详情
	*/
	BossPaymentDetialVo queryOfSettleDetial(1:string paymentId);

	/**
	* 查询支付详情
	*/
	BossPaymentDetialVo queryOfPaymentRefundPointDetial(1:string paymentId);

	BossPaymentDetialVo queryOfPaymentRefundCashDetial(1:string paymentId);

	/**
	* 查询退款详情
	*/
	BossPaymentDetialVo queryOfRefundDetial(1:string paymentId);
	
	/**
	* 重新发起结算
	*/
	Common.ResultVo retryOfSettle(1:string paymentId);
	
	/**
	* 重新发起退款
	*/
	Common.ResultVo retryOfReturn(1:string paymentId);
	
	/**
	* 退还现金
	*/
	Common.ResultVo returnCash(1:string paymentId);
	
	/**
	* 退还积分
	*/
	Common.ResultVo returnPoint(1:string paymentId);
	
	/**
	* 退还积分&现金
	*/
	Common.ResultVo returnPointAndCash(1:string paymentId);
}