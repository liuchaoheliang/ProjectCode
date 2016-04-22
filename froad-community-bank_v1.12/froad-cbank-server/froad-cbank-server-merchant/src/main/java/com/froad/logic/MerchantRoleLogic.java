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
 * @Title: MerchantRoleLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantRole;
import com.froad.po.MerchantRoleAddRes;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: MerchantRoleLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantRoleLogic {


    /**
     * 增加 MerchantRole
     * @param merchantRole
     * @return MerchantRoleAddRes
     */
	public MerchantRoleAddRes addMerchantRole(MerchantRole merchantRole)throws FroadServerException,Exception;



    /**
     * 删除 MerchantRole
     * @param merchantRole
     * @return Result
     */
	public Result deleteMerchantRole(MerchantRole merchantRole)throws FroadServerException,Exception;



    /**
     * 修改 MerchantRole
     * @param merchantRole
     * @return Result
     */
	public Result updateMerchantRole(MerchantRole merchantRole)throws FroadServerException,Exception;



    /**
     * 查询 MerchantRole
     * @param merchantRole
     * @return List<MerchantRole>    结果集合 
     */
	public List<MerchantRole> findMerchantRole(MerchantRole merchantRole);

	/**
     * 查询 MerchantRole
     * @param roleId
     * @return MerchantRole
     */
	public MerchantRole findMerchantRoleById(Long roleId);

    /**
     * 分页查询 MerchantRole
     * @param page
     * @param merchantRole
     * @return Page<MerchantRole>    结果集合 
     */
	public Page<MerchantRole> findMerchantRoleByPage(Page<MerchantRole> page, MerchantRole merchantRole);
	
	
	/**
     * 根据客户端id 和角色描述查询角色
     * @param roleId
     * @return MerchantRole
     */
	public MerchantRole findMerchantRoleByClientIdAndDesc(String clientId,String roleDesc);




}