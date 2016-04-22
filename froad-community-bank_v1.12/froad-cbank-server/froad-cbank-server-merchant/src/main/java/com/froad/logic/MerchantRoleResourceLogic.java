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
 * @Title: MerchantRoleResourceLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;


import java.util.List;

import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantRoleResourceAddRes;
import com.froad.po.Result;
import com.froad.po.mongo.MerchantRoleResource;

/**
 * 
 * <p>@Title: MerchantRoleResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantRoleResourceLogic {


    /**
     * 增加 MerchantRoleResource
     * @param merchantRoleResource
     * @return MerchantRoleResourceAddRes
     */
	public MerchantRoleResourceAddRes addMerchantRoleResource(MerchantRoleResource merchantRoleResource);



    /**
     * 删除 MerchantRoleResource
     * @param _id (client_id_role_id)
     * @return Result
     */
	public Result deleteMerchantRoleResource(String _id)throws FroadServerException,Exception;



    /**
     * 修改 MerchantRoleResource
     * @param merchantRoleResource
     * @return Result
     */
	public Result updateMerchantRoleResource(MerchantRoleResource merchantRoleResource)throws FroadServerException,Exception;



    /**
     * 查询 MerchantRoleResource
     * @param _id (client_id_role_id)
     * @return MerchantRoleResource    
     */
	public MerchantRoleResource findMerchantRoleResource(String _id);

	/**
     * 查询 List<MerchantRoleResource>
     * @param client_id
     * @return List<MerchantRoleResourceVo>
     * 
     */
	public List<MerchantRoleResource> getMerchantRoleResourceListByClientId(String client_id);
	
	/**
     * 查询 List<MerchantRoleResource> (全部)
     * @return List<MerchantRoleResourceVo>
     */
	public List<MerchantRoleResource> getMerchantRoleResourceList();
}