﻿namespace java com.froad.thrift.vo.order

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"


/**
 * 商品信息
 */
struct ProductInfoVo {
    1:string productName,         // 商品名称
    2:string productImage,        // 商品图片
    3:i32 quantity,               // 商品数量
    4:double money,               // 商品单价金额
    5:double subTotalMoney,       // 金额小计
    6:bool isOutletComment,       // 是否评价
    7:string takeCode,            // 提货码（团购交易使用）
    8:string deliverState,        // 配送或者自提状态(0.未发货，1.已发货，2.已收货，3.未提货，4.已提货,5.部分提货)
    9:string settlementStatus,	  // 结算状态（0.未结算，2.结算成功）--只要结算一个则结算成功并带结算数量
    10:string settlementNumber,	  // 结算数量--实际结算数量
    // -------以下为银行管理使用内容--------
    /**预售-开始时间-预售为提货时间段， 团购为有效时间段*/ 
    11:i64 startTime,
    /**预售-结束时间*/
    12:i64 endTime,
    /**提货状态-1.未消费，2.已消费，3.部分消费*/
    13:string consumeStatus,
    /** 运费-新增 */
    14:double deliveryMoney,
    /** 商品ID */
    15:string productId,
    /** 退款数量 */
    16:i32 refundNumber,
    /** 剩余数量 */
    17:i32 surplusNumber,
    /** 配送方式 */
    18:string deliveryOption,

    /** 验码时间 */
    19:i64 checkCodeTime,

    /** 消费门店 */
    20:string outletName,

    /** 操作员 */
    21:i64 merchantUserId,
    /** 实付款 */
    22:double realTotalMoney,
    /** 退款原因 */
    23:string refundReason,
    /** 已提货数量 */
    24:i32 takeNumber,
    /** 提货时间 */
    25:i64 takeTime,
    /** 退款申请时间 */
    26:i64 refundApplyTime,
    /** 退款成功时间 */
    27:i64 refundSuccessTime,
    /**退款状态*/
    28:string refundState,
    /**退款中数量*/
    29:i32 refundingNumber,
    30:i32 vipQuantity,               // 商品VIP价数量
    31:double vipMoney,               // 商品VIP价
}

/**商品信息-按mongo表类型*/
struct ProductVo {
	/**商品编号*/
	1:string productId,
	/**商品名称*/
	2:string productName,
	/**商品单价*/
	3:double money,
	/**商品VIP价格*/
	4:double vipMoney,
	/**普通购买数量*/
	5:i32 quantity,
	/**VIP购买数量*/
	6:i32 vipQuantity,
	/**赠送积分*/
	7:double points,
	/**配送或者自提方式*/
	8:string deliveryOption,
	/**运费*/
	9:double deliveryMoney,	
	
	/**增加有效期*/
	/**团购预售（券的有效期-开始时间）*/
	10:i64 startTime,
	/**团购预售（券的有效期-结束时间）*/
	11:i64 endTime,
	/**小计*/
	12:double subTotalMoney,
	/**退款数量*/
	13:i32 refundNumber,
	/**剩余数量*/
	14:i32 surplusNumber,
	/**配送或者自提状态*/
	15:string deliveryState,
	/**预售和团购的消费状态 1：未消费， 2. 已消费， 3. 部分消费*/
	16:i32 consumeStatus,
	/**预售返回的列表中的商品所属法人行社用到*/
	17:string orgNames,
	/**退款中数量*/
	18:i32 refundingNumber,
	/**结算状态（0.未结算，2.结算成功）--只要结算一个则结算成功并带结算数量*/
	19:string settlementStatus,

}

/**
 * 订单列表商户手机端显示
 */
struct QueryOrderByMerchantPhoneVo {
    1:string subOrderId;             // 子订单ID（面对面则为大订单号）
    2:list<string> productImages;    // 商品默认图片
    3:i32 quantity;                  // 数量
    4:string orderStatus;            // 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
    5:string memberName;             // 会员姓名
    6:i64 createTime;                // 创建时间
    7:double subTotalMoney;          // 订单总金额

    /** 大订单号*/
    8:string orderId;

