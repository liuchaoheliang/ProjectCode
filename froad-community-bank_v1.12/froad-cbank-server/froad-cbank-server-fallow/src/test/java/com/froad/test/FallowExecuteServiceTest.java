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
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.vo.CreateInstanceReqVo;
import com.froad.thrift.vo.CreateInstanceResVo;
import com.froad.thrift.vo.ExecuteTaskReqVo;
import com.froad.thrift.vo.ExecuteTaskResVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.util.PropertiesUtil;

/**
 * <p>
 * Title: FallowExecuteServiceTest.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年6月8日 下午5:21:01
 */

public class FallowExecuteServiceTest {
	static String  clientIP = "";
	static TSocket transport = null;
	static FallowExecuteService.Client client = null;
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

			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, FallowExecuteService.class.getSimpleName());
			client = new FallowExecuteService.Client(mp);
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
//	@Ignore("还没写好!!!")
	public void createInstance() throws TException {
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.bank);
		originVo.setOperatorId(100004933);
		originVo.setOperatorIp(clientIP);
		originVo.setDescription("启动流程");
		originVo.setClientId("chongqing");
		originVo.setOrgId("440203");
		originVo.setOperatorUserName("4003026");
		
		CreateInstanceReqVo req = new CreateInstanceReqVo();
		req.setOrigin(originVo);
//		req.setProcessId("100000000");
		req.setBessId("0000000010");
//		req.setOrgCode("000000");
		req.setProcessType("1");
		req.setProcessTypeDetail("0");
		req.setBessData("{\"merchantID\":\"5009\",\"merchantName\":\"商户名称9ijuwsx\",\"phone\":\"1361233333\",\"type\":\"1\",\"coagyTpye\":{\"name\":\"汽车asd\",code:1009}}");
		req.setOrgCode("340101");
		
		CreateInstanceResVo res = client.createInstance(req);
		Assert.assertNotNull(res);
		System.out.println("createInstance返回结果:" + JSON.toJSONString(res, true));

	}

	@Test()
	@Ignore("还没写好!!!")
	public void executeTask() throws TException {
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.merchant_h5);
		originVo.setClientId("chongqing");
		originVo.setOperatorId(100004921);
		originVo.setOperatorUserName("4001001");
		originVo.setOperatorIp(clientIP);
		originVo.setDescription("执行审核");
		originVo.setOrgId("440000");

		ExecuteTaskReqVo req = new ExecuteTaskReqVo();
		req.setOrigin(originVo);
		req.setTaskId("10E69C130001");
		req.setInstanceId("10E69C130000");
		req.setBessId("2000000211");
		req.setAuditState("2"); // // 审核状态:1审核通过;2审核不通过
		req.setRemark("执行审核");

		ExecuteTaskResVo res = client.executeTask(req);
		Assert.assertNotNull(res);
		System.out.println("executeTask返回结果:" + JSON.toJSONString(res, true));
	}
}
