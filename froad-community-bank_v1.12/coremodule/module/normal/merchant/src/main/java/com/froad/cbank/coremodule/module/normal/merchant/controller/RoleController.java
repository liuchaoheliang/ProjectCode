package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.service.Role_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;

/**
 * 角色
 * @ClassName RoleController
 * @author zxl
 * @date 2015年4月21日 上午10:24:36
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {
	
	@Resource
	Role_Service role_Service;
	
	/**
	 * 角色列表
	 * @tilte query_list
	 * @author zxl
	 * @date 2015年4月21日 上午10:42:40
	 * @param model
	 * @param request
	 * @param req
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void query_list(ModelMap model,HttpServletRequest request){
		try {
			String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
			model.putAll(role_Service.query_list(clientId));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 角色权限
	 * @tilte query_source_list
	 * @author zxl
	 * @date 2015年4月21日 上午11:01:06
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/rs", method = RequestMethod.GET)
	public void query_source_list(ModelMap model,HttpServletRequest request,String roleId){
		try {
			if(StringUtils.isBlank(roleId)){
				throw new MerchantException(EnumTypes.fail.getCode(), "	角色ID不能为空");
			}
			String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
			model.putAll(role_Service.query_resouce_list(clientId,roleId));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
}
