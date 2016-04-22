/**
 * Project Name:coremodule-bank
 * File Name:SettlementBankService.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.service
 * Date:2015年8月17日下午1:11:23
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.NumberUtil4Bank;
import com.froad.cbank.coremodule.module.normal.bank.vo.SettlementReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.SettlementResVo;
import com.froad.thrift.service.SettlementService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.settlement.SettlementPage;
import com.froad.thrift.vo.settlement.SettlementVo;

/**
 * ClassName:SettlementBankService Reason: 结算查询相关 Date:
 * 
 * 2015年8月17日 下午1:11:23
 * 
 * @author 明灿
 * @version
 * @see
 */
@Service
public class SettlementBankService {

	@Resource
	SettlementService.Iface settlementService;

	/**
	 * 
	 * queryByConditions:银行管理平台结算查询(结算失败)
	 *
	 * @author 明灿 2015年8月17日 下午3:38:41
	 * @param 请求参数
	 * @return
	 * @throws Exception
	 *
	 */
	public Map<String, Object> queryByConditions(SettlementReqVo voReq)
			throws Exception {
		LogCvt.info("银行管理平台结算查询请求参数: " + JSON.toJSONString(voReq));
		Map<String, Object> map = new HashMap<String, Object>();
		List<SettlementResVo> list = null;
		SettlementResVo settlementResVo = null;
		SettlementPage page = new SettlementPage();
		// 封装查询条件
		packConditions(voReq, page);
		// 调用server接口
		SettlementPage queryByPage = settlementService.queryByPage(page);
		LogCvt.info("银行管理平台结算查询返回数据: " + JSON.toJSONString(queryByPage));
		if (queryByPage != null) {
			List<SettlementVo> respList = queryByPage.getRespList();
			list = new ArrayList<SettlementResVo>();
			if (respList != null && respList.size() > 0) {
				for (SettlementVo settlementVo : respList) {
					settlementResVo = new SettlementResVo();
					// 拷贝返回的vo数据
					copyValue(settlementResVo, settlementVo);
					list.add(settlementResVo);
				}
			}
			// 将数据put到map中返回
			putValueToMap(map, list, queryByPage);
		}
		return map;
	}

	/**
	 * 
	 * putValueToMap:将返回值put到map集合中
	 *
	 * @author 明灿 2015年8月17日 下午3:35:44
	 * @param map
	 * @param list
	 * @param queryByPage
	 *
	 */
	private void putValueToMap(Map<String, Object> map,
			List<SettlementResVo> list, SettlementPage queryByPage) {
		map.put("settlementList", list);
		if (queryByPage.getPage() != null) {
			map.put("pageCount", queryByPage.getPage().getPageCount());
			map.put("totalCount", queryByPage.getPage().getTotalCount());
			map.put("pageNumber", queryByPage.getPage().getPageNumber());
			map.put("lastPageNumber",
					queryByPage.getPage().getLastPageNumber());
			map.put("firstRecordTime",
					queryByPage.getPage().getFirstRecordTime());
			map.put("lastRecordTime",
					queryByPage.getPage().getLastRecordTime());
		} else {
			map.put("pageCount", 0);
			map.put("totalCount", 0);
			map.put("pageNumber", 1);
			map.put("lastPageNumber", 0);
			map.put("firstRecordTime", 0);
			map.put("lastRecordTime", 0);
		}
	}

	/**
	 * 
	 * copyValue:拷贝返回的属性
	 *
	 * @author 明灿 2015年8月17日 下午3:12:42
	 * @param settlementResVo
	 * @param settlementVo
	 *
	 */
	private void copyValue(SettlementResVo settlementResVo,
			SettlementVo settlementVo) {
		settlementResVo.setId(settlementVo.getId());
		settlementResVo.setOrderId(settlementVo.getOrderId());// 订单编号
		settlementResVo.setSubOrderId(settlementVo.getSubOrderId());// 子订单编号
		settlementResVo.setSettlementId(settlementVo.getSettlementId());// 结算id
		settlementResVo.setType(settlementVo.getType());// 结算类型
		settlementResVo.setMerchantName(settlementVo.getMerchantName());// 商户名称

		// if (3 == settlementVo.getType()) {
		// // 如果是面对面就取大订单号
		// // LogCvt.info("=====大订单号=====" + settlementVo.getOrderId() + "!!");
		// settlementResVo.setOrderId(settlementVo.getOrderId());// 订单编号
		// }
		// 券码
		if (settlementVo.getTickets() != null
				&& settlementVo.getTickets().size() > 0) {
			settlementResVo.setTicket(settlementVo.getTickets().get(0));
		} else {
			settlementResVo.setTicket("--");
		}

		settlementResVo.setMoney(settlementVo.getMoney());// 结算金额

		settlementResVo.setCreateTime(
				DateUtil.dateFormart(settlementVo.getCreateTime()));// 结算时间
		// 结算状态(0-未结算,1-结算中,2-结算成功,3-结算失败,4-无效结算记录)
		// replaceSettleState(settlementVo.getSettleState());
		replaceSettleState(settlementVo, settlementResVo);
	}

