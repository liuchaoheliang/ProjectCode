package com.froad.cbank.coremodule.module.normal.bank.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.CreateSource;
import com.froad.cbank.coremodule.module.normal.bank.enums.DistributionType;
import com.froad.cbank.coremodule.module.normal.bank.enums.OrderTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.PayMentMethodEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.RefundStateEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.NumberUtil4Bank;
import com.froad.cbank.coremodule.module.normal.bank.util.OrderTypeUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.OrgCodeUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrderProductVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrderResVo4PointList;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankProductVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BoutiqueOrderListResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BoutiqueOrderReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.DeliveryCompanyReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderDetailVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderReqOfOptimize;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderResOfOptimize;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderVo4Face2Face;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderVoOfOptimize;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVo4List;
import com.froad.cbank.coremodule.module.normal.bank.vo.SubOrderVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.SubProductVo;
import com.froad.thrift.service.DeliveryCorpService;
import com.froad.thrift.service.DeliveryWayBillService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrderQueryService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.DeliveryCorpVo;
import com.froad.thrift.vo.DeliveryWayBillVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.DeliverInfoVo;
import com.froad.thrift.vo.order.GetOrderDetailByBankManageVoReq;
import com.froad.thrift.vo.order.GetOrderDetailByBankManageVoRes;
import com.froad.thrift.vo.order.OrderDetailRes;
import com.froad.thrift.vo.order.ProductInfoVo;
import com.froad.thrift.vo.order.ProductVo;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVo;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVoRes;
import com.froad.thrift.vo.order.QueryOrderByBankManageVo;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoRes;

/**
 * 订单管理
 * 
 * @author ylchu
 * 
 * 陈明灿
 *
 */
@Service
public class OrderManageService {

	@Resource
	OrderQueryService.Iface orderQueryService;
	@Resource
	DeliveryCorpService.Iface deliveryCorpService;
	@Resource
	OrderService.Iface orderService;
	@Resource
	OutletService.Iface outletService;
	@Resource
	OrgService.Iface orgService;
	@Resource
	MerchantUserService.Iface merchantUserService;

	@Resource
	DeliveryWayBillService.Iface deliveryWayBillService;

	private static final String ALL_REFUND = "1";
	private static final String NOT_ALL_REFUND = "0";

	/**
	 * 查询列表
	 * 
	 * @param order
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	public HashMap<String, Object> findPageByConditions(OrderReq order) throws TException, ParseException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		QueryOrderByBankManageVoReq req = new QueryOrderByBankManageVoReq();
		PageVo page = new PageVo();
		page.setPageNumber(order.getPageNumber());
		page.setPageSize(order.getPageSize());
		page.setLastPageNumber(order.getLastPageNumber());
		page.setFirstRecordTime(order.getFirstRecordTime());
		page.setLastRecordTime(order.getLastRecordTime());
		req.setPage(page);
		req.setClientId(order.getClientId());
		req.setOrgCode(order.getOrgCode());
		req.setType(order.getType());
		if (StringUtil.isNotEmpty(order.getOrderId())) {
			req.setOrderId(order.getOrderId());
		}
		if (StringUtil.isNotEmpty(order.getOrderStatus())) {
			// req.setOrderStatus(order.getOrderStatus());
		}
		if (StringUtil.isNotEmpty(order.getMerchantName())) {
			req.setMerchantName(order.getMerchantName());
		}
		if (StringUtil.isNotEmpty(order.getStartDate())) {
			req.setStartTime(DateUtil.DateFormat(order.getStartDate()));
		}
		if (StringUtil.isNotEmpty(order.getEndDate())) {
			req.setEndTime(DateUtil.DateFormatEnd(order.getEndDate()));
		}
		QueryOrderByBankManageVoRes queryRes = orderQueryService.queryOrderByBankManage(req);
		List<OrderRes> orderList = new ArrayList<OrderRes>();
		List<QueryOrderByBankManageVo> bankOrderList = queryRes.getOrdervo();
		if (bankOrderList != null && bankOrderList.size() > 0) {

			// LogCvt.info("getOrg列表请求返回数据长度:" + orgVos.size());
			for (QueryOrderByBankManageVo bankOrder : bankOrderList) {
				OrderRes res = new OrderRes();
				res.setOrderId(bankOrder.getOrderId());
				// String orgCode = bankOrder.getOrgCode();

				res.setCreateTime(
						DateUtil.formatDate(new Date(bankOrder.getCreateTime()), DateUtil.DATE_TIME_FORMAT_01));
				res.setSubOrderId(bankOrder.getSubOrderId());
				res.setPaymentMethod(bankOrder.getPaymentMethod());
				if (StringUtil.isNotEmpty(bankOrder.getPaymentMethod())) {
					if (bankOrder.getPaymentMethod().equals(PayMentMethodEnum.cash.getCode())) {
						res.setPaymentMethodName(PayMentMethodEnum.cash.getDescription());
					}
					if (bankOrder.getPaymentMethod().equals(PayMentMethodEnum.froadPoints.getCode())) {
						res.setPaymentMethodName(PayMentMethodEnum.froadPoints.getDescription());
					}
					if (bankOrder.getPaymentMethod().equals(PayMentMethodEnum.bankPoints.getCode())) {
						res.setPaymentMethodName(PayMentMethodEnum.bankPoints.getDescription());
					}
					if (bankOrder.getPaymentMethod().equals(PayMentMethodEnum.froadPointsAndCash.getCode())) {
						res.setPaymentMethodName(PayMentMethodEnum.froadPointsAndCash.getDescription());
					}
					if (bankOrder.getPaymentMethod().equals(PayMentMethodEnum.bankPointsAndCash.getCode())) {
						res.setPaymentMethodName(PayMentMethodEnum.bankPointsAndCash.getDescription());
					}
				} else {
					res.setPaymentMethodName("未支付");
				}
				res.setOrderState(bankOrder.getOrderStatus()); // 订单状态(1.
																// 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5.
																// 订单支付失败，6.订单支付完成)
				if (StringUtil.isNotEmpty(bankOrder.getOrderStatus())) {
					if (bankOrder.getOrderStatus().equals("1")) {
						res.setOrderStateName("订单创建");
					}
					if (bankOrder.getOrderStatus().equals("2")) {
						res.setOrderStateName("订单关闭");
					}
					if (bankOrder.getOrderStatus().equals("3")) {
						res.setOrderStateName("订单系统关闭");
					}
					if (bankOrder.getOrderStatus().equals("4")) {
						res.setOrderStateName("订单支付中");
					}
					if (bankOrder.getOrderStatus().equals("5")) {
						res.setOrderStateName("订单支付失败");
					}
					if (bankOrder.getOrderStatus().equals("6")) {
						res.setOrderStateName("订单支付完成");
					}
				} else {
					res.setOrderStateName("");
				}
				if (StringUtil.isNotEmpty(bankOrder.getPoint() + "")) {
					res.setPoint(TargetObjectFormat.getDoubleDecimalNum(bankOrder.getPoint()));
				}
				if (StringUtil.isNotEmpty(bankOrder.getSubTotalMoney() + "")) {
					res.setSubTotalMoney(TargetObjectFormat.getDoubleDecimalString(bankOrder.getSubTotalMoney()));
				}
				if (StringUtil.isNotEmpty(bankOrder.getCash() + "")) {
					res.setCash(TargetObjectFormat.getDoubleDecimalString(bankOrder.getCash()));
				}
				if (StringUtil.isNotEmpty(bankOrder.getCreateSoure())) {
					if (bankOrder.getCreateSoure().equals(CreateSource.pc.getCode())) {
						res.setCreateSoure(CreateSource.pc.getDescribe());
					}
					if (bankOrder.getCreateSoure().equals(CreateSource.android.getCode())) {
						res.setCreateSoure(CreateSource.android.getDescribe());
					}
					if (bankOrder.getCreateSoure().equals(CreateSource.iphone.getCode())) {
						res.setCreateSoure(CreateSource.iphone.getDescribe());
					}
					if (bankOrder.getCreateSoure().equals(CreateSource.ipad.getCode())) {
						res.setCreateSoure(CreateSource.ipad.getDescribe());
					}
				} else {
					res.setCreateSoure("");
				}
				res.setMerchantName(bankOrder.getMerchantName());
				// 在预售订单中merchant_name就是orgname
				res.setOrgName(bankOrder.getMerchantName());
				res.setProductName(bankOrder.getProductName());
				res.setDeliveryState(bankOrder.getDeliveryState());// 配送状态（只有名优特惠有值）
																	// 0.未发货，1.
																	// 已发货， 2.
																	// 已收货
				if (StringUtil.isNotEmpty(bankOrder.getDeliveryState())) {
					if (bankOrder.getDeliveryState().equals("0")) {
						res.setDeliveryStateName("未发货");
					}
					if (bankOrder.getDeliveryState().equals("1")) {
						res.setDeliveryStateName("已发货");
					}
					if (bankOrder.getDeliveryState().equals("2")) {
						res.setDeliveryStateName("已收货");
					}
				} else {
					res.setDeliveryStateName("");
				}
				res.setRefundState(bankOrder.getRefundState());// 退款状态
				if (StringUtil.isNotEmpty(bankOrder.getRefundState())) {
					if (bankOrder.getRefundState().equals(RefundStateEnum.REFUND_INIT.getCode())) {// 未退款为空
						res.setRefundStateName("");
					}
					if (bankOrder.getRefundState().equals(RefundStateEnum.REFUND_PROCESSING.getCode())) {
						res.setRefundStateName(RefundStateEnum.REFUND_PROCESSING.getDescription());
					}
					if (bankOrder.getRefundState().equals(RefundStateEnum.REFUND_SUCCESS.getCode())) {
						res.setRefundStateName(RefundStateEnum.REFUND_SUCCESS.getDescription());
					}
					if (bankOrder.getRefundState().equals(RefundStateEnum.REFUND_PART.getCode())) {
						res.setRefundStateName(RefundStateEnum.REFUND_PART.getDescription());
					}
				} else {
					res.setRefundStateName("");
				}
				// 销售数量
				res.setQuantity(
						StringUtil.isNotBlank(bankOrder.getQuantity()) ? String.valueOf(bankOrder.getQuantity()) : "");
				orderList.add(res);
			}
		}
		reMap.put("orderList", orderList);
		putPageValueToMap(reMap, queryRes);
		return reMap;
	}

	/**
	 * 
	 * getGroupOrderExport:(订单下载优化).
	 *
	 * @author wufei 2015-9-7 下午02:31:39
	 * @param order
	 * @return
	 *
	 */
	public Map<String, Object> getGroupOrderExport(OrderReqOfOptimize req) throws TException, ParseException {
		LogCvt.info("订单导出-请求参数: " + JSON.toJSONString(req));
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		QueryOrderByBankManageVoReq reqVo = new QueryOrderByBankManageVoReq();
		//
		if ((OrderTypeEnum.PRESALE.getCode().equals(req.getType())
				|| OrderTypeEnum.ONLINE.getCode().equals(req.getType())
				|| OrderTypeEnum.OFFLINE.getCode().equals(req.getType()))
				&& DistributionType.TAKE.getCode().equals(req.getDeliveryOption())) {
			List<String> orgCodeList = orgService.getIntersectionOrgCodeList(req.getClientId(), req.getMyOrgCode(),
					req.getOrgCode());
			if (orgCodeList != null && orgCodeList.size() > 0) {
				LogCvt.info("orgCode交集长度：" + orgCodeList.size());
				reqVo.setOrgCodes(orgCodeList);
			} else {
				LogCvt.error("orgCode交集为空");

			}
		}
		// 封装请求参数
		setReqArgument(req, reqVo);
		// LogCvt.info("*************订单导出传递参数" + JSON.toJSONString(req));
		ExportResultRes result = orderQueryService.exportOrderByBankManage(reqVo);
		LogCvt.info("*************订单导出返回数据" + JSON.toJSONString(result));
		if (result.getResultVo() != null && StringUtil.isNotBlank(result.getUrl())) {
			reMap.put("url", result.getUrl());
			reMap.put("code", result.getResultVo().getResultCode());
			reMap.put("message", result.getResultVo().getResultDesc());
		} else {
			reMap.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			reMap.put(ResultEnum.MESSAGE.getCode(), "订单导出失败");
		}
		return reMap;
	}

