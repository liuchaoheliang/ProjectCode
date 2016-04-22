package com.froad.jetty.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.enums.DeliveryType;
import com.froad.enums.H5ResultCode;
import com.froad.enums.ModuleID;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.jetty.service.OrderService;
import com.froad.jetty.vo.FillOrderRequestVo;
import com.froad.jetty.vo.PlaceOrderRequestVo;
import com.froad.jetty.vo.PlaceOrderResponseVo;
import com.froad.jetty.vo.QueryOrderResponseVo;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.SeckillProductLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.SeckillProductLogicImpl;
import com.froad.po.AcceptOrder;
import com.froad.po.Org;
import com.froad.po.SeckillProduct;
import com.froad.po.mongo.ProductMongo;
import com.froad.thrift.client2.ServiceClient;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.service.SeckillOrderService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.member.UserEnginePointsVo;
import com.froad.thrift.vo.order.AddDeliveryInfoVoReq;
import com.froad.thrift.vo.order.AddDeliveryInfoVoRes;
import com.froad.util.Arith;
import com.froad.util.SimpleID;

/**
 * 订单业务实现类
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 上午10:18:57
 * @version v1.0
 */
public class OrderServiceImpl implements OrderService {
	
	private SeckillProductLogic seckillProductLogic = new SeckillProductLogicImpl();
	
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	public OrderServiceImpl(){}
	
