package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.CountTypeEnum;
import com.froad.enums.ResultCode;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportLogic;
import com.froad.logic.impl.ReportLogicImpl;
import com.froad.po.req.DefineTaskReq;
import com.froad.po.req.ReqPo;
import com.froad.po.resp.AmountQuota;
import com.froad.po.resp.DefineTaskResp;
import com.froad.po.resp.MerchantInfoResp;
import com.froad.po.resp.OrderQuota;
import com.froad.po.resp.ProductInfoResp;
import com.froad.po.resp.Resp;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ReportService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.coremodule.DefineTaskReqVo;
import com.froad.thrift.vo.coremodule.DefineTaskRespPageVo;
import com.froad.thrift.vo.coremodule.DefineTaskRespVo;
import com.froad.thrift.vo.coremodule.MerchantInfoRespVo;
import com.froad.thrift.vo.coremodule.ProductInfoRespVo;
import com.froad.thrift.vo.coremodule.ReportAmountQuotaVo;
import com.froad.thrift.vo.coremodule.ReportBaseRespVo;
import com.froad.thrift.vo.coremodule.ReportMemberQuotaVo;
import com.froad.thrift.vo.coremodule.ReportOrderQuotaVo;
import com.froad.thrift.vo.coremodule.ReportReqVo;
import com.froad.thrift.vo.coremodule.ReportRespPageVo;
import com.froad.thrift.vo.coremodule.ReportRespVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.utils.DateSystemUtil;

/**
 * 金额指标
 * 
 * @author bruce
 */
public class ReportServiceImpl extends BizMonitorBaseService implements ReportService.Iface {

	ReportLogic amountLogic = new ReportLogicImpl();
	private ReportRespVo reportResp;

	public ReportServiceImpl(String name, String version) {
		super(name, version);
	}

