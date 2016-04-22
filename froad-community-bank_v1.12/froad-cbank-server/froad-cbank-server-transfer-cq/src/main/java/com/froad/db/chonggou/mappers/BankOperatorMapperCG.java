package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.BankOperatorCG;

/**
 * 
 * <p>@Title: BankOperatorCGMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankOperatorMapperCG {


    /**
     * 增加 BankOperatorCG
     * @param bankOperatorCG
     * @return Long    主键ID
     */
	public Long addBankOperator(BankOperatorCG bankOperatorCG);



    /**
     * 批量增加 BankOperatorCG
     * @param List<bankOperatorCGList>
     * @return Boolean    是否成功
     */
	public Boolean addBankOperatorByBatch(@Param("bankOperatorCGList")List<BankOperatorCG> bankOperatorCGList);
	
	
	public BankOperatorCG findByUsername(@Param("username")String username, @Param("clientId")String clientId);
	
	
	public List<BankOperatorCG> findAll();
	
	/**
	 * delete by client id
	 * 
	 * @param clientId
	 */
	public void deleteByClientId(String clientId);
}