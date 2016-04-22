package com.froad.db.mongo;

import java.util.List;

import com.froad.po.mongo.SubOrderMongo;
import com.mongodb.DBObject;

public interface SubOrderMongoService {
	
	/**
	 * 根据条件查询
	 * @Title: findByCondition 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param query
	 * @return
	 * @throws
	 */
	List<SubOrderMongo> findByCondition(DBObject query);
	
	/**
	 * 根据条件查询
	 * @Title: findByCondition 
	 * @Description: TODO
	 * @author: froad-lf 2015年5月19日
	 * @param String
	 * @return
	 * @throws
	 */
	List<SubOrderMongo> findByCondition(String clientId_orderId);
	
	/**
	 * 原子性修改
	 * @Title: modifySubOrder 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param setObj
	 * @param where
	 * @param modifier
	 * @return
	 * @throws
	 */
	Boolean modifySubOrder(DBObject setObj, DBObject where, String modifier);
	
	/**
	 * 查询单个
	 * @Title: findSubOrderMongo 
	 * @Description: TODO
	 * @author: lf 2015年6月28日
	 * @param subOrderId
	 * @return SubOrderMongo
	 * @throws
	 */
	SubOrderMongo findSubOrderMongo(String subOrderId);
}
