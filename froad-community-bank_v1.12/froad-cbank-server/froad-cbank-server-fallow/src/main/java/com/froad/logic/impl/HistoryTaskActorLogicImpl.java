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
 * @Title: HistoryTaskActorLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.HistoryTaskActorHandler;
import com.froad.handler.impl.HistoryTaskActorHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.HistoryTaskActorLogic;
import com.froad.po.mysql.HistoryTaskActor;

/**
 * 
 * <p>@Title: HistoryTaskActorLogic.java</p>
 * <p>Description: 实现对HistoryTaskActor所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryTaskActorLogicImpl implements HistoryTaskActorLogic {
	private HistoryTaskActorHandler historyTaskActorHandler = new HistoryTaskActorHandlerImpl();

    /**
     * 增加 HistoryTaskActor
     * @param historyTaskActor
     * @return String    主键ID
     */
	@Override
	public String addHistoryTaskActor(HistoryTaskActor historyTaskActor) {

		String result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.addHistoryTaskActor(historyTaskActor);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 HistoryTaskActor
     * @param historyTaskActor
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteHistoryTaskActor(HistoryTaskActor historyTaskActor) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.deleteHistoryTaskActor(historyTaskActor) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId删除 HistoryTaskActor
     * @param taskId
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteHistoryTaskActorByTaskId(String taskId) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.deleteHistoryTaskActorByTaskId(taskId) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据taskId删除HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 HistoryTaskActor
     * @param historyTaskActor
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateHistoryTaskActor(HistoryTaskActor historyTaskActor) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.updateHistoryTaskActor(historyTaskActor) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId修改 HistoryTaskActor
     * @param historyTaskActor
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateHistoryTaskActorByTaskId(HistoryTaskActor historyTaskActor) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.updateHistoryTaskActorByTaskId(historyTaskActor) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据taskId修改HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据taskId查询单个 HistoryTaskActor
     * @param taskId
     * @return HistoryTaskActor    单个 HistoryTaskActor
     */
	@Override
	public HistoryTaskActor findHistoryTaskActorByTaskId(String taskId) {

		HistoryTaskActor result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.findHistoryTaskActorByTaskId(taskId);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 HistoryTaskActor
     * @param historyTaskActor
     * @return HistoryTaskActor    单个 HistoryTaskActor
     */
	@Override
	public HistoryTaskActor findOneHistoryTaskActor(HistoryTaskActor historyTaskActor) {

		HistoryTaskActor result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.findOneHistoryTaskActor(historyTaskActor);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 HistoryTaskActor
     * @param historyTaskActor
     * @return Integer    返回记录数 HistoryTaskActor
     */
	@Override
	public Integer countHistoryTaskActor(HistoryTaskActor historyTaskActor) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.countHistoryTaskActor(historyTaskActor);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 HistoryTaskActor
     * @param historyTaskActor
     * @return List<HistoryTaskActor>    HistoryTaskActor集合 
     */
	@Override
	public List<HistoryTaskActor> findHistoryTaskActor(HistoryTaskActor historyTaskActor) {

		List<HistoryTaskActor> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyTaskActorHandler.findHistoryTaskActor(historyTaskActor);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 HistoryTaskActor
     * @param page
     * @param historyTaskActor
     * @return Page<HistoryTaskActor>    HistoryTaskActor分页结果 
     */
	@Override
	public Page<HistoryTaskActor> findHistoryTaskActorByPage(Page<HistoryTaskActor> page, HistoryTaskActor historyTaskActor) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = historyTaskActorHandler.findHistoryTaskActorByPage(page, historyTaskActor);
		} catch (Exception e) { 
			LogCvt.error("分页查询HistoryTaskActor失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}