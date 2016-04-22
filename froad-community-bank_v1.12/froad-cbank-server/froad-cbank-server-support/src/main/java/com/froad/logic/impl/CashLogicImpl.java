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
 * @Title: CashLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.CashMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.CashLogic;
import com.froad.po.Cash;

/**
 * 
 * <p>@Title: CashLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class CashLogicImpl implements CashLogic {


    /**
     * 增加 Cash
     * @param cash
     * @return Long    主键ID
     */
	@Override
	public Long addCash(Cash cash) {

		Long result = null; 
		SqlSession sqlSession = null;
		CashMapper cashMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			cashMapper = sqlSession.getMapper(CashMapper.class);

			if (cashMapper.addCash(cash) > -1) { 
				result = cash.getId(); 
			}
			sqlSession.commit(true); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加Cash失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 Cash
     * @param cash
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteCash(Cash cash) {

		Boolean result = null; 
		SqlSession sqlSession = null;
		CashMapper cashMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			cashMapper = sqlSession.getMapper(CashMapper.class);

			result = cashMapper.deleteCash(cash); 
			sqlSession.commit(true);

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
		} catch (Exception e) { 
			sqlSession.rollback(true); 
			result = false; 
			LogCvt.error("删除Cash失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 Cash
     * @param cash
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateCash(Cash cash) {

		Boolean result = null; 
		SqlSession sqlSession = null;
		CashMapper cashMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			cashMapper = sqlSession.getMapper(CashMapper.class);

			result = cashMapper.updateCash(cash); 
			sqlSession.commit(true);

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("修改Cash失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 Cash
     * @param cash
     * @return List<Cash>    结果集合 
     */
	@Override
	public List<Cash> findCash(Cash cash) {

		List<Cash> result = new ArrayList<Cash>(); 
		SqlSession sqlSession = null;
		CashMapper cashMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			cashMapper = sqlSession.getMapper(CashMapper.class);

			result = cashMapper.findCash(cash); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Cash失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Cash
     * @param page
     * @param cash
     * @return Page<Cash>    结果集合 
     */
	@Override
	public Page<Cash> findCashByPage(Page<Cash> page, Cash cash) {

		List<Cash> result = new ArrayList<Cash>(); 
		SqlSession sqlSession = null;
		CashMapper cashMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			cashMapper = sqlSession.getMapper(CashMapper.class);

			result = cashMapper.findByPage(page, cash); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询Cash失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}