	@Override
	public ResponseVo calculateAmount(String loginId, 
			PlaceOrderRequestVo reqVo, 
			SeckillProduct product){
		Integer total = product.getSecPrice() * reqVo.getBuyNum();
		Integer vipTotal = product.getVipSecPrice() * reqVo.getBuyNum();
		// 秒杀商品总金额
		Double totalAmount = Arith.div(total.doubleValue(), 1000, 3);
		// 秒杀商品VIP总金额
		Double vipTotalAmount = Arith.div(vipTotal.doubleValue(), 1000, 3);
		// 商品运费
		Double deliveryMoney = Arith.div(product.getDeliveryMoney(), 1000, 3);
		
		// 当前用户需要支付的金额
		// 是不是vip
		Double needPayAmount = reqVo.isVip() ? vipTotalAmount : totalAmount;
		// 加上运费
		needPayAmount = Arith.add(needPayAmount, deliveryMoney);
		
		// 当前用户需要支付的积分金额、现金金额
		Double needPayPointAmount = null, needPayCashAmount = null;
		
		// 积分、积分+现金支付
		if (PaymentMethod.froadPoints.getCode().equals(reqVo.getPayType().toString())
				|| PaymentMethod.bankPoints.getCode().equals(reqVo.getPayType().toString())
				|| PaymentMethod.froadPointsAndCash.getCode().equals(reqVo.getPayType().toString())
				|| PaymentMethod.bankPointsAndCash.getCode().equals(reqVo.getPayType().toString())) {
			// 查询用户积分
			UserEnginePointsVo uePoints = null;
			ServiceClient serviceClient = new ServiceClient();
			try {
				MemberInformationService.Iface memberInformationService = (MemberInformationService.Iface)serviceClient.createClient(MemberInformationService.Iface.class);
				uePoints = memberInformationService.selectMemberPointsInfoByLoginID(reqVo.getClientId(), loginId);
				if (uePoints == null || !uePoints.isResultSuccess()) {
					LogCvt.info(H5ResultCode.userPointFaild.getMsg() 
							+ "[memberCode=" + reqVo.getMemberCode() 
							+ ", clientId=" + reqVo.getClientId() 
							+ ", loginId=" + loginId 
							+ ", pointOrgNo=" + reqVo.getPointOrgNo() + "]");
					return new ResponseVo(H5ResultCode.userPointFaild);
				}
				
				LogCvt.debug("用户积分信息：[froadOrgNo=" + uePoints.getFroadOrgNo() 
						+ ", froadPoints=" + uePoints.getFroadPoints() 
						+ ", froadPointsRate=" + uePoints.getFroadPointsExchageRate() 
						+ ", bankPoints=" + uePoints.getBankPoints() 
						+ ", bankPointsRate=" + uePoints.getBankPointsExchageRate() + "]");
			} catch (TException e) {
				LogCvt.error("查询用户积分异常[memberCode=" + reqVo.getMemberCode() 
						+ ", clientId=" + reqVo.getClientId() 
						+ ", loginId=" + loginId 
						+ ", pointOrgNo=" + reqVo.getPointOrgNo() + "]", e);
				return new ResponseVo(H5ResultCode.userPointCenterException);
			} finally {
				try {
					serviceClient.returnClient();
				} catch (Exception e) {
					LogCvt.error("释放Thrift连接异常", e);
					return new ResponseVo(H5ResultCode.userPointCenterException);
				}
			}
			
			// 校验用户积分的数据
			if (PaymentMethod.froadPoints.getCode().equals(reqVo.getPayType().toString()) 
					|| PaymentMethod.froadPointsAndCash.getCode().equals(reqVo.getPayType().toString())) {
				
				if (StringUtils.isEmpty(uePoints.getFroadPoints()) 
						|| StringUtils.isEmpty(uePoints.getFroadPointsExchageRate())) {
					LogCvt.debug(H5ResultCode.userPointFaild.getMsg() 
							+ ", 用户方付通积分为空[memberCode=" + reqVo.getMemberCode() 
							+ ", clientId=" + reqVo.getClientId() 
							+ ", loginId=" + loginId 
							+ ", froadOrgNo=" + uePoints.getFroadPoints() 
							+ ", froadPointsExchageRate=" + uePoints.getFroadPointsExchageRate() + "]");
					return new ResponseVo(H5ResultCode.userPointFaild);
					
				} else if (!reqVo.getPointOrgNo().equals(uePoints.getFroadOrgNo())) {
					LogCvt.debug(H5ResultCode.userPointOrgNoNotMatch.getMsg() 
							+ "[memberCode=" + reqVo.getMemberCode() 
							+ ", clientId=" + reqVo.getClientId() 
							+ ", loginId=" + loginId 
							+ ", pointOrgNo=" + reqVo.getPointOrgNo() 
							+ ", froadOrgNo=" + uePoints.getFroadOrgNo() + "]");
					return new ResponseVo(H5ResultCode.userPointOrgNoNotMatch);
				}
				
			} else if (PaymentMethod.bankPoints.getCode().equals(reqVo.getPayType().toString()) 
					|| PaymentMethod.bankPointsAndCash.getCode().equals(reqVo.getPayType().toString())) {
				
				if (StringUtils.isEmpty(uePoints.getBankPoints()) 
						|| StringUtils.isEmpty(uePoints.getBankPointsExchageRate())) {
					LogCvt.debug(H5ResultCode.userPointFaild.getMsg() 
							+ ", 用户银行积分为空[memberCode=" + reqVo.getMemberCode() 
							+ ", clientId=" + reqVo.getClientId() 
							+ ", loginId=" + loginId 
							+ ", bankOrgNo=" + uePoints.getBankPoints() 
							+ ", bankPointsExchageRate=" + uePoints.getBankPointsExchageRate() + "]");
					return new ResponseVo(H5ResultCode.userPointFaild);
					
				} else if (reqVo.getPointOrgNo().equals(uePoints.getFroadOrgNo())) {
					// 接口返回的只有方付通积分机构号，所以只简单校验一下。
					// 进到这个逻辑里，那么积分机构号不应该是方付通积分机构号
					LogCvt.debug(H5ResultCode.userPointOrgNoNotMatch.getMsg() 
							+ "[memberCode=" + reqVo.getMemberCode() 
							+ ", clientId=" + reqVo.getClientId() 
							+ ", loginId=" + loginId 
							+ ", pointOrgNo=" + reqVo.getPointOrgNo() + "]");
					return new ResponseVo(H5ResultCode.userPointOrgNoNotMatch);
				}
				
			}
			
			boolean pointsAndCash = false;
			Double points = null, pointsRate = null;
			if (PaymentMethod.froadPoints.getCode().equals(reqVo.getPayType().toString())) {
				points = Double.parseDouble(uePoints.getFroadPoints());
				pointsRate = Double.parseDouble(uePoints.getFroadPointsExchageRate());
			} else if (PaymentMethod.froadPointsAndCash.getCode().equals(reqVo.getPayType().toString())) {
				points = Double.parseDouble(uePoints.getFroadPoints());
				pointsRate = Double.parseDouble(uePoints.getFroadPointsExchageRate());
				pointsAndCash = true;
			} else if (PaymentMethod.bankPoints.getCode().equals(reqVo.getPayType().toString())) {
				points = Double.parseDouble(uePoints.getBankPoints());
				pointsRate = Double.parseDouble(uePoints.getBankPointsExchageRate());
			} else if (PaymentMethod.bankPointsAndCash.getCode().equals(reqVo.getPayType().toString())) {
				points = Double.parseDouble(uePoints.getBankPoints());
				pointsRate = Double.parseDouble(uePoints.getBankPointsExchageRate());
				pointsAndCash = true;
			}
			
			// 把总的支付金额换算成积分
			Double payTotalPoints = Arith.mul(needPayAmount, pointsRate);
			// 剩余积分
			Double overPoints =  Arith.sub(points, payTotalPoints);
			if (overPoints >= 0) { // 方付通积分足够支付
				needPayPointAmount = payTotalPoints;
				needPayCashAmount = 0D;
			} else { // 方付通积分余额不足
				if (pointsAndCash) { // 混合支付
					// 用足积分
					needPayPointAmount = points;
					// 不足部分换算成现金，用现金支付
					needPayCashAmount = Arith.div(overPoints, pointsRate, 3);
				} else {
					LogCvt.info(H5ResultCode.userPointNotEnough.getMsg() 
							+ "[memberCode=" + reqVo.getMemberCode() 
							+ ", pointOrgNo=" + reqVo.getPointOrgNo() 
							+ ", points=" + points 
							+ ", needPayPoints=" + payTotalPoints + "]");
					return new ResponseVo(H5ResultCode.userPointNotEnough);
				}
			}
		}
		
		// 纯现金支付
		else if (PaymentMethod.cash.getCode().equals(reqVo.getPayType().toString())) {
			needPayPointAmount = 0D;
			needPayCashAmount = needPayAmount;
		}
		
		if (needPayPointAmount == null || needPayCashAmount == null) {
			LogCvt.info(H5ResultCode.failed.getMsg() + "[memberCode=" + reqVo.getMemberCode() + ", needPayPointAmount=" + needPayPointAmount + ", needPayPointAmount=" + needPayPointAmount + "]");
			return new ResponseVo(H5ResultCode.failed);
		}
		
		reqVo.setPointAmount(needPayPointAmount);
		reqVo.setCashAmount(needPayCashAmount);
		
		return new ResponseVo(H5ResultCode.success);
	}
	