	@Override
	public ReportRespPageVo getRespPage(PageVo pageVo, ReportReqVo reportReqVo) throws TException {
		LogCvt.info("分页查询getRespPage");
		ReqPo req = (ReqPo) BeanUtil.copyProperties(ReqPo.class, reportReqVo);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Page<Resp> page = (Page) BeanUtil.copyProperties(Page.class, pageVo);
		req.setPageNumber(page.getPageNumber());
		req.setPageSize(page.getPageSize());
		page = amountLogic.findReportByPage(page, req);
		ReportRespPageVo reportRespPage = new ReportRespPageVo();
		List<ReportRespVo> reportRespList = new ArrayList<ReportRespVo>();
		for (Resp resp : page.getResultsContent()) {
			reportResp = new ReportRespVo();

			ReportBaseRespVo base = (ReportBaseRespVo) BeanUtil.copyProperties(ReportBaseRespVo.class, resp.getBase());
			reportResp.setBaseRespVo(base);
			
			req.setHasMyself(false);
			if((req.getOrgLevel()+"").equals(resp.getBase().getOrgLevel())){
				req.setHasMyself(true);
			}
			
			Resp sumR=null;
			Resp sumF=null;
			if(req.getCheckedAmountCumulation() || req.getCheckedOrderCumulation() || req.getCheckedAmountCumulationTurnover() || req.getCheckedOrderCumulationTurnover() ){//需要计算累计信息 
				if(req.getCountType().equals(CountTypeEnum.day.name())){
					req.setEndDate(resp.getBase().getDay());
				}else if(req.getCountType().equals(CountTypeEnum.week.name())){
					req.setEndDate(DateSystemUtil.getWeekInfoLastDay(Integer.valueOf(resp.getBase().getYear()), Integer.valueOf(resp.getBase().getWeekTemp())));
				}else if(req.getCountType().equals(CountTypeEnum.month.name())){
					req.setEndDate(DateSystemUtil.getWeekInfoLastMonth(Integer.valueOf(resp.getBase().getYear()), Integer.valueOf(resp.getBase().getMonthTemp())));
				}else if(req.getCountType().equals(CountTypeEnum.year.name())){
//					req.setEndYear(resp.getBase().getYear());
				}
				if(base.getOrgCode()!=null){
					req.setOrgCode(resp.getBase().getOrgCode());
				}
				if(base.getMerchantId()!=null){
					req.setMerchantId(resp.getBase().getMerchantId());
				}
				if(base.getMerchantCategoryId()!=null){
					req.setMerchantCategoryId(resp.getBase().getMerchantCategoryId());
				}
 				sumR=amountLogic.sumAmountOrOrder(req);
				if(sumR==null){
					sumR=new Resp();
					sumR.setOrderLat(new OrderQuota());
					sumR.setAmountLat(new AmountQuota());
				}
				if(sumR.getAmountLat().getOrderAmount()==null){
					sumR.getAmountLat().setOrderAmount(0l);
				}
				if(sumR.getOrderLat().getOrderCount()==null){
					sumR.getOrderLat().setOrderCount(0);
				} 
				if(req.getCheckedAmountCumulationTurnover() || req.getCheckedOrderCumulationTurnover() ){
					sumF=amountLogic.sumRefundAmountOrOrder(req);
					if(sumF==null){
						sumF=new Resp();
						sumF.setOrderLat(new OrderQuota());
						sumF.setAmountLat(new AmountQuota());
					}
					if(sumF.getAmountLat().getRefundAmount()==null){
						sumF.getAmountLat().setRefundAmount(0l);
					}
					if(sumF.getOrderLat().getOrderRefund()==null){
						sumF.getOrderLat().setOrderRefund(0);
					}
				}
			} 
			if (resp.getAmountLat() != null) {
				ReportAmountQuotaVo amountLat = (ReportAmountQuotaVo) BeanUtil.copyProperties(ReportAmountQuotaVo.class,resp.getAmountLat());
				if(sumR!=null && req.getCheckedAmountCumulation()){ 
					amountLat.setOrderTotalPrice(sumR.getAmountLat().getOrderAmount()); 
				}
				if(sumF!=null && req.getCheckedAmountCumulationTurnover() ){ 
					amountLat.setAmountCumulatiTurnover(sumR.getAmountLat().getOrderAmount()-sumF.getAmountLat().getRefundAmount());
				}
				reportResp.setAmountLat(amountLat);
			}
			if (resp.getOrderLat() != null) {
				ReportOrderQuotaVo orderLat = (ReportOrderQuotaVo) BeanUtil.copyProperties(ReportOrderQuotaVo.class,
						resp.getOrderLat());
				if(sumR!=null && req.getCheckedOrderCumulation()){ 
					orderLat.setOrderUmulation(sumR.getOrderLat().getOrderCount()); 
				}
				if(sumF!=null && req.getCheckedOrderCumulationTurnover()){
					orderLat.setOrderCumulationTurnover(sumR.getOrderLat().getOrderCount()-sumF.getOrderLat().getOrderRefund());
				}
				reportResp.setOrderLat(orderLat);
			}
			
			
			Resp sumMerchant=null;
			if(req.getCheckedMerchantCumulation() || req.getCheckedOutletCumulation()){//是否有累计商户数和累计门店数
				if(req.getCountType().equals(CountTypeEnum.day.name())){
					req.setEndDate(resp.getBase().getDay());
				}else if(req.getCountType().equals(CountTypeEnum.week.name())){
					req.setEndDate(DateSystemUtil.getWeekInfoLastDay(Integer.valueOf(resp.getBase().getYear()), Integer.valueOf(resp.getBase().getWeekTemp())));
				}else if(req.getCountType().equals(CountTypeEnum.month.name())){
					req.setEndDate(DateSystemUtil.getWeekInfoLastMonth(Integer.valueOf(resp.getBase().getYear()), Integer.valueOf(resp.getBase().getMonthTemp())));
				}else if(req.getCountType().equals(CountTypeEnum.year.name())){
//					req.setEndYear(resp.getBase().getYear());
				}
				if(base.getOrgCode()!=null){
					req.setOrgCode(resp.getBase().getOrgCode());
				}
				if(base.getMerchantId()!=null){
					req.setMerchantId(resp.getBase().getMerchantId());
				}
				if(base.getMerchantCategoryId()!=null){
					req.setMerchantCategoryId(resp.getBase().getMerchantCategoryId());
				}
				sumMerchant=amountLogic.sumMerchantCount(req);
				if(sumMerchant==null){
					sumMerchant=new Resp();
					sumMerchant.setMerchantInfoResp(new MerchantInfoResp());
 				}
				if(sumMerchant.getMerchantInfoResp().getMerchantCumulation()==null){
					sumMerchant.getMerchantInfoResp().setMerchantCumulation(0);
				}
				if(sumMerchant.getMerchantInfoResp().getOutletCumulation()==null){
					sumMerchant.getMerchantInfoResp().setOutletCumulation(0);
				} 
			}
			
			if (resp.getMerchantInfoResp() != null) {
				MerchantInfoRespVo merchantInfoRespVo = (MerchantInfoRespVo) BeanUtil
						.copyProperties(MerchantInfoRespVo.class, resp.getMerchantInfoResp());
				if(sumMerchant!=null && req.getCheckedMerchantCumulation()){
					merchantInfoRespVo.setMerchantCumulation(sumMerchant.getMerchantInfoResp().getMerchantCumulation());
				}
				if(sumMerchant!=null && req.getCheckedOutletCumulation()){
					merchantInfoRespVo.setOutletCumulation(sumMerchant.getMerchantInfoResp().getOutletCumulation());
				}
				reportResp.setMerchantInfoRespVo(merchantInfoRespVo);
			}
			
			Resp sumProduct=null;
			if(req.getCheckedProductCumulation() || req.getCheckedProductDownComulation()){//是否有累计商品数量、累计下架商品数量
				if(req.getCountType().equals(CountTypeEnum.day.name())){
					req.setEndDate(resp.getBase().getDay());
				}else if(req.getCountType().equals(CountTypeEnum.week.name())){
					req.setEndDate(DateSystemUtil.getWeekInfoLastDay(Integer.valueOf(resp.getBase().getYear()), Integer.valueOf(resp.getBase().getWeekTemp())));
				}else if(req.getCountType().equals(CountTypeEnum.month.name())){
					req.setEndDate(DateSystemUtil.getWeekInfoLastMonth(Integer.valueOf(resp.getBase().getYear()), Integer.valueOf(resp.getBase().getMonthTemp())));
				}else if(req.getCountType().equals(CountTypeEnum.year.name())){
//					req.setEndYear(resp.getBase().getYear());
				}
				if(base.getOrgCode()!=null){
					req.setOrgCode(resp.getBase().getOrgCode());
				}
				if(base.getMerchantId()!=null){
					req.setMerchantId(resp.getBase().getMerchantId());
				}
				if(base.getMerchantCategoryId()!=null){
					req.setMerchantCategoryId(resp.getBase().getMerchantCategoryId());
				}
				sumProduct=amountLogic.sumProductCount(req);
				if(sumProduct==null){
					sumProduct=new Resp();
					sumProduct.setProductInfoResp(new ProductInfoResp());
 				}
				if(sumProduct.getProductInfoResp().getProductCumulation()==null){
					sumProduct.getProductInfoResp().setProductCumulation(0);
				}
				if(sumProduct.getProductInfoResp().getProductDownComulation()==null){
					sumProduct.getProductInfoResp().setProductDownComulation(0);
				} 
			}
			
			if (resp.getProductInfoResp() != null) {
				ProductInfoRespVo productInfoResp = (ProductInfoRespVo) BeanUtil.copyProperties(ProductInfoRespVo.class,
						resp.getProductInfoResp());
				if(sumProduct!=null && req.getCheckedProductCumulation()){
					productInfoResp.setProductCumulation(sumProduct.getProductInfoResp().getProductCumulation());
				}
				if(sumProduct!=null && req.getCheckedProductDownComulation()){
					productInfoResp.setProductDownComulation(sumProduct.getProductInfoResp().getProductDownComulation()); 
				}
				reportResp.setProductInfoRespVo(productInfoResp);
			}
			if (resp.getMemberLat() != null) {
				ReportMemberQuotaVo memberLat = (ReportMemberQuotaVo) BeanUtil.copyProperties(ReportMemberQuotaVo.class,
						resp.getMemberLat());
				reportResp.setMemberLat(memberLat);
			}
			reportRespList.add(reportResp);
		}
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
		reportRespPage.setPage(pageVo);
		reportRespPage.setReportResp(reportRespList);
		return reportRespPage;
	}

