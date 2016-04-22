/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantTempVo.thrift文件 */
include "MerchantTempVo.thrift"


namespace java com.froad.thrift.vo


/**
 * 商户审核的响应
 */
struct MerchantAuditVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
}




/**
 * 商户修改审核变更信息
 */
struct MerchantTempVoReq {
	
	/**商户修改审核变更信息vo*/
	1:MerchantTempVo.MerchantTempVo merchantTempVo;
}
