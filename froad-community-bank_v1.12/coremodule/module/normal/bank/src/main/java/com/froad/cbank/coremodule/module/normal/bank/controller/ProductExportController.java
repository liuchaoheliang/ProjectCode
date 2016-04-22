package com.froad.cbank.coremodule.module.normal.bank.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.service.GroupProductService;
import com.froad.cbank.coremodule.module.normal.bank.service.LineProductService;
import com.froad.cbank.coremodule.module.normal.bank.service.MerchantManageService;
import com.froad.cbank.coremodule.module.normal.bank.service.PreferentProductService;
import com.froad.cbank.coremodule.module.normal.bank.service.PresaleProductService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;
import com.froad.thrift.service.BankOperatorService;

/**
 * 商品导出
 * 
 * @author yfy
 *
 */
@Controller
@RequestMapping(value = "/productExport")
public class ProductExportController {

	@Resource
	private LineProductService lineProductService;
	@Resource
	private GroupProductService groupProductService;
	@Resource
	private PresaleProductService presaleProductService;
	@Resource
	private PreferentProductService famousPreferentProductService;
	@Resource
	private MerchantManageService mmService;
	@Resource
	BankOperatorService.Iface bankOperatorService;
	@Resource
	PreferentProductService preferentProductService;
	
	

	/**
	 * 
	 * MerchantExportOfOptimize:(商户列表导出优化).
	 *
	 * @author wufei 2015-9-6 下午02:45:16
	 * @param model
	 * @param req
	 * @param merchant
	 * @throws Exception
	 *
	 */
	
	@CheckPermission(keys={"merchant_list_export"})
	@RequestMapping(value = "/mhet",method = RequestMethod.GET)
	public void MerchantExportOfOptimize(ModelMap model,HttpServletRequest req,MerchantReq merchant){
		try {
			merchant.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(mmService.getMerchantListExport(merchant));
		} catch (Exception e) {
			model.clear();
			LogCvt.info("商户列表下载异常: " + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		} 
	}
	
	/**
	 * 
	 * groupProExportOfOptimize:团购商品下载优化
	 *
	 * @author 明灿 2015年9月6日 上午10:09:29
	 * @param model
	 * @param req
	 * @param voReq
	 * @throws Exception
	 *
	 */

	@CheckPermission(keys={"group_product_goet"})
	@RequestMapping(value = "/goet", method = RequestMethod.GET)
	public void groupProExportOfOptimize(ModelMap model, HttpServletRequest req, ProductVoReq voReq) {
		try {
			// 获取clientId
			voReq.setClientId((String) req.getAttribute(Constants.CLIENT_ID));
			model.putAll(groupProductService.getGroupListExport(voReq));
		} catch (Exception e) {
			model.clear();
			LogCvt.info("团购商品列表下载异常: " + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 
	 * presaleProExportOfOptimize:预售商品列表下载优化
	 *
	 * @author 明灿 2015年9月6日 下午1:44:33
	 * @param model
	 * @param req
	 * @param voReq
	 *
	 */
	
	@CheckPermission(keys={"presale_product_pret"})
	@RequestMapping(value = "/pret", method = RequestMethod.GET)
	public void presaleProExportOfOptimize(ModelMap model, HttpServletRequest req, ProductVoReq voReq) {
		try {
			// 获取clientId
			voReq.setClientId((String) req.getAttribute(Constants.CLIENT_ID));
			model.putAll(presaleProductService.getPresaleListExport(voReq));
		} catch (Exception e) {
			model.clear();
			LogCvt.info("预售商品列表下载异常: " + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 
	 * lineProExportOfOptimize:积分兑换商品列表下载
	 *
	 * @author 明灿 2015年9月6日 下午1:48:08
	 * @param model
	 * @param req
	 * @param voReq
	 *
	 */
	
	@CheckPermission(keys={"outline_present_export"})
	@RequestMapping(value = "/liet", method = RequestMethod.GET)
	public void lineProExportOfOptimize(ModelMap model, HttpServletRequest req, ProductVoReq voReq) {
		try {
			// 获取clientId
			voReq.setClientId((String) req.getAttribute(Constants.CLIENT_ID));
			model.putAll(lineProductService.getLineListExport(voReq));
		} catch (Exception e) {
			model.clear();
			LogCvt.info("积分兑换商品列表下载异常: " + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 
	 * preferentProExportOfOptimize:名优特惠列表条件查询导出优化
	 *
	 * @author 明灿 2015年9月6日 下午1:49:03
	 * @param model
	 * @param req
	 * @param voReq
	 *
	 */
	
	@CheckPermission(keys={"preferential_product_pret"})
	@RequestMapping(value = "/pfet", method = RequestMethod.GET)
	public void preferentProExportOfOptimize(ModelMap model, HttpServletRequest req, ProductVoReq voReq) {
		try {
			// 获取clientId
			voReq.setClientId((String) req.getAttribute(Constants.CLIENT_ID));
			model.putAll(preferentProductService.getPreferentListExport(voReq));
		} catch (Exception e) {
			model.clear();
			LogCvt.info("名优特惠列表下载异常: " + e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}


}
