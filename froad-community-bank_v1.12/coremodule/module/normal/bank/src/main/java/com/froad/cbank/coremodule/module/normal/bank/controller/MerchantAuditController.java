/**
 * Project Name:coremodule-bank
 * File Name:MerchantAuditController.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.controller
 * Date:2015-8-14下午01:29:55
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.AuditMerchantService;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantEditReq;

/**
 * 
 * ClassName: MerchantAuditController
 * Function: 商户审核
 * date: 2015-8-14 下午03:30:41
 *
 * @author wufei
 * @version
 */
@Controller
@RequestMapping(value = "/merchantAudit")
public class MerchantAuditController extends BasicSpringController{
	
	@Resource
	private AuditMerchantService auditMerchantService;
	@Resource
	private LoginService loginService;
	
	/**
	 * 
	 * merchantAuditDetail:(待审核商户修改信息详情).
	 *
	 * @author wufei
	 * 2015-8-14 下午01:58:32
	 * @param model
	 * @param req
	 * @param merchantId
	 *
	 */
	@CheckPermission(keys={"fomous_merchant_examinemerchant","fomous_merchant_detial_bind"})
	@RequestMapping(value = "/auditOrder",method = RequestMethod.GET)
	public void merchantAuditDetail(ModelMap model,HttpServletRequest req,String merchantId,String merchantUserId){
			
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			model.putAll(auditMerchantService.merchantAuditDetail(merchantId,loginService.getOriginVo(req),merchantUserId,clientId));
		} catch (TException e) {
			LogCvt.info("待审核商户修改信息详情"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
		
		
	}
	
	/**
	 * 
	 * MerchantEdit:(商户待审核信息编辑).
	 *
	 * @author wufei
	 * 2015-8-14 下午02:27:34
	 * @param model
	 * @param req
	 * @param merchantEditReq
	 *
	 */
	@RequestMapping(value = "/ue",method = RequestMethod.PUT)
	public void merchantEdit(ModelMap model,HttpServletRequest req,@RequestBody MerchantEditReq merchantEditReq){
		try {
			merchantEditReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(auditMerchantService.merchantEdit(merchantEditReq,req));
		} catch (TException e) {
			LogCvt.info("商户待审核信息编辑"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
			
	}
	
	/**
	 * 
	 * auditStatus:(商户审核状态查询).
	 *
	 * @author wufei
	 * 2015-8-18 下午05:40:34
	 * @param model
	 * @param req
	 * @param merchantId
	 *
	 */
	@RequestMapping(value = "/auditStatus",method = RequestMethod.GET)
	public void getAuditStatus(ModelMap model,HttpServletRequest req,String merchantId){
		try {
			model.putAll(auditMerchantService.getAuditStatus(merchantId));
		} catch (TException e) {
			LogCvt.info("商户审核编辑状态查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
}
