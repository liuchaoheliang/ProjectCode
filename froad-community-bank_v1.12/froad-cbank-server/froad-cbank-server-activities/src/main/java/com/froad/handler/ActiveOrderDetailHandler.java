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
 * @Title: ActiveOrderDetailHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveOrderDetail;

/**
 * 
 * <p>@Title: ActiveOrderDetailHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体ActiveOrderDetail的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveOrderDetailHandler {


    /**
     * 增加 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveOrderDetail(ActiveOrderDetail activeOrderDetail)  throws Exception;



    /**
     * 增加 ActiveOrderDetail
     * @param sqlSession
     * @param activeOrderDetail
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveOrderDetail(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception;

	/**
	 * 批量新增 ActiveOrderDetail
	 * @param sqlSession
	 * @param List<ActiveOrderDetail>
	 * */
	public boolean addActiveOrderDetailByBatch(SqlSession sqlSession, List<ActiveOrderDetail> activeOrderDetailList);

    /**
     * 删除 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception;



    /**
     * 删除 ActiveOrderDetail
     * @param sqlSession
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveOrderDetail(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception;



    /**
     * 根据id删除 ActiveOrderDetail
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveOrderDetailById(Long id) throws Exception;



    /**
     * 根据id删除 ActiveOrderDetail
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveOrderDetailById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception;

	
    /**
     * 修改 ActiveOrderDetail中的购买产品数量
     * @param activeOrderDetail
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderDetailCount(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception;


	
	public Integer updateActiveOrderDetailCountBack(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception;



    /**
     * 修改 ActiveOrderDetail
     * @param sqlSession
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderDetail(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception;



    /**
     * 根据id修改 ActiveOrderDetail
     * @param ActiveOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderDetailById(ActiveOrderDetail activeOrderDetail) throws Exception;


    /**
     * 根据orderId修改 ActiveOrderDetail
     * @param ActiveOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderDetailByOrderId(ActiveOrderDetail activeOrderDetail) throws Exception;




    /**
     * 根据id修改 ActiveOrderDetail
     * @param sqlSession
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderDetailById(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception;


	

    /**
     * 根据id查询单个 ActiveOrderDetail
     * @param id
     * @return ActiveOrderDetail    单个ActiveOrderDetail
     * @throws Exception
     */
	public ActiveOrderDetail findActiveOrderDetailById(Long id) throws Exception;



    /**
     * 查询一个 ActiveOrderDetail
     * @param activeOrderDetail
     * @return ActiveOrderDetail    单个ActiveOrderDetail
     * @throws Exception
     */
	public ActiveOrderDetail findOneActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception;


	public ActiveOrderDetail findOneActiveOrderDetail(SqlSession sqlSession ,ActiveOrderDetail activeOrderDetail) throws Exception;

    /**
     * 查询统计 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    返回记录数ActiveOrderDetail
     * @throws Exception
     */
	public Integer countActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception;



    /**
     * 查询 ActiveOrderDetail
     * @param activeOrderDetail
     * @return List<ActiveOrderDetail>    ActiveOrderDetail集合 
     * @throws Exception
     */
	public List<ActiveOrderDetail> findActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception;
	
	

	
 
	public List<ActiveOrderDetail> findActiveOrderDetail(SqlSession sqlSession,ActiveOrderDetail activeOrderDetail) throws Exception;

	


    /**
     * 查询 ActiveOrderDetail
     * @param activeOrderDetail
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveOrderDetail>    ActiveOrderDetail集合 
     * @throws Exception
     */
	public List<ActiveOrderDetail> findActiveOrderDetail(ActiveOrderDetail activeOrderDetail, String order) throws Exception;



    /**
     * 分页查询 ActiveOrderDetail
     * @param page
     * @param activeOrderDetail
     * @return Page<ActiveOrderDetail>    ActiveOrderDetail分页结果 
     * @throws Exception 
     */
	public Page<ActiveOrderDetail> findActiveOrderDetailByPage(Page<ActiveOrderDetail> page, ActiveOrderDetail activeOrderDetail) throws Exception;



}