/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo


/**
 *---------------------------------------------------------审核服务--------------------------------------
 */
 
/**
 * 待审核数量对象Vo
 * PreAuditNumVo
 */
struct PreAuditNumVo {
	/** 待审核积分兑换商品数量 */
    1: optional i32 preauditDuihuan;   
    /** 待审核名优特惠数量  */
    2: optional i32 preauditMingYou;    
    /** 待审核商户数量 */
    3: optional i32 preauditMerchant; 	
    /** 待审核预售商品数量 */ 
    4: optional i32 preauditPresell; 	
    /** 待审核团购商品数量  */ 
    5: optional i32 preauditGroup;   	
    /** 待审核秒杀商品数量  */ 
    6: optional i32 preauditSeckill;
    
}
/**
 * 审核对象
 * BankAuditVo
 */
struct BankAuditVo {
	/** 审核对象id(可传商品id或商户id)  */ 
    1: optional string auditId;    		
    /** 客户端id  */ 
    2: optional string clientId;    		
    /** 商品审核状态(1审核通过,2审核未通过)  */ 
    3: optional string auditState; 		
    /** 审核人username  */ 
    4: optional string auditStaff; 		
    /** 审核备注  */  
    5: optional string auditComment; 	
    
    
}

/**
 * 审核后的响应对象
 */
struct BankAuditVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 是否最终审核 */
	2:bool isFinalAudit;
}


/**
 *---------------------------------------------------------银行操作日志服务--------------------------------------
 */
 
/**
 * 分页操作日志对象VoRes
 * list<BankOperateLogVo>
 */
struct BankOperateLogPageVoRes {
	1:Common.PageVo page;  /** 分页对象page */
	2:list<BankOperateLogVo> bankOperateLogVoList; /** 多条操作日志List */
}


/**
 * 用户操作记录Vo
 * BankOperateLogVo
 */
struct BankOperateLogVo {
	/** 主键id  */  
	1: optional i64 id; 			
	/** 创建时间  */  
    2: optional i64 createTime;    	
    /** 平台来源  */   
    3: optional i32 platType;    	
    /** 用户id(条件)  */   
    4: optional i64 userId;    		
    /** 用户名称 */  
    5: optional string username; 	
    /** 角色名称  */  
    6: optional string roleName;	
    /** 操作说明  */  
    7: optional string description; 
    /** 客户端id  */   
    8: optional string clientId;   
    /** 机构编号码(条件)  */   
    9: optional string orgCode;     
    /** 机构名称  */  
    10: optional string orgName;  	
    /** 操作ip  */  
    12: optional string operatorIp;	
    /** 是否登录日志  */  
    13: optional bool logType;	
    /** 开始时间(条件)  */  
    14: optional i64 startDate; 	
    /** 结束时间(条件)  */  
    15: optional i64 endDate;  	
    	
    
}


/**
 *---------------------------------------------------------银行用户管理服务--------------------------------------
 */

/**
 * 银行用户分页VoRes
 * BankOperatorPageVoRes
 */
struct BankOperatorPageVoRes {
	/** 分页page  */  
	1:Common.PageVo page;
	/** 返回结果list  */  
	2:list<BankOperatorVo> bankOperatorVoList;
}

/**
 * 登录返回对象VoRes
 * LoginBankOperatorVoRes
 */
struct LoginBankOperatorVoRes {
	/** 结果集vo  */  
	1:Common.ResultVo result;				
	/** 登录失败次数  */  
	2:i32 loginFailureCount;		
	/** token值  */  
	3:string token; 				
	/** 银行用户vo  */  
	4:BankOperatorVo bankOperator;  
	
}

/**
 * token检验返回对象
 * BankOperatorCheckVoRes
 */
struct BankOperatorCheckVoRes {
	/** 结果集vo  */  
	1:Common.ResultVo result;	
	/** 银行用户vo  */  
	2:BankOperatorVo bankOperator;  
}


/**
 * 银行用户信息Vo
 * BankOperatorVo
 */
