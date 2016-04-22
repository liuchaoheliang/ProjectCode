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
 * @Title: HistoryTaskLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryTask;

/**
 * 
 * <p>@Title: HistoryTaskLogic.java</p>
 * <p>Description: 封装对HistoryTask所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryTaskLogic {


    /**
     * 增加 HistoryTask
     * @param historyTask
     * @return Long    主键ID
     */
	public Long addHistoryTask(HistoryTask historyTask) ;



    /**
     * 删除 HistoryTask
     * @param historyTask
     * @return Boolean    是否成功
     */
	public Boolean deleteHistoryTask(HistoryTask historyTask) ;



    /**
     * 根据id删除 HistoryTask
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteHistoryTaskById(Long id) ;



    /**
     * 修改 HistoryTask
     * @param historyTask
     * @return Boolean    是否成功
     */
	public Boolean updateHistoryTask(HistoryTask historyTask) ;



    /**
     * 根据id修改 HistoryTask
     * @param historyTask
     * @return Boolean    是否成功
     */
	public Boolean updateHistoryTaskById(HistoryTask historyTask) ;



    /**
     * 根据id查询单个 HistoryTask
     * @param id
     * @return HistoryTask    单个 HistoryTask
     */
	public HistoryTask findHistoryTaskById(Long id) ;



    /**
     * 查询一个 HistoryTask
     * @param historyTask
     * @return HistoryTask    单个 HistoryTask
     */
	public HistoryTask findOneHistoryTask(HistoryTask historyTask) ;



    /**
     * 统计 HistoryTask
     * @param historyTask
     * @return Integer    返回记录数 HistoryTask
     */
	public Integer countHistoryTask(HistoryTask historyTask) ;



    /**
     * 查询 HistoryTask
     * @param historyTask
     * @return List<HistoryTask>    HistoryTask集合 
     */
	public List<HistoryTask> findHistoryTask(HistoryTask historyTask) ;



    /**
     * 分页查询 HistoryTask
     * @param page
     * @param historyTask
     * @return Page<HistoryTask>    HistoryTask分页结果 
     */
	public Page<HistoryTask> findHistoryTaskByPage(Page<HistoryTask> page, HistoryTask historyTask) ;



}