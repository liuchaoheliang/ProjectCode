/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo.orgRoleManager

/**
* -------------------------------------组织管理服务Vo---------------------------------
*/

/**
 *  组织Vo
 *  FFTOrgVo
 */
struct FFTOrgVo {
    /** 主键id  */ 
    1: optional i64 id;    	 	 
    /** 组织Id  */  
    2: optional string orgId;     
    /** 父级组织代码，顶级组织的父级组织Id为0  */  
    3: optional i64 parentId;    	 
    /** 组织级别  */  
    4: optional i32 level;    
    /** 平台名称(boss、bank、merchant)  */  
    5: optional string platform;    		
    /** 所属客户端  */   
    6: optional string clientId;    	 
    /** 树路径(从顶级到自己本身的ID，逗号分隔)  */ 
    7: optional string treePath;     
    /** 组织代码(boss对应主键ID，bank对应机构号，merchant对应商户Id)  */  
    8: optional string code;    	 
    /** 组织名(boss对应方付通部门，bank对应机构名，merchant对应商户名)  */  
    9: optional string orgName;    		 
    /** 电话号码  */  
    10: optional string phone;			
    /** 区域Id */  
    11: optional i64 areaId;
    /** 地址信息 */
    12: optional string address;
    /** 是否删除 */
    13: optional bool isDelete;
}


/**
 * 组织查询响应VoRes
 * FFTOrgListVoRes
 */
struct FFTOrgListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 组织信息集合*/
	2:list<FFTOrgVo> voList;
}

/**
 * 组织查询响应VoRes-带上级名称、treePath名称的响应
 * FFTOrgNameListVoRes
 */
struct FFTOrgNameListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 组织信息集合*/
	2:list<FFTOrgDetailVo> voList;
}

/**
 * 组织分页查询VoRes
 */
struct FFTOrgPageVoRes{
	/** 分页page  */  
	1:Common.PageVo page;
	/**返回结果*/
	2:Common.ResultVo resultVo;
	/** 返回结果list  */  
	3:list<FFTOrgDetailVo> voList;
}

/**
 *  详情组织Vo
 *  FFTOrgDetailVo
 */
struct FFTOrgDetailVo {
    /** 主键id  */ 
    1: optional i64 id;    	 	 
    /** 组织Id  */  
    2: optional string orgId;     
    /** 父级组织代码，顶级组织的父级组织Id为0  */  
    3: optional i64 parentId;
    /** 父级组织名称  */  
    4: optional string parentName;       	 
    /** 组织级别  */  
    5: optional i32 level;    
    /** 平台名称(boss、bank、merchant)  */  
    6: optional string platform;    		
    /** 所属客户端  */   
    7: optional string clientId;    	 
    /** 树路径(从顶级到自己本身的ID，逗号分隔)  */ 
    8: optional string treePath;
    /** 树路径名称(从顶级到自己本身的orgName，逗号分隔)  */ 
    9: optional string treePathName;         
    /** 组织代码(boss对应主键ID，bank对应机构号，merchant对应商户Id)  */  
    10: optional string code;    	 
    /** 组织名(boss对应方付通部门，bank对应机构名，merchant对应商户名)  */  
    11: optional string orgName;    		 
    /** 电话号码  */  
    12: optional string phone;			
    /** 区域Id */  
    13: optional i64 areaId;
    /** 地址信息 */
    14: optional string address;
    /** 是否删除 */
    15: optional bool isDelete;
}

/**
 *  权限组织Vo
 *  FFTOrgReVo
 */
struct FFTOrgReVo {
    /** 主键id  */ 
    1: optional i64 id;    	 	 
    /** 组织Id  */  
    2: optional string orgId;     
    /** 权限组织Id  */  
    3: optional string reOrgId;
    /** 组织代码(boss对应主键ID，bank对应机构号，merchant对应商户Id)  */  
    4: optional string code;      	
    /** 平台名称(boss、bank、merchant)  */  
    5: optional string platform;
    /** 权限组织id对应的树路径名称(从顶级到自己本身的orgName，逗号分隔)  */  
    6: optional string reOrgIdTreePathName;
    /** 权限组织id对应的树路径(从顶级到自己本身的ID，逗号分隔)  */ 
    7: optional string reOrgIdtreePath;     
}


/**
 * 权限组织查询响应VoRes
 * FFTOrgReListVoRes
 */
struct FFTOrgReListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 权限组织信息集合*/
	2:list<FFTOrgReVo> voList;
}

/**
 *  组织角色Vo
 *  OrgRoleVo
 */
struct OrgRoleVo {
    /** 主键id  */ 
    1: optional i64 id;    	 	 
    /** 组织Id  */  
    2: optional string orgId;     
    /** 角色Id  */  
    3: optional i64 roleId;
    /** 角色名称  */  
    4: optional string roleName;      	
}

/**
 * 组织角色查询响应VoRes
 * OrgRoleListVoRes
 */
struct OrgRoleListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 组织角色信息集合*/
	2:list<OrgRoleVo> voList;
}

/**
 * 组织角色查询响应VoRes
 * OrgRoleIdListVoRes
 */
struct OrgRoleIdListVoRes {
	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	/** 组织角色id信息集合*/
	2:list<i64> roleIdVoList;
}


/**
 * 验证组织下是否有下级组织对象
 * isNextFFTOrgVo
 */
struct isNextFFTOrgVo {
	/**返回结果*/
	1:bool isNextFFTOrg;
	
	/**组织对应的主键id */
	2:i64 id;
}
