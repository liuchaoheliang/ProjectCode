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
 * @Title: ActiveSustainRelationLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.handler.ActiveSustainRelationHandler;
import com.froad.handler.impl.ActiveSustainRelationHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveSustainRelationLogic;
import com.froad.po.ActiveSustainRelation;

/**
 * 
 * <p>
 * @Title: ActiveSustainRelationLogic.java
 * </p>
 * <p>
 * Description: 实现对ActiveSustainRelation所有业务逻辑处理
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActiveSustainRelationLogicImpl implements
		ActiveSustainRelationLogic {
	private ActiveSustainRelationHandler activeSustainRelationHandler = new ActiveSustainRelationHandlerImpl();

	/**
	 * 增加 ActiveSustainRelation
	 * 
	 * @param activeSustainRelation
	 * @return Long 主键ID
	 */
	@Override
	public Long addActiveSustainRelation(
			ActiveSustainRelation activeSustainRelation) {

		Long result = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.addActiveSustainRelation(activeSustainRelation);

			if (null != result) { // 添加成功
				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
			} else { // 添加失败

			}
		} catch (Exception e) {
			result = null;
			LogCvt.error("添加ActiveSustainRelation失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 删除 ActiveSustainRelation
	 * 
	 * @param activeSustainRelation
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean deleteActiveSustainRelation(
			ActiveSustainRelation activeSustainRelation) {

		Boolean result = false;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.deleteActiveSustainRelation(activeSustainRelation) > 0;

			if (result) { // 删除成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
			} else { // 删除失败
			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("删除ActiveSustainRelation失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 根据id删除 ActiveSustainRelation
	 * 
	 * @param id
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean deleteActiveSustainRelationById(Long id) {

		Boolean result = false;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.deleteActiveSustainRelationById(id) > 0;

			if (result) { // 删除成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
			} else { // 删除失败
			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("根据id删除ActiveSustainRelation失败，原因:" + e.getMessage(),
					e);
		} finally {
		}
		return result;

	}

	/**
	 * 修改 ActiveSustainRelation
	 * 
	 * @param activeSustainRelation
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean updateActiveSustainRelation(
			ActiveSustainRelation activeSustainRelation) {

		Boolean result = false;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.updateActiveSustainRelation(activeSustainRelation) > 0;
			if (result) { // 修改成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
				result = true;

			} else { // 修改失败
			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("修改ActiveSustainRelation失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 根据id修改 ActiveSustainRelation
	 * 
	 * @param activeSustainRelation
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean updateActiveSustainRelationById(
			ActiveSustainRelation activeSustainRelation) {

		Boolean result = false;
		try {
			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.updateActiveSustainRelationById(activeSustainRelation) > 0;

			if (result) { // 修改成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
			} else { // 修改失败
			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("根据id修改ActiveSustainRelation失败，原因:" + e.getMessage(),
					e);
		} finally {
		}
		return result;

	}

	/**
	 * 根据id查询单个 ActiveSustainRelation
	 * 
	 * @param id
	 * @return ActiveSustainRelation 单个 ActiveSustainRelation
	 */
	@Override
	public ActiveSustainRelation findActiveSustainRelationById(Long id) {

		ActiveSustainRelation result = null;
		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.findActiveSustainRelationById(id);
		} catch (Exception e) {
			result = null;
			LogCvt.error("根据id查询ActiveSustainRelation失败，原因:" + e.getMessage(),
					e);
		} finally {
		}
		return result;

	}

	/**
	 * 查询一个 ActiveSustainRelation
	 * 
	 * @param activeSustainRelation
	 * @return ActiveSustainRelation 单个 ActiveSustainRelation
	 */
	@Override
	public ActiveSustainRelation findOneActiveSustainRelation(
			ActiveSustainRelation activeSustainRelation) {

		ActiveSustainRelation result = null;
		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.findOneActiveSustainRelation(activeSustainRelation);
		} catch (Exception e) {
			result = null;
			LogCvt.error(
					"根据条件查询一个ActiveSustainRelation失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return result;

	}

	/**
	 * 统计 ActiveSustainRelation
	 * 
	 * @param activeSustainRelation
	 * @return Integer 返回记录数 ActiveSustainRelation
	 */
	@Override
	public Integer countActiveSustainRelation(
			ActiveSustainRelation activeSustainRelation) {

		Integer result = null;
		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.countActiveSustainRelation(activeSustainRelation);
		} catch (Exception e) {
			result = null;
			LogCvt.error("根据条件统计ActiveSustainRelation失败，原因:" + e.getMessage(),
					e);
		} finally {
		}
		return result;

	}

	/**
	 * 查询 ActiveSustainRelation
	 * 
	 * @param activeSustainRelation
	 * @return List<ActiveSustainRelation> ActiveSustainRelation集合
	 */
	@Override
	public List<ActiveSustainRelation> findActiveSustainRelation(
			ActiveSustainRelation activeSustainRelation) {

		List<ActiveSustainRelation> result = null;
		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			result = activeSustainRelationHandler
					.findActiveSustainRelation(activeSustainRelation);
		} catch (Exception e) {
			result = null;
			LogCvt.error("根据条件查询ActiveSustainRelation失败，原因:" + e.getMessage(),
					e);
		} finally {
		}
		return result;

	}

	/**
	 * 分页查询 ActiveSustainRelation
	 * 
	 * @param page
	 * @param activeSustainRelation
	 * @return Page<ActiveSustainRelation> ActiveSustainRelation分页结果
	 */
	@Override
	public Page<ActiveSustainRelation> findActiveSustainRelationByPage(
			Page<ActiveSustainRelation> page,
			ActiveSustainRelation activeSustainRelation) {

		try {
			/********************** 操作Redis缓存 **********************/

			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			List<ActiveSustainRelation> sustainRelationsList = activeSustainRelationHandler
					.findActiveSustainRelationByPage(page,
							activeSustainRelation);
			page.setResultsContent(sustainRelationsList);
		} catch (Exception e) {
			LogCvt.error("分页查询ActiveSustainRelation失败，原因:" + e.getMessage(), e);
		} finally {
		}
		return page;

	}

}