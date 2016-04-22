namespace java com.froad.thrift.vo
/* 引入CategoryInfoVo.thrift文件 */
include "CategoryInfoVo.thrift"

/* 引入TypeInfoVo.thrift文件 */
include "TypeInfoVo.thrift"

/**
 * 商户详情信息
 */
struct MerchantDetailVo {
    
    /** id(商户id) */
    1: optional string id;
    /** 创建时间 */
    2: optional i64 createTime;
    /** 客户端id */
    3: string clientId;
    /**一级机构*/
    4: optional string proOrgCode;
    /**二级机构*/
	5: optional string cityOrgCode;
	/**三级级机构*/
	6: optional string countyOrgCode;
    /** 所属组织机构编号 */
    7: optional string orgCode;
    /** 是否启动 */
    8: optional bool isEnable;
    /** 商户名称 */
    9: optional string merchantName;
    /** 商户全称 */
    10: optional string merchantFullname;   
    /** 商标 */
    11: optional string logo;
    /** 地址 */
    12: optional string address;
    /** 电话 */
    13: optional string phone;
    /** 简介 */
    14: optional string introduce;
    /** 区域id */
    15: optional i64 areaId;
    /** 对应地区表的冗余 */
    16: optional string treePathName;
    /** 门店信息 */
    17: optional list<string> outletInfo;
    /** 分类列表 */
    18: optional list<CategoryInfoVo.CategoryInfoVo> categoryInfo;
    /** 类型列表 */
    19: optional list<TypeInfoVo.TypeInfoVo> typeInfo;
    
    /** 评论星级 */
    20: optional i32 starLevel;
    
    /** 机构名称(查询单个详细信息时返回) */
    21: string orgName;
}
