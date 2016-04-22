include "BizMonitor.thrift"

namespace java com.froad.thrift.service

/**
 * 平台代码
 */
enum PlatType {
	/**
 	 * boss
 	 */
	boss = 1,
	/**
 	 * 银行端
 	 */
    bank = 2,
    /**
 	 * 商户pc
 	 */
    merchant_pc = 3,
    
    /**
 	 * 商户h5
 	 */
    merchant_h5 = 4,
    /**
 	 * 个人pc
 	 */
    personal_pc = 5,
    
    /**
 	 * 个人h5
 	 */
    personal_h5 = 6,
}

/**
 * 源对象信息(包含平台,操作ip,操作员id等...)
 */
struct OriginVo {
	/**
	 * 平台代码
	 */
	1: PlatType platType,
	
	/**
	 * 操作员id(说明:如果是个人操作则传用户id;如果是银行操作则传银行操作员id;如果是商户用户操作则传商户用户id)
	 */
	2: i64 operatorId,
	
	/**
	 * 终端ip
	 */
	3: string operatorIp,
	
	/**
	 * 操作说明
	 */
	4: string description,
	
	/**
	 * 客户端Id
	 */
	5: string clientId,
	/**
	 * 操作人角色id
	 */
	6: string roleId
}

/**
 * PageVo 分页基础
 */
struct PageVo{
	/** 当前页码 */
	1:i32 pageNumber =1, 
	/** 每页记录数 */
	2:i32 pageSize =20,   
	/** 总记录数 */
	3:i32 totalCount =0,  
	/** 总页数 */	
	4:i32 pageCount=0,    
	/** 开始日期*/
	5:i64 begDate,		  
	/** 结束日期*/
	6:i64 endDate,		  
	/** 上次查询页码 */
	7:i32 lastPageNumber,  
	/** 当前页第一条记录时间 */
	8:i64 firstRecordTime, 
	/** 当前页最后一条记录时间 */
	9:i64 lastRecordTime, 
	/** 是否还有下一页 */ 
	10:bool hasNext
}

/**
 * 返回结果
 */
struct ResultVo {
     /**
      * 返回码 : 0000:成功 ,失败：xxxx
      */
     1:string resultCode,
     /**
      * 返回信息
      */
     2:string resultDesc
}

/** 
* boss商品管理平台分页查询商品列表
*/
struct BossProductListPageVo{
	1:PageVo page,
	2:list<BossProductListVo> productListVos
}

/** 
* boss商品管理平台分页查询商品列表vo
*/
struct BossProductListVo {
    /** id */
    1:i64 id,
    /** 创建时间 */
    2:i64 createTime,
    /** 商品编号 */   
    3:string productId, 
    /** 商品名称 */
    4:string name,
    /** 商品分类名称 */
    5:string categoryName, 
    /** 商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品;*/
    6:string type,
    /** 客户端id */
    7:string clientId,
    /** 客户端名称即所属行 */
    8:string clientName,
    /** 录入渠道 "1":boss,"2":银行端,"3":商户pc,"4":商户h5,"5":个人pc,"6":个人h5;*/
    9:string platType,
    /** 审核状态 */
    10:string auditStatus,
    /** 上下架状态 */
    11:string marketableStatus,
    /** 银行名称 */
    12:string bankName
}

/**
 * Boss商品管理平台查询商品过滤条件请求参数
 */
struct BossProductFilterVo {
    /** 客户端id即所属行id */
    1:string clientId,
    /** 商品类型  "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品;*/
    2:string type, 
    /** 商品分类id */
    3:i64 categoryId,
    /** 上下架状态 */
    4:string marketableStatus,
    /** 审核状态 */
    5:string auditStatus,
    /** 商品名称 */
    6:string name,
    /** 录入渠道 "1":boss,"2":银行端,"3":商户pc,"4":商户h5,"5":个人pc,"6":个人h5;*/
    7:string platType   
}

/**
 * Boss商品管理平台新增商品的响应
 */
