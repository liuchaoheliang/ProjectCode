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
 * @Title: ProcessMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.Process;

/**
 * 
 * <p>@Title: ProcessMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体Process的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessMapper {


    /**
     * 增加 Process
     * @param process
     * @return Long    受影响行数
     */
	public Long addProcess(Process process);



    /**
     * 批量增加 Process
     * @param processList
     * @return Boolean    是否成功
     */
	public Boolean addProcessByBatch(@Param("processList")List<Process> processList);



    /**
     * 删除 Process
     * @param process
     * @return Integer    受影响行数
     */
	public Integer deleteProcess(Process process);



    /**
     * 根据id删除一个 Process
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteProcessById(Long id);



    /**
     * 根据processId删除一个 Process
     * @param processId
     * @return Integer    受影响行数
     */
	public Integer deleteProcessByProcessId(String processId);



    /**
     * 修改 Process
     * @param process
     * @return Integer    受影响行数
     */
	public Integer updateProcess(Process process);



    /**
     * 根据id修改一个 Process
     * @param process
     * @return Integer    受影响行数
     */
	public Integer updateProcessById(Process process);



    /**
     * 根据processId修改一个 Process
     * @param process
     * @return Integer    受影响行数
     */
	public Integer updateProcessByProcessId(Process process);



    /**
     * 根据id查询一个 Process
     * @param id
     * @return Process    返回结果
     */
	public Process findProcessById(Long id);



    /**
     * 根据processId查询一个 Process
     * @param processId
     * @return Process    返回结果
     */
	public Process findProcessByProcessId(String processId);



    /**
     * 查询一个 Process
     * @param process
     * @return Process    返回结果集
     */
	public Process findOneProcess(Process process);



    /**
     * 统计 Process
     * @param process
     * @return Integer    返回记录数
     */
	public Integer countProcess(Process process);



    /**
     * 查询 Process
     * @param process
     * @return List<Process>    返回结果集
     */
	public List<Process> findProcess(@Param("process")Process process, @Param("order")String order);



    /**
     * 分页查询 Process
     * @param page 
     * @param process
     * @return List<Process>    返回分页查询结果集
     */
	public List<Process> findByPage(Page page, @Param("process")Process process);



}