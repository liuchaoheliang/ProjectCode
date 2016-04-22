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
 * @Title: VouchersUseInfoLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.VouchersUseInfoHandler;
import com.froad.handler.impl.VouchersUseInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.VouchersUseInfoLogic;
import com.froad.po.VouchersUseInfo;

/**
 * 
 * <p>@Title: VouchersUseInfoLogic.java</p>
 * <p>Description: 实现对VouchersUseInfo所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class VouchersUseInfoLogicImpl implements VouchersUseInfoLogic {
	private VouchersUseInfoHandler vouchersUseInfoHandler = new VouchersUseInfoHandlerImpl();

    /**
     * 增加 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Long    主键ID
     */
	@Override
	public Long addVouchersUseInfo(VouchersUseInfo vouchersUseInfo) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.addVouchersUseInfo(vouchersUseInfo);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteVouchersUseInfo(VouchersUseInfo vouchersUseInfo) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.deleteVouchersUseInfo(vouchersUseInfo) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 VouchersUseInfo
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteVouchersUseInfoById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.deleteVouchersUseInfoById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateVouchersUseInfo(VouchersUseInfo vouchersUseInfo) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.updateVouchersUseInfo(vouchersUseInfo) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateVouchersUseInfoById(VouchersUseInfo vouchersUseInfo) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.updateVouchersUseInfoById(vouchersUseInfo) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 VouchersUseInfo
     * @param id
     * @return VouchersUseInfo    单个 VouchersUseInfo
     */
	@Override
	public VouchersUseInfo findVouchersUseInfoById(Long id) {

		VouchersUseInfo result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.findVouchersUseInfoById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 VouchersUseInfo
     * @param vouchersUseInfo
     * @return VouchersUseInfo    单个 VouchersUseInfo
     */
	@Override
	public VouchersUseInfo findOneVouchersUseInfo(VouchersUseInfo vouchersUseInfo) {

		VouchersUseInfo result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.findOneVouchersUseInfo(vouchersUseInfo);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    返回记录数 VouchersUseInfo
     */
	@Override
	public Integer countVouchersUseInfo(VouchersUseInfo vouchersUseInfo) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.countVouchersUseInfo(vouchersUseInfo);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 VouchersUseInfo
     * @param vouchersUseInfo
     * @return List<VouchersUseInfo>    VouchersUseInfo集合 
     */
	@Override
	public List<VouchersUseInfo> findVouchersUseInfo(VouchersUseInfo vouchersUseInfo) {

		List<VouchersUseInfo> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersUseInfoHandler.findVouchersUseInfo(vouchersUseInfo);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 VouchersUseInfo
     * @param page
     * @param vouchersUseInfo
     * @return Page<VouchersUseInfo>    VouchersUseInfo分页结果 
     */
	@Override
	public Page<VouchersUseInfo> findVouchersUseInfoByPage(Page<VouchersUseInfo> page, VouchersUseInfo vouchersUseInfo) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = vouchersUseInfoHandler.findVouchersUseInfoByPage(page, vouchersUseInfo);
		} catch (Exception e) { 
			LogCvt.error("分页查询VouchersUseInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}