package com.froad.fft.test.mapper;

	 

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.persistent.api.impl.MerchantAccountMapperImpl;
import com.froad.fft.persistent.api.impl.WithdrawPointsRuleMapperImpl;
import com.froad.fft.persistent.entity.WithdrawPointsRule;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 上午11:28:41 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class WithdrawPointsRuleMapperTest {

	@Resource
	WithdrawPointsRuleMapperImpl withdrawPointsRuleMapperImpl;
	
	@Test
	public void add(){
		WithdrawPointsRule withdrawPointsRule=new WithdrawPointsRule();
		withdrawPointsRule.setCreateTime(new Date());
		withdrawPointsRule.setUpdateTime(new Date());
		withdrawPointsRule.setName("name");
		withdrawPointsRule.setIsEnabled(true);
		withdrawPointsRule.setBankFeeRule("bankFeeRule");
		withdrawPointsRule.setFftFeeRule("fftFeeRule");
		withdrawPointsRule.setClientId(1212312313L);
		Long id=withdrawPointsRuleMapperImpl.saveWithdrawPointsRule(withdrawPointsRule);
		System.out.println(id);
	}
	
	
	
	@Test
	public void select(){
		WithdrawPointsRule withdrawPointsRule=new WithdrawPointsRule();
		withdrawPointsRule=withdrawPointsRuleMapperImpl.selectWithdrawPointsRuleById(1L);
		System.out.println(JSON.toJSONString(withdrawPointsRule));
	}
	
	@Test
	public void update(){
		WithdrawPointsRule withdrawPointsRule=new WithdrawPointsRule();
		withdrawPointsRule.setId(1L);
		withdrawPointsRule.setCreateTime(new Date());
		withdrawPointsRule.setUpdateTime(new Date());
		withdrawPointsRule.setName("rename");
		withdrawPointsRule.setIsEnabled(false);
		withdrawPointsRule.setBankFeeRule("rebankFeeRule");
		withdrawPointsRule.setFftFeeRule("refftFeeRule");
		withdrawPointsRule.setClientId(1111111222L);
		boolean flag=withdrawPointsRuleMapperImpl.updateWithdrawPointsRuleById(withdrawPointsRule);
		System.out.println(flag);
	}
}
