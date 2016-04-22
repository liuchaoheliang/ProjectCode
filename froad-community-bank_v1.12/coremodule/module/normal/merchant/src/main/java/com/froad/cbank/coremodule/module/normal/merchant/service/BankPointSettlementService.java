package com.froad.cbank.coremodule.module.normal.merchant.service;

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
import com.froad.cbank.coremodule.module.normal.merchant.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.BankPointSettlementResVo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Point_Report_Req;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.PageResUtil;
import com.froad.thrift.service.PointSettlementService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementDetailResVo;
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
	 * @throws MerchantException
	 */
	public Map<String, Object> getShoppingOrderTotal(Point_Report_Req req,
			String type) throws TException, ParseException, MerchantException {
		LogCvt.info("积分报表-订单统计列表请求参数:" + JSON.toJSONString(req));
		if (!StringUtil.isNotBlank(req.getStartDate())) {
			throw new MerchantException(EnumTypes.fail.getCode(), "结算开始时间不能为空");
		}
		if (!StringUtil.isNotBlank(req.getEndDate())) {
			throw new MerchantException(EnumTypes.fail.getCode(), "结算结束时间不能为空");
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		PointSettlementReqVo reqVo = new PointSettlementReqVo();
		PageVo page = null;
		// 请求参数封装
		this.requestParams(reqVo, req);
		reqVo.setPage(PageResUtil.getPageVo(req));
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
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.success.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.success.getMsg());
			} else {
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.thrift_err.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.thrift_err.getMsg());
			}
			resMap.put("orderList", reslist);
			PageResUtil.setPagesValueToResMap(resMap, page);
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
	private void requestParams(PointSettlementReqVo reqVo, Point_Report_Req req)
			throws ParseException {
		reqVo.setStartTime(Long.parseLong(req.getStartDate()));
		reqVo.setEndTime(Long.parseLong(req.getEndDate()));
		MerchantUser merchantUser = req.getMerchantUser();
		reqVo.setMerchantId(merchantUser.getMerchantId());
		reqVo.setClientId(req.getClientId());

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
	 * @throws MerchantException
	 */
	public Map<String, Object> getPointTotal(Point_Report_Req req)
			throws ParseException, TException, MerchantException {
		if (!StringUtil.isNotBlank(req.getStartDate())) {
			throw new MerchantException(EnumTypes.fail.getCode(), "结算开始时间不能为空");
		}
		if (!StringUtil.isNotBlank(req.getEndDate())) {
			throw new MerchantException(EnumTypes.fail.getCode(), "结算结束时间不能为空");
		}
		LogCvt.info("积分报表-积分汇总请求参数:" + JSON.toJSONString(req));
		Map<String, Object> resMap = new HashMap<String, Object>();
		PointSettlementReqVo reqVo = new PointSettlementReqVo();
		this.requestParams(reqVo, req);
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
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.success.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.success.getMsg());
			} else {
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.thrift_err.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.thrift_err.getMsg());
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
	 * @throws MerchantException
	 */
	public Map<String, Object> getPointTotalReport(Point_Report_Req req)
			throws ParseException, TException, MerchantException {
		LogCvt.info("积分报表-积分汇总导出请求参数:" + JSON.toJSONString(req));
		if (!StringUtil.isNotBlank(req.getStartDate())) {
			throw new MerchantException(EnumTypes.fail.getCode(), "结算开始时间不能为空");
		}
		if (!StringUtil.isNotBlank(req.getEndDate())) {
			throw new MerchantException(EnumTypes.fail.getCode(), "结算结束时间不能为空");
		}
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
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.success.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.success.getMsg());
			} else {
				resMap.put(ResultEnum.CODE.getCode(),
						EnumTypes.thrift_err.getCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.thrift_err.getMsg());
			}
		}
		return resMap;
	}

}
