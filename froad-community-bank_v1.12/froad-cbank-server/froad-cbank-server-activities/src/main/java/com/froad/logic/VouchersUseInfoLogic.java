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
 * @Title: VouchersUseInfoLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersUseInfo;

/**
 * 
 * <p>@Title: VouchersUseInfoLogic.java</p>
 * <p>Description: 封装对VouchersUseInfo所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersUseInfoLogic {


    /**
     * 增加 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Long    主键ID
     */
	public Long addVouchersUseInfo(VouchersUseInfo vouchersUseInfo) ;



    /**
     * 删除 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Boolean    是否成功
     */
	public Boolean deleteVouchersUseInfo(VouchersUseInfo vouchersUseInfo) ;



    /**
     * 根据id删除 VouchersUseInfo
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteVouchersUseInfoById(Long id) ;



    /**
     * 修改 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Boolean    是否成功
     */
	public Boolean updateVouchersUseInfo(VouchersUseInfo vouchersUseInfo) ;



    /**
     * 根据id修改 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Boolean    是否成功
     */
	public Boolean updateVouchersUseInfoById(VouchersUseInfo vouchersUseInfo) ;



    /**
     * 根据id查询单个 VouchersUseInfo
     * @param id
     * @return VouchersUseInfo    单个 VouchersUseInfo
     */
	public VouchersUseInfo findVouchersUseInfoById(Long id) ;



    /**
     * 查询一个 VouchersUseInfo
     * @param vouchersUseInfo
     * @return VouchersUseInfo    单个 VouchersUseInfo
     */
	public VouchersUseInfo findOneVouchersUseInfo(VouchersUseInfo vouchersUseInfo) ;



    /**
     * 统计 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    返回记录数 VouchersUseInfo
     */
	public Integer countVouchersUseInfo(VouchersUseInfo vouchersUseInfo) ;



    /**
     * 查询 VouchersUseInfo
     * @param vouchersUseInfo
     * @return List<VouchersUseInfo>    VouchersUseInfo集合 
     */
	public List<VouchersUseInfo> findVouchersUseInfo(VouchersUseInfo vouchersUseInfo) ;



    /**
     * 分页查询 VouchersUseInfo
     * @param page
     * @param vouchersUseInfo
     * @return Page<VouchersUseInfo>    VouchersUseInfo分页结果 
     */
	public Page<VouchersUseInfo> findVouchersUseInfoByPage(Page<VouchersUseInfo> page, VouchersUseInfo vouchersUseInfo) ;



}