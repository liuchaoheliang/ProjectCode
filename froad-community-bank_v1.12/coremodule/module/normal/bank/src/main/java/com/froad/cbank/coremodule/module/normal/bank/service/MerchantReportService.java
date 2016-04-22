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
import com.froad.cbank.coremodule.module.normal.bank.enums.ClientIdEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.vo.DateForReportVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantBusiReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantBusiVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantDetailReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantDetailVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantOutletRePortResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantTrendReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantTrendVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantTypePercentReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantTypePercentVo;
import com.froad.thrift.service.ReportMerchantInfoService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.MerchantTrendResVo;
import com.froad.thrift.vo.report.ReportMerchantBussinessResVo;
import com.froad.thrift.vo.report.ReportMerchantBussinessVo;
import com.froad.thrift.vo.report.ReportMerchantDetailPageVo;
import com.froad.thrift.vo.report.ReportMerchantDetailResVo;
import com.froad.thrift.vo.report.ReportMerchantDetailVo;
import com.froad.thrift.vo.report.ReportMerchantOutletResVo;
import com.froad.thrift.vo.report.ReportMerchantOutletVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;

/**
 * 类描述：商户信息报表相关业务类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-6-2下午3:49:18
 */
@Service
public class MerchantReportService {
	@Resource
	ReportMerchantInfoService.Iface reportMerchantInfoService;