    /** 退款状态：1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款（2015-5-25 新增） */
    9:string refundState;

    /** 发货状态： 0.未发货，1.已发货，2.已收货，3.未提货，4.已提货 */
    10:string deliveryStatus;

    /** 手机号码 */
    11:string phone;

    /** 支付时间 */
    12:i64 paymentTime;

    /** 消费总额 */
    13:double totalPrice;

    /** 实际付款 */
    14:double realPrice;

    /** 操作员ID */
    15:i64 merchantUserId;

    /** 操作员名 */
    16:string merchantUserName;
    
    /** 结算状态 0:未结算 1:结算中  2:结算成功 3:结算失败 */
    17:string settlementStatus;

    /** 门店ID */
    18:string outletId;

    /** 门店名称 */
    19:string outletName;
}

/**
 * 商户管理需要显示的子订单内容
 */
struct QueryOrderByMerchantManageVo {
    1:string orderId,		   // 订单号（发货情况下使用）
    2:string subOrderId,           // 子订单号
    3:i64 createTime,              // 创建时间
    4:double subTotalMoney,        // 总金额
    //5:string productName,        // 商品名（多个以逗号隔开）
    /**商品信息*/
    5:list<ProductInfoVo> productInfoVo,
    6:i32 quantity,                // 数量
    7:string type,                 // 业务类型（0. 面对面，1.团购，2.预售，3.名优特惠，4.积分礼品兑换）
    8:string orderStatus,          // 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
    9:string settleState,          // 结算状态（不使用产品需求中批注：结算可能出现多次结算的情况）
    10:string deliveryType, 	   // 配送方式 0、送货上门，1.网点自提，2.配送或自提
    11:string deliveryStatus,      // 0.未发货，1.已发货，2.已收货，3.未提货，4.已提货
    // 新增 2015-05-11 11：45：00
    /**结算状态 0-未结算， 2：已结算*/
    12:string settlementStatus,
    /**结算数量*/
    13:i32 settlementNumber,
    
    /**退款状态--2015-5-25 新增 TODO： 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款 */
    14:string refundState,

    /**收货人姓名（名优特惠使用）*/
    15:string consignee,

    /**收货人地址（名优特惠使用）*/
    16:string address,	

    /**收货人手机号（名优特惠使用）*/
    17:string phone,

    /**收货人地址所属地区ID*/
    18:i64 areaId,
   
    /**商户操作员ID*/
    19:i64 merchantUserId;
}

/**
 * 银行管理需要显示的子订单信息
 */
struct QueryOrderByBankManageVo {
    1:string orderId,              // 订单号
    2:string subOrderId,           // 子订单号
    3:string paymentMethod,        // 支付方式
    4:string orderStatus,          // 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
    5:double subTotalMoney,        // 订单金额
    6:double point,                // 积分
    7:double cash,                 // 现金
    8:string createSoure,          // 购买来源
    9:string merchantName,         // 商户名
    10:string productName,         // 商品名
    11:string deliveryState,	   // 配送状态（只有名优特惠有值） 0.未发货，1. 已发货， 2. 已收货
    12:i64 createTime,             // 创建时间（可能需要使用）
    13:string orgCode,		   // 机构代码（可能需要使用）
    /** 退款状态--2015-5-6 新增 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款 */
    14:string refundState,
    /** 商品数量 */
    15:i32 quantity,

    /** 所属机构 */
    16:string orgNames,	

    17:list<ProductVo> productList,

    /** 收货人信息|提货人信息|消费人信息 */
    18:DeliverInfoVo deliverInfoVo,
    /**配送方式 自提、配送*/ 
    19:string deliveryOption,
    /**结算状态（0.未结算，2.结算成功）--只要结算一个则结算成功并带结算数量*/
    20:string settlementStatus,
    /**商户操作员ID*/
    21:i64 merchantUserId;
    /**提货状态-1.未消费，2.已消费，3.部分消费*/
    22:string consumeStatus,
    /** 结算时间 */
    23:i64 settleTime,
    /** 商户用户名 */
    24:string merchantUserName,
    /** 订单金额-预售列表获取这个值----把自提商品和配送商品分开了 */
    25:double subTotalMoneyPresell,
    /**门店ID*/
    26:string outletId,
}

