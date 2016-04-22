include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo


/**
 * 商品添加的响应
 */
struct AddProductVoRes {
	/** 响应基础信息 */
	1:Common.ResultVo result,
	/** 商品id */
	2:string productId
}

/**
 * 商品分类添加的响应
 */
struct AddProductCategoryVoRes {
	/** 响应基础信息 */
	1:Common.ResultVo result,
	/** 商品分类id */
	2:i64 id
}

/**商品分类接口*/
/**
 * 商品分类
 */
struct ProductCategoryVo {
	/** id */
    1:i64 id,   
    /** 客户端id */
    2:string clientId,    
    /** name */
    3:string name,   
    /** 树路径 空格间隔 */
    4:string treePath,    
    /** 父节点id */
    5:i64 parentId,
    /** 小图标 */
    6:string icoUrl,
    /**  排序*/
    7:i16 orderValue,
    /**  是否有子分类*/
    8:bool isHaveChild
}

/**
 * 商品分类分页
 */
struct ProductCategoryPageVo{
	1:Common.PageVo page,
	2:list<ProductCategoryVo> categoryVoList
}

/**
 * 商品收藏
 */
struct StoreProductInfoVo{

	/** 商品id */
	1: optional string productId,
	/** 商品名称 */
	2: optional string productName,
	/** 商品图片 */
	3: optional string productImage,
	/** 商户id*/
	4: optional string merchantId
}

/**
 * 商品收藏分页的响应结果
 */
struct StoreProductInfoPageVoRes {
	
	/** 分页基础信息 */
	1:Common.PageVo page,
	/** 商品收藏信息列表 */
	2:list<StoreProductInfoVo> storeProductInfoVoList
}


/**商品接口*/
/**
 * ProductBuyLimitVo
 */
struct ProductBuyLimitVo {
    /** 最大购买数量 */ 
    1:i32 max,  
    /** vip最大购买数量 */
    2:i32 maxVip,   
    /** 贴膜卡最大购买数量 */
    3:i32 maxCard,   
    /** 限制开始时间 */
    4:i64 startTime,    
    /** 限制结束时间 */
    5:i64 endTime    
}

/**
 * ProductImageVo
 */
struct ProductImageVo {
    /** 图片标题 */
    1:string title,    
    /** 图片原图地址 */
    2:string source,    
    /** 图片大图地址 */
    3:string large,    
    /** 图片中图地址 */
    4:string medium,    
    /** 图片小图地址 */
    5:string thumbnail    
}

/**
 * ProductOutletVo
 */
struct ProductOutletVo {
    /** id */
    1:i64 id,    
    /** 客户端ID */
    2:string clientId,    
    /** 商户ID */
    3:string merchantId,    
    /** 门店ID */
    4:string outletId,    
    /** 地区ID */
    5:i64 areaId,    
    /** 店名 */
    6:string outletName, 
    /** 地址 */   
    7:string address,
    /** 电话 */ 
    8:string phone    
}
/**
 * ProductSearchVo
 */
struct ProductSearchVo {
    /** 客户端ID */
    1:string clientId,    
    /** 机构号 */
    2:string orgCode,    
    /** 审核状态("0待审核",1审核通过,2审核不通过,3未提交) */
    3:string auditState,    
    /** 商户名称 */
    4:string merchantName,    
    /** 商品名称 */
    5:string productName, 
    /** 上下架状态 */   
    6:string isMarketable,
    /** 审核开始时间 */ 
    7:optional i64 auditStartTime,
    /** 审核结束时间 */ 
    8:optional i64 auditEndTime,
    /**积分开始值 */ 
    9:optional i32 pointStart,
    /**积分截止值 */ 
    10:optional i32 pointEnd        
}
/**
 * ProductVo
 */
struct ProductVo {
    /** 客户端id */
    1:string clientId,    
    /** 商户ID */
    2:string merchantId,    
    /** 商品对应商户所属机构编号 */
    3:string orgCode,    
    /** 商品id */
    4:string productId, 
    /** 是否上架 0未上架,1已上架,2已下架,3已删除*/   
    5:string isMarketable,    
    /** 上架时间 */
    6:i64 rackTime,    
    /** createTime */
    7:i64 createTime,  
    /** 商品类型 */  
    8:string type,    
    /** 0送货上门,1网点自提,2配送或自提 */
    9:string deliveryOption,    
    /** 商品名 */
    10:string name,    
    /** 商品全名 */
    11:string fullName,   
    /** 销售价 */ 
    12:double price,    
    /** 成本价 */
    13:double cost,    
    /** 市场价 */
    14:double marketPrice,    
    /** 商品重量 */
    15:string weight, 
    /** 重量单位 */   
    16:string weightUnit,    
    /** 商品库存数量 */
    17:i32 store,    
    /** 销售数量 */   
    18:i32 sellCount,    
    /** 排序(1-10) */
    19:i32 orderValue,    
    /** 是否为精品 */
    20:bool isBest,    
    /** 是否限购 */
    21:bool isLimit,    
    /** 评分 */
    22:i32 point,    
    /** 简介 */
    23:string briefIntroduction,    
    /** 介绍 */
    24:string introduction,    
    /** 商品须知 */
    25:string buyKnow,    
    /** 商品审核状态 ("0待审核",1审核通过,2审核不通过,3未提交)*/
    26:string auditState,   
    /** 待审核机构编号 */ 
    27:string auditOrgCode,    
    /** 审核步骤 */
    28:string auditStage,    
    /** 复核人 */
    29:string reviewStaff,    
    /** 审核人 */
    30:string auditStaff,    
    /** 用户收藏数 */
    31:i32 storeCount,    
    
    /** 团购或预售有效期开始 */
    32:i64 startTime,    
    /** 团购或预售有效期结束 */
    33:i64 endTime,    
    /** 运费 */
    34:double deliveryMoney,  
    
