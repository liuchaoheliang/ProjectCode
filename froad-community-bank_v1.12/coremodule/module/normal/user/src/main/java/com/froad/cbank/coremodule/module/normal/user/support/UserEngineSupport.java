package com.froad.cbank.coremodule.module.normal.user.support;

import static com.froad.cbank.coremodule.module.normal.user.utils.Constants.Token.COOKIE_DOMAIN;
import static com.froad.cbank.coremodule.module.normal.user.utils.Constants.Token.COOKIE_KEY;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.DESUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.ClientChannelEnum;
import com.froad.cbank.coremodule.module.normal.user.pojo.QuestionPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.SafeAppealPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.UserEnginePojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.coremodule.module.normal.user.utils.SensEncryptUtil;
import com.froad.cbank.coremodule.module.normal.user.vo.SafeQuestionPojo;
import com.froad.thrift.service.EncryptService;
import com.froad.thrift.service.RSAPasswordService;
import com.froad.thrift.service.VIPWhiteListService;
import com.froad.thrift.service.ActiveRunService.AsyncProcessor.returnMarketOrder;
import com.froad.thrift.vo.CheckVIPExistWhiteListReqVo;
import com.froad.thrift.vo.CheckVIPExistWhiteListRespVo;
import com.pay.user.helper.ClientChannel;
import com.froad.thrift.vo.EncryptType;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.pay.pe.dto.AccountResult;
import com.pay.pe.dto.QuestionSpecDto;
import com.pay.pe.helper.ErrorCodeType;
import com.pay.pe.service.AcctSpecService;
import com.pay.user.dto.MemberApplySpecDto;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.CreateChannel;
import com.pay.user.helper.LoginType;
import com.pay.user.helper.MemberType;
import com.pay.user.helper.UserLevel;
import com.pay.user.helper.VIPLevel;
import com.pay.user.helper.VIPStatus;
import com.pay.user.service.UserOuterSpecService;
import com.pay.user.service.UserSpecService;

@Service
public class UserEngineSupport extends BaseSupport {

	@Resource
	private EncryptService.Iface encryptService;
	
	@Resource
	private RSAPasswordService.Iface rsaPasswordService;
	
	@Resource
	private VIPWhiteListService.Iface vipWhiteListService;
	
	@Resource
	private UserSpecService userSpecService;
	
	@Resource 
	private AcctSpecService acctSpecService;
	
	@Resource
	private UserOuterSpecService userOuterSpecService;
	
	@Resource
	private VipSupport vipSupport;
	