struct BankOperatorVo {
	/** 主键id  */  
    1: optional i64 id;    				 
    /** 创建时间  */  
    2: optional i64 createTime;    		
    /** 客户端id  */   
    3: optional string clientId;    		
    /** 登录名  */  
    4: optional string username;    	
    /** 登录密码  */   
    5: optional string password;    	 
    /** 机构编号  */   
    6: optional string orgCode;   		
    /** 手机号  */   
    7: optional string mobile;    		
    /** email  */   
    8: optional string email;    		  
    /** 操作员姓名  */  
    9: optional string name;   	 		 
    /** 角色id  */   
    10: optional i64 roleId;    		
    /** 部门  */  
    11: optional string department;   	 
    /** 职位  */   
    12: optional string position;  		 
    /** 0-不可用 1-可用  */   
    13: optional bool status;    		
    /** 密码是否修改 1-是 0-否(0时弹出框强制性修改密码)  */   
    14: optional bool isReset;    		
    /** 备注  */   
    15: optional string remark;    		
    /** 是否删除 0-未删除 1-删除  */   
    16: optional bool isDelete;  
    /** 用户类型：1、平台用户 2、银行用户 */   
    17: optional string type;    		
    /** 最后登录ip  */   
    18: optional string lastLoginIp; 
    /** 最后登录时间  */   
    19: optional i64 lastLoginTime;   	  		 
}

/**
 *---------------------------------------------------------银行资源服务--------------------------------------
 */

/*
 * 银行资源分页VoRes
 * BankResourcePageVoRes
 */
struct BankResourcePageVoRes {
 	/** 分页page  */ 
	1:Common.PageVo page;
	 /** 查询结果list  */ 
	2:list<BankResourceVo> bankResourceVoList;
}



/**
 * 银行资源Vo
 * BankResourceVo
 */
struct BankResourceVo {
 	/** 资源id 主键id  */ 
    1: optional i64 id;    				
    /** 客户端id  */ 
    2: optional string clientId;    	 
    /** 资源名称  */  
    3: optional string resourceName;     
    /** 0:菜单1:按钮  */  
    4: optional bool resourceType;    	 
    /** 父级资源Id(顶级资源的父资源ID为0)  */  
    5: optional i64 parentResourceId;    
    /** 0-不可用 1-可用  */  
    6: optional bool status;    		
    /** 资源url  */   
    7: optional string resourceUrl;    	 
    /** 资源图标  */ 
    8: optional string resourceIcon;     
    /** 资源路径(路径连接符为空格)  */  
    9: optional string treePath;    	 
    /** 是否删除 0-未删除 1-删除  */  
    10: optional bool isDelete;    		 
    /** 资源接口  */  
    11: optional string api;			
    /** 资源排序  */  
    12: optional i32 orderValue;	
}

/**
 *---------------------------------------------------------银行角色资源关系服务--------------------------------------
 */

/**
 * 银行角色资源关系MongoVo
 * BankUserResourceVo
 */
struct BankUserResourceVo {
	/** 资源id 主键id  */
    1: optional string id;    								
    /** 客户端id   */
    2: optional list<ResourcesInfoVo> resources;    		
    
}

/**
 * 银行角色资源Vo
 * ResourcesInfoVo
 */
struct ResourcesInfoVo {
	/** 资源ID   */
	1: optional  i64	resourceId;		
	/** 资源名称   */
	2: optional  string resourceName;	
	/** 资源类型   */
	3: optional  string resourceType;	 
	/** 资源路径   */
	4: optional  string treePath;		
	/** 资源图标   */
	5: optional  string resourceIcon; 	
	/** 资源url   */
	6: optional  string resourceUrl; 	 
	/** 父级资源id   */
	7: optional  i64 	parentResourceId; 	
	/** 资源接口   */
	8: optional  string api;	
	/** 资源排序   */
	9: optional  i32 orderValue;		
}


/**
 *---------------------------------------------------------银行角色服务--------------------------------------
 */

/**
 * 银行角色分页对象VoRes
 * BankResourceVo
 */
struct BankRolePageVoRes {
	/** 分页page   */
	1:Common.PageVo page;
	/** 结果list   */
	2:list<BankRoleVo> bankRoleVoList;
}



