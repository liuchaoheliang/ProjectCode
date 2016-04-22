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
 * @Title: VouchersExpiredInfoHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersExpiredInfo;

/**
 * 
 * <p>@Title: VouchersExpiredInfoHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体VouchersExpiredInfo的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersExpiredInfoHandler {


    /**
     * 增加 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo)  throws Exception;



    /**
     * 增加 VouchersExpiredInfo
     * @param sqlSession
     * @param vouchersExpiredInfo
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addVouchersExpiredInfo(SqlSession sqlSession, VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 删除 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 删除 VouchersExpiredInfo
     * @param sqlSession
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersExpiredInfo(SqlSession sqlSession, VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 根据id删除 VouchersExpiredInfo
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersExpiredInfoById(Long id) throws Exception;



    /**
     * 根据id删除 VouchersExpiredInfo
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersExpiredInfoById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 修改 VouchersExpiredInfo
     * @param sqlSession
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersExpiredInfo(SqlSession sqlSession, VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 根据id修改 VouchersExpiredInfo
     * @param VouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersExpiredInfoById(VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 根据id修改 VouchersExpiredInfo
     * @param sqlSession
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersExpiredInfoById(SqlSession sqlSession, VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 根据id查询单个 VouchersExpiredInfo
     * @param id
     * @return VouchersExpiredInfo    单个VouchersExpiredInfo
     * @throws Exception
     */
	public VouchersExpiredInfo findVouchersExpiredInfoById(Long id) throws Exception;



    /**
     * 查询一个 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return VouchersExpiredInfo    单个VouchersExpiredInfo
     * @throws Exception
     */
	public VouchersExpiredInfo findOneVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 查询统计 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    返回记录数VouchersExpiredInfo
     * @throws Exception
     */
	public Integer countVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 查询 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return List<VouchersExpiredInfo>    VouchersExpiredInfo集合 
     * @throws Exception
     */
	public List<VouchersExpiredInfo> findVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



    /**
     * 查询 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<VouchersExpiredInfo>    VouchersExpiredInfo集合 
     * @throws Exception
     */
	public List<VouchersExpiredInfo> findVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo, String order) throws Exception;



    /**
     * 分页查询 VouchersExpiredInfo
     * @param page
     * @param vouchersExpiredInfo
     * @return Page<VouchersExpiredInfo>    VouchersExpiredInfo分页结果 
     * @throws Exception 
     */
	public Page<VouchersExpiredInfo> findVouchersExpiredInfoByPage(Page<VouchersExpiredInfo> page, VouchersExpiredInfo vouchersExpiredInfo) throws Exception;



}