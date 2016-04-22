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
 * @Title: VouchersExpiredInfoMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersExpiredInfo;

/**
 * 
 * <p>@Title: VouchersExpiredInfoMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体VouchersExpiredInfo的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersExpiredInfoMapper {


    /**
     * 增加 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Long    受影响行数
     */
	public Long addVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo);



    /**
     * 批量增加 VouchersExpiredInfo
     * @param vouchersExpiredInfoList
     * @return Boolean    是否成功
     */
	public Boolean addVouchersExpiredInfoByBatch(@Param("vouchersExpiredInfoList") List<VouchersExpiredInfo> vouchersExpiredInfoList);



    /**
     * 删除 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     */
	public Integer deleteVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo);



    /**
     * 根据id删除一个 VouchersExpiredInfo
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteVouchersExpiredInfoById(Long id);



    /**
     * 修改 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     */
	public Integer updateVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo);



    /**
     * 根据id修改一个 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     */
	public Integer updateVouchersExpiredInfoById(VouchersExpiredInfo vouchersExpiredInfo);



    /**
     * 根据id查询一个 VouchersExpiredInfo
     * @param id
     * @return VouchersExpiredInfo    返回结果
     */
	public VouchersExpiredInfo findVouchersExpiredInfoById(Long id);



    /**
     * 查询一个 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return VouchersExpiredInfo    返回结果集
     */
	public VouchersExpiredInfo findOneVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo);



    /**
     * 统计 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    返回记录数
     */
	public Integer countVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo);



    /**
     * 查询 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return List<VouchersExpiredInfo>    返回结果集
     */
	public List<VouchersExpiredInfo> findVouchersExpiredInfo(@Param("vouchersExpiredInfo")VouchersExpiredInfo vouchersExpiredInfo, @Param("order")String order);



    /**
     * 分页查询 VouchersExpiredInfo
     * @param page 
     * @param vouchersExpiredInfo
     * @return List<VouchersExpiredInfo>    返回分页查询结果集
     */
	public List<VouchersExpiredInfo> findByPage(Page page, @Param("vouchersExpiredInfo")VouchersExpiredInfo vouchersExpiredInfo);



}