//--------------------商户H5------------------------------

/**
 * 查询订单列表请求参数
 */
struct QueryOrderByMerchantPhoneVoReq {
    1:required string clientId,          // 客户端ID
    2:required string merchantId,		// 商户号
    3:required string type,           // 订单类型（订单类型，0. 面对面，1.团购，2.预售，3.名优特惠，4.积分礼品兑换）
    4:string status,		 		// 1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成
    5:optional string outletId,       // 所属门店
    6:optional string deliveryStatus, // 发货状态0.未发货 1.已发货，2.已收货
    7:required Common.PageVo page,    // 分页信息
}

/**
 * 查询订单列表返回参数
 */
struct QueryOrderByMerchantPhoneVoRes {
    1:list<QueryOrderByMerchantPhoneVo> orderVo;
    2:Common.PageVo page,            // 分页信息
    3:Common.ResultVo resultVo,      // 结果

    /** 订单条数 */
    4:i32 orderCount;

    /** 收入总金额 */
    5:double totalIncome;
}
/**
 * 请求详细请求参数
 */
struct GetOrderDetailByMerchantPhoneVoReq {
    1:required string subOrderId,      // 子订单ID(如果面对面则为大订单号)
    2:required string type,            // 订单类型， 0. 面对面，1.团购，2.预售，3.名优特惠，4.积分礼品兑换
    /**客户端号*/	
    3:string clientId,
}
/**
 * 订单详细响应参数detailed
 */
struct GetOrderDetailByMerchantPhoneVoRes {
    1:string memberName,                // 会员名
    2:i64 memberCode,                   // 会员ID
    3:i64 createTime,                   // 创建时间
    4:string settleState,               // 结算状态（0-未结算 1-结算中 2-已结算 3-结算失败, 4-部分结算）
    5:string outletId,                  // 所属门店
    6:bool isOutletComment,             // 是否评价，团购在商品中，只有面对面有值
    7:string orderStatus,          		// 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
    8:double totalPrice,				// 订单总金额
    9:i32 quantity;						// 商品数量
    10:i64 paymentTime;						// 支付时间
    11:list<ProductInfoVo> productInfos, // 商品相关信息
    12:Common.ResultVo resultVo,         // 结果
    
    // 新增发货状态和物流信息
    /** 收货人姓名（名优特惠使用）*/
    13:string consignee,				
    /** 收货地址（名优特惠使用）*/
    14:string address,				
    /**收货人手机号码（名优特惠使用）*/
    15:string phone,
    /**物流ID*/			
    16:string deliveryCorpId, 
    /**物流名称*/
    17:string deliveryCorpName, 
    /**物流单号*/
    18:string trackingNo, 
    /**发货时间*/
    19:i64 shippingTime, 
    /**地址ID收货地址使用*/
    20:i64 areaId,
   	/**发货状态*/
    21:string deliveryStatus, // 发货状态0.未发货 1.已发货，2.已收货
    /**商户ID--面对面新增*/
    22:string merchantId,
    /**商户名--面对面新增*/
    23:string merchantName,
    
    /**退款状态--2015-5-25 新增 TODO： 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款 */
    24:string refundState,

    /**子订单号*/
    25:string subOrderId;

    /**积分抵扣*/
    26:double cutPoint;

    /**实付款*/
    27:double totalCash;

    /**积分抵扣金额*/
    28:double pointMoney;

    /**优惠金额 */
    29:double cutMoney;

    /**消费总金额*/
    30:double consumeMoney;

    /**门店折扣金额*/
    31:double discountMoney;

    /**门店名称*/
    32:string outletName;

    /**门店LOGO*/
    33:string outletImg;

    /**操作员ID*/
    34:i64 merchantUserId;

    /**操作员名*/
    35:string merchantUserName;

    
}
//-------------------商户管理平台-------------------------------
/**
 * 商户管理-订单查询_请求参数
 */
