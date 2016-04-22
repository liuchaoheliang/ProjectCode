package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.db.mongo.BossCommonMongo;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.OrderStatus;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.enums.ShippingStatus;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.BusinessOrderQueryLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.WaybillCommonLogic;
import com.froad.logic.impl.BusinessOrderQueryLogicImpl;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.WaybillCommonLogicImpl;
import com.froad.po.Client;
import com.froad.po.Waybill;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BusinessOrderQueryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.Business.BusinessOrderListReq;
import com.froad.thrift.vo.Business.BusinessOrderListRes;
import com.froad.thrift.vo.Business.BusinessOrderPyamentInfoRes;
import com.froad.thrift.vo.Business.BusinessOrderPyamentInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderRefundInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderRefundInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderShippingInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderShippingInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderTradeInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderTradeInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.Util;

public class BusinessOrderQueryServiceImpl extends BizMonitorBaseService implements BusinessOrderQueryService.Iface{

	private BusinessOrderQueryLogic logic = new BusinessOrderQueryLogicImpl();
	private WaybillCommonLogic waybillCommonLogic = new WaybillCommonLogicImpl();
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	
	public BusinessOrderQueryServiceImpl() {
		
	}
	
	public BusinessOrderQueryServiceImpl(String name, String version) {
		super(name, version);
	}
	
