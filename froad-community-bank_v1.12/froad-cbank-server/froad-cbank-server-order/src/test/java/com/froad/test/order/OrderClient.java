/////////////////////////////////////////////////////////////
//系统名称：TopGun
//
//Copyright (c) 2014 Top-Gun Corporation. All Rights Reserved.
//
//Class：HelloClientDemo.java
//
//【 大模块名称 】：子模块名称_Action/DTO/DAO/Service/ServiceImpl处理类
//
//日期   ：2015年3月10日 下午4:03:11
//
//作者：Administrator
//
//说明：列表/查询/删除/添加/修改/复制
//
//履历： 2015年3月10日 下午4:03:11 open  新建
//       2015年3月10日 下午4:03:11 open  修改XXX
/////////////////////////////////////////////////////////////

package com.froad.test.order;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ActivitiesType;
import com.froad.enums.CreateSource;
import com.froad.enums.DeliveryType;
import com.froad.enums.OrderStatus;
import com.froad.enums.ProductType;
import com.froad.po.productdetail.ProductActivitiesInfo;
import com.froad.po.productdetail.ProductBuyLimit;
import com.froad.po.productdetail.ProductCategoryInfo;
import com.froad.po.productdetail.ProductDetail;
import com.froad.po.productdetail.ProductImage;
import com.froad.po.productdetail.ProductOutlet;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.ShoppingCartService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.AddMerchantVo;
import com.froad.thrift.vo.order.AddOrderVoReq;
import com.froad.thrift.vo.order.AddOrderVoRes;
import com.froad.thrift.vo.order.AddProductVo;
import com.froad.thrift.vo.order.AddQrcodeOrderVoReq;
import com.froad.thrift.vo.order.AddQrcodeOrderVoRes;
import com.froad.thrift.vo.order.AddVIPOrderVoReq;
import com.froad.thrift.vo.order.AddVIPOrderVoRes;
import com.froad.thrift.vo.order.DeleteOrderVoReq;
import com.froad.thrift.vo.order.DeleteOrderVoRes;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoReq;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoRes;
import com.froad.thrift.vo.order.GetOrderDetailVoReq;
import com.froad.thrift.vo.order.GetOrderDetailVoRes;
import com.froad.thrift.vo.order.GetOrderSummaryVoReq;
import com.froad.thrift.vo.order.GetOrderSummaryVoRes;
import com.froad.thrift.vo.order.GetPointExchangeDetailVoReq;
import com.froad.thrift.vo.order.GetPointExchangeDetailVoRes;
import com.froad.thrift.vo.order.GetPointExchangeListVoReq;
import com.froad.thrift.vo.order.GetPointExchangeListVoRes;
import com.froad.thrift.vo.order.ReceiptOrderReq;
import com.froad.thrift.vo.order.ShippingOrderVoReq;
import com.froad.thrift.vo.order.ShippingOrderVoRes;
import com.froad.util.JSonUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * 订单管理模块客户端模拟测试
 */
 
public class OrderClient {
 
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

//    public static final String SERVER_IP = "10.43.2.3";
//    public static final int SERVER_PORT = 15501;
//    public static final int TIMEOUT = 30000;
    
    public Data data = new Data();
    
    /**测试数据**/
    public class Data{
    	String clientId = "anhui";
    	
    	String clientId_ah = "1001";
    	
    	//商品
    	int isLimit = 1;
    	int isNotLimit = 0;
    	//商品1-预售自提
    	String productId1 = "01E0BFD08000";
    	/*String productName1 = "蕾丝仙女中裙子";
    	String productType1 = ProductType.presell.getCode();*/
    	String price1 = "59.9";
    	int quantity1 = 1;//买1
    	String store1 = "10";
    	
    	//商品2-预售送货上门
    	String productId2 = "0091716b8000";
    	String productName2 = "卤肉饭";
    	String productType2 = ProductType.presell.getCode();
    	int quantity2 = 3;//买2
    	int price2 = 10;
    	int store2 = 5;
    	
    	//预售商品
    	String productId21 = "0122BEC58000";
    	String productName21 = "Cetaphil洁面乳";
    	String productType21 = ProductType.presell.getCode();
    	int quantity21 = 1;//买2
    	int price21 = 5990;
    	int store21 = 5;
    	
