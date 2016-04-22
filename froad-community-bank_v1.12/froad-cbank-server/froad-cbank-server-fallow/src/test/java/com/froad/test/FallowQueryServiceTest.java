/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: Test.java
 * @Package com.froad.test.ticket
 * @Description: TODO
 * @author vania
 * @date 2015年6月8日
 */

package com.froad.test;

import java.net.InetAddress;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.FallowQueryService;
import com.froad.thrift.vo.GetAlreadyAuitListPageVo;
import com.froad.thrift.vo.GetAlreadyAuitListReqVo;
import com.froad.thrift.vo.GetApplyAuitListPageVo;
import com.froad.thrift.vo.GetApplyAuitListReqVo;
import com.froad.thrift.vo.GetInstanceIdByAttrReqVo;
import com.froad.thrift.vo.GetInstanceIdByAttrResVo;
import com.froad.thrift.vo.GetPendAuitListPageVo;
import com.froad.thrift.vo.GetPendAuitListReqVo;
import com.froad.thrift.vo.GetTaskOverviewReqVo;
import com.froad.thrift.vo.GetTaskOverviewResVo;
import com.froad.thrift.vo.GetTaskReqVo;
import com.froad.thrift.vo.GetTaskResVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.util.PropertiesUtil;

/**
 * <p>
 * Title: FallowQueryServiceTest.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年6月8日 下午5:21:01
 */
public class FallowQueryServiceTest {
	static String clientIP = null;
	static TSocket transport = null;
	static FallowQueryService.Client client = null;
	static {
		PropertiesUtil.load();
	}

	@BeforeClass
	public static void init() {
		try {
			String host = "10.43.2.3";
			int port = 0;
			host = "127.0.0.1";
			// host = "10.43.1.9";
//			 host = "10.24.248.186";
			port = 16401;
			transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, FallowQueryService.class.getSimpleName());
			client = new FallowQueryService.Client(mp);
			transport.open();

			clientIP = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void destroy() {
		if (transport != null)
			transport.close();
	}


	
	@Test()
	@Ignore("还没写好!!!")
	public void getTaskOverview() throws TException {
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.bank);
		originVo.setOperatorId(100004933);
		originVo.setOperatorIp(clientIP);
		originVo.setClientId("chongqing");
		originVo.setOrgId("440203");
		originVo.setOperatorUserName("4003026");
		originVo.setDescription("审核概要");

		GetTaskOverviewReqVo req = new GetTaskOverviewReqVo();
		req.setOrigin(originVo);
		req.setInstanceId("10E345E00000");

		GetTaskOverviewResVo res = (GetTaskOverviewResVo) client.getTaskOverview(req);
		Assert.assertNotNull(res);
		System.out.println("getTaskOverview返回结果:" + JSON.toJSONString(res, true));
	}
	
	@Test()
	@Ignore("还没写好!!!")
	public void getTaskList() throws TException {
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.bank);
//		originVo.setOperatorId(100004933);
		originVo.setOperatorIp(clientIP);
		originVo.setClientId("chongqing");
//		originVo.setOrgId("440203");
//		originVo.setOperatorUserName("4003026");
		originVo.setDescription("审核步骤");

		GetTaskReqVo req = new GetTaskReqVo();
		req.setOrigin(originVo);
		req.setInstanceId("117B3A530000");

		GetTaskResVo res = client.getTaskList(req);
		Assert.assertNotNull(res);
		System.out.println("getTaskList返回结果:" + JSON.toJSONString(res, true));
	}
	
	
	
	@Test()
	@Ignore("还没写好!!!")
	public void getPendAuitList() throws TException {
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.bank);
		originVo.setOperatorId(100005112);
		originVo.setOperatorUserName("0293007");
		originVo.setOperatorIp(clientIP);
		originVo.setClientId("chongqing");		
		originVo.setRoleId("100000277");
		originVo.setOrgId("020110");