struct QueryOrderByMerchantManageVoReq {
    1:required string clientId,                // 客户端Id
    2:required string merchantId,              // 商户ID
    3:optional string outletId			// 门店ID
    4:i64 startTime,               // 开始时间
    5:i64 endTime,                 // 结束时间
    6:string type,                 // 业务类型（0. 面对面，1.团购，2.预售，3.名优特惠，4.积分礼品兑换）
    7:string orderStatus,          // 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
    8:string operType,             // 操作类型（1.查询，2.下载）
    9:Common.PageVo page,          // 分页数据
    10:string deliveryStatus,      // 发货状态（0.未发货，1.已发货，2.已收货）
}



/**
 * 商户管理-订单列表_返回参数List
 */
struct QueryOrderByMerchantManageVoRes {
	1:list<QueryOrderByMerchantManageVo> orderVo;
    2:Common.PageVo pageVo,       // 分页数据
    3:Common.ResultVo resultVo,      // 结果
}

/**
 * 商户管理-订单详细_请求参数
 */
struct GetOrderDetailByMerchantManageVoReq {
    1:required string subOrderId,    // 子订单号
    2:required string type,           // 交易类型(0. 面对面，1.团购，2.预售，3.名优特惠，4.积分礼品兑换)
    /**客户端号*/	
	3:string clientId,
	/**配送方式 自提、配送*/ 
    4:string deliveryOption,
}
/**
 * 商户管理-订单详细_返回参数
 */
struct GetOrderDetailByMerchantManageVoRes {
    /**创建时间*/
    1:i64 createTime,
    /**订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)*/
    2:string orderStatus, 
    /**积分抵扣*/
    3:double pointDiscount, 
    /**应付款总计*/
    4:double totalMoney,
    /**门店ID--面对面使用*/
    5:string outletId,
    /**结算状态-（0.未结算，2.结算成功）--面对面使用*/
    6:string settlementStatus,
    /**收货人姓名（名优特惠使用）*/
    7:string consignee,	
    /**收货地址（名优特惠使用）*/
    8:string address,
    /**收货人手机号码（名优特惠使用）*/
    9:string phone,
    /**物流ID*/
    10:string deliveryCorpId,
    /**物流名称*/
    11:string deliveryCorpName,
    /**物流单号*/
    12:string trackingNo,	 
    /**创建时间*/
    13:i64 shippingTime,	
    /** 支付时间（付款时间）*/
    14:i64 paymentTime,
    /**商品列表*/
    15:list<ProductInfoVo> productInfo,
    /**结果*/
    16:Common.ResultVo resultVo,
    /**地址ID收货地址使用*/
    17:i64 areaId,    
    /**退款状态--2015-5-25 新增 TODO： 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款 */
    18:string refundState,
    /**商户用户ID */
    19:i64 merchantUserId;
    /**商户ID*/
    20:string merchantId,
    /**商户名*/
    21:string merchantName,
    /** 验码时间（团购验码时间）*/
    22:i64 ticketTime,
    /** 订单关闭类型：1:用户关闭，2:系统关闭，3:退款关闭*/
    23:string closeType,
    /** 收货人信息|提货人信息|消费人信息 */
    24:DeliverInfoVo deliverInfoVo,
    /** 所属机构 */
    25:string orgNames,
    /** 订单关闭原因*/
    26:string closeReason,
    /**结算状态:已结算、未结算 新增*/
    27:string settleState,
    /** 提货时间 */
    28:i64 takeTime,
    /** 提货码 */
    29:string takeCode, 
    /**配送方式 自提、配送*/ 
    30:string deliveryOption,
    /** 退款申请时间 */
    31:i64 refundApplyTime,
    /** 退款成功时间 */
    32:i64 refundSuccessTime,
    /** 确认收货时间 */
    33:i64 confirmReceiveTime,
    /** 结算时间 */
    34:i64 settleTime,
    /** 验码有效期截止时间 */
    35:i64 expireTime,

    /**实付款*/
    36:double totalCash;

    /**积分抵扣金额*/
    37:double pointMoney;

    /**优惠金额 */
    38:double cutMoney;

    /**消费总金额*/
    39:double consumeMoney;

