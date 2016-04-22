namespace java com.froad.thrift.service

include "Common.thrift"

/** =================================== 公共部分 =================================== **/

/**
 * 公共参数
 */
struct CommonParamVo{
 	1: optional	i64 begDate;
 	2: optional i64 endDate;
 	3: required string clientId;
 	4: required string orgCode;
}

/**
 * 商户类型占比
 */
struct TypePercentVo{
	/** 类型 **/
	1: required string type;
	/** 类型名称 **/
	2: required string typeName;
	/** 类型占比 **/
	3: required double percent;
}

/**
 * 类型占比返回参数
 */
struct TypePercentResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 类型占比集合 **/
	2:required list<TypePercentVo> typePercentVos;
}

/** =================================== 社区银行商户信息统计 =================================== **/

/**
 * 商户走势图
 */
struct MerchantTrendVo{
	/** 周 **/
	1: required i32 week;
	/** 新增商户数 **/	
	2: required i32 addCount;
}

/**
 * 商户走势返回参数
 */
struct MerchantTrendResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 商户走势集合 **/
	2:required list<MerchantTrendVo> merchantTrendVo;
}

/**
 * 商户信息统计详情列表
 */
struct ReportMerchantDetailVo{
	/** 客户端号 **/
	1: required string clientId;
	/** 机构号 **/
	2: required string orgCode;
	/** 机构名称 **/
	3: required string orgName;
	/** 新增商户数 **/
	4: required i32 addCount;
	/** 动账商户数 **/
	5: required i32 changeCount;
	/** 结余商户数 **/
	6: required i32 totalCount;
	/** 商户占比 **/
	7: required double percent;
	/** 订单数 **/
	8: required i32 orderCount;
	/** 订单金额 **/
	9: required double totalAmount;
}

/**
 * 商户业务统计信息
 */
struct ReportMerchantBussinessVo{
	/** 客户端号 **/
	1: required	string clientId;
	/** 机构号 **/
	2: required string orgCode;
	/** 机构名称 **/
	3: required string orgName;
	/** 面对面商户新增数 **/
	4: required i32 faceAddCount;
	/** 面对面商户注销数 **/
	5: required i32 faceCancelCount;
	/** 面对面商户结余数 **/
	6: required i32 faceTotalCount;
	/** 名优特惠新增数 **/
	7: required i32 specialAddCount;
	/** 名优特惠注销数 **/
	8: required i32 specialCancelCount;
	/** 名优特惠结余数 **/
	9: required i32 specialTotalCount;
	/** 团购商户新增数 **/
	10: required i32 groupAddCount;
	/** 团购商户注销数 **/
	11: required i32 groupCancelCount;
	/** 团购商户结余数 **/
	12: required i32 groupTotalCount;
}


/**
 * 商户信息统计详情列表返回参数
 */
struct ReportMerchantDetailResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 商户信息统计详情列表集合 **/
	2:required list<ReportMerchantDetailVo> merchantDetailVos;
}

/**
 * 商户业务统计信息返回参数
 */
struct ReportMerchantBussinessResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 商户业务统计信息集合 **/
	2:required list<ReportMerchantBussinessVo> merchantBussinessVos;
}

/**
 * 商户信息统计详情列表分页返回参数
 */
struct ReportMerchantDetailPageVo{
	/** 分页 **/
	1:required Common.PageVo pageVo;
	/** 商户信息统计详情列表集合 **/
	2:required list<ReportMerchantDetailVo> merchantDetailVos;
}

/**
 * 商户信息统计
 */
service ReportMerchantInfoService{
	
	/**
	 * 商户走势图
	 */
	MerchantTrendResVo getMerchantTrend(1:string clientId, 2:string orgCode);
	 
	 
    /**
 	 * 商户类型占比
     */
	TypePercentResVo getMerchantTypePercent(1:CommonParamVo commonParamVo);
	 
	 
     /**
 	 * 商户业务占比
 	 */
	TypePercentResVo getMerchantBussinessPercent(1:CommonParamVo commonParamVo);

	
    /**
	 * 商户信息统计详情列表
 	 */
	 ReportMerchantDetailResVo getMerchantDetailList(1:CommonParamVo commonParamVo);
	 
	 
	/**
	 * 商户业务统计信息
 	 */
	ReportMerchantBussinessResVo getMerchantBussinessList(1:string clientId, 2:string orgCode);

	 
	/**
	 * 商户信息统计详情列表(分页)
 	 */
	ReportMerchantDetailPageVo getMerchantDetailListByPage(1:Common.PageVo pageVo, 2:CommonParamVo commonParamVo);
}


/** =================================== 社区银行商户签约人统计 =================================== **/

/**
 * 商户签约人排行
 */
