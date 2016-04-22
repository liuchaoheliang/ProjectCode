namespace java com.froad.thrift.vo.active

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/**
 * 营销活动标签关联
 */
struct ActiveTagRelationVo{
    /**
      * 主键ID
    */
    1:i64 id;
    /**
      * 客户端ID
    */    
    2:string clientId
    /**
      * 创建时间
    */    
    3:i64 createTime;
    /**
      * 更新时间
    */     
    4:i64 updateTime;
    /**
      * 活动ID
    */     
    5:string activeId;
    /**
      * 项⽬类型 0-商户1-门店(预留) 2-商品
    */    
    6:string itemType;
    /**
      * 商户/门店/商品标签ID
    */    
    7:string itemId;
}

/**
 * 营销活动基础规则
 */
struct ActiveBaseRuleVo {
    /**
      * 主键ID
    */
    1:i64 id;
    /**
      * 客户端ID
    */     
    2:string clientId;
    /**
      * 活动ID
    */    
    3:string activeId;
    /**
      * 活动类型(上限100种活动)
    */
    4:string type;
    /**
      *活动状态 0-待提交1-审核中2-审核不通过
      *3-启动 4-禁止
    */ 
    5:string status;
    /**
      * 创建时间
    */
    6:i64 createTime;
    /**
      * 更新时间
    */
    7:i64 updateTime;
    /**
      * 活动名称
    */
    8:string activeName;
    /**
      * 活动logo对应url(可无)
    */    
    9:string activeLogo;
    /**
      * 有效期开始时间
    */
    10:i64 expireStartTime;
    /**
      * 有效期结束时间
    */    
    11:i64 expireEndTime;
    /**    
     *限制类型
     *0-不限制 1-限制商户 2-限制门
     *店(预留) 3-限制商品
    */ 
    12:string limitType;
    /**
      * 商户补贴比例
    */     
    13:i32 merchantRate;
    /**
      * 银行补贴比例
    */ 
    14:i32 bankRate;
    /**
      * 方付通补贴比例
    */    
    15:i32 fftRate;
    /**
      *结算方式 0-实时结算 1-延期结算
    */
    16:string settleType;
    /**
      * 活动描述
    */
    17:string description; 
    /**
      * 操作员
    */
    18:string operator;
	
}
/**
 * export URL
 */
struct ExportActiveOrderInfoRes {
    /**excel url*/
    1:string exportUrl;
}

/**
 * 营销活动详细规则
 */
struct ActiveDetailRuleVo {
    /**
      * 主键ID
    */
    1:i64 id;
    /**
      * 客户端ID
    */
    2:string clientId;
    /**
      * 活动ID
    */   
    3:string activeId;
    /**
      * 金额下限
    */ 
    4:i64 minLimit;
    /**
      *活动奖励人次限制
    */ 
    5:i64 maxCount;
    /**
      *每人时间间隔单位为时或者日
    */     
    6:bool isPerDay
    /**
      *每人限定的间隔量
    */ 
    7:i32 perDay;
    /**
      *每人间隔量限定的次数
    */
    8:i64 perCount;
    /**
      *全局时间间隔为时或者日
    */
    9:bool isTotalDay;
    /**
      *全局限定的日间隔
    */    
    10:i32 totalDay;
    /**
      *全局限定的次数
    */    
    11:i64 totalCount;
    /**
      *是否支持代金券支付
    */    
    12:bool isPaperPay;
    /**
      *true支付前 false支付之
    */    
    13:bool isPrePay;
    /**
      *减金额
    */ 
    14: optional i64 retMoney;
    /**
      *满减金额总值
    */ 
    15: optional i64 maxMoney;
    /**
      *支付后送优惠活动ID
    */
    16: optional string prePayActiveId;
    /**
      *支付后送商品ID
    */    
    17: optional string productId;
    /**
      *支付后送商品总数限制
    */
    18: optional i32 productCount;
    /**
      *支付后积分数量
    */    
    19: optional i32 point;
    /**
      *支付后积分类型
    */     
    20: optional string pointType;
    /**
      *支付后积分总数量
    */     
    21: optional i32 pointCount;
    /**
      *支付方式限制类型
    */
    22: optional string payMethod;
}

/**
 * 营销活动规则信息
 */
struct ActiveRuleInfoVo {
	/** 营销活动标签关联 */
	1:ActiveTagRelationVo activeTagRelation; 	
	/** 营销活动基础规则 */
	2:ActiveBaseRuleVo activeBaseRule;
	/** 营销活动详细规则 */
	3:ActiveDetailRuleVo activeDetailRule;
}

/**
 * 营销活动订单
 */
struct ActiveOrderVo {
    /**
      * 主键 活动订单id - 记录在社区银行订单表中
    */
    1:string id;
    /**
      * 客户端ID
    */
    2:string clientId;
    /**
      * 创建时间
    */    
    3:i64 createTime;
    /**
      * 更新时间
    */     
    4:i64 updateTime;
    /**
      * Order ID
    */
    5:string orderId;
    /**
      * 状态，是否有效 0-无效 1-有效
    */    
    6:bool status;
    /**
      * 订单原始金额
    */    
    7:i32 orderOriMoney;
    /**
      * 订单成交金额
    */    
    8:i32 orderMoney;
    /**
      * 订单原始金额(有退款操作后更新)
    */    
    9:i32 orderOriModifyMoney;
    /**
      * 订单成交金额(有退款操作后更新)
    */     
    10:i32 orderModifyMoney;
    /**
      * 支付时间(支付成功后记录的时间)
    */     
    11:i64 payTime;
    /**
      * 用户id
    */    
    12:i64 memberCode;
    /**
      * 用户名称
    */      
    13:string memberName;
    /**
      * 用户电话号码
    */      
    14:string phone;
    /**
      * 交易明细-每种商品实际成交价格以及数量[{"product_id":"","money":"","vip_money":"","quantity":"","vip_quantity":""}]
    */     
    15:string detail;
}

