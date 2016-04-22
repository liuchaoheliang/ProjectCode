package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.RuleDetail;


public interface RuleDetailDao {
    
    Integer insert(RuleDetail record);

   
    boolean updateByPrimaryKey(RuleDetail record);

   
    boolean updateByPrimaryKeySelective(RuleDetail record);

    
    RuleDetail selectByPrimaryKey(Integer id);

   
    boolean deleteByPrimaryKey(Integer id);
    
    boolean updateRuleDetailByRule(RuleDetail updateInfo);
    
    List<RuleDetail> selectRuleDetails(RuleDetail con);
    
    boolean insertBatch(List<RuleDetail> ruleDetails);
}