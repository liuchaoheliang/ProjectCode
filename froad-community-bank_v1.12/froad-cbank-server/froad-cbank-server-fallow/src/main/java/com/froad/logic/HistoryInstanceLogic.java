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
 * @Title: HistoryInstanceLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.HistoryInstance;

/**
 * 
 * <p>@Title: HistoryInstanceLogic.java</p>
 * <p>Description: 封装对HistoryInstance所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface HistoryInstanceLogic {


    /**
     * 增加 HistoryInstance
     * @param historyInstance
     * @return Long    主键ID
     */
	public Long addHistoryInstance(HistoryInstance historyInstance) ;



    /**
     * 删除 HistoryInstance
     * @param historyInstance
     * @return Boolean    是否成功
     */
	public Boolean deleteHistoryInstance(HistoryInstance historyInstance) ;



    /**
     * 根据id删除 HistoryInstance
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteHistoryInstanceById(Long id) ;



    /**
     * 修改 HistoryInstance
     * @param historyInstance
     * @return Boolean    是否成功
     */
	public Boolean updateHistoryInstance(HistoryInstance historyInstance) ;



    /**
     * 根据id修改 HistoryInstance
     * @param historyInstance
     * @return Boolean    是否成功
     */
	public Boolean updateHistoryInstanceById(HistoryInstance historyInstance) ;



    /**
     * 根据id查询单个 HistoryInstance
     * @param id
     * @return HistoryInstance    单个 HistoryInstance
     */
	public HistoryInstance findHistoryInstanceById(Long id) ;



    /**
     * 查询一个 HistoryInstance
     * @param historyInstance
     * @return HistoryInstance    单个 HistoryInstance
     */
	public HistoryInstance findOneHistoryInstance(HistoryInstance historyInstance) ;



    /**
     * 统计 HistoryInstance
     * @param historyInstance
     * @return Integer    返回记录数 HistoryInstance
     */
	public Integer countHistoryInstance(HistoryInstance historyInstance) ;



    /**
     * 查询 HistoryInstance
     * @param historyInstance
     * @return List<HistoryInstance>    HistoryInstance集合 
     */
	public List<HistoryInstance> findHistoryInstance(HistoryInstance historyInstance) ;



    /**
     * 分页查询 HistoryInstance
     * @param page
     * @param historyInstance
     * @return Page<HistoryInstance>    HistoryInstance分页结果 
     */
	public Page<HistoryInstance> findHistoryInstanceByPage(Page<HistoryInstance> page, HistoryInstance historyInstance) ;



}