struct MerchantContractRankVo{
	/** 商户签约人 **/
	1:required string constractStaff;
	/** 结余商户数 **/
	2:required i32 count;
	/** 排序 **/
	3:required i32 sort;
}

/**
 * 商户签约人排行返回参数
 */
struct MerchantContractRankResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 商户签约人排行集合 **/
	2:list<MerchantContractRankVo> merchantContractRankVos;
}

/**
 * 商户签约人统计详情
 */
struct ReportMerchantContractDeatailVo{
	/** 签约人 **/
	1:required string constractStaff;
	/** 新增商户数 **/
	2:required i32 addCount;
	/** 动账商户数 **/
	3:required i32 changeCount;
	/** 结余商户数 **/
	4:required i32 totalCount;
	/** 新增占比 **/
	5:required double addPercent;
	/** 订单数 **/
	6:required i32 orderCount;
	/** 订单金额 **/
	7:required double totalAmount;
}

/**
 * 商户签约人统计详情返回参数
 */
struct ReportMerchantContractDeatailResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 商户签约人排行集合 **/
	2:required list<ReportMerchantContractDeatailVo> merchantContractDeatailVos;
}


struct ReportMerchantContractDeatailPageVo{
	/** 分页 **/
	1:required Common.PageVo pageVo;
	/** 商户签约人排行集合 **/
	2:required list<ReportMerchantContractDeatailVo> merchantContractDeatailVos;
}


/**
 * 商户签约人统计
 */
service ReportMerchantContractService{
	
	/**
 	 * 签约人商户数排行榜
 	 */
	MerchantContractRankResVo merchantContractRank(1:string clientId, 2:string orgCode);
	
	/**
 	 * 商户新增类型占比
 	 */
	TypePercentResVo merchantTypeAddPercent(1:CommonParamVo commonParamVo);
	
	/**
 	 * 签约人新增商户数排行
 	 */
	MerchantContractRankResVo merchantContractAddRank(1:CommonParamVo commonParamVo);
	
	/**
 	 * 签约人商户统计详细列表
 	 */
	ReportMerchantContractDeatailResVo merchantContractDetailList(1:CommonParamVo commonParamVo);
	
	/**
 	 * 签约人商户统计详细列表(分页)
 	 */
	ReportMerchantContractDeatailPageVo merchantContractDetailListByPage(1:Common.PageVo pageVo, 2:CommonParamVo commonParamVo);
}



/** =================================== 社区银行用户统计 =================================== **/

/**
 * 用户走势图
 */
struct UserTrendVo{
	/** 周  **/
	1:required i32 week;
	/** 用户总数  **/
	2:required i32 userCount;
}

/**
 * 用户走势图返回信息
 */
struct UserTrendResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 用户走势集合 **/
	2:required list<UserTrendVo> userTrendVos;
}

/**
 * 用户统计详情信息
 */
struct UserSummaryVo{
	/** 机构号 **/
	1:required string orgCode;
	/** 机构名 **/
	2:required string orgName;
	/** 新增用户数 **/
	3:required i32 addCount;
	/** 动账用户数 **/
	4:required i32 changeCount;
	/** 结余用户数 **/
	5:required i32 totalCount;
	/** 新增商户占比 **/
	6:required double percent;
	/** 订单数 **/
	7:required i32 orderCount;
	/** 消费金额 **/
	8:required double totalAmount;
}

/**
 * 用户统计详情返回信息
 */
struct UserSummaryResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 用户统计详情列表 **/
	2:required list<UserSummaryVo> userSummaryVos;
}

/**
 * 用户统计详情分页返回信息
 */
struct UserSummaryPageVo{
	/** 分页 **/
	1:required Common.PageVo pageVo;
	/** 用户统计详情列表 **/
	2:required list<UserSummaryVo> userSummaryVos;
}

/**
 * 用户交易支付详情
 */
struct UserTradeDetailVo{
	/** 机构码 **/
	1:required string orgCode;
	/** 机构名称 **/
	2:required string orgName;
	/** 用户名称**/
	3:optional string userName;
	/** 注册手机号 **/
	4:optional string mobile;
	/** 注册日期 **/
	5:optional i64 regTime;
	/** 注册类型 **/
	6:optional string regType;
	/** 订单数 **/
	7:required i32 totalOrderNumber;
	/** 订单金额 **/
	8:required double totalOrderAmount;
	/** 积分支付笔数 **/
	9:required i32 totalPointNumber;
	/** 积分支付金额 **/
	10:required double totalPointAmount;
	/** 快捷支付笔数 **/
	11:required i32 totalQuickNumber;
	/** 快捷支付金额 **/
	12:required double totalQuickAmount;
	/** 贴膜卡支付笔数 **/
	13:required i32 totalFilmNumber;
	/** 贴膜卡支付金额 **/
	14:required double totalFilmAmount;
	/** 积分+贴膜卡支付笔数 **/
	15:required i32 totalPointFilmNumber;
	/** 积分+贴膜卡支付金额 **/
	16:required double totalPointFilmAmount;
	/** 积分+快捷支付笔数 **/
	17:required i32 totalPointQuickNumber;
	/** 积分+快捷支付金额 **/
	18:required double totalPointQuickAmount;
}

