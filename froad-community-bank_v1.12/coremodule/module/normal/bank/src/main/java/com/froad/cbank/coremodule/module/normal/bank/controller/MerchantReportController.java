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
import com.froad.cbank.coremodule.module.normal.bank.enums.ClientIdEnum;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.service.MerchantReportService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantBusiReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantBusiVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantDetailReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantDetailVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantOutletRePortResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantTrendReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantTypePercentReq;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.vo.BankOperatorCheckVoRes;

/**
 * 类描述：商户信息报表相关业务类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-6-2下午3:46:07
 */

@Controller
@RequestMapping(value = "/report/merchant")
public class MerchantReportController extends BasicSpringController {
	@Resource
	MerchantReportService merchantReportService;
	@Resource
	BankOperatorService.Iface bankOperatorService;

	/**
	 * 方法描述：商户走势列表
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */
	@CheckPermission(keys={"form_merchantinformation_menu"})
	@RequestMapping(value = "/trend", method = RequestMethod.GET)
	public void queryMerchantTrend(ModelMap model, HttpServletRequest request, MerchantTrendReq voReq) {
		LogCvt.info("商户走势查询条件：" + JSON.toJSONString(voReq));
		model.clear();
		try {
			String clientId = (String) request.getAttribute(Constants.CLIENT_ID);
			voReq.setClientId(clientId);
			// 在vo中校验
			// if(StringUtil.isBlank(voReq.getOrgCode())){
			// model.put("code", EnumTypes.thrift_err.getCode());
			// model.put("message", "机构号不能为空");
			// return;
			// }
			model.putAll(merchantReportService.queryMerchantTrendList(voReq));
		} catch (Exception e) {
			LogCvt.info("商户走势条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 方法描述：商户类型占比查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */

	@CheckPermission(keys={"form_merchantinformation_menu","form_merchantcontractors_menu"})
	@RequestMapping(value = "/typePercent", method = RequestMethod.GET)
	public void queryMerchantTypePercent(ModelMap model, HttpServletRequest request, MerchantTypePercentReq req) {
		LogCvt.info("商户类型占比查询条件：" + JSON.toJSONString(req));
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
			model.putAll(merchantReportService.queryMerchantTypePercentList(req));
		} catch (Exception e) {
			LogCvt.info("商户类型占比条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 方法描述：商户业务占比查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */
	
	@CheckPermission(keys={"form_merchantinformation_menu"})
	@RequestMapping(value = "/bussinessPercent", method = RequestMethod.GET)
	public void queryMerchantBusinessPercent(ModelMap model, HttpServletRequest request, MerchantTypePercentReq req) {
		LogCvt.info("商户业务占比查询条件：" + JSON.toJSONString(req));
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
			model.putAll(merchantReportService.queryBusinessPercentList(req));
		} catch (Exception e) {
			LogCvt.info("商户业务占比查询TException" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 方法描述：商户业务信息统计查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */
	@RequestMapping(value = "/bussiness", method = RequestMethod.GET)
	public void queryMerchantBusinessList(ModelMap model, HttpServletRequest request, MerchantBusiReq voReq) {
		LogCvt.info("商户业务信息统计查询条件：" + JSON.toJSONString(voReq));
		try {
			String clientId = (String) request.getAttribute(Constants.CLIENT_ID);
			voReq.setClientId(clientId);
			// 添加时间校验
			Map<String, String> result = DateUtilForReport.verifyDate(voReq.getBeginDate(), voReq.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get(DateUtilForReport.ERROR));
				return;
			}
			model.putAll(merchantReportService.queryMerchantBusinessList(voReq));
		} catch (Exception e) {
			LogCvt.info("商户业务信息统计条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 方法描述：商户信息统计详细数据查询分页
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @time: 2015年6月2日 下午5:42:15
	 */
	
	@CheckPermission(keys={"form_merchantinformation_detaillist_bind"})
	@RequestMapping(value = "/detailList", method = RequestMethod.GET)
	public void queryMerchantDetailList(ModelMap model, HttpServletRequest request, MerchantDetailReq req) {
		LogCvt.info("商户信息统计详细数据查询条件：" + JSON.toJSONString(req));
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
			model.putAll(merchantReportService.queryMerchantDetailList(req));
		} catch (Exception e) {
			LogCvt.info("商户信息统计详细数据查询TException" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 商户信息统计详细数据列表下载
	 * @author froad
	 * @date 2015-6-02下午15:08:32
	 * @param model
	 * @param req
	 * @param resp
	 */
	
	
	@CheckPermission(keys={"form_merchantinformation_detaillist_bind","form_merchantinformation_detaillistexport"})
	@RequestMapping(value = "/detailListExport", method = RequestMethod.GET)
	public void merchantReportDetailExport(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			MerchantDetailReq voReq) {
		LogCvt.info("商户信息统计详细数据查询导出条件：" + JSON.toJSONString(voReq));

		try {// 业务处理-登录验证
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

			HashMap<String, Object> mapMerchantDetailList = merchantReportService.queryExportMerchantDetailList(voReq);
			@SuppressWarnings("unchecked")
			List<MerchantDetailVo> merhcantDetailList = (List<MerchantDetailVo>) mapMerchantDetailList
					.get("merchantDetailList");
			packageMerchantList(data, merhcantDetailList);
			// 导出的标题
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("机构号");
			header.add("所属机构");
			header.add("新增商户数");
			header.add("动帐商户数");
			header.add("结余商户数");
			header.add("商户占比");
			header.add("订单数");
			header.add("订单金额");

			// 生成excel
			HSSFWorkbook book = ExcelUtil.generate(header, data, "商户信息统计详细数据");
			// 下载excel
			// response.setContentType("text/html;charset=UTF-8");
			// response.setContentType("application/vnd.ms-excel");
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			String headerStr = "商户信息统计详细数据-" + ExcelUtil.getFileShortName();
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
	 * packageMerchantList:封装数据到list中
	 *
	 * @author 明灿 2015年8月13日 下午8:09:55
	 * @param data
	 * @param merhcantDetailList
	 *
	 */
	private void packageMerchantList(List<List<String>> data, List<MerchantDetailVo> merhcantDetailList) {
		if (merhcantDetailList != null && merhcantDetailList.size() > 0) {
			int index = 0;
			for (MerchantDetailVo voRes : merhcantDetailList) {
				List<String> list = new ArrayList<String>();
				list.add("" + (++index));
				list.add(voRes.getOrgCode());// 机构编号
				list.add(voRes.getOrgName());// 机构名称
				list.add(String.valueOf(voRes.getAddCount()));// 新增数量
				list.add(String.valueOf(voRes.getChangeAmountCount()));// 动胀商户数
				list.add(String.valueOf(voRes.getTotalMerchantCount()));// 结余商户数
				list.add(voRes.getPercent() + "%");// 占比
				list.add(String.valueOf(voRes.getOrderCount()));// 订单数
				list.add("¥" + voRes.getOrderAmount().toString());// 订单金额
				data.add(list);
			}
		} 
	}

	/**
	 * @Title: 商户业务统计列表下载
	 * @author froad
	 * @date 2015-6-02下午15:08:32
	 * @param model
	 * @param req
	 * @param resp
	 */
	
	@CheckPermission(keys={"form_merchantinformation_bussinessexport"})
	@RequestMapping(value = "/bussinessExport", method = RequestMethod.GET)
	public void merchantReportBusinessExport(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			MerchantBusiReq voReq) {
		LogCvt.info("商户业务统计数据查询导出条件：" + JSON.toJSONString(voReq));
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
			HashMap<String, Object> mapMerchantBusiList = merchantReportService.queryMerchantBusinessList(voReq);

			@SuppressWarnings("unchecked")
			List<MerchantBusiVo> merhcantBusilList = (List<MerchantBusiVo>) mapMerchantBusiList
					.get("merchantBussinessList");
			// 封装list
			PackageMerchantBusinessList(data, merhcantBusilList);
			// 导出的标题
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("所属客户端");
			header.add("机构号");
			header.add("机构名称");
			header.add("面对面商户新增数");
			header.add("面对面商户注销数");
			header.add("面对面商户结余数");
			header.add("团购商户新增数");
			header.add("团购商户注销数");
			header.add("团购商户结余数");
			if (ClientIdEnum.ANHUI.getDescription().equals(clientId)) {
				header.add("名优特惠新增数");
				header.add("名优特惠注销数");
				header.add("名优特惠结余数");
			}
			// 生成excel
			HSSFWorkbook book = ExcelUtil.generate(header, data, "商户业务统计信息");

			// 下载excel
			// response.setContentType("text/html;charset=UTF-8");
			// response.setContentType("application/vnd.ms-excel");
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			String headerStr = "商户业务统计信息-" + ExcelUtil.getFileShortName();
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
	 * merchantOutletReportExport:商户门店信息导出
	 *
	 * @author 明灿 2015年10月12日 上午11:54:28
	 * @param model
	 * @param request
	 * @param response
	 * @param voReq
	 *
	 */
	@CheckPermission(keys={"form_merchantinformation_outletexport"})
	@RequestMapping(value = "/outletExport", method = RequestMethod.GET)
	public void merchantOutletReportExport(ModelMap model,
			HttpServletRequest request, HttpServletResponse response,
			MerchantBusiReq voReq) {
		LogCvt.info("商户门店信息导出条件：" + JSON.toJSONString(voReq));
		try {
			String clientId = (String) request.getAttribute("CLIENT_ID");
			voReq.setClientId(clientId);
			if ((voReq.getUserId() == null) || (voReq.getToken() == null)) {
				throw new BankException(EnumTypes.timeout.getCode(),
						EnumTypes.timeout.getMessage());
			}
			BankOperatorCheckVoRes resVo = this.bankOperatorService.checkToken(
					clientId, Long.valueOf(voReq.getUserId()).longValue(),
					voReq.getToken());
			if (!resVo.getResult().getResultCode()
					.equals(EnumTypes.success.getCode())) {
				throw new BankException(EnumTypes.timeout.getCode(), resVo
						.getResult().getResultDesc());
			}

			Map<String, String> result = DateUtilForReport.verifyDate(
					voReq.getBeginDate(), voReq.getEndDate());
			if (result.size() > 0) {
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", result.get("message"));
				return;
			}

			List<List<String>> data = new ArrayList<List<String>>();
			HashMap<String, Object> mapMerchantBusiList = this.merchantReportService
					.queryMerchantOutletList(voReq);

			@SuppressWarnings("unchecked")
			List<MerchantOutletRePortResVo> merhcantOutletList = (List<MerchantOutletRePortResVo>) mapMerchantBusiList
					.get("merchantOutletList");

			PackageMerchantOutletList(data, merhcantOutletList);

			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("支行");
			header.add("网点");
			header.add("网点地址");
			header.add("有效合作商户");
			header.add("商户门店");

			HSSFWorkbook book = ExcelUtil.generate(header, data, "商户门店信息");

			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			String headerStr = "商户门店信息-" + ExcelUtil.getFileShortName();
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(headerStr.getBytes("GBK"), "ISO-8859-1")
					+ ".xls");
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
	 * PackageMerchantBusinessList:封装数据到list中
	 *
	 * @author 明灿
	 * 2015年8月13日 下午8:14:14
	 * @param data
	 * @param merhcantBusilList
	 *
	 */
	private void PackageMerchantBusinessList(List<List<String>> data, List<MerchantBusiVo> merhcantBusilList) {
		if (merhcantBusilList != null && merhcantBusilList.size() > 0) {
			int index = 0;
			for (MerchantBusiVo voRes : merhcantBusilList) {
				List<String> list = new ArrayList<String>();
				list.add("" + (++index));
				list.add(voRes.getClientId());// 所属客户端
				list.add(voRes.getOrgCode());// 机构编号
				list.add(voRes.getOrgName());// 机构名称
				list.add(String.valueOf(voRes.getFaceAddCount()));// 面对面商户新增数
				list.add(String.valueOf(voRes.getFaceCancelCount()));// 面对面商户注销数
				list.add(String.valueOf(voRes.getFaceTotalCount()));// 面对面商户结余数
				list.add(String.valueOf(voRes.getGroupAddCount()));// 团购商户新增数
				list.add(String.valueOf(voRes.getGroupCancelCount()));// 团购商户注销数
				list.add(String.valueOf(voRes.getGroupTotalCount()));// 团购商户结余数
				// 重庆去除以下字段
				if (ClientIdEnum.ANHUI.getDescription().equals(voRes.getClientId())) {
					list.add(String.valueOf(voRes.getSpecialAddCount()));// 名优特惠新增数
					list.add(String.valueOf(voRes.getSpecialCancelCount()));// 名优特惠注销数
					list.add(String.valueOf(voRes.getSpecialTotalCount()));// 名优特惠结余数
				}
				data.add(list);
			}
		}
	}
	
	/**
	 * 
	 * PackageMerchantOutletList:门店商户信息封装到list中
	 *
	 * @author 明灿 2015年9月1日 下午12:33:18
	 * @param data
	 * @param merhcantOutletList
	 *
	 */
	private void PackageMerchantOutletList(List<List<String>> data, List<MerchantOutletRePortResVo> merhcantOutletList) {
		if (merhcantOutletList != null && merhcantOutletList.size() > 0) {
			int index = 0;
			for (MerchantOutletRePortResVo voRes : merhcantOutletList) {
				List<String> list = new ArrayList<String>();
				list.add("" + (++index));
				list.add(voRes.getOrgName());
				list.add(voRes.getOutletName());
				list.add(voRes.getOutletAddress());
				list.add(voRes.getMerchantName());
				list.add(String.valueOf(voRes.getMerchantOutletCount()));
				data.add(list);
			}
		}
	}
}
