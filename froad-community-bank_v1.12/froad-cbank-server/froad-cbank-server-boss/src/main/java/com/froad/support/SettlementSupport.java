/**
 * Project Name:froad-cbank-server-boss
 * File Name:SettlementSupport.java
 * Package Name:com.froad.support
 * Date:2015年8月6日下午2:55:34
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.support;

import com.froad.db.mongo.page.MongoPage;
import com.froad.po.settlement.Settlement;
import com.froad.po.settlement.SettlementReq;

/**
 * 结算 <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月6日 下午2:55:34
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public interface SettlementSupport {

	/**
	 * 结算分页查询
	 * 
	 * @Title: queryByPage
	 * @Description: TODO
	 * @author zachary.wang 2015年3月28日
	 * @param req
	 *            结算查询条件
	 * @return MongoPage
	 */
	public MongoPage queryByPage(SettlementReq req);

	/**
	 * 查询详情
	 * 
	 * @Title: queryById
	 * @Description: TODO
	 * @author zachary.wang
	 * @param settlementId
	 * @return Settlement
	 */
	public Settlement queryById(String settlementId);

	/**
	 * 更新结算ID
	 * 
	 * @Title: upateById
	 * @Description: TODO
	 * @author zachary.wang
	 * @param settlementId
	 * @param code
	 * @param remark
	 * @return boolean
	 */
	public boolean upateById(String settlementId, String status, String remark);

}
