package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Admin_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Admin_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
/**
 * 营业额
 * @ClassName AdminController
 * @author zxl
 * @date 2015年3月21日 下午2:01:56
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Resource
	Admin_Service admin_Service;
	
	/**
	 * 管理员查询营业额
	 * @tilte queryAdminInfo
	 * @author zxl
	 * @date 2015年3月21日 下午2:10:17
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "qa" , method = RequestMethod.POST)
	public void query_admin(ModelMap model,HttpServletRequest request,@RequestBody Query_Admin_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());		
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(admin_Service.query_admin(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
}
