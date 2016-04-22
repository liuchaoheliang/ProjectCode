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
 * @Title: ProcessConfigLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.ProcessConfig;

/**
 * 
 * <p>@Title: ProcessConfigLogic.java</p>
 * <p>Description: 封装对ProcessConfig所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessConfigLogic {


    /**
     * 增加 ProcessConfig
     * @param processConfig
     * @return Long    主键ID
     */
	public Long addProcessConfig(ProcessConfig processConfig) ;



    /**
     * 删除 ProcessConfig
     * @param processConfig
     * @return Boolean    是否成功
     */
	public Boolean deleteProcessConfig(ProcessConfig processConfig) ;



    /**
     * 根据id删除 ProcessConfig
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteProcessConfigById(Long id) ;



    /**
     * 修改 ProcessConfig
     * @param processConfig
     * @return Boolean    是否成功
     */
	public Boolean updateProcessConfig(ProcessConfig processConfig) ;



    /**
     * 根据id修改 ProcessConfig
     * @param processConfig
     * @return Boolean    是否成功
     */
	public Boolean updateProcessConfigById(ProcessConfig processConfig) ;



    /**
     * 根据id查询单个 ProcessConfig
     * @param id
     * @return ProcessConfig    单个 ProcessConfig
     */
	public ProcessConfig findProcessConfigById(Long id) ;



    /**
     * 查询一个 ProcessConfig
     * @param processConfig
     * @return ProcessConfig    单个 ProcessConfig
     */
	public ProcessConfig findOneProcessConfig(ProcessConfig processConfig) ;



    /**
     * 统计 ProcessConfig
     * @param processConfig
     * @return Integer    返回记录数 ProcessConfig
     */
	public Integer countProcessConfig(ProcessConfig processConfig) ;



    /**
     * 查询 ProcessConfig
     * @param processConfig
     * @return List<ProcessConfig>    ProcessConfig集合 
     */
	public List<ProcessConfig> findProcessConfig(ProcessConfig processConfig) ;



    /**
     * 分页查询 ProcessConfig
     * @param page
     * @param processConfig
     * @return Page<ProcessConfig>    ProcessConfig分页结果 
     */
	public Page<ProcessConfig> findProcessConfigByPage(Page<ProcessConfig> page, ProcessConfig processConfig) ;



}