    /**门店折扣金额*/
    40:double discountMoney;

}

//-------------------银行管理平台-------------------------------
/**
*银行管理平台-精品商城订单管理-查询订单列表-请求参数
*/
struct QueryBoutiqueOrderByBankManageVoReq {
	//	客户端ID
	1:string clientId;
	//	子订单Id
	2:string subOrderId;
	//	订单状态(1.订单创建;2.订单用户关闭;3.订单系统关闭;4.订单支付中;5. 订单支付失败;6.订单支付完成)
	3:string orderStatus;
	//（8个字符，简称模糊匹配全称
	4:string providerName;
	// 下单开始时间
	5:i64 startTime;
	// 下单结束时间           
    6:i64 endTime;
    // 分页数据
    7:Common.PageVo page;       
}

/**
 * 银行管理平台-精品商城订单管理_查询订单列表_返回参数
 */
struct QueryBoutiqueOrderByBankManageVoRes {
    1:list<QueryBoutiqueOrderByBankManageVo> ordervo;
    // 分页数据
    2:Common.PageVo pageVo;  
    // 操作结果  
    3:Common.ResultVo resultVo;    
}

/**
 * 银行管理需要显示的精品商城子订单信息
 */
struct QueryBoutiqueOrderByBankManageVo {
	// 子订单号
    1:string subOrderId; 
    //支付方式
    2:string paymentMethod;
    // 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)       
    3:string orderStatus;         
   	//供应商名称
   	4:string providerName;
   	//商品名称（多个名称用逗号隔开）
   	5:string productNames;
   	//订单金额
   	6:double subTotalMoney;
   	//下单时间
   	7:i64 createTime; 
   	
    8:list<ProductVo> productList;
   	
}


/**
 * 银行管理平台-订单管理_查询订单列表_请求参数
 */
struct QueryOrderByBankManageVoReq {
    1:required string clientId,  // 客户端
    2:required string orgCode,   // 所属商户号：（预售（直接传进来的机构号的所有子集机构的）、团购（传进来的机构号子集机构发展的商户的）、名优特惠（同团购）、积分兑换（同预售）、面对面（同团购））
    3:i64 startTime,             // 下单开始时间
    4:i64 endTime,               // 下单结束时间
    /**订单状态(1. 订单创建，2.订单用户关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)*/
    5:list<string> orderStatus,  
    6:string orderId,            // 大订单号
    7:string type,               // 交易类型（0. 面对面，1.团购，2.预售，3.名优特惠，4.积分礼品兑换）
    8:string merchantName,       // 所属商户（8个字符，简称模糊匹配全称）
    9:Common.PageVo page,        // 分页数据
    10:string subOrderId,        // 子订单号
    /**配送方式 自提、配送*/ 
    11:string deliveryOption,
    /**当前登录人的所属机构号*/
    12:string myOrgCode,
    /**当前登录人机构和查询条件所选的机构相交集合机构下的所有提货网点机构号 (预售和积分兑换需要用到这个来查询条件匹配)*/
    13:list<string> orgCodes, 
}



/**
 * 银行管理平台-订单管理_查询订单列表_返回参数
 */
struct QueryOrderByBankManageVoRes {
    1:list<QueryOrderByBankManageVo> ordervo,
    2:Common.PageVo pageVo,       // 分页数据
    3:Common.ResultVo resultVo,      // 结果
}
/**
 * 银行管理平台-订单管理_查询订单详细_请求参数
 */
struct GetOrderDetailByBankManageVoReq {
    1:required string subOrderId,     // 子订单号（面对面情况下，直接传大订单号）        
    2:required string type,           // 交易类型0. 面对面，1.团购，2.预售，3.名优特惠，4.积分礼品兑换
    /**客户端号*/	
	3:string clientId,
	/**配送方式 自提、配送*/ 
    4:string deliveryOption,
}

/**
 * 配送|提货信息详情
 */
struct DeliverInfoVo {
     /** 提货|收货人|消费人 */
     1:string consignee;     

     /** 提货|收货地址 */
     2:string address;       

     /** 接收券的手机号 */
     3:string phone;         

     /** 收货人信息ID */
     4:string recvId;
}

