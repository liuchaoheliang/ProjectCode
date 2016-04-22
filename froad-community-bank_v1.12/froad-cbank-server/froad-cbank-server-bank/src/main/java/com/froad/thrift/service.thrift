/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入struct.thrift文件 */
include "struct.thrift"




namespace java com.froad.thrift.service


/**
 *---------------------------------------------------------审核服务--------------------------------------
 */

/**
 * 审核服务接口
 * BankAuditService
 */
service BankAuditService extends BizMonitor.BizMonitorService{

	/**
     * 批量审核(商品和商户)
     * @param auditFlag 标识(审核标志 1-商品 2-商户)
     * @param bankAuditVoList 审核对象集合
     * @return 审核失败的审核对象id及名称(id:name;)  审核成功返回OK  审核异常返回ERROR
     */
    struct.BankAuditVoRes auditBatch(1:Common.OriginVo originVo,2:i32 auditFlag,3:list<struct.BankAuditVo> bankAuditVoList);
     
     
 	/**
     * 返回待审核数量
     * @param clientId 客户端id
     * @param orgCode 机构编号
     * @return 
     */
	 struct.PreAuditNumVo getPreAuditNumRes(1:string clientId,2:string orgCode);
}


/**
 *---------------------------------------------------------银行操作日志服务--------------------------------------
 */
 
/**
 * 操作日志服务接口
 * BankOperateLogService
 */
service BankOperateLogService extends BizMonitor.BizMonitorService{


    /**
     * 分页查询 BankOperateLog
     * @param page 分页信息
     * @bankOperateLogVo 操作日志对象
     * @return BankOperatorPageVoRes 分页结果对象
     */
    struct.BankOperateLogPageVoRes getBankOperateLogByPage(1:Common.PageVo page, 2:struct.BankOperateLogVo bankOperateLogVo);
    
}


/**
 *---------------------------------------------------------银行用户管理服务--------------------------------------
 */
/**
 * 银行操作员服务接口
 * BankOperatorService
 */
service BankOperatorService extends BizMonitor.BizMonitorService{

    /**
     * 增加 BankOperator
     * @param bankOperator
     * @return long    主键ID(异常及错误返回-1)
     */
    Common.CommonAddVoRes addBankOperator(1:Common.OriginVo originVo,2:struct.BankOperatorVo bankOperatorVo);

    /**
     * 删除 BankOperator
     * @param bankOperator
     * @说明：根据主键id和cliend_id进行删除操作，cliend_id可传可不传，银行管理平台需传，boss可不传)
     * @return boolean   操作是否成功
     */
    Common.ResultVo deleteBankOperator(1:Common.OriginVo originVo,2:struct.BankOperatorVo bankOperatorVo);

    /**
     * 修改 BankOperator
     * @param bankOperator(根据主键id和cliend_id进行删除操作)
     * @说明：根据主键id和cliend_id进行删除操作，cliend_id可传可不传，银行管理平台需传，boss可不传)
     * @return boolean   操作是否成功
     */
    Common.ResultVo updateBankOperator(1:Common.OriginVo originVo,2:struct.BankOperatorVo bankOperatorVo);

	/**
     * 银行用户登录
     * @param username 登录名
     * @param password 密码
     * @param token值 (用户第一次登录可为空后续登录验证操作需传token值过来验证)
     * @param operatorIp 登录ip
     * @return LoginBankOperatorVoRes 登录成功后返回结果对象
     */
    struct.LoginBankOperatorVoRes loginBankOperator(1:Common.OriginVo originVo,2:string username,3:string password);

    /**
     * 银行用户登录获取错误次数
     * @param username 登录名
     * @param token值 (用户第一次登录可为空后续登录验证操作需传token值过来验证)
     * @param operatorIp 登录ip
     * @return LoginBankOperatorVoRes 登录成功后返回结果对象
     */
    struct.LoginBankOperatorVoRes getLoginFailureCount(1:Common.OriginVo originVo,2:string username);
    
    /**
     * token校验
     * @param clientId 客户端Id
     * @param userId 用户编号
     * @param token 类似uuid的值
     * @return   BankOperatorCheckVoRes结果
     */
    struct.BankOperatorCheckVoRes checkToken(1:string clientId,2:i64 userId,3:string token);
    
    
    /**
     * 根据id查询单个银行管理员信息
     * @param clientId 客户端id(可为0,boss查的时候可传clientId值为0过来，银行管理平台需传具体值过来)
     * @param userId 用户id
     * @return BankOperatorVo 返回银行用户信息
     */
    struct.BankOperatorVo getBankOperatorById(1:string clientId,2:i64 userId);
    
    
    /**
     * 查询 BankOperator
     * @param bankOperator 查询条件
     * @return List<BankOperatorVo> 银行用户List
     */
    list<struct.BankOperatorVo> getBankOperator(1:struct.BankOperatorVo bankOperatorVo);

    /**
     * 分页查询 BankOperator
     * @param page 查询条件
     * @param bankOperator 查询条件
     * @return BankOperatorPageVoRes
     */
    struct.BankOperatorPageVoRes getBankOperatorByPage(1:Common.PageVo page, 2:struct.BankOperatorVo bankOperatorVo);
    
     /**
     * 分页查询 BankOperator 只返回当前机构下的
     * @param page 查询条件
     * @param bankOperator 查询条件
     * @return BankOperatorPageVoRes
     */
    struct.BankOperatorPageVoRes getBankOperatorByPageInCurrentOrg(1:Common.PageVo pageVo, 2:struct.BankOperatorVo bankOperatorVo);

    /**
     * 银行用户退出
     * @param token
     * @return bool 是否成功
     */
    Common.ResultVo logoutBankOperator(1:string token);
}


