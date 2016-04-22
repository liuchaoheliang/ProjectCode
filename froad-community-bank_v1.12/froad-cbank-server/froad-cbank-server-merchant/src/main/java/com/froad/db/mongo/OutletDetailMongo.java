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

package com.froad.db.mongo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantCategoryLogic;
import com.froad.logic.impl.MerchantCategoryLogicImpl;
import com.froad.po.MerchantCategory;
import com.froad.po.Outlet;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.Location;
//import com.froad.po.mongo.Location;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.OutletDetailSimple;
import com.froad.po.mongo.OutletDetailSimpleInfo;
import com.froad.po.mongo.OutletMongoInfo;
import com.froad.po.mongo.TypeInfo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
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
	private MongoManager manager = new MongoManager();
	
	public boolean addOutletDetail(Outlet outlet,MerchantDetail merchantDetail) throws Exception {
		OutletDetail outletDetail = new OutletDetail();
		
//		
		try {
//			BeanUtils.copyProperties(outletDetail, outlet);
			outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outlet);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		outletDetail.setId(outlet.getOutletId());
		
		Location location = new Location();
		location.setLongitude(Double.parseDouble(outlet.getLongitude()));
		location.setLatitude(Double.parseDouble(outlet.getLatitude()));
		
		outletDetail.setLocation(location);
		
		outletDetail.setMerchantName(merchantDetail.getMerchantName());
		outletDetail.setCategoryInfo(merchantDetail.getCategoryInfo());
		outletDetail.setTypeInfo(merchantDetail.getTypeInfo());
	
		if(StringUtils.isNotBlank(outlet.getDiscountCode())){
			outletDetail.setDiscountCode(outlet.getDiscountCode());
		}
		if(StringUtils.isNotBlank(outlet.getDiscountRate())){
			outletDetail.setDiscountRate(outlet.getDiscountRate());
		}
		
		outletDetail.setStoreCount(0); // 设置收藏人数为0

		outletDetail.setStarLevel(0); // 评论星级 初始为 0
		outletDetail.setOneLevelCount(0); // 1 星级 总数 初始为 0
		outletDetail.setTwoLevelCount(0); // 2 星级 总数 初始为 0
		outletDetail.setThreeLevelCount(0); // 3 星级 总数 初始为 0
		outletDetail.setFourLevelCount(0); // 4 星级 总数 初始为 0
		outletDetail.setFiveLevelCount(0); // 5 星级 总数 初始为 0
		
//		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "添加 ------->" + JSON.toJSONString(outletDetail));
//		return manager.add(outletDetail, MongoTableName.CB_OUTLET_DETAIL) > 0; // 向mongodb插入数据
		return this.addOutletDetail(outletDetail); // 向mongodb插入数据
	}
	
	public boolean addOutletDetail(OutletDetail outletDetail) throws Exception {
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "添加 ------->" + JSON.toJSONString(outletDetail));
		return manager.add(outletDetail, MongoTableName.CB_OUTLET_DETAIL) > 0; // 向mongodb插入数据
	}
	
	public boolean deleteOutletDetail(String outletId) throws Exception {
		
		DBObject where = new BasicDBObject();
		where.put("_id", outletId);
		
		DBObject value = new BasicDBObject();
		value.put("is_enable", false);
		
//		manager.findAndModify(value, where, MongoTableName.CB_OUTLET_DETAIL, false, OutletDetail.class);
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "删除outletId为" + outletId + "的数据------->" + JSON.toJSONString(value));
		manager.updateMulti(value, where,  MongoTableName.CB_OUTLET_DETAIL, "$set");
		return true;
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
			manager.updateMulti(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
//			manager.findAndModify(value, where, MongoTableName.CB_OUTLET_DETAIL, true, OutletDetail.class);
			return true;
		}
		return false;
	}
	
	
	/**
	 * 根据商户id修改门店中商户名称.
	 * @param merchantId
	 * @param merchantName
	 * @return
	 */
	public boolean updateOutletDetailMerchantNameInfo(String merchantId,String merchantName){
		if(StringUtils.isNotEmpty(merchantName)) {	
			DBObject where = new BasicDBObject();
			where.put("merchant_id", merchantId);
	
			DBObject value = new BasicDBObject();
			value.put("merchant_name", merchantName);
			
			LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "修改商户id为" + merchantId + "下商户名称信息为:" + value.toString());
			manager.updateMulti(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
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
			manager.updateMulti(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
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
	public boolean updateOutletDetailDefaultImage(String outletId, String defaultImapge) throws Exception {
		if(StringUtils.isNotEmpty(defaultImapge)) {			
			
			DBObject where = new BasicDBObject();
			where.put("_id", outletId);
	
			DBObject value = new BasicDBObject();
			value.put("default_image", defaultImapge);
			manager.updateMulti(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
			LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "修改门店id为" + outletId + "默认图片为:" + defaultImapge.toString());
//			manager.findAndModify(value, where, MongoTableName.CB_OUTLET_DETAIL, true, OutletDetail.class);
			return true;
		}
		return false;
	}
	
	public boolean updateOutletDetail(Outlet outlet, MerchantDetail merchantDetail) throws Exception {
		OutletDetail outletDetail = null;
		
		try {
			outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outlet);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		outletDetail.setId(outlet.getOutletId());

		if (StringUtils.isNotBlank(outlet.getLongitude()) && StringUtils.isNotBlank(outlet.getLatitude())) {
			Location location = new Location();
			location.setLongitude(Double.parseDouble(outlet.getLongitude()));
			location.setLatitude(Double.parseDouble(outlet.getLatitude()));
			outletDetail.setLocation(location);
		}
		
		if(merchantDetail != null) {
			if (StringUtils.isNotBlank(merchantDetail.getMerchantName()))
				outletDetail.setMerchantName(merchantDetail.getMerchantName());
			if (CollectionUtils.isNotEmpty(merchantDetail.getTypeInfo()))
				outletDetail.setTypeInfo(merchantDetail.getTypeInfo());
		}
		
		outletDetail.setCategoryInfo(outlet.getCategoryInfo());
		
		//编辑、编辑审核、录入审核对“优惠折扣码、优惠折扣比”进行冗余缓存.
		if(StringUtils.isNotBlank(outlet.getDiscountCode())){
			outletDetail.setDiscountCode(outlet.getDiscountCode());
		}
		if(StringUtils.isNotBlank(outlet.getDiscountRate())){
			outletDetail.setDiscountRate(outlet.getDiscountRate());
		}
		
		DBObject where = new BasicDBObject();
		where.put("_id", outlet.getOutletId());
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "修改_id为" + outlet.getOutletId() + "的数据为--------->" + JSON.toJSONString(outletDetail));
//		manager.findAndModify((DBObject)com.mongodb.util.JSON.parse(JSonUtil.toJSonString(outletDetail)), where, MongoTableName.CB_OUTLET_DETAIL, false, MerchantDetail.class);
		manager.updateMulti((DBObject)com.mongodb.util.JSON.parse(JSonUtil.toJSonString(outletDetail)), where, MongoTableName.CB_OUTLET_DETAIL, "$set");
		return true;
	}
	
	public MongoPage findOutletDetailByPage(MongoPage mongoPage, OutletDetail outletDetail)throws Exception {
		
//		BasicDBObject where = (BasicDBObject) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(outletDetail));
		BasicDBObject query = new BasicDBObject(); // 联合查询条件
		if(outletDetail.getClientId() != null){
			query.append("client_id", outletDetail.getClientId());
		}
		if(outletDetail.getMerchantId() != null){
			query.append("merchant_id", outletDetail.getMerchantId());
		}
		if(StringUtils.isNotBlank(outletDetail.getMerchantName())) {
			String merchantName = outletDetail.getMerchantName();
			String regexStr = ".*";
//			char[] cs = merchantName.toCharArray();
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
			regexStr += merchantName + ".*";
			
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("merchant_name", like);
		}
		if(outletDetail.getAreaId() != null){
			query.append("area_id", outletDetail.getAreaId());
		}
		if (outletDetail.getIsEnable() != null) {
			query.append("is_enable", outletDetail.getIsEnable());
		}
		if(StringUtils.isNotBlank(outletDetail.getOutletName())){
			String outletName = outletDetail.getOutletName();
			String regexStr = ".*";
//			char[] cs = outletName.toCharArray();
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
			regexStr += outletName + ".*";
			
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("outlet_name", like);
		}
		if(StringUtils.isNotBlank(outletDetail.getOutletFullname())){
			String outletFullname = outletDetail.getOutletFullname();
			String regexStr = ".*";
//			char[] cs = outletFullname.toCharArray();
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
			regexStr += outletFullname + ".*";
			
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("outlet_fullname", like);
		}
		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
		if (CollectionUtils.isNotEmpty(categoryInfo)) {
			Long[] categoryIdArr = new Long[categoryInfo.size()];
			for (int i = 0; i < categoryInfo.size(); i++) {
				CategoryInfo ci = categoryInfo.get(i);
				if (ci != null && ci.getCategoryId() != null)
					categoryIdArr[i] = ci.getCategoryId();
			}
			if (categoryIdArr.length > 0) {
				BasicDBObject categorys = new BasicDBObject();
				categorys.append("$in", categoryIdArr);
				query.append("category_info.category_id", categorys);
			}
		}
		List<TypeInfo> typeInfo = outletDetail.getTypeInfo();
		if (CollectionUtils.isNotEmpty(typeInfo)) {
			Long[] typeIdArr = new Long[typeInfo.size()];
			for (int i = 0; i < typeInfo.size(); i++) {
				TypeInfo ti = typeInfo.get(i);
				if (ti != null && ti.getMerchantTypeId() != null)
					typeIdArr[i] = ti.getMerchantTypeId();
			}
			if (typeIdArr.length > 0) {
				BasicDBObject types = new BasicDBObject();
				types.append("$in", typeIdArr);
				query.append("type_info.merchant_type_id", types);
			}
		}
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "分页搜索门店详情查询条件:" + query.toString());
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "分页搜索门店详情查询mongoPage:" + JSON.toJSONString(mongoPage));
		mongoPage = manager.findByPage(mongoPage, query, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
		return mongoPage;
	}
	

	/**
	 * 分页查询最热门店详情(个人h5用到)
	 * @Title: findHottestOutletDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param mongoPage
	 * @param outletDetail
	 * @return
	 * @throws Exception
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	@Deprecated
	public List<OutletDetailSimpleInfo> findHottestOutletDetailByPage_old(OutletDetail outletDetail,int totalNumber)throws Exception {
		MongoPage mongoPage  = new MongoPage();
		mongoPage.setSort(new Sort("store_count", OrderBy.DESC));
		mongoPage.setPageSize(totalNumber);
		
//		BasicDBObject where = (BasicDBObject) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(outletDetail));

		BasicDBObject query = new BasicDBObject(); // 联合查询条件
		if(outletDetail.getClientId() != null){
			query.append("client_id", outletDetail.getClientId());
		}
		query.append("is_enable", true);
		
		if(outletDetail.getAreaId() != null){
			query.append("area_id", outletDetail.getAreaId());
		}
		if(outletDetail.getParentAreaId() != null){
			query.append("parent_area_id", outletDetail.getParentAreaId());
		}
		
		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
		if (CollectionUtils.isNotEmpty(categoryInfo)) {
			Long[] categoryIdArr = new Long[categoryInfo.size()];
			for (int i = 0; i < categoryInfo.size(); i++) {
				CategoryInfo ci = categoryInfo.get(i);
				if (ci != null && ci.getCategoryId() != null)
					categoryIdArr[i] = ci.getCategoryId();
			}
			if (categoryIdArr.length > 0) {
				BasicDBObject categorys = new BasicDBObject();
				categorys.append("$in", categoryIdArr);
				query.append("category_info.category_id", categorys);
			}
		}

		if(StringUtils.isNotBlank(outletDetail.getOutletName())){
			String outletName = outletDetail.getOutletName();
			StringBuilder regexStr = new StringBuilder( ".*");
//			char[] cs = outletName.toCharArray();
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
			regexStr.append(outletName).append(".*");
			
			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("outlet_name", like);
		}
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "分页查询最热门店详情(个人h5用到):" + query.toString());
//		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "分页搜索门店详情查询mongoPage:" + JSON.toJSONString(mongoPage));
		
		BasicDBObject keys = new BasicDBObject(); // 联合查询条件
		keys.append("_id", 1);
		keys.append("default_image", 1);
		keys.append("outlet_name", 1);		
		keys.append("category_info", 1);
		keys.append("address", 1);
		keys.append("location", 1);
		mongoPage = manager.findByPage(mongoPage, query,keys, MongoTableName.CB_OUTLET_DETAIL, OutletDetailSimpleInfo.class);
		return (List<OutletDetailSimpleInfo>) mongoPage.getItems();
	}
	
	/**
	 * 分页查询最热门店详情(个人h5用到)
	 * @Title: findHottestOutletDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletDetail
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * @return List<OutletDetailSimpleInfo>    返回类型 
	 * @throws
	 */
	public List<OutletDetailSimpleInfo> findHottestOutletDetailByPage(OutletDetail outletDetail, int start, int pageSize)throws Exception {

		List<DBObject> pipeline = new ArrayList<DBObject>();
		
		/*********************************查询条件*********************************/
		BasicDBObject where = new BasicDBObject();
		
		BasicDBObject query = new BasicDBObject();
		if(outletDetail.getClientId() != null){
			query.append("client_id", outletDetail.getClientId());
		}
		query.append("is_enable", true);
		
		if(outletDetail.getAreaId() != null){
			query.append("area_id", outletDetail.getAreaId());
		}
		if(outletDetail.getParentAreaId() != null){
			query.append("parent_area_id", outletDetail.getParentAreaId());
		}
		
		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
		if (CollectionUtils.isNotEmpty(categoryInfo)) {
			Long[] categoryIdArr = new Long[categoryInfo.size()];
			for (int i = 0; i < categoryInfo.size(); i++) {
				CategoryInfo ci = categoryInfo.get(i);
				if (ci != null && ci.getCategoryId() != null)
					categoryIdArr[i] = ci.getCategoryId();
			}
			if (categoryIdArr.length > 0) {
				BasicDBObject categorys = new BasicDBObject();
				categorys.append("$in", categoryIdArr);
				query.append("category_info.category_id", categorys);
			}
		}

		if(StringUtils.isNotBlank(outletDetail.getOutletName())){
			String outletName = outletDetail.getOutletName();
			StringBuilder regexStr = new StringBuilder( ".*");
			regexStr.append(outletName).append(".*");
			
			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("outlet_name", like);
		}
		
		where.put("$match", query);
		
		
		/*********************************返回字段*********************************/
		BasicDBObject project = new BasicDBObject();
		BasicDBObject pro = new BasicDBObject();
//		BasicDBList add = new BasicDBList(); // 统计评论数量
//		add.add("$one_level_count");
//		add.add("$two_level_count");
//		add.add("$three_level_count");
//		add.add("$four_level_count");
//		add.add("$five_level_count");
//		
//		pro.put("comment_count", new BasicDBObject("$add", add));
		pro.put("_id", "$_id");
		pro.put("create_time", "$create_time");
		pro.put("default_image", "$default_image");
		pro.put("outlet_name", "$outlet_name");
		pro.put("category_info", "$category_info");
		pro.put("address", "$address");
		pro.put("location", "$location");
		pro.put("store_count", "$store_count");
		project.put("$project", pro);
		
		
		/*********************************排序条件*********************************/
		BasicDBObject sort = new BasicDBObject();
		BasicDBObject s = new BasicDBObject();
//		s.put("comment_count", -1); // 根据评论数降序
		s.put("store_count", -1);// 根据收藏数降序
		s.put("create_time", -1);// 根据创建时间降序
		sort.put("$sort", s);
		
		/*********************************跳过条数*********************************/
		BasicDBObject skip = new BasicDBObject();
		skip.put("$skip", start);
		
		/*********************************查询条数*********************************/
		BasicDBObject limit = new BasicDBObject();
		limit.put("$limit", pageSize);
		
		
		
		
		/*********************************组装pipeline*********************************/
		pipeline.add(where);
		pipeline.add(project);
		pipeline.add(sort);
		pipeline.add(skip);
		pipeline.add(limit);
		return manager.findByPipeline(pipeline, MongoTableName.CB_OUTLET_DETAIL, OutletDetailSimpleInfo.class);
	}
	
//	public List<OutletDetailSimpleInfo> findHottestOutletDetailByPage(OutletDetail outletDetail, int start, int end) throws Exception {
//		List<OutletDetailSimpleInfo> list = findHottestOutletDetailByPage(outletDetail, end);
//		List<OutletDetailSimpleInfo> resultList = new ArrayList<OutletDetailSimpleInfo>();
//		if (CollectionUtils.isEmpty(list))
//			return resultList;
//		for (int i = 0; i < list.size(); i++) {
//			OutletDetailSimpleInfo osi = list.get(i);
//			System.out.println(i + "\t" + osi.getId() + "\t" + osi.getOutletName() + "\t" + osi.getStoreCount());
//		}
//		int newLength = list.size() < end ? list.size() : end;
//		for (int i = start; i < newLength; i++) {
//			resultList.add(list.get(i));
//		}
//		return resultList;
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
//	public MongoPage findNearbyOutlet(MongoPage mongoPage, OutletDetail outletDetail, double distace) throws Exception {
////		Location location = outletDetail.getLocation();
////		double longitude = location.getLongitude();
////		double latitude = location.getLatitude();
//		
//		
//		
//		BasicDBObject where = new BasicDBObject();
////		where.put("location", "{ $within : { $center : [[0.5, 0.5], 20], $uniqueDocs : true }");
////		where.put("location", "{ $near : [116.325986265656, 26.569789419], $maxDistance:10000, $uniqueDocs : true }");
////		where.put("location", "{ $within : {$center : [[116.325986265656, 26.569789419], 1000000]}}");
////		System.out.println(where);
////		System.out.println(JSON.toJSONString(manager.findAll(where, MongoTableName.CB_OUTLET_DETAIL, Map.class),true));
//		
//		
//		List whereList = new ArrayList();
////		whereList.add(new double[] { longitude, latitude });
//		whereList.add(outletDetail.getLocation());
//		whereList.add((double) distace / 1000.0 / 111.0); // 半径以km为单位
//		where.append("location", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", whereList)));
//		if (outletDetail.getIsEnable() != null) {
//			where.append("is_enable", outletDetail.getIsEnable());
//		}
//		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
//		if (CollectionUtils.isNotEmpty(categoryInfo)) {
//			where.append("category_info", categoryInfo);
//		}
//		List<TypeInfo> typeInfo = outletDetail.getTypeInfo();
//		if (CollectionUtils.isNotEmpty(typeInfo)) {
//			where.append("type_info", typeInfo);
//		}
//		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "附近搜索门店查询条件:" + where.toString());
//		mongoPage = manager.findByPage(mongoPage, where, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
//
////		List<OutletDetail> list = (List<OutletDetail>) manager.findAll(where, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
////		return list;
//		return mongoPage;
//	}
	
	/**
	 *  商户附件排序问题
	  * @Title: findNearByOutlet
	  * @Description: TODO
	  * @author: share 2015年6月11日
	  * @modify: share 2015年6月11日
	  * @param @param mongoPage
	  * @param @param outletDetail
	  * @param @param distance
	  * @param @param orderBy 1 -人气排序 2-距离
	  * @param @return    
	  * @return MongoPage    
	  * @throws
	 */
	public MongoPage findNearByOutlet(MongoPage mongoPage,OutletDetail outletDetail,double distance,int orderBy){
		
		Integer pageSize = mongoPage.getPageSize();
		pageSize = null == pageSize ? 10 : pageSize; // 默认10条
		
		int start = (mongoPage.getPageNumber() - 1) * pageSize;
		int end = start + pageSize;
		LogCvt.info("附近搜索门店mongodb查询[" + start + "]至[" + end + "]条数据[开始...]");
		LogCvt.info("传入的查询条件:" + JSON.toJSONString(outletDetail, true));
		
		// 管道集
		List<DBObject> pipelineCount = new ArrayList<DBObject>();
		List<DBObject> pipeline = new ArrayList<DBObject>();
		BasicDBObject myCmd = new BasicDBObject();
		
		/**
		 *  联合查询条件
		 */
		BasicDBObject query = new BasicDBObject();
		if(outletDetail.getClientId() != null){
			query.append("client_id", outletDetail.getClientId());
		}
		// 是否有效
		query.append("is_enable", true);
		if(outletDetail.getAreaId() != null){
			query.append("area_id", outletDetail.getAreaId());
		}
		if(outletDetail.getParentAreaId() != null){
			query.append("parent_area_id", outletDetail.getParentAreaId());
		}
		
		
		query.append("type_info.type", new BasicDBObject("$in",new String[]{"0","1"}));
		
		/**
		 *  分类
		 */
		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
		if (CollectionUtils.isNotEmpty(categoryInfo)) {
			MerchantCategoryLogic mcl = new MerchantCategoryLogicImpl(); 
			MerchantCategory mc = null;
			List<MerchantCategory> rlist = null;
			//循环列表，获取多个分类实例
			List<String> tlist = new ArrayList<String>();
			for (int i = 0; i < categoryInfo.size(); i++) {
				CategoryInfo ci = categoryInfo.get(i);
				if (ci != null && ci.getCategoryId() != null){
					//根据categoryId到cb_merchant_category获取tree_path中存在该id的列表
					mc = new MerchantCategory();
					mc.setClientId(outletDetail.getClientId());
					mc.setTreePath(String.valueOf(ci.getCategoryId()));
					mc.setIsDelete(false);
					rlist = mcl.findMerchantCategory(mc);
					if(rlist!=null && rlist.size()>0){
						for (int j = 0; j < rlist.size(); j++) {
							tlist.add(String.valueOf(rlist.get(j).getId()));
						}
					}
				}
					
			}
			
			if(tlist!=null && tlist.size()>0){
				Long[] categoryIdArr = new Long[tlist.size()];
				for (int i = 0; i < categoryIdArr.length; i++) {
					categoryIdArr[i] = Long.parseLong(tlist.get(i));
				}
				if (categoryIdArr.length > 0) {
					BasicDBObject categorys = new BasicDBObject();
					categorys.append("$in", categoryIdArr);
					query.append("category_info.category_id", categorys);
				}
			}
			
		}
		
		
		/**
		 *  门店名称
		 */
		if(StringUtils.isNotBlank(outletDetail.getOutletName())){
			String outletName = outletDetail.getOutletName();
			StringBuilder regexStr = new StringBuilder(".*");
			regexStr.append(outletName).append(".*");
			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			query.append("outlet_name", like);
		}
		
		
		Location location = outletDetail.getLocation();
		if(location != null ){
			double[] loc = { location.getLongitude(), location.getLatitude() };
			myCmd.append("near", loc);
			myCmd.append("distanceField", "dis");
			// 是否为球形
			myCmd.append("spherical", true); 
			// 指定球形半径（地球半径）
			myCmd.put("distanceMultiplier", 6378137);
			if(distance > 0){
				myCmd.put("maxDistance", (double) distance / 6378137);
			}
			myCmd.put("query", query);
			pipeline.add(new BasicDBObject("$geoNear", myCmd));
			pipelineCount.add(new BasicDBObject("$geoNear", myCmd));
		}else{
			pipeline.add(new BasicDBObject("$match", query));
			pipelineCount.add(new BasicDBObject("$match", query));
		}
		
		/**
		 *  排序
		 */
		DBObject sort = new BasicDBObject();
		if(orderBy == 1){
			// 人气
//			sort.put("star_level", -1);
			sort.put("store_count", -1);
//			sort.put("dis", 1);
			sort.put("create_time", -1);
		}else if(orderBy == 2 && location != null){
			// 距离
			sort.put("dis", 1);
			sort.put("store_count", -1);
			sort.put("create_time", -1);
		}else{
			// 默认
			if(location != null){
				sort.put("dis", 1);
			}else{
//				sort.put("star_level", -1);
				sort.put("store_count", -1);
			}
			sort.put("create_time", -1);
		}
		pipeline.add(new BasicDBObject("$sort", sort));
		pipeline.add(new BasicDBObject("$skip", start));
	   	pipeline.add(new BasicDBObject("$limit",mongoPage.getPageSize()));
		
		/**
		 *  默认值
		 */
		BasicDBObject pro = new BasicDBObject();
		pro.put("_id", "$_id");
		pro.put("create_time", "$create_time");
		pro.put("default_image", "$default_image");
		pro.put("outlet_name", "$outlet_name");
		pro.put("category_info", "$category_info");
		pro.put("type_info", "$type_info");
		pro.put("address", "$address");
		pro.put("location", "$location");
		pro.put("store_count", "$store_count");
		pro.put("dis", "$dis");
		pro.put("star_level", "$star_level");
		pro.put("discount_code", "$discount_code");
		pro.put("discount_rate", "$discount_rate");
		pipeline.add(new BasicDBObject("$project", pro));
		

		
		LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "附近搜索门店查询条件:" + JSonUtil.toJSonString(pipeline));
		List<OutletDetailSimpleInfo> myResults = manager.findByPipeline(pipeline, MongoTableName.CB_OUTLET_DETAIL, OutletDetailSimpleInfo.class);
		
		mongoPage.build(myResults);
		
		//当为第一页时，查询总记录数及分页数
		if(mongoPage.getPageNumber()==1){
			BasicDBObject groupObj = new BasicDBObject();
			groupObj.put("_id", null);
			groupObj.put("total_count", new BasicDBObject("$sum",1));
			pipelineCount.add(new BasicDBObject("$group", groupObj));
			List<Object> cntRes = manager.findByPipeline(pipelineCount, MongoTableName.CB_OUTLET_DETAIL, Object.class);
			if(cntRes.size()>0){
				//totalPage = (totalRecord + maxResult -1) / maxResult;
				HashMap x = JSonUtil.toObject(cntRes.get(0).toString(), HashMap.class);
				int totalRecord = Integer.parseInt(x.get("total_count").toString());
				int totalPage =  (totalRecord + pageSize -1 ) / pageSize;
				mongoPage.setTotalCount(totalRecord);
				mongoPage.setPageCount(totalPage);
				if(totalPage>mongoPage.getPageNumber()){
					mongoPage.setHasNext(true);
				}else{
					mongoPage.setHasNext(false);
				}
			}else{
				mongoPage.setTotalCount(0);
				mongoPage.setPageCount(0);
				mongoPage.setHasNext(false);
			}
		}else{
			if(mongoPage.getPageCount()>mongoPage.getPageNumber()){
				mongoPage.setHasNext(true);
			}else{
				mongoPage.setHasNext(false);
			}
		}
		
		LogCvt.debug("当前页:"+ mongoPage.getPageNumber());
		LogCvt.debug("总页数:"+ mongoPage.getPageCount());
		LogCvt.debug("总记录："+ mongoPage.getTotalCount());
		LogCvt.debug("下一页："+ mongoPage.getHasNext());
		return mongoPage;
	}
	
	
	public MongoPage findNearByOutletPage(MongoPage mongoPage,OutletDetail outletDetail,double distance,int skip){
		
		Integer pageSize = mongoPage.getPageSize();
		pageSize = null == pageSize ? 10 : pageSize; // 默认10条
		
		int start = (mongoPage.getPageNumber() - 1) * pageSize;
		int end = start + pageSize;
		LogCvt.info("特惠商品附近搜索门店mongodb查询[" + start + "]至[" + end + "]条数据[开始...]-skip->"+skip);
		LogCvt.info("特惠商品传入的查询条件:" + JSON.toJSONString(outletDetail, true));
		
		// 管道集
		List<DBObject> pipeline = new ArrayList<DBObject>();
		BasicDBObject myCmd = new BasicDBObject();
		
		/**
		 *  联合查询条件
		 */
		BasicDBObject query = new BasicDBObject();
		if(outletDetail.getClientId() != null){
			query.append("client_id", outletDetail.getClientId());
		}
		// 是否有效
		query.append("is_enable", true);
		if(outletDetail.getAreaId() != null){
			query.append("area_id", outletDetail.getAreaId());
		}
		
		if(outletDetail.getParentAreaId() != null){
			query.append("parent_area_id", outletDetail.getParentAreaId());
		}
		
		if(StringUtils.isNotBlank(outletDetail.getMerchantId())){
			query.append("merchant_id", outletDetail.getMerchantId());
		}
		
		/**
		 *  分类
		 */
		List<CategoryInfo> categoryInfo = outletDetail.getCategoryInfo();
		if (CollectionUtils.isNotEmpty(categoryInfo)) {
			Long[] categoryIdArr = new Long[categoryInfo.size()];
			for (int i = 0; i < categoryInfo.size(); i++) {
				CategoryInfo ci = categoryInfo.get(i);
				if (ci != null && ci.getCategoryId() != null)
					categoryIdArr[i] = ci.getCategoryId();
			}
			if (categoryIdArr.length > 0) {
				BasicDBObject categorys = new BasicDBObject();
				categorys.append("$in", categoryIdArr);
				query.append("category_info.category_id", categorys);
			}
		}
		/**
		 *  门店名称
		 */
		if(StringUtils.isNotBlank(outletDetail.getOutletName())){
			String outletName = outletDetail.getOutletName();
			StringBuilder regexStr = new StringBuilder(".*");
			regexStr.append(outletName).append(".*");
			Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			query.append("outlet_name", like);
		}
		
		
		Location location = outletDetail.getLocation();
		if(location != null ){
			double[] loc = { location.getLongitude(), location.getLatitude() };
			myCmd.append("near", loc);
			myCmd.append("distanceField", "dis");
			// 是否为球形
			myCmd.append("spherical", true); 
			// 指定球形半径（地球半径）
			myCmd.put("distanceMultiplier", 6378137);
			if(distance > 0){
				myCmd.put("maxDistance", (double) distance / 6378137);
			}
			myCmd.put("limit",1000);
			myCmd.put("query", query);
			pipeline.add(new BasicDBObject("$geoNear", myCmd));
		}else{
			pipeline.add(new BasicDBObject("$match", query));
		}
		
		DBObject group = new BasicDBObject();
		group.put("_id", "$merchant_id");
		group.put("outlet_id", new BasicDBObject("$first", "$_id"));
		group.put("dis", new BasicDBObject("$first", "$dis"));
		group.put("create_time", new BasicDBObject("$first","$create_time"));
		group.put("default_image", new BasicDBObject("$first","$default_image"));
		group.put("outlet_name", new BasicDBObject("$first","$outlet_name"));
		group.put("category_info", new BasicDBObject("$first","$category_info"));
		group.put("type_info", new BasicDBObject("$first","$type_info"));
		group.put("address", new BasicDBObject("$first","$address"));
		group.put("location", new BasicDBObject("$first","$location"));
		group.put("store_count", new BasicDBObject("$first","$store_count"));
		group.put("star_level", new BasicDBObject("$first","$star_level"));
		group.put("merchant_name", new BasicDBObject("$first","$merchant_name"));
		
		
		/**
		 *  排序
		 */
		DBObject sort = new BasicDBObject();
		if(location != null ){
			// 人气
			sort.put("dis", 1);
		}else{
			sort.put("star_level", -1);
			sort.put("store_count", -1);
			sort.put("create_time", -1);
		}
		pipeline.add(new BasicDBObject("$sort", sort));
		pipeline.add(new BasicDBObject("$group", group));
		pipeline.add(new BasicDBObject("$sort", sort));
		
		if(skip >= 0){
			pipeline.add(new BasicDBObject("$skip", skip));
		   	pipeline.add(new BasicDBObject("$limit",mongoPage.getPageSize()));
		}
		
		List<OutletDetailSimple> myResults = manager.findByPipeline(pipeline, MongoTableName.CB_OUTLET_DETAIL, OutletDetailSimple.class);
		
		mongoPage.build(myResults);
		return mongoPage;
	}
	
	
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
	public OutletDetail findOutletDetailByoutletId (String outletId)throws Exception {
		
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
	public boolean updateOutletStarLevelByOutletId(String outletId, int starLevel) throws Exception {
		
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
			double level = outletDetail.getStarLevel();
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
			level = new BigDecimal(average).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
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
	
	
	
	/**
	 * 
	  * @Title: findOutletMongoInfoByMerchantIdList
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-6-10
	  * @modify: zhangxiaohua 2015-6-10
	  * @param @param outletDetail
	  * @param @param totalNumber
	  * @param @param longitude
	  * @param @param latitude
	  * @param @return
	  * @param @throws Exception    
	  * @return List<OutletMongoInfo>    
	  * @throws
	 */
	@SuppressWarnings("unchecked")
	public MongoPage findOutletMongoInfoByMerchantId(OutletDetail outletDetail,MongoPage page) throws Exception {
		try {
			
			Location location = outletDetail.getLocation();
			BasicDBObject myCmd = new BasicDBObject();
			
			
			BasicDBObject query = new BasicDBObject(); // 联合查询条件
			if(outletDetail.getMerchantId() != null){
				query.append("merchant_id", outletDetail.getMerchantId());
			}
			query.append("is_enable", true);
			myCmd.put("query", query); 
			
			// 管道集
			List<DBObject> pipeline = new ArrayList<DBObject>();
			if(location != null){
			
				double[] loc = { location.getLongitude(), location.getLatitude() };
				myCmd.append("near", loc);
				myCmd.append("distanceField", "dis");
				myCmd.append("spherical", true); // 是否为球形
				myCmd.put("distanceMultiplier", 6378137); // 指定球形半径（地球半径）
				pipeline.add(new BasicDBObject("$geoNear", myCmd));
			}else{
				
				pipeline.add(new BasicDBObject("$match", query));
				
				DBObject sort = new BasicDBObject();
				// 人气
				sort.put("store_count", -1);
			
				pipeline.add(new BasicDBObject("$sort", sort));
			}
			

			LogCvt.info(MongoTableName.CB_OUTLET_DETAIL + "搜索经纬度和商户ID门店查询条件:" + myCmd.toString());
			
	
			page = manager.findByPageAggregate(page, pipeline, query, MongoTableName.CB_OUTLET_DETAIL, OutletMongoInfo.class); 
			page.setHasNext(page.getPageCount() > page.getPageNumber());
		} catch (Exception e) {
			LogCvt.error("经纬度和商户ID，原因:" + e.getMessage(), e); 
		}
		
		return page;
	}
	
	public boolean updateOutletDetail(OutletDetail outlet){
		DBObject where = new BasicDBObject();
		where.put("_id", outlet.getId());
		DBObject value = new BasicDBObject();
		value.put("is_enable", outlet.getIsEnable());
		int result = manager.update(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
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
	public boolean getOutletDetailCategoryInfo(String clientId,Long areaId,Long parentId ,Long[] categoryInfo) {
		if(null != categoryInfo) {	
			
			BasicDBObject query = new BasicDBObject();
			
			query.append("client_id", clientId);
			
			query.append("is_enable", true);

			if(areaId>0){
				query.append("area_id", areaId);
			}
			if(parentId>0){
				query.append("parent_area_id", parentId);
			}

			BasicDBObject categorys = new BasicDBObject();
			categorys.append("$in", categoryInfo);
			query.append("category_info.category_id", categorys);
			
			int x = manager.getCount(query, MongoTableName.CB_OUTLET_DETAIL);
			
			if(x>0){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}

