/**
 * @Title: TestRegisterRun.java
 * @Package com.froad
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月11日
 * @version V1.0
**/

package com.froad;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.froad.thrift.service.RegisteredRunService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.RegisteredHandselVo;
import com.froad.thrift.vo.active.RegisteredRuleInfoVo;

 /**
 * @ClassName: TestRegisterRun
 * @Description: TODO
 * @author froad-Joker 2015年12月11日
 * @modify froad-Joker 2015年12月11日
 */

public class TestRegisterRun {
	public static void main(String[] args) throws TException {
		TSocket transport = new TSocket("localhost", 16501);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		
		TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"RegisteredRunService");
		RegisteredRunService.Client service = new RegisteredRunService.Client(mp);
		
		transport.open();
		
		OriginVo originVo = new OriginVo();
		RegisteredRuleInfoVo registeredRuleInfoVo = new RegisteredRuleInfoVo();
		ActiveBaseRuleVo activeBaseRule = new ActiveBaseRuleVo();
		
		RegisteredHandselVo registeredHandselVo = new RegisteredHandselVo();
		registeredHandselVo.setClientId("jilin");
		registeredHandselVo.setLoginId("liuchao");
		//registeredHandselVo.setMemberCode(52001976971L);
		
		service.registeredHandsel(registeredHandselVo);
		
	}
}
