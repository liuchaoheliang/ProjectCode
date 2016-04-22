package com.froad.CB.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.common.Command;
import com.froad.CB.dao.RuleDetailDao;
import com.froad.CB.po.RuleDetail;
import com.froad.util.Assert;

public class RuleDetailDaoImpl  implements RuleDetailDao {
	private SqlMapClientTemplate sqlMapClientTemplate;

    public Integer insert(RuleDetail record) {
    	return (Integer) sqlMapClientTemplate.insert("rule_detail.abatorgenerated_insert", record);
    }

    public boolean updateByPrimaryKey(RuleDetail record) {
    	boolean result=false;
    	if(record==null||record.getId()==null)
    		return result;
        int rows = sqlMapClientTemplate.update("rule_detail.abatorgenerated_updateByPrimaryKey", record);
        if(rows>0)
        	result=true;
        return result;
    }

    public boolean updateByPrimaryKeySelective(RuleDetail record) {
    	boolean result=false;
    	if(record==null||record.getId()==null)
    		return result;
        int rows = sqlMapClientTemplate.update("rule_detail.abatorgenerated_updateByPrimaryKeySelective", record);
        if(rows>0)
        	result=true;
        return result;
    }

    public RuleDetail selectByPrimaryKey(Integer id) {
        RuleDetail key = new RuleDetail();
        key.setId(id);
        RuleDetail record = (RuleDetail) sqlMapClientTemplate.queryForObject("rule_detail.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public boolean deleteByPrimaryKey(Integer id) {
    	boolean result=false;
    	if(id==null)
    		return result;
        RuleDetail key = new RuleDetail();
        key.setId(id);
        int rows = sqlMapClientTemplate.delete("rule_detail.abatorgenerated_deleteByPrimaryKey", key);
        if(rows>0)
        	result=true;
        return result;
    }
    
//    @Override
//	public boolean startRuleDetailByRule(Integer ruleId, String ruleType) {
//		// TODO Auto-generated method stub
//    	boolean result=false;
//		if(ruleId==null||Assert.empty(ruleType))
//			return result;
//		 RuleDetail updateInfo = new RuleDetail();
//		 updateInfo.setRuleId(ruleId);
//		 updateInfo.setRuleType(ruleType);
//		 updateInfo.setState(Command.STATE_CREATE);
//		 sqlMapClientTemplate.update("rule_detail.updateRuleDetailStateByRule", updateInfo);
//		return result;
//	}
//
//	@Override
//	public boolean stopRuleDetailByRule(Integer ruleId, String ruleType) {
//		// TODO Auto-generated method stub
//		boolean result=false;
//		if(ruleId==null||Assert.empty(ruleType))
//			return result;
//		 RuleDetail updateInfo = new RuleDetail();
//		 updateInfo.setRuleId(ruleId);
//		 updateInfo.setRuleType(ruleType);
//		 updateInfo.setState(Command.STATE_STOP);
//		 sqlMapClientTemplate.update("rule_detail.updateRuleDetailStateByRule", updateInfo);
//		return result;
//	}
    
	
	/**
	  * 方法描述：更新条件为：Integer ruleId, String ruleType
	  * 只能更新状态
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-30 下午03:58:57
	 */
	@Override
	public boolean updateRuleDetailByRule(RuleDetail updateInfo) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(updateInfo.getRuleId()==null||Assert.empty(updateInfo.getRuleType())||Assert.empty(updateInfo.getState()))
			return result;
		int rows = sqlMapClientTemplate.update("rule_detail.updateRuleDetailByRule", updateInfo);
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
	public List<RuleDetail> selectRuleDetails(RuleDetail con) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("rule_detail.selectRuleDetails", con);
	}
	
	public boolean insertBatch(List<RuleDetail> ruleDetails){
		sqlMapClientTemplate.insert("rule_detail.insertRuleDetails",ruleDetails);
		return true;
	}
}