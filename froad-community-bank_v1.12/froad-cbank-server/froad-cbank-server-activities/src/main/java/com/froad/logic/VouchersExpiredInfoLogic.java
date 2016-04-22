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
 * @Title: VouchersExpiredInfoLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersExpiredInfo;

/**
 * 
 * <p>@Title: VouchersExpiredInfoLogic.java</p>
 * <p>Description: 封装对VouchersExpiredInfo所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersExpiredInfoLogic {


    /**
     * 增加 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Long    主键ID
     */
	public Long addVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) ;



    /**
     * 删除 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Boolean    是否成功
     */
	public Boolean deleteVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) ;



    /**
     * 根据id删除 VouchersExpiredInfo
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteVouchersExpiredInfoById(Long id) ;



    /**
     * 修改 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Boolean    是否成功
     */
	public Boolean updateVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) ;



    /**
     * 根据id修改 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Boolean    是否成功
     */
	public Boolean updateVouchersExpiredInfoById(VouchersExpiredInfo vouchersExpiredInfo) ;



    /**
     * 根据id查询单个 VouchersExpiredInfo
     * @param id
     * @return VouchersExpiredInfo    单个 VouchersExpiredInfo
     */
	public VouchersExpiredInfo findVouchersExpiredInfoById(Long id) ;



    /**
     * 查询一个 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return VouchersExpiredInfo    单个 VouchersExpiredInfo
     */
	public VouchersExpiredInfo findOneVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) ;



    /**
     * 统计 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    返回记录数 VouchersExpiredInfo
     */
	public Integer countVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) ;



    /**
     * 查询 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return List<VouchersExpiredInfo>    VouchersExpiredInfo集合 
     */
	public List<VouchersExpiredInfo> findVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) ;



    /**
     * 分页查询 VouchersExpiredInfo
     * @param page
     * @param vouchersExpiredInfo
     * @return Page<VouchersExpiredInfo>    VouchersExpiredInfo分页结果 
     */
	public Page<VouchersExpiredInfo> findVouchersExpiredInfoByPage(Page<VouchersExpiredInfo> page, VouchersExpiredInfo vouchersExpiredInfo) ;



}