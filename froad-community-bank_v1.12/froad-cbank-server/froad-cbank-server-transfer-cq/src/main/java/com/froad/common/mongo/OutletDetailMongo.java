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
 * @Title: OutletDetailMongo.java
 * @Package com.froad.db.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年4月1日
 */

package com.froad.common.mongo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.CategoryInfo;
import com.froad.db.chonggou.entity.Location;
import com.froad.db.chonggou.entity.MerchantDetail;
import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.chonggou.entity.OutletDetail;
import com.froad.db.chonggou.entity.TypeInfo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.logback.LogCvt;
import com.froad.util.BeanUtil;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;


/**    
 * <p>Title: OutletDetailMongo.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月1日 上午11:54:15   
 */

public class OutletDetailMongo {
	
	private MongoManager manager = null;
	
	public OutletDetailMongo(MongoManager mongo){
		manager = mongo;
	}
	
	public boolean addOutletDetail(OutletCG outlet,MerchantDetail merchantDetail,AreaCG area,String defaultImage)  {
		OutletDetail outletDetail = new OutletDetail();
		
		
		outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outlet);
		outletDetail.setId(outlet.getOutletId());
		
		Location location = new Location();
		location.setLongitude(Double.parseDouble(outlet.getLongitude()));
		location.setLatitude(Double.parseDouble(outlet.getLatitude()));
		
		outletDetail.setLocation(location);
		if(area != null)
			outletDetail.setParentAreaId(area.getParentId());
		else
			outletDetail.setParentAreaId(null);
		
		if(merchantDetail != null){
			outletDetail.setMerchantName(merchantDetail.getMerchantName());
			outletDetail.setCategoryInfo(merchantDetail.getCategoryInfo());
			outletDetail.setTypeInfo(merchantDetail.getTypeInfo());
		}
		outletDetail.setDefaultImage(Constans.getThumbnail(defaultImage));
		
		outletDetail.setStoreCount(0); // 设置收藏人数为0

		outletDetail.setStarLevel(0); // 评论星级 初始为 0
		outletDetail.setOneLevelCount(0); // 1 星级 总数 初始为 0
		outletDetail.setTwoLevelCount(0); // 2 星级 总数 初始为 0
		outletDetail.setThreeLevelCount(0); // 3 星级 总数 初始为 0
		outletDetail.setFourLevelCount(0); // 4 星级 总数 初始为 0
		outletDetail.setFiveLevelCount(0); // 5 星级 总数 初始为 0
		
		LogCvt.debug(MongoTableName.CB_OUTLET_DETAIL + "添加 ------->" + JSON.toJSONString(outletDetail));

