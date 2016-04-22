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
 * @Title: RegistDetailRuleLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.RegistDetailRuleHandler;
import com.froad.handler.impl.RegistDetailRuleHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.RegistDetailRuleLogic;
import com.froad.po.RegistDetailRule;

/**
 * 
 * <p>@Title: RegistDetailRuleLogic.java</p>
 * <p>Description: 实现对RegistDetailRule所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class RegistDetailRuleLogicImpl implements RegistDetailRuleLogic {
	private RegistDetailRuleHandler registDetailRuleHandler = new RegistDetailRuleHandlerImpl();

    /**
     * 增加 RegistDetailRule
     * @param registDetailRule
     * @return Long    主键ID
     */
	@Override
	public Long addRegistDetailRule(RegistDetailRule registDetailRule) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.addRegistDetailRule(registDetailRule);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 RegistDetailRule
     * @param registDetailRule
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteRegistDetailRule(RegistDetailRule registDetailRule) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.deleteRegistDetailRule(registDetailRule) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 RegistDetailRule
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteRegistDetailRuleById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.deleteRegistDetailRuleById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 RegistDetailRule
     * @param registDetailRule
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateRegistDetailRule(RegistDetailRule registDetailRule) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.updateRegistDetailRule(registDetailRule) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 RegistDetailRule
     * @param registDetailRule
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateRegistDetailRuleById(RegistDetailRule registDetailRule) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.updateRegistDetailRuleById(registDetailRule) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 RegistDetailRule
     * @param id
     * @return RegistDetailRule    单个 RegistDetailRule
     */
	@Override
	public RegistDetailRule findRegistDetailRuleById(Long id) {

		RegistDetailRule result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.findRegistDetailRuleById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 RegistDetailRule
     * @param registDetailRule
     * @return RegistDetailRule    单个 RegistDetailRule
     */
	@Override
	public RegistDetailRule findOneRegistDetailRule(RegistDetailRule registDetailRule) {

		RegistDetailRule result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.findOneRegistDetailRule(registDetailRule);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 RegistDetailRule
     * @param registDetailRule
     * @return Integer    返回记录数 RegistDetailRule
     */
	@Override
	public Integer countRegistDetailRule(RegistDetailRule registDetailRule) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.countRegistDetailRule(registDetailRule);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 RegistDetailRule
     * @param registDetailRule
     * @return List<RegistDetailRule>    RegistDetailRule集合 
     */
	@Override
	public List<RegistDetailRule> findRegistDetailRule(RegistDetailRule registDetailRule) {

		List<RegistDetailRule> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = registDetailRuleHandler.findRegistDetailRule(registDetailRule);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 RegistDetailRule
     * @param page
     * @param registDetailRule
     * @return Page<RegistDetailRule>    RegistDetailRule分页结果 
     */
	@Override
	public Page<RegistDetailRule> findRegistDetailRuleByPage(Page<RegistDetailRule> page, RegistDetailRule registDetailRule) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = registDetailRuleHandler.findRegistDetailRuleByPage(page, registDetailRule);
		} catch (Exception e) { 
			LogCvt.error("分页查询RegistDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}