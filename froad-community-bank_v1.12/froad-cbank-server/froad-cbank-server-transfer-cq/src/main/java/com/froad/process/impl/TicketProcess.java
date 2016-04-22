package com.froad.process.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSession;

import com.froad.cbank.persistent.common.enums.TicketRefundState;
import com.froad.cbank.persistent.common.enums.TicketState;
import com.froad.cbank.persistent.common.enums.TicketType;
import com.froad.cbank.persistent.entity.MerchantUser;
import com.froad.cbank.persistent.entity.ProductPresell;
import com.froad.cbank.persistent.entity.Refunds;
import com.froad.common.qrcode.QrCodeGenerateService;
import com.froad.common.qrcode.QrCodeRequestVo;
import com.froad.common.qrcode.QrCodeResponseVo;
import com.froad.common.redis.OutletRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MerchantUserCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantUserMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.entity.OrderEx;
import com.froad.db.chongqing.entity.OrderProduct;
import com.froad.db.chongqing.mappers.MerchantMapper;
import com.froad.db.chongqing.mappers.MerchantUserMapper;
import com.froad.db.chongqing.mappers.OrderMapper;
import com.froad.db.chongqing.mappers.ProductPresellMapper;
import com.froad.db.chongqing.mappers.RefundsMapper;
import com.froad.db.chongqing.mappers.TicketMapper;
import com.froad.db.chongqing.mysql.CqMyBatisManager;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.FieldMapping;
import com.froad.enums.ModuleID;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.QrCodeType;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Org;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *  卷表数据迁移
  * @ClassName: TicketProcess
  * @author share 2015年5月2日
  * @modify share 2015年5月2日
 */
public class TicketProcess extends AbstractProcess {

	// 初始化内存数据
	Map<String, String> merMap = new HashMap<String, String>();
	Map<String, String> proMap = new HashMap<String, String>();
	Map<String, String> merUserMap = new HashMap<String, String>();
	Map<String, String> orderMap = new HashMap<String, String>();
	Map<String, String> outletMap = new HashMap<String, String>();
	Map<String, String> refundsMap = new HashMap<String, String>();
	
	RedisManager manager = new RedisManager();
	OutletRedis outRedis = new OutletRedis(manager);
	
	private SimpleID simpleId = new SimpleID(ModuleID.ticket);
	
	public TicketProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	
	@Override
	public void begin() {
		super.begin();
	}	
	
	@Override
	public void before() {
		clearTicketMongo();
	}
	