    /** 团购或预售券有效起始日 */
    35:i64 expireStartTime,   
    /** 团购或预售券有效结束日 */ 
    36:i64 expireEndTime,    
    /** 团购或预售真实购买数量 */
    37:i32 trueBuyerNumber,    
    /** 团购或预售虚拟购买数量 */
    38:i32 virtualBuyerNumber,    
    
    /**预售信息 */
    /** 供货商 */
    39:string productSupplier,    
    /** 自提商品每家门店最大支持数量 */
    40:i32 maxPerOutlet,    
    /** 提货-开始 */
    41:i64 deliveryStartTime,    
    /** 提货-结束 */
    42:i64 deliveryEndTime,    
    /** 是否成功成团 */
    43:bool clusterState,    
    /** 成团类型 */
    44:bool clusterType,    
    
    /** 下架时间 */
    45:i64 downTime,
    /** 审核时间 */
    46:i64 auditTime,
    /** 审核备注 */
	47:string auditComment,
	/** 是否秒杀 0非秒杀,1秒杀,2秒杀未上架 */
    48:string isSeckill,
    /** 商户名称 */
    49:string merchantName,
    /** 起始审核机构编号 */ 
    50:string auditStartOrgCode,   
    /** 最终审核机编号 */
    51:string auditEndOrgCode,
    /** 机构名称 */  
    52:string orgName,
    /** 商户电话 */  
    53:string phone,
    /** 签约到期时间 */  
    54:i64 contractEndtime,
    /** 服务器时间 */
    55:i64 serverTime,
    /** 商品秒杀库存数量 */
    56:i32 secStore,   
    /** 秒杀开始时间 */
    57:i64 secStartTime,    
    /** 秒杀结束时间 */
    58:i64 secEndTime,    
    /** 是否参与送积分活动 */
    59:bool isPoint,    
    /** 是否参与VIP规则活动 */
    60:bool isVip,   
    /** VIP价 */ 
    61:double vipPrice,
    /** 二维码地址 */ 
    62:string codeUrl,
    
    /** 客户端名称 */
    63:string clientName,
    /** 是否推荐 */
    64:bool isRecommend,
    /** 是否新品 */
    65:bool isNew,
    /** 是否热销 */
    66:bool isHot,
    /** 是否批量发货 */
    67:bool isBatchDelivery,
    /** 预计发货时间 */
    68:i64 deliveryTime, 
    /** 售后说明*/
    69:string afterShop,
    /** 商品id */
    70:i64 id
}

struct ProductInfoVo{
    /** 商品新加修改管理平台 */
	1:string platType,
    /** 商品基础信息 */
	2:ProductVo product,
	/** 商品分类 */
	3:ProductCategoryVo productCategory,
	/** 商品限购信息 */
	4:ProductBuyLimitVo buyLimit,
	/** 商品图片列表 */
	5:list<ProductImageVo> image,
	/** 商品门店列表 */
	6:list<ProductOutletVo> outlet,
	/** 商品活动 */
	7:list<ProductActivitiesVo> activities,
	/** 商品对应网店所属的机构代码列表 */
	8:list<string> orgCodes,
	/** 是否审核 */
	9:string isAudit
}

/**
 * 修改商品一些确定信息
 */
struct ProductBaseVo {
    /** 客户端id */
    1:string clientId,    
    /** 商品id */
    2:string productId, 
    /** 商品库存数量 */
    3:optional i32 store, 
    /** 团购或预售有效期开始 */
    4:optional i64 startTime,    
    /** 团购或预售有效期结束 */
    5:optional i64 endTime,    
    /** 团购或预售券有效起始日 */
    6:optional i64 expireStartTime,   
    /** 团购或预售券有效结束日 */ 
    7:optional i64 expireEndTime,   
    /** 待审核机构编号 */ 
    8:string auditOrgCode,  
    /** 起始审核机构编号 */ 
    9:string auditStartOrgCode,   
    /** 最终审核机编号 */
    10:string auditEndOrgCode
}

/** 
* 商品管理搜索商品用到 
*/
struct ProductBriefInfoVo {
    /** 客户端id */
    1:string clientId,
    /** 商户ID */
    2:string merchantId, 
    /** 商品id */   
    3:string productId,   
    /** 是否上架 */
    4:string isMarketable,  
    /** 上架时间 */  
    5:i64 rackTime,    
    /** 商品类型 */
    6:string type,    
    /** 0送货上门,1网点自提,2配送或自提 */
    7:string deliveryOption,    
    /** 商品名 */
    8:string name,    
    /** 销售价 */
    9:double price,   
    /** 成本价 */
    10:double cost,   
    /** 市场价 */
    11:double marketPrice,    
    /** 商品库存数量 */
    12:i32 store,   
    /** 商品审核状态代码 (0待审核即审核中；1审核通过即最终的审核通过包括新加后审核通过和修改后审核通过；2审核未通过即新加后审核未通过；3未提交审核 即新加后未提交审核；4"审核通过(更新中)"；5"审核通过(更新审核未通过)"；6修改后未提交审核。)*/
    13:string auditState,
    /** 商品审核状态对应的名称 (0待审核即审核中；1审核通过即最终的审核通过包括新加后审核通过和修改后审核通过；2审核未通过即新加后审核未通过；3未提交审核 即新加后未提交审核；4"审核通过(更新中)"；5"审核通过(更新审核未通过)"；6修改后未提交审核。)*/
    14:string auditStateName,   
    /** 团购或预售有效期开始 */
    15:i64 startTime,   
    /** 团购或预售有效期结束 */
    16:i64 endTime, 
    /** 团购或预售券有效起始日 */ 
    17:i64 expireStartTime,   
    /** 团购或预售券有效结束日 */
    18:i64 expireEndTime,   
    
