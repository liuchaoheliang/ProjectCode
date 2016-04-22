package com.froad.db.mongo.impl;

import com.froad.db.mongo.PointSettlementDetailMongoService;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.po.mongo.PointSettlementDetailMongo;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBObject;

public class PointSettlementDetailMongoServiceImpl implements PointSettlementDetailMongoService {

	private MongoManager manager = new MongoManager();

	@Override
	public long findMaxSettlementTime() {

		MongoPage pageParam = new MongoPage();
		pageParam.setSort(new Sort("create_time", OrderBy.DESC));
		
		//当前页数
		pageParam.setPageNumber(1);
		pageParam.setPageSize(1);
			
		MongoPage m = manager.findByPage(pageParam, new BasicDBObject(), MongoTableName.CB_POINT_SETTLEMENT_DETAIL, PointSettlementDetailMongo.class);
		if(m != null && m.getItems() != null && m.getItems().size() > 0){
			PointSettlementDetailMongo pm =  (PointSettlementDetailMongo)m.getItems().get(0);
			return pm.getCreateTime();
		}
		return 0;
	}
	
	@Override
	public boolean addPointSettlementDetail(PointSettlementDetailMongo objMongo) {
		return manager.add(objMongo, MongoTableName.CB_POINT_SETTLEMENT_DETAIL) != -1;
	}
	
	@Override
	public PointSettlementDetailMongo findBySettlementId(String settlementId,long settlementTime) {

		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("settlement_id", settlementId);
		if(settlementTime > 0){
			dbObject.put("create_time", settlementTime);
		}
		
		PointSettlementDetailMongo psdm = manager.findOne(dbObject, MongoTableName.CB_POINT_SETTLEMENT_DETAIL,PointSettlementDetailMongo.class);
		
		return psdm;
	}
}

