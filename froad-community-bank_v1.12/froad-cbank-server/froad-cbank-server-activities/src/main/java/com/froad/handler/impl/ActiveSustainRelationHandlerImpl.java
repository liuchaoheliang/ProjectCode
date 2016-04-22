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
 * @Title: ActiveSustainRelationHandlerImpl.java
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
import com.froad.db.mysql.mapper.ActiveSustainRelationMapper;
import com.froad.handler.ActiveSustainRelationHandler;
import com.froad.po.ActiveSustainRelation;

/**
 * 
 * <p>@Title: ActiveSustainRelationHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体ActiveSustainRelation的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActiveSustainRelationHandlerImpl implements ActiveSustainRelationHandler {


    /**
     * 增加 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveSustainRelation(ActiveSustainRelation activeSustainRelation)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addActiveSustainRelation(sqlSession, activeSustainRelation);

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
     * 增加 ActiveSustainRelation
     * @param sqlSession
     * @param activeSustainRelation
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveSustainRelation(SqlSession sqlSession, ActiveSustainRelation activeSustainRelation) throws Exception {

		Long result = null; 
		ActiveSustainRelationMapper activeSustainRelationMapper = sqlSession.getMapper(ActiveSustainRelationMapper.class);
		if (activeSustainRelationMapper.addActiveSustainRelation(activeSustainRelation) > -1) { // 添加成功
			result = activeSustainRelation.getId(); 
		}
		return result; 

	}


    /**
     * 删除 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveSustainRelation(sqlSession, activeSustainRelation); 
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
     * 删除 ActiveSustainRelation
     * @param sqlSession
     * @param activeSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveSustainRelation(SqlSession sqlSession, ActiveSustainRelation activeSustainRelation) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveSustainRelationMapper.class).deleteActiveSustainRelation(activeSustainRelation); 
	}


    /**
     * 根据id删除 ActiveSustainRelation
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveSustainRelationById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveSustainRelationById(sqlSession, id); 
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
     * 根据id删除 ActiveSustainRelation
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveSustainRelationById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveSustainRelationMapper.class).deleteActiveSustainRelationById(id); 
	}


    /**
     * 修改 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveSustainRelation(sqlSession, activeSustainRelation); 
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
     * 修改 ActiveSustainRelation
     * @param sqlSession
     * @param activeSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveSustainRelation(SqlSession sqlSession, ActiveSustainRelation activeSustainRelation) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveSustainRelationMapper.class).updateActiveSustainRelation(activeSustainRelation); 
	}


    /**
     * 根据id修改 ActiveSustainRelation
     * @param ActiveSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveSustainRelationById(ActiveSustainRelation activeSustainRelation) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveSustainRelationById(sqlSession, activeSustainRelation); 
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
     * 根据id修改 ActiveSustainRelation
     * @param sqlSession
     * @param activeSustainRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveSustainRelationById(SqlSession sqlSession, ActiveSustainRelation activeSustainRelation) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveSustainRelationMapper.class).updateActiveSustainRelationById(activeSustainRelation); 
	}


    /**
     * 根据id查询单个 ActiveSustainRelation
     * @param id
     * @return ActiveSustainRelation    单个ActiveSustainRelation
     * @throws Exception
     */
	@Override
	public ActiveSustainRelation findActiveSustainRelationById(Long id) throws Exception {

		ActiveSustainRelation result = null; 
		SqlSession sqlSession = null;
		ActiveSustainRelationMapper activeSustainRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeSustainRelationMapper = sqlSession.getMapper(ActiveSustainRelationMapper.class);

			result = activeSustainRelationMapper.findActiveSustainRelationById(id); 
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
     * 查询一个 ActiveSustainRelation
     * @param activeSustainRelation
     * @return ActiveSustainRelation    单个ActiveSustainRelation
     * @throws Exception
     */
	@Override
	public ActiveSustainRelation findOneActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception {

		ActiveSustainRelation result = null; 
		SqlSession sqlSession = null;
		ActiveSustainRelationMapper activeSustainRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeSustainRelationMapper = sqlSession.getMapper(ActiveSustainRelationMapper.class);

			result = activeSustainRelationMapper.findOneActiveSustainRelation(activeSustainRelation); 
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
     * 查询统计 ActiveSustainRelation
     * @param activeSustainRelation
     * @return Integer    返回记录数ActiveSustainRelation
     * @throws Exception
     */
	@Override
	public Integer countActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ActiveSustainRelationMapper activeSustainRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeSustainRelationMapper = sqlSession.getMapper(ActiveSustainRelationMapper.class);

			result = activeSustainRelationMapper.countActiveSustainRelation(activeSustainRelation); 
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
     * 查询 ActiveSustainRelation
     * @param activeSustainRelation
     * @return List<ActiveSustainRelation>    ActiveSustainRelation集合 
     * @throws Exception
     */
	@Override
	public List<ActiveSustainRelation> findActiveSustainRelation(ActiveSustainRelation activeSustainRelation) throws Exception {

		return this.findActiveSustainRelation(activeSustainRelation, null); 

	}


    /**
     * 查询 ActiveSustainRelation
     * @param activeSustainRelation
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveSustainRelation>    ActiveSustainRelation集合 
     * @throws Exception
     */
	@Override
	public List<ActiveSustainRelation> findActiveSustainRelation(ActiveSustainRelation activeSustainRelation, String order) throws Exception {

		List<ActiveSustainRelation> result = null; 
		SqlSession sqlSession = null;
		ActiveSustainRelationMapper activeSustainRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeSustainRelationMapper = sqlSession.getMapper(ActiveSustainRelationMapper.class);

			result = activeSustainRelationMapper.findActiveSustainRelation(activeSustainRelation, order); 
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
     * 分页查询 ActiveSustainRelation
     * @param page
     * @param activeSustainRelation
     * @return Page<ActiveSustainRelation>    ActiveSustainRelation分页结果 
     * @throws Exception 
     */
	@Override
	public List<ActiveSustainRelation> findActiveSustainRelationByPage(Page<ActiveSustainRelation> page, ActiveSustainRelation activeSustainRelation) throws Exception {

		List<ActiveSustainRelation> result = new ArrayList<ActiveSustainRelation>(); 
		SqlSession sqlSession = null;
		ActiveSustainRelationMapper activeSustainRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeSustainRelationMapper = sqlSession.getMapper(ActiveSustainRelationMapper.class);

			result = activeSustainRelationMapper.findByPage(page, activeSustainRelation); 
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
		return result; 

	}


}