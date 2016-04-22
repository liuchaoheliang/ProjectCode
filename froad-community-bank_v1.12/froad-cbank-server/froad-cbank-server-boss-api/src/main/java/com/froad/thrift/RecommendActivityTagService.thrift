namespace java com.froad.thrift.service

include "BizMonitor.thrift"
include "Common.thrift"
include "RecommendActivityTagVo.thrift"


service MerchantActivityTagService extends BizMonitor.BizMonitorService {

	/**
	 * 推荐活动列表分页查询
	 */
	RecommendActivityTagVo.RecommendActivityTagPageVo findMerchantTagByPage(1:RecommendActivityTagVo.RecommendActivityTagVo vo, 2:Common.PageVo pageVo);
	
	
	/**
	 * 推荐活动列表详情查询(operator:当前操作员用户名)
	 */
	RecommendActivityTagVo.MerchantActivityTagDetailResVo findMerchantTagDetail(1:RecommendActivityTagVo.MerchantActivityTagDetailReqVo reqVo);
	
	
	/**
	 * 关联商户信息分页查询
	 */
	RecommendActivityTagVo.MerchantWeightActivityTagPageVo findRelateMerchantInfoByPage(1:RecommendActivityTagVo.MerchantWeightActivityTagPageReqVo pageReqVo);
	
	
	/**
	 * 启用/禁用商户推荐活动标签
	 */
	Common.ResultVo enableMerchantRecommendActivityTag(1:RecommendActivityTagVo.EnableMerchantActivityReqVo reqVo);
	
	
	/**
	 * 调整关联商户权重
	 */
	Common.ResultVo adjustMerchantWeight(1:RecommendActivityTagVo.AdjustMerchantWeightReqVo reqVo);

	
	/**
	 * 删除关联商户
	 */
	Common.ResultVo deleteRelateMerchant(1:RecommendActivityTagVo.DeleteRelateMerchantReqVo reqVo);
	
	
	/**
	 * 添加商户推荐活动标签信息
	 */
	Common.ResultVo addMerchantActivityTag(1:RecommendActivityTagVo.RecommendActivityTagVo recommendVo);
	
	
	/**
	 * 更新商户推荐活动标签信息
	 */
	Common.ResultVo updateMerchantActivityTag(1:RecommendActivityTagVo.RecommendActivityTagVo recommendVo);
	
	
	/**
	 * 关联商户
	 */
	Common.ResultVo relateMerchantInfo(1:RecommendActivityTagVo.RelateMerchantActivityVo vo);
	
	
	/**
	 * 批量导入关联商户
	 */
	Common.ResultVo inputRelateMerchantInfo(1:RecommendActivityTagVo.InputRelateMerchantActivityReqVo reqVo);
	
	
	/**
	 * 根据营业执照查询商户名称
	 */
	RecommendActivityTagVo.MerchantNameResVo queryMerchantNameByLicense(1:string clientId, 2:string license);
	
	
	/**
	 * 导出关联商户标签信息
	 */
	Common.ExportResultRes exportMerchantRelateActivityTag(1:RecommendActivityTagVo.ExportRelateActivityTagReqVo reqVo);

}


service ProductActivityTagService extends BizMonitor.BizMonitorService {

	/**
	 * 推荐活动列表分页查询
	 */
	RecommendActivityTagVo.RecommendActivityTagPageVo findProductTagByPage(1:RecommendActivityTagVo.RecommendActivityTagVo vo, 2:Common.PageVo pageVo);
	
	
	/**
	 * 推荐活动列表详情查询(operator:当前操作员用户名)
	 */
	RecommendActivityTagVo.ProductActivityTagDetailResVo findProductTagDetail(1:RecommendActivityTagVo.ProductActivityTagDetailReqVo reqVo);
	
	
	/**
	 * 关联商品信息分页查询
	 */
	RecommendActivityTagVo.ProductWeightActivityTagPageVo findRelateProductInfoByPage(1:RecommendActivityTagVo.ProductWeightActivityTagPageReqVo pageReqVo);
	
	
	/**
	 * 启用/禁用商品推荐活动标签
	 */
	Common.ResultVo enableProductRecommendActivityTag(1:RecommendActivityTagVo.EnableProductActivityReqVo reqVo);
	
	
	/**
	 * 调整关联商品权重
	 */
	Common.ResultVo adjustProductWeight(1:RecommendActivityTagVo.AdjustProductWeightReqVo reqVo);

	
	/**
	 * 删除关联商品
	 */
	Common.ResultVo deleteRelateProduct(1:RecommendActivityTagVo.DeleteRelateProductReqVo reqVo);
	
	
	/**
	 * 添加商品推荐活动标签信息
	 */
	Common.ResultVo addProductActivityTag(1:RecommendActivityTagVo.RecommendActivityTagVo recommendVo);
	
	
	/**
	 * 更新商品推荐活动标签信息
	 */
	Common.ResultVo updateProductActivityTag(1:RecommendActivityTagVo.RecommendActivityTagVo recommendVo);
	
	
	/**
	 * 关联商品
	 */
	Common.ResultVo relateProductInfo(1:RecommendActivityTagVo.RelateProductActivityVo vo);
	
	
	/**
	 * 批量导入关联商品
	 */
	Common.ResultVo inputRelateProductInfo(1:RecommendActivityTagVo.InputRelateProductActivityReqVo reqVo);
	
	
	/**
	 * 根据商品id查询商品名称
	 */
	RecommendActivityTagVo.ProductNameResVo queryProductNameByProductId(1:string clientId, 2:string productId);
	
	
	/**
	 * 导出关联商品标签信息
	 */
	Common.ExportResultRes exportProductRelateActivityTag(1:RecommendActivityTagVo.ExportRelateActivityTagReqVo reqVo);
}

