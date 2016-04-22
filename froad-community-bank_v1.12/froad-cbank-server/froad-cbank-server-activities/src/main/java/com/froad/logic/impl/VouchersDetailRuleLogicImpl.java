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
 * @Title: VouchersDetailRuleLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.VouchersDetailRuleHandler;
import com.froad.handler.impl.VouchersDetailRuleHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.VouchersDetailRuleLogic;
import com.froad.po.VouchersDetailRule;

/**
 * 
 * <p>@Title: VouchersDetailRuleLogic.java</p>
 * <p>Description: 实现对VouchersDetailRule所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class VouchersDetailRuleLogicImpl implements VouchersDetailRuleLogic {
	private VouchersDetailRuleHandler vouchersDetailRuleHandler = new VouchersDetailRuleHandlerImpl();

    /**
     * 增加 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Long    主键ID
     */
	@Override
	public Long addVouchersDetailRule(VouchersDetailRule vouchersDetailRule) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.addVouchersDetailRule(vouchersDetailRule);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteVouchersDetailRule(VouchersDetailRule vouchersDetailRule) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.deleteVouchersDetailRule(vouchersDetailRule) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 VouchersDetailRule
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteVouchersDetailRuleById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.deleteVouchersDetailRuleById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateVouchersDetailRule(VouchersDetailRule vouchersDetailRule) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.updateVouchersDetailRule(vouchersDetailRule) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateVouchersDetailRuleById(VouchersDetailRule vouchersDetailRule) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.updateVouchersDetailRuleById(vouchersDetailRule) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 VouchersDetailRule
     * @param id
     * @return VouchersDetailRule    单个 VouchersDetailRule
     */
	@Override
	public VouchersDetailRule findVouchersDetailRuleById(Long id) {

		VouchersDetailRule result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.findVouchersDetailRuleById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 VouchersDetailRule
     * @param vouchersDetailRule
     * @return VouchersDetailRule    单个 VouchersDetailRule
     */
	@Override
	public VouchersDetailRule findOneVouchersDetailRule(VouchersDetailRule vouchersDetailRule) {

		VouchersDetailRule result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.findOneVouchersDetailRule(vouchersDetailRule);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    返回记录数 VouchersDetailRule
     */
	@Override
	public Integer countVouchersDetailRule(VouchersDetailRule vouchersDetailRule) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.countVouchersDetailRule(vouchersDetailRule);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 VouchersDetailRule
     * @param vouchersDetailRule
     * @return List<VouchersDetailRule>    VouchersDetailRule集合 
     */
	@Override
	public List<VouchersDetailRule> findVouchersDetailRule(VouchersDetailRule vouchersDetailRule) {

		List<VouchersDetailRule> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersDetailRuleHandler.findVouchersDetailRule(vouchersDetailRule);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 VouchersDetailRule
     * @param page
     * @param vouchersDetailRule
     * @return Page<VouchersDetailRule>    VouchersDetailRule分页结果 
     */
	@Override
	public Page<VouchersDetailRule> findVouchersDetailRuleByPage(Page<VouchersDetailRule> page, VouchersDetailRule vouchersDetailRule) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = vouchersDetailRuleHandler.findVouchersDetailRuleByPage(page, vouchersDetailRule);
		} catch (Exception e) { 
			LogCvt.error("分页查询VouchersDetailRule失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}