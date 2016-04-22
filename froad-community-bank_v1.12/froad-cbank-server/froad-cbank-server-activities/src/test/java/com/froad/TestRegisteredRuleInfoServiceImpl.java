/**
 * @Title: TestRegisteredRuleInfoServiceImpl.java
 * @Package com.froad
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月1日
 * @version V1.0
**/

package com.froad;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.froad.enums.ActiveAwardType;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveType;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveTagRelation;
import com.froad.thrift.service.RegisteredRuleInfoService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.ActiveTagRelationVo;
import com.froad.thrift.vo.active.RegisteredDetailRuleVo;
import com.froad.thrift.vo.active.RegisteredRuleInfoVo;

 /**
 * @ClassName: TestRegisteredRuleInfoServiceImpl
 * @Description: TODO
 * @author froad-Joker 2015年12月1日
 * @modify froad-Joker 2015年12月1日
 */

public class TestRegisteredRuleInfoServiceImpl {
	public static void main(String[] args) throws TException {
		TSocket transport = new TSocket("localhost", 16501);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		
		TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"RegisteredRuleInfoService");
		RegisteredRuleInfoService.Client service = new RegisteredRuleInfoService.Client(mp);
		
		transport.open();
		
		OriginVo originVo = new OriginVo();
		RegisteredRuleInfoVo registeredRuleInfoVo = new RegisteredRuleInfoVo();
		ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
		//==================
		
		
		
		activeBaseRule.setClientId("chongqing");
		//activeBaseRule.setActiveId("chongqing-ZC-2015-001");
		activeBaseRule.setActiveName("注册");
		//activeBaseRule.setSettleType("1");
		//activeBaseRule.setStatus("1");
		//activeBaseRule.setLimitType("1");
		//activeBaseRule.setBankRate(33000);
		//activeBaseRule.setFftRate(33000);
		//activeBaseRule.setMerchantRate(34000);
		//activeBaseRule.setExpireStartTime(System.currentTimeMillis()-5*24*3600);
     	//activeBaseRule.setExpireEndTime(System.currentTimeMillis()+5*24*3600);
		//activeBaseRule.setType(ActiveType.registerGive.getCode());
		//activeBaseRule.setDescription("test  register");
		//activeBaseRule.setOperator("yff1202");
	
		
		ActiveTagRelationVo activeTagRelation = new ActiveTagRelationVo();
		//activeTagRelation.setActiveId("actTest");
		activeTagRelation.setClientId("chongqing");
		activeTagRelation.setItemId("");
		activeTagRelation.setItemType("0");//for error
		
		
		RegisteredDetailRuleVo registeredDetailRule = new RegisteredDetailRuleVo();
		registeredDetailRule.setClientId("chongqing");
		registeredDetailRule.setTriggerType(false);
		registeredDetailRule.setAwardType(ActiveAwardType.vouchers.getCode());
		registeredDetailRule.setAwardCount(2);
		registeredDetailRule.setIsTotalDay(true);
		registeredDetailRule.setTotalDay(10);
		registeredDetailRule.setTotalCount(20);
		registeredDetailRule.setIsAwardCre(false);
		
		registeredRuleInfoVo.setActiveBaseRule(activeBaseRule);
		registeredRuleInfoVo.setActiveTagRelation(activeTagRelation);
		registeredRuleInfoVo.setRegisteredDetailRule(registeredDetailRule);
		
		// 新增
		//service.addRegisteredRuleInfo(originVo, registeredRuleInfoVo);
		
		//======================================== find one===========================
		//service.getRegisteredRuleInfoById("chongqing", "chongqing-ZC-2015-001");
		
		//====================================disabled ========================================		
		//service.disableRegisteredRuleInfo(originVo, "chongqing", "chongqing-ZC-2015-001", "abc");
		
		//更新
		//service.updateRegisteredRuleInfo(originVo, registeredRuleInfoVo);
		
		// 
		PageVo page = new PageVo();
		//service.getRegisteredRuleInfoByPage(page, registeredRuleInfoVo);
		//下载
		service.exportRegisteredRuleInfoInfoResUrl(registeredRuleInfoVo);
	}

}
