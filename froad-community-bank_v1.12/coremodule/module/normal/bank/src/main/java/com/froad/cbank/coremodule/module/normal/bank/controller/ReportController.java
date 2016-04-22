package com.froad.cbank.coremodule.module.normal.bank.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.CountTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.ReportSupport;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.DefineTaskReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.ReportBankReqVo;

/**
 * bruce 2015年11月30日 下午7:54:09
 */
@Controller
@RequestMapping(value = "report")
public class ReportController {

	@Resource
	private ReportSupport reportSupport;

	@CheckPermission(keys = { "report_new_menu", "report_new_list_menu",
			"report_new_list_select_bind" })
	@RequestMapping(value = "/countsByLat", method = RequestMethod.POST)
	public void MerchantExportOfOptimize(ModelMap model, HttpServletRequest req,
			@RequestBody ReportBankReqVo voReq) {
		try {
			model.clear();
			if (voReq.getOrderType() != null
					&& "".equals(voReq.getOrderType().trim())) {
				voReq.setOrderType(null);
			}
			if (voReq.getPayType() != null
					&& "".equals(voReq.getPayType().trim())) {
				voReq.setPayType(null);
			}
			if ((!voReq.getCountType().equals(CountTypeEnum.DIY.getCode()))
					&& (voReq.getLatitudeTupe() == null
							|| "".equals(voReq.getLatitudeTupe()))) {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", EnumTypes.empty.getMessage());
			} else {
				String clientId = (String) req
						.getAttribute(Constants.CLIENT_ID);
				voReq.setClientId(clientId);
				model.putAll(reportSupport.getReportRespPage(voReq, req));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			model.clear();
			LogCvt.info("获取报表失败: " + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * DefinTaskList:(任务列表分页查询).
	 *
	 * @author wufei 2015-12-5 下午05:16:31
	 * @param model
	 * @param req
	 *
	 */
	@CheckPermission(keys = { "report_new_list_tasklist_bind" })
	@RequestMapping(value = "/taskList", method = RequestMethod.POST)
	public void getDefinTaskList(ModelMap model, HttpServletRequest req,
			@RequestBody DefineTaskReq defineTaskReq) {
		try {
			String clientId = (String) req.getAttribute(Constants.CLIENT_ID);
			defineTaskReq.setClientId(clientId);
			model.putAll(reportSupport.getDefinTaskList(defineTaskReq));
		} catch (Exception e) {
			// e.printStackTrace();
			model.clear();
			LogCvt.info("获取任务列表失败: " + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * ReportExport:(报表导出)
	 *
	 * @author wufei 2015-12-5 下午06:46:24
	 * @param model
	 * @param req
	 * @param voReq
	 *
	 */
	@CheckPermission(keys = { "report_new_list_export_bind" })
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void ReportExport(ModelMap model, HttpServletRequest req,
			@RequestBody ReportBankReqVo voReq) {
		try {
			String clientId = (String) req.getAttribute(Constants.CLIENT_ID);
			voReq.setClientId(clientId);
			model.putAll(reportSupport.getReportByCondition(voReq));
		} catch (Exception e) {
			model.clear();
			LogCvt.info("银行报表导出异常: " + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

}
