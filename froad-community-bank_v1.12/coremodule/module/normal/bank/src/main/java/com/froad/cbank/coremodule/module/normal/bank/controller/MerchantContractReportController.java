package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.service.MerchantContractReportService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.bank.vo.ContractRankReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantAddTypePercentReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantContractDetailReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantContractDetailVo;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.vo.BankOperatorCheckVoRes;

/**
 * 类描述：相关业务类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-6-3下午5:41:54
 */

@Controller
@RequestMapping(value = "/report/merchantContract")
public class MerchantContractReportController extends BasicSpringController {
	@Resource
	MerchantContractReportService merchantContractReportService;
	@Resource
	BankOperatorService.Iface bankOperatorService;

	/**
	 * 方法描述：签约人商户数排行榜查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */
	
	@CheckPermission(keys={"form_merchantcontractors_menu"})
	@RequestMapping(value = "rank", method = RequestMethod.GET)
	public void queryMerchantContractRankList(ModelMap model, HttpServletRequest request, ContractRankReq voReq) {
		LogCvt.info("签约人商户数排行榜查询条件：" + JSON.toJSONString(voReq));
		try {
			String clientId = (String) request.getAttribute(Constants.CLIENT_ID);
			voReq.setClientId(clientId);
			model.putAll(merchantContractReportService.queryAddMerchantRankList(voReq));
		} catch (Exception e) {
			LogCvt.info("签约人商户数排行榜条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 方法描述：商户新增类型占比查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */
	@RequestMapping(value = "addPercent", method = RequestMethod.GET)
	public void queryMerchantAddPercent(ModelMap model, HttpServletRequest request, MerchantAddTypePercentReq req) {
		LogCvt.info("商户新增类型占比查询条件：" + JSON.toJSONString(req));
		try {
			String clientId = (String) request.getAttribute(Constants.CLIENT_ID);
			req.setClientId(clientId);
			model.putAll(merchantContractReportService.queryMerchantAddPercentList(req));
		} catch (Exception e) {
			LogCvt.info("商户新增类型占比查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 方法描述：签约人新增商户数排行查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */
	@RequestMapping(value = "addRank", method = RequestMethod.GET)
	public void queryAddMerchantTypeSort(ModelMap model, HttpServletRequest request, MerchantAddTypePercentReq req) {
		LogCvt.info("签约人新增商户数排行查询条件：" + JSON.toJSONString(req));
		try {
			String clientId = (String) request.getAttribute(Constants.CLIENT_ID);
			req.setClientId(clientId);
			model.putAll(merchantContractReportService.queryAddMerchantSortList(req));
		} catch (Exception e) {
			LogCvt.info("签约人新增商户数排行查询TException" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 方法描述：签约人商户统计详细列表查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */
	
	@CheckPermission(keys={"form_merchantcontractors_detaillist_bind"})
	@RequestMapping(value = "detailList", method = RequestMethod.GET)
	public void queryMerchantContractList(ModelMap model, HttpServletRequest request, MerchantContractDetailReq req) {
		LogCvt.info("签约人商户统计详细查询条件：" + JSON.toJSONString(req));
		try {
			String clientId = (String) request.getAttribute(Constants.CLIENT_ID);
			req.setClientId(clientId);
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(req.getBeginDate(), req.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			model.putAll(merchantContractReportService.queryContractDetailList(req));
		} catch (Exception e) {
			LogCvt.info("签约人商户统计详细数据查询TException" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 签约人商户统计详细列表下载
	 * @author froad
	 * @date 2015-6-02下午15:08:32
	 * @param model
	 * @param req
	 * @param resp
	 */
	
	@CheckPermission(keys={"form_merchantcontractors_detaillistexport"})
	@RequestMapping(value = "detailListExport", method = RequestMethod.GET)
	public void merchantContractExport(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			MerchantContractDetailReq voReq) throws Exception {
		LogCvt.info("商户信息统计详细数据查询导出条件：" + JSON.toJSONString(voReq));

		try {// 业务处理
			String clientId = request.getAttribute(Constants.CLIENT_ID) + "";
			voReq.setClientId(clientId);
			if (voReq.getUserId() == null || voReq.getToken() == null) {
				throw new BankException(EnumTypes.timeout.getCode(), EnumTypes.timeout.getMessage());
			}
			BankOperatorCheckVoRes resVo = bankOperatorService.checkToken(clientId, Long.valueOf(voReq.getUserId()),
					voReq.getToken());
			if (!resVo.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
				throw new BankException(EnumTypes.timeout.getCode(), resVo.getResult().getResultDesc());
			}
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(voReq.getBeginDate(), voReq.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			// 导出的数据
			List<List<String>> data = new ArrayList<List<String>>();
			HashMap<String, Object> mapMerchantDetailList = merchantContractReportService
					.queryExportContractDetailList(voReq);
			@SuppressWarnings("unchecked")
			List<MerchantContractDetailVo> merhcantDetailList = (List<MerchantContractDetailVo>) mapMerchantDetailList
					.get("merchantContractDetailList");
			// 封装list
			packageMerchantContractDetailList(data, merhcantDetailList);

			// 导出的标题
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("签约人");
			header.add("新增商户");
			header.add("动账商户");
			header.add("结余商户数");
			header.add("新增占比");
			header.add("订单数");
			header.add("订单金额");

			// 生成excel
			HSSFWorkbook book = ExcelUtil.generate(header, data, "签约人商户统计详细列表");

			// 下载excel
			// response.setContentType("text/html;charset=UTF-8");
			// response.setContentType("application/vnd.ms-excel");
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			String headerStr = "签约人商户统计详细列表-" + ExcelUtil.getFileShortName();
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(headerStr.getBytes("GBK"), "ISO-8859-1") + ".xls");
			OutputStream os = response.getOutputStream();
			book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			model.put("code", "9999");
			model.put("message", "下载异常：");
		}
	}
	
	/**
	 * 
	 * packageMerchantContractDetailList:封装数据到list中
	 *
	 * @author 明灿
	 * 2015年8月13日 下午8:17:14
	 * @param data
	 * @param merhcantDetailList
	 *
	 */
	private void packageMerchantContractDetailList(List<List<String>> data,
			List<MerchantContractDetailVo> merhcantDetailList) {
		if (merhcantDetailList != null && merhcantDetailList.size() > 0) {
			int index = 0;
			for (MerchantContractDetailVo voRes : merhcantDetailList) {
				List<String> list = new ArrayList<String>();
				list.add("" + (++index));
				list.add(voRes.getConstractStaff());// 签约人
				list.add(String.valueOf(voRes.getAddMerchantCount()));// 新增商户数
				list.add(String.valueOf(voRes.getChangeAmountCount()));// 动账商户数
				list.add(String.valueOf(voRes.getTotalMerchantCount()));// 结余商户数
				list.add(String.valueOf(voRes.getAddPercent() + "%"));// 新增占比
				list.add(String.valueOf(voRes.getOrderCount()));// 订单数
				list.add("¥" + String.valueOf(voRes.getTotalAmount()));// 订单金额
				data.add(list);
			}
		} 
	}
}
