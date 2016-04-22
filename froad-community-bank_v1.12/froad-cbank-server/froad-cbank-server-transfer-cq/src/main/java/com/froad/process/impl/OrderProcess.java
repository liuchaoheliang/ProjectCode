package com.froad.process.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.cbank.persistent.common.enums.OrderPaymentStatus;
import com.froad.cbank.persistent.common.enums.OrderStatus;
import com.froad.cbank.persistent.common.enums.PaymentChannelType;
import com.froad.cbank.persistent.entity.Merchant;
import com.froad.cbank.persistent.entity.MerchantOutletBank;
import com.froad.cbank.persistent.entity.OrderDetails;
import com.froad.cbank.persistent.entity.Product;
import com.froad.cbank.persistent.entity.ProductComment;
import com.froad.common.mongo.CommonMongo;
import com.froad.common.qrcode.QrCodeGenerateService;
import com.froad.common.qrcode.QrCodeRequestVo;
import com.froad.common.redis.MerchantRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.OutletProduct;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.entity.OrderEx;
import com.froad.db.chongqing.mappers.MerchantMapper;
import com.froad.db.chongqing.mappers.MerchantOutletBankMapper;
import com.froad.db.chongqing.mappers.OrderMapper;
import com.froad.db.chongqing.mappers.ProductCommentMapper;
import com.froad.db.chongqing.mappers.ProductMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ModuleID;
import com.froad.enums.PaymentMethod;
import com.froad.enums.QrCodeType;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.Constans;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *  订单服务
  * @ClassName: OrderProcess
  * @Description: TODO
  * @author share 2015年6月29日
  * @modify share 2015年6月29日
 */
public class OrderProcess extends AbstractProcess {

	private SimpleID odSimpleID = new SimpleID(ModuleID.order);
	private SimpleID subSimpleID = new SimpleID(ModuleID.suborder);
	private SimpleID prodSimpleID = new SimpleID(ModuleID.product);
	private Map<String,String> productMap = new HashMap<String, String>();
	private Map<String,String> productImageMap = new HashMap<String, String>();
	private Map<String,String> recvMap = new HashMap<String, String>();
	private Map<String,String> merchantMap = new HashMap<String, String>();
	private Map<String,String> outlettMap = new HashMap<String, String>();
	private Map<String,String> merchantOrgMap = new HashMap<String, String>();
	private Map<String,String> commentMap = new HashMap<String,String>();
	private Map<String,String> productAttrMap = new HashMap<String, String>();
	private Map<String,String> bankOrgMap = new HashMap<String, String>();
	private Map<String,String> bankOrgNameMap = new HashMap<String, String>();
    
	
	MerchantRedis merchantRedis = new MerchantRedis(new RedisManager());
    
