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
 * @Title: MerchantUserLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantUser;
import com.froad.po.CommonAddRes;
import com.froad.po.Result;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OriginVo;

/**
 * 
 * <p>@Title: MerchantUserLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantUserLogic {


    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return MerchantUserAddRes
     */
	public CommonAddRes addMerchantUser(MerchantUser merchantUser)throws FroadServerException,Exception;




    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return Result
     */
	public Result updateMerchantUser(MerchantUser merchantUser)throws FroadServerException,Exception;

	
	 /**
     * 查询MerchantUser
     * @param merchantUser
     * @return List<MerchantUser>
     */
	public List<MerchantUser> findMerchantUser(Page<MerchantUser> page,MerchantUser merchantUser)throws FroadServerException,Exception;
	
	
	/**
     * 分页查询 MerchantUser
     * @param page
     * @param merchantUser
     * @return Page<MerchantUser>    结果集合 
     */
	public Page<MerchantUser> findMerchantUserByPage(Page<MerchantUser> page, MerchantUser merchantUser);
}