package com.froad.CB.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.common.Command;
import com.froad.CB.common.constant.RuleType;
import com.froad.CB.po.RuleDetail;
import com.froad.CB.po.TransRule;
import com.froad.CB.service.impl.TransRuleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TransRuleServiceImplTest {
	@Resource
	TransRuleServiceImpl transRuleServiceImpl;
	static Integer rulePkid;
	static Integer ruleDetailPkid;
//	@Test
	public void testAddTranRuleDetail() {
		RuleDetail ruleDetail=new RuleDetail();
		ruleDetail.setRemark("TEST INSERT");
		ruleDetail.setRuleType("01");
		ruleDetailPkid=transRuleServiceImpl.addTranRuleDetail(ruleDetail);
		Assert.assertNotNull(ruleDetailPkid);
	}

	@Test
	public void testAddTransRule() {
		//test  打折
//		TransRule rule=new TransRule();
//		rule.setRuleType(RuleType.PREFERENTIAL.getValue());
//		rule.setState(Command.STATE_START);
//		rule.setCurrencyFormula("8");
//		rule.setFftFactorageFormula("2");
//		rule.setRemark("打8折——test");
//		try {
//			rulePkid=transRuleServiceImpl.addTransRule(rule);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//测试 赠送分分通积分
		TransRule pointsRule=new TransRule();
		pointsRule.setRuleType(RuleType.POINTS.getValue());
		pointsRule.setState(Command.STATE_START);
		pointsRule.setPointsFormula("70");
		pointsRule.setFftFactorageFormula("5");
		pointsRule.setRemark("赠送交易的70%的分分通积分，且购买分分通积分的手续费为5%——test");
		try {
			rulePkid=transRuleServiceImpl.addTransRule(pointsRule);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(rulePkid);
	}

//	@Test
	public void testGetTransRules() {
		TransRule con=new TransRule();
		con.setRuleType("01");
		List result=transRuleServiceImpl.getTransRules(con);
		Assert.assertNotNull(result);
	}

//	@Test
	public void testUpdateTranRuleDetail() {
		RuleDetail ruleDetail=new RuleDetail();
		ruleDetail.setId(ruleDetailPkid);
		ruleDetail.setRemark("TEST update");
		boolean result=transRuleServiceImpl.updateTranRuleDetail(ruleDetail);
		Assert.assertTrue(result);
	}

	@Test
	public void testUpdateTransRule() {
		TransRule con=new TransRule();
		con.setState("40");
		con.setId(100001045);
		boolean result=transRuleServiceImpl.updateTransRule(con);
		System.out.println(result+"====================");
		Assert.assertTrue(result);
	}

}