/**
 * 法人行社管理员服务接口
 * ArtificialPersonService
 */
service ArtificialPersonService extends BizMonitor.BizMonitorService{
	
	/**
     * 批量生成法人行社管理员接口(一个法人行社orgCode只能生成一个管理员用户)
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @param defaultPassword 初始密码
     * @param prefix 登录名前缀
     * @return 批量生成失败的orgCode及名称(orgCode:orgName;)  生成成功返回OK  审核异常返回ERROR 
     */
	Common.ResultVo addArtificialPerson(1:Common.OriginVo originVo,2:string clientId, 3:list<string> orgCodes, 4:string defaultPassword, 5:string prefix);

	/**
     * 批量删除法人行社管理员账号
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @return Boolean    是否成功
     */
	Common.ResultVo deleteArtificialPerson(1:Common.OriginVo originVo,2:string clientId, 3:list<string> orgCodes);
	
	 /**
     * 批量重置法人行社管理员密码
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @param defaultPassword 初始密码
     * @return Boolean    是否成功
     */
	Common.ResultVo updateArtificialPerson(1:Common.OriginVo originVo,2:string clientId, 3:list<string> orgCodes, 4:string defaultPassword);


	
	/**
     * 法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 机构编号
     * @return List<OrgVo> 法人行社org对象
     */
	list<struct.OrgVo> getArtificialPerson(1:string clientId, 2:string orgCode);
	
	
    /**
     * 已生成管理员的法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 机构编号
     * @return List<OrgVo> 已生成的法人行社org对象
     */
	list<struct.OrgVo> getArtificialPersonByAdd(1:string clientId, 2:string orgCode);

	/**
     * 未生成管理员的法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 机构编号
     * @return List<OrgVo> 未生成的法人行社org对象
     */
	list<struct.OrgVo> getArtificialPersonByNotAdd(1:string clientId, 2:string orgCode);


}


/**
 *---------------------------------------------------------银行资源服务--------------------------------------
 */

/**
 * 银行资源服务接口
 * BankResourceService
 */