/**
 * 银行管理平台-订单管理_查询订单详细_返回参数
 */
struct GetOrderDetailByBankManageVoRes {
    1:i64 createTime,                    // 创建时间（购买时间）
    2:string orderStatus,                // 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
    3:double pointDiscount,              // 积分抵扣
    4:double subTotalMoney,              // 应付款总计
    5:string businessType,               // 业务类型
    6:string outletId,                   // 所属门店
    7:string outletName,                 // 门店名 
    8:list<ProductInfoVo> productInfos,  // 商品列表信息
    9:Common.ResultVo resultVo,          // 结果
    10:string deliveryCorpName, 	 // 物流名称
    11:string trackingNo,		 // 物流单号
    12:i64 shippingTime,		 // 发货时间
    /**增加退款状态，2015-05-06 新增 TODO 待补充类型*/
    13:string refundState,
    /**商户ID*/
    14:string merchantId,
    /**商户名*/
    15:string merchantName,

    /** 支付时间（付款时间）*/
    16:i64 paymentTime,

    /** 验码时间（团购验码时间）*/
    17:i64 ticketTime,

    /** 订单关闭类型：1:用户关闭，2:系统关闭，3:退款关闭*/
    18:string closeType,

    /** 收货人信息|提货人信息|消费人信息 */
    19:DeliverInfoVo deliverInfoVo,

    /** 所属机构 */
    20:string orgNames,
    /** 订单关闭原因*/
    21:string closeReason,
    /**结算状态:已结算、未结算 新增*/
    22:string settleState,
    /** 提货时间 */
    23:i64 takeTime,
    /** 提货码 */
    24:string takeCode, 
    /**配送方式 自提、配送*/ 
    25:string deliveryOption,
    /** 退款申请时间 */
    26:i64 refundApplyTime,
    /** 退款成功时间 */
    27:i64 refundSuccessTime,
    /** 确认收货时间 */
    28:i64 confirmReceiveTime,
    /** 结算时间 */
    29:i64 settleTime,
    /** 验码有效期截止时间 */
    30:i64 expireTime,
    /**商户用户ID */
    31:i64 merchantUserId;
    /**实付款 */
    32:double realTotalMoney,
    /**订单号 */
    33:string orderId,
    /**子订单号 */
    34:string subOrderId,     
    /** 已消费数量、已提货数量 */
    35:i32 takeNumber,
    /**操作员*/
    36:string merchantUserName,
    /** 订单商品金额-预售详情自提商品总价 */
    37:double subTotalMoneyZiti,
    /** 订单商品金额-预售列表配送商品总价 */
    38:double subTotalMoneyPeisong,
    /** 运费-新增 */
    39:double deliveryMoney,
    /** 子订单总优惠金额 */
    40:double totalCutMoney;
    /** 总实付金额 */
    41:double totalCash;

    /**积分抵扣金额*/
    42:double pointMoney;

    /**消费总金额*/
    43:double consumeMoney;

    /**门店折扣金额*/
    44:double discountMoney;
    
    /**手机号*/
    45:string phone;
    
    /**发货状态*/
    46:string deliveryState;

    /**会员名*/
    47:string memberName;
}
//----------------------BOSS订单查询----------------------------

struct OrderVo {
	/**用户编号*/
	1:i64 memberCode,
	/**订单状态*/
	2:string orderStatus,
	/**所属客户端*/
	3:string clientId,
	/**支付方式*/
	4:string paymentMethod,
	/**订单来源*/
	5:string createSource,
	/**支付时间*/
	6:i64 paymentTime,
	/**收银交易操作人*/
	7:i64 merchantUserId,
	/**总金额*/
	8:double totalPrice,
	/**积分类型*/
	9:i32 pointType
	/**积分金额*/
	10:double points
	/**现金金额*/
	11:double realPrice,
	/**积分兑换比例*/
	12:string pointRate,
	/**赠送积分数*/
	13:double givePoints
	/**备注*/
	14:string remark,
	/**是否面对面-面对面没有详细内容*/
	15:i32 isQrcode,
	/**订单号*/
	16:string orderId,
	/**创建时间*/
	17:i64 createTime;
}

