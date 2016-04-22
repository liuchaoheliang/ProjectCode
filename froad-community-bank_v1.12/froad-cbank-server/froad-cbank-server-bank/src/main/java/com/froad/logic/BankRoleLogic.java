package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.BankRole;

/**
 * 
 * <p>@Title: BankRoleLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankRoleLogic {


    /**
     * 增加 BankRole
     * @param bankRole
     * @return Long    主键ID
     */
	public Long addBankRole(BankRole bankRole, List<Long> resourceIds) throws FroadServerException, Exception;



    /**
     * 删除 BankRole
     * @param bankRole
     * @return Boolean    是否成功
     */
	public Boolean deleteBankRole(BankRole bankRole) throws FroadServerException, Exception;



    /**
     * 修改 BankRole
     * @param bankRole
     * @return Boolean    是否成功
     */
	public Boolean updateBankRole(BankRole bankRole, List<Long> resourceIds) throws FroadServerException, Exception;


	/**
     * 根据id查询 单个BankRole
     * @param roleId 角色id
     * @return BankRole  对象
     */
	public BankRole findBankRoleById(Long roleId);
	

    /**
     * 查询 BankRole(查询当前机构及当前机构下属所有角色)
     * @param bankRole
     * @return List<BankRole>    结果集合 
     */
	public List<BankRole> findBankRole(BankRole bankRole);


	/**
     * 查询 BankRole(只查询当前机构下的，不查下属机构对应的角色)
     * @param bankRole
     * @return List<BankRole>    结果集合 
     */
	public List<BankRole> findBankRoleInCurrentOrg(BankRole bankRole);
	

    /**
     * 分页查询 BankRole
     * @param page
     * @param bankRole
     * @return Page    结果集合 
     */
	public Page<BankRole> findBankRoleByPage(Page<BankRole> page, BankRole bankRole);



}