/**
 * 营销活动订单详情
 */
struct ActiveOrderDetailVo {
	/** 主键 **/
	1:i64 id;
	
	/** 客户端ID **/
	2:string clientId;
	
	/** 创建时间**/
	3:i64 createTime;
	
	/** 修改时间 **/
	4:i64 updateTime;
	
	/** 订单ID**/
	5:string orderId;
	
	/** 订单优惠类型 0-减 1-送  **/
	6:string orderMarketType;
	
	/**  活动ID **/
	7:string activeId;
	
	/** 营销活动类型**/
	8:string type;
	
	/** 子订单Id**/
	9:string subOrderId;
	
	/** 商品Id **/
	10:string productId;
	
	/** 普通价格优惠单价 **/
	11:i16 normalPriceMarket;
	
	/** 普通价格优惠数量 **/
	12:i16 normalPriceCount;
	
	/** vip价格优惠单价 **/
	13:i16 vipPriceMarket;
	
	/** vip价格优惠数量 **/
	14:i16 vipPriceCount;
	
	/** 送现金券时对应的券id，其它送优惠(商品,积分)直接根据active_id关联查询  **/
	15:string ticketId;
}

/**
 * 新增结果
 */
struct AddResultVo {
	 /**  ResultVo结果*/
	1:Common.ResultVo resultVo;	
	 /** 主键id */
	2:i64 id;       
}

/**
 * 创建结果
 */
struct CreateResultVo {
    /**  ResultVo结果*/
	1:Common.ResultVo result;	
	/** 营销的满减订单id */
	2:string cutOrderId;
	/** 营销的红包订单id */
	3:string vouchersOrderId;  
}

/**
 * 营销活动规则信息 ActiveRuleInfoVo 分页
 */
struct ActiveRuleInfoPageVoRes {
	1:Common.PageVo page;
	2:list<ActiveRuleInfoVo> activeRuleInfoVoList;
}

/** 查询 营销活动规则信息 ActiveRuleInfoVo 列表 结果 */
struct FindAllActiveRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果集 */
	2:list<ActiveRuleInfoVo> activeRuleInfoVoList;
}

/** 查询 营销活动规则信息 ActiveRuleInfoVo 分页 结果 */
struct FindPageActiveRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 分页结果 */
	2:ActiveRuleInfoPageVoRes activeRuleInfoPageVoRes;
}

/** 查询 营销活动规则信息 ActiveRuleInfoVo 单个 结果 */
struct FindActiveRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果 */
	2:ActiveRuleInfoVo activeRuleInfoVo;
}

/**
 * 进入购物车 请求
 */
struct ShoppingCartReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 购物车请求商品 - 列表 */
    4:list<ShoppingCartReqProductVo> shoppingCartReqProductList;
}
/**
 * 购物车请求商品
 */
struct ShoppingCartReqProductVo{
    /** 商品id */
    1:string productId;
    /** 商品名称 */
    2:string productName;
    /** 商品总金额 */
    3:double productTotalMoney;
    /** 商品vip总金额 */
    4:double vipTotalMoney;
    /** 活动id 后台逻辑使用-前天不要传 */
    5:optional string activeId;
}

/**
 * 进入购物车 响应
 */
struct ShoppingCartResVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 购物车响应商品 - 列表 */
    4:list<ShoppingCartResProductVo> shoppingCartResProductList;
	/**购物车满赠 - 列表*/
	5:list<ShoppingCartFullGiveVo> shoppingCartFullGiveList;
}
/**
 * 购物车响应商品
 */
struct ShoppingCartResProductVo{
    /** 商品id */
    1:string productId;
    /** 购物车响应活动 - 列表 */
    2:list<ShoppingCartResActiveVo> shoppingCartResActiveList;
}
/**
 * 购物车响应活动
 */
struct ShoppingCartResActiveVo{
	/** 活动id */
    1:string activeId;
    /** 活动名称 */
    2:string activeName;
    /** 活动类型 */
    3:string activeType;
    /** 满减金额 */
    4:double cutMoney;
    /** 活动状态 */
    5:string activeStatus;
    /** 状态描述 */
    6:string statusMsg;
    /** 是否凑单 */
    7:bool isMinato;
    /** 是否最低删除 */
	8:bool isLowestDelete;
	/** 赠送类型 */
	9:string giveType;
	/** 赠送金额 */
	10:double giveMoney;
}

/**
 * 购物车满赠 
 */
struct ShoppingCartFullGiveVo{
	/** 满赠活动id */
	1:string fullGiveActiveId;
	/** 满赠活动名称 */
	2:string fullGiveActiveName;
	/** 赠送类型 */
	3:string giveType;
	/** 赠送金额 */
	4:double giveMoney;
}

/**
 * 订单校验 请求
 */