	/**
	 * 
	 * replaceSettleState:转化结算状态
	 *
	 * @author 明灿 2015年8月18日 下午2:20:15
	 * @param settlementResVo
	 *
	 */
	private void replaceSettleState(SettlementVo settlementVo,
			SettlementResVo settlementResVo) {
		String state = settlementVo.getSettleState();
		if ("0".equals(state)) {
			settlementResVo.setSettleState("未结算");
		} else if ("1".equals(state)) {
			settlementResVo.setSettleState("结算中");
		} else if ("2".equals(state)) {
			settlementResVo.setSettleState("结算成功");
		} else if ("3".equals(state)) {
			settlementResVo.setSettleState("结算失败");
		} else if ("4".equals(state)) {
			settlementResVo.setSettleState("无效结算");
		} else {
			settlementResVo.setSettleState(" ");
		}
	}

	/**
	 * 
	 * packConditions:封装查询条件
	 *
	 * @author 明灿 2015年8月17日 下午2:52:11
	 * @param voReq
	 * @param page
	 *
	 */
	private void packConditions(SettlementReqVo voReq, SettlementPage page) {

		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(
				voReq.getPageNumber() == null ? 1 : voReq.getPageNumber());
		pageVo.setPageSize(
				voReq.getPageSize() == null ? 10 : voReq.getPageSize());
		pageVo.setLastPageNumber(voReq.getLastPageNumber() == null ? 0
				: voReq.getLastPageNumber());
		pageVo.setLastRecordTime(voReq.getLastRecordTime() == null ? 0
				: voReq.getLastRecordTime());
		pageVo.setFirstRecordTime(voReq.getFirstRecordTime() == null ? 0
				: voReq.getFirstRecordTime());
		page.setClientId(voReq.getClientId());
		page.setPage(pageVo);
		page.setClientId(voReq.getClientId());
		// 开始时间
		if (StringUtil.isNotBlank(voReq.getStartDate())) {
			pageVo.setBegDate(
					DateUtil.dateToTheDayBeforeDawn(voReq.getStartDate()));
		}
		// 结束时间
		if (StringUtil.isNotBlank(voReq.getEndDate())) {
			pageVo.setEndDate(
					DateUtil.dateToTheDayAfterDawn(voReq.getEndDate()));
		}
		// 订单id
		if (StringUtil.isNotBlank(voReq.getOrderId())) {
			page.setOrderId(voReq.getOrderId());
		}
		// 业务类型
		if (StringUtil.isNotBlank(voReq.getType())) {
			page.setType(voReq.getType());
		}
		// 券码
		if (StringUtil.isNotBlank(voReq.getTicket())) {
			page.setTicketId(voReq.getTicket());
		}
		// 结算失败
		List<String> notIn = new ArrayList<String>();
		notIn.add("2");
		notIn.add("0");
		page.setNotInSettleState(notIn);
	}

	/**
	 * 
	 * listExport:结算列表导出
	 *
	 * @author 明灿 2015年8月19日 下午2:45:55
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 */
	public Map<String, Object> listExport(SettlementReqVo reqVo)
			throws Exception {
		// 用户统计详情列表导出
		LogCvt.info("结算列表下载请求参数:", JSON.toJSONString(reqVo));
		int exportPageSize = 500;
		Integer totalPage = 0;
		int index = 0;
		// 导出的数据
		List<List<String>> data = null;
		Map<String, Object> map = new HashMap<String, Object>();
		SettlementPage page = new SettlementPage();
		// 封装查询条件
		packConditions(reqVo, page);
		// 调用server数据
		SettlementPage queryByPage = settlementService.queryByPage(page);
		if (queryByPage != null) {
			data = new ArrayList<List<String>>();
			index = pakageValueToList(reqVo, queryByPage, data, index);
			// 计算调用的次数
			int totalCount = queryByPage.getPage().getTotalCount();
			totalPage = ((totalCount - 1) / exportPageSize) + 1;
			LogCvt.info("......server端返回总计数为: " + totalCount + "......");
			// 循环调用server
			for (int i = 2; i < totalPage + 1; i++) {
				// 封装查询条件
				LogCvt.info("......第" + i + "次查询server......");
				reqVo.setPageNumber(i);
				packConditions(reqVo, page);
				// 调用server数据
				SettlementPage queryByPageAgain = settlementService
						.queryByPage(page);
				if (queryByPageAgain != null) {
					index = pakageValueToList(reqVo, queryByPageAgain, data,
							index);
				}
			}
			List<String> header = getTitle();
			HSSFWorkbook hssfWorkbook = ExcelUtil.generate(header, data,
					"结算查询");
			map.put("workBook", hssfWorkbook);
		}
		return map;
	}