    /**预售信息 */
    /** 提货-开始 */
    19:i64 deliveryStartTime, 
    /** 提货-结束 */  
    20:i64 deliveryEndTime,
    /** 机构代码 */  
    21:string orgCode,
    /** 机构名称 */  
    22:string orgName,
    /** 商户名称 */  
    23:string merchantName,
	/** 是否秒杀 0非秒杀,1秒杀,2秒杀未上架*/
    24:string isSeckill,
    /** 创建时间 */
    25:i64 createTime,
    /** 审核备注 */
    26:string auditComment,
    /** 商品分类名称 */
    27:string categoryName,  
    /** 审核时间 */  
    28:i64 auditTime,
    /** 审核人 */
    29:string auditStaff	   
}


/** 
* 查看商品收藏搜索列表用到 
*/
struct ProductBaseInfoVo {
    /** 客户端id */
    1:string clientId,
    /** 商户ID */
    2:string merchantId, 
    /** 商品id */   
    3:string productId,   
    /** 是否上架 */
    4:string isMarketable,  
    /** 商品类型 */
    5:string type,    
    /** 商品名 */
    6:string name,    
    /** 销售价 */
    7:double price,   
    /** 市场价 */
    8:double marketPrice,    
    /**商品图片*/
    9:string imagePic,    
    /** VIP价 */
    10:double vipPrice	   
}

/** 
* 商品管理搜索商品用到 
*/
struct ProductBriefPageVo{
	1:Common.PageVo page,
	2:list<ProductBriefInfoVo> productBriefInfoVoList
}

/** 
* H5用户搜索商品用到 
*/
struct ProductBriefVoRes {
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
    /** 是否开始 */
    16:bool isStart,
    /** 是否结束 */
    17:bool isEnd,     
    /** 服务器时间 */
    18:i64 serverTime,
    /** 商品秒杀库存数量 */
    19:i32 secStore,   
    /** 秒杀开始时间 */
    20:i64 secStartTime,    
    /** 秒杀结束时间 */
    21:i64 secEndTime,    
    /** 是否参与送积分活动 */
    22:bool isPoint,    
    /** 是否参与VIP规则活动 */
    23:bool isVip,   
    /** VIP价 */ 
    24:double vipPrice   
}

/** 
* H5用户搜索商品用到 
*/
struct ProductBriefPageVoRes{
	1:Common.PageVo page,
	2:list<ProductBriefVoRes> productBriefVoList
}

/**
 * ProductOperateVoReq 商品详细请求参数，商品删除请求参数
 */
struct ProductOperateVoReq {
    /** 客户端id */
    1:string clientId,    
    /** 商户ID */
    2:optional string merchantId,    
    /** 商品id */
    3:string productId,    
    /** 商品类型 */
    4:string type,    
    /** 市id */
    5:i64 cityId    
}

/**
 * ProductFilterVoReq 商品分页查询请求参数
 */
struct ProductFilterVoReq {
    /** 客户端id */
    1:string clientId,    
    /** 商户ID */
    2:optional string merchantId,    
    /** 查询过滤条件 形式为{key1:value1,key2:value2} key为type,name,orgCode等，详见接口文档*/
    3:string filter    
}

/**
 * ProductAmountVoReq 商品销量修改请求参数，商品库存修改请求参数
 */
struct ProductAmountVoReq {
    /** 客户端id */
    1:string clientId,
    /** 商品id */   
    2:string productId,    
    /** 新销售数量,正数代表订单支付成功后销售的数量;购买数量，正数代表减库存，负数代表加回库存 */
    3:i32 count 
}

/**
 * ProductExistVoReq 商品新加或修改判断是否有重复的
 */
struct ProductExistVoReq {
    /** 客户端id */
    1:string clientId,
    /** 商户ID */    
    2:string merchantId, 
    /** 商品id */   
    3:string productId,    
    /** 商品名称 */
    4:string name,    
    /** 商品类型 */
    5:string type    
}

/**
 * ProductStatusVoReq 商品上下架请求参数
 */
struct ProductStatusVoReq {
    /** 客户端id */
    1:string clientId,    
    /** 商户ID */
    2:string merchantId,
    /** 商品id */    
    3:string productId,    
    /** 商品状态 */
    4:string status,
    /** 上下架名称 */
    5:string name
   
}

/**
 * ProductStatusVoReq 商品上下架请求参数
 */
struct ProductStatusBatchVoReq {
    /** 客户端id */
    1:string clientId, 
    /** 商品状态 */
    2:string status,   
    /** 商品id */    
    3:list<string> productIds
}

struct OutletProductVo{
    /** 客户端id */
    1:string clientId,
    /** 商户id */
    2:string merchantId, 
    /** 门店id */
    3:string outletId,
    /** 价格 */ 
    4:double cost,
    /** 商品id */
    5:string productId,
    /** 商户用户id */
    6:string userId,
    /** 备注 */
    7:string remark
}
struct OutletProductDiscountVo{
    /** 客户端id */
    1:string clientId,
    /** 商户id */
    2:string merchantId, 
    /** 门店id */
    3:string outletId,
    /** 消费总金额 */
    4:double consumeAmount,
    /** 不参与优惠金额 */
    5:double notDiscountAmount,
    /** 折扣优惠率 */
    6:double discountRate
}
struct OutletProductVoReq{
    /** 客户端id */
    1:string clientId,
    /** 二维码 */
    2:string qrCode
}

struct OutletProductVoRes{
    /** 客户端id */
    1:string clientId,
    /** 商户id */
    2:string merchantId, 
    /** 门店id */
    3:string outletId,
    /** 价格 */ 
    4:double cost,
    /** 商品id */
    5:string productId,
    /**商户名称*/
	6:string merchantName,
	/** 商户商标 */
    7:string logo,
    /**门店名称*/
	8:string outletName,
	 /** 消费总金额 */
    9:double consumeAmount,
    /** 不参与优惠金额 */
    10:double notDiscountAmount,
    /** 折扣优惠率 */
    11:double discountRate
}

