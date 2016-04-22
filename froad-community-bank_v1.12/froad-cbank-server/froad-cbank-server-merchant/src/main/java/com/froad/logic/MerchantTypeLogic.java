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
 * @Title: MerchantTypeLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantType;

/**
 * 
 * <p>@Title: MerchantTypeLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantTypeLogic {


    /**
     * 增加 MerchantType
     * @param merchantType
     * @return Long    主键ID
     */
	public Long addMerchantType(MerchantType merchantType)throws FroadServerException,Exception;



    /**
     * 删除 MerchantType
     * @param merchantType
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantType(MerchantType merchantType)throws FroadServerException,Exception;



    /**
     * 修改 MerchantType
     * @param merchantType
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantType(MerchantType merchantType)throws FroadServerException,Exception;

	/**
	 * 根据id查询
	 * @Title: findMerchantTypeById 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @param clientId
	 * @return
	 * @return MerchantType    返回类型 
	 * @throws
	 */
	public MerchantType findMerchantTypeById(Long id,String clientId) ;
	
    /**
     * 查询 MerchantType
     * @param merchantType
     * @return List<MerchantType>    结果集合 
     */
	public List<MerchantType> findMerchantType(MerchantType merchantType);



    /**
     * 分页查询 MerchantType
     * @param page
     * @param merchantType
     * @return Page<MerchantType>    结果集合 
     */
	public Page<MerchantType> findMerchantTypeByPage(Page<MerchantType> page, MerchantType merchantType);
	
	
    /**
     * 查询 MerchantType
     * @param merchantType
     * @return List<MerchantType>    结果集合 
     */
	public List<MerchantType> findMerchantTypeByMerchantTypeIdList(String clientId,List<Long> merchantTypeIdList);



}