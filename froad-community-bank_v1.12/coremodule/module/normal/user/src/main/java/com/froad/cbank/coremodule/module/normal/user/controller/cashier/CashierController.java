package com.froad.cbank.coremodule.module.normal.user.controller.cashier;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.enums.CreateSource;
import com.froad.cbank.coremodule.module.normal.user.pojo.AddQrOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.support.CashierSupport;
import com.froad.cbank.coremodule.module.normal.user.support.OrderSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.PayOrdersVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PreferenceViewVo;
import com.froad.thrift.vo.order.OrderVo;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.UserStatus;

/**
 * 收银台 王炜华
 */
@Controller
@RequestMapping
public class CashierController extends BasicSpringController {
	
	@Resource
	private CashierSupport cashierSupport;

	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private OrderSupport orderSupport;

	/**
	 * （针对面对面收银台 ）
	 * 获取银行卡列表，支付渠道列表，联合积分
	 * 
	 * @param model
	 * @param integralVo
	 * @throws Exception
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/cashier/payChannelList", method = RequestMethod.GET)
	public void payChannelList(ModelMap model, @RequestParam("userName") String userName, @RequestHeader("memberCode") Long memberCode, Boolean isSign, HttpServletRequest request) throws Exception {
		String clientId=getClient(request);
		if(StringUtil.isNotBlank(clientId) || isSign == null ){
			//针对用户名为身份证加密的情况
			if(userName != null && userName.indexOf("*") != -1){
				UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
				if(user != null){
					userName = user.getLoginID();
				}
			}
			LogCvt.info(String.format("请求参数:{userName=%s,memberCode=%s,clientId=%s}",userName,memberCode,clientId));
			model.putAll(cashierSupport.getPayChannelList(clientId, userName, memberCode, isSign));
		}else {
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
	}


	/**
	 * shoppingPayChannelList:普通商品收银台
	 * 只含有现金支付渠道
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月26日 下午12:15:41
	 * @param model
	 * @param userName
	 * @param memberCode
	 * @param isSign
	 * @param request
	 * @throws Exception
	 * 
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/cashier/shoppingPayList", method = RequestMethod.GET)
	public void shoppingPayChannelList(ModelMap model, @RequestParam("userName") String userName, @RequestHeader("memberCode") Long memberCode, Boolean isSign, HttpServletRequest request) throws Exception {
		String clientId=getClient(request);
		if(StringUtil.isNotBlank(clientId)){
			LogCvt.info(String.format("请求参数:{memberCode=%s,clientId=%s}",memberCode,clientId));
			model.putAll(cashierSupport.shoppingPayChannelList(clientId, memberCode, isSign));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}

	
	
	/**
	 * 面对面创建订单和订单支付接口
	 * @param model
	 * @param payOrdersVo
	 * @throws Exception
	 */
	@Token
	@RequestMapping(value = "/cashier/payQrcodeOrders", method = RequestMethod.POST)
	public void payQrcodeOrders(ModelMap model, @RequestBody PayOrdersVo payOrdersVo,@RequestHeader Long memberCode, HttpServletRequest request) throws Exception {
		long startTime = System.currentTimeMillis();			
		String clientId = getClient(request);
		payOrdersVo.setMemberCode(memberCode);
		LogCvt.info(String.format("面对面订单支付请求参数:{%s}",JSON.toJSONString(payOrdersVo)));	
		
		//是否简单密码标识
		boolean isSimplePayPwd = false;
		// 支付方式
		int type = payOrdersVo.getType();
		
		// 支付密码校验(含贴膜卡支付的方式，不需要检验支付密码)
		if (type == 1 || type == 2 || type == 3 || type == 4 ||  type == 7) {
			if (StringUtil.isNotBlank(payOrdersVo.getCiphertextPwd()) || StringUtil.isNotBlank(payOrdersVo.getCreateSource())) {
				// 支付密码验证
				CreateSource cs = SimpleUtils.codeToCreateSource(payOrdersVo.getCreateSource());
//				String paypwd = SimpleUtils.decodePwd(payOrdersVo.getCiphertextPwd(), cs);
				String paypwd = payOrdersVo.getCiphertextPwd();
				if (CreateSource.pc.equals(cs) || cs == null) {
		            LogCvt.info("[密码加密] >> 渠道：PC，支付密码加密");
		            paypwd = userEngineSupport.encryptPwd(paypwd);
		            if (StringUtil.isBlank(paypwd)) {
		            	LogCvt.info("[密码加密] >> 支付密码加密失败");
		            	model.put(Results.code, "9999");
						model.put(Results.msg, "支付密码加密失败");
						return;
					}
		        }
				ResultBean verifyPwd = userEngineSupport.verifyMemberPayPwd(payOrdersVo.getMemberCode(), paypwd, payOrdersVo.getClientId());
				if(!verifyPwd.isSuccess()){
					LogCvt.info(String.format("支付密码验证失败:%s",JSON.toJSONString(verifyPwd)));
					model.put(Results.code, StringUtil.isBlank(verifyPwd.getCode()) ? "9999" : verifyPwd.getCode() );
					model.put(Results.msg, verifyPwd.getMsg());
					return ;
				}else{
					LogCvt.info(String.format("支付密码验证成功! orderId:%s, memberCode:%s",payOrdersVo.getOrderId(), payOrdersVo.getMemberCode()));
					ResultBean validPwd = userEngineSupport.validationPayPwdRule(memberCode, paypwd);
					if(!validPwd.isSuccess()){
						isSimplePayPwd = true;
					}
				}

			} else {
				LogCvt.info(String.format("支付密码验证缺少必要参数:{ciphertextPwd=%s,createSource=%s}",payOrdersVo.getCiphertextPwd(),payOrdersVo.getCreateSource()));
				model.put(Results.code, "1001");
				model.put(Results.msg, "缺少必要的请求参数");
				return ;
			}
		}
		
		//用户状态判断
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(payOrdersVo.getMemberCode());
		if(user == null){
			model.put(Results.code, "9999");
			model.put(Results.msg, "查询用户信息失败");
			return;
		}else{
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
		}
		
		//针对用户名为身份证加密带*的情况,重新查询用户名
		payOrdersVo.setUserName(user.getLoginID());
		
		//先创建订单
		String orderId = "";
		if(StringUtil.isNotBlank(payOrdersVo.getQrCode())){
			//组建创建订单数据
			AddQrOrderPojo orderVo = new AddQrOrderPojo();
			orderVo.setQrCode(payOrdersVo.getQrCode());
			orderVo.setCreateSource(payOrdersVo.getCreateSource());
			orderVo.setCouponsNo(payOrdersVo.getCouponsNo());
			orderVo.setRedPacketNo(payOrdersVo.getRedPacketNo());
			Map<String, Object> addOrderMap = orderSupport.addQrOrder(clientId, memberCode, user.getLoginID(),user.getMobile(), orderVo);
			//创建订单流程改造
			orderId = (String) addOrderMap.get("orderId");
			model.put("orderId", orderId);
			if(StringUtil.isBlank(orderId)){
				model.put(Results.code,"9999");
				String msg = (String) ( addOrderMap.get(Results.msg) == null ? "创建面对面订单失败" : addOrderMap.get(Results.msg));
				model.put(Results.msg,msg);
				return ;
			}
			String isNeedCash = (String) addOrderMap.get("isNeedCash");
			// isNeedCash 0 不跳收银台 （不需要支付现金,但需要使用积分） 1 需要跳收银台（需要现金支付）  2 订单支付成功（纯红包或者优惠券）
			
			if("2".equals(isNeedCash)){ 				
				model.put(Results.code, "0000");
				model.put(Results.msg,"订单支付成功");
				model.put("isSimplePayPwd",isSimplePayPwd);
			}else{
				//做支付（只要含有积分或者现金都需要走支付接口）
				payOrdersVo.setClientId(clientId);
				payOrdersVo.setMemberCode(memberCode);
				payOrdersVo.setOrderId(orderId);
				Map<String, Object> resMap = cashierSupport.payQrcodeOrders(payOrdersVo,isSimplePayPwd);
				model.putAll(resMap);
				//业务监控统计
				if("0000".equals(resMap.get(Results.code))){
					Monitor.send(MonitorEnums.user_order_pay_success, "1");
				}
			}			
		}else{
			model.put(Results.code,"9999");
			model.put(Results.msg,"二维码信息为空");
			return;
		}

		Monitor.send(MonitorEnums.user_order_pay_time, Long.toString(System.currentTimeMillis() - startTime));
	}
	