service BankResourceService extends BizMonitor.BizMonitorService{

    /**
     * 增加 BankResource
     * @param bankResource
     * @return long    主键ID(异常及错误返回-1)
     */
    Common.CommonAddVoRes addBankResource(1:Common.OriginVo originVo,2:struct.BankResourceVo bankResourceVo);

    /**
     * 删除 BankResource
     * @param bankResource(根据资源id进行删除操作)
     * @return boolean   操作是否成功
     */
    Common.ResultVo deleteBankResource(1:Common.OriginVo originVo,2:i64 id);

    /**
     * 修改 BankResource
     * @param bankResource(根据资源id进行修改操作)
     * @return boolean   操作是否成功
     */
    Common.ResultVo updateBankResource(1:Common.OriginVo originVo,2:struct.BankResourceVo bankResourceVo);

    /**
     * 查询 BankResource
     * @param bankResource 过滤条件
     * @return List<BankResourceVo>
     */
    list<struct.BankResourceVo> getBankResource(1:struct.BankResourceVo bankResourceVo);

    /**
     * 分页查询 BankResource
     * @param bankResource过滤条件
     * @return BankResourcePageVoRes 分页查询结果对象
     */
    struct.BankResourcePageVoRes getBankResourceByPage(1:Common.PageVo page, 2:struct.BankResourceVo bankResourceVo);
}


/**
 *---------------------------------------------------------银行角色资源关系服务--------------------------------------
 */
 
 
 /**
 * 银行角色资源关系服务接口
 * BankRoleResourceService
 */
service BankRoleResourceService extends BizMonitor.BizMonitorService{


    /**
     * 查询 角色资源关系列表
     * @param clientId 客户端id
     * @param roleId 角色id
     * @return List<BankResourceVo> 资源列表List
     */
    list<struct.BankResourceVo> getBankRoleResource(1:string clientId,2:i64 roleId);


	/**
     * 查询 全部角色资源关系列表
     * @return List<BankResourceVo> 资源列表List
     */
    list<struct.BankUserResourceVo> getBankRoleResourceAll();
    
}


/**
 *---------------------------------------------------------银行角色服务--------------------------------------
 */
  
 /**
 * 银行角色服务接口
 * BankRoleService
 */
service BankRoleService extends BizMonitor.BizMonitorService{

    /**
     * 增加 BankRole
     * @param bankRole 角色对象
     * @param resourceIds 资源id集合
     * @return long    主键ID(异常及错误返回-1)
     */
    Common.CommonAddVoRes addBankRole(1:Common.OriginVo originVo,2:struct.BankRoleVo bankRoleVo, 3:list<i64> resourceIds);

    /**
     * 删除 BankRole
     * @param bankRole 角色对象
     * @return boolean    
     */
    Common.ResultVo deleteBankRole(1:Common.OriginVo originVo,2:struct.BankRoleVo bankRoleVo);

    /**
     * 修改 BankRole
     * @param bankRole
     * @param resourceIds 资源id集合
     * @return boolean    
     */
    Common.ResultVo updateBankRole(1:Common.OriginVo originVo,2:struct.BankRoleVo bankRoleVo, 3:list<i64> resourceIds);

    /**
     * 查询 BankRole(查询当前机构及当前机构下属所有角色)
     * @param bankRole
     * @return List<BankRoleVo>
     */
    list<struct.BankRoleVo> getBankRole(1:struct.BankRoleVo bankRoleVo);
    
    /**
     * 查询 BankRole(只查询当前机构下的，不查下属机构对应的角色)
     * @param bankRole
     * @return List<BankRoleVo>
     */
    list<struct.BankRoleVo> getBankRoleInCurrentOrg(1:struct.BankRoleVo bankRoleVo);
    

    /**
     * 分页查询 BankRole
     * @param bankRole
     * @return BankRolePageVoRes
     */
    struct.BankRolePageVoRes getBankRoleByPage(1:Common.PageVo page, 2:struct.BankRoleVo bankRoleVo);
}


/**
 *---------------------------------------------------------客户端支付渠道服务--------------------------------------
 */
/**
 * 客户端支付渠道服务接口
 * ClientPaymentChannelService
 */
