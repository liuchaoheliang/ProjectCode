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
 * @Title: MerchantOutletPhotoLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantOutletPhoto;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: MerchantOutletPhotoLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantOutletPhotoLogic {


    /**
     * 增加 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Long    主键ID
     */
	public Long addMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto)throws FroadServerException,Exception;

	/**
     * 批量增加 MerchantOutletPhoto
     * @param list<merchantOutletPhoto>
     * @return list<Result>    结果集
     * 
     * @param merchantOutletPhotoList
     */
	public List<Result> addMerchantOutletPhotoByBatch(List<MerchantOutletPhoto> merchantOutletPhotoList)throws FroadServerException,Exception;

	/**
	 * 以集合为准,保存门店相册
	 * @Title: saveMerchantOutletPhoto 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantOutletPhotoList
	 * @return
	 * @throws FroadServerException, Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean saveMerchantOutletPhoto(List<MerchantOutletPhoto> merchantOutletPhotoList) throws FroadServerException, Exception ;
	
    /**
     * 删除 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto)throws FroadServerException,Exception;



    /**
     * 修改 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto)throws FroadServerException,Exception;



    /**
     * 支持多银行客户端和boss查询
     * 查询 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return List<MerchantOutletPhoto>    结果集合 
     */
	public List<MerchantOutletPhoto> findMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto);



    /**
     * 支持多银行客户端和boss查询
     * 分页查询 MerchantOutletPhoto
     * @param page
     * @param merchantOutletPhoto
     * @return Page<MerchantOutletPhoto>    结果集合 
     */
	public Page<MerchantOutletPhoto> findMerchantOutletPhotoByPage(Page<MerchantOutletPhoto> page, MerchantOutletPhoto merchantOutletPhoto);



}