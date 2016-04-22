package com.froad.db.mongo;

import com.froad.po.mongo.PointSettlementDetailMongo;

public interface PointSettlementDetailMongoService {
	
	/**
	 * 查询最大的结算时间
	 * Function : queryMaxSettlementTime<br/>
	 * 2015年12月24日 上午11:12:32 <br/>
	 *  
	 * @author KaiweiXiang
	 * @return
	 */
	public long findMaxSettlementTime();

	/**
	 * 添加几分结算明细
	 * Function : addPointSettlementDetail<br/>
	 * 2015年12月24日 下午2:12:40 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param objMongo
	 * @return
	 */
	public boolean addPointSettlementDetail(PointSettlementDetailMongo objMongo);

	/**
	 * 根据结算记录查询
	 * Function : findBySettlementId<br/>
	 * 2015年12月24日 下午2:19:15 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param settlementId
	 * @return
	 */
	public PointSettlementDetailMongo findBySettlementId(String settlementId,long settlementTime);

	
}

