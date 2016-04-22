
	 /**
  * 文件名：CashPointsRatioMapperTest.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.mapper;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.persistent.api.CashPointsRatioMapper;
import com.froad.fft.persistent.api.impl.CashPointsRatioMapperImpl;
import com.froad.fft.persistent.api.impl.FundsChannelMapperImpl;
import com.froad.fft.persistent.entity.CashPointsRatio;
import com.froad.fft.persistent.entity.ClientChannel;
import com.froad.fft.persistent.entity.FundsChannel;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午2:19:48 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class CashPointsRatioMapperTest {

	@Resource
	CashPointsRatioMapperImpl cashPointsRatioMapperImpl;
	
	CashPointsRatio cashPointsRatio=new CashPointsRatio();
	
	@Test
	public void add(){
		cashPointsRatio.setCreateTime(new Date());
		cashPointsRatio.setUpdateTime(new Date());
		cashPointsRatio.setFftPoints("2");
		cashPointsRatio.setBankPoints("2");
		cashPointsRatio.setSysClientId(123124L);
		long id=cashPointsRatioMapperImpl.saveCashPointsRatio(cashPointsRatio);
		System.out.println(id);
	}
	
	@Test
	public void select(){
		cashPointsRatio=cashPointsRatioMapperImpl.selectBySysClientId(123124L);
		System.out.println(JSON.toJSONString(cashPointsRatio));

	}
	
	@Test
	public void update(){
		cashPointsRatio.setCreateTime(new Date());
		cashPointsRatio.setUpdateTime(new Date());
		cashPointsRatio.setFftPoints("3");
		cashPointsRatio.setBankPoints("3");
		cashPointsRatio.setId(1L);
//		cashPointsRatio.setSysClientId(123124L);
		Boolean flag=cashPointsRatioMapperImpl.updateCashPointsRatioById(cashPointsRatio);
		System.out.println(flag);
	}
	
}
