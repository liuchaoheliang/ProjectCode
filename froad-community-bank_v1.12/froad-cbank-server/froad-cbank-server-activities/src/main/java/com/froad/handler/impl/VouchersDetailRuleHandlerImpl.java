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
 * @Title: VouchersDetailRuleHandlerImpl.java
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
import com.froad.db.mysql.mapper.VouchersDetailRuleMapper;
import com.froad.handler.VouchersDetailRuleHandler;
import com.froad.po.VouchersDetailRule;

/**
 * 
 * <p>@Title: VouchersDetailRuleHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体VouchersDetailRule的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class VouchersDetailRuleHandlerImpl implements VouchersDetailRuleHandler {


    /**
     * 增加 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addVouchersDetailRule(VouchersDetailRule vouchersDetailRule)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addVouchersDetailRule(sqlSession, vouchersDetailRule);

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
     * 增加 VouchersDetailRule
     * @param sqlSession
     * @param vouchersDetailRule
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addVouchersDetailRule(SqlSession sqlSession, VouchersDetailRule vouchersDetailRule) throws Exception {

		Long result = null; 
		VouchersDetailRuleMapper vouchersDetailRuleMapper = sqlSession.getMapper(VouchersDetailRuleMapper.class);
		if (vouchersDetailRuleMapper.addVouchersDetailRule(vouchersDetailRule) > -1) { // 添加成功
			result = vouchersDetailRule.getId(); 
		}
		return result; 

	}


    /**
     * 删除 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteVouchersDetailRule(sqlSession, vouchersDetailRule); 
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
     * 删除 VouchersDetailRule
     * @param sqlSession
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersDetailRule(SqlSession sqlSession, VouchersDetailRule vouchersDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersDetailRuleMapper.class).deleteVouchersDetailRule(vouchersDetailRule); 
	}


    /**
     * 根据id删除 VouchersDetailRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersDetailRuleById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteVouchersDetailRuleById(sqlSession, id); 
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
     * 根据id删除 VouchersDetailRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersDetailRuleById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersDetailRuleMapper.class).deleteVouchersDetailRuleById(id); 
	}


    /**
     * 修改 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateVouchersDetailRule(sqlSession, vouchersDetailRule); 
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
     * 修改 VouchersDetailRule
     * @param sqlSession
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersDetailRule(SqlSession sqlSession, VouchersDetailRule vouchersDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersDetailRuleMapper.class).updateVouchersDetailRule(vouchersDetailRule); 
	}


    /**
     * 根据id修改 VouchersDetailRule
     * @param VouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersDetailRuleById(VouchersDetailRule vouchersDetailRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateVouchersDetailRuleById(sqlSession, vouchersDetailRule); 
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
     * 根据id修改 VouchersDetailRule
     * @param sqlSession
     * @param vouchersDetailRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersDetailRuleById(SqlSession sqlSession, VouchersDetailRule vouchersDetailRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersDetailRuleMapper.class).updateVouchersDetailRuleById(vouchersDetailRule); 
	}


    /**
     * 根据id查询单个 VouchersDetailRule
     * @param id
     * @return VouchersDetailRule    单个VouchersDetailRule
     * @throws Exception
     */
	@Override
	public VouchersDetailRule findVouchersDetailRuleById(Long id) throws Exception {

		VouchersDetailRule result = null; 
		SqlSession sqlSession = null;
		VouchersDetailRuleMapper vouchersDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersDetailRuleMapper = sqlSession.getMapper(VouchersDetailRuleMapper.class);

			result = vouchersDetailRuleMapper.findVouchersDetailRuleById(id); 
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
     * 查询一个 VouchersDetailRule
     * @param vouchersDetailRule
     * @return VouchersDetailRule    单个VouchersDetailRule
     * @throws Exception
     */
	@Override
	public VouchersDetailRule findOneVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception {

		VouchersDetailRule result = null; 
		SqlSession sqlSession = null;
		VouchersDetailRuleMapper vouchersDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersDetailRuleMapper = sqlSession.getMapper(VouchersDetailRuleMapper.class);

			result = vouchersDetailRuleMapper.findOneVouchersDetailRule(vouchersDetailRule); 
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
     * 查询统计 VouchersDetailRule
     * @param vouchersDetailRule
     * @return Integer    返回记录数VouchersDetailRule
     * @throws Exception
     */
	@Override
	public Integer countVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		VouchersDetailRuleMapper vouchersDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersDetailRuleMapper = sqlSession.getMapper(VouchersDetailRuleMapper.class);

			result = vouchersDetailRuleMapper.countVouchersDetailRule(vouchersDetailRule); 
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
     * 查询 VouchersDetailRule
     * @param vouchersDetailRule
     * @return List<VouchersDetailRule>    VouchersDetailRule集合 
     * @throws Exception
     */
	@Override
	public List<VouchersDetailRule> findVouchersDetailRule(VouchersDetailRule vouchersDetailRule) throws Exception {

		return this.findVouchersDetailRule(vouchersDetailRule, null); 

	}


    /**
     * 查询 VouchersDetailRule
     * @param vouchersDetailRule
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<VouchersDetailRule>    VouchersDetailRule集合 
     * @throws Exception
     */
	@Override
	public List<VouchersDetailRule> findVouchersDetailRule(VouchersDetailRule vouchersDetailRule, String order) throws Exception {

		List<VouchersDetailRule> result = null; 
		SqlSession sqlSession = null;
		VouchersDetailRuleMapper vouchersDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersDetailRuleMapper = sqlSession.getMapper(VouchersDetailRuleMapper.class);

			result = vouchersDetailRuleMapper.findVouchersDetailRule(vouchersDetailRule, order); 
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
     * 分页查询 VouchersDetailRule
     * @param page
     * @param vouchersDetailRule
     * @return Page<VouchersDetailRule>    VouchersDetailRule分页结果 
     * @throws Exception 
     */
	@Override
	public Page<VouchersDetailRule> findVouchersDetailRuleByPage(Page<VouchersDetailRule> page, VouchersDetailRule vouchersDetailRule) throws Exception {

		List<VouchersDetailRule> result = new ArrayList<VouchersDetailRule>(); 
		SqlSession sqlSession = null;
		VouchersDetailRuleMapper vouchersDetailRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersDetailRuleMapper = sqlSession.getMapper(VouchersDetailRuleMapper.class);

			result = vouchersDetailRuleMapper.findByPage(page, vouchersDetailRule); 
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