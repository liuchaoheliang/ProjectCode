package com.froad.fft.test.mapper;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.persistent.api.impl.SysClientMapperImpl;
import com.froad.fft.persistent.entity.SysClient;


	 /**
 * 文件名：SysClientMapperTest.java
 * 版本信息：Version 1.0
 * 日期：2014年3月28日
 * author: 刘超 liuchao@f-road.com.cn
 */

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午2:11:53 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class SysClientMapperTest {
	@Resource
	SysClientMapperImpl sysClientMapperImpl;
	
	@Test
	public void select(){
		SysClient sysClient=sysClientMapperImpl.selectByNumber("210");
		System.out.println(JSON.toJSONString(sysClient));		
	}
}
