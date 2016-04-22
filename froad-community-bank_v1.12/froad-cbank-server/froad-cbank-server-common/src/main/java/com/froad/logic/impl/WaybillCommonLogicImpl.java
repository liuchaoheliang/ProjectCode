/**
 * Project Name:froad-cbank-server-common
 * File Name:WaybillCommonLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年11月26日下午5:51:13
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;


import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.WayBillCommonMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.WaybillCommonLogic;
import com.froad.po.Waybill;

/**
 * ClassName:WaybillCommonLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月26日 下午5:51:13
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public class WaybillCommonLogicImpl implements WaybillCommonLogic{

	@Override
	public Waybill findBySubOrderId(String subOrderId) {
		Waybill result = new Waybill(); 
		SqlSession sqlSession = null;
		WayBillCommonMapper waybillCommonMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			waybillCommonMapper = sqlSession.getMapper(WayBillCommonMapper.class);

			result = waybillCommonMapper.findDeliveryWayBill(subOrderId);
		} catch (Exception e) { 
			LogCvt.error("查询 Waybill运营订单物流信息失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

}
