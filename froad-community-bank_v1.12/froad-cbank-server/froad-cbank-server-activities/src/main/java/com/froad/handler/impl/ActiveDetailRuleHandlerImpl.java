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
 * @Title: ActiveDetailRuleHandlerImpl.java
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
import com.froad.db.mysql.mapper.ActiveDetailRuleMapper;
import com.froad.handler.ActiveDetailRuleHandler;
import com.froad.po.ActiveDetailRule;

/**
 * 
 * <p>@Title: ActiveDetailRuleHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体ActiveDetailRule的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActiveDetailRuleHandlerImpl implements ActiveDetailRuleHandler {


    /**
     * 增加 ActiveDetailRule
     * @param activeDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveDetailRule(ActiveDetailRule activeDetailRule)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addActiveDetailRule(sqlSession, activeDetailRule);

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
     * 增加 ActiveDetailRule
     * @param sqlSession
     * @param activeDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveDetailRule(SqlSession sqlSession, ActiveDetailRule activeDetailRule) throws Exception {

		Long result = null; 
		ActiveDetailRuleMapper activeDetailRuleMapper = sqlSession.getMapper(ActiveDetailRuleMapper.class);
		if (activeDetailRuleMapper.addActiveDetailRule(activeDetailRule) > -1) { // 添加成功
			result = activeDetailRule.getId(); 
		}
		return result; 

	}


    /**
     * 删除 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveDetailRule(sqlSession, activeDetailRule); 
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
     * 删除 ActiveDetailRule
     * @param sqlSession
     * @param activeDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveDetailRule(SqlSession sqlSession, ActiveDetailRule activeDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveDetailRuleMapper.class).deleteActiveDetailRule(activeDetailRule); 
	}


    /**
     * 根据id删除 ActiveDetailRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveDetailRuleById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveDetailRuleById(sqlSession, id); 
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
     * 根据id删除 ActiveDetailRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveDetailRuleById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveDetailRuleMapper.class).deleteActiveDetailRuleById(id); 
	}


    /**
     * 修改 ActiveDetailRule
     * @param activeDetailRule
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveDetailRule(sqlSession, activeDetailRule); 
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
     * 修改 ActiveDetailRule
     * @param sqlSession
     * @param activeDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveDetailRule(SqlSession sqlSession, ActiveDetailRule activeDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveDetailRuleMapper.class).updateActiveDetailRule(activeDetailRule); 
	}


    /**
     * 根据id修改 ActiveDetailRule
     * @param ActiveDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveDetailRuleById(ActiveDetailRule activeDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveDetailRuleById(sqlSession, activeDetailRule); 
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
     * 根据id修改 ActiveDetailRule
     * @param sqlSession
     * @param activeDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveDetailRuleById(SqlSession sqlSession, ActiveDetailRule activeDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveDetailRuleMapper.class).updateActiveDetailRuleById(activeDetailRule); 
	}


    /**
     * 根据id查询单个 ActiveDetailRule
     * @param id
     * @return ActiveDetailRule    单个ActiveDetailRule
     * @throws Exception
     */
	@Override
	public ActiveDetailRule findActiveDetailRuleById(Long id) throws Exception {

		ActiveDetailRule result = null; 
		SqlSession sqlSession = null;
		ActiveDetailRuleMapper activeDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeDetailRuleMapper = sqlSession.getMapper(ActiveDetailRuleMapper.class);

			result = activeDetailRuleMapper.findActiveDetailRuleById(id); 
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
     * 查询一个 ActiveDetailRule
     * @param activeDetailRule
     * @return ActiveDetailRule    单个ActiveDetailRule
     * @throws Exception
     */
	@Override
	public ActiveDetailRule findOneActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception {

		ActiveDetailRule result = null; 
		SqlSession sqlSession = null;
		ActiveDetailRuleMapper activeDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeDetailRuleMapper = sqlSession.getMapper(ActiveDetailRuleMapper.class);

			result = activeDetailRuleMapper.findOneActiveDetailRule(activeDetailRule); 
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
     * 查询统计 ActiveDetailRule
     * @param activeDetailRule
     * @return Integer    返回记录数ActiveDetailRule
     * @throws Exception
     */
	@Override
	public Integer countActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ActiveDetailRuleMapper activeDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeDetailRuleMapper = sqlSession.getMapper(ActiveDetailRuleMapper.class);

			result = activeDetailRuleMapper.countActiveDetailRule(activeDetailRule); 
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
     * 查询 ActiveDetailRule
     * @param activeDetailRule
     * @return List<ActiveDetailRule>    ActiveDetailRule集合 
     * @throws Exception
     */
	@Override
	public List<ActiveDetailRule> findActiveDetailRule(ActiveDetailRule activeDetailRule) throws Exception {

		return this.findActiveDetailRule(activeDetailRule, null); 

	}


    /**
     * 查询 ActiveDetailRule
     * @param activeDetailRule
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveDetailRule>    ActiveDetailRule集合 
     * @throws Exception
     */
	@Override
	public List<ActiveDetailRule> findActiveDetailRule(ActiveDetailRule activeDetailRule, String order) throws Exception {

		List<ActiveDetailRule> result = null; 
		SqlSession sqlSession = null;
		ActiveDetailRuleMapper activeDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeDetailRuleMapper = sqlSession.getMapper(ActiveDetailRuleMapper.class);

			result = activeDetailRuleMapper.findActiveDetailRule(activeDetailRule, order); 
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
     * 分页查询 ActiveDetailRule
     * @param page
     * @param activeDetailRule
     * @return Page<ActiveDetailRule>    ActiveDetailRule分页结果 
     * @throws Exception 
     */
	@Override
	public Page<ActiveDetailRule> findActiveDetailRuleByPage(Page<ActiveDetailRule> page, ActiveDetailRule activeDetailRule) throws Exception {

		List<ActiveDetailRule> result = new ArrayList<ActiveDetailRule>(); 
		SqlSession sqlSession = null;
		ActiveDetailRuleMapper activeDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeDetailRuleMapper = sqlSession.getMapper(ActiveDetailRuleMapper.class);

			result = activeDetailRuleMapper.findByPage(page, activeDetailRule); 
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
	public Integer deleteActiveBaseRuleByActiveId(String activeId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}