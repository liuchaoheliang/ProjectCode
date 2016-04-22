namespace java com.froad.thrift.vo.Business

include "BizMonitor.thrift"
include "Common.thrift"


/**
 * 运营订单Vo
 */
struct BusinessOrderVo {
	/** 订单编号 */
    1:string orderId;
    
    /** 子订单编号 */
    2:string subOrderId;
    
    /** 订单状态1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成 */
    3:string orderStatus;
    
    /** 用户编号 */
    4:i64 memberCode;
      
    /** 支付方式 1:现金支付;2:联盟积分支付;3:银行积分支付;4:联盟积分+现金支付;5:银行积分+现金支付;6:赠送积分 */
    5:string paymentMethod;
        
    /** 总金额 */
    6:double totalPrice;
    
    /** 现金 */
    7:double realPrice;
    
    /** 联盟积分 */
    8:double fftPoints;
    
    /**银行积分*/
    9:double bankPoints;
    
    /** 银行积分兑换比例 */
    10:string poinRate;
    
    /** 订单创建时间 */
    11:i64 createTime;
    
    /** 支付时间 */
    12:i64 paymentTime;
    
    /** 所属客户端 */
    13:string clientId;
    
    /** 客户端名称 */
    14:string clientName;
    
    /** 发货状态 0:未发货;1:已发货;2:已收货;3:未提货;4:已提货 */
    15:string deliveryStatus;
    
    /** 退款状态 1:未退款;2:退款中;3:退款完成;4:部分退款 */
    16:string refundState;
}

/**
 *运营订单请求信息
 */
struct BusinessOrderListReq {
	/** 订单编号 */
	1:string orderId;
	
	/** 用户编号 */
	2:i64 memberCode;
	
	/** 所属客户端 */
	3:string clientId;
	
	/** 发货状态 0:未发货;1:已发货;2:已收货;3:未提货;4:已提货 */
	4:string deliveryStatus;
	
	/** 支付方式 1:现金支付;2:联盟积分支付;3:银行积分支付;4:联盟积分+现金支付;5:银行积分+现金支付;6:赠送积分 */
	5:string paymentMethod;
	
	/** 订单状态1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成 */
	6:string orderStatus;
	
	/** 订单创建开始时间 */
	7:i64 createStartTime;
	
	/** 订单创建结束时间 */
	8:i64 createEndTime;
	
	/**分页数据*/
	9:Common.PageVo pageVo;
	
	
}

/**
 *运营订单列表查询结果
 */
struct BusinessOrderListRes {
	/** 运营订单集合*/
	1:list<BusinessOrderVo> voList;
	
	/**返回结果*/
	2:Common.ResultVo resultVo;
	
	/**分页数据*/
	3:Common.PageVo pageVo;
}
/**
 *运营订单列支付信息Vo
 */
struct BusinessOrderPyamentInfoVo {
	/** 订单编号 */
	1:string orderId;
	
	/** 子订单编号 */
    2:string subOrderId;
	
	/** 创建时间 */
	3:i64 createTime;
	
	/** 用户编号 */
	4:i64 memberCode;
	
	/** 用户姓名 */
	5:string memberName;
	
	/** 所属客户端 */
	6:string clientId;
	
	/** 客户端名称 */
	7:string clientName;
	
	/** 订单来源 */
	8:string createResource;
	
	/** 支付方式 */
	9:string paymentMethod;
	
	/** 订单金额 */
	10:double totalPrice;
	
	/** 现金 */
	11:double realPrice;
	
	/** 联盟积分 */
	12:double fftPoints;
	
	/** 银行积分 */
	13:double bankPoints;
	
	/** 订单状态 */
	14:string orderStatus;
	
	/** 支付时间 */
	15:i64 paymentTime;
	
	/** 支付账单号 */
	16:string billNo;
	
	/** 银行积分兑换比例 */
    17:string poinRate;
}

/**
 *查询运营订单支付信息结果
 */
struct BusinessOrderPyamentInfoRes {
	/** 支付信息 */
	1:BusinessOrderPyamentInfoVo infoVo;
	
	/** 返回结果 */
	2:Common.ResultVo resultVo;
}

/**
 *运营订单交易概要信息Vo
 */
struct BusinessOrderTradeInfoVo {
	/** 结算状态 */
	1:string settleState;
	
	/** 结算账单号 */
	2:string settlement_id;
	
	/** 结算时间 */
	3:i64 settlementTime;
	
	/** 订单类型 */
	4:string orderType;
	
	/** 商品名称 */
	5:string productName;
	
	/** 购买数量 */
	6:i32 productCount;
	
	/** 预计发货日期 */
	7:i64 deliveryTime;
	