struct OutletProductQrCodeVo{
    /** 二维码 */
    1:string qrCode,
    /** 商品id */
    2:string productId,
    /** 二维码url */
    3:string url
}

struct AddOutletProductVoRes{
    /** 返回结果信息 */
    1:Common.ResultVo resultVo,
    /** 二维码等信息 */
    2:OutletProductQrCodeVo outletProductQrCodeVo
}


/** 
* 面对面商品分页查询 
*/
struct OutletProductPageVo{
	1:Common.PageVo page,
	2:list<OutletProductVo> outletProductVoList
}

/**
 *商品品论与回复表信息
 */
 struct ProductCommentVo{
 	/**评论id*/
 	1:string commentId,
	/**商品Id*/
	2:string productId,
	/**商品名称*/
	3:string productName,
	/**排序*/
	4:i16 orderValue,
	/**客户端id*/
	5:string clientId,
	/**商户ID*/
	6:string merchantId,
	/**商户名称*/
	7:string merchantName,	
	/**交易编号*/
	8:string orderId,
	/**交易类型*/
	9:string orderType,
	/**用户编码*/
	10:string memberCode, 
	/**评论人*/
	12:string memberName,
	/**评价星级*/
	13:i16 starLevel,		
	/**评价内容*/	
	14:string commentDescription,
	/**回复评价内容*/	
	15:string recomment,
	/**评价时间*/	
	16:i64 createTime
	/**机构码*/	
	17:string orgCode,
	/**回复时间*/
	18:i64 recommentTime,
    /**回复人*/
	19:string merchantUserName,
	/**商品电话*/
	20:string phone,
	/**商品图片*/
	21:string imagePic,
	/**商品类型*/
	22:string type,
	/**大交易编号*/
	23:string bigOrderId
}

/**
 *商品品论与回复表信息分页信息
 */
struct ProductCommentPageVo{
	1:Common.PageVo page,
	2:list<ProductCommentVo> ProductCommentVoList
}

/**
 *商品品论与回复查询条件
 */
 struct ProductCommentFilterReq{
	/**客户端id*/
	1:string clientId,
	/** 查询过滤条件 形式为{key1:value1,key2:value2} key为type,name,orgCode等，详见接口文档*/
    2:string filter  
 }
 
/**
 *商品审核信息
 */
struct ProductAuditVo{
	/**商品Id*/
	1:string productId, 
	/**客户端id*/
	2:string clientId,
	/**商户ID*/
	3:string merchantId, 
	/**审核机构代码*/
	4:string auditOrgCode,
    /**机构代码*/
    6:string orgCode,
    /**商品审核状态("0待审核",1审核通过,2审核不通过,3未提交)*/
	7:string auditState, 
	/**审核步骤*/
	8:string auditStage,
	/**审核人*/
	9:string auditStaff,
	/**银行用户所属机构代码-审核人所属机构编号*/
	10:string auditOrg,
	/**审核备注*/
	11:string auditComment
}

/**
 *商品关联门店信息
 */
 struct ProductRelateOutletVo{
	/**商品Id*/
	1:string productId, 
	/**客户端id*/
	2:string clientId,
	/**商户ID*/
	3:string merchantId,
	/**门店信息outletIds*/
	4:list<string> outletIds
}

/**
 *商品关联活动信息
 */
struct ProductRelateActivitiesVo{
	/**商品Id*/
	1:string productId, 
	/**客户端id*/
	2:string clientId,
	/**商户ID*/
	3:string merchantId,
	/**门店信息outletIds*/
	4:list<ProductActivitiesVo> activities
}

/**
 *商品活动信息
 */
struct ProductActivitiesVo{
	/**活动Id*/
	1:i64 activitiesId, 
	/**活动类型:1-赠送积分*/
	2:string activitiesType,
	/**当活动类型为赠送积分时使用points字段 */
	3: optional double points    
}

/**
 *商品关联的区网点信息
 */
struct ProductAreaVo{
	/**商品Id*/
	1:string productId, 
	/**客户端id*/
	2:string clientId,
	/**区域id*/
	3:i64 areaId, 
	/**门店id*/
	4:string outletId, 
	/**门店名称*/
	5:string outletName,
	/**商户ID*/
	6:string merchantId,
	/** 门店对应机构编号 */
    7:string orgCode,
    /** 城市id*/
    8:i64 cityId
}

/**
 *商品收藏查询基础信息vo
 */
struct ProductStoreFilterVo{
	/**商户ID*/
	1:string merchantId,
	/**商品Id*/
	2:string productId 
}

struct InvalidProductBatchVo {
    /** 客户端id */
    1:string clientId,    
    /** 商户ID */
    2:string merchantId,    
    /** 商品对应商户所属机构编号 */
    3:string orgCode,  
}

/** 
* 活动绑定商品列表vo 
*/
struct ActivityProductPageVo{
	1:Common.PageVo page,
	2:list<ActivityProductVo> activityProductVoList
}

/** 
* 活动绑定商品列表 
*/
struct ActivityProductVo {
    /** 客户端id */
    1:string clientId,
    /** 商品id */   
    2:string productId, 
    /** 商品名 */
    3:string name,  
    /** 上架时间 long类型*/  
    4:i64 rackTime,  
    /** 销售价 */
    5:double price,
    /** vip1价 */
    6:double vip1Price,  
    /** 是否上架 */
    7:string isMarketable,  
    /** 创建时间 */
    8:i64 createTime,
    /** vip1限购 */
    9:i32 max
}


/**
 *提货网店分页vo
 */
struct ProductOutletPageVo{
	1:Common.PageVo page,
	2:list<ProductOutletVo> ProductOutletVo
}