	@Override
	public void process() {
		TransferMapper tsfMapper = sqlSession.getMapper(TransferMapper.class);
		
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
		
		List<Transfer> refundsList = tsfMapper.queryGroupList(TransferTypeEnum.refund_id);
		if(refundsList != null && refundsList.size() >0) {
			for(Transfer tf : refundsList) {
				refundsMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		/**
		 *  迁移已生成券信息
		 */
		LogCvt.info("开始迁移现有券");
		convertExistingTicket();
		LogCvt.info("现有券迁移完成");
		
		/**
		 * 迁移未生成预售券
		 */
		LogCvt.info("开始生成未发送预售券");
		projectPresellTicket();
		LogCvt.info("开始生成未发送预售迁移完成");
	}
	
	/**
	 * convert existing tickets
	 */
	private void convertExistingTicket(){
		TicketMapper ticektMapper = cqSqlSession.getMapper(TicketMapper.class);
		ExecutorService pool = Executors.newFixedThreadPool(20);
		
		/**
		 *  移植所有的卷信息
		 */
		List<com.froad.cbank.persistent.entity.Ticket> ticketsList = ticektMapper.selectByCondition(null);
		for(final com.froad.cbank.persistent.entity.Ticket sourceTicket : ticketsList){
			pool.submit(new Runnable() {
				@Override
				public void run() {
					createTicket(sourceTicket);
				}
			});
		}
		pool.shutdown();
	}
	
	// TODO
	private void projectPresellTicket(){
		OrderMapper orderMapper = cqSqlSession.getMapper(OrderMapper.class);
		ProductPresellMapper presellMapper = cqSqlSession.getMapper(ProductPresellMapper.class);
		
		Map<String, String> outletRedis = null;
		String outletName = null;
		
		List<OrderEx> orderList = orderMapper.selectPresellProduct();
		
		/**
		 *  移植所有的未生成卷的订单信息
		 */
		for(OrderEx orderEx : orderList){
			// mongo卷对象
			com.froad.po.Ticket targetTicket = new com.froad.po.Ticket();
			// 创建时间
			long createTime = orderEx.getCreateTime().getTime();//TODO
			// 客户端ID
			String clientId = Constans.clientId;
			// 类型
			String transType = SubOrderType.presell_org.getCode();
			// 门店信息
			String newMerchantId = merMap.get(String.valueOf(orderEx.getMerchantId()));
			// 处理卷的特殊信息字段
			handlePresellTicket(targetTicket, transType, newMerchantId);
			// 订单ID
			String orderIds = orderMap.get(String.valueOf(orderEx.getId()));
			if(orderIds == null){
				LogCvt.error("订单ID无对应中间表记录:" + orderEx.getId());
				continue;
			}
			String orderId = orderIds.split(",")[0];
			// 子订单ID
			String subOrderId = orderIds.split(",")[1];
			// 会员号
			long memberCode = orderEx.getMemberCode();
			// 提货人
			String memberName = orderEx.getBuyerName();
			// 卷号
			String ticketId = simpleId.nextId();
			// image卷二维码F
			String image = generateQrcode(ticketId, clientId);
			// 卷提货手机号
			String smsNumber = orderEx.getMemberMobile();
			// 商品ID
			long proId = orderEx.getDetail().getProductId();
			String productId = proMap.get(proId+"");
			// 商品名称
			String productName = orderEx.getDetail().getProductName();
			// 商品数量
			int quantity = orderEx.getDetail().getQuantity();
			// 卷状态
			ProductPresell presell = presellMapper.selectProductPresellById(proId);
			if(presell == null){
				LogCvt.error("预售商品信息不存在,ID="+proId);
				continue;
			}
			long expireTime = presell.getExpireTime().getTime();
			// 待发卷
			String status = TicketStatus.init.getCode();
			
			// 商户ID
			String merchantId = merMap.get(orderEx.getMerchantId()+"");
			// 商户名称
			String merchantName = orderEx.getMerchantName();
			// 卷退款信息
			
			outletRedis = outRedis
					.get_cbbank_outlet_client_id_merchant_id_outlet_id(
							clientId,
							newMerchantId,
							targetTicket.getOutletId());
			if (outletRedis != null && outletRedis.size() > 0) {
				outletName = outletRedis.get("outlet_name");
			}
			
			// Ticket卷表添加到Mongo
			targetTicket.setClientId(clientId);
			targetTicket.setCreateTime(createTime);
			targetTicket.setExpireTime(expireTime);
			targetTicket.setImage(image);
			targetTicket.setMemberCode(memberCode+"");
			targetTicket.setMemberName(memberName);
			targetTicket.setMerchantId(merchantId);
			targetTicket.setMerchantName(merchantName);
			targetTicket.setMobile(smsNumber);
			targetTicket.setOrderId(orderId);
			targetTicket.setSubOrderId(subOrderId);
			targetTicket.setProductId(productId);
			targetTicket.setProductName(productName);
			targetTicket.setOutletName(outletName);
			targetTicket.setQuantity(quantity);
			targetTicket.setStatus(status);
			targetTicket.setTicketId(ticketId);
			targetTicket.setType(transType);
			targetTicket.setPrice(Arith.mul(Double.parseDouble(orderEx.getDetail().getSingle()), 1000));
			mongo.add(targetTicket, MongoTableName.CB_TICKET);
		}
	}
	
	public void createTicket(com.froad.cbank.persistent.entity.Ticket sourceTicket ){
		SqlSession session = null;
		Map<String, String> outletRedis = null;
		
		try {
			session = CqMyBatisManager.getSqlSessionFactory().openSession();
			OrderMapper orderMapper = session.getMapper(OrderMapper.class);
			MerchantMapper merchantMapper = session.getMapper(MerchantMapper.class);
			RefundsMapper refundMapper = session.getMapper(RefundsMapper.class);
			MerchantUserMapper merchantUserMapper=session.getMapper(MerchantUserMapper.class);
			// mongo卷对象
			com.froad.po.Ticket targetTicket = new com.froad.po.Ticket();
			// 客户端ID
			String clientId = Constans.clientId;
			// 类型
			String ticketType = getTicketType(sourceTicket.getType());
			if(ticketType == null){
				LogCvt.warn("无效的卷类型："+sourceTicket.getId());
				return;
			}
			// 门店ID
			String outletId = null;
			String outletName = null;
			// 订单信息
			OrderProduct orderProduct = orderMapper.selectProductInfoByOrderId(sourceTicket.getOrderId());
			if(SubOrderType.presell_org.getCode().equals(ticketType)){
				// 处理卷的特殊信息字段
				handlePresellTicket(targetTicket, ticketType, merMap.get(String.valueOf(orderProduct.getMerchantId())));
				
				outletRedis = outRedis
						.get_cbbank_outlet_client_id_merchant_id_outlet_id(
								clientId,
								String.valueOf(orderProduct.getMerchantId()),
								targetTicket.getOutletId());
				if (outletRedis != null && outletRedis.size() > 0) {
					outletName = outletRedis.get("outlet_name");
				}
			}else{
				outletId = outletMap.get(sourceTicket.getMerchantOutletId() + "");
				outletRedis = outRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(
								clientId,
								String.valueOf(orderProduct.getMerchantId()),
								outletId);
				if (outletRedis != null && outletRedis.size() > 0) {
					outletName = outletRedis.get("outlet_name");
				}
			}
			// 创建时间
			long createTime = sourceTicket.getCreateTime().getTime();
			// 订单ID
			String orderIds = orderMap.get(String.valueOf(sourceTicket.getOrderId()));
			if(orderIds == null){
				LogCvt.warn("退款订单ID无对应中间表记录:" + sourceTicket.getOrderId());
				return;
			}
			String orderId = orderIds.split(",")[0];
			// 子订单ID
			String subOrderId = orderIds.split(",")[1];
			// 会员号
			long memberCode = sourceTicket.getMemberCode();
			// 提货人
			String memberName = orderProduct.getBuyerName();
			// 卷号
			String ticketId = sourceTicket.getSecuritiesNo().replaceFirst("cq", "");
			// image卷二维码
			String image = generateQrcode(ticketId, clientId);
			// 商品ID
			long proId = orderProduct.getProductId();
			String productId = proMap.get(proId+"");
			// 商品名称
			String productName = orderProduct.getProductName();
			// 商品数量
			int quantity = SubOrderType.group_merchant.getCode().equals(ticketType)?1:orderProduct.getQuantity();
			// 卷状态
			long smsTime = sourceTicket.getSmsTime().getTime();
			long expireTime = sourceTicket.getExpireTime().getTime();
			boolean isConsume = sourceTicket.getIsConsume();
			String state = sourceTicket.getState().getCode();
			String ticketRefundState = sourceTicket.getRefundState().getCode();
			
			// 消费时间
			Long consumeTime = sourceTicket.getConsumeTime()==null?null:sourceTicket.getConsumeTime().getTime();
			String status = "";
			if (isConsume){
				status = TicketStatus.consumed.getCode();
			} else if (ticketRefundState.equals(TicketRefundState.refunding.getCode())) {
				status = TicketStatus.refunding.getCode();
			} else if (ticketRefundState.equals(TicketRefundState.refunded.getCode())) {
				status = TicketStatus.refunded.getCode();
			} else if (expireTime < System.currentTimeMillis() && state.equals(TicketState.enable.getCode())) {
				status = TicketStatus.expired.getCode();
			} else if (expireTime >= System.currentTimeMillis()
					&& smsTime < System.currentTimeMillis()
					&& state.equals(TicketState.enable.getCode())) {
				status = TicketStatus.sent.getCode();
			} else {
				LogCvt.info("其他状态设置为，已发送卷ID："+sourceTicket.getId());
				status = TicketStatus.sent.getCode();
			}
			
			// 团购商户用户ID
			Long merchantUserId = null;
			String merchantUserName = null;
			MerchantUser merchantUser=null;
			if(SubOrderType.group_merchant.getCode().equals(ticketType) && TicketStatus.consumed.getCode().equals(status)){
				merchantUserId = Long.parseLong(merUserMap.get(String.valueOf(sourceTicket.getMerchantUserId())));
			    merchantUser=	merchantUserMapper.selectMerchantUserById(sourceTicket.getMerchantUserId());
			    merchantUserName = merchantUser.getAlias();
			}
			// 商户ID
			String merchantId = merMap.get(sourceTicket.getMerchantId()+"");
			// 商户名称
			String merchantName = merchantMapper.selectById(sourceTicket.getMerchantId()).getName();
			// 卷退款信息
			String refundId = null;
			Long refundTime = null;
			if(TicketStatus.refunded.getCode().equals(status) || TicketStatus.refunding.getCode().equals(status)){
				long id = sourceTicket.getId();
				Refunds refund = refundMapper.selectByTicketId(id).get(0);
				if(refund != null){
					refundId = refundsMap.get(refund.getId()+"");
					refundTime = refund.getCreateTime().getTime();	
				}
			}
			
			// Ticket卷表添加到Mongo
			targetTicket.setClientId(clientId);
			targetTicket.setConsumeTime(consumeTime);
			targetTicket.setCreateTime(createTime);
			targetTicket.setExpireTime(expireTime);
			targetTicket.setImage(image);
			targetTicket.setMemberCode(memberCode+"");
			targetTicket.setMemberName(memberName);
			targetTicket.setMerchantId(merchantId);
			targetTicket.setMerchantName(merchantName);
			targetTicket.setMerchantUserId(merchantUserId);
			targetTicket.setMerchantUserName(merchantUserName);
//			targetTicket.setMobile(orderProduct.getMemberMobile());
			targetTicket.setMobile(orderProduct.getPhone());//使用接收券短信的手机号，而非用户注册手机号
			targetTicket.setOrderId(orderId);
			targetTicket.setSubOrderId(subOrderId);
			targetTicket.setOutletId(outletId);
			targetTicket.setOutletName(outletName);
			targetTicket.setProductId(productId);
			targetTicket.setProductName(productName);
			targetTicket.setQuantity(quantity);
			targetTicket.setRefundId(refundId);
			targetTicket.setRefundTime(refundTime);
			targetTicket.setStatus(status);
			targetTicket.setTicketId(ticketId);
			targetTicket.setType(ticketType);
			targetTicket.setPrice(Arith.mul(Double.parseDouble(orderProduct.getSinglePrice()), 1000));
			mongo.add(targetTicket, MongoTableName.CB_TICKET);
		} catch (Exception e) {
			LogCvt.error("券信息转移异常",e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}
	
	/**
	 *  预售卷的特殊处理
	  * @Title: handlePresellTicket
	  * @author: share 2015年5月2日
	  * @modify: share 2015年5月2日
	  * @param targetTicket
	  * @param transType   
	  * @param newMerchantId 
	  * @return void    
	  * @throws
	 */
	private void handlePresellTicket(com.froad.po.Ticket targetTicket, String transType, String newMerchantId) {
		// 预售第一机构码
		String forgCode = "000000";
		// 第二级机构码
		String sorgCode = null;
		// 第三级机构码
		String torgCode = null;
		// 第四级机构码
		String lorgCode = null;
		// 预售提货网点和名称
		String orgCode = null;
		String orgName = null;
		CommonLogic commonLogic = null;
		Org org = null;
		
		commonLogic = new CommonLogicImpl();
		org = commonLogic.queryByMerchantInfo(Constans.clientId, newMerchantId);
		
		if (org == null){
			LogCvt.error(new StringBuffer("预售券机构码信息不存在，newMerchantId=").append(newMerchantId).toString());
		} else {
			orgCode = org.getOrgCode();
			orgName = org.getOrgName();
			if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_one.getLevel())){
				forgCode = orgCode;
				sorgCode = "";
				torgCode = "";
				lorgCode = "";
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())) {
				forgCode = org.getProvinceAgency();
				sorgCode = orgCode;
				torgCode = "";
				lorgCode = "";
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())) {
				forgCode = org.getProvinceAgency();
				sorgCode = org.getCityAgency();
				torgCode = orgCode;
				lorgCode = "";
			} else if (org.getOrgLevel().equals(OrgLevelEnum.orgLevel_three.getLevel())) {
				forgCode = org.getProvinceAgency();
				sorgCode = org.getProvinceAgency();
				torgCode = org.getCountyAgency();
				lorgCode = orgCode;
			}
			
			targetTicket.setOutletId(org.getOutletId());
		}
		
		targetTicket.setForgCode(forgCode);
		targetTicket.setSorgCode(sorgCode);
		targetTicket.setTorgCode(torgCode);
		targetTicket.setLorgCode(lorgCode);
		targetTicket.setOrgCode(orgCode);
		targetTicket.setOrgName(orgName);
	}
	
	
	/**
	 * 生成券二维码
	 * 
	 * @param qrCodeLogic
	 * @param ticketId
	 * @param clientId
	 */
	private String generateQrcode(String ticketId, String clientId){
		StringBuffer keyword = null;
		QrCodeRequestVo request = null;
		QrCodeResponseVo response = null;
		String imageUrl = null;
		
		try {
			keyword = new StringBuffer();
			keyword.append(QrCodeType.TICKET);
			keyword.append(ticketId);
			
			request = new QrCodeRequestVo();
			request.setClientId(clientId);
			request.setKeyword(keyword.toString());
			
			response = new QrCodeGenerateService().generateQrCode(request);
			
			if (response.getResultCode().equals(ResultCode.success.getCode())){
				imageUrl = response.getUrl();
				LogCvt.debug(new StringBuffer(keyword).append("二维码生成成功.").toString());
			} else {
				LogCvt.debug(new StringBuffer(keyword).append("二维码生成失败. ").append(response.getResultDesc()).toString());
			}
		} catch (Exception e){
			LogCvt.error("券号二维码", e);
		}
		
		return imageUrl;
	}
	
	private String getTicketType(TicketType type){
		
		if(TicketType.group.getCode().equals(type.getCode())){
			// 团购
			return SubOrderType.group_merchant.getCode();
		}else if(TicketType.presell.getCode().equals(type.getCode())){
			// 预售
			return SubOrderType.presell_org.getCode();
		}
		return null;
	}
	
	private String getOrgCode(String sourceCode){
		if(sourceCode == null){
			return sourceCode;
		}
		return sourceCode.split("_")[1];
	}
	
	/**
	 * clear tickets with client id
	 */
	private void clearTicketMongo(){
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.CLIENT_ID.getMongoField(), Constans.clientId);
		
		mongo.remove(queryObj, MongoTableName.CB_TICKET);
	}
	
	public static void main(String[] args) throws ParseException {
	}

}