	/**
	 * 查询运营订单列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BusinessOrderListRes queryBusinessOrderList(BusinessOrderListReq req) throws TException {
		BusinessOrderListRes res = new BusinessOrderListRes();
		List<BusinessOrderVo> voList = new ArrayList<BusinessOrderVo>();
		ResultVo resultVo = null;
		
		try {
			if (Checker.isEmpty(req)) {
				resultVo = new ResultVo(ResultCode.failed.getCode(), "查询条件不能为空");
				res.setResultVo(resultVo);
				res.setVoList(voList);
				return res;
			}
			
			MongoPage page = logic.getBusinessOrderList(req);
			
			PageVo pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			voList = (List<BusinessOrderVo>) page.getItems();
			res.setPageVo(pageVo);
			res.setVoList(voList);
			
			resultVo = new ResultVo(ResultCode.success.getCode(), "查询运营订单分页列表成功");
			res.setResultVo(resultVo);
			
		} catch(Exception e) {
			LogCvt.error("Boss平台查询运营订单分页列表失败", e);
			resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			res.setResultVo(resultVo);
			res.setVoList(voList);
			res.setPageVo(req.getPageVo());
		}
		return res;
	}

	
	@Override
	public ExportResultRes exportBusinessOrder(BusinessOrderListReq req) throws TException {
		int pageNo = 0;
		int pageSize = 1000;
		PageVo pageVo = req.getPageVo() == null ? new PageVo() : req.getPageVo();
		pageVo.setPageSize(pageSize);
		
		//第一步，根据条件查询所有的数据
		List<BusinessOrderVo> orderList = new ArrayList<BusinessOrderVo>();
		BusinessOrderListRes res = new BusinessOrderListRes();
		
		//第二步：将excel列头放入List<String>   将数据放入List<List<String>> 
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("订单编号");
		header.add("子订单编号");
		header.add("订单状态");
		header.add("用户编号");
		header.add("支付方式");
		header.add("总金额");
		header.add("现金");
		header.add("联盟积分");
		header.add("银行积分");
		header.add("银行积分兑换比例");
		header.add("订单创建时间");
		header.add("支付时间");
		header.add("所属客户端");
		header.add("发货状态");
		
		List<List<String>> data = new ArrayList<List<String>>();
		ResultVo resultVo = new ResultVo();
		String url = null;
		try {
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			boolean isSuccess = true;
			do {
				pageNo++;
				pageVo.setPageNumber(pageNo);
				req.setPageVo(pageVo);
				res.setVoList(null);
				res = queryBusinessOrderList(req);//查询运营订单列表
				
				if (Checker.isNotEmpty(res)) {
					pageVo = res.getPageVo();
					orderList = res.getVoList();
					
					data = orderConvertExcel(orderList, (pageNo - 1) * pageSize);
					try {
						excelWriter.write(header, data, "精品商城订单", "精品商城订单");
					} catch (Exception e) {
						LogCvt.error("导出运营订单列表失败", e);
						isSuccess = false;
						break;
					}
				} else {
					orderList = null;
				}
			} while(orderList != null && orderList.size() >= pageSize);
			if(isSuccess) {
				url = excelWriter.getUrl();
				if(StringUtils.isNotEmpty(url)) {
					resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
				} else {
					resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
				}
			} else {
				resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			}
		} catch(Exception e) {
			resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(resultVo);
		resultRes.setUrl(url);
		return resultRes;
	}


 	/**
	 * 运营订单列表导出
	 * @author liuyanyun
	 */
	private List<List<String>> orderConvertExcel(List<BusinessOrderVo> orderList, int index) {
		List<List<String>> data = new ArrayList<List<String>>();
			
		if (orderList == null || orderList.size() == 0) {
			return data;
		}
			
		//将数据放入List<List<String>>
		List<String> record =null;
		String createTime = "";
		for (BusinessOrderVo vo : orderList) {
			record = new ArrayList<String>();
			//订单编号
			if (Checker.isNotEmpty(vo.getOrderId())) {
				record.add(vo.getOrderId());
			} else {
				record.add("-");
			}
			//子订单编号
			if(Checker.isNotEmpty(vo.getSubOrderId())){
				record.add(vo.getSubOrderId());
			} else{
				record.add("-");
			}
			//订单状态 1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成
			if(Checker.isNotEmpty(vo.getOrderStatus())){
				record.add(OrderStatus.getType(vo.getOrderStatus()).getBossDescribe());
			} else {
				record.add("-");
			}
			//用户编号
			if (Checker.isNotEmpty(vo.getMemberCode())) {
				record.add(String.valueOf(vo.getMemberCode()));
			} else {
				record.add("-");
			}
			//支付方式 1:现金支付;2:联盟积分支付;3:银行积分支付;4:联盟积分+现金支付;5:银行积分+现金支付;6:赠送积分
			if (Checker.isNotEmpty(vo.getPaymentMethod())) {
				record.add(PaymentMethod.getByCode(vo.getPaymentMethod()).getBossDescribe());
			} else {
				record.add("-");
			}
			//总金额
			if (Checker.isNotEmpty(vo.getTotalPrice()) && vo.getTotalPrice() != 0) {
				record.add(Util.formatDecimalNum(vo.getTotalPrice(), Util.moneyFormat));
			} else {
				record.add("-");
			}
			//现金
			if (vo.getRealPrice() != 0) {
				record.add(Util.formatDecimalNum(vo.getRealPrice(), Util.moneyFormat));
			} else {
				record.add("-");
			}
			//联盟积分
			if (vo.getFftPoints() != 0) {
				record.add(Util.formatDecimalNum(vo.getFftPoints(), Util.moneyFormat));
			} else {
				record.add("-");
			}
			//银行积分
			if (vo.getBankPoints() != 0) {
				record.add(Util.formatDecimalNum(vo.getBankPoints(), Util.moneyFormat));
			} else {
				record.add("-");
			}
			//银行积分兑换比例
			if (Checker.isNotEmpty(vo.getPoinRate()) && !"0".equals(vo.getPoinRate())) {
				record.add(vo.getPoinRate() + ":1");
			} else {
				record.add("-");
			}
			//订单创建时间
			if (vo.getCreateTime() != 0) {
				createTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getCreateTime()));
				record.add(createTime);
			} else {
				record.add("-");
			}
			//支付时间
			if (vo.getPaymentTime() != 0) {
				createTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getPaymentTime()));
				record.add(createTime);
			} else {
				record.add("-");
			}
			//所属客户端
			if (Checker.isNotEmpty(vo.getClientId())) {
				Client client = commonLogic.getClientById(vo.getClientId());
				record.add(client.getBankName());
			} else {
				record.add("-");
			}
			//发货状态 0:未发货;1:已发货;2:已收货;3:未提货;4:已提货
			if (Checker.isNotEmpty(vo.getDeliveryStatus())) {
				record.add(ShippingStatus.getType(vo.getDeliveryStatus()).getDescribe());
			} else {
				record.add("-");
			}
			data.add(record);
		}
		return data;
	}

	@Override
	public BusinessOrderPyamentInfoRes queryBusinessOrderPaymentInfo(
			String clientId, String subOrderId)
			throws TException {
		BusinessOrderPyamentInfoRes res = new BusinessOrderPyamentInfoRes();
		BusinessOrderPyamentInfoVo infoVo = new BusinessOrderPyamentInfoVo();
		ResultVo resultVo = new ResultVo();
		
		if (Checker.isEmpty(clientId) || Checker.isEmpty(subOrderId)) {
			resultVo = new ResultVo(ResultCode.failed.getCode(), "客户端ID和子订单号不能为空");
			res.setInfoVo(infoVo);
			res.setResultVo(resultVo);
			return res;
		}
		
		return logic.getBusinessOrderPaymentInfo(clientId, subOrderId);
	}

	@Override
	public BusinessOrderTradeInfoVoRes queryBusinessOrderTradeInfo(
			String clientId, String subOrderId)
			throws TException {
		BusinessOrderTradeInfoVoRes res = new BusinessOrderTradeInfoVoRes();
		List<BusinessOrderTradeInfoVo> infoVos = new ArrayList<BusinessOrderTradeInfoVo>();
		ResultVo resultVo = new ResultVo();
		
		if (Checker.isEmpty(clientId) || Checker.isEmpty(subOrderId)) {
			resultVo = new ResultVo(ResultCode.failed.getCode(), "客户端ID和子订单号不能为空");
			res.setInfoVos(infoVos);
			res.setResultVo(resultVo);
			return res;
		}
		
		return logic.getBusinessOrderTradeInfo(clientId, subOrderId);
	}

	/**
	 * 查询运营订单物流信息
	 * 2015-12-18
	 * * @param clientId
	 * @param subOrderId
	 * @return  BusinessOrderShippingInfoVoRes
	 * 
	 */
	@Override
	public BusinessOrderShippingInfoVoRes queryBusinessOrderShippingInfo(
			String clientId, String subOrderId)
			throws TException {
		BusinessOrderShippingInfoVoRes res = new BusinessOrderShippingInfoVoRes();
		BusinessOrderShippingInfoVo infoVo = new BusinessOrderShippingInfoVo();
		ResultVo resultVo = new ResultVo();
		
		if (Checker.isEmpty(clientId) && Checker.isEmpty(subOrderId)) {
			resultVo = new ResultVo(ResultCode.failed.getCode(), "客户端ID和子订单号不能为空");
			res.setResultVo(resultVo);
			res.setInfoVo(infoVo);
			return res;
		}
		
		BossCommonMongo mongo = new BossCommonMongo();
		
		Waybill waybill = waybillCommonLogic.findBySubOrderId(subOrderId);
		SubOrderMongo sub = mongo.findSubOrderBySubOrderId(subOrderId);
		if (Checker.isEmpty(waybill) || Checker.isEmpty(sub)) {
			resultVo = new ResultVo(ResultCode.failed.getCode(), "子订单号subOrderId：" + subOrderId + "不存在物流信息");
			res.setResultVo(resultVo);
			res.setInfoVo(infoVo);
			return res;
		}
		
		
		infoVo.setStatus(sub.getDeliveryState());//发货状态
		infoVo.setShippingCorp(waybill.getShippingCorp());//物流公司
		infoVo.setShippingTime(waybill.getShippingTime().getTime());//发货日期
		infoVo.setShippingId(waybill.getShippingId());//物流单号
		infoVo.setMessage(waybill.getMessage());
		
		resultVo = new ResultVo(ResultCode.success.getCode(), "queryBusinessOrderShippingInfo：查询运营订单物流信息成功");
		res.setInfoVo(infoVo);
		res.setResultVo(resultVo);
		return res;
	}

	/**
	 * 查询运营订单退款信息
	 * 2015-12-18 
	 * @param clientId
	 * @param subOrderId
	 * @return  BusinessOrderRefundInfoVoRes
	 */
	@Override
	public BusinessOrderRefundInfoVoRes queryBusinessOrderRefundInfo(
			String clientId, String subOrderId)
			throws TException {
		BusinessOrderRefundInfoVoRes res = new BusinessOrderRefundInfoVoRes();
		ResultVo resultVo = new ResultVo();
		
		if (Checker.isEmpty(clientId) && Checker.isEmpty(subOrderId)) {
			resultVo = new ResultVo(ResultCode.failed.getCode(), "客户端ID和子订单号不能为空");
			res.setResultVo(resultVo);
			res.setInfoVo(new ArrayList<BusinessOrderRefundInfoVo>());
			return res;
		}
		
		return logic.getBusinessOrderRefundInfo(clientId, subOrderId);
	}
		
}
