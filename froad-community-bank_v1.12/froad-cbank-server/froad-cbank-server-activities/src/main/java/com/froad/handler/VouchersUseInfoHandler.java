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
 * @Title: VouchersUseInfoHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.VouchersUseInfo;

/**
 * 
 * <p>@Title: VouchersUseInfoHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体VouchersUseInfo的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface VouchersUseInfoHandler {


    /**
     * 增加 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addVouchersUseInfo(VouchersUseInfo vouchersUseInfo)  throws Exception;



    /**
     * 增加 VouchersUseInfo
     * @param sqlSession
     * @param vouchersUseInfo
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addVouchersUseInfo(SqlSession sqlSession, VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 删除 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 删除 VouchersUseInfo
     * @param sqlSession
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersUseInfo(SqlSession sqlSession, VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 根据id删除 VouchersUseInfo
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersUseInfoById(Long id) throws Exception;



    /**
     * 根据id删除 VouchersUseInfo
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteVouchersUseInfoById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 修改 VouchersUseInfo
     * @param sqlSession
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersUseInfo(SqlSession sqlSession, VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 根据id修改 VouchersUseInfo
     * @param VouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersUseInfoById(VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 根据id修改 VouchersUseInfo
     * @param sqlSession
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateVouchersUseInfoById(SqlSession sqlSession, VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 根据id查询单个 VouchersUseInfo
     * @param id
     * @return VouchersUseInfo    单个VouchersUseInfo
     * @throws Exception
     */
	public VouchersUseInfo findVouchersUseInfoById(Long id) throws Exception;



    /**
     * 查询一个 VouchersUseInfo
     * @param vouchersUseInfo
     * @return VouchersUseInfo    单个VouchersUseInfo
     * @throws Exception
     */
	public VouchersUseInfo findOneVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 查询统计 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    返回记录数VouchersUseInfo
     * @throws Exception
     */
	public Integer countVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 查询 VouchersUseInfo
     * @param vouchersUseInfo
     * @return List<VouchersUseInfo>    VouchersUseInfo集合 
     * @throws Exception
     */
	public List<VouchersUseInfo> findVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception;



    /**
     * 查询 VouchersUseInfo
     * @param vouchersUseInfo
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<VouchersUseInfo>    VouchersUseInfo集合 
     * @throws Exception
     */
	public List<VouchersUseInfo> findVouchersUseInfo(VouchersUseInfo vouchersUseInfo, String order) throws Exception;



    /**
     * 分页查询 VouchersUseInfo
     * @param page
     * @param vouchersUseInfo
     * @return Page<VouchersUseInfo>    VouchersUseInfo分页结果 
     * @throws Exception 
     */
	public Page<VouchersUseInfo> findVouchersUseInfoByPage(Page<VouchersUseInfo> page, VouchersUseInfo vouchersUseInfo) throws Exception;



}