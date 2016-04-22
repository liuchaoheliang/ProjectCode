package com.froad.cbank.coremodule.module.normal.user.controller.order;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.GenerateOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.QrcodeOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.support.IntegralSupport;
import com.froad.cbank.coremodule.module.normal.user.support.MerchantSupport;
import com.froad.cbank.coremodule.module.normal.user.support.OrderSupport;
import com.froad.cbank.coremodule.module.normal.user.support.RedPacketSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.support.VipSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.SensEncryptUtil;
import com.froad.cbank.coremodule.module.normal.user.vo.DisplayPayChannelVo;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.LoginType;
import com.pay.user.helper.UserStatus;

/** 
 * 订单
 * @ClassName: OrderController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Create Author: hjz
 * @Create Date: 2015-3-30 上午11:08:20 
 */ 
@Controller
@RequestMapping
public class OrderController extends BasicSpringController {

	@Resource
	private OrderSupport orderSupport;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private VipSupport vipSupport;
	
	@Resource
	private RedPacketSupport redPacketSupport;
	
	@Resource
	private IntegralSupport integralSupport;

	@Resource
	private MerchantSupport merchantSupport;
	/**
	 * <p>创建订单</p>
	 * ****************************************
	 * 
	 * <p>团购交易，手机号phone不能为空</p>
	 * 
	 * <p>名优交易，手机号phone不能为空，配送信息recvId不能为空</p>
	 * 
	 * <p>在线积分兑换，手机号phone不能为空，配送信息recvId不能为空</p>
	 * 
	 * <p>预售-网点自提，orgCode,orgName,DeliveryType必填(0:送货上门; 1:网点自提)，网点自提deliveryId、手机号phone必填，送货上门recvId必填</p>
	 * 
	 * ****************************************
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 下午5:03:11
	 */
	@Token
	@RequestMapping(value = "/order/generate", method = RequestMethod.POST)
	public void generate(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody GenerateOrderPojo orderVo) {
		long startTime = System.currentTimeMillis();
		
		String clientId = getClient(request);
		//查询会员信息
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		String  memberName="";
		if(user == null){
			model.put(Results.code, "9999");
			model.put(Results.msg, "查询用户信息失败");
			return;
		}else {
			//隐藏身份证敏感信息(联合登录用户)
			memberName = user.getLoginID();
//			if(user.getLoginType() == LoginType.UNION.getValue()){
//				memberName = SensEncryptUtil.encryptIdentityNo(user.getLoginID());
//			}
		}		
		
		LogCvt.info(String.format("用户创建订单>>	clientId:%s, loginId:%s	memberCode:%s", clientId, user.getLoginID(), user.getMemberCode()));
		LogCvt.info(String.format("创建订单信息>> %s",JSONObject.toJSONString(orderVo)));
		
		//查询VIP信息
		int vipLevel = vipSupport.getVipLevel(memberCode, clientId);
		
		model.putAll(orderSupport.addOrder(clientId, memberCode, memberName, vipLevel, orderVo));
		
		//创建订单统计开始
		Monitor.send(MonitorEnums.user_order_generate_statistics, "1");
		Monitor.send(MonitorEnums.user_order_generate_time, Long.toString(System.currentTimeMillis() - startTime));
		//创建订单统计结束
		
		return;
		
	}
	
	
	
	
	
