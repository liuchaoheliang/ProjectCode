package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.UserStatisticsService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo4Trend;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.vo.BankOperatorCheckVoRes;

/**
 * 
 * 类名: UserStatisticsController 
 * 描述: 银行用户统计报表相关 
 * 作者: 明灿 chenmingcan@f-road.com.cn
 * 日期: 2015年6月18日 上午10:05:24 
 *
 */
@Controller
@RequestMapping(value = "report/user")
public class UserStatisticsController extends BasicSpringController {

	@Resource
	private UserStatisticsService userStatisticsService;

	@Resource
	BankOperatorService.Iface bankOperatorService;
	/**
	 * 
	 * 方法名称: trend 
	 * 简要描述: 用户走势图 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年6月18日 上午10:05:50
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_userstatistics_menu"})
	@RequestMapping(value = "trend", method = RequestMethod.GET)
	public void trend(ModelMap model, HttpServletRequest request, ExcelReqVo4Trend reqVo) {
		model.clear();
		// // 添加时间校验
		// Map<String, String> result = DateUtilForReport.verifyDate(
		// reqVo.getBeginDate(), reqVo.getEndDate());
		// if (result.size() > 0) {
		// model.put("code", EnumTypes.illlegal.getCode());
		// model.put("message", result.get(DateUtilForReport.ERROR));
		// return;
		// }
		try {
			// 用户走势
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(userStatisticsService.trend(reqVo, clientId));
		} catch (Exception e) {
			LogCvt.info("用户走势查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "用户走势查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: saleTypePercent 
	 * 简要描述: 用户交易类型占比 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年6月18日 上午10:06:20
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_userstatistics_menu"})
	@RequestMapping(value = "userTradeTypePercent", method = RequestMethod.GET)
	public void saleTypePercent(ModelMap model, HttpServletRequest request,
			ExcelReqVo reqVo) {
		model.clear();
		try {
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(reqVo.getBeginDate(), reqVo.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			// 用户交易类型占比
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(userStatisticsService.userTradeTypePercent(reqVo,
					clientId));
		} catch (Exception e) {
			LogCvt.info("用户交易类型占比查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "用户交易类型占比查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: userConsumeTypePercent 
	 * 简要描述: 用户消费类型占比
	 * 版本信息: V1.0  
	 * 创建时间: 2015年6月18日 上午10:06:34
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_userstatistics_menu"})	
	@RequestMapping(value = "userConsumeTypePercent", method = RequestMethod.GET)
	public void userConsumeTypePercent(ModelMap model,
			HttpServletRequest request,
			ExcelReqVo reqVo) {
		model.clear();
		try {
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(reqVo.getBeginDate(), reqVo.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			// 用户消费类型占比
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(userStatisticsService.userConsumeTypePercent(reqVo,
					clientId));
		} catch (Exception e) {
			LogCvt.info("用户消费类型占比查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "用户消费类型占比查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: userSummaryList 
	 * 简要描述: 用户统计详情列表查询 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年6月18日 上午10:06:51
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_userstatistics_usersummarylist_bind"})
	@RequestMapping(value = "userSummaryList", method = RequestMethod.GET)
	public void userSummaryList(ModelMap model, HttpServletRequest request,
			ExcelReqVo reqVo) {
		model.clear();
		try {
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(reqVo.getBeginDate(), reqVo.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			// 用户统计详情列表查询
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(userStatisticsService.userSummaryList(reqVo,
					clientId));
		} catch (Exception e) {
			LogCvt.info("用户统计详情列表查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "用户统计详情列表查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: userSummaryExport 
	 * 简要描述: 用户统计详情列表导出 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年6月18日 上午10:07:10
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param response
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_userstatistics_usersummaryexport"})	
	@RequestMapping(value = "/userSummaryExport", method = RequestMethod.GET)
	public void userSummaryExport(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, ExcelReqVo reqVo) {
		model.clear();
		try {
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(reqVo.getBeginDate(), reqVo.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			// 用户统计详情列表导出接口
			/*************** 校验登入情况begin ***************/

			String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.parseLong(reqVo.getUserId()),
					reqVo.getToken());
			// 校验登录状态
			ExcelUtil.checkToken(reqVo, clientId, resVo);
			/*************** 校验登入情况end ***************/
			Map<String, Object> map = userStatisticsService.userSummaryExport(
					reqVo, clientId);
			if (EnumTypes.fail.getCode().equals((String) map.get("code"))) {
				// OutputStream os = response.getOutputStream();
				// os.write(map.toString().getBytes());
				// os.flush();
				// os.close();
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", map.get("message"));
				return;
			} else {
				// 导出
				ExcelUtil.doExport(response, map, "用户统计详情-");

			}
		} catch (Exception e) {
			LogCvt.info("用户统计详情下载异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}


	/**
	 * 
	 * 方法名称: userTradeDetailExport 
	 * 简要描述: 用户交易支付详情列表接口
	 * 版本信息: V1.0  
	 * 创建时间: 2015年6月18日 上午10:07:28
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param response
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	
	@CheckPermission(keys={"form_userstatistics_usertradedetailexport"})	
	@RequestMapping(value = "userTradeDetailExport", method = RequestMethod.GET)
	public void userTradeDetailExport(ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response, ExcelReqVo reqVo) {
		model.clear();
		try {
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(reqVo.getBeginDate(), reqVo.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			// 用户交易支付详情列表接口
			String clientId = request.getAttribute(Constants.CLIENT_ID) + "";
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.parseLong(reqVo.getUserId()),
					reqVo.getToken());
			// 校验登录状态
			ExcelUtil.checkToken(reqVo, clientId, resVo);
			// 调用查询逻辑
			Map<String, Object> map = userStatisticsService
					.userTradeDetailExport(reqVo, clientId);
			if (EnumTypes.fail.getCode().equals((String) map.get("code"))) {
				// OutputStream os = response.getOutputStream();
				// os.write(map.toString().getBytes());
				// os.flush();
				// os.close();
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", map.get("message"));
				return;
			} else {
				// 导出
				ExcelUtil.doExport(response, map, "用户交易支付详情-");

			}
		} catch (Exception e) {
			LogCvt.info("用户交易支付详情列表下载异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}

	/**
	 * 
	 * 方法名称: userTradeInfoExport 
	 * 简要描述: 用户交易信息导出 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月4日 上午11:32:40
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param response
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_userstatistics_usertradeinfoexport"})
	@RequestMapping(value = "userTradeInfoExport", method = RequestMethod.GET)
	public void userTradeInfoExport(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, ExcelReqVo reqVo) {
		model.clear();
		try {
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(reqVo.getBeginDate(), reqVo.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			// 用户交易信息导出
			String clientId = request.getAttribute(Constants.CLIENT_ID) + "";
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.parseLong(reqVo.getUserId()),
					reqVo.getToken());
			// 校验登录状态
			ExcelUtil.checkToken(reqVo, clientId, resVo);
			// 调用查询逻辑
			Map<String, Object> map = userStatisticsService
					.userTradeInfoExport(
					reqVo, clientId);
			if (EnumTypes.fail.getCode().equals((String) map.get("code"))) {

				model.put("code", EnumTypes.fail.getCode());
				model.put("message", map.get("message"));
				return;
			} else {
				// 导出
				ExcelUtil.doExport(response, map, "用户交易信息-");

			}
		} catch (Exception e) {
			LogCvt.info("用户交易信息导出异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());

		}
	}
}