    	//预售商品
    	String productId22 = "007dd1fe8000";
    	String productName22 = "麦克赛尔SDXC";
    	String productType22 = ProductType.presell.getCode();
    	int quantity22 = 1;//买2
    	int price22 = 5990;
    	int store22 = 5;
    	
    	String merchantId22 = "01A240508000";
    	
    	//商品3-面对面商品
    	String productId3 = "00A80E718000";
    	int cost = 10999;
    	String qrcode = "1000A80E718001";
    	
    	//商品4-名优特惠
    	String productId4 = "0093991f0000";
    	String productName4 = "金满堂黑糖四物饮";
    	String productType4 = ProductType.special.getCode();
    	int quantity4 = 3;//买2
    	int price4 = 10;
    	int store4 = 5;
    	
    	//名优特惠
    	//商户
    	String sMerchantId1 = "0164FC090000";
    	//商品
    	String sProductId1 = "01767C390000";
    	String sProductId2 = "0178C8690000";
    	
    	//团购
    	//商品
    	String gProductId1 = "01679CA68000";
    	String gProductId2 = "016852B68000";
    	String gProductId3 = "01781B090000";
    	
    	//商品5-团购商品
    	String productId5 = "011252050000";
    	String productName5 = "商品简称ddddddd";
    	String productType5 = ProductType.group.getCode();
    	int quantity5 = 1;//买2
    	int price5 = 888000;
    	int store5 = 5;
    	
    	/*----------------------------------------------*/
    	//商品5（1）-团购商品
    	String productId51 = "01126E450000";
    	String productName51 = "ddddds";
    	String productType51 = ProductType.group.getCode();
    	int quantity51 = 1;//买2
    	int price51 = 10;
    	int store51 = 5;
    	
    	
    	//商户
    	String merchantId51 = "0039f38a8000";
    	String merchantName51 = "阿里巴巴小微贷";
    	/*-----------------------------------------------*/
    	
    	//商品6-网点礼品
    	String productId6 = "01673C760000";
    	String productName6 = "网店兑换1";
    	String productType6 = ProductType.dotGift.getCode();
    	int quantity6 = 3;//买2
    	int price6 = 10;
    	int store6 = 5;
    	
    	//限购
    	int maxBuy = 3;
    	
    	//门店最大提货数量
    	String presellMax = "15";
    	
    	//活动1
    	Long activityId1 = 10000L;
    	String activityType1 = ActivitiesType.vip.getCode();
    	String vipPrice = "4";
    	//活动2
    	Long activityId2 = 20000L;
    	String activityType2 = ActivitiesType.point.getCode();
    	
    	//门店
    	String outletId1 = "583584220961263616";
    	String outletName1 = "阿牛嫂梅花路店";
    	
    	//门店
    	String outletId2 = "583584220961263610";
    	String outletName2 = "阿牛嫂樱花路店";
    	
    	//银行二级机构商户
    	String merchantId1 = "0026cbd80000";
    	String merchantName1 = "安徽肥东农村合作银行";
    	
    	//银行三级机构商户
    	String merchantId2 = "0027d7380000";
    	String merchantName2 = "肥西农村商业银行肥光支行";
    	
    	//名优特惠商户
    	String merchantId3 = "00932ef20000";
    	String merchantName3 = "纽伊斯特责任无限公司";
    	
    	//团购商户
    	String merchantId4 = "0039f38a8000";
    	String merchantName4 = "阿里巴巴小微贷";
    	
    	//团购商户
    	String merchantId41 = "00932ef20000";
    	String merchantName41 = "纽伊斯特责任无限公司";
    	
    	
    	
    	//网点礼品
    	String merchantId5 = "01CEA1C80000";
//    	String merchantName5 = "合肥科技银行新站支行";
    	
        //提货网点机构
//    	String orgCode = "3401030221";
//    	String orgName = "肥西农村商业银行肥光支行";
    	String orgCode = "3401010003";
    	String orgName = "芜湖津盛农商银行花桥支行";
//    	String cardNo = "6229538106502396018";//安徽银行卡
    	String cardNo = "6229538106500532358";//安徽银行卡
    	
