namespace java com.froad.thrift.service

/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
/* 引入 struct.thrift 文件 */
include "struct.thrift"
/* 引入 SMSVO.thrift 文件 */
include "SMSVO.thrift"
/**
 * 活动 服务
 */
service ActivitiesService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Activities
     * @param activities
     * @return long    主键ID
     */
    struct.AddResultVo addActivities(1:Common.OriginVo originVo ,2:struct.ActivitiesVo activitiesVo);

    /**
     * 删除 Activities
     * @return boolean    
     */
    Common.ResultVo deleteActivities(1:Common.OriginVo originVo , 2:string clientId,3:i64 activitiesId);

    /**
     * 修改 Activities
     * @param activities
     * @return boolean    
     */
    Common.ResultVo updateActivities(1:Common.OriginVo originVo ,2:struct.ActivitiesVo activitiesVo);

    /**
     * 查询 Activities
     * @param activities
     * @return List<ActivitiesVo>
     */
    list<struct.ActivitiesVo> getActivities(1:struct.ActivitiesVo activitiesVo);
    
     /**
     * 分页查询 Activities
     * @param activities
     * @return ActivitiesPageVoRes
     */
    struct.ActivitiesPageVoRes getActivitiesByPage(1:Common.PageVo page,2:struct.ActivitiesVo activitiesVo);
    
    /**
     * 查询 Activities
     * @return ActivitiesVo
     */
    struct.ActivitiesVo getActivitiesById(1:string clientId,2:i64 id);
}

/**
 * 广告位 服务
 * 注 - 已经摒弃使用;新的广告位服务是 AdLocationService
 */
service AdPositionService extends BizMonitor.BizMonitorService {

    /**
     * 增加 AdPosition
     * @param adPosition
     * @return long    主键ID
     */
    struct.AddResultVo addAdPosition(1:Common.OriginVo originVo ,2:struct.AdPositionVo adPositionVo);

    /**
     * 删除 AdPosition
     * @param adPosition
     * @return boolean    
     */
    Common.ResultVo deleteAdPosition(1:Common.OriginVo originVo ,2:struct.AdPositionVo adPositionVo);

    /**
     * 修改 AdPosition
     * @param adPosition
     * @return boolean    
     */
    Common.ResultVo updateAdPosition(1:Common.OriginVo originVo ,2:struct.AdPositionVo adPositionVo);

    /**
     * 查询 AdPosition
     * @param adPosition
     * @return List<AdPositionVo>
     */
    list<struct.AdPositionVo> getAdPosition(1:struct.AdPositionVo adPositionVo);
    
    /**
     * 分页查询 AdPosition
     * @param adPosition
     * @return AdPositionPageVoRes
     */
    struct.AdPositionPageVoRes getAdPositionByPage(1:Common.PageVo page,2:struct.AdPositionVo adPositionVo);
    
    /**
     * 查询 AdPosition
     * @return AdPositionVo
     */
    struct.AdPositionVo getAdPositionById(1:string clientId,2:i64 id);
}

/**
 * 广告 服务
 * 注 - 已经摒弃使用;新的广告位服务是 AdvertisingService
 */
service AdService  extends BizMonitor.BizMonitorService {

    /**
     * 增加 Ad
     * @param ad
     * @return long    主键ID
     */
    struct.AddResultVo addAd(1:Common.OriginVo originVo ,2:struct.AdVo adVo);

    /**
     * 删除 Ad
     * @param ad
     * @return boolean    
     */
    Common.ResultVo deleteAd(1:Common.OriginVo originVo ,2:struct.AdVo adVo);

    /**
     * 修改 Ad
     * @param ad
     * @return boolean    
     */
    Common.ResultVo updateAd(1:Common.OriginVo originVo ,2:struct.AdVo adVo);

    /**
     * 查询 Ad
     * @param ad
     * @return List<AdVo>
     */
    list<struct.AdVo> getAd(1:struct.AdVo adVo,2:struct.AdPositionVo adPositionVo);
    
    /**
     * 分页查询 Ad
     * @param ad
     * @return AdPageVoRes
     */
    struct.AdPageVoRes getAdByPage(1:Common.PageVo page,2:struct.AdVo adVo);
    
    /**
     * 查询 Ad
     * @return AdVo
     */
    struct.AdVo getAdById(1:i64 id);
    
    /**
     * 查询 Ad
     * @return AdVo
     */
    map<string,list<struct.AdVo>> getAdByPositionIds(1:list<i64> positionIds);
}

