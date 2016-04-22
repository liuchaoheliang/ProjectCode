
	 /**
  * 文件名：PresentPointsRuleMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月26日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantAccountMapper;
import com.froad.fft.persistent.api.PresentPointsRuleMapper;
import com.froad.fft.persistent.entity.PresentPointsRule;
import com.froad.fft.persistent.entity.Product;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月26日 下午8:42:09 
 */
public class PresentPointsRuleMapperImpl implements PresentPointsRuleMapper {
	
	@Resource PresentPointsRuleMapper presentPointsRuleMapper;
	
	@Override
	public Long savePresentPointsRule(PresentPointsRule presentPointsRule) {
		presentPointsRuleMapper.savePresentPointsRule(presentPointsRule);
		return presentPointsRule.getId();
	}


	@Override
	public Boolean updatePresentPointsRuleById(
			PresentPointsRule presentPointsRule) {
		return presentPointsRuleMapper.updatePresentPointsRuleById(presentPointsRule);
	}


	@Override
	public PresentPointsRule selectPresentPointsRuleById(Long id) {
		return presentPointsRuleMapper.selectPresentPointsRuleById(id);
	}


	@Override
	public PresentPointsRule findPresentPointsRuleByPage(
			PresentPointsRule PresentPointsRule) {
		return presentPointsRuleMapper.findPresentPointsRuleByPage(PresentPointsRule);
	}

}
