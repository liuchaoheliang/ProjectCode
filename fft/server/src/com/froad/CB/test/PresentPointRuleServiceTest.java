package com.froad.CB.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.froad.CB.dao.impl.PresentPointRuleDaoImpl;
import com.froad.CB.po.PresentPointRule;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class PresentPointRuleServiceTest {
	@Resource
	private PresentPointRuleDaoImpl pointRuleDaoImpl;
	
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
	
	
	
	@Test
	public void add(){
		PresentPointRule presentPointRule=new PresentPointRule();
		presentPointRule.setGoodName("测试商品");
		presentPointRule.setGoodExchangeRackId("1231231234");
		presentPointRule.setPointValue("120");
		presentPointRule.setActiveTime(sdf.format(new Date()));
		presentPointRule.setExpireTime(sdf.format(new Date()));
		System.out.println(pointRuleDaoImpl.addPresentPointRule(presentPointRule));
		
	}
		
	@Test
	public void select(){
//		PresentPointRule presentPointRule=new PresentPointRule();
//		presentPointRule.setGoodExchangeRackId("123124124");
//		presentPointRule.setState(1);
//		PresentPointRule list=pointRuleDaoImpl.getByPresentPointRulePager(presentPointRule);
//		presentPointRule.setId(100000001);
//		presentPointRule=pointRuleDaoImpl.getById(100000001);
		List<PresentPointRule> list= pointRuleDaoImpl.getByRackId("1231231234");
		System.out.println(list.size());
//		System.out.println(JSONObject.toJSONString(presentPointRule));
//		System.out.println(list.getList().size());
		
	}
	
	@Test
	public void update(){
		PresentPointRule presentPointRule=new PresentPointRule();
		presentPointRule.setId(100000001);
		presentPointRule.setState(0);
		System.out.println(pointRuleDaoImpl.updatePresentPointRuleById(presentPointRule));
	}
}
