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
 * @Title: HistoryTaskActorMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryTaskActor;

/**
 * 
 * <p>@Title: HistoryTaskActorMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体HistoryTaskActor的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryTaskActorMapper {


    /**
     * 增加 HistoryTaskActor
     * @param historyTaskActor
     * @return Long    受影响行数
     */
	public Long addHistoryTaskActor(HistoryTaskActor historyTaskActor);



    /**
     * 批量增加 HistoryTaskActor
     * @param historyTaskActorList
     * @return Boolean    是否成功
     */
	public Boolean addHistoryTaskActorByBatch(@Param("historyTaskActorList")List<HistoryTaskActor> historyTaskActorList);



    /**
     * 删除 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    受影响行数
     */
	public Integer deleteHistoryTaskActor(HistoryTaskActor historyTaskActor);



    /**
     * 根据taskId删除一个 HistoryTaskActor
     * @param taskId
     * @return Integer    受影响行数
     */
	public Integer deleteHistoryTaskActorByTaskId(String taskId);



    /**
     * 修改 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    受影响行数
     */
	public Integer updateHistoryTaskActor(HistoryTaskActor historyTaskActor);



    /**
     * 根据taskId修改一个 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    受影响行数
     */
	public Integer updateHistoryTaskActorByTaskId(HistoryTaskActor historyTaskActor);



    /**
     * 根据taskId查询一个 HistoryTaskActor
     * @param taskId
     * @return HistoryTaskActor    返回结果
     */
	public HistoryTaskActor findHistoryTaskActorByTaskId(String taskId);



    /**
     * 查询一个 HistoryTaskActor
     * @param historyTaskActor
     * @return HistoryTaskActor    返回结果集
     */
	public HistoryTaskActor findOneHistoryTaskActor(HistoryTaskActor historyTaskActor);



    /**
     * 统计 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    返回记录数
     */
	public Integer countHistoryTaskActor(HistoryTaskActor historyTaskActor);



    /**
     * 查询 HistoryTaskActor
     * @param historyTaskActor
     * @return List<HistoryTaskActor>    返回结果集
     */
	public List<HistoryTaskActor> findHistoryTaskActor(@Param("historyTaskActor")HistoryTaskActor historyTaskActor, @Param("order")String order);



    /**
     * 分页查询 HistoryTaskActor
     * @param page 
     * @param historyTaskActor
     * @return List<HistoryTaskActor>    返回分页查询结果集
     */
	public List<HistoryTaskActor> findByPage(Page page, @Param("historyTaskActor")HistoryTaskActor historyTaskActor);



}