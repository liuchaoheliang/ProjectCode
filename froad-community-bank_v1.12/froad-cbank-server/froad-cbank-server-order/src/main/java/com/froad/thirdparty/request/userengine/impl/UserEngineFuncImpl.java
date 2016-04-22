package com.froad.thirdparty.request.userengine.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.logback.LogCvt;
import com.froad.thirdparty.common.UserEngineCommend;
import com.froad.thirdparty.request.userengine.DESUtil;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.EsyT;
import com.pay.pe.dto.AccountResult;
import com.pay.pe.dto.QuestionSpecDto;
import com.pay.pe.service.AcctSpecService;
import com.pay.user.dto.MemberBankSpecDto;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.ContactType;
import com.pay.user.helper.VIPLevel;
import com.pay.user.service.UserSpecService;
import com.pay.user.service.VIPSpecService;

public class UserEngineFuncImpl implements UserEngineFunc {

	private static HessianProxyFactory factory = new HessianProxyFactory();

	//用户引擎服务地址
	private static String URL_PE = UserEngineCommend.USER_ENGINE_URL;
	
	//用户账户服务地址
	private static String URL_ACCOUNT = UserEngineCommend.PE_ACCT_URL;
	
	private static String VIP_URL = UserEngineCommend.VIP_USER_URL;
	

	public static void main(String[] args) {
		System.out.println(JSONObject.toJSONString(new UserEngineFuncImpl().queryByMemberCode(50000037488L)));
	}
	
	@Override
	public Result processVIPOrder(Long memberCode, VIPLevel vipLevel,Date vipExpirationTime, String bankLabelID, String bankOrg,String clientChannel) {
		Result result = new Result(false);
		try {
			VIPSpecDto vipSpecDto = new VIPSpecDto();
			vipSpecDto.setMemberCode(memberCode);
			vipSpecDto.setVipLevel(vipLevel);
			vipSpecDto.setVipExpirationTime(vipExpirationTime);;
			vipSpecDto.setBankLabelID(bankLabelID);
			vipSpecDto.setBankOrgNo(bankOrg);
			vipSpecDto.setClientChannel(clientChannel);
			result = ((VIPSpecService) factory.create(VIPSpecService.class, VIP_URL)).processVIPOrder(vipSpecDto);
		} catch (Exception e) {
			LogCvt.error("hessian异常: processVIPOrder", e);
		}
		LogCvt.debug("开通会员响应信息: " + result);
		return result;
	}
	
	@Override
	public Result cancelVIPOrder(Long memberCode, String bankOrg) {
		Result result = new Result(false);
		try {
			result = ((VIPSpecService) factory.create(VIPSpecService.class, VIP_URL)).cancelVIPOrder(memberCode,bankOrg);
		} catch (Exception e) {
			LogCvt.error("hessian异常: cancelVIPOrder", e);
		}
		return result;
	}
	
	@Override
	public Result synchBankLabel(String bankLabelID, String bankLabelName, Integer state,String clientId) {
		Result result = new Result(false);
		try {
			result = ((VIPSpecService) factory.create(VIPSpecService.class, VIP_URL)).synchBankLabel_v2(bankLabelID, bankLabelName,clientId);
		} catch (Exception e) {
			LogCvt.error("hessian异常: synchBankLabel", e);
		}
		return result;
	}
	
