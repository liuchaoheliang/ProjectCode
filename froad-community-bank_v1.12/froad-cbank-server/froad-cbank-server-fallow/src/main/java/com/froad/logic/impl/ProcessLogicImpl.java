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
 * @Title: ProcessLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.ProcessHandler;
import com.froad.handler.impl.ProcessHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ProcessLogic;
import com.froad.po.mysql.Process;

/**
 * 
 * <p>@Title: ProcessLogic.java</p>
 * <p>Description: 实现对Process所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProcessLogicImpl implements ProcessLogic {
	private ProcessHandler processHandler = new ProcessHandlerImpl();

    /**
     * 增加 Process
     * @param process
     * @return Long    主键ID
     */
	@Override
	public Long addProcess(Process process) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processHandler.addProcess(process);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 Process
     * @param process
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteProcess(Process process) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processHandler.deleteProcess(process) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 Process
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteProcessById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processHandler.deleteProcessById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据processId删除 Process
     * @param processId
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteProcessByProcessId(String processId) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processHandler.deleteProcessByProcessId(processId) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据processId删除Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 Process
     * @param process
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateProcess(Process process) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processHandler.updateProcess(process) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 Process
     * @param process
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateProcessById(Process process) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processHandler.updateProcessById(process) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据processId修改 Process
     * @param process
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateProcessByProcessId(Process process) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processHandler.updateProcessByProcessId(process) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据processId修改Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 Process
     * @param id
     * @return Process    单个 Process
     */
	@Override
	public Process findProcessById(Long id) {

		Process result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processHandler.findProcessById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据processId查询单个 Process
     * @param processId
     * @return Process    单个 Process
     */
	@Override
	public Process findProcessByProcessId(String processId) {

		Process result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			result = processHandler.findProcessByProcessId(processId);
			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据processId查询Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 Process
     * @param process
     * @return Process    单个 Process
     */
	@Override
	public Process findOneProcess(Process process) {

		Process result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processHandler.findOneProcess(process);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 Process
     * @param process
     * @return Integer    返回记录数 Process
     */
	@Override
	public Integer countProcess(Process process) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processHandler.countProcess(process);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 Process
     * @param process
     * @return List<Process>    Process集合 
     */
	@Override
	public List<Process> findProcess(Process process) {

		List<Process> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processHandler.findProcess(process);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 Process
     * @param page
     * @param process
     * @return Page<Process>    Process分页结果 
     */
	@Override
	public Page<Process> findProcessByPage(Page<Process> page, Process process) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = processHandler.findProcessByPage(page, process);
		} catch (Exception e) { 
			LogCvt.error("分页查询Process失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}