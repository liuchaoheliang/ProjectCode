/**
 * Project Name:coremodule-bank
 * File Name:SettlementController.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.controller
 * Date:2015年8月17日下午1:08:34
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.SettlementBankService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.SettlementReqVo;
import com.froad.thrift.service.BankOperatorService;

/**
 * ClassName:SettlementController
 * Reason:	 结算查询相关的业务
 * Date:     2015年8月17日 下午1:08:34
 * @author   明灿
 * @version  
 * @see 	 
 */

@Controller
@RequestMapping(value = "/settlement")
public class SettlementController extends BasicSpringController {

	@Resource
	private SettlementBankService settlementBankService;
	
	@Resource
	BankOperatorService.Iface bankOperatorService;
	/**
	 * 
	 * queryByConditions:银行管理平台结算查询(结算失败)
	 *
	 * @author 明灿 2015年8月17日 下午3:37:40
	 * @param model
	 * @param req
	 * @param voReq
	 *
	 */
	
	@CheckPermission(keys={"setquery_menu","setquery_select_bind"})
	@RequestMapping(value = "/lt", method = RequestMethod.POST)
	public void queryByConditions(ModelMap model, HttpServletRequest req, @RequestBody SettlementReqVo voReq) {
		try {
		//校验券码不超过16个字符
		verifyTicket(model, voReq);
		// 时间校验
		Map<String, String> verifyMap = DateUtilForReport.verifyDate(voReq.getStartDate(), voReq.getEndDate());
			if (verifyMap != null && verifyMap.size() > 0) {
				model.put("code", EnumTypes.fail.getCode());
			model.put("message", verifyMap.get(DateUtilForReport.ERROR));
			return;
		}
			String clientId = (String) req.getAttribute(Constants.CLIENT_ID);
			voReq.setClientId(clientId);
			model.putAll(settlementBankService.queryByConditions(voReq));
		} catch (Exception e) {
			LogCvt.info("银行结算查询异常" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 
	 * verifyTicket:校验券码长度
	 *
	 * @author 明灿
	 * 2015年8月21日 上午10:58:53
	 * @param model
	 * @param voReq
	 *
	 */
	private void verifyTicket(ModelMap model, SettlementReqVo voReq) {
		if (voReq.getTicket() != null && voReq.getTicket().length() > 16) {
			model.put("code", "9999");
			model.put("message", "券码不能超过16位");
			return ;
		}
	}

	
	
	/**
	 * 
	 * listExportOfOptimize:结算查询下载优化
	 *
	 * @author 明灿
	 * 2015年9月15日 下午5:24:25
	 * @param model
	 * @param request
	 * @param reqVo
	 *
	 */
	
	@CheckPermission(keys={"setquery_export"})
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void listExportOfOptimize(ModelMap model, HttpServletRequest request, SettlementReqVo reqVo) {
		try {
			// 用户统计详情列表导出接口
			reqVo.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			// 校验券码长度
			verifyTicket(model, reqVo);
			// 校验时间
			Map<String, String> verifyMap = DateUtilForReport.verifyDate(reqVo.getStartDate(), reqVo.getEndDate());
			if (!verifyMap.isEmpty()) {
				model.put("code", "9999");
				model.put("message", verifyMap.get(DateUtilForReport.ERROR));
				return;
			}
			Map<String, Object> map = settlementBankService.listExportOfOptimize(reqVo);
			model.putAll(map);
		} catch (Exception e) {
			LogCvt.info("结算下载异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}
}