/**
 * 银行角色Vo
 * BankRoleVo
 */
struct BankRoleVo {
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
    /** 标明角色身份，身份比如管理员、操作员(管理员暂时定为0)   */ 
    7: optional string tag; 
    /** 创建角色的机构号 */
    8: optional string orgCode;		 
}


/**
 *---------------------------------------------------------客户端支付渠道服务--------------------------------------
 */

/**
 * 客户端分页对象VoRes
 */
struct ClientPaymentChannelPageVoRes {
	/** 分页page   */ 
	1:Common.PageVo page;
	/** 结果list   */ 
	2:list<ClientPaymentChannelVo> clientPaymentChannelVoList;
}



/**
 * 客户端支付渠道Vo
 */
struct ClientPaymentChannelVo {
	/** 主键id   */ 
    1: optional i64 id;    					 
    /** 客户端id   */ 
    2: optional string clientId;   			 
    /** 支付渠道id   */  
    3: optional string paymentChannelId;     
    /** 资金机构名称   */  
    4: optional string name;   				 
    /** 资金机构全名   */  
    5: optional string fullName;    		
    /** 渠道类型   */  
    6: optional string type;    			
    /** 支付方式图标   */  
    7: optional string icoUrl;    			 
    /** 资金机构代号（支付系统）   */  
    8: optional string paymentOrgNo;    	 
    /** 是否方付通验码   */  
    9: optional bool isFroadCheckToken;    	 
    /** 积分兑换比例(实时获取)   */  
    10: optional string pointRate;    		 
    /** 是否删除    */  
    11: optional bool isDelete;    			 
}


/**
 *---------------------------------------------------------客户端服务--------------------------------------
 */
 
/*
 * 客户端分页对象VoRes
 */
struct ClientPageVoRes {
	/** 分页page    */ 
	1:Common.PageVo page;
	/** 结果list    */ 
	2:list<ClientVo> clientVoList;
}



/**
 * 客户端Vo
 */
struct ClientVo {
	/** 主键id    */
    1: optional i64 id;   		 			  
    /** 客户端id    */
    2: optional string clientId;    		 
    /** 客户端uri    */ 
    3: optional string uri;					 
    /** 创建时间    */ 
    4: optional i64 createTime;    			 
    /** 客户端名称    */ 
    5: optional string name;    			 
    /** 积分平台商户号    */ 
    6: optional string pointPartnerNo;  	
    /** 支付平台商户号    */ 
    7: optional string openapiPartnerNo;     
    /** 订单显示名    */ 
    8: optional string orderDisplay;    	 
    /** APPKEY（手机客户端）    */ 
    9: optional string appkey;    			 
    /** 私钥（手机客户端）    */ 
    10: optional string appsecret;    		 
    /** 银行类型  0-重庆模式 1-安徽模式   */ 
    11: optional string bankType;    		 
    /** 银行名    */ 
    12: optional string bankName;    		 
    /** 银行logo图片地址    */ 
    13: optional string qrLogo;    			 
    /** 支付结果通知地址    */ 
    14: optional string returnUrl;   		 
    /** 备注    */ 
    15: optional string remark;    			
    /** 积分平台银行代码    */ 
    16: optional string bankId;	
    /** 结算组织机构代码    */ 
    17: optional string settlePayOrg;				 
    /** vip所属银行标签    */ 
    18: optional string bankOrg;
    /** 银行组号    */ 
    19: optional string bankZH;
}


/**
 *---------------------------------------------------------银行机构服务--------------------------------------
 */

/**
 * 机构信息分页对象VoRes
 */
struct OrgPageVoRes {
	/** 分页page    */ 
	1:Common.PageVo page;
	/** 结果list    */ 
	2:list<OrgVo> orgVoList;
}

/**
 * 机构信息Vo
 */
