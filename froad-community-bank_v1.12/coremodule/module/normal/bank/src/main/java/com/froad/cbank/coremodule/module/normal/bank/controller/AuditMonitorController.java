/**
 * Project Name:coremodule-bank
 * File Name:AuditMonitorController.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.controller
 * Date:2015-8-14下午03:17:09
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
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.AuditMonitorService;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditTaskReq;

/**
 * 
 * ClassName: AuditMonitorController Function: 审核箱 date: 2015-8-14 下午03:27:26
 *
 * @author wufei
 * @version
 */
@Controller
@RequestMapping(value = "/auditMonitor")
public class AuditMonitorController extends BasicSpringController{

	@Resource
	private AuditMonitorService auditMonitorService;
	@Resource
	private LoginService loginService;
	
	/**
	 * 
	 * auditMonitorList:(审核监控列表查询).
	 *
	 * @author wufei 2015-8-14 下午03:25:09
	 * @param model
	 * @param req
	 * @param auditMonitorReq
	 *
	 */
	@CheckPermission(keys={"examinemonitor_menu","examinemonitor_select_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
	public void auditMonitorList(ModelMap model,HttpServletRequest req,@RequestBody AuditTaskReq auditTaskReq){
		try {
			model.clear();
			auditTaskReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(auditMonitorService.auditMonitorListNew(auditTaskReq,loginService.getOrigin(req)));
		} catch (Exception e) {
			LogCvt.info("审核监控列表查询" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
		
	}
	

	/**
	 * 
	 * compositeQuery:(综合查询列表).
	 *
	 * @author wufei 2015-8-14 下午03:28:57
	 * @param model
	 * @param req
	 * @param auditMonitorReq
	 *
	 */
	@CheckPermission(keys={"comprehensivequery_menu","comprehensivequery_select_bind"})	
	@RequestMapping(value = "/composite",method = RequestMethod.POST)
	public void compositeQuery(ModelMap model,HttpServletRequest req,@RequestBody AuditTaskReq auditTaskReq){
		try {
			auditTaskReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(auditMonitorService.compositeQueryNew(auditTaskReq,loginService.getOrigin(req)));
		} catch (Exception e) {
			LogCvt.info("综合查询" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * 
	 * auditMonitorDetail:(审核监控和综合查询详情).
	 *
	 * @author wufei 2015-8-26 下午12:50:17
	 * @param model
	 * @param req
	 * @param auditId
	 * @param merchantId
	 * @param merchantUserId
	 *
	 */
	@CheckPermission(keys={"examinemonitor_detail_bind","comprehensivequery_detail_bind","examine_monitor_detail_details","comprehensive_query_details","fomous_merchant_examinemerchant"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
	public void auditMonitorDetail(ModelMap model,HttpServletRequest req,String auditId,String merchantId,String merchantUserId){
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			model.putAll(auditMonitorService.getAuditTaskDetail(auditId, merchantId, merchantUserId, loginService.getOriginVo(req),clientId));
		} catch (TException e) {
			LogCvt.info("审核监控和综合查询详情" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
		
	}
}
