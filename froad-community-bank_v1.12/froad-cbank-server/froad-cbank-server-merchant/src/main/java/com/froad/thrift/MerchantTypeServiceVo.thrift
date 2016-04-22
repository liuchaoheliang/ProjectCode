/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantTypeVo.thrift文件 */
include "MerchantTypeVo.thrift"

namespace java com.froad.thrift.vo


/**
 * 商户类型的响应
 */
struct MerchantTypeAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 商户门店相册id */
	2:i64 id;
}

/**
 * 商户类型分页的响应
 */
struct MerchantTypePageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户类型信息列表 */
	2:list<MerchantTypeVo.MerchantTypeVo> merchantTypeVoList;
}