	public OrderProcess(String name, ProcessServiceConfig config) {
		super(name, config);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void before() {
		// 删除旧的mongo订单信息
		DBObject dbo = new BasicDBObject();
		dbo.put("client_id", Constans.clientId);
		
		mongo.remove(dbo, MongoTableName.CB_ORDER);
		mongo.remove(dbo, MongoTableName.CB_SUBORDER_PRODUCT);
		mongo.remove(dbo, MongoTableName.CB_OUTLET_PRODUCT);
		
		// 初始化数据
		loadData();
		
	}

	@Override
	public void process() {
		OrderMapper ordrMapper = cqSqlSession.getMapper(OrderMapper.class);
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		List<OrderEx> orderList = ordrMapper.selectAll();
		
		for (OrderEx order : orderList) {
			// 订单详情
			OrderDetails detail = order.getDetail();
			String clientId = Constans.clientId;
			/******************大订单信息******************/
			OrderMongo orderMongo = new OrderMongo();
			orderMongo.setVipDiscount(0);
			// 银行积分
			int bankPoint = arith(order.getBankPoints());
			orderMongo.setBankPoints(bankPoint);
			orderMongo.setClientId(clientId);
			// 来源
			String createSource = order.getCreateSource().getCode();
			orderMongo.setCreateSource(createSource);
			// 创建时间
			long createTime = order.getCreateTime().getTime();
			orderMongo.setCreateTime(createTime);
			// 手续费
			//int fee = arith(order.getFee());
			//orderMongo.setFee(fee);
			// 方付通积分
			int fftPoint = arith(order.getFftPoints());
			orderMongo.setFftPoints(fftPoint);
			// 是否积分兑换 默认是否
			int isPoint = 0;
			orderMongo.setIsPoint(isPoint);
			// 是否为面对面订单
			int isQrcode = 0;
			String type = order.getType().getCode();
			if("03".equals(type)){
				isQrcode = 1;
			}
			orderMongo.setIsQrcode(isQrcode);
			// 是否为描述商品
			int isSeckill = 0;
			orderMongo.setIsSeckill(isSeckill);
			// 会员号
			Long memberCode = order.getMemberCode();
			orderMongo.setMemberCode(memberCode);
			// 会员名
			String memberName = order.getMemberName();
			orderMongo.setMemberName(memberName);
//			String remark = order.getRemark();
			String remark = order.getCancelRemark();//订单关闭检查cancelRemark而非remark
			orderMongo.setRemark(remark);
			// 订单状态 
			String orderStatus = hanldOrderStatus(order.getOrderStatus(),order.getPaymentStatus(),remark);
			orderMongo.setOrderStatus(orderStatus);
			// 支付方式
			String paymentMethod = hanldPaymentMethod(order.getPaymentChannelType(),fftPoint,bankPoint);
			orderMongo.setPaymentMethod(paymentMethod);
			// 支付时间
			Long paymentTime = order.getPaymentTime() == null? null : order.getPaymentTime().getTime();
			orderMongo.setPaymentTime(paymentTime);
			// 电话
			String phone = order.getPhone();
			orderMongo.setPhone(phone);
			// 实际金额
			int realPrice = arith(order.getRealPrice());
			orderMongo.setRealPrice(realPrice);
			// 提货网点
			String recvId = null;
//			String deliverId = null;
			Long receiverId = order.getReceiverId();
			String deveryType = order.getDeliveryType();
			
			if("10".equals(deveryType)){
				// 自提
				recvId = recvMap.get(receiverId.toString());
			}else if("20".equals(deveryType)){
				// 配送
				recvId = recvMap.get(receiverId.toString());
			}
			if(recvId == null && isQrcode ==0){
				LogCvt.error("订单收货地址信息为空，orderId:"+order.getId());
			}
			
			orderMongo.setRecvId(recvId);
//			orderMongo.setDeliverId(deliverId);
			// 状态
			String state = handOrderState(order.getOrderStatus());
			orderMongo.setState(state);
			// 总金额
			int totalPrice = arith(order.getTotalPrice());
			orderMongo.setTotalPrice(totalPrice);
			// 商品ID(面对面)
			if(isQrcode == 1){
				handFace2FaceOrder(orderMongo, order);
			}
			// 生成订单ID
			String orderId = odSimpleID.nextId();
			orderMongo.setOrderId(orderId);
			
			// 子订单ID
			String subOrderId = "";
			if(isQrcode != 1){
			
				/****************子订单*****************/
				SubOrderMongo subOrderMongo = new SubOrderMongo();
				
				// 机构码
				String fOrgCode = "000000";
				orderMongo.setForgCode(fOrgCode);
				String orgCode  = merchantOrgMap.get(order.getMerchantId().toString());
				String[] orgCodes = orgCode.split(",");
				if(orgCodes.length == 0){
					orgCodes = new String[]{"",""};
				}
				String sOrgCode = "".equals(orgCodes[0])?null:orgCodes[0];
				orderMongo.setSorgCode(Constans.filterOrg(sOrgCode));
				String tOrgCode = "".equals(orgCodes[1])?null:orgCodes[1];
				orderMongo.setTorgCode(Constans.filterOrg(tOrgCode));
				subOrderMongo.setForgCode(Constans.filterOrg(fOrgCode));
				subOrderMongo.setSorgCode(Constans.filterOrg(sOrgCode));
				subOrderMongo.setTorgCode(Constans.filterOrg(tOrgCode));
				subOrderMongo.setOrderId(orderId);
				subOrderMongo.setCreateTime(createTime);
				subOrderMongo.setClientId(clientId);
				subOrderMongo.setMemberCode(memberCode);
				subOrderMongo.setMemberName(memberName);
				// 商品类型1-团购，2-预售。
				String merchantId = "";
				String merchantName = "";
				String productType = productAttrMap.get(detail.getProductId()+"_type");
				if("1".equals(productType)){
					// 商户ID
					merchantId = merchantMap.get(order.getMerchantId().toString());
					if(merchantId == null){
						LogCvt.error("商户ID获取失败,商户ID："+order.getMerchantId());
					}
					// 商户名称
					Map<String,String> merchantRedisMap = merchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
					if(merchantRedisMap != null && merchantRedisMap.size() > 0){
						merchantName = merchantRedisMap.get("merchant_name");
					}
				}else if("2".equals(productType)){
					// 预售子订单，商户ID为orgCode，商户名称为orgName
					merchantId = bankOrgMap.get(order.getMerchantOutletId()+"");
					merchantName = bankOrgNameMap.get(order.getMerchantOutletId()+"");
				}else{
					LogCvt.error("订单类型错误ID:"+order.getId());
				}
			
				subOrderMongo.setMerchantId(merchantId);
				subOrderMongo.setMerchantName(merchantName);
				subOrderMongo.setOrderStatus(orderStatus);
				// 子订单ID
				subOrderId = subSimpleID.nextId();
				subOrderMongo.setSubOrderId(subOrderId);
				List<ProductMongo> products = new ArrayList<ProductMongo>();
				ProductMongo product = new ProductMongo();
				String isComment = commentMap.get(order.getId().toString());
				product.setCommentState(isComment==null?"0":"1");
				product.setDeliverMoney(0);
				product.setMoney(arith(detail.getSingle()));
				product.setPoints(0);
				// 商品ID
				String productId = productMap.get(detail.getProductId().toString());
				product.setProductId(productId);
				product.setProductName(detail.getProductName());
				product.setProductImage(productImageMap.get(detail.getProductId().toString()));
				product.setQuantity(detail.getQuantity());
				product.setType(productType);
				product.setVipMoney(0);
				product.setVipQuantity(0);
				// 10自提 20配送
				product.setDeliveryOption("20".equals(deveryType)?"0":"1");
				products.add(product);
				subOrderMongo.setProducts(products);
				subOrderMongo.setType(productType);
				//  添加子订单
				mongo.add(subOrderMongo, MongoTableName.CB_SUBORDER_PRODUCT);
			}
			// 添加订单记录
			mongo.add(orderMongo, MongoTableName.CB_ORDER);
			// 添加中间表记录
			Transfer transfer = new Transfer();
			transfer.setOldId(order.getId().toString());
			transfer.setNewId(orderId+","+subOrderId);
			transfer.setType(TransferTypeEnum.order_id);
			transferMapper.insert(transfer);
			
		}
		
	}
	
	
	public void handFace2FaceOrder(OrderMongo orderMongo,OrderEx order){
		String clientId = orderMongo.getClientId();
		int totalPrice = orderMongo.getTotalPrice();
		// 商户ID
		String merchantId = merchantMap.get(order.getMerchantId().toString());
		if(merchantId == null){
			LogCvt.error("商户ID获取失败,商户ID："+order.getMerchantId());
		}
		orderMongo.setMerchantId(merchantId);
		// 商户名称
		Map<String,String> merchantRedisMap = merchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
		String merchantName = "";
		if(merchantRedisMap != null && merchantRedisMap.size() > 0){
			merchantName = merchantRedisMap.get("merchant_name");
		}
		orderMongo.setMerchantName(merchantName);
		// 门店ID
		String outletId = merchantMap.get(order.getMerchantOutletId().toString());
		orderMongo.setOutletId(outletId);
		// 机构码
		String fOrgCode = "000000";
		orderMongo.setForgCode(Constans.filterOrg(fOrgCode));
		String orgCode  = merchantOrgMap.get(order.getMerchantId().toString());
		String[] orgCodes = orgCode.split(",");
		if(orgCodes.length == 0){
			orgCodes = new String[]{"",""};
		}
		String sOrgCode = "".equals(orgCodes[0])?null:orgCodes[0];
		orderMongo.setSorgCode(Constans.filterOrg(sOrgCode));
		String tOrgCode = "".equals(orgCodes[1])?null:orgCodes[1];
		orderMongo.setTorgCode(Constans.filterOrg(tOrgCode));
		// 创建面对面商品
		String proId = null;
		// 二维码 重新生成
		String qrcode = null;
		OutletProduct virProduct = CommonMongo.queryOutletProduct(merchantId, outletId, totalPrice);
		if(virProduct == null) {
			OutletProduct svOutPro = new OutletProduct();
			proId = prodSimpleID.nextId();
			svOutPro.setProductId(proId);
			svOutPro.setCost(totalPrice);
			svOutPro.setMerchantId(merchantId);
			svOutPro.setClientId(Constans.clientId);
			svOutPro.setOutletId(outletId);
			CommonMongo.addOutletProduct(svOutPro);
		} else {
			proId = virProduct.getProductId();
		}
		
		if(StringUtils.isNotEmpty(merchantId)) {
			qrcode = qcode(proId, merchantId);
		}
		orderMongo.setQrcode(qrcode);
		orderMongo.setProductId(proId);
	}
	
	
	public String qcode(String productId, String merchantUserId ) {
		 generateQrCode(Constans.clientId,productId,QrCodeType.FACE2FACE.getCode());
		 String qcode = QrCodeType.FACE2FACE.getCode() + productId;
		 String key = RedisKeyUtil.cbbank_outlet_product_clientId_qrcode(Constans.clientId, qcode);
        
        RedisManager redis = new RedisManager();
        redis.hset(key, "state", "1");
        redis.hset(key, "id", productId+"_"+merchantUserId);
        return qcode;
	}
	
	
	 private void generateQrCode(String clientId,String productId,String qrCodeType){
        try {
        	
        	QrCodeGenerateService qrCodeService = new QrCodeGenerateService();
            QrCodeRequestVo qrcodeRequestVo = new QrCodeRequestVo(qrCodeType+productId, clientId);
            //面对面商品二维码url
            qrCodeService.generateQrCode(qrcodeRequestVo);
            LogCvt.debug("生成二维码结束"+new Date().getTime());
        }catch (Exception e) {
            LogCvt.error("商品二维码url异常：" +e);
            LogCvt.error("productId:"+productId+",clientId:"+clientId+",qrCodeType:"+qrCodeType);
        }
	 }
	
	
	private String handOrderState(OrderStatus orderStatus) {
		// TODO Auto-generated method stub
		String status = orderStatus.getCode();
		if("40".equals(status)){
			return "3";
		}else{
			return "1";
		}
	}

	private void loadData() {
		// TODO Auto-generated method stub
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		MerchantMapper merchantMapper = cqSqlSession.getMapper(MerchantMapper.class);
		ProductCommentMapper productCommentMapper = cqSqlSession.getMapper(ProductCommentMapper.class);
		ProductMapper productMapper = cqSqlSession.getMapper(ProductMapper.class);
		MerchantOutletBankMapper outleBankMapper = cqSqlSession.getMapper(MerchantOutletBankMapper.class);
		
		List<Product> producImagetList = productMapper.selectAllProductImage();
		if(producImagetList != null && producImagetList.size() >0) {
			for(Product tf : producImagetList) {
				productImageMap.put(tf.getId()+"" , Constans.getThumbnail(tf.getImage()));
			}
		}
		producImagetList = null;
		
		List<Transfer> productList = transferMapper.queryGroupList(TransferTypeEnum.product_id);
		if(productList != null && productList.size() >0) {
			for(Transfer tf : productList) {
				productMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		productList = null;
		
		List<Transfer> recevList = transferMapper.queryGroupList(TransferTypeEnum.receiver_id);
		if(recevList != null && recevList.size() >0) {
			for(Transfer tf : recevList) {
				recvMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		recevList = null;
		
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
		
		List<Merchant> merList = merchantMapper.selectByCondition(new Merchant());
		for(Merchant merchant : merList){
			String travelAgency = merchant.getTravelAgency();
			travelAgency = travelAgency == null?"":travelAgency;
			String latticePoint = merchant.getLatticePoint();
			latticePoint = latticePoint == null?"":latticePoint;
			merchantOrgMap.put(merchant.getId().toString(), travelAgency+","+latticePoint);
		}
		merList = null;
		
		List<ProductComment> productCommentList = productCommentMapper.selectAllProductComment();
		for(ProductComment comment : productCommentList){
			commentMap.put(comment.getOrderId().toString(), "x");
		}
		productCommentList = null;
		
		List<Product> proList = productMapper.selectAllProduct();
		for(Product pro : proList){
			String typeCode = pro.getType().getCode();
			String type = "1";
			if("02".equals(typeCode)){
				type = "2";
			}
			if("03".equals(typeCode)){
				continue;
			}
			productAttrMap.put(pro.getId()+"_type", type);
			productAttrMap.put(pro.getId()+"_image", pro.getImage());
		}
		productCommentList = null;
		
		
		List<MerchantOutletBank> merchantOutletBankList = outleBankMapper.selectPresellOutlet();
		for(MerchantOutletBank merchantOutletBank : merchantOutletBankList){
			bankOrgMap.put(merchantOutletBank.getMerchantOutletId()+"", Constans.filterOrg(merchantOutletBank.getBankOrg()));
			bankOrgNameMap.put(merchantOutletBank.getMerchantOutletId()+"", merchantOutletBank.getOrgName());
		}
		merchantOutletBankList = null;
	}

	private String hanldPaymentMethod(PaymentChannelType type,int fftPoint,int bankPoint) {
		// TODO Auto-generated method stub
		if(type == null)return null;
		
		String code = type.getCode();
		if("00".equals(code)){
			if(fftPoint > 0){
				return PaymentMethod.froadPoints.getCode();
			}
			return PaymentMethod.bankPoints.getCode();
		}else if(bankPoint > 0){
			return PaymentMethod.bankPointsAndCash.getCode();
		}else if(fftPoint > 0){
			return PaymentMethod.froadPointsAndCash.getCode();
		}
		
		return PaymentMethod.cash.getCode();
	}

	private String hanldOrderStatus(OrderStatus orderStatus,OrderPaymentStatus status,String remark) {
		// TODO Auto-generated method stub
		String str = orderStatus.getCode();
		String paymentStatus = status.getCode();
		if("10".equals(str)){
			if("10".equals(paymentStatus)){
				return com.froad.enums.OrderStatus.create.getCode();
			}
			if("20".equals(paymentStatus)){
				return com.froad.enums.OrderStatus.paying.getCode();
			}
			if("40".equals(paymentStatus)){
				return com.froad.enums.OrderStatus.paying.getCode();
			}
		}
		if("20".equals(str)){
			return "6";
		}
		if("30".equals(str)){
//			return "5";
			return "6";//30 - 经查实际为支付成功的
		}
		if("40".equals(str)){
			if(remark != null && remark.contains("用户")){
				return "2";
			}else if(remark != null && remark.contains("系统")){
				return "3";
			}
			return "3";
		}
		return null;
	}

	public int arith(String str){
		if(str == null){
			return 0;
		}
		
		double amt = 0;
		try {
			amt = Double.parseDouble(str);
		} catch (Exception e) {
		}
		
		return Arith.mul(amt, 1000);
		
	}

	public static void main(String[] args) {
		System.out.println((""+","+"").split(",").length==0);
	}
	
}

