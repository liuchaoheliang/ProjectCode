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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.db.chonggou.entity.DeliverInfo;
import com.froad.db.chonggou.entity.MerchantOutletFavorite;
import com.froad.db.chonggou.entity.RecvInfo;
import com.froad.db.chonggou.entity.StoreOutletInfo;
import com.froad.db.chonggou.entity.StoreProductInfo;
import com.froad.db.mongo.impl.CursorHandleImpl;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
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

public class MerchantOutletFavoriteMongo {
	
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = null;
	
	public MerchantOutletFavoriteMongo(MongoManager mongos){
		mongo = mongos;
	}

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
		// TODO: handle exception
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
		// TODO Auto-generated method stub
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
		return	mongo.update(productDetailValue, productetailWhere, MongoTableName.CB_OUTLET_DETAIL,"$set");
		
		
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
		List<StoreProductInfo> storeProductInfoList = merchantOutletFavorite.getStoreProductInfo();
       return storeProductInfoList;
        
	}
	
	
	/** 是否收藏门店
	* @Title: countStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param merchantId
	* @param @return
	* @return int
	* @throws 
	*/
	public int isExitsStoreOutletInfo(String clientId, long memberCode,String outletId) {
		// TODO Auto-generated method stub
		String id = clientId+"_"+memberCode;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		// 门店ID
		queryObj.put("store_outlet_info.outlet_id", outletId);
		return mongo.getCount(queryObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
	}
	
	
	/** 增加门店详情收藏总数
	* @Title: addStoreOutletStoreCount 
	* @Description: 
	* @author longyunbo
	* @param @param outletId
	* @param @return
	* @return int
	* @throws 
	*/
	public int addStoreOutletStoreCount(String outletId){
		DBObject outletDetailValue= new BasicDBObject();
		DBObject outletDetailWhere = new BasicDBObject();
		outletDetailValue.put("store_count", 1);
		outletDetailWhere.put("_id", outletId);
		return mongo.update(outletDetailValue, outletDetailWhere, MongoTableName.CB_OUTLET_DETAIL,"$set");
	}
	
	
	/** 增加门店收藏(数组)
	* @Title: addStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param merchantId
	* @param @param storeOutletInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public int addStoreOutletInfos(String clientId, long memberCode,StoreOutletInfo storeOutletInfo){
		String id=clientId+"_"+memberCode;
		List<StoreOutletInfo> stoList = new ArrayList<StoreOutletInfo>();
		DBObject valueObj  = new BasicDBObject();
		stoList.add(storeOutletInfo);
		valueObj.put("store_outlet_info", JSON.parse(JSonUtil.toJSonString(stoList)));
		valueObj.put("_id", id);
		return mongo.add(valueObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
		
		
	}
	
	
	/** 增加门店收藏(对象)
	* @Title: updateStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param storeOutletInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public int updateStoreOutletInfo (String clientId, long memberCode,StoreOutletInfo storeOutletInfo){
		String id=clientId+"_"+memberCode;
		// 条件
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id );
		DBObject valueObj  = new BasicDBObject();
		valueObj.put("store_outlet_info", JSON.parse(JSonUtil.toJSonString(storeOutletInfo)));
		return mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$push");
	}

	/** 更新门店详情收藏总数
	* @Title: updateoutletDetail 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param outletId
	* @param @param storeCount
	* @param @return
	* @return int
	* @throws 
	*/
	public int updateoutletDetail(String clientId, long memberCode,String outletId ,int storeCount){
		//更新商户详情表中的总收藏数
		DBObject outletDetailValue= new BasicDBObject();
		DBObject outletDetailWhere = new BasicDBObject();
		//更新门店详情收藏数
		outletDetailWhere.put("_id", outletId);
		outletDetailValue.put("store_count", storeCount);
		return	mongo.update(outletDetailValue, outletDetailWhere, MongoTableName.CB_OUTLET_DETAIL,"$set");
		
		
	}
	
	
	
	/** 取消门店收藏
	* @Title: removeStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param merchantId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeStoreOutletInfo(String clientId, long memberCode, String outletId) {
		String id = clientId+"_"+memberCode;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		// 商品ID
		queryObj.put("store_outlet_info.outlet_id", outletId);
		
		DBObject pullWhereObj = new BasicDBObject();
		pullWhereObj.put("store_outlet_info", new BasicDBObject("outlet_id",outletId));
		return mongo.update(pullWhereObj, queryObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, "$pull");
		
	}
	

	/** 查询全部门店
	* @Title: queryStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<StoreOutletInfo>
	* @throws 
	*/
	public List<StoreOutletInfo> queryStoreOutletInfo(String clientId, long memberCode){
		String id = clientId+"_"+memberCode;
		DBObject query = new BasicDBObject();
		query.put("_id", id);
		//获取整个收藏对象
		MerchantOutletFavorite merchantOutletFavorite =  mongo.findOne(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		//获取商品
		List<StoreOutletInfo> storeOutletInfoList = new ArrayList<StoreOutletInfo>();
		if(merchantOutletFavorite !=null && merchantOutletFavorite.getStoreOutletInfo()!= null)
		{
			storeOutletInfoList=merchantOutletFavorite.getStoreOutletInfo();
		}
       return storeOutletInfoList;
        
	}
	
	/** 获取用户商品、门店收藏数
	* @Title: countStoreOutletInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return int
	* @throws 
	*/
	public Map<String,Integer> countProductStoreOutletInfo(String clientId, long memberCode){
		String id = clientId+"_"+memberCode;
		DBObject query = new BasicDBObject();
		Map<String,Integer> map = new HashMap<String,Integer>();
		query.put("_id", id);
		//获取整个收藏对象
		MerchantOutletFavorite merchantOutletFavorite =  mongo.findOne(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		if(merchantOutletFavorite == null){
			return map;
		}
		//商品
		List<StoreProductInfo> storeProductInfoList = merchantOutletFavorite.getStoreProductInfo();
		//门店 
		List<StoreOutletInfo> storeOutletInfoList = merchantOutletFavorite.getStoreOutletInfo();
		if(storeProductInfoList!=null){
			map.put("productcount", storeProductInfoList.size());
		}else{
			map.put("productcount", 0);
		}
		if(storeOutletInfoList!=null){
			map.put("outletcount", storeOutletInfoList.size());
		}else{
			map.put("outletcount", 0);
		}
        return map;
        
	}
	
	
	/** 收货信息是否存在
	* @Title: isExitsRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param recvId
	* @param @return
	* @return int
	* @throws 
	*/
	public int isExitsRecvInfo(String clientId, long memberCode,String recvId) {
		String id = clientId+"_"+memberCode;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		// 商品ID
		queryObj.put("recv_info.recv_id", recvId);
		return mongo.getCount(queryObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
	}
	
	
	/** 追加收货信息
	* @Title: removeRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param recvId
	* @param @return
	* @return int
	* @throws 
	*/
	public int appedRecvInfo(String clientId, long memberCode, RecvInfo recvInfo) {
		DBObject valueObj =null;
		int result = 0;
		String id=clientId+"_"+memberCode;
		valueObj = new BasicDBObject();
		// 条件
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id );
		valueObj.put("recv_info", JSON.parse(JSonUtil.toJSonString(recvInfo)));
		result = mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$push");
		return result;
	}
	/**
	 * 第一次新增加
	 * @param clientId
	 * @param memberCode
	 * @param recvId
	 * @param stoList
	 * @return
	 */
	public int addRecvInfo2(String clientId, long memberCode,List<RecvInfo> stoList ) {
		DBObject valueObj =null;
		int result = 0;
		String id=clientId+"_"+memberCode;
		valueObj = new BasicDBObject();
		valueObj.put("recv_info", JSON.parse(JSonUtil.toJSonString(stoList)));
		valueObj.put("_id", id);
		result = mongo.add(valueObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
		return result;
	}
	
	
	
	/** 删除收货信息
	* @Title: removeRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param recvId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeRecvInfo(String clientId, long memberCode, String recvId) {
		
		String id = clientId+"_"+memberCode;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		// 收货信息ID
		queryObj.put("recv_info.recv_id", recvId);
		
		DBObject pullWhereObj = new BasicDBObject();
		pullWhereObj.put("recv_info", new BasicDBObject("recv_id",recvId));
		return mongo.update(pullWhereObj, queryObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, "$pull");
		
	}
	
	/**
	 * 修改默认收货地址
	 * @param clientId
	 * @param memberCode
	 * @param truedefault
	 * @param falseDefalut
	 * @return
	 */
	public int updateDefaultRecvInfo(String clientId, long memberCode,RecvInfo recvInfo) {
		int result = -1;
		String id = clientId+"_"+memberCode;
		DBObject valueObj = new BasicDBObject();
		if(recvInfo.getAddress()!=null && !"".equals(recvInfo.getAddress()))
		{
			valueObj.put("recv_info.$.address", recvInfo.getAddress());
		}
		if(recvInfo.getConsignee()!=null && !"".equals(recvInfo.getConsignee()))
		{
			valueObj.put("recv_info.$.consignee", recvInfo.getConsignee());
		}
		if(recvInfo.getIsDefault()!=null && !"".equals(recvInfo.getIsDefault()))
		{
			valueObj.put("recv_info.$.isdefault", recvInfo.getIsDefault());
		}
		if(recvInfo.getPhone()!=null && !"".equals(recvInfo.getPhone()))
		{
			valueObj.put("recv_info.$.phone", recvInfo.getPhone());
		}
		if(recvInfo.getAreaId()!=null && !"".equals(recvInfo.getAreaId()))
		{
			valueObj.put("recv_info.$.area_id", recvInfo.getAreaId());
		}
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id);
//		whereObj.put("recv_info.recv_id",recvInfo.getRecvId());
		DBObject dbo = new BasicDBObject();
		dbo.put("$elemMatch", new BasicDBObject("recv_id",recvInfo.getRecvId()));
		whereObj.put("recv_info",dbo);
		result= mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$set");
		
		return result;
	}
	
	/**
	 * 修改默认收货地址
	 * @param clientId
	 * @param memberCode
	 * @param truedefault
	 * @param falseDefalut
	 * @return
	 */
	public int updateDefaultRecvInfo(String clientId, long memberCode,String truedefault,String falseDefalut) {
		int result = -1;
		String id = clientId+"_"+memberCode;
		DBObject valueObj = new BasicDBObject();
		valueObj.put("recv_info.$.isdefault", truedefault);
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id);
		DBObject dbo = new BasicDBObject();
		dbo.put("$elemMatch", new BasicDBObject("isdefault",falseDefalut));
		whereObj.put("recv_info",dbo);
		result= mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$set");
		
		return result;
		
	}

	
	/** 查询收货信息
	* @Title: queryRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<RecvInfo>
	* @throws 
	*/
	public List<RecvInfo> queryRecvInfo(String clientId, long memberCode){
		String id = clientId+"_"+memberCode;
		DBObject query = new BasicDBObject();
		query.put("_id", id);
		//获取整个收藏对象
		MerchantOutletFavorite merchantOutletFavorite =  mongo.findOne(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		List<RecvInfo> recvInfoList =new ArrayList<RecvInfo>();
		if(merchantOutletFavorite !=null && merchantOutletFavorite.getRecvInfo()!= null)
		{
			recvInfoList=merchantOutletFavorite.getRecvInfo();
		}
       return recvInfoList;
        
	}
	
	
	/** 查询默认收货信息
	* @Title: queryDefaultRecvInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param isDefault
	* @param @return
	* @return List<RecvInfo>
	* @throws 
	*/
	public List<RecvInfo> queryDefaultRecvInfo(String clientId, long memberCode,String isDefault){
		String id = clientId+"_"+memberCode;
		DBObject whereObj = new BasicDBObject();
		List<RecvInfo> recvInfoList = new ArrayList<RecvInfo>();
		RecvInfo rec = null;
		List<DBObject> pipeLine = new ArrayList<DBObject>();
		whereObj.put("_id", id);
//		whereObj.put("recv_info.isdefault",isDefault);
		pipeLine.add(new BasicDBObject("$match", whereObj));
	    pipeLine.add(new BasicDBObject("$unwind", "$recv_info"));
	    
	    DBObject query2 = new BasicDBObject();
	 	query2.put("recv_info.isdefault", isDefault);
	    pipeLine.add(new BasicDBObject("$match", query2));
	    
	    Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_MERCHANT_OUTLET_FAVORITE).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
        	String json = JSonUtil.toJSonString(dbObj.get("recv_info"));
       		rec = JSonUtil.toObject(json, RecvInfo.class);
       	    recvInfoList.add(rec);
        }
		//获取整个收藏对象
//		MerchantOutletFavorite merchantOutletFavorite =  mongo.findOne(whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		//获取商品
//		List<RecvInfo> recvInfoList = merchantOutletFavorite.getRecvInfo();
       return recvInfoList;
        
	}
	
	/** 查询收货信息
	* @Title: queryRecvInfoById 
	* @Description: 
	* @author lf
	* @param recvId
	* @return RecvInfo
	* @throws 
	*/
	public RecvInfo queryRecvInfoById(String clientId, long memberCode,String recvId){
		RecvInfo result = null;
		String id = clientId+"_"+memberCode;
		DBObject query = new BasicDBObject();
		query.put("_id", id);
		//获取整个收藏对象
		MerchantOutletFavorite merchantOutletFavorite =  mongo.findOne(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		if( merchantOutletFavorite != null ){
			List<RecvInfo> recvInfoList = merchantOutletFavorite.getRecvInfo();
			if( recvInfoList != null && recvInfoList.size() > 0 )
			for( RecvInfo recv : recvInfoList ){
				if( recvId.equals(recv.getRecvId()) ){
					result = recv;
				}
			}
		}
		return result;
	}
	
	/** 查询提货信息是否存在
	* @Title: isExitsDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliveryId
	* @param @return
	* @return int
	* @throws 
	*/
	public int isExitsDeliverInfo(String clientId, long memberCode,String deliveryId) {
		// TODO Auto-generated method stub
		String id = clientId+"_"+memberCode;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		// 商品ID
		queryObj.put("delivery_info.delivery_id", deliveryId);
		return mongo.getCount(queryObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
	}
	
	
	/** 追加提货人信息
	* @Title: appedDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliverInfo
	* @param @return
	* @return int	
	* @throws 
	*/
	public int appedDeliverInfo(String clientId, long memberCode,DeliverInfo deliverInfo) {
		DBObject valueObj =null;
		int result = 0;
		String id=clientId+"_"+memberCode;
		valueObj = new BasicDBObject();
		// 条件
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id );
		valueObj.put("delivery_info", JSON.parse(JSonUtil.toJSonString(deliverInfo)));
		result = mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$push");
		return result;
	}
	
	
	/** 第一次添加提货人信息
	* @Title: addDeliverInfo2 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param stoList
	* @param @return
	* @return int
	* @throws 
	*/
	public int addDeliverInfo2(String clientId, long memberCode,List<DeliverInfo> stoList) {
		DBObject valueObj =null;
		int result = 0;
		String id=clientId+"_"+memberCode;
		valueObj = new BasicDBObject();
		valueObj.put("delivery_info", JSON.parse(JSonUtil.toJSonString(stoList)));
		valueObj.put("_id", id);
		result = mongo.add(valueObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE);
		return result;
	}
	
	
	/** 删除提货信息
	* @Title: removeDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliveryId
	* @param @return
	* @return int
	* @throws 
	*/
	public int removeDeliverInfo(String clientId, long memberCode, String deliveryId) {
		
		String id = clientId+"_"+memberCode;
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", id);
		// 提货信息ID
		queryObj.put("delivery_info.delivery_id", deliveryId);
		
		DBObject pullWhereObj = new BasicDBObject();
		pullWhereObj.put("delivery_info", new BasicDBObject("delivery_id",deliveryId));
		return mongo.update(pullWhereObj, queryObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, "$pull");
		
	}
	
	/** 更新默认提货信息
	* @Title: updateDefaultDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliverInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public int updateDefaultDeliverInfo(String clientId, long memberCode,DeliverInfo deliverInfo){
		int result = -1;
		String id = clientId+"_"+memberCode;
		DBObject valueObj = new BasicDBObject();
		if(deliverInfo.getConsignee()!=null && !"".equals(deliverInfo.getConsignee()))
		{
			valueObj.put("delivery_info.$.consignee", deliverInfo.getConsignee());
		}
		if(deliverInfo.getIsDefault()!=null && !"".equals(deliverInfo.getIsDefault()))
		{
			valueObj.put("delivery_info.$.isdefault", deliverInfo.getIsDefault());
		}
		if(deliverInfo.getPhone()!=null && !"".equals(deliverInfo.getPhone()))
		{
			valueObj.put("delivery_info.$.phone", deliverInfo.getPhone());
		}
		if(deliverInfo.getAreaId()!=null && !"".equals(deliverInfo.getAreaId()))
		{
			valueObj.put("delivery_info.$.area_id", deliverInfo.getAreaId());
		}
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id);
//		whereObj.put("recv_info.recv_id",recvInfo.getRecvId());
		DBObject dbo = new BasicDBObject();
		dbo.put("$elemMatch", new BasicDBObject("delivery_id",deliverInfo.getDeliveryId()));
		whereObj.put("delivery_info",dbo);
		result= mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$set");
		
		return result;
	}
	
	
	/** 修改提货信息
	* @Title: updateDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliverInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public int updateDeliverInfo(String clientId, long memberCode,DeliverInfo deliverInfo) {
		
		String id = clientId+"_"+memberCode;
		DBObject valueObj = new BasicDBObject();
		if(deliverInfo.getConsignee()!=null && !"".equals(deliverInfo.getConsignee()))
		{
			valueObj.put("delivery_info.$.consignee", deliverInfo.getConsignee());
		}
		if(deliverInfo.getIsDefault()!=null && !"".equals(deliverInfo.getIsDefault()))
		{
			valueObj.put("delivery_info.$.isdefault", deliverInfo.getIsDefault());
		}
		if(deliverInfo.getPhone()!=null && !"".equals(deliverInfo.getPhone()))
		{
			valueObj.put("delivery_info.$.phone", deliverInfo.getPhone());
		}
		if(deliverInfo.getAreaId()!=null && !"".equals(deliverInfo.getAreaId()))
		{
			valueObj.put("delivery_info.$.area_id", deliverInfo.getAreaId());
		}
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id);
		DBObject dbo = new BasicDBObject();
		dbo.put("$elemMatch", new BasicDBObject("delivery_id",deliverInfo.getDeliveryId()));
		whereObj.put("delivery_info",dbo);
		return mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$set");
	}
	
	/**
	 * 修改默认提货地址
	 * @param clientId
	 * @param memberCode
	 * @param truedefault
	 * @param falseDefalut
	 * @return
	 */
	public int updateDefaultDeliverInfo(String clientId, long memberCode,String truedefault,String falseDefalut) {
		int result = -1;
		String id = clientId+"_"+memberCode;
		DBObject valueObj = new BasicDBObject();
		valueObj.put("delivery_info.$.isdefault", truedefault);
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id",id);
		DBObject dbo = new BasicDBObject();
		dbo.put("$elemMatch", new BasicDBObject("isdefault",falseDefalut));
		whereObj.put("delivery_info",dbo);
		result= mongo.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE,"$set");
		
		
		return result;
		
	}
	
	
	/** 查询提货信息
	* @Title: queryDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @return
	* @return List<DeliverInfo>
	* @throws 
	*/
	public List<DeliverInfo> queryDeliverInfo(String clientId, long memberCode){
		String id = clientId+"_"+memberCode;
		DBObject query = new BasicDBObject();
		query.put("_id", id);
		//获取整个收藏对象
		MerchantOutletFavorite merchantOutletFavorite =  mongo.findOne(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		//获取商品
		List<DeliverInfo> deliverInfoList = new ArrayList<DeliverInfo>();
		if(merchantOutletFavorite !=null && merchantOutletFavorite.getDeliverInfo() != null)
		{
			deliverInfoList=merchantOutletFavorite.getDeliverInfo();
		}
       return deliverInfoList;
        
	}
	
	
	/** 查询默认提货信息
	* @Title: queryDefaultDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param isDefault
	* @param @return
	* @return List<DeliverInfo>
	* @throws 
	*/
	public List<DeliverInfo> queryDefaultDeliverInfo(String clientId, long memberCode,String isDefault){
		String id = clientId+"_"+memberCode;
		DBObject whereObj = new BasicDBObject();
		List<DeliverInfo> deliverInfoList = new ArrayList<DeliverInfo>();
		DeliverInfo del = null;
		List<DBObject> pipeLine = new ArrayList<DBObject>();
		whereObj.put("_id", id);
		pipeLine.add(new BasicDBObject("$match", whereObj));
	    pipeLine.add(new BasicDBObject("$unwind", "$delivery_info"));
	    
	    DBObject query2 = new BasicDBObject();
	 	query2.put("delivery_info.isdefault", isDefault);
	    pipeLine.add(new BasicDBObject("$match", query2));
	    
	    Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_MERCHANT_OUTLET_FAVORITE).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
        	String json = JSonUtil.toJSonString(dbObj.get("delivery_info"));
       		del = JSonUtil.toObject(json, DeliverInfo.class);
       	   deliverInfoList.add(del);
        }
      
       return deliverInfoList;
	}

	
}
