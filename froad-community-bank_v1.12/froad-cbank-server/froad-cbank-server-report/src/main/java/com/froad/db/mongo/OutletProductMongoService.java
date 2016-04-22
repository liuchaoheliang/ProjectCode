package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.po.mongo.OutletProduct;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class OutletProductMongoService {
	
	private MongoManager mongo = new MongoManager();
	
	public Integer getCountGroupByMerchant(){
		Integer count = 0;
		
		List<DBObject> pipeline = new ArrayList<DBObject>();
		
		DBObject group1 = new BasicDBObject();
		group1.put("$group", new BasicDBObject("_id", "$merchant_id"));
		
		DBObject group2 = new BasicDBObject();
		group2.put("$group", new BasicDBObject("_id", null).append("count", new BasicDBObject("$sum", 1)));
		
		pipeline.add(group1);
		pipeline.add(group2);
		
		Iterator<DBObject> it = mongo.findByPipeline(pipeline, MongoTableName.CB_OUTLET_PRODUCT);
		if(it.hasNext()){
			count = Integer.valueOf(it.next().get("count").toString());
		}
		
		return count;
	}
	
	
	public MongoPage findFacesGroupByMerchant(MongoPage page){
		page.setTotalCount(this.getCountGroupByMerchant());
		
		List<DBObject> pipeline = new ArrayList<DBObject>();
		
		DBObject group = new BasicDBObject();
		group.put("$group", new BasicDBObject("_id", "$merchant_id").append("total_products", new BasicDBObject("$sum", 1)));
		pipeline.add(group);
		
		int skip = (page.getPageNumber() - 1)* page.getPageSize();
		pipeline.add(new BasicDBObject("$skip", skip));
		pipeline.add(new BasicDBObject("$limit", page.getPageSize()));
		
		List<OutletProduct> list = mongo.findByPipeline(pipeline, MongoTableName.CB_OUTLET_PRODUCT, OutletProduct.class);
		
		page.build(list);

		return page;
	}
	
}
