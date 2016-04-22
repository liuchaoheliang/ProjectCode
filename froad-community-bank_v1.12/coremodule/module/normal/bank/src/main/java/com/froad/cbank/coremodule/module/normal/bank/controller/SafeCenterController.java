package com.froad.cbank.coremodule.module.normal.bank.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.LoginReq;

/**
 * 安全中心
 * @author ylchu
 *
 */
@Controller
@RequestMapping(value = "/safeCenter")
public class SafeCenterController extends BasicSpringController {

	@Resource
	private LoginService loginService;
	/**
	 * @Title: 用户登录密码修改
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/lpue",method = RequestMethod.POST)
	public void loginPasswordUpdate(ModelMap model,HttpServletRequest req,@RequestBody LoginReq loginReq){
		try {
			loginReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(loginService.loginPasswordUpdate(req,loginReq.getUserId(), loginReq.getClientId(),
					loginReq.getOldPassword(), loginReq.getPassword()));
		} catch (TException e) {
			LogCvt.info("密码修改"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
}