//		originVo.setDescription("待办箱列表");
		GetPendAuitListReqVo req = new GetPendAuitListReqVo();
		req.setOrigin(originVo);
		req.setProcessTypeDetail("1");
		req.setProcessType("1");
		req.setBessData("{}");
		
//		req.setStarTime(1440929345388L);
//		req.setEndTime(1486929345388L);
		
		 PageVo pageVo = new PageVo();
         pageVo.setPageSize(10);
         pageVo.setPageNumber(1);
         pageVo.setTotalCount(0);
         pageVo.setBegDate(0);
         pageVo.setEndDate(0);
         pageVo.setLastPageNumber(1);
//         pageVo.setFirstRecordTime(1447036857694l);
//         pageVo.setLastRecordTime(1446707583856l);
         pageVo.setHasNext(false);
         

		GetPendAuitListPageVo res = client.getPendAuitList(req, pageVo);
		Assert.assertNotNull(res);
		System.out.println("getPendAuitList返回结果:" + JSON.toJSONString(res, true));
	}
	
	
	@Test()
	@Ignore("还没写好!!!")
	public void getInstanceIdByAttr() throws TException {
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.merchant_h5);
		originVo.setOperatorId(100000000);
		originVo.setOperatorIp(clientIP);
		originVo.setDescription("查询流水号");
		originVo.setClientId("chongqing");
		originVo.setOperatorUserName("张三");
		GetInstanceIdByAttrReqVo req = new GetInstanceIdByAttrReqVo();
		req.setOrigin(originVo);
		req.setBessId("2000000211");

		GetInstanceIdByAttrResVo res = client.getInstanceIdByAttr(req);
		Assert.assertNotNull(res);
		System.out.println("getInstanceIdByAttr返回结果:" + JSON.toJSONString(res, true));
	}
	
	
	@Test()
	@Ignore("还没写好!!!")
	public void getApplyAuitList() throws TException {
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.bank);
		originVo.setOperatorId(100004933);
		originVo.setOperatorIp(clientIP);
		originVo.setClientId("chongqing");
		originVo.setOrgId("440203");
		originVo.setOperatorUserName("4003026");
		originVo.setDescription("待办箱列表");
		
		GetApplyAuitListReqVo req = new GetApplyAuitListReqVo();
		req.setOrigin(originVo);
		
		req.setProcessType("1");
//		req.setBessData("{\"phone\":\"1365455887\"}");
		
//		req.setStarTime(1440929345388L);
//		req.setEndTime(1486929345388L);
		
		 PageVo pageVo = new PageVo();
         pageVo.setPageSize(10);
         pageVo.setPageNumber(1);
         

         GetApplyAuitListPageVo res = client.getApplyAuitList(req, pageVo);
		Assert.assertNotNull(res);
		System.out.println("getApplyAuitList返回结果:" + JSON.toJSONString(res, true));
	}
	
	
	
	
	@Test()
//	@Ignore("还没写好!!!")
	public void getAlreadyAuitList() throws TException {
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.bank);
		originVo.setOperatorId(100005112);
		originVo.setOperatorUserName("0293007");
		originVo.setOperatorIp(clientIP);
		originVo.setClientId("chongqing");		
		originVo.setRoleId("100000277");
		originVo.setOrgId("020110");
//		originVo.setDescription("待办箱列表");
		GetAlreadyAuitListReqVo req = new GetAlreadyAuitListReqVo();
		req.setOrigin(originVo);
		req.setType("1");
		req.setProcessType("1");
		req.setBessData("{}");
		
		req.setOrigin(originVo);
		
//		req.setBessData("{\"phone\":\"1365455887\"}");
		
//		req.setStarTime(1440929345388L);
//		req.setEndTime(1486929345388L);
		
		 PageVo pageVo = new PageVo();
         pageVo.setPageSize(10);
         pageVo.setPageNumber(1);
         

         GetAlreadyAuitListPageVo res = client.getAlreadyAuitList(req, pageVo);
		Assert.assertNotNull(res);
		System.out.println("getAlreadyAuitList返回结果:" + JSON.toJSONString(res, true));
	}
	
}
