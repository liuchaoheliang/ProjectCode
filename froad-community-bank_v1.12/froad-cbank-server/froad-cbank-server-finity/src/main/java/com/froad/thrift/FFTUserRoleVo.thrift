/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo.orgRoleManager

/**
* -------------------------------------用户角色服务Vo---------------------------------
*/

/**
 *  用户角色Vo
 *  FFTUserRoleVo
 */
struct FFTUserRoleVo {
    /** 主键id  */ 
    1: optional i64 id;    	 	 
    /** 用户Id  */  
    2: optional i64 userId;     
    /** 角色Id  */  
    3: optional i64 roleId;    	 
    /** 来源 0-继承自组织 1-用户直接分配  */  
    4: optional i32 source;    
    /** 组织Id  */  
    5: optional string orgId;    		
}


/**
 * 用户角色查询响应VoRes
 * FFTUserRoleListVoRes
 */
struct FFTUserRoleListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 用户角色信息集合*/
	2:list<FFTUserRoleVo> voList;
}

/**
 * 用户角色查询响应VoRes
 * FFTUserRoleIdListVoRes
 */
struct FFTUserRoleIdListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 用户角色id信息集合*/
	2:list<i64> roleIdList;
}
