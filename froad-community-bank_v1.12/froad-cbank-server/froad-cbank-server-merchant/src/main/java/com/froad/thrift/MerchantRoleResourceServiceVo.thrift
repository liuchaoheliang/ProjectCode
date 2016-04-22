/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantRoleResourceVo.thrift文件 */
include "MerchantRoleResourceVo.thrift"

namespace java com.froad.thrift.vo

/**
 * 商户角色资源分页的响应结果
 */
struct MerchantRoleResourcePageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户角色资源信息列表 */
	2:list<MerchantRoleResourceVo.MerchantRoleResourceVo> merchantRoleResourceList;
}

/**
 * 新增返回bean
 */
struct MerchantRoleResourceAddVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result;
	/** 主键id */
	2:string id;
}