/**
 * 协议 服务
 */
service AgreementService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Agreement
     * @param agreement
     * @return long    主键ID
     */
    struct.AddResultVo addAgreement(1:Common.OriginVo originVo ,2:struct.AgreementVo agreementVo);

    /**
     * 删除 Agreement
     * @param agreement
     * @return boolean    
     */
    Common.ResultVo deleteAgreement(1:Common.OriginVo originVo ,2:struct.AgreementVo agreementVo);

    /**
     * 修改 Agreement
     * @param agreement
     * @return boolean    
     */
    Common.ResultVo updateAgreement(1:Common.OriginVo originVo ,2:struct.AgreementVo agreementVo);

    /**
     * 查询 Agreement
     * @param agreement
     * @return List<AgreementVo>
     */
    list<struct.AgreementVo> getAgreement(1:struct.AgreementVo agreementVo);
    
     /**
     * 分页查询 Agreement
     * @param agreement
     * @return AgreementPageVoRes
     */
    struct.AgreementPageVoRes getAgreementByPage(1:Common.PageVo page,2:struct.AgreementVo agreementVo);
}

/**
 * 地区 服务
 */
service AreaService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Area
     * @param area
     * @return long    主键ID
     */
    struct.AddResultVo addArea(1:Common.OriginVo originVo ,2:struct.AreaVo areaVo);

    /**
     * 删除 Area
     * @param area
     * @return boolean    
     */
    Common.ResultVo deleteArea(1:Common.OriginVo originVo ,2:struct.AreaVo areaVo);

    /**
     * 修改 Area
     * @param area
     * @return boolean    
     */
    Common.ResultVo updateArea(1:Common.OriginVo originVo ,2:struct.AreaVo areaVo);

    /**
     * 查询 Area
     * @param area
     * @return List<AreaVo>
     */
    list<struct.AreaVo> getArea(1:struct.AreaVo areaVo);

    /**
     * 分页查询 Area
     * @param area
     * @return AreaPageVoRes
     */
    struct.AreaPageVoRes getAreaByPage(1:Common.PageVo page,2:struct.AreaVo areaVo);


    /**
     * 根据id获取地区 
     * @param id
     * @return AreaVo
     */
     struct.AreaVo findAreaById(1:i64 id);
     
     /**
     * 根据 areaCode 获取地区 
     * @return AreaVo
     */
     struct.AreaVo findAreaByAreaCode(1:string areaCode);
     
     /**
     * 根据 areaCode 和 clientId 获取地区 
     * @return AreaVo
     */
     struct.AreaVo findAreaByAreaCodeAndClientId(1:string areaCode,2:string clientId);


    /**
     * 根据id获取子集地区
     * @return List<AreaVo>
     */
    list<struct.AreaVo> findChildrenInfoById(1:i64 id,2:string areaCode);
    
    /**
     * 根据id获取子集地区
     * 查询第一级时，根据clientId进行过滤
     * @return List<AreaVo>
     */
    list<struct.AreaVo> findChildrenInfo(1:i64 id,2:string areaCode,3:string clientId);
    
    /**
     * 判断areaCode是否属于clientId的范围内
     * @return boolean
     */
    bool isAreaCodeScopeOfClientId(1:string areaCode,2:string clientId);

	/**
     * 根据clientId获取省级地区 
     * @return List<AreaVo>
     */
	list<struct.AreaVo> findProvinceAreaByClientId(1:string clientId);

}