    	//会员
//    	Long memberCode = 10000012041L;
    	Long memberCode = 50000000225L;
    	String memberName = "T哪有女流氓";
    	String phone = "13024323415";
    	String deliverId = "0156A7630000";//自提
    	String recvId = "01CEFE188000";//收货
    	String merchantUserId = "10001";
    	
    	//订单
    	String orderId = "74493165568";
    	String subOrderId = "74493100032";
    	
    	//配送
    	String deliveryCorpId = "100000";
    	String deliveryCorpName = "顺风";
    	String trackingNo = "1234567";
    	
    	
    }
    
 
    public void addMongoTestData(){
    	/*MongoManager mongo = new MongoManager();
    	//商品详情
    	ProductDetail product = new ProductDetail();
    	product.setId(data.productId1);
    	product.setIsLimit(data.isLimit);
    	product.setName(data.productName1);
    	product.setProductType(ProductType.presell.getCode());
    	product.setPrice(10);
    	product.setMerchantId(data.merchantId1);
    	//门店
    	ProductOutlet outletInfo = new ProductOutlet();
    	outletInfo.setOutletId(data.outletId1);
    	List<ProductOutlet> outlet_info = new ArrayList<ProductOutlet>();
    	outlet_info.add(outletInfo);
    	product.setOutletInfo(outlet_info);
    	//活动
    	ProductActivitiesInfo activitiesInfo = new ProductActivitiesInfo();
    	activitiesInfo.setActivitiesId(data.activityId1);
    	activitiesInfo.setActivitiesType(data.activityType1);
    	List<ProductActivitiesInfo> activities_info = new ArrayList<ProductActivitiesInfo>();
    	activities_info.add(activitiesInfo);
    	product.setActivitiesInfo(activities_info);
    	//图片
    	ProductImage imageInfo = new ProductImage();
    	imageInfo.setTitle("美女图");
    	imageInfo.setLarge("Large");
    	imageInfo.setMedium("Medium");
    	imageInfo.setSource("Source");
    	imageInfo.setThumbnail("thumbnail");
    	List<ProductImage> image_info = new ArrayList<ProductImage>();
    	image_info.add(imageInfo);
    	product.setImageInfo(image_info);
    	//商品类型
    	ProductCategoryInfo productCategoryInfo = new ProductCategoryInfo();
    	productCategoryInfo.setProductCategoryId(1L);
    	List<ProductCategoryInfo> product_category_info = new ArrayList<ProductCategoryInfo>();// 商品分类
    	product_category_info.add(productCategoryInfo);
    	product.setProductCategoryInfo(product_category_info);
    	//限购
    	ProductBuyLimit buy_limit = new ProductBuyLimit();
    	buy_limit.setStartTime(new Date().getTime());
    	buy_limit.setEndTime(new Date().getTime());
    	buy_limit.setMax(5);
    	buy_limit.setMaxVip(1);
    	buy_limit.setMin(0);
    	buy_limit.setMinVip(0);
    	buy_limit.setMaxCard(1);
    	product.setBuyLimit(buy_limit);
    	int flag = mongo.add(product, "cb_product_details");
    	System.out.println("商品详情插入是否成功：" + (flag!=-1));*/
    }
    
    public void updateMongoTestData(){
    	MongoManager mongo = new MongoManager();
    	
    	//限购
    	ProductBuyLimit buy_limit = new ProductBuyLimit();
    	buy_limit.setStartTime(new Date().getTime());
    	buy_limit.setEndTime(new Date().getTime());
    	buy_limit.setMax(data.maxBuy);
    	buy_limit.setMaxVip(1);
    	buy_limit.setMin(0);
    	buy_limit.setMinVip(0);
    	buy_limit.setMaxCard(1);
    	
    	DBObject where = new BasicDBObject();
    	where.put("_id", "1111");
    	DBObject value = new BasicDBObject();
//    	value.put("buy_limit", buy_limit);
    	value.put("buy_limit", JSON.parse(JSonUtil.toJSonString(buy_limit)));
    	int result = mongo.update(value, where, "cb_product_details", "$push");
    	System.out.println("更新是否成功：" + (result!=-1));
    	
    }
    
