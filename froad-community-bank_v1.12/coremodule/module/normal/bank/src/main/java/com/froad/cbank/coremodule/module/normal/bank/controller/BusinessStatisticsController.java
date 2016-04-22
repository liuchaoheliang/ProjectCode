package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.BusinessStatisticsService;
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
 * 类名: BusinessStatisticsController 
 * 描述: 销售报表相关 
 * 作者: 明灿 chenmingcan@f-road.com.cn
 * 日期: 2015年7月22日 下午2:11:31 
 *
 */
@Controller
@RequestMapping(value = "report/bussiness")
public class BusinessStatisticsController extends BasicSpringController {

	@Resource
	private BusinessStatisticsService businessStatisticsService;

	@Resource
	BankOperatorService.Iface bankOperatorService;

	/**
	 * 
	 * 方法名称: trend 
	 * 简要描述: 销售额走势 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:11:39
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_businesssales_menu"})
	@RequestMapping(value = "trend", method = RequestMethod.GET)
	public void trend(ModelMap model, HttpServletRequest request,
 ExcelReqVo4Trend reqVo) {
		model.clear();
		// 添加时间校验
		// Map<String, String> result =
		// DateUtilForReport.verifyDate(reqVo.getBeginDate(),
		// reqVo.getEndDate());
		// if (result.size() > 0) {
		// model.put("code", EnumTypes.illlegal.getCode());
		// model.put("message", result.get(DateUtilForReport.ERROR));
		// return;
		// }
		try {
			// 销售额走势
			String clientId = (String) request.getAttribute(Constants.CLIENT_ID);
			model.putAll(businessStatisticsService.trend(reqVo, clientId));
		} catch (Exception e) {
			LogCvt.info("销售额走势查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "销售额走势查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: saleTypePercent 
	 * 简要描述: 业务类型销售占比 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:11:46
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_businesssales_menu"})
	@RequestMapping(value = "saleTypePercent", method = RequestMethod.GET)
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
			// 业务类型销售占比
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(businessStatisticsService.saleTypePercent(reqVo,
					clientId));
		} catch (Exception e) {
			LogCvt.info("业务类型销售占比查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "业务类型销售占比查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: payTypePercent 
	 * 简要描述: 支付方式占比
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:11:53
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	@RequestMapping(value = "payTypePercent", method = RequestMethod.GET)
	public void payTypePercent(ModelMap model, HttpServletRequest request,
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
			// 支付方式占比
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(businessStatisticsService.payTypePercent(reqVo,
					clientId));
		} catch (Exception e) {
			LogCvt.info("支付方式占比查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "支付方式占比查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: statisticList 
	 * 简要描述: 业务销售统计列表查询 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:11:59
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_businesssales_statisticlist_bind"})
	@RequestMapping(value = "statisticList", method = RequestMethod.GET)
	public void statisticList(ModelMap model, HttpServletRequest request,
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
			// 业务销售统计列表查询
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(businessStatisticsService.statisticList(reqVo,
					clientId));
		} catch (Exception e) {
			LogCvt.info("业务销售统计列表查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "业务销售统计列表查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: statisticListExport 
	 * 简要描述: 业务销售统计列表查询
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:12:04
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param response
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_businesssales_statisticlistexport"})
	@RequestMapping(value = "statisticListExport", method = RequestMethod.GET)
	public void statisticListExport(ModelMap model, HttpServletRequest request,
 HttpServletResponse response,
			ExcelReqVo reqVo, RedirectAttributes attrs) {
		model.clear();
		try {
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(reqVo.getBeginDate(), reqVo.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			String clientId = request.getAttribute(Constants.CLIENT_ID) + "";
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.parseLong(reqVo.getUserId()),
					reqVo.getToken());
			// 校验登录状态
			ExcelUtil.checkToken(reqVo, clientId, resVo);
			// 调用查询逻辑
			Map<String, Object> map = businessStatisticsService
					.statisticListExport(reqVo, clientId);
			if (EnumTypes.fail.getCode().equals((String) map.get("code"))) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", map.get("message"));
				return;
			} else {
				// 导出
				ExcelUtil.doExport(response, map, "业务销售统计-");
			}
		} catch (Exception e) {
			LogCvt.info("业务销售统计列表下载异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}




	/**
	 * 
	 * 方法名称: bussinessSaleExport 
	 * 简要描述: 业务销售统计详情列表下载
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:12:11
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param response
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_businesssales_bussinesssaleexport"})
	@RequestMapping(value = "bussinessSaleExport", method = RequestMethod.GET)
	public void bussinessSaleExport(ModelMap model, HttpServletRequest request,
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
			// 业务销售统计详情列表下载
			String clientId = request.getAttribute(Constants.CLIENT_ID) + "";
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.parseLong(reqVo.getUserId()),
					reqVo.getToken());
			// 校验登录状态
			ExcelUtil.checkToken(reqVo, clientId, resVo);
			Map<String, Object> map = businessStatisticsService
					.bussinessSaleExport(reqVo, clientId);
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
				ExcelUtil.doExport(response, map, "业务类型销售详情-");

			}
		} catch (Exception e) {
			LogCvt.info("业务销售统计详情列表下载异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}


	/**
	 * 
	 * 方法名称: merchantSaleExport 
	 * 简要描述: 商户业务销售详情列表下载
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:12:17
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param response
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_businesssales_merchantsaleexport"})
	@RequestMapping(value = "merchantSaleExport", method = RequestMethod.GET)
	public void merchantSaleExport(ModelMap model, HttpServletRequest request,
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
			// 商户业务销售详情列表下载

			/*************** 校验登入情况begin ***************/
			String clientId = request.getAttribute(Constants.CLIENT_ID) + "";
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.parseLong(reqVo.getUserId()),
					reqVo.getToken());
			// 校验登录状态
			ExcelUtil.checkToken(reqVo, clientId, resVo);
			// 调用查询逻辑
			Map<String, Object> map = businessStatisticsService
					.merchantSaleExport(reqVo, clientId);
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
				ExcelUtil.doExport(response, map, "商户业务销售详情-");

			}
		} catch (Exception e) {
			LogCvt.info("商户业务销售详情列表下载异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
			// OutputStream os;
			// try {
			// os = response.getOutputStream();
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("code", EnumTypes.fail.getCode());
			// map.put("message", "商户业务销售详情列表下载失败！");
			// os.write(map.toString().getBytes());
			// os.flush();
			// os.close();
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
		}
	}
}
