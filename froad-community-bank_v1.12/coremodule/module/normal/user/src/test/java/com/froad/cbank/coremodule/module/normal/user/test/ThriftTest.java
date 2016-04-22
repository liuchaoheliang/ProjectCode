package com.froad.cbank.coremodule.module.normal.user.test;
import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.cbank.coremodule.framework.expand.thrift.pool.ConnectionManager;
import com.froad.cbank.coremodule.module.normal.user.support.BaseSupport;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/conf/soft/spring/spring-config.xml","classpath:/conf/soft/spring/spring-expand-thrift.xml"})
public class ThriftTest extends BaseSupport{
	@Resource
	ConnectionManager connectionManager;
	
//	@Resource
//	private HelloService.Iface helloService;
	
	
	@Test
	
	public void Test(){
//		HelloService.Client service1 = new HelloService.Client(getProtocol(tSocket,HelloService.class));
//		HelloService.Client service1 = new HelloService.Client(getProtocol(tSocket,HelloService.class));
		
	
//		try {
//			TMultiplexedProtocol mp1 =getProtocol(HelloService.class);
			
//			HelloService.Client service1 = new HelloService.Client(mp1);
//			helloService.helloString("hello");
//			System.out.println("ok");
//			closeSource(mp1);
//		} catch (TException e) {
//			e.printStackTrace();
//		}	
	}
	
	
}
