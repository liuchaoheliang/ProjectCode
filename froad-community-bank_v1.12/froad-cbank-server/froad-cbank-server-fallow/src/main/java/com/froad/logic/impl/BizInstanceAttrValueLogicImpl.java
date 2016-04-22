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
 * @Title: BizInstanceAttrValueLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.BizInstanceAttrValueHandler;
import com.froad.handler.impl.BizInstanceAttrValueHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.BizInstanceAttrValueLogic;
import com.froad.po.mysql.BizInstanceAttrValue;

/**
 * 
 * <p>@Title: BizInstanceAttrValueLogic.java</p>
 * <p>Description: 实现对BizInstanceAttrValue所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BizInstanceAttrValueLogicImpl implements BizInstanceAttrValueLogic {
	private BizInstanceAttrValueHandler bizInstanceAttrValueHandler = new BizInstanceAttrValueHandlerImpl();

    /**
     * 增加 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return String    主键ID
     */
	@Override
	public String addBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) {

		String result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.addBizInstanceAttrValue(bizInstanceAttrValue);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.deleteBizInstanceAttrValue(bizInstanceAttrValue) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据instanceId删除 BizInstanceAttrValue
     * @param instanceId
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBizInstanceAttrValueByInstanceId(String instanceId) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.deleteBizInstanceAttrValueByInstanceId(instanceId) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据instanceId删除BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.updateBizInstanceAttrValue(bizInstanceAttrValue) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据instanceId修改 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBizInstanceAttrValueByInstanceId(BizInstanceAttrValue bizInstanceAttrValue) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.updateBizInstanceAttrValueByInstanceId(bizInstanceAttrValue) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据instanceId修改BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据instanceId查询单个 BizInstanceAttrValue
     * @param instanceId
     * @return BizInstanceAttrValue    单个 BizInstanceAttrValue
     */
	@Override
	public BizInstanceAttrValue findBizInstanceAttrValueByInstanceId(String instanceId) {

		BizInstanceAttrValue result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.findBizInstanceAttrValueByInstanceId(instanceId);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return BizInstanceAttrValue    单个 BizInstanceAttrValue
     */
	@Override
	public BizInstanceAttrValue findOneBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) {

		BizInstanceAttrValue result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.findOneBizInstanceAttrValue(bizInstanceAttrValue);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    返回记录数 BizInstanceAttrValue
     */
	@Override
	public Integer countBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.countBizInstanceAttrValue(bizInstanceAttrValue);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return List<BizInstanceAttrValue>    BizInstanceAttrValue集合 
     */
	@Override
	public List<BizInstanceAttrValue> findBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) {

		List<BizInstanceAttrValue> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrValueHandler.findBizInstanceAttrValue(bizInstanceAttrValue);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 BizInstanceAttrValue
     * @param page
     * @param bizInstanceAttrValue
     * @return Page<BizInstanceAttrValue>    BizInstanceAttrValue分页结果 
     */
	@Override
	public Page<BizInstanceAttrValue> findBizInstanceAttrValueByPage(Page<BizInstanceAttrValue> page, BizInstanceAttrValue bizInstanceAttrValue) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = bizInstanceAttrValueHandler.findBizInstanceAttrValueByPage(page, bizInstanceAttrValue);
		} catch (Exception e) { 
			LogCvt.error("分页查询BizInstanceAttrValue失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}