/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantVo.thrift文件 */
include "OutletTempVo.thrift"

namespace java com.froad.thrift.vo


/**
 * 门店临时添加的响应
 */
struct OutletTempAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 门店id */
	2:string outletId;
}