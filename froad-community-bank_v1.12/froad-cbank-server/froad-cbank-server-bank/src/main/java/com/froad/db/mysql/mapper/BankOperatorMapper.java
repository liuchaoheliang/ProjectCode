package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BankOperator;

/**
 * 
 * <p>@Title: BankOperatorMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankOperatorMapper {


    /**
     * 增加 BankOperator
     * @param bankOperator
     * @return Long    主键ID
     */
	public Long addBankOperator(BankOperator bankOperator);



    /**
     * 批量增加 BankOperator
     * @param List<BankOperator>
     * @return Boolean    是否成功
     */
	public Boolean addBankOperatorByBatch(List<BankOperator> bankOperatorList);



    /**
     * 删除 BankOperator
     * @param bankOperator
     * @return Boolean    是否成功
     */
	public Boolean deleteBankOperator(BankOperator bankOperator);

	
	 /**
     * 批量删除法人行社管理员信息
     * @param clientId 客户端编号(条件)
     * @param orgCodeList 机构编号集合(条件)
     * @param roleId 角色id(条件)
     * @return Boolean    是否成功
     */
	public Boolean deleteBankOperatorByList(@Param("clientId")String clientId,@Param("orgCodeList")List<String> orgCodeList,@Param("roleId")Long roleId);

	

    /**
     * 修改 BankOperator
     * @param bankOperator
     * @return Boolean    是否成功
     */
	public Boolean updateBankOperator(BankOperator bankOperator);

	
	/**
     * 修改 BankOperator是否删除及状态值
     * @param bankOperator
     * @return Boolean    是否成功
     */
	public Boolean updateBankOperatorIsDelete(BankOperator bankOperator);
	
	
	
	 /**
     * 批量修改法人行社管理员密码
     * @param password 密码(修改的值)
     * @param clientId 客户端编号(条件)
     * @param orgCodeList 机构编号集合(条件)
     * @param roleId 角色id(条件)
     * @return Boolean    是否成功
     */
	public Boolean updateBankOperatorByList(@Param("password")String password,@Param("clientId")String clientId,@Param("orgCodeList")List<String> orgCodeList,@Param("roleId")Long roleId);
	

	/**
     * 查询 BankOperator
     * @param username 登录名
     * @return BankOperator 银行用户po
     */
	public BankOperator findBankOperatorByUsernameId(BankOperator bankOperator);
	

    /**
     * 查询 BankOperator
     * @param bankOperator
     * @return List<BankOperator>    返回结果集
     */
	public List<BankOperator> findBankOperator(BankOperator bankOperator);



    /**
     * 分页查询 BankOperator
     * @param page 
     * @param bankOperator
     * @return List<BankOperator>    返回分页查询结果集
     */
	public List<BankOperator> findByPage(Page page, @Param("bankOperator")BankOperator bankOperator);
    
	/**
	 * 
	 * @param page
	 * @param bankOperator
	 * @return
	 */
	public List<BankOperator> findFilterByPage(Page page, @Param("bankOperator")BankOperator bankOperator); 

}