	/**
	 * 方法描述：商户走势列表查询
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
	public HashMap<String, Object> queryMerchantTrendList(MerchantTrendReq req)
			throws TException, IllegalAccessException,
			InvocationTargetException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantTrendVo> merchantTrendList = new ArrayList<MerchantTrendVo>();
		MerchantTrendVo merchantTrendVo = null;
		// MerchantTrendResVo merchantTrendResVo =
		// reportMerchantInfoService.getMerchantTrend(req.getClientId(),
		// req.getOrgCode());
		CommonParamVo commonParamVo = new CommonParamVo();
		commonParamVo.setFlag(false);
		commonParamVo.setClientId(req.getClientId());
		commonParamVo.setOrgCode(req.getOrgCode());
		MerchantTrendResVo merchantTrendResVo = reportMerchantInfoService
				.getMerchantTrend(commonParamVo);
		LogCvt.info("商户走势查询后端返回数据：" + JSON.toJSONString(merchantTrendResVo));
		List<com.froad.thrift.vo.report.MerchantTrendVo> list = merchantTrendResVo
				.getMerchantTrendVo();
		if (list != null && list.size() > 0) {
			for (com.froad.thrift.vo.report.MerchantTrendVo vo : list) {
				merchantTrendVo = new MerchantTrendVo();
				merchantTrendVo.setAddCount(vo.getAddCount());
				merchantTrendVo.setWeek(vo.getWeek());
				merchantTrendList.add(merchantTrendVo);
			}
		}
		resMap.put("merchantTrendList", merchantTrendList);
		return resMap;
	}

	/**
	 * 方法描述：商户类型占比查询
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
	public HashMap<String, Object> queryMerchantTypePercentList(
			MerchantTypePercentReq req)
					throws TException, IllegalAccessException,
					InvocationTargetException, ParseException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantTypePercentVo> percentList = new ArrayList<MerchantTypePercentVo>();
		MerchantTypePercentVo merchantTypePercentVo = null;
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
		TypePercentResVo typePercentResVo = reportMerchantInfoService
				.getMerchantTypePercent(commonParamVo);
		LogCvt.info("商户类型占比后端返回数据：" + JSON.toJSONString(typePercentResVo));
		List<TypePercentVo> list = typePercentResVo.getTypePercentVos();
		if (list != null && list.size() > 0) {
			for (TypePercentVo res : list) {
				merchantTypePercentVo = new MerchantTypePercentVo();
				merchantTypePercentVo
						.setPercent(new BigDecimal(res.getPercent()));
				// if(StringUtil.isNotBlank(res.getType())){
				// //0-面对面 1-团购 3-名优特惠
				// if("0".equals(res.getType())){
				// merchantTypePercentVo.setType("面对面 ");
				// }else if("1".equals(res.getType())){
				// merchantTypePercentVo.setType("团购");
				// }else if("3".equals(res.getType())){
				// merchantTypePercentVo.setType("名优特惠");
				// }else{
				// merchantTypePercentVo.setType("");
				// }
				// }else{
				// merchantTypePercentVo.setType("");
				// }
				merchantTypePercentVo.setType(res.getTypeName());
				percentList.add(merchantTypePercentVo);
			}
		}
		// for(int i =1;i < 6;i++){
		// merchantTypePercentVo = new MerchantTypePercentVo();
		// merchantTypePercentVo.setPercent(new BigDecimal(i/100));
		// merchantTypePercentVo.setType(String.valueOf(i));
		// percentList.add(merchantTypePercentVo);
		// }
		resMap.put("percentList", percentList);
		return resMap;
	}

	/**
	 * 方法描述：商户业务占比表查询
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
	public HashMap<String, Object> queryBusinessPercentList(
			MerchantTypePercentReq req)
					throws TException, IllegalAccessException,
					InvocationTargetException, ParseException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantTypePercentVo> percentList = new ArrayList<MerchantTypePercentVo>();
		MerchantTypePercentVo merchantTypePercentVo = null;
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
		TypePercentResVo typePercentResVo = reportMerchantInfoService
				.getMerchantBussinessPercent(commonParamVo);
		LogCvt.info("商户业务占比后端返回数据：" + JSON.toJSONString(typePercentResVo));
		List<TypePercentVo> list = typePercentResVo.getTypePercentVos();
		if (list != null && list.size() > 0) {
			for (TypePercentVo res : list) {
				merchantTypePercentVo = new MerchantTypePercentVo();
				merchantTypePercentVo
						.setPercent(new BigDecimal(res.getPercent()));
				// if(StringUtil.isNotBlank(res.getType())){
				// //0-面对面 1-团购 3-名优特惠
				// if("0".equals(res.getType())){
				// merchantTypePercentVo.setType("面对面 ");
				// }else if("1".equals(res.getType())){
				// merchantTypePercentVo.setType("团购");
				// }else if("3".equals(res.getType())){
				// merchantTypePercentVo.setType("名优特惠");
				// }else{
				// merchantTypePercentVo.setType("");
				// }
				// }else{
				// merchantTypePercentVo.setType("");
				// }
				merchantTypePercentVo.setType(res.getTypeName());
				percentList.add(merchantTypePercentVo);
			}
		}
		// for(int i =1;i < 6;i++){
		// merchantTypePercentVo = new MerchantTypePercentVo();
		// merchantTypePercentVo.setPercent(new BigDecimal(i/100));
		// merchantTypePercentVo.setType(String.valueOf(i));
		// percentList.add(merchantTypePercentVo);
		// }
		resMap.put("percentList", percentList);
		return resMap;
	}

	/**
	 * 方法描述：商户业务信息统计查询
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: f-road.com.cn
	 * @throws TException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @time: 2015年6月3日 下午3:43:27
	 */
	public HashMap<String, Object> queryMerchantBusinessList(
			MerchantBusiReq req) throws TException, IllegalAccessException,
					InvocationTargetException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantBusiVo> merchantBusiList = new ArrayList<MerchantBusiVo>();
		MerchantBusiVo merchantBusiVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		commonParamVo.setClientId(req.getClientId());
		commonParamVo.setOrgCode(req.getOrgCode());
		if (StringUtil.isNotBlank(req.getBeginDate())
				&& StringUtil.isNotBlank(req.getEndDate())) {
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
		// ReportMerchantBussinessResVo reportMerchantBussinessResVo =
		// reportMerchantInfoService.getMerchantBussinessList(req.getClientId(),
		// req.getOrgCode());
		if (req.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		ReportMerchantBussinessResVo reportMerchantBussinessResVo = reportMerchantInfoService
				.getMerchantBussinessList(commonParamVo);
		LogCvt.info("商户业务信息统计查询后端返回数据："
				+ JSON.toJSONString(reportMerchantBussinessResVo));
		List<ReportMerchantBussinessVo> list = reportMerchantBussinessResVo
				.getMerchantBussinessVos();
		if (list != null && list.size() > 0) {
			for (ReportMerchantBussinessVo vo : list) {
				merchantBusiVo = new MerchantBusiVo();
				merchantBusiVo.setClientId(vo.getClientId());
				merchantBusiVo.setFaceAddCount(vo.getFaceAddCount());
				merchantBusiVo.setFaceCancelCount(vo.getFaceCancelCount());
				merchantBusiVo.setFaceTotalCount(vo.getFaceTotalCount());
				merchantBusiVo.setGroupAddCount(vo.getGroupAddCount());
				merchantBusiVo.setGroupTotalCount(vo.getGroupTotalCount());
				merchantBusiVo.setGroupCancelCount(vo.getGroupCancelCount());
				merchantBusiVo.setOrgCode(vo.getOrgCode());
				merchantBusiVo.setOrgName(vo.getOrgName());
				// 安徽为定制版,有名优系列商品
				if (ClientIdEnum.ANHUI.getDescription()
						.equals(req.getClientId())) {
					merchantBusiVo.setSpecialAddCount(vo.getSpecialAddCount());
					merchantBusiVo
							.setSpecialCancelCount(vo.getSpecialCancelCount());
					merchantBusiVo
							.setSpecialTotalCount(vo.getSpecialTotalCount());
				}
				merchantBusiList.add(merchantBusiVo);
			}
		}
		// for(int i =1;i < 7;i++){
		// merchantTrendVo = new MerchantTrendVo();
		// merchantTrendVo.setAddCount(i*10);
		// merchantTrendVo.setWeek(i);
		// merchantTrendList.add(merchantTrendVo);
		// }
		resMap.put("merchantBussinessList", merchantBusiList);
		return resMap;
	}

	/**
	 * 方法描述：商户信息统计详细数据查询
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
	public HashMap<String, Object> queryMerchantDetailList(
			MerchantDetailReq req) throws TException, IllegalAccessException,
					InvocationTargetException, ParseException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();

		// 分页对象转换
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(
				req.getPageNumber() == 0 ? 1 : req.getPageNumber());
		pageVo.setPageSize(req.getPageSize() == 0 ? 10 : req.getPageSize());
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

		List<MerchantDetailVo> merchantDetailList = new ArrayList<MerchantDetailVo>();
		MerchantDetailVo merchantDetailVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();

		if (StringUtil.isNotBlank(req.getBeginDate())
				&& StringUtil.isNotBlank(req.getEndDate())) {
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
		if (req.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}

		commonParamVo.setClientId(req.getClientId());
		commonParamVo.setOrgCode(req.getOrgCode());
		// ReportMerchantDetailResVo reportMerchantDetailResVo =
		// reportMerchantInfoService.getMerchantDetailList(commonParamVo);//导出调用这个接口，不分页
		ReportMerchantDetailPageVo reportMerchantDetailPageVo = reportMerchantInfoService
				.getMerchantDetailListByPage(pageVo, commonParamVo);// 分页接口
		LogCvt.info("商户信息统计详细数据分页查询后端返回数据："
				+ JSON.toJSONString(reportMerchantDetailPageVo));
		List<ReportMerchantDetailVo> list = reportMerchantDetailPageVo
				.getMerchantDetailVos();
		if (list != null && list.size() > 0) {
			for (ReportMerchantDetailVo res : list) {
				merchantDetailVo = new MerchantDetailVo();
				merchantDetailVo.setPercent(new BigDecimal(res.getPercent())
						.setScale(4, BigDecimal.ROUND_HALF_UP));
				merchantDetailVo.setAddCount(res.getAddCount());
				merchantDetailVo.setChangeAmountCount(res.getChangeCount());
				merchantDetailVo
						.setOrderAmount(new BigDecimal(res.getTotalAmount())
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				merchantDetailVo.setOrderCount(res.getOrderCount());
				merchantDetailVo.setOrgCode(res.getOrgCode());
				merchantDetailVo.setOrgName(res.getOrgName());
				merchantDetailVo.setTotalMerchantCount(res.getTotalCount());
				merchantDetailList.add(merchantDetailVo);
			}
		}
		resMap.put("merchantDetailList", merchantDetailList);
		if (reportMerchantDetailPageVo.getPageVo() != null) {
			resMap.put("pageCount",
					reportMerchantDetailPageVo.getPageVo().getPageCount());
			resMap.put("totalCountExport",
					reportMerchantDetailPageVo.getPageVo().getTotalCount());
			resMap.put("totalCount",
					reportMerchantDetailPageVo.getPageVo().getTotalCount());
			resMap.put("pageNumber",
					reportMerchantDetailPageVo.getPageVo().getPageNumber());
			resMap.put("lastPageNumber",
					reportMerchantDetailPageVo.getPageVo().getLastPageNumber());
			resMap.put("firstRecordTime", reportMerchantDetailPageVo.getPageVo()
					.getFirstRecordTime());
			resMap.put("lastRecordTime",
					reportMerchantDetailPageVo.getPageVo().getLastRecordTime());
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
	 * 方法描述：商户信息统计详细数据导出查询
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
	public HashMap<String, Object> queryExportMerchantDetailList(
			MerchantDetailReq req) throws TException, IllegalAccessException,
					InvocationTargetException, ParseException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantDetailVo> merchantDetailList = new ArrayList<MerchantDetailVo>();
		MerchantDetailVo merchantDetailVo = null;
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
		ReportMerchantDetailResVo reportMerchantDetailResVo = reportMerchantInfoService
				.getMerchantDetailList(commonParamVo);// 导出调用这个接口，不分页
		// ReportMerchantDetailPageVo reportMerchantDetailPageVo =
		// reportMerchantInfoService.getMerchantDetailListByPage(pageVo,
		// commonParamVo);//分页接口
		LogCvt.info("商户信息统计详细数据后端返回数据："
				+ JSON.toJSONString(reportMerchantDetailResVo));
		List<ReportMerchantDetailVo> list = reportMerchantDetailResVo
				.getMerchantDetailVos();
		if (list != null && list.size() > 0) {
			for (ReportMerchantDetailVo res : list) {
				merchantDetailVo = new MerchantDetailVo();
				merchantDetailVo
						.setPercent(new BigDecimal(res.getPercent() * 100)
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				merchantDetailVo.setAddCount(res.getAddCount());
				merchantDetailVo.setChangeAmountCount(res.getChangeCount());
				merchantDetailVo
						.setOrderAmount(new BigDecimal(res.getTotalAmount())
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				merchantDetailVo.setOrderCount(res.getOrderCount());
				merchantDetailVo.setOrgCode(res.getOrgCode());
				merchantDetailVo.setOrgName(res.getOrgName());
				merchantDetailVo.setTotalMerchantCount(res.getTotalCount());
				merchantDetailList.add(merchantDetailVo);
			}
		}
		resMap.put("merchantDetailList", merchantDetailList);
		return resMap;
	}

	/**
	 * 
	 * queryMerchantOutletList:商户门店信息导出
	 *
	 * @author 明灿 2015年10月12日 上午11:57:57
	 * @param req
	 * @return
	 * @throws Exception
	 *
	 */
	public HashMap<String, Object> queryMerchantOutletList(MerchantBusiReq req)
			throws Exception {
		LogCvt.info("商户门店信息请求参数：" + JSON.toJSONString(req));
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		List<MerchantOutletRePortResVo> merchantOutletList = new ArrayList<MerchantOutletRePortResVo>();
		MerchantOutletRePortResVo merchantOutletVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (StringUtil.isNotBlank(req.getBeginDate())
				&& StringUtil.isNotBlank(req.getEndDate())) {
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
		if (req.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		ReportMerchantOutletResVo merchantOutletResList = reportMerchantInfoService
				.getMerchantOutletList(commonParamVo);
		if (merchantOutletResList != null) {
			LogCvt.info(
					"商户门店信息后端返回数据：" + JSON.toJSONString(merchantOutletResList));
			List<ReportMerchantOutletVo> merchantOutletVos = merchantOutletResList
					.getMerchantOutletVos();
			if (merchantOutletVos != null && merchantOutletVos.size() > 0) {
				for (ReportMerchantOutletVo vo : merchantOutletVos) {
					merchantOutletVo = new MerchantOutletRePortResVo();
					merchantOutletVo.setMerchantName(vo.getMerchantName());
					merchantOutletVo.setMerchantOutletCount(
							vo.getMerchantOutletCount());
					merchantOutletVo.setOrgName(vo.getOrgName());
					merchantOutletVo.setOutletAddress(vo.getOutletAddress());
					merchantOutletVo.setOutletName(vo.getOutletName());
					merchantOutletList.add(merchantOutletVo);
				}
			}
		}
		resMap.put("merchantOutletList", merchantOutletList);
		return resMap;
	}

	// public static void main(String[] args) {
	// String t = "2015-05-02 00:00:00";
	// String t2 = "2015-06-05 23:59:59";
	// SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// try {
	// System.out.println(re.parse(t).getTime() + " " +re.parse(t2).getTime());
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
}
