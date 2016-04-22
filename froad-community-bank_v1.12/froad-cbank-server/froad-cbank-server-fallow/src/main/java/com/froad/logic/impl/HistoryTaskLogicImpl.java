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
 * @Title: HistoryTaskLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.HistoryTaskHandler;
import com.froad.handler.impl.HistoryTaskHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.HistoryTaskLogic;
import com.froad.po.mysql.HistoryTask;

/**
 * 
 * <p>@Title: HistoryTaskLogic.java</p>
 * <p>Description: 实现对HistoryTask所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryTaskLogicImpl implements HistoryTaskLogic {
	private HistoryTaskHandler historyTaskHandler = new HistoryTaskHandlerImpl();

    /**
     * 增加 HistoryTask
     * @param historyTask
     * @return Long    主键ID
     */
	@Override
	public Long addHistoryTask(HistoryTask historyTask) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.addHistoryTask(historyTask);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 HistoryTask
     * @param historyTask
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteHistoryTask(HistoryTask historyTask) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.deleteHistoryTask(historyTask) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 HistoryTask
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteHistoryTaskById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.deleteHistoryTaskById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 HistoryTask
     * @param historyTask
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateHistoryTask(HistoryTask historyTask) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.updateHistoryTask(historyTask) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 HistoryTask
     * @param historyTask
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateHistoryTaskById(HistoryTask historyTask) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.updateHistoryTaskById(historyTask) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 HistoryTask
     * @param id
     * @return HistoryTask    单个 HistoryTask
     */
	@Override
	public HistoryTask findHistoryTaskById(Long id) {

		HistoryTask result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.findHistoryTaskById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 HistoryTask
     * @param historyTask
     * @return HistoryTask    单个 HistoryTask
     */
	@Override
	public HistoryTask findOneHistoryTask(HistoryTask historyTask) {

		HistoryTask result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.findOneHistoryTask(historyTask);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 HistoryTask
     * @param historyTask
     * @return Integer    返回记录数 HistoryTask
     */
	@Override
	public Integer countHistoryTask(HistoryTask historyTask) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.countHistoryTask(historyTask);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 HistoryTask
     * @param historyTask
     * @return List<HistoryTask>    HistoryTask集合 
     */
	@Override
	public List<HistoryTask> findHistoryTask(HistoryTask historyTask) {

		List<HistoryTask> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyTaskHandler.findHistoryTask(historyTask);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 HistoryTask
     * @param page
     * @param historyTask
     * @return Page<HistoryTask>    HistoryTask分页结果 
     */
	@Override
	public Page<HistoryTask> findHistoryTaskByPage(Page<HistoryTask> page, HistoryTask historyTask) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = historyTaskHandler.findHistoryTaskByPage(page, historyTask);
		} catch (Exception e) { 
			LogCvt.error("分页查询HistoryTask失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}