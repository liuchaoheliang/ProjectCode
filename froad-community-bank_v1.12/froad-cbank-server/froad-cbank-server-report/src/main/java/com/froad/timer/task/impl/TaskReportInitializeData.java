package com.froad.timer.task.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.PaymentMongoService;
import com.froad.db.mongo.RefundShoppingService;
import com.froad.db.mongo.SubOrderMongoService;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ReportPage;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.db.mysql.mappers.ProductMapper;
import com.froad.db.mysql.rp_mappers.BatchCycleMapper;
import com.froad.db.mysql.rp_mappers.ReportBankOrgMapper;
import com.froad.db.mysql.rp_mappers.ReportBankUserMapper;
import com.froad.db.mysql.rp_mappers.ReportOrderMapper;
import com.froad.db.mysql.rp_mappers.ReportOrderProductMapper;
import com.froad.db.mysql.rp_mappers.ReportOrderRefundMapper;
import com.froad.db.mysql.rp_mappers.ReportProductSummaryMapper;
import com.froad.db.mysql.rp_mappers.ReportUserMapper;
import com.froad.enums.CashType;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.PaymentMethod;
import com.froad.enums.PaymentReason;
import com.froad.enums.PaymentStatus;
import com.froad.enums.RefundState;
import com.froad.enums.ReportOrderType;
import com.froad.enums.ReportPayType;
import com.froad.enums.SubOrderRefundState;
import com.froad.enums.SubOrderType;
import com.froad.logback.LogCvt;
import com.froad.po.BatchCycle;
import com.froad.po.MerchantCount;
import com.froad.po.Org;
import com.froad.po.ReportBankOrg;
import com.froad.po.ReportBankUser;
import com.froad.po.ReportMerchantProduct;
import com.froad.po.ReportOrder;
import com.froad.po.ReportOrderProduct;
import com.froad.po.ReportOrderRefund;
import com.froad.po.ReportProductSummary;
import com.froad.po.ReportUser;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.PaymentMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.RefundPaymentInfo;
import com.froad.po.mongo.RefundProduct;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.thirdparty.UserSpecFunc;
import com.froad.thirdparty.impl.UserSpecFuncImpl;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractPreTask;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.DateConfigUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.OrgUtil;
import com.froad.util.TaskTimeUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.pay.user.dto.MemberSignSpecDto;
import com.pay.user.helper.BankOrg;

public class TaskReportInitializeData extends AbstractPreTask {
	// 银行用户
	private MerchantMapper merchantMapper;
	private ReportBankUserMapper reportBankUserMapper;

	// 交易信息
	private ReportOrderMapper reportOrderMapper = null;
	private ReportOrderRefundMapper reportRefundMapper = null;
	private ReportOrderProductMapper reportProductMapper = null;
	
	// 交易信息
	private RefundShoppingService refundShoppingService = null;
	private OrderMongoService orderMongoService = null;
	private SubOrderMongoService subOrderMongoService = null;
	private PaymentMongoService payService = null;
	
	private OrgUtil orgUtil = null;
	
	// 会员信息
	private ReportBankOrgMapper bankOrgMapper;
	private ReportUserMapper userMapper;
	private UserSpecFunc userSpecFunc;
	
	private BatchCycleMapper batchCycleMapper = null;
	
	// 机构商品统计
	private ProductMapper productMapper = null;
	private ReportProductSummaryMapper prdSummaryMapper = null;
	
	public TaskReportInitializeData(String name, TaskBean task) {
		super(name, task);
	}

	/**
	 * 初始化银行用户数据
	 */
	@SuppressWarnings("unchecked")
	private void initBankUserData(){
		Date begDate = null;
		Date endDate = null;
		ReportPage<MerchantCount> page = null;
		List<MerchantCount> list = null;
		List<ReportBankUser> reports = null;
		ReportBankUser r = null;
		ReportBankUser find = null;
		List<MerchantCount> tmpList = null;
		int pageNumber = 1;
		int pageSize = 3000;
		
		LogCvt.info("初始化银行用户开始");
		
		page = new ReportPage<MerchantCount>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		begDate = TaskTimeUtil.convertToDayBegin(DateConfigUtil.INITIAL_START_DAY);
		endDate = TaskTimeUtil.convertToDayEnd(DateConfigUtil.INITIAL_END_DAY);
		tmpList = merchantMapper.selectTotalMerchantByPage(begDate, endDate, page);
		list = new ArrayList<MerchantCount>();
		while (null != tmpList && tmpList.size() > 0){
			list.addAll(tmpList);
			if (tmpList.size() < pageSize){
				break;
			}
			pageNumber += 1;
			page.setPageNumber(pageNumber);
			
			sqlSession.commit(false);
			tmpList = merchantMapper.selectTotalMerchantByPage(begDate, endDate, page);
		}
		
		reports = new ArrayList<ReportBankUser>();
		for (MerchantCount c : list) {
			r = new ReportBankUser();
			setReportBankUser(c, r, orgUtil);
			find = reportBankUserMapper.selectIsExist(r);
			if(Checker.isNotEmpty(find)){
				LogCvt.warn(JSonUtil.toJSonString(find) + " 已存在");
				continue;
			}
			r.setCreateTime(TaskTimeUtil.convertToDay(DateConfigUtil.INITIAL_END_DAY));
			reports.add(r);
		}
		if(Checker.isNotEmpty(reports)){
			LogCvt.info("总共"+reports.size()+"条数据待添加");
			List<List<ReportBankUser>> splitList = CollectionsUtil.splitList(reports, CollectionsUtil.MAX_INSERT_SIZE);
			for(List<ReportBankUser> addRecords : splitList){
				boolean result = reportBankUserMapper.addByBatch(addRecords);
				LogCvt.info("签约用户表cb_report_bank_user 添加"+(result?"成功":"失败"));
			}
		}
		
		LogCvt.info("初始化银行用户完成");
	}
	
