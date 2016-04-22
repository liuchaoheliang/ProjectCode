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
 * @Title: MerchantAccountMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.MerchantAccount;

/**
 * 
 * <p>@Title: MerchantAccountMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantAccountMapper {


    /**
     * 增加 MerchantAccount
     * @param merchantAccount
     * @return Long    主键ID
     */
	public Long addMerchantAccount(MerchantAccount merchantAccount);



    /**
     * 批量增加 MerchantAccount
     * @param List<MerchantAccount>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantAccountByBatch(List<MerchantAccount> merchantAccountList);



    /**
     * 删除 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantAccount(MerchantAccount merchantAccount);

	/**
	 * 物理删除MerchantAccount
	 * @Title: removeMerchantAccount 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean removeMerchantAccount(Long id);

    /**
     * 修改 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantAccount(MerchantAccount merchantAccount);



    /**
     * 查询一个 MerchantAccount
     * @param id
     * @return MerchantAccount    返回结果
     */
	public MerchantAccount findMerchantAccountById(Long id);

	 /**
     * 查询一个 MerchantAccount
     * @param outletId
     * @return MerchantAccount    返回结果
     */
	public MerchantAccount findMerchantAccountByOutletId(String outletId);

    /**
     * 查询 MerchantAccount
     * @param merchantAccount
     * @return List<MerchantAccount>    返回结果集
     */
	public List<MerchantAccount> findMerchantAccount(MerchantAccount merchantAccount);



    /**
     * 分页查询 MerchantAccount
     * @param page 
     * @param merchantAccount
     * @return List<MerchantAccount>    返回分页查询结果集
     */
	public List<MerchantAccount> findByPage(Page page, @Param("merchantAccount")MerchantAccount merchantAccount);



}