package com.froad.test.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.OrderMapper;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.DeliveryType;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.po.Org;
import com.froad.po.Product;
import com.froad.po.Store;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.productdetail.ProductDetail;
import com.froad.support.OrderSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.util.EmptyChecker;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class DataRepair {
	
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = new MongoManager();
	
	private RedisManager redis = new RedisManager();
	
	private void showSellCount(String clientId,String merchantId,String productId){
		/**销售量查询*/
		SqlSession sqlSession = null;
		try {
		    //redis销售数量
		    int redisSellCount = RedisCommon.getProductSellCountRedis(clientId, merchantId, productId);
		    if(redisSellCount==-1){
		    	LogCvt.error("[销售数量更新]-销售数量查询，redis为空，key：" + RedisKeyUtil.cbbank_product_sellcount_client_id_merchant_id_product_id(clientId,merchantId,productId));
		    }
		    
		    //mysql销售数量
		    sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		    OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
		    
		    Product product = new Product();
		    product.setClientId(clientId);
		    product.setMerchantId(merchantId);
		    product.setProductId(productId);
		    Integer mysqlSellCount = orderMapper.getProductSellCount(product);
		    if(EmptyChecker.isEmpty(mysqlSellCount)){
		    	LogCvt.error("[销售数量更新]-销售数量查询，mysql为空，查询条件：clientId:"+clientId+", merchantId:" + merchantId + ", productId:" + productId);
		    }
		    
		    //mongo销售数量
		    DBObject where = new BasicDBObject();
		    if(EmptyChecker.isNotEmpty(clientId)){
		        where.put("client_id", clientId);
		    }
		    if(EmptyChecker.isNotEmpty(merchantId)){
		        where.put("merchant_id", merchantId);
		    }
		    where.put("_id", productId);
		    ProductDetail productSellCount = mongo.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
		    if(EmptyChecker.isEmpty(productSellCount)){
		    	LogCvt.error("[销售数量更新]-销售数量查询，Mongo为空，查询条件：" + where);
		    }
		    Integer mongoSellCount = productSellCount.getSellCount();
			LogCvt.error("[销售数量更新]-销售数量更新结果，redis:"+redisSellCount +", mysql:" + mysqlSellCount + ", mongo:" + mongoSellCount);
			
		} catch (Exception e) {
            sqlSession.rollback(true);
            LogCvt.error("查询销售量失败，原因:" + e.getMessage()); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
	}
	
	/**
	 * 修复订单状态
	 */
	private void OrderStatus(){
		
	}
	
	/**
	 * 修复销售数量
	 */
	private void ProductSellCount(){
		
	}
	
	/**
	 * 修复销售数量
	 */
	private void repairProductSellCount(){
		//mongo销售数量
	    DBObject where = new BasicDBObject();
	    /*where.put("client_id", "anhui");*/
	    LogCvt.error("[销售数量更新]-销售数量查询，Mongo为空，查询条件：" + where);
	    List<ProductDetail> productSellCountList = (List<ProductDetail>) mongo.findAll(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
	    int i = 0;
	    StringBuffer sb = new StringBuffer("{_id:{$in:");
	    SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	    OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
	    if(EmptyChecker.isNotEmpty(productSellCountList)){
	    	for(ProductDetail productDetail : productSellCountList){
	    		//mongo销售数量
	    		Integer mongoSellCount = productDetail.getSellCount();
	    		
	    		//mysql销售数量
			    Product product = new Product();
			    product.setClientId(productDetail.getClientId());
			    product.setMerchantId(productDetail.getMerchantId());
			    product.setProductId(productDetail.getId());
			    Integer mysqlSellCount = orderMapper.getProductSellCount(product);
			    
				//redis销售数量
				int redisSellCount = RedisCommon.getProductSellCountRedis(productDetail.getClientId(), productDetail.getMerchantId(), productDetail.getId());
				
				if(EmptyChecker.isNotEmpty(mysqlSellCount) && mysqlSellCount > 0 ){
					i++;
					sb.append(productDetail.getId()+",");
//					mongoSellCountUpdate(productDetail.getClientId(),productDetail.getMerchantId(),productDetail.getId(),mysqlSellCount);
					
//					redisSellCountUpdate(productDetail.getClientId(),productDetail.getMerchantId(),productDetail.getId(),mongoSellCount,mysqlSellCount);
					
					LogCvt.error("[销售数量更新]-销售数量更新结果，redis:"+redisSellCount +", mysql:" + mysqlSellCount + ", mongo:" + mongoSellCount);
					
				}
				
		    }
	    }
	    sqlSession.close();
	    sb.append("]}}");
	    System.out.println("总条数："+productSellCountList.size());
	    System.out.println("更新条数："+i);
	    System.out.println("更新过的productId："+sb);
	}
	
	/**
	 * 更新mongo商品销售量
	 * @param clientId
	 * @param merchantId
	 * @param productId
	 * @param sellCount
	 */
	private void mongoSellCountUpdate(String clientId,String merchantId,String productId,int sellCount){
		DBObject sellwhere = new BasicDBObject();
    	sellwhere.put("client_id", clientId);
    	sellwhere.put("merchant_id", merchantId);
        sellwhere.put("_id",productId );
        DBObject sellvalue = new BasicDBObject();
        /*int sellCount = EmptyChecker.isEmpty(productDetail.getSellCount()) ? 0 : productDetail.getSellCount();*/
        sellvalue.put("sell_count", sellCount);
        mongo.update(sellvalue, sellwhere, MongoTableName.CB_PRODUCT_DETAIL, "$set");
	}
	
	/**
	 * 更新redis商品销售量
	 * @param clientId
	 * @param merchantId
	 * @param productId
	 * @param oldSellCount
	 * @param sellCount
	 */
	private void redisSellCountUpdate(String clientId,String merchantId,String productId,Integer oldSellCount,Integer sellCount){
		String sellCountKey = RedisKeyUtil.cbbank_product_sellcount_client_id_merchant_id_product_id(clientId,merchantId,productId);
		System.out.println("redis-key:"+sellCountKey+"更新前：" + oldSellCount);
		redis.putString(sellCountKey, sellCount.toString());
		System.out.println("redis-key:"+sellCountKey+"更新后：" + RedisCommon.getProductSellCountRedis(clientId,merchantId,productId));
	}
	
	/**
	 * 清除OrgLevel缓存
	 */
	public void clearRedisOrgLevel(){
		
	}
	
	/**
	 * 将四级机构转移到三级机构
	 * {l_org_code:1,t_org_code:1,create_time:1,merchant_id:1}
	 * {create_time:-1}
	 */
	public void mongoChangeLastorgToThirdOrg(){
		//mongo销售数量
	    DBObject where = new BasicDBObject();
	    /*where.put("client_id", "anhui");*/
	    LogCvt.info("[销售数量更新]-销售数量查询，Mongo为空，查询条件：" + where);
	    List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) mongo.findAll(where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	    int i = 0;
	    StringBuffer sb = new StringBuffer("{_id:{$in:");
	    if(EmptyChecker.isNotEmpty(subOrderList)){
	    	for(SubOrderMongo subOrder : subOrderList){
	    		if(EmptyChecker.isNotEmpty(subOrder.getLorgCode()) && EmptyChecker.isEmpty(subOrder.getTorgCode())){
	    			i++;
	    			sb.append(subOrder.getSubOrderId());
	    			DBObject sellwhere = new BasicDBObject();
	    	    	sellwhere.put("sub_order_id", subOrder.getSubOrderId());
	    	    	sellwhere.put("order_id", subOrder.getOrderId());
	    	        sellwhere.put("client_id",subOrder.getClientId());
	    	        DBObject sellvalue = new BasicDBObject();
	    	        sellvalue.put("l_org_code", "");
	    	        sellvalue.put("t_org_code", subOrder.getLorgCode());
	    	        System.out.println("更新条件："+sellwhere + "  更新值："+sellvalue);
	    	        mongo.update(sellvalue, sellwhere, MongoTableName.CB_SUBORDER_PRODUCT, "$set");
	    		}
		    }
	    }
	    sb.append("]}}");
	    System.out.println("更新条数："+i);
	    System.out.println("更新过的productId："+sb);
	}
	
	/**
	 * 关闭支付失败且超过两小时的订单，补全订单创建时间缓存
	 */
	public void addOrderRedisForPayFailedOrderOverTwoHours(){
		
		DBObject query = new BasicDBObject();
		query.put("client_id", "anhui");
		query.put("order_status", OrderStatus.payfailed.getCode());
		query.put("create_time", new BasicDBObject(QueryOperators.LT,1434529320000L));
		List<OrderMongo> list =  (List<OrderMongo>) mongo.findAll(query, MongoTableName.CB_ORDER, OrderMongo.class);
		
		System.out.println("找到记录条数："+list.size() + " 查找结果：" + JSON.toJSONString(list,true));
		for(OrderMongo order : list){
			String timeOrderKey = RedisKeyUtil.cbbank_time_order_key();
			String timeOrderVlue = RedisKeyUtil.cbbank_time_order_value("anhui", order.getOrderId());
			LogCvt.info("订单创建时间缓存  key：" + timeOrderKey + "value:" + timeOrderVlue);
			
			Set<String> set = new HashSet<String>();
			set.add(timeOrderVlue);
			redis.putSet(timeOrderKey, set);
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertiesUtil.load();
		DataRepair test = new DataRepair();
//		test.repairProductSellCount();
//		test.mongoChangeLastorgToThirdOrg();
		test.addOrderRedisForPayFailedOrderOverTwoHours();
	}

}