	/**
	 * 
	 * setReqArgument:请求参数注入
	 *
	 * @author 明灿 2015年10月10日 下午4:38:35
	 * @param order
	 * @param req
	 * @throws ParseException
	 *
	 */
	private void setReqArgument(OrderReqOfOptimize order, QueryOrderByBankManageVoReq req) throws ParseException {
		req.setClientId(order.getClientId());
		req.setOrgCode(order.getOrgCode());
		req.setType(order.getType()); // 订单类型
		if (StringUtil.isNotEmpty(order.getBigOrderId())) {
			req.setOrderId(order.getBigOrderId()); // 订单编号
		}
		if (StringUtil.isNotEmpty(order.getOrderStatus())) {
			OrderTypeUtil.getOrderStatus(order.getOrderStatus(), req);// 订单状态
		}
		if (StringUtil.isNotEmpty(order.getMerchantName())) {
			req.setMerchantName(order.getMerchantName()); // 所属商户
		}
		if (StringUtil.isNotEmpty(order.getStartDate())) {
			req.setStartTime(DateUtil.DateFormat(order.getStartDate())); // 下单开始时间
		}
		if (StringUtil.isNotEmpty(order.getEndDate())) {
			req.setEndTime(DateUtil.DateFormatEnd(order.getEndDate())); // 下单结束时间
		}
		// 预售列表参数
		if (StringUtil.isNotBlank(order.getDeliveryOption())) {
			req.setDeliveryOption(order.getDeliveryOption());// 0;配送,1:自提
		}
		if (StringUtil.isNotBlank(order.getMyOrgCode())) {
			req.setMyOrgCode(order.getMyOrgCode());// 当前登录人的所属机构
		}
	}

