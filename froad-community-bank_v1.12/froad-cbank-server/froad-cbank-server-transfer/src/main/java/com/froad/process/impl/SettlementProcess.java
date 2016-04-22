package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.common.mongo.CommonMongo;
import com.froad.common.redis.MerchantRedis;
import com.froad.common.redis.OutletRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.TransDto;
import com.froad.db.ahui.mappers.MerchantGroupUserMapper;
import com.froad.db.ahui.mappers.MerchantMapper;
import com.froad.db.ahui.mappers.PayMapper;
import com.froad.db.ahui.mappers.TicketMapper;
import com.froad.db.ahui.mappers.TransMapper;
import com.froad.db.chonggou.entity.OutletProduct;
import com.froad.db.chonggou.entity.Settlement;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.SettlementStatus;
import com.froad.enums.SettlementType;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.MerchantGroupUser;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.DesUtils;
import com.froad.util.MongoTableName;
import com.froad.util.SimpleID;

/**
 *  结算记录迁移
  * @ClassName: SettlementProcess
  * @Description: TODO
  * @author share 2015年5月1日
  * @modify share 2015年5月1日
 */
public class SettlementProcess extends AbstractProcess{
	
	private SimpleID simple = new SimpleID(ModuleID.settlement);
	
	public SettlementProcess(String name,ProcessServiceConfig config) {
		super(name,config);
		// TODO Auto-generated constructor stub
	}
	
	Map<String, String> merMap = new HashMap<String, String>();
	Map<String, String> proMap = new HashMap<String, String>();
	Map<String, String> merUserMap = new HashMap<String, String>();
	Map<String, String> orderMap = new HashMap<String, String>();
	Map<String, String> outletMap = new HashMap<String, String>();
	
	
	