service ClientPaymentChannelService extends BizMonitor.BizMonitorService{

    /**
     * 增加 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return long    返回PaymentChannelId(异常及错误返回-1)
     */
    struct.ClientPaymentChannelAddVoRes addClientPaymentChannel(1:Common.OriginVo originVo,2:struct.ClientPaymentChannelVo clientPaymentChannelVo);

    /**
     * 删除 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return boolean   操作是否成功
     */
    Common.ResultVo deleteClientPaymentChannel(1:Common.OriginVo originVo,2:struct.ClientPaymentChannelVo clientPaymentChannelVo);

    /**
     * 修改 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return boolean    操作是否成功
     */
    Common.ResultVo updateClientPaymentChannel(1:Common.OriginVo originVo,2:struct.ClientPaymentChannelVo clientPaymentChannelVo);

	/**
     * 根据id查询 ClientPaymentChannel
     * @param clientId 客户端id
     * @param paymentChannelId 支付渠道id
     * @return ClientPaymentChannelVo
     */
    struct.ClientPaymentChannelVo getClientPaymentChannelById(1:string clientId,2:string paymentChannelId);

	/**
     * 根据clientId查询 该客户端下支持的支付渠道
     * @param clientId 客户端id
     * @param paymentChannelId 支付渠道id
     * @return ClientPaymentChannelVo
     */
    list<struct.ClientPaymentChannelVo> getClientPaymentChannelByClientId(1:string clientId);
    
    /**
     * 查询 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return List<ClientPaymentChannelVo>
     */
    list<struct.ClientPaymentChannelVo> getClientPaymentChannel(1:struct.ClientPaymentChannelVo clientPaymentChannelVo);

    /**
     * 分页查询 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return ClientPaymentChannelPageVoRes
     */
    struct.ClientPaymentChannelPageVoRes getClientPaymentChannelByPage(1:Common.PageVo page, 2:struct.ClientPaymentChannelVo clientPaymentChannelVo);
}


/**
 *---------------------------------------------------------客户端服务--------------------------------------
 */
 
 /**
 * 客户端服务接口
 * ClientService
 */
service ClientService extends BizMonitor.BizMonitorService{

    /**
     * 增加 Client
     * @param clientVo
     * @return long    返回ClientId(异常及错误返回-1)
     */
    struct.ClientAddVoRes addClient(1:Common.OriginVo originVo,2:struct.ClientVo clientVo);

    /**
     * 根据clientId修改 Client
     * @param client
     * @return boolean    
     */
    Common.ResultVo updateClient(1:Common.OriginVo originVo,2:struct.ClientVo clientVo);

	/**
     * 根据id查询 Client
     * @param client 客户端id
     * @return ClientVo
     */
    struct.ClientVo getClientById(1:string clientId);
    
    
    /**
     * 查询 Client
     * @param client
     * @return List<ClientVo>
     */
    list<struct.ClientVo> getClient(1:struct.ClientVo clientVo);

    /**
     * 分页查询 Client
     * @param client
     * @return ClientPageVoRes
     */
    struct.ClientPageVoRes getClientByPage(1:Common.PageVo page, 2:struct.ClientVo clientVo);
}


/**
 *---------------------------------------------------------银行机构服务--------------------------------------
 */
 /**
 * 银行机构服务接口
 */
