package com.froad.db.mysql.mapper;

import com.froad.po.BankOperator;

/**
 * 
 * <p>@Title: BankOperatorCommonMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年5月8日
 */
public interface BankOperatorCommonMapper {

	
    /**
     * 查询 BankOperator
     * @param bankOperator
     * @return List<BankOperator>    返回结果集
     */
	public BankOperator findBankOperatorById(Long userId);


}