namespace java com.froad.thrift.vo.outletActivity

include "BizMonitor.thrift"
include "Common.thrift"
/**
 *推荐活动标签信息Vo
 */
struct RecommendActivityTagVo {
	/** 活动标签id **/
	1: optional i64 id;
	/** 客户端ID **/
 	2: optional string clientId;
	/** 活动名称 **/
 	3: optional string activityName;
	/** 活动编号 **/
 	4: optional string activityNo;
	/** 操作人 **/
 	5: optional string operator;
	/** 描述**/
 	6: optional string activityDesc;
	/** 状态: 启1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5 **/
	7: optional string status;
	/** 活动类型: 商户活动1; 门店活动2; 商品活动3 **/
 	8: optional string activityType;
	/** 活动logo_url **/
 	9: optional string logoUrl;
	/** 创建时间 **/
 	10: optional i64 createTime;
	/** 更新时间 **/
 	11: optional i64 updateTime;
}

struct OutletWeightActivityTagVo {
	/** 权重id **/
	1:optional i64 id;
	/** 客户端ID **/
 	2:optional string clientId;
	/** 活动标签id **/
	3:optional i64 activityId;
	/** 活动编号 **/
	4:optional string activityNo;
	/** 商户ID/门店ID/商品ID **/
 	5:optional string elementId;
	/** 权重 **/
 	6:optional string weight;
	/** 活动类型: 商户活动1; 门店活动2; 商品活动3 **/
 	7:optional string activityType;
	/** 创建时间 **/
 	8:optional i64 createTime;
	/** 更新时间 **/
 	9:optional i64 updateTime;
	/** 商户名称 **/
 	10:optional string merchantName;
 	/** 商户Id **/
 	11:optional string merchantId;
	/** 门店名称 **/
 	12:optional string outletName;
	/** 操作人 **/
 	13:optional string operator;
 	/** 所在地区 **/
 	14:optional string areaName;
}

/**
 *推荐活动列表详情查询Vo
 */
struct RecommendActivityTagPageVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 分页 **/
	2:required Common.PageVo pageVo;
	/** 分页查询结果集 **/
	3:required list<RecommendActivityTagVo> recommendvos;
}

/**
 *推荐活动列表详情查询ReqVo
 */
struct OutletActivityTagDetailReqVo {
	/** 活动标签id **/
	1:required i64 activityId;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 当前操作人 **/
	4:required string operator;
}

/**
 *推荐活动列表详情查询ResVo
 */
struct OutletActivityTagDetailResVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 推荐活动信息 **/
	2:required RecommendActivityTagVo recommendVo;
}

/**
 * 关联门店信息分页查询ReqVo
 */
struct OutletWeightActivityTagPageReqVo {
	/** 活动标签id **/
	1:optional i64 activityId;
	/** 客户端ID **/
	2:optional string clientId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 分页 **/
	4:required Common.PageVo pageVo;
}

/**
 * 关联门店信息分页查询PageVo
 */
struct OutletWeightActivityTagPageVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 分页 **/
	2:required Common.PageVo pageVo;
	/** 关联门店信息 **/
	3:required list<OutletWeightActivityTagVo> relateOutlets;
}

/**
 * 启用/禁用门店推荐活动标签ReqVo
 */
struct EnableOutletActivityReqVo {
	/** 活动标签id **/
	1:required i64 id;
	/** 客户端ID **/
	2:required string clientId;
	/** 状态: 启1; 禁用2 **/
	3:required string status;
	/** 当前操作人 **/
	4:required string operator;
}

/**
 * 调整关联门店权重ReqVo
 */
struct AdjustOutletWeightReqVo {
	/** 权重id **/
	1:required i64 id;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:required string activityNo;
	/** 权重 **/
	4:required string weight;
	/** 当前操作人 **/
	5:required string operator
}

/**
 * 删除关联门店ReqVo
 */