    public void setRedisTestData(){
    	System.out.println("缓存设置...");
    	RedisManager redis = new RedisManager();
    	//商户缓存
    	redis.hset(RedisKeyUtil.cbbank_merchant_client_id_merchant_id(data.clientId,data.merchantId2), "merchant_name", data.merchantName1);
    	redis.hset(RedisKeyUtil.cbbank_merchant_client_id_merchant_id(data.clientId,data.merchantId2), "is_enable", "1");
    	redis.hset(RedisKeyUtil.cbbank_merchant_client_id_merchant_id(data.clientId,data.merchantId2), "merchant_status", "1");
    	
    	//商品缓存
    	redis.hset(RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(data.clientId,data.merchantId2,data.productId2), "price", data.price1);
    	//0-未上架 1-上架 2-下架
    	redis.hset(RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(data.clientId,data.merchantId2,data.productId2), "is_marketable", "1");
    	redis.hset(RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(data.clientId,data.merchantId2,data.productId2), "start_time",String.valueOf(new Date().getTime()));
    	redis.hset(RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(data.clientId,data.merchantId2,data.productId2), "end_time",String.valueOf(new Date().getTime()));
    	//运费
    	redis.hset(RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(data.clientId,data.merchantId2,data.productId2), "delivery_money","5");
    	
    	//库存缓存
    	redis.hset(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(data.clientId,data.merchantId2,data.productId2),"store",data.store1);
    	
    	//门店缓存
    	redis.hset(RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(data.clientId,data.merchantId2,data.outletId2),"is_enable","1");//boolean类数据，在数据库中必须为int型
    	
    	//门店最大提货数量
    	redis.putString(RedisKeyUtil.cbbank_product_presell_max_product_id(data.productId2),data.presellMax);
    	
    	//活动缓存
    	redis.hset(RedisKeyUtil.cbbank_activities_client_id_activities_id(data.clientId,data.activityId2),"begin_time",String.valueOf(new Date().getTime()));
    	redis.hset(RedisKeyUtil.cbbank_activities_client_id_activities_id(data.clientId,data.activityId2),"end_time",String.valueOf(new Date().getTime()));
//    	redis.hset(RedisKeyUtil.cbbank_activities_client_id_activities_id(data.clientId,data.activityId2),"vip_price",data.vipPrice);
    	redis.hset(RedisKeyUtil.cbbank_activities_client_id_activities_id(data.clientId,data.activityId2),"points","1");
    	System.out.println("缓存设置成功");
    }
    
    /**
     * 订单创建
     */
    public void testAddOrder(OrderService.Client client) throws TException{
    	AddOrderVoReq request = new AddOrderVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(data.memberCode);
        request.setMemberName(data.memberName);
        request.setCreateSource(CreateSource.android.getCode());
        request.setPhone(data.phone);
        request.setDeliverId(data.deliverId);
        request.setRecvId(data.deliverId);
        request.setCardNo(data.cardNo);
        
        List<AddMerchantVo> addMerchantVoList = new ArrayList<AddMerchantVo>();
        AddMerchantVo addMerchantVo = new AddMerchantVo();
        addMerchantVo.setMerchantId(data.merchantId5);
        
        List<AddProductVo> addProductVoList = new ArrayList<AddProductVo>();
        AddProductVo addProductVo1 = new AddProductVo();
        addProductVo1.setProductId(data.productId6);
        addProductVo1.setQuantity(data.quantity6);
        addProductVo1.setOrgCode(data.orgCode);
        addProductVo1.setOrgName(data.orgName);
        addProductVo1.setDeliveryType(DeliveryType.take.getCode());
        addProductVoList.add(addProductVo1);
        
        addMerchantVo.setAddProductVoList(addProductVoList);
        addMerchantVoList.add(addMerchantVo);
        request.setAddMerchantVoList(addMerchantVoList);
        request.setRemark("线下积分兑换-创建订单测试");
        AddOrderVoRes response = client.addOrder(request);
        System.out.println("创建订单后，订单ID为: " + response.getOrderId());
    }
    
