package com.froad.cbank.coremodule.module.normal.bank.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.vo.ContractRankReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ContractRankVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.DateForReportVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantAddTypePercentReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantAddTypePercentVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantAddTypeSortVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantContractDetailReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantContractDetailVo;
import com.froad.thrift.service.ReportMerchantContractService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.MerchantContractRankResVo;
import com.froad.thrift.vo.report.MerchantContractRankVo;
import com.froad.thrift.vo.report.ReportMerchantContractDeatailPageVo;
import com.froad.thrift.vo.report.ReportMerchantContractDeatailResVo;
import com.froad.thrift.vo.report.ReportMerchantContractDeatailVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;

/**
 * 类描述：相关业务类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-6-3下午5:45:24
 */

@Service
public class MerchantContractReportService {
	@Resource
	ReportMerchantContractService.Iface reportMerchantContractService;

	/**
	 * 方法描述：签约人商户数排行榜查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @throws TException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @time: 2015年6月2日 下午3:43:27
	 */
	public HashMap<String, Object> queryAddMerchantRankList(ContractRankReq req)
			throws TException, IllegalAccessException,
			InvocationTargetException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<ContractRankVo> contractRankList = new ArrayList<ContractRankVo>();
		ContractRankVo contractRankVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		commonParamVo.setFlag(false);
		commonParamVo.setClientId(req.getClientId());
		commonParamVo.setOrgCode(req.getOrgCode());
		MerchantContractRankResVo merchantContractRankResVo = reportMerchantContractService
				.merchantContractRank(commonParamVo);
		// MerchantContractRankResVo merchantContractRankResVo =
		// reportMerchantContractService.merchantContractRank(req.getClientId(),
		// req.getOrgCode());
		LogCvt.info("签约人商户数排行榜查询后端返回数据："
				+ JSON.toJSONString(merchantContractRankResVo));
		List<MerchantContractRankVo> list = merchantContractRankResVo
				.getMerchantContractRankVos();
		if (list != null && list.size() > 0) {
			for (MerchantContractRankVo vo : list) {
				contractRankVo = new ContractRankVo();
				contractRankVo.setConstractStaff(vo.getConstractStaff());
				contractRankVo.setCount(String.valueOf(vo.getCount()));
				contractRankVo.setSort(String.valueOf(vo.getSort()));
				contractRankList.add(contractRankVo);
			}
		}

