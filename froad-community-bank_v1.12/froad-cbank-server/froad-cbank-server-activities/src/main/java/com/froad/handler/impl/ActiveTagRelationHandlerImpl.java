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
 * @Title: ActiveTagRelationHandlerImpl.java
 * @Package com.froad.handler.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ActiveTagRelationMapper;
import com.froad.db.mysql.mapper.WeightActivityTagMapper;
import com.froad.handler.ActiveTagRelationHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveTagRelation;

/**
 * 
 * <p>@Title: ActiveTagRelationHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体ActiveTagRelation的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActiveTagRelationHandlerImpl implements ActiveTagRelationHandler {


    /**
     * 增加 ActiveTagRelation
     * @param activeTagRelation
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveTagRelation(ActiveTagRelation activeTagRelation)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addActiveTagRelation(sqlSession, activeTagRelation);

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
     * 增加 ActiveTagRelation
     * @param sqlSession
     * @param activeTagRelation
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveTagRelation(SqlSession sqlSession, ActiveTagRelation activeTagRelation) throws Exception {

		Long result = null; 
		activeTagRelation.setCreateTime(new Date());
		activeTagRelation.setUpdateTime(new Date());
		ActiveTagRelationMapper activeTagRelationMapper = sqlSession.getMapper(ActiveTagRelationMapper.class);
		if (activeTagRelationMapper.addActiveTagRelation(activeTagRelation) > -1) { // 添加成功
			result = activeTagRelation.getId(); 
		}
		return result; 

	}


    /**
     * 删除 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveTagRelation(sqlSession, activeTagRelation); 
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
     * 删除 ActiveTagRelation
     * @param sqlSession
     * @param activeTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveTagRelation(SqlSession sqlSession, ActiveTagRelation activeTagRelation) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveTagRelationMapper.class).deleteActiveTagRelation(activeTagRelation); 
	}


    /**
     * 根据id删除 ActiveTagRelation
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveTagRelationById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveTagRelationById(sqlSession, id); 
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
     * 根据id删除 ActiveTagRelation
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveTagRelationById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveTagRelationMapper.class).deleteActiveTagRelationById(id); 
	}


    /**
     * 修改 ActiveTagRelation
     * @param activeTagRelation
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveTagRelation(sqlSession, activeTagRelation); 
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
     * 修改 ActiveTagRelation
     * @param sqlSession
     * @param activeTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveTagRelation(SqlSession sqlSession, ActiveTagRelation activeTagRelation) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveTagRelationMapper.class).updateActiveTagRelation(activeTagRelation); 
	}


    /**
     * 根据id修改 ActiveTagRelation
     * @param ActiveTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveTagRelationById(ActiveTagRelation activeTagRelation) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveTagRelationById(sqlSession, activeTagRelation); 
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
     * 根据id修改 ActiveTagRelation
     * @param sqlSession
     * @param activeTagRelation
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveTagRelationById(SqlSession sqlSession, ActiveTagRelation activeTagRelation) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveTagRelationMapper.class).updateActiveTagRelationById(activeTagRelation); 
	}


    /**
     * 根据id查询单个 ActiveTagRelation
     * @param id
     * @return ActiveTagRelation    单个ActiveTagRelation
     * @throws Exception
     */
	@Override
	public ActiveTagRelation findActiveTagRelationById(Long id) throws Exception {

		ActiveTagRelation result = null; 
		SqlSession sqlSession = null;
		ActiveTagRelationMapper activeTagRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeTagRelationMapper = sqlSession.getMapper(ActiveTagRelationMapper.class);

			result = activeTagRelationMapper.findActiveTagRelationById(id); 
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
     * 查询一个 ActiveTagRelation
     * @param activeTagRelation
     * @return ActiveTagRelation    单个ActiveTagRelation
     * @throws Exception
     */
	@Override
	public ActiveTagRelation findOneActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception {

		ActiveTagRelation result = null; 
		SqlSession sqlSession = null;
		ActiveTagRelationMapper activeTagRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeTagRelationMapper = sqlSession.getMapper(ActiveTagRelationMapper.class);

			result = activeTagRelationMapper.findOneActiveTagRelation(activeTagRelation); 
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
     * 查询统计 ActiveTagRelation
     * @param activeTagRelation
     * @return Integer    返回记录数ActiveTagRelation
     * @throws Exception
     */
	@Override
	public Integer countActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ActiveTagRelationMapper activeTagRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeTagRelationMapper = sqlSession.getMapper(ActiveTagRelationMapper.class);

			result = activeTagRelationMapper.countActiveTagRelation(activeTagRelation); 
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
     * 查询 ActiveTagRelation
     * @param activeTagRelation
     * @return List<ActiveTagRelation>    ActiveTagRelation集合 
     * @throws Exception
     */
	@Override
	public List<ActiveTagRelation> findActiveTagRelation(ActiveTagRelation activeTagRelation) throws Exception {

		return this.findActiveTagRelation(activeTagRelation, null); 

	}


    /**
     * 查询 ActiveTagRelation
     * @param activeTagRelation
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveTagRelation>    ActiveTagRelation集合 
     * @throws Exception
     */
	@Override
	public List<ActiveTagRelation> findActiveTagRelation(ActiveTagRelation activeTagRelation, String order) throws Exception {

		List<ActiveTagRelation> result = null; 
		SqlSession sqlSession = null;
		ActiveTagRelationMapper activeTagRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeTagRelationMapper = sqlSession.getMapper(ActiveTagRelationMapper.class);

			result = activeTagRelationMapper.findActiveTagRelation(activeTagRelation, order); 
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
     * 分页查询 ActiveTagRelation
     * @param page
     * @param activeTagRelation
     * @return Page<ActiveTagRelation>    ActiveTagRelation分页结果 
     * @throws Exception 
     */
	@Override
	public Page<ActiveTagRelation> findActiveTagRelationByPage(Page<ActiveTagRelation> page, ActiveTagRelation activeTagRelation) throws Exception {

		List<ActiveTagRelation> result = new ArrayList<ActiveTagRelation>(); 
		SqlSession sqlSession = null;
		ActiveTagRelationMapper activeTagRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeTagRelationMapper = sqlSession.getMapper(ActiveTagRelationMapper.class);

			result = activeTagRelationMapper.findByPage(page, activeTagRelation); 
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
	
	@Override
	public int countLimitProductActivityTag(String clientId, String activeType) {
		int result = 0;
		SqlSession sqlSession = null;
		ActiveTagRelationMapper activeTagRelationMapper = null;
		try { 
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				activeTagRelationMapper = sqlSession.getMapper(ActiveTagRelationMapper.class);
				//MySql查询数据
				result = activeTagRelationMapper.countLimitProductActivityTag(clientId, activeType);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("countLimitProductActivityTag失败，原因:" + e.getMessage(), e); 
			e.printStackTrace();
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}


	 /**
	  * @Title: getAvailableActive
	  * @Description: TODO
	  * @author: Joker 2015年12月25日
	  * @modify: Joker 2015年12月25日
	  * @param clientId
	  * @param itemType
	  * @param itemId
	  * @param activeId
	  * @return
	  * @see com.froad.handler.ActiveTagRelationHandler#getAvailableActive(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	  */
	
	
	@Override
	public ActiveTagRelation getAvailableActive(String clientId,
			String itemType, String itemId, String activeId, String type) {
		ActiveTagRelation result = null; 
		SqlSession sqlSession = null;
		ActiveTagRelationMapper activeTagRelationMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeTagRelationMapper = sqlSession.getMapper(ActiveTagRelationMapper.class);

			result = activeTagRelationMapper.findActive(clientId, itemType, itemId, activeId, type);
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("getAvailableActive失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


}