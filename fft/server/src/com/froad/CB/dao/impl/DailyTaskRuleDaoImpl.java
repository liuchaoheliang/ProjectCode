package com.froad.CB.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.DailyTaskRuleDao;
import com.froad.CB.po.DailyTaskRule;

/** 
 * @author TXL
 * @date 2013-2-4 上午09:47:29
 * @version 1.0
 */
public class DailyTaskRuleDaoImpl implements DailyTaskRuleDao {
	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public DailyTaskRule getDailyTaskRuleByPrimaryId(Integer id) {		
		return (DailyTaskRule)sqlMapClientTemplate.queryForObject("dailyTaskRule.getDailyTaskRuleByPrimaryId",id);
	}

}