struct CheckOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 订单总金额 */
    4:double orderMoney;
    /** 订单商品 - 列表 */
    5:list<OrderProductVo> orderProductList;
    /** 代金券(红包) - 列表 */
    6:optional list<string> vouchersIds;
    /** 订单中参与的营销活动id - 列表 */
    7:optional list<string> sustainActiveIds;
    /** 是否面对面订单 */
    8:bool isF2FOrder;
}
/**
 * 订单商品
 */
struct OrderProductVo{
	/** 商品id */
    1:string productId;
    /** 商品名称 */
    2:string productName;
    /** 满减活动id */
    3:string activeId;
    /** 普通单价 */
    4:double generalPrice;
    /** 普通数量 */
    5:i32 generalCount;
    /** vip单价 */
    6:double vipPrice;
    /** vip数量 */
    7:i32 vipCount;
    /** 满赠活动id */
    8:string activeIdGive;
}

/**
 * 订单校验 响应
 */
struct CheckOrderResVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 订单成交总金额 */
    4:double orderDealMoney;
    /** ResultVo 结果 */
	5:Common.ResultVo result;
    /** 满赠活动响应 - 列表 */
    6:list<FullGiveActiveVo> fullGiveActiveList;
}
/**
 * 订单响应
 */
struct OrderResVo{
    /** 商品id */
    1:string productId;
    /** 活动id */
    2:string activeId;
    /** 活动名称 */
    3:string activeName;
    /** 活动类型 */
    4:string activeType;
    /** 满减金额 */
    5:double cutMoney;
    /** 普通单个满减金额 */
    6:double generalSingleCutMoney;
    /** 普通最后一个满减金额 */
    7:double generalAtLastCutMoney;
    /** VIP单个满减金额 */
    8:double vipSingleCutMoney;
    /** VIP最后一个满减金额 */
    9:double vipAtLastCutMoney;
}

/**
 * 创建订单失败回退 请求
 */
struct FailureGoBackReqVo{
	 /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 订单id */
    4:string orderId;
    /** 满减活动id - 列表 */
    5:optional list<string> activeIdList;
    /** 代金券/红包id - 列表 */
    6:optional list<string> vouchersIdList;
}

/**
 * 创建营销活动订单 请求
 */
struct CreateMarketOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 用户名称 */
    4:optional string memberName;
    /**电话号码 */
    5:string phone;
    /** 订单id */
    6:string orderId;
    /** 订单原始金额 */
    7:double orderOriMoney;
    /** 订单成交金额 */
    8:double orderMoney;
    /** 子订单 - 列表 */
    9:list<MarketSubOrderVo> marketSubOrderList;
    /** 代金券(红包) - 列表 */
    10:optional list<string> vouchersIds;
}

/**
 * 子订单
 */
struct MarketSubOrderVo{
    /** 子订单id */
    1:string subOrderId;
    /** 子订单商品 - 列表 */
    2:list<MarketSubOrderProductVo> marketSubOrderProductList;
}

/**
 * 子订单商品
 */
struct MarketSubOrderProductVo{
    /** 满减活动id */
    1:string activeId;
    /** 商品id */
    2:string productId;
    /** 商品名称 */
    3:string productName;
    /** 普通单价 */
    4:double generalPrice;
    /** 普通数量 */
    5:i32 generalCount;
    /** vip单价 */
    6:double vipPrice;
    /** vip数量 */
    7:i32 vipCount;
    /** 送现金券时对应的券id - 其它送优惠(商品,积分)直接根据active_id关联查询 */
    8:optional string ticketId;
    /** 满赠活动id */
    9:string activeIdGive;
}

/**
 * 创建营销活动订单 响应
 */
struct CreateMarketOrderResVo{
     /** 请求id */
    1:Common.ResultVo Result;
    /**促销平台订单编号*/
    2:string marketOrderId;
}

/**
 * 查询营销活动订单 请求
 */
struct FindMarketOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 订单id */
    3:string orderId;
    /** 子订单id */
    4:string subOrderId;
    /** 商品id */
    5:string productId;
}

/**
 * 查询营销活动订单 响应
 */
struct FindMarketOrderResVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 订单id */
    3:string orderId;
    /** 查询营销活动子订单 - 列表 */
    4:list<FindMarketSubOrderVo> findMarketSubOrderList;
    /** 响应结果 */
    5: Common.ResultVo result;

}
/**
 * 查询营销活动子订单
 */
struct FindMarketSubOrderVo{
    /** 子订单id */
    1:string subOrderId;
    /** 查询营销活动子订单商品 - 列表 */
    2:list<FindMarketSubOrderProductVo> findMarketSubOrderProductList;
}
/**
 * 查询营销活动子订单商品
 */
struct FindMarketSubOrderProductVo{
    /** 商品id */
    1:string productId;
    /** vip满减金额 */
    2:double vipCutMoney;
    /** 满减金额 */
    3:double cutMoney;
    /** vip代金券/红包金额 */
    4:double vipVouMoney;
    /** 代金券/红包金额 */
    5:double vouMoney;
}


/**
 * 关闭营销活动订单 请求
 */
struct CloseMarketOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 订单id */
    3:string orderId;
    /** 用户编号 */
    4:i64 memberCode
    /** 原因  0-系统关单/客户取消订单  1-客户退货关单*/
    5:i32 reason;

}





/**
 * 营销活动订单退款 请求
 */
