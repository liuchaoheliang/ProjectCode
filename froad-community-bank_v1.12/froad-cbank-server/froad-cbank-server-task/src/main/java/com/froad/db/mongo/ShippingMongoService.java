package com.froad.db.mongo;

import java.util.List;

import com.froad.po.mongo.ShippingOrderMongo;
import com.mongodb.DBObject;

public interface ShippingMongoService {
	
	/**
	 * 
	 * @Title: findByCondition 
	 * @Description: 根据条件查询收货信息
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param query
	 * @return
	 * @throws
	 */
	List<ShippingOrderMongo> findByCondition(DBObject query);
	
	/**
	 * 
	 * @Title: modifyShipping 
	 * @Description: 更新收货信息
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param set
	 * @param where
	 * @param modifier
	 * @return
	 * @throws
	 */
	Boolean modifyShipping(DBObject set, DBObject where, String modifier);
}