/**
 *商品查询过滤条件:特惠商品推荐列表或特惠商品列表查询过滤条件
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
 *商品排序
 */
struct FiledSort {
    /** 排序优先级  数字从小到大排序，sortPrior越小的优先排序*/
    1:i32 sortPrior, 
    /** 排序字段名称: 'storeCount'人气排序(收藏数排序);'distance'距离排序 */
    2:string sortName,
    /** 排序规则 负数代表降序，整数代表升序 */
    3:i32 sortBy
}

/**
 *特惠商品推荐列表,特惠商品列表分页信息
 */
struct GroupProductPageVoRes{
	1:Common.PageVo page,
	2:list<GroupProductOutletVo> productOutletVos
}

/**
 *特惠商品推荐列表,特惠商品列表信息
 */
struct GroupProductOutletVo{
	/** 客户端id */
    1:string clientId,
	/** 门店ID */
    2:string outletId,
    /** 店名 */
    3:string outletName,
    /** 门店地址 */   
    4:string address,
    /** 门店评论星级 */
    5:string starLevel,
    /** 距离 */ 
    6:double distance,
    /** 商户ID */
    7:string merchantId,
    /** 商户名 */
    8:string merchantName,
    /** 门店下特惠商品列表 */   
	9:list<GroupProductVo> groupProductVos
}

/**
 *特惠商品信息
 */
struct GroupProductVo{
    /** 商品id */
    1:string productId, 
    /** 商品名 */
    2:string name,    
    /** 商品全名 */
    3:string fullName,   
    /** 销售价 */ 
    4:double price,    
    /** 市场价 */
    5:double marketPrice,    
    /** 销售数量 */   
    6:i32 sellCount,    
    /** 简介 */
    7:string briefIntroduction,    
    /** 小图片地址 */ 
    8:string smallImgUrl,
    /** 团购或预售有效期开始 */
    9:i64 startTime,    
    /** 团购或预售有效期结束 */
    10:i64 endTime, 
    /** 是否秒杀 0非秒杀,1秒杀,2秒杀未上架 */
    11:string isSeckill,
    /** 服务器时间 */
    12:i64 serverTime,
    /** 秒杀开始时间 */
    13:i64 secStartTime,    
    /** 秒杀结束时间 */
    14:i64 secEndTime,
    /** 评论星级 */
    15:i32 starLevel,
    /** 距离 */
    16:double distance,
    /** 门店地址 */   
    17:string address    
}


/**
 *根据商户id(定位的距离查询到的商户)查询该商户的特惠商品列表
 */
struct MerchantProductPageVoRes{
	/** 分页信息 */
	1:Common.PageVo page,
	/** 商户门店信息 */
	2:GroupProductOutletVo merchantOutletVo,
	/** 商户下的商品列表 */
	3:list<GroupProductVo> groupProductVos
}

/**
 *类目营销类里面的特惠商品推荐列表
 */
struct ProductPageVoRes{
	1:Common.PageVo page,
	2:list<GroupProductVo> productVos
}


/**
 *VIP规则列表vo
 */
struct VipProductPageVoRes{
	1:Common.ResultVo resultVo,
	2:Common.PageVo page,
	3:list<VipProductVo> vipProductVos
}

/**
 * VIP规则
 */
struct VipProductVo {
    /** VIP活动id */
    1:string vipId,
    /** 创建时间 */
    2:i64 createTime, 
    /** 客户端id */
    3:string clientId,    
     /** 客户端简称 */
    4:string clientName,
    /** VIP规则名称 */
    5:string activitiesName,
    /** VIP价格 */ 
    6:double vipPrice,
    /** 状态 0未生效,1已生效,2已作废*/   
    7:string status,
    /** 设置了vip价的商品总数 */
    8:i32 count, 
    /** VIP特权介绍 */
    9:string remark
}

/** 
* VIP规则已经或可以绑定的商品列表分页vo 
*/
struct ProductOfVipPageVo{
	1:Common.ResultVo resultVo,
	2:Common.PageVo page,
	3:list<ProductOfVipVo> productOfVipVoList
}

/** 
* VIP规则已经或可以绑定的商品列表vo 
*/
struct ProductOfVipVo {
    /** 商品id */   
    1:string productId, 
    /** 商品名 */
    2:string name,  
    /** 销售价 */
    3:double price,
    /** 上架时间 long类型*/  
    4:i64 rackTime,  
    /** 是否上架 */
    5:string isMarketable,  
    /** 创建时间 long类型*/
    6:i64 createTime, 
    /** VIP1价 */ 
    7:double vipPrice,    
    /** VIP1限购 */
    8:i32 vipLimit
}

/**
 * VIP规则绑定商品信息vo
 */
struct BindVipInfoVo {
    /** 商品id */
    1:string productId, 
    /** VIP1价 */ 
    2:double vipPrice,    
    /** VIP1限购 */
    3:i32 vipLimit
}


/**
 * 商品秒杀配置信息分类
 */
