package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Point_Report_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.BankPointSettlementService;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;

/**
 * 银行积分报表相关类
 * 
 * @author user
 *
 */

@Controller
@RequestMapping(value = "point")
public class BankPointSettlementController extends BasicSpringController {

	@Resource
	BankPointSettlementService bankPointSettlementService;

	public static String SHOPPING = "0";
	public static String FTF = "1";
	/**
	 * 
	 * shoppingOrderTotal:购物类订单银行积分统计查询
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 上午10:37:58
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys = { "merchant_order_integral_menu" })
	@RequestMapping(value = "shoppingTotal", method = RequestMethod.GET)
	public void shoppingOrderTotal(ModelMap model, HttpServletRequest request,
			Point_Report_Req req) {
		model.clear();
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		req.setMerchantUser(
				(MerchantUser) request.getAttribute(Constants.MERCHANT_USER));
		try {
			model.putAll(bankPointSettlementService.getShoppingOrderTotal(req,
					SHOPPING));
		} catch (MerchantException e) {
			new RespError(model, e);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * getFTFOrderTotal:面对面惠付订单银行积分统计列表接口
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午1:58:35
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys = { "merchant_order_integral_menu" })
	@RequestMapping(value = "ftfTotal", method = RequestMethod.GET)
	public void getFTFOrderTotal(ModelMap model, HttpServletRequest request,
			Point_Report_Req req) {
		model.clear();
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		req.setMerchantUser(
				(MerchantUser) request.getAttribute(Constants.MERCHANT_USER));
		try {
			model.putAll(
					bankPointSettlementService.getShoppingOrderTotal(req, FTF));
		} catch (MerchantException e) {
			new RespError(model, e);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}


	/**
	 * 
	 * getPointTotal:积分总汇总查询接口
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午2:49:17
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys = { "merchant_order_integral_menu" })
	@RequestMapping(value = "total", method = RequestMethod.GET)
	public void getPointTotal(ModelMap model, HttpServletRequest request,
			Point_Report_Req req) {
		model.clear();
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		req.setMerchantUser(
				(MerchantUser) request.getAttribute(Constants.MERCHANT_USER));
		try {
			model.putAll(bankPointSettlementService.getPointTotal(req));
		} catch (MerchantException e) {
			new RespError(model, e);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * getPointTotalReport:积分报表导出
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午3:20:51
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys = { "merchant_order_integral_menu" })
	@RequestMapping(value = "report", method = RequestMethod.GET)
	public void getPointTotalReport(ModelMap model, HttpServletRequest request,
			Point_Report_Req req) {
		model.clear();
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		req.setMerchantUser(
				(MerchantUser) request.getAttribute(Constants.MERCHANT_USER));
		try {
			model.putAll(bankPointSettlementService.getPointTotalReport(req));
		} catch (MerchantException e) {
			new RespError(model, e);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
}