struct ReturnMarketOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 订单id */
    3:string orderId;
    /**是否是查询*/
    4:bool  isQuery;
    /** 退款子订单 - 列表 */
    5:list<ReturnSubOrderReqVo> returnSubOrderReqList;
    /** 退款编号*/
    6:string returnBillNo;
    /** 退款金额*/
    7:double returnMoney;
    
}
/**
 * 退款子订单
 */
struct ReturnSubOrderReqVo{
    /** 子订单id */
    1:string subOrderId;
    /** 退款子订单商品 - 列表 */
    2:list<ReturnSubOrderProductReqVo> returnSubOrderProductReqList;
}
/**
 * 退款子订单商品
 */
struct ReturnSubOrderProductReqVo{
    /** 商品id */
    1:string productId;
    /** vip数量 */
    2:i32 vipCount;
    /** 普通数量 */
    3:i32 normalCount;
}




/**
 * 营销活动订单退款 响应
 */
struct ReturnMarketOrderResVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 订单id */
    3:string orderId;
    /** 退款子订单 - 列表 */
    4:list<ReturnSubOrderResVo> returnSubOrderResList;
     /** 响应结果 */
    5: Common.ResultVo result;
}
/**
 * 退款子订单 响应
 */
struct ReturnSubOrderResVo{
    /** 子订单id */
    1:string subOrderId;
    /** 退款子订单商品 - 列表 */
    2:list<ReturnSubOrderProductResVo> returnSubOrderProductResList;
}
/**
 * 退款子订单商品响应
 */
struct ReturnSubOrderProductResVo{
    /** 商品id */
    1:string productId;
    /** 普通满减优惠总价*/
    2:double price;
    /** Vip满减优惠总价*/
    3:double vipPrice;
    /** 普通红包优惠总价*/
    4:double vouPrice;
    /** Vip红包优惠总价*/
    5:double vipVouPrice;
    /** vip数量 */
    6:i32 vipCount;
    /** 普通数量 */
    7:i32 normalCount;
}



/**
 * 营销活动订单退款回退 请求
 */
struct ReturnMarketOrderBackReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 订单id */
    3:string orderId;
    /** 退款子订单 - 列表 */
    4:list<ReturnSubOrderBackReqVo> returnSubOrderBackReqList;
    /** 退款编号*/
    5:string returnBillNo;
    /** 退款金额*/
    6:double returnMoney;
}

/**
 * 退款子订单回退
 */
struct ReturnSubOrderBackReqVo{
    /** 子订单id */
    1:string subOrderId;
    /** 退款子订单商品 - 列表 */
    2:list<ReturnSubOrderProductBackReqVo> returnSubOrderProductBackReqList;
}

/**
 * 退款子订单商品回退
 */
struct ReturnSubOrderProductBackReqVo{
    /** 商品id */
    1:string productId;
    /** vip数量 */
    2:i32 vipCount;
    /**Vip回退总金额*/
    3:double vipPrice;
    /** 普通数量 */
    4:i32 normalCount;
    /**普通回退总金额*/
    5:double normalPrice;
}


 /**
 *结算请求
 **/
struct SettlementMarkOrderReq{
	 /** 请求id */
    1:string reqId;
	/** 商品id */
    2:string productId;
    /**是否最后一个*/
    3:bool isLast;
    /**订单Id*/
    4:string orderId;
    /**订单子Id*/
    5:string subOrderId;
    /**结算普通数量*/
    6:i32 count;
    
    /**结算Vip数量*/
    7:i32 vipCount;
    
}

 /**
 *结算响应
 **/
struct SettlementMarkOrderRes{
	/** 普通优惠金额 */
    1:double money;
    /** vip优惠金额*/
    2:double vipMoney;
    /** 响应结果 */
    3: Common.ResultVo resultVo;
}






/**
 * 修改营销活动订单 请求
 */
struct UpdateMarketOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 订单id */
    4:string orderId;
    /** 营销活动订单id */
    5:string market_Id;
    /** 状态 */
    6:bool status;
    /**支付时间*/
    7:i64 payTime;
    /** 代金券/红包id 列表 */
    8:optional list<string> vouchersIdList;
    /** 是否面对面订单 */
    9:bool isF2FOrder;
    /** 支付账单号 */
    10:string payBillNo;
    /** 满赠活动id - 列表 */
    11:list<string> fullGiveActiveIds;
	/** 会员账号(联盟积分需要传此参数) */
	12:string loginId;    
}

/**
 * 修改营销活动订单 响应
 */
struct UpdateMarketOrderResVo{
    /** 响应结果 */
    1:Common.ResultVo result;
    /** 满赠活动响应 - 列表 */
    2:list<FullGiveActiveVo> fullGiveActiveList;
}

/**
 * 满赠活动响应
 */
struct FullGiveActiveVo{
    /** 活动id */
    1:string activeId;
    /** 活动类型 2红包 4联盟积分 */
    2:string activeType;
    /** 金额值 */
    3:double monry;
}

/**
 * 查询营销活动规则 - 根据商户
 */
struct FindActiveRuleByMerchantVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 客户号 **/
    3:string memberCode;
    /** 商户id - 列表 */
    4:list<string> merchantIdList;
}

/**
 * 查询营销活动规则 - 根据商品
 */
struct FindActiveRuleByProductVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 客户号 **/
    3:string memberCode;
    /** 商品id - 列表 */
    4:list<string> productIdList;
}

