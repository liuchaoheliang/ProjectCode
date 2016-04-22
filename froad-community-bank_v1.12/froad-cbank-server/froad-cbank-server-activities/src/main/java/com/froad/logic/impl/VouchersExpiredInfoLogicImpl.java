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
 * @Title: VouchersExpiredInfoLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.VouchersExpiredInfoHandler;
import com.froad.handler.impl.VouchersExpiredInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.VouchersExpiredInfoLogic;
import com.froad.po.VouchersExpiredInfo;

/**
 * 
 * <p>@Title: VouchersExpiredInfoLogic.java</p>
 * <p>Description: 实现对VouchersExpiredInfo所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class VouchersExpiredInfoLogicImpl implements VouchersExpiredInfoLogic {
	private VouchersExpiredInfoHandler vouchersExpiredInfoHandler = new VouchersExpiredInfoHandlerImpl();

    /**
     * 增加 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Long    主键ID
     */
	@Override
	public Long addVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.addVouchersExpiredInfo(vouchersExpiredInfo);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.deleteVouchersExpiredInfo(vouchersExpiredInfo) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 VouchersExpiredInfo
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteVouchersExpiredInfoById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.deleteVouchersExpiredInfoById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.updateVouchersExpiredInfo(vouchersExpiredInfo) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateVouchersExpiredInfoById(VouchersExpiredInfo vouchersExpiredInfo) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.updateVouchersExpiredInfoById(vouchersExpiredInfo) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 VouchersExpiredInfo
     * @param id
     * @return VouchersExpiredInfo    单个 VouchersExpiredInfo
     */
	@Override
	public VouchersExpiredInfo findVouchersExpiredInfoById(Long id) {

		VouchersExpiredInfo result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.findVouchersExpiredInfoById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return VouchersExpiredInfo    单个 VouchersExpiredInfo
     */
	@Override
	public VouchersExpiredInfo findOneVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) {

		VouchersExpiredInfo result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.findOneVouchersExpiredInfo(vouchersExpiredInfo);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    返回记录数 VouchersExpiredInfo
     */
	@Override
	public Integer countVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.countVouchersExpiredInfo(vouchersExpiredInfo);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return List<VouchersExpiredInfo>    VouchersExpiredInfo集合 
     */
	@Override
	public List<VouchersExpiredInfo> findVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) {

		List<VouchersExpiredInfo> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = vouchersExpiredInfoHandler.findVouchersExpiredInfo(vouchersExpiredInfo);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 VouchersExpiredInfo
     * @param page
     * @param vouchersExpiredInfo
     * @return Page<VouchersExpiredInfo>    VouchersExpiredInfo分页结果 
     */
	@Override
	public Page<VouchersExpiredInfo> findVouchersExpiredInfoByPage(Page<VouchersExpiredInfo> page, VouchersExpiredInfo vouchersExpiredInfo) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = vouchersExpiredInfoHandler.findVouchersExpiredInfoByPage(page, vouchersExpiredInfo);
		} catch (Exception e) { 
			LogCvt.error("分页查询VouchersExpiredInfo失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}