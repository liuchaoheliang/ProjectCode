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
 * @Title: HistoryInstanceMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryInstance;

/**
 * 
 * <p>@Title: HistoryInstanceMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体HistoryInstance的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryInstanceMapper {


    /**
     * 增加 HistoryInstance
     * @param historyInstance
     * @return Long    受影响行数
     */
	public Long addHistoryInstance(HistoryInstance historyInstance);



    /**
     * 批量增加 HistoryInstance
     * @param historyInstanceList
     * @return Boolean    是否成功
     */
	public Boolean addHistoryInstanceByBatch(@Param("historyInstanceList")List<HistoryInstance> historyInstanceList);



    /**
     * 删除 HistoryInstance
     * @param historyInstance
     * @return Integer    受影响行数
     */
	public Integer deleteHistoryInstance(HistoryInstance historyInstance);



    /**
     * 根据id删除一个 HistoryInstance
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteHistoryInstanceById(Long id);

	/**
	 * 根据instanceId删除一个 HistoryInstance
	 * @param id
	 * @return Integer    受影响行数
	 */
	public Integer deleteHistoryInstanceByInstanceId(String instanceId) ;

    /**
     * 修改 HistoryInstance
     * @param historyInstance
     * @return Integer    受影响行数
     */
	public Integer updateHistoryInstance(HistoryInstance historyInstance);



    /**
     * 根据id修改一个 HistoryInstance
     * @param historyInstance
     * @return Integer    受影响行数
     */
	public Integer updateHistoryInstanceById(HistoryInstance historyInstance);
	
	/**
	 * 根据Instance修改一个 HistoryInstance
	 * @param historyInstance
	 * @return Integer    受影响行数
	 */
	public Integer updateHistoryInstanceByInstanceId(HistoryInstance historyInstance);



    /**
     * 根据id查询一个 HistoryInstance
     * @param id
     * @return HistoryInstance    返回结果
     */
	public HistoryInstance findHistoryInstanceById(Long id);
	
	
	public HistoryInstance findHistoryInstanceByInstanceId(String instanceId);



    /**
     * 查询一个 HistoryInstance
     * @param historyInstance
     * @return HistoryInstance    返回结果集
     */
	public HistoryInstance findOneHistoryInstance(HistoryInstance historyInstance);



    /**
     * 统计 HistoryInstance
     * @param historyInstance
     * @return Integer    返回记录数
     */
	public Integer countHistoryInstance(HistoryInstance historyInstance);



    /**
     * 查询 HistoryInstance
     * @param historyInstance
     * @return List<HistoryInstance>    返回结果集
     */
	public List<HistoryInstance> findHistoryInstance(@Param("historyInstance")HistoryInstance historyInstance, @Param("order")String order);



    /**
     * 分页查询 HistoryInstance
     * @param page 
     * @param historyInstance
     * @return List<HistoryInstance>    返回分页查询结果集
     */
	public List<HistoryInstance> findByPage(Page page, @Param("historyInstance")HistoryInstance historyInstance);



}