/**
 * 代金券 服务
 */
service CashService  extends BizMonitor.BizMonitorService{

    /**
     * 增加 Cash
     * @param cash
     * @return long    主键ID
     */
    struct.AddResultVo addCash(1:Common.OriginVo originVo ,2:struct.CashVo cashVo);

    /**
     * 删除 Cash
     * @param cash
     * @return boolean    
     */
    Common.ResultVo deleteCash(1:Common.OriginVo originVo ,2:struct.CashVo cashVo);

    /**
     * 修改 Cash
     * @param cash
     * @return boolean    
     */
    Common.ResultVo updateCash(1:Common.OriginVo originVo ,2:struct.CashVo cashVo);

    /**
     * 查询 Cash
     * @param cash
     * @return List<CashVo>
     */
    list<struct.CashVo> getCash(1:struct.CashVo cashVo);

    /**
     * 分页查询 Cash
     * @param cash
     * @return CashPageVoRes
     */
    struct.CashPageVoRes getCashByPage(1:Common.PageVo page,2:struct.CashVo cashVo);
}

/**
 * 物流公司 服务
 */
service DeliveryCorpService extends BizMonitor.BizMonitorService {

    /**
     * 增加 DeliveryCorp
     * @param deliveryCorp
     * @return long    主键ID
     */
    struct.AddResultVo addDeliveryCorp(1:Common.OriginVo originVo ,2:struct.DeliveryCorpVo deliveryCorpVo);

    /**
     * 删除 DeliveryCorp
     * @param deliveryCorp
     * @return boolean    
     */
    Common.ResultVo deleteDeliveryCorp(1:Common.OriginVo originVo ,2:struct.DeliveryCorpVo deliveryCorpVo);

    /**
     * 修改 DeliveryCorp
     * @param deliveryCorp
     * @return boolean    
     */
    Common.ResultVo updateDeliveryCorp(1:Common.OriginVo originVo ,2:struct.DeliveryCorpVo deliveryCorpVo);

    /**
     * 查询 DeliveryCorp
     * @param deliveryCorp
     * @return List<DeliveryCorpVo>
     */
    list<struct.DeliveryCorpVo> getDeliveryCorp(1:struct.DeliveryCorpVo deliveryCorpVo);

    /**
     * 分页查询 DeliveryCorp
     * @param deliveryCorp
     * @return DeliveryCorpPageVoRes
     */
    struct.DeliveryCorpPageVoRes getDeliveryCorpByPage(1:Common.PageVo page,2:struct.DeliveryCorpVo deliveryCorpVo);
}

/**
 * 获取二维码对外接口
 */
service QrCodeService  extends BizMonitor.BizMonitorService {
	/**
	* 获取二维码url
	*/
	struct.QrCodeResponseVo retrieveQrCode(1:struct.QrCodeRequestVo qrcodeRequestVo);
	/**
	* 生成图片验证码;返回图片验证码url
	*/
	string generateWordImage(1:string content);
}

/**
 * 短信模板 服务
 */
service SmsContentService  extends BizMonitor.BizMonitorService {

    /**
     * 增加 SmsContent
     * @param smsContent
     * @return long    主键ID
     */
    struct.AddResultVo addSmsContent(1:Common.OriginVo originVo ,2:struct.SmsContentVo smsContentVo);

    /**
     * 删除 SmsContent
     * @param smsContent
     * @return boolean    
     */
    Common.ResultVo deleteSmsContent(1:Common.OriginVo originVo ,2:struct.SmsContentVo smsContentVo);

    /**
     * 修改 SmsContent
     * @param smsContent
     * @return boolean    
     */
    Common.ResultVo updateSmsContent(1:Common.OriginVo originVo ,2:struct.SmsContentVo smsContentVo);

    /**
     * 查询 SmsContent
     * @param smsContent
     * @return List<SmsContentVo>
     */
    list<struct.SmsContentVo> getSmsContent(1:struct.SmsContentVo smsContentVo);

    /**
     * 分页查询 SmsContent
     * @param smsContent
     * @return SmsContentPageVoRes
     */
    struct.SmsContentPageVoRes getSmsContentByPage(1:Common.PageVo page,2:struct.SmsContentVo smsContentVo);

	/**
     * 查询 SmsContent
     * @return SmsContentVo
     */
	struct.SmsContentVo getSmsContentByClientIdAndType(1:string clientId,2:i32 smsType);
}

