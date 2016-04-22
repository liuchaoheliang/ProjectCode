namespace java com.froad.thrift.service

/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
/* 引入 struct.thrift 文件 */
include "struct.thrift"

/**
 * 营销活动规则信息 服务
 */
service ActiveRuleInfoService extends BizMonitor.BizMonitorService {

    /**
     * 增加 ActiveRuleInfo
     */
    struct.AddResultVo addActiveRuleInfo(1:Common.OriginVo originVo ,2:struct.ActiveRuleInfoVo activeRuleInfoVo);

    /**
     * 禁用 ActiveRuleInfo
     */
    Common.ResultVo disableActiveRuleInfo(1:Common.OriginVo originVo, 2:string clientId, 3:string activeId, 4:string operator);

    /**
     * 修改 ActiveRuleInfo
     */
    Common.ResultVo updateActiveRuleInfo(1:Common.OriginVo originVo ,2:struct.ActiveRuleInfoVo activeRuleInfoVo);

    /**
     * 查询 ActiveRuleInfo 列表
     */
    struct.FindAllActiveRuleInfoVoResultVo getActiveRuleInfo(1:struct.ActiveRuleInfoVo activeRuleInfoVo);
    
     /**
     * 分页查询 ActiveRuleInfo
     */
    struct.FindPageActiveRuleInfoVoResultVo getActiveRuleInfoByPage(1:Common.PageVo page, 2:struct.ActiveRuleInfoVo activeRuleInfoVo);
    
    /**
     * 查询 ActiveRuleInfo 单个
     */
    struct.FindActiveRuleInfoVoResultVo getActiveRuleInfoById(1:string clientId,2:string activeId);

    /**
     * 下载活动URL
     */
    struct.ExportActiveOrderInfoRes exportActiveOrderInfoUrl(1:struct.ActiveRuleInfoVo activeRuleInfoVo);
}

/**
 * 营销活动运行 服务
 */
service ActiveRunService extends BizMonitor.BizMonitorService {
    
    /**
     * 进入购物车
     */
    struct.ShoppingCartResVo goShoppingCart(1:struct.ShoppingCartReqVo shoppingCartReqVo);
    
    /**
     * 订单校验
     * <br>
     * CheckOrderRes.Result.resultCode = 0000 成功
     * CheckOrderRes.orderResList[n] 订单的各种满减金额
     * <br>
     * CheckOrderRes.Result.resultCode = 6666 没有传递活动
     * CheckOrderRes.orderResList = null
     * <br>
     * CheckOrderRes.Result.resultCode = 9999 活动校验失败
     * CheckOrderRes.Result.resultDesc 失败信息
     * <br>
     */
    struct.CheckOrderResVo checkOrder(1:struct.CheckOrderReqVo checkOrderReqVo);
    
    /**
     * 订单校验失败回退
     */
    Common.ResultVo createOrderFailureGoBack(1:struct.FailureGoBackReqVo failureGoBackReqVo);
    
    /**
     * 创建营销订单
     * <br>
     * CreateResult.Result.resultCode = 0000 成功
     * CreateResult.id = 营销订单编号
     * <br>
     * CreateResult.Result.resultCode != 不成功
     * CreateResult.Result.resultDesc = 失败信息
     */
    struct.CreateResultVo createMarketOrder(1:struct.CreateMarketOrderReqVo createMarketOrderReqVo);
    
    /**
     * 订单查询
     */
    struct.FindMarketOrderResVo findMarketOrder(1:struct.FindMarketOrderReqVo findMarketOrderReqVo);
    
    /**
     * 订单关闭
     */
    Common.ResultVo closeMarketOrder(1:struct.CloseMarketOrderReqVo closeMarketOrderReqVo);
    
    /**
     * 订单退款
     */
    struct.ReturnMarketOrderResVo returnMarketOrder(1:struct.ReturnMarketOrderReqVo returnMarketOrderReqVo);
    
    
    
    /**
    *订单退款回退
    */
    Common.ResultVo returnMarketOrderBack(1:struct.ReturnMarketOrderBackReqVo returnMarketOrderBackReqVo);
    
    
    /**
     * 订单修改
     */
    struct.UpdateMarketOrderResVo updateMarketOrder(1:struct.UpdateMarketOrderReqVo updateMarketOrderReqVo);
    
   /**
   *结算接口
   */
    struct.SettlementMarkOrderRes settlementMarkOrder(1:struct.SettlementMarkOrderReq settlementMarkOrderReq);
    
   /**
     * 新增商品时候判断是否有符合条件的满减
     */    
    Common.ResultVo putFullCut(1:struct.PutFullCutReqVo putFullCutReqVo);
    
  	/**
     * 支付前满赠资格检查
     */
    Common.ResultVo fullGiveCheck(1:struct.FullGiveCheckReqVo fullGiveCheckReqVo);
  	
}

