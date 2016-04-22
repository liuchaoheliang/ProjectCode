package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.ClientIdEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.NumberUtil4Bank;
import com.froad.cbank.coremodule.module.normal.bank.vo.BussinessStatisticResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.DateForReportVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo4Trend;
import com.froad.cbank.coremodule.module.normal.bank.vo.PercentResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.SaleTrendBankResVo;
import com.froad.thrift.service.ReportProductInfoService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.report.BusinessSaleDetailResVo;
import com.froad.thrift.vo.report.BusinessSaleDetailVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.MerchantSaleDetailResVo;
import com.froad.thrift.vo.report.MerchantSaleDetailVo;
import com.froad.thrift.vo.report.SaleCountDetailPageVo;
import com.froad.thrift.vo.report.SaleCountDetailResVo;
import com.froad.thrift.vo.report.SaleCountDetailVo;
import com.froad.thrift.vo.report.SaleTrendResVo;
import com.froad.thrift.vo.report.SaleTrendVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;

@Service
public class BusinessStatisticsService {

	@Resource
	ReportProductInfoService.Iface reportProductInfoService;

	/**
	 * 
	 * 方法名称: trend 简要描述: 销售额走势 版本信息: V1.0 创建时间: 2015年7月22日 下午2:08:36 创建作者: 明灿
	 * chenmingcan@f-road.com.cn 方法参数: @param reqVo 方法参数: @param clientId
	 * 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> trend(ExcelReqVo4Trend reqVo, String clientId) {
		// 销售额走势
		LogCvt.info("销售额走势请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<SaleTrendBankResVo> list = null;
		SaleTrendBankResVo bankResVo = null;
		CommonParamVo paramVo = new CommonParamVo();
		paramVo.setFlag(false);
		// 设置起始和结束时间
		if (StringUtil.isNotBlank(reqVo.getBeginDate())
				&& StringUtil.isNotBlank(reqVo.getEndDate())) {
			DateForReportVo result = DateUtilForReport.getBeginDateAndEndDate(
					reqVo.getBeginDate(), reqVo.getEndDate());
			paramVo.setBegDate(result.getBeginDate());
			paramVo.setEndDate(result.getEndDate());
		}
		paramVo.setClientId(clientId);
		// paramVo.setClientId("anhui");
		paramVo.setOrgCode(reqVo.getOrgCode());
		// paramVo.setOrgCode("340000");
		try {
			SaleTrendResVo saleTrend = reportProductInfoService
					.getSaleTrend(paramVo);
			LogCvt.info("销售额走势请求返回数据:", JSON.toJSONString(saleTrend));
			if (null != saleTrend) {
				List<SaleTrendVo> trendList = saleTrend.getSaleTrendVos();
				if (null != trendList && trendList.size() > 0) {
					list = new ArrayList<SaleTrendBankResVo>();
					for (SaleTrendVo saleTrendVo : trendList) {
						bankResVo = new SaleTrendBankResVo();
						bankResVo.setWeek(saleTrendVo.getWeek());
						bankResVo.setSaleAmountCount(
								saleTrendVo.getSaleProductAmount());
						list.add(bankResVo);
					}
				}
				map.put("saleTrendList", list);
			}
		} catch (Exception e) {
			LogCvt.info("销售额走势查询异常", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "销售额走势查询失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: saleTypePercent 简要描述: 占比走势 版本信息: V1.0 创建时间: 2015年7月22日 下午2:08:21
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo 方法参数: @param
	 * clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> saleTypePercent(ExcelReqVo reqVo,
			String clientId) {
		// 占比
		LogCvt.info("业务类型占比请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<PercentResVo> list = null;
		PercentResVo percentResVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		// 设置起始和结束时间
		if (StringUtil.isNotBlank(reqVo.getBeginDate())
				&& StringUtil.isNotBlank(reqVo.getEndDate())) {
			DateForReportVo result = DateUtilForReport.getBeginDateAndEndDate(
					reqVo.getBeginDate(), reqVo.getEndDate());
			commonParamVo.setBegDate(result.getBeginDate());
			commonParamVo.setEndDate(result.getEndDate());
		}
		commonParamVo.setClientId(clientId);
		// commonParamVo.setClientId("anhui");
		commonParamVo.setOrgCode(reqVo.getOrgCode());
		// commonParamVo.setOrgCode("340000");
		try {
			TypePercentResVo resVo = reportProductInfoService
					.getSaleTypePercent(commonParamVo);
			LogCvt.info("业务类型占比请求返回数据:", JSON.toJSONString(resVo));
			if (null != resVo) {
				List<TypePercentVo> resList = resVo.getTypePercentVos();
				list = new ArrayList<PercentResVo>();
				if (null != resList && resList.size() != 0) {
					for (TypePercentVo saleTypePercentVo : resList) {
						percentResVo = new PercentResVo();
						percentResVo.setPercent(saleTypePercentVo.getPercent());
						// String type = saleTypePercentVo.getType();
						percentResVo.setType(saleTypePercentVo.getTypeName());
						// String type = saleTypePercentVo.getType();
						// percentResVo.setType(PaymentMethod.getDescrible(type));
						// if ("0".equals(type)) {
						// percentResVo.setType("面对面");
						// }
						// if ("1".equals(type)) {
						// percentResVo.setType("团购");
						// }
						// if ("2".equals(type)) {
						// percentResVo.setType("精品预售");
						// }
						// if ("3".equals(type)) {
						// percentResVo.setType("名优特惠");
						// }
						// if ("4".equals(type)) {
						// percentResVo.setType("线上积分兑换");
						// }
						// if ("5".equals(type)) {
						// percentResVo.setType("线下积分兑换");
						// }

						list.add(percentResVo);
					}
					// map.put("1", "1");
					map.put("percentList", list);
				}
			}
		} catch (Exception e) {
			LogCvt.info("业务类型占比异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "业务类型占比失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: payTypePercent 简要描述: 支付方式占比 版本信息: V1.0 创建时间: 2015年7月22日 下午2:08:48
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo 方法参数: @param
	 * clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> payTypePercent(ExcelReqVo reqVo,
			String clientId) {
		// 支付方式占比
		LogCvt.info("支付方式占比请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<PercentResVo> list = null;
		PercentResVo percentResVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		commonParamVo.setOrgCode(reqVo.getOrgCode());
		// commonParamVo.setOrgCode("340000");
		commonParamVo.setClientId(clientId);
		// commonParamVo.setClientId("anhui");
		// 设置起始和结束时间
		if (StringUtil.isNotBlank(reqVo.getBeginDate())
				&& StringUtil.isNotBlank(reqVo.getEndDate())) {
			DateForReportVo result = DateUtilForReport.getBeginDateAndEndDate(
					reqVo.getBeginDate(), reqVo.getEndDate());
			commonParamVo.setBegDate(result.getBeginDate());
			commonParamVo.setEndDate(result.getEndDate());
		}
		try {
			TypePercentResVo resVo = reportProductInfoService
					.getPayTypePercent(commonParamVo);
			LogCvt.info("支付方式占比请求返回数据:", JSON.toJSONString(resVo));
			if (null != resVo) {
				List<TypePercentVo> resList = resVo.getTypePercentVos();
				if (null != resList && resList.size() > 0) {
					list = new ArrayList<PercentResVo>();
					for (TypePercentVo payTypePercentVo : resList) {
						percentResVo = new PercentResVo();
						percentResVo.setPercent(payTypePercentVo.getPercent());
						// String type = payTypePercentVo.getType();
						// 获取中文分类名
						// percentResVo.setType(PaymentMethod.getDescrible(type));
						percentResVo.setType(payTypePercentVo.getTypeName());
						/**
						 * if ("1".equals(type)) {
						 * percentResVo.setType("方付通积分"); } if
						 * ("2".equals(type)) { percentResVo.setType("银行积分"); }
						 * if ("20".equals(type)) {
						 * percentResVo.setType("贴膜卡支付"); } if
						 * ("51".equals(type)) { percentResVo.setType("快捷支付"); }
						 * if ("55".equals(type)) {
						 * percentResVo.setType("即时支付"); }
						 */
						list.add(percentResVo);
					}
					map.put("percentList", list);
				}
			}
		} catch (Exception e) {
			LogCvt.info("支付方式占比异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "支付方式占比失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: statisticList 简要描述: 业务销售统计列表查询 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:08:58 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> statisticList(ExcelReqVo reqVo,
			String clientId) {
		// 业务销售统计列表查询
		LogCvt.info("业务销售统计列表查询请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<BussinessStatisticResVo> list = null;
		BussinessStatisticResVo bussinessStatisticResVo = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		commonParamVo.setOrgCode(reqVo.getOrgCode());
		// commonParamVo.setOrgCode("340000");
		commonParamVo.setClientId(clientId);
		// commonParamVo.setClientId("anhui");
		// 设置起始和结束时间
		if (StringUtil.isNotBlank(reqVo.getBeginDate())
				&& StringUtil.isNotBlank(reqVo.getEndDate())) {
			DateForReportVo result = DateUtilForReport.getBeginDateAndEndDate(
					reqVo.getBeginDate(), reqVo.getEndDate());
			commonParamVo.setBegDate(result.getBeginDate());
			commonParamVo.setEndDate(result.getEndDate());
		}
		PageVo pageVo = new PageVo();
		if (null != reqVo.getPageNumber()) {
			pageVo.setPageNumber(reqVo.getPageNumber());
		}
		if (null != reqVo.getPageSize()) {
			pageVo.setPageSize(reqVo.getPageSize());
		}
		try {
			SaleCountDetailPageVo saleCountDetailListByPage = reportProductInfoService
					.getSaleCountDetailListByPage(pageVo, commonParamVo);
			LogCvt.info("业务销售统计列表查询请求返回数据:",
					JSON.toJSONString(saleCountDetailListByPage));
			if (null != saleCountDetailListByPage) {
				List<SaleCountDetailVo> resList = saleCountDetailListByPage
						.getSaleCountDetailVos();
				// 封装分页信息
				PageVo pageVo2 = saleCountDetailListByPage.getPageVo();
				// BaseVo baseVo = new BaseVo();
				// BeanUtils.copyProperties(baseVo, pageVo2);
				// map.put("page", baseVo);
				if (null != pageVo2) {
					map.put("pageCount", pageVo2.getPageCount());
					map.put("totalCountExport", pageVo2.getTotalCount());
					map.put("totalCount", pageVo2.getTotalCount());
					map.put("pageNumber", pageVo2.getPageNumber());
					map.put("lastPageNumber", pageVo2.getLastPageNumber());
					map.put("firstRecordTime", pageVo2.getFirstRecordTime());
					map.put("lastRecordTime", pageVo2.getLastRecordTime());
				} else {
					map.put("pageCount", 0);
					map.put("totalCountExport", 0);
					map.put("totalCount", 0);
					map.put("pageNumber", 1);
					map.put("lastPageNumber", 0);
					map.put("firstRecordTime", 0);
					map.put("lastRecordTime", 0);
				}
				list = new ArrayList<BussinessStatisticResVo>();
				if (null != resList && resList.size() > 0) {
					for (SaleCountDetailVo saleCountDetailVo : resList) {
						bussinessStatisticResVo = new BussinessStatisticResVo();
						// 属性注入
						bussinessStatisticResVo.setAverAmount(
								saleCountDetailVo.getAverAmount());
						bussinessStatisticResVo
								.setBuyCount(saleCountDetailVo.getBuyCount());
						bussinessStatisticResVo.setOrderCount(
								saleCountDetailVo.getOrderCount());
						bussinessStatisticResVo
								.setOrgCode(saleCountDetailVo.getOrgCode());
						bussinessStatisticResVo
								.setOrgName(saleCountDetailVo.getOrgName());
						bussinessStatisticResVo.setProductSaleAmount(
								saleCountDetailVo.getProductSaleAmount());
						bussinessStatisticResVo.setProductSaleCount(
								saleCountDetailVo.getProductSaleCount());
						bussinessStatisticResVo.setTotalAmount(
								saleCountDetailVo.getTotalAmount());
						// BeanUtils.copyProperties(bussinessStatisticResVo,
						// saleCountDetailVo);
						list.add(bussinessStatisticResVo);
					}
				}
				map.put("bussinessStatisticList", list);
			}
		} catch (TException e) {
			LogCvt.info("业务销售统计列表查询异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "业务销售统计列表查询失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: statisticListExport 简要描述: 业务销售统计列表下载 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:09:11 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> statisticListExport(ExcelReqVo reqVo,
			String clientId) {
		// 业务销售统计列表下载
		LogCvt.info("业务销售统计列表下载请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		List<List<String>> data = null;
		HSSFWorkbook workBook = null;
		List<String> list = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		commonParamVo.setOrgCode(reqVo.getOrgCode());
		// commonParamVo.setOrgCode("340000");
		commonParamVo.setClientId(clientId);
		// commonParamVo.setClientId("anhui");
		// 设置起始和结束时间
		if (StringUtil.isNotBlank(reqVo.getBeginDate())
				&& StringUtil.isNotBlank(reqVo.getEndDate())) {
			DateForReportVo result = DateUtilForReport.getBeginDateAndEndDate(
					reqVo.getBeginDate(), reqVo.getEndDate());
			commonParamVo.setBegDate(result.getBeginDate());
			commonParamVo.setEndDate(result.getEndDate());
		}
		try {
			SaleCountDetailResVo resVo = reportProductInfoService
					.getSaleCountDetail(commonParamVo);
			LogCvt.info("业务销售统计列表下载请求返回数据:", JSON.toJSONString(resVo));
			if (EnumTypes.success.getCode()
					.equals(resVo.getResultVo().getResultCode())) {
				// 调用后台接口成功返回数据.....
				if (null != resVo) {
					List<SaleCountDetailVo> resList = resVo
							.getSaleCountDetailVos();
					data = new ArrayList<List<String>>();
					if (null != resList && resList.size() > 0) {
						// 数据正常
						int index = 0;
						for (SaleCountDetailVo saleResVo : resList) {
							// 封装到list集合中
							list = new ArrayList<String>();
							list.add(++index + "");// 序号
							list.add(saleResVo.getOrgCode());// 机构号
							list.add(saleResVo.getOrgName());// 机构名
							list.add(saleResVo.getOrderCount() + "");// 订单数
							list.add(
									amountToString(saleResVo.getTotalAmount()));// 订单总金额
							// list.add("¥" + saleResVo.getTotalAmount());//
							// 订单总金额
							list.add(saleResVo.getProductSaleCount() + "");// 商品销售数量
							list.add(amountToString(
									saleResVo.getProductSaleAmount()));// 商品销售金额
							// list.add("¥" +
							// saleResVo.getProductSaleAmount());// 商品销售金额
							list.add(saleResVo.getBuyCount() + "");// 购买人次
							list.add(amountToString(saleResVo.getAverAmount()));// 人均消费金额
							// list.add("¥" + saleResVo.getAverAmount());//
							// 人均消费金额
							// 将list添加到list中
							data.add(list);
						}
					}
					// 封装标题
					List<String> title = new ArrayList<String>();
					title.add("序号");
					title.add("机构号");
					if (ClientIdEnum.ANHUI.getDescription().equals(clientId)) {
						title.add("省联社/法人行社/支行/网点");
					} else {
						title.add("所属机构");
					}
					title.add("订单数");
					title.add("订单总金额");
					title.add("商品销售数量");
					title.add("商品销售金额");
					title.add("购买人次");
					title.add("人均消费金额");
					workBook = ExcelUtil.generate(title, data, "业务销售统计");
					map.put("workBook", workBook);
				}
			}
		} catch (TException e) {
			LogCvt.info("业务销售统计列表下载异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "业务销售统计列表下载失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: bussinessSaleExport 简要描述: 业务销售统计详情列表下载 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:09:21 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> bussinessSaleExport(ExcelReqVo reqVo,
			String clientId) {
		LogCvt.info("业务销售统计详情列表下载请求参数:", JSON.toJSONString(reqVo));
		// 业务销售统计详情列表下载
		Map<String, Object> map = new HashMap<String, Object>();
		List<List<String>> data = null;
		List<String> list = null;
		HSSFWorkbook workBook = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		commonParamVo.setOrgCode(reqVo.getOrgCode());
		// commonParamVo.setOrgCode("340000");
		commonParamVo.setClientId(clientId);
		// commonParamVo.setClientId("anhui");
		// 设置起始和结束时间
		if (StringUtil.isNotBlank(reqVo.getBeginDate())
				&& StringUtil.isNotBlank(reqVo.getEndDate())) {
			DateForReportVo result = DateUtilForReport.getBeginDateAndEndDate(
					reqVo.getBeginDate(), reqVo.getEndDate());
			commonParamVo.setBegDate(result.getBeginDate());
			commonParamVo.setEndDate(result.getEndDate());
		}
		try {
			BusinessSaleDetailResVo resVo = reportProductInfoService
					.getBusinessSaleDetail(commonParamVo);
			LogCvt.info("业务销售统计详情列表下载请求返回数据:", JSON.toJSONString(resVo));
			if (EnumTypes.success.getCode()
					.equals(resVo.getResultVo().getResultCode())) {
				// 调用后台接口成功返回数据.....
				if (null != resVo) {
					List<BusinessSaleDetailVo> resList = resVo
							.getBusinessSaleDetailVos();
					data = new ArrayList<List<String>>();
					if (null != resList && resList.size() > 0) {
						// 数据正常
						int index = 0;
						for (BusinessSaleDetailVo saleResVo : resList) {
							// 封装到list集合中
							list = new ArrayList<String>();
							list.add(++index + "");// 序号
							list.add(saleResVo.getType());// 业务类型
							list.add(saleResVo.getOrgCode());// 机构号
							list.add(saleResVo.getOrgName());// 机构名
							list.add(saleResVo.getOrderCount() + "");// 订单数
							// list.add(amountToString(saleResVo.getOrderAmount()));//
							// 总订单金额
							// // list.add("¥" + saleResVo.getOrderAmount());//
							// // 总订单金额
							// list.add(amountToString(saleResVo.getCashAmount()));//
							// 现金总额
							// // list.add("¥" + saleResVo.getCashAmount());//
							// 现金总额
							// list.add(NumberUtil4Bank.amountToString(saleResVo
							// .getBankPointAmount()));// 银行积分总额
							// //list.add(saleResVo.getBankPointAmount() +
							// "");// 银行积分总额
							// list.add(NumberUtil4Bank.amountToString(saleResVo
							// .getFftPointAmount()));// 联盟积分总额
							// // list.add(saleResVo.getFftPointAmount() +
							// "");//
							// // 联盟积分总额
							list.add(saleResVo.getProductCount() + "");// 商品销售数量
							list.add(amountToString(
									saleResVo.getProductAmount()));// 商品销售金额
							// list.add("¥" + saleResVo.getProductAmount());//
							// 商品销售金额
							list.add(saleResVo.getBuyCount() + "");// 购买人次
							// 讲list添加到list中
							data.add(list);
						}
					}
					// 封装标题
					List<String> title = new ArrayList<String>();
					title.add("序号");
					title.add("业务类型");
					title.add("机构号");
					title.add("机构名");
					title.add("订单数");
					// title.add("总订单金额");
					// title.add("现金总额");
					// title.add("银行积分总额");
					// title.add("联盟积分总额");
					title.add("商品销售数量");
					title.add("商品销售金额");
					title.add("购买人次");
					workBook = ExcelUtil.generate(title, data, "业务销售统计详情");
					map.put("workBook", workBook);
				}
			} else {
				// 调用后台接口失败.....
				map.put("code", EnumTypes.fail.getCode());
				map.put("message", resVo.getResultVo().getResultCode());
				return map;
			}
		} catch (TException e) {
			LogCvt.info("业务销售统计详情列表下载异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "业务销售统计详情列表下载失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: merchantSaleExport 简要描述: 商户业务销售详情列表下载 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:09:32 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> merchantSaleExport(ExcelReqVo reqVo,
			String clientId) {
		LogCvt.info("商户业务销售详情列表下载请求参数:", JSON.toJSONString(reqVo));
		// 商户业务销售详情列表下载
		Map<String, Object> map = new HashMap<String, Object>();
		HSSFWorkbook workBook = null;
		List<List<String>> data = null;
		List<String> list = null;
		CommonParamVo commonParamVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			commonParamVo.setFlag(false);
		} else {
			commonParamVo.setFlag(true);
		}
		commonParamVo.setOrgCode(reqVo.getOrgCode());
		// commonParamVo.setOrgCode("340000");
		commonParamVo.setClientId(clientId);
		// commonParamVo.setClientId("anhui");
		// 设置起始和结束时间
		if (StringUtil.isNotBlank(reqVo.getBeginDate())
				&& StringUtil.isNotBlank(reqVo.getEndDate())) {
			DateForReportVo result = DateUtilForReport.getBeginDateAndEndDate(
					reqVo.getBeginDate(), reqVo.getEndDate());
			commonParamVo.setBegDate(result.getBeginDate());
			commonParamVo.setEndDate(result.getEndDate());
		}
		try {
			MerchantSaleDetailResVo merchantSaleDetail = reportProductInfoService
					.getMerchantSaleDetail(commonParamVo);
			// int i = 1 / 0;
			LogCvt.info("业商户业务销售详情列表下载请求返回数据:",
					JSON.toJSONString(merchantSaleDetail));
			if (EnumTypes.success.getCode()
					.equals(merchantSaleDetail.getResultVo().getResultCode())) {
				// 调用后台接口成功返回数据.....
				data = new ArrayList<List<String>>();
				if (null != merchantSaleDetail) {
					List<MerchantSaleDetailVo> resList = merchantSaleDetail
							.getMerchantSaleDetailVos();
					if (null != resList && resList.size() > 0) {
						// 数据正常
						int index = 0;
						for (MerchantSaleDetailVo merchantSaleDetailVo : resList) {
							// 封装到list集合中
							list = new ArrayList<String>();
							list.add(++index + "");// 序号
							list.add(merchantSaleDetailVo.getMerchantId());// 商户号
							list.add(merchantSaleDetailVo.getMerchantName());// 商户名称
							list.add(merchantSaleDetailVo.getMerchantType());// 商户类型
							list.add(merchantSaleDetailVo.getOrgName());// 银行名称
							list.add(merchantSaleDetailVo.getOrgCode());// 机构号
							list.add(merchantSaleDetailVo.getGroupOrderCount()
									+ "");// 团购订单数
							list.add(merchantSaleDetailVo.getFaceOrderCount()
									+ "");// 面对面订单数

							if (ClientIdEnum.ANHUI.getDescription()
									.equals(clientId)) {
								list.add(merchantSaleDetailVo
										.getSpecialOrderCount() + "");// 名优特惠订单数
							}
							list.add(merchantSaleDetailVo.getPresellOrderCount()
									+ "");// 精品预售订单数
							list.add(merchantSaleDetailVo.getOrderCount() + "");// 订单数
							list.add(amountToString(
									merchantSaleDetailVo.getOrderAmount()));// 订单总金额
							// list.add("¥"
							// + merchantSaleDetailVo.getOrderAmount());// 订单总金额
							list.add(amountToString(
									merchantSaleDetailVo.getRefundAmount()));// 退款总金额
							// list.add("¥"
							// + merchantSaleDetailVo.getRefundAmount());//
							// 退款总金额
							// 将list添加到list中
							data.add(list);
						}
					}
					// 封装标题
					List<String> title = new ArrayList<String>();
					title.add("序号");
					title.add("商户号");
					title.add("商户名称");
					title.add("商户类型");
					title.add("银行名称");
					title.add("机构号");
					title.add("团购订单数");
					title.add("面对面订单数");
					if (ClientIdEnum.ANHUI.getDescription().equals(clientId)) {
						title.add("名优特惠订单数");
						title.add("精品预售订单数");
					} else {
						title.add("惠预售订单数");
					}
					// if
					// (ClientIdEnum.CHONG_QING.getDescription().equals(clientId))
					// {
					// }
					title.add("订单数");
					title.add("订单总金额");
					title.add("退款总金额");
					workBook = ExcelUtil.generate(title, data, "商户业务销售详情");
					map.put("workBook", workBook);
				}
			} else {
				// 调用后台接口失败.....
				map.put("code", EnumTypes.fail.getCode());
				map.put("message",
						merchantSaleDetail.getResultVo().getResultDesc());
				return map;
			}

		} catch (Exception e) {
			// 调用后台异常
			LogCvt.info("商户业务销售详情列表下载异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "商户业务销售详情列表下载失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: percentToString 简要描述: 返回百分比 版本信息: V1.0 创建时间: 2015年7月31日 下午2:43:24
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param percent 方法参数: @return
	 * 返回类型: String
	 * 
	 * @throws
	 */
	@SuppressWarnings("unused")
	private String percentToString(double percent) {
		return NumberUtil4Bank.percentToString(percent);
	}

	/**
	 * 
	 * 方法名称: amountToString 简要描述: 格式化金额保留2位小数 版本信息: V1.0 创建时间: 2015年7月31日
	 * 下午2:45:04 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param amount
	 * 方法参数: @return 返回类型: String
	 * 
	 * @throws
	 */
	private String amountToString(double amount) {
		return "¥" + NumberUtil4Bank.amountToString(amount);
	}
}