/**
 * 短信日志 服务
 */
service SmsLogService  extends BizMonitor.BizMonitorService{

    /**
     * 增加 SmsLog
     * @param smsLog
     * @return long    主键ID
     */
    i64 addSmsLog(1:Common.OriginVo originVo ,2:SMSVO.SmsLogVo smsLogVo);

    /**
     * 删除 SmsLog
     * @param smsLog
     * @return boolean    
     */
    bool deleteSmsLog(1:Common.OriginVo originVo ,2:SMSVO.SmsLogVo smsLogVo);

    /**
     * 修改 SmsLog
     * @param smsLog
     * @return boolean    
     */
    bool updateSmsLog(1:Common.OriginVo originVo ,2:SMSVO.SmsLogVo smsLogVo);

    /**
     * 查询 SmsLog
     * @param smsLog
     * @return List<SmsLogVo>
     */
    list<SMSVO.SmsLogVo> getSmsLog(1:SMSVO.SmsLogVo smsLogVo);

    /**
     * 分页查询 SmsLog
     * @param smsLog
     * @return SmsLogPageVoRes
     */
    struct.SmsLogPageVoRes getSmsLogByPage(1:Common.PageVo page,2:SMSVO.SmsLogVo smsLogVo);
}


/**
 * Boss敏感词 服务
 */
service BossSenseWordsService extends BizMonitor.BizMonitorService {

    /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return long    主键ID
     */
    struct.AddResultVo addBossSenseWords(1:Common.OriginVo originVo ,2:struct.BossSenseWordsVo bossSenseWordsVo);

    /**
     * 删除 BossSenseWords
     * @param bossSenseWords
     * @return boolean    
     */
    Common.ResultVo deleteBossSenseWords(1:Common.OriginVo originVo ,2:struct.BossSenseWordsVo bossSenseWordsVo);

    /**
     * 修改 BossSenseWords
     * @param bossSenseWords
     * @return boolean    
     */
    Common.ResultVo updateBossSenseWords(1:Common.OriginVo originVo ,2:struct.BossSenseWordsVo bossSenseWordsVo);

    /**
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWordsVo>
     */
    list<struct.BossSenseWordsVo> getBossSenseWords(1:struct.BossSenseWordsVo bossSenseWordsVo);

    /**
     * 分页查询 BossSenseWords
     * @param bossSenseWords
     * @return BossSenseWordsPageVoRes
     */
    struct.BossSenseWordsPageVoRes getBossSenseWordsByPage(1:Common.PageVo page,2:struct.BossSenseWordsVo bossSenseWordsVo);
}

/**
 * Boss机构 服务
 */
service BossOrgService extends BizMonitor.BizMonitorService {

    /**
     * 增加 BossOrg
     * @param bossOrg
     * @return long    主键ID
     */
    struct.AddResultVo addBossOrg(1:Common.OriginVo originVo ,2:struct.BossOrgVo bossOrgVo);

    /**
     * 删除 BossOrg
     * @param bossOrg
     * @return boolean    
     */
    Common.ResultVo deleteBossOrg(1:Common.OriginVo originVo ,2:struct.BossOrgVo bossOrgVo);

    /**
     * 修改 BossOrg
     * @param bossOrg
     * @return boolean    
     */
    Common.ResultVo updateBossOrg(1:Common.OriginVo originVo ,2:struct.BossOrgVo bossOrgVo);

    /**
     * 查询 BossOrg
     * @param bossOrg
     * @return List<BossOrgVo>
     */
    list<struct.BossOrgVo> getBossOrg(1:struct.BossOrgVo bossOrgVo);

    /**
     * 分页查询 BossOrg
     * @param bossOrg
     * @return BossOrgPageVoRes
     */
    struct.BossOrgPageVoRes getBossOrgByPage(1:Common.PageVo page,2:struct.BossOrgVo bossOrgVo);
}

