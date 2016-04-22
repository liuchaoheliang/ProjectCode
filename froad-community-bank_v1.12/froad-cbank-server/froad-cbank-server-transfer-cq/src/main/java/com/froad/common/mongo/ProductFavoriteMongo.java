/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: MerchantMongo.java
 * @Package com.froad.db.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月25日
 */

package com.froad.common.mongo;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.froad.db.chonggou.entity.MerchantOutletFavorite;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.StoreProductInfo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * <p>
 * Title: MerchantMongo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月25日 下午8:47:31
 */

public class ProductFavoriteMongo {
	
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = new MongoManager();
	
	private static Sort sort = new Sort("_id", OrderBy.ASC);
	
	//收藏上限
	private int countLimit = 10;

	/** 
	 * 获取用户
	* @Title: getMerchantOutletFavorites 
	* @Description: 
	* @author longyunbo
	* @param  id
	* @return MerchantOutletFavorite
	* @throws 
	*/
	public MerchantOutletFavorite findMerchantOutletFavoriteById(String id) {
		MerchantOutletFavorite merchantOutletFavorite = null;
		DBObject dbObj = new BasicDBObject();
		dbObj.put("_id", id);
		merchantOutletFavorite = mongo.findOne(dbObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		return merchantOutletFavorite;
	}
	
	
	/** 删除用户收藏信息
	* @Title: deleteMerchantOutletFavorite 
	* @Description: 
	* @author longyunbo
	* @param @param id
	* @param @return
	* @return int
	* @throws 
	*/
	public int deleteMerchantOutletFavorite(String id){
		int result = 0;
		DBObject dbObj = new BasicDBObject();
		dbObj.put("_id", id);
		result = mongo.remove(dbObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
		return result;
	}
	
	public int deleteMerchantOutletFavoriteByClientId(String clientId){
		int result = 0;
		DBObject dbObj = new BasicDBObject();
		Pattern pattern = Pattern.compile("^.*" + Constans.clientId+ ".*$", Pattern.CASE_INSENSITIVE);
		dbObj.put("_id", pattern);
		result = mongo.remove(dbObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
		return result;
	}
	
	
	/** 是否收藏商品
	* @Title: countProducts 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @return
	* @return int
	* @throws 
	*/
	public int isExitsStoreProductInfo(String clientId, long memberCode,String productId) {
		String id = clientId+"_"+memberCode;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		// 商品ID
		queryObj.put("store_product_info.product_id", productId);
		return mongo.getCount(queryObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
	}
	
	
	
	/** 收藏商品(数组)
	* @Title: addStoreProductInfos 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param storeProductInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public int addStoreProductInfos(String clientId, long memberCode,StoreProductInfo storeProductInfo){
		String id=clientId+"_"+memberCode;
		List<StoreProductInfo> stoList = new ArrayList<StoreProductInfo>();
		DBObject valueObj  = new BasicDBObject();
		stoList.add(storeProductInfo);
		valueObj.put("store_product_info", JSON.parse(JSonUtil.toJSonString(stoList)));
		valueObj.put("_id", id);
		return mongo.add(valueObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
	}
	
	
	/** 更新收藏商品(对象)
	* @Title: updateStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param storeProductInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public int updateStoreProductInfo (String clientId, long memberCode,StoreProductInfo storeProductInfo){
		String id=clientId+"_"+memberCode;
		// 条件
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id );
		DBObject valueObj  = new BasicDBObject();
		valueObj.put("store_product_info", JSON.parse(JSonUtil.toJSonString(storeProductInfo)));
		return mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$push");
	}
	
	/** 更新商品详情收藏总数
	* @Title: updateproductDetail 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @param storeCount
	* @param @return
	* @return int
	* @throws 
	*/
	public int updateproductDetail(String clientId, long memberCode,String productId ,int storeCount){
		//更新商户详情表中的总收藏数
		DBObject productDetailValue= new BasicDBObject();
		DBObject productetailWhere = new BasicDBObject();
		//更新门店详情收藏数
		productetailWhere.put("_id", productId);
		productDetailValue.put("store_count", storeCount);
		return	mongo.update(productDetailValue, productetailWhere, MongoTableName.CB_PRODUCT_DETAIL,"$set");
		
		
	}
	
	
	/** 
	 * 取消商品收藏
	* @Title: removeStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param productId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeStoreProductInfo(String clientId, long memberCode, String productId) {
		
		String id = clientId+"_"+memberCode;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		// 商品ID
		queryObj.put("store_product_info.product_id", productId);
		
		DBObject pullWhereObj = new BasicDBObject();
		pullWhereObj.put("store_product_info", new BasicDBObject("product_id",productId));
		return mongo.update(pullWhereObj, queryObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, "$pull");
		
	}
	
	
	/** 获取全部收藏商品
	* @Title: queryStoreProductInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<StoreProductInfo>
	* @throws 
	*/
	public List<StoreProductInfo> queryStoreProductInfo(String clientId, long memberCode){
		String id = clientId+"_"+memberCode;
		DBObject query = new BasicDBObject();
		query.put("_id", id);
		//获取整个收藏对象
		MerchantOutletFavorite merchantOutletFavorite =  mongo.findOne(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		//获取商品
		List<StoreProductInfo> storeProductInfoList = new ArrayList<StoreProductInfo>();
		if(merchantOutletFavorite!=null && merchantOutletFavorite.getStoreProductInfo()!=null){
			storeProductInfoList = merchantOutletFavorite.getStoreProductInfo();
		}
       return storeProductInfoList;
        
	}
	
	
	/** 添加商品收藏总数字段
	* @Title: addStoreOutletStoreCount 
	* @Description: 
	* @author longyunbo
	* @param @param productId
	* @param @return
	* @return int
	* @throws 
	*/
	public int addStoreOutletStoreCount(String productId){
		DBObject outletDetailValue= new BasicDBObject();
		DBObject outletDetailWhere = new BasicDBObject();
		outletDetailValue.put("store_count", 1);
		outletDetailWhere.put("_id", productId);
		return mongo.update(outletDetailValue, outletDetailWhere, MongoTableName.CB_PRODUCT_DETAIL,"$set");
	}
	
	
	
	/** 查询商品详情
	* @Title: findProductDetailByoutletId 
	* @Description: 
	* @author longyunbo
	* @param @param outletId
	* @param @return
	* @param @throws Exception
	* @return ProductDetail
	* @throws 
	*/
	public ProductDetail findProductDetailByoutletId (String productId) {
		MongoManager manager = new MongoManager();
		DBObject dbObj = new BasicDBObject();
		dbObj.put("_id", productId);
		return manager.findOne(dbObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
	}
	

	
}
