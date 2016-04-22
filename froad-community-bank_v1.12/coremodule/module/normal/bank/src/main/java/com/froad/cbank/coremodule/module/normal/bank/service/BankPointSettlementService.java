package com.froad.cbank.coremodule.module.normal.bank.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.PageUtil;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankPointSettlementMerchantDetailResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankPointSettlementResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BaseVo;
import com.froad.thrift.service.PointSettlementService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementDetailResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementMerchantDetailResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementMerchantResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementReqVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementResVo;

@Service
public class BankPointSettlementService {

	@Resource
	PointSettlementService.Iface pointSettlementService;

	/**
	 * 
	 * getShoppingOrderTotal:购物类订单银行积分统计列表查询
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 上午11:33:07
	 * @param req
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	public Map<String, Object> getShoppingOrderTotal(BaseVo req, String type)
			throws TException, ParseException {
		LogCvt.info("积分报表-订单统计列表请求参数:" + JSON.toJSONString(req));
		Map<String, Object> resMap = new HashMap<String, Object>();
		PointSettlementReqVo reqVo = new PointSettlementReqVo();
		PageVo page = null;
		// 请求参数封装
		this.requestParams(reqVo, req);
		reqVo.setPage(PageUtil.getPageVo(req));
		reqVo.setIsQrcode(type);
		PointSettlementResVo pointSettlementResVo = pointSettlementService
				.queryPointDetail(reqVo);
		LogCvt.info(
				"积分报表-订单统计列表请求返回:"
				+ JSON.toJSONString(pointSettlementResVo));
		List<BankPointSettlementResVo> reslist = new ArrayList<BankPointSettlementResVo>();
		if (pointSettlementResVo != null) {
			ResultVo resultVo = pointSettlementResVo.getResultVo();
			if (resultVo != null && resultVo.getResultCode()
					.equals(ResultEnum.SUCCESS.getDescrition())) {
				List<PointSettlementDetailResVo> resVoList = pointSettlementResVo
						.getDetailResVoList();
				page = pointSettlementResVo.getPage();
				BankPointSettlementResVo bankPointSettlementResVo = null;
				if (resVoList != null && resVoList.size() > 0) {
					for (PointSettlementDetailResVo pointSettlementDetailResVo : resVoList) {
						bankPointSettlementResVo = new BankPointSettlementResVo();
						BeanUtils.copyProperties(bankPointSettlementResVo,
								pointSettlementDetailResVo);
						reslist.add(bankPointSettlementResVo);
					}
				}
				resMap.put("bankPointCount",
						pointSettlementResVo.getBankPointCount());
				resMap.put("froadPointCount",
						pointSettlementResVo.getFroadPointCount());
			} else {
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.thrift_err.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.thrift_err.getMessage());
			}
			resMap.put("orderList", reslist);
			PageUtil.setPagesValueToResMap(resMap, page);
		}
		return resMap;
	}

	/**
	 * 
	 * requestParams:列表请求参数封装
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 上午11:08:35
	 * @param reqVo
	 *            server vo
	 * @param req
	 *            web vo
	 * @throws ParseException
	 */
	private void requestParams(PointSettlementReqVo reqVo, BaseVo req)
			throws ParseException {
		if (StringUtil.isNotBlank(req.getStartDate())) {
			reqVo.setStartTime(Long.parseLong(req.getStartDate()));
		}
		if (StringUtil.isNotBlank(req.getEndDate())) {
			reqVo.setEndTime(Long.parseLong(req.getEndDate()));
		}
		reqVo.setClientId(req.getClientId());

	}

