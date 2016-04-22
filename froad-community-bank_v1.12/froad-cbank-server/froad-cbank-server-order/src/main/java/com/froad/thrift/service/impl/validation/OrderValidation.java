package com.froad.thrift.service.impl.validation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.DeliveryType;
import com.froad.enums.GivePointState;
import com.froad.enums.ModuleID;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderRequestType;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.MemberInformationLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.MemberInformationLogicImpl;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.OrderInfo;
import com.froad.po.OrderValidateInfo;
import com.froad.po.Org;
import com.froad.po.RecvInfo;
import com.froad.po.Store;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductActivitiesInfo;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductCityOutlet;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.ProductOutlet;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.qrcodeproduct.OutletProduct;
import com.froad.po.shoppingcart.req.OrderShoppingListReq;
import com.froad.support.CommonSupport;
import com.froad.support.MemberInformationSupport;
import com.froad.support.OrderSupport;
import com.froad.support.PaymentSupport;
import com.froad.support.impl.CommonSupportImpl;
import com.froad.support.impl.MemberInformationSupportImpl;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.PaymentSupportImpl;
import com.froad.thirdparty.dto.response.pe.MemberInfo;
import com.froad.thirdparty.dto.response.pe.MemberPointsInfo;
import com.froad.thrift.vo.order.AddDeliveryInfoVoReq;
import com.froad.thrift.vo.order.AddMerchantVo;
import com.froad.thrift.vo.order.AddOrderForSeckillVoReq;
import com.froad.thrift.vo.order.AddOrderVoReq;
import com.froad.thrift.vo.order.AddPrefPayOrderReq;
import com.froad.thrift.vo.order.AddProductVo;
import com.froad.thrift.vo.order.AddQrcodeOrderVoReq;
import com.froad.thrift.vo.order.AddSeckillOrderVoReq;
import com.froad.thrift.vo.order.AddVIPOrderVoReq;
import com.froad.thrift.vo.order.CashierVoReq;
import com.froad.thrift.vo.order.CloseOrderVoReq;
import com.froad.thrift.vo.order.DeleteOrderVoReq;
import com.froad.thrift.vo.order.DeliverInfoDetailVo;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoReq;
import com.froad.thrift.vo.order.GetOrderByQrcodeVoReq;
import com.froad.thrift.vo.order.GetOrderDetailVoReq;
import com.froad.thrift.vo.order.GetOrderPaymentResultVoReq;
import com.froad.thrift.vo.order.GetOrderSummaryVoReq;
import com.froad.thrift.vo.order.GetPointExchangeDetailVoReq;
import com.froad.thrift.vo.order.GetPointExchangeListVoReq;
import com.froad.thrift.vo.order.GetQrcodeOrderDetailVoReq;
import com.froad.thrift.vo.order.GetQrcodeOrderSummaryVoReq;
import com.froad.thrift.vo.order.GetSubOrderProductVoReq;
import com.froad.thrift.vo.order.GetSubOrderVoReq;
import com.froad.thrift.vo.order.GetVipDiscountVoReq;
import com.froad.thrift.vo.order.MerchantReturnVo;
import com.froad.thrift.vo.order.ProductReturnVo;
import com.froad.thrift.vo.order.RefundPayingOrderVoReq;
import com.froad.thrift.vo.order.ShippingOrderVoReq;
import com.froad.thrift.vo.order.StoreVoReq;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.OrgSuperUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;
import com.froad.util.payment.BaseSubassembly;
import com.pay.user.dto.UserResult;
import com.pay.user.helper.CreateChannel;

/**
 * 类描述：订单参数验证
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: zhangkai
 * @time: 2015年3月18日 下午3:54:48
 */
public class OrderValidation {
	
	private OrderSupport orderSupport = new OrderSupportImpl();
	
	private CommonSupport commonSupport = new CommonSupportImpl();
	
	//common项目公共
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	private MemberInformationLogic memberInformationLogic = new MemberInformationLogicImpl();

	private MemberInformationSupport memberInformationSupport = new MemberInformationSupportImpl();
	
	private PaymentSupport paymentSupport = new PaymentSupportImpl();
	
	private static SimpleID simpleOrderId = new SimpleID(ModuleID.order);

	private static SimpleID simpleSubOrderId = new SimpleID(ModuleID.suborder);
	
	private MonitorService monitorService = new MonitorManager();
	
	/**
	 * 检查字段是否为空，为空时抛出异常信息
	 * @param field 字段
	 * @param errorMsg 错误信息
	 * @throws FroadBusinessException 自定义业务异常信息
	 */
	public void dataEmptyChecker(Object field, String errorMsg) throws FroadBusinessException {
		//1.请求数据校验
		if(EmptyChecker.isEmpty(field)){
			LogCvt.error(errorMsg);
			throw new FroadBusinessException(ResultCode.failed.getCode(), errorMsg);
		}
	}
	
	/**
	 * 检查小数位数
	 * @param field 字段
	 * @param errorMsg 错误信息
	 * @throws FroadBusinessException 自定义业务异常信息
	 */
	public void dataPrecisionChecker(double number,int n,String errorMsg) throws FroadBusinessException {
		//1.请求数据校验
		String s = number + "";
		if(s.contains(".")){
			int position = s.length() - (s.indexOf(".") + 1);
	        if(position > n){
	        	throw new FroadBusinessException(ResultCode.failed.getCode(), errorMsg);
	        }
		}
	}