	@Override
	public ExportResultRes getReportByCondition(ReportReqVo reportReqVo) throws TException {
		long startTime = System.currentTimeMillis();
		LogCvt.debug("报表导出请求参数:" + JSON.toJSONString(reportReqVo));
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo();
		try {

			ReqPo req = (ReqPo) BeanUtil.copyProperties(ReqPo.class, reportReqVo);

			int everySheetNum = Integer.valueOf(50000);//
			int pageSize = Integer.valueOf(30000);// 2万条一查询
			ExcelWriter excelWriter = new ExcelWriter(everySheetNum);
			Page<Resp> page = new Page<Resp>();
			page.setPageNumber(1);
			page.setPageSize(pageSize);
			boolean _flag = true;
//
//			/** 金额指标 **/
//			if (req.getCheckedAmount() || req.getCheckedAmountCumulation() || req.getCheckedAmountCumulationTurnover()
//					|| req.getCheckedAmountRefund() || req.getCheckedAmountTurnover()) {// 有金额指标
//				req.setPageSize(pageSize);
//				req.setPageNumber(1);
//				while (_flag) {
//					req.setLatitudeTupe(LatitudeTupeEnum.AMOUNT.getDescription());
//					List<String> header = amountLogic.toExcelHeader(req);
//					List<List<String>> data = amountLogic.toExcelByPage(page, req);
//					excelWriter.write(header, data, "金额", "银行报表");
//					if (page.getPageCount() <= req.getPageNumber()) {
//						_flag = false;
//					} else {
//						req.setPageNumber(req.getPageNumber() + 1);
//					}
//				}
//			}
//			/** 订单指标 **/
//			if (req.getCheckedOrderCount() || req.getCheckedOrderCumulation() || req.getCheckedOrderRefund()
//					|| req.getCheckedOrderTurnover() || req.getCheckedOrderCumulationTurnover()) {// 是否有订单指标
//				_flag = true;
//				req.setPageSize(pageSize);
//				req.setPageNumber(1);
//				while (_flag) {
//					req.setLatitudeTupe(LatitudeTupeEnum.ORDER.getDescription());
//					List<String> header = amountLogic.toExcelHeader(req);
//					List<List<String>> data = amountLogic.toExcelByPage(page, req);
//					excelWriter.write(header, data, "订单", "银行报表");
//					if (page.getPageCount() <= req.getPageNumber()) {
//						_flag = false;
//					} else {
//						req.setPageNumber(req.getPageNumber() + 1);
//					}
//				}
//			}
//			/** 商户指标 */
//			if (req.getCheckedMerchantSum() || req.getCheckedMerchantCancelContract() || req.getCheckedMerchantCumulation()
//					|| req.getCheckedOutletCount() || req.getCheckedOutletCumulation()) {// 商户数
//				_flag = true;
//				req.setPageSize(pageSize);
//				req.setPageNumber(1);
//				while (_flag) {
//					req.setLatitudeTupe(LatitudeTupeEnum.MERCHANT.getDescription());
//					List<String> header = amountLogic.toExcelHeader(req);
//					List<List<String>> data = amountLogic.toExcelByPage(page, req);
//					excelWriter.write(header, data, "商户", "银行报表");
//					if (page.getPageCount() <= req.getPageNumber()) {
//						_flag = false;
//					} else {
//						req.setPageNumber(req.getPageNumber() + 1);
//					}
//				}
//			}
//			/** 商品指标 */
//			if (req.getCheckedProductSum() || req.getCheckedProductCumulation() || req.getCheckedProductDownComulation()
//					|| req.getCheckedProductDownSum()) {
//				_flag = true;
//				req.setPageSize(pageSize);
//				req.setPageNumber(1);
//				while (_flag) {
//					req.setLatitudeTupe(LatitudeTupeEnum.PRODUCT.getDescription());
//					List<String> header = amountLogic.toExcelHeader(req);
//					List<List<String>> data = amountLogic.toExcelByPage(page, req);
//					excelWriter.write(header, data, "商品", "银行报表");
//					if (page.getPageCount() <= req.getPageNumber()) {
//						_flag = false;
//					} else {
//						req.setPageNumber(req.getPageNumber() + 1);
//					}
//				}
//			}
//			/** 用户指标 */
//			if (req.getCheckedMemberCount() || req.getCheckedMemberComulationCount()) {
//				_flag = true;
//				req.setPageSize(pageSize);
//				req.setPageNumber(1);
//				while (_flag) {
//					req.setLatitudeTupe(LatitudeTupeEnum.MERMBER.getDescription());
//					List<String> header = amountLogic.toExcelHeader(req);
//					List<List<String>> data = amountLogic.toExcelByPage(page, req);
//					excelWriter.write(header, data, "用户", "银行报表");
//					if (page.getPageCount() <= req.getPageNumber()) {
//						_flag = false;
//					} else {
//						req.setPageNumber(req.getPageNumber() + 1);
//					}
//				}
//			} 
			req.setPageSize(pageSize);
			req.setPageNumber(1);
			while (_flag) {
				List<String> header = amountLogic.toExcelHeader(req);
				List<List<String>> data = amountLogic.toExcel(page, req);
				if(data==null){
					_flag = false;
					break;
				}
				excelWriter.write(header, data, "统计报表", "银行报表");
				if (data.size() < req.getPageSize()) {
					_flag = false;
				} else {
					req.setPageNumber(req.getPageNumber() + 1);
				}
			}
			String url = excelWriter.getUrl();
			// 返回生成好的excel
			if (Checker.isNotEmpty(url)) {
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc("下载成功");
				res.setUrl(url);
			} else {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("下载失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("下载失败");
			LogCvt.error("列表导出异常，原因:" + e.getMessage(), e);
		}finally {
			ReportLogicImpl.totalAmountMap.clear();
			ReportLogicImpl.totalRefundAmountMap.clear();
			ReportLogicImpl.totalDownProductMap.clear();
			ReportLogicImpl.totalMerchantMap.clear();
			ReportLogicImpl.totalOrderMap.clear();
			ReportLogicImpl.totalOutlerMap.clear();
			ReportLogicImpl.totalProductMap.clear();
			ReportLogicImpl.totalRefundOrderMap.clear();
			ReportLogicImpl.keyMap.clear();
		}
		long endTime = System.currentTimeMillis();
		LogCvt.debug("列表导出总耗时:" + (endTime - startTime) + "--startTime:" + startTime + "---endTime:" + endTime);
		res.setResultVo(resultVo);
		return res;
	}

	@Override
	public DefineTaskRespPageVo getDefineTaskPageVo(PageVo pageVo, DefineTaskReqVo defineTaskReqVo) throws TException {
		LogCvt.info("分页查询getDefineTaskPageVo");
		DefineTaskReq defineTaskReq = (DefineTaskReq) BeanUtil.copyProperties(DefineTaskReq.class, defineTaskReqVo);
		@SuppressWarnings("unchecked")
		Page<DefineTaskResp> page = (Page) BeanUtil.copyProperties(Page.class, pageVo);
		page = amountLogic.getDefineTaskByPage(page, defineTaskReq);
		DefineTaskRespPageVo defineTaskRespPageVo = new DefineTaskRespPageVo();
		List<DefineTaskRespVo> defineTaskRespVoList = new ArrayList<DefineTaskRespVo>();
		for (DefineTaskResp defineTaskResp : page.getResultsContent()) {
			DefineTaskRespVo defineTaskRespVo = (DefineTaskRespVo) BeanUtil.copyProperties(DefineTaskRespVo.class,
					defineTaskResp);
			if(defineTaskResp.getCompleteTimes()!=null && defineTaskResp.getCompleteTimes().indexOf("0000-00-00")<0)
				defineTaskRespVo.setCompleteTime(DateSystemUtil.stringToDate(defineTaskResp.getCompleteTimes(), "yyyy-MM-dd HH:mm:ss").getTime());
			if(defineTaskResp.getCommitTimes()!=null && defineTaskResp.getCommitTimes().indexOf("0000-00-00")<0)
				defineTaskRespVo.setCommitTime(DateSystemUtil.stringToDate(defineTaskResp.getCommitTimes(), "yyyy-MM-dd HH:mm:ss").getTime());
			defineTaskRespVoList.add(defineTaskRespVo);
		}
		defineTaskRespPageVo.setDefineTaskRespVo(defineTaskRespVoList);
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
		defineTaskRespPageVo.setPage(pageVo);
		return defineTaskRespPageVo;
	}

}
