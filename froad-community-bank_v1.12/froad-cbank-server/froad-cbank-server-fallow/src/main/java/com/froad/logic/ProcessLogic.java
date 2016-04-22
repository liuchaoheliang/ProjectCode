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
 * @Title: ProcessLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Process;

/**
 * 
 * <p>@Title: ProcessLogic.java</p>
 * <p>Description: 封装对Process所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessLogic {


    /**
     * 增加 Process
     * @param process
     * @return Long    主键ID
     */
	public Long addProcess(Process process) ;



    /**
     * 删除 Process
     * @param process
     * @return Boolean    是否成功
     */
	public Boolean deleteProcess(Process process) ;



    /**
     * 根据id删除 Process
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteProcessById(Long id) ;



    /**
     * 根据processId删除 Process
     * @param processId
     * @return Boolean    是否成功
     */
	public Boolean deleteProcessByProcessId(String processId) ;



    /**
     * 修改 Process
     * @param process
     * @return Boolean    是否成功
     */
	public Boolean updateProcess(Process process) ;



    /**
     * 根据id修改 Process
     * @param process
     * @return Boolean    是否成功
     */
	public Boolean updateProcessById(Process process) ;



    /**
     * 根据processId修改 Process
     * @param process
     * @return Boolean    是否成功
     */
	public Boolean updateProcessByProcessId(Process process) ;



    /**
     * 根据id查询单个 Process
     * @param id
     * @return Process    单个 Process
     */
	public Process findProcessById(Long id) ;



    /**
     * 根据processId查询单个 Process
     * @param processId
     * @return Process    单个 Process
     */
	public Process findProcessByProcessId(String processId) ;



    /**
     * 查询一个 Process
     * @param process
     * @return Process    单个 Process
     */
	public Process findOneProcess(Process process) ;



    /**
     * 统计 Process
     * @param process
     * @return Integer    返回记录数 Process
     */
	public Integer countProcess(Process process) ;



    /**
     * 查询 Process
     * @param process
     * @return List<Process>    Process集合 
     */
	public List<Process> findProcess(Process process) ;



    /**
     * 分页查询 Process
     * @param page
     * @param process
     * @return Page<Process>    Process分页结果 
     */
	public Page<Process> findProcessByPage(Page<Process> page, Process process) ;



}