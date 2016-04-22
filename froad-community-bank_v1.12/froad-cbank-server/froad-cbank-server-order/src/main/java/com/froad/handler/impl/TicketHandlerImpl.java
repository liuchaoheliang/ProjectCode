package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.DeliveryType;
import com.froad.enums.FieldMapping;
import com.froad.enums.ModuleID;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderType;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductType;
import com.froad.enums.QrCodeType;
import com.froad.enums.ResultCode;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SmsType;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.handler.TicketHandler;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.QrCodeLogic;
import com.froad.logic.SettlementLogic;
import com.froad.logic.SmsLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.QrCodeLogicImpl;
import com.froad.logic.impl.SettlementLogicImpl;
import com.froad.logic.impl.SmsLogicImpl;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.Org;
import com.froad.po.RecvInfo;
import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.support.OrderSupport;
import com.froad.support.TicketSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thread.FroadThreadPool;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.thrift.vo.QrCodeResponseVo;
import com.froad.thrift.vo.ticket.MemberProductVerifyInfoVo;
import com.froad.thrift.vo.ticket.TicketSummaryVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerResponseVo;
import com.froad.thrift.vo.ticket.TicketVerifyRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyResponseVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.JSonUtil;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class TicketHandlerImpl implements TicketHandler{
	
	/**
	 * 券失效时间
	 */
	private static final String EXPIRE_END_TIME = "expire_end_time";
	private static final String DELIVERY_END_TIME = "delivery_end_time";
	
	//common项目公共
	private final static CommonLogic commonLogic = new CommonLogicImpl();
	
	/**
	 * 生成券id
	 */
	public static final SimpleID simpleId = new SimpleID(ModuleID.ticket);
	
	/**
	 * 业务监控
	 */
	private static MonitorService monitorService = new MonitorManager();
	
	@Override
	public List<Ticket> generateTicket(OrderMongo order) {
		OrderSupport orderSupport = null;
		List<SubOrderMongo> subOrderList = null;
		SubOrderMongo subOrder = null;
		List<Ticket> ticketList = null;
		List<Ticket> groupTicketList = null;
		List<Ticket> presellTicketList = null;
		TicketSupport ticketSupport = null;
		QrCodeLogic qrCodeLogic = null;
		CommonLogic orgLogic = null;
		SmsLogic smsLogic = null;
		String orderId = null;
		
		orderSupport = new OrderSupportImpl();
		qrCodeLogic = new QrCodeLogicImpl();
		ticketSupport = new TicketSupportImpl();
		orgLogic = new CommonLogicImpl();
		smsLogic = new SmsLogicImpl();
		
		try {
			if (null == order){
				LogCvt.info("订单不能为空");
				return null;
			}
			orderId = order.getOrderId();
			
			if (null != order && order.getIsQrcode() == 1){
				LogCvt.info(new StringBuffer("面对面订单不需要生成券：").append(orderId).toString());
				return null;
			}
			
			subOrderList = orderSupport.getSubOrderListByOrderId(order.getClientId(), orderId);
			
			/**
			 * 验证订单是否已经生成券，避免重复生成券
			 */
			ticketList = ticketSupport.getTickets(orderId, null, null);
			if (null != ticketList && ticketList.size() > 0){
				LogCvt.info(new StringBuffer("重复生成券请求：").append(orderId).toString());
				return null;
			}
			
			if (null != order && null != subOrderList && subOrderList.size() > 0){
				LogCvt.info(new StringBuffer(orderId).append(" 子订单数量为： ").append(subOrderList.size()).toString());
				
				groupTicketList = new ArrayList<Ticket>();
				presellTicketList = new ArrayList<Ticket>();
				
				for (int i = 0; i < subOrderList.size(); i++){
					subOrder = subOrderList.get(i);
					
					if (subOrder.getType().equals(SubOrderType.group_merchant.getCode())){
						// 生成团购券
						LogCvt.info(new StringBuffer("开始生成子订单团购券： ").append(subOrder.getSubOrderId()).toString());
						generateGroupTicket(ticketSupport, orgLogic, qrCodeLogic, order, subOrder, groupTicketList, orderSupport);
					} else if (subOrder.getType().equals(SubOrderType.presell_org.getCode())) {
						// 生成预售券，配送预售商品不需要生成券
						LogCvt.info(new StringBuffer("开始生成子订单预售券： ").append(subOrder.getSubOrderId()).toString());
						generatePresellTicket(ticketSupport, orgLogic, qrCodeLogic, order, subOrder, presellTicketList, orderSupport);
					}
				}
				
				// 发送团购券码
				if (null != groupTicketList && groupTicketList.size() > 0) {
					sendGroupSms(ticketSupport, smsLogic, order.getClientId(), groupTicketList);
				}
				
				// 发送预售下单成功提醒 
				if (null != presellTicketList && presellTicketList.size() > 0){
					sendPresellSms(order.getPhone(), SmsType.presell.getCode(), order.getClientId(), orderId, smsLogic);
				}
			}
		} catch (Exception e) {
			LogCvt.error(new StringBuffer("生成提货码失败：").append(order.getOrderId()).toString(), e);
			return null;
		}

		ticketList = new ArrayList<Ticket>();
		if (null != groupTicketList && groupTicketList.size() > 0){
			ticketList.addAll(groupTicketList);
		}
		
		if (null != presellTicketList && presellTicketList.size() > 0){
			ticketList.addAll(presellTicketList);
		}
		
		return ticketList;
	}
	
	/**
	 * 生成团购券
	 * 
	 * @param ticketSupport
	 * @param orgLogic
	 * @param qrCodeLogic
	 * @param order
	 * @param subOrder
	 * @param ticketList
	 * @return
	 */
	private void generateGroupTicket(TicketSupport ticketSupport, CommonLogic orgLogic,
			QrCodeLogic qrCodeLogic, OrderMongo order, SubOrderMongo subOrder,
			List<Ticket> ticketList, OrderSupport orderSupport) {
		List<ProductMongo> productList = null;
		ProductMongo product = null;
		String clientId = null;
		String orderId = null;
		String subOrderId = null;
		String productId = null;
		String productName = null;
		Ticket ticket = null;
		String ticketId = null;
		Long memberCode = null;
		Map<String, String> redisProductMap = null;
		String merchantId = null;
		long expireTime = 0l;
		List<Ticket> subList = null;
		Map<OrgLevelEnum, String> orgLevelMap = null;
		RecvInfo deliverInfo = null;
		String recvName = null;
		
		clientId = subOrder.getClientId();
		orderId = subOrder.getOrderId();
		subOrderId = subOrder.getSubOrderId();
		memberCode = subOrder.getMemberCode();
		merchantId = subOrder.getMerchantId();
		
		deliverInfo = getDeliveryInfo(order, orderSupport);
		if (null != deliverInfo){
			recvName = deliverInfo.getConsignee();
		} else {
			recvName = order.getMemberName();
		}
		
		orgLevelMap = orgLogic.getSuperOrgByMerchantId(clientId, merchantId);
		
		subList = new ArrayList<Ticket>();
		productList = subOrder.getProducts();
		// 团购商品，一个商品对应一张券
		for (int i = 0; i < productList.size(); i++){
			product = productList.get(i);
			productId = product.getProductId();
			productName = product.getProductName();
			
			redisProductMap = commonLogic.getProductRedis(clientId, merchantId, productId);
			expireTime = Long.parseLong(redisProductMap.get(EXPIRE_END_TIME));
			
			for (int j = 0; j < product.getQuantity(); j ++){
				ticket = new Ticket();
				ticket.setCreateTime(System.currentTimeMillis());
				ticket.setClientId(clientId);
				ticket.setMerchantId(subOrder.getMerchantId());
				ticket.setMerchantName(subOrder.getMerchantName());
				ticket.setMemberCode(String.valueOf(memberCode));
				ticket.setMemberName(recvName);//提货人
				ticket.setOrderId(orderId);
				ticket.setSubOrderId(subOrderId);
				ticket.setMobile(order.getPhone());
//				ticketId = simpleId.nextId();
				ticketId = simpleId.ticketID();
				ticket.setTicketId(ticketId);
				ticket.setType(ProductType.group.getCode());
				ticket.setProductId(productId);
				ticket.setProductName(productName);
				ticket.setQuantity(1);// 一个团购商品对应一张券
				ticket.setStatus(TicketStatus.init.getCode());//初始状态-待发送
				ticket.setExpireTime(expireTime);
				if (null != orgLevelMap && orgLevelMap.size() > 0){
					if (null != orgLevelMap.get(OrgLevelEnum.orgLevel_one)){
						ticket.setForgCode(orgLevelMap.get(OrgLevelEnum.orgLevel_one));
					} else if (null != orgLevelMap.get(OrgLevelEnum.orgLevel_two)){
						ticket.setForgCode(orgLevelMap.get(OrgLevelEnum.orgLevel_two));
					} else if (null != orgLevelMap.get(OrgLevelEnum.orgLevel_two)){
						ticket.setForgCode(orgLevelMap.get(OrgLevelEnum.orgLevel_two));
					} else if (null != orgLevelMap.get(OrgLevelEnum.orgLevel_three)){
						ticket.setForgCode(orgLevelMap.get(OrgLevelEnum.orgLevel_three));
					}
				}
				
				ticket.setPrice(product.getMoney());
				
				// 添加团购券
				ticketSupport.addTicket(ticket);
				
				generateQrcode(ticketSupport, qrCodeLogic, ticketId, clientId);
				
				subList.add(ticket);
			}
		}
		
		LogCvt.info(new StringBuffer(subOrderId).append(" 子订单共生成")
				.append(subList.size()).append("张团购券: ").toString());
		
		ticketList.addAll(subList);
	}
	
	/**
	 * 生成预售券
	 * 
	 * @param ticketSupport
	 * @param orgLogic
	 * @param qrCodeLogic
	 * @param order
	 * @param subOrder
	 * @param ticketList
	 * @return
	 */
	private void generatePresellTicket(TicketSupport ticketSupport, CommonLogic orgLogic,
			QrCodeLogic qrCodeLogic, OrderMongo order, SubOrderMongo subOrder,
			List<Ticket> ticketList, OrderSupport orderSupport) {
		List<ProductMongo> productList = null;
		ProductMongo product = null;
		String clientId = null;
		Long memberCode = null;
		String productId = null;
		Map<String, String> redisProductMap = null;
		long expireTime = 0l;
		Ticket ticket = null;
		String ticketId = null;
		List<Ticket> subList = null;
		Map<String, String> ticketIdMap = null;
		Org org = null;
		//String merchantOrgCode = null; //bug 2312
		String orgCode = null;
		RecvInfo deliverInfo = null;
		String recvName = null;
		Iterator<String> iterator = null;
		
		clientId = subOrder.getClientId();
		memberCode = subOrder.getMemberCode();
		//merchantOrgCode = subOrder.getMerchantId();//bug 2312
		
		deliverInfo = getDeliveryInfo(order, orderSupport);
		if (null != deliverInfo){
			recvName = deliverInfo.getConsignee();
		} else {
			recvName = order.getMemberName();
		}
		
		//org = orgLogic.getOrgByOrgCode(merchantOrgCode, clientId);//bug 2312
		
		/**
		 * 预售券-按网点生成券，一个网点生成一张券
		 */
		subList = new ArrayList<Ticket>();
		ticketIdMap = new HashMap<String, String>();
		productList = subOrder.getProducts();
		for (int i = 0; i < productList.size(); i++){
			product = productList.get(i);
			productId = product.getProductId();
			orgCode = product.getOrgCode();
			org = orgLogic.getOrgByOrgCode(orgCode, clientId);//bug 2312
			
			// 仅自提预售需生成券
			if (!product.getDeliveryOption().equals(DeliveryType.take.getCode())){
				continue;
			}
			
			redisProductMap = commonLogic.getProductRedis(clientId, org.getMerchantId(), productId);
			expireTime = Long.parseLong(redisProductMap.get(DELIVERY_END_TIME));
			
			ticket = new Ticket();
			ticket.setCreateTime(System.currentTimeMillis());
			ticket.setClientId(clientId);
			ticket.setMerchantId(subOrder.getMerchantId());
			ticket.setMerchantName(subOrder.getMerchantName());
			ticket.setMemberCode(String.valueOf(memberCode));
			ticket.setMemberName(recvName);// 提货人
			ticket.setOrderId(order.getOrderId());
			ticket.setSubOrderId(subOrder.getSubOrderId());
			ticket.setMobile(order.getPhone());
			if (null == ticketIdMap.get(orgCode)){
				// 添加网点
//				ticketId = simpleId.nextId();
				ticketId = simpleId.ticketID();
				ticketIdMap.put(orgCode, ticketId);
			} else {
				ticketId = ticketIdMap.get(orgCode);
			}
			ticket.setTicketId(ticketId);
			ticket.setType(ProductType.presell.getCode());
			ticket.setProductId(productId);
			ticket.setProductName(product.getProductName());
			ticket.setQuantity(product.getQuantity() + product.getVipQuantity());//一个预售券对应多个商品
			ticket.setStatus(TicketStatus.init.getCode());//初始状态-待发送
			ticket.setExpireTime(expireTime);
			if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_four.getLevel())){
				ticket.setForgCode(org.getProvinceAgency());
				ticket.setSorgCode(org.getCityAgency());
				ticket.setTorgCode(org.getCountyAgency());
				//ticket.setLorgCode(merchantOrgCode);//bug 2312
				ticket.setLorgCode(orgCode);//bug 2312
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())) {
				ticket.setForgCode(org.getProvinceAgency());
				ticket.setSorgCode(org.getCityAgency());
				//ticket.setTorgCode(merchantOrgCode);//bug 2312
				ticket.setTorgCode(orgCode);//bug 2312
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())) {
				ticket.setForgCode(org.getProvinceAgency());
				//ticket.setSorgCode(merchantOrgCode);//bug 2312
				ticket.setSorgCode(orgCode);//bug 2312
			} else {
				//ticket.setForgCode(merchantOrgCode);//bug 2312
				ticket.setForgCode(orgCode);//bug 2312
			}
			
			// 提货网点
			if (product.getOrgCode() != null){
				ticket.setOrgCode(product.getOrgCode());
			}
			if (product.getOrgName() != null) {
				ticket.setOrgName(product.getOrgName());
			}
			ticket.setPrice(product.getMoney());
			
			// 添加预售券
			ticketSupport.addTicket(ticket);
			
			subList.add(ticket);
		}
		
		iterator = ticketIdMap.values().iterator();
		while (iterator.hasNext()){
			generateQrcode(ticketSupport, qrCodeLogic, iterator.next(), clientId);
		}
		
		LogCvt.info(new StringBuffer(subOrder.getSubOrderId()).append(" 子订单共生成")
				.append(subList.size()).append("张预售券记录: ")
				.append(subList.toString()).toString());
		
		ticketList.addAll(subList);
	}
	
	/**
	 * 发送团购券码
	 * 
	 * @param ticketSupport
	 * @param smsLogic
	 * @param clientId
	 * @param ticketList
	 * @return
	 */
	public void sendGroupSms(final TicketSupport ticketSupport, final SmsLogic smsLogic, final String clientId, final List<Ticket> ticketList){
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				Map<String, List<Ticket>> subOrderTicketMap = null;
				Map<String, String> subOrderMobileMap = null;
				List<String> ticketIdList = new ArrayList<String>();
//				Ticket ticket = null;
				String orderId = null;
//				String subOrderId = null;
				List<String> smsValueList = null;
//				Iterator<String> iterator = null;
//				int ticketCount = 0;
				String mobile = null;
				DBObject valueObj = null;
				
				subOrderTicketMap = new HashMap<String, List<Ticket>>();
				subOrderMobileMap = new HashMap<String, String>();
				
				
				// 把券按子订单划分
				for (int i = 0; i < ticketList.size(); i++){
					Ticket ticket = ticketList.get(i);
//					orderId = ticket.getOrderId();
					String subOrderId = ticket.getSubOrderId();
					
					ticketIdList.add(ticket.getTicketId()); // 
					
					List<Ticket> list = subOrderTicketMap.get(subOrderId);
					if (null == list){
						list = new ArrayList<Ticket>();
					}
					list.add(ticket);
					subOrderTicketMap.put(subOrderId, list);
					
					/****************券id集合****************/					
					
					
					
					if (null == subOrderMobileMap.get(subOrderId)){
						subOrderMobileMap.put(subOrderId, ticket.getMobile());
					}
				}
				
				
				/** 更新券状态为已发送 **/
				valueObj = new BasicDBObject();
				valueObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.sent.getCode());
				ticketSupport.updateByTicketIdList(valueObj, ticketIdList);
				
				int productTicketCount = 5;// 每条短信内容最多显示券码数量
				/**
				 * 根据订单号发送短信，每一个订单号发送一条短信
				 *  - 短信模板：您的团购商品{0}数量{1}，团购码为：{2}，使用有效期至{3}，具体详情请查看个人中心券码信息
				 */
				for (Map.Entry<String, List<Ticket>> kv : subOrderTicketMap.entrySet()) { // 一个商户一个子订单,多种商品
					String subOrderId = kv.getKey(); // 子订单号
					List<Ticket> list = kv.getValue(); // 子订单下面的券
					
					
//					String merchantName = list.get(0).getMerchantName(); // 商户名称
//					String outletName = list.get(0).getOutletName(); // 门店名称
					mobile = subOrderMobileMap.get(subOrderId); // 手机号码
					
					Map<String, List<Ticket>> subOrderProductTicketMap = new HashMap<String, List<Ticket>>();
					for (Ticket ticket1 : list) { // 子订单下面的券
						
						List<Ticket> subOrderTicket = subOrderProductTicketMap.get(ticket1.getProductId());
						if (null == subOrderTicket) {
							subOrderTicket = new ArrayList<Ticket>();
						}
						subOrderTicket.add(ticket1);
						subOrderProductTicketMap.put(ticket1.getProductId(), subOrderTicket);
					}
					
					/** 每种商品发送一条短信 **/
					for (Map.Entry<String, List<Ticket>> subOrderKV : subOrderProductTicketMap.entrySet()) { // 一种商品,多个券
						StringBuilder ticketIdStr = new StringBuilder("");
						
						String productId = subOrderKV.getKey();
						List<Ticket> productTicktLists = subOrderKV.getValue(); // 同一商品下面的券
						
						String productName = productTicktLists.get(0).getProductName();
						
						int count = productTicketCount > productTicktLists.size() ? productTicktLists.size() : productTicketCount;
						for (int i = 0; i < count; i++) {
							ticketIdStr.append(productTicktLists.get(i).getTicketId()).append("，");
						}
						ticketIdStr.delete(ticketIdStr.length() - 1, ticketIdStr.length()); // 去掉最后的分隔符
						if (productTicktLists.size() > productTicketCount) {
							ticketIdStr.append(" 等");
						}
						
						smsValueList = new ArrayList<String>();
//						smsValueList.add(merchantName);//参数0
//						smsValueList.add(outletName);//参数0
						smsValueList.add(productName.toString());//参数0
						smsValueList.add(String.valueOf(productTicktLists.size()));//参数1
						smsValueList.add(ticketIdStr.toString());//参数2
						smsValueList.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT4, new Date(productTicktLists.get(0).getExpireTime())));//参数3
						
						LogCvt.info("团购订单发码短信参数: " + JSonUtil.toJSonString(smsValueList));
						
						if (smsLogic.sendSMS(mobile, SmsType.groupTicket.getCode(), clientId, smsValueList)) {
							LogCvt.info(new StringBuffer("团购订单：").append(orderId)
									.append(" 团购子订单：").append(subOrderId)
									.append(" 商品id：").append(productId).append(" 商品名称：").append(productName)
									.append(" 购买数量：").append(productTicktLists.size())
									.append(" 团购码：").append(ticketIdStr.toString())
									.append(" 已异步发送到手机：").append(mobile).toString());
						} else {
							LogCvt.info(new StringBuffer("团购订单：").append(orderId)
									.append(" 团购子订单：").append(subOrderId)
									.append(" 商品id：").append(productId).append(" 商品名称：").append(productName)
									.append(" 购买数量：").append(productTicktLists.size())
									.append(" 团购码：").append(ticketIdStr.toString())
									.append(" 异步发送失败：").append(mobile).toString());
						}
					}
					
					
				}
				
				
			}
		});
		
	}

	/**
	 * 发送预售提醒短信
	 * 
	 * @param phone
	 * @param smsType
	 * @param clientId
	 * @param orderId
	 * @param smsLogic
	 */
	private void sendPresellSms(final String phone, final int smsType,
			final String clientId, final String orderId, final SmsLogic smsLogic) {
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				if (smsLogic.sendSMS(phone, smsType, clientId, null)){
					LogCvt.info(new StringBuffer("预售订单：").append(orderId)
							.append(" 预售提醒已发送到手机：").append(smsType).toString());
				} else {
					LogCvt.info(new StringBuffer("预售订单：").append(orderId)
							.append(" 发送失败：").append(smsType).toString());
				}
			}
		});
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TicketVerifyResponseVo verifyTickets(TicketVerifyRequestVo requestVo) {
		List<TicketSummaryVo> summaryList = null;
		List<String> ticketIdList = null;
		List<String> groupTicketIdList = null;
		TicketSupport ticketSupport = null;
		TicketVerifyResponseVo responseVo = null;
		List<TicketSummaryVo> validSummaryList = null;
		List<TicketSummaryVo> inValidSummaryList = null;
		Map<String, Ticket> validTicketMap = null;
		String ticketId = null;
		List<Ticket> ticketPoList = null;
		Ticket ticketPo = null;
		ResultVo resultVo = null;
		SettlementLogic settlementLogic = null;
		ResultBean settleResultBean = null;
		Map<String, List<String>> dataMap = null;
		String outletId = null;
		String resource = null;
		Iterator<Ticket> iterator = null;
		
		resource = requestVo.getResource();
		resultVo = new ResultVo();
		responseVo = new TicketVerifyResponseVo();
		
		try {
			LogCvt.info(JSonUtil.toJSonString(requestVo.getOriginVo()));
			monitorService.send(MonitorPointEnum.Order_Ticket_Verify_Count);// 每秒验券次数
			
			summaryList = requestVo.getTicketList();
			if (null == summaryList || summaryList.size() == 0){
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("券ID不能为空");
				responseVo.setResultVo(resultVo);
				LogCvt.info("券ID不能为空");
				return responseVo;
			} else if (!resource.equals("1") && !resource.equals("2")) {
				// 个人客户端不能进行提货操作
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("个人客户端不能进行提货操作");
				responseVo.setResultVo(resultVo);
				LogCvt.info("个人客户端不能进行提货操作");
				return responseVo;
			}
			
			ticketSupport = new TicketSupportImpl();
			ticketIdList = new ArrayList<String>();
			validSummaryList = new ArrayList<TicketSummaryVo>();
			inValidSummaryList = new ArrayList<TicketSummaryVo>();
//			groupTicketIdList = new ArrayList<String>();
			settlementLogic = new SettlementLogicImpl();
			
			outletId = requestVo.getOutletId();
			resource = requestVo.getResource();
			
			for (int i = 0; i < summaryList.size(); i++){
				ticketId = summaryList.get(i).getTicketId();
				ticketIdList.add(ticketId);
			}
			
			// 获取有效状态券：排除已消费，已退款，已过期
			ticketPoList = ticketSupport.getValidTicketList(ticketIdList);
			validTicketMap = filterValidTickets(resource, requestVo.getOrgCode(), summaryList,
					ticketPoList, validSummaryList, inValidSummaryList);
			
			LogCvt.info(new StringBuffer("可消费提货码列表：").append(validTicketMap.keySet().toString()).toString());

			// 更新券的状态为已消费
			updateConsumeStatus(ticketSupport, validSummaryList, validTicketMap, requestVo);
			
			// 团购券结算
			if (resource.equals("2") && validTicketMap.size() > 0){
				groupTicketSettlement(ticketPoList, settlementLogic, requestVo.getMerchantId(), outletId);
//				iterator = validTicketMap.values().iterator();
//				while (iterator.hasNext()) {
//					ticketPo = iterator.next();
//					groupTicketIdList.add(ticketPo.getTicketId());
//				}
//				
//				settleResultBean = settlementLogic.groupSettlement(groupTicketIdList,
//						requestVo.getMerchantId(), outletId);
//				if (settleResultBean.getCode().equals(ResultCode.success.getCode())) {
//					dataMap = (Map<String, List<String>>) settleResultBean.getData();
//					LogCvt.info(new StringBuffer("结算失败券列表：").append(dataMap.get("failList")).toString());
//				} else {
//					LogCvt.info(new StringBuffer("团购券结算失败：").append(settleResultBean.getMsg()).toString());
//					monitorService.send(MonitorPointEnum.Order_Ticket_Settlement_Failed_Count);//结算异常
//				}
			}
			
			resultVo.setResultCode(ResultCode.success.getCode());
			responseVo.setResultVo(resultVo);
			responseVo.setValidTickets(validSummaryList);
			responseVo.setInValidTickets(inValidSummaryList);
			
			if (validSummaryList.size() == 0){
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("输入均为无效券");
				responseVo.setResultVo(resultVo);
			}
			
		} catch (Exception e){
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("提货码验证失败");
			responseVo.setResultVo(resultVo);
			LogCvt.error(new StringBuffer("提货码验证失败：").append(requestVo.toString()).toString(), e);
		}
		
		return responseVo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TicketVerifyOfMergerResponseVo verifyTicketsOfMerger(TicketVerifyOfMergerRequestVo requestVo) {
		List<TicketSummaryVo> summaryList = null;
//		List<String> ticketIdList = null;
		List<String> groupTicketIdList = null;
		TicketSupport ticketSupport = null;
		TicketVerifyOfMergerResponseVo responseVo = null;
		List<TicketSummaryVo> validSummaryList = null;
		List<TicketSummaryVo> noValidTickets = null;
		List<TicketSummaryVo> inValidSummaryList = null;
//		Map<String, Ticket> validTicketMap = null;
//		String ticketId = null;
		List<Ticket> ticketPoList = null;
		Ticket ticketPo = null;
		ResultVo resultVo = null;
		SettlementLogic settlementLogic = null;
		ResultBean settleResultBean = null;
		Map<String, List<String>> dataMap = null;
		String merchantId = null;
		String outletId = null;
//		String resource = null;
		
		
		OriginVo originVo = requestVo.getOriginVo();
		PlatType platType = originVo.getPlatType();
//		resource = requestVo.getResource();
		resultVo = new ResultVo();
		responseVo = new TicketVerifyOfMergerResponseVo();
		
		try {
//			LogCvt.info(JSonUtil.toJSonString(originVo));
			monitorService.send(MonitorPointEnum.Order_Ticket_Verify_Count);// 每秒验券次数
			
			
			summaryList = requestVo.getTicketList();
			if (null == summaryList || summaryList.size() == 0){
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("券ID不能为空");
				responseVo.setResultVo(resultVo);
				LogCvt.info("券ID不能为空");
				return responseVo;
			} 
//			else if (!resource.equals("1") && !resource.equals("2")) {
//				// 个人客户端不能进行提货操作
//				resultVo.setResultCode(ResultCode.failed.getCode());
//				resultVo.setResultDesc("个人客户端不能进行提货操作");
//				responseVo.setResultVo(resultVo);
//				LogCvt.info("个人客户端不能进行提货操作");
//				return responseVo;
//			}
			
			else if (platType == PlatType.personal_h5 || platType == PlatType.personal_pc) {
				// 个人客户端不能进行提货操作
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("个人客户端不能进行提货操作");
				responseVo.setResultVo(resultVo);
				LogCvt.info("个人客户端不能进行提货操作");
				return responseVo;
			}
			
			
			String memberCode = requestVo.getMemberCode(); // 提货人id
			String subOrderId = requestVo.getSubOrderId(); // 子订单id
			if(StringUtils.isEmpty(memberCode) && StringUtils.isEmpty(subOrderId)){
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("提货人和子订单ID不能同时为空");
				responseVo.setResultVo(resultVo);
				LogCvt.info("提货人和子订单ID不能同时为空");
			}
			
			ticketSupport = new TicketSupportImpl();
//			ticketIdList = new ArrayList<String>();
			validSummaryList = new ArrayList<TicketSummaryVo>();
			inValidSummaryList = new ArrayList<TicketSummaryVo>();
			noValidTickets = new ArrayList<TicketSummaryVo>();
//			groupTicketIdList = new ArrayList<String>();
			settlementLogic = new SettlementLogicImpl();
			
			merchantId = requestVo.getMerchantId();
			outletId = requestVo.getOutletId();
			if (StringUtils.isBlank(outletId) || outletId.equals("0")) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("门店ID不能为空");
				responseVo.setResultVo(resultVo);
				LogCvt.info("门店ID不能为空");
				return responseVo;
			}
//			resource = requestVo.getResource();
			
//			for (int i = 0; i < summaryList.size(); i++){
//				ticketId = summaryList.get(i).getTicketId();
//				ticketIdList.add(ticketId);
//			}
			
			List<MemberProductVerifyInfoVo> memberProductVerifyInfos = new ArrayList<MemberProductVerifyInfoVo>();
//			Map<String, Integer> validProductsCount = new HashMap<String, Integer>(); // 本次已经提货数量 key--商品id, value本次已提货数量
//			Map<String, Integer> noValidProductsCount = new HashMap<String, Integer>(); // 剩余可提货数量 key--商品id, value剩余可提货数量
			// 获取有效状态券：排除已消费，已退款，已过期
//			ticketPoList = ticketSupport.getValidTicketList(ticketIdList);
//			ticketPoList = ticketSupport.getValidTicketList(memberCode, subOrderId, merchantId, outletId); // 根据子订单id,提货人id,商户id,门店id获取有效券券详细列表
			Ticket queryTikect = new Ticket();			
			queryTikect.setType(OrderType.group_merchant.getCode()); // 查询团购订单
			queryTikect.setStatus(TicketStatus.sent.getCode());
			queryTikect.setMerchantId(merchantId);
//			queryTikect.setOutletId(outletId);
			queryTikect.setMemberCode(memberCode);
			queryTikect.setSubOrderId(subOrderId);
			ticketPoList = ticketSupport.getTicketList(queryTikect);  // 获取有效券券详细列表
			if (Checker.isEmpty(ticketPoList)) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("用户在本商户无有效券");
				responseVo.setResultVo(resultVo);
			}
			ticketPoList = filterValidGroupTickets(platType,requestVo.getMustValidTicketId(), summaryList,
					ticketPoList, validSummaryList, inValidSummaryList, noValidTickets, memberProductVerifyInfos);
			
			// 更新券的状态为已消费
			updateGroupConsumeStatus(ticketSupport, ticketPoList, requestVo);
			
			// 团购券结算
			if ((platType == PlatType.merchant_h5 || platType == PlatType.merchant_pc) && ticketPoList.size() > 0){
//				iterator = validTicketMap.values().iterator();
				groupTicketSettlement(ticketPoList, settlementLogic, merchantId, outletId);
			}
			
			resultVo.setResultCode(ResultCode.success.getCode());
			responseVo.setResultVo(resultVo);
			responseVo.setValidTickets(validSummaryList);
			responseVo.setInValidTickets(inValidSummaryList);
//			responseVo.setValidProductsCount(validProductsCount);
//			responseVo.setNoValidProductsCount(noValidProductsCount);
//			responseVo.setInValidTickets(inValidSummaryList);
			responseVo.setNoValidTickets(noValidTickets);
			responseVo.setMemberProductVerifyInfos(memberProductVerifyInfos);
			
			if (validSummaryList.size() == 0){
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("输入均为无效券");
				responseVo.setResultVo(resultVo);
			}
			
		} catch (Exception e){
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("提货码验证失败");
			responseVo.setResultVo(resultVo);
			LogCvt.error(new StringBuffer("提货码验证失败：").append(requestVo.toString()).toString(), e);
		}
		
		return responseVo;
	}

	
	/**
	 * 团购券结算
	 * @Title: groupTicketSettlement 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param groupTicketIdList
	 * @param ticketPoList
	 * @param settlementLogic
	 * @param merchantId
	 * @param outletId
	 * @return void    返回类型 
	 * @throws
	 */
	private void groupTicketSettlement(List<Ticket> ticketPoList, SettlementLogic settlementLogic, String  merchantId, String outletId) {
		List<String> groupTicketIdList = new ArrayList<String>();
		Ticket ticketPo;
		ResultBean settleResultBean;
		Map<String, List<String>> dataMap;
		Iterator<Ticket> iterator = ticketPoList.iterator();
		while (iterator.hasNext()) {
			ticketPo = iterator.next();
			groupTicketIdList.add(ticketPo.getTicketId());
		}
		
		settleResultBean = settlementLogic.groupSettlement(groupTicketIdList,merchantId, outletId);
		if (settleResultBean.getCode().equals(ResultCode.success.getCode())) {
			dataMap = (Map<String, List<String>>) settleResultBean.getData();
			LogCvt.info(new StringBuffer("结算失败券列表：").append(dataMap.get("failList")).toString());
		} else {
			LogCvt.info(new StringBuffer("团购券结算失败：").append(settleResultBean.getMsg()).toString());
			monitorService.send(MonitorPointEnum.Order_Ticket_Settlement_Failed_Count);//结算异常
		}
	}
	/**
	 * 生成券二维码
	 * 
	 * @param ticketSupport
	 * @param qrCodeLogic
	 * @param ticketId
	 * @param clientId
	 */
	private void generateQrcode(final TicketSupport ticketSupport, final QrCodeLogic qrCodeLogic, final String ticketId, final String clientId){
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				StringBuffer keyword = null;
				QrCodeRequestVo request = null;
				QrCodeResponseVo response = null;
				String imageUrl = null;
				DBObject queryObj = null;
				DBObject valueObj = null;
				
				try {
					keyword = new StringBuffer();
					keyword.append(QrCodeType.TICKET.getCode());
					keyword.append(ticketId);
					
					request = new QrCodeRequestVo();
					request.setClientId(clientId);
					request.setKeyword(keyword.toString());
					
					response = qrCodeLogic.generateQrCode(request);
					
					if (response.getResultCode().equals(ResultCode.success.getCode())){
						imageUrl = response.getUrl();
						LogCvt.info(new StringBuffer(keyword).append("异步二维码生成成功.").toString());
						queryObj = new BasicDBObject();
						queryObj.put(FieldMapping.TICKET_ID.getMongoField(), ticketId);
						queryObj.put(FieldMapping.IMAGE.getMongoField(), new BasicDBObject(QueryOperators.EXISTS , false));
						valueObj = new BasicDBObject();
						valueObj.put(FieldMapping.IMAGE.getMongoField(), imageUrl);
						ticketSupport.updateMultiByDBObject(queryObj, valueObj);
					} else {
						LogCvt.info(new StringBuffer(keyword).append("异步二维码生成失败. ")
								.append(response.getResultDesc()).toString());
					}
					
				} catch (Exception e){
					// 二维码生成异常
					monitorService.send(MonitorPointEnum.Order_Ticket_Qrcode_Gen_Failed_Count);
				}
			}
		});
	}
	
	/**
	 * 更新券为已消费
	 * 
	 * @param ticketSupport
	 * @param summaryList
	 * @param ticketMap
	 * @param merchantUserId
	 * @param outletId
	 * @param requestVo
	 */
	private void updateConsumeStatus(TicketSupport ticketSupport,
			List<TicketSummaryVo> summaryList,
			Map<String, Ticket> ticketMap, TicketVerifyRequestVo requestVo) {
		TicketSummaryVo summary = null;
		Ticket ticket = null;
		Ticket newItem = null;
		DBObject valueObj = null;
		String objId = null;
		StringBuffer ticketKey = null;
		StringBuffer subOrderKey = null;
		Map<String, Ticket> subOrderMap = null;
		Iterator<Ticket> iterator = null;
		OrderSupport orderSupport = null;
		
		ticketKey = new StringBuffer();
		subOrderKey = new StringBuffer();
		subOrderMap = new HashMap<String, Ticket>();
		orderSupport = new OrderSupportImpl();
		
		for (int i = 0; i < summaryList.size(); i++){
			summary = summaryList.get(i);
			
			ticketKey.delete(0, ticketKey.length());
			ticketKey.append(summary.getTicketId()).append(summary.getProductId());
			ticket = ticketMap.get(ticketKey.toString());
			
			valueObj = new BasicDBObject();
			valueObj.put(FieldMapping.MERCHANT_USER_ID.getMongoField(), requestVo.getMerchantUserId());
			valueObj.put(FieldMapping.MERCHANT_USER_NAME.getMongoField(), requestVo.getMerchantUserName());
			valueObj.put(FieldMapping.OUTLET_ID.getMongoField(), requestVo.getOutletId());
			valueObj.put(FieldMapping.OUTLET_NAME.getMongoField(), requestVo.getOutletName());
			valueObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.consumed.getCode());
			valueObj.put(FieldMapping.CONSUME_TIME.getMongoField(), System.currentTimeMillis());
			
			// 部分提货，券记录一分为二
			if (ticket.getType().equals(SubOrderType.presell_org.getCode())
					&& summary.getQuantity() < ticket.getQuantity()) {
				valueObj.put(FieldMapping.QUANTITY.getMongoField(), summary.getQuantity());
				
				newItem = (Ticket) BeanUtil.copyProperties(Ticket.class, ticket);
				newItem.set_id(null);
				newItem.setMerchantUserId(requestVo.getMerchantUserId());
				newItem.setMerchantUserName(requestVo.getMerchantUserName());
				newItem.setStatus(TicketStatus.sent.getCode());//剩余待消费状态
				newItem.setQuantity(ticket.getQuantity() - summary.getQuantity());
				newItem.setCreateTime(ticket.getCreateTime()); /** 修复bug，预售部分验券拆分券的时候create_time改变了 author vania by 2015-08-26 */
			} else {
				newItem = null;
			}
			
			// 需要更新配送状态的子订单
			if (ticket.getType().equals(SubOrderType.presell_org.getCode())){
				subOrderKey.delete(0, subOrderKey.length());
				subOrderKey.append(ticket.getOrderId()).append(ticket.getSubOrderId()).append(ticket.getProductId());
				if (null == subOrderMap.get(subOrderKey.toString())) {
					ticket.setQuantity(ticket.getQuantity() - summary.getQuantity());//剩余可提货数量
					subOrderMap.put(subOrderKey.toString(), ticket);
				}
			}
			
			try {
				objId = ticket.get_id();
				LogCvt.info(new StringBuffer("更新提货码为已消费：").append(ticket.getTicketId()).toString());
				ticketSupport.updateById(valueObj, JSonUtil.toObjectId(objId));
				
				// 预售部分退款，插入新记录，同一券ID
				if (null != newItem){
					LogCvt.info(new StringBuffer("拆分新的预售未消费记录：").append(ticket.getTicketId()).toString());
					ticketSupport.addTicket(newItem);
				}
			} catch (Exception e){
				// 提货时券状态更新异常
				monitorService.send(MonitorPointEnum.Order_Ticket_Update_Ticket_Status_Failed_Count);
			}
		}
		
		// 更新子订单提货状态
		iterator = subOrderMap.values().iterator();
		while (iterator.hasNext()) {
			ticket = iterator.next();
			
			// 如该子订单下商品已消费，更新该商品配送状态为已提货
			if (ticket.getQuantity() == 0) {
				try {
					// 预售商品更新子订单下商品配送状态
					LogCvt.info(new StringBuffer("更新预售商品提货为已提货：").append(ticket.getSubOrderId()).toString());
					orderSupport.updateSubOrderAfterConsumed(
							ticket.getOrderId(), ticket.getSubOrderId(),
							ticket.getProductId(), ProductType.presell,
							requestVo.getOutletId(),
							ShippingStatus.token.getCode());
				} catch (Exception e) {
					// 订单提货状态更新异常
					monitorService
							.send(MonitorPointEnum.Order_Ticket_Update_Suborder_Status_Failed_Count);
				}
			}
		}
	}
	
	/**
	 * 修改团购订单
	 * @Title: updateGroupConsumeStatus 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param ticketSupport
	 * @param ticketPoList
	 * @param requestVo
	 * @return void    返回类型 
	 * @throws
	 */
	private void updateGroupConsumeStatus(TicketSupport ticketSupport,
			List<Ticket> ticketPoList, TicketVerifyOfMergerRequestVo requestVo) {
//		Ticket summary = null;
		Ticket ticket = null;
//		Ticket newItem = null;
		DBObject valueObj = null;
		String objId = null;
//		StringBuffer ticketKey = null;
//		StringBuffer subOrderKey = null;
//		Map<String, Ticket> subOrderMap = null;
//		Iterator<Ticket> iterator = null;
//		OrderSupport orderSupport = null;
		
//		ticketKey = new StringBuffer();
//		subOrderKey = new StringBuffer();
//		subOrderMap = new HashMap<String, Ticket>();
//		orderSupport = new OrderSupportImpl();
		
		for (int i = 0; i < ticketPoList.size(); i++){
			ticket = ticketPoList.get(i);
			
			
			valueObj = new BasicDBObject();
			valueObj.put(FieldMapping.MERCHANT_USER_ID.getMongoField(), requestVo.getOriginVo().getOperatorId());
			valueObj.put(FieldMapping.MERCHANT_USER_NAME.getMongoField(), requestVo.getMerchantUserName());
			valueObj.put(FieldMapping.OUTLET_ID.getMongoField(), requestVo.getOutletId());
			valueObj.put(FieldMapping.OUTLET_NAME.getMongoField(), requestVo.getOutletName());
			valueObj.put(FieldMapping.STATUS.getMongoField(), TicketStatus.consumed.getCode());
			valueObj.put(FieldMapping.CONSUME_TIME.getMongoField(), System.currentTimeMillis());
			
		
			
			try {
				objId = ticket.get_id();
				ticketSupport.updateById(valueObj, JSonUtil.toObjectId(objId));
				
				
			} catch (Exception e){
				LogCvt.error("修改团购订单失败, 原因:" + e.getMessage(), e);
				// 提货时券状态更新异常
				monitorService.send(MonitorPointEnum.Order_Ticket_Update_Ticket_Status_Failed_Count);
			}
		}
		
	}
	
	/**
	 * 筛选有效券
	 * 
	 * @param resource
	 * @param orgCode
	 * @param summaryList
	 * @param ticketPoList
	 * @param validSummaryList
	 * @param inValidSummaryList
	 * @return
	 */
	private Map<String, Ticket> filterValidTickets(
			String resource,
			String orgCode,
			List<TicketSummaryVo> summaryList,
			List<Ticket> ticketPoList,
			List<TicketSummaryVo> validSummaryList,
			List<TicketSummaryVo> inValidSummaryList) {
		TicketSummaryVo ticketSummary = null;
		Map<String, Ticket> tmpTicketMap = null;
		Map<String, Ticket> validTicketMap = null;
		StringBuffer key = null;
		Ticket ticket = null;
		
		// key = ticketId + productId
		tmpTicketMap = new HashMap<String, Ticket>();
		validTicketMap = new HashMap<String, Ticket>();
		key = new StringBuffer();
		
		for (int i = 0; i < ticketPoList.size(); i++) {
			ticket = ticketPoList.get(i);
			key.delete(0, key.length());
			key.append(ticket.getTicketId()).append(ticket.getProductId());
			tmpTicketMap.put(key.toString(), ticket);
		}
		
		for (int i = 0; i < summaryList.size(); i++){
			ticketSummary = summaryList.get(i);
			key.delete(0, key.length());
			key.append(ticketSummary.getTicketId()).append(ticketSummary.getProductId());
			
			ticket = tmpTicketMap.get(key.toString());
			if (null == ticket){
				// 无效券
				ticketSummary.setRemark("无效提货码");
				inValidSummaryList.add(ticketSummary);
				LogCvt.info(new StringBuffer("无效券：").append(JSonUtil.toJSonString(ticketSummary.getTicketId())).toString());
			} else {
				if (ticketSummary.getQuantity() > ticket.getQuantity()) {
					// 无效券
					ticketSummary.setRemark("提货数量超出可提商品数量");
					inValidSummaryList.add(ticketSummary);
				} else {
					if (resource.equals("1") && ticket.getType().equals(SubOrderType.group_merchant.getCode())) {
						ticketSummary.setRemark("团购提货码只能只能在普通商户提货");
						inValidSummaryList.add(ticketSummary);
					} else if (resource.equals("2") && ticket.getType().equals(SubOrderType.presell_org.getCode())) {
						ticketSummary.setRemark("预售提货码不能在普通商户提货");
						inValidSummaryList.add(ticketSummary);
					} else if (ticket.getType().equals(SubOrderType.presell_org.getCode()) && !ticket.getOrgCode().equals(orgCode)) {
						// bug 2302 start
						ticketSummary.setRemark(new StringBuffer("请到指定的网点提货：").append(ticket.getOrgName()).toString());
						inValidSummaryList.add(ticketSummary);
						// bug 2302 start
					} else {
						// 有效券
						validSummaryList.add(ticketSummary);
						validTicketMap.put(key.toString(), ticket);
					}
				}

			}
		}
		
		return validTicketMap;
	}
	
	/**
	 * 筛选有效团购券数量
	 * @Title: filterValidGroupTickets 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param platType 平台类型
	 * @param mustValidTicketId 必须要验证的券号(客户提供给门店操作员的券号)
	 * @param summaryList 上送的券列表
	 * @param ticketPoList 数据库的券列表
	 * @param validSummaryList 本次验证成功的券列表
	 * @param inValidSummaryList 本次验证失败的券列表
	 * @param noValidTickets 本次剩余券列表 
	 * @param validProductsCount 本次验证成功商品数量(key为商品id   value为数量)
	 * @param noValidProductsCount 未验证商品数量(key为商品id   value为数量)
	 * @return
	 * @return List<Ticket>    返回类型 
	 * @throws
	 */
	private List<Ticket> filterValidGroupTickets(
			PlatType  platType,
			String mustValidTicketId ,
			List<TicketSummaryVo> summaryList, 
			List<Ticket> ticketPoList,
			List<TicketSummaryVo> validSummaryList,
			List<TicketSummaryVo> inValidSummaryList,
			List<TicketSummaryVo> noValidTickets,
			List<MemberProductVerifyInfoVo> memberProductVerifyInfos) {
		TicketSummaryVo ticketSummary = null;
//		Map<String, Ticket> validTicketMap = null;
		StringBuffer key = null;
		Ticket ticket = null;
		
		List<Ticket> validTicketList = new ArrayList<Ticket>();
		
		// key = ticketId + productId
//		tmpTicketMap = new HashMap<String, Ticket>();
//		validTicketMap = new HashMap<String, Ticket>();
		key = new StringBuffer();
		
//		for (int i = 0; i < ticketPoList.size(); i++) {
//			ticket = ticketPoList.get(i);
//			key.delete(0, key.length());
//			key.append(ticket.getTicketId()).append(ticket.getProductId());
//			tmpTicketMap.put(key.toString(), ticket);
//		}
		Map<String, List<Ticket>> map = new HashMap<String, List<Ticket>>(); // key 为商品id-----------value 商品id有多少个券
		for (Ticket ti : ticketPoList) {
			String keys = ti.getProductId();
			List<Ticket> values = map.get(keys);
			if(null == values) {
				values = new ArrayList<Ticket>();
			}
			values.add(ti);
//			map.put(keys, null == values ? 1 : values + 1);
			map.put(keys, values);
		}
		for (int i = 0; i < summaryList.size(); i++){
			ticketSummary = summaryList.get(i);
			
			int q = ticketSummary.getQuantity(); // 提货数量
			q = q == 0 ? 1 : q; // 默认提1个
			
			String productId = ticketSummary.getProductId();
			List<Ticket> countProductTicket = map.get(productId);
			int size = countProductTicket == null ? 0 : countProductTicket.size();
			
			int validCount = q < size ? q : size; // 本次提货数量
			
			LogCvt.info("商品[ID:" + productId + (size > 0 ? "\t名称:" + countProductTicket.get(0).getProductName() : "") + "], 数据库可提货[" + (size > 0 ? countProductTicket.size() : 0) + "]件, 客户本次需要提货[" + q + "]件, 本次实际可提货[" + validCount + "]件");
			
			if (size == 0 ) { // 提货数量超出可提商品数量
				ticketSummary.setRemark("商品无可提货数量");
				inValidSummaryList.add(ticketSummary);
			} else if(q > countProductTicket.size()){
				ticketSummary.setRemark("提货数量超出可提商品数量");
//				inValidSummaryList.add(ticketSummary);				
			}
			
//			boolean isExists  = false;
			int isExistsIndex = -1;
			for (int j = 0; j < size; j++) {
				Ticket ticket2 = countProductTicket.get(j);
				key.delete(0, key.length());
				key.append(ticket2.getTicketId()).append(ticket2.getProductId());
				
				String poTicketId = ticket2.getTicketId();
				boolean isExists = StringUtils.equals(mustValidTicketId, poTicketId);
				if (isExists && j >= validCount)
					isExistsIndex = validTicketList.size();
				if(isExists || j < validCount) { // 本次可提货
					validTicketList.add(ticket2);// 本次验证券po列表
					
					validSummaryList.add((TicketSummaryVo) BeanUtil.copyProperties(TicketSummaryVo.class, ticket2)); // 本次验证券vo列表
				} else { // 本次未提货
					noValidTickets.add((TicketSummaryVo) BeanUtil.copyProperties(TicketSummaryVo.class, ticket2)); // 本次剩余未验证券vo列表
				}
//					validTicketMap.put(key.toString(), ticket2);
			}
			if (isExistsIndex > -1) { // 必须要验的这个券 在集合的最后
				validTicketList.remove(isExistsIndex - 1); // 本次验证的po 把之前的一个给移除掉
				noValidTickets.add(validSummaryList.get(isExistsIndex - 1)); // 本次验证的vo 把之前的一个给   挪到 本次未验证vo列表中
				validSummaryList.remove(isExistsIndex - 1);// 本次验证的vo把之前的一个给移除掉
			}
			
			
			MemberProductVerifyInfoVo mpi = new MemberProductVerifyInfoVo();
			mpi.setProductId(ticketSummary.getProductId());
			mpi.setProductName(countProductTicket == null || countProductTicket.size() == 0 ? "" : countProductTicket.get(0).getProductName());
			mpi.setValidProductsCount(validCount);
			int residue = size - validCount; // 剩余提货数
			mpi.setNoValidProductsCount(residue);
			memberProductVerifyInfos.add(mpi);
//			validProductsCount.put(ticketSummary.getProductId(), validCount); // 商品本次提货数量
//			noValidProductsCount.put(ticketSummary.getProductId(), residue); // 商品剩余提货数
			
			
			/*
			key.delete(0, key.length());
			key.append(ticketSummary.getTicketId()).append(ticketSummary.getProductId());
			
			ticket = tmpTicketMap.get(key.toString());
			if (null == ticket){
				// 无效券
				ticketSummary.setRemark("无效提货码");
				inValidSummaryList.add(ticketSummary);
				LogCvt.info(new StringBuffer("无效券：").append(JSonUtil.toJSonString(ticketSummary.getTicketId())).toString());
			} else {
				if (ticketSummary.getQuantity() > ticket.getQuantity()) {
					// 无效券
					ticketSummary.setRemark("提货数量超出可提商品数量");
					inValidSummaryList.add(ticketSummary);
				} else {
					if ((platType != PlatType.merchant_h5 && platType != PlatType.merchant_pc) && ticket.getType().equals(SubOrderType.group_merchant.getCode())) {
						ticketSummary.setRemark("团购提货码只能只能在普通商户提货");
						inValidSummaryList.add(ticketSummary);
					} else if ((platType == PlatType.merchant_h5 || platType == PlatType.merchant_pc) && ticket.getType().equals(SubOrderType.presell_org.getCode())) {
						ticketSummary.setRemark("预售提货码不能在普通商户提货");
						inValidSummaryList.add(ticketSummary);
					} else if (ticket.getType().equals(SubOrderType.presell_org.getCode()) && !ticket.getOrgCode().equals(orgCode)) {
						// bug 2302 start
						ticketSummary.setRemark(new StringBuffer("请到指定的网点提货：").append(ticket.getOrgName()).toString());
						inValidSummaryList.add(ticketSummary);
						// bug 2302 start
					} else {
						// 有效券
						validSummaryList.add(ticketSummary);
						validTicketMap.put(key.toString(), ticket);
					}
				}

			}
			*/
		}
		
//		return validTicketMap;
		return validTicketList;
	}
	
	/**
	 * 获取配送人信息
	 * 
	 * @param order
	 * @param orderSupport
	 * @return
	 */
	private RecvInfo getDeliveryInfo(OrderMongo order, OrderSupport orderSupport){
		RecvInfo deliveryInfo = null;
		
		//提货人
		if(null != order.getDeliverId() && !order.getDeliverId().trim().equals("")){
			deliveryInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), order.getDeliverId());
		}
		
		//收货人
		if(null == deliveryInfo && null != order.getRecvId() && !order.getRecvId().trim().equals("")){
			deliveryInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), order.getRecvId());
		}
		
		return deliveryInfo;
	}
}
