package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.RuleDetailTemplet;


public interface RuleDetailTempletDao {
    
    Integer insert(RuleDetailTemplet record);

   
    boolean updateByPrimaryKey(RuleDetailTemplet record);

    boolean updateByPrimaryKeySelective(RuleDetailTemplet record);

   
    RuleDetailTemplet selectByPrimaryKey(Integer id);

   
    boolean deleteByPrimaryKey(Integer id);
    
    List<RuleDetailTemplet> selectRuleDetailTemplets(RuleDetailTemplet queryCon);
}