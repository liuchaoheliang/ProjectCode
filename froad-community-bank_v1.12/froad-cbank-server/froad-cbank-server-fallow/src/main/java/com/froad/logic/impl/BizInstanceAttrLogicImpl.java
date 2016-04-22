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
 * @Title: BizInstanceAttrLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.BizInstanceAttrHandler;
import com.froad.handler.impl.BizInstanceAttrHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.BizInstanceAttrLogic;
import com.froad.po.mysql.BizInstanceAttr;

/**
 * 
 * <p>@Title: BizInstanceAttrLogic.java</p>
 * <p>Description: 实现对BizInstanceAttr所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BizInstanceAttrLogicImpl implements BizInstanceAttrLogic {
	private BizInstanceAttrHandler bizInstanceAttrHandler = new BizInstanceAttrHandlerImpl();

    /**
     * 增加 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Long    主键ID
     */
	@Override
	public Long addBizInstanceAttr(BizInstanceAttr bizInstanceAttr) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.addBizInstanceAttr(bizInstanceAttr);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBizInstanceAttr(BizInstanceAttr bizInstanceAttr) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.deleteBizInstanceAttr(bizInstanceAttr) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 BizInstanceAttr
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBizInstanceAttrById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.deleteBizInstanceAttrById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBizInstanceAttr(BizInstanceAttr bizInstanceAttr) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.updateBizInstanceAttr(bizInstanceAttr) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBizInstanceAttrById(BizInstanceAttr bizInstanceAttr) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.updateBizInstanceAttrById(bizInstanceAttr) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 BizInstanceAttr
     * @param id
     * @return BizInstanceAttr    单个 BizInstanceAttr
     */
	@Override
	public BizInstanceAttr findBizInstanceAttrById(Long id) {

		BizInstanceAttr result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.findBizInstanceAttrById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 BizInstanceAttr
     * @param bizInstanceAttr
     * @return BizInstanceAttr    单个 BizInstanceAttr
     */
	@Override
	public BizInstanceAttr findOneBizInstanceAttr(BizInstanceAttr bizInstanceAttr) {

		BizInstanceAttr result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.findOneBizInstanceAttr(bizInstanceAttr);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    返回记录数 BizInstanceAttr
     */
	@Override
	public Integer countBizInstanceAttr(BizInstanceAttr bizInstanceAttr) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.countBizInstanceAttr(bizInstanceAttr);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 BizInstanceAttr
     * @param bizInstanceAttr
     * @return List<BizInstanceAttr>    BizInstanceAttr集合 
     */
	@Override
	public List<BizInstanceAttr> findBizInstanceAttr(BizInstanceAttr bizInstanceAttr) {

		List<BizInstanceAttr> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = bizInstanceAttrHandler.findBizInstanceAttr(bizInstanceAttr);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 BizInstanceAttr
     * @param page
     * @param bizInstanceAttr
     * @return Page<BizInstanceAttr>    BizInstanceAttr分页结果 
     */
	@Override
	public Page<BizInstanceAttr> findBizInstanceAttrByPage(Page<BizInstanceAttr> page, BizInstanceAttr bizInstanceAttr) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = bizInstanceAttrHandler.findBizInstanceAttrByPage(page, bizInstanceAttr);
		} catch (Exception e) { 
			LogCvt.error("分页查询BizInstanceAttr失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}