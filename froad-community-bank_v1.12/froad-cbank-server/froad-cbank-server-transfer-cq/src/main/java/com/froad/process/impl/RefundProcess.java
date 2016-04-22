package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.cbank.persistent.common.enums.RefundsType;
import com.froad.cbank.persistent.entity.Refunds;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chonggou.mongo.refund.RefundHistory;
import com.froad.db.chonggou.mongo.refund.RefundPaymentInfo;
import com.froad.db.chonggou.mongo.refund.RefundProduct;
import com.froad.db.chonggou.mongo.refund.RefundShoppingInfo;
import com.froad.db.chongqing.entity.OrderProduct;
import com.froad.db.chongqing.mappers.OrderMapper;
import com.froad.db.chongqing.mappers.RefundsMapper;
import com.froad.enums.FieldMapping;
import com.froad.enums.ModuleID;
import com.froad.enums.PaymentMethod;
import com.froad.enums.RefundResource;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *  退款数据迁移
  * @ClassName: TransRefundsProcess
  * @author share 2015年5月3日
  * @modify share 2015年5月3日
 */
public class RefundProcess extends AbstractProcess {

	private SimpleID simpleId = new SimpleID(ModuleID.refund);
	
	private TransferMapper tsfMapper = null;
	private RefundsMapper transRefundsMapper = null;
	private OrderMapper orderMapper = null;
	
	public RefundProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void begin() {
		super.begin();
		
		tsfMapper = sqlSession.getMapper(TransferMapper.class);
		transRefundsMapper = cqSqlSession.getMapper(RefundsMapper.class);
		orderMapper = cqSqlSession.getMapper(OrderMapper.class);
	}	
	
	@Override
	public void before() {
		// delete records by client id
		deleteRecords();
	}
	
