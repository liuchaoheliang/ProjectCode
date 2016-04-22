package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.BankPointSettlementService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BaseVo;

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

	public static String SHOPPING = "0";// 购物类
	public static String FTF = "1";// 面对面
	/**
	 * 
	 * shoppingOrderTotal:购物类订单银行积分统计查询
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 上午10:37:58
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys = { "bank_order_integral_menu",
			"bank_order_integral_select_bind" })
	@RequestMapping(value = "shoppingTotal", method = RequestMethod.GET)
	public void shoppingOrderTotal(ModelMap model, HttpServletRequest request,
			BaseVo req) {
		model = this.validateTime(model, req);
		if (!model.isEmpty()) {
			model.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			return;
		}
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		try {
			model.putAll(bankPointSettlementService.getShoppingOrderTotal(req,
					SHOPPING));
		} catch (ParseException e) {
			LogCvt.info("积分报表-购物类订单统计列表异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "日期转化异常");
		} catch (TException e) {
			LogCvt.info("积分报表-购物类订单统计列表异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * validateTime:查询时间校验
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午4:31:27
	 * @param model
	 * @param req
	 * @return
	 */
	private ModelMap validateTime(ModelMap model, BaseVo req) {
		model.clear();
		if (!StringUtil.isNotBlank(req.getStartDate())) {
			model.put(ResultEnum.MESSAGE.getCode(), "结算开始时间不能为空");
			return model;
		}
		if (!StringUtil.isNotBlank(req.getEndDate())) {
			model.put(ResultEnum.MESSAGE.getCode(), "结算结束时间不能为空");
			return model;
		}
		return model;
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
	@CheckPermission(keys = { "bank_order_integral_menu",
			"bank_order_integral_select_bind" })
	@RequestMapping(value = "ftfTotal", method = RequestMethod.GET)
	public void getFTFOrderTotal(ModelMap model, HttpServletRequest request,
			BaseVo req) {
		model = this.validateTime(model, req);
		if (!model.isEmpty()) {
			model.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			return;
		}
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		try {
			model.putAll(
					bankPointSettlementService.getShoppingOrderTotal(req, FTF));
		} catch (ParseException e) {
			LogCvt.info("积分报表-面对面订单统计列表异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "日期转化异常");
		} catch (TException e) {
			LogCvt.info("积分报表-面对面订单统计列表异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * getMerchantTotal:商户汇总列表接口
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午2:00:04
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys = { "bank_order_integral_menu",
			"bank_order_integral_select_bind" })
	@RequestMapping(value = "merchantTotal", method = RequestMethod.GET)
	public void getMerchantTotal(ModelMap model, HttpServletRequest request,
			BaseVo req) {
		model = this.validateTime(model, req);
		if (!model.isEmpty()) {
			model.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			return;
		}
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		try {
			model.putAll(bankPointSettlementService.getMerchantTotal(req));
		} catch (ParseException e) {
			LogCvt.info("积分报表-商户汇总统计列表异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "日期转化异常");
		} catch (TException e) {
			LogCvt.info("积分报表-商户汇总统计列表异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
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
	@CheckPermission(keys = { "bank_order_integral_menu",
			"bank_order_integral_select_bind" })
	@RequestMapping(value = "total", method = RequestMethod.GET)
	public void getPointTotal(ModelMap model, HttpServletRequest request,
			BaseVo req) {
		model = this.validateTime(model, req);
		if (!model.isEmpty()) {
			model.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			return;
		}
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		try {
			model.putAll(bankPointSettlementService.getPointTotal(req));
		} catch (ParseException e) {
			LogCvt.info("积分报表-积分汇总统计异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "日期转化异常");
		} catch (TException e) {
			LogCvt.info("积分报表-积分汇总统计异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * getPointTotalReport:积分报表导出接口
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午3:20:34
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys = { "bank_order_integral_menu",
			"bank_order_integral_select_bind",
			"bank_order_integral_export_bind" })
	@RequestMapping(value = "report", method = RequestMethod.GET)
	public void getPointTotalReport(ModelMap model, HttpServletRequest request,
			BaseVo req) {
		model = this.validateTime(model, req);
		if (!model.isEmpty()) {
			model.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			return;
		}
		req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
		try {
			model.putAll(bankPointSettlementService.getPointTotalReport(req));
		} catch (ParseException e) {
			LogCvt.info("积分报表-积分汇总统计导出异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "日期转化异常");
		} catch (TException e) {
			LogCvt.info("积分报表-积分汇总统计导出异常:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}
}
