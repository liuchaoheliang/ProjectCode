/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo.finity

/**
* -------------------------------------资源服务---------------------------------
*/

/**
 *  资源Vo
 *  FinityResourceVo
 */
struct FinityResourceVo {
    /** 资源id 主键id  */ 
    1: optional i64 id;    	 	 
    /** 资源名称  */  
    2: optional string resourceName;     
    /** 0:菜单1:按钮  */  
    3: optional i32 type;    	 
    /** 父级资源Id(顶级资源的父资源ID为0)  */  
    4: optional i64 parentResourceId;    
    /** 0-不可用 1-可用  */  
    5: optional bool status;    		
    /** 资源url  */   
    6: optional string resourceUrl;    	 
    /** 资源图标  */ 
    7: optional string resourceIcon;     
    /** 资源路径  */  
    8: optional string treePath;    	 
    /** 是否删除 0-未删除 1-删除  */  
    9: optional bool isDelete;    		 
    /** 资源接口  */  
    10: optional string api;			
    /** 资源排序  */  
    11: optional i32 orderValue;
    /** 资源KEY*/
    12: optional string resourceKey;
    /** 是否是系统资源 是否系统资源1是 0否*/
    13: optional bool isSystem;
    /** 是否为菜单:1是，0否*/
    14: optional bool isMenu;
    /** 资源所属平台 boss bank merchant*/
    15: optional string platform;
    /** 子集资源列表*/
    16: optional list<FinityResourceVo> finityResourceList;
    
    
    /** 是否需要数据权限控制 0-否 1-是  */  
    17:optional bool isLimit;   
    /** 更新时间  */  
    18:optional i64 updateTime;
}


/**
 * 角色Vo
 * FinityRoleVo
 */
struct FinityRoleVo {
    /** 主键id 角色id    */
    1: optional i64 id;    			
    /** 客户端id   */
    2: optional string clientId;    	
    /** 角色名称   */ 
    3: optional string roleName;     
    /** 0-不可用 1-可用   */ 
    4: optional bool status;    	 
    /** 备注   */ 
    5: optional string remark;    	 
    /** 是否删除 0-未删除 1-删除   */ 
    6: optional bool isDelete;    	 
    /** 标明角色身份，0法人行社管理员  2管理员 1普通角色    */ 
    7: optional string tag; 
    /** 创建角色的机构号 */
    8: optional string orgCode;		 
    
    
    /** 1-代表系统管理员0-代表其他   */
    9: optional bool isAdmin;  
    /** 添加角色的用户   */ 
    10: optional i64 userId; 
    /** 平台名称(boss、bank、merchant)   */ 
    11: optional string platform;
}


struct FinityRoleListVoRes {

	/**返回结果*/
	1:Common.ResultVo resultVo;
	
	2:list<FinityRoleVo> voList;
}

/**
 * 商户资源信息
 */
struct UserResourceVo{
	 /** 用户Id */
	 1: optional i64 userId;
	 /** 资源Id */
	 2: optional i64 resourceId;
	 /** 用户类型 */
	 3: optional i32 userType;
}

 
