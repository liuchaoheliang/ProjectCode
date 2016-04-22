package com.froad.db.mongo;

import java.util.List;



import com.froad.po.mongo.MerchantOutletFavorite;
import com.mongodb.DBObject;

public interface MerchantOutletService {
	
	MerchantOutletFavorite findByCondition(DBObject query);

}