	/**
	 * 校验创建订单上的参数
	 * @param addOrderVoReq
	 * @return
	 */
	public ResultBean validateAddOrderParam(AddOrderVoReq addOrderVoReq) {
		long st = System.currentTimeMillis();
		LogCvt.info("-----------------------订单校验开始-----------------------");
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			/**
			 * 1.大订单校验
			 */
			boolean isSuccess = true;
			//是否发出监控项：正常校验不发；缓存错误，数据获取错误发
			boolean isMonitor = false;
			//线下积分兑换机构号
			String orderOrgCode = "";
			//是否参与营销活动 
			boolean isJoinMarketActive = false;
			//参数校验
			dataEmptyChecker(addOrderVoReq, "参数错误，无效的下单操作");
			dataEmptyChecker(addOrderVoReq.getClientId(), "参数错误，客户端ID不能为空");
			dataEmptyChecker(addOrderVoReq.getCreateSource(), "参数错误，订单来源不能为空");
			if(addOrderVoReq.getFftPoint()>0){
				dataPrecisionChecker(addOrderVoReq.getFftPoint(),2,"联盟积分只能有两位小数");
			}
			if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode())){
				//线下积分兑换
				dataEmptyChecker(addOrderVoReq.getCardNo(), "参数错误，银行卡号不能为空");
				
				//检查卡号长度
				if(addOrderVoReq.getCardNo().length() <= 15){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "银行卡号码错误");
				}
				
				//线下积分兑换
				dataEmptyChecker(addOrderVoReq.getBankPoint(), "参数错误，银行积分不能为空");
				
				//银行操作员ID
				dataEmptyChecker(addOrderVoReq.getOperatorId(), "参数错误，银行操作员ID不能为空");
				
				//银行操作员名
				dataEmptyChecker(addOrderVoReq.getOperatorName(), "参数错误，银行操作员名不能为空");
				
				//查询
				ResultBean userResult = memberInformationSupport.queryMemberAndCardInfo(addOrderVoReq.getClientId(), addOrderVoReq.getCardNo());
				if(!StringUtils.equals(ResultCode.success.getCode(), userResult.getCode())){
					LogCvt.info("通过银行卡号查询会员失败，原因："+userResult.getMsg());
				}
				Long memberCode = null;
				String memberName = null;
				if(EmptyChecker.isNotEmpty(userResult)){
					UserResult user = (UserResult) userResult.getData();
					if(EmptyChecker.isNotEmpty(user) && EmptyChecker.isNotEmpty(user.getUserList())){
						memberCode = user.getUserList().get(0).getMemberCode();
						memberName = user.getUserList().get(0).getLoginID();
						dataEmptyChecker(memberCode, "会员系统查询的会员号为空");
						dataEmptyChecker(memberName, "会员系统查询的会员名为空");
					    LogCvt.info("通过银行卡号查询会员结果：[会员号："+memberCode+" 会员名："+memberName+"]");
					}else{
						LogCvt.info("查询新注册用户");
						ResultBean queryMemberResult = memberInformationSupport.queryByLoginID(addOrderVoReq.getClientId() + addOrderVoReq.getCardNo());
						if(!StringUtils.equals(ResultCode.success.getCode(), queryMemberResult.getCode())){
							LogCvt.info("通过客户端ID和银行卡号查询新注册会员失败，原因："+queryMemberResult.getMsg());
						}
						MemberInfo memberInfoTemp = (MemberInfo) queryMemberResult.getData();
						if(EmptyChecker.isNotEmpty(memberInfoTemp)){
							memberCode = memberInfoTemp.getMemberCode();
							memberName = memberInfoTemp.getLoginID();
							LogCvt.info("查询新注册会员结果：[会员号："+memberCode+" 会员名："+memberName+"]");
						}else{
							LogCvt.info("会员系统查询结果为空，绑卡注册新用户...");
							dataEmptyChecker(addOrderVoReq.getIp(), "参数错误，IP地址不能为空");
							
							MemberInfo memberInfo = new MemberInfo();
							String pwd = addOrderVoReq.getCardNo().substring(addOrderVoReq.getCardNo().length() - 6);
							memberInfo.setLoginID(addOrderVoReq.getClientId() + addOrderVoReq.getCardNo());
							memberInfo.setLoginPwd(pwd);
							memberInfo.setRegisterIP(addOrderVoReq.getIp());
							memberInfo.setCreateChannel(CreateChannel.FFT.getValue());
							userResult = memberInformationSupport.registerMember(memberInfo);
							dataEmptyChecker(userResult, "会员绑卡注册失败");
							if(!StringUtils.equals(ResultCode.success.getCode(), userResult.getCode())){
								LogCvt.error("会员绑卡注册失败，原因："+userResult.getMsg());
								throw new FroadBusinessException(ResultCode.failed.getCode(), userResult.getMsg());
							}
							user = (UserResult) userResult.getData();
							if(EmptyChecker.isNotEmpty(user) && EmptyChecker.isNotEmpty(user.getUserList())){
								memberCode = user.getUserList().get(0).getMemberCode();
								memberName = user.getUserList().get(0).getLoginID();
								LogCvt.info("通过绑卡注册会员结果：[会员号："+memberCode+" 会员名："+memberName+"]");
							}else{
								LogCvt.error("通过绑卡注册会员失败");
							}
							dataEmptyChecker(memberCode, "会员绑卡注册失败，原因：返回会员ID为空");
							dataEmptyChecker(memberName, "会员绑卡注册失败，原因：返回会员名为空");
						}
					}
				}
				dataEmptyChecker(memberCode, "线下积分兑换会员ID不能为空");
				dataEmptyChecker(memberName, "线下积分兑换会员名不能为空");
				addOrderVoReq.setMemberCode(memberCode);
				addOrderVoReq.setMemberName(memberName);
			}else{
				dataEmptyChecker(addOrderVoReq.getMemberCode(), "会员ID不能为空");
				dataEmptyChecker(addOrderVoReq.getMemberName(), "会员名不能为空");
			}
			
			String pointRate = null;
			if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode()) || StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.online_point_order.getCode())){
//				List<Map<String, String>> listMap = paymentSupport.getPaymentChannelSetFromRedis(addOrderVoReq.getClientId());
//				if(EmptyChecker.isNotEmpty(listMap)){
//					for(Map<String, String> map : listMap) {
//						if("2".equals(map.get("type"))){
//							pointRate = map.get("point_rate");
//							break;
//						}
//					}
//				}
				pointRate = BaseSubassembly.acquireBankPointPointRate(addOrderVoReq.getClientId()).toString();
				if(EmptyChecker.isEmpty(pointRate)){
					isMonitor = true;
					throw new FroadBusinessException(ResultCode.failed.getCode(), "积分支付比例获取失败");
				}
			}
			
			//提货人|收货人详情
			//配送
			DeliverInfoDetailVo deliverInfoDetailVo = null;
			//提货人
			if(EmptyChecker.isNotEmpty(addOrderVoReq.getDeliverId())){
				RecvInfo deliverInfo = orderSupport.getRecvInfo(addOrderVoReq.getClientId(), addOrderVoReq.getMemberCode(), addOrderVoReq.getDeliverId());
				if(EmptyChecker.isNotEmpty(deliverInfo)){
					deliverInfoDetailVo = new DeliverInfoDetailVo();
					deliverInfoDetailVo.setAddress(deliverInfo.getAddress());
					deliverInfoDetailVo.setConsignee(deliverInfo.getConsignee());
					deliverInfoDetailVo.setPhone(deliverInfo.getPhone());
				}
			}
			//收货人
			if(EmptyChecker.isNotEmpty(addOrderVoReq.getRecvId()) && EmptyChecker.isEmpty(deliverInfoDetailVo)){
				RecvInfo recvInfo = orderSupport.getRecvInfo(addOrderVoReq.getClientId(), addOrderVoReq.getMemberCode(), addOrderVoReq.getRecvId());
				if(EmptyChecker.isNotEmpty(recvInfo)){
					deliverInfoDetailVo = new DeliverInfoDetailVo();
					deliverInfoDetailVo.setAddress(recvInfo.getAddress());
					deliverInfoDetailVo.setConsignee(recvInfo.getConsignee());
					deliverInfoDetailVo.setPhone(recvInfo.getPhone());
				}
			}
			
			//订单初始化
			String orderId = String.valueOf(simpleOrderId.nextId());//大订单号
			LogCvt.info("大订单号："+orderId);
			int totalPrice = 0;//实际总价
			int vipDiscount = 0;//VIP优惠价
			int totalGivePoints = 0;//订单赠送总积分
			SubOrderType subOrderType = null;//子订单类型
			List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();//子订单
			List<Store> storeList = new ArrayList<Store>();//待更新库存
			List<OrderShoppingListReq> shopingListReq = new ArrayList<OrderShoppingListReq>();//待清空购物车
			
			//{商户号_子订单类型：子订单号}
			Map<String,String> subOrderMap = new HashMap<String,String>();
			//{子订单号：子订单}
			Map<String,SubOrderMongo> subOrderListMap = new HashMap<String,SubOrderMongo>();
			
			/** 商户响应信息 */
			List<MerchantReturnVo> merchantReturnVoList = new ArrayList<MerchantReturnVo>();
			
			for(AddMerchantVo addMerchantVo : addOrderVoReq.getAddMerchantVoList()){
				/**
				 * ===============================2.商户校验===============================
				 */
				LogCvt.info("-----商户信息校验开始-----");
				dataEmptyChecker(addMerchantVo.getMerchantId(), "商户ID不能为空");
				LogCvt.info("商户ID：" + addMerchantVo.getMerchantId());
				
				/** 商户响应信息 */
				MerchantReturnVo merchantReturnVo = new MerchantReturnVo();
				merchantReturnVo.setMerchantId(addMerchantVo.getMerchantId());
				merchantReturnVo.setErrCode("0000");
				merchantReturnVo.setErrMsg("ok");
				
				//子订单号
				String subOrderId = null;
				
				//商户信息
				Map<String,String> merchantMap = commonLogic.getMerchantRedis(addOrderVoReq.getClientId(), addMerchantVo.getMerchantId());
				//商户是否存在
				if(EmptyChecker.isEmpty(merchantMap)){
					LogCvt.error("商户信息不存在，[商户ID："+addMerchantVo.getMerchantId() + ",redis-key:"+RedisKeyUtil.cbbank_merchant_client_id_merchant_id(addOrderVoReq.getClientId(), addMerchantVo.getMerchantId()));
					isSuccess = false;
					isMonitor = true;
					merchantReturnVo.setErrCode(ResultCode.failed.getCode());
					merchantReturnVo.setErrMsg(ResultCode.merchant_not_exist.getMsg());
					merchantReturnVoList.add(merchantReturnVo);
					continue;
				}
				
				//商户名称
				String merchantName = merchantMap.get("merchant_name");
				LogCvt.info("商户名称:"+merchantName);
				merchantReturnVo.setMerchantName(merchantName);
				
				//商户是否有效
				boolean isEnable = false;
				if(EmptyChecker.isNotEmpty(merchantMap.get("is_enable"))){
					isEnable = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("is_enable"))); // 商户状态
				}else{
					isMonitor = true;
					LogCvt.error("缓存中商户状态为null");
				}
				merchantReturnVo.setMerchantStatus(String.valueOf(BooleanUtils.toInteger(isEnable)));
				if(!isEnable){
					isMonitor = true;
					LogCvt.error("商户无效，商户ID：" + addMerchantVo.getMerchantId());
					isSuccess = false;
					merchantReturnVo.setErrCode(ResultCode.failed.getCode());
					merchantReturnVo.setErrMsg(ResultCode.merchant_enable.getMsg());
					merchantReturnVoList.add(merchantReturnVo);
					continue;
				}
				
				//是否银行机构商户  0否：普通商户，1是：银行商户
				boolean merchantStatus = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("merchant_status"))); 
				//如果是银行商户，merchantId取机构号，merchantName取机构名
				String orgCode = null;
				String orgName = null;
				if(merchantStatus){
					Org org = commonLogic.queryByMerchantInfo(addOrderVoReq.getClientId(), addMerchantVo.getMerchantId());
					if(EmptyChecker.isEmpty(org)){
						isMonitor = true;
						LogCvt.error("商户所属机构不存在，商户ID："+addMerchantVo.getMerchantId());
						isSuccess = false;
						merchantReturnVo.setErrCode(ResultCode.failed.getCode());
						merchantReturnVo.setErrMsg(ResultCode.org_info_not_exists.getMsg());
						merchantReturnVoList.add(merchantReturnVo);
						continue;
					}
					orgCode = org.getOrgCode();
					orgName = org.getOrgName();
					if(!org.getIsEnable()){
						isMonitor = true;
						LogCvt.error("商户所属机构已被禁用，商户ID："+addMerchantVo.getMerchantId());
						isSuccess = false;
						merchantReturnVo.setErrCode(ResultCode.failed.getCode());
						merchantReturnVo.setErrMsg(ResultCode.org_enable.getMsg());
						merchantReturnVoList.add(merchantReturnVo);
						continue;
					}
				}
				
				//根据商户ID获取本级机构号与上级机构号
				Map<OrgLevelEnum, String> orgCodeMap = commonLogic.getSuperOrgByMerchantId(addOrderVoReq.getClientId(),addMerchantVo.getMerchantId());
				if(EmptyChecker.isEmpty(orgCodeMap)){
					isMonitor = true;
					LogCvt.error("商户对应机构信息不存在，商户ID："+addMerchantVo.getMerchantId());
					isSuccess = false;
					merchantReturnVo.setErrCode(ResultCode.failed.getCode());
					merchantReturnVo.setErrMsg(ResultCode.merchant_org_not_exists.getMsg());
					merchantReturnVoList.add(merchantReturnVo);
					continue;
				}
				LogCvt.info("-----商户信息校验结束-----");
				
				dataEmptyChecker(addMerchantVo.getAddProductVoList(), "参数错误，商品信息不能为空，商户号：" + addMerchantVo.getMerchantId());
				
				//商品返回信息
				List<ProductReturnVo> productReturnVoList = new ArrayList<ProductReturnVo>();
				for(AddProductVo productVo : addMerchantVo.getAddProductVoList()){
					/**
					 * ===============================3.商品校验===============================
					 */
					LogCvt.info("-----商品信息校验开始-----");
					LogCvt.info("商品ID："+productVo.getProductId());
					
					//1.商品参数校验
					dataEmptyChecker(productVo.getProductId(), "参数错误，商品ID不能为空");
					dataEmptyChecker(productVo.getQuantity(), "参数错误，商品购买数量不能为空");
					if(productVo.getQuantity() <= 0){
						isMonitor = true;
						throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品购买数量必须大于0");
					}
					
					ProductReturnVo productReturnVo = new ProductReturnVo();
					productReturnVo.setProductId(productVo.getProductId());
					productReturnVo.setOrgCode(productVo.getOrgCode());
					productReturnVo.setOrgName(productVo.getOrgName());
					productReturnVo.setErrCode("0000");
					productReturnVo.setErrMsg("ok");
					
					//商品缓存(Redis)
					Map<String,String> productMap = commonLogic.getProductRedis(addOrderVoReq.getClientId(), addMerchantVo.getMerchantId(), productVo.getProductId());
					if(EmptyChecker.isEmpty(productMap)){
						isMonitor = true;
						LogCvt.error("商品信息在redis中不存在，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(ResultCode.prodcut_not_exist.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//商品详情(Mongo)
					ProductDetail productDetail = orderSupport.queryProductDetail(productVo.getProductId());
					if(EmptyChecker.isEmpty(productDetail)){
						isMonitor = true;
						LogCvt.error("商品信息在mongo中不存在，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(ResultCode.prodcut_not_exist.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					productReturnVo.setProductName(productDetail.getName());
					LogCvt.info("商品名称：" + productDetail.getName());
					
					//2.检查商品是否下架:0-未上架 1-上架 2-下架 3-已删除 4-禁用下架
					String productStatus = productMap.get("is_marketable");
					productReturnVo.setProductStatus(String.valueOf(productStatus));
					if(!StringUtils.equals(productStatus, ProductStatus.onShelf.getCode())){
						LogCvt.error("商品不是上架状态，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg("商品["+productDetail.getName()+"]状态："+ProductStatus.getType(productStatus).getDescribe());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//3.检查商品类型
					ProductType productType = ProductType.getType(String.valueOf(productDetail.getProductType())); // 商品类型
					if(EmptyChecker.isEmpty(productType)){
						isMonitor = true;
						LogCvt.error("商品类型获取异常，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg("商品类型获取异常");
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//根据商品类型获取子订单类型
					subOrderType = getSubOrderType(productType);
					
					if(EmptyChecker.isEmpty(subOrderType)){
						isMonitor = true;
						LogCvt.error("子订单类型获取异常，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg("子订单类型获取异常");
						productReturnVoList.add(productReturnVo);
						merchantReturnVo.setProductReturnVoList(productReturnVoList);
						merchantReturnVoList.add(merchantReturnVo);
						continue;
					}
					
					//4.检查购买数量是否超出库存
					//商品库存(redis)
					int store = commonLogic.getStoreRedis(addOrderVoReq.getClientId(), addMerchantVo.getMerchantId(), productVo.getProductId());
					if(store < productVo.getQuantity()){
						LogCvt.error("购买数量超出库存，购买数量:"+productVo.getQuantity()+"，实际库存:"+store);
						isSuccess = false;
						/*throw new FroadBusinessException(ResultCode.failed.getCode(),ResultCode.product_store_not_enough.getMsg());*/
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(ResultCode.product_store_not_enough.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//5.检查商品是否在购买期内
					long productStartTime = Long.parseLong(productMap.get("start_time"));//团购|预售开始时间
					long productEndTime = Long.parseLong(productMap.get("end_time"));//团购|预售结束时间
					long curTime = System.currentTimeMillis();//当前时间
					/*checkProductBuyTime(productStartTime, productEndTime, curTime);*/
					if(productStartTime > curTime || productEndTime < curTime){
						LogCvt.error("商品不在购买期内，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg("商品不在购买期内");
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//6.配送信息校验
					int price = productDetail.getPrice();// 销售价
					DeliveryType deliveryType = null;//商品配送状态
					if(EmptyChecker.isNotEmpty(productVo.getDeliveryType())){
						deliveryType = DeliveryType.getType(productVo.getDeliveryType());//配送方式
					}
					
					DeliveryType deliveryOption = null;
					if(EmptyChecker.isNotEmpty(productDetail.getDeliveryOption())){
						deliveryOption = DeliveryType.getType(productDetail.getDeliveryOption());//商品默认配送方式
					}
					
					//配送|自提信息校验
					Map<String,Object> deliverMap = null;
					try{
						deliverMap = checkDeliverInfo(
								addOrderVoReq.getClientId(),
								addMerchantVo.getMerchantId(),
								productVo.getProductId(), 
								productDetail.getName(),
								productType,
								deliveryType, deliveryOption,
								deliverInfoDetailVo, addOrderVoReq.getRecvId(),
								addOrderVoReq.getDeliverId(),
								productVo.getOrgCode(), productVo.getOrgName(),
								addOrderVoReq.getCardNo(),
								addOrderVoReq.getMemberCode(), price,
								productVo.getQuantity(),
								addOrderVoReq.getPhone());
					}catch(FroadBusinessException e){
						LogCvt.error(e.getMsg());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(e.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					ShippingStatus shippingStatus = (ShippingStatus) deliverMap.get("shippingStatus");//配送状态
					Integer deliverMoney = (Integer) deliverMap.get("deliveryMoney");//运费
					deliveryType =  (DeliveryType) deliverMap.get("deliveryType");//配送方式
					
					//校验提货网点
					if(EmptyChecker.isNotEmpty(productVo.getOrgCode())){
						Org org = commonLogic.queryByOrgCode(addOrderVoReq.getClientId(), productVo.getOrgCode());
						if(EmptyChecker.isEmpty(org)){
							isMonitor = true;
							LogCvt.error("机构信息在mysql中不存在，机构号："+productVo.getOrgCode());
							isSuccess = false;
							productReturnVo.setErrCode(ResultCode.failed.getCode());
							productReturnVo.setErrMsg("商品提货网点不存在，机构名称：" + productVo.getOrgName());
							productReturnVoList.add(productReturnVo);
							continue;
						}else{
							if(!org.getIsEnable()){
								LogCvt.error("提货网点已被禁用，机构号"+productVo.getOrgCode());
								isSuccess = false;
								productReturnVo.setErrCode(ResultCode.failed.getCode());
								productReturnVo.setErrMsg("商品提货网点已被禁用，机构名称：" + productVo.getOrgName());
								productReturnVoList.add(productReturnVo);
								continue;
							}else{
								productReturnVo.setOrgStatus(String.valueOf(BooleanUtils.toInteger(org.getIsEnable())));
							}
						}
					}
					
					orderOrgCode = productVo.getOrgCode();
					
					//7.检查限购信息，分配限购数量
					//查询用户订单表的购买记录
					Map<String,String> userOrderMap = RedisCommon.getUserOrderCountRedis(addOrderVoReq.getClientId(),addOrderVoReq.getMemberCode(), productVo.getProductId());
					int totalCount = 0;
					int totalVipCount = 0;
					if(EmptyChecker.isNotEmpty(userOrderMap)){
						if(EmptyChecker.isNotEmpty(userOrderMap.get("count"))){
							totalCount = Integer.parseInt(userOrderMap.get("count").toString());
						}
						if(EmptyChecker.isNotEmpty(userOrderMap.get("vip_count"))){
							totalVipCount = Integer.parseInt(userOrderMap.get("vip_count").toString());
						}
					}
					
					
					int vipPrice = 0;//vip价格
					if(EmptyChecker.isNotEmpty(productDetail.getVipPrice()) && productDetail.getVipPrice()>0){
						vipPrice = productDetail.getVipPrice();
					}
					int vipQuantity = productVo.getVipQuantity();
					int normalQuantity = productVo.getQuantity() - productVo.getVipQuantity();
					
					//限购信息校验
					try{
						checkBuyLimit(BooleanUtils.toBoolean(productDetail.getIsLimit()),productDetail.getBuyLimit(),productVo.getQuantity(),productVo.getVipQuantity(),totalCount,totalVipCount,curTime,addOrderVoReq.isVip,productType,vipPrice);
					    /*Map<String,Object> limitMap = null; 
						limitMap = getBuyLimit(BooleanUtils.toBoolean(productDetail.getIsLimit()),productDetail.getBuyLimit(),productVo.getQuantity(),productVo.getVipQuantity(),totalCount,totalVipCount,curTime,addOrderVoReq.isVip);
						int vipQuantity = (Integer)limitMap.get("vipQuantity");// vip价格数量
						int normalQuantity = (Integer)limitMap.get("normalQuantity");// 普通价格数量*/
					}catch(FroadBusinessException e){
						LogCvt.error(e.getMsg());
						if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode())){
							LogCvt.error("线下积分兑换：由于限购的原因导致的无法兑换");
							isSuccess = false;
							productReturnVo.setErrCode(ResultCode.failed.getCode());
							productReturnVo.setErrMsg("由于限购的原因导致的无法兑换");
							productReturnVoList.add(productReturnVo);
							continue;
						}
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(e.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//8.赠送积分活动
					int givePoints = getGivePoints(addOrderVoReq.getClientId(), curTime,addOrderVoReq.getMemberCode(),productVo.getProductId(), productDetail.getActivitiesInfo());
					totalGivePoints += givePoints;//总赠送积分
					
					//9.VIP价
					/*Map<String,Object> activitiesMap = getVipInfo(productType,addOrderVoReq.getClientId(), curTime, addOrderVoReq.getMemberLevel(), (Integer)limitMap.get("vipQuantity"), productVo.getQuantity(), (Integer)limitMap.get("normalQuantity"), productDetail);
					int vipPrice = (Integer)activitiesMap.get("vipPrice");// vip价格
					int vipQuantity = (Integer)activitiesMap.get("vipQuantity");// vip价格数量
					int normalQuantity = (Integer)activitiesMap.get("normalQuantity");// 普通价格数量*/		
					
					//处理订单总价，除以对应积分比例换算成钱  [线上/线下只买一种商品]
					if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode()) || StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.online_point_order.getCode())){
						vipDiscount += Arith.mul(Arith.div(price - vipPrice, Double.valueOf(pointRate)), vipQuantity);//vip优惠金额
						/*totalPrice += vipDiscount + Arith.mul(Arith.div(price, Double.valueOf(pointRate)),normalQuantity) + deliverMoney;//总货币值(普通价总金额+VIP价总金额+运费)*/
						totalPrice += Arith.mul(Arith.div(vipPrice, Double.valueOf(pointRate)),vipQuantity) + Arith.mul(Arith.div(price, Double.valueOf(pointRate)),normalQuantity) + deliverMoney;//总货币值(普通价总金额+VIP价总金额+运费)
					}else{
						vipDiscount += Arith.mul(price - vipPrice, vipQuantity);//vip优惠金额
						/*totalPrice += vipDiscount + Arith.mul(price,normalQuantity) + deliverMoney;//总货币值(普通价总金额+VIP价总金额+运费)*/
						totalPrice += Arith.mul(vipPrice,vipQuantity) + Arith.mul(price,normalQuantity) + deliverMoney;//总货币值(普通价总金额+VIP价总金额+运费)
					}
					
					//线下积分兑换，银行积分必须与商品总价相等
					if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode())){
						if((Arith.mul(vipPrice,vipQuantity) + Arith.mul(price,normalQuantity)) != Arith.mul(addOrderVoReq.getBankPoint(),1000)){
							LogCvt.error("线下积分兑换：银行积分参数bankPoint："+addOrderVoReq.getBankPoint()+"，但商品总价为："+Arith.div((Arith.mul(vipPrice,vipQuantity) + Arith.mul(price,normalQuantity)),1000)+"，与购买商品总价不等，前端参数错误");
							isSuccess = false;
							productReturnVo.setErrCode(ResultCode.failed.getCode());
							productReturnVo.setErrMsg("支付的银行积分与商品总价不等");
							productReturnVoList.add(productReturnVo);
							continue;
						}
					}
					
					/**
					 * ===============================4.门店校验===============================
					 */
					/*dataEmptyChecker(productVo.getOutletId(), "门店ID不能为空");
					//查询门店
					Map<String,String> outletMap = RedisCommon.getOutletRedis(addOrderVoReq.getClientId(), productVo.getMerchantId(), productVo.getOutletId());
					String outletName = outletMap.get("outlet_name");//门店名称
					LogCvt.info("门店名称：" + outletName);
					//检查门店有效性
					boolean outletStatus = BooleanUtils.toBoolean(Integer.valueOf(outletMap.get("is_enable")));
					if(!outletStatus){
						throw new FroadBusinessException(ResultCode.failed.getCode(),ResultCode.outlet_enable.getMsg());
					}
					
					//检查商品是否属于此门店（预售商品）
					checkOutletExists(productType, productDetail.getOutletInfo(), productVo.getOutletId());*/
					
					LogCvt.info("-----商品信息校验结束-----");
					
					/**
					 * ===============================5.子订单下的商品组装===============================
					 */
					//商品Mongo
					ProductMongo productMongo = new ProductMongo();
					productMongo.setProductId(productVo.getProductId());
					productMongo.setProductName(productDetail.getName());
					productMongo.setProductImage(productDetail.getImageInfo() == null? "" : productDetail.getImageInfo().get(0).getThumbnail());
					productMongo.setMoney(price);
					productMongo.setType(productType.getCode());
					productMongo.setQuantity(normalQuantity);
					productMongo.setVipMoney(vipPrice);
					productMongo.setVipQuantity(vipQuantity);
					productMongo.setPoints(givePoints);
					if(givePoints > 0){//有赠送积分
						productMongo.setGivePointState(GivePointState.NO_GIVE.getCode());
					}
					if(EmptyChecker.isNotEmpty(deliveryType)){
						productMongo.setDeliveryOption(deliveryType.getCode());
					}
					if(EmptyChecker.isNotEmpty(shippingStatus)){
						productMongo.setDeliveryState(shippingStatus.getCode());
					}
					productMongo.setOrgCode(productVo.getOrgCode());
					productMongo.setOrgName(productVo.getOrgName());
					productMongo.setDeliveryMoney(deliverMoney);
					
					//满减活动ID
					if(EmptyChecker.isNotEmpty(productVo.getActiveId())){
						isJoinMarketActive = true;
						productMongo.setActiveId(productVo.getActiveId());
					}
					
					//满赠活动ID
					if(EmptyChecker.isNotEmpty(productVo.getGiveActiveId())){
						isJoinMarketActive = true;
						productMongo.setGiveActiveId(productVo.getGiveActiveId());
					}
					
					/**
					 * ===============================6.拆单处理===============================
					 */
					//子订单商品集合
					List<ProductMongo> products = new ArrayList<ProductMongo>();
					//获取子订单号
					String subOrderIdTemp =  subOrderMap.get(addMerchantVo.getMerchantId() + "_" + subOrderType.getCode());
					if(EmptyChecker.isNotEmpty(subOrderIdTemp)){
						subOrderId = subOrderIdTemp;
						
						SubOrderMongo subOrderTemp = subOrderListMap.get(subOrderId);
						subOrderTemp.getProducts().add(productMongo);
						
						//有赠送积分
						if(givePoints > 0){
							subOrderTemp.setIsGivePoint(BooleanUtils.toInteger(true));
						}
						
						subOrderListMap.put(subOrderId, subOrderTemp);
						
					}else{
						subOrderId = simpleSubOrderId.nextId();
						subOrderMap.put(addMerchantVo.getMerchantId() + "_" + subOrderType.getCode(), subOrderId);
						
						products.add(productMongo);
						
						//子订单Mongo
						SubOrderMongo subOrder = new SubOrderMongo();
						subOrder.setCreateTime(new Date().getTime());
						subOrder.setClientId(addOrderVoReq.getClientId());
						subOrder.setOrderId(orderId);
						subOrder.setOrderStatus(OrderStatus.create.getCode());
						subOrder.setMemberCode(addOrderVoReq.getMemberCode());
						subOrder.setMemberName(addOrderVoReq.getMemberName());
						subOrder.setSubOrderId(subOrderId);
						subOrder.setMerchantId(orgCode == null ? addMerchantVo.getMerchantId() : orgCode);
						subOrder.setMerchantName(orgCode == null ? merchantName : orgName);
						subOrder.setType(subOrderType.getCode());//子订单类型
						subOrder.setProducts(products);//商品信息
						subOrder.setForgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_one));
						subOrder.setSorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_two));
						subOrder.setTorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_three));
						subOrder.setLorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_four));
						
						//名优特惠的配送方式和配送状态，记录在子订单上；因为名优特惠子订单下的商品都是统一的，均为送货上门
						//预售的的配送方式和配送状态，记录在商品上。因为预售的配送方式依赖于商品类型，商品有自提和送货上门两种，依赖于用户选择哪种
						if(subOrderType.equals(SubOrderType.special_merchant)){
							subOrder.setDeliveryOption(DeliveryType.home.getCode());
							subOrder.setDeliveryState(ShippingStatus.unshipped.getCode());
						}
						
						//线下积分兑换直接支付完成
						if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode())){
							subOrder.setOrderStatus(OrderStatus.paysuccess.getCode());
							subOrder.setOperatorId(addOrderVoReq.getOperatorId());
							subOrder.setOperatorName(addOrderVoReq.getOperatorName());
						}
						
						//有赠送积分
						if(givePoints > 0){
							subOrder.setIsGivePoint(BooleanUtils.toInteger(true));
						}else{
							subOrder.setIsGivePoint(BooleanUtils.toInteger(false));
						}
						
						subOrderListMap.put(subOrderId, subOrder);
					}
					
					//库存操作
					Store storeInfo = new Store();
					storeInfo.setClientId(addOrderVoReq.getClientId());
					storeInfo.setMerchantId(addMerchantVo.getMerchantId());
					storeInfo.setProductId(productVo.getProductId());
					storeInfo.setReduceStore(productVo.getQuantity());
					storeInfo.setStore(store - productVo.getQuantity());
					storeList.add(storeInfo);
					
					//购物车
					if(addOrderVoReq.isShoppingCartOrder){
						OrderShoppingListReq orderShoppingListReq = new OrderShoppingListReq();
						orderShoppingListReq.setClientId(addOrderVoReq.getClientId());
						orderShoppingListReq.setMemberCode(addOrderVoReq.getMemberCode());
						orderShoppingListReq.setMerchantId(addMerchantVo.getMerchantId());
						orderShoppingListReq.setProductId(productVo.getProductId());
						shopingListReq.add(orderShoppingListReq);
					}
					
					/** 返回商品信息 */
					productReturnVoList.add(productReturnVo);
				}
				
				/** 返回商户信息 */
				merchantReturnVo.setProductReturnVoList(productReturnVoList);
				merchantReturnVoList.add(merchantReturnVo);
			}
			
			/**
			 * ===============================7.子订单组装===============================
			 */
			if(EmptyChecker.isNotEmpty(subOrderListMap)){
				for(Map.Entry<String, SubOrderMongo> entry: subOrderListMap.entrySet()){    
					subOrderList.add(entry.getValue());
				}
			}
			
			/**
			 * ===============================8.大订单组装===============================
			 */
			//大订单Mongo
			OrderMongo order = new OrderMongo();
			order.setClientId(addOrderVoReq.getClientId());
			order.setOrderId(orderId);
			order.setMemberCode(addOrderVoReq.getMemberCode());
			order.setMemberName(addOrderVoReq.getMemberName());
			order.setCreateSource(addOrderVoReq.getCreateSource());
			order.setPhone(addOrderVoReq.getPhone());
			order.setCreateTime(new Date().getTime());
			order.setOrderStatus(OrderStatus.create.getCode());
			order.setIsQrcode(BooleanUtils.toInteger(false));//默认值
			order.setPaymentMethod(PaymentMethod.invalid.getCode());//默认值:空
			order.setCashDiscount(0);//代金券抵扣金额--暂不考虑
			order.setRealPrice(0);//默认值
			order.setGivePoints(totalGivePoints);
			order.setTotalPrice(totalPrice);
			order.setVipDiscount(vipDiscount);
			order.setDeliverId(addOrderVoReq.getDeliverId());
			order.setRecvId(addOrderVoReq.getRecvId());
			order.setState(OrderState.NORMAL.getCode());
			order.setRemark(addOrderVoReq.getRemark());
			order.setBankPoints(0);//默认值
			order.setFftPoints(0);//默认值
			order.setIsPoint(BooleanUtils.toInteger(false));//默认值
			
			order.setIsSeckill(BooleanUtils.toInteger(false));//默认值
			
			order.setIsVipOrder(BooleanUtils.toInteger(false));//默认值
			
			//使用积分支付时
			if (addOrderVoReq.getBankPoint() > 0) {
				order.setBankPoints(Arith.mul(addOrderVoReq.getBankPoint(),1000));
			}
			if (addOrderVoReq.getFftPoint() > 0) {
				order.setFftPoints(Arith.mul(addOrderVoReq.getFftPoint(),1000));
			}
			
			//积分比例
			if(EmptyChecker.isNotEmpty(addOrderVoReq.getPointRate()) && addOrderVoReq.getPointRate() >0){
				order.setPointRate(String.valueOf(addOrderVoReq.getPointRate()));
			}
			
			//使用红包或现金券
			if(EmptyChecker.isNotEmpty(addOrderVoReq.getRedPacketId())){
				isJoinMarketActive = true;
				order.setRedPacketId(addOrderVoReq.getRedPacketId());
			}
			if(EmptyChecker.isNotEmpty(addOrderVoReq.getCashCouponId())){
				isJoinMarketActive = true;
				order.setRedPacketId(addOrderVoReq.getCashCouponId());
			}
			
			//如果是营销活动订单（满减、红包、优惠券）
			if(isJoinMarketActive){
				order.setIsActive("1");
			}
			
			//线下支付是否成功
			Boolean offlinePayFlag = null;
			
			//线下积分兑换，直接支付完成，设置支付方式、银行积分、支付时间、订单总价
			if(isSuccess && StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode())){
				order.setIsPoint(BooleanUtils.toInteger(true));
				order.setPaymentMethod(PaymentMethod.bankPoints.getCode());
				order.setBankPoints(Arith.mul(addOrderVoReq.getBankPoint(), 1000));
				order.setPaymentTime(new Date().getTime());
				order.setOrderStatus(OrderStatus.paysuccess.getCode());
				order.setPointRate(pointRate);
				
				LogCvt.info("线下积分兑换，通过机构号查询上级机构号，机构号：" + orderOrgCode);
				Org tempOrg = commonLogic.getOrgByOrgCode(orderOrgCode, addOrderVoReq.getClientId());
				if(EmptyChecker.isEmpty(tempOrg)){
					order.setOrderStatus(OrderStatus.sysclosed.getCode());
					order.setState(OrderState.SYSTEM_CLOSED.getCode());
					order.setRemark("积分支付失败，系统关单");
					subOrderList.get(0).setOrderStatus(OrderStatus.sysclosed.getCode());
					offlinePayFlag = false;
					LogCvt.error("[订单模块]-订单校验-（网点礼品）线下积分兑换，商品所属机构号查询不到上级机构！");
				}else{
					String orgTop = OrgSuperUtil.getOrgSuper(tempOrg);
					LogCvt.info("通过机构号:"+orderOrgCode+"，查询上级机构号的结果为：机构号=" + orgTop);
					ResultBean pointPayResult =  memberInformationSupport.payPointsByMobile(addOrderVoReq.getClientId(),orderId, "网点积分兑换","银行PC端创建订单："+SubOrderType.offline_points_org.getDescribe(), orgTop,order.getMemberName(), addOrderVoReq.getBankPoint(),addOrderVoReq.getCardNo());
//					LogCvt.info("[订单模块]-线下积分兑换，积分支付结果："+JSON.toJSONString(pointPayResult,true));
					
					offlinePayFlag = true;
					
					//线下积分兑换，支付失败时，直接关单（2015.06.24）
					if(!StringUtils.equals(pointPayResult.getCode(), ResultCode.success.getCode())){
						order.setOrderStatus(OrderStatus.sysclosed.getCode());
						order.setState(OrderState.SYSTEM_CLOSED.getCode());
						order.setRemark("积分支付失败，系统关单");
						subOrderList.get(0).setOrderStatus(OrderStatus.sysclosed.getCode());
						offlinePayFlag = false;
						LogCvt.error("[订单模块]-订单校验-（网点礼品）线下积分兑换，调用积分系统支付结果：积分支付失败！！");
					}
				}
			}
			
			//线上积分兑换
			if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.online_point_order.getCode())){
				order.setIsPoint(BooleanUtils.toInteger(true));
			}
			
			//返回数据
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setSubOrderList(subOrderList);
			orderInfo.setStoreList(storeList);
			orderInfo.setShopingListReq(shopingListReq);
			orderInfo.setDeliverInfoDetailVo(deliverInfoDetailVo);
			orderInfo.setOfflinePayFlag(offlinePayFlag);
			orderInfo.setIsJoinMarketActive(isJoinMarketActive);
			
			/** 返回创建订单响应信息 */
			orderInfo.setMerchantReturnVoList(merchantReturnVoList);
			/*if(isMonitor){//注销监控信息2015.11.11
				monitorService.send(MonitorPointEnum.Order_Checkmerchantandgood_Failed_Count);
			}else{
				monitorService.send(MonitorPointEnum.Order_Checkmerchantandgood_Success_Count);
			}*/
			
			if(isSuccess){
				result = new ResultBean(ResultCode.success,orderInfo);
			}else{
				result = new ResultBean(ResultCode.failed,orderInfo);
			}
		} catch (FroadBusinessException e) {
			LogCvt.error("订单校验失败，原因："+e.getMsg());
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		LogCvt.info("-----------------------订单校验结束("+(System.currentTimeMillis() - st)+"毫秒)-----------------------");
		return result;
	}
	
	
	/**
	 * 精品商城创建订单校验
	 * validateAddBoutiqueOrderParam:(这里用一句话描述这个方法的作用).
	 *
	 * 2015年12月2日 上午10:55:51
	 * @param orderId
	 * @param req
	 * @return
	 *
	 */
	public ResultBean validateAddBoutiqueOrderParam(String orderId, boolean isHasNoboutique, AddOrderVoReq addOrderVoReq) {
		long st = System.currentTimeMillis();
		LogCvt.info("-----------------------精品商城订单校验开始-----------------------");
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			boolean isSuccess = true;
			//是否发出监控项：正常校验不发；缓存错误，数据获取错误发
			boolean isMonitor = false;
			//是否参与营销活动 
			boolean isJoinMarketActive = false;
			//参数校验
			dataEmptyChecker(addOrderVoReq, "参数错误，无效的下单操作");
			dataEmptyChecker(addOrderVoReq.getClientId(), "参数错误，客户端ID不能为空");
			dataEmptyChecker(addOrderVoReq.getCreateSource(), "参数错误，订单来源不能为空");
			dataEmptyChecker(addOrderVoReq.getMemberCode(), "会员ID不能为空");
			dataEmptyChecker(addOrderVoReq.getMemberName(), "会员名不能为空");
			
			DeliverInfoDetailVo deliverInfoDetailVo = null;
			
			if(!isHasNoboutique) {
				orderId = String.valueOf(simpleOrderId.nextId());//大订单号
			}
			
			//收货人
			if(EmptyChecker.isNotEmpty(addOrderVoReq.getRecvId())){
				RecvInfo recvInfo = orderSupport.getRecvInfo(addOrderVoReq.getClientId(), addOrderVoReq.getMemberCode(), addOrderVoReq.getRecvId());
				if(EmptyChecker.isNotEmpty(recvInfo)){
					deliverInfoDetailVo = new DeliverInfoDetailVo();
					deliverInfoDetailVo.setAddress(recvInfo.getAddress());
					deliverInfoDetailVo.setConsignee(recvInfo.getConsignee());
					deliverInfoDetailVo.setPhone(recvInfo.getPhone());
				}
			}
			
			//订单初始化
			int totalPrice = 0;//实际总价
			int vipDiscount = 0;//VIP优惠价
			SubOrderType subOrderType = null;//子订单类型
			List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();//子订单
			List<Store> storeList = new ArrayList<Store>();//待更新库存
			List<OrderShoppingListReq> shopingListReq = new ArrayList<OrderShoppingListReq>();//待清空购物车
			
			//{商户号_子订单类型：子订单号}
			Map<String,String> subOrderMap = new HashMap<String,String>();
			//{子订单号：子订单}
			Map<String,SubOrderMongo> subOrderListMap = new HashMap<String,SubOrderMongo>();
			/** 商户响应信息 */
			List<MerchantReturnVo> merchantReturnVoList = new ArrayList<MerchantReturnVo>();
			
			
			for(AddMerchantVo addMerchantVo : addOrderVoReq.getAddMerchantVoList()){
				/**
				 * ===============================2.商户校验===============================
				 */
				LogCvt.info("-----商户信息校验开始-----");
				dataEmptyChecker(addMerchantVo.getMerchantId(), "商户ID不能为空");
				LogCvt.info("商户ID：" + addMerchantVo.getMerchantId());
				MerchantReturnVo merchantReturnVo = new MerchantReturnVo();
				merchantReturnVo.setMerchantId(addMerchantVo.getMerchantId());
				merchantReturnVo.setErrCode("0000");
				merchantReturnVo.setErrMsg("ok");
				
				//子订单号
				String subOrderId = null;
				//供应商信息
				Map<String,String> merchantMap = commonLogic.getProviderRedis(addMerchantVo.getMerchantId());
				
				//供应商是否存在
				if(EmptyChecker.isEmpty(merchantMap)){
					LogCvt.error("商户信息不存在，[商户ID："+addMerchantVo.getMerchantId() + ",redis-key:"+RedisKeyUtil.cbbank_merchant_client_id_merchant_id(addOrderVoReq.getClientId(), addMerchantVo.getMerchantId()));
					isSuccess = false;
					isMonitor = true;
					merchantReturnVo.setErrCode(ResultCode.failed.getCode());
					merchantReturnVo.setErrMsg(ResultCode.merchant_not_exist.getMsg());
					merchantReturnVoList.add(merchantReturnVo);
					continue;
				}
				//供应商名称
				String merchantName = merchantMap.get("merchant_name");
				LogCvt.info("供应商名称:"+merchantName);
				merchantReturnVo.setMerchantName(merchantName);
				merchantReturnVo.setMerchantStatus(String.valueOf(BooleanUtils.toInteger(true)));
				LogCvt.info("-----商户信息校验结束-----");
				
				dataEmptyChecker(addMerchantVo.getAddProductVoList(), "参数错误，商品信息不能为空，商户号：" + addMerchantVo.getMerchantId());
				//商品返回信息
				List<ProductReturnVo> productReturnVoList = new ArrayList<ProductReturnVo>();
				for(AddProductVo productVo : addMerchantVo.getAddProductVoList()){
					/**
					 * ===============================3.商品校验===============================
					 */
					LogCvt.info("-----商品信息校验开始-----");
					LogCvt.info("商品ID："+productVo.getProductId());
					
					//1.商品参数校验
					dataEmptyChecker(productVo.getProductId(), "参数错误，商品ID不能为空");
					dataEmptyChecker(productVo.getQuantity(), "参数错误，商品购买数量不能为空");
					if(productVo.getQuantity() <= 0){
						isMonitor = true;
						throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品购买数量必须大于0");
					}
					
					ProductReturnVo productReturnVo = new ProductReturnVo();
					productReturnVo.setProductId(productVo.getProductId());
					productReturnVo.setErrCode("0000");
					productReturnVo.setErrMsg("ok");
					
					//商品缓存(Redis)
					Map<String,String> productMap = commonLogic.getProductRedis(addOrderVoReq.getClientId(), addMerchantVo.getMerchantId(), productVo.getProductId());
					if(EmptyChecker.isEmpty(productMap)){
						isMonitor = true;
						LogCvt.error("商品信息在redis中不存在，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(ResultCode.prodcut_not_exist.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//商品详情(Mongo)
					ProductDetail productDetail = orderSupport.queryProductDetail(productVo.getProductId());
					if(EmptyChecker.isEmpty(productDetail)){
						isMonitor = true;
						LogCvt.error("商品信息在mongo中不存在，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(ResultCode.prodcut_not_exist.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					productReturnVo.setProductName(productDetail.getName());
					LogCvt.info("商品名称：" + productDetail.getName());
					
					//2.检查商品是否下架:0-未上架 1-上架 2-下架 3-已删除 4-禁用下架
					String productStatus = productMap.get("is_marketable");
					productReturnVo.setProductStatus(String.valueOf(productStatus));
					if(!StringUtils.equals(productStatus, ProductStatus.onShelf.getCode())){
						LogCvt.error("商品不是上架状态，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg("商品["+productDetail.getName()+"]状态："+ProductStatus.getType(productStatus).getDescribe());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//3.检查商品类型
					ProductType productType = ProductType.getType(String.valueOf(productDetail.getProductType())); // 商品类型
					if(EmptyChecker.isEmpty(productType)){
						isMonitor = true;
						LogCvt.error("商品类型获取异常，商品ID："+productVo.getProductId());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg("商品类型获取异常");
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					subOrderType = SubOrderType.boutique;
					
					//4.检查购买数量是否超出库存
					//商品库存(redis)
					int store = commonLogic.getStoreRedis(addOrderVoReq.getClientId(), addMerchantVo.getMerchantId(), productVo.getProductId());
					if(store < productVo.getQuantity()){
						LogCvt.error("购买数量超出库存，购买数量:"+productVo.getQuantity()+"，实际库存:"+store);
						isSuccess = false;
						/*throw new FroadBusinessException(ResultCode.failed.getCode(),ResultCode.product_store_not_enough.getMsg());*/
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(ResultCode.product_store_not_enough.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//5.检查是否过了预计发货期
					String dtStr = productMap.get("delivery_time");
					if(org.apache.commons.lang.StringUtils.isNotEmpty(dtStr)) {
						long deliveryTime = Long.parseLong(productMap.get("delivery_time"));
						String deliveryTimeStr = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, deliveryTime);
						if(DateUtil.dateCompare(deliveryTimeStr)){
							LogCvt.error("商品不在购买期内，商品ID："+productVo.getProductId());
							isSuccess = false;
							productReturnVo.setErrCode(ResultCode.failed.getCode());
							productReturnVo.setErrMsg("商品已过期");
							productReturnVoList.add(productReturnVo);
							continue;
						}

					}
										
					
					//6.配送信息校验
					int price = productDetail.getPrice();// 销售价
					ShippingStatus shippingStatus = ShippingStatus.unshipped;//配送状态
					Integer deliverMoney = 0;//运费
					//商品配送状态
					DeliveryType deliveryType = DeliveryType.home;
					

					//7.检查限购信息，分配限购数量
					//查询用户订单表的购买记录
					Map<String,String> userOrderMap = RedisCommon.getUserOrderCountRedis(addOrderVoReq.getClientId(),addOrderVoReq.getMemberCode(), productVo.getProductId());
					int totalCount = 0;
					int totalVipCount = 0;
					if(EmptyChecker.isNotEmpty(userOrderMap)){
						if(EmptyChecker.isNotEmpty(userOrderMap.get("count"))){
							totalCount = Integer.parseInt(userOrderMap.get("count").toString());
						}
						if(EmptyChecker.isNotEmpty(userOrderMap.get("vip_count"))){
							totalVipCount = Integer.parseInt(userOrderMap.get("vip_count").toString());
						}
					}
					
					
					int vipPrice = 0;//vip价格
					if(EmptyChecker.isNotEmpty(productDetail.getVipPrice()) && productDetail.getVipPrice()>0){
						vipPrice = productDetail.getVipPrice();
					}
					int vipQuantity = productVo.getVipQuantity();
					int normalQuantity = productVo.getQuantity() - productVo.getVipQuantity();
					
					//限购信息校验
					try{
						checkBoutiqueBuyLimit(BooleanUtils.toBoolean(productDetail.getIsLimit()),productDetail.getBuyLimit(),productVo.getQuantity(),productVo.getVipQuantity(),totalCount,totalVipCount,addOrderVoReq.isVip,productType,vipPrice);
					}catch(FroadBusinessException e){
						LogCvt.error(e.getMsg());
						isSuccess = false;
						productReturnVo.setErrCode(ResultCode.failed.getCode());
						productReturnVo.setErrMsg(e.getMsg());
						productReturnVoList.add(productReturnVo);
						continue;
					}
					
					//处理订单总价，除以对应积分比例换算成钱
					vipDiscount += Arith.mul(price - vipPrice, vipQuantity);//vip优惠金额
					/*totalPrice += vipDiscount + Arith.mul(price,normalQuantity) + deliverMoney;//总货币值(普通价总金额+VIP价总金额+运费)*/
					totalPrice += Arith.mul(vipPrice,vipQuantity) + Arith.mul(price,normalQuantity) + deliverMoney;//总货币值(普通价总金额+VIP价总金额+运费)
					
					/**
					 * ===============================5.子订单下的精品商城商品组装===============================
					 */
					//精品商城商品Mongo
					ProductMongo productMongo = new ProductMongo();
					productMongo.setProductId(productVo.getProductId());
					productMongo.setProductName(productDetail.getName());
					productMongo.setProductImage(productDetail.getImageInfo() == null? "" : productDetail.getImageInfo().get(0).getThumbnail());
					productMongo.setMoney(price);
					productMongo.setType(productType.getCode());
					productMongo.setQuantity(normalQuantity);
					productMongo.setVipMoney(vipPrice);
					productMongo.setVipQuantity(vipQuantity);
					if(EmptyChecker.isNotEmpty(deliveryType)){
						productMongo.setDeliveryOption(deliveryType.getCode());
					}
					if(EmptyChecker.isNotEmpty(shippingStatus)){
						productMongo.setDeliveryState(shippingStatus.getCode());
					}
					//productMongo.setOrgCode(productVo.getOrgCode());
					//productMongo.setOrgName(productVo.getOrgName());
					productMongo.setDeliveryMoney(deliverMoney);
					
					//满减活动ID
					if(EmptyChecker.isNotEmpty(productVo.getActiveId())){
						isJoinMarketActive = true;
						productMongo.setActiveId(productVo.getActiveId());
					}
					
					//满赠活动ID
					if(EmptyChecker.isNotEmpty(productVo.getGiveActiveId())){
						isJoinMarketActive = true;
						productMongo.setGiveActiveId(productVo.getGiveActiveId());
					}
					
					/**
					 * ===============================6.拆单处理===============================
					 */
					//子订单商品集合
					List<ProductMongo> products = new ArrayList<ProductMongo>();
					//获取子订单号
					String subOrderIdTemp =  subOrderMap.get(addMerchantVo.getMerchantId() + "_" + subOrderType.getCode());
					if(EmptyChecker.isNotEmpty(subOrderIdTemp)){
						subOrderId = subOrderIdTemp;
						
						SubOrderMongo subOrderTemp = subOrderListMap.get(subOrderId);
						subOrderTemp.getProducts().add(productMongo);
						subOrderListMap.put(subOrderId, subOrderTemp);
					}else{
						subOrderId = simpleSubOrderId.nextId();
						subOrderMap.put(addMerchantVo.getMerchantId() + "_" + subOrderType.getCode(), subOrderId);
						
						products.add(productMongo);
						
						//子订单Mongo
						SubOrderMongo subOrder = new SubOrderMongo();
						subOrder.setCreateTime(new Date().getTime());
						subOrder.setClientId(addOrderVoReq.getClientId());
						subOrder.setOrderId(orderId);
						subOrder.setOrderStatus(OrderStatus.create.getCode());
						subOrder.setMemberCode(addOrderVoReq.getMemberCode());
						subOrder.setMemberName(addOrderVoReq.getMemberName());
						subOrder.setSubOrderId(subOrderId);
						subOrder.setMerchantId(addMerchantVo.getMerchantId());
						subOrder.setMerchantName(merchantName);
						subOrder.setType(subOrderType.getCode());//子订单类型
						subOrder.setProducts(products);//商品信息
						
						subOrder.setDeliveryOption(DeliveryType.home.getCode());
						subOrder.setDeliveryState(ShippingStatus.unshipped.getCode());
						
						subOrder.setIsGivePoint(BooleanUtils.toInteger(false));
						subOrderListMap.put(subOrderId, subOrder);
					}
					
					//库存操作
					Store storeInfo = new Store();
					storeInfo.setClientId(addOrderVoReq.getClientId());
					storeInfo.setMerchantId(addMerchantVo.getMerchantId());
					storeInfo.setProductId(productVo.getProductId());
					storeInfo.setReduceStore(productVo.getQuantity());
					storeInfo.setStore(store - productVo.getQuantity());
					storeList.add(storeInfo);
					
					//购物车
					if(addOrderVoReq.isShoppingCartOrder){
						OrderShoppingListReq orderShoppingListReq = new OrderShoppingListReq();
						orderShoppingListReq.setClientId(addOrderVoReq.getClientId());
						orderShoppingListReq.setMemberCode(addOrderVoReq.getMemberCode());
						orderShoppingListReq.setMerchantId(addMerchantVo.getMerchantId());
						orderShoppingListReq.setProductId(productVo.getProductId());
						shopingListReq.add(orderShoppingListReq);
					}
					
					/** 返回商品信息 */
					productReturnVoList.add(productReturnVo);
				}
				/** 返回商户信息 */
				merchantReturnVo.setProductReturnVoList(productReturnVoList);
				merchantReturnVoList.add(merchantReturnVo);
			}
			
			/**
			 * ===============================7.子订单组装===============================
			 */
			if(EmptyChecker.isNotEmpty(subOrderListMap)){
				for(Map.Entry<String, SubOrderMongo> entry: subOrderListMap.entrySet()){    
					subOrderList.add(entry.getValue());
				}
			}
			
			
			OrderMongo order = new OrderMongo();
			//如果订单请求中只有精品商城的商品
			if(!isHasNoboutique) {
				order.setClientId(addOrderVoReq.getClientId());
				order.setOrderId(orderId);
				order.setMemberCode(addOrderVoReq.getMemberCode());
				order.setMemberName(addOrderVoReq.getMemberName());
				order.setCreateSource(addOrderVoReq.getCreateSource());
				order.setPhone(addOrderVoReq.getPhone());
				order.setCreateTime(new Date().getTime());
				order.setOrderStatus(OrderStatus.create.getCode());
				order.setIsQrcode(BooleanUtils.toInteger(false));//默认值
				order.setPaymentMethod(PaymentMethod.invalid.getCode());//默认值:空
				order.setCashDiscount(0);//代金券抵扣金额--暂不考虑
				order.setRealPrice(0);//默认值
				order.setGivePoints(0);
				
				order.setDeliverId(addOrderVoReq.getDeliverId());
				order.setRecvId(addOrderVoReq.getRecvId());
				order.setState(OrderState.NORMAL.getCode());
				order.setRemark(addOrderVoReq.getRemark());
				order.setBankPoints(0);//默认值
				order.setFftPoints(0);//默认值
				order.setIsPoint(BooleanUtils.toInteger(false));//默认值
				order.setIsSeckill(BooleanUtils.toInteger(false));//默认值
				order.setIsVipOrder(BooleanUtils.toInteger(false));//默认值
				if(isJoinMarketActive){
					order.setIsActive("1");
				}
				//使用红包或现金券
				if(EmptyChecker.isNotEmpty(addOrderVoReq.getRedPacketId())){
					isJoinMarketActive = true;
					order.setRedPacketId(addOrderVoReq.getRedPacketId());
				}
				if(EmptyChecker.isNotEmpty(addOrderVoReq.getCashCouponId())){
					isJoinMarketActive = true;
					order.setRedPacketId(addOrderVoReq.getCashCouponId());
				}
				
			}
			//使用积分支付时
			if (addOrderVoReq.getBankPoint() > 0) {
				order.setBankPoints(Arith.mul(addOrderVoReq.getBankPoint(),1000));
			}
			if (addOrderVoReq.getFftPoint() > 0) {
				order.setFftPoints(Arith.mul(addOrderVoReq.getFftPoint(),1000));
			}
			order.setTotalPrice(totalPrice);
			order.setVipDiscount(vipDiscount);
			
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setSubOrderList(subOrderList);
			orderInfo.setStoreList(storeList);
			orderInfo.setShopingListReq(shopingListReq);
			orderInfo.setDeliverInfoDetailVo(deliverInfoDetailVo);
			orderInfo.setMerchantReturnVoList(merchantReturnVoList);
			orderInfo.setIsJoinMarketActive(isJoinMarketActive);
			
			if(isSuccess){
				result = new ResultBean(ResultCode.success,orderInfo);
			}else{
				result = new ResultBean(ResultCode.failed,orderInfo);
			}
		} catch (FroadBusinessException e) {
			LogCvt.error("精品商城订单校验失败，原因："+e.getMsg());
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		LogCvt.info("-----------------------精品商城订单校验结束("+(System.currentTimeMillis() - st)+"毫秒)-----------------------");
		return result;
		
	}
	
	
	
	/**
	 * 检查精品商城限购信息，控制限购数量
	 * @param isLimit 是否限购
	 * @param buyLimit 商品限购信息
	 * @param quantity 商品购买数量
	 * @param totalCount 该商品的最大购买数
	 * @param totalVipCount 该商品的VIP价最大购买数
	 * @param curTime 当前系统时间
	 * @param isVip 是否VIP
	 * @return
	 * @throws FroadBusinessException 业务异常
	 */
	public void checkBoutiqueBuyLimit(boolean isLimit,ProductBuyLimit buyLimit,int quantity,int vipQuantity,int totalCount,int totalVipCount,boolean isVip,ProductType productType,int vipPrice) throws FroadBusinessException{
		if(vipQuantity < 0){
			LogCvt.error("参数错误：vip购买数[vipQuantity]小于0");
			throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，该商品的vip价购买数小于0");
		}
		if(quantity-vipQuantity < 0){
			LogCvt.error("参数错误：总购买数[quantity]小于vip购买数[vipQuantity]");
			throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，该商品的总购买数不得小于vip价购买数");
		}
		if(isLimit){
			// 检查商品普通购买数是否在限购数量内
			if(totalCount + totalVipCount + quantity > buyLimit.getMax() && buyLimit.getMax()>0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"该商品的可购买数量已经达到您的购买上限");
			}

			//vip购买数
			int maxVip = buyLimit.getMaxVip();
			
			//校验VIP限购
			if(maxVip-totalVipCount>=0){
				//用户是VIP、vip价>0、vip最大限购数>0、历史购买数没有超限
				if(isVip && vipPrice > 0 && maxVip > 0 && vipQuantity > (maxVip-totalVipCount)){
					LogCvt.info("VIP购买数量："+vipQuantity+"，VIP历史购买数量："+totalVipCount+"，VIP最大购买数量：" + maxVip);
					throw new FroadBusinessException(ResultCode.failed.getCode(),"VIP购买数量超过最大限购数量，VIP可购买数量为：" + (maxVip-totalVipCount));
				}
			}else{
				LogCvt.info("VIP可购买数为："+(maxVip-totalVipCount)+"，已经超限！VIP购买数："+vipQuantity+"，VIP历史购买数量："+totalVipCount+"，VIP最大购买数量：" + maxVip);
				throw new FroadBusinessException(ResultCode.failed.getCode(),"VIP购买数量超过最大限购数量，VIP可购买数量为0");
			}
			//不是VIP、VIP购买数量大于0
			if(!isVip && vipQuantity > 0){
				LogCvt.info("非VIP用户，VIP购买数量为"+vipQuantity+"，下单失败！");
				throw new FroadBusinessException(ResultCode.failed.getCode(),"非VIP用户，不能享受VIP价，下单失败");
			}
			//是VIP、VIP购买数量大于0、vip价=0
			if(isVip && vipQuantity > 0 && vipPrice == 0){
				LogCvt.info("VIP用户，商品无vip价格，VIP购买数量为"+vipQuantity+"，下单失败！");
				throw new FroadBusinessException(ResultCode.failed.getCode(),"商品无vip价格，不能享受VIP价，下单失败");
			}
		} else {
			
		}
	}
	
	
	/**
	 * 线下积分兑换订单——获取会员号、会员名称 
	 * @param addOrderVoReq 请求参数
	 * @return OrderValidateInfo 会员号、会员名
	 * @throws FroadBusinessException 业务异常信息
	 */
	public OrderValidateInfo getMemberInfo(AddOrderVoReq addOrderVoReq) throws FroadBusinessException{
		//响应结果
		OrderValidateInfo OrderValidateInfo = new OrderValidateInfo();
		Long memberCode = null;
		String memberName = null;

		//参数校验
		dataEmptyChecker(addOrderVoReq.getCardNo(), "参数错误，银行卡号不能为空");
		dataEmptyChecker(addOrderVoReq.getBankPoint(), "参数错误，银行积分不能为空");
		if(addOrderVoReq.getCardNo().length() <= 15){
			throw new FroadBusinessException(ResultCode.failed.getCode(), "银行卡号码错误");
		}
		
		//根据银行卡号查询会员信息
		ResultBean userResult = memberInformationSupport.queryMemberAndCardInfo(addOrderVoReq.getClientId(), addOrderVoReq.getCardNo());
		if(!StringUtils.equals(ResultCode.success.getCode(), userResult.getCode())){
			LogCvt.info("通过银行卡号查询会员失败，原因："+userResult.getMsg());
		}
		
		UserResult user = (UserResult) userResult.getData();
		
		if(EmptyChecker.isNotEmpty(user) && EmptyChecker.isNotEmpty(user.getUserList())){//老用户
			memberCode = user.getUserList().get(0).getMemberCode();
			memberName = user.getUserList().get(0).getLoginID();
			dataEmptyChecker(memberCode, "会员系统查询的会员号为空");
			dataEmptyChecker(memberName, "会员系统查询的会员名为空");
		    LogCvt.info("通过银行卡号查询会员结果：[会员号："+memberCode+" 会员名："+memberName+"]");
		}else{//新用户
			LogCvt.info("查询新注册用户");
			ResultBean queryMemberResult = memberInformationSupport.queryByLoginID(addOrderVoReq.getClientId() + addOrderVoReq.getCardNo());
			if(!StringUtils.equals(ResultCode.success.getCode(), queryMemberResult.getCode())){
				LogCvt.info("通过客户端ID和银行卡号查询新注册会员失败，原因："+queryMemberResult.getMsg());
			}
			
			MemberInfo memberInfoTemp = (MemberInfo) queryMemberResult.getData();
			if(EmptyChecker.isNotEmpty(memberInfoTemp)){//新用户已注册
				memberCode = memberInfoTemp.getMemberCode();
				memberName = memberInfoTemp.getLoginID();
				LogCvt.info("查询新注册会员结果：[会员号："+memberCode+" 会员名："+memberName+"]");
			}else{//新用户未注册
				LogCvt.info("会员系统查询结果为空，绑卡注册新用户...");
				dataEmptyChecker(addOrderVoReq.getIp(), "参数错误，IP地址不能为空");
				
				//注册新用户，并返回注册结果
				MemberInfo memberInfo = new MemberInfo();
				String pwd = addOrderVoReq.getCardNo().substring(addOrderVoReq.getCardNo().length() - 6);
				memberInfo.setLoginID(addOrderVoReq.getClientId() + addOrderVoReq.getCardNo());
				memberInfo.setLoginPwd(pwd);
				memberInfo.setRegisterIP(addOrderVoReq.getIp());
				memberInfo.setCreateChannel(CreateChannel.FFT.getValue());
				userResult = memberInformationSupport.registerMember(memberInfo);
				if(!StringUtils.equals(ResultCode.success.getCode(), userResult.getCode())){
					LogCvt.error("会员绑卡注册失败，原因："+userResult.getMsg());
					throw new FroadBusinessException(ResultCode.failed.getCode(), userResult.getMsg());
				}
				
				//获取注册结果
				user = (UserResult) userResult.getData();
				if(EmptyChecker.isNotEmpty(user) && EmptyChecker.isNotEmpty(user.getUserList())){
					memberCode = user.getUserList().get(0).getMemberCode();
					memberName = user.getUserList().get(0).getLoginID();
					LogCvt.info("通过绑卡注册会员结果：[会员号："+memberCode+" 会员名："+memberName+"]");
				}else{
					LogCvt.error("通过绑卡注册会员失败");
				}
				
				//校验注册信息
				dataEmptyChecker(memberCode, "会员绑卡注册失败，原因：返回会员ID为空");
				dataEmptyChecker(memberName, "会员绑卡注册失败，原因：返回会员名为空");
			}
		}
		//校验注册信息
		dataEmptyChecker(memberCode, "线下积分兑换会员ID不能为空");
		dataEmptyChecker(memberName, "线下积分兑换会员名不能为空");
		return OrderValidateInfo;
	}
	
	/**
	 * 线下积分兑换、线下积分兑换订单——获取积分兑换比例
	 * @param addOrderVoReq
	 * @return
	 * @throws FroadBusinessException
	 */
	public String getPointRate(AddOrderVoReq addOrderVoReq) throws FroadBusinessException{
		String pointRate = null;
		//如果订单是线下积分兑换、线上积分兑换，获取积分兑换比例
		if(StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.offline_point_order.getCode()) || StringUtils.equals(addOrderVoReq.getOrderRequestType(), OrderRequestType.online_point_order.getCode())){
//			List<Map<String, String>> listMap = paymentSupport.getPaymentChannelSetFromRedis(addOrderVoReq.getClientId());
//			if(EmptyChecker.isNotEmpty(listMap)){
//				for(Map<String, String> map : listMap) {
//					if("2".equals(map.get("type"))){
//						pointRate = map.get("point_rate");
//						break;
//					}
//				}
//			}
			pointRate = BaseSubassembly.acquireBankPointPointRate(addOrderVoReq.getClientId()).toString();
			if(EmptyChecker.isEmpty(pointRate)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "积分支付比例获取失败");
			}
		}
		return pointRate;
	}
	
	/**
	 * 获取收货人、提货人信息
	 * @return
	 */
	public DeliverInfoDetailVo getDeliverInfo(AddOrderVoReq addOrderVoReq){
		
		DeliverInfoDetailVo deliverInfoDetailVo = null;
		
		//提货人
		if(EmptyChecker.isNotEmpty(addOrderVoReq.getDeliverId())){
			RecvInfo deliverInfo = orderSupport.getRecvInfo(addOrderVoReq.getClientId(), addOrderVoReq.getMemberCode(), addOrderVoReq.getDeliverId());
			if(EmptyChecker.isNotEmpty(deliverInfo)){
				deliverInfoDetailVo = new DeliverInfoDetailVo();
				deliverInfoDetailVo.setAddress(deliverInfo.getAddress());
				deliverInfoDetailVo.setConsignee(deliverInfo.getConsignee());
				deliverInfoDetailVo.setPhone(deliverInfo.getPhone());
			}
		}
		
		//收货人
		if(EmptyChecker.isNotEmpty(addOrderVoReq.getRecvId()) && EmptyChecker.isEmpty(deliverInfoDetailVo)){
			RecvInfo recvInfo = orderSupport.getRecvInfo(addOrderVoReq.getClientId(), addOrderVoReq.getMemberCode(), addOrderVoReq.getRecvId());
			if(EmptyChecker.isNotEmpty(recvInfo)){
				deliverInfoDetailVo = new DeliverInfoDetailVo();
				deliverInfoDetailVo.setAddress(recvInfo.getAddress());
				deliverInfoDetailVo.setConsignee(recvInfo.getConsignee());
				deliverInfoDetailVo.setPhone(recvInfo.getPhone());
			}
		}
		return deliverInfoDetailVo;
	}
	
	/**
	 * 根据子订单类型上报业务监控项
	 * @param list 子订单集合
	 * @param isSuccess 是否成功
	 */
	public void sendMonitor(List<SubOrderMongo> list, boolean isSuccess){
		if(EmptyChecker.isNotEmpty(list)){
			for(SubOrderMongo subOrderMongo : list){
				if(StringUtils.equals(subOrderMongo.getType(), SubOrderType.group_merchant.getCode())){
					if(isSuccess){
						monitorService.send(MonitorPointEnum.Order_Creategroup_Count);
					}else{
						monitorService.send(MonitorPointEnum.Order_Creategroup_Failed_Count);
					}
				}
                if(StringUtils.equals(subOrderMongo.getType(), SubOrderType.offline_points_org.getCode())){
                	if(isSuccess){
						monitorService.send(MonitorPointEnum.Order_Createofflineexchange_Count);
					}else{
						monitorService.send(MonitorPointEnum.Order_Createofflineexchange_Failed_Count);
					}
				}
                if(StringUtils.equals(subOrderMongo.getType(), SubOrderType.online_points_org.getCode())){
                	if(isSuccess){
						monitorService.send(MonitorPointEnum.Order_Createonlineexchange_Count);
					}else{
						monitorService.send(MonitorPointEnum.Order_Createonlineexchange_Failed_Count);
					}
				}
                if(StringUtils.equals(subOrderMongo.getType(), SubOrderType.presell_org.getCode())){
                	if(isSuccess){
						monitorService.send(MonitorPointEnum.Order_Createpresell_Count);
					}else{
						monitorService.send(MonitorPointEnum.Order_Createpresell_Failed_Count);
					}
				}
			}
		}
	}
	
	/**
	 * 检查门店有效性
	 * @param clientId 客户端ID
	 * @param merchantId 商户ID
	 * @param outletId 门店ID
	 * @throws FroadBusinessException 业务异常
	 */
	public void checkOutletEnable(String clientId,String merchantId,String outletId) throws FroadBusinessException{
		//查询门店
		Map<String,String> outletMap = RedisCommon.getOutletRedis(clientId, merchantId, outletId);
		boolean outletStatus = BooleanUtils.toBoolean(Integer.valueOf(outletMap.get("is_enable")));
//		String outletName = outletMap.get("outlet_name");//门店名称
		if(!outletStatus){
			throw new FroadBusinessException(ResultCode.failed.getCode(),"门店无效");
		}
	}
	
	/**
	 * 根据商品类型获取子订单类型
	 * @param productType 商品类型
	 * @return
	 * @throws FroadBusinessException
	 */
	public SubOrderType getSubOrderType(ProductType productType) throws FroadBusinessException{
		if(ProductType.group.equals(productType)){//团购
			return SubOrderType.group_merchant;
		}else if(ProductType.presell.equals(productType)){//预售
			return SubOrderType.presell_org;
		}else if(ProductType.special.equals(productType)){//名优特惠
			return SubOrderType.special_merchant;
		}else if(ProductType.onlinePoint.equals(productType)){//在线积分兑换
			return SubOrderType.online_points_org;
		}else if(ProductType.dotGift.equals(productType)){//网点礼品
			return SubOrderType.offline_points_org;
		}else if(ProductType.boutique.equals(productType)){//网点礼品
			return SubOrderType.boutique;
		}
		return null;
	}
	
	/**
	 * 检查商品是否在购买期内
	 * @param startTime 购买开始时间
	 * @param endTime 购买结束时间
	 * @param curTime 当前系统时间
	 * @throws FroadBusinessException 业务异常
	 */
	public void checkProductBuyTime(long startTime,long endTime,long curTime) throws FroadBusinessException{
		if(startTime > curTime || endTime < curTime){
			throw new FroadBusinessException(ResultCode.failed.getCode(),"商品不在购买期内");
		}
	}
	
	/**
	 * 检查商品是否属于此门店（预售商品）
	 * @param productType 商品类型
	 * @param outletInfoList 门店集合
	 * @param outletId 门店ID
	 * @throws FroadBusinessException 业务异常
	 */
	public void checkOutletExists(ProductType productType,List<ProductCityOutlet> cityOutletInfoList,String outletId) throws FroadBusinessException{
		if(ProductType.presell.equals(productType)){
			boolean flag = false;
			if(EmptyChecker.isNotEmpty(cityOutletInfoList)){
				for(ProductCityOutlet cityOutlet : cityOutletInfoList){
					if(EmptyChecker.isNotEmpty(cityOutlet.getOrgOutlets())){
						for(ProductOutlet outlet : cityOutlet.getOrgOutlets()){
							if(outlet.getOutletId().equals(outletId)){
								flag = true;
								break;
							}
						}
					}
				}
			}
			if(!flag){
				throw new FroadBusinessException(ResultCode.failed.getCode(),ResultCode.prodcut_notin_outlet.getMsg());
			}
		}
	}
	
	/**
	 * 检查限购信息，控制限购数量
	 * @param isLimit 是否限购
	 * @param buyLimit 商品限购信息
	 * @param quantity 商品购买数量
	 * @param totalCount 该商品的最大购买数
	 * @param totalVipCount 该商品的VIP价最大购买数
	 * @param curTime 当前系统时间
	 * @param isVip 是否VIP
	 * @return
	 * @throws FroadBusinessException 业务异常
	 */
	public void checkBuyLimit(boolean isLimit,ProductBuyLimit buyLimit,int quantity,int vipQuantity,int totalCount,int totalVipCount,long curTime,boolean isVip,ProductType productType,int vipPrice) throws FroadBusinessException{
		if(vipQuantity < 0){
			LogCvt.error("参数错误：vip购买数[vipQuantity]小于0");
			throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，该商品的vip价购买数小于0");
		}
		if(quantity-vipQuantity < 0){
			LogCvt.error("参数错误：总购买数[quantity]小于vip购买数[vipQuantity]");
			throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，该商品的总购买数不得小于vip价购买数");
		}
		if(isLimit){
			//是否在限购期内
			if((buyLimit.getStartTime() <curTime && buyLimit.getEndTime() > curTime) || (buyLimit.getStartTime() <curTime && buyLimit.getEndTime() == 0) ){
				// 检查商品普通购买数是否在限购数量内
				if(totalCount + totalVipCount + quantity > buyLimit.getMax() && buyLimit.getMax()>0){
					throw new FroadBusinessException(ResultCode.failed.getCode(),"该商品的可购买数量已经达到您的购买上限");
				}
				if(buyLimit.getMax() == 0 && buyLimit.getMaxVip() == 0){
					LogCvt.error("[数据错误]-商品限购，且在限购期内，但是商品的普通限购数量、VIP限购数量都设置为0！");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"商品限购数量设置有误，请联系管理员");
				}
			}

			//vip购买数
			int maxVip = buyLimit.getMaxVip();
			
			//预售商品，校验VIP限购
			if(StringUtils.equals(productType.getCode(), ProductType.presell.getCode())){
				if(maxVip-totalVipCount>=0){
					//用户是VIP、vip价>0、vip最大限购数>0、历史购买数没有超限
					if(isVip && vipPrice > 0 && maxVip > 0 && vipQuantity > (maxVip-totalVipCount)){
						LogCvt.info("VIP购买数量："+vipQuantity+"，VIP历史购买数量："+totalVipCount+"，VIP最大购买数量：" + maxVip);
						throw new FroadBusinessException(ResultCode.failed.getCode(),"VIP购买数量超过最大限购数量，VIP可购买数量为：" + (maxVip-totalVipCount));
					}
				}else{
					LogCvt.info("VIP可购买数为："+(maxVip-totalVipCount)+"，已经超限！VIP购买数："+vipQuantity+"，VIP历史购买数量："+totalVipCount+"，VIP最大购买数量：" + maxVip);
					throw new FroadBusinessException(ResultCode.failed.getCode(),"VIP购买数量超过最大限购数量，VIP可购买数量为0");
				}
				//不是VIP、VIP购买数量大于0
				if(!isVip && vipQuantity > 0){
					LogCvt.info("非VIP用户，VIP购买数量为"+vipQuantity+"，下单失败！");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"非VIP用户，不能享受VIP价，下单失败");
				}
				//是VIP、VIP购买数量大于0、vip价=0
				if(isVip && vipQuantity > 0 && vipPrice == 0){
					LogCvt.info("VIP用户，商品无vip价格，VIP购买数量为"+vipQuantity+"，下单失败！");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"商品无vip价格，不能享受VIP价，下单失败");
				}
			}else{
				//不是预售商品、VIP购买数量大于0
				if(vipQuantity > 0){
					LogCvt.info("非预售商品，无法使用VIP价购买，VIP购买数量为"+vipQuantity+"，下单失败！");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"非预售商品，不能享受VIP价，下单失败");
				}
			}
		}
	}
	
	/**
	 * 检查限购信息，控制限购数量
	 * @param isLimit 是否限购
	 * @param buyLimit 商品限购信息
	 * @param quantity 商品总购买数量
	 * @param vipQuantity 商品VIP购买数量
	 * @param totalCount 该商品的最大购买数
	 * @param totalVipCount 该商品的VIP价最大购买数
	 * @param curTime 当前系统时间
	 * @param isVip 是否VIP
	 * @return
	 * @throws FroadBusinessException 业务异常
	 */
	public Map<String,Object> getBuyLimit(boolean isLimit,ProductBuyLimit buyLimit,int quantity,int vipQuantity,int totalCount,int totalVipCount,long curTime,boolean isVip) throws FroadBusinessException{
		LogCvt.info("用户限购校验：{总下单购买数："+quantity+"，普通历史购买数："+totalCount+"，VIP下单购买数："+vipQuantity+"，VIP历史购买数："+totalVipCount);
		int vipCanBuyQuantity = 0; //vip购买数量
		int normalCanBuyQuantity = 0; //普通购买数量
		if(vipQuantity<0){
			throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，该商品的vip价购买数量小于0");
		}
		if(quantity < vipQuantity){
			throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，该商品的总购买数量小于vip价购买数量");
		}
		if(isLimit){
			//是否在限购期内
			if(buyLimit.getStartTime() <curTime && buyLimit.getEndTime() > curTime){
				// 检查商品普通购买数是否在限购数量内
				if((totalCount+quantity) > buyLimit.getMax() && buyLimit.getMax()>0){
					throw new FroadBusinessException(ResultCode.failed.getCode(),"该商品的可购买数量已经达到您的购买上限");
				}
				
				//vip购买数
				int maxVip = buyLimit.getMaxVip();
				
				LogCvt.info("商品为限购商品，最大限购数："+ buyLimit.getMax()+"，VIP最大限购数：" + maxVip);
				
				//是VIP、最大购买数>0、历史购买数没有超限
				if(isVip){
					if(maxVip > 0){
						if(maxVip-totalVipCount>0){//未超限
							vipCanBuyQuantity = vipQuantity > (maxVip-totalVipCount) ? (maxVip-totalVipCount) : vipQuantity;
							normalCanBuyQuantity = quantity - vipCanBuyQuantity;//总欲购买数-vip可购买数
							LogCvt.info("用户是vip，vip限购，用户vip购买未超限");
						}else{
							vipCanBuyQuantity = 0;
							normalCanBuyQuantity = quantity - vipCanBuyQuantity;
							LogCvt.info("用户是vip，vip限购，用户vip购买超限");
						}
					}else{
						LogCvt.info("用户是vip，vip不限购");
						vipCanBuyQuantity = vipQuantity;
						normalCanBuyQuantity = quantity - vipCanBuyQuantity;
					}
				}else{
					LogCvt.info("用户不是vip");
					vipCanBuyQuantity = 0;
					normalCanBuyQuantity = quantity - vipCanBuyQuantity;
				}
				
				//不是VIP、VIP购买数量大于0
				if(!isVip && vipQuantity > 0){
					LogCvt.info("非VIP用户，VIP购买数量为"+vipQuantity+"，下单失败！");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"非VIP用户，VIP购买数量为"+vipQuantity+"，下单失败！");
				}
			}
		}
		LogCvt.info("限购校验通过！普通购买数为："+normalCanBuyQuantity+",vip购买数为："+vipCanBuyQuantity);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("vipQuantity", vipCanBuyQuantity);
		map.put("normalQuantity", normalCanBuyQuantity);
		return map;
	}
	
	/**
	 * 获取赠送积分
	 * @param clientId 客户端ID
	 * @param curTime 当前系统时间
	 * @param activitiesList 赠送积分活动信息集合
	 * @return
	 */
	public int getGivePoints(String clientId,long curTime,Long memberCode,String productId,List<ProductActivitiesInfo> activitiesList){
		int givePoints = 0;//赠送积分
		if(EmptyChecker.isNotEmpty(activitiesList)){
			for(ProductActivitiesInfo activityInfo : activitiesList){
				RedisManager redis = new RedisManager();
				long activityId = activityInfo.getActivitiesId();
				
				//查询活动
				String activityRedisKey = RedisKeyUtil.cbbank_activities_client_id_activities_id(clientId, activityId);
				Map<String,String> activityMap = redis.getMap(activityRedisKey);
				
				if(EmptyChecker.isNotEmpty(activityMap)){
					long begTime = Long.parseLong(activityMap.get("begin_time") == null ? "0" : activityMap.get("begin_time"));
					long endTime = Long.parseLong(activityMap.get("end_time") == null ? "0" : activityMap.get("end_time"));

					//赠送积分活动
					String point = activityMap.get("points");
					//检查是否在活动期内
					if(begTime <curTime && endTime > curTime){
						point = point == null ? "0" : point;
						givePoints = Integer.parseInt(point);
						LogCvt.info("满足赠送活动，活动信息："+JSON.toJSONString(activityMap));
						
						if(givePoints > 0){//判断用户积分是否送过，规则：一个商品只能赠送一次积分
							boolean flag = RedisCommon.getUserGivePointsRedis(clientId,memberCode,productId);
							if(flag){
								LogCvt.info("用户已赠送过积分，不再赠送");
								givePoints = 0;
							}
						}
						
					}else{
						LogCvt.info("不在赠送积分活动期内，活动信息："+JSON.toJSONString(activityMap));
					}
				}
			}
		}
		return givePoints;
	}
	
	
	/**
	 * 配送|自提信息校验
	 * @param clientId 客户端ID
	 * @param merchantId 商户ID
	 * @param productId 商品ID
	 * @param productType 商品类型：团购|预售|精品预售|线上积分兑换|网点礼品
	 * @param deliveryType 配送方式：网点自提|送货上门
	 * @param recvId 收货人信息
	 * @param deliverId 提货人信息
	 * @param orgCode 提货网点机构号
	 * @param cardNo 银行卡号
	 * @param memberCode 会员ID
	 * @return
	 * @throws FroadBusinessException 业务异常
	 */
	public Map<String, Object> checkDeliverInfo(String clientId,String merchantId,String productId,String productName,ProductType productType,
			DeliveryType deliveryType,DeliveryType deliveryOption,DeliverInfoDetailVo deliverInfoDetailVo, String recvId, String deliverId,String orgCode,String orgName,String cardNo,long memberCode,int price,int quantity,String phone)
			throws FroadBusinessException {
		ShippingStatus shippingStatus = null;//商品配送|提货状态
		int deliveryMoney = 0;//运费
		/**
		 * 业务规则-2015-04-15：
		 * 预售的自提|发货状态在商品上，名优特惠的发货状态在子订单上
		 */
		if(ProductType.group.equals(productType)){//团购商品时，配送方式：自提（上门消费）
			LogCvt.info("商品类型：团购商品");
			dataEmptyChecker(phone, "参数错误，接收券短信手机号不能为空");
			dataEmptyChecker(recvId, "参数错误，联系人信息不能为空");
			deliveryType = null;
		}else if(ProductType.presell.equals(productType)){//预售商品时，配送方式：网点自提，送货上门
			LogCvt.info("商品类型：预售商品");
			if(!deliveryOption.equals(DeliveryType.home_or_take) && !deliveryOption.equals(deliveryType)){
				LogCvt.error("选择的配送方式有误，选择的配送方式：" + deliveryType.getDescribe()+"，商品的配送方式只能为：" + deliveryOption.getDescribe()+ " 商品ID：" + productId);
				throw new FroadBusinessException(ResultCode.failed.getCode(),"选择的配送方式有误：商品["+productName+"]只能" + deliveryOption.getDescribe());
			} 
			if(DeliveryType.home.equals(deliveryType)){
				//如果送货上门，收货人信息不能为空
				dataEmptyChecker(recvId, "收货信息ID不能为空");
				dataEmptyChecker(deliverInfoDetailVo, "收货信息不存在，收货信息ID:"+recvId);
				//从预售表缓存取运费 
				Map<String,String> deliveryMoneyMap = orderSupport.getProductDeliveryMoney(clientId,merchantId,productId);
				String deliveryMoneyTemp = "0";
				if(EmptyChecker.isNotEmpty(deliveryMoneyMap)){
					if(EmptyChecker.isNotEmpty(deliveryMoneyMap.get("delivery_money"))){
						deliveryMoneyTemp = deliveryMoneyMap.get("delivery_money");
					}
				}
				deliveryMoney = Integer.valueOf(deliveryMoneyTemp);
				shippingStatus = ShippingStatus.unshipped;//配送状态：未配送
				
			}else if(DeliveryType.take.equals(deliveryType)){
				//如果网点自提，提货人不能为空
				/*dataEmptyChecker(deliverId, "提货信息ID不能为空");*/
				
				//如果提货人信息为空，取收货人信息
				if(EmptyChecker.isEmpty(deliverId)){
					dataEmptyChecker(recvId, "提货信息ID不能为空");
					deliverId = recvId;
				}
				
				dataEmptyChecker(deliverInfoDetailVo, "提货信息ID："+deliverId+"，对应的提货信息不存在");
				shippingStatus = ShippingStatus.untake;//提货状态:未提货
				
				//预售自提网点机构号不能为空
				dataEmptyChecker(phone, "参数错误，接收券短信手机号不能为空");
				dataEmptyChecker(orgCode, "预售自提商品提货网点不能为空");
				dataEmptyChecker(orgName, "预售自提商品提货网点名称不能为空");
				
				//该门店的累计购买数量不能超过每个门店的最大提货数量
				//查询门店已提货数量
				int token = RedisCommon.getOutletPresellProductTokenCountRedis(clientId, orgCode, productId);
				//每个门店最大提货数量
				int productPresellMax = commonLogic.getProductPresellMaxRedis(clientId,"",productId);
				//用户累计购买数
				int totalBuy = quantity + token;
				if(productPresellMax >0 && totalBuy > productPresellMax){
					throw new FroadBusinessException(ResultCode.failed.getCode(),"预售商品提货超限：用户累计购买数["+totalBuy+"]，超过了该门店最大提货数["+productPresellMax+"]");
				}
			}else{
				throw new FroadBusinessException(ResultCode.failed.getCode(),"商品配送方式参数错误：预售商品只能网点自提或者送货上门");
			}
		}else if(ProductType.special.equals(productType)){//名优特惠时，配送方式：送货上门
			LogCvt.info("商品类型：名优特惠商品");
			deliveryType = null;
			//如果送货上门，收货人信息不能为空
			dataEmptyChecker(recvId, "收货信息ID不能为空");
			dataEmptyChecker(deliverInfoDetailVo, "收货信息ID："+recvId+"，对应的收货信息不存在");
			//名优特惠包邮
			Map<String,String> deliveryMoneyMap = orderSupport.getProductDeliveryMoney(clientId,merchantId,productId);
			String deliveryMoneyTemp = "0";
			if(EmptyChecker.isNotEmpty(deliveryMoneyMap)){
				if(EmptyChecker.isNotEmpty(deliveryMoneyMap.get("delivery_money"))){
					deliveryMoneyTemp = deliveryMoneyMap.get("delivery_money");
				}
			}
			deliveryMoney = Integer.valueOf(deliveryMoneyTemp);
		}else if(ProductType.onlinePoint.equals(productType)){//线上积分兑换时，配送方式：送货上门
			LogCvt.info("商品类型：在线积分兑换商品");
			deliveryType = null;
			//如果送货上门，收货人信息不能为空，无运费
			dataEmptyChecker(recvId, "收货信息ID不能为空");
			dataEmptyChecker(deliverInfoDetailVo, "收货信息ID："+recvId+"，对应的收货信息不存在");
			
			//在线积分兑换积分查询
			ResultBean memberInfoResult = memberInformationLogic.queryMemberInfoByMemberCode(memberCode,clientId);
			if(!StringUtils.equals(ResultCode.success.getCode(), memberInfoResult.getCode())){
				LogCvt.error("会员在积分系统中不存在   memberCode:" + memberCode+" 错误信息："+memberInfoResult.getMsg());
				throw new FroadBusinessException(ResultCode.failed.getCode(),memberInfoResult.getMsg());
			}
			MemberInfo user = (MemberInfo) memberInfoResult.getData();
			dataEmptyChecker(user, "会员在积分系统中不存在");
			dataEmptyChecker(user.getLoginID(), "会员登录名在积分系统中为空");
			
			//查询用户积分
			ResultBean pointResult = memberInformationLogic.queryMemberPointsInfoByLoginID(clientId, user.getLoginID());
			if(!StringUtils.equals(ResultCode.success.getCode(),pointResult.getCode())){
				LogCvt.error("会员积分账户不存在，无法获取会员积分信息   loginID:" + user.getLoginID()+" 错误信息："+pointResult.getMsg());
				throw new FroadBusinessException(ResultCode.failed.getCode(),pointResult.getMsg());
			}
			MemberPointsInfo pointsInfo = (MemberPointsInfo) pointResult.getData();
			dataEmptyChecker(pointsInfo, "很抱歉您还未开通银行积分");//用户无积分
			if(EmptyChecker.isEmpty(pointsInfo.getBankPoints()) && EmptyChecker.isEmpty(pointsInfo.getFroadPoints())){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"很抱歉您还未开通积分");
			}
			String bankPoint = EmptyChecker.isEmpty(pointsInfo.getBankPoints())? "0" : pointsInfo.getBankPoints();
			String froadPoint = EmptyChecker.isEmpty(pointsInfo.getFroadPoints())? "0" : pointsInfo.getFroadPoints();
			if(Double.valueOf(bankPoint) < Arith.div(price, 1000) && Double.valueOf(froadPoint) < Arith.div(price, 1000)){
				LogCvt.info("用户积分不足，需要积分：" + price + "  所剩积分:" + pointsInfo.getBankPoints());
				throw new FroadBusinessException(ResultCode.failed.getCode(),"用户积分不足");
			}
			
		}else if(ProductType.dotGift.equals(productType)){//网点礼品时，没有送货上门或自提（上门消费）
			LogCvt.info("商品类型：网点礼品");
			deliveryType = null;
			//网点礼品，只能自提
			//网点礼品自提网点机构号不能为空
			dataEmptyChecker(orgCode, "网点礼品提货网点不能为空");
			dataEmptyChecker(orgName, "网点礼品提货网点名称不能为空");
			//网点礼品，银行卡号不能为空
			dataEmptyChecker(cardNo, "银行卡号不能为空");
			//通过银行卡号查询银行积分是否足够
			ResultBean pointResult = memberInformationLogic.queryBankPointsByMobile(clientId, cardNo);
			if(StringUtils.equals(pointResult.getCode(),ResultCode.failed.getCode())){
				throw new FroadBusinessException(ResultCode.failed.getCode(),pointResult.getMsg());
			}
			String point = (String) pointResult.getData();
			dataEmptyChecker(point, "积分查询结果为空");
			if(Double.valueOf(point) < Arith.div(price, 1000)){
				LogCvt.info("用户积分不足，需要积分："+Arith.div(price, 1000)+"  所剩积分:" + point);
				throw new FroadBusinessException(ResultCode.failed.getCode(),"用户积分不足");
			}
		}else{
			throw new FroadBusinessException(ResultCode.failed.getCode(),"商品类型参数错误");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("shippingStatus", shippingStatus);
		map.put("deliveryMoney", deliveryMoney);
		map.put("deliveryType", deliveryType);
		return map;
	}
	
	/**
	 * 秒杀商品类型检查
	 * @param clientId 客户端ID
	 * @param merchantId 商户ID
	 * @param productId 商品ID
	 * @param productType 商品类型：团购|预售|精品预售|线上积分兑换|网点礼品
	 * @param deliveryType 配送方式：网点自提|送货上门
	 * @param recvId 收货人信息
	 * @param deliverId 提货人信息
	 * @param orgCode 提货网点机构号
	 * @param cardNo 银行卡号
	 * @param memberCode 会员ID
	 * @return
	 * @throws FroadBusinessException 业务异常
	 */
	public Map<String, Object> checkSeckillProductType(String clientId,String merchantId,String productId,ProductType productType,
			DeliveryType deliveryType,DeliverInfoDetailVo deliverInfoDetailVo, String recvId, String deliverId,String orgCode,String orgName,String cardNo,long memberCode,int price,int quantity,String phone)
					throws FroadBusinessException {
		ShippingStatus shippingStatus = null;//商品配送|提货状态
		int deliveryMoney = 0;//运费
		/**
		 * 业务规则-2015-04-15：
		 * 预售的自提|发货状态在商品上，名优特惠的发货状态在子订单上
		 */
		if(ProductType.group.equals(productType)){//团购商品时，配送方式：自提（上门消费）
			LogCvt.info("商品类型：团购商品");
			dataEmptyChecker(phone, "参数错误，接收券短信手机号不能为空");
		}else if(ProductType.presell.equals(productType)){//预售商品时，配送方式：网点自提，送货上门
			LogCvt.info("商品类型：预售商品");
			if(DeliveryType.home.equals(deliveryType)){
				//从预售表缓存取运费 
				Map<String,String> deliveryMoneyMap = orderSupport.getProductDeliveryMoney(clientId,merchantId,productId);
				String deliveryMoneyTemp = "0";
				if(EmptyChecker.isNotEmpty(deliveryMoneyMap)){
					if(EmptyChecker.isNotEmpty(deliveryMoneyMap.get("delivery_money"))){
						deliveryMoneyTemp = deliveryMoneyMap.get("delivery_money");
					}
				}
				deliveryMoney = Integer.valueOf(deliveryMoneyTemp);
				shippingStatus = ShippingStatus.unshipped;//配送状态：未配送
			}else if(DeliveryType.take.equals(deliveryType)){
				//如果网点自提，提货人不能为空
				dataEmptyChecker(deliverId, "提货信息ID不能为空");
				dataEmptyChecker(deliverInfoDetailVo, "提货信息ID："+deliverId+"，对应的提货信息不存在");
				shippingStatus = ShippingStatus.untake;//提货状态:未提货
				/*subOrderShippingStatus = null;
				subOrderDeliverType = DeliveryType.home_or_take;*/
				//预售自提网点机构号不能为空
				dataEmptyChecker(phone, "参数错误，接收券短信手机号不能为空");
				dataEmptyChecker(orgCode, "预售自提商品提货网点不能为空");
				dataEmptyChecker(orgName, "预售自提商品提货网点名称不能为空");
				
				//该门店的累计购买数量不能超过每个门店的最大提货数量
				//查询门店已提货数量
				int token = RedisCommon.getOutletPresellProductTokenCountRedis(clientId, orgCode, productId);
				//每个门店最大提货数量
				int productPresellMax = commonLogic.getProductPresellMaxRedis(clientId,"",productId);
				//用户累计购买数
				int totalBuy = quantity + token;
				if(productPresellMax >0 && totalBuy > productPresellMax){
					throw new FroadBusinessException(ResultCode.failed.getCode(),"预售商品提货超限：用户累计购买数["+totalBuy+"]，超过了该门店最大提货数["+productPresellMax+"]");
				}
			}else{
				throw new FroadBusinessException(ResultCode.failed.getCode(),"商品配送方式参数错误：预售商品只能网点自提或者送货上门");
			}
		}else if(ProductType.special.equals(productType)){//名优特惠时，配送方式：送货上门
			LogCvt.info("商品类型：名优特惠商品");
			//如果送货上门，收货人信息不能为空
			dataEmptyChecker(recvId, "收货信息ID不能为空");
			dataEmptyChecker(deliverInfoDetailVo, "收货信息ID"+recvId+" 对应的收货信息不存在");
			//从预售表缓存取运费 
			Map<String,String> deliveryMoneyMap = orderSupport.getProductDeliveryMoney(clientId,merchantId,productId);
			String deliveryMoneyTemp = "0";
			if(EmptyChecker.isNotEmpty(deliveryMoneyMap)){
				if(EmptyChecker.isNotEmpty(deliveryMoneyMap.get("delivery_money"))){
					deliveryMoneyTemp = deliveryMoneyMap.get("delivery_money");
				}
			}
			deliveryMoney = Integer.valueOf(deliveryMoneyTemp);
		}else if(ProductType.onlinePoint.equals(productType)){//线上积分兑换时，配送方式：送货上门
			LogCvt.info("商品类型：线上积分兑换商品");
			//如果送货上门，收货人信息不能为空
			dataEmptyChecker(recvId, "收货信息ID不能为空");
			dataEmptyChecker(deliverInfoDetailVo, "收货信息ID"+recvId+" 对应的收货信息不存在");
			//从预售表缓存取运费 
			Map<String,String> deliveryMoneyMap = orderSupport.getProductDeliveryMoney(clientId,merchantId,productId);
			String deliveryMoneyTemp = "0";
			if(EmptyChecker.isNotEmpty(deliveryMoneyMap)){
				if(EmptyChecker.isNotEmpty(deliveryMoneyMap.get("delivery_money"))){
					deliveryMoneyTemp = deliveryMoneyMap.get("delivery_money");
				}
			}
			deliveryMoney = Integer.valueOf(deliveryMoneyTemp);
		}else if(ProductType.dotGift.equals(productType)){//网点礼品时，没有送货上门或自提（上门消费）
			LogCvt.info("商品类型：网点礼品");
			//网点礼品，只能自提
			//网点礼品自提网点机构号不能为空
			dataEmptyChecker(orgCode, "网点礼品提货网点不能为空");
			dataEmptyChecker(orgName, "网点礼品提货网点名称不能为空");
			//网点礼品，银行卡号不能为空
			dataEmptyChecker(cardNo, "银行卡号不能为空");
			//通过银行卡号查询银行积分是否足够
			ResultBean pointResult = memberInformationLogic.queryBankPointsByMobile(clientId, cardNo);
			if(StringUtils.equals(pointResult.getCode(),ResultCode.failed.getCode())){
				throw new FroadBusinessException(ResultCode.failed.getCode(),pointResult.getMsg());
			}
			String point = (String) pointResult.getData();
			dataEmptyChecker(point, "积分查询结果为空");
			if(Double.valueOf(point) < Arith.div(price, 1000)){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"用户积分不足，需要积分："+price+"  所剩积分:"+point);
			}
		}else{
			throw new FroadBusinessException(ResultCode.failed.getCode(),"商品类型参数错误");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("shippingStatus", shippingStatus);
		map.put("deliveryMoney", deliveryMoney);
		return map;
	}
	
	/**
	 * 获取订单概要请求
	 * @param getOrderSummaryVoReq
	 * @return
	 */
	public ResultBean validateGetOrderSummaryParam(
			GetOrderSummaryVoReq getOrderSummaryVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getOrderSummaryVoReq, "无效的获取订单概要请求");
			dataEmptyChecker(getOrderSummaryVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getOrderSummaryVoReq.getMemberCode(), "会员ID不能为空");
			dataEmptyChecker(getOrderSummaryVoReq.getPage(), "分页不能为空");
			dataEmptyChecker(getOrderSummaryVoReq.getPage().getPageNumber(), "当前页码不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	/**
	 * 获取订单详情参数校验
	 * @param getOrderDetailVoReq
	 * @return
	 */
	public ResultBean validateGetOrderDetailParam(
			GetOrderDetailVoReq getOrderDetailVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getOrderDetailVoReq, "无效的获取订单详情请求");
			dataEmptyChecker(getOrderDetailVoReq.getOrderId(), "订单ID不能为空");
			dataEmptyChecker(getOrderDetailVoReq.getClientId(), "客户端ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	/**
	 * 取消订单参数校验
	 * @param deleteOrderVoReq
	 * @return
	 */
	public ResultBean validateDeleteOrderParam(DeleteOrderVoReq deleteOrderVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(deleteOrderVoReq, "无效的取消订单操作");
			dataEmptyChecker(deleteOrderVoReq.getOrderId(), "订单ID不能为空");
			dataEmptyChecker(deleteOrderVoReq.getClientId(), "客户端ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	/**
	 * 获取面对面支付订单概要参数校验
	 * @param getQrcodeOrderSummaryVoReq
	 * @return
	 */
	public ResultBean validateGetQrcodeOrderSummaryParam(
			GetQrcodeOrderSummaryVoReq getQrcodeOrderSummaryVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getQrcodeOrderSummaryVoReq, "无效的获取面对面支付订单概要订单操作");
			dataEmptyChecker(getQrcodeOrderSummaryVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getQrcodeOrderSummaryVoReq.getMemberCode(), "会员ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	/**
	 * 获取面对面支付订单详情参数校验
	 * @param getQrcodeOrderDetailVoReq
	 * @return
	 */
	public ResultBean validateGetQrcodeOrderDetailParam(
			GetQrcodeOrderDetailVoReq getQrcodeOrderDetailVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getQrcodeOrderDetailVoReq, "无效的获取面对面支付订单详情订单操作");
			dataEmptyChecker(getQrcodeOrderDetailVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getQrcodeOrderDetailVoReq.getMemberCode(), "会员ID不能为空");
			dataEmptyChecker(getQrcodeOrderDetailVoReq.getOrderId(), "订单ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	/**
	 * 订单发货参数校验
	 * @param shippingOrderVoReq
	 * @return
	 */
	public ResultBean validateShippingOrderParam(
			ShippingOrderVoReq shippingOrderVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(shippingOrderVoReq, "无效的获取面对面支付订单详情订单操作");
			dataEmptyChecker(shippingOrderVoReq.getSubOrderId(), "子订单ID不能为空");
			dataEmptyChecker(shippingOrderVoReq.getDeliveryCorpId(), "物流公司ID不能为空");
			dataEmptyChecker(shippingOrderVoReq.getDeliveryCorpName(), "物流公司名称不能为空");
			dataEmptyChecker(shippingOrderVoReq.getTrackingNo(), "物流单号不能为空");
			dataEmptyChecker(shippingOrderVoReq.getMerchantUserId(), "商户用户号不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	/**
	 * 校验面对面支付订单
	 * @param addQrcodeOrderVoReq
	 * @return
	 */
	public ResultBean validateAddQrcodeOrderParam(
			AddQrcodeOrderVoReq addQrcodeOrderVoReq) {
		LogCvt.info("订单校验-开始");
		LogCvt.info("请求参数：" + JSonUtil.toJSonString(addQrcodeOrderVoReq));
		ResultBean result = new ResultBean(ResultCode.success);
		
		try {
			/**
			 * ===============================1.订单参数校验===============================
			 */
			//参数校验
			dataEmptyChecker(addQrcodeOrderVoReq, "无效的下单操作");
			dataEmptyChecker(addQrcodeOrderVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(addQrcodeOrderVoReq.getMemberCode(), "会员ID不能为空");
			dataEmptyChecker(addQrcodeOrderVoReq.getQrcode(), "二维码不能为空");
			dataEmptyChecker(addQrcodeOrderVoReq.getCreateSource(), "订单来源不能为空");
			dataEmptyChecker(addQrcodeOrderVoReq.getPhone(), "用户注册手机号不能为空");
			
			//是否参与营销活动
			boolean isJoinMarketActive = false;
			
			/**
			 * ===============================2.商品有效性校验===============================
			 */
			Map<String,String> productMap = orderSupport.getIdByQrcode(addQrcodeOrderVoReq.getClientId(),addQrcodeOrderVoReq.getQrcode());
			dataEmptyChecker(productMap, "二维码无效");
			String state = productMap.get("state");
			if(StringUtils.equals(state, "0")){
				LogCvt.error("面对面创建订单失败，原因：该二维码已经产生订单，二维码："+addQrcodeOrderVoReq.getQrcode());
				return new ResultBean(ResultCode.failed,"该二维码已产生订单，无法再次付款");
			}
			String id = productMap.get("id");
			dataEmptyChecker(id, "二维码无效");
			String productId = id.substring(0,id.indexOf("_"));
			dataEmptyChecker(productId, "二维码格式不正确");
			String merchantUserId = id.substring(id.indexOf("_")+1);
			dataEmptyChecker(merchantUserId, "二维码格式不正确");
			//检查商品是否存在
			OutletProduct outletProduct =  commonSupport.queryOutletProduct(productId);
			dataEmptyChecker(outletProduct, "面对面商品不存在");
			dataEmptyChecker(outletProduct.getMerchantId(), "面对面商品所属商户不存在");
			dataEmptyChecker(outletProduct.getOutletId(), "面对面商品所属门店不存在");
			Map<String,String> outletNameMap = RedisCommon.getOutletRedis(addQrcodeOrderVoReq.getClientId(),outletProduct.getMerchantId(),outletProduct.getOutletId());
			String outletName = "";
			if(EmptyChecker.isNotEmpty(outletNameMap)){
				outletName = outletNameMap.get("outlet_name");
			}
			/**
			 * ===============================3.商户有效性校验===============================
			 */
			//商户信息
			Map<String,String> merchantMap = commonLogic.getMerchantRedis(addQrcodeOrderVoReq.getClientId(), outletProduct.getMerchantId());
			//商户是否存在
			dataEmptyChecker(merchantMap, ResultCode.merchant_not_exist.getMsg());
			//商户名称
			String merchantName = merchantMap.get("merchant_name");
			LogCvt.info("商户名称:"+merchantName);
			
			//商户是否有效
			boolean isEnable = false;
			if(EmptyChecker.isNotEmpty(merchantMap.get("is_enable"))){
				isEnable = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("is_enable"))); // 商户状态
			}
			if(!isEnable){
				return new ResultBean(ResultCode.failed,"该商户已无法收款，请勿再次支付");
			}
			
			//子订单初始化
			String orderId = String.valueOf(simpleOrderId.nextId());//大订单号
			LogCvt.info("大订单号："+orderId);
			
			int totalPrice = (int) (outletProduct.getCost());//实际总价
			
			int vipDiscount = 0;//VIP优惠价
			int totalGivePoints = 0;//订单赠送总积分
			
			//根据商户ID获取本级机构号与上级机构号
			Map<OrgLevelEnum, String> orgCodeMap = commonLogic.getSuperOrgByMerchantId(addQrcodeOrderVoReq.getClientId(),outletProduct.getMerchantId());
			
			
			//根据商户用户ID获取商户用户名
			String merchantUserName = orderSupport.findMerchantUserNameById(Long.valueOf(merchantUserId));
			
			/**
			 * ===============================4.大订单组装===============================
			 */
			//大订单Mongo
			OrderMongo order = new OrderMongo();
			order.setClientId(addQrcodeOrderVoReq.getClientId());
			order.setOrderId(orderId);
			order.setMemberCode(addQrcodeOrderVoReq.getMemberCode());
			order.setMemberName(addQrcodeOrderVoReq.getMemberName());
			order.setMerchantId(outletProduct.getMerchantId());
			order.setMerchantName(merchantName);
			order.setMerchantUserId(Long.valueOf(merchantUserId));
			order.setMerchantUserName(merchantUserName == null ? "" : merchantUserName);//2015.09.11新增
			order.setOutletId(outletProduct.getOutletId());
			order.setOutletName(outletName);
			order.setCreateSource(addQrcodeOrderVoReq.getCreateSource());
			order.setProductId(productId);
			order.setPhone(addQrcodeOrderVoReq.getPhone());
			order.setCreateTime(new Date().getTime());
			order.setOrderStatus(OrderStatus.create.getCode());
			order.setIsQrcode(BooleanUtils.toInteger(true));
			order.setQrcode(addQrcodeOrderVoReq.getQrcode());
			order.setPaymentMethod(PaymentMethod.invalid.getCode());//支付方式:空
			order.setCashDiscount(0);//代金券抵扣金额--暂不考虑
			order.setRealPrice(0);//总货币值 real_price
			order.setGivePoints(totalGivePoints);//赠送积分
			order.setTotalPrice(totalPrice);//总货币值
			order.setVipDiscount(vipDiscount);//VIP优惠金额
			order.setState(OrderState.NORMAL.getCode());
			order.setBankPoints(0);
			order.setFftPoints(0);
			order.setRemark(addQrcodeOrderVoReq.getRemark());
			
			order.setIsSeckill(BooleanUtils.toInteger(false));//默认值
			order.setIsVipOrder(BooleanUtils.toInteger(false));//默认值
			
			order.setForgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_one));
			order.setSorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_two));
			order.setTorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_three));
			order.setLorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_four));
			
			//红包
			if(EmptyChecker.isNotEmpty(addQrcodeOrderVoReq.getRedPacketId())){
				isJoinMarketActive = true;
				order.setRedPacketId(addQrcodeOrderVoReq.getRedPacketId());
			}
			//优惠券
			if(EmptyChecker.isNotEmpty(addQrcodeOrderVoReq.getCashCouponId())){
				isJoinMarketActive = true;
				order.setCashCouponId(addQrcodeOrderVoReq.getCashCouponId());
			}
			
			if(isJoinMarketActive){
				order.setIsActive("1");
			}
			
			//返回数据
			result = new ResultBean(ResultCode.success,order);
		} catch (FroadBusinessException e) {
			LogCvt.error("订单校验失败，原因：" + e.getMsg());
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		LogCvt.info("订单校验-结束");
		return result;
	}
	
	public ResultBean validateGetVipDiscountParam(
			GetVipDiscountVoReq getVipDiscountVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getVipDiscountVoReq, "无效的获取面对面支付订单详情订单操作");
			dataEmptyChecker(getVipDiscountVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getVipDiscountVoReq.getMemberCode(), "会员ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	public ResultBean validateGetPointExchangeListParam(
			GetPointExchangeListVoReq getPointExchangeListVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getPointExchangeListVoReq, "无效的获取面对面支付订单详情订单操作");
			dataEmptyChecker(getPointExchangeListVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getPointExchangeListVoReq.getMemberCode(), "会员ID不能为空");
			dataEmptyChecker(getPointExchangeListVoReq.getQueryFlag(), "查询标识不能为空");
			if(!StringUtils.equals(getPointExchangeListVoReq.getQueryFlag(), "0") && !StringUtils.equals(getPointExchangeListVoReq.getQueryFlag(), "1")){
				return new ResultBean(ResultCode.failed,"查询标识值有误：0为在线积分兑换，1为网点礼品兑换");
			}
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	public ResultBean validateGetPointExchangeDetailParam(
			GetPointExchangeDetailVoReq getPointExchangeDetailVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getPointExchangeDetailVoReq, "无效的获取积分兑换订单详情订单操作");
			dataEmptyChecker(getPointExchangeDetailVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getPointExchangeDetailVoReq.getMemberCode(), "会员ID不能为空");
			dataEmptyChecker(getPointExchangeDetailVoReq.getOrderId(), "订单ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	/**
	 * 校验获取子订单商品参数
	 * @param getSubOrderProductVoReq
	 * @return
	 */
	public ResultBean validateGetSubOrderProduct(
			GetSubOrderProductVoReq getSubOrderProductVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getSubOrderProductVoReq, "无效的获取子订单商品操作");
			dataEmptyChecker(getSubOrderProductVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getSubOrderProductVoReq.getSubOrderId(), "子订单ID不能为空");
			dataEmptyChecker(getSubOrderProductVoReq.getProductId(), "商品ID不能为空");
			dataEmptyChecker(getSubOrderProductVoReq.getOrderId(), "订单ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	/**
	 * 校验库存处理请求参数
	 * @param storeVoReq
	 * @return
	 */
	public ResultBean validateStoreProcess(StoreVoReq storeVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(storeVoReq, "无效的获取子订单商品操作");
			dataEmptyChecker(storeVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(storeVoReq.getOrderId(), "订单ID不能为空");
			dataEmptyChecker(storeVoReq.getOperationType(), "操作类型参数不能为空");
			if(!StringUtils.equals(storeVoReq.getOperationType(), "0") && !StringUtils.equals(storeVoReq.getOperationType(), "1")){
				return new ResultBean(ResultCode.failed,"操作类型参数有误：0为扣库存，1为还库存");
			}
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	/**
	 * 校验通过二维码获取面对面订单请求参数
	 * @param getOrderByQrcodeVoReq
	 * @return
	 */
	public ResultBean validateGetOrderByQrcode(
			GetOrderByQrcodeVoReq getOrderByQrcodeVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getOrderByQrcodeVoReq, "无效的通过二维码获取订单操作");
			dataEmptyChecker(getOrderByQrcodeVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getOrderByQrcodeVoReq.getQrcode(), "二维码不能为空");
			Map<String,String> map = orderSupport.getIdByQrcode(getOrderByQrcodeVoReq.getClientId(), getOrderByQrcodeVoReq.getQrcode());
			dataEmptyChecker(map, "二维码无效");
			String state = map.get("state");
			if(StringUtils.equals(state, "1")){
				LogCvt.info("订单未创建  [client_id:"+getOrderByQrcodeVoReq.getClientId()+" qrcode:"+getOrderByQrcodeVoReq.getQrcode()+"]");
				return new ResultBean(ResultCode.order_not_create);
			}
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	/**
	 * 秒杀参数校验
	 * @param addSeckillOrderVoReq
	 * @return
	 */
	public ResultBean validateAddSeckillOrderParam(
			AddSeckillOrderVoReq addSeckillOrderVoReq) {

		LogCvt.info("-----------------------秒杀订单校验开始-----------------------");
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			/**
			 * 1.大订单校验
			 */
			//参数校验
			dataEmptyChecker(addSeckillOrderVoReq, "参数错误，无效的下单操作");
			dataEmptyChecker(addSeckillOrderVoReq.getReqId(), "参数错误，受理号不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getClientId(), "参数错误，客户端ID不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getCreateSource(), "参数错误，订单来源不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getMemberCode(), "会员ID不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getMemberName(), "会员名不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getMerchantId(), "商户ID不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getProductId(), "商品ID不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getProductName(), "商品名称不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getProductType(), "商户类型不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getDeliveryOption(), "商品配送方式不能为空");
			if(addSeckillOrderVoReq.getQuantity() == 0 && addSeckillOrderVoReq.getVipQuantity() == 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品购买数量不能为0");
			}
			
			if(addSeckillOrderVoReq.getMoney() == 0 && addSeckillOrderVoReq.getVipMoney() == 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品购买价和VIP购买价不能同时为0");
			}
			
			if(addSeckillOrderVoReq.getMoney() < 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品价格不能小于0");
			}
			
			if(addSeckillOrderVoReq.getVipMoney() < 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品VIP价格不能小于0");
			}
			
			if(addSeckillOrderVoReq.getQuantity() < 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品购买数量不能小于0");
			}
			
			if(addSeckillOrderVoReq.getVipQuantity() < 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品VIP购买数量不能小于0");
			}
			
			dataEmptyChecker(addSeckillOrderVoReq.getAddDeliveryInfoVoReq(), "参数错误，配送信息不能为空");
			dataEmptyChecker(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getRecvId(), "收货信息ID不能为空");
			//6.校验手机号
			if(StringUtils.equals(addSeckillOrderVoReq.getProductType(), ProductType.group.getCode()) 
					|| (StringUtils.equals(addSeckillOrderVoReq.getProductType(), ProductType.presell.getCode())
							&& StringUtils.equals(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getDeliveryType(), DeliveryType.take.getCode()))){
				dataEmptyChecker(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getPhone(), "手机号不能为空");
			}
			
			//订单初始化
			String orderId = String.valueOf(simpleOrderId.nextId());//大订单号
			LogCvt.info("大订单号："+orderId);
			int totalPrice = 0;//实际总价
			int vipDiscount = 0;//VIP优惠价
			SubOrderType subOrderType = null;//子订单类型
			
			List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();//子订单
			List<Store> storeList = new ArrayList<Store>();//待更新库存
			
			/**
			 * ===============================1.商户校验===============================
			 */
			
			//子订单号
			String subOrderId = simpleSubOrderId.nextId();
			
			//商户信息
			Map<String,String> merchantMap = commonLogic.getMerchantRedis(addSeckillOrderVoReq.getClientId(), addSeckillOrderVoReq.getMerchantId());
			//商户是否存在
			dataEmptyChecker(merchantMap, ResultCode.merchant_not_exist.getMsg());
			//商户名称
			String merchantName = merchantMap.get("merchant_name");
			
			//商户是否有效
			boolean isEnable = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("is_enable"))); // 商户状态
			if(!isEnable){
				return new ResultBean(ResultCode.merchant_enable);
			}
			
			//是否银行机构商户  0否：团购商户，1是：预售商户
			boolean merchantStatus = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("merchant_status"))); 
			LogCvt.info("商户类型:"+ (merchantStatus ? "预售商户" : "团购商户"));
			//如果是银行商户，merchantId取机构号，merchantName取机构名
			String orgCode = null;
			String orgName = null;
			if(merchantStatus){
				Org org = commonLogic.queryByMerchantInfo(addSeckillOrderVoReq.getClientId(), addSeckillOrderVoReq.getMerchantId());
				dataEmptyChecker(org, "商户所属机构不存在");
				orgCode = org.getOrgCode();
				orgName = org.getOrgName();
				if(!org.getIsEnable()){
					LogCvt.error("商户所属机构已被禁用，商户ID："+addSeckillOrderVoReq.getMerchantId());
					return new ResultBean(ResultCode.org_enable);
				}
			}
			
			//根据商户ID获取本级机构号与上级机构号
			Map<OrgLevelEnum, String> orgCodeMap = commonLogic.getSuperOrgByMerchantId(addSeckillOrderVoReq.getClientId(),addSeckillOrderVoReq.getMerchantId());
			LogCvt.info("商户ID："+addSeckillOrderVoReq.getMerchantId());
			dataEmptyChecker(orgCodeMap, "商户对应机构信息不存在");
			
			/**
			 * ===============================2.商品校验===============================
			 */
			LogCvt.info("商品ID："+addSeckillOrderVoReq.getProductId());
				
			//3.检查商品类型
			ProductType productType = ProductType.getType(addSeckillOrderVoReq.getProductType()); // 商品类型
			dataEmptyChecker(productType, "商品类型获取异常");
			LogCvt.info("商品类型：" + productType.getDescribe());
			
			subOrderType = getSubOrderType(productType);
			dataEmptyChecker(subOrderType, "子订单类型获取异常");
			
			//4.检查购买数量是否超出库存
			// 秒杀商品库存(redis)
			int store = commonLogic.getSeckillStoreRedis(addSeckillOrderVoReq.getClientId(), addSeckillOrderVoReq.getProductId());
			if(store < addSeckillOrderVoReq.getQuantity()){
				LogCvt.error("购买数量超出库存，购买数量:"+addSeckillOrderVoReq.getQuantity()+"，实际库存:"+store);
				throw new FroadBusinessException(ResultCode.failed.getCode(),ResultCode.product_store_not_enough.getMsg());
			}
			
			//5.检查是否超过用户购买数量
			//取商品缓存
			OrderValidateInfo seckillProduct =  RedisCommon.getSeckillProductRedis(addSeckillOrderVoReq.getClientId(),addSeckillOrderVoReq.getProductId());
			if(EmptyChecker.isEmpty(seckillProduct.getProductEndTime())){
				LogCvt.error("[缓存数据错误]-秒杀商品结束时间为空，key="+RedisKeyUtil.cbbank_seckill_product_client_id_product_id(addSeckillOrderVoReq.getClientId(),addSeckillOrderVoReq.getProductId()));
				throw new FroadBusinessException(ResultCode.failed.getCode(),"秒杀商品结束时间为空");
			}
			if(EmptyChecker.isNotEmpty(seckillProduct.getSeckillProductBuyLimit()) && seckillProduct.getSeckillProductBuyLimit()>0){
				//用户购买数
				int buyRecord = RedisCommon.getUserSeckillOrderCountRedis(String.valueOf(addSeckillOrderVoReq.getMemberCode()), addSeckillOrderVoReq.getProductId(),seckillProduct.getProductEndTime());
				if(buyRecord + addSeckillOrderVoReq.getQuantity() + addSeckillOrderVoReq.getVipQuantity() > seckillProduct.getSeckillProductBuyLimit()){
					LogCvt.info("购买数量超过秒杀商品限购数量！已购买数："+buyRecord+"，欲购买数:"+addSeckillOrderVoReq.getQuantity() + addSeckillOrderVoReq.getVipQuantity()+"，秒杀商品限购数："+seckillProduct.getSeckillProductBuyLimit());
					throw new FroadBusinessException(ResultCode.failed.getCode(),"购买数量超过秒杀商品限购数量");
				}
			}else{
				LogCvt.info("秒杀商品不限购，商品ID：" + addSeckillOrderVoReq.getProductId());
			}
			
			/**
			 * ===============================3.子订单下的商品组装===============================
			 */
			//商品Mongo
			ProductMongo productMongo = new ProductMongo();
			productMongo.setProductId(addSeckillOrderVoReq.getProductId());
			productMongo.setProductName(addSeckillOrderVoReq.getProductName());
			productMongo.setProductImage(addSeckillOrderVoReq.getProductImage());
			productMongo.setMoney(Arith.mul(addSeckillOrderVoReq.getMoney(), 1000));
			productMongo.setType(productType.getCode());
			productMongo.setQuantity(addSeckillOrderVoReq.getQuantity());
			productMongo.setVipMoney(Arith.mul(addSeckillOrderVoReq.getVipMoney(),1000));
			productMongo.setVipQuantity(addSeckillOrderVoReq.getVipQuantity());
			productMongo.setDeliveryMoney(Arith.mul(addSeckillOrderVoReq.getDeliveryMoney(),1000));
			if(subOrderType.equals(SubOrderType.presell_org)){
				//提货配送信息//TODO
				productMongo.setDeliveryOption(addSeckillOrderVoReq.getDeliveryOption());
				productMongo.setDeliveryOption(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getDeliveryType());
				if(StringUtils.equals(DeliveryType.home.getCode(), addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getDeliveryType())){
					productMongo.setDeliveryState(ShippingStatus.unshipped.getCode());
				}
				if(StringUtils.equals(DeliveryType.take.getCode(), addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getDeliveryType())){
					productMongo.setOrgCode(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getOrgCode());
					productMongo.setOrgName(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getOrgName());
					productMongo.setDeliveryState(ShippingStatus.untake.getCode());
				}
			}
			totalPrice += Arith.mul(productMongo.getMoney(),productMongo.getQuantity()) + productMongo.getDeliveryMoney();
			vipDiscount += Arith.mul(productMongo.getMoney() - productMongo.getVipMoney(),productMongo.getVipQuantity());
			productMongo.setPoints(0);
			
			List<ProductMongo> products = new ArrayList<ProductMongo>();
			products.add(productMongo);
			
			/**
			 * ===============================4.子订单组装===============================
			 */
			//子订单Mongo
			SubOrderMongo subOrder = new SubOrderMongo();
			subOrder.setCreateTime(new Date().getTime());
			subOrder.setClientId(addSeckillOrderVoReq.getClientId());
			subOrder.setOrderId(orderId);
			subOrder.setOrderStatus(OrderStatus.create.getCode());
			subOrder.setMemberCode(addSeckillOrderVoReq.getMemberCode());
			subOrder.setMemberName(addSeckillOrderVoReq.getMemberName());
			subOrder.setSubOrderId(subOrderId);
			subOrder.setMerchantId(orgCode == null ? addSeckillOrderVoReq.getMerchantId() : orgCode);
			subOrder.setMerchantName(orgCode == null ? merchantName : orgName);
			subOrder.setType(subOrderType.getCode());
			subOrder.setProducts(products);
			subOrder.setForgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_one));
			subOrder.setSorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_two));
			subOrder.setTorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_three));
			subOrder.setLorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_four));
			
			if(subOrderType.equals(SubOrderType.special_merchant)){
				subOrder.setDeliveryOption(DeliveryType.home.getCode());
				subOrder.setDeliveryState(ShippingStatus.unshipped.getCode());
			}
			subOrderList.add(subOrder);
			
			//库存操作
			Store storeInfo = new Store();
			storeInfo.setClientId(addSeckillOrderVoReq.getClientId());
			storeInfo.setMerchantId(addSeckillOrderVoReq.getMerchantId());
			storeInfo.setProductId(addSeckillOrderVoReq.getProductId());
			storeInfo.setReduceStore(addSeckillOrderVoReq.getQuantity() + addSeckillOrderVoReq.getVipQuantity());
			storeInfo.setStore(store - storeInfo.getReduceStore());
			storeList.add(storeInfo);
			
			/**
			 * ===============================5.大订单组装===============================
			 */
			//大订单Mongo
			OrderMongo order = new OrderMongo();
			order.setClientId(addSeckillOrderVoReq.getClientId());
			order.setOrderId(orderId);
			order.setMemberCode(addSeckillOrderVoReq.getMemberCode());
			order.setMemberName(addSeckillOrderVoReq.getMemberName());
			order.setCreateSource(addSeckillOrderVoReq.getCreateSource());
			order.setCreateTime(new Date().getTime());
			order.setOrderStatus(OrderStatus.create.getCode());
			order.setIsQrcode(BooleanUtils.toInteger(false));//false
			order.setPaymentMethod(PaymentMethod.invalid.getCode());//支付方式:空
			order.setCashDiscount(0);//代金券抵扣金额--暂不考虑
			order.setRealPrice(0);//总货币值 real_price
			order.setGivePoints(0);//赠送积分
			order.setTotalPrice(totalPrice);//总货币值
			order.setVipDiscount(vipDiscount);//VIP优惠金额
			order.setState(OrderState.NORMAL.getCode());
			order.setRemark(addSeckillOrderVoReq.getRemark());
			order.setBankPoints(0);//银行积分  
			order.setFftPoints(0);//方付通积分
			
			order.setIsPoint(BooleanUtils.toInteger(false));
			
			order.setIsSeckill(BooleanUtils.toInteger(true));
			order.setReqId(addSeckillOrderVoReq.getReqId());
			
			order.setRecvId(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getRecvId());
			order.setDeliverId(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getDeliverId());
			order.setPhone(addSeckillOrderVoReq.getAddDeliveryInfoVoReq().getPhone());
			
			//返回数据
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setSubOrderList(subOrderList);
			orderInfo.setStoreList(storeList);
			result = new ResultBean(ResultCode.success,orderInfo);
		} catch (FroadBusinessException e) {
			LogCvt.error("订单校验失败，原因：" + e.getMsg());
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		LogCvt.info("-----------------------订单校验结束-----------------------");
		return result;
	
	}
	
	/**
	 * @param addOrderForSeckillVoReq  重构
	 * @return
	 */
	@Deprecated
	public ResultBean validateAddOrderForSeckillParam(AddOrderForSeckillVoReq addOrderForSeckillVoReq) {
		LogCvt.info("-----------------------秒杀订单校验开始-----------------------");
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			/**
			 * 1.大订单校验
			 */
			//参数校验
			dataEmptyChecker(addOrderForSeckillVoReq, "参数错误，无效的下单操作");
			dataEmptyChecker(addOrderForSeckillVoReq.getReqId(), "参数错误，受理号不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getClientId(), "参数错误，客户端ID不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getCreateSource(), "参数错误，订单来源不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getMemberCode(), "参数错误，会员ID不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getMemberName(), "参数错误，会员名不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getMerchantId(), "参数错误，商户ID不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getProductId(), "参数错误，商品ID不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getProductName(), "参数错误，商品名称不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getProductType(), "参数错误，商户类型不能为空");
//			dataEmptyChecker(addOrderForSeckillVoReq.getDeliveryType(), "参数错误，商品配送方式不能为空");
			dataEmptyChecker(addOrderForSeckillVoReq.getRecvId(), "收货信息ID不能为空");
			
			if(addOrderForSeckillVoReq.getQuantity() == 0 && addOrderForSeckillVoReq.getVipQuantity() == 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "参数错误，商品普通购买数量和VIP购买数量不能同时为0");
			}
			
			if(addOrderForSeckillVoReq.getMoney() == 0 && addOrderForSeckillVoReq.getVipMoney() == 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品购买价和VIP购买价不能同时为0");
			}
			
			if(addOrderForSeckillVoReq.getMoney() < 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品价格不能小于0");
			}
			
			if(addOrderForSeckillVoReq.getVipMoney() < 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品VIP价格不能小于0");
			}
			
			if(addOrderForSeckillVoReq.getQuantity() < 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品购买数量不能小于0");
			}
			
			if(addOrderForSeckillVoReq.getVipQuantity() < 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误，商品VIP购买数量不能小于0");
			}
			
			//订单初始化
			String orderId = String.valueOf(simpleOrderId.nextId());
			LogCvt.info("大订单号："+orderId);
			int totalPrice = 0;//实际总价
			int vipDiscount = 0;//VIP优惠价
			SubOrderType subOrderType = null;//子订单类型
			
			List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();//子订单
			List<Store> storeList = new ArrayList<Store>();//待更新库存
			
			/**
			 * ===============================1.商户校验===============================
			 */
			
			String subOrderId = simpleSubOrderId.nextId();
			
			//商户信息校验
			Map<String,String> merchantMap = commonLogic.getMerchantRedis(addOrderForSeckillVoReq.getClientId(), addOrderForSeckillVoReq.getMerchantId());
			dataEmptyChecker(merchantMap, ResultCode.merchant_not_exist.getMsg());
			String merchantName = merchantMap.get("merchant_name");//商户名称
			boolean isEnable = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("is_enable"))); //商户状态
			if(!isEnable){//商户是否有效
				return new ResultBean(ResultCode.merchant_enable);
			}
			
			//是否银行机构商户  0否：普通商户，1是：机构商户
			boolean merchantStatus = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("merchant_status"))); 
			//如果是银行商户，merchantId取机构号，merchantName取机构名
			String orgCode = null;
			String orgName = null;
			if(merchantStatus){
				Org org = commonLogic.queryByMerchantInfo(addOrderForSeckillVoReq.getClientId(), addOrderForSeckillVoReq.getMerchantId());
				dataEmptyChecker(org, "商户所属机构不存在");
				orgCode = org.getOrgCode();
				orgName = org.getOrgName();
				if(!org.getIsEnable()){
					LogCvt.error("商户所属机构已被禁用，商户ID："+addOrderForSeckillVoReq.getMerchantId());
					return new ResultBean(ResultCode.org_enable);
				}
			}
			
			//根据商户ID获取本级机构号与上级机构号
			Map<OrgLevelEnum, String> orgCodeMap = commonLogic.getSuperOrgByMerchantId(addOrderForSeckillVoReq.getClientId(),addOrderForSeckillVoReq.getMerchantId());
			LogCvt.info("商户ID："+addOrderForSeckillVoReq.getMerchantId());
			dataEmptyChecker(orgCodeMap, "商户对应机构信息不存在");
			
			/**
			 * ===============================2.商品校验===============================
			 */
			LogCvt.info("商品ID："+addOrderForSeckillVoReq.getProductId());
				
			//3.检查商品类型
			ProductType productType = ProductType.getType(addOrderForSeckillVoReq.getProductType());
			dataEmptyChecker(productType, "商品类型获取异常");
			LogCvt.info("商品类型：" + productType.getDescribe());
			
			subOrderType = getSubOrderType(productType);
			dataEmptyChecker(subOrderType, "子订单类型获取异常");
			
			//4.检查购买数量是否超出库存
			// 秒杀商品库存(redis)
			int store = commonLogic.getSeckillStoreRedis(addOrderForSeckillVoReq.getClientId(), addOrderForSeckillVoReq.getProductId());
			if(store < addOrderForSeckillVoReq.getQuantity()){
				LogCvt.error("购买数量超出库存，购买数量:"+addOrderForSeckillVoReq.getQuantity()+"，实际库存:"+store);
				throw new FroadBusinessException(ResultCode.failed.getCode(),ResultCode.product_store_not_enough.getMsg());
			}
			
			/**
			 * ===============================3.子订单下的商品组装===============================
			 */
			//商品Mongo
			ProductMongo productMongo = new ProductMongo();
			productMongo.setProductId(addOrderForSeckillVoReq.getProductId());
			productMongo.setProductName(addOrderForSeckillVoReq.getProductName());
			productMongo.setProductImage(addOrderForSeckillVoReq.getProductImage());
			productMongo.setMoney(Arith.mul(addOrderForSeckillVoReq.getMoney(), 1000));
			productMongo.setType(productType.getCode());
			productMongo.setQuantity(addOrderForSeckillVoReq.getQuantity());
			productMongo.setVipMoney(Arith.mul(addOrderForSeckillVoReq.getVipMoney(),1000));
			productMongo.setVipQuantity(addOrderForSeckillVoReq.getVipQuantity());
			productMongo.setDeliveryMoney(Arith.mul(addOrderForSeckillVoReq.getDeliveryMoney(),1000));
			if(subOrderType.equals(SubOrderType.presell_org)){
				productMongo.setDeliveryOption(addOrderForSeckillVoReq.getDeliveryType());
			}
			totalPrice += productMongo.getMoney() * productMongo.getQuantity() + productMongo.getDeliveryMoney();
			vipDiscount += productMongo.getVipMoney() * productMongo.getVipQuantity();
			productMongo.setPoints(0);
			List<ProductMongo> products = new ArrayList<ProductMongo>();
			products.add(productMongo);
			
			/**
			 * ===============================4.子订单组装===============================
			 */
			//子订单Mongo
			SubOrderMongo subOrder = new SubOrderMongo();
			subOrder.setCreateTime(new Date().getTime());
			subOrder.setClientId(addOrderForSeckillVoReq.getClientId());
			subOrder.setOrderId(orderId);
			subOrder.setOrderStatus(OrderStatus.create.getCode());
			subOrder.setMemberCode(addOrderForSeckillVoReq.getMemberCode());
			subOrder.setMemberName(addOrderForSeckillVoReq.getMemberName());
			subOrder.setSubOrderId(subOrderId);
			subOrder.setMerchantId(orgCode == null ? addOrderForSeckillVoReq.getMerchantId() : orgCode);
			subOrder.setMerchantName(orgCode == null ? merchantName : orgName);
			subOrder.setType(subOrderType.getCode());
			subOrder.setProducts(products);
			subOrder.setForgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_one));
			subOrder.setSorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_two));
			subOrder.setTorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_three));
			subOrder.setLorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_four));
			
			if(subOrderType.equals(SubOrderType.special_merchant)){
				subOrder.setDeliveryOption(DeliveryType.home.getCode());
				subOrder.setDeliveryState(ShippingStatus.unshipped.getCode());
			}
			subOrderList.add(subOrder);
			
			//库存操作
			Store storeInfo = new Store();
			storeInfo.setClientId(addOrderForSeckillVoReq.getClientId());
			storeInfo.setMerchantId(addOrderForSeckillVoReq.getMerchantId());
			storeInfo.setProductId(addOrderForSeckillVoReq.getProductId());
			storeInfo.setReduceStore(addOrderForSeckillVoReq.getQuantity() + addOrderForSeckillVoReq.getVipQuantity());
			storeInfo.setStore(store - storeInfo.getReduceStore());
			storeList.add(storeInfo);
			
			/**
			 * ===============================5.大订单组装===============================
			 */
			//大订单Mongo
			OrderMongo order = new OrderMongo();
			order.setClientId(addOrderForSeckillVoReq.getClientId());
			order.setOrderId(orderId);
			order.setMemberCode(addOrderForSeckillVoReq.getMemberCode());
			order.setMemberName(addOrderForSeckillVoReq.getMemberName());
			order.setCreateSource(addOrderForSeckillVoReq.getCreateSource());
			order.setCreateTime(new Date().getTime());
			order.setOrderStatus(OrderStatus.create.getCode());
			order.setIsQrcode(BooleanUtils.toInteger(false));//false
			order.setPaymentMethod(PaymentMethod.invalid.getCode());//支付方式:空
			order.setCashDiscount(0);//代金券抵扣金额--暂不考虑
			order.setRealPrice(0);//总货币值 real_price
			order.setGivePoints(0);//赠送积分
			order.setTotalPrice(totalPrice);//总货币值
			order.setVipDiscount(vipDiscount);//VIP优惠金额
			order.setState(OrderState.NORMAL.getCode());
			order.setRemark(addOrderForSeckillVoReq.getRemark());
			order.setBankPoints(0);//银行积分  
			order.setFftPoints(0);//方付通积分
			order.setPhone(addOrderForSeckillVoReq.getPhone());
			
			order.setIsSeckill(BooleanUtils.toInteger(true));
			order.setReqId(addOrderForSeckillVoReq.getReqId());
			
			//返回数据
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setSubOrderList(subOrderList);
			orderInfo.setStoreList(storeList);
			result = new ResultBean(ResultCode.success,orderInfo);
		} catch (FroadBusinessException e) {
			LogCvt.error("[秒杀模块]-订单校验失败，原因：" + e.getMsg());
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		LogCvt.info("-----------------------订单校验结束-----------------------");
		return result;
	}
	
	/**
	 * 添加配送信息
	 * @param addDeliveryInfoVoReq
	 * @return
	 */
	public ResultBean validateAddDeliveryInfoParam(AddDeliveryInfoVoReq addDeliveryInfoVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(addDeliveryInfoVoReq, "无效的补全配送信息操作");
			dataEmptyChecker(addDeliveryInfoVoReq.getOrderId(), "订单ID不能为空");
			dataEmptyChecker(addDeliveryInfoVoReq.getRecvId(), "收货信息ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	/**
	 * 会员可购买数量校验
	 * @param addDeliveryInfoVoReq
	 * @return
	 */
	public ResultBean validateGetMemberBuyLimit(GetMemberBuyLimitVoReq getMemberBuyLimitVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getMemberBuyLimitVoReq, "无效的操作");
			dataEmptyChecker(getMemberBuyLimitVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getMemberBuyLimitVoReq.getMemberCode(), "会员号不能为空");
			dataEmptyChecker(getMemberBuyLimitVoReq.getProductId(), "商品ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	public ResultBean validateGetOrderPaymentResult(GetOrderPaymentResultVoReq getOrderPaymentResultVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getOrderPaymentResultVoReq, "无效的操作");
			dataEmptyChecker(getOrderPaymentResultVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getOrderPaymentResultVoReq.getOrderId(), "订单号不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	/**
	 * 创建VIP订单（开通VIP）校验
	 * @param addVIPOrderVoReq
	 * @return
	 */
	public ResultBean addVIPOrderValidate(AddVIPOrderVoReq addVIPOrderVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		
		OrderInfo orderInfo = new OrderInfo();
		
		try {
			dataEmptyChecker(addVIPOrderVoReq, "无效的操作");
			dataEmptyChecker(addVIPOrderVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(addVIPOrderVoReq.getMemberCode(), "会员号不能为空");
			dataEmptyChecker(addVIPOrderVoReq.getMemberName(), "会员名不能为空");
			dataEmptyChecker(addVIPOrderVoReq.getCreateSource(), "订单来源不能为空");
			dataEmptyChecker(addVIPOrderVoReq.getPhone(), "手机号不能为空");
			dataEmptyChecker(addVIPOrderVoReq.getProductId(), "商品ID不能为空");
			/*dataEmptyChecker(addVIPOrderVoReq.getAreaId(), "所属行政区域ID不能为空");
			dataEmptyChecker(addVIPOrderVoReq.getAreaName(), "所属行政区域名不能为空");*/
			dataEmptyChecker(addVIPOrderVoReq.getBankLabelID(), "开户行机构号不能为空");
			dataEmptyChecker(addVIPOrderVoReq.getClientChannel(), "开通方式不能为空");
			
			//是否创建过订单
			boolean isVipOrderCreated = RedisCommon.getUserVipOrderRedis(addVIPOrderVoReq.getClientId(), addVIPOrderVoReq.getMemberCode());
			if(isVipOrderCreated){
				List<OrderMongo> vipOrderList = orderSupport.getVipOrderList(addVIPOrderVoReq.getClientId(), addVIPOrderVoReq.getMemberCode(), false);
				if(EmptyChecker.isNotEmpty(vipOrderList)){
					orderInfo.setOrder(vipOrderList.get(0));
				}
				OrderLogger.info("订单模块", "开通VIP", "创建VIP订单校验：用户已经创建VIP订单", new Object[]{"client_id",addVIPOrderVoReq.getClientId(),"member_code",addVIPOrderVoReq.getMemberCode(),"未支付的VIP订单查询结果",JSON.toJSONString(vipOrderList)});
				throw new FroadBusinessException(ResultCode.failed.getCode(),"您已经提交过VIP订单，请进入'个人中心'查看");
			}
			
			//商品校验
			Map<String,String> productMap = commonLogic.getVipProductRedis(addVIPOrderVoReq.getClientId());
			dataEmptyChecker(productMap, "VIP商品信息获取为空");
			//商品ID
			String productId = productMap.get("vip_id");
			dataEmptyChecker(productId, "缓存中VIP商品ID为空");
			if(!StringUtils.equals(productId, addVIPOrderVoReq.getProductId())){
				LogCvt.error("商品信息不存在，缓存中商品ID："+productId+"，参数中商品ID：" + addVIPOrderVoReq.getProductId());
				throw new FroadBusinessException(ResultCode.failed.getCode(),"购买的VIP商品不存在");
			}
			//商品状态
			String status = productMap.get("status");
			dataEmptyChecker(status, "缓存中VIP商品状态为空");
			if(StringUtils.equals("0", status)){
				LogCvt.error("VIP商品未生效，商品ID："+productId);
				throw new FroadBusinessException(ResultCode.failed.getCode(),"购买的VIP商品未生效");
			}
			if(StringUtils.equals("2", status)){
				LogCvt.error("VIP商品已作废，商品ID："+productId);
				throw new FroadBusinessException(ResultCode.failed.getCode(),"购买的VIP商品已作废");
			}
			//商品价格
			String price = productMap.get("vip_price");
			dataEmptyChecker(status, "缓存中VIP商品价格为空");
			//商品名称
			String productName = productMap.get("activities_name");
			
			//开户行校验
			Org org = commonLogic.queryByOrgCode(addVIPOrderVoReq.getClientId(), addVIPOrderVoReq.getBankLabelID());
			if(EmptyChecker.isEmpty(org)){
				LogCvt.error("开户行机构信息在mysql中不存在，机构号："+addVIPOrderVoReq.getBankLabelID());
				throw new FroadBusinessException(ResultCode.failed.getCode(),"开户行机构信息不存在");
			}else{
				if(!org.getIsEnable()){
					LogCvt.error("提货网点已被禁用，机构号："+addVIPOrderVoReq.getBankLabelID());
					throw new FroadBusinessException(ResultCode.failed.getCode(),"开户行机构已禁用");
				}
			}
			
			//根据商户ID获取本级机构号与上级机构号
			Map<OrgLevelEnum, String> orgCodeMap = commonLogic.getSuperOrgByMerchantId(addVIPOrderVoReq.getClientId(),org.getMerchantId());
			dataEmptyChecker(orgCodeMap, "开户行上级机构信息不存在");
			
			//根据机构号取机构名
			List<Org> orgList = commonLogic.getSuperOrgList(addVIPOrderVoReq.getClientId(), addVIPOrderVoReq.getBankLabelID());
			String bankLabelName = "";
			if(EmptyChecker.isNotEmpty(orgList)){
				StringBuffer sb = new StringBuffer("");
				for(Org orgTemp : orgList){
					sb.append(orgTemp.getOrgName()+"-");
				}
				if(EmptyChecker.isNotEmpty(sb)){
					bankLabelName = sb.toString().substring(0,sb.lastIndexOf("-"));
				}
			}
			
			//订单信息
			OrderMongo order = new OrderMongo();
			order.setClientId(addVIPOrderVoReq.getClientId());
			order.setCreateTime(System.currentTimeMillis());
			order.setMemberCode(addVIPOrderVoReq.getMemberCode());
			order.setMemberName(addVIPOrderVoReq.getMemberName());
			order.setProductId(productId);
			order.setTotalPrice(Integer.valueOf(price));
			order.setRealPrice(Integer.valueOf(price));
			order.setAreaId(addVIPOrderVoReq.getAreaId());
			order.setAreaName(addVIPOrderVoReq.getAreaName());
			order.setCreateSource(addVIPOrderVoReq.getCreateSource());
			order.setForgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_one));
			order.setSorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_two));
			order.setTorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_three));
			order.setLorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_four));
			order.setBankLabelID(addVIPOrderVoReq.getBankLabelID());
			order.setBankLabelName(bankLabelName);
			order.setClientChannel(addVIPOrderVoReq.getClientChannel());
			order.setPhone(addVIPOrderVoReq.getPhone());
			order.setRemark(addVIPOrderVoReq.getRemark());
			order.setProductName(productName);
			order.setPaymentMethod(PaymentMethod.invalid.getCode());//支付方式:空
			
			//默认值
			order.setBankPoints(0);
			order.setFftPoints(0);
			order.setGivePoints(0);
			order.setVipDiscount(0);
			order.setState(OrderState.NORMAL.getCode());
			order.setOrderStatus(OrderStatus.create.getCode());
			order.setIsPoint(BooleanUtils.toInteger(false));
			order.setIsSeckill(BooleanUtils.toInteger(false));
			order.setIsQrcode(BooleanUtils.toInteger(false));
			order.setIsVipOrder(BooleanUtils.toInteger(true));
			
			String orderId = simpleOrderId.nextId();
			LogCvt.info("开通VIP，订单号："+orderId);
			order.setOrderId(orderId);
			
			//VIP订单，订单创建时，更新缓存
			RedisCommon.updateUserVIPOrderRedis(addVIPOrderVoReq.getClientId(),addVIPOrderVoReq.getMemberCode(),true);
			
			orderInfo.setOrder(order);
			
			result = new ResultBean(ResultCode.success,orderInfo);
		} catch (FroadBusinessException e) {
			LogCvt.error("开通VIP业务异常：" + e);
			return new ResultBean(ResultCode.failed,e.getMsg(),orderInfo);
		} catch (Exception e) {
			LogCvt.error("开通VIP系统异常：" + e);
			return new ResultBean(ResultCode.failed,e.getMessage());
		}
		return result;
	}
	
	
	public ResultBean getSubOrderValidate(GetSubOrderVoReq getSubOrderVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(getSubOrderVoReq, "无效的操作");
			dataEmptyChecker(getSubOrderVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(getSubOrderVoReq.getSubOrderId(), "子订单号不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	public ResultBean checkBeforeCashierValidate(CashierVoReq cashierVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(cashierVoReq, "无效的操作");
			dataEmptyChecker(cashierVoReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(cashierVoReq.getMemberCode(), "会员号不能为空");
			dataEmptyChecker(cashierVoReq.getMemberName(), "会员名不能为空");
			dataEmptyChecker(cashierVoReq.getOrderId(), "订单ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	/**
	 * 关闭订单参数校验
	 * @param deleteOrderVoReq
	 * @return
	 */
	public ResultBean validateCloseOrderParam(CloseOrderVoReq closeOrderVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(closeOrderVoReq, "无效的关闭订单操作");
			dataEmptyChecker(closeOrderVoReq.getOrderId(), "订单ID不能为空");
			dataEmptyChecker(closeOrderVoReq.getClientId(), "客户端ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	
	/**
	 * 创建惠付订单参数校验
	 * @param addPrefPayOrderReq
	 * @return
	 */
	public ResultBean validateAddPrefPayOrderParam(
			AddPrefPayOrderReq addPrefPayOrderReq) {
		LogCvt.info("创建惠付订单-订单校验-开始");
		LogCvt.info("请求参数：" + JSonUtil.toJSonString(addPrefPayOrderReq));
		ResultBean result = new ResultBean(ResultCode.success);
		
		try {
			/**
			 * ===============================1.订单参数校验===============================
			 */
			//参数校验
			dataEmptyChecker(addPrefPayOrderReq, "无效的下单操作");
			dataEmptyChecker(addPrefPayOrderReq.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(addPrefPayOrderReq.getMemberCode(), "会员ID不能为空");
			dataEmptyChecker(addPrefPayOrderReq.getMemberName(), "会员名不能为空");
			dataEmptyChecker(addPrefPayOrderReq.getProductId(), "商品ID不能为空");
			dataEmptyChecker(addPrefPayOrderReq.getCreateSource(), "订单来源不能为空");
			dataEmptyChecker(addPrefPayOrderReq.getPhone(), "用户注册手机号不能为空");
			
			//是否参与营销活动
			boolean isJoinMarketActive = false;
			
			/**
			 * ===============================2.商品有效性校验===============================
			 */
			//检查商品是否存在
			OutletProduct outletProduct =  commonSupport.queryOutletProduct(addPrefPayOrderReq.getProductId());
			dataEmptyChecker(outletProduct, "惠付商品不存在");
			dataEmptyChecker(outletProduct.getMerchantId(), "惠付商品所属商户不存在");
			dataEmptyChecker(outletProduct.getOutletId(), "惠付商品所属门店不存在");
			dataEmptyChecker(outletProduct.getConsumeAmount(), "惠付商品消费总金额为空");
			dataEmptyChecker(outletProduct.getDiscountRate(), "惠付商品折扣为空");
			if (outletProduct.getConsumeAmount() <= 0) {
				throw new FroadBusinessException(ResultCode.failed.getCode(), "惠付商品消费金额有误");
			}
			if (outletProduct.getDiscountRate() <= 0 || outletProduct.getDiscountRate() > 10000) {
				throw new FroadBusinessException(ResultCode.failed.getCode(), "惠付商品折扣有误");
			}
			
			Map<String,String> outletNameMap = RedisCommon.getOutletRedis(addPrefPayOrderReq.getClientId(),outletProduct.getMerchantId(),outletProduct.getOutletId());
			String outletName = "";
			if(EmptyChecker.isNotEmpty(outletNameMap)){
				outletName = outletNameMap.get("outlet_name");
			}
			/**
			 * ===============================3.商户有效性校验===============================
			 */
			//商户信息
			Map<String,String> merchantMap = commonLogic.getMerchantRedis(addPrefPayOrderReq.getClientId(), outletProduct.getMerchantId());
			//商户是否存在
			dataEmptyChecker(merchantMap, ResultCode.merchant_not_exist.getMsg());
			//商户名称
			String merchantName = merchantMap.get("merchant_name");
			
			//商户是否有效
			boolean isEnable = false;
			if(EmptyChecker.isNotEmpty(merchantMap.get("is_enable"))){
				isEnable = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("is_enable"))); // 商户状态
			}
			if(!isEnable){
				return new ResultBean(ResultCode.failed,"该商户已无法收款，请勿再次支付");
			}
			
			//子订单初始化
			String orderId = String.valueOf(simpleOrderId.nextId());//大订单号
			
			OrderLogger.info("创建惠付订单", "惠付订单校验", "相关信息", new Object[]{"商户名称",merchantName,"门店名称",outletName,"大订单号",orderId});
			
			//消费总金额
			Integer consumeMoney = outletProduct.getConsumeAmount();
			Integer notDiscountMoney = 0;
			if(EmptyChecker.isNotEmpty(outletProduct.getNotDiscountAmount())){
				notDiscountMoney = outletProduct.getNotDiscountAmount();
			}
			Integer discountRate = outletProduct.getDiscountRate();
			
			if (consumeMoney <= notDiscountMoney) {
				throw new FroadBusinessException(ResultCode.failed.getCode(), "惠付商品消费金额小于或等于不参与优惠金额");
			}
			
			//折扣优惠金额
			double realPayMoney = Arith.round(Arith.mul(Arith.div(Arith.sub(consumeMoney, notDiscountMoney), 1000),Arith.div(discountRate, 10000)), 2);
			Integer discountMoney = Arith.mul(Arith.sub(Arith.div(Arith.sub(consumeMoney, notDiscountMoney), 1000), realPayMoney),1000);
			
			int totalPrice = (int) (outletProduct.getCost());//实际总价
			
			int vipDiscount = 0;//VIP优惠价
			int totalGivePoints = 0;//订单赠送总积分
			
			//根据商户ID获取本级机构号与上级机构号
			Map<OrgLevelEnum, String> orgCodeMap = commonLogic.getSuperOrgByMerchantId(addPrefPayOrderReq.getClientId(),outletProduct.getMerchantId());
			
			/**
			 * ===============================4.大订单组装===============================
			 */
			//大订单Mongo
			OrderMongo order = new OrderMongo();
			order.setClientId(addPrefPayOrderReq.getClientId());
			order.setOrderId(orderId);
			order.setMemberCode(addPrefPayOrderReq.getMemberCode());
			order.setMemberName(addPrefPayOrderReq.getMemberName());
			order.setMerchantId(outletProduct.getMerchantId());
			order.setMerchantName(merchantName);
			order.setOutletId(outletProduct.getOutletId());
			order.setOutletName(outletName);
			order.setCreateSource(addPrefPayOrderReq.getCreateSource());
			order.setProductId(addPrefPayOrderReq.getProductId());
			order.setPhone(addPrefPayOrderReq.getPhone());
			order.setCreateTime(new Date().getTime());
			order.setOrderStatus(OrderStatus.create.getCode());
			order.setIsQrcode(BooleanUtils.toInteger(true));
			order.setPaymentMethod(PaymentMethod.invalid.getCode());//支付方式:空
			order.setCashDiscount(0);//代金券抵扣金额--暂不考虑
			order.setRealPrice(0);//总货币值 real_price
			order.setGivePoints(totalGivePoints);//赠送积分
			order.setTotalPrice(totalPrice);//总货币值
			order.setVipDiscount(vipDiscount);//VIP优惠金额
			order.setState(OrderState.NORMAL.getCode());
			order.setBankPoints(0);
			if(addPrefPayOrderReq.getBankPoint()>0){
				order.setBankPoints(Arith.mul(addPrefPayOrderReq.getBankPoint(), 1000));
			}
			order.setFftPoints(0);
			if(addPrefPayOrderReq.getFftPoint()>0){
				order.setFftPoints(Arith.mul(addPrefPayOrderReq.getFftPoint(),1000));
			}
			//积分比例
			if(EmptyChecker.isNotEmpty(addPrefPayOrderReq.getPointRate()) && addPrefPayOrderReq.getPointRate() >0){
				order.setPointRate(String.valueOf(addPrefPayOrderReq.getPointRate()));
			}
			
			order.setRemark(addPrefPayOrderReq.getRemark());
			
			order.setIsSeckill(BooleanUtils.toInteger(false));//默认值
			order.setIsVipOrder(BooleanUtils.toInteger(false));//默认值
			
			order.setForgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_one));
			order.setSorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_two));
			order.setTorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_three));
			order.setLorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_four));
			
			//是否惠付订单
			order.setIsPrefPay(BooleanUtils.toInteger(true));
			order.setDiscountMoney(discountMoney);
			order.setConsumeMoney(consumeMoney);
			
			//红包
			if(EmptyChecker.isNotEmpty(addPrefPayOrderReq.getRedPacketId())){
				isJoinMarketActive = true;
				order.setRedPacketId(addPrefPayOrderReq.getRedPacketId());
			}
			//优惠券
			if(EmptyChecker.isNotEmpty(addPrefPayOrderReq.getCashCouponId())){
				isJoinMarketActive = true;
				order.setCashCouponId(addPrefPayOrderReq.getCashCouponId());
			}
			
			if(isJoinMarketActive){
				order.setIsActive("1");
			}
			
			//返回数据
			result = new ResultBean(ResultCode.success,order);
		} catch (FroadBusinessException e) {
			LogCvt.error("订单校验失败，原因：" + e.getMsg());
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		LogCvt.info("创建惠付订单-订单校验-结束");
		return result;
	}
	
	public ResultBean validateRefundPayingOrder(RefundPayingOrderVoReq req) {
		ResultBean result = new ResultBean(ResultCode.success);
		try {
			dataEmptyChecker(req, "无效的退积分操作");
			dataEmptyChecker(req.getOrderId(), "订单ID不能为空");
			dataEmptyChecker(req.getClientId(), "客户端ID不能为空");
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	
	public static void main(String[] args){
//		PropertiesUtil.load();
		System.setProperty("CONFIG_PATH", "./config");
		OrderValidation valid =  new OrderValidation();
//		ResultBean pointResult = valid.memberInformationLogic.queryBankPointsByMobile("1001", "6229538106502396018");
		
//		ResultBean userResult = valid.memberInformationSupport.queryMemberAndCardInfo("anhui", "6229538106502396018");
//		System.out.println(JSON.toJSONString(userResult,true));
		
//		Map<String,String> map = RedisCommon.getMerchantRedis("anhui", "01A240508000");
		
//		ResultBean queryMemberResult = valid.memberInformationSupport.queryByLoginID("anhui12345678901");
		
		
		/*ResultBean memberInfoResult = valid.memberInformationLogic.queryMemberInfoByMemberCode(30005240030L);
		
		Map<OrgLevelEnum, String> orgCodeMap = valid.commonLogic.getSuperOrgByMerchantId("01FACAD28000");
		System.out.println(orgCodeMap);*/
		
		//通过商户查询机构信息
//		Org org = valid.commonLogic.queryByMerchantInfo("anhui", "026769D18000");
//		System.out.println(JSON.toJSON(org));
		
//		ResultBean pointResult = valid.memberInformationLogic.queryMemberPointsInfoByLoginID("anhui", user.getLoginID());
		
//		PropertiesUtil.load();
        CommonLogic m = new CommonLogicImpl();
//        System.out.println(JSON.toJSONString(m.getProductRedis("", "0268115C8000", "02681F578000"),true));
        
//        
//        Map<OrgLevelEnum, String> orgCodeMap = m.getSuperOrgByMerchantId("anhui","0266990F0000");
//        
//        System.out.println(JSON.toJSONString(orgCodeMap,true));
        
        List<Org> orgList = m.getSuperOrgList("anhui", "340101");
        System.out.println(JSON.toJSONString(orgList,true));
		String bankLabelName = "";
		if(EmptyChecker.isNotEmpty(orgList)){
			StringBuffer sb = new StringBuffer("");
			for(Org orgTemp : orgList){
				sb.append(orgTemp.getOrgName()+"-");
			}
			if(EmptyChecker.isNotEmpty(sb)){
				bankLabelName = sb.toString().substring(0,sb.lastIndexOf("-"));
			}
		}
		System.out.println("bankLabelName:"+bankLabelName);

	}


	
	
}