	@Override
	public void process() {
		// TODO Auto-generated method stub
		LogCvt.info("结算记录数据迁移开始");
		TransMapper transMapper = ahSqlSession.getMapper(TransMapper.class);
		TransferMapper tsfMapper = sqlSession.getMapper(TransferMapper.class);
		List<TransDto> transList = transMapper.queryBySettlement();
		if(transList == null || transList.isEmpty()){
			LogCvt.error("未查询到结算订单信息");
			return;
		}
		/**
		 *  初始化数据到内存信息
		 */
		List<Transfer> merList = tsfMapper.queryGroupList(TransferTypeEnum.merchant_id);
		if(merList != null && merList.size() >0) {
			for(Transfer tf : merList) {
				merMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		List<Transfer> proList = tsfMapper.queryGroupList(TransferTypeEnum.product_id);
		if(proList != null && proList.size() >0) {
			for(Transfer tf : proList) {
				proMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		List<Transfer> merUserList = tsfMapper.queryGroupList(TransferTypeEnum.merchant_user_id);
		if(merUserList != null && merUserList.size() >0) {
			for(Transfer tf : merUserList) {
				merUserMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		List<Transfer> outletList = tsfMapper.queryGroupList(TransferTypeEnum.outlet_id);
		if(outletList != null && outletList.size() >0) {
			for(Transfer tf : outletList) {
				outletMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		List<Transfer> orderList = tsfMapper.queryGroupList(TransferTypeEnum.order_id);
		if(orderList != null && orderList.size() >0) {
			for(Transfer tf : orderList) {
				orderMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		/**
		 *  处理所有的结算订单信息
		 */
		for(TransDto trans : transList){
			TransType type = trans.getType();
			if("02".equals(type.getCode())){
				//团购
				group(trans);
			}else if("05".equals(type.getCode())){
				// 面对面
				face2face(trans);
			}else{
				// 名优特惠
				special(trans);
			}
		}
		
		merMap = null;
		proMap = null;
		merUserMap = null;
		orderMap = null;
		outletMap = null;
		
	}
	
	/**
	 *  团购结算
	  * @Title: group
	  * @Description: TODO
	  * @author: share 2015年5月1日
	  * @modify: share 2015年5月1日
	  * @param @param trans    
	  * @return void    
	  * @throws
	 */
	public void group(TransDto trans){
		TicketMapper ticketMapper = ahSqlSession.getMapper(TicketMapper.class);
		PayMapper payMapper = ahSqlSession.getMapper(PayMapper.class);
		// 订单ID
		String orderIds = orderMap.get(String.valueOf(trans.getId()));
		String orderId = orderIds.split(",")[0];
		// 子订单ID
		String subOrderId = orderIds.split(",")[1];
		// 结算类型
		String type = SettlementType.group.getCode();
		// 商户ID
		String merchantId = merMap.get(trans.getMerchantId()+"");
		// 客户端ID
		String clientId = Constans.clientId;
		// 商户名称
		Map<String,String> merchantRedis = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
		String merchantName = "";
		if(merchantRedis != null && merchantRedis.size() > 0){
			merchantName = merchantRedis.get("merchant_name");
		}
		// 门店ID
		String outletId = outletMap.get(trans.getOutletId()+"");
		Map<String,String> outletRedis = OutletRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(clientId,merchantId,outletId);
		String outletName = "";
		if(outletRedis != null && outletRedis.size() > 0){
			outletName = outletRedis.get("outlet_name");
		}
		// 查询结算卷信息
		List<TransSecurityTicket> tickets = ticketMapper.queryByGroupTicket(trans.getId());
		for(TransSecurityTicket ticket : tickets){
			// 卷号
			String ticketNo = ticket.getSecuritiesNo();
			// 支付id
			Pay pay = payMapper.queryLastOrderPayInfo(trans.getId());
			if(pay == null){
				LogCvt.error("支付记录为空,交易ID："+trans.getId());
				return;
			}
			String paymentId = pay.getSn();
			// 消费时间
			long createTime = ticket.getConsumeTime()==null?ticket.getCreateTime().getTime():ticket.getConsumeTime().getTime();
			// 备注
			String remark = ticket.getRemark();
			// 商品数量
			int productCount = 1;
			// 商品ID
			long proId = trans.getProductId();
			String productId = proMap.get(proId+"");
			// 商品名称
			String productName = trans.getProductName();
			List<String> ticketList = new ArrayList<String>();
			ticketList.add(decrypt(ticketNo));
			// 金额
			int money = Arith.mul(Double.parseDouble(ticket.getMoney()), 1000);
			// 门店操作员
			Long merchantUserId = 0l;
			try {
				merchantUserId = Long.parseLong(merUserMap.get(ticket.getSysUserId()+""));
			} catch (Exception e) {
				LogCvt.error("异常卷信息:"+ticket.getId());
			}
			// 存储结算记录信息
			Settlement settelment = new Settlement();
			settelment.setSettlementId(simple.nextId());
			settelment.setClientId(clientId);
			settelment.setMerchantId(merchantId);
			settelment.setMerchantUserId(merchantUserId);
			settelment.setMerchantName(merchantName);
			settelment.setMoney(money);
			settelment.setOrderId(orderId);
			settelment.setSubOrderId(subOrderId);
			settelment.setOutletId(outletId);
			settelment.setOutletName(outletName);
			settelment.setPaymentId(paymentId);
			settelment.setProductCount(productCount);
			settelment.setProductId(productId);
			settelment.setProductName(productName);
			settelment.setRemark(remark);
			settelment.setType(type);
			settelment.setTickets(ticketList);
			settelment.setSettleState(SettlementStatus.settlementsucc.getCode());
			settelment.setCreateTime(createTime);
			mongo.add(settelment, MongoTableName.CB_SETTLEMENT);
		}
		
	}
	
	/**
	 *  面对面结算
	  * @Title: group
	  * @Description: TODO
	  * @author: share 2015年5月1日
	  * @modify: share 2015年5月1日
	  * @param @param trans    
	  * @return void    
	  * @throws
	 */
	public void face2face(TransDto trans){
		PayMapper payMapper = ahSqlSession.getMapper(PayMapper.class);
		MerchantGroupUserMapper merchantGroupUserMapper = ahSqlSession.getMapper(MerchantGroupUserMapper.class);
		// 订单ID
		String orderIds = orderMap.get(String.valueOf(trans.getId()));
		String orderId = orderIds.split(",")[0];
		// 结算类型
		String type = SettlementType.face2face.getCode();
		// 商户ID
		String merchantId = merMap.get(trans.getMerchantId()+"");
		// 客户端ID
		String clientId = Constans.clientId;
		// 商户名称
		Map<String,String> merchantRedis = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
		String merchantName = "";
		if(merchantRedis != null && merchantRedis.size() > 0){
			merchantName = merchantRedis.get("merchant_name");
		}
		// 门店ID
		long sysUserId = trans.getSysUserId();
		MerchantGroupUser groupUser = merchantGroupUserMapper.findMerchantGroupUserByUserId(sysUserId);
		String outletId = null;
		String outletName = null;
		if(groupUser.getMerchantOutletId() != null){
			outletId = outletMap.get(groupUser.getMerchantOutletId()+"");
			Map<String,String> outletRedis = OutletRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(clientId,merchantId,outletId);
			if(outletRedis != null && outletRedis.size() > 0){
				outletName = outletRedis.get("outlet_name");
			}
		}
		// 支付id
		Pay pay = payMapper.queryLastOrderPayInfo(trans.getId());
		if(pay == null){
			LogCvt.error("支付记录为空,交易ID："+trans.getId());
			return;
		}
		String paymentId = pay.getSn();
		// 消费时间
		long createTime = trans.getCreateTime().getTime();
		// 金额
		int money = Arith.mul(Double.parseDouble(trans.getGatheringValue()), 1000);
		// 商品数量
		int productCount = 1;
		// 商品ID
		OutletProduct outletProduct = CommonMongo.queryOutletProduct(merchantId, outletId, money);
		String productId = outletProduct==null?"":outletProduct.getProductId();
		// 商品名称
		String productName = "面对面结算";
		// 门店操作员
		Long merchantUserId = Long.parseLong(merUserMap.get(sysUserId+""));
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
		settelment.setSettleState(SettlementStatus.settlementsucc.getCode());
		settelment.setCreateTime(createTime);
		mongo.add(settelment, MongoTableName.CB_SETTLEMENT);
	}
	
	/**
	 *  名优特惠结算
	  * @Title: group
	  * @Description: TODO
	  * @author: share 2015年5月1日
	  * @modify: share 2015年5月1日
	  * @param @param trans    
	  * @return void    
	  * @throws
	 */
	public void special(TransDto trans){
		PayMapper payMapper = ahSqlSession.getMapper(PayMapper.class);
		// 订单ID
		String orderIds = orderMap.get(String.valueOf(trans.getId()));
		String orderId = orderIds.split(",")[0];
		// 子订单ID
		String subOrderId = orderIds.split(",")[1];
		// 结算类型
		String type = SettlementType.special.getCode();
		// 商户ID
		String merchantId = merMap.get(trans.getMerchantId()+"");
		// 客户端ID
		String clientId = Constans.clientId;
		// 商户名称
		Map<String,String> merchantRedis = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
		String merchantName = "";
		if(merchantRedis != null && merchantRedis.size() > 0){
			merchantName = merchantRedis.get("merchant_name");
		}
		
		// 支付id
		Pay pay = payMapper.queryLastOrderPayInfo(trans.getId());
		if(pay == null){
			LogCvt.error("支付记录为空,交易ID："+trans.getId());
			return;
		}
		String paymentId = pay.getSn();
		// 消费时间
		long createTime = trans.getCreateTime().getTime();
		// 金额
		int money = Arith.mul(Double.parseDouble(trans.getTotalPrice()), 1000);
		// 商品数量
		int productCount = trans.getQuantity();
		// 商品ID
		long proId = trans.getProductId();
		String productId = proMap.get(proId+"");
		// 商品名称
		String productName = trans.getProductName();
		// 结算状态
		//物流状态  0：待发货  1:已发货  2:已收货
		String shipState = trans.getShipState();
		String settleState = trans.getSettleState();
		String status = SettlementStatus.unsettlemnt.getCode();
		String remark = null;
		if("2".equals(shipState) && "1".equals(settleState)){
			status = SettlementStatus.settlementsucc.getCode();
			remark = "用户确认收货";
		}
	
		// 存储结算记录信息
		Settlement settelment = new Settlement();
		settelment.setSettlementId(simple.nextId());
		settelment.setClientId(clientId);
		settelment.setMerchantId(merchantId);
		settelment.setMerchantName(merchantName);
		settelment.setMoney(money);
		settelment.setOrderId(orderId);
		settelment.setSubOrderId(subOrderId);
		settelment.setPaymentId(paymentId);
		settelment.setProductCount(productCount);
		settelment.setProductId(productId);
		settelment.setProductName(productName);
		settelment.setType(type);
		settelment.setSettleState(status);
		settelment.setCreateTime(createTime);
		settelment.setRemark(remark);
		mongo.add(settelment, MongoTableName.CB_SETTLEMENT);
	}
	
	
	private String decrypt(String str){
        if (str == null || "".equals(str)){
            return null;
        }
        try
        {
            DesUtils desUtils = new DesUtils();
            String ticketNo = desUtils.decrypt(str);
            if(ticketNo.startsWith("ah")){
            	return ticketNo.substring(2);
            }
            return ticketNo;
            
        }
        catch(Exception e)
        {
           LogCvt.error("券号解密异常", e);
        }
        return null;
    }
	
}

