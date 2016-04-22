package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.TransRule;


public interface TransRuleDao {
   
    Integer insert(TransRule record);

    boolean updateByPrimaryKey(TransRule record);

    
    boolean updateByPrimaryKeySelective(TransRule record);

   
    TransRule selectByPrimaryKey(Integer id);

    boolean deleteByPrimaryKey(Integer id);
    
    List<TransRule> selectTransRules(TransRule queryCon);
}