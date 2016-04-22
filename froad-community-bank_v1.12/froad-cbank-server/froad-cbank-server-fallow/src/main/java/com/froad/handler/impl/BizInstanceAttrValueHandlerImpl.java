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
 * @Title: BizInstanceAttrValueHandlerImpl.java
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
import com.froad.db.mysql.mapper.BizInstanceAttrValueMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.BizInstanceAttrValueHandler;
import com.froad.po.mysql.BizInstanceAttrValue;

/**
 * 
 * <p>@Title: BizInstanceAttrValueHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体BizInstanceAttrValue的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BizInstanceAttrValueHandlerImpl implements BizInstanceAttrValueHandler {


    /**
     * 增加 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return String    主键ID
     * @throws Exception
     */
	@Override
	public String addBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue)  throws Exception {

		String result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addBizInstanceAttrValue(sqlSession, bizInstanceAttrValue);

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
     * 增加 BizInstanceAttrValue
     * @param sqlSession
     * @param bizInstanceAttrValue
     * @return String    主键ID
     * @throws Exception
     */
	@Override
	public String addBizInstanceAttrValue(SqlSession sqlSession, BizInstanceAttrValue bizInstanceAttrValue) throws Exception {

		String result = null; 
		BizInstanceAttrValueMapper bizInstanceAttrValueMapper = sqlSession.getMapper(BizInstanceAttrValueMapper.class);
		if (bizInstanceAttrValueMapper.addBizInstanceAttrValue(bizInstanceAttrValue) > -1) { // 添加成功
			result = bizInstanceAttrValue.getInstanceId(); 
		}
		return result; 

	}


    /**
     * 删除 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteBizInstanceAttrValue(sqlSession, bizInstanceAttrValue); 
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
     * 删除 BizInstanceAttrValue
     * @param sqlSession
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteBizInstanceAttrValue(SqlSession sqlSession, BizInstanceAttrValue bizInstanceAttrValue) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(BizInstanceAttrValueMapper.class).deleteBizInstanceAttrValue(bizInstanceAttrValue); 
	}


    /**
     * 根据instanceId删除 BizInstanceAttrValue
     * @param instanceId
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteBizInstanceAttrValueByInstanceId(String instanceId) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteBizInstanceAttrValueByInstanceId(sqlSession, instanceId); 
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
     * 根据instanceId删除 BizInstanceAttrValue
     * @param instanceId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteBizInstanceAttrValueByInstanceId(SqlSession sqlSession, String instanceId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(BizInstanceAttrValueMapper.class).deleteBizInstanceAttrValueByInstanceId(instanceId); 
	}


    /**
     * 修改 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateBizInstanceAttrValue(sqlSession, bizInstanceAttrValue); 
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
     * 修改 BizInstanceAttrValue
     * @param sqlSession
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateBizInstanceAttrValue(SqlSession sqlSession, BizInstanceAttrValue bizInstanceAttrValue) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(BizInstanceAttrValueMapper.class).updateBizInstanceAttrValue(bizInstanceAttrValue); 
	}


    /**
     * 根据instanceId修改 BizInstanceAttrValue
     * @param BizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateBizInstanceAttrValueByInstanceId(BizInstanceAttrValue bizInstanceAttrValue) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateBizInstanceAttrValueByInstanceId(sqlSession, bizInstanceAttrValue); 
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
     * 根据instanceId修改 BizInstanceAttrValue
     * @param sqlSession
     * @param bizInstanceAttrValue
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateBizInstanceAttrValueByInstanceId(SqlSession sqlSession, BizInstanceAttrValue bizInstanceAttrValue) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(BizInstanceAttrValueMapper.class).updateBizInstanceAttrValueByInstanceId(bizInstanceAttrValue); 
	}


    /**
     * 根据instanceId查询单个 BizInstanceAttrValue
     * @param instanceId
     * @return BizInstanceAttrValue    单个BizInstanceAttrValue
     * @throws Exception
     */
	@Override
	public BizInstanceAttrValue findBizInstanceAttrValueByInstanceId(String instanceId) throws Exception {

		BizInstanceAttrValue result = null; 
		SqlSession sqlSession = null;
		BizInstanceAttrValueMapper bizInstanceAttrValueMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrValueMapper = sqlSession.getMapper(BizInstanceAttrValueMapper.class);

			result = bizInstanceAttrValueMapper.findBizInstanceAttrValueByInstanceId(instanceId); 
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
     * 查询一个 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return BizInstanceAttrValue    单个BizInstanceAttrValue
     * @throws Exception
     */
	@Override
	public BizInstanceAttrValue findOneBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception {

		BizInstanceAttrValue result = null; 
		SqlSession sqlSession = null;
		BizInstanceAttrValueMapper bizInstanceAttrValueMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrValueMapper = sqlSession.getMapper(BizInstanceAttrValueMapper.class);

			result = bizInstanceAttrValueMapper.findOneBizInstanceAttrValue(bizInstanceAttrValue); 
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
     * 查询统计 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return Integer    返回记录数BizInstanceAttrValue
     * @throws Exception
     */
	@Override
	public Integer countBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		BizInstanceAttrValueMapper bizInstanceAttrValueMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrValueMapper = sqlSession.getMapper(BizInstanceAttrValueMapper.class);

			result = bizInstanceAttrValueMapper.countBizInstanceAttrValue(bizInstanceAttrValue); 
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
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @return List<BizInstanceAttrValue>    BizInstanceAttrValue集合 
     * @throws Exception
     */
	@Override
	public List<BizInstanceAttrValue> findBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue) throws Exception {

		return this.findBizInstanceAttrValue(bizInstanceAttrValue, null); 

	}


    /**
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValue
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<BizInstanceAttrValue>    BizInstanceAttrValue集合 
     * @throws Exception
     */
	@Override
	public List<BizInstanceAttrValue> findBizInstanceAttrValue(BizInstanceAttrValue bizInstanceAttrValue, String order) throws Exception {

		List<BizInstanceAttrValue> result = null; 
		SqlSession sqlSession = null;
		BizInstanceAttrValueMapper bizInstanceAttrValueMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrValueMapper = sqlSession.getMapper(BizInstanceAttrValueMapper.class);

			result = bizInstanceAttrValueMapper.findBizInstanceAttrValue(bizInstanceAttrValue, order); 
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
     * 分页查询 BizInstanceAttrValue
     * @param page
     * @param bizInstanceAttrValue
     * @return Page<BizInstanceAttrValue>    BizInstanceAttrValue分页结果 
     * @throws Exception 
     */
	@Override
	public Page<BizInstanceAttrValue> findBizInstanceAttrValueByPage(Page<BizInstanceAttrValue> page, BizInstanceAttrValue bizInstanceAttrValue) throws Exception {

		List<BizInstanceAttrValue> result = new ArrayList<BizInstanceAttrValue>(); 
		SqlSession sqlSession = null;
		BizInstanceAttrValueMapper bizInstanceAttrValueMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrValueMapper = sqlSession.getMapper(BizInstanceAttrValueMapper.class);

			result = bizInstanceAttrValueMapper.findByPage(page, bizInstanceAttrValue); 
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