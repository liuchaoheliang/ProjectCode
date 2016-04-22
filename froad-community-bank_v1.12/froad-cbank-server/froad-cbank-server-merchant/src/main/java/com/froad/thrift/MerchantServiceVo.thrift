/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantVo.thrift文件 */
include "MerchantVo.thrift"

/* 引入MerchantDetailVo.thrift文件 */
include "MerchantDetailVo.thrift"

/* 引入CategoryInfoVo.thrift文件 */
include "CategoryInfoVo.thrift"

/* 引入TypeInfoVo.thrift文件 */
include "TypeInfoVo.thrift"

namespace java com.froad.thrift.vo

/**
 * 商户添加的响应
 */
struct MerchantAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 商户id */
	2:string merchantId;
}

/**
 * 商户分页的响应
 */
struct MerchantPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户信息列表 */
	2:list<MerchantVo.MerchantVo> merchantVoList;
}

/**
 * 商户详情分页的响应
 */
struct MerchantDetailPageVoRes {
	
	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户详情信息列表 */
	2:list<MerchantDetailVo.MerchantDetailVo> merchantDetailVoList;
}

/**
 * 商户信息
 */
struct MerchantVoReq {
	
	/**商户vo*/
	1:MerchantVo.MerchantVo merchantVo;
	/**商户分类VO集合*/
	2:list<CategoryInfoVo.CategoryInfoVo> categoryInfoVoList;
	/**商户类型VO集合*/
	3:list<TypeInfoVo.TypeInfoVo> typeInfoVoList;
}