	/**
	 * 
	 * groupOrderList:订单优化
	 *
	 * @author 明灿 2015年8月24日 下午7:52:11
	 * @param order
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public HashMap<String, Object> getOrderList(OrderReqOfOptimize req) throws TException, ParseException {
		LogCvt.info("----->订单查询请求参数<-----: " + JSON.toJSONString(req));
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		QueryOrderByBankManageVoReq reqVo = new QueryOrderByBankManageVoReq();
		/**
		 * 如果是预售和在线积分商品并且是自提的话,要将登录人的所属机构(myOrgCode)、
		 * 查询条件的所属机构(orgCode)、两者合集的网点集合list<String> orgCodes传过去
		 */
		if ((OrderTypeEnum.PRESALE.getCode().equals(req.getType())
				|| OrderTypeEnum.ONLINE.getCode().equals(req.getType())
				|| OrderTypeEnum.OFFLINE.getCode().equals(req.getType()))
				&& DistributionType.TAKE.getCode().equals(req.getDeliveryOption())) {
			List<String> orgCodeList = orgService.getIntersectionOrgCodeList(req.getClientId(), req.getMyOrgCode(),
					req.getOrgCode());

			if (orgCodeList != null && orgCodeList.size() > 0) {
				LogCvt.info("orgCode交集长度：" + orgCodeList.size());
				reqVo.setOrgCodes(orgCodeList);
			} else {
				LogCvt.error("orgCode交集为空");

			}
		}
		// 请求参数
		requestArguments(req, reqVo);
		// 调用server订单列表
		QueryOrderByBankManageVoRes queryRes = orderQueryService.queryOrderByBankManage(reqVo);
		LogCvt.info("----->订单列表查询返回数据<-----: " + JSON.toJSONString(queryRes));
		OrderResOfOptimize res = null;
		List<OrderResOfOptimize> orderList = null;
		List<QueryOrderByBankManageVo> bankOrderList = queryRes.getOrdervo();
		if (bankOrderList != null && bankOrderList.size() > 0) {
			orderList = new ArrayList<OrderResOfOptimize>();
			for (QueryOrderByBankManageVo bankOrder : bankOrderList) {
				res = new OrderResOfOptimize();
				// 通用属性注入
				this.setValue2OrderListVo(req, res, bankOrder, req.getType(),
						req.getDeliveryOption());
				orderList.add(res);
			}
		}
		reMap.put("orderList", orderList);
		// 将page相关返回值放到map中
		this.putPageValueToMap(reMap, queryRes);
		return reMap;
	}

	/**
	 * 
	 * boutiqueShoppingOrderList:精品商城订单列表
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 下午1:52:53
	 * @param req
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public HashMap<String, Object> boutiqueShoppingOrderList(BoutiqueOrderReq req) throws TException, ParseException {
		LogCvt.info("----->精品商城订单查询请求参数<-----: " + JSON.toJSONString(req));
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		QueryBoutiqueOrderByBankManageVoReq reqVo = new QueryBoutiqueOrderByBankManageVoReq();
		// 请求参数
		boutiqueRequestArguments(req, reqVo);
		// 调用server订单列表
		QueryBoutiqueOrderByBankManageVoRes boutiqueOrderByBankManageVoRes = orderQueryService
				.queryBoutiqueOrderByBankManage(reqVo);
		PageVo pageVo = boutiqueOrderByBankManageVoRes.getPageVo();
		LogCvt.info("----->订单列表查询返回数据<-----: " + JSON.toJSONString(boutiqueOrderByBankManageVoRes));
		BoutiqueOrderListResVo bankOrderRes = null;
		List<BoutiqueOrderListResVo> orderList = null;
		List<QueryBoutiqueOrderByBankManageVo> ordervo = boutiqueOrderByBankManageVoRes.getOrdervo();
		if (ordervo != null && ordervo.size() > 0) {
			orderList = new ArrayList<BoutiqueOrderListResVo>();
			for (QueryBoutiqueOrderByBankManageVo order : ordervo) {
				bankOrderRes = new BoutiqueOrderListResVo();
				BeanUtils.copyProperties(bankOrderRes, order);
				/**
				 * 转化商品名称
				 */
				covertProductName(bankOrderRes, order.getProductNames());
				orderList.add(bankOrderRes);
			}
		}
		reMap.put("orderList", orderList);
		// 将page相关返回值放到map中
		putPageValueToMap(reMap, pageVo);
		return reMap;
	}

	/**
	 * 
	 * covertProductName:转化商品名称
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 下午4:08:54
	 * @param bankOrderRes
	 * @param productNames
	 *
	 */
	private void covertProductName(BoutiqueOrderListResVo bankOrderRes, String productNames) {
		List<String> products = new ArrayList<String>();
		if (StringUtil.isNotBlank(productNames)) {
			String[] strs = productNames.split(",");
			if (strs != null && strs.length > 0) {
				for (String productName : strs) {
					products.add(productName);
				}
			}
		}
		bankOrderRes.setProductNames(products);
	}

	/**
	 * 
	 * putPageValueToMap:封装分页信息
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 下午3:30:07
	 * @param reMap
	 * @param pageVo
	 *
	 */
	private void putPageValueToMap(HashMap<String, Object> reMap, PageVo pageVo) {
		if (pageVo != null) {
			reMap.put("pageCount", pageVo.getPageCount());
			reMap.put("totalCount", pageVo.getTotalCount());
			reMap.put("pageNumber", pageVo.getPageNumber());
			reMap.put("lastPageNumber", pageVo.getLastPageNumber());
			reMap.put("firstRecordTime", pageVo.getFirstRecordTime());
			reMap.put("lastRecordTime", pageVo.getLastRecordTime());
		} else {
			reMap.put("pageCount", 0);
			reMap.put("totalCount", 0);
			reMap.put("pageNumber", 1);
			reMap.put("lastPageNumber", 0);
			reMap.put("firstRecordTime", 0);
			reMap.put("lastRecordTime", 0);
		}

	}

	private void boutiqueRequestArguments(BoutiqueOrderReq bankReqVo, QueryBoutiqueOrderByBankManageVoReq thriftQeqVo)
			throws ParseException {
		PageVo page = new PageVo();
		page.setPageNumber(bankReqVo.getPageNumber() == null ? 1 : bankReqVo.getPageNumber());
		page.setPageSize(bankReqVo.getPageSize() == null ? 10 : bankReqVo.getPageSize());
		page.setLastPageNumber(bankReqVo.getLastPageNumber() == null ? 0 : bankReqVo.getLastPageNumber());
		page.setLastRecordTime(bankReqVo.getLastRecordTime() == null ? 0 : bankReqVo.getLastRecordTime());
		page.setFirstRecordTime(bankReqVo.getFirstRecordTime() == null ? 0 : bankReqVo.getFirstRecordTime());
		thriftQeqVo.setPage(page);
		/**
		 * 导出的请求参数
		 */
		requset4Export(bankReqVo, thriftQeqVo);

	}

	private void requset4Export(BoutiqueOrderReq bankReqVo, QueryBoutiqueOrderByBankManageVoReq thriftQeqVo)
			throws ParseException {
		thriftQeqVo.setProviderName(bankReqVo.getProviderName());
		thriftQeqVo.setClientId(bankReqVo.getClientId());
		thriftQeqVo.setStartTime(DateUtil.DateFormat(bankReqVo.getStartDate()));
		thriftQeqVo.setEndTime(DateUtil.DateFormatEnd(bankReqVo.getEndDate()));
		if (StringUtil.isNotBlank(bankReqVo.getSubOrderId())) {
			thriftQeqVo.setSubOrderId(bankReqVo.getSubOrderId());
		}
		if (StringUtil.isNotBlank(bankReqVo.getOrderStatus())) {
			// 状态码转化
			String status = OrderTypeUtil.getOrderStatus(bankReqVo.getOrderStatus(), thriftQeqVo);
			thriftQeqVo.setOrderStatus(status);
		}
	}
	/**
	 * 
	 * setValue2OrderListVo:订单列表属性封装
	 *
	 * @author 明灿 2015年9月4日 上午10:27:50
	 * @param req
	 * @param res
	 * @param bankOrder
	 * @throws TException
	 *
	 */
	private void setValue2OrderListVo(OrderReqOfOptimize req,
			OrderResOfOptimize res, QueryOrderByBankManageVo bankOrder,
			String type, String deliveryOption) throws TException {
		/**
		 * 添加返回门店的名称
		 */
		String orderId = bankOrder.getOrderId();
		if (StringUtil.isNotBlank(orderId)) {
			OutletVo outletVo = this.getOutletByOutletaId(orderId);
			if (null != outletVo) {
				res.setOutletName(outletVo.getOutletName());
			}
		}
		// 获取收货人信息
		DeliverInfoVo deliverInfoVo = bankOrder.getDeliverInfoVo();
		if (deliverInfoVo != null) {
			res.setPhone(deliverInfoVo.getPhone());// 手机号
			res.setTaker(deliverInfoVo.getConsignee());// 收货人
			res.setAddress(deliverInfoVo.getAddress());// 地址
		}
		// 获取商品集合
		List<ProductVo> productList = bankOrder.getProductList();
		// LogCvt.info("------商品集合数量------" + productList.size());
		/**
		 * 遍历商品集合复制字段
		 */
		this.getProductsAndAddToList(productList, res, req.getType(), deliveryOption);
		if (OrderTypeEnum.PRESALE.getCode().equals(type)) {
			// 精品预售
			res.setStatus(OrderTypeUtil.getPresaleStatus(bankOrder));
		}
		if (OrderTypeEnum.SPECIAL.getCode().equals(type)) {
			// 名优特惠
			res.setStatus(OrderTypeUtil.getPresaleStatus(bankOrder));
		}
		res.setConsumeStatus(bankOrder.getOrderStatus());// 消费状态
		res.setCreateTime(bankOrder.getCreateTime());// 下单时间
		res.setMerchantName(bankOrder.getMerchantName());// 商户名称
		res.setOrderId(bankOrder.getOrderId());// 订单号
		res.setSubOrderId(bankOrder.getSubOrderId());// 子订单号
		// 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5.订单支付失败，6.订单支付完成))
		if (OrderTypeEnum.PRESALE.getCode().equals(type)) {
			res.setMoney(bankOrder.getSubTotalMoneyPresell());// 金额
		} else {
			res.setMoney(bankOrder.getSubTotalMoney());// 金额
		}
		res.setOrderStatus(bankOrder.getOrderStatus());
		// 支付方式
		res.setPaymentMethod(bankOrder.getPaymentMethod());
		res.setPoint(bankOrder.getPoint());// 积分
		res.setQuantity(bankOrder.getQuantity());// 购买数量
		res.setRefundStatus(bankOrder.getRefundState());// 退款状态
		if (OrderTypeEnum.PRESALE.getCode().equals(type)) {
			res.setOrgNames(OrgCodeUtil.getOrgNames(bankOrder.getOrgNames(), type));// 网点
		} else {
			res.setOrgNames(OrgCodeUtil.splitOrgNames(bankOrder.getOrgNames()));// 网点
		}
	}

	/**
	 * 
	 * @Title: getOutletByOutletaId
	 * @Description: 根据门店id获取门店对象
	 * @author chenmingcan@f-road.com.cn
	 * @date 2015年11月24日,下午3:24:15
	 * @param clientId
	 *            客户端
	 * @param outletId
	 *            门店id
	 * @return 门店对象
	 * @throws TException
	 */
	private OutletVo getOutletByOutletaId(String outletId) throws TException {
		OutletVo outletVo = outletService.getOutletByOutletId(outletId);
		if (LogCvt.isDebugEnabled()) {
			LogCvt.debug("订单操作获取门店对象返回数据:" + JSON.toJSONString(outletVo));
		}
		if (null != outletVo) {
			return outletVo;
		}
		return null;
	}

	/**
	 * 
	 * getProductsAndAddToList:
	 *
	 * @author 明灿 2015年9月2日 上午10:48:37
	 * @param productList
	 * @return
	 *
	 */
	private void getProductsAndAddToList(List<ProductVo> productList, OrderResOfOptimize res, String type,
			String deliveryOption) {
		// boolean isAllRefund = true;
		List<ProductVo4List> proList = new ArrayList<ProductVo4List>();
		ProductVo4List pros = null;
		int refundNumber = 0;
		int refundingNumber = 0;
		int quantity = 0;
		if (productList != null && productList.size() > 0) {
			for (ProductVo productVo : productList) {
				if (productVo != null) {
					if (OrderTypeEnum.PRESALE.getCode().equals(type)
							&& (!deliveryOption.equals(productVo.getDeliveryOption()))) {
						continue;
					}
					refundNumber += productVo.getRefundNumber();
					refundingNumber += productVo.getRefundingNumber();
					quantity += productVo.getQuantity();
					pros = new ProductVo4List();
					pros.setProductId(productVo.getProductId());
					pros.setProductName(productVo.getProductName());
					pros.setOrgName(productVo.getOrgNames());
				}
				proList.add(pros);
			}
		}
		if (refundNumber + refundingNumber == quantity) {
			res.setIsAllRefund(ALL_REFUND);
		} else {
			res.setIsAllRefund(NOT_ALL_REFUND);
		}
		res.setProductList(proList);
	}

	/**
	 * 
	 * @Title: putPageValueToMap
	 * @Description: 将page信息注入map中
	 * @author chenmingcan@f-road.com.cn
	 * @date 2015年11月24日
	 * @param reMap
	 * @param queryRes
	 */
	private void putPageValueToMap(HashMap<String, Object> reMap,
			QueryOrderByBankManageVoRes queryRes) {
		if (queryRes.getPageVo() != null) {
			reMap.put("pageCount", queryRes.getPageVo().getPageCount());
			reMap.put("totalCount", queryRes.getPageVo().getTotalCount());
			reMap.put("pageNumber", queryRes.getPageVo().getPageNumber());
			reMap.put("lastPageNumber",
					queryRes.getPageVo().getLastPageNumber());
			reMap.put("firstRecordTime",
					queryRes.getPageVo().getFirstRecordTime());
			reMap.put("lastRecordTime",
					queryRes.getPageVo().getLastRecordTime());
		} else {
			reMap.put("pageCount", 0);
			reMap.put("totalCount", 0);
			reMap.put("pageNumber", 1);
			reMap.put("lastPageNumber", 0);
			reMap.put("firstRecordTime", 0);
			reMap.put("lastRecordTime", 0);
		}
	}

	/**
	 * 
	 * setPaymentMethod:支付方式转化
	 *
	 * @author 明灿 2015年8月26日 上午11:51:12
	 * @param res
	 * @param bankOrder
	 *
	 */
	@SuppressWarnings("unused")
	private String parsePaymentMethod(String type) {
		String method = "未支付";
		if (StringUtil.isNotEmpty(type)) {
			if (type.equals(PayMentMethodEnum.cash.getCode())) {
				method = PayMentMethodEnum.cash.getDescription();
			}
			if (type.equals(PayMentMethodEnum.froadPoints.getCode())) {
				method = PayMentMethodEnum.froadPoints.getDescription();
			}
			if (type.equals(PayMentMethodEnum.bankPoints.getCode())) {
				method = PayMentMethodEnum.bankPoints.getDescription();
			}
			if (type.equals(PayMentMethodEnum.froadPointsAndCash.getCode())) {
				method = PayMentMethodEnum.froadPointsAndCash.getDescription();
			}
			if (type.equals(PayMentMethodEnum.bankPointsAndCash.getCode())) {
				method = PayMentMethodEnum.bankPointsAndCash.getDescription();
			}
		}
		return method;
	}

	/**
	 * 
	 * requestArgumentsColt:积分商品列表订单请求参数
	 *
	 * @author 明灿 2015年9月25日 下午6:37:33
	 * @param order
	 * @param req
	 * @throws ParseException
	 *
	 */
	private void requestArgumentsColt(OrderReqOfOptimize order,
			QueryOrderByBankManageVoReq req) throws ParseException {
		PageVo page = new PageVo();
		page.setPageNumber(
				order.getPageNumber() == null ? 1 : order.getPageNumber());
		page.setPageSize(
				order.getPageSize() == null ? 10 : order.getPageSize());
		page.setLastPageNumber(order.getLastPageNumber() == null
				? 0
				: order.getLastPageNumber());
		page.setLastRecordTime(order.getLastRecordTime() == null
				? 0
				: order.getLastRecordTime());
		page.setFirstRecordTime(order.getFirstRecordTime() == null
				? 0
				: order.getFirstRecordTime());
		req.setPage(page);
		req.setClientId(order.getClientId());
		req.setOrgCode(order.getOrgCode());
		req.setType(order.getType());
		req.setStartTime(DateUtil.DateFormat(order.getStartDate()));
		req.setEndTime(DateUtil.DateFormatEnd(order.getEndDate()));
		// 大订单id
		if (StringUtil.isNotBlank(order.getBigOrderId())) {
			req.setOrderId(order.getBigOrderId());
		}
		if (StringUtil.isNotBlank(order.getOrderId())) {
			req.setSubOrderId(order.getOrderId());
		}
		if (StringUtil.isNotBlank(order.getOrderStatus())) {
			// 状态码转化
			OrderTypeUtil.getOrderStatus(order.getOrderStatus(), req);
		}
		if (StringUtil.isNotBlank(order.getMerchantName())) {
			req.setMerchantName(order.getMerchantName());
		}
		// 预售列表参数
		if (StringUtil.isNotBlank(order.getDeliveryOption())) {
			req.setDeliveryOption(order.getDeliveryOption());// 0:配送,1:自提
		}
		if (StringUtil.isNotBlank(order.getMyOrgCode())) {
			req.setMyOrgCode(order.getMyOrgCode());// 当前登录人的所属机构
		}
	}

	/**
	 * 
	 * requestArguments:订单列表请求参数
	 *
	 * @author 明灿 2015年9月1日 下午6:09:27
	 * @param order
	 * @param req
	 * @throws ParseException
	 *
	 */
	private void requestArguments(OrderReqOfOptimize order,
			QueryOrderByBankManageVoReq req) throws ParseException {
		PageVo page = new PageVo();
		page.setPageNumber(
				order.getPageNumber() == null ? 1 : order.getPageNumber());
		page.setPageSize(
				order.getPageSize() == null ? 10 : order.getPageSize());
		page.setLastPageNumber(order.getLastPageNumber() == null
				? 0
				: order.getLastPageNumber());
		page.setLastRecordTime(order.getLastRecordTime() == null
				? 0
				: order.getLastRecordTime());
		page.setFirstRecordTime(order.getFirstRecordTime() == null
				? 0
				: order.getFirstRecordTime());
		req.setPage(page);
		req.setClientId(order.getClientId());
		req.setOrgCode(order.getOrgCode());
		req.setType(order.getType());
		req.setStartTime(DateUtil.DateFormat(order.getStartDate()));
		req.setEndTime(DateUtil.DateFormatEnd(order.getEndDate()));
		if (StringUtil.isNotBlank(order.getOrderId())) {
			req.setSubOrderId(order.getOrderId());
		}
		// 大订单id
		if (StringUtil.isNotBlank(order.getBigOrderId())) {
			req.setOrderId(order.getBigOrderId());
		}
		if (StringUtil.isNotBlank(order.getOrderStatus())) {
			// 状态码转化
			OrderTypeUtil.getOrderStatus(order.getOrderStatus(), req);
		}
		if (StringUtil.isNotBlank(order.getMerchantName())) {
			req.setMerchantName(order.getMerchantName());
		}
		// 预售列表参数
		if (StringUtil.isNotBlank(order.getDeliveryOption())) {
			req.setDeliveryOption(order.getDeliveryOption());// 0;自提,1:配送
		}
		if (StringUtil.isNotBlank(order.getMyOrgCode())) {
			req.setMyOrgCode(order.getMyOrgCode());// 当前登录人的所属机构
		}
	}

	/**
	 * 子订单详情查询
	 * 
	 * @param order
	 * @return
	 * @throws TException
	 * @throws BankException
	 */
	public HashMap<String, Object> orderSubDetail(String clientId,
			String subOrderId, String type) throws TException, BankException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		if (!StringUtil.isNotEmpty(subOrderId)
				&& !StringUtil.isNotEmpty(type)) {
			reMap.put("code", EnumTypes.fail.getCode());
			reMap.put("message", "子订单号或类型不能为空");
			return reMap;
		}

		GetOrderDetailByBankManageVoReq req = new GetOrderDetailByBankManageVoReq();
		requestArgment4Detail(clientId, subOrderId, type, req, null);

		GetOrderDetailByBankManageVoRes getRes = orderQueryService
				.getOrderDetailByBankManage(req);

		if (getRes.resultVo.resultCode.equals(EnumTypes.success.getCode())) {
			OrderVo o = new OrderVo();
			o.setSubOrderId(subOrderId);
			if (getRes.getCreateTime() > 0) {
				o.setCreateTime(
						DateUtil.formatDate(new Date(getRes.getCreateTime()),
								DateUtil.DATE_TIME_FORMAT_01));
			} else {
				o.setCreateTime("");
			}
			o.setOrderState(getRes.getOrderStatus());
			if (StringUtil.isNotEmpty(getRes.getSubTotalMoney() + "")) {
				o.setSubTotalMoney(TargetObjectFormat
						.getDoubleDecimalString(getRes.getSubTotalMoney()));
			}
			if (StringUtil.isNotEmpty(getRes.getPointDiscount() + "")) {
				o.setPointDiscount(TargetObjectFormat
						.getDoubleDecimalNum(getRes.getPointDiscount()));
			}
			o.setBusinessType(getRes.getBusinessType());
			o.setMerchantId(getRes.getMerchantId());
			o.setMerchantName(getRes.getMerchantName());
			o.setOutletId(getRes.getOutletId());
			if (StringUtil.isNotEmpty(getRes.getOutletId())) {
				if (getRes.getOutletId().equals("0")) {
					o.setOutletName("总店");
				} else {
					OutletVo outletVo = outletService
							.getOutletByOutletId(getRes.getOutletId());
					if (outletVo != null && StringUtil
							.isNotEmpty(outletVo.getOutletName())) {
						o.setOutletName(outletVo.getOutletName());
					}
				}
			} else {
				o.setOutletName("");
			}
			o.setRefundState(getRes.getRefundState());// 退款状态
			o.setCompany(getRes.getDeliveryCorpName());// 物流公司
			o.setOrderNumber(getRes.getTrackingNo());// 物流单号
			if (getRes.getShippingTime() > 0) {
				o.setShippingDate(
						DateUtil.formatDate(new Date(getRes.getShippingTime()),
								DateUtil.DATE_TIME_FORMAT_01));// 发货时间
			} else {
				o.setShippingDate("");
			}
			List<BankProductVo> productVoList = new ArrayList<BankProductVo>();
			if (getRes.getProductInfos() != null) {
				for (int i = 0; i < getRes.getProductInfos().size(); i++) {
					BankProductVo p = new BankProductVo();
					p.setProductId(
							getRes.getProductInfos().get(i).getProductId());
					p.setProductName(
							getRes.getProductInfos().get(i).getProductName());
					p.setProductImage(
							getRes.getProductInfos().get(i).getProductImage());
					p.setQuantity(
							getRes.getProductInfos().get(i).getQuantity());
					if (StringUtil.isNotEmpty(
							getRes.getProductInfos().get(i).getMoney() + "")) {// 商品单价金额
						p.setMoney(TargetObjectFormat.getDoubleDecimalString(
								getRes.getProductInfos().get(i).getMoney()));
					}
					if (StringUtil.isNotEmpty(
							getRes.getProductInfos().get(i).getSubTotalMoney()
									+ "")) {// 金额小计
						p.setSubTotalMoney(TargetObjectFormat
								.getDoubleDecimalString(getRes.getProductInfos()
										.get(i).getSubTotalMoney()));
					}
					p.setOutletComment(getRes.getProductInfos().get(i)
							.isSetIsOutletComment());
					p.setTakeCode(
							getRes.getProductInfos().get(i).getTakeCode());
					p.setDeliverState(
							getRes.getProductInfos().get(i).getDeliverState());
					if (StringUtil.isNotEmpty(
							getRes.getProductInfos().get(i).getDeliveryMoney()
									+ "")) {// 金额小计
						p.setDeliveryMoney(TargetObjectFormat
								.getDoubleDecimalString(getRes.getProductInfos()
										.get(i).getDeliveryMoney()));
					}
					p.setSettlementStatus(getRes.getProductInfos().get(i)
							.getSettlementStatus());
					p.setSettlementNumber(getRes.getProductInfos().get(i)
							.getSettlementNumber());
					if (getRes.getProductInfos().get(i).getStartTime() > 0) {
						p.setTakeStartDate(DateUtil.formatDate(
								new Date(getRes.getProductInfos().get(i)
										.getStartTime()),
								DateUtil.DATE_FORMAT_01));// 开始时间
					} else {
						p.setTakeStartDate("");
					}
					if (getRes.getProductInfos().get(i).getEndTime() > 0) {
						p.setTakeEndDate(DateUtil.formatDate(
								new Date(getRes.getProductInfos().get(i)
										.getEndTime()),
								DateUtil.DATE_FORMAT_01));// 结束时间
					} else {
						p.setTakeEndDate("");
					}
					p.setTakeState(getRes.getProductInfos().get(i)
							.getDeliveryOption());
					p.setConsumeStatus(
							getRes.getProductInfos().get(i).getConsumeStatus());
					p.setRefundNumber(
							getRes.getProductInfos().get(i).getRefundNumber());
					p.setSurplusNumber(
							getRes.getProductInfos().get(i).getSurplusNumber());
					// 提货状态
					productVoList.add(p);
				}
			}
			o.setProductList(productVoList);
			reMap.put("order", o);
		} else {
			throw new BankException(getRes.resultVo.resultCode,
					getRes.resultVo.resultDesc);
		}
		return reMap;
	}

	/**
	 * 
	 * getOrderDetail:订单详情优化
	 *
	 * @author 明灿 2015年9月1日 下午5:05:53
	 * @param clientId
	 * @param subOrderId
	 * @param type
	 * @return
	 * @throws TException
	 * @throws BankException
	 *
	 */
	public HashMap<String, Object> getOrderDetail(String clientId,
			String subOrderId, String type, String deliveryOption)
					throws TException, BankException {
		LogCvt.info("----->订单详情请求参数<----- clientId:" + clientId
				+ "; subOrderId: " + subOrderId + "; type: " + type);
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		// 请求Vo
		GetOrderDetailByBankManageVoReq req = new GetOrderDetailByBankManageVoReq();
		// 请求参数
		this.requestArgment4Detail(clientId, subOrderId, type, req, deliveryOption);
		// 调server接口查询
		GetOrderDetailByBankManageVoRes detailRes = orderQueryService
				.getOrderDetailByBankManage(req);
		LogCvt.info("----->订单详情请求返回数据<-----: " + JSON.toJSONString(detailRes));
		if (detailRes.getResultVo().getResultCode()
				.equals(EnumTypes.success.getCode())) {
			OrderVoOfOptimize oo = new OrderVoOfOptimize();
			oo.setOrderId(subOrderId);
			// 封装部分返回值
			this.setSomeValueToOrderVo(detailRes, oo, type, deliveryOption);
			// 订单的商品集合
			List<ProductInfoVo> productInfos = detailRes.getProductInfos();
			LogCvt.info("----->>订单详情中的商品集合数量<<-----"
					+ JSON.toJSONString(productInfos));
			if (productInfos != null && productInfos.size() > 0) {
				LogCvt.info("=====订单详情的商品集合数量=====:" + productInfos.size());
				this.addProductToList(productInfos, oo, type, deliveryOption);
			}
			resMap.put("order", oo);
		}
		return resMap;
	}

	/**
	 * 精品商城订单详情查询
	 * 说明   description of the class
	 * 创建日期  2016年2月26日  上午11:20:41
	 * 作者  artPing
	 * 参数  @param clientId
	 * 参数  @param subOrderId
	 * 参数  @param type
	 * 参数  @param deliveryOption
	 * 参数  @return
	 * 参数  @throws TException
	 * 参数  @throws BankException
	 */
	public HashMap<String, Object> getBoutiqueOrderDetail(String clientId,
			String subOrderId, String type, String deliveryOption)
					throws TException, BankException {
		LogCvt.info("银行管理平台-获取精品商城订单详情-请求参数：{clientId:" + clientId+ ", subOrderId: " + subOrderId + ", type: " + type+",deliveryOption:"+deliveryOption+"}");
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		GetOrderDetailByBankManageVoReq req = new GetOrderDetailByBankManageVoReq();
		
		requestArgment4Detail(clientId, subOrderId, type, req, deliveryOption);// 封装请求参数
		// 调server接口查询
		GetOrderDetailByBankManageVoRes detailRes = orderQueryService.getOrderDetailByBankManage(req);
		LogCvt.info("银行管理平台-获取精品商城订单详情-获取订单模块响应数据：" + JSON.toJSONString(detailRes));
		if (detailRes.getResultVo().getResultCode().equals(EnumTypes.success.getCode())) {
			OrderVoOfOptimize oo = new OrderVoOfOptimize();
			oo.setOrderId(subOrderId);
			
			setBoutiqueValueToOrderVo(detailRes, oo, type, deliveryOption);// 封装部分返回值
			
			List<ProductInfoVo> productInfos = detailRes.getProductInfos();// 订单的商品集合

			if (productInfos != null && productInfos.size() > 0) {
				addProductToListByBoutique(productInfos, oo, type, deliveryOption);// 封装部分返回值
			}
			//物流详细信息查询
			if(StringUtil.isNotEmpty(detailRes.getTrackingNo())){
				DeliveryWayBillVo devo = new DeliveryWayBillVo();
				devo.setShippingId(detailRes.getTrackingNo());//物流单号
				DeliveryWayBillVo devs = null;
				try {
					LogCvt.info("银行管理平台-精品商城订单详情-获取物流信息请求："+JSON.toJSONString(devo));
					devs = deliveryWayBillService.getDeliveryWayBillById(devo);
					LogCvt.info("银行管理平台-精品商城订单详情-获取物流信息响应："+JSON.toJSONString(devs));
				} catch (Exception e) {
					LogCvt.error("银行管理平台-精品商城订单详情-获取物流信息：异常！",e);
				}
				
				if (devo != null && devs!=null) {
					oo.setShippingState(devs.getShippingState());
					oo.setArriveTime(devs.getArriveTime());
					oo.setShippingTime(devs.getShippingTime());
					oo.setMessage(devs.getMessage());
					String message = devs.getMessage();
					LogCvt.info("物流信息message："+message);
					if(StringUtil.isNotBlank(message)){
						List<Map<String,String>> msgList = new ArrayList<Map<String,String>>();
						String[] msg = message.split("##");
						for(String strs : msg){
							Map<String,String> msgVo = new HashMap<String,String>();
							String[] str = strs.split("@@");
							if (str != null && str.length > 1) {
								msgVo.put(str[0], str[1]);//时间：事件
							}
							msgList.add(msgVo);
						} 
						oo.setMsgList(msgList);
					}
					oo.setShippingCreateTime(devs.getCreateTime());
					oo.setUpdateTime(devs.getUpdateTime());
					oo.setShippingCorpCode(devs.getShippingCorpCode());
					oo.setShippingCorp(devs.getShippingCorp());
					oo.setShippingId(devs.getShippingId());
					oo.setStatus(devs.getStatus());
					oo.setTrackingNo(detailRes.getTrackingNo());
				}
			}
			resMap.put("order", oo);
		}
		return resMap;
	}
	
	/**
	 * 
	 * setSomeValueToOrderVo:封装订单的一部分详情
	 *
	 * @author 明灿 2015年8月31日 上午11:17:18
	 * @param detailRes
	 * @param oo
	 * @throws TException
	 *
	 */
	private void setSomeValueToOrderVo(
			GetOrderDetailByBankManageVoRes detailRes, OrderVoOfOptimize oo,
			String type, String deliveryOption) throws TException {
		DeliverInfoVo deliverInfoVo = detailRes.getDeliverInfoVo();
		oo.setTakeTotalNumber(detailRes.getTakeNumber());// 总提货数量
		if (deliverInfoVo != null) {
			oo.setPhone(deliverInfoVo.getPhone());// 电话
			oo.setTaker(deliverInfoVo.getConsignee());// 提货人
			oo.setAddress(deliverInfoVo.getAddress());// 地址
		}
		oo.setBusinessType(detailRes.getBusinessType());// 交易类型
		oo.setCarriage(detailRes.getDeliveryMoney());// 运费
		oo.setCloseReason(detailRes.getCloseReason());// 关闭原因
		oo.setCreateTime(detailRes.getCreateTime());// 下单时间
		oo.setDeliveryOption(detailRes.getDeliveryOption());// 配送方式
		oo.setOrderStatus(detailRes.getOrderStatus());// 订单状态
		if (OrderTypeEnum.PRESALE.getCode().equals(type)) {
			oo.setOrgNames(OrgCodeUtil.getOrgNames(detailRes.getOrgNames(), type));// 所属机构
		} else {
			oo.setOrgNames(OrgCodeUtil.splitOrgNames(detailRes.getOrgNames()));// 所属机构
		}
		oo.setPaymentTime(detailRes.getPaymentTime());// 支付时间
		oo.setRealityMoney(detailRes.getSubTotalMoney());// 实付款
		oo.setRefundApplyTime(detailRes.getRefundApplyTime());// 退款申请时间
		oo.setRefundFinishTime(detailRes.getRefundSuccessTime());// 退款完成时间
		oo.setSettlementStatus(detailRes.getSettleState());// 结算状态
		oo.setShippingDate(detailRes.getShippingTime());
		oo.setSignforTime(detailRes.getConfirmReceiveTime());// 收货时间
		if (OrderTypeEnum.PRESALE.getCode().equals(type)) {
			if (deliveryOption.equals(DistributionType.HOME.getCode())) {
				oo.setSumMoney(detailRes.getSubTotalMoneyPeisong());// 配送金额
			} else {
				oo.setSumMoney(detailRes.getSubTotalMoneyZiti());// 自提金额
			}
		} else {
			oo.setSumMoney(detailRes.getSubTotalMoney());// 金额
		}
		oo.setTakeAddress(detailRes.getOrgNames());// 提货网点
		if (StringUtil.isNotBlank(detailRes.getTakeCode())) {
			String takeCode = StringUtils.overlay(detailRes.getTakeCode(),
					"****", 4, 8);
			oo.setTakeCode(takeCode);// 提货码
		}
		oo.setTakeTime(detailRes.getTakeTime());// 验码时间
		oo.setMerchantName(detailRes.getMerchantName());// 所属商户
		oo.setTicketTime(detailRes.getTicketTime());// 提货时间
		oo.setRefundStatus(detailRes.getRefundState());// 退款转态
		oo.setSubOrderId(detailRes.getSubOrderId());// 子订单编号
		oo.setOrderId(detailRes.getOrderId());// 订单编号
		LogCvt.info("=========操作员id========" + detailRes.getMerchantUserId());
		oo.setMerchantUserName(
				getMerchantNameByID(detailRes.getMerchantUserId()));// 操作员
		// 线下积分商品直接获取
		if (OrderTypeEnum.OFFLINE.getCode().equals(type)) {
			oo.setMerchantUserName(detailRes.getMerchantUserName());
		}
		oo.setPointDiscount(detailRes.getPointDiscount()); // 积分抵扣
		/**
		 * 返回优惠信息
		 */
		oo.setTotalCash(detailRes.getTotalCash()); // 子订单总实付金额
		oo.setTotalCutMoney(detailRes.getTotalCutMoney()); // 子订单总优惠金额
	}
	
	/**
	 * 对对象部分赋值
	 * 说明   description of the class
	 * 创建日期  2016年2月26日  下午5:31:12
	 * 作者  artPing
	 * 参数  @param detailRes
	 * 参数  @param oo
	 * 参数  @param type
	 * 参数  @param deliveryOption
	 * 参数  @throws TException
	 */
	private void setBoutiqueValueToOrderVo(
			GetOrderDetailByBankManageVoRes detailRes, OrderVoOfOptimize oo,
			String type, String deliveryOption) throws TException {
		DeliverInfoVo deliverInfoVo = detailRes.getDeliverInfoVo();
		oo.setTakeTotalNumber(detailRes.getTakeNumber());// 总提货数量
		oo.setPhone(detailRes.getPhone());
		if (deliverInfoVo != null) {
			oo.setContactPhone(deliverInfoVo.getPhone());// 联系电话
			oo.setTaker(deliverInfoVo.getConsignee());// 提货人
			oo.setAddress(deliverInfoVo.getAddress());// 地址
			
		}
		oo.setBusinessType(detailRes.getBusinessType());// 交易类型
		oo.setCarriage(detailRes.getDeliveryMoney());// 运费
		oo.setCloseReason(detailRes.getCloseReason());// 关闭原因
		oo.setCreateTime(detailRes.getCreateTime());// 下单时间
		oo.setDeliveryOption(detailRes.getDeliveryOption());// 配送方式
		oo.setOrderStatus(detailRes.getOrderStatus());// 订单状态
//		if (OrderTypeEnum.BOUTIQUE.getCode().equals(type)) {
//			oo.setOrgNames(OrgCodeUtil.getOrgNames(detailRes.getOrgNames(), type));// 所属机构
//		} else {
		oo.setOrgNames(OrgCodeUtil.splitOrgNames(detailRes.getOrgNames()));// 所属机构
//		}
		oo.setPaymentTime(detailRes.getPaymentTime());// 支付时间
		oo.setRealityMoney(detailRes.getSubTotalMoney());// 实付款
		oo.setRefundApplyTime(detailRes.getRefundApplyTime());// 退款申请时间
		oo.setRefundFinishTime(detailRes.getRefundSuccessTime());// 退款完成时间
		oo.setSettlementStatus(detailRes.getSettleState());// 结算状态
		oo.setShippingDate(detailRes.getShippingTime());
		oo.setSignforTime(detailRes.getConfirmReceiveTime());// 收货时间
		oo.setSumMoney(detailRes.getSubTotalMoney());// 金额
		oo.setMerchantName(detailRes.getMerchantName());// 所属商户
		oo.setRefundStatus(detailRes.getRefundState());// 退款转态
		oo.setSubOrderId(detailRes.getSubOrderId());// 子订单编号
		oo.setOrderId(detailRes.getOrderId());// 订单编号
		oo.setMerchantUserName(detailRes.getMerchantUserName());// 操作员
		oo.setPointDiscount(detailRes.getPointDiscount()); // 积分抵扣
		oo.setTotalCash(detailRes.getTotalCash()); // 子订单总实付金额
		oo.setTotalCutMoney(detailRes.getTotalCutMoney()); // 子订单总优惠金额
		oo.setMemberName(detailRes.getMemberName());
		oo.setDeliveryState(detailRes.getDeliveryState());
		
	}
	
	/**
	 * 
	 * addProductToList:
	 *
	 * @author 明灿 2015年8月31日 上午11:15:17
	 * @param productInfos
	 * @throws TException
	 *
	 */
	private void addProductToList(List<ProductInfoVo> productInfos,
			OrderVoOfOptimize oo, String type, String deliveryOption)
					throws TException {
		int takeNumber = 0;
		int quantity = 0;
		int refundNumber = 0;
		int refundingNumber = 0;
		int refundIndex = 0;
		int refundingIndex = 0;
		List<BankOrderProductVo> proList = new ArrayList<BankOrderProductVo>();
		BankOrderProductVo pro4Order = null;
		for (ProductInfoVo productInfoVo : productInfos) {
			// deliveryOption 预售的配送方式
			if (StringUtil.isNotBlank(deliveryOption)) {
				// 预售详情
				if (!deliveryOption.equals(productInfoVo.getDeliveryOption())) {
					continue;
				}
				pro4Order = new BankOrderProductVo();
				pro4Order.setCheckCodeTime(productInfoVo.getCheckCodeTime());// 验码时间
				String refundState = productInfoVo.getRefundState();
				pro4Order.setRefundStatus(refundState);// 退款状态
				// 团购是否全单退款
				if (OrderTypeEnum.GROUP.getCode().equals(type)) {
					// 退款完成
					if (RefundStateEnum.REFUND_SUCCESS.getCode()
							.equals(refundState)) {
						++refundIndex;
					}
					// 退款中
					if (RefundStateEnum.REFUND_PROCESSING.getCode()
							.equals(refundState)) {
						++refundingIndex;
					}
				}
				refundNumber += productInfoVo.getRefundNumber();// 退款总数
				takeNumber += productInfoVo.getTakeNumber();// 提货总数
				quantity += productInfoVo.getQuantity();// 购买总数
				refundingNumber += productInfoVo.getRefundingNumber();// 退款中的总数
				// 封装部分返回值
				this.setSomeValueToOrderDetail(pro4Order, productInfoVo);
				proList.add(pro4Order);

			} else {
				pro4Order = new BankOrderProductVo();
				pro4Order.setCheckCodeTime(productInfoVo.getCheckCodeTime());// 验码时间
				String refundState = productInfoVo.getRefundState();
				pro4Order.setRefundStatus(refundState);// 退款状态
				// 团购是否全单退款
				if (OrderTypeEnum.GROUP.getCode().equals(type)) {
					// 退款完成
					if (RefundStateEnum.REFUND_SUCCESS.getCode()
							.equals(refundState)) {
						++refundIndex;
					}
					// 退款中
					if (RefundStateEnum.REFUND_PROCESSING.getCode()
							.equals(refundState)) {
						++refundingIndex;
					}

				}
				refundNumber += productInfoVo.getRefundNumber();// 退款总数
				takeNumber += productInfoVo.getTakeNumber();// 提货总数
				quantity += productInfoVo.getQuantity();// 购买总数
				refundingNumber += productInfoVo.getRefundingNumber();// 退款中的总数
				// 封装部分返回值
				this.setSomeValueToOrderDetail(pro4Order, productInfoVo);
				proList.add(pro4Order);
			}
			// 预售等其他情况
			if ((refundNumber + refundingNumber) == quantity) {
				oo.setIsAllRefund(ALL_REFUND);
			} else {
				oo.setIsAllRefund(NOT_ALL_REFUND);
			}
			// 团购的情况
			if (OrderTypeEnum.GROUP.getCode().equals(type)) {
				if ((refundIndex + refundingIndex == productInfos.size())) {
					oo.setIsAllRefund(ALL_REFUND);
				} else {
					oo.setIsAllRefund(NOT_ALL_REFUND);
				}
			}
			long temp = (takeNumber / quantity) * 10;
			oo.setBuyTotalPoint(quantity - refundingNumber - refundNumber);// 总购买数量-退款数量
			// 如果是团购
			if (OrderTypeEnum.GROUP.getCode().equals(type)) {
				LogCvt.info("======>>refundingNumber=" + refundingNumber
						+ "*********refundNumber=" + refundNumber + "<<======");
				oo.setBuyTotalPoint(
						productInfos.size() - refundIndex - refundingIndex);// 总购买数量-退款数量
			}
			oo.setTakePoint(Math.round(temp));// 提货百分比
			// 退款完成时间,只要有退款中就将退款完成时间设置为0
			if (refundingIndex > 0) {
				oo.setRefundFinishTime(0L);
			}
			oo.setProductList(proList);
		}
	}

	/**
	 * 
	 * 说明   description of the class
	 * 创建日期  2016年2月29日  上午9:50:17
	 * 作者  artPing
	 * 参数  @param productInfos
	 * 参数  @param oo
	 * 参数  @param type
	 * 参数  @param deliveryOption
	 * 参数  @throws TException
	 */
	private void addProductToListByBoutique(List<ProductInfoVo> productInfos,
			OrderVoOfOptimize oo, String type, String deliveryOption)
					throws TException {
	
		List<BankOrderProductVo> proList = new ArrayList<BankOrderProductVo>();
		BankOrderProductVo pro4Order = null;
		for (ProductInfoVo productInfoVo : productInfos) {
			pro4Order = new BankOrderProductVo();
			String refundState = productInfoVo.getRefundState();
			pro4Order.setRefundStatus(refundState);// 退款状态
			pro4Order.setRefindNumber(productInfoVo.getRefundingNumber());//退款中
			pro4Order.setRefundNumber(productInfoVo.getRefundNumber());//退款完成数量
			pro4Order.setQuantity(productInfoVo.getQuantity());//订单商品数量
			pro4Order.setVipQuantity(productInfoVo.getVipQuantity());
			pro4Order.setPrice(productInfoVo.getMoney());// 商品单价
			pro4Order.setVipMoney(productInfoVo.getVipMoney());//vip价格
			pro4Order.setDeliveryOption(productInfoVo.getDeliveryOption());//发货状态
			pro4Order.setProductShippingStatus(productInfoVo.getDeliverState());
			pro4Order.setProductId(productInfoVo.getProductId());// 商品id
			pro4Order.setProductName(productInfoVo.getProductName());// 商品名称
			pro4Order.setRefundReason(productInfoVo.getRefundReason());// 退款原因
			pro4Order.setSumPoint(productInfoVo.getSubTotalMoney());// 商品积分
			pro4Order.setUsefulLife(productInfoVo.getEndTime());// 结束时间
			pro4Order.setSumMoney(productInfoVo.getSubTotalMoney()); // 总金额
			pro4Order.setRefundApplyTime(productInfoVo.getRefundApplyTime());// 退款申请时间
			// 如果是退款中,将退款完成时间设置为0
			if (RefundStateEnum.REFUND_PROCESSING.getCode().equals(productInfoVo.getRefundState())) {
				pro4Order.setRefundSuccessTime(0L);
			} else {
				pro4Order.setRefundSuccessTime(productInfoVo.getRefundSuccessTime());// 退款成功时间
			}
			proList.add(pro4Order);
		}
		oo.setProductList(proList);
	}
	
	/**
	 * 
	 * setSomeValueToOrderDetail:订单详情封装部分返回值
	 *
	 * @author 明灿 2015年9月25日 下午6:22:36
	 * @param pro4Order
	 * @param productInfoVo
	 * @throws TException
	 *
	 */
	private void setSomeValueToOrderDetail(BankOrderProductVo pro4Order,
			ProductInfoVo productInfoVo) throws TException {
		pro4Order.setTakeStatus(productInfoVo.getDeliverState());// 消费状态(名优)
		pro4Order.setConsumeStatus(productInfoVo.getConsumeStatus());// 消费状态
		LogCvt.info(
				"=========操作员id========" + productInfoVo.getMerchantUserId());
		pro4Order.setMerchantUserName(
				getMerchantNameByID(productInfoVo.getMerchantUserId()));// 操作员
		pro4Order.setOutletName(productInfoVo.getOutletName());// 门店名称
		pro4Order.setPoint(productInfoVo.getMoney());// 积分
		pro4Order.setPrice(productInfoVo.getMoney());// 单价
		pro4Order.setDeliveryOption(productInfoVo.getDeliveryOption());
		pro4Order.setProductId(productInfoVo.getProductId());// 商品id
		pro4Order.setProductName(productInfoVo.getProductName());// 商品名称
		pro4Order.setQuantity(productInfoVo.getQuantity());// 数量
		pro4Order.setRefundReason(productInfoVo.getRefundReason());// 退款原因
		pro4Order.setSettlementStatus(productInfoVo.getSettlementStatus());// 结算状态
		pro4Order.setSumPoint(productInfoVo.getSubTotalMoney());// 商品积分
		pro4Order.setTakeNumber(productInfoVo.getTakeNumber());// 已提货数量
		pro4Order.setRefundNumber(productInfoVo.getRefundNumber());// 退货数量
		pro4Order.setTakeTime(productInfoVo.getTakeTime());// 提货时间
		pro4Order.setTicket(
				StringUtils.overlay(productInfoVo.getTakeCode(), "****", 4, 8));// 提货码
		pro4Order.setUserfulLifeStart(productInfoVo.getStartTime());// 开始时间
		pro4Order.setUsefulLife(productInfoVo.getEndTime());// 结束时间
		pro4Order.setSumMoney(productInfoVo.getSubTotalMoney()); // 总金额
		pro4Order.setRefundApplyTime(productInfoVo.getRefundApplyTime());// 退款申请时间
		// 如果是退款中,将退款完成时间设置为0
		if (RefundStateEnum.REFUND_PROCESSING.getCode()
				.equals(productInfoVo.getRefundState())) {
			pro4Order.setRefundSuccessTime(0L);
		} else {
			pro4Order
					.setRefundSuccessTime(productInfoVo.getRefundSuccessTime());// 退款成功时间
		}
	}

	/**
	 * 
	 * getMerchantNameByID:通过id调用后台查询name并且注入name值
	 *
	 * @author 明灿 2015年8月31日 上午11:11:25
	 * @param pro4Order
	 * @param productInfoVo
	 * @throws TException
	 *
	 */
	private String getMerchantNameByID(long id) throws TException {
		String merchantName = "";
		MerchantUserVo merchantUserVo = merchantUserService
				.getMerchantUserById(id);
		LogCvt.info(
				"=====server返回操作员详情=====:" + JSON.toJSONString(merchantUserVo));
		if (merchantUserVo != null) {
			merchantName = merchantUserVo.getUsername();
		}
		return merchantName;
	}

	/**
	 * 
	 * requestArgment4GroupDetail:详情请求参数封装
	 *
	 * @author 明灿 2015年8月28日 下午4:43:05
	 * @param clientId
	 * @param subOrderId
	 * @param type
	 * @param req
	 *
	 */
	private void requestArgment4Detail(String clientId, String subOrderId,
			String type, GetOrderDetailByBankManageVoReq req,
			String deliveryOption) {
		req.setClientId(clientId);
		req.setSubOrderId(subOrderId);
		req.setType(type);
		if (StringUtil.isNotBlank(deliveryOption)) {
			req.setDeliveryOption(deliveryOption);
		}
	}

	/**
	 * 大订单详情查询
	 * 
	 * @param order
	 * @return
	 * @throws TException
	 * @throws BankException
	 */
	public HashMap<String, Object> orderDetail(String clientId, String orderId)
			throws TException, BankException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		if (!StringUtil.isNotEmpty(orderId)) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "订单号不能为空");
			return reMap;
		}

		OrderDetailRes getRes = orderQueryService.getOrderDetailById(orderId,
				clientId);

		if (getRes.resultVo.resultCode.equals(EnumTypes.success.getCode())) {
			OrderDetailVo detailVo = new OrderDetailVo();
			detailVo.setOrderId(orderId);
			if (getRes.getCreateTime() > 0) {
				detailVo.setCreateTime(
						DateUtil.formatDate(new Date(getRes.getCreateTime()),
								DateUtil.DATE_TIME_FORMAT_01));
			}
			detailVo.setOrderState(getRes.getOrderStatus());
			if (StringUtil.isNotEmpty(getRes.getTotalMoney() + "")) {
				detailVo.setSubTotalMoney(TargetObjectFormat
						.getDoubleDecimalString(getRes.getTotalMoney()));
			}
			if (StringUtil.isNotEmpty(getRes.getPointDiscount() + "")) {
				detailVo.setPointDiscount(TargetObjectFormat
						.getDoubleDecimalNum(getRes.getPointDiscount()));
			}

			List<SubOrderVo> subOrgVoList = new ArrayList<SubOrderVo>();
			if (getRes.getSubOrderVo() != null
					&& getRes.getSubOrderVo().size() > 0) {
				for (com.froad.thrift.vo.order.SubOrderVo subOrderVo : getRes
						.getSubOrderVo()) {
					SubOrderVo subVo = new SubOrderVo();
					subVo.setDeliveryCorpId(subOrderVo.getDeliveryCorpId());
					subVo.setDeliveryCorpName(subOrderVo.getDeliveryCorpName());
					subVo.setDeliveryState(subOrderVo.getDeliveryState());
					subVo.setMerchantId(subOrderVo.getMerchantId());
					subVo.setMerchantName(subOrderVo.getMerchantName());
					subVo.setSubOrderId(subOrderVo.getSubOrderId());
					subVo.setRefundState(subOrderVo.getRefundState());
					if (StringUtil
							.isNotEmpty(subOrderVo.getTotalPrice() + "")) {
						subVo.setTotalPrice(
								TargetObjectFormat.getDoubleDecimalString(
										subOrderVo.getTotalPrice()));
					}
					if (subOrderVo.getShippingTime() > 0) {
						subVo.setShippingTime(DateUtil.formatDate(
								new Date(subOrderVo.getShippingTime()),
								DateUtil.DATE_TIME_FORMAT_01));
					} else {
						subVo.setShippingTime("");
					}
					subVo.setTrackingNo(subOrderVo.getTrackingNo());
					subVo.setType(subOrderVo.getType());

					List<SubProductVo> subProductVoList = new ArrayList<SubProductVo>();
					if (subOrderVo.getProducts() != null
							&& subOrderVo.getProducts().size() > 0) {
						for (com.froad.thrift.vo.order.ProductVo productVo : subOrderVo
								.getProducts()) {
							SubProductVo subProductVo = new SubProductVo();
							if (StringUtil.isNotEmpty(
									productVo.getDeliveryMoney() + "")) {
								subProductVo.setDeliverMoney(TargetObjectFormat
										.getDoubleDecimalString(
												productVo.getDeliveryMoney()));
							}
							if (StringUtil
									.isNotEmpty(productVo.getMoney() + "")) {
								subProductVo.setMoney(TargetObjectFormat
										.getDoubleDecimalString(
												productVo.getMoney()));
							}
							if (StringUtil.isNotEmpty(
									productVo.getSubTotalMoney() + "")) {
								subProductVo.setSubTotalMoney(TargetObjectFormat
										.getDoubleDecimalString(
												productVo.getSubTotalMoney()));
							}
							if (StringUtil
									.isNotEmpty(productVo.getPoints() + "")) {
								subProductVo.setPoints(
										TargetObjectFormat.getDoubleDecimalNum(
												productVo.getPoints()));
							}
							subProductVo.setProductId(productVo.getProductId());
							subProductVo
									.setProductName(productVo.getProductName());
							subProductVo.setQuantity(productVo.getQuantity());
							if (productVo.getEndTime() > 0) {
								subProductVo.setTakeEndDate(DateUtil.formatDate(
										new Date(productVo.getEndTime()),
										DateUtil.DATE_TIME_FORMAT_01));
							} else {
								subProductVo.setTakeEndDate("");
							}
							if (productVo.getEndTime() > 0) {
								subProductVo
										.setTakeStartDate(DateUtil.formatDate(
												new Date(productVo
														.getStartTime()),
												DateUtil.DATE_TIME_FORMAT_01));
							} else {
								subProductVo.setTakeEndDate("");
							}
							if (StringUtil
									.isNotEmpty(productVo.getVipMoney() + "")) {
								subProductVo.setVipMoney(TargetObjectFormat
										.getDoubleDecimalString(
												productVo.getVipMoney()));
							}
							subProductVo
									.setVipQuantity(productVo.getVipQuantity());
							subProductVo.setTakeState(
									productVo.getDeliveryOption());
							subProductVo.setDeliveryState(
									productVo.getDeliveryState());
							subProductVo.setConsumeStatus(
									productVo.getConsumeStatus() + "");
							subProductVo.setRefundNumber(
									productVo.getRefundNumber() + "");
							subProductVoList.add(subProductVo);
						}
					}
					subVo.setProductList(subProductVoList);
					subOrgVoList.add(subVo);
				}
			}
			detailVo.setSubOrderVoList(subOrgVoList);
			reMap.put("orderDetailVo", detailVo);
		} else {
			throw new BankException(getRes.resultVo.resultCode,
					getRes.resultVo.resultDesc);
		}
		return reMap;
	}

	/**
	 * 获取物流列表
	 * 
	 * @param req
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> getDeliveryCompany(DeliveryCompanyReq req)
			throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		DeliveryCorpVo deliveryCorp = new DeliveryCorpVo();
		if (StringUtil.isNotEmpty(req.getName())) { // 物流编号
			deliveryCorp.setOrderValue(req.getOrderNumber());
		}
		List<DeliveryCorpVo> deliveryCorpList = deliveryCorpService
				.getDeliveryCorp(deliveryCorp);
		reMap.put("deliveryCompanyList", deliveryCorpList);

		return reMap;
	}

	/**
	 * 
	 * getGroupExport:团购订单下载优化
	 *
	 * @author 明灿 2015年8月26日 下午3:49:07
	 * @param order
	 * @return
	 *
	 */
	public Map<String, Object> getGroupExport(OrderReqOfOptimize order)
			throws Exception {
		// 用户统计详情列表导出
		LogCvt.info("----->团购订单列表下载请求参数<-----:" + JSON.toJSONString(order));
		int exportPageSize = 500;
		Integer totalPage = 0;
		int index = 0;
		// 导出的数据
		List<List<String>> data = null;
		Map<String, Object> map = new HashMap<String, Object>();
		// 封装查询条件
		QueryOrderByBankManageVoReq req = new QueryOrderByBankManageVoReq();
		requestArguments(order, req);
		// 调用server数据
		QueryOrderByBankManageVoRes queryRes = orderQueryService
				.queryOrderByBankManage(req);
		LogCvt.info("----->团购订单列表下载返回数据<-----:" + JSON.toJSONString(queryRes));
		if (queryRes != null) {
			data = new ArrayList<List<String>>();
			pakageValueToList(order, queryRes, data, index);
			// 计算调用的次数
			int totalCount = queryRes.getPageVo().getTotalCount();
			totalPage = (totalCount - 1) / exportPageSize + 1;
			// 循环调用server
			for (int i = 2; i < totalPage + 1; i++) {
				// 封装查询条件
				requestArguments(order, req);
				// 调用server数据
				QueryOrderByBankManageVoRes queryResAgain = orderQueryService
						.queryOrderByBankManage(req);
				if (queryResAgain != null) {
					pakageValueToList(order, queryResAgain, data, index);
				}
			}
			List<String> header = getGroupTitle();
			HSSFWorkbook hssfWorkbook = ExcelUtil.generate(header, data,
					"团购订单");
			map.put("workBook", hssfWorkbook);
		}
		return map;
	}

	/**
	 * 
	 * pakageValueToList:将数据遍历到list中
	 *
	 * @author 明灿 2015年8月26日 下午5:05:51
	 * @param order
	 * @param queryResAgain
	 * @param data
	 * @param index
	 *
	 */
	private void pakageValueToList(OrderReqOfOptimize order,
			QueryOrderByBankManageVoRes queryRes, List<List<String>> data,
			int index) {
		List<String> list;
		// 封装数据
		List<QueryOrderByBankManageVo> ordervo = queryRes.getOrdervo();
		if (ordervo != null && ordervo.size() > 0) {
			// 遍历每一个子订单
			for (QueryOrderByBankManageVo orderRes : ordervo) {
				// 遍历每一条子订单中的商品集合
				for (int i = 0; i < orderRes.getProductList().size(); i++) {
					++index;
					list = new ArrayList<String>();
					addValueToList(list, orderRes, index, i);
					data.add(list);
				}
			}
		}
		PageVo pageVo = queryRes.getPageVo();
		// 注入下一次的查询参数
		setQueryValue(order, pageVo);
	}

	/**
	 * 
	 * setQueryValue:封装分页请求的参数
	 *
	 * @author 明灿 2015年8月26日 下午5:11:35
	 * @param order
	 * @param pageVo
	 *
	 */
	private void setQueryValue(OrderReqOfOptimize order, PageVo pageVo) {
		order.setLastPageNumber(pageVo.getLastPageNumber());
		order.setLastRecordTime(pageVo.getLastRecordTime());
		order.setFirstRecordTime(pageVo.getFirstRecordTime());
	}

	/**
	 * 
	 * addValueToList:将数据添加到list中
	 *
	 * @author 明灿 2015年8月26日 下午5:10:56
	 * @param list
	 * @param orderRes
	 * @param index
	 *
	 */
	private void addValueToList(List<String> list,
			QueryOrderByBankManageVo orderRes, int index, int i) {
		list.add(String.valueOf(index));// 序号
		list.add(orderRes.getSubOrderId());// 订单编号
		list.add(convertPayment(orderRes.getPaymentMethod()));// 支付方式
		list.add(OrderTypeUtil.getOrderStatus(orderRes.getOrderStatus()));// 订单状态
		list.add(NumberUtil4Bank.amountToString(orderRes.getSubTotalMoney()));// 订单金额
		list.add(orderRes.getOrgNames());// 所属机构
		list.add(orderRes.getMerchantName());// 商户名称
		list.add(orderRes.getProductList().get(i).getProductName());// 商品名称
		list.add(
				String.valueOf(orderRes.getProductList().get(i).getQuantity()));// 销售数量
		list.add(DateUtil.dateFormart4Report(orderRes.getCreateTime()));// 结算日期
		// 结算状态
		convertGroupOrderStatus(list, orderRes.getSettlementStatus());
	}

	/**
	 * 
	 * convertGroupOrderStatus:结算状态转化
	 *
	 * @author 明灿 2015年8月26日 下午6:38:06
	 * @param list
	 * @param state
	 *
	 */
	private void convertGroupOrderStatus(List<String> list, String state) {
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

	/**
	 * 
	 * convertPayment:支付方式对应名称
	 *
	 * @author 明灿 2015年8月26日 下午5:29:01
	 * @param paymentMethod
	 * @return
	 *
	 */
	private String convertPayment(String paymentMethod) {
		String payment = "";
		if (paymentMethod.equals(PayMentMethodEnum.cash.getCode())) {
			payment = PayMentMethodEnum.cash.getDescription();
		}
		if (paymentMethod.equals(PayMentMethodEnum.froadPoints.getCode())) {
			payment = PayMentMethodEnum.froadPoints.getDescription();
		}
		if (paymentMethod.equals(PayMentMethodEnum.bankPoints.getCode())) {
			payment = PayMentMethodEnum.bankPoints.getDescription();
		}
		if (paymentMethod
				.equals(PayMentMethodEnum.froadPointsAndCash.getCode())) {
			payment = PayMentMethodEnum.froadPointsAndCash.getDescription();
		}
		if (paymentMethod
				.equals(PayMentMethodEnum.bankPointsAndCash.getCode())) {
			payment = PayMentMethodEnum.bankPointsAndCash.getDescription();
		}
		return payment;
	}

	/**
	 * 
	 * getGroupTitle:团购订单的标题头
	 *
	 * @author 明灿 2015年8月26日 下午4:38:52
	 * @return
	 *
	 */
	private List<String> getGroupTitle() {
		List<String> list = new ArrayList<String>();
		list.add("序号");
		list.add("订单编号");
		list.add("支付方式");
		list.add("订单状态");
		list.add("订单金额");
		list.add("所属机构");
		list.add("商户");
		list.add("商品");
		list.add("销售数量");
		list.add("下单时间");
		list.add("结算状态");
		return list;
	}

	/**
	 * 
	 * getCashierOrderDetail:面对面订单详情优化
	 *
	 * @author 明灿 2015年9月2日 下午4:37:07
	 * @param clientId
	 * @param subOrderId
	 * @param type
	 * @return
	 * @throws Exception
	 *
	 */
	public Map<String, Object> getCashierOrderDetail(String clientId,
			String subOrderId, String type) throws Exception {
		LogCvt.info("=====>面对面订单详情请求参数<===== clientid:" + clientId + ", id:"
				+ subOrderId + ", type:" + type);
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		GetOrderDetailByBankManageVoReq req = new GetOrderDetailByBankManageVoReq();
		requestArgment4Detail(clientId, subOrderId, type, req, null);
		GetOrderDetailByBankManageVoRes getRes = orderQueryService
				.getOrderDetailByBankManage(req);
		LogCvt.info("=====>面对面订单详情返回参数<=====" + JSON.toJSONString(getRes));
		if (getRes.resultVo.resultCode.equals(EnumTypes.success.getCode())) {
			OrderVo4Face2Face off = new OrderVo4Face2Face();
			// 拷贝属性
			off.setOrderId(subOrderId);
			this.copyValueToFace2FaceOrderVo(getRes, off, type);
			reMap.put("order", off);
		} else {
			reMap.put(ResultEnum.FAIL.getCode(),
					getRes.getResultVo().getResultCode());
			reMap.put(ResultEnum.MESSAGE.getCode(),
					getRes.getResultVo().getResultDesc());
		}
		return reMap;
	}

	/**
	 * 
	 * copyValueToFace2FaceOrderVo:拷贝属性
	 *
	 * @author 明灿 2015年9月2日 下午6:12:40
	 * @param getRes
	 * @param off
	 * @throws TException
	 *
	 */
	private void copyValueToFace2FaceOrderVo(
			GetOrderDetailByBankManageVoRes getRes, OrderVo4Face2Face off,
			String type) throws TException {
		if (getRes != null) {
			DeliverInfoVo deliverInfoVo = getRes.getDeliverInfoVo();
			/**
			 * 添加门店名称字段
			 */
			String outletId = getRes.getOutletId();
			if (StringUtil.isNotBlank(outletId)) {
				OutletVo outletVo = this.getOutletByOutletaId(outletId);
				if (null != outletVo) {
					off.setOutletName(outletVo.getOutletName());
				}
			}
			off.setCloseReason(getRes.getCloseReason());// 关闭原因
			off.setCreateTime(getRes.getCreateTime());// 下单时间
			off.setDisbursements(getRes.getPointDiscount());// 实付款
			off.setPointDiscount(getRes.getPointDiscount());// 积分抵扣
			if (OrderTypeEnum.FACE_TO_FACE.getCode().equals(type)) {
				off.setDisbursements(getRes.getRealTotalMoney());// 实付款
				off.setPointDiscount(getRes.getPointDiscount());// 积分抵扣
			}
			off.setMerchantName(getRes.getMerchantName());// 商户名称
			LogCvt.info("=========操作员id========" + getRes.getMerchantUserId());
			off.setMerchantUserName(this.getMerchantNameByID(getRes.getMerchantUserId()));// 操作员
			if (deliverInfoVo != null) {
				if (StringUtil.isNotBlank(deliverInfoVo.getConsignee())) {
					off.setName(deliverInfoVo.getConsignee());// 提货人
				}
				if (StringUtil.isNotBlank(deliverInfoVo.getPhone())) {
					off.setPhone(deliverInfoVo.getPhone());// 手机号
				}
			}
			off.setOrderState(getRes.getOrderStatus());// 订单状态
			if (OrderTypeEnum.PRESALE.getCode().equals(type)) {
				off.setOrgNames(
						OrgCodeUtil.getOrgNames(getRes.getOrgNames(), type));// 机构名称
			} else {
				off.setOrgNames(
						OrgCodeUtil.splitOrgNames(getRes.getOrgNames()));// 机构名称
			}
			off.setPaymentTime(getRes.getPaymentTime());// 支付时间
			off.setSettlementTime(getRes.getSettleTime());// 结算时间
			off.setSettlementStatus(getRes.getSettleState());// 结算状态
			/**
			 * 返回优惠信息
			 */
			off.setTotalCash(getRes.getTotalCash()); // 子订单总实付金额
			off.setTotalCutMoney(getRes.getTotalCutMoney()); // 子订单总优惠金额
			off.setSubTotalMoney(getRes.getSubTotalMoney());//子订单总金额
			/**
			 * 惠付需求添加
			 */
			off.setPointMoney(getRes.getPointMoney());// 积分抵扣金额
			off.setConsmeMoney(getRes.getConsumeMoney());// 消费总金额
			off.setDiscountMoney(getRes.getDiscountMoney());// 门店折扣金额
		}
	}

	/**
	 * 
	 * getPointProOrderList:积分商品订单列表优化
	 *
	 * @author 明灿 2015年9月4日 上午9:46:50
	 * @param order
	 * @return
	 * @throws Exception
	 *
	 */
	public Map<String, Object> getPointProOrderList(OrderReqOfOptimize order)
			throws Exception {
		LogCvt.info("====>订单列表请求参数<====:" + JSON.toJSONString(order));
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		QueryOrderByBankManageVoReq reqVo = new QueryOrderByBankManageVoReq();
		List<BankOrderResVo4PointList> orderList = null;
		BankOrderResVo4PointList orderVo = null;
		// 请求参数
		requestArgumentsColt(order, reqVo);
		QueryOrderByBankManageVoRes resVo = orderQueryService
				.queryOrderByBankManage(reqVo);
		LogCvt.info("====>订单列表请求返回数据<====:" + JSON.toJSONString(resVo));
		if (resVo != null && resVo.getResultVo().getResultCode()
				.equals(EnumTypes.success.getCode())) {
			List<QueryOrderByBankManageVo> listVo = resVo.getOrdervo();
			if (listVo != null && listVo.size() > 0) {
				orderList = new ArrayList<BankOrderResVo4PointList>();
				for (QueryOrderByBankManageVo vo : listVo) {
					// 属性注入
					orderVo = new BankOrderResVo4PointList();
					this.setValue2PointOrderVo(orderVo, vo);
					orderList.add(orderVo);
				}
			}
			resMap.put("orderList", orderList);
		}
		// 分页信息
		putPageValueToMap(resMap, resVo);
		return resMap;
	}

	/**
	 * 
	 * setValue2PointOrderVo:属性注入
	 *
	 * @author 明灿 2015年9月6日 下午3:24:18
	 * @param orderVo
	 * @param vo
	 * @throws TException
	 *
	 */
	private void setValue2PointOrderVo(BankOrderResVo4PointList orderVo,
			QueryOrderByBankManageVo vo) throws TException {
		orderVo.setBuyNo(vo.getQuantity());// 购买数量
		orderVo.setCreateTime(vo.getCreateTime());// 下单时间
		orderVo.setMerchantName(vo.getMerchantName());// 商户名称
		/**
		 * 添加门店名称字段
		 */
		String outletId = vo.getOutletId();
		if (StringUtil.isNotBlank(outletId)) {
			OutletVo outletVo = getOutletByOutletaId(outletId);
			if (null != outletVo) {
				orderVo.setOutletName(outletVo.getOutletName());
			}
		}
		orderVo.setMoney(vo.getSubTotalMoney());// 金额
		orderVo.setPoint(vo.getPoint());// 积分
		orderVo.setOrderId(vo.getOrderId());// 订单编号
		orderVo.setOrderStatus(vo.getOrderStatus());// 订单状态
		orderVo.setPaymentMethod(vo.getPaymentMethod());// 支付方法
		// orderVo.setProductId(); 预留
		orderVo.setProductName(vo.getProductName());// 商品名称
		orderVo.setSubOrderId(vo.getSubOrderId());
	}

	/**
	 * 
	 * getBoutiqueExport:精品商城订单导出接口
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 下午4:29:00
	 * @param reqVo
	 * @return
	 * @throws ParseException
	 * @throws TException
	 *
	 */
	public Map<String, Object> getBoutiqueExport(BoutiqueOrderReq reqVo) throws ParseException, TException {
		LogCvt.info("精品商城订单导出请求参数: " + JSON.toJSONString(reqVo));
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		QueryBoutiqueOrderByBankManageVoReq thriftQeqVo = new QueryBoutiqueOrderByBankManageVoReq();
		// 封装请求参数
		requset4Export(reqVo, thriftQeqVo);
		ExportResultRes exportResult = orderQueryService.exportBoutiqueOrderByBankManage(thriftQeqVo);
		LogCvt.info("***精品商城订单导出返回数据:" + JSON.toJSONString(exportResult));
		if (exportResult.getResultVo() != null && StringUtil.isNotBlank(exportResult.getUrl())) {
			reMap.put("url", exportResult.getUrl());
			reMap.put("code", exportResult.getResultVo().getResultCode());
			reMap.put("message", exportResult.getResultVo().getResultDesc());
		} else {
			reMap.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			reMap.put(ResultEnum.MESSAGE.getCode(), "精品商城订单导出失败");
		}
		return reMap;
	}

}