/**
 * 用户交易支付详情返回信息
 */
struct UserTradeDetailResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 用户统计详情列表 **/
	2:required list<UserTradeDetailVo> userTradeDetailVos;
}

/**
 * 用户交易信息
 */
struct UserTradeInfoVo{
	/** 用户名称**/
	1:optional string userName;
	/** 注册手机号 **/
	2:optional string mobile;
	/** 是否VIP **/
	3:required string isVip;
	/** 订单总数 **/
	4:required i32 orderCount;
	/** 订单总金额 **/
	5:required double totalAmount;
	/** 购买商品数 **/
	6:required i32 productNumber;
	/** 购买商品金额 **/
	7:required double productAmount;
	/** 退款金额 **/
	8:required double refundsAmount;
}

/**
 * 用户交易信息返回信息
 */
struct UserTradeInfoResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 用户交易信息列表 **/
	2:required list<UserTradeInfoVo> userTradeInfoVos;
}

/**
 * 社区银行用户统计
 */
service ReportUserService{
	/**
	 * 用户走势图
	 */
	UserTrendResVo userTrend(1:string clientId, 2:string orgCode);
	
	/**
	 * 用户交易类型占比
	 */
	TypePercentResVo userTradeTypePercent(1:CommonParamVo commonParamVo);
	
	/**
	 * 用户消费类型占比
	 */
	TypePercentResVo userConsumeTypePercent(1:CommonParamVo commonParamVo);
	
	/**
	 * 用户统计详情列表
	 */
	UserSummaryResVo userSummaryList(1:CommonParamVo commonParamVo);
	
	/**
	 * 用户统计详情列表(分页)
	 */
	UserSummaryPageVo userSummaryListByPage(1:Common.PageVo pageVo, 2:CommonParamVo commonParamVo);
	
	/**
	 * 用户交易支付详情列表
	 */
	UserTradeDetailResVo userTradeDetailList(1:CommonParamVo commonParamVo);
	
	/**
	 * 用户交易信息列表
	 */
	UserTradeInfoResVo userTradeInfoList(1:CommonParamVo commonParamVo);
}


/** =================================== 社区银行业务销售统计 =================================== **/

/**
 * 销售走势图
 */
struct SaleTrendVo{
	/** 周 **/
	1: optional i32 week;
	/** 商品总金额 **/	
	2: optional double saleProductAmount;
}

struct SaleTrendResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 销售走势集合 **/
	2:required list<SaleTrendVo> saleTrendVos;
}

/**
 *业务销售统计详情列表
 */
struct SaleCountDetailVo{
	/** 机构号 **/
	1: optional string orgCode;
	/** 机构名 **/
	2: optional string orgName;
	/** 订单数 **/
	3: optional i32 orderCount;
	/** 订单总金额 **/
	4: optional double totalAmount;
	/** 商品销售数量 **/
	5: optional i32 productSaleCount;
	/** 商品销售金额 **/
	6: optional double productSaleAmount;
	/** 购买人次 **/
	7: optional i32 buyCount;
	/** 平均消费金额 **/
	8: optional double averAmount;
}

struct SaleCountDetailResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 业务销售统计详情列表集合 **/
	2:required list<SaleCountDetailVo> saleCountDetailVos;
}


struct SaleCountDetailPageVo{
	/** 分页 **/
	1:required Common.PageVo pageVo;
	/** 业务销售统计详情列表集合 **/
	2:required list<SaleCountDetailVo> saleCountDetailVos;
}

/**
 *商户业务销售详情
 */
struct MerchantSaleDetailVo{
	/** 商户号 **/
	1: optional string merchantId;
	/** 商户名称 **/
	2: optional string merchantName;
	/** 商户类型 **/
	3: optional string merchantType;
	/** 机构号 **/
	4: optional string orgCode;
	/** 银行名称 **/
	5: optional string orgName;
	/** 团购订单数 **/
	6: optional i32 groupOrderCount;
	/** 面对面订单数 **/
	7: optional i32 faceOrderCount;
	/** 精品预售订单数 **/
	8: optional i32 specialOrderCount;
	/** 订单数 **/
	9: optional i32 orderCount;
	/** 订单总金额 **/
	10: optional double orderAmount;
	/** 退款总金额 **/
	11: optional double refundAmount;
}

