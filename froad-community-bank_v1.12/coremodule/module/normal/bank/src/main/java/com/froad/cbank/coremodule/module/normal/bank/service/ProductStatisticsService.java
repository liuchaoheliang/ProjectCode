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
import com.froad.cbank.coremodule.module.normal.bank.vo.DateForReportVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo4Trend;
import com.froad.cbank.coremodule.module.normal.bank.vo.PercentResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductsSaleDetailResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.SaleTrendBankResVo;
import com.froad.thrift.service.ReportProductService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.ProductSaleDetailPageVo;
import com.froad.thrift.vo.report.ProductSaleDetailResVo;
import com.froad.thrift.vo.report.ProductSaleDetailVo;
import com.froad.thrift.vo.report.ProductSaleTrendResVo;
import com.froad.thrift.vo.report.ProductSaleTrendVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;

/**
 * 
 * 类名: ProductStatisticsService 描述: 社区银行商品统计 作者: 明灿 chenmingcan@f-road.com.cn
 * 日期: 2015年7月22日 下午2:20:41
 *
 */
@Service
public class ProductStatisticsService {

	@Resource
	ReportProductService.Iface reportProductService;

	/**
	 * 
	 * 方法名称: trend 简要描述: 销售走势 版本信息: V1.0 创建时间: 2015年7月22日 下午2:20:52 创建作者: 明灿
	 * chenmingcan@f-road.com.cn 方法参数: @param reqVo 方法参数: @param clientId
	 * 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> trend(ExcelReqVo4Trend reqVo, String clientId) {
		// 销售额走势
		LogCvt.info("销售额走势请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		List<SaleTrendBankResVo> list = null;
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
			ProductSaleTrendResVo resVo = reportProductService
					.getProductSaleTrend(paramVo);
			LogCvt.info("销售额走势请求返回数据:", JSON.toJSONString(resVo));
			if (null != resVo) {
				List<ProductSaleTrendVo> resList = resVo
						.getProductSaleTrendVos();
				if (null != resList && resList.size() > 0) {
					list = new ArrayList<SaleTrendBankResVo>();
					for (ProductSaleTrendVo saleTrend : resList) {
						bankResVo = new SaleTrendBankResVo();
						bankResVo.setWeek(saleTrend.getWeek());
						bankResVo.setSaleProductNumber(
								saleTrend.getSaleProductNumber());
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
	 * 方法名称: typePercent 简要描述: 业务类型销售占比 版本信息: V1.0 创建时间: 2015年7月22日 下午2:21:01
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo 方法参数: @param
	 * clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> typePercent(ExcelReqVo reqVo, String clientId) {
		// 业务类型销售占比
		Map<String, Object> map = new HashMap<String, Object>();
		List<PercentResVo> list = null;
		PercentResVo percentResVo = null;
		CommonParamVo paramVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			paramVo.setFlag(false);
		} else {
			paramVo.setFlag(true);
		}
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
			TypePercentResVo resVo = reportProductService
					.getProductTypePercent(paramVo);
			LogCvt.info("业务类型销售占比请求返回数据:", JSON.toJSONString(resVo));
			if (null != resVo) {
				List<TypePercentVo> resList = resVo.getTypePercentVos();
				if (null != resList && resList.size() > 0) {
					list = new ArrayList<PercentResVo>();
					for (TypePercentVo saleTrend : resList) {
						percentResVo = new PercentResVo();
						percentResVo.setPercent(saleTrend.getPercent());
						percentResVo.setType(saleTrend.getTypeName());
						/*
						 * String type = saleTrend.getType(); //
						 * percentResVo.setType
						 * (PaymentMethod.getDescrible(type)); if
						 * ("0".equals(type)) { percentResVo.setType("面对面"); }
						 * if ("1".equals(type)) { percentResVo.setType("团购"); }
						 * if ("2".equals(type)) { percentResVo.setType("精品预售");
						 * } if ("3".equals(type)) {
						 * percentResVo.setType("名优特惠"); } if ("4".equals(type))
						 * { percentResVo.setType("线上积分兑换"); } if
						 * ("5".equals(type)) { percentResVo.setType("线下积分兑换");
						 * }
						 */
						list.add(percentResVo);
					}
				}
				map.put("percentList", list);
			}
		} catch (Exception e) {
			LogCvt.info("业务类型销售占比查询异常", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "业务类型销售占比查询失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: categoryTypePercent 简要描述: 商品类目占比 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:21:11 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> categoryTypePercent(ExcelReqVo reqVo,
			String clientId) {
		// 商品类目占比
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<PercentResVo> list = null;
		PercentResVo percentResVo = null;
		CommonParamVo paramVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			paramVo.setFlag(false);
		} else {
			paramVo.setFlag(true);
		}
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
			TypePercentResVo resVo = reportProductService
					.getProductCategoryPercent(paramVo);
			LogCvt.info("商品类目占比请求返回数据:", JSON.toJSONString(resVo));
			if (null != resVo) {
				List<TypePercentVo> resList = resVo.getTypePercentVos();
				if (null != resList && resList.size() > 0) {
					list = new ArrayList<PercentResVo>();
					for (TypePercentVo saleTrend : resList) {
						percentResVo = new PercentResVo();
						percentResVo.setType(saleTrend.getType());
						percentResVo.setPercent(saleTrend.getPercent());
						percentResVo.setTypeName(saleTrend.getTypeName());
						list.add(percentResVo);
					}
				}
				map.put("percentList", list);
			}
		} catch (Exception e) {
			LogCvt.info("商品类目占比查询异常", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "商品类目占比查询失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: saleDetailList 简要描述: 商品销售详情列表查询 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:21:19 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> saleDetailList(ExcelReqVo reqVo,
			String clientId) {
		// 商品销售详情列表查询
		LogCvt.info("商品销售详情列表请求返回数据:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProductsSaleDetailResVo> list = null;
		ProductsSaleDetailResVo percentResVo = null;
		CommonParamVo paramVo = new CommonParamVo();
		if (reqVo.getFlag().equals("0")) {
			paramVo.setFlag(false);
		} else {
			paramVo.setFlag(true);
		}
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
		PageVo pageVo = new PageVo();
		if (null != reqVo.getPageNumber()) {
			pageVo.setPageNumber(reqVo.getPageNumber());
		}
		if (null != reqVo.getPageSize()) {
			pageVo.setPageSize(reqVo.getPageSize());
		}
		try {
			ProductSaleDetailPageVo listByPage = reportProductService
					.getProductSaleDetailListByPage(pageVo, paramVo);
			LogCvt.info("商品销售详情列表请求返回数据:", JSON.toJSONString(listByPage));
			// BaseVo baseVo = new BaseVo();
			PageVo pageVo2 = listByPage.getPageVo();
			// BeanUtils.copyProperties(baseVo, pageVo2);
			// map.put("page", baseVo);
			/** 封装分页信息 */
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
			if (null != listByPage) {
				List<ProductSaleDetailVo> resList = listByPage
						.getProductSaleDetailVos();
				list = new ArrayList<ProductsSaleDetailResVo>();
				if (null != resList && resList.size() > 0) {
					for (ProductSaleDetailVo saleDetail : resList) {
						percentResVo = new ProductsSaleDetailResVo();
						percentResVo.setOrgCode(saleDetail.getOrgCode());// 机构号
						percentResVo.setOrgName(saleDetail.getOrgName());// 机构名称
						percentResVo.setAddProductCount(
								saleDetail.getAddProductCount());// 新增商品数
						percentResVo
								.setProductCount(saleDetail.getProductCount());// 商品总数
						percentResVo.setProductSaleCount(
								saleDetail.getProductSaleCount());// 商品销售数量
						percentResVo.setProductSaleAmount(
								saleDetail.getProductSaleAmount());// 商品销售金额
						percentResVo
								.setRefundAmount(saleDetail.getRefundAmount());// 退款总金额
						list.add(percentResVo);
					}
				}
				map.put("productSaleDetailList", list);
			}
		} catch (Exception e) {
			LogCvt.info("商品销售详情列表查询异常", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "商品销售详情列表查询失败！");
		}
		return map;

	}

	/**
	 * 
	 * 方法名称: saleDetailListExport 简要描述: 商品销售详情列表下载查询 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:21:31 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> saleDetailListExport(ExcelReqVo reqVo,
			String clientId) {
		// 商品销售详情列表下载查询
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
			ProductSaleDetailResVo resVo = reportProductService
					.getProductSaleDetail(commonParamVo);
			LogCvt.info("业务销售统计列表下载请求返回数据:", JSON.toJSONString(resVo));
			if (EnumTypes.success.getCode()
					.equals(resVo.getResultVo().getResultCode())) {
				// 调用后台接口成功返回数据.....
				if (null != resVo) {
					List<ProductSaleDetailVo> resList = resVo
							.getProductSaleDetailVos();
					data = new ArrayList<List<String>>();
					if (null != resList && resList.size() > 0) {
						// 数据正常
						int index = 0;
						for (ProductSaleDetailVo productResVo : resList) {
							// 封装到list集合中
							list = new ArrayList<String>();
							list.add(++index + "");// 序号
							list.add(productResVo.getOrgCode());// 机构号
							list.add(productResVo.getOrgName());// 机构名称
							list.add(productResVo.getAddProductCount() + "");// 新增商品数
							list.add(productResVo.getProductCount() + "");// 商品总数
							list.add(productResVo.getProductSaleCount() + "");// 商品销售数量
							list.add(amountToString(
									productResVo.getProductSaleAmount()));// 商品销售金额
							// list.add("¥" +
							// productResVo.getProductSaleAmount());// 商品销售金额
							list.add(amountToString(
									productResVo.getRefundAmount()));// 退款总金额
							// list.add("¥" + productResVo.getRefundAmount());//
							// 退款总金额
							// 讲list添加到list中
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
					// if
					// (ClientIdEnum.CHONG_QING.getDescription().equals(clientId))
					// {
					// }
					title.add("新增商品数");
					title.add("商品总数");
					title.add("商品销售数量(件)");
					title.add("商品销售金额");
					title.add("退款总金额");
					workBook = ExcelUtil.generate(title, data, "商品销售详情");
					map.put("workBook", workBook);
				}
			}
		} catch (TException e) {
			LogCvt.info("商品销售详情列表下载异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "商品销售详情列表下载失败！");
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
