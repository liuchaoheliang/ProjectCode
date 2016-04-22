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
import com.froad.cbank.coremodule.module.normal.bank.service.ProductStatisticsService;
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
 * 类名: ProductStatisticsController 
 * 描述: 社区银行商品统计报表 
 * 作者: 明灿 chenmingcan@f-road.com.cn
 * 日期: 2015年7月22日 下午2:18:06 
 *
 */
@Controller
@RequestMapping(value = "report/product")
public class ProductStatisticsController extends BasicSpringController {

	@Resource
	private ProductStatisticsService productStatisticsService;

	@Resource
	BankOperatorService.Iface bankOperatorService;

	/**
	 * 
	 * 方法名称: trend 
	 * 简要描述: 销售额走势 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:18:17
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_product_menu"})
	@RequestMapping(value = "trend", method = RequestMethod.GET)
	public void trend(ModelMap model, HttpServletRequest request,
 ExcelReqVo4Trend reqVo) {
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
			// 销售额走势
			String clientId = (String) request.getAttribute(Constants.CLIENT_ID);
			model.putAll(productStatisticsService.trend(reqVo, clientId));
		} catch (Exception e) {
			LogCvt.info("商品销售额走势查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "商品销售额走势查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: saleTypePercent 
	 * 简要描述: 业务类型销售占比 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:18:32
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_product_menu"})
	@RequestMapping(value = "typePercent", method = RequestMethod.GET)
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
			model.putAll(productStatisticsService.typePercent(reqVo, clientId));
		} catch (Exception e) {
			LogCvt.info("商品销售额走势查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "商品销售额走势查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: categoryTypePercent 
	 * 简要描述: 商品类目占比 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:18:41
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_businesssales_menu","form_product_menu"})
	@RequestMapping(value = "categoryTypePercent", method = RequestMethod.GET)
	public void categoryTypePercent(ModelMap model, HttpServletRequest request,
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
			// 商品类目占比
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(productStatisticsService.categoryTypePercent(reqVo,
					clientId));
		} catch (Exception e) {
			LogCvt.info("商品类目占比查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "商品类目占比查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: saleDetailList 
	 * 简要描述: 商品销售详情列表查询 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:19:18
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param response
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_product_saledetaillist_bind"})
	@RequestMapping(value = "saleDetailList", method = RequestMethod.GET)
	public void saleDetailList(ModelMap model, HttpServletRequest request,
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
			// 商品销售详情列表查询
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(productStatisticsService.saleDetailList(reqVo,
					clientId));
		} catch (Exception e) {
			LogCvt.info("商品销售详情列表查询异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "商品销售详情列表查询失败！");
		}
	}

	/**
	 * 
	 * 方法名称: saleDetailListExport 
	 * 简要描述: 商品销售详情列表下载 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年7月22日 下午2:19:26
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param request
	 * 方法参数: @param response
	 * 方法参数: @param reqVo
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"form_product_saledetaillistexport"})
	@RequestMapping(value = "saleDetailListExport", method = RequestMethod.GET)
	public void saleDetailListExport(ModelMap model,
			HttpServletRequest request, HttpServletResponse response,
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
			// 商品销售详情列表下载
			String clientId = request.getAttribute(Constants.CLIENT_ID) + "";
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.parseLong(reqVo.getUserId()),
					reqVo.getToken());
			// 校验登录状态
			ExcelUtil.checkToken(reqVo, clientId, resVo);
			// 调用查询逻辑
			Map<String, Object> map = productStatisticsService
					.saleDetailListExport(reqVo, clientId);
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
				ExcelUtil.doExport(response, map, "商品销售详情-");

			}
		} catch (Exception e) {
			LogCvt.info("商品销售详情列表下载异常", e.getMessage());
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}
}
