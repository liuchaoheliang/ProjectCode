package com.froad.process.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.ProductGroup;
import com.froad.db.ahui.entity.ProductPresell;
import com.froad.db.ahui.entity.TransDto;
import com.froad.db.ahui.mappers.MerchantExMapper;
import com.froad.db.ahui.mappers.PayMapper;
import com.froad.db.ahui.mappers.ProductImageMapper;
import com.froad.db.ahui.mappers.ProductMapper;
import com.froad.db.ahui.mappers.TransMapper;
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
import com.froad.fft.persistent.common.enums.PayState;
import com.froad.fft.persistent.common.enums.PayType;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.ProductFamous;
import com.froad.fft.persistent.entity.ProductImage;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.froad.util.SimpleID;

/**
 *  支付记录退款数据迁移
  * @ClassName: TransRefundsProcess
  * @Description: TODO
  * @author share 2015年5月3日
  * @modify share 2015年5月3日
 */
public class PayRefundsProcess extends AbstractProcess {

	private SimpleID simpleId = new SimpleID(ModuleID.refund);
	
	public PayRefundsProcess(String name, ProcessServiceConfig config) {
		super(name, config);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		TransMapper transMapper = ahSqlSession.getMapper(TransMapper.class);
		MerchantExMapper merchantMapper = ahSqlSession.getMapper(MerchantExMapper.class);
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		PayMapper payMapper = ahSqlSession.getMapper(PayMapper.class);
		ProductImageMapper productImageMapper = ahSqlSession.getMapper(ProductImageMapper.class);
		// 存在重复数据
		List<Map<String,Long>> transRefundsDuplicate = payMapper.selectPayRefundsDuplicate();
		List<String> transDuplicateList = getTransSn(transRefundsDuplicate);
		List<String> handledList = new ArrayList<String>();
		// 查询团购卷的全部退款信息
		List<Pay> refundList = payMapper.selectPayRefunds();
		for(int i =0; i < refundList.size(); i++){
			Pay pay = refundList.get(i);
			if(handledList.contains(pay.getTransId().toString())){
				LogCvt.info("此退款记录是组合支付已合并处理TransId:"+pay.getTransId().toString());
				continue;
			}
			// 退款mongo对象
			RefundHistory history = new RefundHistory();
			// 创建时间
			long createTime = pay.getCreateTime().getTime();
			long updateTime = pay.getUpdateTime().getTime();
			history.setCreateTime(createTime);
			history.setRefundTime(updateTime);
			// 客户端ID
			String clientId = Constans.clientId;
			history.setClientId(clientId);
			// 退款id
			String _id = simpleId.nextId();
			history.set_id(_id);
			// 订单号表
			TransDto trans = transMapper.queryById(pay.getTransId());
			// 订单ID
			String orderIds = transferMapper.queryNewId(String.valueOf(trans.getId()), TransferTypeEnum.order_id).getNewId();
			String orderId = orderIds.split(",")[0];
			history.setOrderId(orderId);
			// 子订单ID
			String subOrderId = orderIds.split(",")[1];
			// 退款状态
			String refundState = getRefundState(pay.getPayState());
			// 退款时间
			// 交易trans——sn
			String transId = pay.getTransId().toString();
			String type = getPayMethod(pay.getPayType());
			if(type == null){
				LogCvt.error("退款流水记录支付类型为null，ID="+pay.getId());
				continue;
			}
			// mongo退款支付信息
			List<RefundPaymentInfo> paymentInfoList = new ArrayList<RefundPaymentInfo>();
			RefundPaymentInfo paymentInfo = new RefundPaymentInfo();
			String paymentId = pay.getSn();
			// 支付ID
			paymentInfo.setPaymentId(paymentId);
			// 退款金额
			int refundValue = Arith.mul(Double.parseDouble(pay.getPayValue()), 1000);
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
			if(transDuplicateList.contains(transId)){
				Pay pays = refundList.get(i+1);
				handleDupRefunds(paymentInfoList,pays);
				handledList.add(transId);
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
			String merchantId =transferMapper.queryNewId(trans.getMerchantId()+"", TransferTypeEnum.merchant_id).getNewId();
			// 商户名称
			String merchantName = merchantMapper.selectById(trans.getMerchantId()).getName();
			shoppingInfo.setMerchantId(merchantId);
			shoppingInfo.setMerchantName(merchantName);
			shoppingInfo.setSubOrderId(subOrderId);
			// 支付方式
			String subOrderType = getRefundType(trans.getType());
			shoppingInfo.setType(subOrderType);
			List<RefundProduct> products = new ArrayList<RefundProduct>();
			RefundProduct product = new RefundProduct();
			ProductImage image = productImageMapper.selectProductImageByProductId(trans.getProductId());
			// 线上积分兑换
			if(image !=null){
				product.setImageUrl(Constans.getThumbnail(image.getThumbnail()));
			}
			// 商品ID
			product.setPrice(Arith.mul(Double.parseDouble(trans.getSingle()), 1000));
			String productId =transferMapper.queryNewId(trans.getProductId()+"", TransferTypeEnum.product_id).getNewId();
			product.setProductId(productId);
			product.setProductName(trans.getProductName());
			product.setQuantity(trans.getQuantity());
			products.add(product);
			
			shoppingInfo.setProducts(products);
			shoppingInfoList.add(shoppingInfo);
			history.setShoppingInfo(shoppingInfoList);
			mongo.add(history, MongoTableName.CB_ORDER_REFUNDS);
			// 添加中间表记录
			Transfer tf = new Transfer();
			tf.setOldId(pay.getId()+"");
			tf.setNewId(_id);
			tf.setType(TransferTypeEnum.refund_id);
			transferMapper.insert(tf);
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
	private void handleDupRefunds(List<RefundPaymentInfo> paymentInfoList,Pay pay) {
		
		RefundPaymentInfo paymentInfo = new RefundPaymentInfo();
		
		// 退款状态
		String refundState = getRefundState(pay.getPayState());
		// 支付ID
		String paymentId = pay.getSn();
		paymentInfo.setPaymentId(paymentId);
		// 退款金额
		int refundValue = Arith.mul(Double.parseDouble(pay.getPayValue()), 1000);
		paymentInfo.setRefundValue(refundValue);
		// 支付结果
		String resultCode = RefundState.REFUND_SUCCESS.getCode().equals(refundState)?ResultCode.success.getCode():ResultCode.failed.getCode();
		paymentInfo.setResultCode(resultCode);
		// 结果描述
		String resultDesc = RefundState.REFUND_SUCCESS.getCode().equals(refundState)?"退款成功":"退款失败";
		paymentInfo.setResultDesc(resultDesc);
		// 支付方式
		String type = getPayMethod(pay.getPayType());
		paymentInfo.setType(type);
		paymentInfoList.add(paymentInfo);
	}
	
	
	public String getPayMethod(PayType type){
		if(PayType.cash.getCode().equals(type.getCode())){
			return PaymentMethod.froadPoints.getCode();
		}else if(PayType.points.getCode().equals(type.getCode())){
			return PaymentMethod.cash.getCode();
		}
		return null;
	}
	
	
	public String getRefundState(PayState state){
		if(PayState.refund_request_fail.getCode().equals(state.getCode())){
			return RefundState.REFUND_FAILED.getCode();
		}else if(PayState.refund_success.getCode().equals(state.getCode())){
			return RefundState.REFUND_SUCCESS.getCode();
		}else if(PayState.refund_fail.getCode().equals(state.getCode())){
			return RefundState.REFUND_FAILED.getCode();
		}
		return null;
	}
	
	private List<String> getTransSn(List<Map<String,Long>> transRefundsDuplicate){
		List<String> list = new ArrayList<String>();
		for(Map<String,Long> refunds : transRefundsDuplicate){
			list.add(refunds.get("trans_id").toString());
		}
		return list;
	}
	
	
	public String getRefundType(TransType type){
		if(TransType.points_exchange.getCode().equals(type.getCode())){
			return SubOrderType.online_points_org.getCode();
		}else if(TransType.presell.getCode().equals(type.getCode())){
			return SubOrderType.presell_org.getCode();
		}else if(TransType.myth.getCode().equals(type.getCode())){
			return SubOrderType.special_merchant.getCode();
		}else if(TransType.group.getCode().equals(type.getCode())){
			return SubOrderType.group_merchant.getCode();
		}
		return null;
	}

}