	/**
	 * 设置银行用户数据
	 * 
	 * @param c
	 * @param r
	 */
	private void setReportBankUser(MerchantCount c, ReportBankUser r, OrgUtil orgUtil){
		r.setClientId(c.getClientId());
		r.setSignUserName(c.getContractStaff());
		if(c.getProOrgCode() != null){
			r.setForgCode(c.getProOrgCode());
			Org province = orgUtil.getOrg(c.getClientId(), c.getProOrgCode());
			r.setForgName(Checker.isNotEmpty(province)?province.getOrgName():"");
		}
		if(c.getCityOrgCode() != null){
			r.setSorgCode(c.getCityOrgCode());
			Org city = orgUtil.getOrg(c.getClientId(), c.getCityOrgCode());
			r.setSorgName(Checker.isNotEmpty(city)?city.getOrgName():"");
		}
		if(c.getCountyOrgCode() != null){
			r.setTorgCode(c.getCountyOrgCode());
			Org county = orgUtil.getOrg(c.getClientId(), c.getCountyOrgCode());
			r.setTorgName(Checker.isNotEmpty(county)?county.getOrgName():"");
		}
		Org last = orgUtil.getOrg(c.getClientId(), c.getOrgCode());
		if(c.getOrgCode() != null && Checker.isNotEmpty(last) && StringUtils.equals("4", last.getOrgLevel())){
			r.setLorgCode(c.getOrgCode());
			r.setLorgName(Checker.isNotEmpty(last)?last.getOrgName():"");
		}
	}
	
	/**
	 * 初始化会员信息
	 */
	private void initMemberData(){
		Date begDate = null;
		Date endDate = null; 
		Page<ReportBankOrg> page = null;
		List<ReportUser> users = null;
		ReportUser find = null;
		ReportUser dbUser = null;
		ReportUser user = null;
		Map<String, List<MemberSignSpecDto>> signMems = null;
		List<MemberSignSpecDto> signs = null;
		Map<Long, MemberSignSpecDto> userInfoMap = null;
		MemberSignSpecDto sign = null;
		int pageNumber = 1;
		int pageSize = 3000;
		List<ReportBankOrg> tmpList = null;
		List<ReportBankOrg> bankOrgs = null;
		
		LogCvt.info("初始化会员数据开始");
		
		page = new Page<ReportBankOrg>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		
		begDate = TaskTimeUtil.convertToDayBegin(DateConfigUtil.INITIAL_START_DAY);
		endDate = TaskTimeUtil.convertToDayEnd(DateConfigUtil.INITIAL_END_DAY);
		
		bankOrgs = new ArrayList<ReportBankOrg>();
		tmpList = bankOrgMapper.findInfoByPage(page);
		while (null != tmpList && tmpList.size() > 0){
			bankOrgs.addAll(tmpList);
			if (tmpList.size() < pageSize){
				break;
			}
			pageNumber += 1;
			page.setPageNumber(pageNumber);
			
			rpSqlSession.commit(false);
			tmpList = bankOrgMapper.findInfoByPage(page);
		}
		
		try {
			if (Checker.isNotEmpty(bankOrgs)){
				signMems = userSpecFunc.queryMemberByBankId(BankOrg.BANK_AH.getBankID(), begDate, endDate);
			}
		} catch (IOException e) {
			return;
		}
		users = new ArrayList<ReportUser>();
		for(ReportBankOrg bankOrg : bankOrgs){
//			signs = (List<MemberSignSpecDto>) userSpecFunc.queryMemberByCardBin(bankOrg.getBankCardId(), BankOrg.BANK_AH.getBankID(), begDate, endDate).getData();
			
			signs = signMems.get(bankOrg.getBankCardId());
			
			userInfoMap = getUserInfoMap(signs);
			if(Checker.isEmpty(userInfoMap)){
				continue;
			}
			for(Long memberCode : userInfoMap.keySet()){
				sign = userInfoMap.get(memberCode);
				find = new ReportUser();
				find.setClientId(bankOrg.getClientId());
				find.setBankCardId(bankOrg.getBankCardId());
				find.setForgCode(bankOrg.getForgCode());
				find.setSorgCode(bankOrg.getSorgCode());
				find.setTorgCode(bankOrg.getTorgCode());
				find.setLorgCode(bankOrg.getLorgCode());
				find.setUserId(memberCode);
				dbUser = userMapper.findOneUser(find);
				if(Checker.isNotEmpty(dbUser)){
					dbUser.setSignTime(sign.getSignTime());
					dbUser.setUserName(sign.getCardHostName());
					dbUser.setIsVip(sign.getIsVip());
					dbUser.setValidStatus(sign.getValidStatus());
					userMapper.updateUser(dbUser);
					continue;
				}
				
				user = new ReportUser();
				user.setCreateTime(TaskTimeUtil.convertToDay(DateConfigUtil.INITIAL_END_DAY));
				user.setClientId(bankOrg.getClientId());
				user.setBankCardId(bankOrg.getBankCardId());
				user.setForgCode(bankOrg.getForgCode());
				user.setForgName(bankOrg.getForgName());
				user.setSorgCode(bankOrg.getSorgCode());
				user.setSorgName(bankOrg.getSorgName());
				user.setTorgCode(bankOrg.getTorgCode());
				user.setTorgName(bankOrg.getTorgName());
				user.setLorgCode(bankOrg.getLorgCode());
				user.setLorgName(bankOrg.getLorgName());
				user.setUserId(sign.getMemberCode());
				user.setLoginId(sign.getLoginID());
				user.setUserName(sign.getCardHostName());
				user.setMobile(sign.getMobile());
				user.setRegTime(sign.getCreateTime());
				user.setRegType(sign.getCreateChannel());
				user.setIsVip(Checker.isNotEmpty(sign.getIsVip()) ? sign.getIsVip() : false);
				user.setSignTime(sign.getSignTime());
				user.setValidStatus(sign.getValidStatus());
				users.add(user);
			}
			
		}
		
		if(Checker.isNotEmpty(users)){
			List<List<ReportUser>> splitList = CollectionsUtil.splitList(users, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportUser> subList : splitList){
				boolean result = userMapper.addUserByBatch(subList);
				LogCvt.info("用户表cb_report_user添加数据"+(result ? "成功" : "失败"));
			}
		}
		
		LogCvt.info("初始化会员数据结束");
	}
	