struct OrgVo {
	/** 主键id    */ 
    1: optional i64 id;    					
    /** 客户端id    */ 
    2: optional string clientId;    			 
    /** 银行类型 0代表省联社-市联社-县联社-网点，1代表省联社-市联社-网点，2代表市联社-网点，3县联社-网点    */  
    3: optional string bankType;    		
    /** 机构编号    */  
    4: optional string orgCode;   			
    /** 机构名称    */  
    5: optional string orgName;    			
    /** 一级org_code    */  
    6: optional string provinceAgency;    	 
    /** 二级org_code    */  
    7: optional string cityAgency;    		
    /** 三级org_code    */  
    8: optional string countyAgency;    	 
    /** 联系电话    */  
    9: optional string phone;    			 
    /** 机构对应商户id    */  
    10: optional string merchantId;    		 
    /** 机构对应门店id    */  
    11: optional string outletId;    		 
    /** 机构地区    */  
    12: optional i64 areaId;    			 
    /** 机构级别1-2-3-4-    */  
    13: optional string orgLevel;    		 
    /** 是否需要双人审核    */ 
    14: optional bool needReview;    		 
    /** 0-部门机构，1-业务机构    */ 
    15: optional bool orgType;    			 
    /** 是否有效    */  
    16: optional bool isEnable;    			 
    /** 上级机构名称(用于前端显示)    */  
    17: optional string superOrgName;		 
}


/**
 *---------------------------------------------------------机构级别角色关系服务--------------------------------------
 */
 
/**
 * 银行联合登录-机构级别角色关系分页对象VoRes
 */
struct OrgLevelPageVoRes {
	/** 分页page    */ 
	1:Common.PageVo page;
	/** 结果list    */ 
	2:list<OrgLevelVo> orgLevelVoList;
}


/**
 * 银行联合登录-机构级别角色关系Vo
 */
struct OrgLevelVo {
	/** 主键id    */ 
    1: optional i64 id;    			
    /** 客户端Id   */ 
    2: optional string clientId;   	
    /** 角色id    */ 
    3: optional i64 roleId;    		
    /** 机构级别    */ 
    4: optional string orgLevel;    	
}

/**
 *---------------------------------------------------------银行联合登录帐号服务--------------------------------------
 */

/**
 * token检验返回对象
 * OrgUserRoleCheckVoRes
 */
struct OrgUserRoleCheckVoRes {
	/** 结果集vo  */  
	1:Common.ResultVo result;	
	/** 联合登录用户vo  */  
	2:OrgUserRoleVo orgUserRole;  
}


/**
 * 银行联合登录帐号分页对象VoRes
 */
struct OrgUserRolePageVoRes {
	/** 分页page    */ 
	1:Common.PageVo page;
	/** 结果list    */ 
	2:list<OrgUserRolePageVo> orgUserRolePageVoList;
}

/**
 * 银行联合登录帐号分页对象Vo
 */
struct OrgUserRolePageVo {
	/** 主键id    */ 
    1: optional i64 id;    			
    /** 客户端id    */  
    2: optional string clientId;    	 
    /** 角色名称    */  
    3: optional string roleName;    
    /** 角色id    */  
    4: optional i64 roleId;    		 
    /** 机构编号    */  
    5: optional string orgCode;
    /** 机构名称    */  
    6: optional string orgName;      	
    /** 登录名   */  
    7: optional string username;     
    /** 机构级别    */  
    8: optional string orgLevel;    	  
}



/**
 * 银行联合登录返回对象VoRes
 * LoginOrgUserRoleVoRes
 */
struct LoginOrgUserRoleVoRes {
	/** 结果集vo  */  
	1:Common.ResultVo result;				
	/** 登录失败次数  */  
	2:i32 loginFailureCount;		
	/** token值  */  
	3:string token; 				
	/** 银行联合登录帐号vo    */ 
	4:OrgUserRoleVo orgUserRole;  
	
}

/**
 * 银行联合登录帐号Vo
 */
struct OrgUserRoleVo {
	/** 主键id    */ 
    1: optional i64 id;    			
    /** 客户端id    */  
    2: optional string clientId;    	 
    /** 角色名称    */  
    3: optional string roleName;    
    /** 角色id    */  
    4: optional i64 roleId;    		 
    /** 机构编号    */  
    5: optional string orgCode;    	
    /** 登录名   */  
    6: optional string username;     
    /** 机构级别    */  
    7: optional string orgLevel;    	  
}

