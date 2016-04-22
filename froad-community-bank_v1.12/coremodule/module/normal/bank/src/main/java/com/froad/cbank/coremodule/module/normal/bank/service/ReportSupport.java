package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.data.thrift.service.BankUserDefinedService;
import com.data.thrift.vo.BankUserDefinedRequestVo;
import com.data.thrift.vo.BankUserDefinedResponseVo;
import com.data.thrift.vo.OriginVo;
import com.data.thrift.vo.PlatType;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.CountTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.LatitudeTupeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.AmountQuotaResp;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.BaseRespVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.DefineTaskReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.DefineTaskResp;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.MemberQuotaResp;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.MerchantInfoResp;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.OrderQuotaResp;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.ProductInfoResp;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.ReportBankReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.report.ReprotResp;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.ReportService;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.coremodule.DefineTaskReqVo;
import com.froad.thrift.vo.coremodule.DefineTaskRespPageVo;
import com.froad.thrift.vo.coremodule.DefineTaskRespVo;
import com.froad.thrift.vo.coremodule.ReportReqVo;
import com.froad.thrift.vo.coremodule.ReportRespPageVo;
import com.froad.thrift.vo.coremodule.ReportRespVo;

/**
 * bruce 2015年11月30日 下午6:54:42
 */
@Service
public class ReportSupport {

	@Resource
	ReportService.Iface reportService;
	@Resource
	BankUserDefinedService.Iface bankUserDefinedService;
	@Resource
	BankOperatorService.Iface bankOperatorService;

