package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.OrderManageService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BoutiqueOrderReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.DeliveryCompanyReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderReqOfOptimize;

/**
 * 订单管理
 * 
 * @author ylchu
 *
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BasicSpringController {

	@Resource
	private OrderManageService orderManageService;

	/**
	 * 
	 * boutiqueShoppingOrderList:精品商城订单列表
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 下午1:40:19
	 * @param model
	 * @param req
	 * @param order
	 *
	 */
	@CheckPermission(keys = { "boutiquemallorder_menu", "boutiquemallorder__select_bind" })
	@RequestMapping(value = "/bsol", method = RequestMethod.POST)
	public void boutiqueShoppingOrderList(ModelMap model, HttpServletRequest req, @RequestBody BoutiqueOrderReq reqVo) {
		try {
			// 时间校验
			Map<String, String> verifyMap = DateUtilForReport.verifyDate(reqVo.getStartDate(), reqVo.getEndDate());
			if (!verifyMap.isEmpty()) {
				model.put("code", "9999");
				model.put("message", verifyMap.get(DateUtilForReport.ERROR));
				return;
			}

			reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			// order.setType(ProductTypeEnum.PRESALE.getCode());
			model.putAll(orderManageService.boutiqueShoppingOrderList(reqVo));
		} catch (Exception e) {
			LogCvt.info("预售订单列表查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 精品商城订单详情查询
	 * 说明   description of the class
	 * 创建日期  2016年2月26日  上午11:19:12
	 * 作者  artPing
	 * 参数  @param model
	 * 参数  @param req
	 * 参数  @param subOrderId
	 * 参数  @param type
	 */
	@RequestMapping(value = "/boutiqueDetail", method = RequestMethod.GET)
	public void boutiqueDetail(ModelMap model, HttpServletRequest req, String subOrderId, String type) {

		try {
			if (!(StringUtil.isNotBlank(subOrderId) && StringUtil.isNotBlank(type))) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "订单编号不能为空");
				return;
			}
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(orderManageService.getBoutiqueOrderDetail(clientId, subOrderId, type, null));
		} catch (Exception e) {
			LogCvt.info("精品商城订单详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * presaleOrderListOfOptimize:预售订单列表优化
	 *
	 * @author 明灿 2015年8月31日 上午11:24:46
	 * @param model
	 * @param req
	 * @param order
	 *
	 */
	@CheckPermission(keys = { "boutiqueorderlist_menu", "boutiqueorderlist_select_bind" })
	@RequestMapping(value = "/polt", method = RequestMethod.POST)
	public void presaleOrderListOfOptimize(ModelMap model, HttpServletRequest req,
			@RequestBody OrderReqOfOptimize order) {
		try {
			// 时间校验
			Map<String, String> verifyMap = DateUtilForReport.verifyDate(order.getStartDate(), order.getEndDate());
			if (!verifyMap.isEmpty()) {
				model.put("code", "9999");
				model.put("message", verifyMap.get(DateUtilForReport.ERROR));
				return;
			}
			if (!StringUtil.isNotBlank(order.getDeliveryOption())) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "配送方式不能为空");
				return;
			}
			// 类型非空校验
			verifyType(model, order);
			long begin = System.currentTimeMillis();
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			// order.setType(ProductTypeEnum.PRESALE.getCode());
			model.putAll(orderManageService.getOrderList(order));
			Monitor.send(MonitorEnums.bank_preferential_order_lt, String.valueOf(System.currentTimeMillis() - begin));
		} catch (Exception e) {
			LogCvt.info("预售订单列表查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * verifyType:类型非空校验
	 *
	 * @author 明灿 2015年10月10日 下午4:37:11
	 * @param model
	 * @param order
	 *
	 */
	private void verifyType(ModelMap model, OrderReqOfOptimize order) {
		if (StringUtil.isBlank(order.getType())) {
			model.put("code", "9999");
			model.put("message", "查询类型type不能为空");
			return;
		}
	}

	/**
	 * 
	 * presaleOrderDetailOfOptimize:预售订单详情查询优化
	 *
	 * @author 明灿 2015年9月1日 下午6:44:47
	 * @param model
	 * @param req
	 * @param subOrderId
	 * @param type
	 *
	 */
	@CheckPermission(keys = { "grouporderdetail", "boutiqueorderlist_detail_bind" })
	@RequestMapping(value = "/podl", method = RequestMethod.GET)
	public void presaleOrderDetailOfOptimize(ModelMap model, HttpServletRequest req, String subOrderId, String type,
			String deliveryOption) {
		try {
			if (!(StringUtil.isNotBlank(subOrderId) && StringUtil.isNotBlank(type))) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "订单编号不能为空");
				return;
			}
			if (!(StringUtil.isNotBlank(deliveryOption) && StringUtil.isNotBlank(type))) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "配送方式不能为空");
				return;
			}
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(orderManageService.getOrderDetail(clientId, subOrderId, type, deliveryOption));
		} catch (Exception e) {
			LogCvt.info("预售订单详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 团购订单列表查询优化
	 * @author ylchu
	 * @param model
	 * @param req
	 */

	@CheckPermission(keys = { "grouporderlist_menu", "grouporderlist_select_bind", "grouporderlist_detail_bind" })
	@RequestMapping(value = "/golt", method = RequestMethod.POST)
	public void groupOrderList(ModelMap model, HttpServletRequest req, @RequestBody OrderReqOfOptimize order) {
		try {
			// 时间校验
			Map<String, String> verifyMap = DateUtilForReport.verifyDate(order.getStartDate(), order.getEndDate());
			if (!verifyMap.isEmpty()) {
				model.put("code", "9999");
				model.put("message", verifyMap.get(DateUtilForReport.ERROR));
				return;
			}
			// 类型非空校验
			verifyType(model, order);
			long begin = System.currentTimeMillis();
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			// order.setClientId("anhui");
			// order.setType(ProductTypeEnum.GROUP.getCode());
			model.putAll(orderManageService.getOrderList(order));
			Monitor.send(MonitorEnums.bank_group_order_lt, String.valueOf(System.currentTimeMillis() - begin));
		} catch (Exception e) {
			LogCvt.info("团购订单列表查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * groupOrderDetailOfOptimize:团购订单详情优化
	 *
	 * @author 明灿 2015年8月28日 下午3:55:01
	 * @param model
	 * @param req
	 * @param subOrderId
	 * @param type
	 *
	 */

	@CheckPermission(keys = { "settlement_order_detail_group", "setquery_detail_bind", "grouporderlist_detail_bind" })
	@RequestMapping(value = "/godl", method = RequestMethod.GET)
	public void groupOrderDetailOfOptimize(ModelMap model, HttpServletRequest req, String subOrderId, String type) {

		try {
			if (!(StringUtil.isNotBlank(subOrderId) && StringUtil.isNotBlank(type))) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "订单编号不能为空");
				return;
			}
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(orderManageService.getOrderDetail(clientId, subOrderId, type, null));
			// model.putAll(orderManageService.orderSubDetail("anhui",
			// "05C868210000", "1"));
		} catch (Exception e) {
			LogCvt.info("团购订单详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * getCashierOrderList:面对面订单列表
	 *
	 * @author 明灿 2015年9月6日 上午9:41:49
	 * @param model
	 * @param req
	 * @param order
	 *
	 */

	@CheckPermission(keys = { "faceorderlist_menu", "faceorderlist_select_bind" })
	@RequestMapping(value = "/colt", method = RequestMethod.POST)
	public void getCashierOrderList(ModelMap model, HttpServletRequest req, @RequestBody OrderReqOfOptimize order) {
		try {
			// 时间校验
			Map<String, String> verifyMap = DateUtilForReport.verifyDate(order.getStartDate(), order.getEndDate());
			if (!verifyMap.isEmpty()) {
				model.put("code", "9999");
				model.put("message", verifyMap.get(DateUtilForReport.ERROR));
				return;
			}
			// 类型非空校验
			this.verifyType(model, order);
			long begin = System.currentTimeMillis();
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			// order.setType(ProductTypeEnum.CASHIER.getCode());
			model.putAll(orderManageService.getPointProOrderList(order));
			Monitor.send(MonitorEnums.bank_confront_order_lt, String.valueOf(System.currentTimeMillis() - begin));
		} catch (Exception e) {
			LogCvt.info("面对面订单列表查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * cashierOrderDetailOfOptimize:面对面订单详情优化
	 *
	 * @author 明灿 2015年9月2日 下午4:35:16
	 * @param model
	 * @param req
	 * @param subOrderId
	 * @param type
	 *
	 */

	@CheckPermission(keys = { "setquery_detail_bind", "faceorderdetail", "settlement_order_detail_face",
			"faceorderlist_detail_bind" })
	@RequestMapping(value = "/codl", method = RequestMethod.GET)
	public void cashierOrderDetailOfOptimize(ModelMap model, HttpServletRequest req, String subOrderId, String type) {

		try {
			if (!(StringUtil.isNotBlank(subOrderId) && StringUtil.isNotBlank(type))) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "子订单号或类型不能为空");
				return;
			}
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(orderManageService.getCashierOrderDetail(clientId, subOrderId, type));
		} catch (Exception e) {
			LogCvt.info("面对面订单详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * famousPreferentOrderListOfOptimize:名优特惠订单列表查询优化
	 *
	 * @author 明灿 2015年9月1日 下午2:08:51
	 * @param model
	 * @param req
	 * @param order
	 *
	 */

	@CheckPermission(keys = { "famousorderlist_menu", "famousorderlist_select_bind" })
	@RequestMapping(value = "/fpolt", method = RequestMethod.POST)
	public void famousPreferentOrderListOfOptimize(ModelMap model, HttpServletRequest req,
			@RequestBody OrderReqOfOptimize order) {
		try {
			// 时间校验
			Map<String, String> verifyMap = DateUtilForReport.verifyDate(order.getStartDate(), order.getEndDate());
			if (!verifyMap.isEmpty()) {
				model.put("code", "9999");
				model.put("message", verifyMap.get(DateUtilForReport.ERROR));
				return;
			}
			// 类型非空校验
			verifyType(model, order);
			long begin = System.currentTimeMillis();
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(orderManageService.getOrderList(order));
			Monitor.send(MonitorEnums.bank_preferent_order_lt, String.valueOf(System.currentTimeMillis() - begin));
		} catch (Exception e) {
			LogCvt.info("名优特惠订单列表查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * privilegeOrderDetail:名优特惠详情查询
	 *
	 * @author 明灿 2015年9月1日 下午2:13:21
	 * @param model
	 * @param req
	 * @param subOrderId
	 * @param type
	 *
	 */

	@CheckPermission(keys = { "setquery_detail_bind", "famousorderdetail", "settlement_order_detail_famous",
			"famousorderlist_detail_bind" })
	@RequestMapping(value = "/fpdl", method = RequestMethod.GET)
	public void privilegeOrderDetail(ModelMap model, HttpServletRequest req, String subOrderId, String type) {
		try {
			if (!(StringUtil.isNotBlank(subOrderId) && StringUtil.isNotBlank(type))) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "订单编号不能为空");
				return;
			}
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(orderManageService.getOrderDetail(clientId, subOrderId, type, null));
		} catch (Exception e) {
			LogCvt.info("名优特惠订单详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * creditsExchangeOrderListOfOptimize:积分商品订单优化
	 *
	 * @author 明灿 2015年9月4日 上午9:45:20
	 * @param model
	 * @param req
	 * @param order
	 *
	 */

	@CheckPermission(keys = { "integralorderlist_menu", "integralorderlist_select_bind" })
	@RequestMapping(value = "/ceolt", method = RequestMethod.POST)
	public void creditsExchangeOrderListOfOptimize(ModelMap model, HttpServletRequest req,
			@RequestBody OrderReqOfOptimize order) {
		try {
			// 时间校验
			Map<String, String> verifyMap = DateUtilForReport.verifyDate(order.getStartDate(), order.getEndDate());
			if (!verifyMap.isEmpty()) {
				model.put("code", "9999");
				model.put("message", verifyMap.get(DateUtilForReport.ERROR));
				return;
			}
			// 类型非空校验
			verifyType(model, order);
			long begin = System.currentTimeMillis();
			order.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			// order.setType(ProductTypeEnum.LINEDOWN.getCode());
			// order.setClientId("anhui");
			model.putAll(orderManageService.getPointProOrderList(order));
			Monitor.send(MonitorEnums.bank_lien_order_lt, String.valueOf(System.currentTimeMillis() - begin));
		} catch (Exception e) {
			LogCvt.info("积分兑换订单列表查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * creditsExchangeDetailOfOptimize:积分兑换详情查询优化
	 *
	 * @author 明灿 2015年9月1日 下午6:47:42
	 * @param model
	 * @param req
	 * @param subOrderId
	 * @param type
	 *
	 */

	@CheckPermission(keys = { "integralorderdetailpayoutline", "integralorderlist_detail_bind" })
	@RequestMapping(value = "/cedl", method = RequestMethod.GET)
	public void creditsExchangeDetailOfOptimize(ModelMap model, HttpServletRequest req, String subOrderId,
			String type) {
		try {
			if (!(StringUtil.isNotBlank(subOrderId) && StringUtil.isNotBlank(type))) {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "订单编号不能为空");
				return;
			}
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(orderManageService.getOrderDetail(clientId, subOrderId, type, null));
		} catch (Exception e) {
			LogCvt.info("积分兑换订单详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 获取大订单详情查询
	 * @author yfy
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/sudl", method = RequestMethod.GET)
	public void creditsDetail(ModelMap model, HttpServletRequest req, String orderId) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(orderManageService.orderDetail(clientId, orderId));
		} catch (Exception e) {
			LogCvt.info("大订单详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 获取物流列表
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "dclt", method = RequestMethod.POST)
	public void DeliveryCompanyList(ModelMap model, HttpServletRequest req,
			@RequestBody DeliveryCompanyReq deliveryCompany) {
		try {
			deliveryCompany.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(orderManageService.getDeliveryCompany(deliveryCompany));
		} catch (TException e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

}
