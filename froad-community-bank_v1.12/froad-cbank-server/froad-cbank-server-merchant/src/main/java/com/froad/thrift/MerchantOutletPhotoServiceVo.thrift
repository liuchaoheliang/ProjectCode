/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantOutletPhotoVo.thrift文件 */
include "MerchantOutletPhotoVo.thrift"

namespace java com.froad.thrift.vo

/**
 * 商户门店相册的响应
 */
struct MerchantOutletPhotoAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 商户门店相册id */
	2:i64 id;
}

/**
 * 商户门店相册分页的响应结果
 */
struct MerchantOutletPhotoPageVoRes {

	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户门店相册信息列表 */
	2:list<MerchantOutletPhotoVo.MerchantOutletPhotoVo> merchantOutletPhotoVoList;
}

