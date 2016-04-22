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
 * @Title: VouchersUseInfoMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersUseInfo;

/**
 * 
 * <p>@Title: VouchersUseInfoMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体VouchersUseInfo的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersUseInfoMapper {


    /**
     * 增加 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Long    受影响行数
     */
	public Long addVouchersUseInfo(VouchersUseInfo vouchersUseInfo);



    /**
     * 批量增加 VouchersUseInfo
     * @param vouchersUseInfoList
     * @return Boolean    是否成功
     */
	public Boolean addVouchersUseInfoByBatch(@Param("vouchersUseInfoList") List<VouchersUseInfo> vouchersUseInfoList);



    /**
     * 删除 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     */
	public Integer deleteVouchersUseInfo(VouchersUseInfo vouchersUseInfo);



    /**
     * 根据id删除一个 VouchersUseInfo
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteVouchersUseInfoById(Long id);



    /**
     * 修改 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     */
	public Integer updateVouchersUseInfo(VouchersUseInfo vouchersUseInfo);



    /**
     * 根据id修改一个 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     */
	public Integer updateVouchersUseInfoById(VouchersUseInfo vouchersUseInfo);



    /**
     * 根据id查询一个 VouchersUseInfo
     * @param id
     * @return VouchersUseInfo    返回结果
     */
	public VouchersUseInfo findVouchersUseInfoById(Long id);



    /**
     * 查询一个 VouchersUseInfo
     * @param vouchersUseInfo
     * @return VouchersUseInfo    返回结果集
     */
	public VouchersUseInfo findOneVouchersUseInfo(VouchersUseInfo vouchersUseInfo);



    /**
     * 统计 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    返回记录数
     */
	public Integer countVouchersUseInfo(VouchersUseInfo vouchersUseInfo);



    /**
     * 查询 VouchersUseInfo
     * @param vouchersUseInfo
     * @return List<VouchersUseInfo>    返回结果集
     */
	public List<VouchersUseInfo> findVouchersUseInfo(@Param("vouchersUseInfo")VouchersUseInfo vouchersUseInfo, @Param("order")String order);



    /**
     * 分页查询 VouchersUseInfo
     * @param page 
     * @param vouchersUseInfo
     * @return List<VouchersUseInfo>    返回分页查询结果集
     */
	public List<VouchersUseInfo> findByPage(Page page, @Param("vouchersUseInfo")VouchersUseInfo vouchersUseInfo);



}