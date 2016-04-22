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
 * @Title: ActiveOrderDetailMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveOrderDetail;

/**
 * 
 * <p>@Title: ActiveOrderDetailMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体ActiveOrderDetail的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ActiveOrderDetailMapper {


    /**
     * 增加 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Long    受影响行数
     */
	public Long addActiveOrderDetail(ActiveOrderDetail activeOrderDetail);



    /**
     * 批量增加 ActiveOrderDetail
     * @param activeOrderDetailList
     * @return Boolean    是否成功
     */
	public Boolean addActiveOrderDetailByBatch(@Param("activeOrderDetailList")List<ActiveOrderDetail> activeOrderDetailList);



    /**
     * 删除 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    受影响行数
     */
	public Integer deleteActiveOrderDetail(ActiveOrderDetail activeOrderDetail);



    /**
     * 根据id删除一个 ActiveOrderDetail
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteActiveOrderDetailById(Long id);



    /**
     * 修改 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    受影响行数
     */
	public Integer updateActiveOrderDetail(ActiveOrderDetail activeOrderDetail);
	
	
	
	 /**
     * 修改 updateActiveOrderDetailCount
     * @param activeOrderDetail
     * @return Integer    受影响行数
     */
	public Integer updateActiveOrderDetailCount(ActiveOrderDetail activeOrderDetail);

	
	public Integer updateActiveOrderDetailCountBack(ActiveOrderDetail activeOrderDetail);


    /**
     * 根据id修改一个 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    受影响行数
     */
	public Integer updateActiveOrderDetailById(ActiveOrderDetail activeOrderDetail);

	
	 /**
     * 根据orderId修改一个 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    受影响行数
     */
	public Integer updateActiveOrderDetailByOrderId(ActiveOrderDetail activeOrderDetail);


    /**
     * 根据id查询一个 ActiveOrderDetail
     * @param id
     * @return ActiveOrderDetail    返回结果
     */
	public ActiveOrderDetail findActiveOrderDetailById(Long id);



    /**
     * 查询一个 ActiveOrderDetail
     * @param activeOrderDetail
     * @return ActiveOrderDetail    返回结果集
     */
	public ActiveOrderDetail findOneActiveOrderDetail(ActiveOrderDetail activeOrderDetail);



    /**
     * 统计 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    返回记录数
     */
	public Integer countActiveOrderDetail(ActiveOrderDetail activeOrderDetail);



    /**
     * 查询 ActiveOrderDetail
     * @param activeOrderDetail
     * @return List<ActiveOrderDetail>    返回结果集
     */
	public List<ActiveOrderDetail> findActiveOrderDetail(@Param("activeOrderDetail")ActiveOrderDetail activeOrderDetail, @Param("order")String order);



    /**
     * 分页查询 ActiveOrderDetail
     * @param page 
     * @param activeOrderDetail
     * @return List<ActiveOrderDetail>    返回分页查询结果集
     */
	public List<ActiveOrderDetail> findByPage(Page page, @Param("activeOrderDetail")ActiveOrderDetail activeOrderDetail);



}