service OrgService extends BizMonitor.BizMonitorService{

    /**
     * 增加 Org
     * @param org
     * @return long    主键ID(异常及错误返回-1)
     */
    Common.CommonAddVoRes addOrg(1:Common.OriginVo originVo,2:struct.OrgVo orgVo);


	/**
     * 批量增加 Org
     * @param org
     * @return boolean
     */
    Common.ResultVo addOrgByBatch(1:Common.OriginVo originVo,2:list<struct.OrgVo> orgVo);
    
    
    /**
     * 删除 Org
     * @param org
     * @return boolean    
     */
    Common.ResultVo deleteOrg(1:Common.OriginVo originVo,2:struct.OrgVo orgVo);

    /**
     * 修改 Org
     * @param org
     * @return boolean    
     */
    Common.ResultVo updateOrg(1:Common.OriginVo originVo,2:struct.OrgVo orgVo);


	/**
     * 根据orgCode查询 Org
     * @param orgCode
     * @param clientId
     * @return OrgVo
     */
    struct.OrgVo getOrgById(1:string clientId,2:string orgCode);
    
    
    /**
     * 根据orgCode查询 Org 并 返回上级机构名称
     * @param orgCode
     * @param clientId
     * @return OrgVo
     */
    struct.OrgVo getOrgByIdSuperOrgName(1:string clientId,2:string orgCode);
    
        
    /**
     * 查询 Org
     * @param org
     * @return List<OrgVo>
     */
    list<struct.OrgVo> getOrg(1:struct.OrgVo orgVo);


    /**
     * 根据clientId及orgName查询Org列表信息.
     * @param org
     * @return List<OrgVo>
     */
    list<struct.OrgVo> getOrgInfoByOrgName(1:struct.OrgVo orgVo,2:i32 limit,3:string loginOrgCode);
    
    /**
     * 分页查询 Org
     * @param org
     * @return OrgPageVoRes
     */
    struct.OrgPageVoRes getOrgByPage(1:Common.PageVo page, 2:struct.OrgVo orgVo,3:string loginOrgCode);
    
    
    /**
     * 查询当前机构下的所有下级机构
     * @param orgCode
     * @param clientId
     * @return List<OrgVo> 返回机构编号、机构名称、商户id、门店id的OrgVo的集合
     */
    list<struct.OrgVo> getSubOrgs(1:string clientId,2:string orgCode);
    
     /**
     * 查询全部子机构对象，包含当前机构编码
     * @param orgCode
     * @param clientId
     * @return List<OrgVo> 返回机构编号集合
     */
    list<struct.OrgVo> getAllSubOrgs(1:string clientId,2:string orgCode);
    
    /**
     * 查询全部子机构orgCode，包含当前机构编码
     * @param orgCode
     * @param clientId
     * @return List<string> 返回机构编号集合
     */
    list<string> getAllSubOrgCodes(1:string clientId,2:string orgCode);
    
    
     /**
     * 查询市级区级机构(逗号隔开)
     * @param orgCode 机构编号
     * @param clientId 客户端编号
     * @return string 返回市级区级机构编号
     */
    string getSuperOrgCodeByType(1:string clientId,2:string orgCode);
 
 
 	/**
     * 根据orgCode集合获取机构对象(循环内部处理)
     * @param clientId 客户端编号
     * @param orgCodes 机构编号集合
     * @return list<OrgVo> 返回机构对象集合
     */
 	list<struct.OrgVo> getOrgByList(1:string clientId, 2:list<string> orgCodes);
 
 	/**
     * 根据clientId+areaId 查询有效网点
     * @param clientId 客户端编号
     * @param areaId  地区Id
     * @return list<OrgVo> 返回机构对象集合
     */
 	list<struct.OrgVo> getOrgByAreaId(1:string clientId, 2:i64 areaId);
 
 	/**
     * 查询areaId集合下的有效机构所属的法人行社列表
     * @param clientId 客户端编号
     * @param areaIds  区Id集合
     * @return list<OrgVo> 返回机构对象集合
     */
 	list<struct.OrgVo> getOrgByAreaIdsList(1:string clientId, 2:list<i64> areaIds);
 	
	/**
	 * 获取上级机构对象
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @return 上级Org对象
	 */
	struct.OrgVo getSuperOrg(1:string clientId,2:string orgCode);
 
 
 	/**
	 * 获取上级(多级别)机构对象
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @return 上级Org对象集合
	 */
	list<struct.OrgVo> getSuperOrgList(1:string clientId,2:string orgCode);
	
	
	/**
	 * 获取网点交集集合
	 * @param clientId 客户端id
	 * @param loginOrgCode 登录人所属机构编号
	 * @param filterOrgCode 过滤条件机构编号
	 * @return 二者orgCode下级网点交集集合
	 */
	list<string> getIntersectionOrgCodeList(1:string clientId,2:string loginOrgCode,3:string filterOrgCode);
 
}


/**
 *---------------------------------------------------------机构级别角色关系服务--------------------------------------
 */
 
/**
 * 银行联合登录-机构级别角色关系服务接口
 */