struct ProductSeckillVo {
    /** id */
    1:i64 id,   
    /** 客户端id */
    2:string clientId,
    /** 创建时间(秒杀创建时间) */
    3:i64 createTime,
    /** 商品id */
    4:string productId,
    /** 秒杀库存数量 */
    5:i32 secStore,   
    /** 秒杀价 */
    6:double secPrice,
    /** VIP秒杀价 */
    7:double vipSecPrice,    
    /** 秒杀开始时间 */
    8:i64 startTime,
    /** 秒杀结束时间 */
    9:i64 endTime,
    /** 真实秒杀数量 */
    10:i32 trueBuyerNumber,
    /** 秒杀单用户限购 */
    11:i32 buyLimit,
    /** 是否上架 0未上架,1已上架,2已下架,3已删除*/   
    12:string isMarketable, 
    /** 起始审核机构 */
    13:string auditStartOrgCode,
    /** 最终审核机构 */
    14:string auditEndOrgCode,
    /** 商品审核状态("0待审核",1审核通过,2审核不通过,3未提交) */
    15:string auditState,   
    /** 待审核机构编号(完成置0) */ 
    16:string auditOrgCode,    
    /** 审核步骤(0-初审/1-复审) */
    17:string auditStage,    
    /** 复核人 */
    18:string reviewStaff,    
    /** 审核人 */
    19:string auditStaff,
    /** 审核时间 */
    20:i64 auditTime,
    /** 审核批注 */
    21:string auditComment,
    /** 商品名称类型 */
    22:string type,
    /** 商品名称 */
    23:string name,
    /** 优惠价格 */
    24:double price,
    /** 市场价格 */
    25:double marketPrice,
    /** 提货开始时间 */
    26:i64 deliveryStartTime,
    /** 提货结束时间 */
    27:i64 deliveryEndTime,
    /** 商品简介 */
    28:string briefIntroduction,
    /** 投诉电话 */
    29:string phone,
    /** 配送方式 */
    30:string deliveryOption,
    /** 购买须知 */
    31:string buyKnow,
    /** 商品详情 */
    32:string introduction,
    /** 商户ID */
    33:string merchantId,
    /** 网点机构号 */
    34:string orgCode,
    /** 所属机构 */
    35:string orgName,
    /** 秒杀商品状态 */
    36:string status,
    /** 商户名称 */
    37:string merchantName,
    /** 商品全名 */
    38:string fullName,
    /** 运费 */
    39:double deliveryMoney,
    /** 商品库存数量 */
    40:i32 store   
}

/**
 * 商品秒杀配置明细信息
 */
struct ProductSeckillInfoVo{
    /** 商品新加修改管理平台 */
    1:string platType,
    /** 商品秒杀配置基础信息 */
    2:ProductSeckillVo productSeckill,
    /** 商品分类 */
    3:ProductCategoryVo productCategory,
    /** 商品限购信息 */
    4:ProductBuyLimitVo buyLimit,
    /** 商品图片列表 */
    5:list<ProductImageVo> image,
    /** 商品门店列表 */
    6:list<ProductOutletVo> outlet,
    /** 商品活动 */
    7:list<ProductActivitiesVo> activities,
    /** 商品对应网店所属的机构代码列表 */
    8:list<string> orgCodes
}



/**
 * 秒杀商品列表查询返回
 */
struct ProductSeckillPageVo{
	1:Common.PageVo page,
	2:list<ProductSeckillVo> seckillVoList
}

/**
 * H5秒杀商品列表查询返回
 */
struct ProductSeckillInfoPageVo{
	1:Common.PageVo page,
	2:list<ProductSeckillInfoVo> seckillInfoVoList
}

/**
 * Boss商品管理平台查询商品列表(精品商城商品)过滤条件请求参数
 */
struct ProductFilterVo {
    /** 商品编号 */   
    1:string productId,
    /** 商品全称 */
    2:string productName,
    /** 搜索关键词 */
    3:string seoKeyWords,
    /** 商品分类id */
    4:i64 categoryId,
    /** 客户端id即所属行id */
    5:string clientId,
    /** 商品类型 "-1":全部, "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品,"6":精品商城商品;可以逗号相隔同时查询多种类型比如"1,2,3,6"*/
    6:string type,
    /** 是否秒杀 0非秒杀,1秒杀,2秒杀未上架*/
    7:string isSeckill,
    /** 上架状态: 0未上架,1已上架,2已下架*/   
    8:string isMarketable,
    /** 审核状态("0待审核",1审核通过,2审核不通过,3未提交)*/
    9:string auditState
}

/** 
* boss商品管理平台分页查询商品列表vo(精品商城商品列表vo)
*/
struct ProductListVo {
    /** id */
    1:i64 id,
    /** 商品编号 */   
    2:string productId,
    /** 创建时间 */
    3:i64 createTime,
    /** 商品名称 */
    4:string name,
    /** 商品长名称 */
    5:string fullName,
    /** 商城价 */
    6:double price,
    /** VIP价 */
    7:double vipPrice,
    /** 客户端id */
    8:string clientId,
    /** 客户端名称 */
    9:string clientName,
    /** 上下架状态 */
    10:string marketableStatus,
    /** 商品类型  "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品,"6":精品商城商品;*/ 
    11:string productType,
    /** 库存数量 */
    12:i32 store,
    /** 商户id*/
    13:string merchantId,
    /** 商户名称 */
    14:string merchantName,
    /** 开始时间 */
    15:i64 startTime,
    /** 结束时间 */
    16:i64 endTime,
    /** 是否秒杀 0非秒杀,1秒杀*/
    17:string isSeckill 
}

/** 
* boss商品管理平台分页查询精品商城商品列表
*/
struct ProductListPageVo{
	1:Common.PageVo page,
	2:list<ProductListVo> productListVos
}


/**
 * Boss商品管理平台新增精品商城商品信息Vo
 */