	public Map<String, Object> getReportRespPage(ReportBankReqVo voReq,
			HttpServletRequest req) throws Exception {
		LogCvt.info("银行报表--请求参数:" + JSON.toJSONString(voReq));
		Map<String, Object> map = new HashMap<String, Object>();

		PageVo page = new PageVo();
		page.setPageNumber(voReq.getPageNumber());
		page.setPageSize(voReq.getPageSize());
		
		ReportReqVo reportReqVo = new ReportReqVo();
		BeanUtils.copyProperties(reportReqVo, voReq);
		
		/**
		 * 用户指标查询 根据筛选时间计算 月和周
		 */
		setReportReqVo(voReq, reportReqVo);
		/**
		 * 自定义查询申请
		 */
		if (voReq.getCountType().equals(CountTypeEnum.DIY.getCode())) {
			if(StringUtil.isBlank(voReq.getStartDate()) || StringUtil.isBlank(voReq.getEndDate())){
				map.put("code", EnumTypes.empty.getCode());
				map.put("message", "自定义查询起始日期不能为空");
				return map;
			}else{
			
				BankUserDefinedRequestVo bankUserDefinedRequestVo = new BankUserDefinedRequestVo();
				// 请求实体
				
				bankUserDefinedRequestVo.setOriginVo(getOriginVo(req));
				BeanUtils.copyProperties(bankUserDefinedRequestVo, voReq);
				
				LogCvt.info("银行报表--自定义查询参数:"
						+ JSON.toJSONString(bankUserDefinedRequestVo));
				BankUserDefinedResponseVo bankUserDefinedResponseVo = bankUserDefinedService.applyUserdefinedQuery(bankUserDefinedRequestVo);
				LogCvt.info("银行报表--自定义查询返回数据:"
						+ JSON.toJSONString(bankUserDefinedResponseVo));
	
				if (EnumTypes.success.getCode().equals(
						bankUserDefinedResponseVo.getResultVo().getResultCode())) {
					map.put("code", EnumTypes.success.getCode());
					map.put("message", "申请成功");
				} else {
					map.put("code", EnumTypes.fail.getCode());
					map.put("message", bankUserDefinedResponseVo.getResultVo()
							.getResultDesc());
				}
			}
		} else {
			LogCvt.info("银行报表--查询参数:" + JSON.toJSONString(reportReqVo));
			reportReqVo=this.exReportReqVo(reportReqVo);
			ReportRespPageVo reportRespPage = reportService.getRespPage(page,reportReqVo);
			LogCvt.info("银行报表--返回数据:" + JSON.toJSONString(reportRespPage));
			if (reportRespPage.getReportResp() != null
					&& reportRespPage.getReportResp().size() > 0) {
				List<ReportRespVo> reportRespVoList = reportRespPage
						.getReportResp();

				List<ReprotResp> reprotRespList = new ArrayList<ReprotResp>();
				if (reportRespVoList != null && reportRespVoList.size() > 0) {
					ReprotResp vo = null;
					for (ReportRespVo resp : reportRespVoList) {
						vo = new ReprotResp();
						// 金额指标
						if (resp.getAmountLat() != null) {
							AmountQuotaResp amountLat = new AmountQuotaResp();
							BeanUtils.copyProperties(amountLat,
									resp.getAmountLat());
							vo.setAmountLat(amountLat);
						}
						// 订单指标
						if (resp.getOrderLat() != null) {
							OrderQuotaResp orderLat = new OrderQuotaResp();
							BeanUtils.copyProperties(orderLat,
									resp.getOrderLat());
							vo.setOrderLat(orderLat);
						}
						// 商户指标
						if (resp.getMerchantInfoRespVo() != null) {
							MerchantInfoResp merchantInfoResp = new MerchantInfoResp();
							BeanUtils.copyProperties(merchantInfoResp,
									resp.getMerchantInfoRespVo());
							vo.setMerchantLat(merchantInfoResp);
						}
						// 商品指标
						if (resp.getProductInfoRespVo() != null) {
							ProductInfoResp productInfoResp = new ProductInfoResp();
							BeanUtils.copyProperties(productInfoResp,
									resp.getProductInfoRespVo());
							vo.setProductLat(productInfoResp);
						}
						// 用户指标
						if (resp.getMemberLat() != null) {
							MemberQuotaResp memberLat = new MemberQuotaResp();
							memberLat.setMemberCount(resp.getMemberLat().getConsumptionMemberCount());
							memberLat.setMemberComulationSum(resp.getMemberLat().getConsumptionMemberComulation());
							vo.setMemberLat(memberLat);
						}
						// 基本信息
						if (resp.getBaseRespVo() != null) {
							BaseRespVo base = new BaseRespVo();
							BeanUtils.copyProperties(base,
									resp.getBaseRespVo());
							vo.setBase(base);
						}
						reprotRespList.add(vo);
					}
				}
				map.put("latList", reprotRespList);
			}
			if (reportRespPage.getPage() != null) {
				map.put("pageCount", reportRespPage.getPage().getPageCount());
				map.put("pageNumber", reportRespPage.getPage().getPageNumber());
				map.put("totalCount", reportRespPage.getPage().getTotalCount());
				map.put("hasNext", reportRespPage.getPage().getPageCount() > reportRespPage.getPage().getPageNumber());
			}
		}
		return map;
	}
	
	
	/**
	 * 转换reportReqVo -防止前端数据统一请求
	 * @param reportReqVo
	 * @return
	 */
	public ReportReqVo exReportReqVo(ReportReqVo reportReqVo){
		if( LatitudeTupeEnum.ORDER.getDescription().equalsIgnoreCase(reportReqVo.getLatitudeTupe())){//因订单和金额server对应一个sql，需要区分
			reportReqVo.setCheckedAmount(false);
			reportReqVo.setCheckedAmountCumulation(false);
			reportReqVo.setCheckedAmountCumulationTurnover(false);
			reportReqVo.setCheckedAmountRefund(false);
			reportReqVo.setCheckedAmountTurnover(false);
			
			reportReqVo.setCheckedProductCumulation(false);
			reportReqVo.setCheckedProductDownComulation(false);
			reportReqVo.setCheckedProductDownSum(false);
			reportReqVo.setCheckedProductSum(false);
			 
			reportReqVo.setCheckedMerchantSum(false);
			reportReqVo.setCheckedMerchantCancelContract(false);
			reportReqVo.setCheckedMerchantCumulation(false);
			reportReqVo.setCheckedOutletCount(false);
			reportReqVo.setCheckedOutletCumulation(false);
			
			reportReqVo.setCheckedMemberComulationCount(false);
			reportReqVo.setCheckedMemberCount(false);
			
		}else if( LatitudeTupeEnum.AMOUNT.getDescription().equalsIgnoreCase(reportReqVo.getLatitudeTupe())){//因订单和金额server对应一个sql，需要区分
			reportReqVo.setCheckedOrderCount(false);
			reportReqVo.setCheckedOrderCumulation(false);
			reportReqVo.setCheckedOrderCumulationTurnover(false);
			reportReqVo.setCheckedOrderRefund(false);
			reportReqVo.setCheckedOrderTurnover(false);
			
			reportReqVo.setCheckedProductCumulation(false);
			reportReqVo.setCheckedProductDownComulation(false);
			reportReqVo.setCheckedProductDownSum(false);
			reportReqVo.setCheckedProductSum(false);
			 
			reportReqVo.setCheckedMerchantSum(false);
			reportReqVo.setCheckedMerchantCancelContract(false);
			reportReqVo.setCheckedMerchantCumulation(false);
			reportReqVo.setCheckedOutletCount(false);
			reportReqVo.setCheckedOutletCumulation(false);
			
			reportReqVo.setCheckedMemberComulationCount(false);
			reportReqVo.setCheckedMemberCount(false);
			
		}else if( LatitudeTupeEnum.MERCHANT.getDescription().equalsIgnoreCase(reportReqVo.getLatitudeTupe())){
			reportReqVo.setCheckedOrderCount(false);
			reportReqVo.setCheckedOrderCumulation(false);
			reportReqVo.setCheckedOrderCumulationTurnover(false);
			reportReqVo.setCheckedOrderRefund(false);
			reportReqVo.setCheckedOrderTurnover(false);
			
			reportReqVo.setCheckedAmount(false);
			reportReqVo.setCheckedAmountCumulation(false);
			reportReqVo.setCheckedAmountCumulationTurnover(false);
			reportReqVo.setCheckedAmountRefund(false);
			reportReqVo.setCheckedAmountTurnover(false);
			
			reportReqVo.setCheckedProductCumulation(false);
			reportReqVo.setCheckedProductDownComulation(false);
			reportReqVo.setCheckedProductDownSum(false);
			reportReqVo.setCheckedProductSum(false);
			
			reportReqVo.setCheckedMemberComulationCount(false);
			reportReqVo.setCheckedMemberCount(false);
		}else if( LatitudeTupeEnum.PRODUCT.getDescription().equalsIgnoreCase(reportReqVo.getLatitudeTupe())){
			reportReqVo.setCheckedOrderCount(false);
			reportReqVo.setCheckedOrderCumulation(false);
			reportReqVo.setCheckedOrderCumulationTurnover(false);
			reportReqVo.setCheckedOrderRefund(false);
			reportReqVo.setCheckedOrderTurnover(false);
			
			reportReqVo.setCheckedAmount(false);
			reportReqVo.setCheckedAmountCumulation(false);
			reportReqVo.setCheckedAmountCumulationTurnover(false);
			reportReqVo.setCheckedAmountRefund(false);
			reportReqVo.setCheckedAmountTurnover(false);
			
			reportReqVo.setCheckedMerchantSum(false);
			reportReqVo.setCheckedMerchantCancelContract(false);
			reportReqVo.setCheckedMerchantCumulation(false);
			reportReqVo.setCheckedOutletCount(false);
			reportReqVo.setCheckedOutletCumulation(false);
			
			reportReqVo.setCheckedMemberComulationCount(false);
			reportReqVo.setCheckedMemberCount(false);
		}else if( LatitudeTupeEnum.MERMBER.getDescription().equalsIgnoreCase(reportReqVo.getLatitudeTupe())){
			reportReqVo.setCheckedOrderCount(false);
			reportReqVo.setCheckedOrderCumulation(false);
			reportReqVo.setCheckedOrderCumulationTurnover(false);
			reportReqVo.setCheckedOrderRefund(false);
			reportReqVo.setCheckedOrderTurnover(false);
			
			reportReqVo.setCheckedAmount(false);
			reportReqVo.setCheckedAmountCumulation(false);
			reportReqVo.setCheckedAmountCumulationTurnover(false);
			reportReqVo.setCheckedAmountRefund(false);
			reportReqVo.setCheckedAmountTurnover(false);
			
			reportReqVo.setCheckedMerchantSum(false);
			reportReqVo.setCheckedMerchantCancelContract(false);
			reportReqVo.setCheckedMerchantCumulation(false);
			reportReqVo.setCheckedOutletCount(false);
			reportReqVo.setCheckedOutletCumulation(false);
			
			reportReqVo.setCheckedProductCumulation(false);
			reportReqVo.setCheckedProductDownComulation(false);
			reportReqVo.setCheckedProductDownSum(false);
			reportReqVo.setCheckedProductSum(false); 
		}
		return reportReqVo;
	}

