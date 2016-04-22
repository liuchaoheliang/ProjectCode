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
 * @Title: HistoryTaskActorLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryTaskActor;

/**
 * 
 * <p>@Title: HistoryTaskActorLogic.java</p>
 * <p>Description: 封装对HistoryTaskActor所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryTaskActorLogic {


    /**
     * 增加 HistoryTaskActor
     * @param historyTaskActor
     * @return String    主键ID
     */
	public String addHistoryTaskActor(HistoryTaskActor historyTaskActor) ;



    /**
     * 删除 HistoryTaskActor
     * @param historyTaskActor
     * @return Boolean    是否成功
     */
	public Boolean deleteHistoryTaskActor(HistoryTaskActor historyTaskActor) ;



    /**
     * 根据taskId删除 HistoryTaskActor
     * @param taskId
     * @return Boolean    是否成功
     */
	public Boolean deleteHistoryTaskActorByTaskId(String taskId) ;



    /**
     * 修改 HistoryTaskActor
     * @param historyTaskActor
     * @return Boolean    是否成功
     */
	public Boolean updateHistoryTaskActor(HistoryTaskActor historyTaskActor) ;



    /**
     * 根据taskId修改 HistoryTaskActor
     * @param historyTaskActor
     * @return Boolean    是否成功
     */
	public Boolean updateHistoryTaskActorByTaskId(HistoryTaskActor historyTaskActor) ;



    /**
     * 根据taskId查询单个 HistoryTaskActor
     * @param taskId
     * @return HistoryTaskActor    单个 HistoryTaskActor
     */
	public HistoryTaskActor findHistoryTaskActorByTaskId(String taskId) ;



    /**
     * 查询一个 HistoryTaskActor
     * @param historyTaskActor
     * @return HistoryTaskActor    单个 HistoryTaskActor
     */
	public HistoryTaskActor findOneHistoryTaskActor(HistoryTaskActor historyTaskActor) ;



    /**
     * 统计 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    返回记录数 HistoryTaskActor
     */
	public Integer countHistoryTaskActor(HistoryTaskActor historyTaskActor) ;



    /**
     * 查询 HistoryTaskActor
     * @param historyTaskActor
     * @return List<HistoryTaskActor>    HistoryTaskActor集合 
     */
	public List<HistoryTaskActor> findHistoryTaskActor(HistoryTaskActor historyTaskActor) ;



    /**
     * 分页查询 HistoryTaskActor
     * @param page
     * @param historyTaskActor
     * @return Page<HistoryTaskActor>    HistoryTaskActor分页结果 
     */
	public Page<HistoryTaskActor> findHistoryTaskActorByPage(Page<HistoryTaskActor> page, HistoryTaskActor historyTaskActor) ;



}