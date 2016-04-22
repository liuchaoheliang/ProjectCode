namespace java com.froad.thrift.service

include "Common.thrift"
include "ReportVo.thrift"

/**
 * 商户信息统计
 */
service ReportMerchantInfoService{
	
	/**
	 * 商户走势图
	 */
	ReportVo.MerchantTrendResVo getMerchantTrend(1:ReportVo.CommonParamVo commonParamVo);
	 
	 
    /**
 	 * 商户类型占比
     */
	ReportVo.TypePercentResVo getMerchantTypePercent(1:ReportVo.CommonParamVo commonParamVo);
	 
	 
     /**
 	 * 商户业务占比
 	 */
	ReportVo.TypePercentResVo getMerchantBussinessPercent(1:ReportVo.CommonParamVo commonParamVo);

	
    /**
	 * 商户信息统计详情列表
 	 */
	 ReportVo.ReportMerchantDetailResVo getMerchantDetailList(1:ReportVo.CommonParamVo commonParamVo);
	 
	 
	/**
	 * 商户业务统计信息
 	 */
	ReportVo.ReportMerchantBussinessResVo getMerchantBussinessList(1:ReportVo.CommonParamVo commonParamVo);

	 
	/**
	 * 商户信息统计详情列表(分页)
 	 */
	ReportVo.ReportMerchantDetailPageVo getMerchantDetailListByPage(1:Common.PageVo pageVo, 2:ReportVo.CommonParamVo commonParamVo);
	
	/**
	 * 商户门店信息导出
 	 */
	ReportVo.ReportMerchantOutletResVo getMerchantOutletList(1:ReportVo.CommonParamVo commonParamVo);	
}


/**
 * 商户签约人统计
 */
service ReportMerchantContractService{
	
	/**
 	 * 签约人商户数排行榜
 	 */
	ReportVo.MerchantContractRankResVo merchantContractRank(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
 	 * 商户新增类型占比
 	 */
	ReportVo.TypePercentResVo merchantTypeAddPercent(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
 	 * 签约人新增商户数排行
 	 */
	ReportVo.MerchantContractRankResVo merchantContractAddRank(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
 	 * 签约人商户统计详细列表
 	 */
	ReportVo.ReportMerchantContractDeatailResVo merchantContractDetailList(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
 	 * 签约人商户统计详细列表(分页)
 	 */
	ReportVo.ReportMerchantContractDeatailPageVo merchantContractDetailListByPage(1:Common.PageVo pageVo, 2:ReportVo.CommonParamVo commonParamVo);
}

/**
 * 社区银行用户统计
 */
service ReportUserService{
	/**
	 * 用户走势图
	 */
	ReportVo.UserTrendResVo userTrend(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
	 * 用户交易类型占比
	 */
	ReportVo.TypePercentResVo userTradeTypePercent(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
	 * 用户消费类型占比
	 */
	ReportVo.TypePercentResVo userConsumeTypePercent(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
	 * 用户统计详情列表
	 */
	ReportVo.UserSummaryResVo userSummaryList(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
	 * 用户统计详情列表(分页)
	 */
	ReportVo.UserSummaryPageVo userSummaryListByPage(1:Common.PageVo pageVo, 2:ReportVo.CommonParamVo commonParamVo);
	
	/**
	 * 用户交易支付详情列表
	 */
	ReportVo.UserTradeDetailResVo userTradeDetailList(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
	 * 用户交易信息列表
	 */
	ReportVo.UserTradeInfoResVo userTradeInfoList(1:ReportVo.CommonParamVo commonParamVo);
}


/**
 * 销售信息统计
 */
service ReportProductInfoService{

	/**
	 * 销售走势图
	 */
	 ReportVo.SaleTrendResVo getSaleTrend(1:ReportVo.CommonParamVo commonParamVo);
	 
   /**
    * 业务销售类型占比
 	*/
	 ReportVo.TypePercentResVo getSaleTypePercent(1:ReportVo.CommonParamVo commonParamVo);
	 
	/**
     * 支付方式占比
	 */
	 ReportVo.TypePercentResVo getPayTypePercent(1:ReportVo.CommonParamVo commonParamVo);
	 
	 /**
     * 业务销售统计详情列表
	 */
	ReportVo.SaleCountDetailResVo getSaleCountDetail(1:ReportVo.CommonParamVo commonParamVo);
	
	 /**
     * 业务销售统计详情列表(分页)
	 */
	ReportVo.SaleCountDetailPageVo getSaleCountDetailListByPage(1:Common.PageVo pageVo, 2:ReportVo.CommonParamVo commonParamVo);
	
	/**
     * 商户业务销售详情
	 */
	ReportVo.MerchantSaleDetailResVo getMerchantSaleDetail(1:ReportVo.CommonParamVo commonParamVo);
	
	/**
     * 业务类型销售统计详情列表
	 */
	ReportVo.BusinessSaleDetailResVo getBusinessSaleDetail(1:ReportVo.CommonParamVo commonParamVo)
	
}

service ReportProductService{
	
	/**
	 *商品销售走势
	 */
	ReportVo.ProductSaleTrendResVo getProductSaleTrend(1:ReportVo.CommonParamVo commonParamVo);	

	/**
	 *商品业务类型占比
	 */
	ReportVo.TypePercentResVo getProductTypePercent(1:ReportVo.CommonParamVo commonParamVo);
	
	
	/**
	 *商品类目占比
	 */
	ReportVo.TypePercentResVo getProductCategoryPercent(1:ReportVo.CommonParamVo commonParamVo);
	
	
	/**
	 *商品销售详情列表
	 */
	ReportVo.ProductSaleDetailResVo getProductSaleDetail(1:ReportVo.CommonParamVo commonParamVo);
	
	
	/**
	 *商品销售详情列表(分页)
	 */
	ReportVo.ProductSaleDetailPageVo getProductSaleDetailListByPage(1:Common.PageVo pageVo,2:ReportVo.CommonParamVo commonParamVo);

}
 
