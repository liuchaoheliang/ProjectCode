package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.FailsCountEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProcessTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.util.BankHandle;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankLoginFailsCountReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.LoginReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleReq;
import com.froad.thrift.service.BankAuditService;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.FallowQueryService;
import com.froad.thrift.service.FinityRoleService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OrgUserRoleService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.GetPendAuitCountReqVo;
import com.froad.thrift.vo.GetPendAuitCountResVo;
import com.froad.thrift.vo.LoginBankOperatorVoRes;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.thrift.vo.finity.FinityRoleVo;

/**
 * 银行登录
 * 
 */
@Service
public class LoginService {

	@Resource
	BankOperatorService.Iface bankOperatorService;
	@Resource
	SmsMessageService.Iface smsMessageService;
	@Resource
	OrgService.Iface orgService;
	@Resource
	BankAuditService.Iface bankAuditService;
	@Resource
	FinitRoleService finitRoleService;
	@Resource
	FinityRoleService.Iface finityRoleService;
	@Resource
	OrgUserRoleService.Iface orgUserRoleService;
	@Resource
	FallowQueryService.Iface fallowQueryService;

	/**
	 * 登录验证(银行管理平台用户登录)
	 * 
	 * @param loginName
	 * @param loginPassword
	 * @param identifyCode
	 * @return
	 * @throws OperatorException
	 * @throws TException
	 * @throws DecoderException
	 */
	public HashMap<String, Object> loginValidate(LoginReq req, OriginVo vo)
			throws BankException, TException, DecoderException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		int count = 0;
		LoginBankOperatorVoRes failureCount = bankOperatorService.getLoginFailureCount(vo, req.getUserName());
		LogCvt.info("====>获取登录错误次数返回数据<====:" + JSON.toJSONString(failureCount));
		/**
		 * 新增校验码逻辑
		 */
		if (null != failureCount) {
			count = failureCount.getLoginFailureCount();
		}

