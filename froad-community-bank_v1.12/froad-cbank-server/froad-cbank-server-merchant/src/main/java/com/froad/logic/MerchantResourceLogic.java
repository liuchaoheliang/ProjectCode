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
 * @Title: MerchantResourceLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantResource;
import com.froad.po.MerchantResourceAddRes;
import com.froad.po.Result;
import com.froad.thrift.vo.OriginVo;

/**
 * 
 * <p>@Title: MerchantResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantResourceLogic {


    /**
     * 增加 MerchantResource
     * @param merchantResource
     * @return MerchantResourceAddRes
     */
	public MerchantResourceAddRes addMerchantResource(MerchantResource merchantResource)throws FroadServerException,Exception;



    /**
     * 删除 MerchantResource
     * @param merchantResource
     * @return Result
     */
	public Result deleteMerchantResource(MerchantResource merchantResource)throws FroadServerException,Exception;



    /**
     * 修改 MerchantResource
     * @param merchantResource
     * @return Result
     */
	public Result updateMerchantResource(MerchantResource merchantResource)throws FroadServerException,Exception;


	/**
	 * 移动商户资源至某一个资源之前/之后
	 * @Title: moveMerchantResourceTo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param srcResourceId
	 * @param destResourceId
	 * @param action
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean moveMerchantResourceTo(long srcResourceId, long destResourceId, int action)throws FroadServerException,Exception;
	
    /**
     * 查询 MerchantResource
     * @param merchantResource
     * @return List<MerchantResource>    结果集合 
     */
	public List<MerchantResource> findMerchantResource(MerchantResource merchantResource);



    /**
     * 分页查询 MerchantResource
     * @param page
     * @param merchantResource
     * @return Page<MerchantResource>    结果集合 
     */
	public Page<MerchantResource> findMerchantResourceByPage(Page<MerchantResource> page, MerchantResource merchantResource);



}