/**
 * Boss用户 服务
 */
service BossUserService extends BizMonitor.BizMonitorService {

    /**
     * 增加 BossUser
     * @param bossUser
     * @return long    主键ID
     */
    struct.AddResultVo addBossUser(1:Common.OriginVo originVo ,2:struct.BossUserVo bossUserVo);

    /**
     * 删除 BossUser
     * @param bossUser
     * @return boolean    
     */
    Common.ResultVo deleteBossUser(1:Common.OriginVo originVo ,2:struct.BossUserVo bossUserVo);

    /**
     * 修改 BossUser
     * @param bossUser
     * @return boolean    
     */
    Common.ResultVo updateBossUser(1:Common.OriginVo originVo ,2:struct.BossUserVo bossUserVo);

    /**
     * 查询 BossUser
     * @param bossUser
     * @return List<BossUserVo>
     */
    list<struct.BossUserVo> getBossUser(1:struct.BossUserVo bossUserVo);

    /**
     * 分页查询 BossUser
     * @param bossUser
     * @return BossUserPageVoRes
     */
    struct.BossUserPageVoRes getBossUserByPage(1:Common.PageVo page,2:struct.BossUserVo bossUserVo);
    
    /**
     * 是否存在username
     * @return bool
     */
    bool isExistUsername(1:string username);
    
    /**
	 * boss 用户登录
     *
	 * @return MerchantUserLoginVoRes
	 */
	struct.BossUserLoginVoRes login(1:Common.OriginVo originVo,2:string username,3:string password);
    
    /**
	 * boss 用户登出
	 * 
	 * @return boolean
	 */
	bool logout(1:Common.OriginVo originVo,2:string token);
	
	/**
     * 校验 token
     *
     * @return BossUserCheckVoRes
     */
    struct.BossUserCheckVoRes tokenCheck(1:Common.OriginVo originVo,2:string token,3:i64 userId);
}

/**
 * Boss用户机构关联 服务
 */
service BossUserOrgService extends BizMonitor.BizMonitorService {

    /**
     * 增加 BossUserOrg
     * @param bossUserOrg
     * @return long    主键ID
     */
    struct.AddResultVo addBossUserOrg(1:Common.OriginVo originVo ,2:struct.BossUserOrgVo bossUserOrgVo);

    /**
     * 删除 BossUserOrg
     * @param bossUserOrg
     * @return boolean    
     */
    Common.ResultVo deleteBossUserOrg(1:Common.OriginVo originVo ,2:struct.BossUserOrgVo bossUserOrgVo);

    /**
     * 修改 BossUserOrg
     * @param bossUserOrg
     * @return boolean    
     */
    Common.ResultVo updateBossUserOrg(1:Common.OriginVo originVo ,2:struct.BossUserOrgVo bossUserOrgVo);

    /**
     * 查询 BossUserOrg
     * @param bossUserOrg
     * @return List<BossUserOrgVo>
     */
    list<struct.BossUserOrgVo> getBossUserOrg(1:struct.BossUserOrgVo bossUserOrgVo);

    /**
     * 分页查询 BossUserOrg
     * @param bossUserOrg
     * @return BossUserOrgPageVoRes
     */
    struct.BossUserOrgPageVoRes getBossUserOrgByPage(1:Common.PageVo page,2:struct.BossUserOrgVo bossUserOrgVo);
}

/**
 * Boss角色 服务
 */
