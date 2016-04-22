package com.froad.jetty.service.impl;

import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.enums.CashType;
import com.froad.enums.H5ResultCode;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.jetty.service.SupportService;
import com.froad.jetty.vo.PlaceOrderRequestVo;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;
import com.froad.thrift.client.ThriftService;
import com.froad.thrift.client2.ServiceClient;
import com.froad.thrift.service.MemberSecurityService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.PropertiesUtil;
//import com.pay.pe.dto.AccountResult;
//import com.pay.pe.service.AcctSpecService;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.service.UserSpecService;

/**
 * 支撑逻辑层实现类
 * 
 * @author wangzhangxu
 * @date 2015年4月28日 上午10:59:52
 * @version v1.0
 */
public class SupportServiceImpl implements SupportService {
	
	static final String HESSIAN_SERVICE_FILENAME_KEY = "hessian";
	static final String HESSIAN_SERVICE_USER_URL_KEY = "hessian.service.user.url";
	
	static final String KEY_SECKILL_FILENAME = "seckill";
	static final String KEY_SECKILL_PAY_CHECK_SMS_PREFIX = "seckill.pay.check.sms.";
	
	private HessianProxyFactory hessianFactory;
	private UserSpecService userSpecService;
	//private AcctSpecService acctSpecService;
	
	//private SmsMessageService.Iface smsMessageService;
	//private MemberSecurityService.Iface memberSecurityService;
	