	/**
	 * 会员信息去重
	 * 
	 * @param signs
	 * @return
	 */
	private Map<Long, MemberSignSpecDto> getUserInfoMap(List<MemberSignSpecDto> signs){
		Map<Long, MemberSignSpecDto> map = new HashMap<Long, MemberSignSpecDto>();
		if(Checker.isEmpty(signs)){
			return map;
		}
		for(MemberSignSpecDto sign : signs){
			if(map.containsKey(sign.getMemberCode())){
				MemberSignSpecDto dto = map.get(sign.getMemberCode());
				if(dto.getSignTime().getTime() < sign.getSignTime().getTime()){
					map.put(sign.getMemberCode(), sign);
				}
			}else{
				map.put(sign.getMemberCode(), sign);
			}
		}
		return map;
	}
	
	@Override
	public void task() {
		BatchCycle batchCycle = null;
		int initEndDay = 0;
		
		if (isMasterNode){
			batchCycle = batchCycleMapper.findCurrentCycle();
			initEndDay = DateConfigUtil.INITIAL_END_DAY;
			
			if (null != batchCycle){
				//获取交易信息
				initTransactions(batchCycle.getBatchDate(), batchCycle.getLastBatchDate());
				
				//机构商品统计
				initOrgProductCount(batchCycle.getBatchDate(), batchCycle.getLastBatchDate());
				
				if (batchCycle.getLastBatchDate() == initEndDay){
					LogCvt.info("初始化数据开始");
					initBankUserData();
					initMemberData();
					LogCvt.info("初始化数据结束");
				}
			}
		}
	}

