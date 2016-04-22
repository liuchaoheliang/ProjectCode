package com.froad.cbank.coremodule.module.normal.merchant.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.BasePojo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_Fails_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_No_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_Out_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Login_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Reset_Merchant_User_Pwd_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Merchant_User_Pwd_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Login_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
import com.froad.thrift.vo.PlatType;

/**
 * 登录
 * 
 * @ClassName LoginController
 * @author zxl
 * @date 2015年3月21日 下午1:57:30
 */
@Controller
public class LoginController {

	@Resource
	Login_Service login_Service;

	/**
	 * 验证码专用登录接口
	 * 
	 * @param model
	 * @param request
	 * @param req
	 * @throws IOException
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public void login_vertify(HttpServletRequest request, HttpServletResponse response, @RequestBody Login_Req req)
			throws IOException {
		try {
			String referer = request.getHeader("Referer");

			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType) request.getAttribute(Constants.PLAT_TYPE));

			Login_Res user = login_Service.login_vertify(req);

			String domain = referer.replaceFirst("http://", "").replaceFirst("https://", "");
			domain = domain.substring(0, domain.indexOf("/"));

			Cookie uid = new Cookie("m_uid", user.getUserId() + "");
			uid.setPath("/");
			uid.setDomain(domain);

			Cookie token = new Cookie("m_token", user.getToken());
			token.setPath("/");
			token.setDomain(domain);

			response.addCookie(uid);
			response.addCookie(token);
			String json = JSON.toJSONString(user);
			response.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);

		} catch (MerchantException e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			map.put("loginFailureCount", Integer.toString(e.getFailsCount()));
			response.setStatus(608);
			String json = JSON.toJSONString(map);
			LogCvt.info("RESPONE: " + json);
			response.getWriter().write(json);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", EnumTypes.syserr.getCode());
			map.put("message", EnumTypes.syserr.getMsg());
			response.setStatus(608);
			String json = JSON.toJSONString(map);
			LogCvt.info("RESPONE: " + json);
			response.getWriter().write(json);
		} finally {
			response.flushBuffer();
		}
	}

	/**
	 * 获取验证码图片
	 * 
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/vi", method = RequestMethod.GET)
	public void verifyUrl(ModelMap model, HttpServletRequest request) {
		try {
			model.putAll(login_Service.verifyUrl(request.getAttribute(Constants.CLIENT_ID).toString()));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}

	/**
	 * 获取登录错误统计次数
	 * 
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/getFailsCount", method = RequestMethod.GET)
	public void getFailsCount(ModelMap model, HttpServletRequest request, Login_Fails_Req reqVo) {
		try {
			model.clear();
			if (!StringUtil.isNotBlank(reqVo.getUserName())) {
				model.put("code", "608");
				model.put("message", "用户名不能为空");
				return;
			}
			reqVo.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			reqVo.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			reqVo.setPlatType((PlatType) request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(login_Service.getFailsCount(reqVo));
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}

	/**
	 * 登出
	 * 
	 * @tilte login
	 * @author zxl
	 * @date 2015年3月23日 上午11:41:33
	 * @param model
	 * @param req
	 * @throws IOException
	 */
	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public void loginOut(HttpServletRequest request, HttpServletResponse response, Login_Out_Req lreq)
			throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			BasePojo req = new BasePojo();
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType) request.getAttribute(Constants.PLAT_TYPE));

			login_Service.loginOut(lreq.getUserId(), lreq.getToken(), req);

			String referer = request.getHeader("Referer");
			String domain = referer.replaceFirst("http://", "").replaceFirst("https://", "");
			domain = domain.substring(0, domain.indexOf("/"));

			Cookie uid = new Cookie("m_uid", null);
			uid.setPath("/");
			uid.setDomain(domain);

			Cookie token1 = new Cookie("m_token", null);
			token1.setPath("/");
			token1.setDomain(domain);

			response.addCookie(uid);
			response.addCookie(token1);

		} catch (MerchantException e) {
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			String json = JSON.toJSONString(map);
			response.getWriter().write(json);
			response.flushBuffer();
			LogCvt.info("RESPONE: " + json);
		}
	}

	/**
	 * 修改登录密码
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void query(ModelMap model, HttpServletRequest request, @RequestBody Update_Merchant_User_Pwd_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser) request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType) request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(login_Service.update(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 登录密码重置
	 * 
	 * @tilte reset
	 * @author zxl
	 * @date 2015年4月11日 下午2:50:42
	 * @param model
	 * @param request
	 * @param req
	 */

	@CheckPermission(keys = { "merchant_user_users_menu" })
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public void reset(ModelMap model, HttpServletRequest request, @RequestBody Reset_Merchant_User_Pwd_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser) request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType) request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(login_Service.reset(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 无验证码专用登录接口
	 * 
	 * @param model
	 * @param request
	 * @param req
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login_(HttpServletRequest request, HttpServletResponse response, @RequestBody Login_No_Req req)
			throws IOException {
		try {
			String referer = request.getHeader("Referer");

			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType) request.getAttribute(Constants.PLAT_TYPE));

			Login_Res user = login_Service.login_(req);

			String domain = referer.replaceFirst("http://", "").replaceFirst("https://", "");
			domain = domain.substring(0, domain.indexOf("/"));

			Cookie uid = new Cookie("m_uid", user.getUserId() + "");
			uid.setPath("/");
			uid.setDomain(domain);

			Cookie token = new Cookie("m_token", user.getToken());
			token.setPath("/");
			token.setDomain(domain);

			response.addCookie(uid);
			response.addCookie(token);
			String json = JSON.toJSONString(user);
			response.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);

		} catch (MerchantException e) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			response.setStatus(608);
			String json = JSON.toJSONString(map);
			LogCvt.info("RESPONE: " + json);
			response.getWriter().write(json);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", EnumTypes.syserr.getCode());
			map.put("message", EnumTypes.syserr.getMsg());
			response.setStatus(608);
			String json = JSON.toJSONString(map);
			LogCvt.info("RESPONE: " + json);
			response.getWriter().write(json);
		} finally {
			response.flushBuffer();
		}
	}
}