	@Override
	public void process() {
		LogCvt.info("退款迁移开始");
		
		Map<String, String> merMap = new HashMap<String, String>();
		Map<String, String> proMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
		SubOrderMongo subOrder = null;
		ProductMongo productMongo = null;
		
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
		List<Transfer> orderList = tsfMapper.queryGroupList(TransferTypeEnum.order_id);
		if(orderList != null && orderList.size() >0) {
			for(Transfer tf : orderList) {
				orderMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		// 存在组合支付的数据
//		List<String> transDuplicateList = transRefundsMapper.getCombineOrderSn();
//		List<String> handledList = new ArrayList<String>();
		// 查询团购卷的全部退款信息
		List<Refunds> refundList = transRefundsMapper.selectAll();
		for(int i =0; i < refundList.size(); i++){
			Refunds refund = refundList.get(i);
//			if(handledList.contains(refund.getOrderSn())){
//				LogCvt.info("此退款记录是组合支付已合并处理TransSn:"+refund.getOrderSn());
//				continue;
//			}
			// 退款mongo对象
			RefundHistory history = new RefundHistory();
			// 创建时间
			long createTime = refund.getCreateTime().getTime();
			long updateTime = refund.getUpdateTime().getTime();
			history.setCreateTime(createTime);
			history.setRefundTime(updateTime);
			// 客户端ID
			String clientId = Constans.clientId;
			history.setClientId(clientId);
			// 退款id
			String _id = simpleId.nextId();
			history.set_id(_id);
			// 订单号表
			OrderProduct orderProduct = orderMapper.selectProductInfoBySN(refund.getOrderSn());
			// 订单ID
			String orderIds = orderMap.get(String.valueOf(orderProduct.getOrderId()));
			String orderId = orderIds.split(",")[0];
			history.setOrderId(orderId);
			// 子订单ID
			String subOrderId = null;
			try {
				subOrderId = orderIds.split(",")[1];
			} catch (Exception e){
				LogCvt.error(new StringBuffer("子订单ID为null, 旧订单号=")
						.append(orderProduct.getOrderId()).append(" 新订单号=")
						.append(orderId).toString());
				continue;
			}
			// 退款状态
			String refundState = getRefundState(refund.getState());
			// 退款时间
			// 交易trans——sn
			String type = getPayMethod(refund.getType());
			if(type == null){
				LogCvt.error("退款流水记录支付类型为null，ID="+refund.getId());
				continue;
			}
			// mongo退款支付信息
			List<RefundPaymentInfo> paymentInfoList = new ArrayList<RefundPaymentInfo>();
			RefundPaymentInfo paymentInfo = new RefundPaymentInfo();
			String paymentId = refund.getSn();
			// 支付ID
			paymentInfo.setPaymentId(paymentId);
			// 退款金额
			int refundValue = Arith.mul(Double.parseDouble(refund.getRefundValue()), 1000);
			paymentInfo.setRefundValue(refundValue);
			// 支付结果
			String resultCode = RefundState.REFUND_SUCCESS.getCode().equals(refundState)?ResultCode.success.getCode():ResultCode.failed.getCode();
			paymentInfo.setResultCode(resultCode);
			// 结果描述
			String resultDesc = RefundState.REFUND_SUCCESS.getCode().equals(refundState)?"退款成功":"退款失败";
			paymentInfo.setResultDesc(resultDesc);
			paymentInfo.setType(type);
			paymentInfoList.add(paymentInfo);
			// 检查是否是组合支付的记录
//			if(transDuplicateList.contains(transSn)){
//				Refunds dupRefunds = refundList.get(i+1);
//				handleDupRefunds(paymentInfoList,dupRefunds);
//				handledList.add(transSn);
//			}
			history.setPaymentInfo(paymentInfoList);
			history.setReason("用户退款");
			history.setRefundResource(RefundResource.USER_REFUND.getCode());
			history.setRefundState(refundState);
			history.setMemberCode(orderProduct.getMemberCode()+"");
			// 商品信息
			List<RefundShoppingInfo> shoppingInfoList = new ArrayList<RefundShoppingInfo>();
			RefundShoppingInfo shoppingInfo = new RefundShoppingInfo();
			
			subOrder = findSubOrderBySubOrderId(subOrderId);
			if (null == subOrder){
				LogCvt.error(new StringBuffer("cb_suborder_product找不到相应的子订单记录，sub_order_id=").append(subOrderId).toString());
				continue;
			}
			productMongo = subOrder.getProducts().get(0);
			
			// 商户ID
			shoppingInfo.setMerchantId(subOrder.getMerchantId());
			// 商户名称
			shoppingInfo.setMerchantName(subOrder.getMerchantName());
			shoppingInfo.setSubOrderId(subOrderId);
			shoppingInfo.setType(subOrder.getType());
			List<RefundProduct> products = new ArrayList<RefundProduct>();
			RefundProduct product = new RefundProduct();
			product.setImageUrl(productMongo.getProductImage());
			product.setPrice(Arith.mul(Double.parseDouble(orderProduct.getSinglePrice()), 1000));
			// 商品ID
			product.setProductId(productMongo.getProductId());
			product.setProductName(productMongo.getProductName());
			product.setQuantity((int)Arith.div(Double.valueOf(refund.getRefundValue()), Double.valueOf(orderProduct.getSinglePrice())));
			products.add(product);
			shoppingInfo.setProducts(products);
			shoppingInfoList.add(shoppingInfo);
			history.setShoppingInfo(shoppingInfoList);
			mongo.add(history, MongoTableName.CB_ORDER_REFUNDS);
			// 添加中间表记录
			Transfer tf = new Transfer();
			tf.setOldId(refund.getId()+"");
			tf.setNewId(_id);
			tf.setType(TransferTypeEnum.refund_id);
			tsfMapper.insert(tf);
		}
		
		LogCvt.info("退款迁移完成");
	}

	/**
	 *  处理组合支付合并
	  * @Title: handleDupRefunds
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @param paymentInfoList
	  * @param @param refund    
	  * @return void    
	  * @throws
	 */
	private void handleDupRefunds(List<RefundPaymentInfo> paymentInfoList,Refunds refund) {
		
		RefundPaymentInfo paymentInfo = new RefundPaymentInfo();
		
		// 退款状态
		String refundState = getRefundState(refund.getState());
		// 支付ID
		String paymentId = refund.getSn();
		paymentInfo.setPaymentId(paymentId);
		// 退款金额
		int refundValue = Arith.mul(Double.parseDouble(refund.getRefundValue()), 1000);
		paymentInfo.setRefundValue(refundValue);
		// 支付结果
		String resultCode = RefundState.REFUND_SUCCESS.getCode().equals(refundState)?ResultCode.success.getCode():ResultCode.failed.getCode();
		paymentInfo.setResultCode(resultCode);
		// 结果描述
		String resultDesc = RefundState.REFUND_SUCCESS.getCode().equals(refundState)?"退款成功":"退款失败";
		paymentInfo.setResultDesc(resultDesc);
		// 支付方式
		String type = getPayMethod(refund.getType());
		paymentInfo.setType(type);
		paymentInfoList.add(paymentInfo);
	}
	
	
	public String getPayMethod(RefundsType type){
		if(RefundsType.fftPoints.getCode().equals(type.getCode())){
			return PaymentMethod.froadPoints.getCode();
		}else if(RefundsType.money.getCode().equals(type.getCode())){
			return PaymentMethod.cash.getCode();
		}else if(RefundsType.bankPoints.getCode().equals(type.getCode())){
			return PaymentMethod.bankPoints.getCode();
		}
		return null;
	}
	
	
	public String getRefundState(com.froad.cbank.persistent.common.enums.RefundState state){
		if(com.froad.cbank.persistent.common.enums.RefundState.refund_success.getCode().equals(state.getCode())){
			return RefundState.REFUND_SUCCESS.getCode();
		}else if(com.froad.cbank.persistent.common.enums.RefundState.refund_fail.getCode().equals(state.getCode())){
			return RefundState.REFUND_FAILED.getCode();
		}else if(com.froad.cbank.persistent.common.enums.RefundState.refund_request_fail.getCode().equals(state.getCode())){
			return RefundState.REFUND_FAILED.getCode();
		}else if(com.froad.cbank.persistent.common.enums.RefundState.refund_wait.getCode().equals(state.getCode())){
			return RefundState.REFUND_INIT.getCode();
		}
		return null;
	}
	
	/**
	 * 根据子订单好获取子订单信息
	 * 
	 * @param subOrderId
	 * @return
	 */
	private SubOrderMongo findSubOrderBySubOrderId(String subOrderId){
		SubOrderMongo subOrder = null;
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put("client_id", Constans.clientId);
		queryObj.put("sub_order_id", subOrderId);
		subOrder = mongo.findOne(queryObj, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		
		return subOrder;
	}

	/**
	 * delete records by client id
	 */
	private void deleteRecords(){
		DBObject queryObj = null;
		
		queryObj = new BasicDBObject();
		queryObj.put(FieldMapping.CLIENT_ID.getMongoField(), Constans.clientId);
		
		mongo.remove(queryObj, MongoTableName.CB_ORDER_REFUNDS);
	}
}

