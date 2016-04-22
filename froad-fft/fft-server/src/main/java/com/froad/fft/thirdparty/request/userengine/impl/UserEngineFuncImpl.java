package com.froad.fft.thirdparty.request.userengine.impl;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.thirdparty.common.UserEngineCommend;
import com.froad.fft.thirdparty.request.userengine.UserEngineFunc;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.ContactType;
import com.pay.user.service.UserSpecService;

@Service("userEngineFuncImpl")
public class UserEngineFuncImpl implements UserEngineFunc {
	
	private static Logger logger = Logger.getLogger(UserEngineFuncImpl.class);

	private static HessianProxyFactory factory = new HessianProxyFactory();

	private static String URL = UserEngineCommend.USER_ENGINE_URL;
	
	@Override
	public UserResult registerMember(UserSpecDto userSpecDto) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).registerMember(userSpecDto);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: " + JSONObject.toJSONString(userSpecDto), e);
		}
		return userResult;
	}

	@Override
	public UserResult updateMemberInfoByMemberCode(UserSpecDto userSpecDto) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).updateMemberInfo(userSpecDto);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: " + JSONObject.toJSONString(userSpecDto), e);
		}
		return userResult;
	}

	@Override
	public UserResult queryByMemberCode(Long memberCode) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).queryMemberByMemberCode(memberCode);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}

	@Override
	public UserResult queryByLoginID(String loginID) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).queryMemberByLoginID(loginID);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: loginID" + loginID, e);
		}
		return userResult;
	}
	
	@Override
	public UserResult login(String loginID, String loginPWD, String loginIP) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).login(loginID, loginPWD, loginIP);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: loginID: " + loginID + "|loginPWD: " + loginPWD +"|loginIP: " + loginIP, e);
		}
		return userResult;
	}

	@Override
	public UserResult forgetPwd(Long memberCode) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).forgetPwd(memberCode,ContactType.MOBILE);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}

	@Override
	public UserResult updatePwd(Long memberCode, String oldPwd, String newPwd) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).modifyMemberPwd(memberCode, oldPwd, newPwd);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}

	@Override
	public UserResult bindMobile(Long memberCode, String mobile) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).bindMobile(memberCode,mobile);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}

	@Override
	public UserResult updateMemberInfo(UserSpecDto userSpecDto) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).updateMemberInfo(userSpecDto);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: " + JSONObject.toJSONString(userSpecDto), e);
		}
		return userResult;
	}


	/**
	*<p>重置密码</p>
	* @author larry
	* @datetime 2014-4-11下午03:07:31
	* @return UserResult
	* @throws 
	* @example<br/>
	*
	 */
	public UserResult resetUserPwd(Long memCode, String password) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService)factory.create(UserSpecService.class,URL)).resetMemberPwd(memCode, password);
		} catch (MalformedURLException e) {
			logger.error("hessian异常: memberCode" + memCode, e);
		}
		return userResult;
	}

	
	
	
}