struct OrderListByBossReq {
	/**订单编号*/
	1:string orderId,
	/**支付编号*/
	2:string paymentId,
	/**用户编号*/
	3:i64 memberCode,
	/**提货手机号*/
	4:string phone,
	/**订单来源*/
	5:string createSource,
	/**订单状态*/
	6:string orderStatus,
	/**支付方式*/
	7:string paymentMethod,
	/**所属客户端*/
	8:string clientId,
	/**开始时间*/
	9:i64 startTime,
	/**结束时间*/
    10:i64 endTime,
    /**分页*/
    11:Common.PageVo pageVo,
}

struct OrderListByBossRes {
	/**订单数据*/
	1:list<OrderVo> orders,	
	/**查询结果*/
	2:Common.ResultVo resultVo,		
	/**分页数据*/
	3:Common.PageVo pageVo,
}

/**
 * 查询商品送积分明细-请求
 */
struct QueryGivePointsProductByBossReq {
        /**所属客户端*/
	1:string clientId,

	/**订单编号*/
	2:string orderId,

	/**支付方式*/
	3:string paymentMethod,

        /**订单状态*/
	4:string orderStatus,

	/**开始时间*/
	5:i64 startTime,

	/**结束时间*/
        6:i64 endTime,

        /**分页*/
        7:Common.PageVo pageVo,
}

/**
 * 查询商品送积分明细-响应
 */
struct QueryGivePointsProductByBossRes {
	/**订单数据*/
	1:list<GivePointsOrderVo> givePointsOrderList,
	
	/**查询结果*/
	2:Common.ResultVo resultVo,	
	
	/**分页数据*/
	3:Common.PageVo pageVo,
}

/**
 * 查询商品送积分明细-订单信息
 */
struct GivePointsOrderVo{

	/**订单编号*/
	1:string orderId,

	/**子订单号*/
	2:string subOrderId,

	/**支付方式(对应枚举类PaymentMethod)*/
	3:string paymentMethod,

        /**订单状态(对应枚举类OrderStatus)*/
	4:string orderStatus,

        /**订单子状态，即退款状态(对应枚举类SubOrderRefundState)：{"1":"未退款"，"2":"退款中","3":"退款完成","4":"部分退款"}*/
	5:string refundState,

	/**下单时间*/
	6:string createTime,

        /**订单总金额*/
	7:double totalPrice,

        /**子订单总送积分值*/
	8:double totalGivePoints,

	/**商品信息*/
        9:list<GivePointsProductVo> productList,

        /**客户端号*/
	10:string clientId,
}

/**
 * 查询商品送积分明细-商品信息
 */
struct GivePointsProductVo{

	/**商品ID*/
	1:string productId,

	/**商品名称*/
	2:string productName,	

        /**送积分值*/
	3:double givePoints,

        /**送分状态:{"0":"赠送失败"，"1":"已赠送"，"2":"未赠送"，"3":"退分失败"，"4":"退分成功"}*/
	4:string giveState,

        /**送分时间*/
	5:string paymentTime,
}

struct SubOrderVo {
	/**商户名*/
	1:string merchantName,
	/**子订单类型*/
	2:string type,
	/**配送状态-名优特惠有值*/
	3:string deliveryState,
	/**商品*/
	4:list<ProductVo> products,
	/**商户ID*/
	5:string merchantId,
	
	/**接口通用后加上名优特惠的物流信息**/
	6:string deliveryCorpId, 		// 物流ID
    	7:string deliveryCorpName, 	// 物流名称
    	8:string trackingNo,			// 物流单号
    	9:i64 shippingTime,			// 创建时间
    	10:string subOrderId,		// 子订单编号
    	/**订单金额*/
    	11:double totalPrice,
    	/**订单子状态*/
    	12:string refundState,
}

struct SubOrderByBossReq{
	/**订单号*/
	1:required string orderId,
	/**是否面对面标识1：面对面0.不为面对面*/
	2:required i32 isQrcode,
	/**客户端号*/	
	3:string clientId,
}

