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

package com.froad.db.mongo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.logback.LogCvt;
import com.froad.po.Merchant;
import com.froad.po.Outlet;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;
import com.mysql.fabric.xmlrpc.base.Array;

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

public class MerchantDetailMongo {
	MongoManager manager = new MongoManager();
	
	/**
	 * 
	 * @Title: addMerchantDetail 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean addMerchant(Merchant merchant,List<CategoryInfo> categoryInfoList, List<TypeInfo> typeInfoList) {
		MerchantDetail merchantDetail = new MerchantDetail();

		merchantDetail = (MerchantDetail)BeanUtil.copyProperties(MerchantDetail.class, merchant);

		merchantDetail.setId(merchant.getMerchantId());
		merchantDetail.setMerchantName(merchant.getMerchantName());
		merchantDetail.setIntroduced(merchant.getIntroduce());

		if(CollectionUtils.isNotEmpty(categoryInfoList)) {
			merchantDetail.setCategoryInfo(categoryInfoList);
		}

		if(CollectionUtils.isNotEmpty(typeInfoList)) {
			merchantDetail.setTypeInfo(typeInfoList);
		}

		return addMerchantDetail(merchantDetail); // 向mongodb插入数据
	}
	
	/**
	 * 
	 * @Title: addMerchantDetail 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantDetail
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean addMerchantDetail(MerchantDetail merchantDetail) {
		merchantDetail.setStarLevel(0); // 评论星级 初始为 0
		LogCvt.debug(MongoTableName.CB_MERCHANT_DETAIL + "添加 ------->" + com.alibaba.fastjson.JSON.toJSONString(merchantDetail, true));
		return manager.add(merchantDetail, MongoTableName.CB_MERCHANT_DETAIL) > 0; // 向mongodb插入数据
	}
	
	public boolean addOutlet(Outlet outlet){
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", outlet.getMerchantId());
		
		DBObject pushObj = new BasicDBObject();
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("outlet_id", outlet.getOutletId());
		pushObj.put("outlet_info", JSON.parse(JSonUtil.toJSonString(map)));
		
		LogCvt.debug(MongoTableName.CB_MERCHANT_DETAIL+ "修改merchantId为"+outlet.getMerchantId()+"的添加门店id------->" + com.alibaba.fastjson.JSON.toJSONString(pushObj));
		int result = manager.update(pushObj, whereObj, MongoTableName.CB_MERCHANT_DETAIL, "$push");
		return result != -1;
//			return result != -1;
	}
	
	/**
	 * 删除商户详细信息
	 * @Title: deleteMerchantDetail 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean deleteMerchantDetail(String merchantId) {
		DBObject where = new BasicDBObject();
		where.put("_id", merchantId);
		
		DBObject value = new BasicDBObject();
		value.put("is_enable", false);
		LogCvt.info(MongoTableName.CB_MERCHANT_DETAIL + "表删除merchantId为" + merchantId + "的数据------->" + com.alibaba.fastjson.JSON.toJSONString(value));
		int result = manager.update(value, where, MongoTableName.CB_MERCHANT_DETAIL, COMMAND_SET);
//		int result = manager.findAndRemove(where, MongoTableName.CB_MERCHANT_DETAIL);
		return result != -1;
	}
	
	/**
	 * 物理删除商户详细信息
	 * @Title: removeMerchantDetail 
	 * @author vania
	 * @version 1.0
	 * @see: 
	 * @param merchantId
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean removeMerchantDetail(String merchantId) {
		DBObject where = new BasicDBObject();
		where.put("_id", merchantId);
		
//		DBObject value = new BasicDBObject();
//		value.put("is_enable", false);
//		LogCvt.info(MongoTableName.CB_MERCHANT_DETAIL + "表删除merchantId为" + merchantId + "的数据------->" + com.alibaba.fastjson.JSON.toJSONString(value));
		int result = manager.remove(where, MongoTableName.CB_MERCHANT_DETAIL);
		return result != -1;
	}
	
	/**
	 * 删除商户下的门店
	 * @Title: deleteOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @param outletId
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean deleteOutlet(String merchantId, String outletId) {
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", merchantId);

		DBObject pullObj = new BasicDBObject();
		pullObj.put("outlet_info", new BasicDBObject("outlet_id", outletId));

		LogCvt.info(MongoTableName.CB_MERCHANT_DETAIL + "删除商户id为:" + merchantId + "下的门店id" + outletId);
		int result = manager.update(pullObj, whereObj, MongoTableName.CB_MERCHANT_DETAIL, "$pull");
		return result != -1;
	}
	
	/**
	 * 修改MerchantDetail
	 * @Title: updateMerchantDetailById 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean updateMerchantDetailById(Merchant merchant,List<CategoryInfo> categoryInfoList, List<TypeInfo> typeInfoList) {
		DBObject where = new BasicDBObject();
		where.put("_id", merchant.getMerchantId());
		
//		MerchantDetail merchantDetail = this.findMerchantDetailById(merchant.getMerchantId());
		MerchantDetail merchantDetail = (MerchantDetail) BeanUtil.copyProperties(MerchantDetail.class, merchant);
		merchantDetail.setId(merchant.getMerchantId());

		if(categoryInfoList != null && categoryInfoList.size() >0)
			merchantDetail.setCategoryInfo(categoryInfoList);

		if(typeInfoList != null && typeInfoList.size() >0)
			merchantDetail.setTypeInfo(typeInfoList);
		
		LogCvt.debug(MongoTableName.CB_MERCHANT_DETAIL + "修改_id为:"+merchant.getMerchantId()+"的数据为-------->" + com.alibaba.fastjson.JSON.toJSONString(merchantDetail));
		manager.update((DBObject) JSON.parse(JSonUtil.toJSonString(merchantDetail)), where, MongoTableName.CB_MERCHANT_DETAIL, COMMAND_SET);
		return true;
	}	
	
	/**
	 * 查询商户详情
	 * @Title: findMerchantDetailById 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchat_id
	 * @return
	 * @return MerchantDetail    返回类型 
	 * @throws
	 */
	public MerchantDetail findMerchantDetailById (String merchat_id){
		DBObject dbObj = new BasicDBObject();
		dbObj.put("_id", merchat_id);
		return manager.findOne(dbObj, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
	}
	
	public MongoPage findMerchantDetailByPage(MongoPage mongoPage, MerchantDetail merchantDetail)throws Exception {
//		BasicDBObject where = (BasicDBObject) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(outletDetail));
		BasicDBObject query = new BasicDBObject(); // 联合查询条件
		if(merchantDetail.getClientId() != null){
			query.append("client_id", merchantDetail.getClientId());
		}
		if(StringUtils.isNotBlank(merchantDetail.getProOrgCode())){
			query.append("pro_org_code", merchantDetail.getProOrgCode());
		}
		if(StringUtils.isNotBlank(merchantDetail.getCityOrgCode())){
			query.append("city_org_code", merchantDetail.getCityOrgCode());
		}
		if(StringUtils.isNotBlank(merchantDetail.getCountyOrgCode())){
			query.append("county_org_code", merchantDetail.getCountyOrgCode());
		}
		if(StringUtils.isNotBlank(merchantDetail.getOrgCode())){
			query.append("org_code", merchantDetail.getOrgCode());
		}
		if(merchantDetail.getIsEnable() != null){
			query.append("is_enable", merchantDetail.getIsEnable());
		}
		if(StringUtils.isNotBlank(merchantDetail.getMerchantName())) {
			String merchantName = merchantDetail.getMerchantName();
			String regexStr = ".*";
//			char[] cs = merchantName.toCharArray();
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
			regexStr += merchantName + ".*";
//			regexStr += "";
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("merchant_name", like);
		}
		if(StringUtils.isNotBlank(merchantDetail.getMerchantFullname())) {
			String merchantFullname = merchantDetail.getMerchantFullname();
			String regexStr = ".*";
//			char[] cs = merchantFullname.toCharArray();
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
			regexStr += merchantFullname + ".*";
//			regexStr += "";
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("merchant_fullname", like);
		}
		if(StringUtils.isNotBlank(merchantDetail.getLogo())){
			query.append("logo", merchantDetail.getLogo());
		}
		if(StringUtils.isNotBlank(merchantDetail.getAddress())) {
			String address = merchantDetail.getAddress();
			String regexStr = ".*";
//			char[] cs = address.toCharArray();
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
			regexStr += address + ".*";
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("address", like);
		}
		if(StringUtils.isNotBlank(merchantDetail.getPhone())){
			query.append("phone", merchantDetail.getPhone());
		}
		if(StringUtils.isNotBlank(merchantDetail.getIntroduced())) {
			String introduced = merchantDetail.getIntroduced();
			String regexStr = ".*";
//			char[] cs = introduced.toCharArray();
//			for (char c : cs) {
//				regexStr += c + ".*";
//			}
//			regexStr += "";
			regexStr += introduced + ".*";
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			query .append("introduced", like);
		}
		if(merchantDetail.getAreaId() != null) {
			query.append("area_id", merchantDetail.getAreaId());
		}
		List<String> outletInfo = merchantDetail.getOutletInfo();
		if (CollectionUtils.isNotEmpty(outletInfo)) {			
				BasicDBObject outletInfoArr = new BasicDBObject();
				outletInfoArr.append("$in", outletInfo.toArray(new String[]{}));
				query.append("outlet_info", outletInfoArr);
		}
		List<CategoryInfo> categoryInfo = merchantDetail.getCategoryInfo();
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
		List<TypeInfo> typeInfo = merchantDetail.getTypeInfo();
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
//		LogCvt.debug(MongoTableName.CB_MERCHANT_DETAIL + "查询条件:" + query.toString());
		mongoPage = manager.findByPage(mongoPage, query, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
		return mongoPage;
	}
	
	/**
	 * 根据商户id集合查询详情
	 * @Title: findMerchantDetailbyOutletIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletIdList
	 * @return
	 * @return List<MerchantDetail>    返回类型 
	 * @throws
	 */
	public List<MerchantDetail> findMerchantDetailbyOutletIdList(List<String> merchantIdList){
		List<MerchantDetail> result = null;
		
		try{
			DBObject dbObject = new BasicDBObject();
//			dbObject.put(MONGO_KEY_MERCHANT_ID, merchantId);
			
			BasicDBObject merchantId = new BasicDBObject();
			merchantId.append(QueryOperators.IN, merchantIdList.toArray(new String[]{}));
			
			dbObject.put(MONGO_KEY_ID, merchantId);
			
			
//			BasicDBObject categorys = new BasicDBObject();
//			categorys.append(QueryOperators.IN, categoryIdArr);			
//			dbObject.put(MONGO_KEY_CATEGORY_INFO+"."+MONGO_KEY_CATEGORY_ID, categoryIdArr);
			
//			LogCvt.info("根据商户id集合查询详情条件------>"+ dbObject.toString());
			result = (List<MerchantDetail>)manager.findAll(dbObject, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
		}catch(Exception e){
			LogCvt.error("根据商户id查询商户详情信息列表失败，原因:" + e.getMessage(), e); 
			result = null;
		}
		return result;
	}
	
	/**
	 * 根据商户id集合查询详情
	 * @Title: findMerchantNamebyOutletIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletIdList
	 * @return
	 * @return Map<String, String>   返回类型 
	 * @throws
	 */
	public Map<String, String> findMerchantNamebyOutletIdList(List<String> merchantIdList){
		Map<String, String> result = null;
		
		try{
			DBObject dbObject = new BasicDBObject();
//			dbObject.put(MONGO_KEY_MERCHANT_ID, merchantId);
			
			BasicDBObject merchantId = new BasicDBObject();
			merchantId.append(QueryOperators.IN, merchantIdList.toArray(new String[]{}));
			
			dbObject.put(MONGO_KEY_ID, merchantId);
			
			
//			BasicDBObject categorys = new BasicDBObject();
//			categorys.append(QueryOperators.IN, categoryIdArr);			
//			dbObject.put(MONGO_KEY_CATEGORY_INFO+"."+MONGO_KEY_CATEGORY_ID, categoryIdArr);
			
//			LogCvt.info("根据商户id集合查询详情条件------>"+ dbObject.toString());
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("_id");
			fieldNames.add("merchant_name");
			List<Map<String, String>>list = (List<Map<String, String>>)manager.findAllByFieldNames(dbObject, MongoTableName.CB_MERCHANT_DETAIL, fieldNames, Map.class);
			if (CollectionUtils.isNotEmpty(list)) {
				result = new HashMap<String, String>(); 
				for (Map<String, String> map : list) {
					result.putAll(map);
				}
			}
		}catch(Exception e){
			LogCvt.error("根据商户id查询商户详情信息列表失败，原因:" + e.getMessage(), e); 
			result = null;
		}
		return result;
	}
	
	/**
	 * 查询商户CategoryInfo
	 * @Title: findMerchantCategoryInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchat_id
	 * @return
	 * @return List<CategoryInfo>    返回类型 
	 * @throws
	 */
	public List<CategoryInfo> findMerchantCategoryInfo(String merchat_id){
		
		
		BasicDBObject merchantId = new BasicDBObject();
		merchantId.append(MONGO_KEY_ID, merchat_id);
		
		List<String> fieldNames = new ArrayList<String>();
		fieldNames.add("category_info");
		List<MerchantDetail> md = (List<MerchantDetail>) manager.findAllByFieldNames(merchantId, MongoTableName.CB_MERCHANT_DETAIL, fieldNames, MerchantDetail.class);
		if(CollectionUtils.isEmpty(md)){
			return null;
		}
		return md.get(0).getCategoryInfo();
	}
	
	/**
	 * 查询商户TypeInfo
	 * @Title: findMerchantTypeInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchat_id
	 * @return
	 * @return List<TypeInfo>    返回类型 
	 * @throws
	 */
	public List<TypeInfo> findMerchantTypeInfo(String merchat_id){

		
		BasicDBObject merchantId = new BasicDBObject();
		merchantId.append(MONGO_KEY_ID, merchat_id);
		
		List<String> fieldNames = new ArrayList<String>();
		fieldNames.add("type_info");
		List<MerchantDetail> md = (List<MerchantDetail>) manager.findAllByFieldNames(merchantId, MongoTableName.CB_MERCHANT_DETAIL, fieldNames, MerchantDetail.class);
		if(CollectionUtils.isEmpty(md)){
			return null;
		}
		return md.get(0).getTypeInfo();
	}
	
	/**
	 * 更新商户评价星级
	 * 
	 * 1 - 查询所属门店
	 * 2 - 统计计算评价星级(小数点一位四省五入取整)
	 * 3 - 更新
	 * 
	 * @Title: updateMerchantStarLevelByMerchantId 
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * 
	 * @return boolean
	 * */
	public boolean updateMerchantStarLevelByMerchantId(String merchantId) throws Exception {
		
		boolean result = false;
		
		if( merchantId != null && !"".equals(merchantId) ){
			
			// 查询所属门店
			OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
			List<OutletDetail> outletDetailList = outletDetailMongo.findOutletDetailListByMerchantId(merchantId);
			
			if( outletDetailList != null && outletDetailList.size() > 0 ){
				
				int levelCount = 0;
				double levelSum = 0,levelTemp = 0;
				// 循环所属门店 - 统计所有星级总和
				for( OutletDetail outletDetail : outletDetailList ){
					levelTemp = outletDetail.getStarLevel();
					if( levelTemp > 0 ){
						levelSum += outletDetail.getStarLevel();
						levelCount++; // 统计有几个门店有评级数据
					}
					
				}
				// 小数点一位四省五入取整
				double average = Double.valueOf(levelSum)/levelCount;
				double level = new BigDecimal(average).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			
				DBObject where = new BasicDBObject();
				where.put(MONGO_KEY_ID, merchantId);
			
				DBObject value = new BasicDBObject();
				value.put(MONGO_KEY_STAR_LEVEL, level);
				
				result = manager.update(value, where, MongoTableName.CB_MERCHANT_DETAIL, COMMAND_SET) > -1 ? true:false;
			}
		}
		
		return result;
	}
	private static final String MONGO_KEY_ID = "_id";
	private static final String MONGO_KEY_STAR_LEVEL = "star_level";
	private static final String COMMAND_SET = "$set";
}
