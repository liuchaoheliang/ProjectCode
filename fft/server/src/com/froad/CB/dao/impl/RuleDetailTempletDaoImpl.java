package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.RuleDetailTempletDao;
import com.froad.CB.po.RuleDetailTemplet;

public class RuleDetailTempletDaoImpl implements RuleDetailTempletDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
    

    public Integer insert(RuleDetailTemplet record) {
    	return (Integer) sqlMapClientTemplate.insert("rule_detail_templet.abatorgenerated_insert", record);
    }

    public boolean updateByPrimaryKey(RuleDetailTemplet record) {
    	boolean result=false;
    	if(record==null||record.getId()==null)
    		return result;
    	int rows = sqlMapClientTemplate.update("rule_detail_templet.abatorgenerated_updateByPrimaryKey", record);
    	if(rows>0)
          result=true;
        return result;
    }

    public boolean updateByPrimaryKeySelective(RuleDetailTemplet record) {
    	boolean result=false;
    	if(record==null||record.getId()==null)
    		return result;
    	int rows = sqlMapClientTemplate.update("rule_detail_templet.abatorgenerated_updateByPrimaryKeySelective", record);
    	if(rows>0)
            result=true;
        return result;
    }

    public RuleDetailTemplet selectByPrimaryKey(Integer id) {
        RuleDetailTemplet key = new RuleDetailTemplet();
        key.setId(id);
        RuleDetailTemplet record = (RuleDetailTemplet) sqlMapClientTemplate.queryForObject("rule_detail_templet.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public boolean deleteByPrimaryKey(Integer id) {
    	boolean result=false;
    	if(id==null)
    		return result;
        RuleDetailTemplet key = new RuleDetailTemplet();
        key.setId(id);
        int rows = sqlMapClientTemplate.delete("rule_detail_templet.abatorgenerated_deleteByPrimaryKey", key);
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
	public List<RuleDetailTemplet> selectRuleDetailTemplets(
			RuleDetailTemplet queryCon) {
		// TODO Auto-generated method stub
		return 	sqlMapClientTemplate.queryForList("rule_detail_templet.selectRuleDetailTemplets", queryCon);
	}
}