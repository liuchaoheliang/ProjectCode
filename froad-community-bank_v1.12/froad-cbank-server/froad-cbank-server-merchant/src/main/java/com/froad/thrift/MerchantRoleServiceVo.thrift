/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantRoleVo.thrift文件 */
include "MerchantRoleVo.thrift"

namespace java com.froad.thrift.vo

/**
 * 商户角色分页的响应结果
 */
struct MerchantRolePageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户角色信息列表 */
	2:list<MerchantRoleVo.MerchantRoleVo> merchantRoleVoList;
}

/**
 * 新增返回bean
 */
struct MerchantRoleAddVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result;
	/** 主键id */
	2:i64 id;
}
