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
 * @Title: ProcessConfigMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.ProcessConfig;

/**
 * 
 * <p>@Title: ProcessConfigMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体ProcessConfig的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessConfigMapper {


    /**
     * 增加 ProcessConfig
     * @param processConfig
     * @return Long    受影响行数
     */
	public Long addProcessConfig(ProcessConfig processConfig);



    /**
     * 批量增加 ProcessConfig
     * @param processConfigList
     * @return Boolean    是否成功
     */
	public Boolean addProcessConfigByBatch(@Param("processConfigList")List<ProcessConfig> processConfigList);



    /**
     * 删除 ProcessConfig
     * @param processConfig
     * @return Integer    受影响行数
     */
	public Integer deleteProcessConfig(ProcessConfig processConfig);



    /**
     * 根据id删除一个 ProcessConfig
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteProcessConfigById(Long id);



    /**
     * 修改 ProcessConfig
     * @param processConfig
     * @return Integer    受影响行数
     */
	public Integer updateProcessConfig(ProcessConfig processConfig);



    /**
     * 根据id修改一个 ProcessConfig
     * @param processConfig
     * @return Integer    受影响行数
     */
	public Integer updateProcessConfigById(ProcessConfig processConfig);



    /**
     * 根据id查询一个 ProcessConfig
     * @param id
     * @return ProcessConfig    返回结果
     */
	public ProcessConfig findProcessConfigById(Long id);



    /**
     * 查询一个 ProcessConfig
     * @param processConfig
     * @return ProcessConfig    返回结果集
     */
	public ProcessConfig findOneProcessConfig(ProcessConfig processConfig);



    /**
     * 统计 ProcessConfig
     * @param processConfig
     * @return Integer    返回记录数
     */
	public Integer countProcessConfig(ProcessConfig processConfig);



    /**
     * 查询 ProcessConfig
     * @param processConfig
     * @return List<ProcessConfig>    返回结果集
     */
	public List<ProcessConfig> findProcessConfig(@Param("processConfig")ProcessConfig processConfig, @Param("order")String order);



    /**
     * 分页查询 ProcessConfig
     * @param page 
     * @param processConfig
     * @return List<ProcessConfig>    返回分页查询结果集
     */
	public List<ProcessConfig> findByPage(Page page, @Param("processConfig")ProcessConfig processConfig);



}