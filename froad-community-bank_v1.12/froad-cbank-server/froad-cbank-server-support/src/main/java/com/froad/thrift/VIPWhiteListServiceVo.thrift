/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo

struct CheckVIPExistWhiteListReqVo {	
	/**源对象信息*/
    1: Common.OriginVo origin;
    
	/**会员编号*/
    2: i64 memberCode;
}


struct CheckVIPExistWhiteListRespVo {
	/**返回结果*/
	1: Common.ResultVo result;
	
	/**是否存在于白名单中*/
	2: bool existWhiteList;
}

struct AddVIPWhiteListReqVo {	
	/**源对象信息*/
    1: Common.OriginVo origin;
    
	/**会员编号*/
    2: list<i64> memberCode;
}

struct RemoveVIPWhiteListReqVo {	
	/**源对象信息*/
    1: Common.OriginVo origin;
    
	/**会员编号*/
    2: list<i64> memberCode;
}