/**
 *---------------------------------------------------------商户审核配置服务--------------------------------------
 */

/**
 * 商户审核配置分页VoRes
 */
struct ClientMerchantAuditPageVoRes {
	/** 分页page    */ 
	1: optional Common.PageVo page;
	/** 结果list    */ 
	2: optional list<ClientMerchantAuditVo> clientMerchantAuditVoList;
}
/**
 * 商户审核配置Vo
 */
struct ClientMerchantAuditVo {
	/** 主键id    */ 
    1: optional i64 id;   				
    /** 客户端id    */ 
    2: optional string clientId;    	
    /** 类型 1-审核  2-商户密码重置    */ 
    3: optional string type;    	
    /** 商户所属机构级别    */ 
    4: optional string orgLevel;  	 	
    /** 起始机构级别    */ 
    5: optional string startOrgLevel;   
    /** 终审机构级别    */ 
    6: optional string endOrgLevel;    	
}
/**
 * 商户审核配置Vo返回orgCode
 */
struct ClientMerchantAuditOrgCodeVo {
	/** 主键id    */ 
    1: optional i64 id;   				
    /** 客户端id    */ 
    2: optional string clientId;    
    /** 类型 1-审核  2-商户密码重置    */ 
    3: optional string type;    		
    /** 商户所属机构级别    */ 
    4: optional string orgLevel;  	 	
    /** 起始机构编号    */ 
    5: optional string startAuditOrgCode;    
    /** 终审机构编号    */ 
    6: optional string endAuditOrgCode;    	
}

/**
 *---------------------------------------------------------商品审核配置服务--------------------------------------
 */
 
/**
 * 商品审核配置分页VoRes
 */
struct ClientProductAuditPageVoRes {
	/** 分页page    */ 
	1: optional Common.PageVo page;
	/** 结果list    */ 
	2: optional list<ClientProductAuditVo> clientProductAuditVoList;
}
/**
 * 商品审核配置Vo
 */
struct ClientProductAuditVo {
	/** 主键id    */ 
    1: optional i64 id;    				
    /** 客户端id    */ 
    2: optional string clientId;    	
    /** 商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)    */ 
    3: optional string productType;     
    /** 商品所属商户所属机构级别    */ 
    4: optional string orgLevel;    	
    /** 起始机构级别    */ 
    5: optional string startOrgLevel;   
    /** 终审机构级别    */ 
    6: optional string endOrgLevel;    	
}

/**
 * 商品审核配置Vo返回orgCode
 */
struct ClientProductAuditOrgCodeVo {
	/** 主键id    */ 
    1: optional i64 id;    				
    /** 客户端id   */ 
    2: optional string clientId;    	
    /** 商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)    */ 
    3: optional string productType;     
    /** 商品所属商户所属机构级别    */ 
    4: optional string orgLevel;    	
    /** 起始机构编号    */ 
    5: optional string startAuditOrgCode;    
    /** 终审机构编号    */ 
    6: optional string endAuditOrgCode;    	
}


/**
 *---------------------------------------------------------审核任务订单服务--------------------------------------
 */

/**
 * 审核任务订单分页VoRes
 */
struct AuditTaskPageVoRes {
	/** 分页page    */ 
	1: optional Common.PageVo page;
	/** 结果list    */ 
	2: optional list<AuditTaskPageDetailVo> auditTaskVoList;
}

/**
 * 待审核任务订单详情vo
 */
struct AuditTaskPageDetailVo {
	/** 审核流水号    */ 
    1: optional string auditId; 
    /** 创建时间    */ 
    2: optional i64 createTime; 
     /** 业务类型  1-商户审核  */ 
    3: optional string type; 
    /** 商户名称   */ 
    4: optional string name; 
    /** 审核状态    0-待审核 1-审核通过 2-审核不通过*/ 
    5: optional string auditState; 
    /** 创建机构    */ 
    6: optional string orgName; 
    /** 创建机构的上级机构    */ 
    7: optional string superOrgName; 
    /** 归档时间    */ 
    8: optional i64 auditTime; 
    /** 客户端id    */ 
    9: optional string clientId;   
}