	/**
	 * 获取交易信息
	 * 
	 * @param batchDate
	 * @param lastBatchDate
	 */
	private void initTransactions(int batchDate, int lastBatchDate){
		List<ReportOrder> orders = null;
		List<ReportOrderRefund> refunds = null;
		List<ReportOrderProduct> products = null;
		List<OrderMongo> f2fOrderMongoList = null;
		List<SubOrderMongo> subOrderMongoList = null;
		List<RefundHistory> refundHisList = null;
		boolean isf2fExists = true;
		boolean isSubOrderExists = true;
		boolean isRefundExists = true;
		OrderMongo orderMongo = null;
		SubOrderMongo subOrderMongo = null;
		String orgCode = null;
		long subOrderAmount = 0l;
		ProductMongo productMongo = null;
		RefundHistory refundHis = null;
		RefundPaymentInfo refundPay = null;
		RefundProduct refundProduct = null;
		long refundAmount = 0l;
		long refundPoint = 0l;
		long startTime = 0l;
		long endTime = 0l;
		String payType = null;
		double pointRate = 0.0d;
		
		LogCvt.info("获取交易信息开始");
		
		startTime = TaskTimeUtil.convertToDayBegin(lastBatchDate).getTime();
		endTime = TaskTimeUtil.convertToDayBegin(batchDate).getTime();
		
		orders = new ArrayList<ReportOrder>();
		refunds = new ArrayList<ReportOrderRefund>();
		products = new ArrayList<ReportOrderProduct>();
		
		f2fOrderMongoList = findF2fOrder(startTime, endTime);
		
		if (f2fOrderMongoList == null || f2fOrderMongoList.size() == 0){
			isf2fExists = false;
		}
		
		subOrderMongoList = findSubOrder(startTime, endTime);
		
		if (subOrderMongoList == null || subOrderMongoList.size() == 0){
			isSubOrderExists = false;
		}
		
		refundHisList = findRefundHistory(startTime, endTime);
		if (refundHisList == null || refundHisList.size() == 0){
			isRefundExists = false;
		}
		
		// 处理面对面订单
		if (isf2fExists){
			for (int i = 0; i < f2fOrderMongoList.size(); i++){
				orderMongo = f2fOrderMongoList.get(i);
				
				payType = retrievePayType(orderMongo);
				
				// 普通商户
				if (EmptyChecker.isNotEmpty(orderMongo.getLorgCode())){
					orgCode = orderMongo.getLorgCode();
				} else if (EmptyChecker.isNotEmpty(orderMongo.getTorgCode())){
					orgCode = orderMongo.getTorgCode();
				} else if (EmptyChecker.isNotEmpty(orderMongo.getSorgCode())){
					orgCode = orderMongo.getSorgCode();
				} else if (EmptyChecker.isNotEmpty(orderMongo.getForgCode())){
					orgCode = orderMongo.getForgCode();
				}
				
				ReportOrder reportOrder = new ReportOrder();
				reportOrder.setBankPoint(orderMongo.getBankPoints());
				reportOrder.setClientId(orderMongo.getClientId());
				reportOrder.setCreateDate(batchDate);
				reportOrder.setFftPoint(orderMongo.getFftPoints());
				reportOrder.setForgCode(orderMongo.getForgCode());
				if (orgUtil.getOrg(orderMongo.getClientId(), orderMongo.getForgCode()) != null) {
					reportOrder.setForgName(orgUtil.getOrg(orderMongo.getClientId(), orderMongo.getForgCode()).getOrgName());
				}
				if (Checker.isNotEmpty(orderMongo.getLorgCode()) && !orderMongo.getLorgCode().equals("0")){
					reportOrder.setLorgCode(orderMongo.getLorgCode());
				} else {
					reportOrder.setLorgCode("");
				}
				if (orgUtil.getOrg(orderMongo.getClientId(), orderMongo.getLorgCode()) != null) {
					reportOrder.setLorgName(orgUtil.getOrg(orderMongo.getClientId(), orderMongo.getLorgCode()).getOrgName());
				}
				reportOrder.setMemberCode(orderMongo.getMemberCode());
				reportOrder.setMemberName(orderMongo.getMemberName());
				reportOrder.setMerchantId(orderMongo.getMerchantId());
				reportOrder.setMerchantName(orderMongo.getMerchantName());
				reportOrder.setOrderDate(new Date(orderMongo.getCreateTime()));
				reportOrder.setOrderId(orderMongo.getOrderId());
				reportOrder.setOrderStatus(orderMongo.getOrderStatus());
				reportOrder.setOrderType(OrderType.face2face.getCode());
				reportOrder.setOrgCode(orgCode);
				reportOrder.setPayType(payType);
				reportOrder.setProductId(orderMongo.getProductId());
				reportOrder.setPointRate(orderMongo.getPointRate());
				reportOrder.setRealPrice(orderMongo.getRealPrice());
				reportOrder.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
				reportOrder.setSorgCode(orderMongo.getSorgCode());
				if (orgUtil.getOrg(orderMongo.getClientId(), orderMongo.getSorgCode()) != null) {
					reportOrder.setSorgName(orgUtil.getOrg(orderMongo.getClientId(), orderMongo.getSorgCode()).getOrgName());
				}
				reportOrder.setSubOrderAmount(orderMongo.getTotalPrice());
				reportOrder.setSubOrderId(orderMongo.getOrderId());//没有子订单的类型，用大订单号
				reportOrder.setTorgCode(orderMongo.getTorgCode());
				if (Checker.isNotEmpty(orderMongo.getTorgCode()) && !orderMongo.getTorgCode().equals("0")){
					reportOrder.setTorgCode(orderMongo.getTorgCode());
				} else {
					reportOrder.setTorgCode("");
				}
				if (orgUtil.getOrg(orderMongo.getClientId(), orderMongo.getTorgCode()) != null) {
					reportOrder.setTorgName(orgUtil.getOrg(orderMongo.getClientId(), orderMongo.getTorgCode()).getOrgName());
				}
				reportOrder.setTotalPrice(orderMongo.getTotalPrice());
				orders.add(reportOrder);
				
				ReportOrderProduct reportProduct = new ReportOrderProduct();
				reportProduct.setClientId(orderMongo.getClientId());
				reportProduct.setCreateDate(batchDate);
				reportProduct.setDeliveryMoney(0);
				reportProduct.setMemberCode(orderMongo.getMemberCode());
				reportProduct.setMerchantId(orderMongo.getMerchantId());
				reportProduct.setMoney(orderMongo.getTotalPrice());
				reportProduct.setOrderId(orderMongo.getOrderId());
				reportProduct.setOrderStatus(OrderStatus.paysuccess.getCode());
				reportProduct.setProductId(orderMongo.getProductId());
				reportProduct.setProductName("");
				reportProduct.setProductType(ReportOrderType.face2face.getCode());
				reportProduct.setQuantity(1);
				reportProduct.setRefundQuantity(0);
				reportProduct.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
				reportProduct.setSubOrderId(orderMongo.getOrderId());
				reportProduct.setVipMoney(0);
				reportProduct.setVipQuantity(0);
				reportProduct.setVipRefundQuantity(0);
				products.add(reportProduct);
			}
		}
		
		// 处理其他子订单
		if (isSubOrderExists){
			for (int i = 0; i < subOrderMongoList.size(); i++){
				subOrderMongo = subOrderMongoList.get(i);
				
				//全退子订单在退款记录中统计，避免重复统计商品数
				if (SubOrderRefundState.REFUND_SUCCESS.getCode().equals(subOrderMongo.getRefundState())){
					continue;
				}
				
				if (EmptyChecker.isNotEmpty(subOrderMongo.getLorgCode())){
					orgCode = subOrderMongo.getLorgCode();
				} else if (EmptyChecker.isNotEmpty(subOrderMongo.getTorgCode())){
					orgCode = subOrderMongo.getTorgCode();
				} else if (EmptyChecker.isNotEmpty(subOrderMongo.getSorgCode())){
					orgCode = subOrderMongo.getSorgCode();
				} else if (EmptyChecker.isNotEmpty(subOrderMongo.getForgCode())){
					orgCode = subOrderMongo.getForgCode();
				}
				
				orderMongo = orderMongoService.findById(subOrderMongo.getOrderId());
				
				if (null == orderMongo){
					LogCvt.error(new StringBuffer("子订单找不到对应大订单，order_id=").append(subOrderMongo.getOrderId()).toString());
					continue;
				}
				
				payType = retrievePayType(orderMongo);
				
				subOrderAmount = 0;
				for (int j = 0; j < subOrderMongo.getProducts().size(); j++){
					productMongo = subOrderMongo.getProducts().get(j);
					
					ReportOrderProduct reportProduct = new ReportOrderProduct();
					if (subOrderMongo.getType().equals(SubOrderType.online_points_org.getCode())
							|| subOrderMongo.getType().equals(SubOrderType.offline_points_org.getCode())){
						if (orderMongo.getPointRate() == null){
							LogCvt.error(new StringBuffer("积分订单缺少积分比例，order_id=").append(subOrderMongo.getOrderId()).toString());
							if (orderMongo.getClientId().equals("anhui")){
								pointRate = 10.0d;
							} else {
								pointRate = 1.0d;
							}
						} else {
							pointRate = Double.valueOf(orderMongo.getPointRate());
						}
						
						subOrderAmount += Arith.div((productMongo.getDeliveryMoney()
										+ productMongo.getMoney() * productMongo.getQuantity() 
										+ productMongo.getVipMoney() * productMongo.getVipQuantity()),
										pointRate);
						reportProduct.setMoney((long)Arith.div(productMongo.getMoney().doubleValue(), pointRate));
						reportProduct.setVipMoney((long)Arith.div(productMongo.getVipMoney().doubleValue(), pointRate));
					} else {
						subOrderAmount += productMongo.getDeliveryMoney()
								+ productMongo.getMoney()
								* productMongo.getQuantity()
								+ productMongo.getVipMoney()
								* productMongo.getVipQuantity();
						reportProduct.setMoney(productMongo.getMoney());
						reportProduct.setVipMoney(productMongo.getVipMoney());
					}
					
					reportProduct.setClientId(subOrderMongo.getClientId());
					reportProduct.setCreateDate(batchDate);
					reportProduct.setDeliveryMoney(productMongo.getDeliveryMoney());
					reportProduct.setMemberCode(subOrderMongo.getMemberCode());
					reportProduct.setMerchantId(subOrderMongo.getMerchantId());
					reportProduct.setOrderId(subOrderMongo.getOrderId());
					reportProduct.setOrderStatus(subOrderMongo.getOrderStatus());
					reportProduct.setProductId(productMongo.getProductId());
					reportProduct.setProductName(productMongo.getProductName());
					reportProduct.setProductType(subOrderMongo.getType());
					reportProduct.setQuantity(productMongo.getQuantity());
					reportProduct.setRefundQuantity(0);
					reportProduct.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
					reportProduct.setSubOrderId(subOrderMongo.getSubOrderId());
					reportProduct.setVipQuantity(productMongo.getVipQuantity());
					reportProduct.setVipRefundQuantity(0);
					products.add(reportProduct);
				}
				
				ReportOrder reportOrder = new ReportOrder();
				reportOrder.setBankPoint(orderMongo.getBankPoints());
				reportOrder.setClientId(subOrderMongo.getClientId());
				reportOrder.setCreateDate(batchDate);
				reportOrder.setFftPoint(orderMongo.getFftPoints());
				reportOrder.setForgCode(subOrderMongo.getForgCode());
				if (orgUtil.getOrg(subOrderMongo.getClientId(), subOrderMongo.getForgCode()) != null) {
					reportOrder.setForgName(orgUtil.getOrg(subOrderMongo.getClientId(), subOrderMongo.getForgCode()).getOrgName());
				}
				if (Checker.isNotEmpty(subOrderMongo.getLorgCode()) && !subOrderMongo.getLorgCode().equals("0")){
					reportOrder.setLorgCode(subOrderMongo.getLorgCode());
				} else {
					reportOrder.setLorgCode("");
				}
				if (orgUtil.getOrg(subOrderMongo.getClientId(), subOrderMongo.getLorgCode()) != null){
					reportOrder.setLorgName(orgUtil.getOrg(subOrderMongo.getClientId(), subOrderMongo.getLorgCode()).getOrgName());
				}
				reportOrder.setMemberCode(subOrderMongo.getMemberCode());
				reportOrder.setMemberName(subOrderMongo.getMemberName());
				reportOrder.setMerchantId(subOrderMongo.getMerchantId());
				reportOrder.setMerchantName(subOrderMongo.getMerchantName());
				reportOrder.setOrderDate(new Date(subOrderMongo.getCreateTime()));
				reportOrder.setOrderId(orderMongo.getOrderId());
				reportOrder.setOrderStatus(subOrderMongo.getOrderStatus());
				reportOrder.setOrderType(subOrderMongo.getType());
				reportOrder.setOrgCode(orgCode);
				reportOrder.setPayType(payType);
				reportOrder.setPointRate(orderMongo.getPointRate());
				reportOrder.setRealPrice(orderMongo.getRealPrice());
				if (subOrderMongo.getRefundState() == null){
					reportOrder.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
				} else {
					reportOrder.setRefundState(subOrderMongo.getRefundState());
				}
				reportOrder.setSorgCode(subOrderMongo.getSorgCode());
				if (orgUtil.getOrg(subOrderMongo.getClientId(), subOrderMongo.getSorgCode()) != null){
					reportOrder.setSorgName(orgUtil.getOrg(subOrderMongo.getClientId(), subOrderMongo.getSorgCode()).getOrgName());
				}
				reportOrder.setSubOrderAmount(subOrderAmount);
				reportOrder.setSubOrderId(subOrderMongo.getSubOrderId());//没有子订单的类型，用大订单号
				reportOrder.setTorgCode(subOrderMongo.getTorgCode());
				if (orgUtil.getOrg(subOrderMongo.getClientId(), subOrderMongo.getTorgCode()) != null){
					reportOrder.setTorgName(orgUtil.getOrg(subOrderMongo.getClientId(), subOrderMongo.getTorgCode()).getOrgName());
				}
				reportOrder.setTotalPrice(orderMongo.getTotalPrice());
				orders.add(reportOrder);
			}
		}
		
		// 退款记录
		if (isRefundExists){
			for (int i = 0; i < refundHisList.size(); i++){
				refundHis = refundHisList.get(i);
				
				orderMongo = orderMongoService.findById(refundHis.getOrderId());
				
				if (null == orderMongo){
					LogCvt.error(new StringBuffer("退款订单找不到原订单记录，order_id=").append(refundHis.getOrderId()).toString());
					continue;
				}
				
				if (null == refundHis.getPaymentInfo()){
					LogCvt.error(new StringBuffer("退款记录缺少退款支付流水信息，refund_id=").append(refundHis.get_id()).toString());
					continue;
				}
				
				refundAmount = 0;
				refundPoint = 0;
				for (int j = 0; j < refundHis.getPaymentInfo().size(); j++){
					refundPay = refundHis.getPaymentInfo().get(j);
					
					if (refundPay.getType().equals(PaymentMethod.cash.getCode())){
						refundAmount += refundPay.getRefundValue();
					} else if (refundPay.getType().equals(PaymentMethod.bankPoints.getCode())) {
						refundPoint += refundPay.getRefundValue();
					} else if (refundPay.getType().equals(PaymentMethod.froadPoints.getCode())) {
						refundPoint += refundPay.getRefundValue();
					}
				}
				
				ReportOrderRefund reportRefund = new ReportOrderRefund();
				reportRefund.setClientId(refundHis.getClientId());
				reportRefund.setCreateDate(batchDate);
				reportRefund.setMemberCode(Long.parseLong(refundHis.getMemberCode()));
				reportRefund.setMerchantId(refundHis.getShoppingInfo().get(0).getMerchantId());
				reportRefund.setOrderId(refundHis.getOrderId());
				reportRefund.setOrderType(refundHis.getShoppingInfo().get(0).getType());
				reportRefund.setPointRate(orderMongo.getPointRate());
				reportRefund.setRefundAmount(refundAmount);
				reportRefund.setRefundId(refundHis.get_id());
				if (refundHis.getRefundTime() != null && refundHis.getRefundTime() > 0){
					reportRefund.setRefundTime(new Date(refundHis.getRefundTime()));
				}
				reportRefund.setRefundPoint(refundPoint);
				reportRefund.setSubOrderId(refundHis.getShoppingInfo().get(0).getSubOrderId());
				refunds.add(reportRefund);
				
				subOrderMongo = findSubOrderBySubOrderId(refundHis.getClientId(), refundHis.getShoppingInfo().get(0).getSubOrderId());
				if (null != subOrderMongo){
					for (int k = 0; k < refundHis.getShoppingInfo().get(0).getProducts().size(); k++){
						refundProduct = refundHis.getShoppingInfo().get(0).getProducts().get(k);
						Integer refundVipPrice = refundProduct.getVipPrice() == null ? 0 : refundProduct.getVipPrice();
						Integer refundVipQuantity = refundProduct.getVipQuantity() == null ? 0 : refundProduct.getVipQuantity();
						
						ReportOrderProduct reportProduct = new ReportOrderProduct();
						if (subOrderMongo.getType().equals(SubOrderType.online_points_org.getCode())
								|| subOrderMongo.getType().equals(SubOrderType.offline_points_org.getCode())){
							reportProduct.setMoney((long)Arith.div(refundProduct.getPrice().doubleValue(), Double.valueOf(orderMongo.getPointRate())));
							reportProduct.setVipMoney((long)Arith.div(refundVipPrice.doubleValue(), Double.valueOf(orderMongo.getPointRate())));
						} else {
							reportProduct.setMoney(refundProduct.getPrice());
							reportProduct.setVipMoney(refundVipPrice);
						}
						reportProduct.setClientId(refundHis.getClientId());
						reportProduct.setCreateDate(batchDate);
						reportProduct.setDeliveryMoney(0);
						reportProduct.setMemberCode(Long.valueOf(refundHis.getMemberCode()));
						reportProduct.setMerchantId(refundHis.getShoppingInfo().get(0).getMerchantId());
						reportProduct.setOrderId(refundHis.getOrderId());
						reportProduct.setOrderStatus(subOrderMongo.getOrderStatus());
						reportProduct.setProductId(refundProduct.getProductId());
						reportProduct.setProductName(refundProduct.getProductName());
						reportProduct.setProductType(refundHis.getShoppingInfo().get(0).getType());
						reportProduct.setQuantity(0);
						reportProduct.setRefundQuantity(refundProduct.getQuantity());
						if (subOrderMongo.getRefundState() == null){
							reportProduct.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
						} else {
							reportProduct.setRefundState(subOrderMongo.getRefundState());
						}
						reportProduct.setSubOrderId(refundHis.getShoppingInfo().get(0).getSubOrderId());
						reportProduct.setVipQuantity(0);
						reportProduct.setVipRefundQuantity(refundVipQuantity);
						products.add(reportProduct);
					}
				}
			}
		}
		
		if (orders != null && orders.size() > 0){
			LogCvt.info(new StringBuffer("交易订单数").append(orders.size()).toString());
			List<List<ReportOrder>> totalOrderList = CollectionsUtil.splitList(orders, CollectionsUtil.MAX_INSERT_SIZE);;
			for (List<ReportOrder> subList : totalOrderList){
				reportOrderMapper.addOrderByBatch(subList);
			}
		}
		
		if (refunds != null && refunds.size() > 0){
			LogCvt.info(new StringBuffer("退款单数：").append(refunds.size()).toString());
			List<List<ReportOrderRefund>> totalRefundList = CollectionsUtil.splitList(refunds, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportOrderRefund> subList : totalRefundList){
				reportRefundMapper.addRefundByBatch(subList);
			}
		}
		
		if (products != null && products.size() > 0){
			LogCvt.info(new StringBuffer("商品活动数：").append(products.size()).toString());
			List<List<ReportOrderProduct>> totalProductList = CollectionsUtil.splitList(products, CollectionsUtil.MAX_INSERT_SIZE);
			for (List<ReportOrderProduct> subList : totalProductList){
				reportProductMapper.addProductByBatch(subList);
			}
		}
		
		LogCvt.info("获取交易信息结束");
	}
	