	/**
	 * 创建面对面订单  由于收银台改造，创建订单移到和面对面支付一起做
	 *@description 
	 *@author Liebert
	 *@date 2015年4月22日 下午4:41:37
	 */
	
/*	@Token
	@RequestMapping(value = "/order/add_qrcode", method = RequestMethod.POST)
	public void addQrcode(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody AddQrOrderPojo orderVo) {
		long startTime = System.currentTimeMillis();
		
		String clientId = getClient(request);
		if(StringUtil.isNotBlank(orderVo.getQrCode())){
			UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
			if(user == null){
				model.put(Results.code, "9999");
				model.put(Results.msg, "查询用户信息失败");
				return;
			}else{
				if(!user.getIsBindMobile()){
					respError(model, "请先前往安全中心进行手机号绑定");
					return;
				}
			}
			LogCvt.info(String.format("用户创建面对面订单>>	clientId:%s, loginId:%s	memberCode:%s", clientId, user.getLoginID(), user.getMemberCode()));
			LogCvt.info(String.format("创建面对面订单信息>> %s",JSONObject.toJSONString(orderVo)));
			
			model.putAll(orderSupport.addQrOrder(clientId, memberCode, user.getLoginID(),user.getMobile(), orderVo));
			
			//创建面对面订单统计开始
			Monitor.send(MonitorEnums.user_qrorder_generate_statistics, "1");
			Monitor.send(MonitorEnums.user_qrorder_generate_time, Long.toString(System.currentTimeMillis() - startTime));
			//创建面对面订单统计结束
			
			return;
		}else{
			model.put(Results.code,"9999");
			model.put(Results.msg,"二维码信息为空");
			return;
		}
		
	}*/
	
	
	/**
	 * 查询订单支付结果
	 * @param request
	 * @param memberCode
	 * @return
	 */
	@Token
	@RequestMapping(value = "/order/getPayRes", method = RequestMethod.GET)
	public void getOrderPaymentResult(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, String orderId) {
		String clientId = getClient(request);
		
		model.putAll(orderSupport.getOrderPaymentResult(clientId, orderId));
		
	}
	

	/**
	 * getPreference:(查询订单优惠活动信息)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月9日 下午7:15:37
	 * @param model
	 * @param request
	 * @param memberCode
	 * @param orderId
	 * 
	 */
	@Token
	@RequestMapping(value = "/order/preference", method = RequestMethod.GET)
	public void getPreference(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, String orderId,String subOrderId, String productId) {
		String clientId = getClient(request);
		if(StringUtil.isNotBlank(clientId)){			
			model.putAll(orderSupport.getOrderPreference(clientId, memberCode, orderId, subOrderId, productId));
		}else{
			model.put(Results.code,"9999");
			model.put(Results.msg,"必要参数为空");
		}
		
	}
	
	
	
	/**
	 * checkBefore:检查订单数据是否有效（积分值是否足够）
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年12月4日 下午6:44:29
	 * @param model
	 * @param request
	 * @param memberCode
	 * @param orderId
	 * @param subOrderId
	 * @param productId
	 * 
	 */
	@Token
	@RequestMapping(value = "/order/checkBefore", method = RequestMethod.GET)
	public void checkBefore(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, String orderId) {
		String clientId = getClient(request);
		if(StringUtil.isNotBlank(clientId)){
			//查询用户信息
			UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
			if(user == null){
				model.put(Results.code, "9999");
				model.put(Results.msg, "查询用户信息失败");
				return;
			}
			model.putAll(orderSupport.checkBeforeOrder(clientId, orderId, user.getLoginID()));
		}else{
			model.put(Results.code,"9999");
			model.put(Results.msg,"必要参数为空");
		}
		
	}
	
	/**
	 * 校验是否是有效的贴膜卡手机号
	 * @author 周煜涵
	 * @param model
	 * @param request
	 * @param mobileNum 手机号
	 * @date 2015年12月21日 下午3:27:57
	 */
	@Token
	@RequestMapping(value = "/order/checkFoilCardPhone", method = RequestMethod.GET)
	public void checkFoilCardPhone(ModelMap model, HttpServletRequest request,String mobileNum) {
		String clientId = getClient(request);
		if (StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(mobileNum)) {
			model.putAll(orderSupport.checkFoilCardPhone(mobileNum, clientId));
		} else {
			model.put(Results.code, "9999");
			model.put(Results.msg, "必要参数为空");
		}
	}
	
	
	/**
	 * hfOrderConfirm:惠付确认订单初始化页面
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2016年1月4日 下午1:37:37
	 * @param model
	 * @param request
	 * @param outletId
	 * 
	 */
	@Token
	@RequestMapping(value = "/order/hfOrderConfirm", method = RequestMethod.GET)
	public void hfOrderConfirm(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode ,String outletId) {
		String clientId = getClient(request);
		if (StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(outletId)) {
			//门店信息
			Map<String,Object> outletPoJo = merchantSupport.getSimpleOutletDetail(clientId, outletId);
			model.putAll(outletPoJo);
			
			//查询当前用户支持面对面付款的红包数量
			Map<String, Object>  map = redPacketSupport.canUsePackets(clientId, memberCode, true, true, null, null, new PagePojo());
			PagePojo page = (PagePojo) (map.get("page")!=null ? map.get("page"): new PagePojo());
			model.put("canUsePacketCount", page.getTotalCount());
			
			//用户积分信息
			//针对用户名为身份证加密的情况
			String userName = "";
			UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
			if(user != null){
				userName = user.getLoginID();
			}
			
			Map<String,Object> point =integralSupport.getUserPointsAmount(clientId, userName);
			DisplayPayChannelVo displayChannel = (DisplayPayChannelVo) point.get("displayChannel");
			//是否展示银行积分或者联盟积分
			if(displayChannel.isDisplayUnionPoint()){
				model.put("unionPointAmount",point.get("unionPointAmount"));
			}
			if(displayChannel.isDisplayBankPoint()){
				model.put("bankPointAmount",point.get("bankPointAmount"));
			}
		
		} else {
			model.put(Results.code, "9999");
			model.put(Results.msg, "必要参数为空");
		}
	}
	
	
	