/**
 * 营销活动查询 服务
 */
service ActiveSearchService extends BizMonitor.BizMonitorService {

    /**
     * 营销活动查询 - 根据商户id列表
     */
    struct.FindActiveRuleListResVo findActiveRuleByMerchantIds(1:struct.FindActiveRuleByMerchantVo findActiveRuleByMerchantVo);
    
    /**
     * 营销活动查询 - 根据商品id列表
     */
    struct.FindActiveRuleListResVo findActiveRuleByProductIds(1:struct.FindActiveRuleByProductVo findActiveRuleByProductVo);
    
    /**
     * 凑单
     */
    struct.MinatoSingleProductListVo findProductListOfMinatoSingle(1:Common.PageVo page, 2:struct.MinatoSingleParamVo minatoSingleParamVo);

}

/**
 * 代金券规则信息 服务
 */
service VouchersRuleInfoService extends BizMonitor.BizMonitorService {

    /**
     * 增加 VouchersRuleInfo
     */
    struct.AddResultVo addVouchersRuleInfo(1:Common.OriginVo originVo ,2:struct.VouchersRuleInfoVo vouchersRuleInfoVo);

    /**
     * 禁用 VouchersRuleInfo
     */
    Common.ResultVo disableVouchersRuleInfo(1:Common.OriginVo originVo, 2:string clientId, 3:string activeId, 4:string operator);

    /**
     * 修改 VouchersRuleInfo
     */
    Common.ResultVo updateVouchersRuleInfo(1:Common.OriginVo originVo ,2:struct.VouchersRuleInfoVo vouchersRuleInfoVo);

    /**
     * 查询 VouchersRuleInfo 列表
     */
    struct.FindAllVouchersRuleInfoVoResultVo getActiveRuleInfo(1:struct.VouchersRuleInfoVo vouchersRuleInfoVo);
    
     /**
     * 分页查询 VouchersRuleInfo
     */
    struct.FindPageVouchersRuleInfoVoResultVo getActiveRuleInfoByPage(1:Common.PageVo page, 2:struct.VouchersRuleInfoVo vouchersRuleInfoVo);
    
    /**
     * 查询 VouchersRuleInfo 单个
     */
    struct.FindVouchersRuleInfoVoResultVo getActiveRuleInfoById(1:string clientId, 2:string activeId);
    
    /**
     * 下载红包劵码明细信息
     */
    struct.ExportVouchersDetailInfo exportVouchersDetailInfo(1:string clientId, 2:string activeId);
    
    /**
     * 保存红包券码临时信息
     */
    struct.AddResultVo addTemporaryVouchersRuleInfo(1:Common.OriginVo originVo ,2:struct.TemporaryVouchersDetailInfoVO temporaryVouchersDetailInfoVO);

}

/**
 * 代金券运行 服务
 */
