package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankLoginFailsCountReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.LoginReq;

/**
 * 用户登录
 * 
 * @author ylchu
 *
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BasicSpringController {

	@Resource
	private LoginService loginService;

	/**
	 * 获取登录错误统计次数
	 * 
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/getFailsCount", method = RequestMethod.GET)
	public void getFailsCount(ModelMap model, HttpServletRequest request, BankLoginFailsCountReq reqVo) {
		try {
			model.clear();
			if (!StringUtil.isNotBlank(reqVo.getUserName())) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), "用户名不能为空");
				return;
			}
			if (!StringUtil.isNotBlank(reqVo.getType())) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), "登录方式不能为空");
				return;
			}
			model.putAll(loginService.getFailsCount(request, reqVo));
		} catch (Exception e) {
			LogCvt.info("获取登录错误统计次数异常" + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 登录（银行管理平台账号登录）
	 * 
	 * @param model
	 * @param req
	 * @throws IOException
	 */
	@RequestMapping(value = "/ve", method = RequestMethod.POST)
	public void loginValidate(HttpServletRequest req, HttpServletResponse res, @RequestBody LoginReq loginReq)
			throws IOException {
		LogCvt.info("=====银行管理平台登录请求参数======controller:" + JSON.toJSONString(loginReq));
		String json = "";
		try {
			long begin = System.currentTimeMillis();
			res.setCharacterEncoding("UTF-8");
			String referer = req.getHeader("Referer");
			if (referer == null) {
				throw new BankException(EnumTypes.fail.getCode(), "来源未知");
			}
			loginReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(loginReq.getUserName()) && StringUtil.isNotEmpty(loginReq.getPassword())) {
				req.setAttribute(Constants.USER_ID, "");
				// LogCvt.info("=====验证码的token: " + loginReq.getToken());
				map = loginService.loginValidate(loginReq, loginService.getOriginVo(req));
				// LogCvt.info("登录获取返回信息:"+JSON.toJSONString(map));
				LogCvt.debug("登录获取返回信息:" + JSON.toJSONString(map));
				Monitor.send(MonitorEnums.bank_login_ve, String.valueOf(System.currentTimeMillis() - begin));

				String domain = referer.replaceFirst("http://", "").replaceFirst("https://", "");
				domain = domain.substring(0, domain.indexOf("/"));

				Cookie uid = new Cookie(Constants.USER_ID_COOKIE, map.get("userId") + "");
				uid.setPath("/");
				uid.setDomain(domain);

				Cookie token = new Cookie("b_token", map.get("token") + "");
				token.setPath("/");
				token.setDomain(domain);

				res.addCookie(uid);
				res.addCookie(token);
				json = JSON.toJSONString(map);
				res.getWriter().write(json);
				LogCvt.info("RESPONE: " + json);
			} else {
				map.put("code", EnumTypes.empty.getCode());
				map.put("message", "用户名或密码不能为空");
				json = JSON.toJSONString(map);
				res.getWriter().write(json);
				LogCvt.info("RESPONE: " + json);
			}
		} catch (BankException e) {
			LogCvt.info("用户登陆" + e.getMessage(), e);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", e.getCode());
			map.put("message", e.getMessage());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (Exception e) {
			LogCvt.info("用户登陆" + e.getMessage(), e);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", EnumTypes.thrift_err.getCode());
			map.put("message", EnumTypes.thrift_err.getMessage());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} finally {
			res.flushBuffer();
		}

	}

	/**
	 * 登录（银行用户登录）
	 * 
	 * @param model
	 * @param req
	 * @throws IOException
	 */
	@RequestMapping(value = "/bankVe", method = RequestMethod.POST)
	public void bankUserLoginValidate(HttpServletRequest req, HttpServletResponse res, @RequestBody LoginReq loginReq)
			throws IOException {

		String json = "";
		try {
			long begin = System.currentTimeMillis();
			res.setCharacterEncoding("UTF-8");
			String referer = req.getHeader("Referer");
			if (referer == null) {
				throw new BankException(EnumTypes.fail.getCode(), "来源未知");
			}
			if (StringUtil.isBlank(loginReq.getClientId())) {
				loginReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			}
			if (StringUtil.isBlank((String) req.getAttribute(Constants.CLIENT_ID))) {
				req.setAttribute(Constants.CLIENT_ID, loginReq.getClientId());
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(loginReq.getUserName()) && StringUtil.isNotEmpty(loginReq.getPassword())) {
				req.setAttribute(Constants.USER_ID, "");
				map = loginService.bankUserloginValidate(loginReq, loginService.getOriginVo(req));
				// LogCvt.info("登录获取返回信息:"+JSON.toJSONString(map));
				LogCvt.debug("登录获取返回信息:" + JSON.toJSONString(map));
				Monitor.send(MonitorEnums.bank_login_ve, String.valueOf(System.currentTimeMillis() - begin));

				String domain = referer.replaceFirst("http://", "").replaceFirst("https://", "");
				domain = domain.substring(0, domain.indexOf("/"));

				Cookie uid = new Cookie(Constants.USER_ID_COOKIE, map.get("userId") + "");
				uid.setPath("/");
				uid.setDomain(domain);

				Cookie token = new Cookie("b_token", map.get("token") + "");
				token.setPath("/");
				token.setDomain(domain);

				Cookie orgCode = new Cookie("b_orgCode", map.get("orgCode") + "");
				orgCode.setPath("/");
				orgCode.setDomain(domain);

				Cookie username = new Cookie("b_username", map.get("username") + "");
				username.setPath("/");
				username.setDomain(domain);

				// Cookie bankUserLoginFlag = new Cookie("b_bankUserLoginFlag",
				// map.get("bankUserLoginFlag")+"");
				// bankUserLoginFlag.setPath("/");
				// bankUserLoginFlag.setDomain(domain);

				res.addCookie(uid);
				res.addCookie(token);
				// 增加机构号和银行用户名、银行用户登录标识
				res.addCookie(orgCode);
				res.addCookie(username);
				// res.addCookie(bankUserLoginFlag);

				// String flag = (String)map.get("bankUserLoginFlag");
				// String orgcode = (String)map.get("orgCode");
				// String name = (String)map.get("username");
				// if(StringUtil.isBlank(flag)){
				// req.setAttribute(Constants.FLAG, flag);
				// }
				// if(StringUtil.isBlank(orgcode)){
				// req.setAttribute(Constants.CODE, orgcode);
				// }
				// if(StringUtil.isBlank(name)){
				// req.setAttribute(Constants.USERNAME, name);
				// }

				json = JSON.toJSONString(map);
				res.getWriter().write(json);
				LogCvt.info("RESPONE: " + json);
			} else {
				map.put("code", EnumTypes.empty.getCode());
				map.put("message", "用户名或密码不能为空");
				json = JSON.toJSONString(map);
				res.getWriter().write(json);
				LogCvt.info("RESPONE: " + json);
			}
		} catch (BankException e) {
			LogCvt.info("用户登陆" + e.getMessage(), e);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", e.getCode());
			map.put("message", e.getMessage());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (Exception e) {
			LogCvt.info("用户登陆" + e.getMessage(), e);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", EnumTypes.thrift_err.getCode());
			map.put("message", EnumTypes.thrift_err.getMessage());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} finally {
			res.flushBuffer();
		}

	}

	/**
	 * 用户退出
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "olt", method = RequestMethod.POST)
	public void operateLogout(ModelMap model, HttpServletRequest req, HttpServletResponse res,
			@RequestBody LoginReq login) throws IOException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map = loginService.operatorLogout(login.getToken());
			LogCvt.info("用户退出获取返回信息:" + JSON.toJSONString(map));
			String referer = req.getHeader("Referer");
			if (referer == null) {
				throw new BankException(EnumTypes.fail.getCode(), "来源未知");
			}

			String domain = referer.replaceFirst("http://", "").replaceFirst("https://", "");
			domain = domain.substring(0, domain.indexOf("/"));

			Cookie uid = new Cookie(Constants.USER_ID_COOKIE, null);
			uid.setPath("/");
			uid.setDomain(domain);

			Cookie token = new Cookie("b_token", null);
			token.setPath("/");
			token.setDomain(domain);

			res.addCookie(uid);
			res.addCookie(token);

		} catch (BankException e) {
			LogCvt.info("用户退出" + e.getMessage(), e);
			map.put("code", e.getCode());
			map.put("message", e.getMessage());
			res.setStatus(608);
			String json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (Exception e) {
			LogCvt.info("用户退出" + e.getMessage(), e);
			map.put("code", EnumTypes.thrift_err.getCode());
			map.put("message", EnumTypes.thrift_err.getMessage());
			res.setStatus(608);
			String json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} finally {
			String json = JSON.toJSONString(map);
			res.getWriter().write(json);
			res.flushBuffer();
			LogCvt.info("RESPONE: " + json);
		}

	}

	/**
	 * 待审核数量查询
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys = { "work_remind_menu" })
	@RequestMapping(value = "pan", method = RequestMethod.POST)
	public void getPreAuditNum(ModelMap model, HttpServletRequest req, @RequestBody LoginReq login) {
		try {
			login.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(login.getOrgCode())) {
				model.putAll(loginService.getPreAuditNum(login.getClientId(), login.getOrgCode(),
						loginService.getOriginVo(req)));
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "组织编码不能为空");
			}
		} catch (TException e) {
			LogCvt.info("待审核数量查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 获取验证码
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "gic", method = RequestMethod.POST)
	public void getIdentifyCode(ModelMap model, HttpServletRequest req) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(loginService.getIdentifyCode(clientId));
		} catch (Exception e) {
			LogCvt.info("获取验证码" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 验证码验证
	 * 
	 * @param model
	 * @param req
	 */
	// @RequestMapping(value = "vt", method = RequestMethod.POST)
	// public void verifyToken(ModelMap model, HttpServletRequest req,
	// @RequestBody LoginReq login) {
	// try {
	// if (StringUtil.isNotEmpty(login.getOrgCode())) {
	// model.putAll(loginService.verifyToken(login.getToken(),
	// login.getCode()));
	// } else {
	// model.put("code", EnumTypes.empty.getCode());
	// model.put("message", "参数不全");
	// }
	// } catch (TException e) {
	// LogCvt.info("验证码验证"+e.getMessage(), e);
	// model.clear();
	// model.put("code", EnumTypes.thrift_err.getCode());
	// model.put("message", EnumTypes.thrift_err.getMessage());
	// }
	// }

}