	@Override
	public UserResult queryByMemberCode(Long memberCode) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).queryMemberByMemberCode(memberCode);
			
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}
	
	@Override
	public UserResult queryByLoginID(String loginID) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).queryMemberByLoginID(loginID);
		} catch (Exception e) {
			LogCvt.error("hessian异常: loginID" + loginID, e);
		}
		return userResult;
	}
	
	@Override
	public Result queryVIPInfoByMemberCode(Long memberCode,String clientId) {
		Result result = new Result(false);
		try {
			result = ((VIPSpecService) factory.create(VIPSpecService.class, VIP_URL)).queryUserVIPInfo(memberCode,BaseSubassembly.acquireBankOrg(clientId));
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memberCode, e);
		}
		return result;
	}
	
	@Override
	public AccountResult verifyPayPwd(Long memberCode, String payPwd) {
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			AcctSpecService acctSpecService = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			String encodePassword = DESUtil.encrypt(payPwd);
			accountResult = acctSpecService.validationPayPwd(memberCode, encodePassword);
//			accountResult = acctSpecService.validationMemberAcctPwd(memberCode, Long.parseLong(memberCode + "10"), payPwd);
			LogCvt.info("账户账务系统  校验支付密码完成：" + JSONObject.toJSONString(accountResult));
		} catch (Exception e) {
			LogCvt.error("账户账务系统 校验改支付密码发生系统异常", e);
		}
		return accountResult;
	}
	
	@Override
	public AccountResult isUserSetPayPwd(Long memberCode) {
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			
			accountResult = service.isExistPayPwd(memberCode);
//			accountResult = service.isExitMemberAcctPwd(memberCode, Long.parseLong(memberCode + "10"));
			LogCvt.info("账户账务系统 查询用户支付密码存在性完成：" + JSONObject.toJSONString(accountResult));
		} catch (Exception e) {
			LogCvt.error("账户账务系统 查询用户支付密码存在性发生异常", e);
		}
		return accountResult;
	}
	
	@Override
	public AccountResult isUserSetQuestion(Long memberCode) {
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			accountResult = service.isExitQuestion(memberCode);
			LogCvt.info("账户账务系统  校验安全问题存在性完成：" + JSONObject.toJSONString(accountResult));
		} catch (Exception e) {
			LogCvt.error("账户账务系统 校验安全问题存在性发生系统异常", e);
		}
		return accountResult;
	}
	
	@Override
	public AccountResult selectPreinstallQuestion(int questionAmount) {
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			accountResult = service.queryQuestion(questionAmount);
			LogCvt.info("账户账务系统  查询预设问题完成：" + JSONObject.toJSONString(accountResult));
		} catch (Exception e) {
			LogCvt.error("账户账务系统 查询预设问题发生系统异常", e);
		}
		return accountResult;
	}
	
	@Override
	public AccountResult selectMemberSetQuestion(Long memberCode) {
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			accountResult = service.queryMemberQuestion(memberCode);
			LogCvt.info("账户账务支付系统  查询用户已设置的安全问题完成：" + JSONObject.toJSONString(accountResult));
		} catch (Exception e) {
			LogCvt.error("账户账务支付系统   查询用户已设置的安全问题发生系统异常", e);
		}
		return accountResult;
	}
	
	@Override
	public AccountResult veryfyMemberQuestion(Long memberCode, List<QuestionSpecDto> questions) {
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			accountResult = service.veryfyPwdQuestion(memberCode, questions);
			LogCvt.info("账户账务支付系统  校验安全问题完成：" + JSONObject.toJSONString(accountResult));
		} catch (Exception e) {
			LogCvt.error("账户账务支付系统   校验安全问题发生系统异常", e);
		}
		return accountResult;
	}
	
	public AccountResult setUserPayPwd(Long memberCode, String payPwd) {
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			String encodePassword = DESUtil.encrypt(payPwd);
			accountResult = service.resetPayPwd(memberCode, encodePassword);
//			accountResult = service.resetMemberAcctPwd(memberCode, Long.parseLong(memberCode + "10"), payPwd);
			LogCvt.info("账户账务支付系统  设置支付密码完成：" + JSONObject.toJSONString(accountResult));
		} catch (Exception e) {
			LogCvt.error("账户账务支付系统  设置支付密码发生系统异常", e);
		}
		return accountResult;
	}
	
	@Override
	public AccountResult setUserQuestion(Long memberCode, List<QuestionSpecDto> questions) {
		AccountResult accountResult = new AccountResult();
		accountResult.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			accountResult = service.setMemberQuestion(memberCode, questions);
			LogCvt.info("账户账务支付系统  设置问题完成：" + JSONObject.toJSONString(accountResult));
		} catch (Exception e) {
			LogCvt.error("账户账务支付系统   设置问题发生系统异常", e);
		}
		return accountResult;
	}
	
	@Override
	public UserResult getAllSignCardByMemberCode(Long memberCode) {
		UserResult userResult = new UserResult();
		userResult.setResult(false);
		try {
			UserSpecService service = (UserSpecService) factory.create(UserSpecService.class, URL_PE);
			userResult = service.querySignByMemberCode(memberCode);
			LogCvt.info("账户账务系统 查询快捷签约卡完成：" + JSONObject.toJSONString(userResult));
		} catch (Exception e) {
			LogCvt.error("账户账务系统 查询快捷签约卡异常", e);
		}
		return userResult;
	}
	
	@Override
	public UserResult signCard(String cardNo, String bankName, String uname, String idcard, String phone, Long memberCode,String agreementNo, String easyPayGroupId, String singlePenLimit, String dayLimit, String monthLimit) {
		UserResult userResult = new UserResult();
		userResult.setResult(false);
		try {
			UserSpecService service = (UserSpecService) factory.create(UserSpecService.class, URL_PE);
			MemberBankSpecDto memberBankSpecDto = new MemberBankSpecDto();
			memberBankSpecDto.setBankGroupId(easyPayGroupId);
			memberBankSpecDto.setCardNo(cardNo);
			memberBankSpecDto.setCardHostName(uname);
			memberBankSpecDto.setMobile(phone);
			memberBankSpecDto.setIdentifyNo(idcard);
			memberBankSpecDto.setMemberCode(memberCode);
			memberBankSpecDto.setAgreementNo(agreementNo);
			memberBankSpecDto.setOrderLimit(singlePenLimit);
			memberBankSpecDto.setDayLimit(dayLimit);
			memberBankSpecDto.setMonthLimit(monthLimit);
			memberBankSpecDto.setBankName(bankName);
			LogCvt.info("快捷签约-账户账务请求信息：" + JSONObject.toJSONString(memberBankSpecDto));
			userResult = service.cardSign(memberBankSpecDto);
			LogCvt.info("快捷签约-账户账务请求完成：" + JSONObject.toJSONString(userResult));
		} catch (Exception e) {
			LogCvt.error("账户账务系统 快捷签约异常", e);
		}
		return userResult;
	}
	
	@Override
	public UserResult cancelCardByDataID(Long memberBankID) {
		UserResult userResult = new UserResult();
		userResult.setResult(false);
		try {
			UserSpecService service = (UserSpecService) factory.create(UserSpecService.class, URL_PE);
			userResult = service.cardSignCancel(memberBankID);
			LogCvt.info("账户账务系统 解绑快捷签约卡完成：" + JSONObject.toJSONString(userResult));
		} catch (Exception e) {
			LogCvt.error("账户账务系统 解绑快捷签约卡异常", e);
		}
		return userResult;
	}
	
	
	
	@Override
	public UserResult setLimitCash(MemberBankSpecDto dto) {
		UserResult userResult = new UserResult();
		userResult.setResult(false);
		try {
			UserSpecService service = (UserSpecService) factory.create(UserSpecService.class, URL_PE);
			userResult = service.modifyPayLimit(dto);
			LogCvt.info("账户账务系统   设置限额完成：" + JSONObject.toJSONString(userResult));
		} catch (Exception e) {
			LogCvt.error("账户账务系统   设置限额发生系统异常", e);
		}
		return userResult;
	}
	
	@Override
	public UserResult updateMemberPwd(Long memberCode, String oldPwd, String newPwd) {
		UserResult userResult = new UserResult();
		try {
		    String oldPassword = DESUtil.encrypt(oldPwd);
		    String newPassword = DESUtil.encrypt(newPwd);
			userResult = ((UserSpecService)factory.create(UserSpecService.class, URL_PE)).modifyMemberPwd(memberCode,oldPassword, newPassword);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}
	
	public UserResult resetUserPwd(Long memCode, String password) {
		UserResult userResult = new UserResult();
		try {
		    String encodePassword = DESUtil.encrypt(password);
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).resetMemberPwd(memCode,encodePassword);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memCode, e);
		}
		return userResult;
	}
	
	@Override
	public Result loginUnion(String mobile,String idnetifyNo, String userBankId, String createChannel,BankOrg bankOrg,String identifyType) {
		Result result = new Result(false);
		try {
			result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).loginUnion(mobile, identifyType/**类型指明: 身份证*/, idnetifyNo, userBankId, createChannel, bankOrg == null ? null : bankOrg.getBankOrg());
		} catch (Exception e) {
			LogCvt.error("hessian异常");
		}
		return result;
	}
	
	@Override
	public UserResult bindDefaultCard(Long cardId, Long memberCode) {
		UserResult userResult = new UserResult();
		userResult.setResult(false);
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).bindDefaultCard(cardId, memberCode);
		} catch (Exception e) {
			LogCvt.error("hessian异常");
		}
		return userResult;
	}
	
	@Override
	public Result oauth(String openId, String nickName, String oauthType, Long memberCode) {
		Result result = new Result(false);
		try {
			result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).oauth(openId, nickName,oauthType, memberCode);
		} catch (Exception e) {
			LogCvt.error("hessian异常: openId: " + openId + "|oauthType: " + oauthType + "|memberCode: " + memberCode, e);
		}
		return result;
	}
	
	
	
	@Override
	public UserResult registerMember(UserSpecDto userSpecDto) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).registerMember(userSpecDto);
		} catch (Exception e) {
			LogCvt.error("hessian异常: " + JSONObject.toJSONString(userSpecDto), e);
		}
		return userResult;
	}

	@Override
	public UserResult updateMemberInfoByMemberCode(UserSpecDto userSpecDto) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE))
					.updateMemberInfo(userSpecDto);
		} catch (Exception e) {
			LogCvt.error("hessian异常: " + JSONObject.toJSONString(userSpecDto), e);
		}
		return userResult;
	}

	@Override
	public UserResult login(String loginID, String loginPWD, String loginIP) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).login(loginID, loginPWD,
					loginIP);
		} catch (Exception e) {
			LogCvt.error("hessian异常: loginID: " + loginID + "|loginPWD: " + loginPWD + "|loginIP: " + loginIP, e);
		}
		return userResult;
	}

	@Override
	public UserResult forgetPwd(Long memberCode) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).forgetPwd(memberCode,
					ContactType.MOBILE);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}

	@Override
	public UserResult bindMobile(Long memberCode, String mobile) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).bindMobile(memberCode,
					mobile);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}
	
	/**
	 * 绑定邮箱
	 * @param memberCode
	 * @param email
	 * @return    
	 * @see com.froad.thirdparty.request.userengine.UserEngineFunc#bindMail(java.lang.Long, java.lang.String)
	 */
	@Override
	public UserResult bindMail(Long memberCode, String email) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).bindMail(memberCode,
					email);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}
	
	/**     
	 * 解绑会员手机
	 * @param memberCode
	 * @return    
	 * @see com.froad.thirdparty.request.userengine.UserEngineFunc#unBindMobile(java.lang.Long)    
	 */	
	@Override
	public UserResult unBindMobile(Long memberCode) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).unBindMobile(memberCode);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}

	
	/**     
	 * 解绑会员邮箱
	 * @param memberCode
	 * @return    
	 * @see com.froad.thirdparty.request.userengine.UserEngineFunc#unBindMail(java.lang.Long)    
	 */	
	@Override
	public UserResult unBindMail(Long memberCode) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).unBindMail(memberCode);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode" + memberCode, e);
		}
		return userResult;
	}

	@Override
	public UserResult updateMemberInfo(UserSpecDto userSpecDto) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE))
					.updateMemberInfo(userSpecDto);
		} catch (Exception e) {
			LogCvt.error("hessian异常: " + JSONObject.toJSONString(userSpecDto), e);
		}
		return userResult;
	}
	
	@Override
	public UserResult forgetPwdToReset(UserSpecDto user) {
		UserResult userResult = new UserResult();
//		try {
//			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE))
//					.forgetPwdWithChannel(user.getMemberCode(), user.g, channel);
//		} catch (Exception e) {
//			LogCvt.error("hessian异常: " + JSONObject.toJSONString(userSpecDto), e);
//		}
		return userResult;
	}

	

	@Override
	public UserResult bankCardAuth(MemberBankSpecDto memberBank) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).bankCardAuth(memberBank);
		} catch (Exception e) {
			LogCvt.error("hessian异常: " + JSONObject.toJSONString(userResult), e);
		}
		return userResult;
	}

	public UserResult bindMemberBank(MemberBankSpecDto memberBank) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).bindMemberBank(memberBank);
		} catch (Exception e) {
			LogCvt.error("hessian异常: " + JSONObject.toJSONString(userResult), e);
		}
		return userResult;
	}

	public UserResult queryMemberBankInfo(Long memberCode) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE))
					.queryMemberBankInfo(memberCode);
			if (!userResult.getResult()) {
				LogCvt.info("银行列表查询失败，返回消息：" + JSONObject.toJSONString(userResult));
			}
		} catch (Exception e) {
			LogCvt.error("hessian异常", e);
		}
		return userResult;
	}

	public UserResult removeMemberBank(Long id, Long memberCode) {
		UserResult userResult = new UserResult();
		try {
			userResult = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).removeMemberBank(id,
					memberCode);
		} catch (Exception e) {
			LogCvt.error("hessian异常: " + JSONObject.toJSONString(userResult), e);
		}
		return userResult;
	}


	@Override
	public AccountResult updateUserPayPwd(Long memberCode, String payPwd, String payPwdNew) {
		AccountResult ar = new AccountResult();
		ar.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			String oldPassword = DESUtil.encrypt(payPwd);
			String newPassword = DESUtil.encrypt(payPwdNew);
			ar = service.modifyPayPwd(memberCode,oldPassword, newPassword);
//			ar = service.modifyMemberAcctPwd(memberCode, Long.parseLong(memberCode + "10"), payPwd, payPwdNew);
			LogCvt.info("账户账务支付系统  修改支付密码完成：" + JSONObject.toJSONString(ar));
		} catch (Exception e) {
			LogCvt.error("账户账务支付系统  修改支付密码发生系统异常", e);
		}
		return ar;
	}


	@Override
	public Result disableOauth(String openId, String oauthType) {
		Result result = new Result(false);
		try {
			result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).disableOauth(openId, oauthType);
		} catch (Exception e) {
			LogCvt.error("hessian异常: openId: " + openId + "|oauthType: " + oauthType, e);
		}
		return result;
	}

	@Override
	public UserResult queryMemberOauth(Long memberCode, String oauthType) {

		UserResult result = new UserResult();
		try {
			result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).queryMemberOauth(memberCode,
					oauthType);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode: " + memberCode + "|oauthType: " + oauthType, e);
		}
		return result;
	}

	@Override
	public Result synchroName(Long memberCode, String oauthType, String nickName) {
		Result result = new Result(false);
		try {
			result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).synchroName(memberCode, oauthType, nickName);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode: " + memberCode + "|oauthType: " + oauthType + "|nickName: " + nickName, e);
		}
		return result;
	}

	@Override
	public UserResult forgetPwdNew(Long memberCode, String SMSSuffix) {
        //验证通过，重置密码
        UserResult result = new UserResult();
		try {
			result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).forgetPwdNew(memberCode, ContactType.MOBILE, SMSSuffix);
		} catch (Exception e) {
			LogCvt.error("hessian异常: memberCode: " + memberCode + "|SMSSuffix: " + SMSSuffix , e);
		}
		return result;
	}
	
	@Override
	public UserResult queryMemberAndCardInfo(String cardNo,String bankOrgNo){
        //验证通过，重置密码
        UserResult result = new UserResult();
        try {
            result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).querySignByCardNo(cardNo, bankOrgNo);
        } catch (Exception e) {
            LogCvt.error("hessian异常: cardNo: " + cardNo + "|bankOrgNo: " + bankOrgNo , e);
        }
	    return result;
	}
	
	@Override
	public Result processVIPOrder(Long memeberCode, VIPLevel level, Integer availableDays, Long labelID, Integer bankLevel){
        Result result = new Result(false);
        try {
            result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).processVIPOrder(memeberCode, level, availableDays, labelID, bankLevel);
        } catch (Exception e) {
            LogCvt.error("hessian异常：memberCode:" + memeberCode + ", level:"+level+", availableDays:"+availableDays+", labelID:"+labelID+", bankLevel:"+bankLevel  , e);
        }
	    return result;
	}
	
	@Override
	public Result querySecondaryBankListByBankID(Long bankID) {
        Result result = new Result(false);
        try {
            result = ((UserSpecService) factory.create(UserSpecService.class, URL_PE)).querySecondaryBankListByBankID(bankID);
        } catch (Exception e) {
            LogCvt.error("hessian异常：bank_id:"+bankID , e);
        }
	    return result;
	}

	@Override
	public AccountResult deleteUserSettedQuestion(long memberCode) {
		AccountResult result = new AccountResult();
		result.setResult(false);
		try {
			AcctSpecService service = (AcctSpecService) factory.create(AcctSpecService.class, URL_ACCOUNT);
			result = service.disableMemberQuestion(memberCode);
		} catch (Exception e) {
			
		}
		return result;
	}
}