/**
 * 查询营销活动规则 结果集
 */
struct FindActiveRuleListResVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 查询营销活动规则结果 列表 */
    3:list<FindActiveRuleResVo> findActiveRuleResList;
    
}
/**
 * 查询营销活动规则结果
 */
struct FindActiveRuleResVo{
    /** 查询条件id - merchantId/productId */
    1:string id;
    2:list<FindActiveVo> findActiveList;
}
/**
 * 查询营销活动
 */
struct FindActiveVo{
    /** 活动id */
    1:string activeId;
    /** 活动名称 */
    2:string activeName;
    /** 活动类型 */
    3:string activeType;
    /** 活动描述 **/
    4:string description;
}


/**
 * 凑单 - 参数
 */
struct MinatoSingleParamVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 商品id */
    3:string productId;
    /** 活动id */
    4:string activeId;
    /** 区域id */
    5:string areaId;
    /** cookie id */
    6:string cookieId;
    /** 商品类型 1 团购;2:预售;3:名优特惠;4:在线兑换;5:网店礼品**/
    7:string productType;
    /** 凑单坐标 */
    8:MinatoSingleLocationVo minatoSingleLocation;
}
/**
 * 凑单坐标
 */
struct MinatoSingleLocationVo{
    /** 经度 */
	1:double longitude;
	/** 纬度 */
	2:double latitude;
}

/**
 * 凑单商品列表
 */
struct MinatoSingleProductListVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 活动对象 */
    3:FindActiveVo activeVo 
    /** 区域id */
    4:string areaId;
    /** 分页信息**/
    5:Common.PageVo page;
    /** 凑单商品列表 **/
    6:list<MinatoSingleProductVo> minatoSingleProductVoList;
}
/**
 * 凑单商品
 */
struct MinatoSingleProductVo{
    /** 客户端id */
    1:string clientId,    
    /** 商户ID */
    2:string merchantId,    
    /** 商品id */
    3:string productId,    
    /** 商品类型 */
    4:string type,    
    /** 商品名 */
    5:string name, 
    /** 商品全名 */
    6:string fullName,   
    /** 销售价 */
    7:double price,    
    /** 市场价 */
    8:double marketPrice,    
    /** 销售数量 */
    9:i32 sellCount,    
    /** 销售有效期开始 */
    10:i64 startTime,    
    /** 销售有效期结束 */
    11:i64 endTime,    
    /** 简介 */ 
    12:string briefIntroduction,
    /** 小图片地址 */ 
    13:string smallImgUrl,
	/** 是否秒杀0非秒杀,1秒杀,2秒杀未上架 */
    14:string isSeckill,
    /** 商品库存数量 */
    15:i32 store,          
    /** 服务器时间 */
    18:i64 serverTime,    
    /** 是否参与送积分活动 */
    19:bool isPoint,      
    /** VIP价 */ 
    20:double vipPrice   

 }
 
struct OutletDetailSimplePageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店详情信息列表 */
	2:list<OutletDetailSimpleVo> outletDetailSimpleVoList;
}


/**
 * 门店详情概要信息
 */
struct OutletDetailSimpleVo {
    
    /** 商户id */
    1:  optional string merchantId;      
    
       /** 商户名称 */
    2: optional string merchantName;      
    
       /** 门店Id */
    3: optional string outletId;                                            
    
    /** 门店名称 */
    4:  optional string  outletName;
    /** 门店默认图片(小图) */
    5:  optional string defaultImage;                                                                   
    
    /** 地址 */
    8: optional string  address;                                                                      
    
    /** 计算出的距离 */
    9: optional double dis;  
    
      /** 星评 */
    10: optional string starLevel;  
    
}

/**
 *商品列表查询过滤条件
 */
struct QueryProductFilterVo {
    /** 客户端ID */
    1:string clientId, 
    /** 商品类型 */
    2:string type,
    /** 地区ID */
    3:i64 areaId, 
    /** 经度 */ 
    4:double longitude,
    /** 纬度 */ 
    5:double latitude,
    /** 距离 */ 
    6:double distance,
    /** 是否推荐列表 */   
    7:bool isRecommend,
    /** 商品分类ID */
    8:i64 productCategoryId,
    /** 商品名 */
    9:string productName,
    /** 商户ID */   
    10:string merchantId
    
}

/**
 *商品是否需要添加满减活动
 */
struct PutFullCutReqVo {
    /** 客户端ID */    
    1:string clientId;
    /** 商品ID */    
    2:set<string> productIds;
    /** 标签类型 */
    3:string itemType;
    /** 标签id */
    4:string itemId;

}

/**
 * 满赠资格检查
 */
struct FullGiveCheckReqVo {
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 客户号 **/
    3:i64 memberCode;
    /** 满赠活动id - 列表 */
    4:list<string> fullGiveActiveIds;
    /** 订单商品 - 列表 */
    5:list<OrderProductVo> orderProductList;
}

/**
 * 代金券规则信息
 */
struct VouchersRuleInfoVo {
	/** 营销活动标签关联 */
	1:ActiveTagRelationVo activeTagRelation; 	
	/** 营销活动基础规则 */
	2:ActiveBaseRuleVo activeBaseRule;
	/** 代金券详细规则 */
	3:VouchersDetailRuleVo vouchersDetailRule;
}

/**
 * 代金券详细规则
 */
