/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BankRoleMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BankRole;

/**
 * 
 * <p>@Title: BankRoleMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankRoleMapper {


    /**
     * 增加 BankRole
     * @param bankRole
     * @return Long    主键ID
     */
	public Long addBankRole(BankRole bankRole);



    /**
     * 批量增加 BankRole
     * @param List<BankRole>
     * @return Boolean    是否成功
     */
	public Boolean addBankRoleByBatch(List<BankRole> bankRoleList);



    /**
     * 删除 BankRole
     * @param bankRole
     * @return Boolean    是否成功
     */
	public Boolean deleteBankRole(BankRole bankRole);



    /**
     * 修改 BankRole
     * @param bankRole
     * @return Boolean    是否成功
     */
	public Boolean updateBankRole(BankRole bankRole);



    /**
     * 查询 BankRole(查询当前机构及当前机构下属所有角色)
     * @param bankRole
     * @return List<BankRole>    返回结果集
     */
	public List<BankRole> findBankRole(BankRole bankRole);
	

	/**
     * 查询 BankRole(只查询当前机构下的，不查下属机构对应的角色)
     * @param bankRole
     * @return List<BankRole>    返回结果集
     */
	public List<BankRole> findBankRoleInCurrentOrg(BankRole bankRole);


    /**
     * 分页查询 BankRole
     * @param page 
     * @param bankRole
     * @return List<BankRole>    返回分页查询结果集
     */
	public List<BankRole> findByPage(Page page, @Param("bankRole")BankRole bankRole);



}