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
 * @Title: HistoryInstanceLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.List;

import net.sf.oval.exception.ConstraintsViolatedException;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.handler.HistoryInstanceHandler;
import com.froad.handler.impl.HistoryInstanceHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.HistoryInstanceLogic;
import com.froad.po.mysql.HistoryInstance;
import com.froad.util.ValidatorUtil;

/**
 * 
 * <p>@Title: HistoryInstanceLogic.java</p>
 * <p>Description: 实现对HistoryInstance所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class HistoryInstanceLogicImpl implements HistoryInstanceLogic {
	private HistoryInstanceHandler historyInstanceHandler = new HistoryInstanceHandlerImpl();

    /**
     * 增加 HistoryInstance
     * @param historyInstance
     * @return Long    主键ID
     */
	@Override
	public Long addHistoryInstance(HistoryInstance historyInstance) {

		Long result = null; 
		try { 
			try {

				// Verify.verifyThrowException(merchant); // 校验bean
				ValidatorUtil.getValidator().assertValid(historyInstance); // 校验bean
			} catch (ConstraintsViolatedException e) {
				throw new FroadServerException(e.getMessage());
			}
			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.addHistoryInstance(historyInstance);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 HistoryInstance
     * @param historyInstance
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteHistoryInstance(HistoryInstance historyInstance) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.deleteHistoryInstance(historyInstance) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 HistoryInstance
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteHistoryInstanceById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.deleteHistoryInstanceById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 HistoryInstance
     * @param historyInstance
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateHistoryInstance(HistoryInstance historyInstance) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.updateHistoryInstance(historyInstance) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 HistoryInstance
     * @param historyInstance
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateHistoryInstanceById(HistoryInstance historyInstance) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.updateHistoryInstanceById(historyInstance) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 HistoryInstance
     * @param id
     * @return HistoryInstance    单个 HistoryInstance
     */
	@Override
	public HistoryInstance findHistoryInstanceById(Long id) {

		HistoryInstance result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.findHistoryInstanceById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 HistoryInstance
     * @param historyInstance
     * @return HistoryInstance    单个 HistoryInstance
     */
	@Override
	public HistoryInstance findOneHistoryInstance(HistoryInstance historyInstance) {

		HistoryInstance result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.findOneHistoryInstance(historyInstance);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 HistoryInstance
     * @param historyInstance
     * @return Integer    返回记录数 HistoryInstance
     */
	@Override
	public Integer countHistoryInstance(HistoryInstance historyInstance) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.countHistoryInstance(historyInstance);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 HistoryInstance
     * @param historyInstance
     * @return List<HistoryInstance>    HistoryInstance集合 
     */
	@Override
	public List<HistoryInstance> findHistoryInstance(HistoryInstance historyInstance) {

		List<HistoryInstance> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = historyInstanceHandler.findHistoryInstance(historyInstance);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 HistoryInstance
     * @param page
     * @param historyInstance
     * @return Page<HistoryInstance>    HistoryInstance分页结果 
     */
	@Override
	public Page<HistoryInstance> findHistoryInstanceByPage(Page<HistoryInstance> page, HistoryInstance historyInstance) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = historyInstanceHandler.findHistoryInstanceByPage(page, historyInstance);
		} catch (Exception e) { 
			LogCvt.error("分页查询HistoryInstance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}