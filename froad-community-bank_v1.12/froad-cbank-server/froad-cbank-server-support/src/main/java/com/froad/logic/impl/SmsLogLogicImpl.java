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
 * @Title: SmsLogLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.SmsLogMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.SmsLogLogic;
import com.froad.po.SmsLog;
import com.froad.util.SmsTypeUtil;

/**
 * 
 * <p>@Title: SmsLogLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class SmsLogLogicImpl implements SmsLogLogic {


    /**
     * 增加 SmsLog
     * @param smsLog
     * @return Long    主键ID
     */
	@Override
	public Long addSmsLog(SmsLog smsLog) {

		Long result = null; 
		SqlSession sqlSession = null;
		SmsLogMapper smsLogMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsLogMapper = sqlSession.getMapper(SmsLogMapper.class);

			if (smsLogMapper.addSmsLog(smsLog) > -1) { 
				result = smsLog.getId(); 
			}
			sqlSession.commit(true); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加SmsLog失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 SmsLog
     * @param smsLog
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteSmsLog(SmsLog smsLog) {

		Boolean result = null; 
		SqlSession sqlSession = null;
		SmsLogMapper smsLogMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsLogMapper = sqlSession.getMapper(SmsLogMapper.class);

			result = smsLogMapper.deleteSmsLog(smsLog); 
			sqlSession.commit(true);

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("删除SmsLog失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 SmsLog
     * @param smsLog
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateSmsLog(SmsLog smsLog) {

		Boolean result = null; 
		SqlSession sqlSession = null;
		SmsLogMapper smsLogMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsLogMapper = sqlSession.getMapper(SmsLogMapper.class);

			result = smsLogMapper.updateSmsLog(smsLog); 
			sqlSession.commit(true);

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("修改SmsLog失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 SmsLog
     * @param smsLog
     * @return List<SmsLog>    结果集合 
     */
	@Override
	public List<SmsLog> findSmsLog(SmsLog smsLog,String flag) {

		List<SmsLog> result = new ArrayList<SmsLog>(); 
		SqlSession sqlSession = null;
		SmsLogMapper smsLogMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsLogMapper = sqlSession.getMapper(SmsLogMapper.class);
            Integer[] smsTypes= SmsTypeUtil.getSmstypeCondition(flag);
            
			result = smsLogMapper.findSmsLog(smsLog,smsTypes); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询SmsLog失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 SmsLog
     * @param page
     * @param smsLog
     * @return Page<SmsLog>    结果集合 
     */
	@Override
	public Page<SmsLog> findSmsLogByPage(Page<SmsLog> page, SmsLog smsLog,String flag) {

		List<SmsLog> result = new ArrayList<SmsLog>(); 
		SqlSession sqlSession = null;
		SmsLogMapper smsLogMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsLogMapper = sqlSession.getMapper(SmsLogMapper.class);
            Integer[] smsTypes=SmsTypeUtil.getSmstypeCondition(flag);
			result = smsLogMapper.findByPage(page, smsLog,smsTypes); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询SmsLog失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}