	/**
	 * 普通商品订单支付接口
	 * @param model
	 * @param payOrdersVo
	 * @throws Exception
	 */
	@Token
	@RequestMapping(value = "/cashier/payOrders", method = RequestMethod.POST)
	public void payOrders(ModelMap model, @RequestBody PayOrdersVo payOrdersVo,@RequestHeader Long memberCode, HttpServletRequest request) throws Exception {
		long startTime = System.currentTimeMillis();
		String clientId = getClient(request);
		payOrdersVo.setClientId(clientId);
		payOrdersVo.setMemberCode(memberCode);
		LogCvt.info(String.format("普通商品支付请求参数:{%s}",JSON.toJSONString(payOrdersVo)));
		Map<String, Object> resMap = cashierSupport.payOrders(payOrdersVo);
		model.putAll(resMap);
		//业务监控统计
		if("0000".equals(resMap.get(Results.code))){
			Monitor.send(MonitorEnums.user_order_pay_success, "1");
		}
		Monitor.send(MonitorEnums.user_order_pay_time, Long.toString(System.currentTimeMillis() - startTime));
	}
	
	/**
	 * 
	 * shoppingPayVipChannel:开通vip支付.
	 *
	 * @author wm
	 * 2015年12月11日 上午11:49:29
	 * @param model
	 * @param userName
	 * @param memberCode
	 * @param isSign
	 * @param request
	 * @throws Exception
	 *
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/cashier/shoppingPayVipChannel", method = RequestMethod.GET)
	public void shoppingPayVipChannel(ModelMap model, @RequestParam("userName") String userName, @RequestHeader("memberCode") Long memberCode, Boolean isSign, HttpServletRequest request) throws Exception {
		String clientId=getClient(request);
		if(StringUtil.isNotBlank(clientId)){
			LogCvt.info(String.format("请求参数:{memberCode=%s,clientId=%s}",memberCode,clientId));
			model.putAll(cashierSupport.shoppingPayVipChannel(clientId,userName, memberCode, isSign));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
}