	@Override
	public ResponseVo checkFoilCardNum(PlaceOrderRequestVo reqVo) {
		// 检查贴膜卡号
		ResultVo resultVo = null;
		ServiceClient serviceClient = new ServiceClient();
		try {
			PaymentService.Iface paymentService = (PaymentService.Iface)serviceClient.createClient(PaymentService.Iface.class);
			resultVo = paymentService.verifyFoilCardNum(reqVo.getFoilCardNum(), reqVo.getClientId());
			if (resultVo == null || !ResultCode.success.getCode().equals(resultVo.getResultCode())) {
				LogCvt.debug("检查贴膜卡号，" + resultVo.getResultDesc() + "[foilCardNum=" + reqVo.getFoilCardNum() + ", clientId=" + reqVo.getClientId() + "]");
			}
			return new ResponseVo(resultVo.getResultCode(), resultVo.getResultDesc());
		} catch (TException e) {
			LogCvt.error("检查贴膜卡号，调用Thrift异常", e);
			return new ResponseVo(H5ResultCode.foilCardNumFailed);
		} catch (Exception e) {
			LogCvt.error("检查贴膜卡号异常", e);
			return new ResponseVo(H5ResultCode.foilCardNumFailed);
		} finally {
			try {
				serviceClient.returnClient();
			} catch (Exception e) {
				LogCvt.error("释放Thrift连接异常", e);
				return new ResponseVo(H5ResultCode.foilCardNumFailed);
			}
		}
	}
	
