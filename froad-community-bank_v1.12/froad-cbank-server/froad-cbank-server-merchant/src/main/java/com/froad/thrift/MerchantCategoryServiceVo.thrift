/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantCategoryVo.thrift文件 */
include "MerchantCategoryVo.thrift"

namespace java com.froad.thrift.vo


/**
 * 商户分类的响应
 */
struct MerchantCategoryAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 商户分类id */
	2:i64 id;
}

/**
 * 商户分类分页的响应结果
 */
struct MerchantCategoryPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户分类信息列表 */
	2:list<MerchantCategoryVo.MerchantCategoryVo> merchantCategoryVoList;
}

