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
 * @Title: BizInstanceAttrHandlerImpl.java
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
import com.froad.db.mysql.mapper.BizInstanceAttrMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.BizInstanceAttrHandler;
import com.froad.po.mysql.BizInstanceAttr;

/**
 * 
 * <p>@Title: BizInstanceAttrHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体BizInstanceAttr的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BizInstanceAttrHandlerImpl implements BizInstanceAttrHandler {


    /**
     * 增加 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addBizInstanceAttr(BizInstanceAttr bizInstanceAttr)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addBizInstanceAttr(sqlSession, bizInstanceAttr);

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
     * 增加 BizInstanceAttr
     * @param sqlSession
     * @param bizInstanceAttr
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addBizInstanceAttr(SqlSession sqlSession, BizInstanceAttr bizInstanceAttr) throws Exception {

		Long result = null; 
		BizInstanceAttrMapper bizInstanceAttrMapper = sqlSession.getMapper(BizInstanceAttrMapper.class);
		if (bizInstanceAttrMapper.addBizInstanceAttr(bizInstanceAttr) > -1) { // 添加成功
			result = bizInstanceAttr.getId(); 
		}
		return result; 

	}


    /**
     * 删除 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteBizInstanceAttr(sqlSession, bizInstanceAttr); 
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
     * 删除 BizInstanceAttr
     * @param sqlSession
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteBizInstanceAttr(SqlSession sqlSession, BizInstanceAttr bizInstanceAttr) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(BizInstanceAttrMapper.class).deleteBizInstanceAttr(bizInstanceAttr); 
	}


    /**
     * 根据id删除 BizInstanceAttr
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteBizInstanceAttrById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteBizInstanceAttrById(sqlSession, id); 
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
     * 根据id删除 BizInstanceAttr
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteBizInstanceAttrById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(BizInstanceAttrMapper.class).deleteBizInstanceAttrById(id); 
	}


    /**
     * 修改 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateBizInstanceAttr(sqlSession, bizInstanceAttr); 
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
     * 修改 BizInstanceAttr
     * @param sqlSession
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateBizInstanceAttr(SqlSession sqlSession, BizInstanceAttr bizInstanceAttr) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(BizInstanceAttrMapper.class).updateBizInstanceAttr(bizInstanceAttr); 
	}


    /**
     * 根据id修改 BizInstanceAttr
     * @param AuditInstanceDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateBizInstanceAttrById(BizInstanceAttr bizInstanceAttr) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateBizInstanceAttrById(sqlSession, bizInstanceAttr); 
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
     * 根据id修改 BizInstanceAttr
     * @param sqlSession
     * @param bizInstanceAttr
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateBizInstanceAttrById(SqlSession sqlSession, BizInstanceAttr bizInstanceAttr) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(BizInstanceAttrMapper.class).updateBizInstanceAttrById(bizInstanceAttr); 
	}


    /**
     * 根据id查询单个 BizInstanceAttr
     * @param id
     * @return BizInstanceAttr    单个BizInstanceAttr
     * @throws Exception
     */
	@Override
	public BizInstanceAttr findBizInstanceAttrById(Long id) throws Exception {

		BizInstanceAttr result = null; 
		SqlSession sqlSession = null;
		BizInstanceAttrMapper bizInstanceAttrMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrMapper = sqlSession.getMapper(BizInstanceAttrMapper.class);

			result = bizInstanceAttrMapper.findBizInstanceAttrById(id); 
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
     * 查询一个 BizInstanceAttr
     * @param bizInstanceAttr
     * @return BizInstanceAttr    单个BizInstanceAttr
     * @throws Exception
     */
	@Override
	public BizInstanceAttr findOneBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception {

		BizInstanceAttr result = null; 
		SqlSession sqlSession = null;
		BizInstanceAttrMapper bizInstanceAttrMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrMapper = sqlSession.getMapper(BizInstanceAttrMapper.class);

			result = bizInstanceAttrMapper.findOneBizInstanceAttr(bizInstanceAttr); 
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
     * 查询统计 BizInstanceAttr
     * @param bizInstanceAttr
     * @return Integer    返回记录数BizInstanceAttr
     * @throws Exception
     */
	@Override
	public Integer countBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		BizInstanceAttrMapper bizInstanceAttrMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrMapper = sqlSession.getMapper(BizInstanceAttrMapper.class);

			result = bizInstanceAttrMapper.countBizInstanceAttr(bizInstanceAttr); 
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
     * 查询 BizInstanceAttr
     * @param bizInstanceAttr
     * @return List<BizInstanceAttr>    BizInstanceAttr集合 
     * @throws Exception
     */
	@Override
	public List<BizInstanceAttr> findBizInstanceAttr(BizInstanceAttr bizInstanceAttr) throws Exception {

		return this.findBizInstanceAttr(bizInstanceAttr, null); 

	}


    /**
     * 查询 BizInstanceAttr
     * @param bizInstanceAttr
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<BizInstanceAttr>    BizInstanceAttr集合 
     * @throws Exception
     */
	@Override
	public List<BizInstanceAttr> findBizInstanceAttr(BizInstanceAttr bizInstanceAttr, String order) throws Exception {

		List<BizInstanceAttr> result = null; 
		SqlSession sqlSession = null;
		BizInstanceAttrMapper bizInstanceAttrMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrMapper = sqlSession.getMapper(BizInstanceAttrMapper.class);

			result = bizInstanceAttrMapper.findBizInstanceAttr(bizInstanceAttr, order); 
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
     * 分页查询 BizInstanceAttr
     * @param page
     * @param bizInstanceAttr
     * @return Page<BizInstanceAttr>    BizInstanceAttr分页结果 
     * @throws Exception 
     */
	@Override
	public Page<BizInstanceAttr> findBizInstanceAttrByPage(Page<BizInstanceAttr> page, BizInstanceAttr bizInstanceAttr) throws Exception {

		List<BizInstanceAttr> result = new ArrayList<BizInstanceAttr>(); 
		SqlSession sqlSession = null;
		BizInstanceAttrMapper bizInstanceAttrMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bizInstanceAttrMapper = sqlSession.getMapper(BizInstanceAttrMapper.class);

			result = bizInstanceAttrMapper.findByPage(page, bizInstanceAttr); 
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