	/**
	 * 
	 * getMerchantTotal:商户汇总列表查询
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午2:25:52
	 * @param req
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	public Map<String, Object> getMerchantTotal(BaseVo req)
			throws TException, ParseException {
		LogCvt.info("积分报表-商户汇总列表请求参数:" + JSON.toJSONString(req));
		Map<String, Object> resMap = new HashMap<String, Object>();
		PointSettlementReqVo reqVo = new PointSettlementReqVo();
		this.requestParams(reqVo, req);
		reqVo.setPage(PageUtil.getPageVo(req));
		PointSettlementMerchantResVo queryMerchantPointCount = pointSettlementService
				.queryMerchantPointCount(reqVo);
		LogCvt.info("积分报表-商户汇总列表请求返回:" + JSON.toJSONString(req));
		PageVo page = null;
		List<BankPointSettlementMerchantDetailResVo> resList = new ArrayList<BankPointSettlementMerchantDetailResVo>();
		BankPointSettlementMerchantDetailResVo bankMerchantResVo = null;
		if (queryMerchantPointCount != null) {
			ResultVo resultVo = queryMerchantPointCount.getResultVo();
			if (resultVo != null && resultVo.getResultCode()
					.equals(ResultEnum.SUCCESS.getDescrition())) {
				List<PointSettlementMerchantDetailResVo> detailResVoList = queryMerchantPointCount
						.getDetailResVoList();
				page = queryMerchantPointCount.getPage();
				if (detailResVoList != null && detailResVoList.size() > 0) {
					for (PointSettlementMerchantDetailResVo psmd : detailResVoList) {
						bankMerchantResVo = new BankPointSettlementMerchantDetailResVo();
						BeanUtils.copyProperties(bankMerchantResVo, psmd);
						resList.add(bankMerchantResVo);
					}
				}
			} else {
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.thrift_err.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.thrift_err.getMessage());
			}
		}
		resMap.put("merchantList", resList);
		PageUtil.setPagesValueToResMap(resMap, page);
		return resMap;
	}

	/**
	 * 
	 * getPointTotal:积分总汇总查询
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午2:52:10
	 * @param req
	 * @return
	 * @throws ParseException
	 * @throws TException
	 */
	public Map<String, Object> getPointTotal(BaseVo req)
			throws ParseException, TException {
		LogCvt.info("积分报表-积分汇总请求参数:" + JSON.toJSONString(req));
		Map<String, Object> resMap = new HashMap<String, Object>();
		PointSettlementReqVo reqVo = new PointSettlementReqVo();
		this.requestParams(reqVo, req);
		reqVo.setPage(PageUtil.getPageVo(req));
		reqVo.setPage(null);
		PointSettlementResVo queryPointCount = pointSettlementService
				.queryPointCount(reqVo);
		LogCvt.info("积分报表-积分汇总请求返回:" + JSON.toJSONString(queryPointCount));
		if (queryPointCount != null) {
			ResultVo resultVo = queryPointCount.getResultVo();
			if (resultVo != null && resultVo.getResultCode()
					.equals(ResultEnum.SUCCESS.getDescrition())) {
				resMap.put("froadPointCount",
						queryPointCount.getFroadPointCount());
				resMap.put("bankPointCount",
						queryPointCount.getBankPointCount());
				resMap.put(ResultEnum.SUCCESS.getCode(),
						ResultEnum.SUCCESS.getDescrition());
				resMap.put(ResultEnum.MESSAGE.getCode(), "操作成功");
			} else {
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.thrift_err.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.thrift_err.getMessage());
			}
		}
		return resMap;
	}

	/**
	 * 
	 * getPointTotalReport:积分报表导出接口
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月31日 下午3:01:36
	 * @param req
	 * @return
	 * @throws ParseException
	 * @throws TException
	 */
	public Map<String, Object> getPointTotalReport(BaseVo req)
			throws ParseException, TException {
		LogCvt.info("积分报表-积分汇总导出请求参数:" + JSON.toJSONString(req));
		Map<String, Object> resMap = new HashMap<String, Object>();
		PointSettlementReqVo reqVo = new PointSettlementReqVo();
		this.requestParams(reqVo, req);
		reqVo.setPage(null);
		ExportResultRes exportPointSettlement = pointSettlementService
				.exportPointSettlement(reqVo);
		LogCvt.info(
				"积分报表-积分汇总导出请求返回:" + JSON.toJSONString(exportPointSettlement));
		if (exportPointSettlement != null) {
			ResultVo resultVo = exportPointSettlement.getResultVo();
			if (resultVo != null && resultVo.getResultCode()
					.equals(ResultEnum.SUCCESS.getDescrition())) {
				resMap.put("url", exportPointSettlement.getUrl());
				resMap.put(ResultEnum.CODE.getCode(), resultVo.getResultCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						resultVo.getResultDesc());
			} else {
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.thrift_err.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.thrift_err.getMessage());
			}
		}
		return resMap;
	}

}
