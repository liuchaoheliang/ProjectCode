package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.TransDto;
import com.froad.db.ahui.mappers.MerchantExMapper;
import com.froad.db.ahui.mappers.ProductImageMapper;
import com.froad.db.ahui.mappers.ProductMapper;
import com.froad.db.ahui.mappers.TransMapper;
import com.froad.db.ahui.mappers.TransRefundsMapper;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chonggou.mongo.refund.RefundHistory;
import com.froad.db.chonggou.mongo.refund.RefundPaymentInfo;
import com.froad.db.chonggou.mongo.refund.RefundProduct;
import com.froad.db.chonggou.mongo.refund.RefundShoppingInfo;
import com.froad.enums.ModuleID;
import com.froad.enums.PaymentMethod;
import com.froad.enums.RefundResource;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.ProductImage;
import com.froad.fft.persistent.entity.TransRefunds;
import com.froad.fft.persistent.entity.TransRefunds.State;
import com.froad.fft.persistent.entity.TransRefunds.Type;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.froad.util.SimpleID;

/**
 *  退款数据迁移
  * @ClassName: TransRefundsProcess
  * @Description: TODO
  * @author share 2015年5月3日
  * @modify share 2015年5月3日
 */
public class TransRefundsProcess extends AbstractProcess {

	private SimpleID simpleId = new SimpleID(ModuleID.refund);
	
	public TransRefundsProcess(String name, ProcessServiceConfig config) {
		super(name, config);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		TransMapper transMapper = ahSqlSession.getMapper(TransMapper.class);
		MerchantExMapper merchantMapper = ahSqlSession.getMapper(MerchantExMapper.class);
		TransferMapper tsfMapper = sqlSession.getMapper(TransferMapper.class);
		TransRefundsMapper transRefundsMapper = ahSqlSession.getMapper(TransRefundsMapper.class);
		ProductImageMapper productImageMapper = ahSqlSession.getMapper(ProductImageMapper.class);
		
		Map<String, String> merMap = new HashMap<String, String>();
		Map<String, String> proMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
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
		List<Map<String,String>> transRefundsDuplicate = transRefundsMapper.getTransRefundsDuplicate();
		List<String> transDuplicateList = getTransSn(transRefundsDuplicate);
		List<String> handledList = new ArrayList<String>();
		// 查询团购卷的全部退款信息
		List<TransRefunds> refundList = transRefundsMapper.getTransRefundsAll();
		for(int i =0; i < refundList.size(); i++){
			TransRefunds refund = refundList.get(i);
			if(handledList.contains(refund.getTransSn())){
				LogCvt.info("此退款记录是组合支付已合并处理TransSn:"+refund.getTransSn());
				continue;
			}
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
			TransDto trans = transMapper.queryBySn(refund.getTransSn());
			// 订单ID
			String orderIds = orderMap.get(String.valueOf(trans.getId()));
			String orderId = orderIds.split(",")[0];
			history.setOrderId(orderId);
			// 子订单ID
			String subOrderId = orderIds.split(",")[1];
			// 退款状态
			String refundState = getRefundState(refund.getState());
			// 退款时间
			// 交易trans——sn
			String transSn = refund.getTransSn();
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
			if(transDuplicateList.contains(transSn)){
				TransRefunds dupRefunds = refundList.get(i+1);
				handleDupRefunds(paymentInfoList,dupRefunds);
				handledList.add(transSn);
			}
			history.setPaymentInfo(paymentInfoList);
			history.setReason("用户退款");
			history.setRefundResource(RefundResource.USER_REFUND.getCode());
			history.setRefundState(refundState);
			history.setMemberCode(trans.getMemberCode()+"");
			// 商品信息
			List<RefundShoppingInfo> shoppingInfoList = new ArrayList<RefundShoppingInfo>();
			RefundShoppingInfo shoppingInfo = new RefundShoppingInfo();
			// 商户ID
			String merchantId = merMap.get(trans.getMerchantId()+"");
			// 商户名称
			String merchantName = merchantMapper.selectById(trans.getMerchantId()).getName();
			shoppingInfo.setMerchantId(merchantId);
			shoppingInfo.setMerchantName(merchantName);
			shoppingInfo.setSubOrderId(subOrderId);
			shoppingInfo.setType(SubOrderType.group_merchant.getCode());
			List<RefundProduct> products = new ArrayList<RefundProduct>();
			RefundProduct product = new RefundProduct();
			ProductImage image = productImageMapper.selectProductImageByProductId(trans.getProductId());
			if(image !=null){
				product.setImageUrl(Constans.getThumbnail(image.getThumbnail()));
			}
			product.setPrice(Arith.mul(Double.parseDouble(trans.getSingle()), 1000));
			// 商品ID
			String productId = proMap.get(trans.getProductId()+"");
			product.setProductId(productId);
			product.setProductName(trans.getProductName());
			product.setQuantity(1);
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
		
	}

	/**
	 *  处理组合支付合并
	  * @Title: handleDupRefunds
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @param paymentInfoList
	  * @param @param refund    
	  * @return void    
	  * @throws
	 */
	private void handleDupRefunds(List<RefundPaymentInfo> paymentInfoList,TransRefunds refund) {
		
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
	
	
	public String getPayMethod(Type type){
		if(Type.fftPoints.getCode().equals(type.getCode())){
			return PaymentMethod.froadPoints.getCode();
		}else if(Type.money.getCode().equals(type.getCode())){
			return PaymentMethod.cash.getCode();
		}else if(Type.bankPoints.getCode().equals(type.getCode())){
			return PaymentMethod.bankPoints.getCode();
		}
		return null;
	}
	
	
	public String getRefundState(State state){
		if(State.success.getCode().equals(state.getCode())){
			return RefundState.REFUND_SUCCESS.getCode();
		}else if(State.fail.getCode().equals(state.getCode())){
			return RefundState.REFUND_FAILED.getCode();
		}else if(State.request_fail.getCode().equals(state.getCode())){
			return RefundState.REFUND_FAILED.getCode();
		}else if(State.wait.getCode().equals(state.getCode())){
			return RefundState.REFUND_INIT.getCode();
		}
		return null;
	}
	
	private List<String> getTransSn(List<Map<String,String>> transRefundsDuplicate){
		List<String> list = new ArrayList<String>();
		for(Map<String,String> refunds : transRefundsDuplicate){
			list.add(refunds.get("trans_sn"));
		}
		return list;
	}

}

