
	 /**
  * 文件名：TestCashPointsRatio.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.remoteservice;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.api.service.CashPointsRatioExportService;
import com.froad.fft.api.service.impl.CashPointsRatioExportServiceImpl;
import com.froad.fft.dto.CashPointsRatioDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午3:40:35 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		}) 
public class TestCashPointsRatio {
	@Resource
	CashPointsRatioExportServiceImpl cashPointsRatioExportServiceImpl;
	
	CashPointsRatioDto cashPointsRatioDto=new CashPointsRatioDto();
	
	@Test
	public void add (){
		cashPointsRatioDto.setBankPoints("3");
		cashPointsRatioDto.setFftPoints("2");
		cashPointsRatioDto.setSysClientId(12121L);
		long id=cashPointsRatioExportServiceImpl.addCashPointsRatio(null, null, cashPointsRatioDto);
		System.out.println(id);
	}
	
	@Test
	public  void update(){
		cashPointsRatioDto.setId(2L);
		cashPointsRatioDto.setBankPoints("4");
		cashPointsRatioDto.setFftPoints("4");
		cashPointsRatioDto.setSysClientId(12121L);
		boolean flag=cashPointsRatioExportServiceImpl.updateCashPointsRatioById(null, null, cashPointsRatioDto);
		System.out.println(flag);
	}
	
	
	
	@Test
	public void select (){
		cashPointsRatioDto=cashPointsRatioExportServiceImpl.selectBySysClientId(null, null, 2L);
		System.out.println(JSON.toJSONString(cashPointsRatioDto));
		
	}
}