    /**
     * 预售订单创建
     */
    public void testAddOrderPresell(OrderService.Client client) throws TException{ //TODO
    	AddOrderVoReq request = new AddOrderVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(data.memberCode);
        request.setMemberName(data.memberName);
        request.setCreateSource(CreateSource.android.getCode());
        request.setPhone(data.phone);
        /*request.setDeliverId(data.deliverId);*/
        request.setRecvId(data.recvId);
        request.setOrderRequestType("2");
        
        List<AddMerchantVo> addMerchantVoList = new ArrayList<AddMerchantVo>();
        AddMerchantVo addMerchantVo = new AddMerchantVo();
        addMerchantVo.setMerchantId(data.merchantId22);
        
        //门店最大提货数量
        PropertiesUtil.load();
    	new RedisManager().putString(RedisKeyUtil.cbbank_product_presell_max_product_id(data.productId1),data.presellMax);
        
        List<AddProductVo> addProductVoList = new ArrayList<AddProductVo>();
        AddProductVo addProductVo1 = new AddProductVo();
        addProductVo1.setProductId(data.productId1);
        addProductVo1.setQuantity(data.quantity1);
        /*addProductVo1.setOrgCode(data.orgCode);*/
        addProductVo1.setOrgName(data.orgName);
        addProductVo1.setDeliveryType(DeliveryType.home.getCode());
        addProductVoList.add(addProductVo1);
        
//        AddProductVo addProductVo2 = new AddProductVo();
//        addProductVo2.setProductId(data.productId2);
//        addProductVo2.setMerchantId(data.merchantId2);
//        addProductVo2.setQuantity(data.quantity2);
//        addProductVo2.setOrgCode(data.orgCode);
//        addProductVo2.setDeliveryType(DeliveryType.home.getCode());
//        addProductVoList.add(addProductVo2);
        
        addMerchantVo.setAddProductVoList(addProductVoList);
        addMerchantVoList.add(addMerchantVo);
        request.setAddMerchantVoList(addMerchantVoList);
        
        request.setRemark("预售自提-创建订单测试");
        AddOrderVoRes response = client.addOrder(request);
        System.out.println("创建订单后，订单ID为: " + response.getOrderId());
    }
    
    /**
     * 添加团购订单
     */
    public void testAddOrderGroup(OrderService.Client client) throws TException{//TODO
    	AddOrderVoReq request = new AddOrderVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(data.memberCode);
        request.setMemberName(data.memberName);
        request.setCreateSource(CreateSource.android.getCode());
        request.setPhone(data.phone);
        request.setOrderRequestType("2");
        request.setRecvId(data.deliverId);
        
        //名优特惠商户|团购商户
        List<AddMerchantVo> addMerchantVoList = new ArrayList<AddMerchantVo>();
        AddMerchantVo sm1 = new AddMerchantVo();
        sm1.setMerchantId(data.sMerchantId1);
        
        List<AddProductVo> addProductVoList = new ArrayList<AddProductVo>();
        AddProductVo sp1 = new AddProductVo();
        sp1.setProductId(data.sProductId1);
        sp1.setQuantity(2);
        addProductVoList.add(sp1);
        
        AddProductVo sp2 = new AddProductVo();
        sp2.setProductId(data.sProductId2);
        sp2.setQuantity(1);
        addProductVoList.add(sp2);
        
        AddProductVo gp1 = new AddProductVo();
        gp1.setProductId(data.gProductId1);
        gp1.setQuantity(1);
        addProductVoList.add(gp1);
        
        AddProductVo gp2 = new AddProductVo();
        gp2.setProductId(data.gProductId2);
        gp2.setQuantity(1);
        addProductVoList.add(gp2);
        
        AddProductVo gp3 = new AddProductVo();
        gp3.setProductId(data.gProductId3);
        gp3.setQuantity(1);
        addProductVoList.add(gp3);
        
//        AddMerchantVo addMerchantVo2 = new AddMerchantVo();
//        addMerchantVo2.setMerchantId(data.productId6);
        
//        List<AddProductVo> addProductVoList2 = new ArrayList<AddProductVo>();
        /*AddProductVo addProductVo2 = new AddProductVo();
        addProductVo2.setProductId(data.productId51);
        addProductVo2.setQuantity(data.quantity51);
        addProductVoList.add(addProductVo2);*/
        
        sm1.setAddProductVoList(addProductVoList);
        addMerchantVoList.add(sm1);
        
//        addMerchantVo2.setAddProductVoList(addProductVoList2);
//        addMerchantVoList.add(addMerchantVo2);
        
        request.setAddMerchantVoList(addMerchantVoList);
        
        request.setRemark("团购+名优特惠-创建订单测试");
        AddOrderVoRes response = client.addOrder(request);
        System.out.println("创建订单后，订单ID为: " + response.getOrderId());
    }
    