service OrgLevelService extends BizMonitor.BizMonitorService{

    /**
     * 增加 OrgLevel
     * @param orgLevel
     * @return long    主键ID(异常及错误返回-1)
     */
    Common.CommonAddVoRes addOrgLevel(1:Common.OriginVo originVo,2:struct.OrgLevelVo orgLevelVo);

    /**
     * 删除 OrgLevel
     * @param orgLevel
     * @return boolean    
     */
    Common.ResultVo deleteOrgLevel(1:Common.OriginVo originVo,2:struct.OrgLevelVo orgLevelVo);

    /**
     * 修改 OrgLevel
     * @param orgLevel
     * @return boolean    
     */
    Common.ResultVo updateOrgLevel(1:Common.OriginVo originVo,2:struct.OrgLevelVo orgLevelVo);

    /**
     * 查询 OrgLevel
     * @param orgLevel
     * @return List<OrgLevelVo>
     */
    list<struct.OrgLevelVo> getOrgLevel(1:struct.OrgLevelVo orgLevelVo);

    /**
     * 分页查询 OrgLevel
     * @param orgLevel
     * @return OrgLevelPageVoRes
     */
    struct.OrgLevelPageVoRes getOrgLevelByPage(1:Common.PageVo page, 2:struct.OrgLevelVo orgLevelVo);
}


/**
 *---------------------------------------------------------银行联合登录帐号服务--------------------------------------
 */
/**
 * 银行联合登录帐号服务接口
 */
service OrgUserRoleService extends BizMonitor.BizMonitorService{

	/**
     * 银行联合登录
     * @param originVo 来源地址信息
     * @param username 登录名
     * @param password 密码
     * @return LoginOrgUserRoleVoRes
     */
     struct.LoginBankOperatorVoRes loginOrgUserRole(1:Common.OriginVo originVo,2:string username,3:string password);

	/**
     * 修改角色 
     * @param originVo 地址源信息
     * @param orgUserRoleVo (根据主键id和cliend_id进行删除操作)
     * @说明：根据主键id
     * @return boolean   操作是否成功
     */
    Common.ResultVo updateOrgUserRole(1:Common.OriginVo originVo,2:struct.OrgUserRoleVo orgUserRoleVo);
    
    /**
     * 查询 OrgUserRole详情
     * @param orgUserRole
     * @return List<OrgUserRoleVo>
     */
    struct.OrgUserRolePageVo getOrgUserRoleById(1:i64 id);
    
    
    /**
     * 查询 OrgUserRole
     * @param orgUserRole
     * @return List<OrgUserRoleVo>
     */
    list<struct.OrgUserRoleVo> getOrgUserRole(1:struct.OrgUserRoleVo orgUserRoleVo);

    /**
     * 分页查询 OrgUserRole
     * @param orgUserRole
     * @return OrgUserRolePageVoRes
     */
    struct.OrgUserRolePageVoRes getOrgUserRoleByPage(1:Common.PageVo page, 2:struct.OrgUserRoleVo orgUserRoleVo);
    
    /**
     * 银行用户联合登录获取错误次数
     * @param username 登录名
     * @return LoginBankOperatorVoRes 登录成功后返回结果对象
     */
    struct.LoginBankOperatorVoRes getLoginFailureCount(1:Common.OriginVo originVo,2:string username);
}


/**
 *---------------------------------------------------------商户审核配置服务--------------------------------------
 */
/**
 * 商户审核配置服务
 * ClientMerchantAuditService
 */
service ClientMerchantAuditService extends BizMonitor.BizMonitorService{

    /**
     * 增加 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return long    主键ID
     */
    Common.CommonAddVoRes addClientMerchantAudit(1:Common.OriginVo originVo,2:struct.ClientMerchantAuditVo clientMerchantAuditVo);

    /**
     * 删除 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return boolean    
     */
    Common.ResultVo deleteClientMerchantAudit(1:Common.OriginVo originVo,2:struct.ClientMerchantAuditVo clientMerchantAuditVo);

    /**
     * 修改 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return boolean    
     */
    Common.ResultVo updateClientMerchantAudit(1:Common.OriginVo originVo,2:struct.ClientMerchantAuditVo clientMerchantAuditVo);

 	/**
     * 根据clientId+orgCode查询商户审核配置信息
     * @param clientId
     * @param orgCode
     * @param type 1-审核 2-商户重置密码
     * @return ClientMerchantAuditOrgCodeVo
     */
	struct.ClientMerchantAuditOrgCodeVo getClientMerchantAuditByOrgCode(1:string clientId,2:string orgCode,3:string type);

    /**
     * 查询 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return List<ClientMerchantAuditVo>
     */
    list<struct.ClientMerchantAuditVo> getClientMerchantAudit(1:struct.ClientMerchantAuditVo clientMerchantAuditVo);

    /**
     * 分页查询 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return ClientMerchantAuditPageVoRes
     */
    struct.ClientMerchantAuditPageVoRes getClientMerchantAuditByPage(1:Common.PageVo page, 2:struct.ClientMerchantAuditVo clientMerchantAuditVo);
}