service VouchersRunService extends BizMonitor.BizMonitorService {
    
    /**
     * 查询红包详情 - 订单确认
     */
    struct.FindVouchersResVo findVouchersOfSubmit(1:struct.FindVouchersOfSubmitReqVo findVouchersOfSubmitReq);
    
    /**
     * 查询红包详情 - 会员中心
     */
    struct.FindVouchersResVo findVouchersOfCenter(1:struct.FindVouchersOfCenterReqVo findVouchersOfCenterReq);
    
    /**
     * 校验代金券
     */
    struct.CheckVouchersResVo checkVouchers(1:struct.CheckVouchersReqVo checkVouchersReq);
    
    /**
     * 订单创建失败回退
     */
    Common.ResultVo createVouchersOrderFailureGoBack(1:struct.CreateVouchersOrderFailureGoBackReqVo createVouchersOrderFailureGoBackReq);
    
    /**
     * 支付结果通知
     */
    Common.ResultVo payResultNotice(1:struct.PayResultNoticeReqVo payResultNoticeReq);
    
    /**
     * 关单
     */
    Common.ResultVo closeVouchersOrder(1:struct.CloseVouchersOrderReqVo closeVouchersOrderReq);
	
    /**
     * 红包转化接口
     */
    Common.ResultVo vouchersToRedPack(1:struct.VouchersToRedPackReqVo vouchersToRedPackReqVo);
}

/** 代金券查询服务 **/
service VouchersSearchService extends BizMonitor.BizMonitorService {
	
	/** 详情页查询红包券码明细列表分页 **/
	struct.FindVouchersDetailInfoVO findVouchersDetailInfo(1:Common.PageVo page,2:string clientId, 3:string activeId);
	
	/** 新增页查询红包券码明细列表分页（红包临时信息） **/
	struct.FindVouchersDetailInfoVO findVouchersDetailTemporaryInfo(1:Common.PageVo page,2:string clientId, 3:string activeId);
	
	/** 查询红包活动券码个数  **/
	struct.FindVouchersCountVO FindVouchersCountInfo(1:string clientId, 2:string activeId);
}

/** 活动支持的活动 服务 */
service ActiveSustainRelationService extends BizMonitor.BizMonitorService {

    /** 促销活动列表接 **/
	struct.FindAllPromotionActiveVO findAllPromotionActive(1:struct.ActiveBaseRuleVo activeBaseRuleVo);
	
	/** 详情页查询活动支持列表分页接口(分页) **/
	struct.FindPromotionActiveByPageVO findPromotionActiveByPage(1:Common.PageVo page,2:struct.ActiveBaseRuleVo activeBaseRuleVo);
}

/**
 * 注册(首单)规则信息 服务
 */
service RegisteredRuleInfoService extends BizMonitor.BizMonitorService {

    /**
     * 增加 RegisteredRuleInfo
     */
    struct.AddResultVo addRegisteredRuleInfo(1:Common.OriginVo originVo ,2:struct.RegisteredRuleInfoVo registeredRuleInfoVo);

    /**
     * 禁用 RegisteredRuleInfo
     */
    Common.ResultVo disableRegisteredRuleInfo(1:Common.OriginVo originVo, 2:string clientId, 3:string activeId, 4:string operator);

    /**
     * 修改 RegisteredRuleInfo
     */
    Common.ResultVo updateRegisteredRuleInfo(1:Common.OriginVo originVo ,2:struct.RegisteredRuleInfoVo registeredRuleInfoVo);

    /**
     * 查询 RegisteredRuleInfo 列表
     */
    struct.FindAllRegisteredRuleInfoVoResultVo getRegisteredRuleInfo(1:struct.RegisteredRuleInfoVo registeredRuleInfoVo);
    
     /**
     * 分页查询 RegisteredRuleInfo
     */
    struct.FindPageRegisteredRuleInfoVoResultVo getRegisteredRuleInfoByPage(1:Common.PageVo page, 2:struct.RegisteredRuleInfoVo registeredRuleInfoVo);
    
    /**
     * 查询 VouchersRuleInfo 单个
     */
    struct.FindRegisteredRuleInfoVoResultVo getRegisteredRuleInfoById(1:string clientId, 2:string activeId);
    
     /**
     * 下载活动URL
     */
    struct.ExportRegisteredRuleInfoInfoRes exportRegisteredRuleInfoInfoResUrl(1:struct.RegisteredRuleInfoVo registeredRuleInfoVo);

}

/**
 * 注册(首单)运行 服务
 */
service RegisteredRunService extends BizMonitor.BizMonitorService {
    /**
     * 注册送
     */
    Common.ResultVo registeredHandsel(1:struct.RegisteredHandselVo registeredHandselVo);
}