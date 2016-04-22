/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:ProviderOrderServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015年11月26日下午4:55:43
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.FieldMapping;
import com.froad.enums.OrderStatus;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.ProviderOrderLogic;
import com.froad.logic.impl.ProviderOrderLogicImpl;
import com.froad.po.ProviderOrderQuery;
import com.froad.po.ShippingExcelInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ProviderOrderService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.providerorder.ProviderOrderQueryPageVo;
import com.froad.thrift.vo.providerorder.ProviderOrderQueryReq;
import com.froad.thrift.vo.providerorder.ProviderOrderQueryVo;
import com.froad.thrift.vo.providerorder.ShippingExcelInfoVo;
import com.froad.thrift.vo.providerorder.UpdateShippingInfoReq;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * ClassName:ProviderOrderServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月26日 下午4:55:43
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProviderOrderServiceImpl extends BizMonitorBaseService implements ProviderOrderService.Iface {
	
	ProviderOrderLogic logic = new ProviderOrderLogicImpl();
	
	public ProviderOrderServiceImpl(String name, String version){
		super(name, version);
	}

	@Override
	public ProviderOrderQueryPageVo queryProviderOrderInfoByPage(ProviderOrderQueryReq req, PageVo pageVo)
			throws TException {
		LogCvt.info("[供应商订单列表分页查询]-开始");
		ProviderOrderQueryPageVo resVo = new ProviderOrderQueryPageVo();
		try {
			MongoPage page = (MongoPage) BeanUtil.copyProperties(MongoPage.class, pageVo);
			DBObject reqQuery = convertToDbObject(req);
			page = logic.queryProviderOrderInfoByPage(reqQuery, page);
			List<ProviderOrderQueryVo> vos = (List<ProviderOrderQueryVo>) BeanUtil.copyProperties(ProviderOrderQueryVo.class, page.getItems());
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			
			resVo.setResultVo(convertToResultVo(ResultCode.success));
			resVo.setPageVo(pageVo);
			resVo.setOrderList(vos);
		} catch (Exception e) {
			LogCvt.error("[供应商订单列表分页查询]-异常", e);
			resVo.setResultVo(convertToResultVo(ResultCode.failed));
			resVo.setPageVo(pageVo);
			resVo.setOrderList(new ArrayList<ProviderOrderQueryVo>());
		}
		LogCvt.info("[供应商订单列表分页查询]-结束");
		return resVo;
	}

	@Override
	public ExportResultRes exportProviderOrderInfo(ProviderOrderQueryReq req) throws TException {
		LogCvt.info("[供应商订单列表导出]-开始");
		ExportResultRes res = new ExportResultRes();
		String url = "";
		ResultVo resultVo = null;
		try {
			List<String> header = new ArrayList<String>();
			header.add("订单编号");
			header.add("订单状态");
			header.add("用户编号");
			header.add("用户手机号");
			header.add("总金额");
			header.add("创建时间");
			header.add("所属客户端");
			header.add("收货人");
			header.add("商品名称");
			header.add("收货地址");
			header.add("发货状态");
			header.add("物流公司");
			header.add("物流单号");
			header.add("上传时间");
			
			int pageNo = 0;
			int pageSize = 1000;
			MongoPage page = new MongoPage();
			page.setPageSize(pageSize);
			List<List<String>> allData = new ArrayList<List<String>>();
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			DBObject reqQuery = convertToDbObject(req);
			List<ProviderOrderQuery> infos = null;
			do {
				pageNo++;
				page.setPageNumber(pageNo);
				
				page = logic.queryProviderOrderInfoByPage(reqQuery, page);
				infos = (List<ProviderOrderQuery>) page.getItems();
				
				allData = convertExportProviderOrder(infos);
				excelWriter.write(header, allData, "精品商城订单", "精品商城订单");
			} while (pageNo < page.getPageCount());
			
			url = excelWriter.getUrl();
			if(StringUtils.isNotEmpty(url)) {
				resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			}else{
				resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			}
			res.setResultVo(resultVo);
			res.setUrl(url);
		} catch (Exception e) {
			LogCvt.error("[供应商订单列表导出]-异常", e);
			resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			res.setResultVo(resultVo);
			res.setUrl(url);
		}
		LogCvt.info("[供应商订单列表导出]-结束");
		return res;
	}


	@Override
	public ExportResultRes inputShippingInfo(List<ShippingExcelInfoVo> excelInfoVos) throws TException {
		LogCvt.info("[导入物流信息导出]-开始");
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo();
		String url = "";
		try {
			
			List<ShippingExcelInfo> inputs = (List<ShippingExcelInfo>) BeanUtil.copyProperties(ShippingExcelInfo.class, excelInfoVos);
			inputs = logic.inputShippingInfo(inputs);
			
			int inputCount = excelInfoVos.size();
			int errCount = 0;
			
			List<String> header = new ArrayList<String>();
			header.add("订单编号");
			header.add("订单状态");
			header.add("用户编号");
			header.add("用户手机号");
			header.add("总金额");
			header.add("创建时间");
			header.add("所属客户端");
			header.add("收货人");
			header.add("商品名称");
			header.add("收货地址");
			header.add("发货状态");
			header.add("物流公司");
			header.add("物流单号");
			header.add("上传时间");
			
			List<List<String>> allData = new ArrayList<List<String>>();
			if(Checker.isNotEmpty(inputs)){
				errCount = inputs.size();
				allData = convertExportShippingInfo(inputs);
				
				ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
				excelWriter.write(header, allData, "精品商城订单", "精品商城订单");
				url = excelWriter.getUrl();
				
				if(StringUtils.isNotEmpty(url)) {
					StringBuilder builder = new StringBuilder(ResultCode.input_partial_fail.getMsg());
					builder.append("，").append("剩余").append(errCount).append("条数据未能成功导入，请重新下载附件查看。");
					
					resultVo.setResultCode(ResultCode.input_partial_fail.getCode());
					resultVo.setResultDesc(builder.toString());
				} else {
					resultVo = convertToResultVo(ResultCode.failed);
				}
				
			}else{
				StringBuilder builder = new StringBuilder(ResultCode.success.getMsg());
				builder.append("，一共导入").append(inputCount).append("条数据。");
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(builder.toString());
			}
			
			res.setResultVo(resultVo);
			res.setUrl(url);
		} catch (Exception e) {
			LogCvt.error("[导入物流信息导出]-异常", e);
			resultVo = convertToResultVo(ResultCode.failed);
			res.setResultVo(resultVo);
			res.setUrl(url);
		}
		LogCvt.info("[导入物流信息导出]-结束");
		return res;
	}

	@Override
	public ResultVo shippingByOrderId(String orderId) throws TException {
		LogCvt.info("[出库操作]-开始");
		ResultVo resultVo = new ResultVo();
		try {
			boolean flag = logic.shippingByOrderId(orderId);
			if(flag){
				resultVo = convertToResultVo(ResultCode.success);
			}else{
				resultVo = convertToResultVo(ResultCode.failed);
			}
		} catch (Exception e) {
			LogCvt.error("[出库操作]-异常", e);
			resultVo = convertToResultVo(ResultCode.failed);
		}
		LogCvt.info("[出库操作]-结束");
		return resultVo;
	}

	@Override
	public ResultVo updateShippingInfo(UpdateShippingInfoReq req) throws TException {
		LogCvt.info("[更新物流信息]-开始");
		ResultVo resultVo = new ResultVo();
		try {
			Map<String, Object> reqMap = new HashMap<String, Object>();
			if(Checker.isNotEmpty(req.getSubOrderId())){
				reqMap.put(FieldMapping.SUB_ORDER_ID.getMongoField(), req.getSubOrderId());
			}
			if(Checker.isNotEmpty(req.getShippingCorpCode())){
				reqMap.put("shipping_corp_code", req.getShippingCorpCode());
			}
			if(Checker.isNotEmpty(req.getShippingId())){
				reqMap.put("shipping_id", req.getShippingId());
			}
			if(req.getInputTime() > 0){
				reqMap.put("input_time", req.getInputTime());
			}
			
			boolean flag = logic.updateShippingInfo(reqMap);
			if(flag){
				resultVo = convertToResultVo(ResultCode.success);
			}else{
				resultVo = convertToResultVo(ResultCode.failed);
			}
		} catch (FroadBusinessException e) {
			LogCvt.error("[更新物流信息]-异常", e);
			resultVo = convertToResultVo(e);
		} catch (Exception e) {
			LogCvt.error("[更新物流信息]-异常", e);
			resultVo = convertToResultVo(ResultCode.failed);
		}
		LogCvt.info("[更新物流信息]-结束");
		return resultVo;
	}
	
	
	private DBObject convertToDbObject(ProviderOrderQueryReq req){
		DBObject query = new BasicDBObject();
		
		if(Checker.isNotEmpty(req.getOrderId())){
			query.put("order_id_like", req.getOrderId());
		}
		
		if (Checker.isNotEmpty(req.getClientId())) {
			query.put(FieldMapping.CLIENT_ID.getMongoField(), req.getClientId());
		}

		if (req.getMemberCode() > 0) {
			query.put(FieldMapping.MEMBER_CODE.getMongoField(), req.getMemberCode());
		}

		if (Checker.isNotEmpty(req.getShippingStatus())) {
			query.put("delivery_state", req.getShippingStatus());
		}

		if (req.getBegTime() > 0 && req.getEndTime() > 0) {
			DBObject peroid = new BasicDBObject();
			peroid.put(QueryOperators.GTE, req.getBegTime());
			peroid.put(QueryOperators.LTE, req.getEndTime());
			query.put(FieldMapping.CREATE_TIME.getMongoField(), peroid);
		}
		
		if(Checker.isNotEmpty(req.getPhone())){
			query.put("phone", req.getPhone());
		}
		
		return query;
	}
	
	
	private ResultVo convertToResultVo(ResultCode resultCode){
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(resultCode.getCode());
		resultVo.setResultDesc(resultCode.getMsg());
		return resultVo;
	}
	
	
	private ResultVo convertToResultVo(FroadBusinessException ex){
		ResultVo resultVo = new ResultVo();
		resultVo.setResultCode(ex.getCode());
		resultVo.setResultDesc(ex.getMsg());
		return resultVo;
	}

	
	private List<List<String>> convertExportProviderOrder(List<ProviderOrderQuery> orderInfos) {
		List<List<String>> perList = new ArrayList<List<String>>();
		if(Checker.isEmpty(orderInfos)) {
			return perList;
		}
		
		List<String> rowList = null;
		ProviderOrderQuery query = null;
		for(int i = 0; i < orderInfos.size(); i++) {
			rowList = new ArrayList<String>();
			query = orderInfos.get(i);
			rowList.add(ObjectUtils.toString(query.getOrderId(), "-"));
			//订单状态
			if(Checker.isNotEmpty(OrderStatus.getType(query.getOrderStatus()))){
				rowList.add(OrderStatus.getType(query.getOrderStatus()).getBossDescribe());
			}else{
				rowList.add("-");
			}
			
			//用户编号
			rowList.add(ObjectUtils.toString(query.getMemberCode(), "-"));
			rowList.add(ObjectUtils.toString(query.getPhone(), "-"));
			rowList.add(Checker.isNotEmpty(query.getTotalPrice()) ? Util.formatDecimalNum(query.getTotalPrice(), Util.moneyFormat) : "-");
			rowList.add(DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, query.getCreateTime()));
			rowList.add(ObjectUtils.toString(query.getClientName(), "-"));
			rowList.add(ObjectUtils.toString(query.getConsignee(), "-"));
			rowList.add(ObjectUtils.toString(query.getProductInfo(), "-"));
			rowList.add(ObjectUtils.toString(query.getRecvAddress(), "-"));
			rowList.add(ObjectUtils.toString(query.getShippingStatus(), "-"));
			rowList.add(ObjectUtils.toString(query.getShippingCorp(), "-"));
			rowList.add(ObjectUtils.toString(query.getShippingId(), "-"));
			rowList.add(query.getInputTime() != null ? DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, query.getInputTime()): "-");
			perList.add(rowList);
		}
		return perList;
	}
	
	
	private List<List<String>> convertExportShippingInfo(List<ShippingExcelInfo> list) {
		List<List<String>> perList = new ArrayList<List<String>>();
		if(Checker.isEmpty(list)) {
			return perList;
		}
		
		List<String> rowList = null;
		ShippingExcelInfo info = null;
		for(int i = 0; i < list.size(); i++) {
			rowList = new ArrayList<String>();
			info = list.get(i);
			rowList.add(ObjectUtils.toString(info.getOrderId(), "-"));
			rowList.add(ObjectUtils.toString(info.getOrderStatus(), "-"));
			rowList.add(ObjectUtils.toString(info.getMemberCode(), "-"));
			rowList.add(ObjectUtils.toString(info.getPhone(), "-"));
			rowList.add(ObjectUtils.toString(info.getTotalPrice(), "-"));
			rowList.add(ObjectUtils.toString(info.getCreateTime(), "-"));
			rowList.add(ObjectUtils.toString(info.getClientName(), "-"));
			rowList.add(ObjectUtils.toString(info.getConsignee(), "-"));
			rowList.add(ObjectUtils.toString(info.getProductInfo(), "-"));
			rowList.add(ObjectUtils.toString(info.getRecvAddress(), "-"));
			rowList.add(ObjectUtils.toString(info.getShippingStatusDesc(), "-"));
			rowList.add(ObjectUtils.toString(info.getShippingCorp(), "-"));
			rowList.add(ObjectUtils.toString(info.getShippingId(), "-"));
			rowList.add(ObjectUtils.toString(info.getInputTime(), "-"));
			perList.add(rowList);
		}
		return perList;
	}
	
}
