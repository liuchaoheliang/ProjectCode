
	 /**
  * 文件名：WithdrawPointsRuleMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月26日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.WithdrawPointsRuleMapper;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.WithdrawPointsRule;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月26日 下午8:44:40 
 */
public class WithdrawPointsRuleMapperImpl implements WithdrawPointsRuleMapper {

	@Resource WithdrawPointsRuleMapper withdrawPointsRuleMapper;

	@Override
	public Long saveWithdrawPointsRule(WithdrawPointsRule withdrawPointsRule) {
		withdrawPointsRuleMapper.saveWithdrawPointsRule(withdrawPointsRule);
		return withdrawPointsRule.getId();
	}


	@Override
	public Boolean updateWithdrawPointsRuleById(
			WithdrawPointsRule withdrawPointsRule) {
		return withdrawPointsRuleMapper.updateWithdrawPointsRuleById(withdrawPointsRule);
	}


	@Override
	public WithdrawPointsRule selectWithdrawPointsRuleById(Long id) {
		return withdrawPointsRuleMapper.selectWithdrawPointsRuleById(id);
	}


	@Override
	public WithdrawPointsRule findWithdrawPointsRuleByPage(
			WithdrawPointsRule withdrawPointsRule) {
		return withdrawPointsRuleMapper.findWithdrawPointsRuleByPage(withdrawPointsRule);
	}

}