	/**
	 * 
	 * listExportOfOptimize:导出优化
	 *
	 * @author 明灿 2015年9月2日 下午1:50:29
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 */
	public Map<String, Object> listExportOfOptimize(SettlementReqVo reqVo)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SettlementPage page = new SettlementPage();
		// 封装查询条件
		packConditions(reqVo, page);
		// 调用server数据
		ExportResultRes resultRes = settlementService.exportSettlements(page);
		ResultVo resultVo = resultRes.getResultVo();
		if (EnumTypes.success.getCode()
				.equals(resultRes.getResultVo().getResultCode())) {
			String url = resultRes.getUrl();
			LogCvt.info(".....server端返回url....." + url);
			map.put("url", url);
		} else {
			map.put("code", resultVo.getResultCode());
			map.put("message", resultVo.getResultDesc());
		}
		return map;
	}

	/**
	 * 
	 * getTitle:报表标题
	 *
	 * @author 明灿 2015年8月19日 下午3:21:34
	 * @return
	 *
	 */
	private List<String> getTitle() {
		List<String> list = new ArrayList<String>();
		list.add("序号");
		list.add("订单编号");
		list.add("结算单号");
		list.add("业务类型");
		list.add("商户名称");
		list.add("券码");
		list.add("结算金额");
		list.add("结算时间");
		list.add("状态");
		return list;
	}

	private int pakageValueToList(SettlementReqVo reqVo,
			SettlementPage queryByPage, List<List<String>> data, int index) {
		List<String> list;
		// 封装数据
		List<SettlementVo> respList = queryByPage.getRespList();

		if (respList != null && respList.size() > 0) {
			for (SettlementVo settlementVo : respList) {
				++index;
				list = new ArrayList<String>();
				addValueToList(list, settlementVo, index);
				data.add(list);
			}
		}
		PageVo pageVo = queryByPage.getPage();
		// 注入下一次的查询参数
		setQueryValue(reqVo, pageVo);
		return index;
	}

	/**
	 * 
	 * setQueryValue:注入下一次请求的参数
	 *
	 * @author 明灿 2015年8月19日 下午3:01:21
	 * @param reqVo
	 * @param pageVo
	 *
	 */
	private void setQueryValue(SettlementReqVo reqVo, PageVo pageVo) {
		reqVo.setLastPageNumber(pageVo.getLastPageNumber());
		reqVo.setLastRecordTime(pageVo.getLastRecordTime());
		reqVo.setFirstRecordTime(pageVo.getFirstRecordTime());
	}

	/**
	 * 
	 * addValueToList:将数据添加到list<String>中
	 *
	 * @author 明灿 2015年8月19日 下午2:46:21
	 * @param list
	 * @param settlementVo
	 * @param index
	 *
	 */
	private void addValueToList(List<String> list, SettlementVo settlementVo,
			int index) {
		list.add(String.valueOf(index));// 序号
		list.add(settlementVo.getOrderId());// 订单编号
		list.add(settlementVo.getOrderId());// 结算单号
		list.add(settlementVo.getType() == 1 ? "团购"
				: (settlementVo.getType() == 2 ? "名优特惠" : "面对面"));// 业务类型
		list.add(settlementVo.getMerchantName());// 商户名称
		// 券码
		if (settlementVo.getTickets() != null
				&& settlementVo.getTickets().size() > 0) {
			list.add(settlementVo.getTickets().get(0));
		} else {
			list.add("--");
		}
		list.add(NumberUtil4Bank.amountToString(settlementVo.getMoney()));// 结算金额
		list.add(DateUtil.dateFormart(settlementVo.getCreateTime()));// 结算日期
		// 结算状态
		String state = settlementVo.getSettleState();
		if ("0".equals(state)) {
			list.add("未结算");
		} else if ("1".equals(state)) {
			list.add("结算中");
		} else if ("2".equals(state)) {
			list.add("结算成功");
		} else if ("3".equals(state)) {
			list.add("结算失败");
		} else if ("4".equals(state)) {
			list.add("无效结算");
		} else {

			list.add("");
		}
	}
}