		resMap.put("contractRankList", contractRankList);
		return resMap;
	}

	/**
	 * 方法描述：商户新增类型占比查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @throws TException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 * @time: 2015年6月2日 下午3:43:27
	 */
	public HashMap<String, Object> queryMerchantAddPercentList(
			MerchantAddTypePercentReq req)
					throws TException, IllegalAccessException,
					InvocationTargetException, ParseException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantAddTypePercentVo> percentList = new ArrayList<MerchantAddTypePercentVo>();
		MerchantAddTypePercentVo merchantAddTypePercentVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (req.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		if (StringUtil.isNotBlank(req.getBeginDate())
				&& StringUtil.isNotBlank(req.getEndDate())) {
			// Date dd = DateUtil.longToDate(Long.valueOf(req.getBeginDate()));
			// String dateTmp = DateUtil.formatDate(dd, false);
			// String t = dateTmp+" 00:00:00";
			// SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// commonParamVo.setBegDate(re.parse(t).getTime());
			//
			// Date dd2 = DateUtil.longToDate(Long.valueOf(req.getEndDate()));
			// String dateTmp2 = DateUtil.formatDate(dd2, false);
			// String t2 = dateTmp2+" 23:59:59";
			// SimpleDateFormat re2 = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// commonParamVo.setEndDate(re2.parse(t2).getTime());
			commonParamVo.setBegDate(
					DateUtil.dateToTheDayBeforeDawn(req.getBeginDate()));
			commonParamVo.setEndDate(
					DateUtil.dateToTheDayAfterDawn(req.getEndDate()));
		}
		commonParamVo.setClientId(req.getClientId());
		commonParamVo.setOrgCode(req.getOrgCode());
		TypePercentResVo typePercentResVo = reportMerchantContractService
				.merchantTypeAddPercent(commonParamVo);
		LogCvt.info("商户新增类型占比查询后端返回数据：" + JSON.toJSONString(typePercentResVo));
		List<TypePercentVo> list = typePercentResVo.getTypePercentVos();
		if (list != null && list.size() > 0) {
			for (TypePercentVo vo : list) {
				merchantAddTypePercentVo = new MerchantAddTypePercentVo();
				merchantAddTypePercentVo
						.setPercent(new BigDecimal(vo.getPercent()));
				// if(StringUtil.isNotBlank(vo.getType())){
				// //0-面对面 1-团购 3-名优特惠
				// if("0".equals(vo.getType())){
				// merchantAddTypePercentVo.setType("面对面 ");
				// }else if("1".equals(vo.getType())){
				// merchantAddTypePercentVo.setType("团购");
				// }else if("3".equals(vo.getType())){
				// merchantAddTypePercentVo.setType("名优特惠");
				// }else{
				// merchantAddTypePercentVo.setType("");
				// }
				// }else{
				// merchantAddTypePercentVo.setType("");
				// }
				merchantAddTypePercentVo.setType(vo.getTypeName());
				percentList.add(merchantAddTypePercentVo);
			}
		}
		resMap.put("percentList", percentList);
		return resMap;
	}

	/**
	 * 方法描述：签约人新增商户数排行查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @throws TException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 * @time: 2015年6月2日 下午3:43:27
	 */
	public HashMap<String, Object> queryAddMerchantSortList(
			MerchantAddTypePercentReq req)
					throws TException, IllegalAccessException,
					InvocationTargetException, ParseException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantAddTypeSortVo> contractRankList = new ArrayList<MerchantAddTypeSortVo>();
		MerchantAddTypeSortVo merchantAddTypeSortVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (req.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		if (StringUtil.isNotBlank(req.getBeginDate())
				&& StringUtil.isNotBlank(req.getEndDate())) {
			// Date dd = DateUtil.longToDate(Long.valueOf(req.getBeginDate()));
			// String dateTmp = DateUtil.formatDate(dd, false);
			// String t = dateTmp+" 00:00:00";
			// SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// commonParamVo.setBegDate(re.parse(t).getTime());
			//
			// Date dd2 = DateUtil.longToDate(Long.valueOf(req.getEndDate()));
			// String dateTmp2 = DateUtil.formatDate(dd2, false);
			// String t2 = dateTmp2+" 23:59:59";
			// SimpleDateFormat re2 = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// commonParamVo.setEndDate(re2.parse(t2).getTime());
			commonParamVo.setBegDate(
					DateUtil.dateToTheDayBeforeDawn(req.getBeginDate()));
			commonParamVo.setEndDate(
					DateUtil.dateToTheDayAfterDawn(req.getEndDate()));
		}
		commonParamVo.setClientId(req.getClientId());
		commonParamVo.setOrgCode(req.getOrgCode());
		MerchantContractRankResVo merchantContractRankResVo = reportMerchantContractService
				.merchantContractAddRank(commonParamVo);
		LogCvt.info("签约人新增商户数排行查询后端返回数据："
				+ JSON.toJSONString(merchantContractRankResVo));
		List<MerchantContractRankVo> list = merchantContractRankResVo
				.getMerchantContractRankVos();
		if (list != null && list.size() > 0) {
			for (MerchantContractRankVo vo : list) {
				merchantAddTypeSortVo = new MerchantAddTypeSortVo();
				merchantAddTypeSortVo.setConstractStaff(vo.getConstractStaff());
				merchantAddTypeSortVo.setCount(vo.getCount());
				merchantAddTypeSortVo.setSort(vo.getSort());
				contractRankList.add(merchantAddTypeSortVo);
			}
		}
		resMap.put("contractRankList", contractRankList);
		return resMap;
	}

	/**
	 * 方法描述：签约人商户统计详细列表查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @throws TException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 * @time: 2015年6月2日 下午3:43:27
	 */
	public HashMap<String, Object> queryContractDetailList(
			MerchantContractDetailReq req)
					throws TException, IllegalAccessException,
					InvocationTargetException, ParseException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		// 分页对象转换
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber() == 0
				? new Integer(1)
				: req.getPageNumber());
		pageVo.setPageSize(
				req.getPageSize() == 0 ? new Integer(10) : req.getPageSize());
		pageVo.setFirstRecordTime(
				StringUtil.isNotBlank(req.getFirstRecordTime())
						? req.getFirstRecordTime()
						: 0);
		pageVo.setLastPageNumber(StringUtil.isNotBlank(req.getLastPageNumber())
				? req.getLastPageNumber()
				: 0);
		pageVo.setLastRecordTime(StringUtil.isNotBlank(req.getLastRecordTime())
				? req.getLastRecordTime()
				: 0);

		List<MerchantContractDetailVo> merchantContractDetailList = new ArrayList<MerchantContractDetailVo>();
		MerchantContractDetailVo merchantContractDetailVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (req.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		if (StringUtil.isNotBlank(req.getBeginDate())
				&& StringUtil.isNotBlank(req.getEndDate())) {
			// Date dd = DateUtil.longToDate(Long.valueOf(req.getBeginDate()));
			// String dateTmp = DateUtil.formatDate(dd, false);
			// String t = dateTmp+" 00:00:00";
			// SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// commonParamVo.setBegDate(re.parse(t).getTime());
			//
			// Date dd2 = DateUtil.longToDate(Long.valueOf(req.getEndDate()));
			// String dateTmp2 = DateUtil.formatDate(dd2, false);
			// String t2 = dateTmp2+" 23:59:59";
			// SimpleDateFormat re2 = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// commonParamVo.setEndDate(re2.parse(t2).getTime());
			// 设置起始和结束时间
			if (StringUtil.isNotBlank(req.getBeginDate())
					&& StringUtil.isNotBlank(req.getEndDate())) {
				DateForReportVo result = DateUtilForReport
						.getBeginDateAndEndDate(req.getBeginDate(),
								req.getEndDate());
				commonParamVo.setBegDate(result.getBeginDate());
				commonParamVo.setEndDate(result.getEndDate());
			}
		}
		commonParamVo.setClientId(req.getClientId());
		commonParamVo.setOrgCode(req.getOrgCode());
		ReportMerchantContractDeatailPageVo reportMerchantContractDeatailPageVo = reportMerchantContractService
				.merchantContractDetailListByPage(pageVo, commonParamVo);
		LogCvt.info("签约人商户统计详细列表查询后端返回数据："
				+ JSON.toJSONString(reportMerchantContractDeatailPageVo));
		List<ReportMerchantContractDeatailVo> list = reportMerchantContractDeatailPageVo
				.getMerchantContractDeatailVos();
		if (list != null && list.size() > 0) {
			for (ReportMerchantContractDeatailVo res : list) {
				merchantContractDetailVo = new MerchantContractDetailVo();
				merchantContractDetailVo
						.setConstractStaff(res.getConstractStaff());
				merchantContractDetailVo.setAddMerchantCount(res.getAddCount());
				merchantContractDetailVo
						.setAddPercent(new BigDecimal(res.getAddPercent())
								.setScale(4, BigDecimal.ROUND_HALF_UP));
				merchantContractDetailVo
						.setChangeAmountCount(res.getChangeCount());
				merchantContractDetailVo.setOrderCount(res.getOrderCount());
				merchantContractDetailVo
						.setTotalAmount(new BigDecimal(res.getTotalAmount())
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				merchantContractDetailVo
						.setTotalMerchantCount(res.getTotalCount());
				merchantContractDetailList.add(merchantContractDetailVo);
			}
		}
		resMap.put("merchantContractDetailList", merchantContractDetailList);
		if (reportMerchantContractDeatailPageVo.getPageVo() != null) {
			resMap.put("pageCount", reportMerchantContractDeatailPageVo
					.getPageVo().getPageCount());
			resMap.put("totalCountExport", reportMerchantContractDeatailPageVo
					.getPageVo().getTotalCount());
			resMap.put("totalCount", reportMerchantContractDeatailPageVo
					.getPageVo().getTotalCount());
			resMap.put("pageNumber", reportMerchantContractDeatailPageVo
					.getPageVo().getPageNumber());
			resMap.put("lastPageNumber", reportMerchantContractDeatailPageVo
					.getPageVo().getLastPageNumber());
			resMap.put("firstRecordTime", reportMerchantContractDeatailPageVo
					.getPageVo().getFirstRecordTime());
			resMap.put("lastRecordTime", reportMerchantContractDeatailPageVo
					.getPageVo().getLastRecordTime());
		} else {
			resMap.put("pageCount", 0);
			resMap.put("totalCountExport", 0);
			resMap.put("totalCount", 0);
			resMap.put("pageNumber", 1);
			resMap.put("lastPageNumber", 0);
			resMap.put("firstRecordTime", 0);
			resMap.put("lastRecordTime", 0);
		}
		return resMap;
	}

	/**
	 * 方法描述：签约人商户统计详细列表导出查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @throws TException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ParseException
	 * @time: 2015年6月2日 下午3:43:27
	 */
	public HashMap<String, Object> queryExportContractDetailList(
			MerchantContractDetailReq req)
					throws TException, IllegalAccessException,
					InvocationTargetException, ParseException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantContractDetailVo> merchantContractDetailList = new ArrayList<MerchantContractDetailVo>();
		MerchantContractDetailVo merchantContractDetailVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (req.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		if (StringUtil.isNotBlank(req.getBeginDate())
				&& StringUtil.isNotBlank(req.getEndDate())) {
			// Date dd = DateUtil.longToDate(Long.valueOf(req.getBeginDate()));
			// String dateTmp = DateUtil.formatDate(dd, false);
			// String t = dateTmp+" 00:00:00";
			// SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// commonParamVo.setBegDate(re.parse(t).getTime());
			//
			// Date dd2 = DateUtil.longToDate(Long.valueOf(req.getEndDate()));
			// String dateTmp2 = DateUtil.formatDate(dd2, false);
			// String t2 = dateTmp2+" 23:59:59";
			// SimpleDateFormat re2 = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// commonParamVo.setEndDate(re2.parse(t2).getTime());

			// 设置起始和结束时间
			if (StringUtil.isNotBlank(req.getBeginDate())
					&& StringUtil.isNotBlank(req.getEndDate())) {
				DateForReportVo result = DateUtilForReport
						.getBeginDateAndEndDate(req.getBeginDate(),
								req.getEndDate());
				commonParamVo.setBegDate(result.getBeginDate());
				commonParamVo.setEndDate(result.getEndDate());
			}
		}
		commonParamVo.setClientId(req.getClientId());
		commonParamVo.setOrgCode(req.getOrgCode());
		// ReportMerchantContractDeatailPageVo
		// reportMerchantContractDeatailPageVo=reportMerchantContractService.merchantContractDetailListByPage(pageVo,
		// commonParamVo);
		ReportMerchantContractDeatailResVo reportMerchantContractDeatailResVo = reportMerchantContractService
				.merchantContractDetailList(commonParamVo);
		LogCvt.info("签约人商户统计详细列表导出查询后端返回数据："
				+ JSON.toJSONString(reportMerchantContractDeatailResVo));
		List<ReportMerchantContractDeatailVo> list = reportMerchantContractDeatailResVo
				.getMerchantContractDeatailVos();
		if (list != null && list.size() > 0) {
			for (ReportMerchantContractDeatailVo res : list) {
				merchantContractDetailVo = new MerchantContractDetailVo();
				merchantContractDetailVo
						.setConstractStaff(res.getConstractStaff());
				merchantContractDetailVo.setAddMerchantCount(res.getAddCount());
				merchantContractDetailVo
						.setAddPercent(new BigDecimal(res.getAddPercent() * 100)
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				merchantContractDetailVo
						.setChangeAmountCount(res.getChangeCount());
				merchantContractDetailVo.setOrderCount(res.getOrderCount());
				merchantContractDetailVo
						.setTotalAmount(new BigDecimal(res.getTotalAmount())
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				merchantContractDetailVo
						.setTotalMerchantCount(res.getTotalCount());
				merchantContractDetailList.add(merchantContractDetailVo);
			}
		}
		resMap.put("merchantContractDetailList", merchantContractDetailList);
		return resMap;
	}
}
