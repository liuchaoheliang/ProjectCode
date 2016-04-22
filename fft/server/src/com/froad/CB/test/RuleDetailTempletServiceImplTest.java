package com.froad.CB.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.RuleDetail;
import com.froad.CB.po.RuleDetailTemplet;
import com.froad.CB.service.impl.RuleDetailTempletServiceImpl;
import com.froad.CB.service.impl.TransRuleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class RuleDetailTempletServiceImplTest {

	@Autowired
	RuleDetailTempletServiceImpl ruleDetailTempletServiceImpl;
	static Integer ruleDetailTempletPkid;
	@Test
	public void testAddTransRuleDetailTemplet() throws Exception {
		RuleDetailTemplet ruleDetailTemplet=new RuleDetailTemplet();
		ruleDetailTemplet.setRemark("TEST INSERT");
		ruleDetailTempletPkid=ruleDetailTempletServiceImpl.addRuleDetailTemplet(ruleDetailTemplet);
		Assert.assertNotNull(ruleDetailTempletPkid);
	}

	@Test
	public void testUpdateTransRuleDetailTemplet() {
		RuleDetailTemplet ruleDetailTemplet=new RuleDetailTemplet();
		ruleDetailTemplet.setId(ruleDetailTempletPkid);
		ruleDetailTemplet.setRemark("update INSERT");
		boolean result=ruleDetailTempletServiceImpl.updateRuleDetailTemplet(ruleDetailTemplet);
		Assert.assertTrue(result);
	}

}