struct AddProductVoRes {
	/** 响应基础信息 */
	1:ResultVo result,
	/** 商品id */
	2:string productId
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
 * Boss商品管理平台新增商品基础信息Vo
 */
struct BossProductVo {    
    /** 商品id */
    1:string productId,
    /** 商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品;*/
    2:string type,
    /** 商品分类id */
    3:i64 categoryId,
    /** 客户端id 即所属行id*/
    4:string clientId,
    /** 所属行的一级机构编号 */
    5:string orgCode,
    /** 商品简称 */
    6:string name,    
    /** 商品全称 */
    7:string fullName,
    /** 副标题 */
    8:string briefIntroduction,
    /** VIP价 */ 
    9:optional double vipPrice,
    /** vip限购数量 */
    10:optional i32 maxVip,
    /** 销售价 */ 
    11:double price,    
    /** 市场价 */
    12:double marketPrice,
    /** 运费 */
    13:double deliveryMoney,
    /** 库存数量 */
    14:i32 store,
    /** 限购数量 */ 
    15:optional i32 max,  
    /** 商品重量 */
    16:string weight, 
    /** 重量单位 */   
    17:string weightUnit,
    /** 销售开始 */
    18:i64 startTime,    
    /** 销售结束 */
    19:i64 endTime,   
    /** 提货-开始 */
    20:i64 deliveryStartTime,    
    /** 提货-结束 */
    21:i64 deliveryEndTime,
    /** 提货方式 "0":送货上门,"1":网点自提,"2":配送或自提; */
    22:string deliveryOption, 
    /** 商品购买须知 */
    23:string buyKnow,   
    /** 介绍即商品详情 */
    24:string introduction,
    /** 录入渠道 "1":boss,"2":银行端,"3":商户pc,"4":商户h5,"5":个人pc,"6":个人h5;*/
    25:string platType,
    /** 审核状态 */
    26:string auditStatus,
    /** 上下架状态 */
    27:string marketableStatus,
    /** 团购或预售券有效起始日 */
    28:i64 expireStartTime,   
    /** 团购或预售券有效结束日 */ 
    29:i64 expireEndTime,
    /** VIP活动id 新增商品时候绑定vip价格时候需要传值*/
    30:string vipId,
    /** 修改商品时候是否删除VIP规则绑定关系 */
    31:bool isRemoveVipId,
    /** 商品分类名称 */
    32:string categoryName, 
    /** 客户端名称即所属行 */
    33:string clientName,
    /** vip名称*/
    34:string vipName,
    /** 银行名称*/
    35:string bankName

}

/**
 * Boss商品管理平台新增商品信息Vo
 */
struct BossProductInfoVo{
    /** 商品基础信息 */
	1:BossProductVo product,
	/** 商品图片列表 */
	2:list<ProductImageVo> image,
	/** 商品提货网点所属法人行社的机构代码列表 */
	3:list<string> orgCodes,
	/** 商品提货网点的门店ID，提货方式有自提时候需要传值 */
	4:list<string> outletIds
}

/**
 * Boss商品管理平台查看商品详情Vo
 */
struct BossProductDetailVo{
    /** 商品基础信息 */
	1:BossProductVo product,
	/** 商品图片列表 */
	2:list<ProductImageVo> image,
	/** 商品提货网点所属法人行社的机构代码列表 */
	3:optional list<string> orgCodes,
	/** 商品提货网点的门店信息 */
	4:optional list<ProductOutletVo> outlets
}

/**
 * 门店信息
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
 * Boss商品审核Vo
 */
struct BossAuditProcessVo{
    /** 商品id */
	1:string productId,
	/** 用户id */
	2:string userId,
	/** 用户角色id */
	3:string roleId,
	/**审核时间*/
	4:i64 auditTime,
	/**审核备注*/
	5:string auditRemark
	/**审核状态*/
	6:string auditState
}

/**
 * boss管理平台上下架商品请求参数
 */
struct BossProductStatusVoReq {
    /** 商品id */    
    1:string productId,    
    /** 商品状态 */
    2:string marketableStatus
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
 * BossProductService boss管理平台的商品service
 */
service BossProductService extends BizMonitor.BizMonitorService {

    /**
	 * boss商品管理平台分页查询商品列表
	 * @param BossProductFilterVo
	 * @param pageVo
	 * @return BossProductListPageVo
	 */
    BossProductListPageVo findProductsByPage(1:BossProductFilterVo productFilterVo,2:PageVo pageVo),
    
    /**
     * boss商品管理平台新增商品
     * @param OriginVo
     * @param BossProductInfoVo
     * @return AddProductVoRes
     */
    AddProductVoRes addProduct(1:OriginVo originVo,2:BossProductInfoVo productInfoVo),

    /**
     * boss商品管理平台修改商品
     * @param OriginVo
     * @param ProductInfoVo
     * @return ResultVo    
     */
    ResultVo updateProduct(1:OriginVo originVo,2:BossProductInfoVo productInfoVo),
    
    /**
     * boss商品管理平台查询商品详情修改商品之前查询用到
     * @param string
     * @return BossProductDetailVo
     */
    BossProductDetailVo getBossProductDetail(1:string productId),
    
    /**
     * boss商品审核
     * @param BossAuditProcessVo
     * @return ResultVo
     */
    ResultVo auditProduct(1:OriginVo originVo,2:BossAuditProcessVo processVo),
    
    /**
	 * boss查询待审核商品
	 * @param BossProductFilterVo
	 * @param pageVo
	 * @return BossProductListPageVo
	 */
    BossProductListPageVo findAuditProductsByPage(1:BossProductFilterVo productFilterVo,2:PageVo pageVo),
    
    /**
     * boss管理平台删除boss平台新加的商品
     * @param OriginVo
     * @param string
     * @return ResultVo    
     */
    ResultVo deleteProduct(1:OriginVo originVo,2:string productId),
    
    /**
     * boss管理平台上下架商品
     * @param BossProductStatusVoReq
     * @return boolean    
     */
    ResultVo updateProductStatus(1:OriginVo originVo,2:BossProductStatusVoReq productStatusVoReq),
    
    /**
     * 查询vip规则列表
     * @param clientId
     * @return list<VipProductVo>
     */
    list<VipProductVo> getVipProducts(1:string clientId)
    
}