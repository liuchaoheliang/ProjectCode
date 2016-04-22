package com.froad.thrift;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.thirdparty.util.DateUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-4下午12:46:12 
 */
public class MakeOrderLogInfo {

	public static void main(String[] args) {
		PropertiesUtil.load();
		makeOrderLog();
		
//		System.out.println("time1="+DateUtil.parse("yyyy-MM-dd HH:mm:ss", "2015-09-01 00:00:00").getTime());
	}

	
	public static void makeOrderLog(){
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		BufferedWriter addWriter = null;
		BufferedWriter updateWriter = null;
		BufferedWriter takeWriter = null;
		DBObject where = new BasicDBObject();
		List<Object> listTypes = new ArrayList<Object>();
		listTypes.add(new BasicDBObject("client_id", "chongqing"));
		listTypes.add(new BasicDBObject("client_id", "taizhou"));
		listTypes.add(new BasicDBObject("client_id", "jilin"));
		where.put(QueryOperators.OR, listTypes);
//		where.put("client_id", "chongqing");
//		where.put("type", "");
//		where.put("order_status", "");
//		where.put("client_id", "");
//		where.put("client_id", "");
//		where.put("client_id", "");
		MongoManager mongoManager = new MongoManager(); 
		List<SubOrderMongo> subOrderList=(List<SubOrderMongo>)mongoManager.findAll(where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
//		BasicDBList values = new BasicDBList();
//		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,1441036800000l)));
//		values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,1447742556207l)));
//		where.put(QueryOperators.AND,values);
//		List<OrderMongo> orderList=(List<OrderMongo>)mongoManager.findAll(where, MongoTableName.CB_ORDER,OrderMongo.class);
		SubOrderMongo subOrder = null;
		OrderMongo order = null;
		MerchantDetail merchantDetail = null;
		List<ProductMongo> productMongoList = null;	
		ProductMongo productMongo=null;
		ProductDetail productDetail1=null;
		ProductDetail productDetail2=null;
		ProductDetail productDetail3=null;
		
//		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
//		accountMapper = sqlSession.getMapper(MerchantAccountMapper.class);		
//		merchant = new Merchant();
//		merchant.setClientId("chongqing");
//		merchantList = merchantMapper.findMerchant(merchant);
		
		try {
			addWriter = new BufferedWriter(new FileWriter(new File("E:\\log\\new\\order_success.log")));
			updateWriter = new BufferedWriter(new FileWriter(new File("E:\\log\\new\\order_modify.log")));
			takeWriter = new BufferedWriter(new FileWriter(new File("E:\\log\\new\\order_create.log")));
			StringBuffer addStr=null;
			StringBuffer updateStr=null;
			StringBuffer takeStr=null;
			StringBuffer addBigStr=null;
			StringBuffer updateBigStr=null;
			StringBuffer takeBigStr=null;
//			for (int i = 0; i < orderList.size(); i++){
//				order = orderList.get(i);
//				if(order!=null && order.getIsQrcode()==1){//面对面订单
//					//面对面订单
//					DBObject queryObj3 = new BasicDBObject();
//					queryObj3.put("_id", order.getMerchantId());
//					MerchantDetail merchantDetail2 = mongoManager.findOne(queryObj3,MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
//					if("1".equals(order.getOrderStatus())){
//						takeBigStr = new StringBuffer();
//						takeBigStr.append("{");
//						takeBigStr.append("\"key\": { \"id\":");
//						takeBigStr.append(order.getOrderId());
//						takeBigStr.append("},");
//						takeBigStr.append("\"action\": \"ORDERADD\",");
//						takeBigStr.append("\"client_id\": \"");
//						takeBigStr.append(order.getClientId());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"time\": ");
//						takeBigStr.append(order.getCreateTime());
//						takeBigStr.append(",");
//						takeBigStr.append("\"data\": {");
//						takeBigStr.append("\"id\":");
//						takeBigStr.append(order.getOrderId());
//						takeBigStr.append(",");
//						takeBigStr.append("\"order_id\":\"");
//						takeBigStr.append(order.getOrderId());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"sub_order_id\":\"");
//						takeBigStr.append(order.getOrderId());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"order_type\":\"");
//						takeBigStr.append("0");
//						takeBigStr.append("\",");
//						takeBigStr.append("\"order_status\":\"");
//						takeBigStr.append(order.getOrderStatus());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"org_code\":\"");
//						takeBigStr.append(merchantDetail2==null || merchantDetail2.getOrgCode()==null||"".equals(merchantDetail2.getOrgCode())?"":merchantDetail2.getOrgCode());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"member_code\":\"");
//						takeBigStr.append(order.getMemberCode());
//						takeBigStr.append("\",");//TODO org_name
//						takeBigStr.append("\"is_seckill\":\"");
//						takeBigStr.append(order.getIsSeckill());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"is_vip_order\":\"");
//						takeBigStr.append(order.getIsVipOrder());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"total_price\": \"");
//						takeBigStr.append(String.valueOf(order.getTotalPrice()));
//						takeBigStr.append("\",");
//						takeBigStr.append("\"fft_points\": \"");
//						takeBigStr.append(order.getFftPoints());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"bank_points\": \"");
//						takeBigStr.append(order.getBankPoints());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"cash_price\": \"");
//						takeBigStr.append(order.getCashDiscount());
//						takeBigStr.append("\",");
//						takeBigStr.append("\"payment_method\": \"");
//						takeBigStr.append(order.getPaymentMethod());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"payment_time\": \"");
//						takeBigStr.append(order.getPaymentTime());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"create_time\": \"");
//						takeBigStr.append(order.getCreateTime());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"vip_discount\": \"");
//						takeBigStr.append(order.getVipDiscount());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"merchant_id\": \"");
//						takeBigStr.append(order.getMerchantId());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"outlet_id\": \"");
//						takeBigStr.append(order.getOutletId());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"merchant_user_id\": \"");
//						takeBigStr.append(order.getMerchantUserId());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"create_source\": \"");
//						takeBigStr.append(order.getCreateSource());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"f_org_code\": \"");
//						takeBigStr.append(order.getForgCode());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"s_org_code\": \"");
//						takeBigStr.append(order.getSorgCode());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"t_org_code\": \"");
//						takeBigStr.append(order.getTorgCode());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"l_org_code\": \"");
//						takeBigStr.append(order.getLorgCode());//subOrder
//						takeBigStr.append("\",");
//						takeBigStr.append("\"recv_id\": \"");
//						takeBigStr.append(order.getRecvId());//Order
//						takeBigStr.append("\",");
//						takeBigStr.append("\"products\": [");	
//						takeBigStr.append("]");
//						takeBigStr.append("}");
//						takeBigStr.append("}");
//						takeBigStr.append("\n");
//					takeWriter.write(takeBigStr.toString());
//					}else if("6".equals(order.getOrderStatus())){
//						addBigStr = new StringBuffer();
//						addBigStr.append("{");
//						addBigStr.append("\"key\": { \"id\":");
//						addBigStr.append(order.getOrderId());
//						addBigStr.append("},");
//						addBigStr.append("\"action\": \"ORDERPAYSUCCESS\",");
//						addBigStr.append("\"client_id\": \"");
//						addBigStr.append(order.getClientId());
//						addBigStr.append("\",");
//						addBigStr.append("\"time\": ");
//						addBigStr.append(order.getCreateTime());
//						addBigStr.append(",");
//						addBigStr.append("\"data\": {");
//						addBigStr.append("\"id\":");
//						addBigStr.append(order.getOrderId());
//						addBigStr.append(",");
//						addBigStr.append("\"order_id\":\"");
//						addBigStr.append(order.getOrderId());
//						addBigStr.append("\",");
//						addBigStr.append("\"sub_order_id\":\"");
//						addBigStr.append(order.getOrderId());
//						addBigStr.append("\",");
//						addBigStr.append("\"order_type\":\"");
//						addBigStr.append("0");
//						addBigStr.append("\",");
//						addBigStr.append("\"order_status\":\"");
//						addBigStr.append(order.getOrderStatus());
//						addBigStr.append("\",");
//						addBigStr.append("\"org_code\":\"");
//						addBigStr.append(merchantDetail2==null || merchantDetail2.getOrgCode()==null||"".equals(merchantDetail2.getOrgCode())?"":merchantDetail2.getOrgCode());
//						addBigStr.append("\",");
//						addBigStr.append("\"member_code\":\"");
//						addBigStr.append(order.getMemberCode());
//						addBigStr.append("\",");//TODO org_name
//						addBigStr.append("\"is_seckill\":\"");
//						addBigStr.append(order.getIsSeckill());
//						addBigStr.append("\",");
//						addBigStr.append("\"is_vip_order\":\"");
//						addBigStr.append(order.getIsVipOrder());
//						addBigStr.append("\",");
//						addBigStr.append("\"total_price\": \"");
//						addBigStr.append(String.valueOf(order.getTotalPrice()));
//						addBigStr.append("\",");
//						addBigStr.append("\"fft_points\": \"");
//						addBigStr.append(order.getFftPoints());
//						addBigStr.append("\",");
//						addBigStr.append("\"bank_points\": \"");
//						addBigStr.append(order.getBankPoints());
//						addBigStr.append("\",");
//						addBigStr.append("\"cash_price\": \"");
//						addBigStr.append(order.getCashDiscount());
//						addBigStr.append("\",");
//						addBigStr.append("\"payment_method\": \"");
//						addBigStr.append(order.getPaymentMethod());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"payment_time\": \"");
//						addBigStr.append(order.getPaymentTime());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"create_time\": \"");
//						addBigStr.append(order.getCreateTime());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"vip_discount\": \"");
//						addBigStr.append(order.getVipDiscount());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"merchant_id\": \"");
//						addBigStr.append(order.getMerchantId());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"outlet_id\": \"");
//						addBigStr.append(order.getOutletId());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"merchant_user_id\": \"");
//						addBigStr.append(order.getMerchantUserId());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"create_source\": \"");
//						addBigStr.append(order.getCreateSource());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"f_org_code\": \"");
//						addBigStr.append(order.getForgCode());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"s_org_code\": \"");
//						addBigStr.append(order.getSorgCode());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"t_org_code\": \"");
//						addBigStr.append(order.getTorgCode());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"l_org_code\": \"");
//						addBigStr.append(order.getLorgCode());//subOrder
//						addBigStr.append("\",");
//						addBigStr.append("\"recv_id\": \"");
//						addBigStr.append(order.getRecvId());//Order
//						addBigStr.append("\",");
//						addBigStr.append("\"products\": [");	
//						addBigStr.append("]");
//						addBigStr.append("}");
//						addBigStr.append("}");
//						addBigStr.append("\n");
//						addWriter.write(addBigStr.toString());
//					}else{
//						updateBigStr = new StringBuffer();
//						updateBigStr.append("{");
//						updateBigStr.append("\"key\": { \"id\":");
//						updateBigStr.append(order.getOrderId());
//						updateBigStr.append("},");
//						updateBigStr.append("\"action\": \"ORDERMODIFY\",");
//						updateBigStr.append("\"client_id\": \"");
//						updateBigStr.append(order.getClientId());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"time\": ");
//						updateBigStr.append(order.getCreateTime());
//						updateBigStr.append(",");
//						updateBigStr.append("\"data\": {");
//						updateBigStr.append("\"id\":");
//						updateBigStr.append(order.getOrderId());
//						updateBigStr.append(",");
//						updateBigStr.append("\"order_id\":\"");
//						updateBigStr.append(order.getOrderId());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"sub_order_id\":\"");
//						updateBigStr.append(order.getOrderId());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"order_type\":\"");
//						updateBigStr.append("0");
//						updateBigStr.append("\",");
//						updateBigStr.append("\"order_status\":\"");
//						updateBigStr.append(order.getOrderStatus());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"org_code\":\"");
//						updateBigStr.append(merchantDetail2==null || merchantDetail2.getOrgCode()==null||"".equals(merchantDetail2.getOrgCode())?"":merchantDetail2.getOrgCode());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"member_code\":\"");
//						updateBigStr.append(order.getMemberCode());
//						updateBigStr.append("\",");//TODO org_name
//						updateBigStr.append("\"is_seckill\":\"");
//						updateBigStr.append(order.getIsSeckill());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"is_vip_order\":\"");
//						updateBigStr.append(order.getIsVipOrder());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"total_price\": \"");
//						updateBigStr.append(String.valueOf(order.getTotalPrice()));
//						updateBigStr.append("\",");
//						updateBigStr.append("\"fft_points\": \"");
//						updateBigStr.append(order.getFftPoints());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"bank_points\": \"");
//						updateBigStr.append(order.getBankPoints());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"cash_price\": \"");
//						updateBigStr.append(order.getCashDiscount());
//						updateBigStr.append("\",");
//						updateBigStr.append("\"payment_method\": \"");
//						updateBigStr.append(order.getPaymentMethod());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"payment_time\": \"");
//						updateBigStr.append(order.getPaymentTime());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"create_time\": \"");
//						updateBigStr.append(order.getCreateTime());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"vip_discount\": \"");
//						updateBigStr.append(order.getVipDiscount());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"merchant_id\": \"");
//						updateBigStr.append(order.getMerchantId());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"outlet_id\": \"");
//						updateBigStr.append(order.getOutletId());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"merchant_user_id\": \"");
//						updateBigStr.append(order.getMerchantUserId());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"create_source\": \"");
//						updateBigStr.append(order.getCreateSource());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"f_org_code\": \"");
//						updateBigStr.append(order.getForgCode());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"s_org_code\": \"");
//						updateBigStr.append(order.getSorgCode());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"t_org_code\": \"");
//						updateBigStr.append(order.getTorgCode());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"l_org_code\": \"");
//						updateBigStr.append(order.getLorgCode());//subOrder
//						updateBigStr.append("\",");
//						updateBigStr.append("\"recv_id\": \"");
//						updateBigStr.append(order.getRecvId());//Order
//						updateBigStr.append("\",");
//						updateBigStr.append("\"products\": [");	
//						updateBigStr.append("]");
//						updateBigStr.append("}");
//						updateBigStr.append("}");
//						updateBigStr.append("\n");
//						updateWriter.write(updateBigStr.toString());
//					}
//				}
//			}
			for (int i = 0; i < subOrderList.size(); i++){//面对面之外的其他订单
				subOrder = subOrderList.get(i);
				DBObject queryObj = new BasicDBObject();
				queryObj.put("_id", subOrder.getOrderId());
				order = mongoManager.findOne(queryObj,MongoTableName.CB_ORDER, OrderMongo.class);
				DBObject queryObj2 = new BasicDBObject();
				queryObj2.put("_id", subOrder.getMerchantId());
				merchantDetail = mongoManager.findOne(queryObj2,MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
				
				productMongoList = subOrder.getProducts();
				//if(productMongoList!=null && productMongoList.size()>0 && merchantDetail!=null){
				// 订单支付成功
//				if(order!=null && order.getIsQrcode()==1){
//					
//				}else 
				if(subOrder!=null && "6".equals(subOrder.getOrderStatus())){
					addStr = new StringBuffer();
					addStr.append("{");
					addStr.append("\"key\": { \"id\":");
					addStr.append(subOrder.getId());
					addStr.append("},");
					addStr.append("\"action\": \"ORDERPAYSUCCESS\",");
					addStr.append("\"client_id\": \"");
					addStr.append(subOrder.getClientId());
					addStr.append("\",");
					addStr.append("\"time\": ");
					addStr.append(subOrder.getCreateTime());
					addStr.append(",");
					addStr.append("\"data\": {");
					addStr.append("\"id\":");
					addStr.append(subOrder.getId());
					addStr.append(",");
					addStr.append("\"order_id\":\"");
					addStr.append(subOrder.getOrderId());
					addStr.append("\",");
					addStr.append("\"sub_order_id\":\"");
					addStr.append(subOrder.getSubOrderId());
					addStr.append("\",");
					addStr.append("\"order_type\":\"");
					addStr.append(subOrder.getType());
					addStr.append("\",");
					addStr.append("\"order_status\":\"");
					addStr.append(subOrder.getOrderStatus());
					addStr.append("\",");
					addStr.append("\"org_code\":\"");
					addStr.append(merchantDetail==null || merchantDetail.getOrgCode()==null||"".equals(merchantDetail.getOrgCode())?"":merchantDetail.getOrgCode());
					addStr.append("\",");
					addStr.append("\"member_code\":\"");
					addStr.append(subOrder.getMemberCode());
					addStr.append("\",");//TODO org_name
					addStr.append("\"is_seckill\":\"");
					addStr.append(order.getIsSeckill());
					addStr.append("\",");
					addStr.append("\"is_vip_order\":\"");
					addStr.append(order.getIsVipOrder());
					addStr.append("\",");
					addStr.append("\"total_price\": \"");
					addStr.append(String.valueOf(order.getTotalPrice()));
					addStr.append("\",");
					addStr.append("\"fft_points\": \"");
					addStr.append(order.getFftPoints());
					addStr.append("\",");
					addStr.append("\"bank_points\": \"");
					addStr.append(order.getBankPoints());
					addStr.append("\",");
					addStr.append("\"cash_price\": \"");
					addStr.append(order.getCashDiscount());
					addStr.append("\",");
					addStr.append("\"payment_method\": \"");
					addStr.append(order.getPaymentMethod());//subOrder
					addStr.append("\",");
					addStr.append("\"payment_time\": \"");
					addStr.append(order.getPaymentTime());//subOrder
					addStr.append("\",");
					addStr.append("\"create_time\": \"");
					addStr.append(order.getCreateTime());//subOrder
					addStr.append("\",");
					addStr.append("\"vip_discount\": \"");
					addStr.append(order.getVipDiscount());//subOrder
					addStr.append("\",");
					addStr.append("\"merchant_id\": \"");
					addStr.append(subOrder.getMerchantId());//subOrder
					addStr.append("\",");
					addStr.append("\"outlet_id\": \"");
					addStr.append(subOrder.getOrderId());//subOrder
					addStr.append("\",");
					addStr.append("\"merchant_user_id\": \"");
					addStr.append(order.getMerchantUserId());//subOrder
					addStr.append("\",");
					addStr.append("\"recv_id\": \"");
					addStr.append(order.getRecvId());//Order
					addStr.append("\",");
					addStr.append("\"create_source\": \"");
					addStr.append(order.getCreateSource());//subOrder
					addStr.append("\",");					
					addStr.append("\"f_org_code\": \"");
					addStr.append(subOrder.getForgCode());//subOrder
					addStr.append("\",");
					addStr.append("\"s_org_code\": \"");
					addStr.append(subOrder.getSorgCode());//subOrder
					addStr.append("\",");
					addStr.append("\"t_org_code\": \"");
					addStr.append(subOrder.getTorgCode());//subOrder
					addStr.append("\",");
					addStr.append("\"l_org_code\": \"");
					addStr.append(subOrder.getLorgCode());//subOrder
					addStr.append("\",");					
					addStr.append("\"products\": [");
					DBObject queryObj3 = null;
					for (int j = 0; j < productMongoList.size(); j++){
						productMongo = productMongoList.get(j);
						if(j==0){
							addStr.append("{");
						}else{
							addStr.append(",{");
						}
						queryObj3 = new BasicDBObject();
						queryObj3.put("_id", productMongo.getProductId());
						productDetail1 = mongoManager.findOne(queryObj3,MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
						addStr.append("\"product_id\": \"");
						addStr.append(productMongo.getProductId());
						addStr.append("\",\"product_name\": \"");
						addStr.append(productMongo.getProductName());
						addStr.append("\",\"category_id\": \"");
						addStr.append(productDetail1==null||productDetail1.getProductCategoryInfo()==null||productDetail1.getProductCategoryInfo().size()==0?"":productDetail1.getProductCategoryInfo().get(0).getProductCategoryId());
						addStr.append("\",\"money\": \"");
						addStr.append(String.valueOf(productMongo.getMoney()));
						addStr.append("\",\"vip_money\": \"");
						addStr.append(String.valueOf(productMongo.getVipMoney()));
						addStr.append("\",\"quantity\": \"");
						addStr.append(String.valueOf(productMongo.getQuantity()));
						addStr.append("\",\"type\": \"");
						addStr.append(String.valueOf(subOrder.getType()));
						addStr.append("\",\"vip_quantity\": \"");
						addStr.append(String.valueOf(productMongo.getVipQuantity()));
						addStr.append("\",\"take_org_code\": \"");
						addStr.append(productMongo.getOrgCode());
						addStr.append("\",\"take_org_name\": \"");
						addStr.append(productMongo.getOrgName());
						addStr.append("\",\"delivery_option\": \"");
						addStr.append(productMongo.getDeliveryOption());
						addStr.append("\",\"delivery_state\": \"");
						addStr.append(productMongo.getDeliveryState());
						addStr.append("\",\"delivery_money\": \"");
						addStr.append(productMongo.getDeliveryMoney());
						addStr.append("\"}");
					}
					addStr.append("]");
					addStr.append("}");
					addStr.append("}");
					addStr.append("\n");
					addWriter.write(addStr.toString());
				}else if(subOrder!=null && !"6".equals(subOrder.getOrderStatus()) && !"1".equals(subOrder.getOrderStatus())){//订单修改
						updateStr = new StringBuffer();
						updateStr.append("{");
						updateStr.append("\"key\": { \"id\":");
						updateStr.append(subOrder.getId());
						updateStr.append("},");
						updateStr.append("\"action\": \"ORDERMODIFY\",");
						updateStr.append("\"client_id\": \"");
						updateStr.append(subOrder.getClientId());
						updateStr.append("\",");
						updateStr.append("\"time\": ");
						updateStr.append(subOrder.getCreateTime());
						updateStr.append(",");
						updateStr.append("\"data\": {");
						updateStr.append("\"id\":");
						updateStr.append(subOrder.getId());
						updateStr.append(",");
						updateStr.append("\"order_id\":\"");
						updateStr.append(subOrder.getOrderId());
						updateStr.append("\",");
						updateStr.append("\"sub_order_id\":\"");
						updateStr.append(subOrder.getSubOrderId());
						updateStr.append("\",");
						updateStr.append("\"order_type\":\"");
						updateStr.append(subOrder.getType());
						updateStr.append("\",");
						updateStr.append("\"order_status\":\"");
						updateStr.append(subOrder.getOrderStatus());
						updateStr.append("\",");
						updateStr.append("\"org_code\":\"");
						updateStr.append(merchantDetail==null || merchantDetail.getOrgCode()==null||"".equals(merchantDetail.getOrgCode())?"":merchantDetail.getOrgCode());
						updateStr.append("\",");
						updateStr.append("\"member_code\":\"");
						updateStr.append(subOrder.getMemberCode());
						updateStr.append("\",");//TODO org_name
						updateStr.append("\"is_seckill\":\"");
						updateStr.append(order.getIsSeckill());
						updateStr.append("\",");
						updateStr.append("\"is_vip_order\":\"");
						updateStr.append(order.getIsVipOrder());
						updateStr.append("\",");
						updateStr.append("\"total_price\": \"");
						updateStr.append(String.valueOf(order.getTotalPrice()));
						updateStr.append("\",");
						updateStr.append("\"fft_points\": \"");
						updateStr.append(order.getFftPoints());
						updateStr.append("\",");
						updateStr.append("\"bank_points\": \"");
						updateStr.append(order.getBankPoints());
						updateStr.append("\",");
						updateStr.append("\"cash_price\": \"");
						updateStr.append(order.getCashDiscount());
						updateStr.append("\",");
						updateStr.append("\"payment_method\": \"");
						updateStr.append(order.getPaymentMethod());//subOrder
						updateStr.append("\",");
						updateStr.append("\"payment_time\": \"");
						updateStr.append(order.getPaymentTime());//subOrder
						updateStr.append("\",");
						updateStr.append("\"create_time\": \"");
						updateStr.append(order.getCreateTime());//subOrder
						updateStr.append("\",");
						updateStr.append("\"vip_discount\": \"");
						updateStr.append(order.getVipDiscount());//subOrder
						updateStr.append("\",");
						updateStr.append("\"merchant_id\": \"");
						updateStr.append(subOrder.getMerchantId());//subOrder
						updateStr.append("\",");
						updateStr.append("\"outlet_id\": \"");
						updateStr.append(subOrder.getOrderId());//subOrder
						updateStr.append("\",");
						updateStr.append("\"merchant_user_id\": \"");
						updateStr.append(order.getMerchantUserId());//subOrder
						updateStr.append("\",");
						updateStr.append("\"create_source\": \"");
						updateStr.append(order.getCreateSource());//order
						updateStr.append("\",");
						updateStr.append("\"f_org_code\": \"");
						updateStr.append(subOrder.getForgCode());//subOrder
						updateStr.append("\",");
						updateStr.append("\"s_org_code\": \"");
						updateStr.append(subOrder.getSorgCode());//subOrder
						updateStr.append("\",");
						updateStr.append("\"t_org_code\": \"");
						updateStr.append(subOrder.getTorgCode());//subOrder
						updateStr.append("\",");
						updateStr.append("\"l_org_code\": \"");
						updateStr.append(subOrder.getLorgCode());//subOrder
						updateStr.append("\",");
						updateStr.append("\"recv_id\": \"");
						updateStr.append(order.getRecvId());//Order
						updateStr.append("\",");
						updateStr.append("\"products\": [");	
						DBObject queryObj4 = null;
						for (int j = 0; j < productMongoList.size(); j++){
							productMongo = productMongoList.get(j);
							if(j==0){
								updateStr.append("{");
							}else{
								updateStr.append(",{");
							}
							queryObj4 = new BasicDBObject();
							queryObj4.put("_id", productMongo.getProductId());
							productDetail2 = mongoManager.findOne(queryObj4,MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
							updateStr.append("\"product_id\": \"");
							updateStr.append(productMongo.getProductId());
							updateStr.append("\",\"product_name\": \"");
							updateStr.append(productMongo.getProductName());
							updateStr.append("\",\"category_id\": \"");
							updateStr.append(productDetail2==null||productDetail2.getProductCategoryInfo()==null||productDetail2.getProductCategoryInfo().size()==0?"":productDetail2.getProductCategoryInfo().get(0).getProductCategoryId());
							updateStr.append("\",\"money\": \"");
							updateStr.append(String.valueOf(productMongo.getMoney()));
							updateStr.append("\",\"vip_money\": \"");
							updateStr.append(String.valueOf(productMongo.getVipMoney()));
							updateStr.append("\",\"type\": \"");
							updateStr.append(String.valueOf(subOrder.getType()));
							updateStr.append("\",\"quantity\": \"");
							updateStr.append(String.valueOf(productMongo.getQuantity()));
							updateStr.append("\",\"vip_quantity\": \"");
							updateStr.append(String.valueOf(productMongo.getVipQuantity()));
							updateStr.append("\",\"take_org_code\": \"");
							updateStr.append(productMongo.getOrgCode());
							updateStr.append("\",\"take_org_name\": \"");
							updateStr.append(productMongo.getOrgName());
							updateStr.append("\",\"delivery_option\": \"");
							updateStr.append(productMongo.getDeliveryOption());
							updateStr.append("\",\"delivery_state\": \"");
							updateStr.append(productMongo.getDeliveryState());
							updateStr.append("\",\"delivery_money\": \"");
							updateStr.append(productMongo.getDeliveryMoney());
							updateStr.append("\"}");
						}	
						updateStr.append("]");
						updateStr.append("}");
						updateStr.append("}");
						updateStr.append("\n");
						updateWriter.write(updateStr.toString());
					}else if(subOrder!=null && "1".equals(subOrder.getOrderStatus())){// 订单创建
						takeStr = new StringBuffer();
						takeStr.append("{");
						takeStr.append("\"key\": { \"id\":");
						takeStr.append(subOrder.getId());
						takeStr.append("},");
						takeStr.append("\"action\": \"ORDERADD\",");
						takeStr.append("\"client_id\": \"");
						takeStr.append(subOrder.getClientId());
						takeStr.append("\",");
						takeStr.append("\"time\": ");
						takeStr.append(subOrder.getCreateTime());
						takeStr.append(",");
						takeStr.append("\"data\": {");
						takeStr.append("\"id\":");
						takeStr.append(subOrder.getId());
						takeStr.append(",");
						takeStr.append("\"order_id\":\"");
						takeStr.append(subOrder.getOrderId());
						takeStr.append("\",");
						takeStr.append("\"sub_order_id\":\"");
						takeStr.append(subOrder.getSubOrderId());
						takeStr.append("\",");
						takeStr.append("\"order_type\":\"");
						takeStr.append(subOrder.getType());
						takeStr.append("\",");
						takeStr.append("\"order_status\":\"");
						takeStr.append(subOrder.getOrderStatus());
						takeStr.append("\",");
						takeStr.append("\"org_code\":\"");
						takeStr.append(merchantDetail==null || merchantDetail.getOrgCode()==null||"".equals(merchantDetail.getOrgCode())?"":merchantDetail.getOrgCode());
						takeStr.append("\",");
						takeStr.append("\"member_code\":\"");
						takeStr.append(subOrder.getMemberCode());
						takeStr.append("\",");//TODO org_name
						takeStr.append("\"is_seckill\":\"");
						takeStr.append(order.getIsSeckill());
						takeStr.append("\",");
						takeStr.append("\"is_vip_order\":\"");
						takeStr.append(order.getIsVipOrder());
						takeStr.append("\",");
						takeStr.append("\"total_price\": \"");
						takeStr.append(String.valueOf(order.getTotalPrice()));
						takeStr.append("\",");
						takeStr.append("\"fft_points\": \"");
						takeStr.append(order.getFftPoints());
						takeStr.append("\",");
						takeStr.append("\"bank_points\": \"");
						takeStr.append(order.getBankPoints());
						takeStr.append("\",");
						takeStr.append("\"cash_price\": \"");
						takeStr.append(order.getCashDiscount());
						takeStr.append("\",");
						takeStr.append("\"payment_method\": \"");
						takeStr.append(order.getPaymentMethod());//subOrder
						takeStr.append("\",");
						takeStr.append("\"payment_time\": \"");
						takeStr.append(order.getPaymentTime());//subOrder
						takeStr.append("\",");
						takeStr.append("\"create_time\": \"");
						takeStr.append(order.getCreateTime());//subOrder
						takeStr.append("\",");
						takeStr.append("\"vip_discount\": \"");
						takeStr.append(order.getVipDiscount());//subOrder
						takeStr.append("\",");
						takeStr.append("\"merchant_id\": \"");
						takeStr.append(subOrder.getMerchantId());//subOrder
						takeStr.append("\",");
						takeStr.append("\"outlet_id\": \"");
						takeStr.append(subOrder.getOrderId());//subOrder
						takeStr.append("\",");
						takeStr.append("\"merchant_user_id\": \"");
						takeStr.append(order.getMerchantUserId());//subOrder
						takeStr.append("\",");
						takeStr.append("\"create_source\": \"");
						takeStr.append(order.getCreateSource());//order
						takeStr.append("\",");
						takeStr.append("\"f_org_code\": \"");
						takeStr.append(subOrder.getForgCode());//subOrder
						takeStr.append("\",");
						takeStr.append("\"s_org_code\": \"");
						takeStr.append(subOrder.getSorgCode());//subOrder
						takeStr.append("\",");
						takeStr.append("\"t_org_code\": \"");
						takeStr.append(subOrder.getTorgCode());//subOrder
						takeStr.append("\",");
						takeStr.append("\"l_org_code\": \"");
						takeStr.append(subOrder.getLorgCode());//subOrder
						takeStr.append("\",");
						takeStr.append("\"recv_id\": \"");
						takeStr.append(order.getRecvId());//Order
						takeStr.append("\",");
						takeStr.append("\"products\": [");	
						DBObject queryObj5 =null;
							for (int j = 1; j < productMongoList.size(); j++){
								productMongo = productMongoList.get(j);
								if(j==0){
									takeStr.append("{");
								}else{
									takeStr.append(",{");
								}
								queryObj5 = new BasicDBObject();
								queryObj5.put("_id", productMongo.getProductId());
								productDetail3 = mongoManager.findOne(queryObj5,MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
								takeStr.append("\"product_id\": \"");
								takeStr.append(productMongo.getProductId());
								takeStr.append("\",\"product_name\": \"");
								takeStr.append(productMongo.getProductName());
								takeStr.append("\",\"category_id\": \"");
								takeStr.append(productDetail3==null||productDetail3.getProductCategoryInfo()==null||productDetail3.getProductCategoryInfo().size()==0?"":productDetail3.getProductCategoryInfo().get(0).getProductCategoryId());
								takeStr.append("\",\"money\": \"");
								takeStr.append(String.valueOf(productMongo.getMoney()));
								takeStr.append("\",\"vip_money\": \"");
								takeStr.append(String.valueOf(productMongo.getVipMoney()));
								takeStr.append("\",\"quantity\": \"");
								takeStr.append(String.valueOf(productMongo.getQuantity()));
								takeStr.append("\",\"type\": \"");
								takeStr.append(String.valueOf(subOrder.getType()));
								takeStr.append("\",\"vip_quantity\": \"");
								takeStr.append(String.valueOf(productMongo.getVipQuantity()));
								takeStr.append("\",\"take_org_code\": \"");
								takeStr.append(productMongo.getOrgCode());
								takeStr.append("\",\"take_org_name\": \"");
								takeStr.append(productMongo.getOrgName());
								takeStr.append("\",\"delivery_option\": \"");
								takeStr.append(productMongo.getDeliveryOption());
								takeStr.append("\",\"delivery_state\": \"");
								takeStr.append(productMongo.getDeliveryState());
								takeStr.append("\",\"delivery_money\": \"");
								takeStr.append(productMongo.getDeliveryMoney());
								takeStr.append("\"}");
							}
							takeStr.append("]");
							takeStr.append("}");
							takeStr.append("}");
							takeStr.append("\n");
						takeWriter.write(takeStr.toString());
					}
					
				//}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				addWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				addWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				updateWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				updateWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				takeWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				takeWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
