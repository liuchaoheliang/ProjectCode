/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantUserVo.thrift文件 */
include "MerchantUserVo.thrift"

namespace java com.froad.thrift.vo

/**
 * 商户用户分页的响应
 */
struct MerchantUserPageVoRes {
	
	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户用户信息列表 */
	2:list<MerchantUserVo.MerchantUserVo> merchantUserVoList;
}

/**
 * 登录返回bean
 */
struct MerchantUserLoginVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result; 
	/** 返回的用户信息 */
	2:MerchantUserVo.MerchantUserVo merchantUser; 
	/** 登录失败次数 */
	3:i32 loginFailureCount; 
	/** token值 */
	4:string token; 
	/** 是否管理员 */
	5:bool isAdmin; 
}

/**
 * 新增返回bean
 */
struct MerchantUserAddVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result;
	/** 主键id */
	2:i64 id;
}

/**
 * check返回bean
 */
struct MerchantUserCheckVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result; 
	/** 返回的用户信息 */
	2:MerchantUserVo.MerchantUserVo merchantUser; 

}