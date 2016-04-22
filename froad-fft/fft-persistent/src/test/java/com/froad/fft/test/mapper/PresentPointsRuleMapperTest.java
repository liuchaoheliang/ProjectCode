package com.froad.fft.test.mapper;

	 /**
  * 文件名：PresentPointsRuleMapperTest.java
  * 版本信息：Version 1.0
  * 日期：2014年3月27日
  * author: 刘超 liuchao@f-road.com.cn
  */

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.persistent.api.impl.PresentPointsRuleMapperImpl;
import com.froad.fft.persistent.entity.PresentPointsRule;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 上午11:52:23 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class PresentPointsRuleMapperTest {
	
	@Resource
	PresentPointsRuleMapperImpl presentPointsRuleMapperImpl;
	
	@Test
	public void add(){
		PresentPointsRule presentPointsRule=new PresentPointsRule();
		presentPointsRule.setCreateTime(new Date());
		presentPointsRule.setUpdateTime(new Date());
		presentPointsRule.setName("name");
		presentPointsRule.setMerchantId(123123123L);
		presentPointsRule.setIsEnabled(true);
		presentPointsRule.setRule("rule");
		presentPointsRule.setDescription("description");
		Long id=presentPointsRuleMapperImpl.savePresentPointsRule(presentPointsRule);
		System.out.println(id);
	}
	
	
	@Test
	public void select (){
		PresentPointsRule presentPointsRule=new PresentPointsRule();
		presentPointsRule=presentPointsRuleMapperImpl.selectPresentPointsRuleById(1L);
		System.out.println(JSON.toJSONString(presentPointsRule));
	}
	
	@Test
	public void update(){
		PresentPointsRule presentPointsRule=new PresentPointsRule();
		presentPointsRule.setId(1L);
		presentPointsRule.setCreateTime(new Date());
		presentPointsRule.setUpdateTime(new Date());
		presentPointsRule.setName("rename");
		presentPointsRule.setMerchantId(23232323232L);
		presentPointsRule.setIsEnabled(false);
		presentPointsRule.setRule("rerule");
		presentPointsRule.setDescription("redescription");
		boolean flag=presentPointsRuleMapperImpl.updatePresentPointsRuleById(presentPointsRule);
		System.out.println(flag);
	}
}