	/**
	 * 获取支付方式
	 * 
	 * @param orderMongo
	 * @return
	 */
	private String retrievePayType(OrderMongo orderMongo){
		List<PaymentMongo> payList = null;
		DBObject queryObj = null;
		ReportPayType payType = null;
		PaymentMongo payMongo = null;
		
		//纯积分支付
		if (orderMongo.getRealPrice() == null || orderMongo.getRealPrice() == 0){
			if (orderMongo.getFftPoints() != null && orderMongo.getFftPoints() > 0){
				payType = ReportPayType.fft_point;
			} else if (orderMongo.getBankPoints() != null && orderMongo.getBankPoints() > 0) {
				payType = ReportPayType.bank_point;
			}
		} else {
			//带现金的支付
			queryObj = new BasicDBObject();
			queryObj.put("order_id", orderMongo.getOrderId());
			queryObj.put("client_id", orderMongo.getClientId());
			queryObj.put("payment_reason", PaymentReason.payment.getCode());
			queryObj.put("payment_status", PaymentStatus.pay_success.getCode());
			payList = payService.findByCondition(queryObj);
			
			if (Checker.isNotEmpty(payList)){
				for (int i = 0; i < payList.size(); i++){
					payMongo = payList.get(i);
					if (null == payMongo.getPaymentTypeDetails()){
						//组合支付积分支付流水，无payment_type_details标识
						continue;
					}
					if (payMongo.getPaymentTypeDetails().equals(CashType.bank_fast_pay.code())){
						if (orderMongo.getTotalPrice().intValue() == orderMongo.getRealPrice().intValue()){
							payType = ReportPayType.quick_pay;
						} else if (orderMongo.getFftPoints() > 0) {
							payType = ReportPayType.fft_and_quick_pay;
						} else {
							payType = ReportPayType.bank_and_quick_pay;
						}
						break;
					} else if (payMongo.getPaymentTypeDetails().equals(CashType.foil_card.code())) {
						if (orderMongo.getTotalPrice().intValue() == orderMongo.getRealPrice().intValue()){
							payType = ReportPayType.film_pay;
						} else if (orderMongo.getFftPoints() > 0) {
							payType = ReportPayType.fft_and_film_pay;
						} else {
							payType = ReportPayType.bank_and_film_pay;
						}
						break;
					}
				}
			} else {
				LogCvt.error(new StringBuffer("找不到支付流水，orderId = ").append(orderMongo.getOrderId()).toString());
				payType = ReportPayType.quick_pay;
			}
		}
		
		if (null == payType){
			payType = ReportPayType.quick_pay;
			LogCvt.error(new StringBuffer("匹配不到对应支付方式，默认设置为快捷支付，orderId = ").append(orderMongo.getOrderId()).toString());
		}
		
		return payType != null ? payType.getCode() : ReportPayType.quick_pay.getCode();
	}
	
