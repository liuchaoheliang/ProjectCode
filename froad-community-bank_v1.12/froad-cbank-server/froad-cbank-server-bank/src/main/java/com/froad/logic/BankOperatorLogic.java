package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.logic.res.BankOperatorCheckRes;
import com.froad.logic.res.LoginBankOperatorRes;
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
     * 增加 BankOperator
     * @param bankOperator
     * @return Long    主键ID
     */
	public Long addBankOperator(BankOperator bankOperator) throws FroadServerException, Exception;



    /**
     * 删除 BankOperator
     * @param bankOperator
     * @return Boolean    是否成功
     */
	public Boolean deleteBankOperator(BankOperator bankOperator) throws FroadServerException, Exception;



    /**
     * 修改 BankOperator
     * @param bankOperator
     * @return Boolean    是否成功
     */
	public Boolean updateBankOperator(BankOperator bankOperator) throws FroadServerException, Exception;



	/**
     * 银行用户登录BankOperator
     * @param username 登录名
     * @param password 密码
     * @param token值 (用户第一次登录可为空后续登录验证操作需传token值过来验证)
     * @return 登录失败次数
     */
	public LoginBankOperatorRes loginBankOperator(String username,String password,String clientId,String operatorIp) throws FroadServerException, Exception;

	
	/**
    * token校验
    * @param clientId 客户端Id
    * @param userId 用户编号
    * @param token
    * @return token和userId值是否匹配
    */
	public BankOperatorCheckRes checkToken(String clientId,long userId,String token) throws FroadServerException, Exception;
		
	
	
    /**
     * 查询 BankOperator
     * @param bankOperator
     * @return List<BankOperator>    结果集合 
     */
	public List<BankOperator> findBankOperator(BankOperator bankOperator);



    /**
     * 分页查询 BankOperator
     * @param page
     * @param bankOperator
     * @return Page    结果集合 
     */
	public Page<BankOperator> findBankOperatorByPage(Page<BankOperator> page, BankOperator bankOperator);

	/**
     * 银行用户登出 BankOperator
     * @param token
     * @return Boolean    是否成功 
     */
	public Boolean logoutBankOperator(String token) throws FroadServerException, Exception;
	
	 /**
     * 分页查询 BankOperator(只查当前机构下的信息，不涉及下属机构)
     * @param page
     * @param bankOperator
     * @return Page    结果集合 
     */
	public Page<BankOperator> findBankOperatorByPageIncurrent(Page<BankOperator> page, BankOperator bankOperator);
	

	 /**
	 * 银行用户登录获取错误次数
     * @param username 登录名
     * @return 登录失败次数
	 * @throws Exception 
	 * @throws FroadServerException 
     */
	public LoginBankOperatorRes findLoginFailureCount(String clientId,String username) throws FroadServerException, Exception;
}