struct MerchantSaleDetailResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 商户业务销售集合 **/
	2:required list<MerchantSaleDetailVo> merchantSaleDetailVos;
}

/**
 *业务类型销售统计详情列表
 */
struct BusinessSaleDetailVo{
	/** 业务类型 **/
	1: optional string type;
	/** 机构号 **/
	2: optional string orgCode;
	/** 机构名 **/
	3: optional string orgName;
	/** 订单数 **/
	4: optional i32 orderCount;
	/** 总订单金额 **/
	5: optional double orderAmount;
	/** 现金总额 **/
	6: optional double cashAmount;
	/** 银行积分总额 **/
	7: optional double bankPointAmount;
	/** 联盟积分总额 **/
	8: optional double fftPointAmount;
	/** 商品销售数量 **/
	9: optional i32 productCount;
	/** 商品销售金额 **/
	10: optional double productAmount;
	/** 购买人次 **/
	11: optional i32 buyCount;
}

struct BusinessSaleDetailResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 业务类型销售统计详情集合 **/
	2:required list<BusinessSaleDetailVo> businessSaleDetailVos;
}

/**
 * 销售信息统计
 */
service ReportProductInfoService{

	/**
	 * 销售走势图
	 */
	 SaleTrendResVo getSaleTrend(1:CommonParamVo commonParamVo);
	 
   /**
    * 业务销售类型占比
 	*/
	 TypePercentResVo getSaleTypePercent(1:CommonParamVo commonParamVo);
	 
	/**
     * 支付方式占比
	 */
	 TypePercentResVo getPayTypePercent(1:CommonParamVo commonParamVo);
	 
	 /**
     * 业务销售统计详情列表
	 */
	SaleCountDetailResVo getSaleCountDetail(1:CommonParamVo commonParamVo);
	
	 /**
     * 业务销售统计详情列表(分页)
	 */
	SaleCountDetailPageVo getSaleCountDetailListByPage(1:Common.PageVo pageVo, 2:CommonParamVo commonParamVo);
	
	/**
     * 商户业务销售详情
	 */
	MerchantSaleDetailResVo getMerchantSaleDetail(1:CommonParamVo commonParamVo);
	
	/**
     * 业务类型销售统计详情列表
	 */
	BusinessSaleDetailResVo getBusinessSaleDetail(1:CommonParamVo commonParamVo)
	
}


/** =================================== 社区银行商品销售统计 =================================== **/

/**
 *商品销售走势
 */
struct ProductSaleTrendVo{
	/** 周 **/
	1: optional i32 week;
	/** 商品总金额 **/	
	2: optional double saleProductAmount;
}

struct ProductSaleTrendResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 销售走势集合 **/
	2:required list<ProductSaleTrendVo> productSaleTrendVos;
}


/**
 *商品销售详情列表
 */
struct ProductSaleDetailVo{
	/** 机构号 **/
	1: optional string orgCode;
	/** 机构名称 **/
	2: optional string orgName;
	/** 新增商品数 **/
	3: optional i32 addProductCount;
	/** 商品总数 **/
	4: optional i32 productCount;
	/** 商品销售数量 **/
	5: optional i32 productSaleCount;
	/** 商品销售金额 **/
	6: optional double productSaleAmount;
	/** 退款总金额 **/
	7: optional double refundAmount;
}

struct ProductSaleDetailResVo{
	/** 返回结果 **/
	1:required Common.ResultVo resultVo;
	/** 商品销售详情集合 **/
	2:required list<ProductSaleDetailVo> productSaleDetailVos;
}

struct ProductSaleDetailPageVo{
	/** 分页 **/
	1:required Common.PageVo pageVo;
	/** 商品销售详情集合 **/
	2:required list<ProductSaleDetailVo> productSaleDetailVos;
}


service ReportProductService{
	
	/**
	 *商品销售走势
	 */
	ProductSaleTrendResVo getProductSaleTrend(1:CommonParamVo commonParamVo);	

	/**
	 *商品业务类型占比
	 */
	TypePercentResVo getProductTypePercent(1:CommonParamVo commonParamVo);
	
	
	/**
	 *商品类目占比
	 */
	TypePercentResVo getProductCategoryPercent(1:CommonParamVo commonParamVo);
	
	
	/**
	 *商品销售详情列表
	 */
	ProductSaleDetailResVo getProductSaleDetail(1:CommonParamVo commonParamVo);
	
	
	/**
	 *商品销售详情列表(分页)
	 */
	ProductSaleDetailPageVo getProductSaleDetailListByPage(1:Common.PageVo pageVo,2:CommonParamVo commonParamVo);

}
 