		return manager.add(outletDetail, MongoTableName.CB_OUTLET_DETAIL) > 0; // 向mongodb插入数据
	}
	
	public boolean deleteOutletDetail(String clientId) {
		
		DBObject where = new BasicDBObject();
		where.put("client_id", clientId);
		
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "删除clientId为" + clientId + "的数据------->" );
		int result = manager.remove(where, MongoTableName.CB_OUTLET_DETAIL);
		return result != -1;
	}
	
	
	/**
	 * 根据商户id修改分类信息
	 * @Title: updateOutletDetailCategoryInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @param categoryInfo
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean updateOutletDetailCategoryInfo(String merchantId, List<CategoryInfo> categoryInfo) {
		if(CollectionUtils.isNotEmpty(categoryInfo)) {	
			
			DBObject where = new BasicDBObject();
			where.put("merchant_id", merchantId);
	
			DBObject value = new BasicDBObject();
			value.put("category_info", (BasicDBList) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(categoryInfo)));
			
			LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "修改商户id为" + merchantId + "下的所有门店的商户分类信息为:" + value.toString());
			manager.update(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
//			manager.findAndModify(value, where, MongoTableName.CB_OUTLET_DETAIL, true, OutletDetail.class);
			return true;
		}
		return false;
	}

	/**
	 * 根据商户id修改类型信息
	 * @Title: updateOutletDetailTypeInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @param typeInfo
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean updateOutletDetailTypeInfo(String merchantId, List<TypeInfo> typeInfo) {
		if(CollectionUtils.isNotEmpty(typeInfo)) {			
			
			DBObject where = new BasicDBObject();
			where.put("merchant_id", merchantId);
	
			DBObject value = new BasicDBObject();
			value.put("type_info", (BasicDBList) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(typeInfo)));
			LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "修改商户id为" + merchantId + "下的所有门店的商户类型信息为:" + value.toString());
			manager.update(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
//			manager.findAndModify(value, where, MongoTableName.CB_OUTLET_DETAIL, true, OutletDetail.class);
			return true;
		}
		return false;
	}
	
	/**
	 * 修改门店默认图片
	 * @Title: updateOutletDetailDefaultImage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @param defaultImapge
	 * @return
	 * @throws Exception
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean updateOutletDetailDefaultImage(String outletId, String defaultImapge)  {
		if(StringUtils.isNotEmpty(defaultImapge)) {			
			
			DBObject where = new BasicDBObject();
			where.put("_id", outletId);
	
			DBObject value = new BasicDBObject();
			value.put("default_image", defaultImapge);
			manager.update(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
			LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "修改门店id为" + outletId + "默认图片为:" + defaultImapge.toString());
			return true;
		}
		return false;
	}
	
	public boolean updateOutletDetail(OutletCG outlet, MerchantDetail merchantDetail) throws Exception {
		OutletDetail outletDetail = null;
//		outletDetail = this.findOutletDetailByoutletId(outlet.getOutletId());
		
		try {
			outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outlet);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		outletDetail.setId(outlet.getOutletId());
//		if (StringUtils.isNotBlank(outlet.getOutletName())){
//			outletDetail.setOutletName(outlet.getOutletName());
//		}
//		if (StringUtils.isNotBlank(outlet.getOutletFullname())){
//			outletDetail.setOutletFullname(outlet.getOutletFullname());
//		}
//		if (StringUtils.isNotBlank(outlet.getAddress())){
//			outletDetail.setAddress(outlet.getAddress());
//		}
//		if (StringUtils.isNotBlank(outlet.getPhone())) {
//			outletDetail.setPhone(outlet.getPhone());
//		}
//		if (StringUtils.isNotBlank(outlet.getDescription())) {
//			outletDetail.setDescription(outlet.getDescription());
//		}
//		if (StringUtils.isNotBlank(outlet.getPreferDetails())) {
//			outletDetail.setPreferDetails(outlet.getPreferDetails());
//		}
		if (StringUtils.isNotBlank(outlet.getLongitude()) && StringUtils.isNotBlank(outlet.getLatitude())) {
			Location location = new Location();
			location.setLongitude(Double.parseDouble(outlet.getLongitude()));
			location.setLatitude(Double.parseDouble(outlet.getLatitude()));
			outletDetail.setLocation(location);
		}
//		outletDetail.setLocation(new Double[]{Double.parseDouble(outlet.getLongitude()), Double.parseDouble(outlet.getLatitude())});
		
		if(merchantDetail != null) {
			if (CollectionUtils.isNotEmpty(merchantDetail.getCategoryInfo()))
				outletDetail.setCategoryInfo(merchantDetail.getCategoryInfo());
			if (StringUtils.isNotBlank(merchantDetail.getMerchantName()))
				outletDetail.setMerchantName(merchantDetail.getMerchantName());
			if (CollectionUtils.isNotEmpty(merchantDetail.getTypeInfo()))
				outletDetail.setTypeInfo(merchantDetail.getTypeInfo());
		}
		
		DBObject where = new BasicDBObject();
		where.put("_id", outlet.getOutletId());
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "修改_id为" + outlet.getOutletId() + "的数据为--------->" + JSON.toJSONString(outletDetail));
//		manager.findAndModify((DBObject)com.mongodb.util.JSON.parse(JSonUtil.toJSonString(outletDetail)), where, MongoTableName.CB_OUTLET_DETAIL, false, MerchantDetail.class);
		manager.update((DBObject)com.mongodb.util.JSON.parse(JSonUtil.toJSonString(outletDetail)), where, MongoTableName.CB_OUTLET_DETAIL, "$set");
		return true;
	}
	
//	public MongoPage findOutletDetailByPage(MongoPage mongoPage, OutletDetail outletDetail)throws Exception {
//		
////		BasicDBObject where = (BasicDBObject) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(outletDetail));
//		BasicDBObject query = new BasicDBObject(); // 联合查询条件
//		if(outletDetail.getClientId() != null){
//			query.append("client_id", outletDetail.getClientId());
//		}
//		if(outletDetail.getMerchantId() != null){
//			query.append("merchant_id", outletDetail.getMerchantId());
//		}
//		if(StringUtils.isNotBlank(outletDetail.getMerchantName())) {
//			String merchantName = outletDetail.getMerchantName();
//			char[] cs = merchantName.toCharArray();
//			String regexStr = ".*";
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
//			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
//			BasicDBObject like = new BasicDBObject();
//			like.append("$regex", pattern);
//			
//			query .append("merchant_name", like);
//		}
//		if(outletDetail.getAreaId() != null){
//			query.append("area_id", outletDetail.getAreaId());
//		}
//		if (outletDetail.getIsEnable() != null) {
//			query.append("is_enable", outletDetail.getIsEnable());
//		}
//		if(StringUtils.isNotBlank(outletDetail.getOutletName())){
//			String outletName = outletDetail.getOutletName();
//			char[] cs = outletName.toCharArray();
//			String regexStr = ".*";
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
//			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
//			BasicDBObject like = new BasicDBObject();
//			like.append("$regex", pattern);
//			
//			query .append("outlet_name", like);
//		}
//		if(StringUtils.isNotBlank(outletDetail.getOutletFullname())){
//			String outletName = outletDetail.getOutletFullname();
//			char[] cs = outletName.toCharArray();
//			String regexStr = ".*";
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
//			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
//			BasicDBObject like = new BasicDBObject();
//			like.append("$regex", pattern);
//			
//			query .append("outlet_fullname", like);
//		}
//		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
//		if (CollectionUtils.isNotEmpty(categoryInfo)) {
//			Long[] categoryIdArr = new Long[categoryInfo.size()];
//			for (int i = 0; i < categoryInfo.size(); i++) {
//				CategoryInfo ci = categoryInfo.get(i);
//				if (ci != null && ci.getCategoryId() != null)
//					categoryIdArr[i] = ci.getCategoryId();
//			}
//			if (categoryIdArr.length > 0) {
//				BasicDBObject categorys = new BasicDBObject();
//				categorys.append("$in", categoryIdArr);
//				query.append("category_info.category_id", categorys);
//			}
//		}
//		List<TypeInfo> typeInfo = outletDetail.getTypeInfo();
//		if (CollectionUtils.isNotEmpty(typeInfo)) {
//			Long[] typeIdArr = new Long[typeInfo.size()];
//			for (int i = 0; i < typeInfo.size(); i++) {
//				TypeInfo ti = typeInfo.get(i);
//				if (ti != null && ti.getMerchantTypeId() != null)
//					typeIdArr[i] = ti.getMerchantTypeId();
//			}
//			if (typeIdArr.length > 0) {
//				BasicDBObject types = new BasicDBObject();
//				types.append("$in", typeIdArr);
//				query.append("type_info.merchant_type_id", types);
//			}
//		}
//		if(outletDetail.getOutletStatus() != null) {
//			query.append("outlet_status", outletDetail.getOutletStatus());
//		}
//		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "分页搜索门店详情查询条件:" + query.toString());
//		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "分页搜索门店详情查询mongoPage:" + JSON.toJSONString(mongoPage));
//		mongoPage = manager.findByPage(mongoPage, query, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
//		return mongoPage;
//	}
	
	/**
	 * 查询附近n米远的门店
	 * @Title: findNearbyOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param longitude
	 * @param latitude
	 * @param distace
	 * @return
	 * @return List<OutletDetail>    返回类型 
	 * @throws
	 */
	public MongoPage findNearbyOutlet(MongoPage mongoPage, OutletDetail outletDetail, double distace) throws Exception {
//		Location location = outletDetail.getLocation();
//		double longitude = location.getLongitude();
//		double latitude = location.getLatitude();
		
		
		
		BasicDBObject where = new BasicDBObject();
//		where.put("location", "{ $within : { $center : [[0.5, 0.5], 20], $uniqueDocs : true }");
//		where.put("location", "{ $near : [116.325986265656, 26.569789419], $maxDistance:10000, $uniqueDocs : true }");
//		where.put("location", "{ $within : {$center : [[116.325986265656, 26.569789419], 1000000]}}");
//		System.out.println(where);
//		System.out.println(JSON.toJSONString(manager.findAll(where, MongoTableName.CB_OUTLET_DETAIL, Map.class),true));
		
		
		List whereList = new ArrayList();
//		whereList.add(new double[] { longitude, latitude });
		whereList.add(outletDetail.getLocation());
		whereList.add((double) distace / 1000.0 / 111.0); // 半径以km为单位
		where.append("location", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", whereList)));
		if (outletDetail.getIsEnable() != null) {
			where.append("is_enable", outletDetail.getIsEnable());
		}
		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
		if (CollectionUtils.isNotEmpty(categoryInfo)) {
			where.append("category_info", categoryInfo);
		}
		List<TypeInfo> typeInfo = outletDetail.getTypeInfo();
		if (CollectionUtils.isNotEmpty(typeInfo)) {
			where.append("type_info", typeInfo);
		}
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "附近搜索门店查询条件:" + where.toString());
		mongoPage = manager.findByPage(mongoPage, where, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);

//		List<OutletDetail> list = (List<OutletDetail>) manager.findAll(where, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
//		return list;
		return mongoPage;
	}
	
	
	/**
	 * 查询附近n米远的门店
	 * @Title: findNearbyOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletDetail
	 * @param pageSize
	 * @param distace
	 * @param dis
	 * @return
	 * @return List<OutletDetail>    返回类型 
	 * @throws
	 */
