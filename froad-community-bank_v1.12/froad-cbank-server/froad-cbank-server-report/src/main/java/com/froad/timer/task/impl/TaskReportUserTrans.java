package com.froad.timer.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportUserMapper;
import com.froad.db.mysql.rp_mappers.ReportUserTransMapper;
import com.froad.enums.OrderStatus;
import com.froad.enums.ReportOrderType;
import com.froad.enums.ReportPayType;
import com.froad.enums.SubOrderRefundState;
import com.froad.enums.SubOrderType;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.ReportOrder;
import com.froad.po.ReportOrderProduct;
import com.froad.po.ReportOrderRefund;
import com.froad.po.ReportUser;
import com.froad.po.ReportUserTrans;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.ReportOrderProductUtil;
import com.froad.util.ReportOrderRefundUtil;
import com.froad.util.ReportOrderUtil;
import com.froad.util.TaskTimeUtil;

public class TaskReportUserTrans extends AbstractProTask {
	
	private ReportUserMapper userMapper;
	private ReportUserTransMapper userTransMapper;
	
	public TaskReportUserTrans(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	protected void processByChunk(BatchChunk batchChunk) {
		StringBuffer logMsg = null;
		Page<ReportUser> page = null;
		List<ReportUser> users = null;
		List<ReportUserTrans> trans = null;
		ReportUserTrans tran = null;
		Date endDate = null;
		ReportOrderUtil orderUtil = null;
		ReportOrderProductUtil productUtil = null;
		ReportOrderRefundUtil refundUtil = null;
		List<ReportOrder> reportOrderList = null;
		List<ReportOrderProduct> reportProductList = null;
		List<ReportOrderRefund> reportRefundList = null;
		
		// log message
		logMsg = new StringBuffer();
		logMsg.append("Start process batch_id:batchDate:jobName:chunkId ");
		logMsg.append(batchChunk.getBatchId());
		logMsg.append(":");
		logMsg.append(batchChunk.getBatchDate());
		logMsg.append(":");
		logMsg.append(batchChunk.getJobName());
		logMsg.append(":");
		logMsg.append(batchChunk.getChunkId());
		LogCvt.info(logMsg.toString());
		
		page = new Page<ReportUser>();
		page.setPageSize(batchChunk.getChunkSize());
		page.setPageNumber(batchChunk.getChunkPage());
		
		endDate = TaskTimeUtil.convertToDayEnd(batchChunk.getBatchDate());
		
		// 订单信息
		orderUtil = new ReportOrderUtil(rpSqlSession, batchChunk.getBatchDate(), ReportOrderUtil.KEY_MEMBER_CODE_ORDER_TYPE);
		
		// 商品信息
		productUtil = new ReportOrderProductUtil(rpSqlSession, batchChunk.getBatchDate(), ReportOrderProductUtil.KEY_MEMBER_CODE_PRODUCT_TYPE);
		
		// 退款信息
		refundUtil = new ReportOrderRefundUtil(rpSqlSession, batchChunk.getBatchDate(), ReportOrderRefundUtil.KEY_MEMBER_CODE_ORDER_TYPE);
		
		users = userMapper.findInfoByPage(endDate, page);
		trans = new ArrayList<ReportUserTrans>();
		for(ReportUser user : users){
			// 名对面订单（大订单）
			reportOrderList = orderUtil.getOrderList(user.getClientId(), user.getUserId(), ReportOrderType.face2face.getCode());
			reportProductList = productUtil.getProductList(user.getClientId(), user.getUserId(), ReportOrderType.face2face.getCode());
			reportRefundList = refundUtil.getRefundList(user.getClientId(), user.getUserId(), ReportOrderType.face2face.getCode());
			if (Checker.isNotEmpty(reportOrderList) || Checker.isNotEmpty(reportRefundList)){
				tran = processIndividualUser(user, batchChunk.getBatchDate(),
						ReportOrderType.face2face.getCode(), reportOrderList,
						reportProductList, reportRefundList);
				trans.add(tran);
			}
			
			// 团购订单（子订单）
			reportOrderList = orderUtil.getOrderList(user.getClientId(), user.getUserId(), SubOrderType.group_merchant.getCode());
			reportProductList = productUtil.getProductList(user.getClientId(), user.getUserId(), SubOrderType.group_merchant.getCode());
			reportRefundList = refundUtil.getRefundList(user.getClientId(), user.getUserId(), SubOrderType.group_merchant.getCode());
			if (Checker.isNotEmpty(reportOrderList) || Checker.isNotEmpty(reportRefundList)){
				tran = processIndividualUser(user, batchChunk.getBatchDate(),
						SubOrderType.group_merchant.getCode(), reportOrderList,
						reportProductList, reportRefundList);
				trans.add(tran);
			}
			
			// 预售订单（子订单）
			reportOrderList = orderUtil.getOrderList(user.getClientId(), user.getUserId(), SubOrderType.presell_org.getCode());
			reportProductList = productUtil.getProductList(user.getClientId(), user.getUserId(), SubOrderType.presell_org.getCode());
			reportRefundList = refundUtil.getRefundList(user.getClientId(), user.getUserId(), SubOrderType.presell_org.getCode());
			if (Checker.isNotEmpty(reportOrderList) || Checker.isNotEmpty(reportRefundList)){
				tran = processIndividualUser(user, batchChunk.getBatchDate(),
						SubOrderType.presell_org.getCode(), reportOrderList,
						reportProductList, reportRefundList);
				trans.add(tran);
			}
			
			// 名优特惠订单（子订单）
			reportOrderList = orderUtil.getOrderList(user.getClientId(), user.getUserId(), SubOrderType.special_merchant.getCode());
			reportProductList = productUtil.getProductList(user.getClientId(), user.getUserId(), SubOrderType.special_merchant.getCode());
			reportRefundList = refundUtil.getRefundList(user.getClientId(), user.getUserId(), SubOrderType.special_merchant.getCode());
			if (Checker.isNotEmpty(reportOrderList) || Checker.isNotEmpty(reportRefundList)){
				tran = processIndividualUser(user, batchChunk.getBatchDate(),
						SubOrderType.special_merchant.getCode(), reportOrderList,
						reportProductList, reportRefundList);
				trans.add(tran);
			}

			// Bug 4366 线下积分兑换、线上积分兑换			
			// 线上积分兑换订单（子订单）/
			reportOrderList = orderUtil.getOrderList(user.getClientId(), user.getUserId(), SubOrderType.online_points_org.getCode());
			reportProductList = productUtil.getProductList(user.getClientId(), user.getUserId(), SubOrderType.online_points_org.getCode());
			reportRefundList = refundUtil.getRefundList(user.getClientId(), user.getUserId(), SubOrderType.online_points_org.getCode());
			if (Checker.isNotEmpty(reportOrderList) || Checker.isNotEmpty(reportRefundList)){
				tran = processIndividualUser(user, batchChunk.getBatchDate(),
						SubOrderType.online_points_org.getCode(), reportOrderList,
						reportProductList, reportRefundList);
				trans.add(tran);
			}
			
			// 线下积分兑换订单（子订单）
			reportOrderList = orderUtil.getOrderList(user.getClientId(), user.getUserId(), SubOrderType.offline_points_org.getCode());
			reportProductList = productUtil.getProductList(user.getClientId(), user.getUserId(), SubOrderType.offline_points_org.getCode());
			reportRefundList = refundUtil.getRefundList(user.getClientId(), user.getUserId(), SubOrderType.offline_points_org.getCode());
			if (Checker.isNotEmpty(reportOrderList) || Checker.isNotEmpty(reportRefundList)){
				tran = processIndividualUser(user, batchChunk.getBatchDate(),
						SubOrderType.offline_points_org.getCode(), reportOrderList,
						reportProductList, reportRefundList);
				trans.add(tran);
			}
		}
		
		if(Checker.isNotEmpty(trans)){
			List<List<ReportUserTrans>> totalList = CollectionsUtil.splitList(trans, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportUserTrans> subList : totalList){
				boolean result = userTransMapper.addByBatch(subList);
				LogCvt.info("用户交易表cb_report_user_trans 添加数据"+(result ? "成功" : "失败"));
			}
		}
		
	}
	
	/**
	 * 处理单个用户交易类型
	 * 
	 * @param user
	 * @param batchDate
	 * @param orderType
	 * @param reportOrderList
	 * @param reportProductList
	 * @param reportRefundList
	 * @return
	 */
	private ReportUserTrans processIndividualUser(ReportUser user,
			int batchDate, String orderType, List<ReportOrder> reportOrderList,
			List<ReportOrderProduct> reportProductList,
			List<ReportOrderRefund> reportRefundList) {
		ReportUserTrans userTran = null;
		long orderCount = 0;
		long orderAmount = 0;
		long productCount = 0;
		long productAmount = 0;
		long refundAmount = 0;
		long pointPayCount = 0;
		long pointPayAmount = 0;
		long quickPayCount = 0;
		long quickPayAmount = 0;
		long filmPayCount = 0;
		long filmPayAmount = 0;
		long pointFilmPayCount = 0;
		long pointFilmPayAmount = 0;
		long pointQuickPayCount = 0;
		long pointQuickPayAmount = 0;
		ReportOrder reportOrder = null;
		ReportOrderProduct reportProduct = null;
		ReportOrderRefund reportRefund = null;
		
		// 计算支付方式及其金额
		if (reportOrderList != null && reportOrderList.size() > 0){
			orderCount = reportOrderList.size();
			for (int i = 0; i < reportOrderList.size(); i++){
				reportOrder = reportOrderList.get(i);
				orderAmount += reportOrder.getSubOrderAmount();
				if (reportOrder.getPayType().equals(ReportPayType.fft_point.getCode())){
					pointPayCount += 1;
					pointPayAmount += reportOrder.getSubOrderAmount();
				} else if (reportOrder.getPayType().equals(ReportPayType.bank_point.getCode())) {
					pointPayCount += 1;
					pointPayAmount += reportOrder.getSubOrderAmount();
				} else if (reportOrder.getPayType().equals(ReportPayType.quick_pay.getCode())) {
					quickPayCount += 1;
					quickPayAmount += reportOrder.getSubOrderAmount();
				} else if (reportOrder.getPayType().equals(ReportPayType.film_pay.getCode())) {
					filmPayCount += 1;
					filmPayAmount += reportOrder.getSubOrderAmount();
				} else if (reportOrder.getPayType().equals(ReportPayType.fft_and_quick_pay.getCode())){
					pointQuickPayCount += 1;
					pointQuickPayAmount += reportOrder.getSubOrderAmount();
				} else if (reportOrder.getPayType().equals(ReportPayType.fft_and_film_pay.getCode())) {
					pointFilmPayCount += 1;
					pointFilmPayAmount += reportOrder.getSubOrderAmount();
				} else if (reportOrder.getPayType().equals(ReportPayType.bank_and_quick_pay.getCode())) {
					pointQuickPayCount += 1;
					pointQuickPayAmount += reportOrder.getSubOrderAmount();
				} else if (reportOrder.getPayType().equals(ReportPayType.bank_and_film_pay.getCode())) {
					pointFilmPayCount += 1;
					pointFilmPayAmount += reportOrder.getSubOrderAmount();
				}
			}
		}
		
		// 计算商品数量及金额
		if (reportProductList != null && reportProductList.size() > 0){
			for (int i = 0; i < reportProductList.size(); i++){
				reportProduct = reportProductList.get(i);
				// 全额退款的不计算
				if (reportProduct.getOrderStatus().equals(OrderStatus.paysuccess.getCode())
						&& !reportProduct.getRefundState().equals(SubOrderRefundState.REFUND_SUCCESS.getCode())) {
					productCount += reportProduct.getQuantity() + reportProduct.getVipQuantity();
					productAmount += (reportProduct.getMoney()
							* reportProduct.getQuantity()
							+ reportProduct.getVipMoney()
							* reportProduct.getVipQuantity());
				}
			}
		}
		
		// 计算退款金额
		if (reportRefundList != null && reportRefundList.size() > 0){
			for (int i = 0; i < reportRefundList.size(); i++){
				reportRefund = reportRefundList.get(i);
				refundAmount += reportRefund.getRefundAmount();
				if (reportRefund.getRefundPoint() > 0 && reportRefund.getPointRate() != null){
					refundAmount += Double.valueOf(
							Arith.div(Long.valueOf(reportRefund.getRefundPoint()).doubleValue(), Double.valueOf(reportRefund.getPointRate())))
							.intValue();
				}
			}
		}
		
		userTran = new ReportUserTrans();
		userTran.setCreateTime(TaskTimeUtil.convertToDay(batchDate));
		userTran.setClientId(user.getClientId());
		userTran.setBankCardId(user.getBankCardId());
		userTran.setForgCode(user.getForgCode());
		userTran.setForgName(user.getForgName());
		userTran.setSorgCode(user.getSorgCode());
		userTran.setSorgName(user.getSorgName());
		userTran.setTorgCode(user.getTorgCode());
		userTran.setTorgName(user.getTorgName());
		userTran.setLorgCode(user.getLorgCode());
		userTran.setLorgName(user.getLorgName());
		userTran.setUserId(user.getUserId());
		userTran.setLoginId(user.getLoginId());
		userTran.setUserName(user.getUserName());
		userTran.setMobile(user.getMobile());
		userTran.setRegTime(user.getRegTime());
		userTran.setRegType(user.getRegType());
		userTran.setIsVip(user.getIsVip());
		userTran.setSignTime(user.getSignTime());
		userTran.setValidStatus(user.getValidStatus());
		userTran.setOrderType(orderType);
		userTran.setTotalOrderNumber(orderCount);
		userTran.setTotalOrderAmount(orderAmount);
		userTran.setTotalProductNumber(productCount);
		userTran.setTotalProductAmount(productAmount);
		userTran.setTotalRefundsAmount(refundAmount);
		userTran.setTotalPointNumber(pointPayCount);
		userTran.setTotalPointAmount(pointPayAmount);
		userTran.setTotalQuickNumber(quickPayCount);
		userTran.setTotalQuickAmount(quickPayAmount);
		userTran.setTotalFilmNumber(filmPayCount);
		userTran.setTotalFilmAmount(filmPayAmount);
		userTran.setTotalPointFilmNumber(pointFilmPayCount);
		userTran.setTotalPointFilmAmount(pointFilmPayAmount);
		userTran.setTotalPointQuickNumber(pointQuickPayCount);
		userTran.setTotalPointQuickAmount(pointQuickPayAmount);
		
		return userTran;
	}
	
	@Override
	public void initialize() {
		userMapper = rpSqlSession.getMapper(ReportUserMapper.class);
		userTransMapper = rpSqlSession.getMapper(ReportUserTransMapper.class);
	}

}
