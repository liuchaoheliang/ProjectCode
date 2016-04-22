package com.froad.thrift;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Product;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.InvalidProductBatchVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletProductVo;
import com.froad.thrift.vo.OutletProductVoReq;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductActivitiesVo;
import com.froad.thrift.vo.ProductAreaVo;
import com.froad.thrift.vo.ProductBuyLimitVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ProductCommentFilterReq;
import com.froad.thrift.vo.ProductCommentVo;
import com.froad.thrift.vo.ProductExistVoReq;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductStatusBatchVoReq;
import com.froad.thrift.vo.ProductStatusVoReq;
import com.froad.thrift.vo.ProductStoreFilterVo;
import com.froad.thrift.vo.ProductVo;
import com.froad.util.BeanUtil;
import com.froad.util.DateUtil;

public class Client {

	public static void main(String[] args) {
	    
//      String filter = "{\"orderField\":{\"2\":\"sellCount-desc\",\"1\":\"createTime-desc\"},\"type\":\"1\"}";
////    String filter = "{\"name\":\"\",\"type\":\"1\",\"orderField\":{\"2\":\"sellCount-desc\",\"1\":\"createTime-desc\"}}";
////    String filter = "{\"orderField\":{\"2\":\"sellCount-desc\",\"1\":\"createTime-desc\"}}";
////    String filter = "{\"name\":\"\",\"type\":\"1\"}";
////    String filter = "{\"type\":\"1\"}";
////    String filter = "{\"name\":\"\",\"orderField\":{\"2\":\"sellCount-desc\",\"1\":\"createTime-desc\"},\"type\":\"1\"}";
	    
	    
//	    System.out.println("startTime:"+DateUtil.formatDateTime("yyyyMMddHHmmss", new Date(1430928000000L)));
//	    System.out.println("endTime:"+DateUtil.formatDateTime("yyyyMMddHHmmss", new Date(1436068800000L)));
	    System.out.println("endTime:"+DateUtil.parse(DateUtil.DATE_TIME_FORMAT2,"2015-07-15 12:00:00").getTime());
	    
	    
	    
//	    startTime:1430928000000, endTime:1432310399059,
	    
//	    PropertiesUtil.load();
//        CommonLogic m = new CommonLogicImpl();
//        m.getProductRedis("", "", "0274C5498000");
	    
//		try {
//			TSocket transport = new TSocket("localhost", 15401);
////		    TSocket transport = new TSocket("10.43.2.3", 15401);
////		    TSocket transport = new TSocket("10.43.1.9", 15401);
//			TBinaryProtocol protocol = new TBinaryProtocol(transport);
//
//			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"ProductCategoryService");
//			ProductCategoryService.Client service1 = new ProductCategoryService.Client(mp1);
//			
//			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"ProductService");
//            ProductService.Client service2 = new ProductService.Client(mp2);
////
//			transport.open();
//			
//			PlatType platType = PlatType.findByValue(1);
//			OriginVo originVo = new OriginVo();
//			originVo.setPlatType(platType);
////			platType, 0L, "192.168.19.107", "手动新加");
////			InvalidProductBatchVo invalidProductBatchVo = new InvalidProductBatchVo();
////			invalidProductBatchVo.setClientId("anhui");
////			invalidProductBatchVo.setMerchantId("01A211208000");
////			System.out.println(service2.endisableProductBatch(originVo, invalidProductBatchVo, "1"));
//			
//			
//			//(long productCategoryId, String clientId, String name, String treePath, long parentId,String icoUrl)
////			ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", " 全部分类 ", "", 0L,"","1");
////			ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "餐饮美食", "100000000", 100000000L,"/froad-cbank/image/201501/729b1318-52d8-4a70-8acc-f19a7c15283a-thumbnail.jpg");
////			ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "休闲娱乐", "100000000", 100000000L, "/froad-cbank/image/201501/753855f8-b2e4-417b-93ae-5a857ed8c466-thumbnail.jpg");
////			ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "婚纱摄影", "100000000", 100000000L, "/froad-cbank/image/201501/d38fad6e-5534-453c-949e-db0e76f2037a-thumbnail.jpg");
////          ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "美容美体", "100000000", 100000000L, "/froad-cbank/image/201501/845eaebe-b550-49d8-adc7-56f0e83bc6c1-thumbnail.jpg");
////			ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "洗衣家政", "100000000", 100000000L, "/froad-cbank/image/201501/d5941763-229a-4899-8bde-9d9b004a3a06-thumbnail.jpg");
////	          ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "汽车服务", "100000000", 100000000L, "/froad-cbank/image/201501/e4ef7c7a-ed52-4c71-a9af-ad08b2f144c4-thumbnail.jpg");
////			ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "日用百货", "100000000", 100000000L, "/froad-cbank/image/201501/cd2eb0ec-f10f-4f4d-93f3-b282df7ced65-thumbnail.jpg");
////            ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "交通旅游", "100000000", 100000000L, "/froad-cbank/image/201501/b23d5baa-6936-4865-8b4d-6dbd195e9082-thumbnail.jpg");
////            ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "时尚女装", "100000000", 100000000L, "/froad-cbank/image/201501/1de30c59-a66b-4501-92c4-488239e99a30-thumbnail.jpg");
////            ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "韩版女装", "100000000 100000009", 100000009L, "/froad-cbank/image/201501/659986da-86dd-4fc9-af95-acb367dca9d8-thumbnail.jpg");
////            ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "秋冬新款", "100000000 100000009 100000010", 100000010L, "/froad-cbank/image/201501/a7cd2769-1bfe-44af-a951-5d99f7481ea6-thumbnail.jpg");
////			ProductCategoryVo pc = new ProductCategoryVo();
////			pc.setId(500000017L);
////			pc.setClientId("anhui");
////			pc.setName("rdd秋冬新款ttrr");
////			pc.setParentId(100000011L);
////			pc.setIcoUrl("/froad-cbank/image/201501/a7cd2769-1bfe-44af-a951-5d99f7481ea6-thumbnail.jpg");
////			pc.setOrderValue((short)1);
////			System.out.println(service1.addProductCategory(originVo,pc));
////			System.out.println(service1.findCategorys("anhui"));
////			System.out.println(service1.updateProductCategory(originVo,pc));
//			
////			ProductCategoryVo productCategoryVo14 = new ProductCategoryVo(100000006L, "anhui", " 苹果 ", " 100000000 ", 100000005L);
////            System.out.println(service1.isProductCategoryExist(productCategoryVo14));
//            
//            
////          ProductCategoryVo productCategoryVo12 = new ProductCategoryVo(100000016L, "anhui", "", "", 100000007L,"");
////          System.out.println(service1.deleteProductCategory(productCategoryVo12));
//			
////			ProductCategoryVo productCategoryVo12 = new ProductCategoryVo(100000006L, "anhui", " 川菜 ", " 100000000 100000005 ", 100000006L);
////			System.out.println(service1.getProductCategoryById(productCategoryVo12));
////			
////			ProductCategoryVo productCategoryVo12 = new ProductCategoryVo(0L, "anhui", "", "", 100000000L);
////			ProductCategoryVo productCategoryVo12 = new ProductCategoryVo();
////			productCategoryVo12.setClientId("anhui");
////			productCategoryVo12.setParentId(100000000L);
//////			productCategoryVo12.setName("美");
////			PageVo pageVo = new PageVo(1,8,0,0);
////            System.out.println(service1.findCategorysByPage(productCategoryVo12, pageVo));
//			
////			ProductCategoryVo productCategoryVo12 = new ProductCategoryVo(0L, "anhui", "", "", 0L);
////			ProductCategoryVo productCategoryVo = new ProductCategoryVo();
////			productCategoryVo.setClientId("anhui");
////			PageVo pageVo = new PageVo(1,10,0,0);
////			System.out.println(service1.queryProductCategorys(productCategoryVo, pageVo));
//			
//            //预售
////			ProductInfoVo productInfoVo = new ProductInfoVo();
////            productInfoVo.setPlatType("1");
////           ProductVo product = 
//////           clientId, merchantId, orgCode, productId, isMarketable, rackTime, createTime, type, deliveryOption, 
//////                   name, fullName, price, cost,marketPrice, weight, weightUnit, store, sellCount, orderValue, 
//////                   isBest, isLimit, point, briefIntroduction, introduction, buyKnow, 
//////           auditState, auditOrgCode, auditStage, reviewStaff, auditStaff, storeCount, startTime, endTime, deliveryMoney, 
//////             expireStartTime,expireEndTime, trueBuyerNumber, virtualBuyerNumber, productSupplier, maxPerOutlet, deliveryStartTime, deliveryEndTime, 
//////              clusterState, clusterType, downTime, auditTime, auditComment, isSeckill, merchantName, auditStartOrgCode, auditEndOrgCode, orgName)
////           new ProductVo("anhui", "017D5BF20000", "3410000", "", "0", 0L, 0L, "2","0",
////                   "Cetaphil洁面乳", "Cetaphil 丝塔芙洁面乳118ml双支装套装 ", 5.990, 200, 200, "100", "KG", 100,0, 0, 
////                   true,true,0, "药妆 进口商品 两支超值组合 洗面奶 洁面 可卸妆 温和不刺激","上线时间分别为：每日 10:00 更新该产品可凑单，订单10KG以内满百免邮。部分地区不参加满百包邮，注：该团购不支持崇明地区。组合产品[1]：水之密语 凝润水护洗发露 600ml（资生堂授权特供） ","限时折扣，全场包邮，7天无理由退换，支持货到付款！赠运费保险！",
////                   "0", "3410000","0", "", "", 0, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime(),4.99,
////                   0L, 0L, 0, 100, "供货商名称",10, 0L, 0L, 
////              true, true, 0L,0,"",false,"","0","0","");
////           productInfoVo.setProduct(product);
////           
////           ProductCategoryVo productCategory = new ProductCategoryVo();
////           productCategory.setId(100000001L);
////           productInfoVo.setProductCategory(productCategory);
////           
////           ProductBuyLimitVo buyLimit = new ProductBuyLimitVo(10, 10, 10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150421").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime());
////           productInfoVo.setBuyLimit(buyLimit);
////           
////           List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////           ProductImageVo image1 = new ProductImageVo("预售商品",
////                   "/froad-cb/coremodule/9769a538-8e35-4b13-b4df-43e601174bf6.jpg",
////                   "/froad-cb/coremodule/9769a538-8e35-4b13-b4df-43e601174bf6-large.jpg",
////                   "/froad-cb/coremodule/9769a538-8e35-4b13-b4df-43e601174bf6-medium.jpg",
////                   "/froad-cb/coremodule/9769a538-8e35-4b13-b4df-43e601174bf6-thumbnail.jpg");
////           image.add(image1);
////           
////           productInfoVo.setImage(image);
////           
////           List<ProductOutletVo> outlet = new ArrayList<ProductOutletVo>();
////           ProductOutletVo out1 = new ProductOutletVo();
////           out1.setOutletId("017D5BF20000");
////           outlet.add(out1);
////           
////           productInfoVo.setOutlet(outlet);
////           
//////           List<ProductActivitiesVo> activiiesInfo = new ArrayList<ProductActivitiesVo>();
//////           ProductActivitiesVo ac = new ProductActivitiesVo();
//////           ac.setActivitiesId(100000000);
//////           ac.setActivitiesType("0");
//////           activiiesInfo.add(ac);
//////           productInfoVo.setActivities(activiiesInfo);
////           System.out.println(service2.addProduct(originVo,productInfoVo));
//			
////			PlatType platType = PlatType.findByValue(2);
////			OriginVo originVo = new OriginVo(platType, 100000011L, "10.43.1.10", null);
////			ProductInfoVo productInfoVo = new ProductInfoVo();
////			ProductVo product = new ProductVo();
////			product.setMerchantId("01776B208001");
////			product.setClientId("anhui");
//////			product.setOrgCode("340000");
////			product.setOrgCode(" ");
////			product.setType("2");
////			product.setDeliveryOption("0");
////			product.setName("testrrrr");
////			product.setFullName("testsizerrrr");
////			product.setPrice(1.0);
////			product.setMarketPrice(10.0);
////			product.setStore(100);
////			product.setSellCount(0);
////			product.setOrderValue(0);
////			product.setIsBest(false);
////			
////			product.setIsLimit(true);
////			product.setPoint(0);
////			product.setBriefIntroduction("ss");
////			product.setIntroduction("ha<img src=\"//img.sqyh365.cn/froad-cb/coremodule/20150504/7361572f-3efe-44e5-bff7-521cc7cce352.jpeg\" alt=\"\" />");
////			product.setBuyKnow("ha");
////			product.setAuditState("1");
////			product.setAuditOrgCode("0");
////			product.setStoreCount(0);
////			product.setStartTime(1430697600000l);
////			product.setEndTime(1430798400000L);
////			product.setDeliveryStartTime(1430870400000L);
////			product.setDeliveryEndTime(1431230400000L);
////			product.setClusterState(false);
////			product.setClusterType(false);
////			product.setIsSeckill(false);
////			product.setAuditStartOrgCode("0");
////			product.setAuditEndOrgCode("0");
////			productInfoVo.setProduct(product);
////			
////			ProductBuyLimitVo buyLimit = new ProductBuyLimitVo();
////			buyLimit.setMax(1);
////			buyLimit.setMaxVip(1);
////			productInfoVo.setBuyLimit(buyLimit);
////			
////			List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////			ProductImageVo imageVo1 = new ProductImageVo();
////			imageVo1.setTitle("预售商品");
////			imageVo1.setSource("/froad-cb/coremodule/20150504/b8247535-650b-4ab5-b594-ae22c18fb024.gif");
////			imageVo1.setLarge("/froad-cb/coremodule/20150504/b8247535-650b-4ab5-b594-ae22c18fb024-large.gif");
////			imageVo1.setMedium("/froad-cb/coremodule/20150504/b8247535-650b-4ab5-b594-ae22c18fb024-medium.gif");
////			imageVo1.setThumbnail("/froad-cb/coremodule/20150504/b8247535-650b-4ab5-b594-ae22c18fb024-thumbnail.gif");
////			image.add(imageVo1);
////			
////			ProductImageVo imageVo2 = new ProductImageVo();
////            imageVo2.setTitle("预售商品");
////            imageVo2.setSource("/froad-cb/coremodule/20150504/c4d1ddf7-3395-4f1a-b493-e71f5c92b98a.gif");
////            imageVo2.setLarge("/froad-cb/coremodule/20150504/c4d1ddf7-3395-4f1a-b493-e71f5c92b98a-large.gif");
////            imageVo2.setMedium("/froad-cb/coremodule/20150504/c4d1ddf7-3395-4f1a-b493-e71f5c92b98a-medium.gif");
////            imageVo2.setThumbnail("/froad-cb/coremodule/20150504/c4d1ddf7-3395-4f1a-b493-e71f5c92b98a-thumbnail.gif");
////            image.add(imageVo2);
////            
////            ProductImageVo imageVo3 = new ProductImageVo();
////            imageVo3.setTitle("预售商品");
////            imageVo3.setSource("/froad-cb/coremodule/20150504/834fa6fb-e3ab-4971-a964-12e970c7e438.jpeg");
////            imageVo3.setLarge("/froad-cb/coremodule/20150504/834fa6fb-e3ab-4971-a964-12e970c7e438-large.jpeg");
////            imageVo3.setMedium("/froad-cb/coremodule/20150504/834fa6fb-e3ab-4971-a964-12e970c7e438-medium.jpeg");
////            imageVo3.setThumbnail("/froad-cb/coremodule/20150504/834fa6fb-e3ab-4971-a964-12e970c7e438-thumbnail.jpeg");
////            image.add(imageVo3);
////            
////            productInfoVo.setImage(image);
////            
////            List<ProductOutletVo> outlet = new ArrayList<ProductOutletVo>();
////            ProductOutletVo outlet1 = new ProductOutletVo();
////            outlet1.setOutletId("01D60C030000");
////            outlet.add(outlet1);    
////            
////            productInfoVo.setOutlet(outlet);
////            
////            List<String> orgCodes = new ArrayList<String>();
////            orgCodes.add("340101");
////            
////            productInfoVo.setOrgCodes(orgCodes);
//			
////			        (platType, product, productCategory, buyLimit, image, outlet, activities, orgCodes)
////			添加Product:ProductInfoVo(platType:null, product:ProductVo(clientId:anhui, merchantId:null, orgCode:340000, productId:null, isMarketable:null, rackTime:0, createTime:0, type:2, deliveryOption:0, name:test,
////			fullName:testsize, price:1.0, cost:0.0, marketPrice:10.0, weight:null, weightUnit:null, store:1000, sellCount:0, orderValue:0,
////			isBest:false, isLimit:true, point:0, briefIntroduction:ss, introduction:ha<img src="//img.sqyh365.cn/froad-cb/coremodule/20150504/7361572f-3efe-44e5-bff7-521cc7cce352.jpeg" alt="" />, 
////			buyKnow:ha, auditState:1, auditOrgCode:0, auditStage:null, reviewStaff:null, auditStaff:null, storeCount:0, startTime:1430697600000, endTime:1430798400000,
////			deliveryMoney:0.0, expireStartTime:0, expireEndTime:0, trueBuyerNumber:0, virtualBuyerNumber:0, productSupplier:null, maxPerOutlet:0, deliveryStartTime:1430870400000, deliveryEndTime:1431230400000, clusterState:false, clusterType:false, downTime:0, auditTime:0, auditComment:null, isSeckill:false, merchantName:null, auditStartOrgCode:0, auditEndOrgCode:0, orgName:null, phone:null), productCategory:null, buyLimit:ProductBuyLimitVo(max:1, maxVip:1, maxCard:0, startTime:0, endTime:0), image:[ProductImageVo(title:预售商品, source:/froad-cb/coremodule/20150504/b8247535-650b-4ab5-b594-ae22c18fb024.gif, large:/froad-cb/coremodule/20150504/b8247535-650b-4ab5-b594-ae22c18fb024-large.gif, medium:/froad-cb/coremodule/20150504/b8247535-650b-4ab5-b594-ae22c18fb024-medium.gif, thumbnail:/froad-cb/coremodule/20150504/b8247535-650b-4ab5-b594-ae22c18fb024-thumbnail.gif), ProductImageVo(title:预售商品, source:/froad-cb/coremodule/20150504/c4d1ddf7-3395-4f1a-b493-e71f5c92b98a.gif, large:/froad-cb/coremodule/20150504/c4d1ddf7-3395-4f1a-b493-e71f5c92b98a-large.gif, medium:/froad-cb/coremodule/20150504/c4d1ddf7-3395-4f1a-b493-e71f5c92b98a-medium.gif, thumbnail:/froad-cb/coremodule/20150504/c4d1ddf7-3395-4f1a-b493-e71f5c92b98a-thumbnail.gif), ProductImageVo(title:预售商品, source:/froad-cb/coremodule/20150504/834fa6fb-e3ab-4971-a964-12e970c7e438.jpeg, large:/froad-cb/coremodule/20150504/834fa6fb-e3ab-4971-a964-12e970c7e438-large.jpeg, medium:/froad-cb/coremodule/20150504/834fa6fb-e3ab-4971-a964-12e970c7e438-medium.jpeg, thumbnail:/froad-cb/coremodule/20150504/834fa6fb-e3ab-4971-a964-12e970c7e438-thumbnail.jpeg)], outlet:[ProductOutletVo(id:0, clientId:null, merchantId:null, outletId:01D60C030000, areaId:0, outletName:null, address:null)], activities:[], orgCodes:[340101])
////			20150504 16:54:04 604[AEC5DB5AC7734B46][INFO]-开始生成二维码1430729644604
////			System.out.println(service2.addProduct(originVo,productInfoVo));
//			
////           ProductInfoVo productInfoVo = new ProductInfoVo();
////           
////           ProductBuyLimitVo buyLimit = new ProductBuyLimitVo();
////           buyLimit.setMax(9999999);
////           buyLimit.setMaxVip(9999999);
////           buyLimit.setMaxCard(9999999);
////           buyLimit.setStartTime(1430882898122L);
////           buyLimit.setEndTime(1433792670641L);
////           
////           productInfoVo.setBuyLimit(buyLimit);
////           
////           List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////           ProductImageVo imageVo1 = new ProductImageVo();
////           imageVo1.setTitle("");
////           imageVo1.setSource("");
////           imageVo1.setLarge("");
////           imageVo1.setMedium("");
////           imageVo1.setThumbnail("");
////           image.add(imageVo1);
////           productInfoVo.setImage(image);
////           
////           List<ProductOutletVo> outlet = new ArrayList<ProductOutletVo>();
////           ProductOutletVo outlet1 = new ProductOutletVo();
////           outlet1.setOutletId("023B48760000");
////           outlet.add(outlet1);   
////           productInfoVo.setOutlet(outlet);
////           
////           ProductVo product = new ProductVo();
////           
////           product.setAuditOrgCode("0");
////           product.setAuditStartOrgCode("0");
////           product.setAuditEndOrgCode("0");
////           product.setAuditComment(" ");
////           product.setAuditStaff(" ");
////           product.setAuditState("1");
////           product.setAuditStage("1");
////           product.setAuditTime(1430882898122L);
////           product.setBriefIntroduction("简介：5daqyO26pQVcVL2UpJJV");
////           product.setBuyKnow("商品须知：wOutYotZPTCFRGPew41k");
////           product.setClientId("anhui");
////           product.setClusterState(true);
////           product.setClusterType(true);
////           product.setCost(1393.0);
////           product.setPrice(11111111.0);
////           product.setMarketPrice(14931111);
////           product.setCreateTime(1430882898122L);
////           product.setDeliveryStartTime(1430882898122L);
////           product.setDeliveryEndTime(1433792670641L);
////           product.setDeliveryMoney(0.0);
////           product.setDeliveryOption("1");
////           product.setDownTime(1433792670641L);
//////           product.setStartTime(1430697600000l);
////           product.setEndTime(1433792670641L);
////           product.setExpireEndTime(1433792670641L);
////           product.setExpireStartTime(1430882898122L);
////           product.setIsSeckill(false);
////           product.setFullName("性能测试商品uLEcWoBAzH");
////           product.setIntroduction("介绍：MoJR693qUcI6dhoUzUcO");
////           product.setIsBest(true);
////           product.setIsLimit(false);
////           product.setIsMarketable("1");
////           product.setMaxPerOutlet(30);
////           product.setMerchantId("023B48660002");
////           product.setMerchantName(" ");
////           product.setName("性能测试商品uLEcWoBAzH");
////           product.setOrderValue(1);
////               "orgCode":" ","orgName":"","point":1,"price":1443,"productId":" ",
////               "productSupplier":" ","rackTime":1430882898122,"reviewStaff":" ",
////               "sellCount":228,"setAuditComment":true,"setAuditEndOrgCode":true,
////               "setAuditOrgCode":true,"setAuditStaff":true,"setAuditStage":true,
////               "setAuditStartOrgCode":true,"setAuditState":true,"setAuditTime":true,
////               "setBriefIntroduction":true,"setBuyKnow":true,"setClientId":true,"setClusterState":true,
////               "setClusterType":true,"setCost":true,"setCreateTime":true,"setDeliveryEndTime":true,
////               "setDeliveryMoney":true,"setDeliveryOption":true,"setDeliveryStartTime":true,"setDownTime":true,
////               "setEndTime":true,"setExpireEndTime":true,"setExpireStartTime":true,"setFullName":true,
////               "setIntroduction":true,"setIsBest":true,"setIsLimit":true,"setIsMarketable":true,
////               "setIsSeckill":true,"setMarketPrice":true,"setMaxPerOutlet":true,"setMerchantId":true,
////               "setMerchantName":true,"setName":true,"setOrderValue":true,"setOrgCode":true,"setOrgName":false,
////               "setPoint":true,"setPrice":true,"setProductId":true,"setProductSupplier":true,"setRackTime":true,
////               "setReviewStaff":true,"setSellCount":true,"setStartTime":true,"setStore":true,"setStoreCount":true,
////               "setTrueBuyerNumber":true,"setType":true,"setVirtualBuyerNumber":true,"setWeight":true,"setWeightUnit":true,
////               "startTime":1430882898122,"store":1000000,"storeCount":0,"trueBuyerNumber":30,"type":"1","virtualBuyerNumber":30,
////               "weight":"17","weightUnit":"KG"},"productCategory":{"clientId":"anhui","icoUrl":" ","id":100000004,"name":"美容美体",
////                   "parentId":0,"setClientId":true,"setIcoUrl":true,"setId":true,"setName":true,"setParentId":true,"setTreePath":true,"treePath":" "},"setActivities":true,"setBuyLimit":true,"setImage":true,"setOrgCodes":true,"setOutlet":true,"setPlatType":true,"setProduct":true,"setProductCategory":true}
////           product.setOrgCode("340000");
////           product.setType("2");
////           product.setDeliveryOption("0");
////           product.setName("testrrrr");
////           product.setFullName("testsizerrrr");
////           
////           product.setStore(100);
////           product.setSellCount(0);
////           product.setOrderValue(0);
////           
////           product.setPoint(0);
////           product.setBriefIntroduction("ss");
////           product.setIntroduction("ha<img src=\"//img.sqyh365.cn/froad-cb/coremodule/20150504/7361572f-3efe-44e5-bff7-521cc7cce352.jpeg\" alt=\"\" />");
////           product.setBuyKnow("ha");
////           
////           product.setStoreCount(0);
////           
////           productInfoVo.setProduct(product);
////           
////           System.out.println(service2.addProduct(originVo,productInfoVo));
//			
//			
////			originVo：OriginVo(platType:merchant_pc, operatorId:100000122, operatorIp:10.43.2.2, description:null)
////          PlatType platType = PlatType.findByValue(3);
////         OriginVo originVo = new OriginVo(platType, 100000122L, "10.43.2.2", null);
////                 
////         ProductInfoVo productInfoVo = new ProductInfoVo();
////         productInfoVo.setPlatType("3");
////         ProductVo product = new ProductVo();
////         product.setProductId("023775F60000");
////         product.setClientId("anhui");
////         product.setMerchantId("01B863EE0000");
////         product.setOrgCode("3402050515");
////         product.setType("1");
////         
////         product.setName("rtrtr");
////         product.setFullName("trtrt");
////         product.setPrice(211.0);
////         product.setMarketPrice(222.0);
////         product.setStore(233);
////         product.setSellCount(0);
////         product.setOrderValue(0);
////         product.setIsBest(true);
////         product.setIsLimit(true);
////         product.setPoint(0);
////         product.setBriefIntroduction("d");
////         product.setIntroduction("d");
////         product.setBuyKnow("d");
////         product.setAuditState("0");
////         product.setAuditOrgCode("340205");
////         product.setStoreCount(0);
////         product.setStartTime(1432483200000L);
////         product.setEndTime(1432742400000L);
////         
////         product.setDeliveryStartTime(1431878400000L);
////         product.setDeliveryEndTime(1433520000000L);
////         product.setClusterState(false);
////         product.setClusterType(false);
////         product.setIsSeckill(false);
////         product.setAuditStartOrgCode("340205");
////         product.setAuditEndOrgCode("340000");
////         productInfoVo.setProduct(product);
////         
////         ProductCategoryVo productCategory = new ProductCategoryVo();
////         productCategory.setId(100000002L);
////         productInfoVo.setProductCategory(productCategory);
////         
////         ProductBuyLimitVo buyLimit = new ProductBuyLimitVo();
////         buyLimit.setMax(2);
////         buyLimit.setMaxVip(0);
////         buyLimit.setMaxCard(0);
////         buyLimit.setStartTime(1432483200000L);
////         buyLimit.setEndTime(1432742400000L);
////         productInfoVo.setBuyLimit(buyLimit);
////         
////         List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////         ProductImageVo imageVo1 = new ProductImageVo();
////         imageVo1.setSource("/froad-cb/coremodule/201505/cf648045-181c-415c-8d7d-06efbee35114.jpg");
////         imageVo1.setLarge("/froad-cb/coremodule/201505/cf648045-181c-415c-8d7d-06efbee35114-large.jpg");
////         imageVo1.setMedium("/froad-cb/coremodule/201505/cf648045-181c-415c-8d7d-06efbee35114-medium.jpg");
////         imageVo1.setThumbnail("/froad-cb/coremodule/201505/cf648045-181c-415c-8d7d-06efbee35114-thumbnail.jpg");
////         image.add(imageVo1);
////         
////         productInfoVo.setImage(image);
////           
////           
//////           修改Product,productInfoVo:ProductInfoVo(platType:3, product:ProductVo(clientId:anhui, merchantId:01B863EE0000, orgCode:3402050515, productId:023775F60000, 
//////                   isMarketable:null, rackTime:0, createTime:0, type:1, deliveryOption:null, name:rtrtr, fullName:trtrt, price:211.0, cost:0.0, marketPrice:222.0, weight:null, weightUnit:null, 
//////                   store:233, sellCount:0, orderValue:0, isBest:true, isLimit:true, point:0, briefIntroduction:d, introduction:d, buyKnow:d, auditState:0, auditOrgCode:340205, auditStage:null, 
//////                   reviewStaff:null, auditStaff:null, storeCount:0, startTime:1432483200000, endTime:1432742400000,
//////                   deliveryMoney:0.0, expireStartTime:1431878400000, expireEndTime:1433520000000, trueBuyerNumber:0, 
//////                   virtualBuyerNumber:0, productSupplier:null, maxPerOutlet:0, deliveryStartTime:0, deliveryEndTime:0, clusterState:false, clusterType:false, downTime:0, auditTime:0, auditComment:null, isSeckill:false, merchantName:null, auditStartOrgCode:340205, auditEndOrgCode:340000, orgName:null, phone:null, contractEndtime:0), productCategory:ProductCategoryVo(id:100000002, clientId:anhui, name:null, treePath:null, parentId:0, icoUrl:null, orderValue:0), buyLimit:ProductBuyLimitVo(max:2, maxVip:0, maxCard:0, startTime:1432483200000, endTime:1432742400000), image:[ProductImageVo(title:null, source:/froad-cb/coremodule/201505/cf648045-181c-415c-8d7d-06efbee35114.jpg, large:/froad-cb/coremodule/201505/cf648045-181c-415c-8d7d-06efbee35114-large.jpg, medium:/froad-cb/coremodule/201505/cf648045-181c-415c-8d7d-06efbee35114-medium.jpg, thumbnail:/froad-cb/coremodule/201505/cf648045-181c-415c-8d7d-06efbee35114-thumbnail.jpg)], outlet:null, activities:null, orgCodes:null)
//////                   20150505 10:12:03 226[2B4C00F6999F4E6F][DEBUG]-
////         
////         System.out.println(service2.updateProduct(originVo,productInfoVo));
//			
//			//名优特惠
////			ProductInfoVo productInfoVo = new ProductInfoVo();
////            productInfoVo.setPlatType("1");
////           ProductVo product = 
////           new ProductVo("anhui", "0165AAEB8000", "340101", "", "0", new Date().getTime(), new Date().getTime(), "3","0",
////                   "泡沫奶茶gggg", "gg热销原装进口德的泡沫奶茶g", 5.990, 200, 200, "100", "G", 200,0, 2, 
////                   true,false,0, "ggg热销原装进口德林亲爱的泡沫奶茶 即溶冲调饮品 300g10包盒装tstt ","ggggtstt满5盒包邮，不包括港澳台等部分地区，详细请看运费栏。好东西就是要和好朋友分享，德林亲爱的品质优良，入口香醇，午后休闲和送礼赠友之首选。礼赠友之首选。阿拉比卡咖啡滤泡式黑咖啡已经通过台湾SGS六项塑化剂检验，全部产品均无塑化剂成分，请安心选用。","限时折扣，全场包邮，7天无理由退换，支持货到付款！赠运费保险！","0", "340101", 
////              "0", "", "", 0, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime(),4.99,DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(), 
////              DateUtil.parse(DateUtil.DATE_FORMAT2, "20150425").getTime(), 0, 100, "供货商名称",10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150501").getTime(), 
////              true, true, 20150409L,0,"",false,"");
//			
//			
////			ProductVo product = 
//////		           clientId, merchantId, orgCode, productId, isMarketable, rackTime, createTime, type, deliveryOption, 
//////		                   name, fullName, price, cost,marketPrice, weight, weightUnit, store, sellCount, orderValue, 
//////		                   isBest, isLimit, point, briefIntroduction, introduction, buyKnow, 
//////		           auditState, auditOrgCode, auditStage, reviewStaff, auditStaff, storeCount, startTime, endTime, deliveryMoney, 
//////		             expireStartTime,expireEndTime, trueBuyerNumber, virtualBuyerNumber, productSupplier, maxPerOutlet, deliveryStartTime, deliveryEndTime, 
//////		              clusterState, clusterType, downTime, auditTime, auditComment, isSeckill, merchantName, auditStartOrgCode, auditEndOrgCode, orgName)
////		           new ProductVo("anhui", "017D5BF20000", "3410000", "", "0", 0L, 0L, "2","0",
////		                   "Cetaphil洁面乳", "Cetaphil 丝塔芙洁面乳118ml双支装套装 ", 5.990, 200, 200, "100", "KG", 100,0, 0, 
////		                   true,true,0, "药妆 进口商品 两支超值组合 洗面奶 洁面 可卸妆 温和不刺激","上线时间分别为：每日 10:00 更新该产品可凑单，订单10KG以内满百免邮。部分地区不参加满百包邮，注：该团购不支持崇明地区。组合产品[1]：水之密语 凝润水护洗发露 600ml（资生堂授权特供） ","限时折扣，全场包邮，7天无理由退换，支持货到付款！赠运费保险！",
////		                   "0", "3410000","0", "", "", 0, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime(),4.99,
////		                   0L, 0L, 0, 100, "供货商名称",10, 0L, 0L, 
////		              true, true, 0L,0,"",false,"","0","0","");
////           productInfoVo.setProduct(product);
////           
////           ProductCategoryVo productCategory = new ProductCategoryVo();
////           productCategory.setId(100000001L);
////           productInfoVo.setProductCategory(productCategory);
////           
////           ProductBuyLimitVo buyLimit = new ProductBuyLimitVo(10, 10, 10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150421").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime());
////           productInfoVo.setBuyLimit(buyLimit);
////           
////           List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////           ProductImageVo image1 = new ProductImageVo("gg泡沫奶茶tstt",
////                   "/froad-cbank/image/201501/83648df1-6f7c-480b-8456-147febf38edb-thumbnail.jpg",
////                   "/froad-cbank/image/201501/83648df1-6f7c-480b-8456-147febf38edb-thumbnail.jpg",
////                   "/froad-cbank/image/201501/83648df1-6f7c-480b-8456-147febf38edb-thumbnail.jpg",
////                   "/froad-cbank/image/201501/83648df1-6f7c-480b-8456-147febf38edb-thumbnail.jpg");
////           image.add(image1);
////           
////           productInfoVo.setImage(image);
////           
////           List<ProductOutletVo> outlet = new ArrayList<ProductOutletVo>();
////           ProductOutletVo out1 = new ProductOutletVo();
////           out1.setOutletId("016780CC8000");
////           outlet.add(out1);
////           
////           productInfoVo.setOutlet(outlet);
////           System.out.println(service2.addProduct(originVo,productInfoVo));
//			
//			
//			//团购
////          ProductInfoVo productInfoVo = new ProductInfoVo();
////            productInfoVo.setPlatType("1");
////           ProductVo product =  
//////                   new ProductVo(clientId, merchantId, orgCode, productId, isMarketable, rackTime, createTime, type, deliveryOption,
//////                   name, fullName, price, cost, marketPrice, weight, weightUnit, store, sellCount, orderValue, 
//////                   isBest, isLimit, point, briefIntroduction, introduction, buyKnow, 
//////                   auditState, auditOrgCode, auditStage, reviewStaff, auditStaff, storeCount, startTime, endTime, deliveryMoney, 
//////                   expireStartTime, expireEndTime, trueBuyerNumber, virtualBuyerNumber, productSupplier, maxPerOutlet, 
//////                   deliveryStartTime, deliveryEndTime, 
//////                   clusterState, clusterType, downTime, auditTime, auditComment, isSeckill, merchantName, auditStartOrgCode, auditEndOrgCode, orgName, phone)
////           new ProductVo("anhui", "0165AAEB8000", "340101", "", "0", new Date().getTime(), new Date().getTime(), "1","1",
////                   "桃子冻干果干1", "冻干果干 康熙来了热推1", 1.990, 200, 200, "100", "G", 200,0, 2, 
////                   true,false,0, "泰国Crispy 6 旅行者6号 桃子冻干果干 康熙来了热推 30g盒装脆片","满5盒包邮，不包括港澳台等部分地区，详细请看运费栏。纯天然水果冷冻脱水制成，完全保留住水果本身的营养元素，无任何添加剂，全家人皆可享用的休闲零食，多种吃法，乐趣多多。 ","限时折扣，全场包邮，7天无理由退换，支持货到付款！赠运费保险！",
////                   "0", "340101","0", "", "", 0, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150504").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150528").getTime(),4.99,
////                   DateUtil.parse(DateUtil.DATE_FORMAT2, "20150510").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150525").getTime(), 0, 100, "供货商名称",10,
////              DateUtil.parse(DateUtil.DATE_FORMAT2, "20150510").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150530").getTime(), 
////              true, true, 20150409L,0L,"",false,"","0","0","","");
////           productInfoVo.setProduct(product);
////           
////////               clientId, merchantId, orgCode, productId, isMarketable, rackTime, createTime, type, deliveryOption, 
////////                       name, fullName, price, cost,marketPrice, weight, weightUnit, store, sellCount, orderValue, 
////////                       isBest, isLimit, point, briefIntroduction, introduction, buyKnow, 
////////               auditState, auditOrgCode, auditStage, reviewStaff, auditStaff, storeCount, startTime, endTime, deliveryMoney, 
////////                 expireStartTime,expireEndTime, trueBuyerNumber, virtualBuyerNumber, productSupplier, maxPerOutlet, 
//////           deliveryStartTime, deliveryEndTime, 
////////                  clusterState, clusterType, downTime, auditTime, auditComment, isSeckill, merchantName, auditStartOrgCode, auditEndOrgCode, orgName)
//////               new ProductVo("anhui", "017D5BF20000", "3410000", "", "0", 0L, 0L, "2","0",
//////                       "Cetaphil洁面乳", "Cetaphil 丝塔芙洁面乳118ml双支装套装 ", 5.990, 200, 200, "100", "KG", 100,0, 0, 
//////                       true,true,0, "药妆 进口商品 两支超值组合 洗面奶 洁面 可卸妆 温和不刺激","上线时间分别为：每日 10:00 更新该产品可凑单，订单10KG以内满百免邮。部分地区不参加满百包邮，注：该团购不支持崇明地区。组合产品[1]：水之密语 凝润水护洗发露 600ml（资生堂授权特供） ","限时折扣，全场包邮，7天无理由退换，支持货到付款！赠运费保险！",
//////                       "0", "3410000","0", "", "", 0, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime(),4.99,
//////                       0L, 0L, 0, 100, "供货商名称",10, 0L, 0L, 
//////                  true, true, 0L,0,"",false,"","0","0","");
////                   
////                   
////           
////           ProductCategoryVo productCategory = new ProductCategoryVo();
////           productCategory.setId(100000001L);
////           productInfoVo.setProductCategory(productCategory);
////           
////           ProductBuyLimitVo buyLimit = new ProductBuyLimitVo(10, 10, 10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150421").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime());
////           productInfoVo.setBuyLimit(buyLimit);
////           
////           List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////           ProductImageVo image1 = new ProductImageVo("桃子冻干果干1",
////                   "/froad-cbank/image/201501/83648df1-6f7c-480b-8456-147febf38edb-thumbnail.jpg",
////                   "/froad-cbank/image/201501/83648df1-6f7c-480b-8456-147febf38edb-thumbnail.jpg",
////                   "/froad-cbank/image/201501/83648df1-6f7c-480b-8456-147febf38edb-thumbnail.jpg",
////                   "/froad-cbank/image/201501/83648df1-6f7c-480b-8456-147febf38edb-thumbnail.jpg");
////           image.add(image1);
////           
////           productInfoVo.setImage(image);
////           
////           List<ProductOutletVo> outlet = new ArrayList<ProductOutletVo>();
////           ProductOutletVo out1 = new ProductOutletVo();
////           out1.setOutletId("016780CC8000");
////           outlet.add(out1);
////           
////           productInfoVo.setOutlet(outlet);
////           System.out.println(service2.addProduct(originVo,productInfoVo));
//			
//	         //在线积分兑换
////	          ProductInfoVo productInfoVo = new ProductInfoVo();
////	            productInfoVo.setPlatType("1");
////	           ProductVo product = 
//////	                   new ProductVo(clientId, merchantId, orgCode, productId, isMarketable, rackTime, createTime, type, deliveryOption, 
//////	                           name, fullName, price, cost, marketPrice, weight, weightUnit, store, sellCount, orderValue, 
//////	                           isBest, isLimit, point, briefIntroduction, introduction, buyKnow, auditState, auditOrgCode,
//////	                           auditStage, reviewStaff, auditStaff, storeCount, startTime, endTime, deliveryMoney, 
//////	                           expireStartTime, expireEndTime, trueBuyerNumber, virtualBuyerNumber, productSupplier, maxPerOutlet, 
//////	                           deliveryStartTime, deliveryEndTime, 
//////	                           clusterState, clusterType, downTime, auditTime, auditComment, isSeckill, merchantName, 
//////	                           auditStartOrgCode, auditEndOrgCode, orgName, phone, contractEndtime, serverTime)
////	           new ProductVo("anhui", "01568F630000", "340101", "", "0", new Date().getTime(), new Date().getTime(), "4","0",
////	                   "505元移动话费", "在线积分兑换505元移动话费", 1.990, 200, 200, "", "", 200,0, 2, 
////	                   true,true,0, null,null,null,"0", "340101", 
////	              "0", "", "", 0, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150501").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150530").getTime(),4.99,
////	              DateUtil.parse(DateUtil.DATE_FORMAT2, "20150502").getTime(), 
////	              DateUtil.parse(DateUtil.DATE_FORMAT2, "20150530").getTime(), 0, 100, "供货商名称",10, 
////	              DateUtil.parse(DateUtil.DATE_FORMAT2, "20150501").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150530").getTime(), 
////	              true, true, 20150409L,0,"",false,"","0","0","","",0L,0L);
////	           productInfoVo.setProduct(product);
////	           
////	           ProductCategoryVo productCategory = new ProductCategoryVo();
////	           productCategory.setId(100000001L);
////	           productInfoVo.setProductCategory(productCategory);
////	           
////	           ProductBuyLimitVo buyLimit = new ProductBuyLimitVo(10, 10, 10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150501").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150530").getTime());
////	           productInfoVo.setBuyLimit(buyLimit);
////	           
////	           List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////	           ProductImageVo image1 = new ProductImageVo("在线积分兑换505元移动话费",
////	                           "/froad-cb/coremodule/68d6defa-07e3-4830-96c9-618acf8ba85d.jpg",
////	                           "/froad-cb/coremodule/68d6defa-07e3-4830-96c9-618acf8ba85d-large.jpg",
////	                           "/froad-cb/coremodule/68d6defa-07e3-4830-96c9-618acf8ba85d-medium.jpg",
////	                           "/froad-cb/coremodule/68d6defa-07e3-4830-96c9-618acf8ba85d-thumbnail.jpg");
////	           
////	           image.add(image1);
////	           
////	           productInfoVo.setImage(image);
////	           
////	           List<ProductOutletVo> outlet = new ArrayList<ProductOutletVo>();
////	           ProductOutletVo out1 = new ProductOutletVo();
////	           out1.setOutletId("0267A3968000");
////	           outlet.add(out1);
////	           productInfoVo.setOutlet(outlet);
//	           
////	           System.out.println(service2.addProduct(originVo,productInfoVo));
//			
////		    ProductOperateVoReq productVoReq = new ProductOperateVoReq("anhui", "01F8AC398000", "5");
////		    System.out.println(service2.deleteProduct(originVo,productVoReq));
//		    
////		    ProductExistVoReq productExistVoReq = new ProductExistVoReq(1L, "1", "100000002", "鲁西烤牛肉", "1");
////            System.out.println(service2.isProductExist(productExistVoReq))
//            
//			// new ProductVo("anhui", "01D60C030000", "340101", "01E4E9420000",
////			ProductStatusVoReq productStatusVoReq = new ProductStatusVoReq("anhui", "01568F630000", "02CDAED28000", "1","");
////			System.out.println(service2.updateProductStatus(originVo,productStatusVoReq));
//            
////			ProductCountVoReq productCountVoReq= new ProductCountVoReq(1L, "100000002", -2);
////			System.out.println(service2.updateProductStockNum(productCountVoReq));
//			
////			ProductCountVoReq productCountVoReq= new ProductCountVoReq(1L, "100000002", 10);
////			System.out.println(service2.updateProductSellCount(productCountVoReq));
//			
////			List<ProductStatusVoReq> productStatusVoReqs = new ArrayList<ProductStatusVoReq>();
////			ProductStatusVoReq b = new ProductStatusVoReq("", "", "01E512EF0000", "2", "");
////			        productStatusVoReqs.add(b);
////          System.out.println(service2.updateProductStatusBatch(originVo, productStatusVoReqs));
//
////			InvalidProductBatchVo inp = new InvalidProductBatchVo("anhui", "01F883940000", "");
////			System.out.println(service2.invalidProductBatch(originVo, inp));
//			
//			
//			
//          //在线积分兑换
////          ProductInfoVo productInfoVo = new ProductInfoVo();
////            productInfoVo.setPlatType("1");
////           ProductVo product = 
////        new ProductVo("anhui", "01D60C030000", "340101", "01E4E9420000", "0", new Date().getTime(), new Date().getTime(), "4","0",
////                "100元移动话费", "在线积分兑换100元移动话费", 1.990, 200, 200, "100", "G", 200,0, 2, 
////                true,true,0, "100元移动话费","在线积分兑换。纯天然水果冷冻脱水制成，完全保留住水果本身的营养元素","赠运费保险！","0", "340101", 
////           "0", "", "", 0, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150429").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150530").getTime(),4.99,DateUtil.parse(DateUtil.DATE_FORMAT2, "20150429").getTime(), 
////           DateUtil.parse(DateUtil.DATE_FORMAT2, "20150530").getTime(), 0, 100, "供货商名称",10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150501").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150521").getTime(), 
////           true, true, 20150409L,0,"",false,"","0","0","");
////        productInfoVo.setProduct(product);
////        
////        ProductCategoryVo productCategory = new ProductCategoryVo();
////        productCategory.setId(100000001L);
////        productInfoVo.setProductCategory(productCategory);
////        
////        ProductBuyLimitVo buyLimit = new ProductBuyLimitVo(10, 10, 10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150429").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150520").getTime());
////        productInfoVo.setBuyLimit(buyLimit);
////        
////        List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////        ProductImageVo image1 = new ProductImageVo("在线积分兑换100元移动话费",
////                        "/froad-cb/coremodule/68d6defa-07e3-4830-96c9-618acf8ba85d.jpg",
////                        "/froad-cb/coremodule/68d6defa-07e3-4830-96c9-618acf8ba85d-large.jpg",
////                        "/froad-cb/coremodule/68d6defa-07e3-4830-96c9-618acf8ba85d-medium.jpg",
////                        "/froad-cb/coremodule/68d6defa-07e3-4830-96c9-618acf8ba85d-thumbnail.jpg");
////        
////        image.add(image1);
////        
////        productInfoVo.setImage(image);
////        
////        List<ProductOutletVo> outlet = new ArrayList<ProductOutletVo>();
////        ProductOutletVo out1 = new ProductOutletVo();
////        out1.setOutletId("01B8BDBE0000");
////        outlet.add(out1);
////        productInfoVo.setOutlet(outlet);
////        
////        System.out.println(service2.updateProduct(originVo,productInfoVo));
//			
////          ProductStatusVoReq productStatusVoReq = new ProductStatusVoReq("anhui", "01F88C540000", "020FCA938000", "1","");
////         System.out.println(service2.updateProductStatus(originVo,productStatusVoReq));
//			
////         ProductOperateVoReq productVoReq = new ProductOperateVoReq();
////         productVoReq.setClientId("anhui");
//////         productVoReq.setMerchantId("01B863EE0000");
////         productVoReq.setProductId("02F280668000");
////         productVoReq.setType("1");
////         
//////         clientId:anhui, merchantId:01B863EE0000, productId:01B8E0218000, type:1
////         
//////////         ProductInfoVo p = 
////                 System.out.println(service2.getMerchantProductDetail(productVoReq));
////         System.out.println(p);
////         if(p!=null && p.getProduct()!=null){
////             p.getProduct().setStartTime(DateUtil.parse(DateUtil.DATE_FORMAT2, "20150502").getTime());
////             System.out.println(service2.updateProduct(originVo,p));
////         }
//			
//			//线下积分
////          ProductInfoVo productInfoVo = new ProductInfoVo();
////            productInfoVo.setPlatType("1");
////           ProductVo product = 
////           new ProductVo("anhui", "01776B208001", "340000", "0125E02E0000", "0", new Date().getTime(), new Date().getTime(), "5","",
////                   "15元移动话费", "线下积分兑换15元移动话费", 2.990, 200, 200, "100", "G", 200,0, 2, 
////                   true,false,0, "15元移动话费","线下积分兑换。纯天然水果冷冻脱水制成，完全保留住水果本身的营养元素","赠运费保险！","0", "340000", 
////              "0", "", "", 0, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(),DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime(),4.99,DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(), 
////              DateUtil.parse(DateUtil.DATE_FORMAT2, "20150425").getTime(), 0, 100, "供货商名称",10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150410").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150501").getTime(), 
////              true, true, 20150409L,0,"",false,"");
////           productInfoVo.setProduct(product);
////           
////           ProductCategoryVo productCategory = new ProductCategoryVo();
////           productCategory.setId(100000001L);
////           productInfoVo.setProductCategory(productCategory);
////           
////           ProductBuyLimitVo buyLimit = new ProductBuyLimitVo(10, 10, 10, DateUtil.parse(DateUtil.DATE_FORMAT2, "20150421").getTime(), DateUtil.parse(DateUtil.DATE_FORMAT2, "20150428").getTime());
////           productInfoVo.setBuyLimit(buyLimit);
////           
////           List<ProductImageVo> image = new ArrayList<ProductImageVo>();
////           ProductImageVo image1 = new ProductImageVo("15元移动话费",
////           "/image/froad-cb/coremodule/9769a538-8e35-4b13-b4df-43e601174bf6.jpg",
////         "/image/froad-cb/coremodule/9769a538-8e35-4b13-b4df-43e601174bf6-large.jpg",
////         "/image/froad-cb/coremodule/9769a538-8e35-4b13-b4df-43e601174bf6-medium.jpg",
////         "/image/froad-cb/coremodule/9769a538-8e35-4b13-b4df-43e601174bf6-thumbnail.jpg");
////           
////           image.add(image1);
////           
////           productInfoVo.setImage(image);
////           
////           List<ProductOutletVo> outlet = new ArrayList<ProductOutletVo>();
////           ProductOutletVo out1 = new ProductOutletVo();
////           out1.setOutletId("01776B208001");
////           outlet.add(out1);
////           
////           
////           productInfoVo.setOutlet(outlet);
////           System.out.println(service2.addProduct(originVo,productInfoVo));
//			
//			
////	        ProductOperateVoReq(clientId:1000, productId:00E7D9B70000, type:2)
////			ProductOperateVoReq productVoReq = new ProductOperateVoReq("anhui", "02CDAED28000", "4");
////			System.out.println(service2.getMerchantProductDetail(productVoReq));
//			
////			ProductOperateVoReq productVoReq = new ProductOperateVoReq("anhui", "02CDAED28000", "4");
////			ProductOperateVoReq productVoReq = new ProductOperateVoReq();
////	         productVoReq.setProductId("02F280668000");
////            System.out.println(service2.queryProductDetail(productVoReq));
//			
////			ProductFilterVoReq(clientId:1000, merchantId:00932ef20000, 
////			        filter:{"auditState":"","clientId":1000,"endTime":"","isMarketable":"","merchantId":"00932ef20000","name":"","orgCode":"","pageNumber":1,"pageSize":10,"startTime":"","token":"dc15699847ee4af98df0a74bc6d55e5b","type":"3","userId":"100000001"})
////			PageVo pageVo = new PageVo(1,10,0,0);
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
////			productFilterVoReq.setClientId("anhui");
////			productFilterVoReq.setMerchantId("00932ef20000");
////			String filter="{\"type\":\"3\"}";340101  2 anhui 
////			String filter="{\"orgCode\":\"340101\",\"type\":\"2\"}";
////			String filter="{\"orgCode\":\"340000\",\"name\":\"测试团购商品FF\",\"type\":\"1\"}";
////			String filter="{\"type\":\"4\"}";
////			String filter="{\"auditState\":\"0\",\"type\":\"5\"}";
////			productFilterVoReq.setFilter(filter);
////			String filter="{\"priceStart\":0,\"name\":\"康熙来了热推\",\"priceEnd\":99,\"type\":\"4\"}";
////			String filter="{\"priceStart\":0,\"fullName\":\"康熙来了热推\"}";
////			String filter="{\"isMarketable\":-1}";
////			
////			String filter="{\"auditState\":\"0\",\"startTimeEnd\":\"1430496000000\",\"startTimeStart\":\"1433088000000\",\"auditOrgCode\":\"340101\",\"type\":\"1\"}";
////			String filter="{\"priceStart\":\"10\"}";
////			productFilterVoReq.setFilter(filter);
////			
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"isSeckill\":false,\"name\":\"预售\"}");
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"auditState\":\"0\",\"name\":\"\",\"auditOrgCode\":\"340000\",\"type\":\"2\"}");
////			clientId:anhui, filter:{"auditState":"0","name":"","auditOrgCode":"340000","type":"2"}
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"type\":\"1\"}");
////           ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
////           productFilterVoReq.setClientId("anhui");
////           productFilterVoReq.setMerchantId("01B863EE0000");
////           String filter = "{\"orderField\":{\"2\":\"sellCount-desc\",\"1\":\"createTime-desc\"},\"type\":\"1\"}";
////           String filter = "{\"name\":\"\",\"type\":\"1\",\"orderField\":{\"2\":\"sellCount-desc\",\"1\":\"createTime-desc\"}}";
////           String filter = "{\"orderField\":{\"2\":\"sellCount-desc\",\"1\":\"createTime-desc\"}}";
////           String filter = "{\"name\":\"\",\"type\":\"1\"}";
////           String filter = "{\"type\":\"1\",\"isMarketable\":\"4\"}";
////           String filter = "{\"auditState\":\"\",\"orgCode\":\"340000\",\"name\":\"\",\"type\":\"2\"}";
////           String filter = "{\"name\":\"\",\"type\":\"1\"}";
////           productFilterVoReq.setFilter(filter);
////                   
////         ProductFilterVoReq(clientId:anhui, merchantId:01B863EE0000, filter:{"orderField":{"2":"sellCount-desc","1":"createTime-desc"},"type":"1"})
////
////           PageVo pageVo = new PageVo(); 
////           pageVo.setPageNumber(1); 
////           pageVo.setPageSize(10);
////			System.out.println(service2.findMerchantProductsByPage(productFilterVoReq, pageVo));
//			
//			
////			ProductFilterVoReq(clientId:1000, filter:{"type":"0"})PageVo(pageNumber:1, pageSize:6, totalCount:0, pageCount:0)
////			ProductFilterVoReq(clientId:1000, filter:{"type":"2"})PageVo(pageNumber:1, pageSize:6, totalCount:0, pageCount:0)
////			ProductFilterVoReq(clientId:1000, filter:{"type":"1"})PageVo(pageNumber:1, pageSize:6, totalCount:0, pageCount:0)
////			ProductFilterVoReq(clientId:1000, filter:{"name":"薰衣草","type":"2"})
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"type\":\"5\",\"areaId\":\"100000006\",\"clientId\":\"1000\"}");
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"outletId\":\"01776B208002\"}");
////			PageVo pageVo = new PageVo(1,10,0,0);
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"type\":\"3\",\"orderField\":\"{\"2\":\"price-asc\",\"1\":\"sellCount-desc\"}\"}");
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"type\":\"1\",\"startTimeStart\":\"1430668800000\",\"endTimeEnd\":\"1483113600473\"}");
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"merchantId\":\"0154CF218000\"}");
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"type\":\"2\",\"presellNum\":\"3\"}");
////            System.out.println(new Date(1483113600473L));
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui","{\"startTimeStart\":\""+DateUtil.parse(DateUtil.DATE_FORMAT2,"20000425").getTime()+"\",\"endTimeStart\":\"1430619623771\",\"endTimeEnd\":\"1483113600771\",\"type\":\"1\"}");
////			{"clientId":"anhui","filter":"{\"orderField\":\"{\\\"1\\\", \\\"startTime-desc\\\"}\",\"type\":\"3\"}",
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"orderField\":\"{\\\"1\\\", \\\"startTime-asc\\\"}\",\"type\":\"3\"}");
////
////			{"clientId":"anhui","filter":"{\"endTimeStart\":\"1430619623771\",\"endTimeEnd\":\"1483113600771\",\"type\":\"1\"}","setClientId":true,"setFilter":true,"setMerchantId":false}
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "\"endTimeStart\":\"1430640182091\",\"endTimeEnd\":\"1483113600091\",\"startTimeStart\":\"Wed Jan 01 00:00:00 CST 2014\",\"type\":\"1\"");
////			{"clientId":"anhui","filter":"{\"endTimeStart\":\"1430640182091\",\"endTimeEnd\":\"1483113600091\",\"startTimeStart\":\"Wed Jan 01 00:00:00 CST 2014\",\"type\":\"1\"}","setClientId":true,"setFilter":true,"setMerchantId":false}
////
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"outletId\":\"01A240508000\"}");
//			
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"categoryId\":100000001}");
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"name\":\"康熙来了热推\"}");
//            
////			:Product by pageProductFilterVoReq(clientId:anhui, filter:{"categoryId":"100000001"})PageVo(pageNumber:1, pageSize:9, totalCount:0, pageCount:0)
////			20150504 10:48:26 605[735113C2230E4A19][DEBUG]-mongo findByPage: collection=cb_product_details,SQL=[{ "is_marketable" : "1" , "client_id" : "anhui" , "start_time" : { "$lte" : 1430707706442} , "end_time" : { "$gte" : 1430707706442} , "product_category_info.product_category_id" : 100000001}]
////			20150504 10:48:26 610[735113C2230E4A19][ERROR]-商品用户分页查询list Product失败，原因:null
////			{"clientId":"anhui","filter":"{\"name\":\"\",\"orderField\":\"{\"1\": \"sellCount-desc\"}\",\"type\":\"3\"}","setClientId":true,"setFilter":true,"setMerchantId":false}
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"name\":\"\",\"orderField\":\"{\"1\": \"sellCount-desc\"}\",\"type\":\"3\"}");
//			
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"priceStart\":\"0\",\"priceEnd\":\"100\",\"outletId\":\"01A240508000\"}");
////			queryProducts priceStart:0   priceEnd:100  outletId:01A240508000   clietnId:anhui
//			
////          {"clientId":"anhui","filter":"{\"name\":\"\",\"orderField\":\"{\"1\": \"sellCount-desc\"}\",\"type\":\"3\"}","setClientId":true,"setFilter":true,"setMerchantId":false}
////          ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"orderField\":\"{\"1\": \"sellCount-desc\"}\",\"type\":\"2\"}");
////           ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"outletId\":\"026811568000\"}");
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"name\":\"鸡中\"}");
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"priceStart\":\"0\",\"name\":\"\",\"type\":\"5\",\"priceEnd\":\"100\"}");
////			pageProductFilterVoReq(clientId:anhui, filter:{"priceStart":"0","name":"","outletId":"0278E5568000","type":"5","priceEnd":"100"}
////			PageVo pageVo = new PageVo(); 
////			pageVo.setPageNumber(1);
////			pageVo.setPageSize(10);
////			(clientId:anhui, filter:{"name":"","orderField":"{"1": "sellCount-desc"}","type":"3"})PageVo(pageNumber:1, pageSize:10, totalCount:0, pageCount:0)
////			ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq("anhui", "{\"name\":\"测试\",\"type\":\"0\"}");
////          System.out.println(service2.queryProducts(productFilterVoReq, pageVo));
//  
////		    OutletProductVo outletProductVo = new OutletProductVo();
////		    outletProductVo.setClientId("anhui");
////		    outletProductVo.setMerchantId("01A240508000");
////		    outletProductVo.setOutletId("01A240508000");
////		    outletProductVo.setUserId("1000009");
////		    outletProductVo.setCost(2.990);
////		    System.out.println(service2.addOutletProduct(originVo,outletProductVo));
//		    
////            System.out.println(service2.deleteOutletProduct(outletProductVo));
////            PageVo pageVo = new PageVo(1, 5, 0, 0);
////            System.out.println(service2.getOutletProductListByPage(outletProductVo, pageVo));
//			
////			 String commentId,String productId,String productName,short orderValue,long clientId,String merchantId,
////			    String merchantName,String orderId,String orderType,String memberCode,String memberName,
////			    short starLevel,String commentDescription,String recomment,long createTime)
////			ProductCommentVo pc = new ProductCommentVo("", "581301354186940416", "【凤凰台】鲁西肥牛", (short)1, 1L, "1", "merchantname", "1", "1", "tttetee", "nananna", (short)5, "t444444t", "", 20140502L);
//			ProductCommentVo pc = new ProductCommentVo();
//			pc.setOrderId("01A206D38000");
//			pc.setProductId("01764A090000");
//			pc.setMemberCode("30005240042");
//			pc.setMemberName("哪有");
//			pc.setStarLevel((short)1);
//			pc.setOrderType("1");
//			System.out.println(service2.addProductComment(pc));
//			
////			ProductCommentVo pc = new ProductCommentVo("551ba4bf49b3ea9e9892e6b3", "581301354186940416", "", (short)1, 1L, "1", "", "1", "1", "tttetee", "nananna", (short)5, "tezuizuizuiommment", "", 20140502L);
////            System.out.println(service2.updateProductComment(pc));
//			
////                 commentId:55497f5845cee0a2d53d6c72, productId:null, productName:null, orderValue:0, clientId:anhui, merchantId:null, 
////                 merchantName:null, orderId:null, orderType:null, memberCode:null, memberName:yancs, starLevel:0, commentDescription:null, recomment:wfwef223, createTime:0, orgCode:null, recommentTime:0, merchantUserName:null, phone:null, imagePic:null, type:null
//////			ProductCommentVo pc = new ProductCommentVo("551ba4bf49b3ea9e9892e6b3", "581301354186940416", "", (short)1, 1L, "1", "", "1", "1", "tttetee", "nananna", (short)5, "tezuizuizuiommment", "很好的", 20140502L);
////                 ProductCommentVo pc = new ProductCommentVo();
////                 pc.setCommentId("55497f5845cee0a2d53d6c72");
////                 pc.setMerchantUserName(merchantUserName)
////                 System.out.println(service2.replayProductComment(pc));
//			
////			List<ProductCommentVo> productCommentVo
////			
////			productCommentVo
////			
////			"55485d358dad4d0290fb36dc"
////			 pc.setMerchantUserName(merchantUserName)
////             System.out.println(service2.replayProductCommentBatch(productCommentVo));
//			
////			ProductCommentVo pc = new ProductCommentVo("551bb0a849b306c599b2fc4d", "581301354186940416", "", (short)1, 1L, "1", "", "1", "1", "tttetee", "nananna", (short)5, "tezuizuizuiommment", "很好的", 20140502L);
////            System.out.println(service2.deleteProductComment(pc));
//			
////			ProductCommentVo pc = new ProductCommentVo();
////			pc.setCommentId("554861c08dad6674eb9a4505");
////            System.out.println(service2.getProductCommentDetail(pc));
//			
////			ProductCommentVo pc = new ProductCommentVo("551bb08d49b306c599b2fc4c", "581301354186940416", "", (short)1, 1L, "1", "", "1", "1", "tttetee", "nananna", (short)5, "tezuizuizuiommment", "很好的", 20140502L);
////          System.out.println(service2.queryProductComment(pc));
//			
////			PageVo pageVo = new PageVo();
////            pageVo.setPageNumber(1);
////            pageVo.setPageSize(5);
////			PageVo pageVo = new PageVo(1, 10, 0, 0);
////			ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq(1, "{'startLervel':'00','merchantName':'na','productName':'鲁西肥','pointStartTime':'20141223','ointEndTime':'20141230','isReply':'-1','memberName':'t'}");
////			ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq(1, "{'startLervel':'00','productName':'鲁西肥'}");
////			ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq(1, "{'clientId':'1','isReply':'-1'}");
////			ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq("anhui", "{\"type\":\"1\"}");
////			ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq("anhui", "");
////			ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq("anhui", "{\"memberName\":\"vip110\"}");
////			System.out.println(service2.getProductCommentList(productCommentFilterReq, pageVo));
//			
////          public ProductCommentVo(
////         String commentId,String productId,String productName,short orderValue,String clientId,String merchantId,
////         String merchantName,String orderId,String orderType,String memberCode,String memberName,short starLevel,
////         String commentDescription,String recomment,long createTime,String orgCode,long recommentTime,
////         String merchantUserName,String phone,String imagePic,String type)
//			
////			ProductCommentVo productCommentVo = new ProductCommentVo();
////			productCommentVo.setMerchantName(merchantName)
////			productCommentVo.setMerchantId(merchantId)
////            productCommentVo.setProductId("01764A090000");
////            productCommentVo.setOrderValue((short)1);
////            productCommentVo.setOrderId("01A1A8E28000");//
////            productCommentVo.setBigOrderId("");
////            productCommentVo.setOrderType("2");
////            productCommentVo.setMemberCode("50000000197");
////            productCommentVo.setMemberName("gxl007");
////            productCommentVo.setStarLevel((short)4);
////            productCommentVo.setCommentDescription("评论内容1");
////            System.out.println(service2.addProductComment(productCommentVo));
//			
////			PageVo pageVo = new PageVo();
////			pageVo.setPageNumber(1);
////			pageVo.setPageSize(5);
////			ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq("anhui", "");
////			System.out.println(service2.queryProductComments(productCommentFilterReq, pageVo));
//			
////			艳姐，商品评论列表接口又没有返回订单信息了（订单ID和订单类型），帮忙查看一下吧 0 0 
////			productService.queryProductComments(req, page);
//			
////			ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq();
////			productCommentFilterReq.setClientId("anhui");
////			String filter = "{\"isSeachAll\":\"true\",\"isReply\":-1,\"productId\":\"01B8FEA18000\"}";
////			productCommentFilterReq.setFilter(filter);
////			System.out.println(service2.queryProductComments(productCommentFilterReq, pageVo));
//			
//			
////			productId=01E5F2A28000&areaId=100000020
////			ProductAreaVo productAreaVo = new ProductAreaVo("01E5F2A28000", "", 100000020, "", "","","");
////         System.out.println(service2.getProductAreaOutlets(productAreaVo));
//			
////			List<ProductCategoryVo> productCategoryVos = new ArrayList<ProductCategoryVo>();
////			ProductCategoryVo productCategoryVo1 = new ProductCategoryVo(0L, "anhui", "婚纱摄影tesbatch", "100000000", 100000000L, "/froad-cbank/image/201501/d5941763-229a-4899-8bde-9d9b004a3a06-thumbnail.jpg");
////			productCategoryVos.add(productCategoryVo1);
////			ProductCategoryVo productCategoryVo2 = new ProductCategoryVo(0L, "anhui", "美容美体tesbatch", "100000000", 100000000L, "/froad-cbank/image/201501/d5941763-229a-4899-8bde-9d9b004a3a06-thumbnail.jpg");
////			productCategoryVos.add(productCategoryVo2);
////			ProductCategoryVo productCategoryVo3 = new ProductCategoryVo(0L, "anhui", "洗衣家政tesbatch", "100000000", 100000000L, "/froad-cbank/image/201501/d5941763-229a-4899-8bde-9d9b004a3a06-thumbnail.jpg");
////			productCategoryVos.add(productCategoryVo3);
////			System.out.println(service1.addProductCategoryBatch(productCategoryVos));
//			
////			OutletProductVoReq outletProductVoReq = new OutletProductVoReq("anhui", "1001B990A48001");
////			System.out.println(service2.getOutletProduct(outletProductVoReq));
//			
////			List<String> ps = new ArrayList<String>();
////			ps.add("01E4F78E8000");
////			ps.add("01E512EF0000");
////			ProductStatusBatchVoReq productStatusBatchVoReq = new ProductStatusBatchVoReq();
////			productStatusBatchVoReq.setStatus("2");
////			productStatusBatchVoReq.setProductIds(ps);
////			System.out.println(service2.updateProductStatusBatch(originVo, productStatusBatchVoReq));
////			System.out.println(service2.deleteProductBatch(originVo, productStatusBatchVoReq));
//			
////			List<ProductStoreFilterVo> products = new ArrayList<ProductStoreFilterVo>();
////			ProductStoreFilterVo p = new ProductStoreFilterVo();
////			p.setProductId("0274C5498000");
////			products.add(p);
////			System.out.println(service2.getProductBaseInfo("", products));
//			
//			transport.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