struct VouchersDetailRuleVo {
	/** 主键ID */
	1:i64 id;
	/** 客户端ID */
	2:string clientId;  
	/** 活动ID */
	3:string activeId;
	/** 代金券最小金额 */
	4:double minMoney;
	/** 代金券最大金额 */
	5:double maxMoney;
	/** 代金券总金额 */
	6:double totalMoney;
	/** 时间段限制单位0小时1天 */
	7:bool isTotalDay; 
	/** 时间段限制数量 */  
	8:i32 totalDay;
	/** 时间段限制次数 */
	9:i32 totalCount;
	/** 订单最小限额-才能使用 */
	10:double orderMinMoney;
	/** 每人限购-时间段单位 */
	11:bool isPerDay;
	/** 每人限购-时间段数量 */
	12:i32 perDay;
	/** 每人限购-数量 */
	13:i32 perCount;
	/** 是否可以和其它红包重复使用 */
	14:bool isRepeat;
	/** 是否支持参与其它促销 */
	15:bool isOtherActive;
	/** 0-⽀付前 1-⽀付后 */
	16:bool isPrePay;  
	/** 是否支持面对面 */
	17:bool isFtof;
	/** 是否仅限新用户试用 **/
	18:bool isOnlyNewUsers
	/** ⽀付⽅式限制类型 */
	19:optional string payMethod;  
	/** 支持参与的促销活动列表 */
	20:optional list<ActiveSustainRelationVo> activeSustainRelationList;
	/** 代金券id 列表 */
	21:optional list<VouchersCouponCodeVO> vouchersCouponCodelist;
	/** 代金券实际总数 （分批录入）*/
	22:i64 vouchersTotal;
	/** 上传券码活动ID */
	23:string temporaryActiveId;
}

/**
 * 支持参与的促销活动
 */
struct ActiveSustainRelationVo{
    /** 主键ID */
    1:i64 id;  
    /** 客户端ID */
    2:string clientId;  
    /** 创建时间 */
    3:i64 createTime;  
    /** 更新时间 */
    4:i64 updateTime;
    /** 活动ID */   
    5:string activeId;
    /** 活动名称 */   
    6:string activeName ;
    /** 支持活动类型 */   
    7:string sustainActiveType;
    /** 支持活动ID */ 
    8:string sustainActiveId;
    /** 支持活动名称 **/
    9:string sustainActiveName;
}

/** 查询 代金券规则信息 VouchersRuleInfoVo 列表 结果 */
struct FindAllVouchersRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果集 */
	2:list<VouchersRuleInfoVo> vouchersRuleInfoList;
}

/** 查询 代金券规则信息 VouchersRuleInfoVo 分页 结果 */
struct FindPageVouchersRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 分页结果 */
	2:VouchersRuleInfoPageVoRes vouchersRuleInfoPageRes;
}

/** 查询 代金券规则信息 VouchersRuleInfoVo 单个 结果 */
struct FindVouchersRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果 */
	2:VouchersRuleInfoVo vouchersRuleInfo;
}

/**
 * 代金券规则信息 VouchersRuleInfoVo 分页
 */
struct VouchersRuleInfoPageVoRes {
	1:Common.PageVo page;
	2:list<VouchersRuleInfoVo> vouchersRuleInfoList;
}

/**
 * 查询红包详情 请求 - 订单确认
 */
struct FindVouchersOfSubmitReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 查询可用类型 true可用 */
    4:bool isAvailable;
    /** 订单中参与的别的营销活动id列表 */
    5:list<string> sustainActiveIds;
    /** 商品列表 - 查询校验使用 */
    6:list<ProductOfFindUseVo> productOfFindUseList;
    /** 分页参数 */
    7:Common.PageVo page;
    /** 订单金额 */
    8:double orderMoney;  
    /** 是否支持面对面*/
    9:bool isFtoF; 
}

/**
 * 商品 - 查询校验使用
 */
struct ProductOfFindUseVo{
    /** 商品id */
    1:string productId;
    /** 商品名称 */
    2:string productName;
    /** 普通金额 */
    3:double generalMoney;
    /** 普通数量 */
    4:i32 generalCount;
    /** vip金额 */
    5:double vipMoney;
    /** vip数量 */
    6:i32 vipCount;
}

/**
 * 查询红包详情 响应
 */
struct FindVouchersResVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 分页参数 */
    4:Common.PageVo page;
    /** 红包信息列表 */
    5:list<VouchersInfoVo> vouchersInfoList;
}

/**
 * 红包信息
 */
struct VouchersInfoVo{
    /** 请求id */
    1:i64 id; 
    /** 客户端id */
    2:string clientId;  
    /** 创建时间 */
    3:i64 createTime;
    /** 修改时间 */
    4:i64 updateTime;
    /** 活动id */
    5:string activeId;
    /** 代金券(红包)id */
    6:string vouchersId;
    /** 代金券(红包)金额 */
    7:double vouchersMoney;
    /** 代金券(红包)使用金额 */
    8:double vouchersUseMoney;
    /** 代金券(红包)剩余金额 */
    9:double vouchersResMoney;
    /** 会员编号 */
    10:optional i64 memberCode;
    /** 发送代金券(红包)的活动id */
    11:optional string sendActiveId;
    /** 发送时间 */
    12:optional i64 sendTime;
    /** 状态 0初始化(未使用) 1支付中 2支付成功 */
    13:string status;
    /** 使用时间 */
    14:optional i64 useTime;
    /** 使用的会员编号 */
    15:optional i64 userMemberCode;
    /** 暂不能使用的原因 */
    16:optional string reason;
    /** 使用的天数预警 */
    17:optional string daysWarn;
    /** 活动名称 */
    18:optional string activeName;
    /** 活动描述 */
    19:optional string description;
    /** 有效期结束时间 */    
    20:optional i64 expireEndTime;
    /** 有效期开始时间 */    
    21:optional i64 expireStartTime;
    /** 使用的订单id */    
    22:optional i64 useOrderId;
}

