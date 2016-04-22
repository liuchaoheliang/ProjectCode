package com.froad.support.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.SettlementType;
import com.froad.logback.LogCvt;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.po.shoppingcart.mongo.ShoppingCart;
import com.froad.support.ShoppingCartSupport;
import com.froad.util.Arith;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;


public class ShoppingCartSupportImpl implements ShoppingCartSupport {
	
	
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = new MongoManager();
	

	/**
	 *  获取购物车单个商品信息
	  * @Title: queryShoppingProduct
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param cientId
	  * @param @param memberCode
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#queryShoppingProduct(long, long, java.lang.String, java.lang.String)
	 */
	@Override
	public ShoppingCart queryShoppingCart(String clientId, long memberCode, String productId) {
		// TODO Auto-generated method stub
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("member_code", memberCode);
		queryObj.put("product_id", productId);
		return mongo.findOne(queryObj, MongoTableName.CB_PRE_SHOPPING, ShoppingCart.class);

	}


	
	/**
	 *  计算用户在一个client的下购物车商品的数量
	  * @Title: getMemberShoppingCartCount
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param _id
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int queryMemberShoppingCartCount(String clientId,long memberCode,String merchantId,String productId){
		
		// 查询条件
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("member_code", memberCode);
		return mongo.getCount(queryObj, MongoTableName.CB_PRE_SHOPPING);
	}

	/**
	 *  获取用户购车数量
	  * @Title: countMemberCarts
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#countMemberCarts(long, long)
	 */
	@Override
	public int countMemberCarts(String clientId, long memberCode) {
		// TODO Auto-generated method stub
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("member_code", memberCode);
		int count = mongo.getCount(queryObj, MongoTableName.CB_PRE_SHOPPING);
		return count;
	}

	/**
	 *  更新商品
	  * @Title: updateProduct
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @param merchantId
	  * @param @param product
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#updateProduct(long, long, java.lang.String, com.froad.po.shoppingcart.mongo.ShoppingProduct)
	 */
	@Override
	public boolean insertOrUpdateProduct(ShoppingCart cart) {
		
		DBObject queryObj = new BasicDBObject();
		cart.setId(null);
		queryObj.put("client_id", cart.getClientId());
		queryObj.put("member_code", cart.getMemberCode());
		// 商品ID
		queryObj.put("product_id", cart.getProductId());
		
		DBObject value = (DBObject)JSON.parse(JSonUtil.toJSonString(cart));
		int result = mongo.update(value,queryObj, MongoTableName.CB_PRE_SHOPPING, null, true,false);
		return result != -1;
	}
	
	@Override
	public boolean updateProduct(ShoppingCart cart) {
		
		DBObject queryObj = new BasicDBObject();
		cart.setId(null);
		queryObj.put("client_id", cart.getClientId());
		queryObj.put("member_code", cart.getMemberCode());
		// 商品ID
		queryObj.put("product_id", cart.getProductId());
		DBObject value = new BasicDBObject("$set", JSON.parse(JSonUtil.toJSonString(cart))); 
		int result = mongo.update(value, queryObj, MongoTableName.CB_PRE_SHOPPING,null);
		return result != -1;
	}

	/**
	 *   清除购物车单个商品信息
	  * @Title: removeProduct
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#removeProduct(long, long, java.lang.String, java.lang.String)
	 */
	@Override
	public int removeProduct(String clientId, long memberCode, String merchantId,String productId) {
		
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("member_code", memberCode);
		// 商品ID
		queryObj.put("product_id", productId);
		return mongo.remove(queryObj, MongoTableName.CB_PRE_SHOPPING);
		
	}
	
	
	/**
	 *  清空购物车
	  * @Title: deleteCart
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#deleteCart(long, long)
	 */
	@Override
	public int deleteCart(long memberCode, String clientId) {
		// TODO Auto-generated method stub
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id",  clientId);
		queryObj.put("member_code", memberCode);
		
		int result = mongo.remove(queryObj, MongoTableName.CB_PRE_SHOPPING);
		return result;
	}

