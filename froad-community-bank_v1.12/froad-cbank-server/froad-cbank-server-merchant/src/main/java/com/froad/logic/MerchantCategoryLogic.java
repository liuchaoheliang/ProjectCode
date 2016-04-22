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
 * @Title: MerchantCategoryLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantCategory;

/**
 * 
 * <p>@Title: MerchantCategoryLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantCategoryLogic {


    /**
     * 增加 MerchantCategory
     * @param merchantCategory
     * @return Long    主键ID
     */
	public Long addMerchantCategory(MerchantCategory merchantCategory)throws FroadServerException,Exception;



    /**
     * 删除 MerchantCategory
     * @param merchantCategory
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantCategory(MerchantCategory merchantCategory)throws FroadServerException,Exception;



    /**
     * 修改 MerchantCategory
     * @param merchantCategory
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantCategory(MerchantCategory merchantCategory)throws FroadServerException,Exception;

	/**
	 * 查询分类
	 * @Title: findMerchantCategoryById 
	 * @author vania
	 * @version 1.0
	 * @param id
	 * @return
	 * @return MerchantCategory    返回类型 
	 * @throws
	 */
	public MerchantCategory findMerchantCategoryById(String clientId, Long id) ;

    /**
     * 支持多银行客户端和boss查询
     * 查询 MerchantCategory
     * @param merchantCategory
     * @return List<MerchantCategory>    结果集合 
     */
	public List<MerchantCategory> findMerchantCategory(MerchantCategory merchantCategory);



    /**
     * 支持多银行客户端和boss查询
     * 分页查询 MerchantCategory
     * @param page
     * @param merchantCategory
     * @return Page    结果集合 
     */
	public Page<MerchantCategory> findMerchantCategoryByPage(Page<MerchantCategory> page, MerchantCategory merchantCategory);
	
	
	/**
     * 分页查询 MerchantCategory
     * @param page
     * @param merchantCategory
     * @return List    结果集合 
     */
	List<MerchantCategory> findMerchantCategoryByCategoryIdList(String clientId, List<Long> categoryIdList);


	/**
	 * 获取商户分类
	 * @param merchantCategory 商户分类对象
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 */
	public List<MerchantCategory> findMerchantCategoryByH5(MerchantCategory merchantCategory)throws FroadServerException,Exception;
}