/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo.orgRoleManager

/**
* -------------------------------------用户组织服务Vo---------------------------------
*/


/**
 * 用户组织查询响应VoRes
 * FFTUserOrgListVoRes
 */
struct FFTUserOrgListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 用户所属组织集合*/
	2:list<string> orgIds;
}

