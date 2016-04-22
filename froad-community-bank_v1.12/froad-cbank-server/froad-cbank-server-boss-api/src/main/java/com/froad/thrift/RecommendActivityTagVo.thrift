namespace java com.froad.thrift.vo.recommendactivitytag

include "BizMonitor.thrift"
include "Common.thrift"

struct RecommendActivityTagVo {
	/** id **/
	1:optional i64 id;
	/** 客户端ID **/
 	2:optional string clientId;
	/** 活动名称 **/
 	3:optional string activityName;
	/** 活动编号 **/
 	4:optional string activityNo;
	/** 操作人 **/
 	5:optional string operator;
	/** 描述**/
 	6:optional string activityDesc;
	/** 状态: 启1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5 **/
	7:optional string status;
	/** 活动类型: 商户活动1; 门店活动2; 商品活动3 **/
 	8:optional string activityType;
	/** 活动logo_url **/
 	9:optional string logoUrl;
	/** 创建时间 **/
 	10:optional i64 createTime;
	/** 更新时间 **/
 	11:optional i64 updateTime;
}

struct RecommendActivityTagPageVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 分页 **/
	2:required Common.PageVo pageVo;
	/** 分页查询结果集 **/
	3:required list<RecommendActivityTagVo> recommendvos;
}

struct ExportRelateActivityTagReqVo {
	/** 活动标签id **/
	1:required i64 activityId;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:required string activityNo;
}

//==========================================商户Vo==========================================

struct MerchantWeightActivityTagVo {
	/** id **/
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
	/** 营业执照 **/
 	11:optional string license;
	/** 操作人 **/
 	12:optional string operator;
 	/** 所在地区 **/
 	13:optional string areaName;
}

struct MerchantActivityTagDetailReqVo {
	/** 活动标签id **/
	1:required i64 activityId;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 当前操作人 **/
	4:required string operator;
}

struct MerchantActivityTagDetailResVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 推荐活动信息 **/
	2:required RecommendActivityTagVo recommendVo;
}

struct MerchantWeightActivityTagPageReqVo {
	/** 活动标签id **/
	1:optional i64 activityId;
	/** 客户端ID **/
	2:optional string clientId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 分页 **/
	4:required Common.PageVo pageVo;
}

struct MerchantWeightActivityTagPageVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 分页 **/
	2:required Common.PageVo pageVo;
	/** 关联商户信息 **/
	3:required list<MerchantWeightActivityTagVo> relateMerchants;
}

struct EnableMerchantActivityReqVo {
	/** 活动标签id **/
	1:required i64 id;
	/** 客户端ID **/
	2:required string clientId;
	/** 状态: 启1; 禁用2 **/
	3:required string status;
	/** 当前操作人 **/
	4:required string operator;
}

struct AdjustMerchantWeightReqVo {
	/** 商户关联id **/
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

struct DeleteRelateMerchantReqVo {
	/** 商户关联id **/
	1:required i64 id;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:required string activityNo;
	/** 当前操作人 **/
	4:required string operator;
}


struct RelateMerchantActivityVo {
	/** 营业执照 **/
	1:optional string license;
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

struct InputRelateMerchantActivityVo {
	/** 序号 **/
	1:optional i64 id;
	/** 商户名称 **/
 	2:optional string merchantName;
	/** 商户id **/
 	3:optional string merchantId;
	/** 权重 **/
	4:optional string weight;
	/** 活动编号 **/
	5:optional string activityNo;
}

struct InputRelateMerchantActivityReqVo {
	/** 导入文件数据 **/
	1:optional list<InputRelateMerchantActivityVo> vos;
	/** 活动标签id **/
	2:optional i64 activityId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 客户端号 **/
	4:optional string clientId;
	/** 当前操作员 **/
	5:optional string operator;
}

struct MerchantNameResVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 商户名称 **/
	2:string merchantName;
}


//==========================================商品Vo==========================================

struct ProductActivityTagDetailReqVo {
	/** 活动标签id **/
	1:required i64 activityId;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 当前操作人 **/
	4:required string operator;
}

struct ProductActivityTagDetailResVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 推荐活动信息 **/
	2:required RecommendActivityTagVo recommendVo;
}

struct ProductWeightActivityTagVo {
	/** id **/
	1: optional i64 id;
	/** 客户端ID **/
 	2: optional string clientId;
	/** 活动id **/
	3: optional i64 activityId;
	/** 活动编号 **/
	4: optional string activityNo;
	/** 商户ID/门店ID/商品ID **/
 	5: optional string elementId;
	/** 权重 **/
 	6: optional string weight;
	/** 活动类型: 商户活动1; 门店活动2; 商品活动3 **/
 	7: optional string activityType;
	/** 创建时间 **/
 	8: optional i64 createTime;
	/** 更新时间 **/
 	9: optional i64 updateTime;
	/** 商品名称 **/
 	10: optional string productName;
	/** 所属商户Id **/
 	11: optional string merchantId;
	/** 所属商户名称 **/
 	12: optional string merchantName;
	/** 操作人 **/
 	13: optional string operator;
}

struct ProductWeightActivityTagPageReqVo {
	/** 活动标签id **/
	1:optional i64 activityId;
	/** 客户端ID **/
	2:optional string clientId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 分页 **/
	4:required Common.PageVo pageVo;
}

struct ProductWeightActivityTagPageVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 分页 **/
	2:required Common.PageVo pageVo;
	/** 关联商品信息 **/
	3:required list<ProductWeightActivityTagVo> relateProducts;
}

struct EnableProductActivityReqVo {
	/** 活动标签id **/
	1:required i64 id;
	/** 客户端ID **/
	2:required string clientId;
	/** 状态: 启1; 禁用2 **/
	3:required string status;
	/** 当前操作人 **/
	4:required string operator;
}

struct AdjustProductWeightReqVo {
	/** 商品关联id **/
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

struct DeleteRelateProductReqVo {
	/** 商品关联id **/
	1:required i64 id;
	/** 客户端ID **/
	2:required string clientId;
	/** 活动编号 **/
	3:required string activityNo;
	/** 当前操作人 **/
	4:required string operator;
}

struct RelateProductActivityVo {
	/** 商品id **/
	1:optional string productId;
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

struct InputRelateProductActivityVo {
	/** 序号 **/
	1: optional i64 id;
	/** 商品名称 **/
 	2: optional string productName;
	/** 商品id **/
 	3: optional string productId;
	/** 权重 **/
	4:optional string weight;
	/** 活动编号 **/
	5:optional string activityNo;
}

struct InputRelateProductActivityReqVo {
	/** 导入文件数据 **/
	1:optional list<InputRelateProductActivityVo> vos;
	/** 活动标签id **/
	2:optional i64 activityId;
	/** 活动编号 **/
	3:optional string activityNo;
	/** 客户端号 **/
	4:optional string clientId;
	/** 当前操作员 **/
	5:optional string operator;
}

struct ProductNameResVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 商品名称 **/
	2:string productName;
}