service BossRoleService extends BizMonitor.BizMonitorService {

    /**
     * 增加 BossRole
     * @param bossRole
     * @return long    主键ID
     */
    struct.AddResultVo addBossRole(1:Common.OriginVo originVo ,2:struct.BossRoleVo bossRoleVo);

    /**
     * 删除 BossRole
     * @param bossRole
     * @return boolean    
     */
    Common.ResultVo deleteBossRole(1:Common.OriginVo originVo ,2:struct.BossRoleVo bossRoleVo);

    /**
     * 修改 BossRole
     * @param bossRole
     * @return boolean    
     */
    Common.ResultVo updateBossRole(1:Common.OriginVo originVo ,2:struct.BossRoleVo bossRoleVo);

    /**
     * 查询 BossRole
     * @param bossRole
     * @return List<BossRoleVo>
     */
    list<struct.BossRoleVo> getBossRole(1:struct.BossRoleVo bossRoleVo);

    /**
     * 分页查询 BossRole
     * @param bossRole
     * @return BossRolePageVoRes
     */
    struct.BossRolePageVoRes getBossRoleByPage(1:Common.PageVo page,2:struct.BossRoleVo bossRoleVo);
}

/**
 * 敏感词 服务
 */
service SenseWordsService extends BizMonitor.BizMonitorService {
    
    /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return long    主键ID
     */
    struct.AddResultVo addBossSenseWords(1:Common.OriginVo originVo ,2:struct.BossSenseWordsVo bossSenseWordsVo);

    /**
     * 删除 BossSenseWords
     * @param bossSenseWords
     * @return boolean    
     */
    Common.ResultVo deleteBossSenseWords(1:Common.OriginVo originVo ,2:struct.BossSenseWordsVo bossSenseWordsVo);

    /**
     * 修改 BossSenseWords
     * @param bossSenseWords
     * @return boolean    
     */
    Common.ResultVo updateBossSenseWords(1:Common.OriginVo originVo ,2:struct.BossSenseWordsVo bossSenseWordsVo);

    /**
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWordsVo>
     */
    list<struct.BossSenseWordsVo> getBossSenseWords(1:struct.BossSenseWordsVo bossSenseWordsVo);

    /**
     * 分页查询 BossSenseWords
     * @param bossSenseWords
     * @return BossSenseWordsPageVoRes
     */
    struct.BossSenseWordsPageVoRes getBossSenseWordsByPage(1:Common.PageVo page,2:struct.BossSenseWordsVo bossSenseWordsVo);
    /**
     * 判断文字是否包含敏感字符
     * @param word
     * @return bool
     */
    Common.ResultVo isContaintSensitiveWord(1:string word);
}

/**
 * 客户端启动页服务接口
 * TerminalStartService
 */
service TerminalStartService extends BizMonitor.BizMonitorService{

 	/**
     * 客户端启动页查询
     * @param clientId 客户端id
     * @param appType 客户端类型  1-个人 2-商户
     * @param terminalType 终端类型 1-pc 2-andriod 3-ios
     * @return 启动页详情
     */
	 struct.TerminalStartVo getTerminalStart(1:string clientId,2:string appType;3:string terminalType);
}

/**
 * 广告位 服务
 */
