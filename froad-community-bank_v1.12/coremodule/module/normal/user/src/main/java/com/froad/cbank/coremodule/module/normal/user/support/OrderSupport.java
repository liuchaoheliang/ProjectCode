package com.froad.cbank.coremodule.module.normal.user.support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.CreateSource;
import com.froad.cbank.coremodule.module.normal.user.enums.DeliveryType;
import com.froad.cbank.coremodule.module.normal.user.enums.OrderStatus;
import com.froad.cbank.coremodule.module.normal.user.enums.PaymentChannel;
import com.froad.cbank.coremodule.module.normal.user.enums.PaymentMethod;
import com.froad.cbank.coremodule.module.normal.user.enums.ProductType;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.enums.ShippingStatus;
import com.froad.cbank.coremodule.module.normal.user.pojo.AddProductPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.AddQrOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.AreaPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.DeliverInfoPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.DeliveryBillVo;
import com.froad.cbank.coremodule.module.normal.user.pojo.DeliveryMessageVo;
import com.froad.cbank.coremodule.module.normal.user.pojo.FindActivePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.GenerateOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.MerchantReturnPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OrderDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OrderProductPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OrderProductSummaryPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OrderSummaryPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PointExchangeOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductReturnPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.QrcodeOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ShippingDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.SubOrderDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.SubOrderPreferencePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.SubOrderSimplePojo;
import com.froad.cbank.coremodule.module.normal.user.utils.AmountUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.DisplayPayChannelVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PayOrdersVo;
import com.froad.cbank.coremodule.module.normal.user.vo.QrcodeOrderDetailResVo;
import com.froad.cbank.coremodule.module.normal.user.vo.QrcodeOrderSummaryResVo;
import com.froad.thrift.service.ActiveRuleInfoService;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.service.ClientPaymentChannelService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.DeliveryWayBillService;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.MerchantOutletFavoriteService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AddOutletProductVoRes;
import com.froad.thrift.vo.ClientPaymentChannelVo;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.DeliveryWayBillVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletProductDiscountVo;
import com.froad.thrift.vo.OutletProductVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.RecvInfoVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveRuleInfoVo;
import com.froad.thrift.vo.active.FindActiveRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindActiveRuleListVo;
import com.froad.thrift.vo.active.FindActiveRuleListVoResultVo;
import com.froad.thrift.vo.active.FindActiveVo;
import com.froad.thrift.vo.active.FindMarketOrderReqVo;
import com.froad.thrift.vo.active.FindMarketOrderResVo;
import com.froad.thrift.vo.active.FindMarketSubOrderProductVo;
import com.froad.thrift.vo.active.FindMarketSubOrderVo;
import com.froad.thrift.vo.member.UserEnginePointsVo;
import com.froad.thrift.vo.order.AddMerchantVo;
import com.froad.thrift.vo.order.AddOrderVoReq;
import com.froad.thrift.vo.order.AddOrderVoRes;
import com.froad.thrift.vo.order.AddPrefPayOrderReq;
import com.froad.thrift.vo.order.AddPrefPayOrderRes;
import com.froad.thrift.vo.order.AddProductVo;
import com.froad.thrift.vo.order.AddQrcodeOrderVoReq;
import com.froad.thrift.vo.order.AddQrcodeOrderVoRes;
import com.froad.thrift.vo.order.CashierVoReq;
import com.froad.thrift.vo.order.CashierVoRes;
import com.froad.thrift.vo.order.DeleteOrderVoReq;
import com.froad.thrift.vo.order.DeleteOrderVoRes;
import com.froad.thrift.vo.order.DeliverInfoDetailVo;
import com.froad.thrift.vo.order.GetOrderDetailVoReq;
import com.froad.thrift.vo.order.GetOrderDetailVoRes;
import com.froad.thrift.vo.order.GetOrderPaymentResultVoReq;
import com.froad.thrift.vo.order.GetOrderPaymentResultVoRes;
import com.froad.thrift.vo.order.GetOrderSummaryVoReq;
import com.froad.thrift.vo.order.GetOrderSummaryVoRes;
import com.froad.thrift.vo.order.GetPointExchangeDetailVoReq;
import com.froad.thrift.vo.order.GetPointExchangeDetailVoRes;
import com.froad.thrift.vo.order.GetPointExchangeListVoReq;
import com.froad.thrift.vo.order.GetPointExchangeListVoRes;
import com.froad.thrift.vo.order.GetQrcodeOrderDetailVoReq;
import com.froad.thrift.vo.order.GetQrcodeOrderDetailVoRes;
import com.froad.thrift.vo.order.GetQrcodeOrderSummaryVoReq;
import com.froad.thrift.vo.order.GetQrcodeOrderSummaryVoRes;
import com.froad.thrift.vo.order.GetSubOrderVoReq;
import com.froad.thrift.vo.order.GetSubOrderVoRes;
import com.froad.thrift.vo.order.MerchantReturnVo;
import com.froad.thrift.vo.order.OrderDetailVo;
import com.froad.thrift.vo.order.OrderSummaryVo;
import com.froad.thrift.vo.order.PointExchangeVo;
import com.froad.thrift.vo.order.ProductDetailVo;
import com.froad.thrift.vo.order.ProductReturnVo;
import com.froad.thrift.vo.order.ProductSummaryVo;
import com.froad.thrift.vo.order.QrcodeOrderSummaryVo;
import com.froad.thrift.vo.order.ReceiptOrderReq;
import com.froad.thrift.vo.order.ShippingOrderVoRes;
import com.froad.thrift.vo.order.SubOrderDetailVo;
import com.froad.thrift.vo.order.UpdateSubOrderLogisticVoReq;
import com.froad.thrift.vo.order.UpdateSubOrderLogisticVoRes;
import com.pay.user.dto.Result;
import com.pay.user.service.VIPSpecService;


/**
 * 订单相关接口支持
 * @description 
 * @author Liebert
 * @date 2015年4月7日 上午10:36:41
 *
 */
@Service
public class OrderSupport extends BaseSupport{

	
	@Resource
	private PaymentService.Iface paymentService;
	
	@Resource
	private OrderService.Iface orderService;
	
	@Resource
	private MerchantService.Iface merchantService;
	
	@Resource
	private ProductService.Iface productService;
	
	@Resource
	private MerchantOutletFavoriteService.Iface merchantOutletFavoriteService;
	
	@Resource
	private OrderValidateSupport orderValidateSupport;
	
	
	@Resource
	private ActiveRunService.Iface activeRunService;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private OutletCommentService.Iface outletCommentService;
	
	@Resource
	private ActiveRuleInfoService.Iface activeRuleInfoService;
	
	@Resource
	private DeliveryWayBillService.Iface deliveryWayBillService;
	
	@Resource
	private OutletService.Iface outletService;
	
	@Resource
	private CashierSupport cashierSupport;
	
	@Resource
	private ClientConfigSupport clientConfigSupport;
	
	@Resource
	private MemberInformationService.Iface memberInformationService;

	@Resource
	private VIPSpecService vipSpecService;
	
	@Resource
	private ClientService.Iface clientService;
	@Resource
	private RefundSupport refundSupport;
	@Resource
	private AreaSupport areaSupport;
	
	private static final Integer PAGE_NUMBER = 1;
	
	private static final Integer PAGE_SIZE = 10;
	
