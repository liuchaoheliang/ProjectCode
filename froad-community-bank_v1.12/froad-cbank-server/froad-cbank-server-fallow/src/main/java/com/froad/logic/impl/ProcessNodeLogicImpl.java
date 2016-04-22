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
 * @Title: ProcessNodeLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.ProcessNodeHandler;
import com.froad.handler.impl.ProcessNodeHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ProcessNodeLogic;
import com.froad.po.mysql.ProcessNode;

/**
 * 
 * <p>@Title: ProcessNodeLogic.java</p>
 * <p>Description: 实现对ProcessNode所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProcessNodeLogicImpl implements ProcessNodeLogic {
	private ProcessNodeHandler processNodeHandler = new ProcessNodeHandlerImpl();

    /**
     * 增加 ProcessNode
     * @param processNode
     * @return Long    主键ID
     */
	@Override
	public Long addProcessNode(ProcessNode processNode) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.addProcessNode(processNode);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 ProcessNode
     * @param processNode
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteProcessNode(ProcessNode processNode) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.deleteProcessNode(processNode) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 ProcessNode
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteProcessNodeById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.deleteProcessNodeById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 ProcessNode
     * @param processNode
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateProcessNode(ProcessNode processNode) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.updateProcessNode(processNode) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 ProcessNode
     * @param processNode
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateProcessNodeById(ProcessNode processNode) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.updateProcessNodeById(processNode) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 ProcessNode
     * @param id
     * @return ProcessNode    单个 ProcessNode
     */
	@Override
	public ProcessNode findProcessNodeById(Long id) {

		ProcessNode result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.findProcessNodeById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 ProcessNode
     * @param processNode
     * @return ProcessNode    单个 ProcessNode
     */
	@Override
	public ProcessNode findOneProcessNode(ProcessNode processNode) {

		ProcessNode result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.findOneProcessNode(processNode);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 ProcessNode
     * @param processNode
     * @return Integer    返回记录数 ProcessNode
     */
	@Override
	public Integer countProcessNode(ProcessNode processNode) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.countProcessNode(processNode);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 ProcessNode
     * @param processNode
     * @return List<ProcessNode>    ProcessNode集合 
     */
	@Override
	public List<ProcessNode> findProcessNode(ProcessNode processNode) {

		List<ProcessNode> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processNodeHandler.findProcessNode(processNode);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 ProcessNode
     * @param page
     * @param processNode
     * @return Page<ProcessNode>    ProcessNode分页结果 
     */
	@Override
	public Page<ProcessNode> findProcessNodeByPage(Page<ProcessNode> page, ProcessNode processNode) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = processNodeHandler.findProcessNodeByPage(page, processNode);
		} catch (Exception e) { 
			LogCvt.error("分页查询ProcessNode失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}