struct GoodsInfoVo {    
    /** 客户端id*/
    1:string clientId,
    /** 供货商id*/
    2:string merchantId,
	/** 商品图片列表 */
	3:list<ProductImageVo> images,
	/** 商品简称 */
    4:string name,    
    /** 商品全称 */
    5:string fullName,
	/** 副标题 */
    6:string briefIntroduction,
    /** 搜索关键词 */
    7:string seoKeyWords,
    /** 市场价 */
    8:double marketPrice,
    /** 商城价 */ 
    9:double price,    
    /** VIP价 */ 
    10:double vipPrice,
    /** 商品分类id */
    11:i64 categoryId,
    /** 最低购买数量 */ 
    12:i32 min,
    /** 限购数量 */ 
    13:i32 max,
    /** VIP限购数量 */
    14:i32 maxVip,
    /** 库存数量 */
    15:i32 store,
    /** 是否推荐 */
    16:bool isRecommend,
    /** 是否新品 */
    17:bool isNew,
    /** 是否热销 */
    18:bool isHot,
    /** 是否批量发货 */
    19:bool isBatchDelivery,
    /** 预计发货时间 */
    20:i64 deliveryTime, 
    /** 商品介绍 */
    21:string introduction,
    /** 商品购买须知 */
    22:string buyKnow,  
    /** 售后说明*/
    23:string afterShop,
    /** 商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品,"6":精品商城商品;*/
    24:string type,
    /** 商品id */
    25:i64 id, 
    /** 商品编号 */
    26:string productId, 
    /** 商品分类名称 */
    27:string categoryName,
    /** 客户端名称 */
    28:string clientName,
     /** 供货商名称 */
    29:string merchantName,  
    /** 上下架状态 */
    30:string marketableStatus,
    /** 创建时间 */
    31:i64 createTime,
    /** 是否秒杀 0非秒杀,1秒杀*/
    32:string isSeckill
}


/**
 *精品商品查询过滤条件
 */
struct QueryBoutiqueGoodsFilterVo {
    /** 客户端ID */
    1:string clientId, 
    /** 商品名 */
    2:string productName,
    /** 商品分类ID */
    3:i64 productCategoryId,
    /** '1'推荐;'2'热销;'3'新品;'4'秒杀 */
    4:string goodFlag
}

/**
 *精品商品商品列表
 */
struct BoutiqueProductPageVoRes{
	1:Common.PageVo page,
	2:list<BoutiqueProductVo> productVos
}

/**
 *精品商品商品列表信息
 */
struct BoutiqueProductVo{
    /** 商品id */
    1:string productId, 
    /** 商品名 */
    2:string name,    
    /** 商品全名 */
    3:string fullName,   
    /** 销售价 */ 
    4:double price,    
    /** VIP价 */
    5:double vipPrice,    
    /** 销售数量 */   
    6:i32 sellCount,    
    /** 简介 */
    7:string briefIntroduction,    
    /** 图片地址 */ 
    8:ProductImageVo image,
    /** 服务器时间 */
    9:i64 serverTime,
    /** 是否推荐 */
    10:bool isRecommend,
    /** 是否新品 */
    11:bool isNew,
    /** 是否热销 */
    12:bool isHot,
    /** 是否秒杀 0非秒杀,1秒杀*/
    13:string isSeckill
}


/**
 * H5精品商城商品商品详情信息Vo
 */
struct BoutiqueGoodsInfoVo {    
    /** 商品编号 */
    1:string productId,
    /** 客户端id*/
    2:string clientId,
    /** 供货商id*/
    3:string merchantId,
    /** 商品类型*/
    4:string type,
	/** 商品简称 */
    5:string name,    
    /** 商品全称 */
    6:string fullName,
	/** 副标题 */
    7:string briefIntroduction,
    /** 市场价 */
    8:double marketPrice,
    /** 商城价 */ 
    9:double price,    
    /** VIP价 */ 
    10:double vipPrice,
    /** 最低购买数量 */ 
    11:i32 min,
    /** 限购数量 */ 
    12:i32 max,
    /** VIP限购数量 */
    13:i32 maxVip,
    /** 销售数量 */   
    14:i32 sellCount,
    /** 库存数量 */
    15:i32 store,
    /** 是否推荐 */
    16:bool isRecommend,
    /** 是否新品 */
    17:bool isNew,
    /** 是否热销 */
    18:bool isHot,
    /** 预计发货时间 */
    19:i64 deliveryTime, 
    /** 商品介绍 */
    20:string introduction,
    /** 商品购买须知 */
    21:string buyKnow,  
    /** 售后说明*/
    22:string afterShop,
    /** 商品图片列表 */
	23:list<string> imageUrls,
    /** 供货商名称 */
    24:string merchantName,
    /** 是否秒杀 0非秒杀,1秒杀*/
    25:string isSeckill
}

/**
 *管理平台商品管理查询商品列表查询过滤条件(商户管理平台和银行管理平台)
 */
struct ProductFilterReqVo {
    /** 客户端ID */
    1:string clientId, 
    /** 商户ID */
    2:string merchantId,
    /** 商品类型 ('1,2'可以逗号分隔查询多个商品类型)"1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品,"6":精品商城商品;*/
    3:string type,
    /** 商品名模糊查询 简称和全称模糊查询 */
    4:string productName,
    /** 商品分类ID */
    5:i64 productCategoryId,
    /** 机构号查询 '340000,340101'可以逗号分隔查询多个机构下的商品*/
    6:string orgCode,
    /** 商户名模糊查询*/
    7:string merchantName,
    /** 上架状态: 0未上架,1已上架,2已下架*/   
    8:string isMarketable,
    /** 需要过滤掉不返回的上架状态*/
    9:string filterStatuts,
    /** 审核状态("0待审核",1审核通过,2审核不通过,3未提交)*/
    10:string auditState,
    /** 需要过滤掉不返回的审核状态("0待审核",1审核通过,2审核不通过,3未提交)*/
    11:string filterAuditState,
    /** 是否秒杀 0非秒杀,1秒杀,2秒杀未上架*/
    12:string isSeckill,
    /** 销售开始时间 开始范围*/
    13:i64 startTimeStart, 
    /** 销售开始时间 结束范围 */
    14:i64 startTimeEnd,  
    /** 销售结束时间 开始范围 */
    15:i64 endTimeStart,  
    /** 销售结束时间 结束范围 */
    16:i64 endTimeEnd,  
    /** 审核时间开始范围 */
    17:i64 auditTimeStart,  
    /** 审核时间结束范围 */
    18:i64 auditTimeEnd,   
    /** 销售价 开始范围*/ 
    19:double priceStart,   
    /** 销售价结束范围 */ 
    20:double priceEnd
}

