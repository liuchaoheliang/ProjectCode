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
 * @Title: ProcessNodeMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.ProcessNode;

/**
 * 
 * <p>@Title: ProcessNodeMapper.java</p>
 * <p>Description: 封装mybatis对MySQL映射的实体ProcessNode的CURD操作Mapper </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessNodeMapper {


    /**
     * 增加 ProcessNode
     * @param processNode
     * @return Long    受影响行数
     */
	public Long addProcessNode(ProcessNode processNode);



    /**
     * 批量增加 ProcessNode
     * @param processNodeList
     * @return Boolean    是否成功
     */
	public Boolean addProcessNodeByBatch(@Param("processNodeList")List<ProcessNode> processNodeList);



    /**
     * 删除 ProcessNode
     * @param processNode
     * @return Integer    受影响行数
     */
	public Integer deleteProcessNode(ProcessNode processNode);



    /**
     * 根据id删除一个 ProcessNode
     * @param id
     * @return Integer    受影响行数
     */
	public Integer deleteProcessNodeById(Long id);



    /**
     * 修改 ProcessNode
     * @param processNode
     * @return Integer    受影响行数
     */
	public Integer updateProcessNode(ProcessNode processNode);



    /**
     * 根据id修改一个 ProcessNode
     * @param processNode
     * @return Integer    受影响行数
     */
	public Integer updateProcessNodeById(ProcessNode processNode);



    /**
     * 根据id查询一个 ProcessNode
     * @param id
     * @return ProcessNode    返回结果
     */
	public ProcessNode findProcessNodeById(Long id);



    /**
     * 查询一个 ProcessNode
     * @param processNode
     * @return ProcessNode    返回结果集
     */
	public ProcessNode findOneProcessNode(ProcessNode processNode);



    /**
     * 统计 ProcessNode
     * @param processNode
     * @return Integer    返回记录数
     */
	public Integer countProcessNode(ProcessNode processNode);



    /**
     * 查询 ProcessNode
     * @param processNode
     * @return List<ProcessNode>    返回结果集
     */
	public List<ProcessNode> findProcessNode(@Param("processNode")ProcessNode processNode, @Param("order")String order);



    /**
     * 分页查询 ProcessNode
     * @param page 
     * @param processNode
     * @return List<ProcessNode>    返回分页查询结果集
     */
	public List<ProcessNode> findByPage(Page page, @Param("processNode")ProcessNode processNode);



}