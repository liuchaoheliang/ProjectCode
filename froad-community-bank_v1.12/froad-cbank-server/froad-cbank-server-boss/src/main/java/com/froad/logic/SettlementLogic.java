/**
 * Project Name:froad-cbank-server-boss
 * File Name:SettlementLogic.java
 * Package Name:com.froad.logic.impl
 * Date:2015年8月6日下午3:26:53
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.logic;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.SettlementStatus;
import com.froad.po.settlement.SettlementReq;

/**
 * ClassName: SettlementLogic <br/>
 * Reason: 结算逻辑 <br/>
 * Date: 2015年8月6日 下午3:26:53
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public interface SettlementLogic {

	/**
	 * 结算记录分页查询
	 * 
	 * @Title: querySettlementByPage
	 * @Description: TODO
	 * @author: zachary.wang 2015年8月6日 下午3:26:53
	 * @param req 结算查询条件
	 * @return List<Settlement>
	 * @throws
	 */
	public MongoPage querySettlementByPage(SettlementReq req);
	
	/**
	 *  更新结算状态
	  * @Title: updateSettlement
	  * @Description: TODO
	  * @author: zachary.wang 2015年8月11日 下午6:01:53
	  * @param id
	  * @param status
	  * @param remark
	  * @return ResultBean
	 */
	public ResultBean updateSettlement(String id, SettlementStatus status, String remark);
	

}
