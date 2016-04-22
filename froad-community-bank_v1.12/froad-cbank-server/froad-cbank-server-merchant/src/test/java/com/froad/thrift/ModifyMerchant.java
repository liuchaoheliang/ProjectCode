package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;


public class ModifyMerchant {
	
	/**
	  * @Title: main
	  * @Description: TODO
	  * @author: share 2015年6月13日
	  * @modify: share 2015年6月13日
	  * @param @param args    
	  * @return void    
	  * @throws
	*/
	
	
	public static void main(String[] args) {
		PropertiesUtil.load();
//		modifyMerchant();
//		modifyOutlet();
//		modifyOutlet11();
//		modifyMerchant();
		modifyOutlet();
		
		
		
		
	}

	private static void modifyMerchant() {
		MongoManager manager = new MongoManager();
		DBObject dbo = new BasicDBObject();
		List<MerchantDetail> detailList = (List<MerchantDetail>) manager.findAll(dbo, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
		
		for(MerchantDetail detail : detailList){
			LogCvt.info("修改ID："+detail.getId());
			DBObject where = new BasicDBObject();
			where.put("_id", detail.getId());
			
			List<TypeInfo> oldTypeInfos = detail.getTypeInfo();
			
			DBObject value = new BasicDBObject();
			List<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
			for(TypeInfo infos : oldTypeInfos){
				if(infos.getMerchantTypeId().longValue() == 100000000l){
					TypeInfo info = new TypeInfo();
					info.setMerchantTypeId(100000001l);
					info.setTypeName("直接优惠");
					info.setType("1");
					info.setType("0");
					typeInfos.add(info);
				}
				
				if(infos.getMerchantTypeId().longValue() == 100000001l){
					TypeInfo info = new TypeInfo();
					info.setMerchantTypeId(100000000l);
					info.setTypeName("特惠商户");
					info.setType("0");
					info.setType("1");
					typeInfos.add(info);
				}
				
				if(infos.getMerchantTypeId().longValue() == 100000002l){
					TypeInfo info = new TypeInfo();
					info.setMerchantTypeId(100000002l);
					info.setTypeName("名优特惠");
					info.setType("2");
					typeInfos.add(info);
				}
					
			}
			if(typeInfos.size() > 0){
				value.put("type_info", JSON.parse(JSonUtil.toJSonString(typeInfos)));
				manager.update(value, where, MongoTableName.CB_MERCHANT_DETAIL, "$set");
				LogCvt.info("商户类型已修改："+detail.getId());
			}
//			break;
		}
	}
	
	

	private static void modifyOutlet() {
		MongoManager manager = new MongoManager();
		DBObject dbo = new BasicDBObject();
		List<OutletDetail> detailList = (List<OutletDetail>) manager.findAll(dbo, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
		
		for(OutletDetail detail : detailList){
			DBObject where = new BasicDBObject();
			where.put("_id", detail.getId());
			
			
			List<TypeInfo> oldTypeInfos = manager.findOneById(detail.getMerchantId(), MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class).getTypeInfo();
			
			DBObject value = new BasicDBObject();
			if(oldTypeInfos.size() > 0){
				value.put("type_info", JSON.parse(JSonUtil.toJSonString(oldTypeInfos)));
				manager.update(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
				LogCvt.info("门店类型已修改："+detail.getId());
			}
//			break;
		}
	}
	
	
	
	private static void modifyOutlet11() {
		MongoManager manager = new MongoManager();
		DBObject dbo = new BasicDBObject();
		List<OutletDetail> detailList = (List<OutletDetail>) manager.findAll(dbo, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
		
		for(OutletDetail detail : detailList){
			LogCvt.info("修改ID："+detail.getId());
			DBObject where = new BasicDBObject();
			where.put("_id", detail.getId());
			
			List<CategoryInfo> categoryList = detail.getCategoryInfo();
			boolean isChange = false;
			for(CategoryInfo categoryInfo : categoryList){
				if(categoryInfo.getCategoryId()==107){
					categoryInfo.setName("其它");
					isChange = true;
				}
			}
			
			if(categoryList!=null && categoryList.size() > 0 && isChange){
				DBObject value = new BasicDBObject();
				value.put("category_info", JSON.parse(JSonUtil.toJSonString(categoryList)));
				manager.update(value, where, MongoTableName.CB_OUTLET_DETAIL, "$set");
				LogCvt.info("门店类型已修改："+detail.getId());
//				break;
			}
		}
	}
	
	
}