	public SupportServiceImpl(){
		hessianFactory = new HessianProxyFactory();
		try {
			String urlPrefix = PropertiesUtil.getProperty(HESSIAN_SERVICE_FILENAME_KEY, HESSIAN_SERVICE_USER_URL_KEY);
			userSpecService = (UserSpecService)hessianFactory.create(UserSpecService.class, urlPrefix + "userSpecService");
			//acctSpecService = (AcctSpecService)hessianFactory.create(AcctSpecService.class, urlPrefix + "acctSpecService");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//smsMessageService = ThriftService.getSmsMessageService();
		//memberSecurityService = ThriftService.getMemberSecurityService();
	}
	
	@Override
	public ResponseVo checkToken(Long memberCode, String token) {
		Result result = null;
		try {
			result = userSpecService.verifyToken(token, memberCode);
			if (!result.getResult()) {
				LogCvt.debug(H5ResultCode.errorToken.getMsg() + "[memberCode=" + memberCode + ", token=" + token + "]");
				return new ResponseVo(H5ResultCode.errorToken);
			}
		} catch (Exception e){
			LogCvt.error("验证用户登录Token，" + H5ResultCode.userLoginCenterException.getMsg() + "[memberCode=" + memberCode + ", token=" + token + "]", e);
			return new ResponseVo(H5ResultCode.userLoginCenterException);
		}
		
		return new ResponseVo(H5ResultCode.success);
	}
	
	@Override
	public ResponseVo checkSmsCode(String clientId, String smsToken, String smsCode) {
		String checkSmsCodeKey = KEY_SECKILL_PAY_CHECK_SMS_PREFIX + clientId;
		String switchFlag = PropertiesUtil.getProperty(KEY_SECKILL_FILENAME, checkSmsCodeKey);
		if ("on".equalsIgnoreCase(switchFlag)) { // 是否有配置为ON的
			LogCvt.debug("验证支付短信验证码开启[" + checkSmsCodeKey + "=" + switchFlag + "]");
			
			ResultVo result = null;
			ServiceClient serviceClient = new ServiceClient();
			try {
				SmsMessageService.Iface smsMessageService = (SmsMessageService.Iface)serviceClient.createClient(SmsMessageService.Iface.class);
				result = smsMessageService.verifyMobileToken(smsToken, smsCode);
				if (!H5ResultCode.success.getCode().equals(result.getResultCode())) {
					LogCvt.debug(result.getResultDesc() + "[clientId=" + clientId + ", smsToken=" + smsToken + ", smsCode=" + smsCode + "]");
				}
				return new ResponseVo(result.getResultCode(), result.getResultDesc());
			} catch (TException e) {
				LogCvt.error("验证短信验证码，" + H5ResultCode.thriftException.getMsg() + "[clientId=" + clientId + ", smsToken=" + smsToken + ", smsCode=" + smsCode + "]", e);
				return new ResponseVo(H5ResultCode.thriftException);
			} finally {
				try {
					serviceClient.returnClient();
				} catch(Exception e) {
					LogCvt.error("释放Thrift连接异常", e);
					return new ResponseVo(H5ResultCode.thriftException);
				}
			}
		}
		
		return new ResponseVo(H5ResultCode.success);
	}

	/*
	@Override
	public ResponseVo checkPayPassword(Long memberCode, String password, String source) {
		// 是否已设置支付密码
		AccountResult result = null;
		try {
			result = acctSpecService.isExistPayPwd(memberCode);
			if (!result.getResult()) {
				LogCvt.debug(H5ResultCode.payPasswordNotExist.getMsg() + "[memberCode=" + memberCode + "]");
				return new ResponseVo(H5ResultCode.payPasswordNotExist);
			}
		}catch(Exception e){
			LogCvt.error("验证用户是否设置支付密码，" + H5ResultCode.userPayCenterException.getMsg() + "[memberCode=" + memberCode + "]", e);
			return new ResponseVo(H5ResultCode.userPayCenterException);
		}
		
		// 支付密码是否正确
		try {
			// 需要根据source解密password
			result = acctSpecService.validationPayPwd(memberCode, password);
			if (!result.getResult()) {
				LogCvt.debug(H5ResultCode.errorPayPassword.getMsg() + "[memberCode=" + memberCode + "]");
				return new ResponseVo(H5ResultCode.errorPayPassword);
			}
		}catch(Exception e){
			LogCvt.error("验证用户支付密码，" + H5ResultCode.userPayCenterException.getMsg() + "[memberCode=" + memberCode + "]", e);
			return new ResponseVo(H5ResultCode.userPayCenterException);
		}
		
		return new ResponseVo(H5ResultCode.success);
	}
	*/
	
	@Override
	public ResponseVo checkPayPassword(Long memberCode, String password, String source) {
		// 是否已设置支付密码
		ResultVo resultVo = null;
		ServiceClient serviceClient = new ServiceClient();
		try {
			MemberSecurityService.Iface memberSecurityService = (MemberSecurityService.Iface)serviceClient.createClient(MemberSecurityService.Iface.class);
			resultVo = memberSecurityService.isMemberSetPayPwd(memberCode);
			if (!ResultCode.success.getCode().equals(resultVo.getResultCode())) {
				LogCvt.debug(H5ResultCode.payPasswordNotExist.getMsg() + "[memberCode=" + memberCode + "]");
				return new ResponseVo(H5ResultCode.payPasswordNotExist);
			}
		}catch(Exception e){
			LogCvt.error("验证用户是否设置支付密码，" + H5ResultCode.userPayCenterException.getMsg() + "[memberCode=" + memberCode + "]", e);
			return new ResponseVo(H5ResultCode.userPayCenterException);
		} finally {
			try {
				serviceClient.returnClient();
			} catch(Exception e) {
				LogCvt.error("释放Thrift连接异常", e);
				return new ResponseVo(H5ResultCode.thriftException);
			}
		}
		
		// 支付密码是否正确
		serviceClient = new ServiceClient();
		try {
			MemberSecurityService.Iface memberSecurityService = (MemberSecurityService.Iface)serviceClient.createClient(MemberSecurityService.Iface.class);
			resultVo = memberSecurityService.verifyMemberPayPwd(memberCode, password, source);
			if (!ResultCode.success.getCode().equals(resultVo.getResultCode())) {
				LogCvt.debug(H5ResultCode.errorPayPassword.getMsg() + "[memberCode=" + memberCode + ", source=" + source + "]");
				return new ResponseVo(H5ResultCode.errorPayPassword);
			}
		}catch(Exception e){
			LogCvt.error("验证用户支付密码，" + H5ResultCode.userPayCenterException.getMsg() + "[memberCode=" + memberCode + "]", e);
			return new ResponseVo(H5ResultCode.userPayCenterException);
		} finally {
			try {
				serviceClient.returnClient();
			} catch(Exception e) {
				LogCvt.error("释放Thrift连接异常", e);
				return new ResponseVo(H5ResultCode.thriftException);
			}
		}
		
		return new ResponseVo(H5ResultCode.success);
	}
	
	public UserSpecDto getUserInfo(Long memberCode){
		UserResult userResult = null;
		UserSpecDto userDto = null;
		try {
			userResult = userSpecService.queryMemberByMemberCode(memberCode);
			if (userResult.getResult()) {
				userDto = userResult.getUserList().get(0);
			} else {
				LogCvt.debug("未获取到用户的信息[memberCode=" + memberCode + "]");
			}
		}catch(Exception e){
			LogCvt.error("获取用户信息，" + H5ResultCode.userLoginCenterException.getMsg() + "[memberCode=" + memberCode + "]", e);
		}
		
		return userDto;
		
	}

	@Override
	public ResponseVo calculateAmount(PlaceOrderRequestVo reqVo, Double totalAmount) {
		// 清空积分和现金金额
		reqVo.setPointAmount(0D);
		reqVo.setCashAmount(0D);
		
		
		if (PaymentMethod.cash.getCode().equals(reqVo.getPayType().toString())) {
			if (reqVo.getCashType() == null) {
				
			} else if (CashType.foil_card.code() == reqVo.getCashType().intValue()) {
				if (StringUtils.isEmpty(reqVo.getFoilCardNum())) {
					
				}
			} else if (CashType.bank_fast_pay.code() != reqVo.getCashType().intValue()  
					&& CashType.foil_card.code() != reqVo.getCashType().intValue() 
					&& CashType.timely_pay.code() != reqVo.getCashType().intValue() ) {
				
			}
		} else if (PaymentMethod.froadPoints.getCode().equals(reqVo.getPayType().toString()) 
				|| PaymentMethod.bankPoints.getCode().equals(reqVo.getPayType().toString())) {
			if (StringUtils.isEmpty(reqVo.getPointOrgNo()) 
					|| reqVo.getPointAmount() == null) {
				
			}
		} else if (PaymentMethod.froadPointsAndCash.getCode().equals(reqVo.getPayType().toString()) 
				|| PaymentMethod.bankPointsAndCash.getCode().equals(reqVo.getPayType().toString())) {
			if (StringUtils.isEmpty(reqVo.getPointOrgNo()) 
					|| reqVo.getPointAmount() == null
					|| StringUtils.isEmpty(reqVo.getCashOrgNo()) 
					|| reqVo.getCashAmount() == null) {
				
			}
		} else {
			
		}
		return null;
	}
	
	
}