	/**
	 *  查询商品购物车
	  * @Title: queryShoppingCart
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#queryShoppingCart(long, long)
	 */
	@Override
	public List<ShoppingCart> queryShoppingCart(String clientId, long memberCode) {
		// TODO Auto-generated method stub
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id",  clientId);
		queryObj.put("member_code", memberCode);
		
		DBObject oerderBy = new BasicDBObject("time", -1);
		
		List<ShoppingCart> memberCart = (List<ShoppingCart>) mongo.findAll(queryObj, oerderBy, MongoTableName.CB_PRE_SHOPPING, ShoppingCart.class);
		return memberCart;
	}



	/**
	 *  更新商品数量
	  * @Title: updateProductQuantity
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @param product
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#updateProductQuantity(com.froad.po.shoppingcart.mongo.ShoppingCart2)
	 */
	@Override
	public boolean updateProductQuantity(ShoppingCart cart) {
		// TODO Auto-generated method stub
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", cart.getClientId());
		queryObj.put("member_code", cart.getMemberCode());
		queryObj.put("product_id", cart.getProductId());
		
		DBObject value = new BasicDBObject();
		value.put("quantity", cart.getQuantity());
		value.put("vip_quantity", cart.getVipQuantity());
		int result = mongo.update(new BasicDBObject("$set", value), queryObj, MongoTableName.CB_PRE_SHOPPING,null);
		return result != -1;
	}

	/**
	 *  获取商品数目
	  * @Title: queryMemberShoppingProductCount
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport2#queryMemberShoppingProductCount(java.lang.String, long)
	 */
	@Override
	public int queryMemberShoppingByProductIdCount(String clientId, long memberCode,String productId) {
		// TODO Auto-generated method stub
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("member_code", memberCode);
		queryObj.put("product_id", productId);
		return mongo.getCount(queryObj, MongoTableName.CB_PRE_SHOPPING);
	}
	
	/**
	 *  获取用户购车商品总数量
	  * @Title: countMemberCarts
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#countMemberCarts(long, long)
	 */
	@Override
	public int countMemberCartsTotal(String clientId, long memberCode) {
		
		// $match
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("member_code", memberCode);
		
		DBObject matchObj = new BasicDBObject();
		matchObj.put("$match", queryObj);
		
		// $group
		DBObject groupFieldObj = new BasicDBObject();
		groupFieldObj.put("_id", null);
		
		List<String> addList = new ArrayList<String>();
		addList.add("$quantity");
		addList.add("$vip_quantity");
		
		DBObject addObj = new BasicDBObject();
		addObj.put("$add", addList);
		
		
		groupFieldObj.put("count", new BasicDBObject("$sum", addObj));
		
		DBObject groupObj = new BasicDBObject();
		groupObj.put("$group", groupFieldObj);
		
		List<DBObject> pipeline = new ArrayList<DBObject>();
		pipeline.add(matchObj);
		pipeline.add(groupObj);
		
		LogCvt.info(new StringBuffer("用户购物车商品总数查询：").append(pipeline.toString()).toString());
		
		Iterator<DBObject> iterator = mongo.findByPipeline(pipeline, MongoTableName.CB_PRE_SHOPPING);
		
		int count = 0;
		
		if (iterator.hasNext()){
			DBObject tmpObj = iterator.next();
			if (tmpObj.containsField("count")){
				count = Integer.parseInt(tmpObj.get("count").toString());
			}
		}
		OrderLogger.info("购物车模块", "查询用户购物车中商品总数量", "查询结果："+count,null);
		return count;
	}
	
	@Override
	public int queryMemberShoppingProductCount(String clientId, long memberCode) {
		// TODO Auto-generated method stub
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("member_code", memberCode);
		return mongo.getCount(queryObj, MongoTableName.CB_PRE_SHOPPING);
	}

}

