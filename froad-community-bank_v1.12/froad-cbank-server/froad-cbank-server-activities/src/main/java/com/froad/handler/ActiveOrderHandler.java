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
 * @Title: ActiveOrderHandler.java
 * @Package com.froad.handler
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveOrderDetail;

/**
 * 
 * <p>@Title: ActiveOrderHandler.java</p>
 * <p>Description: 封装对MySQL对应的实体ActiveOrder的CURD操作接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveOrderHandler {


    /**
     * 增加 ActiveOrder
     * @param activeOrder
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveOrder(ActiveOrder activeOrder)  throws Exception;



    /**
     * 增加 ActiveOrder
     * @param sqlSession
     * @param activeOrder
     * @return Long    主键ID
     * @throws Exception
     */
	public Long addActiveOrder(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception;

	/**
	 * 增加 营销订单 和 营销订单详情
	 * @param ActiveOrder
	 * @param List<ActiveOrderDetail>
	 * return boolean
	 * */
	public boolean addActiveOrderAndOrderDetailList(ActiveOrder activeOrder, List<ActiveOrderDetail> activeOrderDetailList);

	/**
	 * 修改 营销订单主表详情 和 新增营销订单详情列表
	 * @param ActiveOrder
	 * @param List<ActiveOrderDetail>
	 * return boolean
	 * */
	public boolean updateActiveOrderdetailAndAddOrderDetailList(ActiveOrder activeOrder, List<ActiveOrderDetail> activeOrderDetailList);
	
    /**
     * 删除 ActiveOrder
     * @param activeOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveOrder(ActiveOrder activeOrder) throws Exception;



    /**
     * 删除 ActiveOrder
     * @param sqlSession
     * @param activeOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveOrder(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception;



    /**
     * 根据id删除 ActiveOrder
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveOrderById(Long id) throws Exception;



    /**
     * 根据id删除 ActiveOrder
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer deleteActiveOrderById(SqlSession sqlSession, Long id) throws Exception;



    /**
     * 修改 ActiveOrder
     * @param activeOrder
     * @return Boolean    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrder(ActiveOrder activeOrder) throws Exception;



    /**
     * 修改 ActiveOrder
     * @param sqlSession
     * @param activeOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrder(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception;



    /**
     * 根据id修改 ActiveOrder
     * @param ActiveOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderById(ActiveOrder activeOrder) throws Exception;

	   /**
     * 根据id修改 ActiveOrder
     * @param ActiveOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderByOrderId(ActiveOrder activeOrder) throws Exception;


	

    /**
     * 根据id修改 ActiveOrder
     * @param sqlSession
     * @param activeOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	public Integer updateActiveOrderById(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception;



    /**
     * 根据id查询单个 ActiveOrder
     * @param id
     * @return ActiveOrder    单个ActiveOrder
     * @throws Exception
     */
	public ActiveOrder findActiveOrderById(Long id) throws Exception;



    /**
     * 查询一个 ActiveOrder
     * @param activeOrder
     * @return ActiveOrder    单个ActiveOrder
     * @throws Exception
     */
	public ActiveOrder findOneActiveOrder(ActiveOrder activeOrder) throws Exception;
	
	/**
	  * @Title: findOneActiveOrder
	  * @Description: 用于同一个事物里面的查询
	  * @author: zengfanting 2015年11月12日
	  * @modify: zengfanting 2015年11月12日
	  * @param sqlSession
	  * @param activeOrder
	  * @return
	  * @throws Exception
	 */
	public ActiveOrder findOneActiveOrder(SqlSession sqlSession,ActiveOrder activeOrder) throws Exception;



    /**
     * 查询统计 ActiveOrder
     * @param activeOrder
     * @return Integer    返回记录数ActiveOrder
     * @throws Exception
     */
	public Integer countActiveOrder(ActiveOrder activeOrder) throws Exception;



    /**
     * 查询 ActiveOrder
     * @param activeOrder
     * @return List<ActiveOrder>    ActiveOrder集合 
     * @throws Exception
     */
	public List<ActiveOrder> findActiveOrder(ActiveOrder activeOrder) throws Exception;



    /**
     * 查询 ActiveOrder
     * @param activeOrder
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveOrder>    ActiveOrder集合 
     * @throws Exception
     */
	public List<ActiveOrder> findActiveOrder(ActiveOrder activeOrder, String order) throws Exception;



    /**
     * 分页查询 ActiveOrder
     * @param page
     * @param activeOrder
     * @return Page<ActiveOrder>    ActiveOrder分页结果 
     * @throws Exception 
     */
	public Page<ActiveOrder> findActiveOrderByPage(Page<ActiveOrder> page, ActiveOrder activeOrder) throws Exception;
	
	public Integer updateActiveOrderByOrderId(SqlSession sqlSession,ActiveOrder activeOrder)throws Exception;
	
	public Integer updateActiveOrderReturnBillNoAndReturnMoney(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception;

	
}