	@Resource
	private CodeSupport codeSupport;

	
	/**
	 * 会员登录
	 *@description 
	 *@author Liebert
	 *@date 2015年4月7日 下午3:39:53
	 */
	public UserResult loginMember(String loginId, String loginPwd, String loginIp, CreateChannel createChannel, String clientId){
		UserResult userResult = new UserResult();
		ResultVo resultVo = new ResultVo();
//		String loginPwd1 = "a123456";
//		try {
//			loginPwd1 = DESUtil.encrypt(loginPwd1);
//			System.out.println(loginPwd1);
//		} catch (Exception e1) {
//			LogCvt.error("登录密码加密异常", e1);
//		}
		try{
			//对密码进行解密加密
			resultVo = processor(loginPwd);
			String encodePassword = "";
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.resultCode)) {
				encodePassword = resultVo.getResultDesc();
			}else {
				LogCvt.error("会员密码加密解密失败："+resultVo.getResultDesc());
				userResult.setResult(false);
				userResult.setMsgCode(9999);
				userResult.setErrorMsg("会员登录密码解密失败");
				return userResult;
			}
			//登录
			userResult = userSpecService.loginMember(loginId, encodePassword, loginIp, createChannel);
		}catch (TException e) {
			LogCvt.error("会员密码加密解密异常："+e);
			userResult.setResult(false);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("会员登录密码解密失败");
			return userResult;
		}catch(Exception e){
			LogCvt.error("会员登录接口异常", e);
			userResult.setResult(false);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("会员登录接口异常");
		}
		
		if(!userResult.getResult()){
			handleLoginFailure(userResult.getDemo(), clientId, loginId, loginIp);
		}
		
		return userResult;
	}
	
	
	
	/**
	 * 会员注册
	 *@description 
	 *@author Liebert
	 *@date 2015年4月7日 下午4:05:17
	 */
	public UserResult registerMember(String loginId, String loginPwd, String mobile, String createChannel, String ip, String clientId){
		UserResult userResult = new UserResult();
		UserSpecDto userSpecDto = new UserSpecDto();
		ResultVo resultVo = new ResultVo();
		
//		String encodePwd = DESUtil.encrypt(loginPwd);
		userSpecDto.setLoginID(loginId);
//		userSpecDto.setLoginPwd(encodePwd);
		userSpecDto.setMobile(mobile);
		userSpecDto.setCreateChannel(createChannel);
		userSpecDto.setRegisterIP(ip);
		userSpecDto.setMemberType(MemberType.PERSONAL.getValue());
		
		BankOrg bankOrg = ClientChannelEnum.getClientBankOrg(clientId);
		userSpecDto.setBankOrgNo(bankOrg.getBankOrg());
		
		//TODO 机构信息
		userSpecDto.setUserBankID(null);
		userSpecDto.setOrgCode(null);
		
		try{
			//对密码解加密
			resultVo = processor(loginPwd);
			String encodePassword = "";
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.resultCode)) {
				encodePassword = resultVo.getResultDesc();
				userSpecDto.setLoginPwd(encodePassword);
			}else {
				LogCvt.error("注册密码加密解密失败："+resultVo.getResultDesc());
				userResult.setResult(false);
				userResult.setMsgCode(9999);
				userResult.setErrorMsg("会员注册密码加解密失败");
				return userResult;
			}
			userResult = userSpecService.registerMember(userSpecDto);
		}catch (TException e) {
			LogCvt.error("注册密码加密解密异常："+e);
			userResult.setResult(false);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("会员注册密码加解密失败");
			return userResult;
		}catch(Exception e){
			LogCvt.error("会员注册接口异常", e);
			userResult.setResult(false);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("会员注册接口异常");
		}
		
		return userResult;
	}
	
	
	
	/**
	 * 联合登陆 - 校验登录密码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 下午3:30:02
	 */
	public UserResult verifyUnionAccountLoginPwd(String mobile, String loginPwd){
//		try {
//			loginPwd = DESUtil.encrypt(loginPwd);
//		} catch (Exception e1) {
//			LogCvt.error("登录密码加密异常", e1);
//		}
		
		UserResult userResult = new UserResult();
		ResultVo resultVo = new ResultVo();
		try{
			//对密码进行解密加密
			resultVo = processor(loginPwd);
			String encodePassword = "";
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.resultCode)) {
				encodePassword = resultVo.getResultDesc();
			}else {
				LogCvt.error("联合登陆校验登录密码加密解密失败："+resultVo.getResultDesc());
				userResult.setResult(false);
				userResult.setMsgCode(9999);
				userResult.setErrorMsg("校验登录密码加解密失败");
				return userResult;
			}
			userResult = userSpecService.checkPwd(mobile, encodePassword);
		}catch (TException e) {
			LogCvt.error("联合登陆校验登录密码加密解密异常："+e);
			userResult.setResult(false);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("校验登录密码加解密失败");
			return userResult;
		}catch(Exception e){
			LogCvt.error("联合登陆校验登录密码接口异常", e);
			userResult.setResult(false);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("校验登录密码接口异常");
		}
		return userResult;
		
	}

	
	
	/**
	 * 校验用户名是否被使用 <br/>
	 * True:已经被使用；False:未使用
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 下午3:57:02
	 */
	public boolean queryIsLoginIdUsed(String loginId){
		UserResult userResult = new UserResult();
		try{
			userResult = userSpecService.validUserLoginId(loginId);
		}catch(Exception e){
			LogCvt.error("校验用户名存在性接口异常", e);
			userResult.setResult(true);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("校验用户名存在性接口异常");
		}
		return userResult.getResult();
		
	}
	
	
	/**
	 * 校验手机号是否被使用  联合登陆用户使用
	 * True:已经被使用；False:未使用
	 *@description 
	 *@author Liebert
	 *@date 2015年6月24日 下午4:50:02
	 */
	public boolean queryIsUnionMobileUsed(Long memberCode, String mobile){
		UserResult userResult = new UserResult();
		try{
			userResult = userSpecService.validUnionMobile(memberCode,mobile);
		}catch(Exception e){
			LogCvt.error("校验手机号存在性接口异常", e);
			userResult.setResult(true);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("校验手机号存在性接口异常");
		}
		return userResult.getResult();
		
	}
	
	
	
	/**
	 * 校验手机号是否被使用  - 联合登陆用户使用
	 * True:已经被使用；False:未使用
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 下午3:50:02
	 */
	public boolean queryIsMobileUsed(String mobile){
		UserResult userResult = new UserResult();
		try{
			userResult = userSpecService.validUserMobile(mobile);
		}catch(Exception e){
			LogCvt.error("校验手机号存在性接口异常", e);
			userResult.setResult(true);
			userResult.setMsgCode(9999);
			userResult.setErrorMsg("校验手机号存在性接口异常");
		}
		return userResult.getResult();
		
	}
	
	/**
	 * 
	 * @Description: 校验用户是否设置支付密码
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月17日 下午4:30:55
	 * @param memberCode
	 * @return
	 */
	public boolean isMemberExistPayPwd(Long memberCode){
		
		AccountResult paypwdResult = new AccountResult();
		try{
			paypwdResult = acctSpecService.isExistPayPwd(memberCode);
		}catch(Exception e){
			LogCvt.error("校验支付密码存在性接口异常", e);
			paypwdResult.setResult(true);
		}
		return paypwdResult.getResult();
	}

	
	
	/**
	 * 设置支付密码
	 * @description 
	 * @author 
	 * @date 2015年5月19日 下午2:36:00
	 *
	 */
	public ResultBean setMemberPayPwd(Long memberCode, String payPwd){
		ResultBean result = new ResultBean();
		ResultVo resultVo = new ResultVo();
		if(StringUtil.isBlank(payPwd)){
			result.setSuccess(false);
			result.setCode("9999");
			result.setMsg("支付密码不能为空");
			return result;
		}
		
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		
		try{
//			String encodePassword1 = DESUtil.encrypt(payPwd);
			
			//对密码进行解密加密
			resultVo = processor(payPwd);
			String encodePassword = "";
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.resultCode)) {
				encodePassword = resultVo.getResultDesc();
			}else {
				LogCvt.error("支付密码加密解密失败："+resultVo.getResultDesc());
				result.setCode(resultVo.getResultCode());
				result.setMsg("支付密码加解密失败");
				return result;
			}
			//设置支密码
			accountResult = acctSpecService.resetPayPwd(memberCode, encodePassword);
		}catch (TException e) {
			LogCvt.error("支付密码加密解密异常："+e);
			result.setCode(9999+"");
			result.setMsg("支付密码加解密失败");
			return result;
		}catch(Exception e){
			LogCvt.error("设置支付密码发生异常", e);
		}
		
		if(accountResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{
			result.setSuccess(false);
			result.setCode(accountResult.getErrorCode());
			result.setMsg(accountResult.getErrorMsg());
			return result;
		}
	}
	
	

	/**
	 * 校验支付密码
	 * @description 
	 * @author 
	 * @date 2015年5月20日 上午10:17:40
	 *
	 */
	public ResultBean verifyMemberPayPwd(Long memberCode, String payPwd, String clientId){
		ResultBean result = new ResultBean();
		ResultVo resultVo = new ResultVo();
		if(StringUtil.isBlank(payPwd)){
			result.setSuccess(false);
			result.setCode("9999");
			result.setMsg("支付密码不能为空");
			return result;
		}
		
		
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
//			String encodePassword = DESUtil.encrypt(payPwd);
			//对密码进行解密加密
			resultVo = processor(payPwd);
			
			String encodePassword = "";
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.resultCode)) {
				encodePassword = resultVo.getResultDesc();
			}else {
				LogCvt.error("支付密码加密解密失败："+resultVo.getResultDesc());
				result.setSuccess(false);
				result.setCode(resultVo.getResultCode());
				result.setMsg("支付密码加密解密失败");
				return result;
			}
			//校验密码
			accountResult = acctSpecService.validationPayPwd(memberCode, encodePassword);
		}catch (TException e) {
			LogCvt.error("支付密码加密解密异常："+e);
			result.setSuccess(false);
			result.setCode(9999+"");
			result.setMsg("支付密码加密解密失败");
			return result;
		} catch (Exception e) {
			LogCvt.error("校验支付密码发生异常", e);
		}
		
		if(accountResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{

			//支付密码验证失败超过次数发送警告短信
			handlePayPwdValidateFailure(accountResult.getErrorCode(), clientId, memberCode);
			
			result.setSuccess(false);
			result.setCode(accountResult.getErrorCode());
			result.setMsg(accountResult.getErrorMsg());
			return result;
		}
	}
	
	
	
	/**
	 * 校验支付密码安全规则
	 * @description 
	 * @author 
	 * @date 2015年9月18日 上午10:17:40
	 *
	 */
	public ResultBean validationPayPwdRule(Long memberCode, String payPwd){
		ResultBean result = new ResultBean();
		
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			ResultVo resultVo = processor(payPwd);
			String encodePassword = "";
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.resultCode)) {
				encodePassword = resultVo.getResultDesc();
			}else {
				LogCvt.error("支付密码加密解密失败："+resultVo.getResultDesc());
				result.setCode(resultVo.getResultCode());
				result.setMsg("支付密码加解密失败");
				return result;
			}
		
			accountResult = acctSpecService.validationPayPwdRule(memberCode, encodePassword);
		}catch (TException e) {
			LogCvt.error("支付密码加解密异常", e);
		}catch (Exception e) {
			LogCvt.error("校验支付密码安全规则发生异常", e);
		}
		
		if(accountResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{
			result.setSuccess(false);
			result.setCode(accountResult.getErrorCode());
			result.setMsg(accountResult.getErrorMsg());
			return result;
		}
	}
	
	

	/**
	 * 
	 * @Description: 重置登录密码
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月17日 下午5:32:51
	 * @param memberCode
	 * @param newPwd
	 * @return
	 */
	public ResultBean resetMemberPwd(Long memberCode, String newPwd){
		ResultBean result = new ResultBean();
		ResultVo resultVo = new ResultVo();
		if(StringUtil.isBlank(newPwd)){
			result.setSuccess(false);
			result.setCode("9999");
			result.setMsg("新密码不能为空");
			return result;
		}
		
		UserResult userResult = new UserResult();
		userResult.setResult(false);
		try {
//		    String encodePassword = DESUtil.encrypt(newPwd);
			//对密码进行解密加密
			resultVo = processor(newPwd);
			String encodePassword = "";
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.resultCode)) {
				encodePassword = resultVo.getResultDesc();
			}else {
				LogCvt.error("登录密码加密解密失败："+resultVo.getResultDesc());
				result.setSuccess(false);
				result.setCode(resultVo.getResultCode());
				result.setMsg("登录密码加密解密失败");
				return result;
			}
			//修改密码
			userResult = userSpecService.resetMemberPwd(memberCode, encodePassword);
		}catch (TException e) {
			LogCvt.error("登录密码加密解密异常："+e);
			result.setSuccess(false);
			result.setCode(9999+"");
			result.setMsg("登录密码加密解密失败");
			return result;
		} catch (Exception e) {
			LogCvt.error("重置登录密码发生异常" + memberCode, e);
		}
		
		if(userResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{
			result.setSuccess(false);
			result.setCode(userResult.getMsgCode()+"");
			result.setMsg(userResult.getErrorMsg());
			return result;
		}
	}
	

	

	/**
	 * 
	 * @Description: 修改登录密码
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月17日 下午5:36:12
	 * @param memberCode
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	public ResultBean resetMemberPwdByOldPwd(Long memberCode,String oldPwd,String newPwd){
		ResultBean result = new ResultBean();
		ResultVo oldResultVo = new ResultVo();
		ResultVo newResultVo = new ResultVo();
		if(StringUtil.isBlank(newPwd) || StringUtil.isBlank(oldPwd)){
			result.setSuccess(false);
			result.setCode("9999");
			result.setMsg("新密码或旧密码不能为空");
			return result;
		}
		
		UserResult userResult = new UserResult();
		userResult.setResult(false);
		try {
//		    String oldPassword = DESUtil.encrypt(oldPwd);
//		    String newPassword = DESUtil.encrypt(newPwd);
			//新旧密码的解密加密
		    oldResultVo = processor(oldPwd);
		    newResultVo = processor(newPwd);

		    String oldPassword = "";
		    String newPassword = "";
		    if (Constants.RESULT_CODE_SUCCESS.equals(oldResultVo.getResultCode())) {
		    	oldPassword = oldResultVo.getResultDesc();
		    	
		    	if (Constants.RESULT_CODE_SUCCESS.equals(newResultVo.getResultCode())) {
		    		newPassword = newResultVo.resultDesc;
		    		//新旧密码均加密成功，修改密码
		    		userResult = userSpecService.modifyMemberPwd(memberCode,oldPassword, newPassword);
				}else {
					LogCvt.error("新登录密码加密解密失败："+newResultVo.getResultDesc());
					result.setCode(newResultVo.getResultCode());
					result.setMsg("新登录密码加密解密失败");
					return result;
				}
			}else {
				LogCvt.error("旧登录密码加密解密异常："+oldResultVo.getResultDesc());
				result.setCode(oldResultVo.getResultCode());
				result.setMsg("旧登录密码加密解密失败");
				return result;
			}
		}catch (TException e) {
			LogCvt.error("密码加密解密失败："+e);
			result.setCode(9999+"");
			result.setMsg("密码加密解密失败");
			return result;
		} catch (Exception e) {
			LogCvt.error("修改登录密码发生异常" + memberCode, e);
		}
		
		if(userResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{
			result.setSuccess(false);
			result.setCode(userResult.getMsgCode()+"");
			result.setMsg(userResult.getErrorMsg());
			return result;
		}
	}
	
	
	

	
	

	/**
	 * 绑定手机号
	 * @description 
	 * @author 
	 * @date 2015年5月19日 下午4:15:49
	 *
	 */
	public ResultBean updateUserMobile(Long memberCode, String mobile){
		ResultBean result = new ResultBean();
		
		if(StringUtil.isBlank(mobile)){
			result.setSuccess(false);
			result.setCode("9999");
			result.setMsg("手机号不能为空");
			return result;
		}
		

		UserResult userResult = new UserResult();
		try {
			userResult = userSpecService.bindMobile(memberCode,mobile);
		} catch (Exception e) {
			LogCvt.error("绑定手机号发生异常: memberCode" + memberCode, e);
		}
		
		
		if(userResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{
			result.setSuccess(false);
			result.setCode(userResult.getMsgCode()+"");
			result.setMsg(userResult.getErrorMsg());
			return result;
		}
	}
	
	
	

	/**
	 * 用户是否已设置安全问题
	 * @title isMemberSetQuestion
	 * @description 
	 * @author Liebert
	 * @date 2015年5月25日 下午5:24:17
	 * @param 
	 * @return boolean
	 */
	public boolean isMemberSetQuestion(Long memberCode){
		
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			accountResult = acctSpecService.isExitQuestion(memberCode);
		} catch (Exception e) {
			LogCvt.error("校验安全问题存在性发生系统异常", e);
		}
		
		if(accountResult.getResult()){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	/**
	 * 设置安全问题
	 * @description 
	 * @author 
	 * @date 2015年5月19日 下午2:35:30
	 *
	 */
	public ResultBean setMemberQuestion(Long memberCode, List<SafeQuestionPojo> safeQuestionPojoList){
		ResultBean result = new ResultBean();
		
		if(safeQuestionPojoList == null || safeQuestionPojoList.size() ==0 ){
			result.setSuccess(false);
			result.setCode("9999");
			result.setMsg("安全问题不能为空");
			return result;
		}
		
		List<QuestionSpecDto> questionList = new ArrayList<QuestionSpecDto>();
		for(SafeQuestionPojo safeQuestionPojo : safeQuestionPojoList){
			QuestionSpecDto questionDto = new QuestionSpecDto();
			questionDto.setQuestionID(safeQuestionPojo.getQuestionID());
			questionDto.setAnswer(safeQuestionPojo.getAnswer());
			questionDto.setCreateTime(new Date());
			questionList.add(questionDto);
		}
		
		
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			accountResult = acctSpecService.setMemberQuestion(memberCode, questionList);
		} catch (Exception e) {
			LogCvt.error("设置问题发生异常", e);
		}
		
		if(accountResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{
			result.setSuccess(false);
			result.setCode(accountResult.getErrorCode());
			result.setMsg(accountResult.getErrorMsg());
			return result;
		}
	}
	
	
	
	

	
	/**
	 * 删除用户设置的安全问题
	 * @param memberCode
	 * @param clientId
	 * @return
	 */
	public ResultBean disableMemberQuestion(Long memberCode){
		ResultBean result = new ResultBean();
		
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			accountResult = acctSpecService.disableMemberQuestion(memberCode);
		} catch (Exception e) {
			LogCvt.error("删除安全问题发生异常：" + memberCode, e);
		}
		
		if(accountResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{
			result.setSuccess(false);
			result.setCode(accountResult.getErrorCode());
			result.setMsg(accountResult.getErrorMsg());
			return result;
		}
	}
	
	
	

	
	/**
	 * 获取系统预设安全问题
	 * @description 
	 * @author 
	 * @date 2015年5月20日 上午10:16:53
	 *
	 */
	public Map<String,Object> selectPreinstallQuestion(int num){
		if(num == 0){
			num = 10;
		}
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			accountResult = acctSpecService.queryQuestion(num);
		} catch (Exception e) {
			LogCvt.error("查询预设问题发生异常", e);
		}
		
		if(accountResult.getResult() && accountResult.getQuestionSpecDtos() != null){
			List<QuestionPojo> questionList = new ArrayList<QuestionPojo>();
			List<QuestionSpecDto> questionDtoList = accountResult.getQuestionSpecDtos();
			if(questionDtoList.size() > 0){
				QuestionPojo pojo = null;
				for(QuestionSpecDto dto : questionDtoList){
					pojo = new QuestionPojo();
					pojo.setQuestionID(dto.getQuestionID());
					pojo.setQuestionName(dto.getQuestionName());
					questionList.add(pojo);
				}
			}
			resMap.put("questionList", questionList);
			return resMap;
		}else{
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "获取安全问题失败");
			return resMap;
		}
	}
	
	

	/**
	 * 获取会员设置的安全问题
	 * @description 
	 * @author 
	 * @date 2015年5月20日 上午10:17:17
	 *
	 */
	public Map<String,Object> getMemberQuestion(Long memberCode,int num){
		if(num == 0){
			num = 1;
		}
		Map<String,Object> resMap = new HashMap<String,Object>();

		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			accountResult = acctSpecService.queryMemberQuestion(memberCode);
		} catch (Exception e) {
			LogCvt.error("查询用户已设置的安全问题发生异常", e);
		}
		
		if(accountResult.getResult() && accountResult.getQuestionSpecDtos() != null){
			List<QuestionPojo> questionList = new ArrayList<QuestionPojo>();
			List<QuestionSpecDto> questionDtoList = accountResult.getQuestionSpecDtos();
			if(questionDtoList.size() > 0){
				QuestionPojo pojo = null;
				//如果用户设置的问题大于1条，而且前端只需要一条，就随机一条返回给前端
				if(num == 1 && questionDtoList.size()>1){
					QuestionSpecDto dto =questionDtoList.get((int)(Math.random()*questionDtoList.size()));
					pojo = new QuestionPojo();
					pojo.setQuestionID(dto.getQuestionID());
					pojo.setQuestionName(dto.getQuestionName());
					questionList.add(pojo);
					
				}else{
					for(QuestionSpecDto dto : questionDtoList){
						pojo = new QuestionPojo();
						pojo.setQuestionID(dto.getQuestionID());
						pojo.setQuestionName(dto.getQuestionName());
						questionList.add(pojo);
					}
				}
			}
			
			resMap.put("questionList", questionList);
			return resMap;
		}else{
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "获取安全问题失败");
			return resMap;
		}
		
	}
	
	
	
	
	
	
	/**
	 * 校验安全问题
	 * @description 
	 * @author 
	 * @date 2015年5月20日 上午10:18:20
	 *
	 */
	public ResultBean verifyMemberQuestion(Long memberCode, List<SafeQuestionPojo> safeQuestionPojoList){
		ResultBean result = new ResultBean();
		
		List<QuestionSpecDto> questionList = new ArrayList<QuestionSpecDto>();
		for(SafeQuestionPojo safeQuestionPojo : safeQuestionPojoList){
			QuestionSpecDto questionDto = new QuestionSpecDto();
			questionDto.setQuestionID(safeQuestionPojo.getQuestionID());
			questionDto.setAnswer(safeQuestionPojo.getAnswer());
			questionList.add(questionDto);
		}
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			accountResult = acctSpecService.veryfyPwdQuestion(memberCode, questionList);
		} catch (Exception e) {
			LogCvt.error("账户账务支付系统   校验安全问题发生系统异常", e);
		}
		
		if(accountResult.getResult()){
			result.setSuccess(true);
			return result;
		}else{
			result.setSuccess(false);
			result.setCode(accountResult.getErrorCode());
			
			//返回信息处理
			String errorMessage = null;
			try{
				JSONObject json = JSON.parseObject(accountResult.getDemo());
				errorMessage = json.getString("msg");
			}catch(Exception e){
				LogCvt.error("安全问题结果信息转换json异常",e);
				errorMessage = "安全问题答案错误";
			}
			
			result.setMsg(errorMessage);
			return result;
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * 校验Token
	 * @description 
	 * @author Liebert
	 * @date 2015年5月11日 上午10:08:39
	 * 
	 */
	public boolean verifyToken(String token, Long memberCode) {
		Result result = new Result(false);
		try{
			result = userSpecService.verifyToken(token, memberCode);
		}catch(Exception e){
			LogCvt.error("校验Token接口异常", e);
			
			//token校验异常统计
			Monitor.send(MonitorEnums.user_token_verify_exception, "1");
		}
		return result.getResult();
	}

	
	/**
	 * 注销token
	 *@description 
	 *@author Liebert
	 *@date 2015年4月21日 下午4:53:16
	 */
	public boolean invalidateToken(String token){
		Result result = new Result(false);
		try{
			result = userSpecService.logOff(token);
		}catch(Exception e){
			LogCvt.error("注销token接口异常", e);
		}
		return result.getResult();
	}
	
	
	
	/**
	 * 根据手机号或loginId查找用户
	 *@description 
	 *@author Liebert
	 *@date 2015年4月25日 下午3:00:56
	 */
	public UserSpecDto queryMemberByLoginID(String loginID){
		UserResult result = new UserResult();
		UserSpecDto user = null;
		try{
			result = userSpecService.queryMemberByLoginID(loginID);
		}catch(Exception e){
			LogCvt.error("查找用户接口异常", e);
			result.setResult(false);
			result.setMsgCode(9999);
			result.setErrorMsg("查找用户接口异常");
		}
		if(!result.getResult() || result.getUserList() == null || result.getUserList().size() == 0){
			LogCvt.info(String.format("用户查询失败:%s",JSONObject.toJSONString(result)));
		}else{
			user = result.getUserList().get(0);
		}
		return user;
	}
	
	
	
	
	/**
	 * 根据memberCode查找用户
	 *@description 
	 *@author Liebert
	 *@date 2015年4月25日 下午3:00:56
	 */
	public UserSpecDto queryMemberByMemberCode(Long memberCode){
		UserResult result = new UserResult();
		UserSpecDto user = null;
		try{
			result = userSpecService.queryMemberByMemberCode(memberCode);
		}catch(Exception e){
			LogCvt.error("查找用户接口异常", e);
			result.setResult(false);
			result.setMsgCode(9999);
			result.setErrorMsg("查找用户接口异常");
		}
		if(!result.getResult() || result.getUserList() == null || result.getUserList().size() == 0){
			LogCvt.info(String.format("用户查询失败:%s",JSONObject.toJSONString(result)));
		}else{
			user = result.getUserList().get(0);
		}
		return user;
	}
	
	
	/**
	 * 用户登录结果返回体封装
	 */
	public UserEnginePojo UserSpecToUserEngineDto(UserSpecDto userSpecDto, String clientId) {
		if (userSpecDto == null) {
			return null;
		}
		Long memberCode = userSpecDto.getMemberCode();
		//登录客户端渠道
		BankOrg clientBank = ClientChannelEnum.getClientBankOrg(clientId);
		//查询是否设置支付密码
		AccountResult paypwdResult = acctSpecService.isExistPayPwd(memberCode);
		//查询是否设置安全问题
		AccountResult quetionResult =  acctSpecService.isExitQuestion(memberCode);
		
		//查询会员等级
		String vipLevel = null;
		String vipStatus = null;
		
		//VIP信息
		List<VIPSpecDto> vips = userSpecDto.getVipSpecList();
		if(clientBank != null && vips != null && vips.size() > 0){
			for(VIPSpecDto vip : vips){
				if(clientBank.getBankOrg().equals(vip.getBankOrgNo())){
			    		vipLevel = vip.getVipLevel().getValue();
			    		vipStatus = vip.getVipStatus().getValue();
			    		break;
				}
			}
		}
		
		/*try {
			MemberInfoVo res = memberInformationService.selectUserByMemberCode(memberCode,clientId);
			//未开通过VIP为null
			if(res.getMemberVIPInfoVo() != null){
				vipLevel = res.getMemberVIPInfoVo().getVipLevel();
				vipStatus = res.getMemberVIPInfoVo().getVipStatus();
			}
		} catch (TException e) {
			LogCvt.error("查询用户VIP等级失败>> memberCode:" + memberCode,e);
		}*/
		
		//bank group id不为空则为已签约
		//新增对比bank id
		boolean signFlag = false;
		UserResult signResult = userSpecService.querySignByBankId(memberCode, clientBank.getBankID());
		if(signResult.getResult() && signResult.getMemberBankList() != null && signResult.getMemberBankList().size() > 0){
			signFlag = true;
		}
		
		
		String loginId = userSpecDto.getLoginID();
		if(userSpecDto.getLoginType() == LoginType.UNION.getValue()){
			//联合登陆隐藏身份证号码
			//安徽联合登陆用户名为身份证号码
			//重构联合登陆为手机号_o2o或o2obill随机
			if(loginId.indexOf("o2o") == -1){
				loginId = SensEncryptUtil.encryptIdentityNo(loginId);
			}
		}
		
		String email = userSpecDto.getEmail();
		if(userSpecDto.getIsBindEmail()){
			email = SensEncryptUtil.encryptEmail(email);
		}
		
		String mobile = userSpecDto.getMobile();
		if(userSpecDto.getIsBindMobile()){
			mobile = SensEncryptUtil.encryptMobile(mobile);
		}
		
		boolean isSimpleLoginPwd = false;
		if(UserLevel.NEEDRET.getValue().equals(userSpecDto.getServiceLevelCode())){
			isSimpleLoginPwd = true;
		}
		
		//判断是否白名单用户
		boolean isInWhiteList = isWhiteUser(clientId,memberCode);
		
		return new UserEnginePojo(memberCode, loginId, mobile, email, userSpecDto.getIsBindMobile(), paypwdResult.getResult(), quetionResult.getResult(), signFlag, vipLevel, vipStatus, isSimpleLoginPwd, isInWhiteList);
	}


	/**
	 * 判断是否白名单用户
	 * @param ckReqVo
	 * @return
	 * @date 2015年12月10日 下午3:19:02
	 */
	public boolean isWhiteUser(String clientId,long  memberCode) {
		//封装请求体
		CheckVIPExistWhiteListReqVo ckReqVo = new CheckVIPExistWhiteListReqVo();
		OriginVo originVo = new OriginVo();
		originVo.setClientId(clientId);
		ckReqVo.setMemberCode(memberCode);
		ckReqVo.setOrigin(originVo);
		//返回参数
		CheckVIPExistWhiteListRespVo ckResVo = new CheckVIPExistWhiteListRespVo();
		
		boolean isWhiteUser = false ;
		try {
			ckResVo = vipWhiteListService.checkVIPExistWhiteList(ckReqVo);
		} catch (TException e) {
			LogCvt.error("判断是否白名单用户接口异常", e);
		}
		if (ckResVo != null) {
			isWhiteUser = ckResVo.existWhiteList;
		}
		return isWhiteUser;
	}
	
	
	/**
	 * 用户申诉
	 * @param memberCode
	 * @param appeal
	 * @return
	 */
	public ResultBean memberAppeal(ClientChannel clientChannel, Long memberCode, String email, SafeAppealPojo appeal){
		ResultBean result = new ResultBean();
		MemberApplySpecDto apply = new MemberApplySpecDto();
		
		apply.setMemberCode(memberCode);
		apply.setYourName(appeal.getRealName());
		apply.setIdentifyType(appeal.getIdentifyType());
		apply.setIdentifyNo(appeal.getIdentifyNo());
		apply.setIdentifyPic(appeal.getIdentifyPic());
		apply.setMobile(appeal.getMobile());
		apply.setCardNo(appeal.getCardNo());
		apply.setDemo(appeal.getDemo());
		apply.setEmail(email);
		apply.setClientChannel(clientChannel.getValue());
		apply.setCreateTime(new Date());
		
		Result userResult = null;
		try{
			userResult = userOuterSpecService.saveApply(apply);
		}catch(Exception e){
			LogCvt.error("用户申诉服务异常",e);
			return new ResultBean();
		}
		
		if(userResult.getResult()){
			MemberApplySpecDto dto = (MemberApplySpecDto) userResult.getData();
			Integer stateCode  = dto.getState();
			result.setSuccess(true);
			result.setMsg(userResult.getMessage());
			result.setCode(stateCode+"");//code为状态码， 3为自动审核不通过
		}else{
			result.setSuccess(false);
			result.setMsg(userResult.getMessage());
			result.setCode("9999");
		}
		
		return result;
		
	}
	
	
	/**
	 * 获取cookie域 - .ubank365.com使用
	 * @description 
	 * @author Liebert
	 * @date 2015年5月11日 下午3:53:46
	 *
	 */
	public String getUbankTokenCookieDomain(HttpServletRequest request){
		String domain = COOKIE_DOMAIN;
    	/*String referer = request.getHeader(HttpHeaders.REFERER);
    	if(StringUtil.isBlank(referer)){
    		LogCvt.info(String.format("请求Header Referer为空：IP:%s", request.getRemoteAddr()));
    	}else{
    		String[] paths = referer.split("/");
        	if(paths.length < 3){
        		LogCvt.info(String.format("请求Header Referer异常：Referer:%s", referer));
        	}else{
        		//test.ubank365.com
        		//test.item.ubank365.com
        		//ubank365.com
        		//item.ubank365.com   秒杀
        		COOKIE_DOMAIN = paths[2];
        		int _index = COOKIE_DOMAIN.indexOf(".");
        		if(_index != -1){
        			COOKIE_DOMAIN = COOKIE_DOMAIN.substring(_index);
        		}
        		if(COOKIE_DOMAIN.indexOf(":") != -1){
        			COOKIE_DOMAIN = COOKIE_DOMAIN.substring(0,COOKIE_DOMAIN.indexOf(":"));
        		}
        	}
    	}*/
    	return domain;
	}
	
	
	
	/**
	 * 清除cookie - .ubank365.com使用
	 * @description 
	 * @author Liebert
	 * @date 2015年5月11日 下午3:54:04
	 *
	 */
	public void clearUbankTokenCookie(HttpServletRequest req, HttpServletResponse res){
		
		String COOKIE_DOMAIN = getUbankTokenCookieDomain(req);
    	
    	Cookie cookie = new Cookie(COOKIE_KEY,null);
		cookie.setMaxAge(0);
		cookie.setDomain(COOKIE_DOMAIN);
		cookie.setPath("/");
		res.addCookie(cookie);
	}
	
	
	
	/**
	 * getUserInfo:(根据用户标识获取用户的详细信息)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年8月26日 上午10:26:55
	 * @return
	 * 
	 */
	public UserEnginePojo getUserInfo(Long memberCode , String clientId) {
		UserSpecDto userSpecDto=this.queryMemberByMemberCode(memberCode);
		if(userSpecDto == null ){
			return null ;
		}
		//登录客户端渠道
		BankOrg clientBank = ClientChannelEnum.getClientBankOrg(clientId);
		//查询是否设置支付密码
		AccountResult paypwdResult = acctSpecService.isExistPayPwd(memberCode);
		//查询是否设置安全问题
		AccountResult quetionResult =  acctSpecService.isExitQuestion(memberCode);
		
		
		Map<String, Object> map = vipSupport.getMemberVipInfomation(memberCode, clientId);
		
		// 查询会员等级
		VIPLevel vipLevel = (VIPLevel) map.get("vipLevel") != null ? (VIPLevel) map.get("vipLevel") : null;
		VIPStatus vipStatus = (VIPStatus) map.get("vipStatus") != null ? (VIPStatus) map.get("vipStatus") : null;
		
		//在当前银行是否有签约
		boolean signFlag = false;
		UserResult signResult = userSpecService.querySignByBankId(memberCode, clientBank.getBankID());
		if(signResult.getResult() && signResult.getMemberBankList() != null && signResult.getMemberBankList().size() > 0){
			signFlag = true;
		}
		
		
		//联合登陆隐藏身份证号码
		String loginId = userSpecDto.getLoginID();
		if(userSpecDto.getLoginType() == LoginType.UNION.getValue()){
			if(loginId.indexOf("o2o") == -1){
				loginId = SensEncryptUtil.encryptIdentityNo(loginId);
			}
		}
		//隐藏邮箱
		String email = userSpecDto.getEmail();
		if(userSpecDto.getIsBindEmail()){
			email = SensEncryptUtil.encryptEmail(email);
		}
		//隐藏手机号
		String mobile = userSpecDto.getMobile();
		if(userSpecDto.getIsBindMobile()){
			mobile = SensEncryptUtil.encryptMobile(mobile);
		}

		//判断是否白名单用户
		CheckVIPExistWhiteListReqVo ckReqVo = new CheckVIPExistWhiteListReqVo();
		OriginVo originVo = new OriginVo();
		originVo.setClientId(clientId);
		ckReqVo.setMemberCode(memberCode);
		ckReqVo.setOrigin(originVo);
		
		//判断是否白名单用户
		boolean isInWhiteList = isWhiteUser(clientId,memberCode);
		
		UserEnginePojo user=new UserEnginePojo(memberCode, loginId, mobile, email,  userSpecDto.getIsBindMobile(), paypwdResult.getResult(), quetionResult.getResult(), signFlag, vipLevel.getValue(), vipStatus.getValue(), null, isInWhiteList);	
		return user;
	}
	
	
	
	/**
	 * 处理登录失败的错误提示信息
	 * @title handleLoginFailureMessage
	 * @description 
	 * @author Liebert
	 * @date 2015年5月25日 上午10:40:39
	 * @param 
	 * @return String
	 */
	public String getLoginFailureMessage(String demo,String defaultMsg){
		String message = "";
		
		if(StringUtil.isNotBlank(demo)){
			JSONObject obj = JSON.parseObject(demo);
			message = (String)obj.get("msg");
		}else{
			message = defaultMsg;
		}
		
		return message;
	}
	


	/**
	 * 
	 * @Description: 登录验证失败超过次数发送警告短信
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月21日 下午7:57:08
	 * @param demo
	 * @param clientId
	 * @param loginId
	 * @param ip
	 */
	public void handleLoginFailure(String demo, String clientId, String loginId, String ip){
		if(StringUtil.isNotBlank(demo)){
			JSONObject obj = JSON.parseObject(demo);
			int failureTime = obj.getInteger("failureTime");
			int loginLimit = obj.getInteger("loginLimit");
			if(failureTime == loginLimit){
				LogCvt.info("登录密码验证失败超过次数限制，发送提醒短信..");
				//查询会员
				UserSpecDto user = queryMemberByLoginID(loginId);
				if(user != null){
					if(user.getIsBindMobile()){
						boolean result = codeSupport.sendLoginPwdWarningSMS(clientId, loginId, user.getMobile(), ip);
						if(result){
							LogCvt.info("登录密码验证失败超过次数限制，发送提醒短信 : 成功");
						}else{
							LogCvt.info("登录密码验证失败超过次数限制，发送提醒短信 : 失败");
						}
					}else{
						LogCvt.info("用户未绑定手机号");
					}
				}
			}
		}
	}
	
	
	

	/**
	 * 
	 * @Description: 支付验证密码超过次数发送警告短信
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月21日 下午7:58:53
	 */
	public void handlePayPwdValidateFailure(String errorCode, String clientId, Long memberCode){
		if(StringUtil.isNotBlank(errorCode)){
			if(ErrorCodeType.PASSWORD_FIVE_LOCK.getValue().toString().equals(errorCode)){
				LogCvt.info("支付密码验证失败超过次数限制，发送提醒短信..");
				//查询会员
				UserSpecDto user = queryMemberByMemberCode(memberCode);
				if(user != null){
					if(user.getIsBindMobile()){
						boolean result = codeSupport.sendPayPwdWarningSMS(clientId, user.getLoginID(), user.getMobile(), null);
						if(result){
							LogCvt.info("支付密码验证失败超过次数限制，发送提醒短信 : 成功");
						}else{
							LogCvt.info("支付密码验证失败超过次数限制，发送提醒短信 : 失败");
						}
					}else{
						LogCvt.info("用户未绑定手机号");
					}
				}
			}
		}
	}
	
	/**
	 * 对PC密码进行rsa加密
	 * @param pwd
	 * @author 周煜涵
	 * @Date: 2016年1月6日 下午3:58:53
	 * @return
	 */
	public String encryptPwd(String pwd){
		ResultVo resultVo  = new ResultVo();
		String encodePassword = "";
		try {
			resultVo = encryptRSA(pwd);
			if (Constants.RESULT_CODE_SUCCESS.equals(resultVo.getResultCode())) {
				encodePassword = resultVo.getResultDesc();
			}else {
				LogCvt.info("[密码加密] >> 加密失败：" + resultVo.getResultDesc());
			}
		} catch (TException e) {
			e.printStackTrace();
			LogCvt.info("[密码加密] >> 加密异常：" + e);
		}
		return encodePassword;
	}
	
	/**
	 * 对密码进行rsa解密和des加密
	 * @author 周煜涵
	 * @Date: 2015年12月30日 上午9:58:53
	 * @param pwd
	 * @return
	 * @throws TException
	 */
	public ResultVo processor(String pwd) throws TException{
		long time = (new Date()).getTime();
		String clientChannel = String.valueOf(com.froad.thrift.vo.ClientId.common.getValue());
		String encryptType = String.valueOf(EncryptType.rsa.getValue());
		ResultVo resultVo  = new ResultVo();
		//新旧密码的解密加密
		resultVo = rsaPasswordService.processor(time, clientChannel, encryptType, pwd, "");
		return resultVo;
	}
	
	/**
	 * 对pwd进行rsa加密
	 * @param pwd
	 * @author 周煜涵
	 * @Date: 2016年1月6日 下午2:58:53
	 * @return
	 * @throws TException
	 */
	public ResultVo encryptRSA(String pwd) throws TException{
		long time = (new Date()).getTime();
		String clientChannel = String.valueOf(com.froad.thrift.vo.ClientId.common.getValue());
		String encryptType = String.valueOf(EncryptType.rsa.getValue());
		ResultVo resultVo  = new ResultVo();
		resultVo = encryptService.encryptRSA(time, clientChannel, encryptType, pwd, "");
		return resultVo;
	}
	
}
