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
 * @Title: RegistDetailRuleHandlerImpl.java
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
import com.froad.db.mysql.mapper.RegistDetailRuleMapper;
import com.froad.logback.LogCvt;
import com.froad.po.RegistDetailRule;
import com.froad.handler.RegistDetailRuleHandler;

/**
 * 
 * <p>@Title: RegistDetailRuleHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体RegistDetailRule的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class RegistDetailRuleHandlerImpl implements RegistDetailRuleHandler {


    /**
     * 增加 RegistDetailRule
     * @param registDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addRegistDetailRule(RegistDetailRule registDetailRule)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addRegistDetailRule(sqlSession, registDetailRule);

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
     * 增加 RegistDetailRule
     * @param sqlSession
     * @param registDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addRegistDetailRule(SqlSession sqlSession, RegistDetailRule registDetailRule) throws Exception {

		Long result = null; 
		RegistDetailRuleMapper registDetailRuleMapper = sqlSession.getMapper(RegistDetailRuleMapper.class);
		if (registDetailRuleMapper.addRegistDetailRule(registDetailRule) > -1) { // 添加成功
			result = registDetailRule.getId(); 
		}
		return result; 

	}


    /**
     * 删除 RegistDetailRule
     * @param registDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteRegistDetailRule(RegistDetailRule registDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteRegistDetailRule(sqlSession, registDetailRule); 
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
     * 删除 RegistDetailRule
     * @param sqlSession
     * @param registDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteRegistDetailRule(SqlSession sqlSession, RegistDetailRule registDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(RegistDetailRuleMapper.class).deleteRegistDetailRule(registDetailRule); 
	}


    /**
     * 根据id删除 RegistDetailRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteRegistDetailRuleById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteRegistDetailRuleById(sqlSession, id); 
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
     * 根据id删除 RegistDetailRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteRegistDetailRuleById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(RegistDetailRuleMapper.class).deleteRegistDetailRuleById(id); 
	}


    /**
     * 修改 RegistDetailRule
     * @param registDetailRule
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateRegistDetailRule(RegistDetailRule registDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateRegistDetailRule(sqlSession, registDetailRule); 
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
     * 修改 RegistDetailRule
     * @param sqlSession
     * @param registDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateRegistDetailRule(SqlSession sqlSession, RegistDetailRule registDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(RegistDetailRuleMapper.class).updateRegistDetailRule(registDetailRule); 
	}


    /**
     * 根据id修改 RegistDetailRule
     * @param RegistDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateRegistDetailRuleById(RegistDetailRule registDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateRegistDetailRuleById(sqlSession, registDetailRule); 
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
     * 根据id修改 RegistDetailRule
     * @param sqlSession
     * @param registDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateRegistDetailRuleById(SqlSession sqlSession, RegistDetailRule registDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(RegistDetailRuleMapper.class).updateRegistDetailRuleById(registDetailRule); 
	}


    /**
     * 根据id查询单个 RegistDetailRule
     * @param id
     * @return RegistDetailRule    单个RegistDetailRule
     * @throws Exception
     */
	@Override
	public RegistDetailRule findRegistDetailRuleById(Long id) throws Exception {

		RegistDetailRule result = null; 
		SqlSession sqlSession = null;
		RegistDetailRuleMapper registDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			registDetailRuleMapper = sqlSession.getMapper(RegistDetailRuleMapper.class);

			result = registDetailRuleMapper.findRegistDetailRuleById(id); 
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
     * 查询一个 RegistDetailRule
     * @param registDetailRule
     * @return RegistDetailRule    单个RegistDetailRule
     * @throws Exception
     */
	@Override
	public RegistDetailRule findOneRegistDetailRule(RegistDetailRule registDetailRule) throws Exception {

		RegistDetailRule result = null; 
		SqlSession sqlSession = null;
		RegistDetailRuleMapper registDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			registDetailRuleMapper = sqlSession.getMapper(RegistDetailRuleMapper.class);

			result = registDetailRuleMapper.findOneRegistDetailRule(registDetailRule); 
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
     * 查询统计 RegistDetailRule
     * @param registDetailRule
     * @return Integer    返回记录数RegistDetailRule
     * @throws Exception
     */
	@Override
	public Integer countRegistDetailRule(RegistDetailRule registDetailRule) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		RegistDetailRuleMapper registDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			registDetailRuleMapper = sqlSession.getMapper(RegistDetailRuleMapper.class);

			result = registDetailRuleMapper.countRegistDetailRule(registDetailRule); 
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
     * 查询 RegistDetailRule
     * @param registDetailRule
     * @return List<RegistDetailRule>    RegistDetailRule集合 
     * @throws Exception
     */
	@Override
	public List<RegistDetailRule> findRegistDetailRule(RegistDetailRule registDetailRule) throws Exception {

		return this.findRegistDetailRule(registDetailRule, null); 

	}


    /**
     * 查询 RegistDetailRule
     * @param registDetailRule
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<RegistDetailRule>    RegistDetailRule集合 
     * @throws Exception
     */
	@Override
	public List<RegistDetailRule> findRegistDetailRule(RegistDetailRule registDetailRule, String order) throws Exception {

		List<RegistDetailRule> result = null; 
		SqlSession sqlSession = null;
		RegistDetailRuleMapper registDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			registDetailRuleMapper = sqlSession.getMapper(RegistDetailRuleMapper.class);

			result = registDetailRuleMapper.findRegistDetailRule(registDetailRule, order); 
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
     * 分页查询 RegistDetailRule
     * @param page
     * @param registDetailRule
     * @return Page<RegistDetailRule>    RegistDetailRule分页结果 
     * @throws Exception 
     */
	@Override
	public Page<RegistDetailRule> findRegistDetailRuleByPage(Page<RegistDetailRule> page, RegistDetailRule registDetailRule) throws Exception {

		List<RegistDetailRule> result = new ArrayList<RegistDetailRule>(); 
		SqlSession sqlSession = null;
		RegistDetailRuleMapper registDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			registDetailRuleMapper = sqlSession.getMapper(RegistDetailRuleMapper.class);

			result = registDetailRuleMapper.findByPage(page, registDetailRule); 
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

	/**
	 * 查询当前客户端有效的注册送规则
	 * @param clientId
	 * */
	@Override
	public RegistDetailRule findNowEffectiveRegisteredHandsel(String clientId) throws Exception {
		RegistDetailRule result = null; 
		SqlSession sqlSession = null;
		RegistDetailRuleMapper registDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			registDetailRuleMapper = sqlSession.getMapper(RegistDetailRuleMapper.class);
			result = registDetailRuleMapper.findNowEffectiveRegisteredHandsel(clientId); 
		} catch (Exception e) { 
			LogCvt.error("查询当前客户端有效的注册送规则 异常", e);
			result = null; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

	
}