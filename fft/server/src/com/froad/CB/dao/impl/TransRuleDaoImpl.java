package com.froad.CB.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.TransRuleDao;
import com.froad.CB.po.RuleDetail;
import com.froad.CB.po.TransRule;

public class TransRuleDaoImpl implements TransRuleDao {
	private SqlMapClientTemplate sqlMapClientTemplate;

    
    public Integer insert(TransRule record) {
    	return (Integer) sqlMapClientTemplate.insert("trans_rule.insert", record);
    }

    public boolean updateByPrimaryKey(TransRule record) {
    	boolean result=false;
    	if(record==null||record.getId()==null)
    		return result;
    	int rows = sqlMapClientTemplate.update("trans_rule.updateByPrimaryKey", record);
    	if(rows>0)
            result=true;
    	return result;
    }
    public boolean updateByPrimaryKeySelective(TransRule record) {
    	boolean result=false;
    	if(record==null||record.getId()==null)
    		return result;
    	int rows = sqlMapClientTemplate.update("trans_rule.updateByPrimaryKeySelective", record);
    	if(rows>0)
            result=true;
    	return result;
    }

    public TransRule selectByPrimaryKey(Integer id) {
        TransRule key = new TransRule();
        key.setId(id);
        TransRule record = (TransRule) sqlMapClientTemplate.queryForObject("trans_rule.selectByPrimaryKey", key);
        return record;
    }

    public boolean deleteByPrimaryKey(Integer id) {
    	boolean result=false;
    	if(id==null)
    		return result;
        TransRule key = new TransRule();
        key.setId(id);
        int rows = sqlMapClientTemplate.delete("trans_rule.deleteByPrimaryKey", key);
    	if(rows>0)
            result=true;
    	return result;
    }

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<TransRule> selectTransRules(TransRule queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("trans_rule.selectTransRules", queryCon);
	}
    
    
}