	/**
	 * 
	 * getOriginVo:(封装OriginVo源对象).
	 *
	 * @author wufei
	 * 2015-12-7 上午10:53:55
	 * @param req
	 * @return
	 * @throws NumberFormatException
	 * @throws TException
	 *
	 */
	public OriginVo getOriginVo(HttpServletRequest req) throws NumberFormatException, TException{
		OriginVo originVo = new OriginVo();
		originVo.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
		originVo.setOperatorIp(TargetObjectFormat.getIpAddr(req));
		originVo.setPlatType(PlatType.bank);
		if(StringUtil.isNotEmpty(req.getAttribute(Constants.USER_ID)+"")){
			BankOperatorVo bankOperatorVo = bankOperatorService.getBankOperatorById(req.getAttribute(Constants.CLIENT_ID)+"",
					Long.valueOf(req.getAttribute(Constants.USER_ID)+"")); 
			originVo.setOperatorId(bankOperatorVo.getId());
			originVo.setOperatorUserName(bankOperatorVo.getUsername());
			originVo.setOrgId(bankOperatorVo.getOrgCode());
			originVo.setRoleId(String.valueOf(bankOperatorVo.getRoleId()));
		}
		return originVo;
		
	}
	

	/**
	 * 
	 * setReportReqVo:(用户指标 按月和周统计的时候给month和week设值).
	 *
	 * @author wufei 2015-12-5 下午02:36:12
	 * @param voReq
	 * @param reportReqVo
	 *
	 */
	private void setReportReqVo(ReportBankReqVo voReq,
			ReportReqVo reportReqVo) {
		
		//处理查询时间范围，周、月需要包含整个周月跨度
		if (StringUtil.isNotBlank(voReq.getStartDate())&& StringUtil.isNotBlank(voReq.getEndDate())) {
			reportReqVo.setStartYear(DateUtilForReport.getYearByDate(voReq.getStartDate()));
			reportReqVo.setEndYear(DateUtilForReport.getYearByDate(voReq.getEndDate())); 
			if(StringUtil.isNotBlank(voReq.getCountType()) && voReq.getCountType().equals(CountTypeEnum.YEAR.getCode())){
				reportReqVo.setStartDate(DateUtil.getYearFirstDay(DateUtil.strDate2Date(voReq.getStartDate())));
				reportReqVo.setEndDate(DateUtil.getYearLastMonthLastDay(DateUtil.strDate2Date(voReq.getEndDate())));
			}else if(StringUtil.isNotBlank(voReq.getCountType()) && voReq.getCountType().equals(CountTypeEnum.MONTH.getCode())){
				reportReqVo.setStartMonth(DateUtilForReport.getMonthByDate(voReq.getStartDate()));
				reportReqVo.setEndMonth(DateUtilForReport.getMonthByDate(voReq.getEndDate()));
				reportReqVo.setStartDate(DateUtil.getCurrentMonthFirstDay(DateUtil.strDate2Date(voReq.getStartDate())));
				reportReqVo.setEndDate(DateUtil.getCurrentMonthLastDay(DateUtil.strDate2Date(voReq.getEndDate())));
			}else if(StringUtil.isNotBlank(voReq.getCountType()) && voReq.getCountType().equals(CountTypeEnum.WEEK.getCode())){
				reportReqVo.setStartWeek(DateUtilForReport.getWeekByDate(voReq.getStartDate()));
				reportReqVo.setEndWeek(DateUtilForReport.getWeekByDate(voReq.getEndDate()));
				reportReqVo.setStartDate(DateUtil.getCurrentWeekFirstDay(DateUtil.strDate2Date(voReq.getStartDate())));
				reportReqVo.setEndDate(DateUtil.getCurrentWeekLastDay(DateUtil.strDate2Date(voReq.getEndDate())));
			} 
		}
		 
	}