/**
 * 待审核任务订单详情vo
 */
struct AuditTaskVo {
	/** 审核流水号    */ 
    1: optional string auditId; 
    /** 审核状态    0-待审核 1-审核通过 2-审核不通过*/ 
    2: optional string auditState; 
    /** 创建时间    */ 
    3: optional i64 createTime; 
    /** 商户名称   */ 
    4: optional string name; 
    /** 业务号码    */ 
    5: optional string busCode; 
    /** 创建人    */ 
    6: optional string userName; 
    /** 创建机构orgCode    */ 
    7: optional string orgCode;   
    /** 创建机构    */ 
    8: optional string orgName; 
    /** 归档时间    */ 
    9: optional i64 auditTime; 
    /** 业务类型  1-商户审核  */ 
    10: optional string type; 
    /** 客户端id    */ 
    11: optional string clientId;
}


/**
 * 审核任务订单查询过滤对象vo
 */
struct AuditTaskFilterVo {
	/** 审核流水号    */ 
    1: optional string auditId; 
    /** 业务类型    1-商户审核*/ 
    2: optional string type; 
    /** 所属机构：若没选择，则传当前登录人所属orgCode    */ 
    3: optional string orgCode; 
    /** 创建开始时间    */ 
    4: optional i64 startCreateTime;
    /** 创建结束时间    */ 
    5: optional i64 endCreateTime;  
    /** 商户名称   */ 
    6: optional string name; 
    /** 业务号码    */ 
    7: optional string busCode; 
    /** 查在途/查归档   0-在途 1-归档*/ 
    8: optional string state; 
    /** 归档开始时间    */ 
    9: optional i64 startAuditTime; 
    /** 归档结束时间    */ 
    10: optional i64 endAuditTime; 
    /** 客户端id    */ 
    11: optional string clientId;   
}



/**
 *---------------------------------------------------------审核任务服务--------------------------------------
 */

/**
 * 待审核任务记录详情vo
 */
struct AuditProcessVo {
	/** 任务流水号   */ 
    1: optional string taskId; 
    /** 创建时间    */ 
    2: optional i64 createTime; 
    /** 审核时间    */ 
    3: optional i64 auditTime; 
    /** 审核机构    */ 
    4: optional string orgName; 
    /** 审核人    */ 
    5: optional string auditStaff; 
    /** 状态    0-待审核 1-审核通过 2-审核不通过*/ 
    6: optional string auditState; 
    /** 备注    */ 
    7: optional string auditComment; 
    /** 客户端id    */ 
    8: optional string clientId;   
}






/**
 *---------------------------------------------------------多银行配置服务--------------------------------------
 */

/**
 * 添加数据返回主键id公共响应
 */
struct BankAccessModuleListRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 主键id */
	2:list<BankAccessModuleListVo> moduleList;
}

/**
 * 客户端功能列表vo
 */
struct BankAccessModuleListVo {
	/** id */ 
    1: optional string id; 
    /** 客户端Id*/ 
    2: optional string clientId; 
    /** 功能模块类型1:特惠商户;2:特惠商品;3:精品预售;4:扫码支付；5:积分兑换；*/ 
    3: optional string type; 
    /**模块名称*/ 
    4: optional string moduleName; 
    /** 模块别名*/ 
    5: optional string moduleAlias; 
    /** 模块图标url*/ 
    6: optional string iconUrl; 
    /**排序值*/ 
    7: optional string sortValue; 
}

/**
 * 添加Clien客户端t响应
 */
struct ClientAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** clientId */
	2:string clientId;
}

/**
 * 添加ClientPaymentChannel支付渠道响应
 */
struct ClientPaymentChannelAddVoRes {

	/** 响应基础信息 */
	1:Common.ResultVo result;
	
	/** 主键payment_channel_id */
	2:string paymentChannelId;
}
