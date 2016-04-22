package com.froad.CB.dao.user.impl;

import java.net.MalformedURLException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.CB.common.SysCommand;
import com.froad.CB.dao.user.UserSpecFunc;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.ContactType;
import com.pay.user.helper.CreateChannel;
import com.pay.user.service.UserSpecService;


public class UserSpecFuncImpl implements UserSpecFunc{

	
	private static Logger log = Logger.getLogger(UserSpecFuncImpl.class);
	private static HessianProxyFactory factory = new HessianProxyFactory();
	
	private static final String URL = SysCommand.USER_SPEC_SERVICE_URL;
	
	public UserResult registerMember(UserSpecDto usd){
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.registerMember(usd);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 UserSpecDto ：" + JSONObject.fromObject(usd) + " 出现异常", e);
			ur = null;
		}
		return ur;
	}
	
	public UserResult updateMemberInfoByMemberCode(UserSpecDto usd){
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.updateMemberInfo(usd);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 UserSpecDto ：" + JSONObject.fromObject(usd) + " 出现异常", e);
			ur = null;
		}
		return ur;
	}
	
	public UserResult queryByUserID(String userID){
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.queryMemberByUserID(userID);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 userID ：" + userID + " 出现异常", e);
			ur = null;
		}
		return ur;
	}

	public UserResult queryByLoginID(String loginID) {
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.queryMemberByLoginID(loginID);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 loginID ：" + loginID + " 出现异常", e);
			ur = null;
		}
		return ur;
	}

	public UserResult queryByMemberCode(Long memberCode) {
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.queryMemberByMemberCode(memberCode);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 MemberCode ：" + memberCode + " 出现异常", e);
			ur = null;
		}
		return ur;
	}

	public UserResult login(String loginID, String loginPWD, String loginIP) {
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.login(loginID, loginPWD, loginIP);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 loginID ：" + loginID + "loginPWD：" + loginPWD + " loginIP：" + loginIP + "出现异常", e);
			ur = null;
		}
		return ur;
	}
	
	public UserResult forgetPwd(Long memberCode){
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.forgetPwd(memberCode, ContactType.MOBILE);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 memberCode ：" + memberCode + " 出现异常", e);
			ur = null;
		}
		return ur;
	}
	
	@Override
	public UserResult forgetPwdWithChannel(Long memberCode) {
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.forgetPwdWithChannel(memberCode, ContactType.MOBILE,CreateChannel.FFT);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 memberCode ：" + memberCode + " 出现异常", e);
			ur = null;
		}
		return ur;
	}	

	@Override
	public UserResult bindMail(Long memberCode, String email) {
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.bindMail(memberCode, email);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 memberCode ：" + memberCode + "email：" + email +" 出现异常", e);
			ur = null;
		}
		return ur;
	}

	@Override
	public UserResult updatePwd(Long memberCode, String oldPwd, String newPwd) {
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.modifyMemberPwd(memberCode, oldPwd, newPwd);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 memberCode ：" + memberCode + "oldPwd：" + oldPwd +"newPwd：" + newPwd + "出现异常", e);
			ur = null;
		}
		return ur;
	}

	@Override
	public UserResult bindMobile(Long memberCode, String mobile) {
		UserResult ur = null;
		try {
			UserSpecService userSpecService = (UserSpecService)factory.create(UserSpecService.class,URL);
			ur = userSpecService.bindMobile(memberCode,mobile);
		} catch (MalformedURLException e) {
			log.error("调用账户账务平台  地址：" + URL + "  传入参数 memberCode ：" + memberCode + "mobile：" + mobile + "出现异常", e);
			ur = null;
		}
		return ur;
	}

	
}