struct DeleteRelateOutletReqVo {
	/** 门店关联id **/
	1:required i64 id;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:required string activityNo;
	/** 当前操作人 **/
	4:required string operator;
}

/**
 * 关联门店
 */
struct RelateOutletActivityVo {
	/** 门店id **/
	1:optional string outletId;
	/** 权重 **/
	2:optional string weight;
	/** 活动标签id **/
	3:optional i64 activityId;
	/** 活动编号 **/
	4:optional string activityNo;
	/** 客户端号 **/
	5:optional string clientId;
	/** 当前操作员 **/
	6:optional string operator;
}


struct InputRelateOutletActivityVo {
	/** 序号 **/
	1:optional i64 id;
	/** 门店名称 **/
 	2:optional string outletName;
	/** 门店id **/
 	3:optional string outletId;
	/** 权重 **/
	4:optional string weight;
	/** 活动编号 **/
	5:optional string activityNo;
}
/**
 * 批量导入关联门店
 */
struct InputRelateOutletActivityReqVo {
	/** 导入文件数据 **/
	1:optional list<InputRelateOutletActivityVo> vos;
	/** 活动标签id **/
	2:optional i64 activityId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 客户端号 **/
	4:optional string clientId;
	/** 当前操作员 **/
	5:optional string operator;
}

/**
 * 根据门店id查询门店名称和商户名称
 */
struct OutletNameAndMerchantNameResVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 门店名称 **/
	2:string outletName;
	/** 商户名称 **/
	3:string merchantName;
}


struct ExportRelateActivityTagReqVo {
	/** 活动标签id **/
	1:required i64 activityId;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:required string activityNo;
}

service OutletActivityService extends BizMonitor.BizMonitorService {

	/**
	 * 推荐活动分页列表查询
	 */
	RecommendActivityTagPageVo findOutletTagByPage(1:RecommendActivityTagVo vo, 2:Common.PageVo pageVo);
	
	/**
	 *推荐活动列表详情查询(operator:当前操作员用户名)
	 */
	OutletActivityTagDetailResVo findOutletTagDetail(1:OutletActivityTagDetailReqVo reqVo);
	
	/**
	 * 关联门店信息分页查询
	 */
	OutletWeightActivityTagPageVo findRelateOutletInfoByPage(1:OutletWeightActivityTagPageReqVo pageReqVo);
	
	/**
	 * 启用/禁用门店推荐活动标签
	 */
	Common.ResultVo enableOutletRecommendActivityTag(1:EnableOutletActivityReqVo reqVo);
	
	/**
	 * 调整关联门店权重
	 */
	Common.ResultVo adjustOutletWeight(1:AdjustOutletWeightReqVo reqVo);
	
	/**
	 * 删除关联门店
	 */
	Common.ResultVo deleteRelateOutlet(1:DeleteRelateOutletReqVo reqVo);
	
	/**
	 * 添加门店推荐活动标签信息
	 */
	Common.ResultVo addOutletActivityTag(1:RecommendActivityTagVo recommendVo);
	
	/**
	 * 更新门店推荐活动标签信息
	 */
	Common.ResultVo updateOutletActivityTag(1:RecommendActivityTagVo recommendVo);
	
	/**
	 * 关联门店
	 */
	Common.ResultVo relateOutletInfo(1:RelateOutletActivityVo vo);
	
	/**
	 * 批量导入关联门店
	 */
	Common.ResultVo inputRelateOutletInfo(1:InputRelateOutletActivityReqVo reqVo);
	
	/**
	 * 根据门店id查询门店名称和商户名称
	 */
	OutletNameAndMerchantNameResVo queryOutletNameAndMerchantNameByOutletId(1:string clientId, 2:string outletId);
	
	/**
	 * 导出关联门店标签信息
	 */
	Common.ExportResultRes exportOutletRelateActivityTag(1:ExportRelateActivityTagReqVo reqVo);
	
}