struct SubOrderByBossRes {
	/**子订单列表*/
	1:list<SubOrderVo> subOrders,

	/**查询结果*/
	2:Common.ResultVo resultVo,
}

/**
 * 查询配送商品所有相关订单的发货人信息-请求
 */
struct QueryRecvInfoForProductByBossReq{
	/**客户端ID*/
	1:optional string clientId,

	/**商品预售开始时间*/
	2:optional i64 presellStartTime,

        /**商品预售结束时间*/
	3:optional i64 presellEndTime,

	/**下单开始时间*/
	4:optional i64 orderStartTime,

        /**下单结束时间*/
	5:optional i64 orderEndTime,

        /**所属一级机构号*/
	6:optional string fOrgCode,

        /**所属二级机构号*/
	7:optional string sOrgCode,

        /**分页*/
        8:Common.PageVo pageVo,
}

/**
 * 查询配送商品所有相关订单的发货人信息-实体
 */
struct RecvInfoForProductVo{

	/**订单ID*/
	1:string orderId,

        /**子订单ID*/
	2:string subOrderId,

        /**所属一级机构*/
	3:string fOrgName,

        /**所属二级机构*/
        4:string sOrgName,

        /**商品名称*/
	5:string productName,

        /**下单时间*/
	6:string createTime,

        /**购买数量*/
	7:i32 buyCount,

        /**退款数量*/
	8:i32 refundCount,

        /**实际订购数量*/
	9:i32 realCount,

        /**收货人*/
	10:string reciver,

        /**联系电话*/
	11:string phone,

        /**收货地址*/
	12:string address,
}

/**
 * 查询配送商品所有相关订单的发货人信息-响应
 */
struct QueryRecvInfoForProductByBossRes {
	/**子订单列表*/
	1:list<RecvInfoForProductVo> recvList,

	/**查询结果*/
	2:Common.ResultVo resultVo,

        /**分页数据*/
	3:Common.PageVo pageVo,
}

/**
 * 大订单显示全部子订单内容
 */
struct OrderDetailRes {
	/**订单创建时间*/
	1:i64 createTime,
	/**订单状态*/
	2:string orderStatus,
	/**积分抵扣*/
	3:double pointDiscount,
	/**金额总计*/
	4:double totalMoney
	/**子订单列表*/
	5:list<SubOrderVo> subOrderVo,
	/**响应结果*/
	6:Common.ResultVo resultVo,
}

/**
 * 商户/门店结算情况查询请求
 */
struct QueryMerchantSettlementReq {
	/**客户端ID*/
	1:string clientId,
	/**商户ID*/
	2:string merchantId,
	/**门店ID*/
	3:string outletId,
        /**门店操作员ID/门店管理员ID,如果是超级管理员不用传*/
	4:string userId,
}

/**
 * 商户/门店结算情况查询响应
 */
struct QueryMerchantSettlementRes {
	/**今日团购总数*/
	1:i64 groupTodayTotalCount,
	/**今日团购总金额（含未消费）*/
	2:double groupTodayTotalMoney,
	/**今日团购已提货笔数*/
	3:i64 groupTodayTakeCount,
	/**今日团购已结算金额*/
	4:double groupTodaySettleMoney,	
	/**今日面对面总笔数*/
	5:i64 f2fTodayTotalCount,
	/**今日面对面总金额*/
	6:double f2fTodayTotalMoney,	
	/**本月团购总数*/
	7:i64 groupMonthTotalCount,
	/**本月团购总金额（含未消费）*/
	8:double groupMonthTotalMoney,
	/**本月团购已提货笔数*/
	9:i64 groupMonthTakeCount,
	/**本月团购已结算金额*/
	10:double groupMonthSettleMoney,	
	/**本月面对面总笔数*/
	11:i64 f2fMonthTotalCount,
	/**本月面对面总金额*/
	12:double f2fMonthTotalMoney,	
	/**本月结算总笔数（团购+面对面）*/
	13:i64 monthTotalSettleCount,
	/**本月结算总金额（团购+面对面）*/
	14:double monthTotalSettleMoney,
	/**响应结果*/
	15:Common.ResultVo resultVo,
}