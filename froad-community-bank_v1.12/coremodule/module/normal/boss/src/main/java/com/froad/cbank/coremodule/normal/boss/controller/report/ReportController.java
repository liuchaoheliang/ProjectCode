package com.froad.cbank.coremodule.normal.boss.controller.report;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.excel.JxlsExcelUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.annotation.ImpExp;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.report.BaseReportReq;
import com.froad.cbank.coremodule.normal.boss.support.report.GroupOrderReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.MemberReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.MerchantReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.OrderReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.PayTypeReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.PresellOrderReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.ProductCategoryReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.ProductSellReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.SummaryReportSupport;
import com.froad.cbank.coremodule.normal.boss.support.report.TakeOutletReportSupport;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 报表
 * @ClassName ReportController
 * @author zxl
 * @date 2015年11月3日 上午10:07:59
 */
@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Resource
	OrderReportSupport orderReportSupport;
	@Resource
	ProductCategoryReportSupport productCategoryReportSupport;
	@Resource
	PayTypeReportSupport payTypeReportSupport;
	@Resource
	TakeOutletReportSupport takeOutletReportSupport;
	@Resource
	ProductSellReportSupport productSellReportSupport;
	@Resource
	MemberReportSupport memberReportSupport;
	@Resource
	MerchantReportSupport merchantReportSupport;
	@Resource
	SummaryReportSupport summaryReportSupport;
	@Resource
	PresellOrderReportSupport presellOrderReportSupport;
	@Resource
	GroupOrderReportSupport groupOrderReportSupport;
	/**
	 * 查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年11月3日 上午10:08:29
	 * @param model
	 */
	@Auth(keys={"boss_report_all_menu","boss_report_pre_menu","boss_report_group_menu","boss_report_order_menu","boss_report_goods_menu",
			"boss_report_merchant_menu","boss_report_cate_menu","boss_report_paytype_menu","boss_report_user_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(ModelMap model,BaseReportReq req){
		try {
			model.clear();
			LogCvt.info("报表列表查询条件："+JSON.toJSONString(req));
			if(StringUtils.isBlank(req.getStartTime())){
				throw new BossException("开始时间不能为空");
			}
			if(StringUtils.isBlank(req.getEndTime())){
				throw new BossException("结束时间不能为空");
			}
			switch (req.getType()) {
			case 1: //社区银行总分析报表
				model.putAll(summaryReportSupport.list(req));
				break;
			case 2: //商品预售销售报表
				model.putAll(presellOrderReportSupport.list(req));			
				break;
			case 3: //团购销售报表
				model.putAll(groupOrderReportSupport.list(req));	
				break;
			case 4: //订单分析报表
				model.putAll(orderReportSupport.list(req));
				break;
			case 5: //商品销售报表
				model.putAll(productSellReportSupport.list(req));
				break;
			case 6: //商户销售报表
				model.putAll(merchantReportSupport.list(req));
				break;
			case 7: //类目销售报表
				model.putAll(productCategoryReportSupport.list(req));
				break;
			case 8: //支付方式分析报表
				model.putAll(payTypeReportSupport.list(req));
				break;
			case 9: //提货网点分析报表
				model.putAll(takeOutletReportSupport.list(req));
				break;
			case 10: //会员注册分析报表
				model.putAll(memberReportSupport.list(req));
				break;
			default:
				throw new BossException("无效的报表类型");
			}
			
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 报表下载
	 * @tilte download
	 * @author zxl
	 * @date 2015年11月3日 下午5:56:27
	 * @param request
	 * @param response
	 * @param req
	 */
	@ImpExp
	@SuppressWarnings("rawtypes")
	@Auth(keys={"boss_report_all_export","boss_report_pre_export","boss_report_group_export","boss_report_order_export","boss_report_goods_export",
			"boss_report_merchant_export","boss_report_cate_export","boss_report_paytype_export","boss_report_user_export"})
	@RequestMapping(value="download",method = RequestMethod.GET)
	public void download(HttpServletRequest request,HttpServletResponse response, BaseReportReq req) {
		OutputStream os = null;
		int pageSize = 200;
		try {
			os = response.getOutputStream();
			req.setPageSize(pageSize);
			req.setPageNumber(1);
			
			List list = null;
			String resource = "";
			String name = "";
			
			if(StringUtils.isBlank(req.getStartTime())){
				throw new BossException("开始时间不能为空");
			}
			if(StringUtils.isBlank(req.getEndTime())){
				throw new BossException("结束时间不能为空");
			}
			
			switch (req.getType()) {
			case 1: //社区银行总分析报表
				name = "社区银行总分析报表.xls";
				resource = "/excel/summary-report.xls";
				list = summaryReportSupport.export(req);
				break;
			case 2: //商品预售销售报表
				name = "商品预售销售报表.xls";
				resource = "/excel/presellorder-report.xls";
				list = presellOrderReportSupport.export(req);
				break;
			case 3: //团购销售报表
				name = "团购销售报表.xls";
				resource = "/excel/grouporder-report.xls";
				list = groupOrderReportSupport.export(req);
				break;
			case 4: //订单分析报表
				name = "订单分析报表.xls";
				resource = "/excel/order-report.xls";
				list = orderReportSupport.export(req);
				break;
			case 5: //商品销售报表
				name = "商品销售报表.xls";
				resource = "/excel/productsell-report.xls";
				list = productSellReportSupport.export(req);
				break;
			case 6: //商户销售报表
				name="商户销售报表.xls";
				resource="/excel/merchant-report.xls";
				list = merchantReportSupport.export(req);
				break;
			case 7: //类目销售报表
				name="类目销售报表.xls";
				resource="/excel/productCategory-report.xls";
				list = productCategoryReportSupport.export(req);
				break;
			case 8: //支付方式分析报表
				name="支付方式分析报表.xls";
				resource="/excel/payType-report.xls";
				list = payTypeReportSupport.export(req);
				break;
			case 9: //提货网点分析报表
				name="提货网点分析报表.xls";
				resource="/excel/takeOutlet-report.xls";
				list = takeOutletReportSupport.export(req);
				break;
			case 10: //会员注册分析报表
				name="会员注册分析报表.xls";
				resource="/excel/member-report.xls";
				list = memberReportSupport.export(req);
				break;
			default:
				throw new BossException("无效的报表类型");
			}
			
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(name,"UTF-8"));
			response.setContentType("application/ms-excel");
			
			HashMap<String,Object> bean = new HashMap<String,Object>();
			bean.put("data", list);
			
			os.write(JxlsExcelUtil.export(new File(this.getClass().getResource(resource).toURI()), bean).toByteArray());
			
		}catch (BossException e) {
			HashMap<String,Object> h = new HashMap<String, Object>();
			h.put("code", e.getCode());
			h.put("message", e.getMessage());
			try {
				os.write(JSON.toJSONString(h).getBytes());
			} catch (IOException e1) {
			}
			LogCvt.error(e.getMessage(), e);
		}catch (Exception e) {
			HashMap<String,Object> h = new HashMap<String, Object>();
			h.put("code", ErrorEnums.syserr.getCode());
			h.put("message", ErrorEnums.syserr.getMsg());
			try {
				os.write(JSON.toJSONString(h).getBytes());
			} catch (IOException e1) {
			}
			LogCvt.error(e.getMessage(), e);
		}finally{
			try {
				os.flush();
				os.close();
			} catch (Exception e) {
				LogCvt.error(e.getMessage(), e);
			}
		}
	}
}