	/**
	 * 
	 * getDefinTaskList:(任务列表分页查询).
	 *
	 * @author wufei 2015-12-5 下午07:03:54
	 * @param defineTaskReq
	 * @return
	 * @throws Exception
	 *
	 */
	public Map<String, Object> getDefinTaskList(DefineTaskReq defineTaskReq)
			throws Exception {
		LogCvt.info("任务列表--请求参数:" + JSON.toJSONString(defineTaskReq));
		Map<String, Object> resMap = new HashMap<String, Object>();
		// 封装分页
		PageVo page = setPageVo(defineTaskReq);

		DefineTaskReqVo defineTaskReqVo = new DefineTaskReqVo();
		if (StringUtil.isNotBlank(defineTaskReq.getClientId())) {
			defineTaskReqVo.setClientId(defineTaskReq.getClientId());
		}
		if (StringUtil.isNotBlank(defineTaskReq.getLoginId())) {
			defineTaskReqVo.setLoginId(defineTaskReq.getLoginId());
		}
		List<DefineTaskResp> defineTaskRespList = new ArrayList<DefineTaskResp>();
		LogCvt.info("任务列表--查询参数:" + JSON.toJSONString(defineTaskReqVo));
		DefineTaskRespPageVo defineTaskRespPageVo = reportService
				.getDefineTaskPageVo(page, defineTaskReqVo);
		LogCvt.info("任务列表--返回数据:" + JSON.toJSONString(defineTaskRespPageVo));
		if (defineTaskRespPageVo.getDefineTaskRespVo() != null
				&& defineTaskRespPageVo.getDefineTaskRespVo().size() > 0) {
			for (DefineTaskRespVo defineTaskRespVo : defineTaskRespPageVo
					.getDefineTaskRespVo()) {
				LogCvt.info("=====>"+defineTaskRespVo.getCommitTime() +"  "+DateUtil.formatDate(new Date(defineTaskRespVo.getCommitTime()), "yyyy-MM-dd HH:mm:ss"));
				DefineTaskResp defineTaskResp = new DefineTaskResp();
				BeanUtils.copyProperties(defineTaskResp, defineTaskRespVo);
				defineTaskRespList.add(defineTaskResp);
			}
		}
		if(defineTaskRespPageVo.getPage() != null){
			resMap.put("pageCount", defineTaskRespPageVo.getPage().getPageCount());
			resMap.put("pageNumber", defineTaskRespPageVo.getPage().getPageNumber());
			resMap.put("totalCount", defineTaskRespPageVo.getPage().getTotalCount());
			resMap.put("hasNext", defineTaskRespPageVo.getPage().getPageCount() > defineTaskRespPageVo.getPage().getPageNumber());
			resMap.put("lastPageNumber", defineTaskRespPageVo.getPage().getLastPageNumber());
			resMap.put("firstRecordTime", defineTaskRespPageVo.getPage().getFirstRecordTime());
			resMap.put("lastRecordTime", defineTaskRespPageVo.getPage().getLastRecordTime());
		}
		resMap.put("taskList", defineTaskRespList);
		return resMap;

	}