    /**
     * 添加名优特惠订单
     */
    public void testAddOrderSpecial(OrderService.Client client) throws TException{//TODO
    	AddOrderVoReq request = new AddOrderVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(data.memberCode);
        request.setMemberName(data.memberName);
        request.setCreateSource(CreateSource.android.getCode());
        request.setPhone(data.phone);
        
        List<AddProductVo> addProductVoList = new ArrayList<AddProductVo>();
        //名优特惠
        AddProductVo addProductVo1 = new AddProductVo();
//        addProductVo1.setMerchantId(data.merchantId4);
        addProductVo1.setProductId(data.productId4);
        addProductVo1.setQuantity(data.quantity4);
        addProductVo1.setDeliveryType(DeliveryType.home.getCode());
        addProductVoList.add(addProductVo1);
        
        //团购
        AddProductVo addProductVo2 = new AddProductVo();
//        addProductVo2.setMerchantId(data.merchantId4);
        addProductVo2.setProductId(data.productId5);
        addProductVo2.setQuantity(data.quantity5);
        addProductVoList.add(addProductVo2);
        
//        request.setAddProductVoList(addProductVoList);
        request.setRemark("名优特惠/团购-创建订单测试");
        AddOrderVoRes response = client.addOrder(request);
        System.out.println("创建订单后，订单ID为: " + response.getOrderId());
    }
    
    /**
     * 网点礼品--创建订单
     */
    public void testAddOrderPointGift(OrderService.Client client) throws TException{//TODO
    	AddOrderVoReq request = new AddOrderVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(data.memberCode);
        request.setMemberName(data.memberName);
        request.setCreateSource(CreateSource.pc.getCode());
        request.setPhone(data.phone);
        request.setDeliverId(data.deliverId);
        request.setRecvId(data.deliverId);
        request.setOrderRequestType("1");
        
        request.setCardNo(data.cardNo);//银行卡号
        
        List<AddMerchantVo> addMerchantVoList = new ArrayList<AddMerchantVo>();
        AddMerchantVo addMerchantVo = new AddMerchantVo();
        addMerchantVo.setMerchantId(data.merchantId5);
        
        List<AddProductVo> addProductVoList = new ArrayList<AddProductVo>();
        AddProductVo addProductVo1 = new AddProductVo();
        addProductVo1.setProductId(data.productId6);
        addProductVo1.setQuantity(data.quantity6);
        addProductVo1.setOrgCode(data.orgCode);
        addProductVo1.setOrgName(data.orgName);
        addProductVo1.setDeliveryType(DeliveryType.take.getCode());
        addProductVoList.add(addProductVo1);
        
        addMerchantVo.setAddProductVoList(addProductVoList);
        addMerchantVoList.add(addMerchantVo);
        
        request.setAddMerchantVoList(addMerchantVoList);
        request.setRemark("线下积分兑换（网点礼品）-创建订单测试");
        AddOrderVoRes response = client.addOrder(request);
        System.out.println("创建订单后，订单ID为: " + response.getOrderId());
    }
    
    /**
     * 添加线上积分兑换商品
     */
    public void testAddOrderOnlinePoint(OrderService.Client client) throws TException{//TODO
    	AddOrderVoReq request = new AddOrderVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(50000000213L);
        request.setMemberName("test123");
        request.setCreateSource(CreateSource.android.getCode());
        request.setPhone("15900686646");
        /*request.setDeliverId("01A4D8220000");*/
        request.setRecvId("01A4D8220000");
        request.setOrderRequestType("2");
        request.setCardNo(data.cardNo);
        
        request.setRemark("订单创建测试");
        
        List<AddProductVo> addProductVoList = new ArrayList<AddProductVo>();
        AddProductVo addProductVo1 = new AddProductVo();
//        addProductVo1.setMerchantId(data.merchantId5);
        addProductVo1.setProductId(data.productId6);
        addProductVo1.setQuantity(data.quantity6);
        addProductVo1.setOrgCode(data.orgCode);
        addProductVo1.setOrgName(data.orgName);
//        addProductVo1.setDeliveryType(DeliveryType.home.getCode());
        addProductVo1.setDeliveryType(DeliveryType.take.getCode());
        addProductVoList.add(addProductVo1);
        
        AddMerchantVo addMerchantVo = new AddMerchantVo();
        addMerchantVo.setMerchantId(data.merchantId5);
//        AddProductVo addProductVo2 = new AddProductVo();
//        addProductVo2.setProductId(data.productId2);
//        addProductVo2.setMerchantId(data.merchantId2);
//        addProductVo2.setQuantity(data.quantity2);
//        addProductVo2.setOrgCode(data.orgCode);
//        addProductVo2.setDeliveryType(DeliveryType.home.getCode());
//        addProductVoList.add(addProductVo2);
        
//        request.setAddProductVoList(addProductVoList);
        addMerchantVo.setAddProductVoList(addProductVoList);
        List<AddMerchantVo> list = new ArrayList<AddMerchantVo>();
        list.add(addMerchantVo);
        request.setAddMerchantVoList(list);
        request.setRemark("线下积分兑换-创建订单测试");
        AddOrderVoRes response = client.addOrder(request);
        System.out.println("创建订单后，订单ID为: " + response.getOrderId());
    }
    