/** 
* 管理平台商品管理查询商品列表(商户管理平台和银行管理平台)
*/
struct ProductPageVo{
	1:Common.PageVo page,
	2:list<ProductBriefVo> productBriefVoList
}

/** 
* 管理平台商品管理查询商品列表(商户管理平台和银行管理平台)
*/
struct ProductBriefVo {
    /** 客户端id */
    1:string clientId,
    /** 商户ID */
    2:string merchantId, 
    /** 商品id即商品编号 */   
    3:string productId,   
    /** 上架状态 */
    4:string isMarketable,  
    /** 上架时间 */  
    5:i64 rackTime,    
    /** 商品类型"1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品,"6":精品商城商品; */
    6:string type,    
    /** 0送货上门,1网点自提,2配送或自提 */
    7:string deliveryOption,    
    /** 商品简称 */
    8:string name, 
    /** 商品全称 */
    9:string fullName,    
    /** 销售价 */
    10:double price,   
    /** 市场价 */
    11:double marketPrice,    
    /** 成本价 */
    12:double cost,
    /** 商品库存数量 */
    13:i32 store,   
    /** 商品审核状态代码 ("0待审核",1审核通过,2审核不通过,3未提交)*/
    14:string auditState,
    /** 商品审核状态对应的名称 ("审核中","审核通过","审核不通过","未提交","审核通过(更新中)","审核通过(更新审核未通过)")*/
    15:string auditStateName,   
    /** 销售期开始 */
    16:i64 startTime,   
    /** 销售期结束 */
    17:i64 endTime, 
    /** 销售券有效起始时间 */ 
    18:i64 expireStartTime,   
    /** 销售券有效结束时间 */
    19:i64 expireEndTime,   
    /** 商户名称 */  
    20:string merchantName,
	/** 是否秒杀 0非秒杀,1秒杀,2秒杀未上架*/
    21:string isSeckill,
    /** 创建时间 */
    22:i64 createTime,
    /** 审核备注 */
    23:string auditComment,
    /** 商品分类名称 */
    24:string categoryName,  
    /** 审核时间 */  
    25:i64 auditTime
}

/**
 * 商品信息详情
 */
struct ProductDetailInfo { 
    /** 商品信息Vo，最新信息*/ 
    1:ProductDetailVo productDetailVo,
    /** 原始商品信息Vo，修改前信息 */ 
    2:ProductDetailVo oldProductDetailVo
}


/**
 * 管理端商品详情信息Vo(银行管理端，商户管理端)
 */
struct ProductDetailVo { 
    /** 客户端id */ 
    1:string clientId,    
    /** 商户ID */
    2:string merchantId,    
    /** 商品对应商户所属机构编号 */
    3:string orgCode,    
    /** 商品id即商品编号 */
    4:string productId, 
    /** 是否上架 0未上架,1已上架,2已下架*/   
    5:string isMarketable,    
    /** 上架时间 */
    6:i64 rackTime,    
    /** createTime */
    7:i64 createTime,  
    /** 商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品,"6":精品商城商品;*/  
    8:string type,    
    /** 0送货上门,1网点自提,2配送或自提 */
    9:string deliveryOption,    
    /** 商品名 */
    10:string name,    
    /** 商品全名 */
    11:string fullName,   
    /** 销售价 */ 
    12:double price,    
    /** 成本价 */
    13:double cost,    
    /** 市场价 */
    14:double marketPrice,    
    /** 商品库存数量 */
    15:i32 store,    
    /** 销售数量 */   
    16:i32 sellCount,  
    /** 商品分类id */
    17:i64 productCategoryId,   
    /**商品分类名称 */
    18:string productCategoryName,   
    /** 商品分类树路径 空格间隔 */
    19:string categoryTreePath, 
    /** 商品分类全名包括父类节点名 */
    20:string categoryTreePathName,   
    /** 是否限购 */
    21:bool isLimit,    
    /** 评分 */
    22:i32 point,    
    /** 简介 */
    23:string briefIntroduction,    
    /** 介绍 */
    24:string introduction,    
    /** 商品须知 */
    25:string buyKnow,    
    /** 商品审核状态 ("0待审核",1审核通过,2审核不通过,3未提交)*/
    26:string auditState,
    /** 商品审核状态对应的名称 ("审核中","审核通过","审核不通过","未提交","审核通过(更新中)","审核通过(更新审核未通过)")*/
    27:string auditStateName,   
    /** 用户收藏数 */
    28:i32 storeCount,    
    /** 销售期开始 */
    29:i64 startTime,    
    /** 销售期结束 */
    30:i64 endTime,    
    /** 运费 */
    31:double deliveryMoney,  
    /** 销售券有效起始日 */
    32:i64 expireStartTime,   
    /** 销售券有效结束日 */ 
    33:i64 expireEndTime,    
    /** 下架时间 */
    34:i64 downTime,
    /** 审核时间 */
    35:i64 auditTime,
    /** 审核备注 */
	36:string auditComment,
	/** 是否秒杀 0非秒杀,1秒杀,2秒杀未上架 */
    37:string isSeckill,
    /** 商户名称 */
    38:string merchantName,
    /** 机构名称 */  
    39:string orgName,
    /** VIP价 */ 
    40:double vipPrice,
    /** 客户端名称 */
    41:string clientName,
    /** 二维码地址 */ 
    42:string codeUrl,
	/** 商品限购信息 */
	43:ProductBuyLimitVo buyLimit,
	/** 商品图片列表 */
	44:list<ProductImageVo> images
}


/**
 * 更新商品审核状态vo
 */
struct EditProductAuditStateVo { 
    /** 商品id即商品编号 */
    1:string productId,
    /** 商品审核状态 ("0待审核",3未提交)*/
    2:string auditState
}

