package com.froad;


import java.util.Date;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.po.ActiveBaseRule;
import com.froad.thrift.service.ActiveRuleInfoService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.ActiveDetailRuleVo;
import com.froad.thrift.vo.active.ActiveRuleInfoVo;
import com.froad.thrift.vo.active.ActiveTagRelationVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.FindPageActiveRuleInfoVoResultVo;

public class TestClient {


	public static void main(String[] args){
		try{
			//TSocket transport = new TSocket("10.24.248.188", 16501);
			TSocket transport = new TSocket("localhost", 16501);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"ActiveRuleInfoService");
			ActiveRuleInfoService.Client service = new ActiveRuleInfoService.Client(mp);
			
			transport.open();
			
			OriginVo originVo = new OriginVo();
			//================================add===================================
			
//			ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
//			activeBaseRule.setClientId("chongqing");
//			//activeBaseRule.setActiveId("actTest");
//			activeBaseRule.setActiveName("测试促销活动8888");
//			activeBaseRule.setSettleType("1");
//			activeBaseRule.setStatus("1");
//			activeBaseRule.setLimitType("1");
//			activeBaseRule.setBankRate(33000);
//			activeBaseRule.setFftRate(33000);
//			activeBaseRule.setMerchantRate(34000);
//			activeBaseRule.setExpireStartTime(System.currentTimeMillis());
//			activeBaseRule.setExpireEndTime(System.currentTimeMillis());
//			activeBaseRule.setType("1");
//			activeBaseRule.setDescription("test");
//			activeBaseRule.setOperator("yff123");
//			
//			ActiveDetailRuleVo activeDetailRule = new ActiveDetailRuleVo();
//			activeDetailRule.setClientId("chongqing");
//			//activeDetailRule.setActiveId("actTest");
//			activeDetailRule.setMinLimit(5000000000L);
//			activeDetailRule.setMaxCount(50);
//			activeDetailRule.setIsPerDay(true);
//			activeDetailRule.setIsPaperPay(true);
//			activeDetailRule.setPerDay(0);
//			activeDetailRule.setPerCount(0);
//			activeDetailRule.setIsTotalDay(true);
//			activeDetailRule.setTotalDay(0);
//			activeDetailRule.setTotalCount(0);
//			activeDetailRule.setIsPrePay(false);
//			activeDetailRule.setRetMoney(5000000000L);
//			activeDetailRule.setMaxMoney(5000000000L);
//			activeDetailRule.setPrePayActiveId("prePayacti");
//			activeDetailRule.setProductId("a,b,c");
//			activeDetailRule.setProductCount(50);
//			activeDetailRule.setPoint(50);
//			activeDetailRule.setPointCount(50);
//			activeDetailRule.setPayMethod("1");
//			
//			ActiveTagRelationVo activeTagRelation = new ActiveTagRelationVo();
//			//activeTagRelation.setActiveId("actTest");
//			activeTagRelation.setClientId("chongqing");
//			activeTagRelation.setItemId("");
//			activeTagRelation.setItemType("0");//for error
//			
//			ActiveRuleInfoVo activeRuleInfo = new ActiveRuleInfoVo();
//			activeRuleInfo.setActiveBaseRule(activeBaseRule);
//			activeRuleInfo.setActiveDetailRule(activeDetailRule);
//			activeRuleInfo.setActiveTagRelation(activeTagRelation);
//			
//			AddResultVo resultVo = service.addActiveRuleInfo(originVo, activeRuleInfo);
//			System.out.println(JSON.toJSONString(resultVo));
			
			//================================disable===================================
//			service.disableActiveRuleInfo(originVo,"test1111.7","11FC61308000","ff333fff");
			
			//=====================================update=================================
//			
			ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
			activeBaseRule.setClientId("chongqing");
			activeBaseRule.setActiveId("chongqing-MJ-2015-003");
			activeBaseRule.setActiveName("actName1007");
			activeBaseRule.setSettleType("2");
			activeBaseRule.setStatus("1");
			activeBaseRule.setLimitType("1");
			activeBaseRule.setBankRate(33000);
			activeBaseRule.setFftRate(33000);
			activeBaseRule.setMerchantRate(34000);
			activeBaseRule.setExpireStartTime(System.currentTimeMillis());
			activeBaseRule.setExpireEndTime(System.currentTimeMillis());
			activeBaseRule.setType("1");
			activeBaseRule.setDescription("test");
			activeBaseRule.setOperator("fffffff");
			
			ActiveDetailRuleVo activeDetailRule = new ActiveDetailRuleVo();
			activeDetailRule.setClientId("chongqing");
			activeDetailRule.setActiveId("chongqing-MJ-2015-003");
			activeDetailRule.setMinLimit(1000);
			activeDetailRule.setMaxCount(500);
			activeDetailRule.setIsPerDay(false);
			activeDetailRule.setIsPaperPay(true);
			activeDetailRule.setPerDay(1);
			activeDetailRule.setPerCount(5);
			activeDetailRule.setIsTotalDay(true);
			activeDetailRule.setTotalDay(20);
			activeDetailRule.setTotalCount(50);
			activeDetailRule.setIsPrePay(false);
			activeDetailRule.setRetMoney(50);
			activeDetailRule.setMaxMoney(500);
			activeDetailRule.setPrePayActiveId("prePayacti");
			activeDetailRule.setProductId("a,b,c");
			activeDetailRule.setProductCount(50);
			activeDetailRule.setPoint(50);
			activeDetailRule.setPointCount(50);
			activeDetailRule.setPayMethod("1");
			
			ActiveTagRelationVo activeTagRelation = new ActiveTagRelationVo();
			activeTagRelation.setActiveId("chongqing-MJ-2015-003");
			activeTagRelation.setClientId("chongqing");
			activeTagRelation.setItemId("");
			activeTagRelation.setItemType("0");//for error
			
			ActiveRuleInfoVo activeRuleInfo = new ActiveRuleInfoVo();
			activeRuleInfo.setActiveBaseRule(activeBaseRule);
			activeRuleInfo.setActiveDetailRule(activeDetailRule);
			activeRuleInfo.setActiveTagRelation(activeTagRelation);
			
			System.out.println(activeRuleInfo.getActiveBaseRule());
			System.out.println(activeRuleInfo.getActiveDetailRule());
			System.out.println(activeRuleInfo.getActiveTagRelation());
			ResultVo resultVo = service.updateActiveRuleInfo(originVo, activeRuleInfo);
			//AddResultVo resultVo = service.addActiveRuleInfo(originVo, activeRuleInfo);
			System.out.println(JSON.toJSONString(resultVo));			
//			
			//=====================================one record=================================
			
//			FindActiveRuleInfoVoResultVo vo = service.getActiveRuleInfoById(null, "11FC3FE48000");
//			if(			vo.getActiveRuleInfoVo() != null)
//			{
//				System.out.println(vo.getActiveRuleInfoVo().getActiveBaseRule());
//				System.out.println(vo.getActiveRuleInfoVo().getActiveDetailRule());
//				System.out.println(vo.getActiveRuleInfoVo().getActiveTagRelation());
//			}


//			
			//====list
//			PageVo page = new PageVo();
//			ActiveRuleInfoVo activeRuleInfoVo = new ActiveRuleInfoVo();
//			ActiveBaseRuleVo activeBaseRule1 = new ActiveBaseRuleVo();
//			activeBaseRule1.setClientId("chongqing");
			//满减测试
			//activeBaseRule1.setActiveName("chongqing-MJ-2015-002");
			//activeBaseRule1.setActiveName("ee-MJ-2015-006");
			//activeBaseRule1.setStatus("4");
			//20151108162822
			long end = new Date().getTime();
			long start = new Date().getTime();
			//System.out.println(aa);
			//activeBaseRule1.setExpireEndTime(end);
			//activeBaseRule1.setExpireStartTime(start);
 			
//			activeRuleInfoVo.setActiveBaseRule(activeBaseRule1);
// 			FindPageActiveRuleInfoVoResultVo vo = service.getActiveRuleInfoByPage(page, activeRuleInfoVo);
// 			System.out.println(vo.getActiveRuleInfoPageVoRes().getActiveRuleInfoVoList().size());
// 			for(ActiveRuleInfoVo v1 : vo.getActiveRuleInfoPageVoRes().getActiveRuleInfoVoList())
// 			{
// 				System.out.println(v1.getActiveBaseRule().getActiveId());
// 			}
			
			//=============================================
//			ActiveBaseRuleVo base = new ActiveBaseRuleVo();
//			ActiveDetailRuleVo detail = new ActiveDetailRuleVo();
//			base.setActiveId("chongqing-MJ-2015-003");
//			//base.setActiveId("0007");
//			base.setClientId("chongqing");
//			
//			detail.setIsPrePay(true);
//			ActiveRuleInfoVo activeRuleInfoVo = new ActiveRuleInfoVo();
//			activeRuleInfoVo.setActiveBaseRule(base);
//			activeRuleInfoVo.setActiveDetailRule(detail);
//			service.exportActiveOrderInfoUrl(activeRuleInfoVo);
			
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