/**
 * 查询红包详情 请求 - 会员中心
 */
struct FindVouchersOfCenterReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 1未过期2已过期3已使用 */
    4:string status;
    /** 分页参数 */
    5:Common.PageVo page;
}

/**
 * 校验代金券 请求
 */
struct CheckVouchersReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 代金券id */
    4:string vouchersId;
    /** 订单金额 */
    5:double orderMoney;
    /** 订单中参与的别的营销活动id列表 */
    6:list<string> sustainActiveIds;
    /** 商品列表 - 查询校验使用 */
    7:list<ProductOfFindUseVo> productOfFindUseList;
    /** 是否支持面对面 */
    8:bool isFtoF;
    
}

/**
 * 校验代金券 响应
 */
struct CheckVouchersResVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** ResultVo结果 */
	4:Common.ResultVo result;
	/** 代金券金额 */
	5:double vouchersMonry;
}

/**
 * 校验订单 请求
 */
struct CheckVouchersOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 订单金额 */
    4:double orderMoney;
    /** 代金券id 列表 */
    5:list<string> vouchersIds;
    /** 订单中参与的别的营销活动id列表 */
    6:list<string> sustainActiveIds;
    /** 商品列表 - 查询校验使用 */
    7:list<ProductOfFindUseVo> productOfFindUseList;
}

/**
 * 校验订单 响应
 */
struct CheckVouchersOrderResVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** ResultVo结果 */
	4:Common.ResultVo resultVo;
	/** 代金券金额 */
	5:double vouchersMonry;
}

/**
 * 创建订单 请求
 */
struct CreateVouchersOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 用户名称 */
    4:string memberName;
    /** 电话号码 */
    5:string phone;
    /** 订单id */
    6:string orderId;
    /** 订单金额 */
    7:optional double orderOriMoney;
    /** 订单金额 */
    8:double orderMoney;
    /** 代金券id 列表 */
    9:list<string> vouchersIds;
    /** 子订单 列表 */
    10:list<CreateVouchersSubOrderReqVo> createVouchersSubOrderReqList
}

/**
 * 子订单
 */
struct CreateVouchersSubOrderReqVo{
    /** 子订单id */
    1:string subOrderId;
    /** 子订单商品 列表 */
    6:list<CreateVouchersSubOrderProductReqVo> createVouchersSubOrderProductReqList;
}

/**
 * 子订单商品
 */
struct CreateVouchersSubOrderProductReqVo{
    /** 商品id */
    1:string productId;
    /** 商品名称 */
    2:string productName;
    /** 普通单价 */
    3:double generalPrice;
    /** 普通金额 */
    4:double generalMoney;
    /** 普通数量 */
    5:i32 generalCount;
    /** vip单价 */
    6:double vipPrice;
    /** vip金额 */
    7:double vipMoney;
    /** vip数量 */
    8:i32 vipCount;
}

/**
 * 创建订单 响应
 */
struct CreateVouchersOrderResVo{
    /** 结果 */
    1:Common.ResultVo Result;
    /**促销平台订单编号*/
    2:string marketOrderId;
}

/**
 * 订单创建失败回退 请求
 */
struct CreateVouchersOrderFailureGoBackReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 代金券id 列表 */
    4:list<string> vouchersIds;
}

/**
 * 支付结果通知 请求
 */
struct PayResultNoticeReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 订单id */
    4:string orderId;
    /** 代金券id 列表 */
    5:list<string> vouchersIds;
    /** 成功或者失败 */
    6:bool isSuccess;
}

/**
 * 关单 请求
 */
struct CloseVouchersOrderReqVo{
    /** 请求id */
    1:string reqId;
    /** 客户端id */
    2:string clientId;
    /** 用户编号 */
    3:i64 memberCode;
    /** 订单id */
    4:string orderId;
    /** 原因 */
    5:string reason;
}

/**
 * 红包转换 请求
 */
struct VouchersToRedPackReqVo{
    /**
      * 客户端ID
    */    
    1:string clientId
    /**
      * 券码ID
    */    
    2:string vouchersId
    /**
      * 用户id
    */    
    3:i64 memberCode;
}


/**
 * 下载红包明细
 */
struct ExportVouchersDetailInfo {
    /**excel url*/
    1:string exportUrl;
}

/**
 * 下载注册活动清单
 */
struct ExportRegisteredRuleInfoInfoRes {
	/**excel url*/
    1:string exportUrl;
}

/** 促销活动列表接 * */
struct FindAllPromotionActiveVO {	
	/** 活动基础信息 **/
	1:list<ActiveBaseRuleVo> activeBaseRuleVoList;
}

/** 详情页查询活动支持列表 **/
struct FindPromotionActiveByPageVO {
	/** 分页信息  **/
	1:Common.PageVo page;
	
	/** 活动基础信息 **/
	2:list<ActiveBaseRuleVo> activeBaseRuleVoList;
}

