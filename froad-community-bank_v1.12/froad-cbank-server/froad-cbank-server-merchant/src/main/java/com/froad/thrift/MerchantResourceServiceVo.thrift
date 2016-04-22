/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantResourceVo.thrift文件 */
include "MerchantResourceVo.thrift"

namespace java com.froad.thrift.vo

/**
 * 商户菜单资源分页的响应结果
 */
struct MerchantResourcePageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户菜单资源信息列表 */
	2:list<MerchantResourceVo.MerchantResourceVo> merchantResourceVoList;
}

/**
 * 新增返回bean
 */
struct MerchantResourceAddVoRes {
	
	/** 公共响应信息 */
	1:Common.ResultVo result;
	/** 主键id */
	2:i64 id;
}