    /**
     * 添加面对面商品
     */
    public void addQrcodeProductMongoData(){
    	MongoManager mongo = new MongoManager();
    	DBObject product = new BasicDBObject();
    	product.put("_id", data.productId3);
    	product.put("merchant_id", data.merchantId1);
    	product.put("outlet_id", data.outletId1);
    	product.put("cost", 10);
    	int flag = mongo.add(product, "cb_outlet_product");
    	System.out.println("对面对商品插入是否成功：" + (flag!=-1));
    }
    
    /**
     * 添加对面对支付订单
     */
    public void testQrcodeAddOrder(OrderService.Client client) throws TException{
    	AddQrcodeOrderVoReq request = new AddQrcodeOrderVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(data.memberCode);
        request.setMemberName(data.memberName);
        request.setCreateSource(CreateSource.pc.getCode());
//        request.setMerchantId(data.merchantId1);
//        request.setOutletId(data.outletId1);
        //10+product_id+U+merchant_user_id
        request.setQrcode(data.qrcode);
        request.setRemark("面对面支付订单创建测试");
        AddQrcodeOrderVoRes response = client.addQrcodeOrder(request);
        System.out.println("创建订单后，订单ID为: " + response.getOrderId());
    }
    
    
    /**
     * 获取订单概要
     */
    public void testGetOrderSummary(OrderService.Client client) throws TException{
    	GetOrderSummaryVoReq request = new GetOrderSummaryVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(data.memberCode);
        request.setOrderStatus(OrderStatus.create.getCode());
        request.setStartTime(new Date().getTime()-10000);
        request.setEndTime(new Date().getTime());
        PageVo page = new PageVo();
        page.setPageNumber(1);
        request.setPage(page);
        GetOrderSummaryVoRes response = client.getOrderList(request);
        System.out.println("获取订单概要-查询结果: " + JSonUtil.toJSonString(response));
    }
    
    
    /**
     * 获取积分兑换列表
     */
    public void testGetPointsOrderList(OrderService.Client client) throws TException{
    	GetPointExchangeListVoReq request = new GetPointExchangeListVoReq();
        request.setClientId(data.clientId);
        request.setMemberCode(data.memberCode);
        request.setQueryFlag("1");
        PageVo page = new PageVo();
        page.setPageNumber(1);
        page.setPageSize(5);
        request.setPage(page);
        GetPointExchangeListVoRes response = client.getPointExchangeList(request);
        System.out.println("获取订单概要-查询结果: " + JSonUtil.toJSonString(response));
    }
    