	/**
	 * 获取面对面订单
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private List<OrderMongo> findF2fOrder(long startTime, long endTime){
		List<OrderMongo> orderList = null;
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put("order_status", OrderStatus.paysuccess.getCode());
		queryObj.put("is_qrcode", 1);
		queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		
		orderList = orderMongoService.findByCondition(queryObj);
		
		return orderList;
	}

	/**
	 * 获取子订单
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private List<SubOrderMongo> findSubOrder(long startTime, long endTime){
		List<SubOrderMongo> orderList = null;
		DBObject queryObj = null;
		List<String> typeList = null;
		
		queryObj = new BasicDBObject();
		queryObj.put("order_status", OrderStatus.paysuccess.getCode());
		queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		// 只查找团购、预售、名优特惠子订单
		typeList = new ArrayList<String>();
		typeList.add(SubOrderType.group_merchant.getCode());
		typeList.add(SubOrderType.presell_org.getCode());
		typeList.add(SubOrderType.special_merchant.getCode());
		typeList.add(SubOrderType.online_points_org.getCode());
		typeList.add(SubOrderType.offline_points_org.getCode());
		queryObj.put("type", new BasicDBObject(QueryOperators.IN, typeList));
		
		orderList = subOrderMongoService.findByCondition(queryObj);
		
		return orderList;
	}
	
	/**
	 * 根据子订单号获取子订单
	 * 
	 * @param clientId
	 * @param subOrderId
	 * @return
	 */
	private SubOrderMongo findSubOrderBySubOrderId(String clientId, String subOrderId){
		SubOrderMongo subOrder = null;
		DBObject queryObj = null;
		List<SubOrderMongo> subOrderList = null;
		
		queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("sub_order_id", subOrderId);
		
		subOrderList = subOrderMongoService.findByCondition(queryObj);
		if (Checker.isNotEmpty(subOrderList)){
			subOrder = subOrderList.get(0);
		} else {
			LogCvt.error("Sub order record not found:" + subOrderId);
		}
		
		return subOrder;
	}
	
