package com.froad.cbank.coremodule.module.normal.bank.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.service.BankOrgService;
import com.froad.cbank.coremodule.module.normal.bank.service.OrderManageService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BoutiqueOrderReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderReqOfOptimize;
import com.froad.thrift.service.BankOperatorService;

/**
 * 订单导出
 * 
 * @author yfy
 *
 */
@Controller
@RequestMapping(value = "/orderExport")
public class OrderExportController {

	@Resource
	private OrderManageService orderManageService;
	@Resource
	private BankOrgService BankOrgService;
	@Resource
	BankOperatorService.Iface bankOperatorService;

	/**
	 * 
	 * groupOrderExportOfOptimize:(团购订单下载优化).
	 *
	 * @author wufei 2015-9-7 上午11:42:30
	 * @param model
	 * @param req
	 * @param order
	 *
	 */
	@CheckPermission(keys = { "grouporderlist_export" })
	@RequestMapping(value = "/goet", method = RequestMethod.GET)
	public void groupOrderExportOfOptimize(ModelMap model, HttpServletRequest req, OrderReqOfOptimize order) {
		try {
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(orderManageService.getGroupOrderExport(order));
		} catch (Exception e) {
			LogCvt.info("团购订单下载异常:" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}

	/**
	 * 
	 * presaleOrderExportOfOptimize:(预售订单下载优化).
	 *
	 * @author wufei 2015-9-7 上午11:51:28
	 * @param model
	 * @param req
	 * @param order
	 *
	 */
	@CheckPermission(keys = { "boutiqueorderlist_export" })
	@RequestMapping(value = "/poet", method = RequestMethod.GET)
	public void presaleOrderExportOfOptimize(ModelMap model, HttpServletRequest req, OrderReqOfOptimize order) {
		LogCvt.info("【订单导出】-请求参数：" + JSON.toJSONString(order));
		try {
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(orderManageService.getGroupOrderExport(order));
		} catch (Exception e) {
			LogCvt.info("预售订单下载异常:" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}

	/**
	 * 
	 * getBoutiqueOrderListExport:精品商城订单导出接口
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 下午4:29:26
	 * @param model
	 * @param req
	 * @param reqVo
	 *
	 */
	@CheckPermission(keys = { "boutiquemallorder_t_export" })
	@RequestMapping(value = "/gbsol", method = RequestMethod.GET)
	public void getBoutiqueOrderListExport(ModelMap model, HttpServletRequest req, BoutiqueOrderReq reqVo) {
		try {
			reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(orderManageService.getBoutiqueExport(reqVo));
		} catch (Exception e) {
			LogCvt.info("精品商城订单导出异常:" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}

	/**
	 * 
	 * cashierOrderExportOfOptimize:(面对面订单下载优化).
	 *
	 * @author wufei 2015-9-7 上午11:52:51
	 * @param model
	 * @param req
	 * @param order
	 *
	 */

	@CheckPermission(keys = { "faceorderlist_export" })
	@RequestMapping(value = "/coet", method = RequestMethod.GET)
	public void cashierOrderExportOfOptimize(ModelMap model, HttpServletRequest req, OrderReqOfOptimize order) {
		try {
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(orderManageService.getGroupOrderExport(order));
		} catch (Exception e) {
			LogCvt.info("面对面订单下载异常：" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}

	/**
	 * 
	 * famousPreferentOrderExportOfOptimize:(名优特惠订单下载优化).
	 *
	 * @author wufei 2015-9-7 上午11:55:14
	 * @param model
	 * @param req
	 * @param order
	 *
	 */

	@CheckPermission(keys = { "famousorderlist_export" })
	@RequestMapping(value = "/fpoet", method = RequestMethod.GET)
	public void famousPreferentOrderExportOfOptimize(ModelMap model, HttpServletRequest req, OrderReqOfOptimize order) {
		try {
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(orderManageService.getGroupOrderExport(order));
		} catch (Exception e) {
			LogCvt.info("名优特惠订单下载异常：" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}

	/**
	 * 
	 * creditsExchangeOrderOfOptimize:(积分兑换订单下载优化).
	 *
	 * @author wufei 2015-9-7 上午11:56:39
	 * @param model
	 * @param req
	 * @param order
	 *
	 */

	@CheckPermission(keys = { "integralorderlist_export" })
	@RequestMapping(value = "/ceoet", method = RequestMethod.GET)
	public void creditsExchangeOrderOfOptimize(ModelMap model, HttpServletRequest req, OrderReqOfOptimize order) {
		try {
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(orderManageService.getGroupOrderExport(order));
		} catch (Exception e) {
			LogCvt.info("积分兑换订单下载异常：" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", e.getMessage());
		}
	}

}