	@Override
	public ResponseVo prePlaceOrder(PlaceOrderRequestVo reqVo, SeckillProduct product) {
		Date now = new Date();
		boolean productFlag = true;
		if (product == null) {
			LogCvt.debug("秒杀商品不存在[productId=" + reqVo.getProductId() + ", clientId=" + reqVo.getClientId() + "]");
			productFlag = false;
		} else if (!ProductStatus.onShelf.getCode().equals(product.getIsMarketable())) {
			LogCvt.debug("秒杀商品未上架[productId=" + reqVo.getProductId() + ", clientId=" + reqVo.getClientId() + "]");
			productFlag = false;
		} else if (now.getTime() < product.getStartTime().getTime()) {
			LogCvt.debug("商品秒杀未开始[productId=" + reqVo.getProductId() + ", clientId=" + reqVo.getClientId() + "]");
			productFlag = false;
		} else if (product.getEndTime().getTime() < now.getTime()) {
			LogCvt.debug("商品秒杀已过期[productId=" + reqVo.getProductId() + ", clientId=" + reqVo.getClientId() + "]");
			productFlag = false;
		}
		
		if (!productFlag) {
			return new ResponseVo(H5ResultCode.productNotExist);
		}
		
		// 判断用户购买资格，如果为0，则用户不限购
		if (product.getBuyLimit().intValue() != 0){
			if (reqVo.getBuyNum().intValue() > product.getBuyLimit().intValue()) {
				LogCvt.debug("用户购买数量大于商品购买限额[memberCode=" + reqVo.getMemberCode() + ", productId=" + reqVo.getProductId() + ", buyNum=" + reqVo.getBuyNum() + ", buyLimit=" + product.getBuyLimit() + "]");
				return new ResponseVo(H5ResultCode.productLimit);
			}
			int bought = seckillProductLogic.getMemberBuyNum(reqVo.getMemberCode(), product); // 当前购买数量
			if ((bought + reqVo.getBuyNum()) > product.getBuyLimit().intValue()) {
				LogCvt.debug("用户购买数量大于商品购买限额[memberCode=" + reqVo.getMemberCode() + ", productId=" + reqVo.getProductId() + ", bought=" + bought +", buyNum=" + reqVo.getBuyNum() + ", buyLimit=" + product.getBuyLimit() + "]");
				return new ResponseVo(H5ResultCode.productLimit);
			}
		}
		
		// 判断库存
		boolean storePass = product.getStore().intValue() >= reqVo.getBuyNum().intValue();
		if (!storePass) {
			LogCvt.debug("商品库存不足[memberCode=" + reqVo.getMemberCode() + ", productId=" + reqVo.getProductId() + ", store=" + product.getStore() + ", buyNum=" + reqVo.getBuyNum() + "]");
			return new ResponseVo(H5ResultCode.productStore);
		}
		
		return new ResponseVo(H5ResultCode.success);
	}
	
	@Override
	public ResponseVo prePlaceOrder2(PlaceOrderRequestVo reqVo, SeckillProduct product) {
		// 校验收货地址参数
		boolean pass = true;
		if (product == null) {
			LogCvt.debug("秒杀商品不存在[productId=" + reqVo.getProductId() + ", clientId=" + reqVo.getClientId() + "]");
			pass = false;
		} else if (ProductType.group.getCode().equals(product.getProductType())) {
			if (StringUtils.isEmpty(reqVo.getPhone())) {
				LogCvt.debug("接收券、短信的手机号为空[productId=" + reqVo.getProductId() 
						+ ", clientId=" + reqVo.getClientId() 
						+ ", productType=" + product.getProductType() + "]");
				pass = false;
			}
		} else if (ProductType.presell.getCode().equals(product.getProductType())) {
			if (StringUtils.isEmpty(reqVo.getDeliveryType())) {
				LogCvt.debug("配送类型为空[productId=" + reqVo.getProductId() 
						+ ", clientId=" + reqVo.getClientId() 
						+ ", productType=" + product.getProductType() + "]");
				pass = false;
			} else if (DeliveryType.take.getCode().equals(reqVo.getDeliveryType()) 
					|| DeliveryType.home_or_take.getCode().equals(reqVo.getDeliveryType())) {
				if (StringUtils.isEmpty(reqVo.getPhone()) 
						|| StringUtils.isEmpty(reqVo.getOrgCode()) 
						|| StringUtils.isEmpty(reqVo.getOrgName())) {
					LogCvt.debug("手机号、提货网点机构号或网点机构名称为空[productId=" + reqVo.getProductId() 
							+ ", clientId=" + reqVo.getClientId() 
							+ ", productType=" + product.getProductType() 
							+ ", phone=" + reqVo.getPhone() 
							+ ", orgCode=" + reqVo.getOrgCode() 
							+ ", orgName=" + reqVo.getOrgName() + "]");
					pass = false;
				} else {
					// 校验orgCode是否正确
					Org org = commonLogic.getOrgByOrgCode(reqVo.getOrgCode(), reqVo.getClientId());
					if (org == null) {
						return new ResponseVo(H5ResultCode.orgNotExist);
					} else if (!org.getIsEnable()) {
						return new ResponseVo(H5ResultCode.orgDisabled);
					}
				}
			}
		}
		
		if (!pass) {
			return new ResponseVo(H5ResultCode.missingParam);
		}
		
		ResponseVo responseVo = prePlaceOrder(reqVo, product);
		if (!responseVo.success()) {
			return responseVo;
		}
		
		return new ResponseVo(H5ResultCode.success);
	}
	
