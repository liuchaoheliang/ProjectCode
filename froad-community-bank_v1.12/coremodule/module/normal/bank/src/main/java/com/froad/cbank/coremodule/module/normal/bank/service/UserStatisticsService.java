package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtilForReport;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.NumberUtil4Bank;
import com.froad.cbank.coremodule.module.normal.bank.vo.DateForReportVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo4Trend;
import com.froad.cbank.coremodule.module.normal.bank.vo.UserPercentResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.UserSummaryBankResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.UserTrendBankResVo;
import com.froad.thrift.service.ReportUserService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;
import com.froad.thrift.vo.report.UserSummaryPageVo;
import com.froad.thrift.vo.report.UserSummaryResVo;
import com.froad.thrift.vo.report.UserSummaryVo;
import com.froad.thrift.vo.report.UserTradeDetailResVo;
import com.froad.thrift.vo.report.UserTradeDetailVo;
import com.froad.thrift.vo.report.UserTradeInfoResVo;
import com.froad.thrift.vo.report.UserTradeInfoVo;
import com.froad.thrift.vo.report.UserTrendResVo;
import com.froad.thrift.vo.report.UserTrendVo;

@Service
public class UserStatisticsService {

	@Resource
	ReportUserService.Iface reportUserService;

	/**
	 * 
	 * 方法名称: trend 简要描述: 销售额走势 版本信息: V1.0 创建时间: 2015年7月22日 下午2:51:23 创建作者: 明灿
	 * chenmingcan@f-road.com.cn 方法参数: @param reqVo 方法参数: @param clientId
	 * 方法参数: @return 返回类型: Map<String,Object> @throws
	 */
	public Map<String, Object> trend(ExcelReqVo4Trend reqVo, String clientId) {
		// 销售额走势
		LogCvt.info("用户走势请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<UserTrendBankResVo> list = null;
		UserTrendBankResVo userResVo = null;
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
			// UserTrendResVo userTrend = reportUserService.userTrend("anhui",
			// "340000");
			UserTrendResVo userTrend = reportUserService.userTrend(paramVo);
			// (clientId,reqVo.getOrgCode());
			LogCvt.info("用户走势请求返回数据:", JSON.toJSONString(userTrend));
			if (null != userTrend) {
				List<UserTrendVo> trendList = userTrend.getUserTrendVos();
				if (null != trendList && trendList.size() > 0) {
					list = new ArrayList<UserTrendBankResVo>();
					for (UserTrendVo userTrendVo : trendList) {
						userResVo = new UserTrendBankResVo();
						userResVo.setWeek(userTrendVo.getWeek());
						userResVo.setUserCount(userTrendVo.getUserCount());
						list.add(userResVo);
					}
				}
				map.put("userTrendList", list);
			}
		} catch (Exception e) {
			LogCvt.info("用户走势查询异常", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "用户走势查询失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: userTradeTypePercent 简要描述: 用户交易类型占比 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:51:48 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object> @throws
	 */
	public Map<String, Object> userTradeTypePercent(ExcelReqVo reqVo,
			String clientId) {
		// 用户交易类型占比
		LogCvt.info("用户交易类型占比请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<UserPercentResVo> list = null;
		UserPercentResVo percentResVo = null;
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
			TypePercentResVo resVo = reportUserService
					.userTradeTypePercent(commonParamVo);
			LogCvt.info("用户交易类型占比请求返回数据:", JSON.toJSONString(resVo));
			if (null != resVo) {
				List<TypePercentVo> resList = resVo.getTypePercentVos();
				list = new ArrayList<UserPercentResVo>();
				if (null != resList && resList.size() != 0) {
					for (TypePercentVo saleTypePercentVo : resList) {
						percentResVo = new UserPercentResVo();
						percentResVo.setPercent(saleTypePercentVo.getPercent());
						percentResVo.setType(saleTypePercentVo.getType());
						percentResVo
								.setTypeName(saleTypePercentVo.getTypeName());
						// percentResVo.setType(PaymentMethod.getDescrible(type));
						/*
						 * if ("0".equals(type)) { percentResVo.setType("面对面");
						 * } if ("1".equals(type)) { percentResVo.setType("团购");
						 * } if ("2".equals(type)) {
						 * percentResVo.setType("精品预售"); } if ("3".equals(type))
						 * { percentResVo.setType("名优特惠"); } if
						 * ("4".equals(type)) { percentResVo.setType("线上积分兑换");
						 * } if ("5".equals(type)) {
						 * percentResVo.setType("线下积分兑换"); }
						 */
						list.add(percentResVo);
					}
					// map.put("1", "1");
					map.put("percentList", list);
				}
			}
		} catch (Exception e) {
			LogCvt.info("用户交易类型占比异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "用户交易类型占比失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: userConsumeTypePercent 简要描述: 用户消费类型占比 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:52:01 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object> @throws
	 */
	public Map<String, Object> userConsumeTypePercent(ExcelReqVo reqVo,
			String clientId) {
		// 用户消费类型占比
		LogCvt.info("用户消费类型占比请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<UserPercentResVo> list = null;
		UserPercentResVo percentResVo = null;
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
			TypePercentResVo resVo = reportUserService
					.userConsumeTypePercent(commonParamVo);
			LogCvt.info("用户消费类型占比请求返回数据:", JSON.toJSONString(resVo));
			if (null != resVo) {
				List<TypePercentVo> resList = resVo.getTypePercentVos();
				if (null != resList && resList.size() > 0) {
					list = new ArrayList<UserPercentResVo>();
					for (TypePercentVo payTypePercentVo : resList) {
						percentResVo = new UserPercentResVo();
						percentResVo.setPercent(payTypePercentVo.getPercent());
						percentResVo.setType(payTypePercentVo.getType());
						percentResVo
								.setTypeName(payTypePercentVo.getTypeName());
						// 获取中文分类名
						// percentResVo.setType(PaymentMethod.getDescrible(type));
						/*
						 * if ("0".equals(type)) { percentResVo.setType("面对面");
						 * } if ("1".equals(type)) { percentResVo.setType("团购");
						 * } if ("2".equals(type)) {
						 * percentResVo.setType("精品预售"); } if ("3".equals(type))
						 * { percentResVo.setType("名优特惠"); } if
						 * ("4".equals(type)) { percentResVo.setType("线上积分兑换");
						 * } if ("5".equals(type)) {
						 * percentResVo.setType("线下积分兑换"); }
						 */
						list.add(percentResVo);
					}
					map.put("percentList", list);
				}
			}
		} catch (Exception e) {
			LogCvt.info("用户消费类型占比查询异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "用户消费类型占比查询失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: userSummaryList 简要描述: 用户统计详情列表查询 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:52:10 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object> @throws
	 */
	public Map<String, Object> userSummaryList(ExcelReqVo reqVo,
			String clientId) {
		// 用户统计详情列表查询
		LogCvt.info("用户统计详情列表查询请求参数:", JSON.toJSONString(reqVo));
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<UserSummaryBankResVo> list = null;
		UserSummaryBankResVo userSummaryBankResVo = null;
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
			UserSummaryPageVo listByPage = reportUserService
					.userSummaryListByPage(pageVo, commonParamVo);
			LogCvt.info("用户统计详情列表查询请求返回数据:", JSON.toJSONString(listByPage));
			if (null != listByPage) {
				List<UserSummaryVo> vosList = listByPage.getUserSummaryVos();
				// 封装分页信息
				PageVo pageVo2 = listByPage.getPageVo();
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
				list = new ArrayList<UserSummaryBankResVo>();
				if (null != vosList && vosList.size() > 0) {
					for (UserSummaryVo userSummaryVo : vosList) {
						userSummaryBankResVo = new UserSummaryBankResVo();
						// 属性注入
						userSummaryBankResVo
								.setAddCount(userSummaryVo.getAddCount());// 新增用户数
						userSummaryBankResVo
								.setChangeCount(userSummaryVo.getChangeCount());// 动账用户数
						userSummaryBankResVo
								.setOrderCount(userSummaryVo.getOrderCount());// 订单数
						userSummaryBankResVo
								.setOrgCode(userSummaryVo.getOrgCode());// 机构号
						userSummaryBankResVo
								.setOrgName(userSummaryVo.getOrgName());// 机构名称
						userSummaryBankResVo
								.setPercent(userSummaryVo.getPercent());// 新增商户占比
						userSummaryBankResVo
								.setTotalAmount(userSummaryVo.getTotalAmount());// 消费金额
						userSummaryBankResVo
								.setTotalCount(userSummaryVo.getTotalCount());// 结余用户数
						// BeanUtils.copyProperties(bussinessStatisticResVo,
						// saleCountDetailVo);
						list.add(userSummaryBankResVo);
					}
				}
				map.put("userSummaryList", list);
			}
		} catch (TException e) {
			LogCvt.info("用户统计详情列表查询异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "用户统计详情列表查询失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: userSummaryExport 简要描述: 用户统计详情列表导出 版本信息: V1.0 创建时间: 2015年7月22日
	 * 下午2:52:19 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object> @throws
	 */
	public Map<String, Object> userSummaryExport(ExcelReqVo reqVo,
			String clientId) {
		// 用户统计详情列表导出
		LogCvt.info("用户统计详情列表下载请求参数:", JSON.toJSONString(reqVo));
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
			UserSummaryResVo resList = reportUserService
					.userSummaryList(commonParamVo);
			LogCvt.info("用户统计详情列表下载请求返回数据:", JSON.toJSONString(resList));
			if (EnumTypes.success.getCode()
					.equals(resList.getResultVo().getResultCode())) {
				LogCvt.info("用户统计详情列表下载请求返回数据长度:",
						JSON.toJSONString(resList.getUserSummaryVos().size()));
				// 调用后台接口成功返回数据.....
				if (null != resList) {
					List<UserSummaryVo> list2 = resList.getUserSummaryVos();
					data = new ArrayList<List<String>>();
					if (null != list2 && list2.size() > 0) {
						// 数据正常
						int index = 0;
						for (UserSummaryVo userSummaryVo : list2) {
							// 封装到list集合中
							list = new ArrayList<String>();
							list.add(++index + "");// 序号
							list.add(userSummaryVo.getOrgCode());// 机构号
							list.add(userSummaryVo.getOrgName());// 机构名
							list.add(userSummaryVo.getAddCount() + "");// 新增用户数
							list.add(userSummaryVo.getChangeCount() + "");// 动账用户数
							list.add(userSummaryVo.getTotalCount() + "");// 结余用户数
							list.add(percentToString(
									userSummaryVo.getPercent()));// 新增商户占比
							// list.add(userSummaryVo.getPercent() * 100 +
							// "%");// 新增商户占比
							list.add(userSummaryVo.getOrderCount() + "");// 订单数
							list.add(amountToString(
									userSummaryVo.getTotalAmount()));// 消费金额
							// list.add("¥" + userSummaryVo.getTotalAmount());//
							// 消费金额
							// 将list添加到list中
							data.add(list);
						}
					}

					// 封装标题
					List<String> title = new ArrayList<String>();
					title.add("序号");
					title.add("机构号");
					title.add("法人行社/支行");
					title.add("新增用户数");
					title.add("用户动户数");
					title.add("结余用户数");
					title.add("新增占比");
					title.add("订单数");
					title.add("消费金额");
					workBook = ExcelUtil.generate(title, data, "用户统计详情");
					map.put("workBook", workBook);
				}
			}
		} catch (TException e) {
			LogCvt.info("用户统计详情列表下载异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "用户统计详情列表下载失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: userTradeDetailExport 简要描述: 用户交易支付详情列表导出 版本信息: V1.0 创建时间:
	 * 2015年6月18日 下午6:14:55 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param
	 * reqVo 方法参数: @param clientId 方法参数: @return 返回类型: Map
	 * <String,Object> @throws
	 */
	public Map<String, Object> userTradeDetailExport(ExcelReqVo reqVo,
			String clientId) {
		// 用户交易支付详情导出
		LogCvt.info("用户交易支付详情导出:", JSON.toJSONString(reqVo));
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
			UserTradeDetailResVo detailList = reportUserService
					.userTradeDetailList(commonParamVo);
			if (null != detailList) {
				LogCvt.info("用户交易支付详情列表请求返回数据:", JSON.toJSONString(detailList));
				List<UserTradeDetailVo> detailVos = detailList
						.getUserTradeDetailVos();
				int index = 0;
				data = new ArrayList<List<String>>();
				if (null != detailVos && detailVos.size() > 0) {
					for (UserTradeDetailVo userTradeDetailVo : detailVos) {
						// 封装到list集合中
						list = new ArrayList<String>();
						list.add(++index + "");// 序号
						list.add(userTradeDetailVo.getOrgCode());// 机构号
						list.add(userTradeDetailVo.getOrgName());// 机构名称
						list.add(userTradeDetailVo.getUserName());// 用户名称
						list.add(userTradeDetailVo.getMobile());// 注册手机号
						// 将时间转成yyyy-MM-dd
						list.add(DateUtil.formatDate(
								new Date(userTradeDetailVo.getRegTime()),
								true));// 注册时间
						list.add(userTradeDetailVo.getRegType());// 注册类型
						list.add(userTradeDetailVo.getTotalPointNumber() + "");// 积分支付笔数
						list.add(amountToString(
								userTradeDetailVo.getTotalPointAmount()));// 积分支付金额
						// list.add("¥" +
						// userTradeDetailVo.getTotalPointAmount());// 积分支付金额

						list.add(userTradeDetailVo.getTotalQuickNumber() + "");// 快捷支付笔数
						list.add(amountToString(
								userTradeDetailVo.getTotalQuickAmount()));// 快捷支付金额
						// list.add("¥"
						// + userTradeDetailVo.getTotalQuickAmount());//
						// 快捷支付金额
						list.add(userTradeDetailVo.getTotalFilmNumber() + "");// 贴膜卡支付笔数
						list.add(amountToString(
								userTradeDetailVo.getTotalFilmAmount()));// 贴膜卡支付金额
						// list.add("¥" +
						// userTradeDetailVo.getTotalFilmAmount());// 贴膜卡支付金额
						list.add(userTradeDetailVo.getTotalPointQuickNumber()
								+ "");// 积分+快捷支付笔数
						list.add(amountToString(
								userTradeDetailVo.getTotalPointQuickAmount()));// 积分+快捷支付金额
						// list.add("¥"
						// + userTradeDetailVo.getTotalPointQuickAmount());//
						// 积分+快捷支付金额
						list.add(userTradeDetailVo.getTotalPointFilmNumber()
								+ "");// 积分+贴膜卡支付笔数
						list.add(amountToString(
								userTradeDetailVo.getTotalPointFilmAmount()));// 积分+贴膜卡支付金额
						// list.add("¥"
						// + userTradeDetailVo.getTotalPointFilmAmount());//
						// 积分+贴膜卡支付金额
						list.add(userTradeDetailVo.getTotalOrderNumber() + "");// 订单数
						list.add(amountToString(
								userTradeDetailVo.getTotalOrderAmount()));// 订单金额
						// list.add("¥" +
						// userTradeDetailVo.getTotalOrderAmount());// 订单金额
						data.add(list);
					}
				}
				// 封装标题
				List<String> title = new ArrayList<String>();
				title.add("序号");
				title.add("机构号");
				title.add("机构名称");
				title.add("客户姓名");
				title.add("注册手机号");
				title.add("注册日期");
				title.add("注册类型");
				title.add("积分支付笔数");
				title.add("积分支付金额");
				title.add("快捷支付笔数");
				title.add("快捷支付金额");
				title.add("贴膜卡支付笔数");
				title.add("贴膜卡支付金额");
				title.add("积分+快捷支付笔数");
				title.add("积分+快捷支付金额");
				title.add("积分+贴膜卡支付笔数");
				title.add("积分+贴膜卡支付金额");
				title.add("订单数");
				title.add("订单金额");
				workBook = ExcelUtil.generate(title, data, "用户交易支付详情");
				map.put("workBook", workBook);
			}
		} catch (Exception e) {
			LogCvt.info("用户交易支付详情列表查询异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "用户交易支付详情列表查询失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: userTradeInfoExport 简要描述: 用户交易信息导出 版本信息: V1.0 创建时间: 2015年7月4日
	 * 上午11:35:00 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param reqVo
	 * 方法参数: @param clientId 方法参数: @return 返回类型: Map<String,Object> @throws
	 */
	public Map<String, Object> userTradeInfoExport(ExcelReqVo reqVo,
			String clientId) {
		// 用户统计详情列表导出
		LogCvt.info("用户交易信息导出请求参数:", JSON.toJSONString(reqVo));
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
			UserTradeInfoResVo tradeInfoList = reportUserService
					.userTradeInfoList(commonParamVo);
			LogCvt.info("用户交易信息导出请求返回数据:", JSON.toJSONString(tradeInfoList));
			if (EnumTypes.success.getCode()
					.equals(tradeInfoList.getResultVo().getResultCode())) {
				// 调用后台接口成功返回数据.....
				if (null != tradeInfoList) {
					List<UserTradeInfoVo> list2 = tradeInfoList
							.getUserTradeInfoVos();
					data = new ArrayList<List<String>>();
					if (null != list2 && list2.size() > 0) {
						// 数据正常
						int index = 0;
						for (UserTradeInfoVo userTradeInfoVo : list2) {
							// 封装到list集合中
							list = new ArrayList<String>();
							list.add(++index + "");// 序号
							list.add(userTradeInfoVo.getUserName());// 客户姓名
							list.add(userTradeInfoVo.getMobile());// 注册手机号
							if ("1".equals(userTradeInfoVo.getIsVip())) {
								list.add("VIP");// Vip
							} else {
								list.add("非VIP");// 非Vip
							}
							list.add(userTradeInfoVo.getOrderCount() + "");// 订单数
							list.add(amountToString(
									userTradeInfoVo.getTotalAmount()));// 订单金额
							// list.add("¥" +
							// userTradeInfoVo.getTotalAmount());// 订单金额
							list.add(userTradeInfoVo.getProductNumber() + "");// 购买商品数量
							list.add(amountToString(
									userTradeInfoVo.getProductAmount()));// 购买商品金额
							// list.add("¥" +
							// userTradeInfoVo.getProductAmount());// 购买商品金额
							list.add(amountToString(
									userTradeInfoVo.getRefundsAmount()));// 退款金额
							// list.add("¥" +
							// userTradeInfoVo.getRefundsAmount());// 退款金额
							// 讲list添加到list中
							data.add(list);
						}
					}
					// 封装标题
					List<String> title = new ArrayList<String>();
					title.add("序号");
					title.add("客户姓名");
					title.add("注册手机号");
					title.add("是否VIP");
					title.add("订单数");
					title.add("订单金额");
					title.add("购买商品数量");
					title.add("购买商品金额");
					title.add("退款金额");
					workBook = ExcelUtil.generate(title, data, "用户交易信息");
					map.put("workBook", workBook);
				}
			}
		} catch (TException e) {
			LogCvt.info("用户交易信息导出异常:", e.getMessage());
			map.clear();
			map.put("code", EnumTypes.fail.getCode());
			map.put("message", "用户交易信息导出失败！");
		}
		return map;
	}

	/**
	 * 
	 * 方法名称: percentToString 简要描述: 返回百分比 版本信息: V1.0 创建时间: 2015年7月31日 下午2:43:24
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param percent 方法参数: @return
	 * 返回类型: String @throws
	 */
	private String percentToString(double percent) {
		return NumberUtil4Bank.percentToString(percent);
	}

	/**
	 * 
	 * 方法名称: amountToString 简要描述: 格式化金额保留2位小数 版本信息: V1.0 创建时间: 2015年7月31日
	 * 下午2:45:04 创建作者: 明灿 chenmingcan@f-road.com.cn 方法参数: @param amount
	 * 方法参数: @return 返回类型: String @throws
	 */
	private String amountToString(double amount) {
		return "¥" + NumberUtil4Bank.amountToString(amount);
	}
}
