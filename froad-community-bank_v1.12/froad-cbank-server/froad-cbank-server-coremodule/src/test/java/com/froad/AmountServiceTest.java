/**
 * Project Name:froad-cbank-server-finity
 * File Name:RoleResourceServiceTest.java
 * Package Name:com.froad.thrift
 * Date:2015��9��23������8:13:36
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad;

import java.util.List;

import org.junit.Test;

import com.froad.logic.ReportLogic;
import com.froad.logic.impl.ReportLogicImpl;
import com.froad.po.req.ReqPo;
import com.froad.po.resp.Resp;


/** 
 * @version 
 */

public class AmountServiceTest {
	
	private  ReportLogic amountLogicLogic=new ReportLogicImpl();
	
 
	@Test
	public void select(){
		ReqPo req=new ReqPo();
		req.setClientId("chongqing");
		req.setOrgCode("022001");
		req.setOrgLevel(3); 
		req.setCheckedAmount(true);
		req.setCountType("day");
		req.setCheckedAmountRefund(true);
		req.setCheckedAmountCumulationTurnover(true);
//		List<AmountResp> list=amountLogicLogic.selectAmount(req);
//		System.out.println(list.size());
//		for(AmountResp r:list){
//			System.out.println(r.getAmountLat().getAmountCumulatiTurnover());
//		}
	}
	
}