	@Override
	public ResponseVo placeOrder(String loginId, PlaceOrderRequestVo reqVo, PlaceOrderResponseVo resVo) {
		long productInfoStart = System.currentTimeMillis();
		SeckillProduct product = seckillProductLogic.getProduct(reqVo.getClientId(), reqVo.getProductId());
		long productInfoEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单-获取秒杀商品信息，耗时：" + (productInfoEnd - productInfoStart) + " ms");
		
		// 下单前的预处理
		long checkProductStart = System.currentTimeMillis();
		ResponseVo responseVo = prePlaceOrder(reqVo, product);
		long checkProductEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单-校验秒杀商品信息，耗时：" + (checkProductEnd - checkProductStart) + " ms");
		if (!responseVo.success()) {
			return responseVo;
		}
		
		// 计算下单的金额
		long calculateAmountStart = System.currentTimeMillis();
		responseVo = calculateAmount(loginId, reqVo, product);
		long calculateAmountEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单-计算订单金额，耗时：" + (calculateAmountEnd - calculateAmountStart) + " ms");
		if (!responseVo.success()) {
			return responseVo;
		}
		
		// 创建受理订单
		String acceptOrderId = genAcceptOrderId(); // 生成受理订单号
		AcceptOrder acceptOrder = new AcceptOrder();
		acceptOrder.setReqId(acceptOrderId);
		acceptOrder.setClientId(reqVo.getClientId());
		acceptOrder.setMerchantId(product.getMerchantId());
		acceptOrder.setMemberCode(reqVo.getMemberCode());
		acceptOrder.setMemberName(reqVo.getMemberName());
		acceptOrder.setProductId(reqVo.getProductId());
		acceptOrder.setProductName(product.getProductName());
		acceptOrder.setProductType(product.getProductType());
		acceptOrder.setProductImage(product.getProductImage());
		acceptOrder.setDeliveryOption(product.getDeliveryOption());
		Double secPrice = Arith.div(product.getSecPrice().doubleValue(), 1000, 3); // 换算一下
		acceptOrder.setSecPrice(secPrice);
		Double vipSecPrice = Arith.div(product.getVipSecPrice().doubleValue(), 1000, 3); // 换算一下
		acceptOrder.setVipSecPrice(vipSecPrice);
		Double deliveryAmount = Arith.div(product.getDeliveryMoney().doubleValue(), 1000, 3); // 换算一下
		acceptOrder.setDeliveryMoney(deliveryAmount);
		acceptOrder.setBuyNum(reqVo.getBuyNum());
		acceptOrder.setPayType(reqVo.getPayType());
		acceptOrder.setCashType(reqVo.getCashType());
		acceptOrder.setPointOrgNo(reqVo.getPointOrgNo());
		acceptOrder.setPointAmount(reqVo.getPointAmount());
		acceptOrder.setCashOrgNo(reqVo.getCashOrgNo());
		acceptOrder.setCashAmount(reqVo.getCashAmount());
		acceptOrder.setFoilCardNum(reqVo.getFoilCardNum());
		acceptOrder.setCreateSource(reqVo.getSource());
		acceptOrder.setOrderQueueDate(new Date());
		
		// 生成受理订单
		long createAcceptOrderStart = System.currentTimeMillis();
		boolean createOk = seckillProductLogic.createAcceptOrder(acceptOrder);
		long createAcceptOrderEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单-生成受理订单，耗时：" + (createAcceptOrderEnd - createAcceptOrderStart) + " ms");
		if (!createOk) {
			LogCvt.debug("创建受理订单失败" + acceptOrder.toString());
			return new ResponseVo(H5ResultCode.acceptOrderFailed);
		} 
		
		resVo.setAcceptOrderId(acceptOrderId);
		resVo.setIntervalSeconds(0);
		
		LogCvt.info("秒杀商品已受理，受理订单已生成. [memberCode=" + reqVo.getMemberCode() + ", productId=" + reqVo.getProductId() + ", acceptOrderId=" + acceptOrderId + "]");
		
		return new ResponseVo(H5ResultCode.success);
	}
	