/**详情页查询红包券码明细列表**/
struct FindVouchersDetailInfoVO {
	/** 分页信息  **/
	1:Common.PageVo page;
	
	/**红包券码明细列表 **/
	2:list<VouchersCouponCodeVO> vouchersCouponCodelist;
}

/** 查询红包活动券码个数  **/
struct FindVouchersCountVO {
	/** 券码个数 **/
	1:i32 vouchersCount;
	/** ResultVo 结果 */
	2:Common.ResultVo resultVo;
}

/**临时红包券码明细列表**/
struct TemporaryVouchersDetailInfoVO {
	/** 临时券码活动ID **/
	1:i64 activeId;
	/** 券码总个数 **/
	2:i64 vouchersTotal;
	/**红包券码明细列表 **/
	3:list<VouchersCouponCodeVO> vouchersCouponCodelist;
}

/**红包券码明细 **/
struct VouchersCouponCodeVO {
	/**序号**/
	1:i16 numberCode;
	
	/**红包券码**/
	2:string vouchersIds;
	
	/**金额**/
	3:double vouchersMoney;
	
	/**余额**/
	4:double vouchersResMoney;
	
	/**上传时间**/
	5:i64 createTime;
	
	/** 状态 **/
	6:string status;
}

/**注册(首单)规则信息 **/
struct RegisteredRuleInfoVo{
	/** 营销活动标签关联 */
	1:ActiveTagRelationVo activeTagRelation; 	
	/** 营销活动基础规则 */
	2:ActiveBaseRuleVo activeBaseRule;
	/** 注册(首单)详细规则 */
	3:RegisteredDetailRuleVo registeredDetailRule;
}

/** 注册(首单)详细规则 */
struct RegisteredDetailRuleVo{
	/** 主键id */
	1:i64 id;
	/** 客户端id */
	2:string clientId;
	/** 活动id */
	3:string activeId;
	/** 触发方式0注册1收单交易 */
	4:bool triggerType;
	/** 奖励方式1满减2红包3实物 */
	5:string awardType;
	/** 满减的金额下限 award_type=1时有效 */
	6:optional i64 limitMoney;
	/** 满减的额度 award_type=1时有效 */
	7:optional i64 cutMoney;
	/** 满减活动总额 **/
	8:optional i64 totalMoney;
	/** 送红包的代金券规则id award_type=2时有效 */
	9:optional string vouchersActiveId;
	/** 送实物的商品id award_type=3时有效 */
	10:optional string productId;
	/** 送实物的商品数量 award_type=3时有效 */
	11:optional i32 productCount;
	/** 活动奖励人数 award_type=2/3时要检查红包个数/商品数量和奖励人数的约束 */
	12:i32 awardCount;
	/** 每次送银行积分 */
	13:i32 perBankIntegral;	
	/** 银行总积分 */
	14:i32 totalBankIntegral;	
	/** 每次送联盟积分 */
	15:i32 perUnionIntegral;	
	/** 联盟总积分 */
	16:i32 totalUnionIntegral;	
	/** 时间段限制的时间单位天或者日 */
	17:bool isTotalDay;
	/** 时间段限制的时间数量 */
	18:i32 totalDay;
	/** 时间段限制的次数 */
	19:i32 totalCount;
	/** 是否奖励推荐人0不奖励1奖励 */
	20:bool isAwardCre;
	/** 推荐人奖励方式0红包1实物 is_award_cre=1时有效 */
	21:optional bool creAwardType;
	/** 推荐人奖励红包的代金券规则id cre_award_type=0时有效 */
	22:optional string creVouchersActiveId;
	/** 推荐人奖励实物的商品id cre_award_type=1时有效 */
	23:optional string creProductId;
	/** 是否限制奖励推荐人次数0不限制1限制 is_award_cre=1时有效 */
	24:optional bool isLimitCreCount;
	/** 推荐人奖励次数 is_award_cre=1时有效 */
	25:optional i32 creAwardCount;
}

/** 注册(首单)活动规则信息 RegisteredRuleInfoVo 分页 */
struct RegisteredRuleInfoPageVoRes {
	1:Common.PageVo page;
	2:list<RegisteredRuleInfoVo> registeredRuleInfoVoList;
}

/** 查询注册(首单)活动规则信息 RegisteredRuleInfoVo 列表 结果 */
struct FindAllRegisteredRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果集 */
	2:list<RegisteredRuleInfoVo> registeredRuleInfoVoList;
}

/** 查询 注册(首单)活动规则信息 RegisteredRuleInfoVo 分页 结果 */
struct FindPageRegisteredRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 分页结果 */
	2:RegisteredRuleInfoPageVoRes registeredRuleInfoPageVoRes;
}

/** 查询 注册(首单)活动规则信息 RegisteredRuleInfoVo 单个 结果 */
struct FindRegisteredRuleInfoVoResultVo{
	/** ResultVo 结果 */
	1:Common.ResultVo resultVo;
	/** 结果 */
	2:RegisteredRuleInfoVo registeredRuleInfoVo;
}

/** 查询 注册(首单)参数 RegisteredHandselVo */
struct RegisteredHandselVo{
	1:Common.OriginVo originVo;
	/** 客户端 */
	2:string clientId;
	/** 会员编号 */ 
	3:i64 memberCode;
	/** 会员账号(联盟积分需要传此参数) */
	4:string loginId;
}