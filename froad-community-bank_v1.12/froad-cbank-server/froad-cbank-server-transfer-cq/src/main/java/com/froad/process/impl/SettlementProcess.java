package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.cbank.persistent.common.enums.SettleState;
import com.froad.cbank.persistent.entity.Payment;
import com.froad.cbank.persistent.entity.Ticket;
import com.froad.common.mongo.CommonMongo;
import com.froad.common.redis.MerchantRedis;
import com.froad.common.redis.OutletRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.OutletProduct;
import com.froad.db.chonggou.entity.Settlement;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.entity.OrderEx;
import com.froad.db.chongqing.mappers.OrderMapper;
import com.froad.db.chongqing.mappers.PaymentMapper;
import com.froad.db.chongqing.mappers.TicketMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ModuleID;
import com.froad.enums.SettlementStatus;
import com.froad.enums.SettlementType;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *  结算交易
  * @ClassName: SettlementProcess
  * @Description: TODO
  * @author share 2015年7月23日
  * @modify share 2015年7月23日
 */
public class SettlementProcess extends AbstractProcess {

	

	private SimpleID simple = new SimpleID(ModuleID.settlement);
	private Map<String,String> orderMap = new HashMap<String, String>();
	private Map<String,String> productMap = new HashMap<String, String>();
	private Map<String,String> merchantMap = new HashMap<String, String>();
	private Map<String,String> outlettMap = new HashMap<String, String>();
	private Map<String,String> merchantUserMap = new HashMap<String, String>();
	private Map<String,String> paymentMap = new HashMap<String, String>();
	
	RedisManager manager = new RedisManager();
	MerchantRedis merRedis = new MerchantRedis(manager);
	OutletRedis outRedis = new OutletRedis(manager);
	
