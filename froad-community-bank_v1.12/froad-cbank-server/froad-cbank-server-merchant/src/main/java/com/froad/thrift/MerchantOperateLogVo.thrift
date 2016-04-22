namespace java com.froad.thrift.vo

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/**
 * 商户操作日志vo
 */
struct MerchantOperateLogVo {
    
    /** 主键ID */
    1: optional i64 id;
    
    /** 用户id */
    2: optional i64 userId;
    
    /** 用户名 */
    3:optional string username;
    
    /** 角色id */
    4: optional i64 roleId;
    /** 描述 */
    5: optional string description;
    
    /** 客户端ID */
    6: optional string clientId;
    /** 机构码 */
    7: optional string orgCode;
    /** 机构名称 */
    8: optional string orgName;
    /** ip */
    9: optional string operatorIp;
    /**时间*/
    10: optional i64 operatorTime;
}

/**
 * 日志分页响应
 */
struct MerchantOperateLogPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 门店评论信息列表 */
	2:list<MerchantOperateLogVo> MerchantOperateLogVoList;
}