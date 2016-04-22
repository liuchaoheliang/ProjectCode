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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.froad.common.enums.ProductQuerySort;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.enums.DeliveryType;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Area;
import com.froad.po.ProductFilter;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductOutlet;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.QueryProductFilterVo;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;


/**
 * 商品mongo操作
 * @author wangyan
 *
 */
public class ProductDetialMongo {
	private MongoManager manager = new MongoManager();
	
	/**
	 * VIP预售推荐列表
	 * @param mongoPage
	 * @param product
	 * @return MongoPage
	 */
	public MongoPage queryVipPresellProducts(MongoPage mongoPage, ProductFilter product){
	    Integer pageSize = mongoPage.getPageSize();
        pageSize = null == pageSize ? 3 : pageSize; // 默认3条
        
        int start = (mongoPage.getPageNumber() - 1) * pageSize;
        
        // 管道集
        List<DBObject> pipeline = new ArrayList<DBObject>();
        
        /**
         *  需要查询的字段
         */
        BasicDBObject pro = new BasicDBObject();
        pro.put("_id", "$_id");
        pro.put("name", "$name");
        pro.put("price", "$price");
        pro.put("market_price", "$market_price");
        pro.put("sell_count", "$sell_count");
        pro.put("start_time", "$start_time");
        pro.put("end_time", "$end_time");
        pro.put("full_name", "$full_name");
        pro.put("brief_introduction", "$brief_introduction");
        pro.put("image_info", "$image_info");
        pro.put("vip_price", "$vip_price");
        pro.put("rack_time", "$rack_time");
        List<String> orArray = new ArrayList<String>();
        orArray.add("$vip_price");
        orArray.add("$price");
        pro.put("you_rate" , new BasicDBObject("$divide", JSON.toJSON(orArray)));
        
        //排序
        DBObject sort = new BasicDBObject();
        sort.put("you_rate", 1);//VIP会员价/普通会员价，数值越小，折扣越大，表示越优惠
        sort.put("vip_price", -1);//优惠相同，VIP价最高的排最前
        sort.put("rack_time", -1);//若VIP价也相同，则最新上架的商品排最前。
        
        /**
         * 1.数据取自精品预售商品数据；
         * 2.优先取“正在预售”中的商品，取最优惠的前三个商品，“最优惠”定义为：VIP会员价/普通会员价，数值越小，折扣越大，表示越优惠；若折扣率相同，则VIP价最高的排最前；若VIP价也相同，则最新上架的商品排最前。
         * 3.若正在预售中的商品取出来后不足3个，则有几个展示几个；
         * 4.若没有正在预售中的商品，则取“下期预售”中的商品，并按照最优惠定义展示前3个；
         */
        BasicDBObject query = new BasicDBObject();
        query.append("client_id", product.getClientId());
        query.append("product_type", product.getType());
        query.append("is_marketable", product.getIsMarketable());
        query.put("vip_price", new BasicDBObject(QueryOperators.GT,0));
        query.put("price", new BasicDBObject(QueryOperators.NE,0));
        if(product.getAreaId()!=null && product.getAreaId()>0){//预售商品可以自提的对应的区域   
        	
        	//区域查询条件
        	List<DBObject> queryOrArray = new ArrayList<DBObject>();
            queryOrArray.add(new BasicDBObject("delivery_option",  new BasicDBObject(QueryOperators.NE, DeliveryType.take.getCode())));
            DBObject aa = new BasicDBObject("delivery_option",  DeliveryType.take.getCode());
            
            Long cityId = 0L;//市id
            CommonLogic comLogic = new CommonLogicImpl();
            Area area = comLogic.findAreaById(product.getAreaId());
            if(area!=null){
                String areaTreePath = area.getTreePath();
                if(Checker.isNotEmpty(areaTreePath)){
                    String[] treePtah = areaTreePath.split(",");
                    if(treePtah.length==2){
                    	cityId = product.getAreaId();//区id
                    }
                }
            }
            if(cityId>0){//市id
            	aa.put("city_areas.city_id", product.getAreaId());
            } else {
            	aa.put("city_areas.countys.area_id", product.getAreaId());
            }
            queryOrArray.add(aa);                  
            query.put("$or", JSON.toJSON(queryOrArray));
        }
        
        Long now = new Date().getTime();
        //正在预售
        query.put("start_time", new BasicDBObject(QueryOperators.LTE,now));
        query.put("end_time", new BasicDBObject(QueryOperators.GTE,now));
       
        
        int count = 0;
        // 总记录数
        if (null == mongoPage.getTotalCount() || mongoPage.getTotalCount() == 0){
            count = manager.getCount(query,  MongoTableName.CB_PRODUCT_DETAIL);
        }
        
        if(count<=0){
        	query.put("start_time", new BasicDBObject(QueryOperators.GT,now));
            query.put("end_time", new BasicDBObject(QueryOperators.LTE,GoodsConstants.FUTURE_END_DATE.getTime()));
            count = manager.getCount(query,  MongoTableName.CB_PRODUCT_DETAIL);
        }
        mongoPage.setTotalCount(count);
        if(count<=0){
            return mongoPage;
        }
        pipeline.add(new BasicDBObject("$match", query));
        pipeline.add(new BasicDBObject("$project", pro));
        pipeline.add(new BasicDBObject("$sort", sort));
        pipeline.add(new BasicDBObject("$skip", start));
        pipeline.add(new BasicDBObject("$limit",mongoPage.getPageSize()));
        
        List<ProductDetail> myResults = manager.findByPipeline(pipeline, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
        if(myResults!=null && myResults.size()>0){//查询下期预售
        	mongoPage.build(myResults);
        }
        mongoPage.build(myResults);
        return mongoPage;
	}
	
	public List<String> queryGroupProductMercantIds(ProductFilter product){
	    // 管道集
	    List<DBObject> pipeline = new ArrayList<DBObject>();
        /**
         *  联合查询条件
         */
        BasicDBObject query = new BasicDBObject();
        if(product.getClientId()!=null && !"".equals(product.getClientId())){
            query.put("client_id", product.getClientId());
        }
        query.put("product_type", ProductType.group.getCode());
        query.put("is_marketable", ProductStatus.onShelf.getCode());//已经上架的查询出来
        
        
        if(product.getName()!=null && !"".equals(product.getName()) 
                && product.getFullName()!=null && !"".equals(product.getFullName())){

            StringBuilder nameRegexStr = new StringBuilder(".*");
            nameRegexStr.append(product.getName()).append(".*");
            Pattern namePattern = Pattern.compile(nameRegexStr.toString(), Pattern.CASE_INSENSITIVE);
            
            StringBuilder fullNameRegexStr = new StringBuilder(".*");
            fullNameRegexStr.append(product.getFullName()).append(".*");
            Pattern fullNamePattern = Pattern.compile(fullNameRegexStr.toString(), Pattern.CASE_INSENSITIVE);
            
            List<DBObject> orArray = new ArrayList<DBObject>();
            orArray.add(new BasicDBObject("name", ""));
            orArray.add(new BasicDBObject("full_name", ""));
            
            JSONArray jsonOrArray = (JSONArray)JSON.toJSON(orArray);
            for(Object ob : jsonOrArray){
                JSONObject jb = (JSONObject)ob;
                for (String key : jb.keySet()) {
                    if(key.equals("name")){
                        BasicDBObject nameLike = new BasicDBObject();
                        nameLike.append("$regex", namePattern);
                        jb.put(key, nameLike);
                    } else if(key.equals("full_name")){
                        BasicDBObject fullNameLike = new BasicDBObject();
                        fullNameLike.append("$regex", fullNamePattern);
                        jb.put(key, fullNameLike);
                    }
                }
            }
            query.put("$or", jsonOrArray);
        } else {
            if(product.getName()!=null && !"".equals(product.getName())){
                StringBuilder regexStr = new StringBuilder( ".*");
                regexStr.append(product.getName()).append(".*");
                Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
                BasicDBObject like = new BasicDBObject();
                like.append("$regex", pattern);
                query.put("name", like);
            }
            
            if(product.getFullName()!=null && !"".equals(product.getFullName())){
                StringBuilder regexStr = new StringBuilder( ".*");
                regexStr.append(product.getFullName()).append(".*");
                Pattern pattern = Pattern.compile(regexStr.toString(), Pattern.CASE_INSENSITIVE);
                BasicDBObject like = new BasicDBObject();
                like.append("$regex", pattern);
                query.put("full_name", like);
            }
        }
        
        /*设置查询条件*/
        if(product.getCategoryId()>0){
            //商品分类查询条件
            query.put("product_category_info.product_category_id", product.getCategoryId());
        }
        query.put("end_time", new BasicDBObject(QueryOperators.GTE, new Date().getTime())); // 结束时间大于等于当前时间,过了团购期商品不显示
        
        pipeline.add(new BasicDBObject("$match", query));
        
        //排序
        Sort sort = new Sort("sell_count", OrderBy.DESC).on("start_time", OrderBy.DESC);
        List<String> merchantIds = queryProductMercantIds(query,sort);
        return merchantIds;
	}
	
	
	
	public List<String> queryProductMercantIds(BasicDBObject query,Sort sort){
	    // 管道集
	    List<DBObject> pipeline = new ArrayList<DBObject>();
        /**
         *  联合查询条件
         */
        pipeline.add(new BasicDBObject("$match", query));
        
        DBObject group = new BasicDBObject();
        group.put("_id", "$merchant_id");
        DBObject orderBy = sort.getSortObject();
        pipeline.add(new BasicDBObject("$group", group));
        pipeline.add(new BasicDBObject("$sort", orderBy));
        
        List<ProductDetail> pds = manager.findByPipeline(pipeline, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
        List<String> merchantIds = new ArrayList<String>();
        for(ProductDetail pd : pds){
            merchantIds.add(pd.getId());
        }
        return merchantIds;
	}
	
	/**
	 * 类目营销里的特惠商品列表查询
	 * @param filterVo
	 * @param fs
	 * @param merchantIds
	 * @param mongoPage
	 * @return
	 */
	public List<ProductDetail> queryGroupProducts(QueryProductFilterVo filterVo,FiledSort fs,Set<String> merchantIds,MongoPage mongoPage){
		// 管道集
	    List<DBObject> pipeline = new ArrayList<DBObject>();
	    
	    /**
         *  需要查询的字段
         */
        BasicDBObject pro = new BasicDBObject();
        pro.put("_id", "$_id");//商品id
        pro.put("client_id", "$client_id");//客户端id
        pro.put("name", "$name");//商品名
        pro.put("price", "$price");//销售价
        pro.put("market_price", "$market_price");//市场价
        pro.put("sell_count", "$sell_count");//销售数量
        pro.put("start_time", "$start_time");//销售期开始
        pro.put("end_time", "$end_time");//销售期结束
        pro.put("full_name", "$full_name");//商品全名
        pro.put("brief_introduction", "$brief_introduction");//简介
        pro.put("image_info", "$image_info");//图片地址
        pro.put("rack_time", "$rack_time");
        pro.put("is_seckill", "$is_seckill");//是否秒杀 0非秒杀,1秒杀,2秒杀未上架
        pro.put("merchant_id", "$merchant_id");//商户ID
        List<String> addArray = new ArrayList<String>();
        addArray.add("$price");
        addArray.add("$market_price");
        pro.put("dis_count" , new BasicDBObject("$divide", JSON.toJSON(addArray)));
        
        
        /**
         *  查询条件
         */
        BasicDBObject query = groupProductQueryDBObject(filterVo);
        
        //排序
        DBObject sort = new BasicDBObject();
        
        Integer sortBy = 1;
        if(fs.getSortBy() < 0){// 排序规则 负数代表降序，整数代表升序
        	sortBy = -1;
        } else {
        	sortBy = 1;
        }
        if(ProductQuerySort.distance.getCode().equals(fs.getSortName())){//距离我最近排序
        	//商户ids in 查询
            query.put("merchant_id", new BasicDBObject("$in", merchantIds));
            
            //-距我最近排序查询 ：类目商品按照所在门店距离由近至远进行排序，如距离相同按照商品销售数量由高到低排序，如销售数量相同则按商品上架时间由远至近排序
            sort.put("merchant_id", sortBy);//类目商品按照所在门店距离由近至远进行排序
            sort.put("sell_count", -1);//如距离相同按照商品销售数量由高到低排序
            sort.put("rack_time", 1);//如销售数量相同则按商品上架时间由远至近排序
    	} else if(ProductQuerySort.sellCount.getCode().equals(fs.getSortName())){//销量排序
    		//销量优先：在列表中，类目商品按照商品销售数量由高到低进行排序，如商品销售数量相同则按照商品折扣由高到低排序，如商品销售总星数相同则按照商品上架时间由远至近排序。
    		sort.put("sell_count", sortBy);//如距离相同按照商品销售数量由高到低排序
    		sort.put("dis_count", 1);//类目商品按照所在门店距离由近至远进行排序
            sort.put("rack_time", 1);//如销售数量相同则按商品上架时间由远至近排序
    	} else if(ProductQuerySort.price.getCode().equals(fs.getSortName())){//价格排序
    		//价格最低：在列表中，类目商品按照商品价格由低至高进行排序，如商品价格相同则按照销售数量由高到低排序。如销售数量相同则按照商品上架时间由远至近排序。
    		sort.put("price", sortBy);//类目商品价格由低至高进行排序
            sort.put("sell_count", -1);//如距离相同按照商品销售数量由高到低排序
            sort.put("rack_time", 1);//如销售数量相同则按商品上架时间由远至近排序
    	} else if(ProductQuerySort.disCount.getCode().equals(fs.getSortName())){//折扣排序
    		//折扣最大：在列表中，类目商品按照商品折扣率（商品售价/商品原价）由低到高进行排序，如商品折扣率相同则按照销量由高至低排序，如销量相同则按照商品上架时间由远至近排序。（只在3.4生效时显示）
    		sort.put("dis_count", sortBy);//类目商品折扣率（商品售价/商品原价）由低到高进行排序
            sort.put("sell_count", -1);//如距离相同按照商品销售数量由高到低排序
            sort.put("rack_time", 1);//如销售数量相同则按商品上架时间由远至近排序
    	}
        
        query.put("market_price", new BasicDBObject(QueryOperators.NE,0));
        
        //查询前组装
        pipeline.add(new BasicDBObject("$match", query));
        pipeline.add(new BasicDBObject("$project", pro));
        pipeline.add(new BasicDBObject("$sort", sort));
        
        if(ProductQuerySort.distance.getCode().equals(fs.getSortName())){//距离我最近排序
        	List<ProductDetail> myResults = manager.findByPipeline(pipeline, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
        	//类目商品按照所在门店距离由近至远进行排序，如距离相同按照商品销售数量由高到低排序，如销售数量相同则按商品上架时间由远至近排序
            List<ProductDetail> pds = new ArrayList<ProductDetail>();
        	if(myResults!=null && myResults.size()>0){
        		Map<String,List<ProductDetail>> pdMap = new HashMap<String,List<ProductDetail>>();
            	for(ProductDetail pd : myResults){
            		if(pdMap.get(pd.getMerchantId())==null){
            			pdMap.put(pd.getMerchantId(), new ArrayList<ProductDetail>());
            		}
            		pdMap.get(pd.getMerchantId()).add(pd);
            	}
            	for(String merchantId : merchantIds){
                	if(pdMap.get(merchantId)!=null){
                		pds.addAll(pdMap.get(merchantId));
                	}
                }
        	}
        	return pds;
        } else {
            int start = (mongoPage.getPageNumber() - 1) * mongoPage.getPageSize();
            
        	pipeline.add(new BasicDBObject("$skip", start));
            pipeline.add(new BasicDBObject("$limit",mongoPage.getPageSize()));
            List<ProductDetail> myResults = manager.findByPipeline(pipeline, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            return myResults;
        }
	}
	
	/**
	 * 生成类目营销里的特惠商品列表的mongo查询条件
	 * @param filterVo
	 * @return
	 */
	public BasicDBObject groupProductQueryDBObject(QueryProductFilterVo filterVo){
		/**
         *  查询条件
         */
        BasicDBObject query = new BasicDBObject();
        query.put("client_id", filterVo.getClientId());
        query.put("product_type", ProductType.group.getCode());
        query.put("is_marketable", ProductStatus.onShelf.getCode());//已经上架的查询出来
        
        //根据查询条件传进来的areaid判断是市级地区id还是区级地区id
        Long cityId = null;//市级地区id
        Long areaId = null;//区级地区id
        if(filterVo.getAreaId()>0){
        	areaId = filterVo.getAreaId();
            CommonLogic comLogic = new CommonLogicImpl();
            Area area = comLogic.findAreaById(filterVo.getAreaId());
            if(area!=null){
                String areaTreePath = area.getTreePath();
                if(Checker.isNotEmpty(areaTreePath)){
                    String[] treePtah = areaTreePath.split(",");
                    if(treePtah.length==2){//areaId为市
                    	cityId = filterVo.getAreaId();
                    } 
                }
            }
        }
        
        if(cityId!=null && cityId>0){//市级地区id
        	query.put("city_areas.city_id", cityId);
        } else if(areaId!=null && areaId>0){//区级地区id
        	query.put("city_areas.countys.area_id", areaId);     
        }
        //商品名称模糊查询
        if(filterVo.getProductName()!=null && !"".equals(filterVo.getProductName())){

            StringBuilder nameRegexStr = new StringBuilder(".*");
            nameRegexStr.append(filterVo.getProductName()).append(".*");
            Pattern namePattern = Pattern.compile(nameRegexStr.toString(), Pattern.CASE_INSENSITIVE);
            
            StringBuilder fullNameRegexStr = new StringBuilder(".*");
            fullNameRegexStr.append(filterVo.getProductName()).append(".*");
            Pattern fullNamePattern = Pattern.compile(fullNameRegexStr.toString(), Pattern.CASE_INSENSITIVE);
            
            List<DBObject> orArray = new ArrayList<DBObject>();
            orArray.add(new BasicDBObject("name", ""));
            orArray.add(new BasicDBObject("full_name", ""));
            
            JSONArray jsonOrArray = (JSONArray)JSON.toJSON(orArray);
            for(Object ob : jsonOrArray){
                JSONObject jb = (JSONObject)ob;
                for (String key : jb.keySet()) {
                    if(key.equals("name")){
                        BasicDBObject nameLike = new BasicDBObject();
                        nameLike.append("$regex", namePattern);
                        jb.put(key, nameLike);
                    } else if(key.equals("full_name")){
                        BasicDBObject fullNameLike = new BasicDBObject();
                        fullNameLike.append("$regex", fullNamePattern);
                        jb.put(key, fullNameLike);
                    }
                }
            }
            query.put("$or", jsonOrArray);
        }
        
        /*设置查询条件*/
        if(filterVo.getProductCategoryId()>0){
            //商品分类查询条件
            query.put("product_category_info.product_category_id", filterVo.getProductCategoryId());
        }
        query.put("end_time", new BasicDBObject(QueryOperators.GTE, new Date().getTime())); // 结束时间大于等于当前时间,过了团购期商品不显示
        return query;
	}
	
	/**
	 * 查询类目营销里的特惠商品列表总条数
	 * @param filterVo
	 * @return
	 */
	public int queryGroupProductCount(QueryProductFilterVo filterVo){
		/**
         *  查询条件
         */
        BasicDBObject query = groupProductQueryDBObject(filterVo);
        
        return manager.getCount(query, MongoTableName.CB_PRODUCT_DETAIL);
	}
	
	/**
	 * 查询团购商品的一条门店的结构
	 * @param where
	 * @return
	 */
	public Map<String,Object> queryGroupProductOneOutlet(DBObject where){
		Map<String,Object> p = new HashMap<String,Object>();
        
        // 管道集
	    List<DBObject> pipeline = new ArrayList<DBObject>();
	    
	    /**
         *  需要查询的字段
         */
        BasicDBObject pro = new BasicDBObject();
        pro.put("_id", "$_id");//商品id
        pro.put("merchant_id", "$merchant_id");//商户id
        pro.put("org_outlets", "$org_outlets");//市门店
        
        
        //排序
        DBObject sort = new BasicDBObject();
        sort.put("create_time", -1);
        
        //查询前组装
        pipeline.add(new BasicDBObject("$match", where));
        pipeline.add(new BasicDBObject("$project", pro));
        pipeline.add(new BasicDBObject("$sort", sort));
        pipeline.add(new BasicDBObject("$unwind", "$org_outlets"));
        pipeline.add(new BasicDBObject("$skip", 0));
        pipeline.add(new BasicDBObject("$limit",1));
        
        ProductDetail pd  = null;
		DBCollection dbCollection = null;
		AggregationOutput aggOutput = null;
		Iterator<DBObject> it = null;
		try {
			dbCollection = manager.getReadDBCollection(MongoTableName.CB_PRODUCT_DETAIL);
			dbCollection.setOptions(Bytes.QUERYOPTION_SLAVEOK);// 读写分离，读从服务器，写主服务器
			
			aggOutput = dbCollection.aggregate(pipeline);
			if (null != aggOutput && null != aggOutput.results()){
				it = aggOutput.results().iterator();
				while (it.hasNext()){
					DBObject ob = it.next();
					pd = new ProductDetail();
					pd.setId(ob.get("_id").toString());
					pd.setMerchantId(ob.get("merchant_id").toString());
					p.put("pd", pd);
					
					// Convert DBObject to JSON string
					String jsonStr = com.mongodb.util.JSON.serialize(ob);
			        String outletIdStr = "\"outlet_id\" : \""+where.get("org_outlets.org_outlets.outlet_id")+"\"";
			        
			        int i = jsonStr.indexOf(outletIdStr);
					if(i!=-1){
						String outletBefore = jsonStr.substring(0, i);
						int ii = outletBefore.lastIndexOf("{");
						String outletAfter = jsonStr.substring(i+outletIdStr.length());
						int iii = outletAfter.indexOf("}");
						String outletStr = jsonStr.substring(ii, i+outletIdStr.length()+iii+"}".length());
						
						ProductOutlet po = JSonUtil.toObject(outletStr, ProductOutlet.class);
						p.put("po", po);
					}
				}
			}

		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			MongoManager.closeConnection();
		}
        return p;
	}
	
	/**
	 * 精品商城商品列表
	 * @param query 查询条件
	 * @param pageNumber 页码
	 * @param pageSize 每页列数
	 * @param needGoodsNum
	 * @param fs 排序条件
	 * @return
	 */
	public List<ProductDetail> queryBoutiqueProducts(final DBObject query, final int pageNumber, final int pageSize, final int needGoodsNum, final FiledSort fs) {
        int start = (pageNumber - 1) * pageSize;
        
		// 管道集
        List<DBObject> pipeline = new ArrayList<DBObject>();
		/**
         *  需要查询的字段
         */
        BasicDBObject pro = new BasicDBObject();
        pro.put("_id", "$_id");
        pro.put("name", "$name");
        pro.put("full_name", "$full_name");
        pro.put("price", "$price");
        pro.put("vip_price", "$vip_price");
        pro.put("sell_count", "$sell_count");
        pro.put("brief_introduction", "$brief_introduction");
        pro.put("image_info", "$image_info");
        pro.put("rack_time", "$rack_time");
        pro.put("is_recommend", "$is_recommend");
		pro.put("is_new", "$is_new");
		pro.put("is_hot", "$is_hot");
		pro.put("is_seckill", "$is_seckill");
		
		//排序
	    DBObject sort = new BasicDBObject();
	    
	    Integer sortBy = 1;
	    if(fs!=null && fs.getSortBy() < 0){// 排序规则 负数代表降序，整数代表升序
	    	sortBy = -1;
	    }
	    if(fs!=null && ProductQuerySort.sellCount.getCode().equals(fs.getSortName())){//销售排序
    		//根据商品总销售数量由高到低进行排序；
	    	sort.put("sell_count", sortBy);
    		//销量相同VIP售价由低到高进行排序(升序)
	    	sort.put("vip_price", 1);
    		//VIP售价也相同时以上架时间排序，最新上架的排前（降序）
	  		sort.put("rack_time", -1);
    	} else if(fs!=null && ProductQuerySort.price.getCode().equals(fs.getSortName())){//价格排序
    		//1.	以VIP售价作为排序字段；
    		//2.	默认以售价由低到高进行排序（三角形的箭头此时朝上），再次点击后变为以售价由高到低进行排序（三角形的箭头此时朝下）；
    		sort.put("vip_price", sortBy);
    		//3.	售价相同则以销量由高到低进行排序，销量也相同时以上架时间排序，最新上架的排前；
    		sort.put("sell_count", -1);//（降序）
    		sort.put("rack_time", -1);//（降序）
    	} else if(fs!=null && ProductQuerySort.hensive.getCode().equals(fs.getSortName())){//综合排序
    		//默认的排序方式
    		//以商品标签“推荐”进行优先排列，若多个商品存在“荐”情况下根据销量由高到低排序，若销量也相同则根据上架时间排序，最新上架的排前；
    		sort.put("is_recommend", -1);//（降序）
    		//根据商品总销售数量由高到低进行排序；（降序）
	    	sort.put("sell_count", -1);
    		//销量也相同则根据上架时间排序，最新上架的排前；
	  		sort.put("rack_time", -1);
    	} else if(fs!=null && ProductQuerySort.recommend.getCode().equals(fs.getSortName())){//推荐优先  精品商城个人H5用到 我的VIP页面里的为您推荐用到
    		//1.数据取自精品商城商品数据；
    		//2.取最优惠的前三个商品，“最优惠”定义为：VIP会员价/普通会员价，数值越小，折扣越大，表示越优惠；
    		//若折扣率相同，则VIP价最高的排最前；若VIP价也相同，则有“推荐”标签的商品排在最前，
    		//若有多个推荐标签商品，则最新上架的商品排最前。
    		List<String> orArray = new ArrayList<String>();
    		orArray.add("$vip_price");
    		orArray.add("$price");
    		pro.put("you_rate" , new BasicDBObject("$divide", JSON.toJSON(orArray)));
    		
    		sort.put("you_rate", 1);//VIP会员价/普通会员价，数值越小，折扣越大，表示越优惠
            sort.put("vip_price", -1);//优惠相同，VIP价最高的排最前
            sort.put("is_recommend", -1);//（降序）
            sort.put("rack_time", -1);//若VIP价也相同，则最新上架的商品排最前。
    	} else {
    		//根据商品总销售数量由高到低进行排序；（降序）
	    	sort.put("sell_count", -1);
//    		//销量相同VIP售价由低到高进行排序(升序)
//	    	sort.put("vip_price", 1);
    		//VIP售价也相同时以上架时间排序，最新上架的排前（降序）
	  		sort.put("rack_time", -1);
    	}
	    pipeline.add(new BasicDBObject("$match", query));
	    pipeline.add(new BasicDBObject("$project", pro));
	    pipeline.add(new BasicDBObject("$sort", sort));
	    pipeline.add(new BasicDBObject("$skip", start));
	    pipeline.add(new BasicDBObject("$limit", needGoodsNum));
	    
        List<ProductDetail> pds = manager.findByPipeline(pipeline, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
        return pds;
	}
	
}