	public SettlementProcess(String name, ProcessServiceConfig config) {
		super(name, config);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void before() {
		// TODO Auto-generated method stub
		DBObject dbo = new BasicDBObject();
		dbo.put("client_id", Constans.clientId);
		
		mongo.remove(dbo, MongoTableName.CB_SETTLEMENT);
		
		loadData();
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
		/**
		 *  面对面订单结算记录转移
		 */
		face2face();
		
		/**
		 *  团购结算记录转移
		 */
		group();

	}
	
	
	/**
	 *  团购结算
	  * @Title: group
	  * @Description: TODO
	  * @author: share 2015年7月24日
	  * @modify: share 2015年7月24日
	  * @param     
	  * @return void    
	  * @throws
	 */
	private void group(){
		TicketMapper ticketMapper = cqSqlSession.getMapper(TicketMapper.class);
		
		List<Ticket> groupTicket = ticketMapper.selectTicketByGroup();
		for(Ticket ticket : groupTicket){
			// 订单ID
			String orderIds = orderMap.get(String.valueOf(ticket.getOrderId()));
			String orderId = orderIds.split(",")[0];
			// 结算类型
			String type = SettlementType.group.getCode();
			// 商户ID
			String merchantId = merchantMap.get(ticket.getMerchantId()+"");
			// 客户端ID
			String clientId = Constans.clientId;
			// 商户名称
			Map<String,String> merchantRedis = merRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
			String merchantName = "";
			if(merchantRedis != null && merchantRedis.size() > 0){
				merchantName = merchantRedis.get("merchant_name");
			}
			// 门店ID
			String outletId = null;
			String outletName = null;
			if(ticket.getMerchantOutletId() != null){
				outletId = outlettMap.get(ticket.getMerchantOutletId()+"");
				Map<String,String> outletRedis = outRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(clientId,merchantId,outletId);
				if(outletRedis != null && outletRedis.size() > 0){
					outletName = outletRedis.get("outlet_name");
				}
			}
			// 结算状态
			String settlementStatus = ticket.getSettleState()?SettlementStatus.settlementsucc.getCode():SettlementStatus.settlementfailed.getCode();
			
			// 支付id
			String paymentId = null;
			if(SettlementStatus.settlementsucc.getCode().equals(settlementStatus)){
				paymentId = paymentMap.get(ticket.getOrderId()+ticket.getId().toString());
				if(paymentId == null){
					LogCvt.error("结算PaymentID获取失败,OrderId:"+paymentId);
				}
			}
			
			// 消费时间
			long createTime = ticket.getCreateTime().getTime();
			// 金额
			int money = Arith.mul(Double.parseDouble(ticket.getMoney()), 1000);
			// 商品数量
			int productCount = 1;
			// 商品ID
			OutletProduct outletProduct = CommonMongo.queryOutletProduct(merchantId, outletId, money);
			String productId = outletProduct==null?"":outletProduct.getProductId();
			// 商品名称
			String productName = ticket.getProductFullName();
			// 门店操作员
			Long merchantUserId = null;
			try {
				merchantUserId = Long.parseLong(merchantUserMap.get(ticket.getMerchantUserId()+""));
			} catch (Exception e){
				LogCvt.error("merchantUserId not found: " + ticket.getMerchantUserId());
			}
			
			//  卷码
			List<String> tickets = new ArrayList<String>();
			tickets.add(ticket.getSecuritiesNo().substring(2));
			// 存储结算记录信息
			Settlement settelment = new Settlement();
			settelment.setSettlementId(simple.nextId());
			settelment.setClientId(clientId);
			settelment.setMerchantId(merchantId);
			settelment.setMerchantName(merchantName);
			settelment.setMerchantUserId(merchantUserId);
			settelment.setMoney(money);
			settelment.setOrderId(orderId);
			settelment.setOutletId(outletId);
			settelment.setOutletName(outletName);
			settelment.setPaymentId(paymentId);
			settelment.setProductCount(productCount);
			settelment.setProductId(productId);
			settelment.setProductName(productName);
			settelment.setType(type);
			// 结算状态
			settelment.setSettleState(settlementStatus);
			settelment.setCreateTime(createTime);
			settelment.setTickets(tickets);
			mongo.add(settelment, MongoTableName.CB_SETTLEMENT);
		}
		
		
	}
	
	/**
	 *  面对面结算
	  * @Title: face2face
	  * @Description: TODO
	  * @author: share 2015年7月24日
	  * @modify: share 2015年7月24日
	  * @param     
	  * @return void    
	  * @throws
	 */
	private void face2face() {
		// 面对面结算订单
		OrderMapper ordrMapper = cqSqlSession.getMapper(OrderMapper.class);
		/**
		 * 查询面对面订单
		 */
		List<OrderEx> orderList = ordrMapper.selectFaceSettelment();
		
		for(OrderEx order : orderList){
			// 订单ID
			String orderIds = orderMap.get(String.valueOf(order.getId()));
			String orderId = orderIds.split(",")[0];
			// 结算类型
			String type = SettlementType.face2face.getCode();
			// 商户ID
			String merchantId = merchantMap.get(order.getMerchantId()+"");
			// 客户端ID
			String clientId = Constans.clientId;
			// 商户名称
			Map<String,String> merchantRedis = merRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
			String merchantName = "";
			if(merchantRedis != null && merchantRedis.size() > 0){
				merchantName = merchantRedis.get("merchant_name");
			}
			// 门店ID
			String outletId = null;
			String outletName = null;
			if(order.getMerchantOutletId() != null){
				outletId = outlettMap.get(order.getMerchantOutletId()+"");
				Map<String,String> outletRedis = outRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(clientId,merchantId,outletId);
				if(outletRedis != null && outletRedis.size() > 0){
					outletName = outletRedis.get("outlet_name");
				}
			}
			// 结算状态
			String settlementStatus = getSettlementStatus(order.getSettleState());
			
			// 支付id
			String paymentId = null;
			if(SettlementStatus.settlementsucc.getCode().equals(settlementStatus)){
				paymentId = paymentMap.get(order.getId()+"");
				if(paymentId == null){
					LogCvt.error("结算PaymentID获取失败,OrderId:"+paymentId);
				}
			}
			
			// 消费时间
			long createTime = order.getCreateTime().getTime();
			// 金额
			int money = Arith.mul(Double.parseDouble(order.getGatheringValue()), 1000);
			// 商品数量
			int productCount = 1;
			// 商品ID
			OutletProduct outletProduct = CommonMongo.queryOutletProduct(merchantId, outletId, money);
			String productId = outletProduct==null?"":outletProduct.getProductId();
			// 商品名称
			String productName = "面对面结算";
			// 门店操作员
			Long merchantUserId = 0l;
			try {
				merchantUserId = Long.parseLong(merchantUserMap.get(order.getMerchantUserId()+""));
			} catch (Exception e) {
				// TODO: handle exception
				LogCvt.error("无面对面结算门店操作员,order_id:"+order.getId());
			}
			
			// 存储结算记录信息
			Settlement settelment = new Settlement();
			settelment.setSettlementId(simple.nextId());
			settelment.setClientId(clientId);
			settelment.setMerchantId(merchantId);
			settelment.setMerchantName(merchantName);
			settelment.setMerchantUserId(merchantUserId);
			settelment.setMoney(money);
			settelment.setOrderId(orderId);
			settelment.setOutletId(outletId);
			settelment.setOutletName(outletName);
			settelment.setPaymentId(paymentId);
			settelment.setProductCount(productCount);
			settelment.setProductId(productId);
			settelment.setProductName(productName);
			settelment.setType(type);
			// 结算状态
			settelment.setSettleState(settlementStatus);
			settelment.setCreateTime(createTime);
			mongo.add(settelment, MongoTableName.CB_SETTLEMENT);
		}
	}
	
	

	private String getSettlementStatus(SettleState settleState) {
		// TODO Auto-generated method stub
		if(settleState.getCode().equals(SettleState.init.getCode())){
			return SettlementStatus.unsettlemnt.getCode();
		}
		
		return SettlementStatus.settlementsucc.getCode();
	}

	private void loadData() {
		// TODO Auto-generated method stub
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		PaymentMapper paymentMapper = cqSqlSession.getMapper(PaymentMapper.class);
		
		List<Transfer> productList = transferMapper.queryGroupList(TransferTypeEnum.product_id);
		if(productList != null && productList.size() >0) {
			for(Transfer tf : productList) {
				productMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		productList = null;
		
		
		List<Transfer> orderList = transferMapper.queryGroupList(TransferTypeEnum.order_id);
		if(orderList != null && orderList.size() >0) {
			for(Transfer tf : orderList) {
				orderMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		orderList = null;
		
		
		List<Transfer> merchantList = transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
		if(merchantList != null && merchantList.size() >0) {
			for(Transfer tf : merchantList) {
				merchantMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		merchantList = null;
		
		List<Transfer> outletList = transferMapper.queryGroupList(TransferTypeEnum.outlet_id);
		if(outletList != null && outletList.size() >0) {
			for(Transfer tf : outletList) {
				outlettMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		outletList = null;
		
		List<Transfer> merchantUserList = transferMapper.queryGroupList(TransferTypeEnum.merchant_user_id);
		if(merchantUserList != null && merchantUserList.size() >0) {
			for(Transfer tf : merchantUserList) {
				merchantUserMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		outletList = null;
		
		List<Payment> paymentList = paymentMapper.selectSettlementAll();
		if(paymentList != null && paymentList.size() >0) {
			for(Payment tf : paymentList) {
				paymentMap.put(tf.getOrderId()+(tf.getTicketId()==null?"":tf.getTicketId().toString())  , tf.getSn());
			}
		}
		outletList = null;
		
	}

}

