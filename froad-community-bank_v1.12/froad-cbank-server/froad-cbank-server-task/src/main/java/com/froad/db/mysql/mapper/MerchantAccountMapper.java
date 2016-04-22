package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.MerchantAccount;

public interface MerchantAccountMapper {

	public MerchantAccount queryByCondition(MerchantAccount merchantAccount);
	
	public List<MerchantAccount> queryByConditionList(MerchantAccount merchantAccount);
	
	public Boolean updateMerchantAccount(MerchantAccount merchantAccount);
}
