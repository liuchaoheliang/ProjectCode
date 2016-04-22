package com.froad.CB.dao.tran;

import java.util.List;

import com.froad.CB.po.transaction.SellerRuleDetail;

public interface SellerRuleDetailDAO {

	void insert(SellerRuleDetail record);

	int updateByPrimaryKeySelective(SellerRuleDetail record);

	SellerRuleDetail selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	List<SellerRuleDetail> selectSellerRuleDetail(SellerRuleDetail queryCon);
	
	boolean updateSellerRuleDetailStateByRule(SellerRuleDetail con);
	
}