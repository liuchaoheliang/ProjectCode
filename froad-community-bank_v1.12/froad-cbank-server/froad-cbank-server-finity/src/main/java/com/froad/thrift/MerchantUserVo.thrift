/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo


/**
 * 商户用户信息
 */
struct MerchantUserVo {
    
    /** 主键ID */
    1: optional i64 id;
    /** 创建时间 */
    2: optional i64 createTime;
    /** 客户端ID */
    3: optional string clientId;
    /** 商户ID */
    4: optional string merchantId;
    /** 门店ID */
    5: optional string outletId;
    /** 角色ID */
    6: optional i64 merchantRoleId;
    /** 帐号 */
    7: optional string username;
    /** 密码 */
    8: optional string password;
    /** 邮箱地址 */
    9: optional string email;
    /** 电话号码 */
    10: optional string phone;
    /** 密码是否重置 */
    11: optional bool isRest;
    /** 是否删除 */
    12: optional bool isDelete;
    
    /** 真实姓名 */
    13: optional string realname;
    
    /** 商户名称 */
    14: optional string merchantName;
    /** 商户是否可用 */
    15: optional bool merchantIsEnable;
    /** 商户禁用状态 */
	16: optional string merchantDisableStatus;
    /** 商户所属机构码 */
    17: optional string orgCode;
    /** 门店名称 */
    18: optional string outletName;
    /** 操作人ID**/
    19: optional i64 operatorUserId;
}

/**
 * 商户用户分页的响应
 */
struct MerchantUserPageVoRes {
	
	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户用户信息列表 */
	2:list<MerchantUserVo> merchantUserVoList;
}
