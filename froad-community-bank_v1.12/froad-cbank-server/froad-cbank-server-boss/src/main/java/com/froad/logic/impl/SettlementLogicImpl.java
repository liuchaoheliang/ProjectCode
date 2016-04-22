/**
 * Project Name:froad-cbank-server-boss
 * File Name:SettlementLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年8月6日下午3:27:50
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.logic.impl;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.SettlementLogic;
import com.froad.po.settlement.Settlement;
import com.froad.po.settlement.SettlementReq;
import com.froad.support.SettlementSupport;
import com.froad.support.impl.SettlementSupportImpl;

/**
 * ClassName: SettlementLogicImpl <br/>
 * Reason: 结算逻辑实现 <br/>
 * Date: 2015年8月6日 下午3:27:50
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class SettlementLogicImpl implements SettlementLogic {

	private SettlementSupport settlementSupport = new SettlementSupportImpl();

	@Override
	public MongoPage querySettlementByPage(SettlementReq req) {
		return settlementSupport.queryByPage(req);
	}

	@Override
	public ResultBean updateSettlement(String id, SettlementStatus status, String remark) {
		LogCvt.info("BOSS修改结算状态，id=" + id + ", status=" + status.getCode());
		Settlement settlement = settlementSupport.queryById(id);
		if (settlement == null) {
			return new ResultBean(ResultCode.settlement_not_exists);
		}

		boolean flag = settlementSupport.upateById(settlement.getId(), status.getCode(), remark);
		if (flag) {
			return new ResultBean(ResultCode.success);
		} else {
			return new ResultBean(ResultCode.failed, "更新结算记录失败");
		}

	}
}
