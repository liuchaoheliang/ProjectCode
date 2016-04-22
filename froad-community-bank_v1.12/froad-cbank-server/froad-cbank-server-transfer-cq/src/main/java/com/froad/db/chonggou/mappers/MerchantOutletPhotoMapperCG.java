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
 * @Title: MerchantOutletPhotoMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.MerchantOutletPhotoCG;
import com.froad.db.mysql.bean.Page;

/**
 * 
 * <p>@Title: MerchantOutletPhotoMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantOutletPhotoMapperCG {


    /**
     * 增加 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Long    主键ID
     */
	public Long addMerchantOutletPhoto(MerchantOutletPhotoCG merchantOutletPhoto);



    /**
     * 批量增加 MerchantOutletPhoto
     * @param List<MerchantOutletPhoto>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantOutletPhotoByBatch(List<MerchantOutletPhotoCG> merchantOutletPhotoList);

	/**
	 * 
	 * @Title: deleteMerchantOutletPhotoByOutletId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean deleteMerchantOutletPhotoByMerchantId(String merchantId);

    /**
     * 删除 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantOutletPhoto(MerchantOutletPhotoCG merchantOutletPhoto);



    /**
     * 修改 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantOutletPhoto(MerchantOutletPhotoCG merchantOutletPhoto);



    /**
     * 查询一个 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return MerchantOutletPhoto    返回结果
     */
	public MerchantOutletPhotoCG findMerchantOutletPhotoById(Long id);



    /**
     * 查询 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return List<MerchantOutletPhoto>    返回结果集
     */
	public List<MerchantOutletPhotoCG> findMerchantOutletPhoto(MerchantOutletPhotoCG merchantOutletPhoto);





}