	/**
	 * createHfOrder:创建惠付订单
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2016年1月4日 下午3:49:02
	 * @param model
	 * @param request
	 * @param memberCode
	 * @param orderVo
	 * 
	 */
	@Token
	@RequestMapping(value = "/order/createHfOrder", method = RequestMethod.POST)
	public void createHfOrder(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody QrcodeOrderPojo orderVo) {
		long startTime = System.currentTimeMillis();
		
		String clientId = getClient(request);
		//查询会员信息
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		String  memberName="";
		if(user == null){
			model.put(Results.code, "9999");
			model.put(Results.msg, "查询用户信息失败");
			return;
		}else {
			//未激活用户不可下单
			if(UserStatus.UNAVAILABLE.getValue() == user.getStatus()){
				model.put(Results.code, "9999");
				model.put(Results.msg, "当前用户处于未激活状态，有疑问请致电客服。");
				return ;
			}
			//没有手机号用户无法创建订单
			if(!user.getIsBindMobile()){
				respError(model, "请先前往安全中心进行手机号绑定");
				return;
			}
			
			//隐藏身份证敏感信息(联合登录用户)
			memberName = user.getLoginID();
//			if(user.getLoginType() == LoginType.UNION.getValue()){
//				memberName = SensEncryptUtil.encryptIdentityNo(user.getLoginID());
//			}
		}		
		String outletId = orderVo.getOutletId();
		Map<String,Object> outletPoJo = merchantSupport.getSimpleOutletDetail(clientId, outletId);
		
		boolean isEnable = (Boolean) (outletPoJo.get("preferStatus") != null ? outletPoJo.get("preferStatus") : false);
		if(!isEnable){
			// 若此时门店惠付状态未启用，则无法创建订单
			model.put(Results.code, "9999");
			model.put(Results.msg, "该门店不支持此功能");
			return;
		}
		//启用折扣优惠
//		if(orderVo.getIsEnableDiscount()){
//			//商户门店是否开启折扣优惠，设置折扣率
//			String discountRate = (String) (outletPoJo.get("discountRate")!= null ? outletPoJo.get("discountRate") : "");
//			orderVo.setDiscountRate(discountRate);
//		}

		//设置商户信息
		String merchantId = (String) (outletPoJo.get("merchantId")!= null ? outletPoJo.get("merchantId") : "");
		orderVo.setMerchantId(merchantId);
		
		LogCvt.info(String.format("用户创建惠付订单>>	clientId:%s, loginId:%s	memberCode:%s", clientId, user.getLoginID(), user.getMemberCode()));
		LogCvt.info(String.format("创建惠付订单信息>> %s",JSONObject.toJSONString(orderVo)));
		
		model.putAll(orderSupport.createHfOrder(clientId, memberCode, memberName,user.getMobile(),orderVo));
		
		//创建订单统计开始
		Monitor.send(MonitorEnums.user_order_generate_statistics, "1");
		Monitor.send(MonitorEnums.user_order_generate_time, Long.toString(System.currentTimeMillis() - startTime));
		//创建订单统计结束
		
		return;
		
	}
	
	
}
