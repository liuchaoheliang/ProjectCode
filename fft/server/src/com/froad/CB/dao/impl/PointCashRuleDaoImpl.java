package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.PointCashRuleDao;
import com.froad.CB.po.PointCashRule;

/** 
 * @author TXL
 * @date 2013-2-25 pm
 * @version 1.0
 * 
 */
public class PointCashRuleDaoImpl implements PointCashRuleDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(
			SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	@Override
	public PointCashRule getByID(Integer id) {
		return (PointCashRule) sqlMapClientTemplate.queryForObject("pointCashRule.getPointCashRuleByID", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PointCashRule> getAllPointCashRule() {
		return sqlMapClientTemplate.queryForList("pointCashRule.getAllPointCashRule");
	}

}