	/**
	 * 
	 * setPageVo:(封装分页请求参数).
	 *
	 * @author wufei 2015-12-5 下午06:57:31
	 * @param defineTaskReq
	 * @return
	 *
	 */
	private PageVo setPageVo(DefineTaskReq defineTaskReq) {
		PageVo page = new PageVo();
		page.setPageNumber(defineTaskReq.getPageNumber() == null ? 1
				: defineTaskReq.getPageNumber());
		page.setPageSize(defineTaskReq.getPageSize() == null ? 10
				: defineTaskReq.getPageSize());
		if (defineTaskReq.getLastPageNumber() != null) {
			page.setLastPageNumber(defineTaskReq.getLastPageNumber());
		}
		if (defineTaskReq.getFirstRecordTime() != null) {
			page.setFirstRecordTime(defineTaskReq.getFirstRecordTime());
		}
		if (defineTaskReq.getLastRecordTime() != null) {
			page.setLastRecordTime(defineTaskReq.getLastRecordTime());
		}
		return page;
	}

	/**
	 * 
	 * getReportByCondition:银行报表导出
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月5日 下午7:57:38
	 * @param voReq
	 * @return
	 * @throws TException
	 *
	 */
	public Map<String, Object> getReportByCondition(ReportBankReqVo voReq)
			throws TException {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ReportReqVo reportReqVo = new ReportReqVo();
		BeanUtils.copyProperties(reportReqVo, voReq);
		
		/**
		 * 用户指标查询 根据筛选时间计算 月和周
		 */
		setReportReqVo(voReq, reportReqVo);
		
		ExportResultRes resultRes = reportService.getReportByCondition(reportReqVo); 
		if (null != resultRes) {
			if (resultRes.getResultVo().getResultCode().equals(ResultEnum.SUCCESS.getDescrition())) {
				resMap.put("url", resultRes.getUrl());
			}
			resMap.put("code", resultRes.getResultVo().getResultCode());
			resMap.put("message", resultRes.getResultVo().getResultDesc());
		} else {
			resMap.put("code", "9999");
			resMap.put("message", "银行报表导出异常");
		}
		return resMap;
	}
}
