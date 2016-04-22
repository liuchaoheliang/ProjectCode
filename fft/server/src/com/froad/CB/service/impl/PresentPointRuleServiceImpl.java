package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;


import org.apache.log4j.Logger;

import com.froad.CB.dao.PresentPointRuleDao;
import com.froad.CB.po.PresentPointRule;
import com.froad.CB.service.PresentPointRuleService;
@WebService(endpointInterface="com.froad.CB.service.PresentPointRuleService")

public class PresentPointRuleServiceImpl implements PresentPointRuleService {
	private PresentPointRuleDao presentPointRuleDao; 
	private static final Logger logger=Logger.getLogger(PresentPointRuleServiceImpl.class);	
	@Override
	public Integer addPresentPointRule(PresentPointRule presentPointRule) {
		if(presentPointRule==null){
			logger.info("数据为空，插入失败");
			return null;
		}
		return presentPointRuleDao.addPresentPointRule(presentPointRule);
	}

	@Override
	public boolean updatePresentPointRuleById(PresentPointRule presentPointRule) {
		if(presentPointRule==null || "".equals(presentPointRule.getId())){
			logger.info("更新数据主键为空");
			return false;
		}
			return presentPointRuleDao.updatePresentPointRuleById(presentPointRule);
	}

	@Override
	public PresentPointRule getById(Integer id) {
		if(id==null){
			logger.info("查询主键为空");
			return null;
		}
		return presentPointRuleDao.getById(id);
	}

	@Override
	public PresentPointRule getPresentPointRuleByPager(
			PresentPointRule presentPointRule) {
		if(presentPointRule==null){
			logger.info("传入数据为空");
			return null;
		}
		return presentPointRuleDao.getByPresentPointRulePager(presentPointRule);
	}

	public PresentPointRuleDao getPresentPointRuleDao() {
		return presentPointRuleDao;
	}

	public void setPresentPointRuleDao(PresentPointRuleDao presentPointRuleDao) {
		this.presentPointRuleDao = presentPointRuleDao;
	}

	@Override
	public List<PresentPointRule> getByConditions(PresentPointRule presentPointRule) {
		if(presentPointRule==null){
			logger.info("传入数据为空");
		}
		return presentPointRuleDao.getByConditons(presentPointRule);
	}
	
	

}