	@Override
	public ResponseVo placeOrder2(String loginId, PlaceOrderRequestVo reqVo, PlaceOrderResponseVo resVo) {
		long productInfoStart = System.currentTimeMillis();
		SeckillProduct product = seckillProductLogic.getProduct(reqVo.getClientId(), reqVo.getProductId());
		if (DeliveryType.take.getCode().equals(reqVo.getDeliveryType())) { // 自提不需要运费
			product.setDeliveryMoney(0);
		}
		long productInfoEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单-获取秒杀商品信息，耗时：" + (productInfoEnd - productInfoStart) + " ms");
		
		// 下单前的预处理
		long checkProductStart = System.currentTimeMillis();
		ResponseVo responseVo = prePlaceOrder2(reqVo, product);
		long checkProductEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单-校验秒杀商品信息，耗时：" + (checkProductEnd - checkProductStart) + " ms");
		if (!responseVo.success()) {
			return responseVo;
		}
		
		// 计算下单的金额
		long calculateAmountStart = System.currentTimeMillis();
		responseVo = calculateAmount(loginId, reqVo, product);
		long calculateAmountEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单-计算订单金额，耗时：" + (calculateAmountEnd - calculateAmountStart) + " ms");
		if (!responseVo.success()) {
			return responseVo;
		}
		
		// 创建受理订单
		String acceptOrderId = genAcceptOrderId(); // 生成受理订单号
		AcceptOrder acceptOrder = new AcceptOrder();
		acceptOrder.setReqId(acceptOrderId);
		acceptOrder.setClientId(reqVo.getClientId());
		acceptOrder.setMerchantId(product.getMerchantId());
		acceptOrder.setMemberCode(reqVo.getMemberCode());
		acceptOrder.setMemberName(reqVo.getMemberName());
		acceptOrder.setProductId(reqVo.getProductId());
		acceptOrder.setProductName(product.getProductName());
		acceptOrder.setProductType(product.getProductType());
		acceptOrder.setProductImage(product.getProductImage());
		acceptOrder.setDeliveryOption(product.getDeliveryOption());
		Double secPrice = Arith.div(product.getSecPrice().doubleValue(), 1000, 3); // 换算一下
		acceptOrder.setSecPrice(secPrice);
		Double vipSecPrice = Arith.div(product.getVipSecPrice().doubleValue(), 1000, 3); // 换算一下
		acceptOrder.setVipSecPrice(vipSecPrice);
		Double deliveryAmount = Arith.div(product.getDeliveryMoney().doubleValue(), 1000, 3); // 换算一下
		acceptOrder.setDeliveryMoney(deliveryAmount);
		acceptOrder.setBuyNum(reqVo.getBuyNum());
		acceptOrder.setPayType(reqVo.getPayType());
		acceptOrder.setCashType(reqVo.getCashType());
		acceptOrder.setPointOrgNo(reqVo.getPointOrgNo());
		acceptOrder.setPointAmount(reqVo.getPointAmount());
		acceptOrder.setCashOrgNo(reqVo.getCashOrgNo());
		acceptOrder.setCashAmount(reqVo.getCashAmount());
		acceptOrder.setFoilCardNum(reqVo.getFoilCardNum());
		acceptOrder.setCreateSource(reqVo.getSource());
		
		acceptOrder.setRecvId(reqVo.getRecvId());
		acceptOrder.setDeliveryType(reqVo.getDeliveryType());
		acceptOrder.setPhone(reqVo.getPhone());
		acceptOrder.setOrgCode(reqVo.getOrgCode());
		acceptOrder.setOrgName(reqVo.getOrgName());
		acceptOrder.setOrderQueueDate(new Date());
		
		// 生成受理订单
		long createAcceptOrderStart = System.currentTimeMillis();
		boolean createOk = seckillProductLogic.createAcceptOrder(acceptOrder);
		long createAcceptOrderEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单-生成受理订单，耗时：" + (createAcceptOrderEnd - createAcceptOrderStart) + " ms");
		if (!createOk) {
			LogCvt.debug("创建受理订单失败" + acceptOrder.toString());
			return new ResponseVo(H5ResultCode.acceptOrderFailed);
		} 
		
		resVo.setAcceptOrderId(acceptOrderId);
		resVo.setIntervalSeconds(0);
		
		LogCvt.info("秒杀商品已受理，受理订单已生成. [memberCode=" + reqVo.getMemberCode() + ", productId=" + reqVo.getProductId() + ", acceptOrderId=" + acceptOrderId + "]");
		
		return new ResponseVo(H5ResultCode.success);
	}
	
