namespace java com.froad.thrift.service

include "BizMonitor.thrift"
include "Common.thrift"

/**
 * 查询退款列表请求req
 * source: 
 *  1 – 根据会员编号(memberCode)查找其所有退款记录，可分页查找。如不通过此条件查找，传null
 *  2 – 根据开始结束时间(startDate, endDate)查找退款记录，分页查找，主要是boss查找。如不通过此条件查找，均传null
 *  3 – 如不需分页查找pageNumber，pageSize均传null
 *
 */
struct BossQueryRefundOrderListReq{
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
 * 查询退款列表响应res
 */
struct BossQueryRefundOrderListRes{
	/** 返回结果  */
	1:Common.ResultVo resultVo,
	/** 退款信息列表  */
	2:list<BossQueryRefundInfoVo> refundInfoList,
	/** 分页信息  */
	3:Common.PageVo pageVo,
}

/**
 * 退款信息
 */
struct BossQueryRefundInfoVo{
	/** 退款ID  */
	1:string refundId,
	/** 原订单号  */
	2:string orderId,
	/** 退款申请时间  */
	3:i64 requestTime,
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
	/** 退款时间  */
	11:i64 refundTime,		
	/** 退款商品列表  */
	12:list<BossQueryRefundProductVo> productList,
	/** 退款商品列表  */
	13:list<BossQueryRefundPaymentVo> payList,	
	/** 银行端ID  */
	14:string clientId,	
}


/**
 * 退款商品
 */
struct BossQueryRefundProductVo{
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
struct BossQueryRefundPaymentVo{
	/** 支付流水 */
	1:string paymentId,
	/** 支付类型  */
	2:string paymentType,
	/** 退还金额或积分  */
	3:string refundValue,
	/** 退款支付时间  */
	4:i64 paymentTime,
}


/**
 * 退款详情查找请求VO
 */
struct BossQueryRefundDetailReq{
	/** 客户端ID  */
	1:string clientId,
	/** 退款号  */
	2:string refundId,
}


/**
 * 退款响应VO
 */
struct BossQueryRefundDetailRes{
	/** 返回结果  */
	1:Common.ResultVo resultVo,
	/** 退款ID  */
	2:BossQueryRefundInfoVo refundInfo,
}

/**
 * service定义 : 退款相关接口
 */
service BossQueryRefundOrderService extends BizMonitor.BizMonitorService {
	/**
	 * 退款查询接口
	 */
	BossQueryRefundOrderListRes getRefundOrderList(1:BossQueryRefundOrderListReq req);
	
	/**
	*退款查询报表导出
	*/
	Common.ExportResultRes exportRefundOrderList(1:BossQueryRefundOrderListReq req);
	
	/**
	*退款详情查询接口
	**/
	BossQueryRefundDetailRes getRefundDetail(1:BossQueryRefundDetailReq req);
}