	@Resource
	private ClientPaymentChannelService.Iface clientPaymentChannelService;
	
	
	/**
	 * 创建订单
	 * @param clientId
	 * @param memberCode
	 * @param memberName
	 * @param vipLevel
	 * @param orderVo
	 * @return
	 */
	public Map<String,Object> addOrder(String clientId, Long memberCode, String memberName, int vipLevel, GenerateOrderPojo orderVo){
		Map<String,Object> resMap = new HashMap<String,Object>();
		//是否为积分兑换订单
		boolean isOnlinePointOrder = false;
		//是否需要提货人，条件是存在预售交易，网点自提的商品
		boolean isNeedDeliver = false;
		//是否需要收货人
		boolean isNeedReciver = false;
		
		//校验支付密码是否符合安全规则
		boolean isSimplePayPwd = false ;
		
		//只有纯积分支付或者纯积分+优惠券/红包时，需要在创建订单时传入支付密码，其他情况在收银台支付时校验
		if(StringUtil.isNotBlank(orderVo.getPayPassWord()) ){
			//用户使用银行积分或者联盟积分，需要校验支付密码
			if( (StringUtil.isNotBlank(orderVo.getBankPoint()) && !"0".equals(orderVo.getBankPoint()) ) || 
					StringUtil.isNotBlank(orderVo.getUnionPoint()) && !"0".equals(orderVo.getUnionPoint()) ) {
				//校验必要参数
				if(StringUtil.isNotBlank(orderVo.getPayPassWord()) && StringUtil.isNotBlank(orderVo.getCreateSource())) {
					CreateSource cs = SimpleUtils.codeToCreateSource(orderVo.getCreateSource());
//					String paypwd = SimpleUtils.decodePwd(orderVo.getPayPassWord(), cs);
					String paypwd = orderVo.getPayPassWord();
					if (CreateSource.pc.equals(cs) || cs == null) {
			            LogCvt.info("[密码加密] >> 渠道：PC，支付密码：加密处理");
			            paypwd = userEngineSupport.encryptPwd(paypwd);
			            if (StringUtil.isBlank(paypwd)) {
			            	LogCvt.info("[密码加密] >> 支付密码加密失败");
							resMap.put(Results.code, "9999");
							resMap.put(Results.msg, "支付密码加密失败");
							return resMap;
						}
			        }
					ResultBean verifyPwd = userEngineSupport.verifyMemberPayPwd(memberCode, paypwd, clientId);
					if(!verifyPwd.isSuccess()){
						LogCvt.info(String.format("支付密码验证失败:%s",JSON.toJSONString(verifyPwd)));
						resMap.put(Results.code, StringUtil.isBlank(verifyPwd.getCode()) ? "9999" : verifyPwd.getCode() );
						resMap.put(Results.msg, verifyPwd.getMsg());
						return resMap;
					}else{
						LogCvt.info(String.format("支付密码验证成功! memberCode:%s",memberCode));
					}

					ResultBean validPwd = userEngineSupport.validationPayPwdRule(memberCode, paypwd);
					if(!validPwd.isSuccess()){
						isSimplePayPwd = true;
					}
				}else {
					LogCvt.info(String.format("支付密码验证缺少必要参数:{ciphertextPwd=%s,createSource=%s}",orderVo.getPayPassWord(),orderVo.getCreateSource()));
					resMap.put(Results.code, "1001");
					resMap.put(Results.msg, "缺少必要的请求参数");
					return resMap;
				}
			}
		}
		Integer boutiqueProductCount = 0;
		List<AddMerchantVo> addMerchantVoList = new ArrayList<AddMerchantVo>();
		Map<String,AddMerchantVo> merchantMap = new HashMap<String,AddMerchantVo>();
		ResultBean validateResult = new ResultBean();
		for(AddProductPojo product : orderVo.getAddProducts()){
			//校验订单参数
			ProductType productType = ProductType.getType(product.getType());
			
			if(productType == null){
				LogCvt.info(String.format("校验订单信息失败>> 无法识别的商品类型：%s", product.getType()));
				resMap.put(Results.code, "9999");
				resMap.put(Results.msg, "无法识别的商品类型");
				return resMap;
			}
			
			switch (productType) {
			case group:
				//团购
				validateResult = orderValidateSupport.validateGroup(orderVo, product, clientId);
				if(!validateResult.isSuccess()){
					LogCvt.info(String.format("校验订单信息失败>> %s",validateResult.getMsg()));
					resMap.put(Results.code, "9999");
					resMap.put(Results.msg, validateResult.getMsg());
					return resMap;
				}
				if(!isNeedReciver){
					isNeedReciver = true;
				}
				break;
				
			case presell:
				//预售
				validateResult = orderValidateSupport.validatePresell(orderVo, product, clientId);
				if(!validateResult.isSuccess()){
					LogCvt.info(String.format("校验订单信息失败>> %s",validateResult.getMsg()));
					resMap.put(Results.code, "9999");
					resMap.put(Results.msg, validateResult.getMsg());
					return resMap;
				}
				if(!isNeedReciver && DeliveryType.home.getCode().equals(product.getDeliveryType())){
					isNeedReciver = true;
				}
				if(!isNeedDeliver && DeliveryType.take.getCode().equals(product.getDeliveryType())){
					//存在网点自提的商品，需要deliverId
					isNeedDeliver = true;
				}
				break;
				
			case special:
				//名优特惠
				validateResult = orderValidateSupport.validateFamousBand(orderVo, product);
				if(!validateResult.isSuccess()){
					LogCvt.info(String.format("校验订单信息失败>> %s",validateResult.getMsg()));
					resMap.put(Results.code, "9999");
					resMap.put(Results.msg, validateResult.getMsg());
					return resMap;
				}
				if(!isNeedReciver){
					isNeedReciver = true;
				}
				break;
			
			case onlinePoint:
				//积分兑换
				validateResult = orderValidateSupport.validatePointExchange(orderVo, product);
				if(!validateResult.isSuccess()){
					LogCvt.info(String.format("校验订单信息失败>> %s",validateResult.getMsg()));
					resMap.put(Results.code, "9999");
					resMap.put(Results.msg, validateResult.getMsg());
					return resMap;
				}
				if(!isNeedReciver){
					isNeedReciver = true;
				}
				if(!isOnlinePointOrder){
					isOnlinePointOrder = true;
				}
				break;
			
			case boutique:
				//精品商城商品
				validateResult = orderValidateSupport.validateBoutiqueExchange(orderVo, product);
				if(!validateResult.isSuccess()){
					LogCvt.info(String.format("校验订单信息失败>> %s",validateResult.getMsg()));
					resMap.put(Results.code, ResultCode.failed.getCode());
					resMap.put(Results.msg, validateResult.getMsg());
					return resMap;
				}
				if(!isNeedReciver && DeliveryType.home.getCode().equals(product.getDeliveryType())){
					isNeedReciver = true;
				}
				boutiqueProductCount++;
				break;
				
			default:
				LogCvt.info(String.format("校验订单信息失败>> %s","无法识别的商品类型"));
				resMap.put(Results.code, "9999");
				resMap.put(Results.msg, "无法识别的商品类型");
				return resMap;
			}
			
			AddMerchantVo addMerchantVo = new AddMerchantVo();
			//商户ID
			String merchantId = product.getMerchantId();
			if(merchantMap.containsKey(merchantId)){
				//merchantMap中存在则取出addMerchantVo
				addMerchantVo = merchantMap.get(merchantId);
				//获取addMerchantVo在addMerchantVoList的位置
				int m_index = addMerchantVoList.indexOf(addMerchantVo);
				//商品传参
				AddProductVo addProductVo = new AddProductVo();
				addProductVo.setProductId(product.getProductId());
				addProductVo.setQuantity(product.getQuantity());
				addProductVo.setOrgCode(product.getOrgCode());
				addProductVo.setOrgName(product.getOrgName());
				addProductVo.setDeliveryType(product.getDeliveryType());
				addProductVo.setVipQuantity(product.getVipQuantity() == null ? 0 : product.getVipQuantity());
				//添加商品活动Id
				addProductVo.setActiveId(product.getActiveId());
				//添加满赠活动ID
				addProductVo.setGiveActiveId(product.getGiveActiveId());
				addMerchantVo.getAddProductVoList().add(addProductVo);
				
				//加入addMerchantVoList
				addMerchantVoList.set(m_index, addMerchantVo);
				
			}else{
				//merchantMap中不存在则创建
				addMerchantVo = new AddMerchantVo();
				addMerchantVo.setAddProductVoList(new ArrayList<AddProductVo>());
				//商品传参
				AddProductVo addProductVo = new AddProductVo();
				addProductVo.setProductId(product.getProductId());
				addProductVo.setQuantity(product.getQuantity());
				addProductVo.setOrgCode(product.getOrgCode());
				addProductVo.setOrgName(product.getOrgName());
				addProductVo.setDeliveryType(product.getDeliveryType());
				addProductVo.setVipQuantity(product.getVipQuantity() == null ? 0 : product.getVipQuantity());
				
				//添加商品活动Id
				addProductVo.setActiveId(product.getActiveId());
				//添加满赠活动ID
				addProductVo.setGiveActiveId(product.getGiveActiveId());
				
				addMerchantVo.getAddProductVoList().add(addProductVo);
				
				addMerchantVo.setMerchantId(merchantId);				
				//加入merchantMap
				merchantMap.put(merchantId, addMerchantVo);
				//加入addMerchantVoList
				addMerchantVoList.add(addMerchantVo);
			}
		}
		//有精品商城精品则需要限制配送地区
		if(StringUtil.isNotBlank(orderVo.getAreaId())){
			if(boutiqueProductCount > 0){
				AreaPojo areaPojo = areaSupport.findAreaByAreaId(Long.valueOf(orderVo.getAreaId()));
				if(areaPojo != null){
					if(StringUtil.isBlank(areaPojo.getClientId())){
						resMap.put(Results.code, "11001");
						resMap.put(Results.msg, "精品商城商品不支持配送至该地区，请更换地址");
						return resMap;
					}else{
						if(!areaPojo.getClientId().equals(clientId)){
							resMap.put(Results.code, "11001");
							resMap.put(Results.msg, "精品商城商品不支持配送至该地区，请更换地址");
							return resMap;
						}
					}
				}
			}
		}
		AddOrderVoReq req = new AddOrderVoReq();
		req.setMemberCode(memberCode);
		req.setMemberName(memberName);
		req.setIsVip(vipLevel != 0 ? true : false);
		req.setMemberLevel(vipLevel);
		req.setClientId(clientId);
		req.setRecvId(isNeedReciver ? orderVo.getRecvId() : null);
		req.setDeliverId(isNeedDeliver ? orderVo.getRecvId() : null);
		req.setRemark(orderVo.getRemark());
		//req.setCardNo(orderVo.getCardNo());
		req.setPhone(orderVo.getPhone());
		req.setCreateSource(orderVo.getCreateSource());
		req.setOrderRequestType(isOnlinePointOrder ? "3" : "2");
		req.setIsShoppingCartOrder(orderVo.isIsShoppingCartOrder());
		req.setAddMerchantVoList(addMerchantVoList);
		//设置红包或者优惠券
		req.setRedPacketId(orderVo.getRedPacketNo());
		req.setCashCouponId(orderVo.getCouponsNo());
		
		//如果使用了积分
		if(StringUtil.isNotBlank(orderVo.getBankPoint()) && !"0".equals(orderVo.getBankPoint())){
			// 积分查询
			UserEnginePointsVo userPoints;
			try {
				userPoints = memberInformationService.selectMemberPointsInfoByLoginID(clientId,memberName);
				BigDecimal bankPoint = new BigDecimal(orderVo.getBankPoint());
				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(orderVo.getBankPoint(),userPoints.getBankPointsExchageRate());
				//设置积分值和积分抵扣金额
				req.setBankPoint(bankPoint.intValue());
				req.setPointMoney(pointAmount.doubleValue());
				if(StringUtil.isNotBlank(userPoints.getBankPointsExchageRate())){
					req.setPointRate(Long.parseLong(userPoints.getBankPointsExchageRate()));
				}
			} catch (TException e) {
				LogCvt.error("查询用户积分接口异常",e);
				resMap.put(Results.code,"9999");
				resMap.put(Results.msg,"查询用户积分接口异常");
				return resMap;
			}

		}else if(StringUtil.isNotBlank(orderVo.getUnionPoint()) && !"0".equals(orderVo.getUnionPoint())){
			// 积分查询
			UserEnginePointsVo userPoints;
			try {
				userPoints = memberInformationService.selectMemberPointsInfoByLoginID(clientId,memberName);
				BigDecimal unionPoint = new BigDecimal(orderVo.getUnionPoint());
				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(orderVo.getUnionPoint(),userPoints.getFroadPointsExchageRate());
				//设置联盟积分值和积分抵扣金额
				req.setFftPoint(unionPoint.doubleValue());
				req.setPointMoney(pointAmount.doubleValue());
				if(StringUtil.isNotBlank(userPoints.getFroadPointsExchageRate())){
					req.setPointRate(Long.parseLong(userPoints.getFroadPointsExchageRate()));
				}
				
			} catch (TException e) {
				LogCvt.error("查询用户积分接口异常",e);
				resMap.put(Results.code,"9999");
				resMap.put(Results.msg,"查询用户积分接口异常");
				return resMap;
			}
		}

		AddOrderVoRes res = null;
		try {	
			res = orderService.addOrder(req);
		} catch (TException e) {
			LogCvt.error("创建订单接口调用异常",e);
			res = new AddOrderVoRes();
			ResultVo resultVo = new ResultVo();

			resultVo.setResultCode("9999");
			resultVo.setResultDesc("创建订单异常");
			res.setResultVo(resultVo);
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())
				&& StringUtil.isNotBlank(res.getOrderId())){
			LogCvt.info(String.format("普通商品订单创建成功>> 订单号:%s,---- isNeedCash:%s", res.getOrderId(), res.getIsNeedCash()));
			
			// isNeedCash 0 不跳收银台 （不需要支付现金,但需要使用积分） 1 需要跳收银台（需要现金支付）  2 订单支付成功（纯红包或者优惠券）
			String isNeedCash = res.getIsNeedCash();
			if("2".equals(isNeedCash)){ 
				//订单直接成功
				resMap.put(Results.code, "0000");
				resMap.put(Results.msg, "订单成功");
				resMap.put("isSimplePayPwd", isSimplePayPwd);
			}else if ("0".equals(isNeedCash)){ //不需要现金支付，但有积分支付（自动调用支付接口）
				// 支付渠道列表
				Map<String,Object> payChannelMap = clientConfigSupport.getClientPaymentChannel(clientId);
				DisplayPayChannelVo displayChannel = new DisplayPayChannelVo();
				displayChannel = (DisplayPayChannelVo) payChannelMap.get("payChannel");
				
				PayOrdersVo payOrdersVo = new PayOrdersVo();
				int bankPoint = res.getBankPoint();
				double froadPoint = res.getFroadPoint();
				//判断是否使用联盟积分
				if(froadPoint > 0.0){
					payOrdersVo.setPointOrgNo(displayChannel.getUnionPointOrgNo());
					payOrdersVo.setType(1);
				}
				//判断是否使用银行积分
				if(bankPoint > 0 ){
					payOrdersVo.setPointOrgNo(displayChannel.getBankPointOrgNo());
					payOrdersVo.setType(2);
				}
				payOrdersVo.setOrderId(res.getOrderId());
				payOrdersVo.setClientId(clientId);
				payOrdersVo.setMemberCode(memberCode);
				payOrdersVo.setCiphertextPwd(orderVo.getPayPassWord());
				payOrdersVo.setCreateSource(orderVo.getCreateSource());
				//自动支付
				resMap.putAll(cashierSupport.payOrders(payOrdersVo));
			}
			//订单信息
			resMap.put("orderId", res.getOrderId());
			resMap.put("cash", res.getCash());
			resMap.put("totalPrice", res.getTotalPrice());
			resMap.put("vipDiscount", res.getVipDiscount());
			return resMap;
		}else if(ResultCode.thriftException.getCode().equals(res.getResultVo().getResultCode())){
			LogCvt.info(String.format("订单创建失败>> 结果码:%s, 结果描述:%s", res.getResultVo().getResultCode(), res.getResultVo().getResultDesc()));
			resMap.put(Results.code,"9999");
			resMap.put(Results.msg,"创建订单异常");
			return resMap;
		}else{
			LogCvt.info(String.format("订单创建失败>> 结果码:%s, 结果描述:%s", res.getResultVo().getResultCode(), res.getResultVo().getResultDesc()));
			LogCvt.info(String.format("订单创建失败>> 失败信息：%s", JSONObject.toJSONString(res.getMerchantReturnVoList())));
			//如果是满减活动创建订单失败，直接返回 msg
			if(ResultCode.activeCheckFailed.getCode().equals(res.getResultVo().getResultCode())){
				resMap.put(Results.code,ResultCode.activeCheckFailed.getCode());
				resMap.put(Results.msg,ResultCode.activeCheckFailed.getMsg());
				return resMap;
			}
			resMap.put(Results.code,ResultCode.orderGenerateFailed.getCode());
			resMap.put(Results.msg,ResultCode.orderGenerateFailed.getMsg());
			
			if(res.getMerchantReturnVoListSize() > 0){
				List<MerchantReturnPojo>  returnPojoList = new ArrayList<MerchantReturnPojo>();
				List<MerchantReturnVo> merchantReturnVoList = res.getMerchantReturnVoList();
				MerchantReturnPojo merchantReturnPojo = null;
				
				for(MerchantReturnVo merchantReturnVo:merchantReturnVoList ){
					merchantReturnPojo = new MerchantReturnPojo();
					BeanUtils.copyProperties(merchantReturnPojo, merchantReturnVo);
					
					if(merchantReturnVo.getProductReturnVoListSize() > 0){
						List<ProductReturnPojo> productReturnList = new ArrayList<ProductReturnPojo>();
						List<ProductReturnVo> productReturnVoList = merchantReturnVo.getProductReturnVoList();
						ProductReturnPojo productReturnPojo = null;
						for(ProductReturnVo productReturnVo: productReturnVoList){
							productReturnPojo = new ProductReturnPojo();
							BeanUtils.copyProperties(productReturnPojo, productReturnVo);
							productReturnList.add(productReturnPojo);
						}
						merchantReturnPojo.setProductReturnList(productReturnList);
					}
					returnPojoList.add(merchantReturnPojo);
				}
				
				resMap.put(Results.result,returnPojoList);
			}
			return resMap;
		}
		
	}
	
	
	
	
	/**
	 * 创建面对面订单
	 * @param clientId
	 * @param memberCode
	 * @param memberName
	 * @param orderVo
	 * @return
	 */
	public Map<String,Object> addQrOrder(String clientId, Long memberCode, String memberName, String mobile, AddQrOrderPojo orderVo){
		Map<String,Object> resMap = new HashMap<String,Object>();
		AddQrcodeOrderVoReq req = new AddQrcodeOrderVoReq();
		req.setQrcode(orderVo.getQrCode());
		req.setMemberCode(memberCode);
		req.setMemberName(memberName);
		req.setPhone(mobile);
		req.setClientId(clientId);
		req.setCreateSource(orderVo.getCreateSource());
		req.setRemark(orderVo.getRemark());
		req.setCashCouponId(orderVo.getCouponsNo());
		req.setRedPacketId(orderVo.getRedPacketNo());
		
		AddQrcodeOrderVoRes res = null;
		try {
			res = orderService.addQrcodeOrder(req);
		} catch (TException e) {
			LogCvt.error("创建面对面订单接口异常",e);
			res = new AddQrcodeOrderVoRes();
			ResultVo resultVo = new ResultVo();
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("创建面对面订单失败");
			res.setResultVo(resultVo);
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			LogCvt.info(String.format("面对面订单创建成功>> 订单号:%s, 商户名:%s", res.getOrderId(), res.getMerchantName()));
			resMap.put("isNeedCash", res.getIsNeedCash());
			resMap.put("orderId",res.getOrderId());
			resMap.put("cost",res.getCost());			
			resMap.put("remark",res.getRemark());
			resMap.put("merchantName",res.getMerchantName());
			try {
				MerchantVo merchant = merchantService.getMerchantByMerchantId(res.getMerchantId());
				resMap.put("merchantLogo", merchant.getLogo());
			} catch (TException e) {
				LogCvt.error("商户查询接口异常", e);
			}
			return resMap;
		}else{
			LogCvt.info(String.format("面对面订单创建失败>> 结果码:%s, 结果描述:%s", res.getResultVo().getResultCode(), res.getResultVo().getResultDesc()));
			resMap.put(Results.code,res.getResultVo().getResultCode());
			resMap.put(Results.msg,res.getResultVo().getResultDesc());
			return resMap;
		}
	}
	
	
	
	/**
	 * 查询订单支付结果
	 * @param clientId
	 * @param orderId
	 * @return
	 */
	public Map<String,Object> getOrderPaymentResult(String clientId, String orderId){
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		if(StringUtil.isBlank(orderId)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "订单号不能为空");
			return resMap;
		}
		
		GetOrderPaymentResultVoReq req = new GetOrderPaymentResultVoReq();
		req.setClientId(clientId);
		req.setOrderId(orderId);
		
		GetOrderPaymentResultVoRes res = new GetOrderPaymentResultVoRes();
		try {
			res = orderService.getOrderPaymentResult(req);
		} catch (TException e) {
			LogCvt.error("查询订单支付结果异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询订单支付结果异常");
			return resMap;
		}
		
		if(res.isSetResultVo() && Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			resMap.put("orderStatus", res.getOrderStatus());
			if("1001".equals(res.getRemark())){
				//销户提示信息
				resMap.put("remark", "账户已销户，请更换银行卡。");
				resMap.put("errorCode", "1001");
			}else{
				resMap.put("remark", res.getRemark());
			}
			return resMap;
		}else{
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询订单支付结果失败");
			return resMap;
		}
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 获取用户订单列表
	 *@description 
	 *@author Liebert
	 *@date 2015年4月3日 下午2:48:39
	 */
	public HashMap<String,Object> getOrderList(String clientId, Long memberCode, String orderStatus, Date startTime, Date endTime, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pageNumber != null ? pageNumber : PAGE_NUMBER);
		pageVo.setPageSize(pageSize != null ? pageSize : PAGE_SIZE);
		pageVo.setLastPageNumber(lastPageNumber != null ? lastPageNumber : 0);
		pageVo.setFirstRecordTime(firstRecordTime != null ? firstRecordTime : 0);
		pageVo.setLastRecordTime(lastRecordTime != null ? lastRecordTime : 0);

		GetOrderSummaryVoReq req = new GetOrderSummaryVoReq ();
		req.setClientId(clientId);
		req.setMemberCode(memberCode);
		if(StringUtil.isNotBlank(orderStatus)){
			req.setOrderStatus(orderStatus);
		}
		if(endTime != null){
			req.setEndTime(endTime.getTime());
		}
		if(startTime != null){
			req.setStartTime(startTime.getTime());
		}
		
		
		req.setPage(pageVo);
		ClientVo clientVo = new ClientVo();
		GetOrderSummaryVoRes res = new GetOrderSummaryVoRes();
		try {
			res = orderService.getOrderList(req);
			clientVo = clientService.getClientById(clientId);
		} catch (TException e) {
			LogCvt.error("订单列表接口调用异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询订单列表失败");
			return resMap;
		}  catch (Exception e) {
			LogCvt.error("订单列表接口调用异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询订单列表失败");
			return resMap;
		}
		
		OrderSummaryVo orderSummaryVo = null;
		OrderSummaryPojo orderSummary = null;
		
		ProductSummaryVo productSummaryVo = null;
		OrderProductSummaryPojo productSummaryPojo = null;
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			//订单列表
			List<OrderSummaryPojo> orderList = new ArrayList<OrderSummaryPojo>();
			if(res.getOrderSummaryVoListSize() > 0){
				Iterator<OrderSummaryVo> orderIter = res.getOrderSummaryVoListIterator();

				while(orderIter.hasNext()){

					orderSummaryVo = orderIter.next();
					orderSummary = new OrderSummaryPojo();

					BeanUtils.copyProperties(orderSummary, orderSummaryVo);
					
					//订单状态
//						OrderStatus orderStatusEnums = OrderStatus.getOrderStatus(orderSummary.getOrderStatus());
//						orderSummary.setOrderStatus(orderStatusEnums != null ? orderStatusEnums.getDescribe() : "");
					
					//订单商品列表
					if(orderSummaryVo.getProductSummaryVoListSize() > 0){
						List<OrderProductSummaryPojo> productList = new ArrayList<OrderProductSummaryPojo>();
						Iterator<ProductSummaryVo> productIter = orderSummaryVo.getProductSummaryVoListIterator();

						while(productIter.hasNext()){

							productSummaryVo = productIter.next();
							productSummaryPojo = new OrderProductSummaryPojo();

							BeanUtils.copyProperties(productSummaryPojo, productSummaryVo);
							
							productList.add(productSummaryPojo);
						}
						orderSummary.setProductList(productList);
					}
					//是否显示退款按钮
					if(orderSummaryVo.isVipOrder && StringUtil.isNotBlank(clientVo.getBankOrg())){//是否支持退款
						Result result = vipSpecService.cancelCheck(memberCode, clientVo.getBankOrg());
						if(result.getResult()){
							//VIP退款校验true可以退款
							Boolean falg = refundSupport.doOrderRefundOfVIP(orderSummaryVo.getOrderId(), clientId, memberCode);
							if(falg){
								orderSummary.setVipRefund(result.getResult());
							}
						}
					}
					orderList.add(orderSummary);
				}
			}
			
			resMap.put("orderList", orderList);
			
			PagePojo page = new PagePojo();
			if(res.isSetPage()){

				BeanUtils.copyProperties(page, res.getPage());
				page.setHasNext(page.getPageNumber() < page.getPageCount());

			}
			resMap.put("page", page);
			
		}else{
			resMap.put(Results.code, res.getResultVo().getResultCode());
			resMap.put(Results.msg, res.getResultVo().getResultDesc());
		}
		
		
		
		return resMap;
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 获取订单详情
	 *@description 
	 *@author Liebert
	 *@date 2015年4月3日 下午2:48:54
	 */
	public HashMap<String,Object> getOrderDetail(String clientId, Long memberCode, String orderId){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		if(StringUtil.isBlank(orderId)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "订单号为空");
			return resMap;
		}
		GetOrderDetailVoReq req = new GetOrderDetailVoReq();
		req.setOrderId(orderId);
		req.setClientId(clientId);
		
		GetOrderDetailVoRes res = new GetOrderDetailVoRes();
		try {
			res  = orderService.getOrderDetail(req);
		} catch (TException e) {
			LogCvt.error("订单详情接口调用异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询订单详情失败");
			return resMap;
		}  catch (Exception e) {
			LogCvt.error("订单详情接口调用异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询订单详情失败");
			return resMap;
		}
		
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			
			OrderDetailPojo orderDetail = new OrderDetailPojo();
			if(res.isSetOrderDetailVo()){
				
				OrderDetailVo orderDetailVo = res.getOrderDetailVo();
				BeanUtils.copyProperties(orderDetail, orderDetailVo);
				
				// 支付渠道列表
				ClientPaymentChannelVo payChannelVo = new ClientPaymentChannelVo();
				payChannelVo.setClientId(clientId);
				
				List<ClientPaymentChannelVo> payChannelList;
				try {
					payChannelList = clientPaymentChannelService.getClientPaymentChannel(payChannelVo);
					String froadPointRate = "";
					String bankPointRate = "";
					for (ClientPaymentChannelVo payChannel : payChannelList) {
						if (PaymentChannel.froad_point.getCode().equals(payChannel.getType())) {
							froadPointRate = payChannel.getPointRate();
						} else if (PaymentChannel.bank_point.getCode().equals(payChannel.getType())) {
							bankPointRate = payChannel.getPointRate();
						}
					}
					
					//设置积分抵扣金额
					BigDecimal fftpointAmount = AmountUtils.getPointExchangeAmount(String.valueOf(orderDetail.getFftPoints()), froadPointRate);
					orderDetail.setFftPointsAmount(fftpointAmount.doubleValue());
					BigDecimal bankpointAmount = AmountUtils.getPointExchangeAmount(String.valueOf(orderDetail.getBankPoints()), bankPointRate);
					orderDetail.setBankPointsAmount(bankpointAmount.doubleValue());
					
				} catch (TException e2) {
					LogCvt.info("获取积分比列接口出错",e2);
				}

				
				
				//订单优惠信息
				HashMap<String,double[]> orderMap = getOrderCutMoney(clientId, orderId);
				//订单状态
//				OrderStatus orderStatusEnums = OrderStatus.getOrderStatus(orderDetail.getOrderStatus());
//				orderDetail.setOrderStatus(orderStatusEnums != null ? orderStatusEnums.getDescribe() : "");
				
				//支付方式
				PaymentMethod paymentMethodEnums = PaymentMethod.getPaymentMethod(orderDetail.getPaymentMethod());
				if(paymentMethodEnums != null){
					if(PaymentMethod.invalid.equals(paymentMethodEnums)){
						orderDetail.setPaymentMethod("尚未支付");
					}else{
						orderDetail.setPaymentMethod(paymentMethodEnums.getDescribe());
					}
				}else if(StringUtil.isBlank(orderDetail.getPaymentMethod())){
					orderDetail.setPaymentMethod("尚未支付");
				}
				
				if(orderDetailVo.getSubOrderDetailVoListSize() > 0){
					Iterator<SubOrderDetailVo> subOrderIter = orderDetailVo.getSubOrderDetailVoListIterator();
					List<SubOrderDetailPojo> subOrderDetailList = new ArrayList<SubOrderDetailPojo>();
					SubOrderDetailVo subOrderVo = null;
					SubOrderDetailPojo subOrder = null;
					ProductDetailVo productDetail = null;
					OrderProductPojo orderProduct = null;
					//子订单

					while(subOrderIter.hasNext()){

						subOrderVo = subOrderIter.next();
						subOrder = new SubOrderDetailPojo();
						BeanUtils.copyProperties(subOrder, subOrderVo);
						
						//子订单商品
						if(subOrderVo.getProductDetailVoListSize() > 0){
							Iterator<ProductDetailVo> productIter = subOrderVo.getProductDetailVoListIterator();
							List<OrderProductPojo> orderProductList = new ArrayList<OrderProductPojo>();

							while(productIter.hasNext()){

								productDetail = productIter.next();
								orderProduct = new OrderProductPojo();
								BeanUtils.copyProperties(orderProduct, productDetail);
								
								double[] cutMoney = orderMap.get(orderProduct.getProductId());
								if(cutMoney != null){
									//设置红包优惠金额
									orderProduct.setVouCutMoeny(cutMoney[0]);
									//设置满减优惠金额
									orderProduct.setTotalCutMoney(cutMoney[1]);
								}
								
								//关联活动信息
								String[] activeId = null;
								if(StringUtil.isNotEmpty(productDetail.getActiveId())){
									activeId =  productDetail.getActiveId().split(",");
								}
								
								FindActiveRuleListVoResultVo activeRes = null;
								List<FindActivePojo> list = new ArrayList<FindActivePojo>();
								if(activeId != null && activeId.length > 0){
									List<String> ids=Arrays.asList(activeId);
									FindActiveRuleListVo activeIds = new FindActiveRuleListVo();
									activeIds.setActiveIdList(ids);
									try {
										activeRes = activeRuleInfoService.getActiveRuleListByIdList(activeIds);
										if(Constants.RESULT_CODE_SUCCESS.equals(activeRes.getResultVo().getResultCode()) && !ArrayUtil.empty(activeRes.getActiveRuleInfoVoList()) ){
											FindActivePojo activePojo ;
											for(FindActiveVo findActiveVo : activeRes.getActiveRuleInfoVoList()){
												activePojo = new FindActivePojo();
												BeanUtils.copyProperties(activePojo,findActiveVo);
												list.add(activePojo);
											 }  
										}
									} catch (TException e1) {
										LogCvt.error("根据Id查询活动基本信息接口异常",e1);
									}
								}
								orderProduct.setActiveList(list);
								
								//如果返回无效的时间，调商品接口查询一遍
								if(orderProduct.getStartTime() == 0 || orderProduct.getEndTime() == 0){
									ProductOperateVoReq productFilterVoReq = new ProductOperateVoReq();
									productFilterVoReq.setClientId(clientId);
									productFilterVoReq.setProductId(productDetail.getProductId());
									productFilterVoReq.setType(null);
									//productFilterVoReq.setFilter(filter);
									
									ProductInfoVo productInfoVo=null;
									try{
										productInfoVo = productService.queryProductDetail(productFilterVoReq);
									}catch(TException e){
										LogCvt.error("商品详情接口异常",e);
									}
									if(productInfoVo != null && productInfoVo.getProduct() != null){
										//增加商品购买开始和结束时间
										orderProduct.setStartTime(productInfoVo.getProduct().getStartTime());
										orderProduct.setEndTime(productInfoVo.getProduct().getEndTime());
									}
									
								}
								
								orderProductList.add(orderProduct);
							}
							subOrder.setOrderProductList(orderProductList);
						}
						//子订单配送信息
						if(subOrderVo.isSetShippingDetailVo()){
							ShippingDetailPojo shippingPojo = new ShippingDetailPojo();
							BeanUtils.copyProperties(shippingPojo, subOrderVo.getShippingDetailVo());
							ShippingStatus ss = ShippingStatus.getShippingStatus(shippingPojo.getShippingStatus());
							if(ss != null){
								shippingPojo.setShippingStatus(ss.getDescribe());
							}
							subOrder.setShippingDetail(shippingPojo);
						}
						subOrderDetailList.add(subOrder);
					}
					orderDetail.setSubOrderDetailList(subOrderDetailList);
				}
				
				//配送信息
				if(orderDetailVo.isSetDeliverInfoDetailVo()){
					DeliverInfoDetailVo deliverInfoDetailVo = orderDetailVo.getDeliverInfoDetailVo();
					DeliverInfoPojo deliverInfoPojo = new DeliverInfoPojo();
					BeanUtils.copyProperties(deliverInfoPojo, deliverInfoDetailVo);
					//除去逗号分割
					deliverInfoPojo.setAddress(deliverInfoPojo.getAddress() != null ? deliverInfoPojo.getAddress().replace(",", "") : "");
					orderDetail.setDeliverInfo(deliverInfoPojo);
				}
				
				//收货人信息
				if(StringUtil.isNotBlank(orderDetailVo.getRecvId())){
					RecvInfoVo recvInfo = null;
					try {
						recvInfo = merchantOutletFavoriteService.getRecvInfoVoById(clientId, memberCode, orderDetailVo.getRecvId());
					} catch (TException e) {
						LogCvt.error("查询收货人信息接口异常", e);
					}
					if(recvInfo != null){

						String address = recvInfo.getTreePathName() +recvInfo.getAddress();
						//除去逗号分割
						address=address.replace(",", "");
						orderDetail.setAddress(address);
					}
					
				}

			}
			resMap.put("orderDetail", orderDetail);
			
		}else{
			resMap.put(Results.code, res.getResultVo().getResultCode());
			resMap.put(Results.msg, res.getResultVo().getResultDesc());
		}
		return resMap;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 取消订单
	 *@description 
	 *@author Liebert
	 *@date 2015年4月3日 下午2:49:04
	 */
	public HashMap<String,Object> cancelOrder(String clientId, String orderId){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		if(StringUtil.isBlank(orderId)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "订单Id不能为空");
			return resMap;
		}
		
		DeleteOrderVoReq req = new DeleteOrderVoReq();
		req.setOrderId(orderId);
		req.setClientId(clientId);
		
		DeleteOrderVoRes res = null;
		
		try {
			res = orderService.deleteOrder(req);
		} catch (TException e) {
			LogCvt.error("取消订单接口调用异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "取消订单失败");
			return resMap;
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			resMap.put(Results.code, "0000");
			resMap.put(Results.msg, "取消订单成功");
		}else{
			resMap.put(Results.code, res.getResultVo().getResultCode());
			resMap.put(Results.msg, res.getResultVo().getResultDesc());
		}
		
		return resMap;
	}
	
	
	
	
	
	
	/**
	 * 订单确认收货
	 * @param clientId
	 * @param orderId
	 * @param subOrderId
	 * @return
	 */
	public HashMap<String,Object> comfirmReceipt(String clientId, String orderId, String subOrderId){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		if(StringUtil.isBlank(orderId)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "订单Id不能为空");
			return resMap;
		}
		
		if(StringUtil.isBlank(subOrderId)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "子订单Id不能为空");
			return resMap;
		}
		
		ReceiptOrderReq req = new ReceiptOrderReq();
		req.setOrderId(orderId);
		req.setSubOrderId(subOrderId);
		req.setClientId(clientId);
		
		ShippingOrderVoRes res = null;
		try {
			res = orderService.receiptOrder(req);
		} catch (TException e) {
			LogCvt.error("确认收货接口调用异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "确认收货失败");
			return resMap;
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			resMap.put(Results.code, ResultCode.success.getCode());
			resMap.put(Results.msg, "确认收货成功");
		}else{
			resMap.put(Results.code, res.getResultVo().getResultCode());
			resMap.put(Results.msg, res.getResultVo().getResultDesc());
		}
		
		return resMap;
		
	}
	
	

	
	
	
	/**
	 * 积分兑换订单列表
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 上午10:55:19
	 */
	public HashMap<String,Object> getPointExchangeOrderList(String clientId, Long memberCode, String orderType, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		GetPointExchangeListVoReq req = new GetPointExchangeListVoReq();
		req.setClientId(clientId);
		req.setMemberCode(memberCode);
		req.setQueryFlag(orderType != null ? orderType : "0");
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pageNumber != null ? pageNumber : PAGE_NUMBER);
		pageVo.setPageSize(pageSize != null ? pageSize : PAGE_SIZE);
		pageVo.setLastPageNumber(lastPageNumber != null ? lastPageNumber : 0);
		pageVo.setFirstRecordTime(firstRecordTime != null ? firstRecordTime : 0);
		pageVo.setLastRecordTime(lastRecordTime != null ? lastRecordTime : 0);
		
		req.setPage(pageVo);
		
		GetPointExchangeListVoRes res = new GetPointExchangeListVoRes();
		try {
			res = orderService.getPointExchangeList(req);
		} catch (TException e) {
			LogCvt.error("查询积分兑换订单列表接口异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询积分兑换订单列表失败");
			return resMap;
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().resultCode)){
			List<PointExchangeOrderPojo> orderList = new ArrayList<PointExchangeOrderPojo>();
			if(res.getPointExchangeVoListSize() > 0){
				Iterator<PointExchangeVo> pointIter = res.getPointExchangeVoListIterator();
				PointExchangeVo pointVo = null;
				PointExchangeOrderPojo pointPojo = null;
				while(pointIter.hasNext()){

					pointVo = pointIter.next();
					pointPojo = new PointExchangeOrderPojo();

					BeanUtils.copyProperties(pointPojo, pointVo);
					
					//订单状态
					OrderStatus orderStatusEnums = OrderStatus.getOrderStatus(pointPojo.getOrderStatus());
					pointPojo.setOrderStatus(orderStatusEnums != null ? orderStatusEnums.getDescribe() : "");
					if(OrderStatus.paysuccess.equals(orderStatusEnums)){
						pointPojo.setOrderStatus("支付成功");
					}

					orderList.add(pointPojo);
				}
			}
			resMap.put("orderList", orderList);
			
			PagePojo page = new PagePojo();
			if(res.isSetPage()){

				BeanUtils.copyProperties(page, res.getPage());
				page.setHasNext(page.getPageNumber() < page.getPageCount());

			}
			resMap.put("page", page);
			
		}else{
			resMap.put(Results.code, res.getResultVo().getResultCode());
			resMap.put(Results.msg, res.getResultVo().getResultDesc());
		}
		
		return resMap;
		
	}
	
	
	
	
	
	/**
	 * 积分兑换 订单详情
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 上午11:10:02
	 */
	public HashMap<String,Object> getPointExchangeOrderDetail(String clientId, Long memberCode, String orderId){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		GetPointExchangeDetailVoReq req = new GetPointExchangeDetailVoReq ();
		
		req.setClientId(clientId);
		req.setMemberCode(memberCode);
		req.setOrderId(orderId);
		
		GetPointExchangeDetailVoRes res = new GetPointExchangeDetailVoRes ();
		try {
			res = orderService.getPointExchangeDetail(req);
		} catch (TException e) {
			LogCvt.error("查询积分兑换订单列表接口异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询积分兑换订单列表失败");
			return resMap;
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			PointExchangeOrderPojo pointPojo = new PointExchangeOrderPojo();
			if(res.isSetPointExchangeVo()){
				PointExchangeVo pointVo = res.getPointExchangeVo();

				BeanUtils.copyProperties(pointPojo, pointVo);
				
				//订单状态
				OrderStatus orderStatusEnums = OrderStatus.getOrderStatus(pointPojo.getOrderStatus());
				pointPojo.setOrderStatus(orderStatusEnums != null ? orderStatusEnums.getDescribe() : "");
				if(OrderStatus.paysuccess.equals(orderStatusEnums)){
					pointPojo.setOrderStatus("支付成功");
				}

			}
			
			resMap.put("orderDetail", pointPojo);
			
			DeliverInfoPojo deliverPojo = new DeliverInfoPojo();
			if(res.isSetDeliverInfoDetailVo()){
				DeliverInfoDetailVo deliverVo = res.getDeliverInfoDetailVo();

				BeanUtils.copyProperties(deliverPojo, deliverVo);
				//除去逗号分割
				deliverPojo.setAddress(deliverPojo.getAddress() != null ? deliverPojo.getAddress().replace(",", "") : "");
				
				//收货人信息
				if(StringUtil.isNotBlank(deliverPojo.getRecvId())){
					RecvInfoVo recvInfo = null;
					try {
						recvInfo = merchantOutletFavoriteService.getRecvInfoVoById(clientId, memberCode, deliverPojo.getRecvId());
					} catch (TException e) {
						LogCvt.error("查询收货人信息接口异常", e);
					}
					if(recvInfo != null){

						String address = recvInfo.getTreePathName() + recvInfo.getAddress();
						//除去逗号分割
						address=address.replace(",", "");
						deliverPojo.setAddress(address);
					}
					
				}
			}
			resMap.put("deliverInfo", deliverPojo);
		}else{
			resMap.put(Results.code, res.getResultVo().getResultCode());
			resMap.put(Results.msg, res.getResultVo().getResultDesc());
		}
		return resMap;
		
	}

	
	
	
	
	
	/**
	 * 面对面订单列表
	 *@description 
	 *@author Liebert
	 *@date 2015年5月7日 下午7:24:17
	 */
	public Map<String,Object> getQrcodeOrderList(String clientId, Long memberCode, String orderStatus, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		GetQrcodeOrderSummaryVoReq req = new GetQrcodeOrderSummaryVoReq();
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pageNumber != null ? pageNumber : PAGE_NUMBER);
		pageVo.setPageSize(pageSize != null ? pageSize : PAGE_SIZE);
		pageVo.setLastPageNumber(lastPageNumber != null ? lastPageNumber : 0);
		pageVo.setFirstRecordTime(firstRecordTime != null ? firstRecordTime : 0);
		pageVo.setLastRecordTime(lastRecordTime != null ? lastRecordTime : 0);

		req.setClientId(clientId);
		req.setMemberCode(memberCode);
		if(StringUtil.isNotBlank(orderStatus)){
			req.setOrderStatus(orderStatus);
		}
		req.setPage(pageVo);
		
		GetQrcodeOrderSummaryVoRes res = null;
		try{
			res = orderService.getQrcodeOrderList(req);
		}catch(TException e){
			LogCvt.error("查询面对面订单列表接口异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询面对面订单列表失败");
			return resMap;
		}
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			List<QrcodeOrderSummaryResVo> orderList = new ArrayList<QrcodeOrderSummaryResVo>();
			
			if(res.getOrderSummaryVoListSize() > 0){
				Iterator<QrcodeOrderSummaryVo> voIter = res.getOrderSummaryVoListIterator();
				QrcodeOrderSummaryResVo order = null;
				while(voIter.hasNext()){
					order = new QrcodeOrderSummaryResVo();

					BeanUtils.copyProperties(order, voIter.next());

					orderList.add(order);
				}
			}
			
			PagePojo page = new PagePojo();
			if(res.isSetPage()){

				BeanUtils.copyProperties(page, res.getPage());
				page.setHasNext(page.getPageNumber() < page.getPageCount());

			}
			
			resMap.put("orderSummaryVoList", orderList);
			resMap.put("page", page);
			return resMap;
		}else{
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询面对面订单列表失败");
			return resMap;
		}
		
	}
	
	
	
	/**
	 * 面对面订单详情
	 *@description 
	 *@author Liebert
	 *@date 2015年5月7日 下午7:45:33
	 */
	public Map<String,Object> getQrcodeOrderDetail(String clientId, Long memberCode, String orderId){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		GetQrcodeOrderDetailVoReq req = new GetQrcodeOrderDetailVoReq();
		req.setOrderId(orderId);
		req.setClientId(clientId);
		req.setMemberCode(memberCode);
		
		GetQrcodeOrderDetailVoRes res;
		try {
			res = orderService.getQrcodeOrderDetail(req);
		} catch (TException e1) {
			LogCvt.error("查询面对面订单详情接口异常",e1);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询面对面订单详情失败");
			return resMap;
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			QrcodeOrderDetailResVo orderDetailResVo = new QrcodeOrderDetailResVo();
			if(res.getQrcodeOrderDetailVo()!=null){

				BeanUtils.copyProperties(orderDetailResVo, res.getQrcodeOrderDetailVo());
				//支付方式
				PaymentMethod paymentMethodEnums = PaymentMethod.getPaymentMethod(orderDetailResVo.getPaymentMethod());
				if(paymentMethodEnums != null){
					if(PaymentMethod.invalid.equals(paymentMethodEnums)){
						orderDetailResVo.setPaymentMethod("尚未支付");
					}else{
						orderDetailResVo.setPaymentMethod(paymentMethodEnums.getDescribe());
					}
				}else if(StringUtil.isBlank(orderDetailResVo.getPaymentMethod())){
					orderDetailResVo.setPaymentMethod("尚未支付");
				}
				
				//获取门店信息
				String outletId = orderDetailResVo.getOutletId();
				OutletVo outlet;
				try {
					//默认都是已经评论过的（即不展示评论按钮）
					boolean isComment = true ;
					
					if(StringUtil.isNotBlank(outletId)){
						//设置是否评论过(若订单不成功则不展示去评论)
						isComment = outletCommentService.isExitsFaceToFaceComment(memberCode.toString(), outletId,orderId);
						orderDetailResVo.setIsComment(isComment);
						
						//设置门店名称
						outlet = outletService.getOutletByOutletId(outletId);
						if(outlet != null ){
							orderDetailResVo.setOutletName(outlet.getOutletName());
						}
					}
						

				} catch (TException e) {
					LogCvt.error("根据门店ID查询门店接口异常",e);
				}
				
			}
			resMap.put(Results.result, orderDetailResVo);
			return resMap;
			
		}else{
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "查询面对面订单列表失败");
			return resMap;
		}
		
	}
	
	
	
	/**
	 * getSubOrderDetail:(查询子订单详情)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年8月26日 下午3:54:05
	 * @param subOrderId
	 * @param clientId
	 * 
	 */
	public Map<String, Object> getSubOrderDetail(String subOrderId, String clientId) {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		GetSubOrderVoReq getSubOrderVoReq = new GetSubOrderVoReq();
		getSubOrderVoReq.setClientId(clientId);
		getSubOrderVoReq.setSubOrderId(subOrderId);

		SubOrderSimplePojo subOrderSimplePojo = null;
		try {
			LogCvt.info("查询子订单详情传入参数: subOrderId[" + subOrderId + "],clientId[" + clientId + "]");
			GetSubOrderVoRes res = orderService.getSubOrder(getSubOrderVoReq);
			if (!StringUtil.empty(res.getOrderId())) {
				subOrderSimplePojo = new SubOrderSimplePojo();
				List<OrderProductPojo> list = null;
				BeanUtils.copyProperties(subOrderSimplePojo, res);
				// 处理关联商品信息
				if (!ArrayUtil.empty(res.getProductList())) {
					list = new ArrayList<OrderProductPojo>();
					OrderProductPojo productPojo = null;
					for (ProductDetailVo temp : res.getProductList()) {
						productPojo = new OrderProductPojo();
						BeanUtils.copyProperties(productPojo, temp);
						list.add(productPojo);
					}
					subOrderSimplePojo.setProductList(list);
				}
			}
			resMap.put(Results.result, subOrderSimplePojo);
		} catch (TException e) {
			LogCvt.error("查询子订单详情接口异常", e);
		}
		return resMap;
	}
	
	/**
	 * getPreference:(查询订单或者子订单或者某个商品的优惠金额)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月9日 下午7:26:58
	 * @param clientId
	 * @param memberCode
	 * @param orderId
	 * @param subOrderId
	 * @param productId
	 * @return
	 * 
	 */
	public Map<String, Object> getOrderPreference(String clientId,Long memberCode,String orderId,String subOrderId,String productId){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		//组装请求体
		FindMarketOrderReqVo orderReqVo = new FindMarketOrderReqVo();
		orderReqVo.setClientId(clientId);
		orderReqVo.setOrderId(orderId);
		orderReqVo.setSubOrderId(subOrderId);
		orderReqVo.setProductId(productId);
		 
		FindMarketOrderResVo res;
		try {
			res = activeRunService.findMarketOrder(orderReqVo);
			if(!ArrayUtil.empty(res.getFindMarketSubOrderList())){
				//如果是大订单，则循环取子订单
				for(FindMarketSubOrderVo marketSubOrderVo : res.getFindMarketSubOrderList()){
					String oId=marketSubOrderVo.getSubOrderId();
					List<SubOrderPreferencePojo> plist = new ArrayList<SubOrderPreferencePojo>();
					//循环取子订单里面的商品优惠信息
					List<FindMarketSubOrderProductVo> slist = marketSubOrderVo.getFindMarketSubOrderProductList();
					SubOrderPreferencePojo orderProductVo = null ; 
					for(FindMarketSubOrderProductVo temp : slist){
						orderProductVo = new SubOrderPreferencePojo();
						BeanUtils.copyProperties(orderProductVo, temp);
						plist.add(orderProductVo);
					}
					resMap.put(oId, plist);
				}
			}
		} catch (TException e) {
			LogCvt.error("查询子订单优惠金额接口异常", e);
		}

		return resMap;
	}
	
	/**
	 * 查看物流信息
	 *@description 
	 *@author yfy
	 * @throws TException 
	 *@date 2015年12月1日 下午15:29:31
	 */
	public Map<String,Object> getLogistics(String subOrderId) {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		DeliveryWayBillVo req = new DeliveryWayBillVo();
		req.setSubOrderId(subOrderId);
		try{
			DeliveryWayBillVo res = deliveryWayBillService.getDeliveryWayBillById(req);
			if(StringUtil.isNotBlank(res.getId())){
				DeliveryBillVo deliveryBillVo = new DeliveryBillVo();
				BeanUtils.copyProperties(deliveryBillVo, res);
				String message = res.getMessage();
				if(StringUtil.isNotBlank(message)){
					List<DeliveryMessageVo> msgList = new ArrayList<DeliveryMessageVo>();
					String[] msg = message.split("##");
					for(String strs : msg){
						DeliveryMessageVo msgVo = new DeliveryMessageVo();
						String[] str = strs.split("@@");
						msgVo.setCreateTime(str[0]);//时间
						msgVo.setMsg(str[1]);//事件
						msgList.add(msgVo);
					} 
					deliveryBillVo.setMsgList(msgList);
				}
				resMap.put("deliveryBillVo", deliveryBillVo);
			}else{
				resMap.put(Results.code, ResultCode.failed.getCode());
				resMap.put(Results.msg, "查看物流信息失败");
			}
		} catch (TException e) {
			LogCvt.error("查看物流信息接口异常", e);
		}
		return resMap;
	}
	
	/**
	 * 精品商城商品确认收货更改物流状态
	 *@description 
	 *@author yfy
	 * @throws TException 
	 *@date 2015年12月2日 下午15:21:31
	 */
	public Map<String,Object> confirmReceipt(String clientId, String subOrderId) {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		UpdateSubOrderLogisticVoReq voReq = new UpdateSubOrderLogisticVoReq();
		voReq.setClientId(clientId);
		voReq.setSubOrderId(subOrderId);
		voReq.setDeliveryState(ShippingStatus.receipt.getCode());//2-已收货（0-未发货1-已发货）
		try{
			//更新子订单配送状态
			UpdateSubOrderLogisticVoRes res = orderService.updateSubOrderLogistic(voReq);
			if(res.getResultVo().getResultCode().equals(ResultCode.success.getCode())){
				DeliveryWayBillVo billVo = new DeliveryWayBillVo();
				billVo.setSubOrderId(subOrderId);
				billVo.setShippingState(1);//1-人工确认(0-未收货   2-系统确认)
//				billVo.setStatus(DeliveryStatus.sign_in.getCode());//3-已签收
				//更新物流信息状态
				ResultVo resultVo = deliveryWayBillService.updateDeliveryWayBillByCondition(billVo);
				if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
					resMap.put(Results.code, ResultCode.success.getCode());
					resMap.put(Results.msg, "确认收货成功");
				}else{
					resMap.put(Results.code, resultVo.getResultCode());
					resMap.put(Results.msg, resultVo.getResultDesc());
				}
			}else{
				resMap.put(Results.code, res.getResultVo().getResultCode());
				resMap.put(Results.msg, res.getResultVo().getResultDesc());
			}
		} catch (TException e) {
			LogCvt.error("精品商城商品确认收货更改物流状态接口异常", e);
		}
		return resMap;
	}
	
	
	/**
	 * checkBeforeOrder:检验订单数据有效性
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年12月4日 下午6:49:12
	 * @param clientId
	 * @param orderId
	 * @param LoginId
	 * @return
	 * 
	 */
	public Map<String,Object> checkBeforeOrder(String clientId ,String orderId, String LoginId) {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		CashierVoReq voReq = new CashierVoReq();
		voReq.setClientId(clientId);
		voReq.setMemberName(LoginId);
		voReq.setOrderId(orderId);
		CashierVoRes res = null ;
		try{
			res = orderService.checkBeforeCashier(voReq);
			if(res.getResultVo().getResultCode().equals(ResultCode.success.getCode())){
				resMap.put("cash", res.getCash());
				resMap.put("orderId", res.getOrderId());
			}else{
				resMap.put(Results.code, res.getResultVo().getResultCode());
				resMap.put(Results.msg, res.getResultVo().getResultDesc());
			}
		} catch (TException e) {
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "校验订单有效接口异常");
			LogCvt.error("检验订单数据有效性接口异常", e);
		}
		return resMap;
	}
	
	
	/**
	 * getOrderCutMoney:获取订单优惠金额
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年12月15日 下午12:03:27
	 * @param clientId
	 * @param orderId
	 * @return
	 *  double[]   0-红包优惠金额  1-满减优惠金额 
	 */
	public HashMap<String, double[]> getOrderCutMoney(String clientId,String orderId){
		//查询订单优惠信息
		HashMap<String, double[]> orderMap = new HashMap<String, double[]>();
		double[] money; 
		FindMarketOrderReqVo arg0 = new FindMarketOrderReqVo();
		arg0.setClientId(clientId);
		arg0.setOrderId(orderId);
		try {
			FindMarketOrderResVo resOrderVo = activeRunService.findMarketOrder(arg0);
			if(!ArrayUtil.empty(resOrderVo.getFindMarketSubOrderList())){
				for(FindMarketSubOrderVo subOrderVo : resOrderVo.getFindMarketSubOrderList()){
					for(FindMarketSubOrderProductVo temp : subOrderVo.getFindMarketSubOrderProductList()){
						money = new double[2];
						String pId = temp.getProductId();
						//红包或优惠券优惠金额
						money[0] = temp.getVouMoney()+temp.getVipVouMoney();
						//满减优惠金额
						money[1] = temp.getCutMoney()+temp.getVipCutMoney();
						orderMap.put(pId, money);
					}
				}
			}
		} catch (TException e2) {
			LogCvt.error("订单详情查询订单优惠金额接口异常",e2);
		}
		return orderMap;
	}
	
	/**
	 * 校验是否是有效的贴膜卡手机号
	 * @param mobileNum 手机号
	 * @param clientId 客户端id
	 * @author 周煜涵
	 * @return
	 * @date 2015年12月21日 下午3:20:29
	 */
	public Map<String,Object> checkFoilCardPhone(String mobileNum,String clientId){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		ResultVo resultVo = null;
		Boolean isValidNum = false; //是否有效号码
		try {
			resultVo = paymentService.verifyFoilCardNum(mobileNum,clientId);
			//结果码判断
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.resultCode)) {
				isValidNum = true;
			}else {
				resMap.put(Results.msg, "请输入有效的贴膜卡手机号");
			}
		} catch (TException e) {
			LogCvt.error("校验是否是有效的贴膜卡手机号接口异常", e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "请输入有效的贴膜卡手机号");
		}
		resMap.put("isValid", isValidNum);
		return resMap;
	}
	
	
	/**
	 * createHfOrder:创建惠付订单
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2016年1月4日 下午4:02:00
	 * @param clientId
	 * @param memberCode
	 * @param memberName
	 * @param orderVo
	 * @return
	 * 
	 */
	public Map<String,Object> createHfOrder(String clientId, Long memberCode, String memberName,String phone, QrcodeOrderPojo orderVo){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		//简单密码标识
		boolean isSimplePayPwd = false ;
		
		//只有纯积分支付或者纯积分+优惠券/红包时，需要在创建订单时传入支付密码，其他情况在收银台支付时校验
		if(StringUtil.isNotBlank(orderVo.getPayPassWord()) ){
			//用户使用银行积分或者联盟积分，需要校验支付密码
			if( (StringUtil.isNotBlank(orderVo.getBankPoint()) && !"0".equals(orderVo.getBankPoint()) ) || 
					StringUtil.isNotBlank(orderVo.getUnionPoint()) && !"0".equals(orderVo.getUnionPoint()) ) {
				//校验必要参数
				if(StringUtil.isNotBlank(orderVo.getPayPassWord()) && StringUtil.isNotBlank(orderVo.getCreateSource())) {
					CreateSource cs = SimpleUtils.codeToCreateSource(orderVo.getCreateSource());
//					String paypwd = SimpleUtils.decodePwd(orderVo.getPayPassWord(), cs);
					String paypwd = orderVo.getPayPassWord();
					if (CreateSource.pc.equals(cs) || cs == null) {
			            LogCvt.info("[密码加密] >> 渠道：PC，支付密码：加密处理");
			            paypwd = userEngineSupport.encryptPwd(paypwd);
			            if (StringUtil.isBlank(paypwd)) {
			            	LogCvt.info("[密码加密] >> 支付密码加密失败");
			            	resMap.put(Results.code, "9999");
							resMap.put(Results.msg, "支付密码加密失败");
							return resMap;
						}
			        }
					ResultBean verifyPwd = userEngineSupport.verifyMemberPayPwd(memberCode, paypwd, clientId);
					if(!verifyPwd.isSuccess()){
						LogCvt.info(String.format("支付密码验证失败:%s",JSON.toJSONString(verifyPwd)));
						resMap.put(Results.code, StringUtil.isBlank(verifyPwd.getCode()) ? "9999" : verifyPwd.getCode() );
						resMap.put(Results.msg, verifyPwd.getMsg());
						return resMap;
					}else{
						LogCvt.info(String.format("支付密码验证成功! memberCode:%s",memberCode));
					}
					ResultBean validPwd = userEngineSupport.validationPayPwdRule(memberCode, paypwd);
					if(!validPwd.isSuccess()){
						isSimplePayPwd = true;
					}
				}else {
					LogCvt.info(String.format("支付密码验证缺少必要参数:{ciphertextPwd=%s,createSource=%s}",orderVo.getPayPassWord(),orderVo.getCreateSource()));
					resMap.put(Results.code, "1001");
					resMap.put(Results.msg, "缺少必要的请求参数");
					return resMap;
				}
			}
		}
		//创建虚拟商品（操作员信息）
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.personal_h5);
		originVo.setClientId(clientId);
		originVo.setOperatorId(memberCode);
		//商品信息
		OutletProductDiscountVo productVo = new OutletProductDiscountVo();
		productVo.setClientId(clientId);
		productVo.setOutletId(orderVo.getOutletId());
		productVo.setMerchantId(orderVo.getMerchantId());
		productVo.setConsumeAmount(orderVo.getConsumeAmount());
		productVo.setNotDiscountAmount(orderVo.getNotDiscountAmount());
		if(StringUtil.isNotBlank(orderVo.getDiscountRate())){
			productVo.setDiscountRate(Double.parseDouble(orderVo.getDiscountRate()));
		}
		//二维码商品ID
		String productId = "";
		AddOutletProductVoRes rep;
		try {
			rep = productService.addH5OutletProduct(originVo, productVo);
			if(Constants.RESULT_CODE_SUCCESS.equals(rep.getResultVo().getResultCode())){
				productId = rep.getOutletProductQrCodeVo().getProductId();
			}
			if(StringUtil.isBlank(productId)){
				LogCvt.info("二维码商品创建失败");
				resMap.put(Results.code,"9999");
				resMap.put(Results.msg,"惠付订单创建失败");
				return resMap;
			}
		} catch (TException e1) {
			resMap.put(Results.code,"9999");
			resMap.put(Results.msg,"惠付订单创建失败");
			return resMap;
		}

		//创建订单
		AddPrefPayOrderReq req = new AddPrefPayOrderReq();
		req.setClientId(clientId);
		req.setMemberCode(memberCode);
		req.setMemberName(memberName);
		req.setBankPoint(orderVo.getBankPoint());
		req.setFftPoint(orderVo.getUnionPoint());
		req.setCreateSource(orderVo.getCreateSource());
		req.setCashCouponId(orderVo.getCouponsNo());
		req.setRedPacketId(orderVo.getRedPacketNo());
		req.setPhone(phone);
		req.setProductId(productId);
		
		//如果使用了积分
		if(orderVo.getBankPoint() > 0){
			// 积分查询
			UserEnginePointsVo userPoints;
			try {
				userPoints = memberInformationService.selectMemberPointsInfoByLoginID(clientId,memberName);
				BigDecimal bankPoint = new BigDecimal(orderVo.getBankPoint());
				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(String.valueOf(orderVo.getBankPoint()),userPoints.getBankPointsExchageRate());
				//设置积分值和积分抵扣金额
				req.setBankPoint(bankPoint.intValue());
				req.setPointMoney(pointAmount.doubleValue());
				if(StringUtil.isNotBlank(userPoints.getBankPointsExchageRate())){
					req.setPointRate(Long.parseLong(userPoints.getBankPointsExchageRate()));
				}
			} catch (TException e) {
				LogCvt.error("查询用户积分接口异常",e);
				resMap.put(Results.code,"9999");
				resMap.put(Results.msg,"查询用户积分接口异常");
				return resMap;
			}

		}else if(orderVo.getUnionPoint() > 0d ){
			// 积分查询
			UserEnginePointsVo userPoints;
			try {
				userPoints = memberInformationService.selectMemberPointsInfoByLoginID(clientId,memberName);
				BigDecimal unionPoint = new BigDecimal(orderVo.getUnionPoint());
				BigDecimal pointAmount = AmountUtils.getPointExchangeAmount(String.valueOf(orderVo.getUnionPoint()),userPoints.froadPointsExchageRate);
				//设置联盟积分值和积分抵扣金额
				req.setFftPoint(unionPoint.doubleValue());
				req.setPointMoney(pointAmount.doubleValue());
				if(StringUtil.isNotBlank(userPoints.getFroadPointsExchageRate())){
					req.setPointRate(Long.parseLong(userPoints.getFroadPointsExchageRate()));
				}
			} catch (TException e) {
				LogCvt.error("查询用户积分接口异常",e);
				resMap.put(Results.code,"9999");
				resMap.put(Results.msg,"查询用户积分接口异常");
				return resMap;
			}
		}
		
		try {
			AddPrefPayOrderRes res = orderService.addPrefPayOrder(req);
			
			if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())
					&& StringUtil.isNotBlank(res.getOrderId())){
				LogCvt.info(String.format("惠付订单创建成功>> 订单号:%s,---- isNeedCash:%s", res.getOrderId(), res.getIsNeedCash()));
				
				// isNeedCash 0 不跳收银台 （不需要支付现金,但需要使用积分） 1 需要跳收银台（需要现金支付）  2 订单支付成功（纯红包或者优惠券）
				String isNeedCash = res.getIsNeedCash();
				if("2".equals(isNeedCash)){ 
					//订单直接成功
					resMap.put(Results.code, "0000");
					resMap.put(Results.msg, "订单成功");
					resMap.put("isSimplePayPwd", isSimplePayPwd);
				}else if ("0".equals(isNeedCash)){ //不需要现金支付，但有积分支付（自动调用支付接口）
					// 支付渠道列表
					Map<String,Object> payChannelMap = clientConfigSupport.getClientPaymentChannel(clientId);
					DisplayPayChannelVo displayChannel = new DisplayPayChannelVo();
					displayChannel = (DisplayPayChannelVo) payChannelMap.get("payChannel");
					
					PayOrdersVo payOrdersVo = new PayOrdersVo();
					int bankPoint = orderVo.getBankPoint();
					double froadPoint = orderVo.getUnionPoint();
					//判断是否使用联盟积分
					if(froadPoint > 0.0){
						payOrdersVo.setPointOrgNo(displayChannel.getUnionPointOrgNo());
						payOrdersVo.setType(1);
					}
					//判断是否使用银行积分
					if(bankPoint > 0 ){
						payOrdersVo.setPointOrgNo(displayChannel.getBankPointOrgNo());
						payOrdersVo.setType(2);
					}
					payOrdersVo.setOrderId(res.getOrderId());
					payOrdersVo.setClientId(clientId);
					payOrdersVo.setMemberCode(memberCode);
					payOrdersVo.setCiphertextPwd(orderVo.getPayPassWord());
					payOrdersVo.setCreateSource(orderVo.getCreateSource());
					//自动支付
					resMap.putAll(cashierSupport.payOrders(payOrdersVo));
				}
				//订单信息
				resMap.put("orderId", res.getOrderId());
				resMap.put("cash", res.getCost());
				return resMap;
			}else{
				LogCvt.info(String.format("惠付订单创建失败>> 结果码:%s, 结果描述:%s", res.getResultVo().getResultCode(), res.getResultVo().getResultDesc()));
				resMap.put(Results.code,"9999");
				if(res!=null && res.getResultVo()!=null && StringUtil.isNotBlank(res.getResultVo().getResultDesc())){
					resMap.put(Results.msg,res.getResultVo().getResultDesc());
				}else{
					resMap.put(Results.msg,"惠付订单创建失败");
				}
			}
		} catch (TException e) {
			LogCvt.error("创建惠付订单接口异常", e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "惠付订单创建失败");
		}
		return resMap;
	}
	
}