	/**
	 * 获取订单退款记录
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private List<RefundHistory> findRefundHistory(long startTime, long endTime){
		List<RefundHistory> refundList = null;
		DBObject queryObj = null;
		List<String> stateList = null;
		List<String> typeList = null;
		
		queryObj = new BasicDBObject();
		stateList = new ArrayList<String>();
		stateList.add(RefundState.REFUND_SUCCESS.getCode());
		stateList.add(RefundState.REFUND_MANUAL_SUCCESS.getCode());
		queryObj.put("refund_state", new BasicDBObject(QueryOperators.IN, stateList));
		queryObj.put("refund_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		// 只查找团购、预售、名优特惠退款订单
		typeList = new ArrayList<String>();
		typeList.add(SubOrderType.group_merchant.getCode());
		typeList.add(SubOrderType.presell_org.getCode());
		typeList.add(SubOrderType.special_merchant.getCode());
		queryObj.put("shopping_info.type", new BasicDBObject(QueryOperators.IN, typeList));
		
		refundList = refundShoppingService.findByCondition(queryObj);
		
		return refundList;
	}
	
	/**
	 * 初始化机构商品数，团购、预售、名优特惠
	 * 
	 * @param batchDate
	 * @param lastBatchDate
	 */
	private void initOrgProductCount(int batchDate, int lastBatchDate){
		Date endDate = null;
		Iterator<String> keyIt = null;
		String keyStr = null;
		Org org = null;
		ReportProductSummary summary = null;
		List<ReportProductSummary> summaryList = null;
		List<List<ReportProductSummary>> collectionList = null;
		Map<String, Org> orgMap = null;
		Map<String, Long> productCountMap = null;
		
		LogCvt.info("开始初始化机构商品数");
		endDate = TaskTimeUtil.convertToDayEnd(lastBatchDate);
		
		summaryList = new ArrayList<ReportProductSummary>();
		productCountMap = getProductCountMap(endDate);
		
		if (Checker.isNotEmpty(productCountMap)){
			orgMap = orgUtil.getOrgMap();
			keyIt = orgMap.keySet().iterator();
			while (keyIt.hasNext()){
				keyStr = keyIt.next();
				org = orgMap.get(keyStr);
				if (Checker.isNotEmpty(productCountMap.get(keyStr))){
					summary = new ReportProductSummary();
					summary.setClientId(org.getClientId());
					summary.setCreateDate(batchDate);
					summary.setForgCode(org.getProvinceAgency());
					summary.setLorgCode("");
					summary.setOrgCode(org.getOrgCode());
					summary.setSorgCode(org.getCityAgency());
					summary.setTorgCode(org.getCountyAgency());
					summary.setTotalProducts(productCountMap.get(keyStr));
					
					if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_one.getLevel())){
						summary.setForgCode(org.getOrgCode());
					} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())) {
						summary.setSorgCode(org.getOrgCode());
					} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())) {
						summary.setTorgCode(org.getOrgCode());
					} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_four.getLevel())) {
						summary.setLorgCode(org.getOrgCode());
					}
					
					if (summary.getTotalProducts() == null || summary.getTotalProducts() == 0){
						continue;
					}
					
					summaryList.add(summary);
				}
			}
			
			if (Checker.isNotEmpty(summaryList)){
				collectionList = CollectionsUtil.splitList(summaryList, CollectionsUtil.MAX_INSERT_SIZE);
				for (List<ReportProductSummary> subList : collectionList){
					prdSummaryMapper.addSummaryByBatch(subList);
				}
			}
		}
		
		LogCvt.info("初始化机构商品数完成");
	}
	
	/**
	 * get product count map
	 * 
	 * @param endDate
	 * @return
	 */
	private Map<String, Long> getProductCountMap(Date endDate){
		Map<String, Long> countMap = null;
		StringBuffer key = null;
		List<ReportMerchantProduct> subList = null;
		List<ReportMerchantProduct> countList = null;
		Page<ReportMerchantProduct> page = null;
		int pageNumber = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		
		page = new Page<ReportMerchantProduct>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		subList = productMapper.findProductCountByPage(endDate, page);
		countList = new ArrayList<ReportMerchantProduct>();
		while (null != subList && subList.size() > 0){
			countList.addAll(subList);
			if (subList.size() < pageSize){
				break;
			}
			pageNumber += 1;
			page.setPageNumber(pageNumber);
			
			sqlSession.commit(false);
			subList = productMapper.findProductCountByPage(endDate, page);
		}
		
		countMap = new HashMap<String, Long>();
		if (Checker.isNotEmpty(countList)){
			for (ReportMerchantProduct product : countList){
				key = new StringBuffer();
				key.append(product.getClientId());
				key.append(product.getOrgCode());
				countMap.put(key.toString(), product.getTotalProducts());
			}
		}
		
		return countMap;
	}
	
	@Override
	public void initialize() {
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		reportBankUserMapper = rpSqlSession.getMapper(ReportBankUserMapper.class);
		reportOrderMapper = rpSqlSession.getMapper(ReportOrderMapper.class);
		reportRefundMapper = rpSqlSession.getMapper(ReportOrderRefundMapper.class);
		reportProductMapper = rpSqlSession.getMapper(ReportOrderProductMapper.class);
		batchCycleMapper = rpSqlSession.getMapper(BatchCycleMapper.class);
		
		bankOrgMapper = rpSqlSession.getMapper(ReportBankOrgMapper.class);
		userMapper = rpSqlSession.getMapper(ReportUserMapper.class);
		userSpecFunc = new UserSpecFuncImpl();
		
		productMapper = sqlSession.getMapper(ProductMapper.class);
		prdSummaryMapper = rpSqlSession.getMapper(ReportProductSummaryMapper.class);
		
		refundShoppingService = new RefundShoppingService();
		orderMongoService = new OrderMongoService();
		subOrderMongoService = new SubOrderMongoService();
		payService = new PaymentMongoService();
		
		orgUtil = new OrgUtil(sqlSession);
	}
}