//	public List<OutletDetail> findNearbyOutlet(OutletDetail outletDetail,int pageSize, double distace, double dis) throws Exception {
////		LogCvt.info("传入的查询条件:" + JSON.toJSONString(outletDetail, true));
//		List<OutletDetail> list = new ArrayList<OutletDetail>();
//		Location location = outletDetail.getLocation();
//		
//		
//		BasicDBObject myCmd = new BasicDBObject();
//		myCmd.append("geoNear", MongoTableName.CB_OUTLET_DETAIL);
//		double[] loc = { location.getLongitude(), location.getLatitude() };
//		myCmd.append("near", loc);
////		myCmd.append("near", outletDetail.getLocation());
//		myCmd.append("spherical", true); // 是否为球形
////		myCmd.append("maxDistance", (double) distace / 3959);
//		myCmd.put("distanceMultiplier", 6378137); // 指定球形半径（地球半径）
//		myCmd.put("maxDistace", (double) distace / 6378137); // 距离单位默认为米 [弧度（弧度=弧长/半径 一千米的弧度1000/6378000），]
////		myCmd.put("maxDistace",5.9196398572184);
//		
////		myCmd.append("maxDistace", (double) distace / 1000.0 / 111.0);
//		
////		myCmd.append("num", pageSize < 1 ? 10 : pageSize);
////		myCmd.append("limit", pageSize < 1 ? 10 : pageSize);
//		
//		
//		BasicDBObject query = new BasicDBObject(); // 联合查询条件
//		if(outletDetail.getClientId() != null){
//			query.append("client_id", outletDetail.getClientId());
//		}
//		if(outletDetail.getMerchantId() != null){
//			query.append("merchant_id", outletDetail.getMerchantId());
//		}
//		if(StringUtils.isNotBlank(outletDetail.getMerchantName())) {
//			String merchantName = outletDetail.getMerchantName();
//			char[] cs = merchantName.toCharArray();
//			String regexStr = ".*";
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
//			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
//			BasicDBObject like = new BasicDBObject();
//			like.append("$regex", pattern);
//			
//			query .append("merchant_name", like);
//		}
//		if(outletDetail.getAreaId() != null){
//			query.append("area_id", outletDetail.getAreaId());
//		}
//		if (outletDetail.getIsEnable() != null) {
//			query.append("is_enable", outletDetail.getIsEnable());
//		}
//		if(StringUtils.isNotBlank(outletDetail.getOutletName())){
//			String outletName = outletDetail.getOutletName();
//			char[] cs = outletName.toCharArray();
//			String regexStr = ".*";
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
//			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
//			BasicDBObject like = new BasicDBObject();
//			like.append("$regex", pattern);
//			
//			query .append("outlet_name", like);
//		}
//		if(StringUtils.isNotBlank(outletDetail.getOutletFullname())){
//			String outletName = outletDetail.getOutletFullname();
//			char[] cs = outletName.toCharArray();
//			String regexStr = ".*";
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
//			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
////			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
//			BasicDBObject like = new BasicDBObject();
//			like.append("$regex", pattern);
//			
//			query .append("outlet_fullname", like);
//		}
//		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
//		if (CollectionUtils.isNotEmpty(categoryInfo)) {
//			Long[] categoryIdArr = new Long[categoryInfo.size()];
//			for (int i = 0; i < categoryInfo.size(); i++) {
//				CategoryInfo ci = categoryInfo.get(i);
//				if (ci != null && ci.getCategoryId() != null)
//					categoryIdArr[i] = ci.getCategoryId();
//			}
//			if (categoryIdArr.length > 0) {
//				BasicDBObject categorys = new BasicDBObject();
//				categorys.append("$in", categoryIdArr);
//				query.append("category_info.category_id", categorys);
//			}
//		}
//		List<TypeInfo> typeInfo = outletDetail.getTypeInfo();
//		if (CollectionUtils.isNotEmpty(typeInfo)) {
//			Long[] typeIdArr = new Long[typeInfo.size()];
//			for (int i = 0; i < typeInfo.size(); i++) {
//				TypeInfo ti = typeInfo.get(i);
//				if (ti != null && ti.getMerchantTypeId() != null)
//					typeIdArr[i] = ti.getMerchantTypeId();
//			}
//			if (typeIdArr.length > 0) {
//				BasicDBObject types = new BasicDBObject();
//				types.append("$in", typeIdArr);
//				query.append("type_info.merchant_type_id", types);
//			}
//		}
//		if(outletDetail.getOutletStatus() != null) {
//			query.append("outlet_status", outletDetail.getOutletStatus());
//		}
//		if(!query.isEmpty()){
//			myCmd.put("query", query); // 
//		}
//		
//		
////		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "附近搜索门店查询条件:" + myCmd.toString());
//		
//		
//		
//		CommandResult myResults = manager.command(myCmd); 
//		
////		System.out.println(MongoTableName.CB_OUTLET_DETAIL + "返回的CommandResult:" + myResults.toString());
//		BasicDBList results = (BasicDBList) myResults.get("results");
//		if(null != results)
//		for (Iterator<Object> it = results.iterator(); it.hasNext();) {
//			BasicDBObject result = (BasicDBObject) it.next();
//			// BasicDBObject dbo = (BasicDBObject) result.get("obj");
//			double outletDis = Math.floor(result.getDouble("dis")); // 向下取整
//			if (list.size() < pageSize && outletDis > dis && outletDis <= distace) {
//				LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "符合的结果:" + result.toString());
//				BasicDBObject queryResult = (BasicDBObject)result.get("obj");
//				String jsonStr = com.mongodb.util.JSON.serialize(queryResult);
//	   			
//   				//Convert JSON string to entity
//				OutletDetail entity = JSonUtil.toObject(jsonStr, OutletDetail.class);
//				entity.setDis(outletDis); // 设置距离
//				list.add(entity);
//			}
//
//		}
//		
////		List<OutletDetail> list = (List<OutletDetail>) manager.findAll(where, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
////		return list;
//		return list;
//	}
	
	/**
	 * 查询outlet详情
	 * @Title: findMerchantDetailById 
	 * @author zxh
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @return
	 * @return OutletDetail    返回类型 
	 * @throws
	 */
	public OutletDetail findOutletDetailByoutletId (String outletId) {
		
		DBObject dbObj = new BasicDBObject();
		dbObj.put("_id", outletId);
		return manager.findOne(dbObj, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
	}
	
	/**
	 * 更新门店评价星级
	 * 
	 * 1 - 累计值
	 * 2 - 计算评价星级(小数点一位四省五入取整)
	 * 3 - 更新
	 * 
	 * @Title: updateOutletStarLevelByOutletId 
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @param starLevel
	 * 
	 * @return boolean
	 * */
	public boolean updateOutletStarLevelByOutletId(String outletId, int starLevel) {
		
		boolean result = false;
		
		if( outletId != null && !"".equals(outletId) && starLevel > 0 ){
			
			// 根据 门店id 查询 门店详情
			OutletDetail outletDetail = this.findOutletDetailByoutletId(outletId);
			if( outletDetail == null ){
				return result;
			}
			
			// 得到数据进行累加和计算
			Integer one = outletDetail.getOneLevelCount();
			Integer two = outletDetail.getTwoLevelCount();
			Integer three = outletDetail.getThreeLevelCount();
			Integer four = outletDetail.getFourLevelCount();
			Integer five = outletDetail.getFiveLevelCount();
			Integer level = outletDetail.getStarLevel();
			switch (starLevel){
			case 1:
				one++;
				outletDetail.setOneLevelCount(one);
				break;
			case 2:
				two++;
				outletDetail.setTwoLevelCount(two);
				break;
			case 3:
				three++;
				outletDetail.setThreeLevelCount(three);
				break;
			case 4:
				four++;
				outletDetail.setFourLevelCount(four);
				break;
			case 5:
				five++;
				outletDetail.setFiveLevelCount(five);
				break;
			default:;
			}
			// 小数点一位四省五入取整
			double average = Double.valueOf(one+two*2+three*3+four*4+five*5)/(one+two+three+four+five);
			level = new BigDecimal(average).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			outletDetail.setStarLevel(level);
			
			// 更新门店评价星级
			result = updateOutletStarLevel(outletDetail);
			
		}
		
		return result;
	}
	
	private static final String MONGO_KEY_ID = "_id";
	private static final String MONGO_KEY_MERCHANT_ID = "merchant_id";
	private static final String MONGO_KEY_STAR_LEVEL = "star_level";
	private static final String MONGO_KEY_ONE_LEVEL_COUNT = "one_level_count";
	private static final String MONGO_KEY_TWO_LEVEL_COUNT = "two_level_count";
	private static final String MONGO_KEY_THREE_LEVEL_COUNT = "three_level_count";
	private static final String MONGO_KEY_FOUR_LEVEL_COUNT = "four_level_count";
	private static final String MONGO_KEY_FIVE_LEVEL_COUNT = "five_level_count";
	private static final String COMMAND_SET = "$set";
	// 更新门店评价星级
	private boolean updateOutletStarLevel(OutletDetail outletDetail){
		try{
			
			DBObject where = new BasicDBObject();
			where.put(MONGO_KEY_ID, outletDetail.getId());
	
			DBObject value = new BasicDBObject();
			value.put(MONGO_KEY_STAR_LEVEL, outletDetail.getStarLevel());
			value.put(MONGO_KEY_ONE_LEVEL_COUNT, outletDetail.getOneLevelCount());
			value.put(MONGO_KEY_TWO_LEVEL_COUNT, outletDetail.getTwoLevelCount());
			value.put(MONGO_KEY_THREE_LEVEL_COUNT, outletDetail.getThreeLevelCount());
			value.put(MONGO_KEY_FOUR_LEVEL_COUNT, outletDetail.getFourLevelCount());
			value.put(MONGO_KEY_FIVE_LEVEL_COUNT, outletDetail.getFiveLevelCount());
			
			return manager.update(value, where, MongoTableName.CB_OUTLET_DETAIL, COMMAND_SET) > 0;
		}catch(Exception e){
			LogCvt.error("更新门店评价星级失败，原因:" + e.getMessage(), e); 
			return false;
		}
	}
	
	/**
	 * 查询门店评价信息列表
	 * 
	 * @Title: findOutletDetailListByMerchantId 
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * 
	 * @return List<OutletDetail>
	 * */
	public List<OutletDetail> findOutletDetailListByMerchantId(String merchantId){
		
		List<OutletDetail> result = null;
		
		try{
			
			DBObject dbObject = new BasicDBObject();
			dbObject.put(MONGO_KEY_MERCHANT_ID, merchantId);
			result = (List<OutletDetail>)manager.findAll(dbObject, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
		}catch(Exception e){
			LogCvt.error("查询门店评价信息列表失败，原因:" + e.getMessage(), e); 
			result = null;
		}
		return result;
	}
	
	/**
	 * 根据门店id集合查询详情
	 * @Title: findOutletDetailbyOutletIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletIdList
	 * @return
	 * @return List<OutletDetail>    返回类型 
	 * @throws
	 */
	public List<OutletDetail> findOutletDetailbyOutletIdList(List<String> outletIdList){
		List<OutletDetail> result = null;
		
		try{
			
			DBObject dbObject = new BasicDBObject();
//			dbObject.put(MONGO_KEY_MERCHANT_ID, merchantId);
			
			BasicDBObject categorys = new BasicDBObject();
			categorys.append(QueryOperators.IN, outletIdList.toArray(new String[]{}));
			
			dbObject.put(MONGO_KEY_ID, categorys);
			
			
//			BasicDBObject categorys = new BasicDBObject();
//			categorys.append(QueryOperators.IN, categoryIdArr);			
//			dbObject.put(MONGO_KEY_CATEGORY_INFO+"."+MONGO_KEY_CATEGORY_ID, categoryIdArr);
			
			LogCvt.info("根据门店id集合查询详情条件------>"+ dbObject.toString());
			result = (List<OutletDetail>)manager.findAll(dbObject, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
		}catch(Exception e){
			LogCvt.error("根据门店id集合查询详情失败，原因:" + e.getMessage(), e); 
			result = null;
		}
		return result;
	}
}
