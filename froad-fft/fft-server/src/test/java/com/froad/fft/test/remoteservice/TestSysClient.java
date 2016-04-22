
	 /**
  * 文件名：TestSysClient.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.remoteservice;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.api.service.impl.RefundsExportServiceImpl;
import com.froad.fft.api.service.impl.SysClientExportServiceImpl;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午2:32:16 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		}) 
public class TestSysClient {
	@Resource
	SysClientExportServiceImpl sysClientExportServiceImpl;
	
	
	@Test
	public void select(){
		System.out.println(JSON.toJSONString(sysClientExportServiceImpl.findSysClientDtoByNumber("210")));
	}
}