/**
 *---------------------------------------------------------商品审核配置服务--------------------------------------
 */
/**
 * 商品审核配置服务
 * ClientProductAuditService
 */
service ClientProductAuditService extends BizMonitor.BizMonitorService{

    /**
     * 增加 ClientProductAudit
     * @param clientProductAudit
     * @return long    主键ID
     */
    Common.CommonAddVoRes addClientProductAudit(1:Common.OriginVo originVo,2:struct.ClientProductAuditVo clientProductAuditVo);

    /**
     * 删除 ClientProductAudit
     * @param clientProductAudit
     * @return boolean    
     */
    Common.ResultVo deleteClientProductAudit(1:Common.OriginVo originVo,2:struct.ClientProductAuditVo clientProductAuditVo);

    /**
     * 修改 ClientProductAudit
     * @param clientProductAudit
     * @return boolean    
     */
    Common.ResultVo updateClientProductAudit(1:Common.OriginVo originVo,2:struct.ClientProductAuditVo clientProductAuditVo);

	/**
     * 根据clientId+orgCode查询商户审核配置信息
     * @param clientId
     * @param orgCode
     * @param productType商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)
     * @return ClientProductAuditOrgCodeVo
     */
	struct.ClientProductAuditOrgCodeVo getClientProductAuditByOrgCode(1:string clientId,2:string orgCode,3:string productType);
	
    /**
     * 查询 ClientProductAudit
     * @param clientProductAudit
     * @return List<ClientProductAuditVo>
     */
    list<struct.ClientProductAuditVo> getClientProductAudit(1:struct.ClientProductAuditVo clientProductAuditVo);

    /**
     * 分页查询 ClientProductAudit
     * @param clientProductAudit
     * @return ClientProductAuditPageVoRes
     */
    struct.ClientProductAuditPageVoRes getClientProductAuditByPage(1:Common.PageVo page, 2:struct.ClientProductAuditVo clientProductAuditVo);
}


/**
 *---------------------------------------------------------审核任务订单服务--------------------------------------
 */
/**
 * 审核任务订单服务
 * AuditTaskService
 */
service AuditTaskService extends BizMonitor.BizMonitorService{
	
	/**
     * 查询待审核任务订单详情
     * @param thridId 若查商户待审核详情，则传商户id
     * @return AuditTaskVo
     */
    struct.AuditTaskVo getAuditTaskWait(1:string thridId);
    
    /**
     * 查询审核流水号详情
     * @param auditId 审核流水号
     * @return AuditTaskVo
     */
    struct.AuditTaskVo getAuditTaskByAuditId(1:string auditId);
    
    /**
     * 分页查询 AuditTask
     * @param page 分页对象
     * @param auditTaskFilterVo 过滤对象
     * @param flag 查询权限标志 1-查当前机构 2-查当前机构所有下属机构
     * @return AuditTaskPageVoRes
     */
    struct.AuditTaskPageVoRes getAuditTaskByPage(1:Common.PageVo page, 2:struct.AuditTaskFilterVo auditTaskFilterVo,3:i32 flag);
    
}


/**
 *---------------------------------------------------------审核任务服务--------------------------------------
 */
/**
 * 审核任务服务
 * AuditProcessService
 */
service AuditProcessService extends BizMonitor.BizMonitorService{
	
	/**
     * 查询审核任务列表
     * @param auditId 审核流水号
     * @return List<AuditProcessVo>
     */
    list<struct.AuditProcessVo> getAuditProcess(1:string auditId);
    
}


/**
 *---------------------------------------------------------多银行配置服务--------------------------------------
 */
service BankAccessModuleService extends BizMonitor.BizMonitorService {
	
	/**
	*获取客户端功能列表
	*/
    struct.BankAccessModuleListRes getBankAccessModuleList(1:string clientId);

}