	@Override
	public QueryOrderResponseVo queryOrder(String acceptOrderId) {
		Map<String,String> orderMap = seckillProductLogic.getAcceptOrderById(acceptOrderId);
		QueryOrderResponseVo qoResVo = null;
		if (orderMap != null) {
			qoResVo = new QueryOrderResponseVo();
			qoResVo.setResultFlag(orderMap.get("result_flag"));
			qoResVo.setIntervalSeconds(5);
			qoResVo.setOrderId(orderMap.get("order_id"));
		}
		return qoResVo;
	}

	@Override
	public ResponseVo fillOrder(FillOrderRequestVo reqVo) {
		// complex required
		ProductMongo productMongo = seckillProductLogic.getProductMongo(reqVo.getOrderId());
		boolean pass = true;
		if (productMongo == null) {
			pass = false;
		} else if (ProductType.group.getCode().equals(productMongo.getType())) {
			if (StringUtils.isEmpty(reqVo.getPhone())) {
				pass = false;
			}
		} else if (ProductType.presell.getCode().equals(productMongo.getType())) {
			if (StringUtils.isEmpty(reqVo.getDeliveryType())) {
				pass = false;
			} else if (DeliveryType.take.getCode().equals(reqVo.getDeliveryType()) 
					|| DeliveryType.home_or_take.getCode().equals(reqVo.getDeliveryType())) {
				if (StringUtils.isEmpty(reqVo.getPhone()) 
						|| StringUtils.isEmpty(reqVo.getOrgCode()) 
						|| StringUtils.isEmpty(reqVo.getOrgName())) {
					pass = false;
				}
			}
		}
		
		if (!pass) {
			return new ResponseVo(H5ResultCode.missingParam);
		}
		
		AddDeliveryInfoVoReq adiReq = new AddDeliveryInfoVoReq();
		adiReq.setOrderId(reqVo.getOrderId());
		adiReq.setDeliveryType(reqVo.getDeliveryType());
		adiReq.setDeliverId(reqVo.getRecvId());
		adiReq.setRecvId(reqVo.getRecvId());
		adiReq.setOrgCode(reqVo.getOrgCode());
		adiReq.setOrgName(reqVo.getOrgName());
		adiReq.setPhone(reqVo.getPhone());
		ServiceClient serviceClient = new ServiceClient();
		try {
			SeckillOrderService.Iface seckillOrderService = (SeckillOrderService.Iface)serviceClient.createClient(SeckillOrderService.Iface.class);
			AddDeliveryInfoVoRes adiRes = seckillOrderService.updateDeliveryInfo(adiReq);
			LogCvt.debug("秒杀填充订单信息结果[code="  + adiRes.getResultCode() + ", msg=" + adiRes.getResultDesc() + "]");
			return new ResponseVo(adiRes.getResultCode(), adiRes.getResultDesc());
		} catch (TException e) {
			LogCvt.error("秒杀填充订单信息，调用Thrift异常", e);
			return new ResponseVo(H5ResultCode.thriftException);
		} finally {
			try {
				serviceClient.returnClient();
			} catch (Exception e) {
				LogCvt.error("释放Thrift连接异常", e);
				return new ResponseVo(H5ResultCode.thriftException);
			}
		}
	}
	
	@Override
	public String genAcceptOrderId() {
		return "AO" + new SimpleID(ModuleID.seckill).nextId();
	}
	
}
