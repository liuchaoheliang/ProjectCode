package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.ModuleID;
import com.froad.enums.OrderStatus;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.enums.SettlementType;
import com.froad.enums.SubOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.SettlementLogic;
import com.froad.logic.impl.payment.PaymentCoreLogicImpl;
import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.qrcodeproduct.OutletProduct;
import com.froad.po.settlement.Settlement;
import com.froad.po.settlement.SettlementReq;
import com.froad.support.CommonSupport;
import com.froad.support.OrderSupport;
import com.froad.support.SettlementSupport;
import com.froad.support.TicketSupport;
import com.froad.support.impl.CommonSupportImpl;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.SettlementSupportImpl;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.settlement.SettlementPage;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.SimpleID;
import com.froad.util.payment.EsyT;
import com.froad.util.settlement.BaseSubassembly;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *  结算业务
  * @ClassName: SettlementLogicImpl
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public class SettlementLogicImpl implements SettlementLogic {
	
	private static final SimpleID simpleId = new SimpleID(ModuleID.settlement);
	/**
	 *  订单信息数据接口
	 */
	private OrderSupport orderSuppport = new OrderSupportImpl();
	
	private SettlementSupport settlementSupport = new SettlementSupportImpl();
	
	private CommonSupport outletProductSupport = new CommonSupportImpl();
	
	private TicketSupport ticketSupport = new TicketSupportImpl();
	
	/**
	 *  结算支付逻辑调用
	 */
//	private final PaymentLogic paymengLogic = new PaymentLogicImpl();
//	private PaymentCoreLogic paymentCoreLogic = new PaymentCoreLogicImpl();
	
	
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	/**
	 *  面对面结算
	  * @Title: cashSettlement
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param orderId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#cashSettlement(java.lang.String)
	 */
	@Override
	public ResultBean paySettlement(OrderMongo order) {
		// TODO Auto-generated method stub
		LogCvt.info("面对面交易结算，orderId="+order.getOrderId());
//		OrderMongo order = orderSuppport.getOrderByOrderId(orderId);
		// 是否面对面支付订单
		boolean qrcode = BooleanUtils.toBoolean(order.getIsQrcode());
		if(qrcode){
			return face2faceSettlement(order);
		}else{
			List<SubOrderMongo> subOrders = orderSuppport.getSubOrderListByOrderId(order.getOrderId(), new String[]{SubOrderType.special_merchant.getCode()});
			if(subOrders.isEmpty()){
				return new ResultBean(ResultCode.success);
			}
			return specialSettlement(subOrders,order);
		}
	}
	
	/**
	 *  名优特惠生成待结算记录
	  * @Title: specialSettlement
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param subOrders
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	private ResultBean specialSettlement(List<SubOrderMongo> subOrders,OrderMongo order) {
		// TODO Auto-generated method stub
		LogCvt.info("名优特惠生产结算记录....");
		/**
		 * 按不同子订单生成待结算记录
		 */
		for(SubOrderMongo subOrder : subOrders){
			try {
				String settlementId = simpleId.nextId();
				// 客户端ID
				String clientId = subOrder.getClientId();
				// 订单ID
				String orderId = subOrder.getOrderId();
				// 子订单ID
				String subOrderId = subOrder.getSubOrderId();
				// 商户ID
				String merchantId = subOrder.getMerchantId();
				
				for(ProductMongo product : subOrder.getProducts()){
					// 商品ID
					String productId = product.getProductId();
					// 商品名称
					String productName = product.getProductName();
					// 结算金额
					int totalMoney = product.getMoney() * product.getQuantity() + product.getVipMoney() * product.getVipQuantity();
					// 商品数量
					int productCount = product.getQuantity() + product.getVipQuantity();
					/**
					 * 添加结算状态为结算中的记录
					 */
					Settlement settlement = new Settlement();
					settlement.setClientId(clientId);
					settlement.setCreateTime(System.currentTimeMillis());
					settlement.setMerchantId(merchantId);
					settlement.setMoney(totalMoney);
					settlement.setOrderId(orderId);
					settlement.setSubOrderId(subOrderId);
					// 支付流水ID待，请求open Api后进行更新
					settlement.setPaymentId("");
					settlement.setSettlementId(settlementId);
					settlement.setType(SettlementType.special.getCode());
					settlement.setProductId(productId);
					settlement.setProductName(productName);
					settlement.setProductCount(productCount);
					
					BaseSubassembly.countDeductibleCashValueOfSpecial(settlement, product, order);
					
					//商户名称
					String merchantName = "";
					try {
						Map<String,String> merchantMap = commonLogic.getMerchantRedis(clientId, merchantId);
						merchantName = merchantMap.get("merchant_name");//门店名称
						LogCvt.info("商户名称：" + merchantName);
					} catch (Exception e) {
						LogCvt.error(e.getMessage(),e);
					}
					settlement.setMerchantName(merchantName);
					
					// 添加结算记录为结算中的记录
					settlement.setSettleState(SettlementStatus.unsettlemnt.getCode());
					boolean isSucc = settlementSupport.createSettlement(settlement);
					if(!isSucc){
						LogCvt.error(new StringBuffer("添加结算记录异常：").append(JSonUtil.toJSonString(settlement)).toString());
						return new ResultBean(ResultCode.failed,"添加名优特惠结算记录失败");
					}
				}
			} catch (Exception e) {
				LogCvt.error("名优特惠结算记录异常"+JSonUtil.toJSonString(subOrder),e);
			}
			
		}
		
		return new ResultBean(ResultCode.success);
	}
	
	/**
	 *  名优特惠确认收货结算
	  * @Title: specialSettlement
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param memberCode
	  * @param @param subOrderId
	  * @param @param source
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#specialSettlement(java.lang.String, java.lang.String, int)
	 */
	@Override
	public ResultBean specialSettlement(String subOrderId,int source) {
		// TODO Auto-generated method stub
		try {
			String sourcedesc = "";
			if(source == 1){
				sourcedesc = "用户确认收货";
			}else if( source == 2){
				sourcedesc = "系统确认收货";
			}else{
				return new ResultBean(ResultCode.failed,"未知原由进行结算");
			}
			LogCvt.info("名优特惠确认结算，子订单ID："+subOrderId+",结算原由："+sourcedesc);
			List<Settlement> settlementList = settlementSupport.querySettlementBySubOrderId(subOrderId);
			if(settlementList == null || settlementList.isEmpty()){
				return new ResultBean(ResultCode.settlement_info_not_exists);
			}
			/**
			 *  统计结算表总结金额
			 */
			int totalMoney = 0;
			String clientId = "";
			String settlementId = "",merchantId = "",orderId = "";
			for(Settlement settle : settlementList){
				totalMoney += settle.getMoney();
				settlementId = settle.getSettlementId();
				merchantId = settle.getMerchantId();
				orderId = settle.getOrderId();
				clientId = settle.getClientId();
				// 结算转账，逐条将状态改成结算中
				Settlement settlet = settlementSupport.upateSettlementing(settlementId,sourcedesc);
				if(settlet == null){
					// 如果为空说明记录已在结算中
					return new ResultBean(ResultCode.settlement_settlemented);
				}
				
			}
			// 结算总金额
			double settlementMoney = Arith.div(totalMoney, 1000,3);
			
			/**
			 * 进入支付转账>>方付通转商户账户
			 */
			String paymentId = "";
			boolean flag = false;
			try {
				ResultBean result = new PaymentCoreLogicImpl().settleToMerchantCapital(orderId, clientId, settlementMoney, merchantId, "0");//paymengLogic.settleMerchantPaymentData(orderId, settlementMoney, clientId, merchantId, "0");
				paymentId = result.getData().toString();
				flag = result.isSuccess();
				LogCvt.info("名优特惠结算请求结算转账结果："+result.getMsg());
			} catch (Exception e) {
				LogCvt.error(e.getMessage(),e);
			}
			
			/**
			 *  检查请求，受理失败，更新结算状态为失败,受理成功，状态依然是结算中等待异步通知结果。
			 */
			/**
			 *  状态依然是结算中等待异步通知结果。
			 */
			if(flag){
				settlementSupport.upateSettlement(settlementId,SettlementStatus.settlementing.getCode(),paymentId);
			}else{
				settlementSupport.upateSettlement(settlementId,SettlementStatus.settlementfailed.getCode(),paymentId);
			}
			return new ResultBean(ResultCode.success);
		} catch (Exception e) {
			LogCvt.error("名优特惠确定收货结算异常"+subOrderId,e);
		}
		
		return new ResultBean(ResultCode.failed);
	}

	/**
	 *  面对面结算
	  * @Title: face2faceSettlement
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param order
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	private ResultBean face2faceSettlement(OrderMongo order) {
		try {
			String orderId = order.getOrderId();
			String clientId = order.getClientId();
			// 支付真实金额
			int totalPrice = (order.getTotalPrice() == null ? 0 : order.getTotalPrice()) 
							+ 
							(order.getCutMoney() == null ? 0 : order.getCutMoney()) ;
			
			
			// 结算金额
			double qrcodePrice = Arith.div(totalPrice, 1000, 3);
			// 面对面支付虚拟商品
			String productId = order.getProductId();
			// 状态
			String status = order.getOrderStatus();
			// 收银员
			long merchantUserId = 0;
			if(EmptyChecker.isNotEmpty(order.getMerchantUserId())){
				merchantUserId = order.getMerchantUserId();
			}
			
			/**
			 * 检查是否支付成功的订单
			 */
			if(!status.equals(OrderStatus.paysuccess.getCode())){
				return new ResultBean(ResultCode.settlement_no_pay);
			}
			
			/**
			 * 防重复结算，检查结算状态
			 */
			if(checkAgainRequestForQrcode(orderId)){
				return new ResultBean(ResultCode.settlement_settlemented);
			}
			
			/**
			 * 检查虚拟商品是否存在
			 */
			OutletProduct product = outletProductSupport.queryOutletProduct(productId);
			if(product == null){
				return new ResultBean(ResultCode.outlet_product_not_exists);
			}
			
			/**
			 * 添加结算状态为结算中的记录
			 */
			
			Settlement settlement = new Settlement();
			settlement.setClientId(clientId);
			settlement.setCreateTime(System.currentTimeMillis());
			settlement.setMerchantId(product.getMerchantId());
			settlement.setOutletId(product.getOutletId());
			settlement.setMerchantUserId(merchantUserId);
			settlement.setMoney(totalPrice);
			settlement.setOrderId(orderId);
			settlement.setProductId(order.getProductId());
			settlement.setProductCount(1);
			settlement.setProductName("面对面结算");
			// 支付流水ID待，请求open Api后进行更新
			settlement.setPaymentId("");
			settlement.setSettlementId(simpleId.nextId());
			settlement.setType(SettlementType.face2face.getCode());
			// 添加结算记录为结算中的记录
			settlement.setSettleState(SettlementStatus.settlementing.getCode());
			
			//查询支付方式用于看使用了多少积分抵扣了多少现金
			BaseSubassembly.countDeductibleCashValueOfFace2face(settlement,order);
			
			//查询门店
			String outletName = "";
			try {
				if (Checker.isNotEmpty(product.getOutletId()) && !product.getOutletId().equals("0")) { // 如果是商户管理员操作 则不查门店
					Map<String, String> outletMap = RedisCommon.getOutletRedis(clientId, product.getMerchantId(), product.getOutletId());
					if (Checker.isNotEmpty(outletMap)) {
						outletName = outletMap.get("outlet_name");// 门店名称
						LogCvt.info("门店名称：" + outletName);
					}
				}
			} catch (Exception e) {
				LogCvt.error(e.getMessage(),e);
			}
			settlement.setOutletName(outletName);
			
			//商户名称
			String merchantName = "";
			try {
				Map<String,String> merchantMap = commonLogic.getMerchantRedis(clientId, product.getMerchantId());
				if (Checker.isNotEmpty(merchantMap)) {
					merchantName = merchantMap.get("merchant_name");// 门店名称
					LogCvt.info("商户名称：" + merchantName);
				}
			} catch (Exception e) {
				LogCvt.error(e.getMessage(),e);
			}
			settlement.setMerchantName(merchantName);
			
			
			boolean isSucc = settlementSupport.createSettlement(settlement);
			if(!isSucc){
				return new ResultBean(ResultCode.failed,"添加结算记录失败");
			}
			
			/**
			 * 进入支付转账>>方付通转商户账户
			 */
			String paymentId = "";
			boolean flag = false;
			try {
				ResultBean result = new PaymentCoreLogicImpl().settleToMerchantCapital(orderId, clientId, qrcodePrice, settlement.getMerchantId(), settlement.getOutletId());//paymengLogic.settleMerchantPaymentData(orderId, qrcodePrice, clientId, settlement.getMerchantId(), settlement.getOutletId());
				paymentId = result.getData().toString();
				flag = result.isSuccess();
				LogCvt.info("面对面结算请求结算转账结果："+result.getMsg());
			} catch (Exception e) {
				LogCvt.error(e.getMessage(),e);
			}
			
			/**
			 *  状态依然是结算中等待异步通知结果。
			 */
			if(flag){
				settlementSupport.upateSettlement(settlement.getSettlementId(),SettlementStatus.settlementing.getCode(),paymentId);
			}else{
				settlementSupport.upateSettlement(settlement.getSettlementId(),SettlementStatus.settlementfailed.getCode(),paymentId);
			}
			return new ResultBean(ResultCode.success);
		} catch (Exception e) {
			LogCvt.error("面对面结算异常",e);
		}
		
		return new ResultBean(ResultCode.failed);
	}

	/**
	 *  团购结算
	  * @Title: groupSettlement
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param orderId
	  * @param @param subOrderId
	  * @param @param merchantId
	  * @param @param outletId
	  * @param @param merchantUserId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#groupSettlement(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultBean groupSettlement(List<String> tickets,String merchantId,String outletId) {
		// TODO Auto-generated method stub
		try {
			if(tickets == null || tickets.size() == 0){
				return new ResultBean(ResultCode.failed,"结算集合不能为空");
			}
			LogCvt.info("批量结算Tickets:"+JSonUtil.toJSonString(tickets));
			
			int realPrice = 0;
			// 客户端ID
			String clientId = "";
			// 订单ID
			String orderId = null;
			String settlementId = simpleId.nextId();
			List<String> ticketsFaild = new ArrayList<String>();
			// Key：商品ID，Value:卷集合，商品分类出不同的结算记录
			Map<String,Settlement> productCategoryMap = new HashMap<String, Settlement>();
			
			
			/**
			 *  批量校验卷信息,剔除无法进行结算的卷信息
			 */
			for(String ticketId : tickets){
				List<Ticket> ticketList = ticketSupport.getTicketByTicketId(ticketId);
				Ticket ticket = null;
				// 检查卷信息是否存在
				if(ticketList == null || ticketList.size() == 0){
					LogCvt.info("团购结算，卷信息不存在："+ticketId);
					ticketsFaild.add(ticketId);
					continue;
				}
				ticket = ticketList.get(0);
				// 客户端ID
				clientId = ticket.getClientId();
				// 订单ID
				orderId = ticket.getOrderId();
				// 子订单ID
				String subOrderId = ticket.getSubOrderId();
				// 商品ID
				String productId = ticket.getProductId();
				// 商品名称
				String productName = ticket.getProductName();
				// 查询子订单信息
				ProductMongo productMongo = orderSuppport.getProductMongo(orderId,subOrderId,productId);
				if(productMongo == null){
					LogCvt.info("团购结算，卷信息不存在："+orderId+",subOrderId:"+subOrderId+",productId:"+productId);
					ticketsFaild.add(ticketId);
					continue;
				}
				
				/**
				 * 防重复结算，检查结算状态
				 */
				if(checkAgainRequestForGroup(orderId, subOrderId,ticketId)){
					LogCvt.info("团购结算卷重复结算："+ticketId);
					ticketsFaild.add(ticketId);
					continue;
				}
				
				int money = 0;
				/**
				 * 添加结算状态为结算中的记录
				 */
				if(productCategoryMap.containsKey(productId)){//多个
					Settlement settlement = productCategoryMap.get(productId);
//					settlement.setMoney(productCategoryMap.get(productId).getMoney() + productMongo.getMoney());
//					settlement.setProductCount(productCategoryMap.get(productId).getProductCount()+1);
					settlement.getTickets().add(ticketId);
					money = BaseSubassembly.countDeductibalCashValueOfGroup(productMongo, settlement);
				}else{//单个
					Settlement settlement = new Settlement();
					settlement.setClientId(clientId);
					settlement.setCreateTime(System.currentTimeMillis());
					settlement.setMerchantId(merchantId);
					settlement.setOutletId(outletId);
//					settlement.setMoney(productMongo.getMoney());
					settlement.setOrderId(orderId);
					settlement.setSubOrderId(subOrderId);
					// 支付流水ID待，请求open Api后进行更新
					settlement.setPaymentId("");
					settlement.setSettlementId(settlementId);
					settlement.setType(SettlementType.group.getCode());
					settlement.setProductId(productId);
					settlement.setProductName(productName);
//					settlement.setProductCount(1);
					
					//查询门店
					String outletName = "";
					try {
						if (Checker.isNotEmpty(outletId) && !outletId.equals("0")) { // 如果是商户管理员操作 则不查门店
							Map<String, String> outletMap = RedisCommon.getOutletRedis(clientId, merchantId, outletId);
							if (Checker.isNotEmpty(outletMap)) {
								outletName = outletMap.get("outlet_name");// 门店名称
								LogCvt.info("门店名称：" + outletName);
							}
						}
					} catch (Exception e) {
						LogCvt.error(e.getMessage(),e);
					}
					settlement.setOutletName(outletName);
					
					//商户名称
					String merchantName = "";
					try {
						Map<String,String> merchantMap = commonLogic.getMerchantRedis(clientId, merchantId);
						if (Checker.isNotEmpty(merchantMap)) {
							merchantName = merchantMap.get("merchant_name");// 门店名称
							LogCvt.info("商户名称：" + merchantName);
						}
					} catch (Exception e) {
						LogCvt.error(e.getMessage(),e);
					}
					settlement.setMerchantName(merchantName);
					
					// 添加结算记录为结算中的记录
					settlement.setSettleState(SettlementStatus.settlementing.getCode());
					List<String> ticketIdList = new ArrayList<String>();
					ticketIdList.add(ticketId);
					settlement.setTickets(ticketIdList);
					
					//计算抵扣价
					money = BaseSubassembly.countDeductibalCashValueOfGroup(productMongo, settlement);
					productCategoryMap.put(productId, settlement);
				}
				// 商品购买单价
				realPrice += money;
			}
			/**
			 * 计算结算总金额
			 */
			double qrcodePrice = Arith.div(realPrice, 1000, 2);
			
			/**
			 * 检查是否有记录进行结算
			 */
			if(productCategoryMap.isEmpty()){
				return new ResultBean(ResultCode.settlement_ticket_error);
			}
			
			/**
			 * 添加Mongo结算记录
			 */
			Iterator<String> keys = productCategoryMap.keySet().iterator();
			while(keys.hasNext()){
				boolean isSucc = settlementSupport.createSettlement(productCategoryMap.get(keys.next()));
				if(!isSucc){
					LogCvt.error(new StringBuffer("添加结算记录异常：").append(JSonUtil.toJSonString(productCategoryMap)).toString());
					// 如果出现异常回滚删除结算信息
					settlementSupport.deleteSettlement(settlementId);
					return new ResultBean(ResultCode.failed,"添加结算记录失败");
				}
			}
			
			/**
			 * 进入支付转账>>方付通转商户账户
			 */
			String paymentId = "";
			boolean flag = false;
			try {
				ResultBean result = new PaymentCoreLogicImpl().settleToMerchantCapital(orderId,clientId, qrcodePrice, merchantId, outletId);//paymengLogic.settleMerchantPaymentData(orderId, qrcodePrice, clientId, merchantId, outletId);
				paymentId = result.getData().toString();
				flag = result.isSuccess();
				LogCvt.info("团购结算请求结算转账结果："+result.getMsg());
			} catch (Exception e) {
				LogCvt.error(e.getMessage(),e);
			}
			/**
			 *  状态依然是结算中等待异步通知结果。
			 */
			if(flag){
				settlementSupport.upateSettlement(settlementId,SettlementStatus.settlementing.getCode(),paymentId);
			}else{
				settlementSupport.upateSettlement(settlementId,SettlementStatus.settlementfailed.getCode(),paymentId);
			}
			
			Map<String,List<String>> data = new HashMap<String,List<String>>();
			// 去除结算校验不通过的结算记录
			for(String ticketId : ticketsFaild){
				tickets.remove(ticketId);
			}
			data.put("succList", tickets);
			data.put("failList", ticketsFaild);
			return new ResultBean(ResultCode.success,data);
		} catch (Exception e) {
			LogCvt.error("团购结算异常["+JSonUtil.toJSonString(tickets)+"],merchantId:"+merchantId+",outletId:"+outletId,e);
		}
		
		return new ResultBean(ResultCode.failed);
		
	}

	/**
	 *  结算异步通知接口
	  * @Title: notifySettlement
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param settlementId
	  * @param @param type
	  * @param @param remark
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#notifySettlement(java.lang.String, com.froad.common.enums.SettlementType, java.lang.String)
	 */
	@Override
	public ResultBean notitySettlement(String paymentId,SettlementStatus type, String remark) {
		// TODO Auto-generated method stub
		
		if(!type.equals(SettlementStatus.settlementsucc) && !type.equals(SettlementStatus.settlementfailed)){
			LogCvt.info("修改结算状态不能为待结算或结算中...");
			return new ResultBean(ResultCode.settlement_state_error);
		}
		boolean result = settlementSupport.upateByPaymentId(paymentId,type.getCode(),remark);
		if(result){
			return new ResultBean(ResultCode.success);
		}else{
			return new ResultBean(ResultCode.failed,"更新结算记录失败");
		}
		
	}
	
	/**
	 *  更新状态
	  * @Title: updateSettlement
	  * @Description: TODO
	  * @author: share 2015年3月30日
	  * @modify: share 2015年3月30日
	  * @param @param id
	  * @param @param type
	  * @param @param remark
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#updateSettlement(java.lang.String, com.froad.common.enums.SettlementType, java.lang.String)
	 */
	@Override
	public ResultBean updateSettlement(String id,SettlementStatus type, String remark) {
		// TODO Auto-generated method stub
//		if(!type.equals(SettlementStatus.settlementsucc) && !type.equals(SettlementStatus.settlementfailed)){
//			LogCvt.info("修改结算状态不能为待结算或结算中...");
//			return new ResultBean(ResultCode.settlement_state_error);
//		}
		LogCvt.info("BOSS修改结算状态："+id+",type:"+type.getCode());
		Settlement settlement = settlementSupport.queryById(id);
		if(settlement == null){
			return new ResultBean(ResultCode.settlement_not_exists);
		}
			
		boolean flag = settlementSupport.upateById(settlement.getId(),type.getCode(),remark);
		if(flag){
			return new ResultBean(ResultCode.success);
		}else{
			return new ResultBean(ResultCode.failed,"更新结算记录失败");
		}
		
	}

	/**
	 *  检查面对面结算状态
	  * @Title: checkAgainRequest
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param orderId
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	private boolean checkAgainRequestForQrcode(String orderId){
		int count = settlementSupport.countSettlement(orderId);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 *  检查团购结算是否重复请求
	  * @Title: checkAgainRequestForGroup
	  * @Description: TODO
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param orderId
	  * @param @param subOrderId
	  * @param @param ticketId
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	private boolean checkAgainRequestForGroup(String orderId,String subOrderId,String ticketId){
		int count = settlementSupport.querySettlement(orderId,subOrderId,ticketId);
		if(count > 0){
			return true;
		}
		
		return false;
	}

	
	/**
	 *  分页查询
	  * @Title: querySettlementByPage
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param req
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#querySettlementByPage(com.froad.po.settlement.SettlementReq)
	 */
	@Override
	public MongoPage querySettlementByPage(SettlementReq req) {
		// TODO Auto-generated method stub
		return settlementSupport.queryByPage(req);
	}
	
	@Override
	public MongoPage querySettlementByPage(SettlementPage page) {
		return settlementSupport.queryByPage(buildQueryPageRequest(page));
	}

	@Override
	public ExportResultRes exportSettlementByPage(SettlementPage page) {
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "");
		PageVo pageVo = page.getPage();
		if (null == pageVo) {
			pageVo = new PageVo();
		}
		pageVo.setPageNumber(1);
		pageVo.setPageSize(20000);
		page.setPage(pageVo);
		MongoPage mongoPage = null;
		boolean hasNext = false; // 是否还有下一页

		ExcelWriter excelWriter = null;
		try {
			do {
				LogCvt.debug("分页导出第" + pageVo.getPageNumber() + "页【结算记录】到Excel，每页显示" + pageVo.getPageSize() + "条，hasNext=" + hasNext);
//				mongoPage = this.querySettlementByPage(page); // 分页查询数据
				mongoPage = settlementSupport.exportByPage(buildQueryPageRequest(page)); // 分页查询数据
				if (mongoPage.getPageNumber() == 1) {
					excelWriter = new ExcelWriter(mongoPage.getPageSize());
				}
				
				generateExcel(excelWriter, mongoPage); // 生成excel

				pageVo.setTotalCount(mongoPage.getTotalCount());
				pageVo.setPageCount(mongoPage.getPageCount());
				pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
				pageVo.setLastPageNumber(mongoPage.getPageNumber());
				pageVo.setPageNumber(mongoPage.getPageNumber() + 1); // 翻到下一页
				pageVo.setLastRecordTime(mongoPage.getLastRecordTime());

				page.setPage(pageVo);
				hasNext = mongoPage.getHasNext(); // 是否还有下一页
			} while (hasNext);
		} catch (Exception e) {
			LogCvt.error("导出结算记录到Excel失败，原因：" + e.getMessage(), e);
			resultVo = new ResultVo(ResultCode.failed.getCode(), "导出结算记录失败");
		}
		res.setResultVo(resultVo);
		res.setUrl(excelWriter != null ? excelWriter.getUrl() : "");
		return res;
	}

	/**
	 * generateExcel:生成excel
	 *
	 * @author vania
	 * 2015年9月11日 上午9:02:29
	 * @param excelWriter
	 * @param mongoPage
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	private void generateExcel(ExcelWriter excelWriter, MongoPage mongoPage ) throws FroadBusinessException,Exception {
//		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "");
//		String url = ""; // 报表URL
		List<Settlement> settlementList = (List<Settlement>) mongoPage.getItems();
		int count = null == settlementList ? 0 : settlementList.size();
		
		String[] header = null; // 表头
		List<List<String>> data = null; // 表数据
		
		String sheetName = ""; // sheetName
		
		header = new String[] { "序号", "订单编号", "结算单号", "业务类型", "商户名称", "券码", "结算金额", "结算时间", "状态" };
		data = new ArrayList<List<String>>();
		
//		sheetName = "结算查询-Page" + mongoPage.getPageNumber();
		sheetName = "结算查询";
		for (int i = 0; i < count; i++) {
			Settlement settlement = settlementList.get(i);
			if(null == settlement)continue;
			String[] row = new String[header.length]; // 行数据
			row[0] = (i + 1) + "";
			row[1] = settlement.getOrderId();
			row[2] = settlement.getSettlementId();
			row[3] = SettlementType.getDescription(settlement.getType());
			row[4] = settlement.getMerchantName();
			List<String> tickets = settlement.getTickets();
			row[5] = tickets == null || tickets.size() < 1 ? "--" : tickets.get(0);
			row[6] = Arith.getDoubleDecimalString(Arith.div(settlement.getMoney(), 1000, 2));
			row[7] = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, settlement.getCreateTime());
			row[8] = !settlement.getSettleState().equals(SettlementStatus.settlementNoInvalid.getCode())?SettlementStatus.getDescription(settlement.getSettleState()):""; // 过滤 无效结算记录
//			LogCvt.debug("ROW:" + ArrayUtils.toString(row));
			data.add(Arrays.asList(row));
		}
		excelWriter.write(Arrays.asList(header), data, sheetName, "结算查询");
//		url = ExcelUtil.export(Arrays.asList(header), data, sheetName, "结算查询");
		
	}
	
	/**
	 * buildQueryPageRequest:构建分页查询请求对象
	 *
	 * @author vania
	 * 2015年9月2日 上午11:33:06
	 * @param page
	 * @return
	 * 
	 */
	private SettlementReq buildQueryPageRequest(SettlementPage page) {
		PageVo pagevo = page.getPage();
		MongoPage mongoPage = new MongoPage();
		mongoPage.setPageNumber(pagevo.getPageNumber());
		mongoPage.setPageSize(pagevo.getPageSize());
		mongoPage.setPageNumber(pagevo.getPageNumber());
		mongoPage.setLastPageNumber(pagevo.getLastPageNumber());
		// 第一条记录开始时间
		if (pagevo.getFirstRecordTime() > 0){
			mongoPage.setFirstRecordTime(pagevo.getFirstRecordTime());
		}
		// 最后一条记录开始时间
		if (pagevo.getLastRecordTime() > 0) {
			mongoPage.setLastRecordTime(pagevo.getLastRecordTime());
		}
		// 总记录数
		if (pagevo.getTotalCount() > 0){
			mongoPage.setTotalCount(pagevo.getTotalCount());
		}
		
		/**
		 *  接口调用
		 */
		SettlementReq req = new SettlementReq();
		req.setBegDate(pagevo.getBegDate());
		req.setEndDate(pagevo.getEndDate());
		req.setClientId(page.getClientId());
		if(page.getMerchantName()!=null){
			req.setMerchantName(page.getMerchantName());
		}
		if(page.getOutletName()!=null){
			req.setOutletName(page.getOutletName());
		}
		req.setPage(mongoPage);
		req.setSettleState(SettlementStatus.getSettlementByCode(page.getSettleState()));
		req.setOrderId(page.getOrderId());
		req.setBillNo(page.getBillNo());
		req.setType(page.getType());
		req.setTicketId(page.getTicketId());
		req.setInSettleState(page.getInSettleState());
		req.setNotInSettleState(page.getNotInSettleState());
		return req;
	}

	/**
	 *  按条件查询
	  * @Title: querySettlement
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param req
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#querySettlement(com.froad.po.settlement.SettlementReq)
	 */
	@Override
	public List<Settlement> querySettlement(SettlementReq req) {
		// TODO Auto-generated method stub
		return settlementSupport.queryList(req);
	}

	/**
	 *  查询详情
	  * @Title: querySettlementById
	  * @Description: TODO
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param settlementId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#querySettlementById(java.lang.String)
	 */
	@Override
	public Settlement querySettlementById(String settlementId) {
		// TODO Auto-generated method stub
		return settlementSupport.queryById(settlementId);
	}
	
	/**
	 *  结算记录设置为无效
	  * @Title: settlementUninvalid
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param subOrderId
	  * @param @param remark
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#settlementUninvalid(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultBean settlementUninvalid(String subOrderId, String remark) {
		// TODO Auto-generated method stub
		boolean flag = settlementSupport.updateSettlementBySubOrderId(subOrderId, SettlementStatus.settlementNoInvalid.getCode(), remark);
		if(!flag){
			return new ResultBean(ResultCode.failed,"修改结算记录失败");
		}
		return new ResultBean(ResultCode.success);
	}

	/**
	 *  查询结算中的支付ID
	  * @Title: querySettlementint
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#querySettlementint()
	 */
	@Override
	public List<String> querySettlementing() {
		// TODO Auto-generated method stub
		return settlementSupport.querySettlementing();
	}
	
	/**
	 *   按支付ID更新结算状态
	  * @Title: updateByPaymentId
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @param paymentId
	  * @param @param code
	  * @param @return
	  * @throws
	  * @see com.froad.logic.SettlementLogic#updateByPaymentId(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateByPaymentId(String paymentId, String code) {
		// TODO Auto-generated method stub
		return settlementSupport.upateByPaymentId(paymentId, code,null);
	}

	/**
	 * 根据券id集合查询券的结算状态
	 * @param ticketIdList
	 * @return    
	 * @see com.froad.logic.SettlementLogic#getTicketIdSettlementList(java.util.List)
	 */
	public List<Settlement> getTicketIdSettlementList(List<String> ticketIdList){
		long st = EsyT.currTime();
		List<Settlement> dataResult = settlementSupport.querySettlementStatusList(ticketIdList);
		LogCvt.info("根据券id集合查询券的结算状态耗时: " + (EsyT.currTime() - st));
		return dataResult;
	}
	
	public static void main(String[] args) {
//		String orderId = "288818659328";
//		
//		System.out.println(new SettlementLogicImpl().paySettlement(orderId));
		
		DBObject dbo = new BasicDBObject();
		dbo.put("aa", null);
		System.out.println(dbo);
		
	}
}