    /**
     * 获取积分兑换详情
     */
    public void testGetPointExchangeDetail(OrderService.Client client) throws TException{
    	GetPointExchangeDetailVoReq request = new GetPointExchangeDetailVoReq();
    	request.setClientId(data.clientId);
    	request.setMemberCode(50000000121L);
    	request.setOrderId("012AB04E0000");
    	GetPointExchangeDetailVoRes response = client.getPointExchangeDetail(request);
    	System.out.println("获取订单概要-查询结果: " + JSonUtil.toJSonString(response));
    }
    
    
    /**
     * 获取订单详情
     */
    public void testGetOrderDetail(OrderService.Client client) throws TException{
    	GetOrderDetailVoReq request = new GetOrderDetailVoReq();
        request.setOrderId(data.orderId);
        GetOrderDetailVoRes response = client.getOrderDetail(request);
        System.out.println("获取订单概要-查询结果: " + JSonUtil.toJSonString(response));
    }
    
    
    /**
     * 订单发货测试
     */
    public void shippingOrderTest(OrderService.Client client) throws TException{
    	ShippingOrderVoReq request = new ShippingOrderVoReq();
    	request.setOrderId(data.orderId);
    	request.setSubOrderId(data.subOrderId);
    	request.setDeliveryCorpId(data.deliveryCorpId);
    	request.setDeliveryCorpName(data.deliveryCorpName);
    	request.setTrackingNo(data.trackingNo);
    	request.setRemark("送货上门");
    	ShippingOrderVoRes response = client.shippingOrder(request);
        System.out.println("订单发货-结果: " + JSonUtil.toJSonString(response));
    }
    
    /**
     * 订单确认收货
     */
    public void confirmShippingOrderTest(OrderService.Client client) throws TException{
    	ReceiptOrderReq request = new ReceiptOrderReq();
    	request.setOrderId("0128BEB48000");
    	request.setSubOrderId("0128BECA8000");
    	
    	ShippingOrderVoRes response = client.receiptOrder(request);
    	System.out.println("订单发货-结果: " + JSonUtil.toJSonString(response));
    }
    
    /**
     * 取消订单
     */
    public void deleteOrderTest(OrderService.Client client) throws TException{
    	DeleteOrderVoReq request = new DeleteOrderVoReq();
//    	request.setOrderId("01BAB1938000");
    	request.setOrderId("0222A5A58000");
    	
    	DeleteOrderVoRes response = client.deleteOrder(request);
    	System.out.println("订单发货-结果: " + JSonUtil.toJSonString(response));
    }
    
    public void getBuyLimitTest(OrderService.Client client) throws TException{
    	GetMemberBuyLimitVoReq request = new GetMemberBuyLimitVoReq();
//    	request.setOrderId("01BAB1938000");
    	request.setClientId("anhui");
    	request.setIsVip(true);
    	
    	GetMemberBuyLimitVoRes response = client.getMemberBuyLimit(request);
    	System.out.println("限购数量-结果: " + JSonUtil.toJSonString(response));
    }
    
    
    public void AddVIPOrderTest(OrderService.Client client) throws TException{
    	AddVIPOrderVoReq request = new AddVIPOrderVoReq();
//    	request.setOrderId("01BAB1938000");
    	request.setClientId("anhui");
    	request.setIsVip(true);
    	
    	AddVIPOrderVoRes response = client.addVIPOrder(request);
    	System.out.println("限购数量-结果: " + JSonUtil.toJSonString(response));
    }
    
    /**
     * @param userName
     */
    public void startClient() {//TODO
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
//            TProtocol protocol = new TBinaryProtocol(transport);
            TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            OrderService.Client client = new OrderService.Client(protocol);
            transport.open();
            
            
            //请求服务端
    		/*TBinaryProtocol protocol = new TBinaryProtocol(transport);
    		TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"OrderService");
    		OrderService.Client client = new OrderService.Client(mp1);
    		transport.open();*/
            
            //创建订单
//            testAddOrder(client);
            
            //名优特惠+团购
//            testAddOrderSpecial(client);
            
            //预售订单
//            testAddOrderPresell(client);
            
            //团购订单
//            testAddOrderGroup(client);
            
            //面对面支付订单
//            testQrcodeAddOrder(client);
            
            //网点礼品
            testAddOrderPointGift(client);
            
            //获取订单概要
//            testGetOrderSummary(client);
            
            //订单发货
//            shippingOrderTest(client);
    		
    		//确认收货
//    		confirmShippingOrderTest(client);
            
//            deleteOrderTest(client);
            
//            testAddOrderOnlinePoint(client);
            
            //
//            testGetPointsOrderList(client);
//            testGetPointExchangeDetail(client);
            
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
    
    public void setRedis(){
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        OrderClient client = new OrderClient();
        client.startClient();
//        client.addQrcodeProductMongoData();
//        client.addMongoTestData();
//        client.updateMongoTestData();
//        client.setRedisTestData();
    }
    
 
}