	/** 购买单价 */
	8:double price;
	
	/** 金额小计 */
	9:double moneyLittleCount;
	
	/** 提货方式 */
	10:string deliveryOption;
	
	/** 提货网点 */
	11:string deliveryAddress;
	
	/** 提（收）货人 */
	12:string consignee;

	/** 提（收）货手机号 */
	13:string phone;
	
	/** 最后消费时间 */
	14:i64 dealineConsumeTime;
	
	/** 消费数量 */
	15:i32 consumeAmount;
	
	/** 商品ID */
	16:string productId;
	
	/** 该商品是否退款  */
	17:bool isRefund;
}


/**
 *查询运营订单交易概要信息结果 
 */
struct BusinessOrderTradeInfoVoRes {
	/** 交易概要信息 */
	1:list<BusinessOrderTradeInfoVo> infoVos;
	
	/** 返回结果 */
	2:Common.ResultVo resultVo;
}

/**
 *运营订单物流信息Vo
 */
struct BusinessOrderShippingInfoVo {
	/** 发货状态  0：在途;1：揽件;2：疑难;3：签收;4：退签;5：派件;6：退回*/
	1:string status;
	
	/** 物流公司 */
	2:string shippingCorp;
	
	/** 发货日期 */
	3:i64 shippingTime;
	
	/** 物流单号*/
	4:string shippingId;

	/** 物流进度信息*/
	5:string message;
}

/**
 *查询运营订单物流信息结果
 */
struct BusinessOrderShippingInfoVoRes {
	/** 物流信息  */
	1:BusinessOrderShippingInfoVo infoVo;
	
	/** 返回结果 */
	2:Common.ResultVo resultVo;
}

/**
 *运营订单退款信息 Vo
 */
struct BusinessOrderRefundInfoVo {
	/** 退款编号 */
	1:string refundId;
	
	/** 退款状态 1:待处理;2:退款中;3:退款完成;4:退款失败;5:异常处理完成;6:商户审核中;7:商户审核拒绝;8:商户审核通过*/
	2:string refundStatus;
	
	/** 退款金额 */
	3:string refundValue;
	
	/** 退款积分 */
	4:string refundPoint;
	
	/** 申请时间 */
	5:i64 applyTime;
	
	/** 退款时间 */
	6:i64 refundTime;
	
	/** 退款说明 */
	7:string refundDesc;
	
	/**退款对应的商品Id */
	8:list<string> productIdList;
}

/**
 *查询运营订单退货信息结果
 */
struct BusinessOrderRefundInfoVoRes {
	/** 退货信息  */
	1:list<BusinessOrderRefundInfoVo> infoVo;
	
	/** 返回结果 */
	2:Common.ResultVo resultVo;

}


/**
 * BusinessOrderQueryService
 */
service BusinessOrderQueryService extends BizMonitor.BizMonitorService {

  
   /**
	 * 查询运营订单列表
	 * @param req 订单查询条件
	 * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	 * @return BusinessOrderListRes
	 */
    BusinessOrderListRes queryBusinessOrderList(1:BusinessOrderListReq req),
  
    /**
     * 运营订单导出
     * 
     * @param req 订单查询条件
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @return Common.ExportResultRes
     */
    Common.ExportResultRes exportBusinessOrder(1:BusinessOrderListReq req),
  
	/**
     * 查询运营订单支付信息
     * 
     * @param clientId 所属客户端
     * @param subOrderId 订单编号
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @return BusinessOrderPyamentInfoRes
     */
	BusinessOrderPyamentInfoRes queryBusinessOrderPaymentInfo(1:string clientId, 2:string subOrderId);
    
    /**
     * 查询运营订单交易概要信息 
     * 
     * @param clientId 所属客户端
     * @param subOrderId 子订单编号
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @return BusinessOrderTradeInfoVoRes
     */
    BusinessOrderTradeInfoVoRes queryBusinessOrderTradeInfo(1:string clientId, 2:string subOrderId);
   	
   	/**
     * 查询运营订单物流信息 
     * 
     * @param clientId 所属客户端
     * @param subOrderId 子订单编号
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @return BusinessOrderShippingInfoVoRes
     */
   	BusinessOrderShippingInfoVoRes queryBusinessOrderShippingInfo(1:string clientId, 2:string subOrderId);
   	
   	/**
     * 查询运营订单退款信息
     * 
     * @param clientId 所属客户端
     * @param subOrderId 子订单编号
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @return BusinessOrderRefundInfoVoRes
     */
   	BusinessOrderRefundInfoVoRes queryBusinessOrderRefundInfo(1:string clientId, 2:string subOrderId);
}