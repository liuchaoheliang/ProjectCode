/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantAccountVo.thrift文件 */
include "MerchantAccountVo.thrift"

namespace java com.froad.thrift.vo


/**
 * 商户账户的响应
 */
struct MerchantAccountAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 商户账户id */
	2:i64 id;
}

/**
 * 商户账户分页的响应结果
 *
 */
struct MerchantAccountPageVoRes {
	
	/** 分页基础信息 */
	1:Common.PageVo page;
	/** 商户账户列表 */
	2:list<MerchantAccountVo.MerchantAccountVo> merchantAccountVoList;
}