		if (count >= 5) {
			// 校验验证码
			if (StringUtil.isBlank(req.getCode())) {
				reMap.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				reMap.put(ResultEnum.MESSAGE.getCode(), "验证码不能为空");
				return reMap;
			}
			// 图片验证码
			ResultVo res = smsMessageService.verifyMobileToken(req.getToken(), req.getCode());
			if (!res.getResultCode().equals(EnumTypes.success.getCode())) {
				reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.verify_code.getCode());
				reMap.put(ResultEnum.MESSAGE.getCode(), EnumTypes.verify_code.getMessage());
				return reMap;
			}
		}
		// 小于5次不校验
		String password = TargetObjectFormat.getPassword(req.getPassword());
		LogCvt.info("=====银行管理平台登录请求参数======service:" + "username:" + req.getUserName() + " ;password:" + password
				+ JSON.toJSONString(vo));
		LoginBankOperatorVoRes res = bankOperatorService.loginBankOperator(vo, req.getUserName(), password);
		LogCvt.info("=====银行管理平台登录请求参数======server:" + JSON.toJSONString(res));
		if (res.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
			// 登录成功后
			doAndSuccess(req, reMap, res);
		} else {
			// 登录失败,将错误次数增加一次
			reMap.put(ResultEnum.FAIL.getCode(), res.getResult().getResultCode());
			reMap.put("loginFailureCount", count + 1);
			reMap.put(ResultEnum.MESSAGE.getCode(), res.getResult().getResultDesc());
		}

		return reMap;
	}

	/**
	 * 
	 * doAndSuccess:登录成功返回信息封装
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 上午11:02:37
	 * @param req
	 * @param reMap
	 * @param res
	 * @throws TException
	 * @throws BankException
	 *
	 */
	private void doAndSuccess(LoginReq req, HashMap<String, Object> reMap, LoginBankOperatorVoRes res)
			throws TException, BankException {
		reMap.put("orgCode", res.getBankOperator().getOrgCode());
		/** 添加返回tag逻辑 */
		FinityRoleVo finityRoleVo = new FinityRoleVo();
		finityRoleVo.setId(res.getBankOperator().getRoleId());
		finityRoleVo.setStatus(true);
		finityRoleVo.setIsDelete(false);
		List<FinityRoleVo> list = finityRoleService.getFinityRole(finityRoleVo);
		String roleTag = "";
		if (null != list && list.size() > 0) {
			FinityRoleVo roleVo = list.get(0);
			if (null != roleVo) {
				roleTag = roleVo.getTag();
				reMap.put("tag", roleVo.getTag());
			} else {
				// reMap.put("code", EnumTypes.fail.getCode());
				// reMap.put("message", "登录异常");
				// return reMap;
				throw new BankException(EnumTypes.fail.getCode(), "登录异常");
			}
		}
		RoleReq role = new RoleReq();
		role.setClientId(req.getClientId());
		role.setRoleId(res.getBankOperator().getRoleId());

		reMap.put("code", res.getResult().getResultCode());
		OrgVo org = orgService.getOrgById(req.getClientId(), res.getBankOperator().getOrgCode());
		if (org != null) {
			reMap.put("orgName", org.getOrgName());
			reMap.put("orgLevel", org.getOrgLevel());
		}
		reMap.putAll(finitRoleService.getResourceList(role, reMap.get("orgLevel"), "login", roleTag));
		reMap.put("loginName", res.getBankOperator().getUsername());
		reMap.put("token", res.getToken());
		reMap.put("isReset", res.getBankOperator().isReset ? "1" : "0");
		reMap.put("orgCode", res.getBankOperator().getOrgCode());
		reMap.put("userId", res.getBankOperator().getId());
		OrgVo orgVo = new OrgVo();
		orgVo.setClientId(req.getClientId());
		orgVo.setOrgLevel("1");
		List<OrgVo> orgList = orgService.getOrg(orgVo);
		if (orgList != null && orgList.size() > 0) {
			OrgVo orgVoRes = orgList.get(0);
			reMap.put("proOrgCode", orgVoRes.getOrgCode());
		}
	}

	/**
	 * 登录验证(银行用户登录)
	 * 
	 * @param loginName
	 * @param loginPassword
	 * @param identifyCode
	 * @return
	 * @throws OperatorException
	 * @throws TException
	 * @throws DecoderException
	 */
	public HashMap<String, Object> bankUserloginValidate(LoginReq req, OriginVo vo)
			throws BankException, TException, DecoderException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		/**
		 * 新增校验码逻辑
		 */
		int count = 0;
		LoginBankOperatorVoRes failureCount = orgUserRoleService.getLoginFailureCount(vo, req.getUserName());
		LogCvt.info("====>获取登录错误次数返回数据<====:" + JSON.toJSONString(failureCount));
		if (null != failureCount) {
			count = failureCount.getLoginFailureCount();
		}
		if (count >= 5) {
			// 校验验证码
			if (StringUtil.isBlank(req.getCode())) {
				reMap.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				reMap.put(ResultEnum.MESSAGE.getCode(), "验证码不能为空");
				return reMap;
			}
			// 图片验证码
			ResultVo res = smsMessageService.verifyMobileToken(req.getToken(), req.getCode());
			if (!res.getResultCode().equals(EnumTypes.success.getCode())) {
				reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.verify_code.getCode());
				reMap.put(ResultEnum.MESSAGE.getCode(), EnumTypes.verify_code.getMessage());
				return reMap;
			}
		}
		// String password =
		// TargetObjectFormat.getPassword(req.getPassword());
		String password = req.getPassword();
		// LoginBankOperatorVoRes res =
		// bankOperatorService.loginBankOperator(vo,req.getUserName(),
		// password);
		LoginBankOperatorVoRes loginBankOperatorVoRes = orgUserRoleService.loginOrgUserRole(vo, req.getUserName(),
				password);
		if (LogCvt.isDebugEnabled()) {
			LogCvt.debug("登录角色返回:" + JSON.toJSONString(loginBankOperatorVoRes));
		}
		if (loginBankOperatorVoRes.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
			// 登录成功
			doWithSuccess(req, reMap, loginBankOperatorVoRes);
		} else {
			// 登录失败,将错误次数增加一次
			reMap.put(ResultEnum.FAIL.getCode(), loginBankOperatorVoRes.getResult().getResultCode());
			reMap.put("loginFailureCount", count + 1);
			reMap.put(ResultEnum.MESSAGE.getCode(), loginBankOperatorVoRes.getResult().getResultDesc());
		}
		return reMap;
	}

	/**
	 * 
	 * doWithSuccess:登录成功返回信息封装
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 上午11:02:12
	 * @param req
	 * @param reMap
	 * @param loginBankOperatorVoRes
	 * @throws TException
	 * @throws BankException
	 *
	 */
	private void doWithSuccess(LoginReq req, HashMap<String, Object> reMap,
			LoginBankOperatorVoRes loginBankOperatorVoRes) throws TException, BankException {
		/** 添加返回tag逻辑 */
		FinityRoleVo finityRoleVo = new FinityRoleVo();
		finityRoleVo.setId(loginBankOperatorVoRes.getBankOperator().getRoleId());
		finityRoleVo.setStatus(true);
		finityRoleVo.setIsDelete(false);
		List<FinityRoleVo> list = finityRoleService.getFinityRole(finityRoleVo);
		String roleTag = "";
		if (null != list && list.size() > 0) {
			FinityRoleVo roleVo = list.get(0);
			if (null != roleVo) {
				roleTag = roleVo.getTag();
				reMap.put("tag", roleVo.getTag());
			} else {
				// reMap.put("code", EnumTypes.fail.getCode());
				// reMap.put("message", "登录异常");
				// return reMap;
				throw new BankException(EnumTypes.fail.getCode(), "银行用户登录异常");
			}
		}
		RoleReq role = new RoleReq();
		role.setClientId(req.getClientId());
		role.setRoleId(loginBankOperatorVoRes.getBankOperator().getRoleId());

		reMap.put("code", loginBankOperatorVoRes.getResult().getResultCode());
		OrgVo org = orgService.getOrgById(req.getClientId(), loginBankOperatorVoRes.getBankOperator().getOrgCode());
		if (org != null) {
			reMap.put("orgName", org.getOrgName());
			reMap.put("orgLevel", org.getOrgLevel());
		}
		reMap.putAll(finitRoleService.getResourceList(role, reMap.get("orgLevel"), "login", roleTag));
		reMap.put("loginName", loginBankOperatorVoRes.getBankOperator().getUsername());
		reMap.put("token", loginBankOperatorVoRes.getToken());
		reMap.put("bankUserLoginFlag", req.getLoginChannelFlag() == null || "".equals(req.getLoginChannelFlag()) ? "1"
				: req.getLoginChannelFlag());
		reMap.put("isReset", "");
		reMap.put("orgCode", loginBankOperatorVoRes.getBankOperator().getOrgCode());
		reMap.put("userId", loginBankOperatorVoRes.getBankOperator().getId());
		OrgVo orgVo = new OrgVo();
		orgVo.setClientId(req.getClientId());
		orgVo.setOrgLevel("1");
		List<OrgVo> orgList = orgService.getOrg(orgVo);
		if (orgList != null && orgList.size() > 0) {
			OrgVo orgVoRes = orgList.get(0);
			reMap.put("proOrgCode", orgVoRes.getOrgCode());
		}
	}

	/**
	 * 用户退出
	 * 
	 * @param token
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> operatorLogout(String token) throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		ResultVo result = bankOperatorService.logoutBankOperator(token);
		reMap.put("code", result.getResultCode());
		reMap.put("message", result.getResultDesc());
		return reMap;
	}

	/**
	 * 待审核数量
	 * 
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> getPreAuditNum(String clientId, String orgCode, OriginVo originVo)
			throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		GetPendAuitCountReqVo reqVo = new GetPendAuitCountReqVo();
		// 待审核商户
		originVo.setOrgId(orgCode);
		reqVo.setOrigin(originVo);

		reqVo.setProcessType(ProcessTypeEnum.MERCHANT.getCode());
		GetPendAuitCountResVo merchantCountResVo = fallowQueryService.getPendAuitCount(reqVo);
		reMap.put("preAuditMerchant", merchantCountResVo.getCount());

		reqVo.setProcessType(ProcessTypeEnum.OUTLET.getCode());
		GetPendAuitCountResVo outletCountResVo = fallowQueryService.getPendAuitCount(reqVo);
		reMap.put("preAuditOutlet", outletCountResVo.getCount());

		reqVo.setProcessType(ProcessTypeEnum.GROUPPRODUCT.getCode());
		GetPendAuitCountResVo groupProductVo = fallowQueryService
				.getPendAuitCount(reqVo);
		reMap.put("preAuditGroup", groupProductVo.getCount());
		return reMap;
	}

	/**
	 * 安全中心，修改密码
	 * 
	 * @throws TException
	 */
	public HashMap<String, Object> loginPasswordUpdate(HttpServletRequest req, String userId, String clientId,
			String oldPassword, String newPassword) throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		BankOperatorVo bankOperator = bankOperatorService.getBankOperatorById(clientId, Long.valueOf(userId));
		if (StringUtil.equals(bankOperator.getPassword(), TargetObjectFormat.getPassword(oldPassword))) {
			if (!StringUtil.equals(TargetObjectFormat.getPassword(oldPassword),
					TargetObjectFormat.getPassword(newPassword))) {
				bankOperator.setPassword(TargetObjectFormat.getPassword(newPassword));
				bankOperator.setIsReset(true);
				ResultVo result = bankOperatorService.updateBankOperator(this.getOriginVo(req), bankOperator);
				if (!result.getResultCode().equals(EnumTypes.success.getCode())) {
					reMap.put("code", EnumTypes.fail.getCode());
					reMap.put("isReset", false);
					reMap.put("message", result.getResultDesc());
				} else {
					reMap.put("isReset", true);
					reMap.put("message", "密码修改成功，请重新登录");
				}
			} else {
				reMap.put("code", EnumTypes.fail.getCode());
				reMap.put("message", "新旧密码不能一致");
			}

		} else {
			reMap.put("code", EnumTypes.fail.getCode());
			reMap.put("message", "原登录密码不正确");
		}
		return reMap;
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 * @throws TException
	 * @throws BankException
	 */
	public HashMap<String, Object> getIdentifyCode(String clientId) throws TException, BankException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		SmsMessageVo smVo = new SmsMessageVo();
		smVo.setSmsType(-1);
		smVo.setClientId(clientId);
		SmsMessageResponseVo res = smsMessageService.createImgage(smVo);
		LogCvt.info("获取验证码后端返回信息:" + JSON.toJSONString(res));
		if (res.getResultCode().equals(EnumTypes.success.getCode())) {
			reMap.put("codeUrl", res.getUrl());
			reMap.put("token", res.getToken());
		} else {
			throw new BankException(res.resultCode, res.resultDesc);
		}
		return reMap;
	}

	/**
	 * 验证验证码
	 * 
	 * @param token
	 * @param code
	 * @return
	 * @throws TException
	 */
	// public HashMap<String,Object> verifyToken(String token,String code)
	// throws TException{
	// HashMap<String,Object> reMap = new HashMap<String, Object>();
	// ResultVo res = smsMessageService.verifyMobileToken(token, code);
	// reMap.put("code", res.getResultCode());
	// reMap.put("message", res.getResultDesc());
	// return reMap;
	// }

	/**
	 * 日志监控
	 * 
	 * @param req
	 * @return
	 */
	public OriginVo getOriginVo(HttpServletRequest req) {
		OriginVo vo = new OriginVo();
		vo.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
		if (StringUtil.isNotEmpty(req.getAttribute(Constants.USER_ID) + "")) {
			vo.setOperatorId(Long.valueOf(req.getAttribute(Constants.USER_ID) + ""));
		}
		vo.setOperatorIp(TargetObjectFormat.getIpAddr(req));
		vo.setPlatType(PlatType.bank);
		return vo;
	}

	public BankOperatorVo getBankOperatorVo(HttpServletRequest req) throws Exception {
		BankOperatorVo bankOperatorVo = null;
		if (StringUtil.isNotEmpty(req.getAttribute(Constants.USER_ID) + "")) {
			bankOperatorVo = bankOperatorService.getBankOperatorById(req.getAttribute(Constants.CLIENT_ID) + "",
					Long.valueOf(req.getAttribute(Constants.USER_ID) + ""));
		}
		return bankOperatorVo;
	}

	public OriginVo getOrigin(HttpServletRequest req) throws NumberFormatException, TException {
		OriginVo originVo = new OriginVo();
		originVo.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
		originVo.setOperatorIp(TargetObjectFormat.getIpAddr(req));
		originVo.setPlatType(PlatType.bank);
		if (StringUtil.isNotEmpty(req.getAttribute(Constants.USER_ID) + "")) {
			BankOperatorVo bankOperatorVo = bankOperatorService.getBankOperatorById(
					req.getAttribute(Constants.CLIENT_ID) + "", Long.valueOf(req.getAttribute(Constants.USER_ID) + ""));
			originVo.setOperatorId(bankOperatorVo.getId());
			originVo.setOperatorUserName(bankOperatorVo.getUsername());
			originVo.setOrgId(bankOperatorVo.getOrgCode());
			originVo.setRoleId(String.valueOf(bankOperatorVo.getRoleId()));
		}
		return originVo;

	}

	/**
	 * 
	 * getFailsCount:获取登录错误统计次数
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月17日 下午12:08:34
	 * @param count_KEY
	 * @return
	 * @throws TException
	 *
	 */
	public Map<String, Object> getFailsCount(HttpServletRequest request, BankLoginFailsCountReq reqVo)
			throws TException {
		LogCvt.info("用户登录错误次数请求参数:" + JSON.toJSONString(reqVo));
		int count = 0;
		LoginBankOperatorVoRes loginFailureCount;
		Map<String, Object> resMap = new HashMap<String, Object>();
		OriginVo originVo = BankHandle.getOriginVo4FailsCount(request);
		// 区分普通登录和联合登录
		if (FailsCountEnum.VE.getCode().equals(reqVo.getType())) {
			loginFailureCount = bankOperatorService.getLoginFailureCount(originVo, reqVo.getUserName());
		} else if (FailsCountEnum.bankVe.getCode().equals(reqVo.getType())) {
			loginFailureCount = orgUserRoleService.getLoginFailureCount(originVo, reqVo.getUserName());
		} else {
			resMap.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			resMap.put(ResultEnum.MESSAGE.getCode(), "登录类型非法");
			return resMap;
		}
		LogCvt.info("用户登录错误次数返回数据:" + JSON.toJSONString(loginFailureCount));
		if (null != loginFailureCount) {
			ResultVo resultVo = loginFailureCount.getResult();
			if (resultVo.getResultCode().equals(ResultEnum.SUCCESS.getDescrition())
					|| resultVo.getResultCode()
							.equals(FailsCountEnum.NOT_EXIST.getCode())) {
				count = loginFailureCount.getLoginFailureCount();
				resMap.put("loginFailureCount", count);
				// 如果用户不存在,也视为成功状态
				resMap.put(ResultEnum.SUCCESS.getCode(),
						ResultEnum.SUCCESS.getDescrition());
				resMap.put(ResultEnum.MESSAGE.getCode(), resultVo.getResultDesc());
			}
		} else {
			resMap.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			resMap.put(ResultEnum.MESSAGE.getCode(), "获取登录错误统计次数异常");
		}
		return resMap;
	}
}
