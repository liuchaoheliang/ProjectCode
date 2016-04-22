package com.froad.db.mongo;

import com.froad.db.mongo.page.MongoPage;


public interface SettlementMongoService {
	
	/**
	 * 分页查询指定时间段内积分结算成功的数据
	 * Function : queryByPage<br/>
	 * 2015年12月23日 下午6:06:46 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param page
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public abstract MongoPage queryPointSettlementSuccByPage(MongoPage page,long startTime,long endTime);

	
}

