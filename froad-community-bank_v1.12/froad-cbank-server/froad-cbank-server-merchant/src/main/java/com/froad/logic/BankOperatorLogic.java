package com.froad.logic;

import java.util.List;

import com.froad.po.BankOperator;

/**
 * 
 * <p>@Title: BankOperatorLogic.java</p>
 * <p>Description: 描述 </p> 银行操作员Logic接口类
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankOperatorLogic {


    
	
	/**
     * 根据id查询 BankOperator
     * @param clientId 客户端id
     * @param userId 用户编号
     * @return BankOperator对象
     */
	public BankOperator findBankOperatorById(String clientId, long userId);
	
	
    /**
     * 查询 BankOperator
     * @param bankOperator
     * @return List<BankOperator>    结果集合 
     */
	public List<BankOperator> findBankOperator(BankOperator bankOperator);




}