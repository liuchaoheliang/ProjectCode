namespace java com.froad.thrift.vo.refund
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"


/**
 * 退款商品
 */
struct RefundProductVo{
	/** 商品ID */
	1:string productId,
	/** 商品名称  */
	2:string productName,
	/** 商品图片  */
	3:string image,
	/** 数量  */
	4:i32 quantity,
	/** 单价  */
	5:double price,
	/** 数量  */
	6:i32 vipQuantity,
	/** 单价  */
	7:double vipPrice,
	/** 小计  */
	8:double priceSum,
	/** 商户ID  */
	9:string merchantId,
	/** 商户名  */
	10:string merchantName,		
}

/**
 * 退款支付流水
 */
struct RefundPaymentVo{
	/** 支付流水 */
	1:string paymentId,
	/** 支付类型  */
	2:string paymentType,
	/** 退还金额或积分  */
	3:string refundValue,
	/** 退款支付时间  */
	4:string paymentTime,
}

/**
 * 退款请求VO
 */
struct RefundRequestVo{
	/** 客户端ID  */
	1:string clientId,
	/** 原订单号  */
	2:string orderId,
	/** 原子订单号  */
	3:string subOrderId,
	/** 退款原因  */
	4:string reason,
	/** 退款商品列表  */
	5:list<RefundProductVo> productList,
	/** 退款选项，1-查询， 2-退款申请  */
	6:string option,	
}

/**
 * 退款详情查找请求VO
 */
struct RefundDetailRequestVo{
	/** 客户端ID  */
	1:string clientId,
	/** 退款号  */
	2:string refundId,
}

/**
 * 退款响应VO
 */
struct RefundResponseVo{
	/** 返回结果  */
	1:Common.ResultVo resultVo,
	/** 退款ID  */
	2:RefundInfoVo refundInfo,
}

/**
 * 查询退款列表请求VO
 *
 * source: 
 *  1 – 根据会员编号(memberCode)查找其所有退款记录，可分页查找。如不通过此条件查找，传null
 *  2 – 根据开始结束时间(startDate, endDate)查找退款记录，分页查找，主要是boss查找。如不通过此条件查找，均传null
 *  3 – 如不需分页查找pageNumber，pageSize均传null
 *
 */
struct RefundListRequestVo{
	/** 客户端ID  */
	1:string clientId,
	/** 查找来源  */
	2:string source,
	/** 会员编号  */
	3:string memberCode,
	/** 退款状态  */
	4:string status,	
	/** 开始时间  */
	5:string startDate,
	/** 结束时间  */
	6:string endDate,
	/** 订单号  */
	7:string orderId,
	/** 子订单号  */
	8:string subOrderId,
	/** 退款ID  */
	9:string refundId,	
	/** 分页信息  */
	10:Common.PageVo pageVo,	
}

/**
 * 退款信息
 */
struct RefundInfoVo{
	/** 退款ID  */
	1:string refundId,
	/** 原订单号  */
	2:string orderId,
	/** 退款申请时间  */
	3:string requestTime,
	/** 退款状态  */
	4:string refundStatus,
	/** 退款金额  */
	5:double refundAmount,
	/** 退还积分  */
	6:double refundPoints,
	/** 退款原因  */
	7:string reason,
	/** 原子订单号  */
	8:string subOrderId,	
	/** 商户ID  */
	9:string merchantId,	
	/** 商户名  */
	10:string merchantName,
	/** 商户名  */
	11:string refundTime,		
	/** 退款商品列表  */
	12:list<RefundProductVo> productList,
	/** 退款商品列表  */
	13:list<RefundPaymentVo> payList,	
	/** 银行端ID  */
	14:string clientId,	
	/**是否是VIP资格的退款*/
	15:string isVipRefund,
}

/**
 * 查询退款列表响应VO
 */
struct RefundListResponseVo{
	/** 返回结果  */
	1:Common.ResultVo resultVo,
	/** 退款信息列表  */
	2:list<RefundInfoVo> refundInfoList,
	/** 分页信息  */
	3:Common.PageVo pageVo,
}

/**
 * 更新退款状态请求VO
 */
struct RefundStateRequestVo{
	/** 退款ID  */
	1:string refundId,
	/** 退款状态  */
	2:string refundState,
}

/**
 * 更新退款状态响应VO
 */
struct RefundStateResponseVo{
	/** 返回结果  */
	1:Common.ResultVo resultVo,
}

/**
 * 券过期自动退款requestVo
 */
struct RefundTicketsRequestVo{
	/** 过期券  */
	1:list<RefundTicketVo> ticketList,
}

/**
 * 退款券信息
 */
struct RefundTicketVo{
	/** 子订单ID  */
	1:string suborderId,
	/** 券ID  */
	2:string ticketId,
	/** 商品ID  */
	3:string productId,
	/** 商品数量  */
	4:i32 quantity,	
	/** 备注  */
	5:string remark,		
	/** 银行编号  */
	6:string clientId,
}

/**
 * 券过期自动退款responseVo
 */
struct RefundTicketsResponseVo{
	/** 返回结果  */
	1:Common.ResultVo resultVo,
	/** 处理成功的过期券  */
	2:list<RefundTicketVo> successList,
	/** 处理失败的过期券  */
	3:list<RefundTicketVo> failedList,		
}
