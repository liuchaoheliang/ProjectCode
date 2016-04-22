package com.froad;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.junit.Test;

import com.froad.thrift.service.ActiveSustainRelationService;
import com.froad.thrift.service.VouchersRuleInfoService;
import com.froad.thrift.service.VouchersSearchService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.TemporaryVouchersDetailInfoVO;

import junit.framework.TestCase;

public class TestSuaActive extends TestCase {
	public static void main(String[] args) throws TException {
		/***********************************************/
		TSocket transport = new TSocket("localhost", 16501);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);

		TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,
				"VouchersRuleInfoService");
		VouchersRuleInfoService.Client service = new VouchersRuleInfoService.Client(
				mp);

		TMultiplexedProtocol checkmp = new TMultiplexedProtocol(protocol,
				"ActiveSustainRelationService");
		ActiveSustainRelationService.Client serClient = new ActiveSustainRelationService.Client(
				checkmp);

		transport.open();
		try {
			PageVo page = new PageVo();
			// 红包规则
			ActiveBaseRuleVo baseRule = new ActiveBaseRuleVo();
			baseRule.setClientId("chongqing");
			//baseRule.setActiveId("chongqing-HB-2015-017");
			baseRule.setStatus("3");
			/*baseRule.setActiveName("红包Q");
			baseRule.setActiveLogo("");
			baseRule.setBankRate(60000);*/			
			/*baseRule.setCreateTime(createTime2);
			baseRule.setDescription("红包Q");
			baseRule.setExpireStartTime(currentTime1);
			baseRule.setExpireEndTime(currentTime);
			baseRule.setFftRate(20000);
			baseRule.setLimitType("1");
			baseRule.setMerchantRate(20000);
			baseRule.setOperator("abc");
			
			baseRule.setType("1");
			baseRule.setUpdateTime(createTime2);
			baseRule.setSettleType("1");*/
			//serClient.findPromotionActiveByPage(page, baseRule);
			PageVo pageVo = new PageVo();
			serClient.findPromotionActiveByPage(pageVo, baseRule);
		} catch (TException e) {
			e.printStackTrace();
		}

		transport.close();
	}
}
