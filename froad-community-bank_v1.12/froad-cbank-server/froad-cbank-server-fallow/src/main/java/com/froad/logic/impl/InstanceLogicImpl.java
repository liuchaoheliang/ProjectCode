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
 * @Title: InstanceLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.InstanceHandler;
import com.froad.handler.impl.InstanceHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.InstanceLogic;
import com.froad.po.mysql.Instance;

/**
 * 
 * <p>@Title: InstanceLogic.java</p>
 * <p>Description: 实现对Instance所有业务逻辑处理 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class InstanceLogicImpl implements InstanceLogic {
	private InstanceHandler instanceHandler = new InstanceHandlerImpl();

    /**
     * 增加 Instance
     * @param instance
     * @return Long    主键ID
     */
	@Override
	public Long addInstance(Instance instance) {

		Long result = null; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = instanceHandler.addInstance(instance);

			if (null != result) { // 添加成功
				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 添加失败

			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("添加Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 删除 Instance
     * @param instance
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteInstance(Instance instance) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = instanceHandler.deleteInstance(instance) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("删除Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id删除 Instance
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteInstanceById(Long id) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = instanceHandler.deleteInstanceById(id) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id删除Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据instanceId删除 Instance
     * @param instanceId
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteInstanceByInstanceId(String instanceId) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = instanceHandler.deleteInstanceByInstanceId(instanceId) > 0;

			if (result) { // 删除成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 删除失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据instanceId删除Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 修改 Instance
     * @param instance
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateInstance(Instance instance) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = instanceHandler.updateInstance(instance) > 0;
			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("修改Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id修改 Instance
     * @param instance
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateInstanceById(Instance instance) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = instanceHandler.updateInstanceById(instance) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据id修改Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据instanceId修改 Instance
     * @param instance
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateInstanceByInstanceId(Instance instance) {

		Boolean result = false; 
		try { 
			/**********************操作MySQL数据库**********************/
			result = instanceHandler.updateInstanceByInstanceId(instance) > 0;

			if (result) { // 修改成功

				/**********************操作Mongodb数据库**********************/

				/**********************操作Redis缓存**********************/
			} else { // 修改失败
			}
		} catch (Exception e) { 
			result = false; 
			LogCvt.error("根据instanceId修改Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据id查询单个 Instance
     * @param id
     * @return Instance    单个 Instance
     */
	@Override
	public Instance findInstanceById(Long id) {

		Instance result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = instanceHandler.findInstanceById(id);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据id查询Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 根据instanceId查询单个 Instance
     * @param instanceId
     * @return Instance    单个 Instance
     */
	@Override
	public Instance findInstanceByInstanceId(String instanceId) {

		Instance result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			result = instanceHandler.findInstanceByInstanceId(instanceId);
			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据instanceId查询Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询一个 Instance
     * @param instance
     * @return Instance    单个 Instance
     */
	@Override
	public Instance findOneInstance(Instance instance) {

		Instance result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = instanceHandler.findOneInstance(instance);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询一个Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 统计 Instance
     * @param instance
     * @return Integer    返回记录数 Instance
     */
	@Override
	public Integer countInstance(Instance instance) {

		Integer result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = instanceHandler.countInstance(instance);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件统计Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 查询 Instance
     * @param instance
     * @return List<Instance>    Instance集合 
     */
	@Override
	public List<Instance> findInstance(Instance instance) {

		List<Instance> result = null; 
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			result = instanceHandler.findInstance(instance);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据条件查询Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return result; 

	}


    /**
     * 分页查询 Instance
     * @param page
     * @param instance
     * @return Page<Instance>    Instance分页结果 
     */
	@Override
	public Page<Instance> findInstanceByPage(Page<Instance> page, Instance instance) {

		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			page = instanceHandler.findInstanceByPage(page, instance);
		} catch (Exception e) { 
			LogCvt.error("分页查询Instance失败，原因:" + e.getMessage(), e); 
		} finally { 
		} 
		return page; 

	}


}