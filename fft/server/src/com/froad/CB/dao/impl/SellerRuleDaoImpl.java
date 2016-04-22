package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.SellerRuleDao;
import com.froad.CB.po.SellerRule;

public class SellerRuleDaoImpl implements SellerRuleDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addSellerRule(SellerRule sellerRule) {
		return (Integer)sqlMapClientTemplate.insert("sellerRule.insert",sellerRule);
	}

	@Override
	public List<SellerRule> getBySelective(SellerRule sellerRule) {
		return sqlMapClientTemplate.queryForList("sellerRule.selectBySelective",sellerRule);
	}

	@Override
	public void updateById(SellerRule sellerRule) {
		sqlMapClientTemplate.update("sellerRule.updateById",sellerRule);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
