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
 * @Title: AgreementLogicImpl.java
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
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.AgreementMapper;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.AgreementLogic;
import com.froad.po.Agreement;

/**
 * 
 * <p>@Title: AgreementLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class AgreementLogicImpl implements AgreementLogic {


    /**
     * 增加 Agreement
     * @param agreement
     * @return Long    主键ID
     */
	@Override
	public ResultBean addAgreement(Agreement agreement) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加协议信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		AgreementMapper agreementMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			agreementMapper = sqlSession.getMapper(AgreementMapper.class);

			if (agreementMapper.addAgreement(agreement) > -1) { 
				result = agreement.getId(); 
			}
			sqlSession.commit(true); 
			 resultBean= new ResultBean(ResultCode.success,"添加协议信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加Agreement失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"添加协议信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 Agreement
     * @param agreement
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteAgreement(Agreement agreement) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除协议信息成功");
		SqlSession sqlSession = null;
		AgreementMapper agreementMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			agreementMapper = sqlSession.getMapper(AgreementMapper.class);

			agreementMapper.deleteAgreement(agreement); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("删除Agreement失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"删除协议信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 Agreement
     * @param agreement
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateAgreement(Agreement agreement) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改活动信息成功");
		SqlSession sqlSession = null;
		AgreementMapper agreementMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			agreementMapper = sqlSession.getMapper(AgreementMapper.class);

			agreementMapper.updateAgreement(agreement); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("修改Agreement失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"修改协议信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 Agreement
     * @param agreement
     * @return List<Agreement>    结果集合 
     */
	@Override
	public List<Agreement> findAgreement(Agreement agreement) {

		List<Agreement> result = new ArrayList<Agreement>(); 
		SqlSession sqlSession = null;
		AgreementMapper agreementMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			agreementMapper = sqlSession.getMapper(AgreementMapper.class);

			result = agreementMapper.findAgreement(agreement); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询Agreement失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Agreement
     * @param page
     * @param agreement
     * @return Page<Agreement>    结果集合 
     */
	@Override
	public Page<Agreement> findAgreementByPage(Page<Agreement> page, Agreement agreement) {

		List<Agreement> result = new ArrayList<Agreement>(); 
		SqlSession sqlSession = null;
		AgreementMapper agreementMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			agreementMapper = sqlSession.getMapper(AgreementMapper.class);

			result = agreementMapper.findByPage(page, agreement); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询Agreement失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}