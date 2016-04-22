/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.ProductCategoryInfo;
import com.froad.po.mongo.ProductDetail;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


/**
 * 商品mongo操作
 * @author wangyan
 *
 */
public class ProductDetialMongo {
	private MongoManager manager = new MongoManager();
	
	private static final String COMMAND_SET = "$set";
	private static final String MONGO_KEY_ID = "_id";
	
	/**
     * 新加商品存储到mongo中
     * @param productInfo
     * @return boolean
     */
    public boolean addProductDetail(ProductDetail productDetail) {
        boolean isSuccess = false;
        
        if(productDetail!=null){
            isSuccess = manager.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL) > -1;// 向mongodb插入数据
        }
        return isSuccess;
    }
    
    /**
     * 更新商品时候需要更新商品mongo表
     * @param productDetail
     * @param isUpdate
     * @return boolean
     */
    public boolean updateProductDetail(ProductDetail productDetail,boolean isUpdate) {
        boolean isSuccess = false;
        try{
            if(isUpdate){
                DBObject where = new BasicDBObject();
                where.put("_id", productDetail.getId());
                Object whereObject = where;
                isSuccess =  manager.update(productDetail, whereObject, MongoTableName.CB_PRODUCT_DETAIL, "$set")> -1;// 向mongodb更新数据
                
                boolean isUnset = false;
                DBObject valueObj = new BasicDBObject();
                DBObject w1 = new BasicDBObject();
                if(productDetail.getBuyLimit()==null){
                    //删除buy_limit这个字段值
                    w1.put("buy_limit", 1);
                    isUnset = true;
                }
                if(productDetail.getCityAreas()==null || productDetail.getCityAreas().size()==0){//市级-区关系
                    //删除city_areas这个字段值
                    w1.put("city_areas", 1);
                    isUnset = true;
                }
                if(productDetail.getOrgOutlets()==null || productDetail.getOrgOutlets().size()==0){//市级-门店关系
                	//删除org_outlets这个字段值
                    w1.put("org_outlets", 1);
                    isUnset = true;
                }
                if(productDetail.getOrgCodes()==null || productDetail.getOrgCodes().size()==0){//商品对应网店所属的机构代码列表
                	//删除org_codes这个字段值
                    w1.put("org_codes", 1);
                    isUnset = true;
                }
                if(productDetail.getActivitiesInfo()==null || productDetail.getActivitiesInfo().size()==0){//商品活动
                	//删除activities_info这个字段值
                    w1.put("activities_info", 1);
                    isUnset = true;
                }
                if(productDetail.getProductCategoryInfo()==null || productDetail.getProductCategoryInfo().size()==0){// 商品分类
                	//删除product_category_info这个字段值
                    w1.put("product_category_info", 1);
                    isUnset = true;
                }
                if(isUnset){
                	valueObj.put("$unset", w1);
                    manager.update(valueObj, where, MongoTableName.CB_PRODUCT_DETAIL, null);//更新mongo vip_price buy_limit和is_limit
                }
            } else {
                isSuccess = manager.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL) > -1;// 向mongodb插入数据
            }    
        } catch (Exception e) { 
            LogCvt.error("boss修改ProductDetail失败，原因:" + e.getMessage(),e); 
        }
        return isSuccess;
    }
	/**
	 * 更新商品详情表信息
	 * @Title: auditProduct 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param audit
	 * @return
	 * @throws
	 */
    public  Boolean auditProduct(ProductDetail audit){
		DBObject where = new BasicDBObject();
		where.put("_id", audit.getId());
		DBObject value = new BasicDBObject();
		value.put("is_marketable", audit.getIsMarketable());
		int result = manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
		return result != -1;
	}
    
    /**
	 * 查询商品ProductCategoryInfo
	 * @Title: findProductCategoryInfo 
	 * @author liuyanyun
	 * @version 1.0
	 * @see: TODO
	 * @param product_id
	 * @return
	 * @return List<ProductCategoryInfo>    返回类型 
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<ProductCategoryInfo> findProductCategoryInfo(String product_id){
		BasicDBObject productId = new BasicDBObject();
		productId.put(MONGO_KEY_ID, product_id);
		
		List<String> fieldNames = new ArrayList<String>();
		fieldNames.add("product_category_info");
		List<ProductDetail> pd = (List<ProductDetail>) manager.findAllByFieldNames(productId, MongoTableName.CB_PRODUCT_DETAIL, fieldNames, ProductDetail.class);
		if(CollectionUtils.isEmpty(pd)){
			return null;
		}
		return pd.get(0).getProductCategoryInfo();
	}
	
	/**
	 * 
	 * updateMerchantCategoryById:(修改商户分类信息).
	 *
	 * @author huangyihao
	 * 2015年11月2日 下午3:45:43
	 * @param merchantId
	 * @param categoryInfoList
	 * @return
	 *
	 */
	public boolean updateProductCategoryById(String productId,List<ProductCategoryInfo> categoryInfo) {
		DBObject where = new BasicDBObject();
		where.put(MONGO_KEY_ID, productId);
		
		DBObject value = new BasicDBObject();
		if(Checker.isNotEmpty(categoryInfo)){
			value.put("product_category_info", (DBObject) com.mongodb.util.JSON.parse(JSonUtil.toJSonString(categoryInfo)));
		}
		manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, COMMAND_SET);
		return true;
	}	
}

