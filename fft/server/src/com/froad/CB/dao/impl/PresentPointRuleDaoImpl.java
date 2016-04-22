package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.PresentPointRuleDao;
import com.froad.CB.po.PresentPointRule;

public class PresentPointRuleDaoImpl implements PresentPointRuleDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addPresentPointRule(PresentPointRule presentPointRule) {
		return (Integer) sqlMapClientTemplate.insert("presentPointRule.insert",presentPointRule);
	}

	@Override
	public boolean updatePresentPointRuleById(PresentPointRule presentPointRule) {
		int n=0;
		n=sqlMapClientTemplate.update("presentPointRule.updateById", presentPointRule);
		if(n>0){
			return true;
		}
		return false;
	}

	@Override
	public PresentPointRule getByPresentPointRulePager(
			PresentPointRule presentPointRule) {
		int count=(Integer) sqlMapClientTemplate.queryForObject("presentPointRule.getByPresentPointRulePagerCount", presentPointRule);
		List list=sqlMapClientTemplate.queryForList("presentPointRule.getByPresentPointRulePager", presentPointRule);
		presentPointRule.setTotalCount(count);
		presentPointRule.setList(list);
		return presentPointRule;
	}
	
	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public PresentPointRule getById(Integer id) {
		 return (PresentPointRule) sqlMapClientTemplate.queryForObject("presentPointRule.getById",id);
	}

	@Override
	public List<PresentPointRule> getByConditons(PresentPointRule presentPointRule) {		
		return (List<PresentPointRule>) sqlMapClientTemplate.queryForList("presentPointRule.getByCondtions",presentPointRule);
	}

	@Override
	public List<PresentPointRule> getByRackId(String RackId) {
		return sqlMapClientTemplate.queryForList("presentPointRule.getByRackId",RackId);
	}
	

}
