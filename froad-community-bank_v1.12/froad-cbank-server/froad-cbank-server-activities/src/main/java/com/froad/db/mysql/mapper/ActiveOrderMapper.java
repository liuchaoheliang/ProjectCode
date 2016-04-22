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
 * @Title: ActiveOrderMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveOrder;

/**
 * 
 * <p>@Title: ActiveOrderMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体ActiveOrder的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveOrderMapper {


    /**
     * 增加 ActiveOrder
     * @param activeOrder
     * @return Long    受影响行数
     */
	public Long addActiveOrder(ActiveOrder activeOrder);



    /**
     * 批量增加 ActiveOrder
     * @param activeOrderList
     * @return Boolean    是否成功
     */
	public Boolean addActiveOrderByBatch(List<ActiveOrder> activeOrderList);



    /**
     * 删除 ActiveOrder
     * @param activeOrder
     * @return Integer    受影响行数
     */
	public Integer deleteActiveOrder(ActiveOrder activeOrder);



    /**
     * 根据id删除一个 ActiveOrder
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActiveOrderById(Long id);



    /**
     * 修改 ActiveOrder
     * @param activeOrder
     * @return Integer    受影响行数
     */
	public Integer updateActiveOrder(ActiveOrder activeOrder);



    /**
     * 根据id修改一个 ActiveOrder
     * @param activeOrder
     * @return Integer    受影响行数
     */
	public Integer updateActiveOrderById(ActiveOrder activeOrder);



    /**
     * 根据id查询一个 ActiveOrder
     * @param id
     * @return ActiveOrder    返回结果
     */
	public ActiveOrder findActiveOrderById(Long id);



    /**
     * 查询一个 ActiveOrder
     * @param activeOrder
     * @return ActiveOrder    返回结果集
     */
	public ActiveOrder findOneActiveOrder(ActiveOrder activeOrder);



    /**
     * 统计 ActiveOrder
     * @param activeOrder
     * @return Integer    返回记录数
     */
	public Integer countActiveOrder(ActiveOrder activeOrder);



    /**
     * 查询 ActiveOrder
     * @param activeOrder
     * @return List<ActiveOrder>    返回结果集
     */
	public List<ActiveOrder> findActiveOrder(@Param("activeOrder")ActiveOrder activeOrder, @Param("order")String order);



    /**
     * 分页查询 ActiveOrder
     * @param page 
     * @param activeOrder
     * @return List<ActiveOrder>    返回分页查询结果集
     */
	public List<ActiveOrder> findByPage(Page page, @Param("activeOrder")ActiveOrder activeOrder);

	
	/**
	  * @Title: updateActiveOrderByOrderId
	  * @Description: 根据OrderId
	  * @author: zengfanting 2015年11月9日
	  * @modify: zengfanting 2015年11月9日
	  * @param activeOrder
	  * @return
	 */
	public Integer updateActiveOrderByOrderId(ActiveOrder activeOrder);
	
	public Integer updateActiveOrderReturnBillNoAndReturnMoney(ActiveOrder activeOrder);
	

}