service AdLocationService extends BizMonitor.BizMonitorService {

    /**
     * 增加 AdLocation
     * @param adLocation
     * @return AddResultVo
     */
    struct.AddResultVo addAdLocation(1:Common.OriginVo originVo ,2:struct.AdLocationVo adLocationVo);

    /**
     * 删除 AdLocation
     * @param id
     * @return ResultVo    
     */
    Common.ResultVo deleteAdLocation(1:Common.OriginVo originVo ,2:i64 id);

    /**
     * 修改 AdLocation
     * @param adLocation
     * @return ResultVo    
     */
    Common.ResultVo updateAdLocation(1:Common.OriginVo originVo ,2:struct.AdLocationVo adLocationVo);

    /**
     * 查询 AdLocation 列表
     * @param adLocation
     * @return List<AdLocationVo>
     */
    struct.FindAllAdLocationResultVo getAdLocation(1:struct.AdLocationVo adLocationVo);
    
    /**
     * 分页查询 AdLocation
     * @param adLocation
     * @return AdLocationPageVoRes
     */
    struct.FindPageAdLocationResultVo getAdLocationByPage(1:Common.PageVo page,2:struct.AdLocationVo adLocationVo);
    
    /**
     * 查询 AdLocation 单个
     * @return AdLocationVo
     */
    struct.FindAdLocationResultVo getAdLocationById(1:i64 id);
    
    /**
     * 禁用 AdLocation
     * @param id
     * @return ResultVo    
     */
    Common.ResultVo disabledAdLocation(1:Common.OriginVo originVo ,2:i64 id);
}

/**
 * 广告服务
 */
service AdvertisingService  extends BizMonitor.BizMonitorService {

    /**
     * 增加 AdvertisingVo
     * @param advertisingVo
     * @return AddResultVo
     */
    struct.AddResultVo addAdvertising(1:Common.OriginVo originVo ,2:struct.AdvertisingVo advertisingVo);

    /**
     * 删除 AdvertisingVo
     * @param id
     * @return ResultVo    
     */
    Common.ResultVo deleteAdvertising(1:Common.OriginVo originVo ,2:i64 id);

    /**
     * 修改 AdvertisingVo
     * @param advertisingVo
     * @return ResultVo    
     */
    Common.ResultVo updateAdvertising(1:Common.OriginVo originVo ,2:struct.AdvertisingVo advertisingVo);

    /**
     * 查询 AdvertisingVo 列表
     * @param advertisingVo
     * @return List<AdvertisingVo>
     */
    struct.FindAllAdvertisingResultVo getAdvertising(1:struct.AdvertisingVo advertisingVo);
    
    /**
     * 分页查询 AdvertisingVo
     * @param advertisingVo
     * @return AdvertisingPageVoRes
     */
    struct.FindPageAdvertisingResultVo getAdvertisingByPage(1:Common.PageVo page,2:struct.AdvertisingVo advertisingVo);
    
    /**
     * 查询 AdvertisingVo 单个
     * @return AdvertisingVo
     */
    struct.FindAdvertisingResultVo getAdvertisingById(1:string clientId,2:i64 id);
    
    /**
     * 页面优化查询 AdvertisingVo 列表
     * @param findAllAdvertisingParamVo
     * @return FindAllAdvertisingResultVo
     */
    struct.FindAllAdvertisingResultVo pageOptFindAdvertisings(1:struct.FindAllAdvertisingParamVo findAllAdvertisingParamVo);
}

/**
 * 热词 服务
 */
service HotWordService extends BizMonitor.BizMonitorService {
    
    /**
     * 查询热词
     * @param page
     * @param hotWordVo
     * @return HotWordPageVo    
     */
    struct.HotWordPageRes searchHotWord(1:Common.PageVo page,2:struct.HotWordVo hotWordVo);





}

/**
 * 物流订单服务
 */
service DeliveryWayBillService extends BizMonitor.BizMonitorService {
    
    /**
     * 查询物流运单
     * 
     * @param subOrderId
     * @return DeliveryWayBillVo    
     */
    struct.DeliveryWayBillVo getDeliveryWayBillById(1:struct.DeliveryWayBillVo deliveryWayBillVo);

    
    /**
     * 更新物流运单状态
     * 
     * @param DeliveryWayBillVo
     * @return ResultVo    
     */
     Common.ResultVo updateDeliveryWayBill(1:struct.DeliveryWayBillVo deliveryWayBillVo);

     /**
     * 更新物流运单状态
     * 
     * @param DeliveryWayBillVo
     * @return ResultVo    
     */
     Common.ResultVo updateDeliveryWayBillByCondition(1:struct.DeliveryWayBillVo deliveryWayBillVo);


}