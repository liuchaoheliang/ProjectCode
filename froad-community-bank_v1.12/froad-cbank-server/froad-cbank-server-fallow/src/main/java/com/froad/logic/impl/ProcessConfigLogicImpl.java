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
 * @Title: ProcessConfigLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.ProcessConfigHandler;
import com.froad.handler.impl.ProcessConfigHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ProcessConfigLogic;
import com.froad.po.mysql.ProcessConfig;

/**
 * 
 * <p>@Title: ProcessConfigLogic.java</p>
 * <p>Description: 实现对ProcessConfig所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProcessConfigLogicImpl implements ProcessConfigLogic {
	private ProcessConfigHandler processConfigHandler = new ProcessConfigHandlerImpl();

    /**
     * 增加 ProcessConfig
     * @param processConfig
     * @return Long    主键ID
     */
	@Override
	public Long addProcessConfig(ProcessConfig processConfig) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.addProcessConfig(processConfig);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 ProcessConfig
     * @param processConfig
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteProcessConfig(ProcessConfig processConfig) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.deleteProcessConfig(processConfig) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 ProcessConfig
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteProcessConfigById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.deleteProcessConfigById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 ProcessConfig
     * @param processConfig
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateProcessConfig(ProcessConfig processConfig) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.updateProcessConfig(processConfig) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 ProcessConfig
     * @param processConfig
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateProcessConfigById(ProcessConfig processConfig) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.updateProcessConfigById(processConfig) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 ProcessConfig
     * @param id
     * @return ProcessConfig    单个 ProcessConfig
     */
	@Override
	public ProcessConfig findProcessConfigById(Long id) {

		ProcessConfig result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.findProcessConfigById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 ProcessConfig
     * @param processConfig
     * @return ProcessConfig    单个 ProcessConfig
     */
	@Override
	public ProcessConfig findOneProcessConfig(ProcessConfig processConfig) {

		ProcessConfig result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.findOneProcessConfig(processConfig);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 ProcessConfig
     * @param processConfig
     * @return Integer    返回记录数 ProcessConfig
     */
	@Override
	public Integer countProcessConfig(ProcessConfig processConfig) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.countProcessConfig(processConfig);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 ProcessConfig
     * @param processConfig
     * @return List<ProcessConfig>    ProcessConfig集合 
     */
	@Override
	public List<ProcessConfig> findProcessConfig(ProcessConfig processConfig) {

		List<ProcessConfig> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = processConfigHandler.findProcessConfig(processConfig);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 ProcessConfig
     * @param page
     * @param processConfig
     * @return Page<ProcessConfig>    ProcessConfig分页结果 
     */
	@Override
	public Page<ProcessConfig> findProcessConfigByPage(Page<ProcessConfig> page, ProcessConfig processConfig) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = processConfigHandler.findProcessConfigByPage(page, processConfig);
		} catch (Exception e) { 
			LogCvt.error("分页查询ProcessConfig失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}