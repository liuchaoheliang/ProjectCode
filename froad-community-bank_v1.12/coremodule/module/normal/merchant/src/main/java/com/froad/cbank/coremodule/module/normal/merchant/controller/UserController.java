package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.enums.UserType;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Add_Merchant_User_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Del_Merchant_User_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_User_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Merchant_User_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Merchant_User_Status_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.User_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
import com.froad.thrift.vo.PlatType;

/**
 * 商户用户
 * @ClassName UserController
 * @author zxl
 * @date 2015年4月17日 上午10:03:32
 */
@Controller
public class UserController {
	
	@Resource
	User_Service user_Service;
	
	/**
	 * 查询门店用户
	 * 
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"merchant_user_users_menu"})
	@RequestMapping(value = "/outlet/mou", method = RequestMethod.POST)
	public void query_merchant_outlet_user(ModelMap model,HttpServletRequest request, @RequestBody Query_Merchant_User_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			MerchantUser user=(MerchantUser) request.getAttribute(Constants.MERCHANT_USER);
			model.putAll(user_Service.query_merchant_outlet_user(req,user));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加门店用户
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_user_users_menu"})
	@RequestMapping(value = "/outlet/moua", method = RequestMethod.POST)
	public void merchant_outlet_user_add(ModelMap model,HttpServletRequest request, @RequestBody Add_Merchant_User_Req req) {
		try {
			MerchantUser user=(MerchantUser)request.getAttribute(Constants.MERCHANT_USER);
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser(user);
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			//默认添加普通用户
			req.setMerchantRoleId(UserType.normalUser.getCode());
			//设置当前操作用户Id
			req.setOperatorUserId(user.getId());
			model.putAll(user_Service.merchant_outlet_user_add(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 删除门店用户
	 * 
	 * @param model
	 * @param req
	 */
	
	@RequestMapping(value = "/outlet/moud", method = RequestMethod.POST)
	public void merchant_outlet_user_delete(ModelMap model,HttpServletRequest request, @RequestBody Del_Merchant_User_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(user_Service.merchant_outlet_user_delete(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 修改门店用户
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_user_users_menu"})
	@RequestMapping(value = "/outlet/moup", method = RequestMethod.POST)
	public void merchant_outlet_user_update(ModelMap model,HttpServletRequest request, @RequestBody Update_Merchant_User_Req req) {
		try {
			MerchantUser user=(MerchantUser)request.getAttribute(Constants.MERCHANT_USER);
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser(user);
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			//设置当前操作用户Id
			req.setOperatorUserId(user.getId());
 			model.putAll(user_Service.merchant_outlet_user_update(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 用户禁启用
	 * @tilte merchant_outlet_user_update_status
	 * @author zxl
	 * @date 2015年5月16日 下午2:19:06
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys={"merchant_user_users_menu"})
	@RequestMapping(value = "/outlet/mous", method = RequestMethod.POST)
	public void merchant_outlet_user_update_status(ModelMap model,HttpServletRequest request, @RequestBody Update_Merchant_User_Status_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(user_Service.merchant_outlet_user_update_status(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
}
