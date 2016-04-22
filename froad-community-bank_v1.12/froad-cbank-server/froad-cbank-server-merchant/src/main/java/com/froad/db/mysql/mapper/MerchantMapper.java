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
 * @Title: MerchantMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Merchant;
import com.froad.po.Outlet;

/**
 * 
 * <p>@Title: MerchantMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantMapper {


    /**
     * 增加 Merchant
     * @param merchant
     * @return Long    主键ID
     */
	public Long addMerchant(Merchant merchant);



    /**
     * 批量增加 Merchant
     * @param List<Merchant>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantByBatch(List<Merchant> merchantList);



    /**
     * 删除 Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchant(Merchant merchant);

	
	/**
     * 物理删除 Merchant
     * @param merchantId
     * @return Boolean    是否成功
     */
	public Boolean removeMerchant(String merchantId);


    /**
     * 修改 Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	public Boolean updateMerchant(Merchant merchant);
	
	
    /**
     * 商户审核通过后在审核修改 Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	public Boolean updateAuditMerchant(Merchant merchant);


    /**
     * 商户审核通过后在新增审核修改 Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	public Boolean updateAuditMerchantByMerchantId(Merchant merchant);
	
    /**
     * 查询一个 Merchant
     * @param merchant
     * @return Merchant    返回结果
     */
	public Merchant findMerchantById(Long id);

	/**
	 * 根据商户id查询Merchant对象
	 * @param merchantId
	 * @return
	 */
	public Merchant findMerchantByMerchantId(String merchantId);

    /**
     * 查询 Merchant
     * @param merchant
     * @return List<Merchant>    返回结果集
     */
	public List<Merchant> findMerchant(Merchant merchant);

	/**
	 * 统计
	 * @Title: countMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return
	 * @return Integer    返回类型 
	 * @throws
	 */
	public Integer countMerchant(Merchant merchant);

    /**
     * 分页查询 Merchant
     * @param page 
     * @param merchant
     * @return List<Merchant>    返回分页查询结果集
     */
	public List<Merchant> findByPage(Page page, @Param("merchant")Merchant merchant);


	/**
	 * 根据clientid和机构码查询银行商户
	 * @Title: findBankMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCodeList
	 * @return
	 * @return List<Merchant>    返回类型 
	 * @throws
	 */
	public List<Merchant> findBankMerchant(@Param("clientId") String clientId,@Param("orgCodeList")List<String> orgCodeList);
	
	
    /**
     * 修改审核通过的商户 Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantByAuditThrough(Merchant merchant);
	
	
	  /**
     * 商户列表导出分页查询 Merchant.
     * @param page 
     * @param merchant
     * @return List<Merchant>    返回分页查询结果集
     */
	public List<Merchant> findExportByPage(Page page, @Param("merchant")Merchant merchant);
}