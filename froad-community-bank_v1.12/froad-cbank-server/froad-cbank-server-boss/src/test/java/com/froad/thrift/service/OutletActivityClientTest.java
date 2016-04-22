/**
 * Project Name:froad-cbank-server-boss
 * File Name:OutletActivityClientTest.java
 * Package Name:com.froad.thrift.service
 * Date:2015年10月30日下午1:59:23
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.vo.businesszonetag.GenerateActivityNoReq;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoRes;
import com.froad.util.PropertiesUtil;

/**
 * ClassName:OutletActivityClientTest
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月30日 下午1:59:23
 * @author   asus
 * @version  
 * @see 	 
 */
public class OutletActivityClientTest {
	public static void main(String[] args) throws TException {
		PropertiesUtil.load();
		//本地测试
		TSocket transport = new TSocket("127.0.0.1", 16001);
		transport.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, BusinessZoneTagService.class.getSimpleName());
		BusinessZoneTagService.Iface businessZoneTagService = new BusinessZoneTagService.Client(multiplexedProtocol);
		GenerateActivityNoRes res = generateActivityNo(businessZoneTagService);
		
	}

	public static GenerateActivityNoRes generateActivityNo(BusinessZoneTagService.Iface businessZoneTagService) throws TException {
		GenerateActivityNoReq req = new GenerateActivityNoReq();
		req.setActivityType("2");
		req.setClientId("anhui");
		GenerateActivityNoRes res = businessZoneTagService.generateActivityNo(req);
		System.out.println(res.getResultVo());
		System.out.println(res.getActiviyNo());
		return res;
		
	}
}
