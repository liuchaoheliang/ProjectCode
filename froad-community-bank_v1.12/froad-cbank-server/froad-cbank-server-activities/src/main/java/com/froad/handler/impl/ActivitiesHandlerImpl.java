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
 * @Title: ActivitiesHandlerImpl.java
 * @Package com.froad.handler.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ActivitiesMapper;
import com.froad.handler.ActivitiesHandler;
import com.froad.po.Activities;

/**
 * 
 * <p>@Title: ActivitiesHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体Activities的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActivitiesHandlerImpl implements ActivitiesHandler {


    /**
     * 增加 Activities
     * @param activities
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActivities(Activities activities)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addActivities(sqlSession, activities);

			if (null != result) { // 添加成功

				sqlSession.commit(true); 

			} else { // 添加失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = null; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			 throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 增加 Activities
     * @param sqlSession
     * @param activities
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActivities(SqlSession sqlSession, Activities activities) throws Exception {

		Long result = null; 
		ActivitiesMapper activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);
		if (activitiesMapper.addActivities(activities) > -1) { // 添加成功
			result = activities.getId(); 
		}
		return result; 

	}


    /**
     * 删除 Activities
     * @param activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActivities(Activities activities) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActivities(sqlSession, activities); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			 throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 Activities
     * @param sqlSession
     * @param activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActivities(SqlSession sqlSession, Activities activities) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActivitiesMapper.class).deleteActivities(activities); 
	}


    /**
     * 根据id删除 Activities
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActivitiesById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActivitiesById(sqlSession, id); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据id删除 Activities
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActivitiesById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActivitiesMapper.class).deleteActivitiesById(id); 
	}


    /**
     * 修改 Activities
     * @param activities
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActivities(Activities activities) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActivities(sqlSession, activities); 
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 Activities
     * @param sqlSession
     * @param activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActivities(SqlSession sqlSession, Activities activities) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActivitiesMapper.class).updateActivities(activities); 
	}


    /**
     * 根据id修改 Activities
     * @param Activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActivitiesById(Activities activities) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActivitiesById(sqlSession, activities); 
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据id修改 Activities
     * @param sqlSession
     * @param activities
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActivitiesById(SqlSession sqlSession, Activities activities) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActivitiesMapper.class).updateActivitiesById(activities); 
	}


    /**
     * 根据id查询单个 Activities
     * @param id
     * @return Activities    单个Activities
     * @throws Exception
     */
	@Override
	public Activities findActivitiesById(Long id) throws Exception {

		Activities result = null; 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);

			result = activitiesMapper.findActivitiesById(id); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询一个 Activities
     * @param activities
     * @return Activities    单个Activities
     * @throws Exception
     */
	@Override
	public Activities findOneActivities(Activities activities) throws Exception {

		Activities result = null; 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);

			result = activitiesMapper.findOneActivities(activities); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询统计 Activities
     * @param activities
     * @return Integer    返回记录数Activities
     * @throws Exception
     */
	@Override
	public Integer countActivities(Activities activities) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);

			result = activitiesMapper.countActivities(activities); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 Activities
     * @param activities
     * @return List<Activities>    Activities集合 
     * @throws Exception
     */
	@Override
	public List<Activities> findActivities(Activities activities) throws Exception {

		return this.findActivities(activities, null); 

	}


    /**
     * 查询 Activities
     * @param activities
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<Activities>    Activities集合 
     * @throws Exception
     */
	@Override
	public List<Activities> findActivities(Activities activities, String order) throws Exception {

		List<Activities> result = null; 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);

			result = activitiesMapper.findActivities(activities, order); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Activities
     * @param page
     * @param activities
     * @return Page<Activities>    Activities分页结果 
     * @throws Exception 
     */
	@Override
	public Page<Activities> findActivitiesByPage(Page<Activities> page, Activities activities) throws Exception {

		List<Activities> result = new ArrayList<Activities>(); 
		SqlSession sqlSession = null;
		ActivitiesMapper activitiesMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activitiesMapper = sqlSession.getMapper(ActivitiesMapper.class);

			result = activitiesMapper.findByPage(page, activities); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// if(null != sqlSession)  
				// sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}