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
 * @Title: ProcessNodeLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.mysql.ProcessNode;

/**
 * 
 * <p>@Title: ProcessNodeLogic.java</p>
 * <p>Description: 封装对ProcessNode所有业务逻辑处理的接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProcessNodeLogic {


    /**
     * 增加 ProcessNode
     * @param processNode
     * @return Long    主键ID
     */
	public Long addProcessNode(ProcessNode processNode) ;



    /**
     * 删除 ProcessNode
     * @param processNode
     * @return Boolean    是否成功
     */
	public Boolean deleteProcessNode(ProcessNode processNode) ;



    /**
     * 根据id删除 ProcessNode
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteProcessNodeById(Long id) ;



    /**
     * 修改 ProcessNode
     * @param processNode
     * @return Boolean    是否成功
     */
	public Boolean updateProcessNode(ProcessNode processNode) ;



    /**
     * 根据id修改 ProcessNode
     * @param processNode
     * @return Boolean    是否成功
     */
	public Boolean updateProcessNodeById(ProcessNode processNode) ;



    /**
     * 根据id查询单个 ProcessNode
     * @param id
     * @return ProcessNode    单个 ProcessNode
     */
	public ProcessNode findProcessNodeById(Long id) ;



    /**
     * 查询一个 ProcessNode
     * @param processNode
     * @return ProcessNode    单个 ProcessNode
     */
	public ProcessNode findOneProcessNode(ProcessNode processNode) ;



    /**
     * 统计 ProcessNode
     * @param processNode
     * @return Integer    返回记录数 ProcessNode
     */
	public Integer countProcessNode(ProcessNode processNode) ;



    /**
     * 查询 ProcessNode
     * @param processNode
     * @return List<ProcessNode>    ProcessNode集合 
     */
	public List<ProcessNode> findProcessNode(ProcessNode processNode) ;



    /**
     * 分页查询 ProcessNode
     * @param page
     * @param processNode
     * @return Page<ProcessNode>    ProcessNode分页结果 
     */
	public Page<ProcessNode> findProcessNodeByPage(Page<ProcessNode> page, ProcessNode processNode) ;



}