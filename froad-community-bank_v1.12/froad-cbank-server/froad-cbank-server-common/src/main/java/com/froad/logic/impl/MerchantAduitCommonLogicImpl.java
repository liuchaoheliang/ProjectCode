package com.froad.logic.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AuditProcessCommonMapper;
import com.froad.db.mysql.mapper.AuditTaskCommonMapper;
import com.froad.enums.ProductAuditState;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantAduitCommonLogic;
import com.froad.po.AuditProcess;
import com.froad.po.AuditTask;
import com.froad.util.Checker;

public class MerchantAduitCommonLogicImpl implements MerchantAduitCommonLogic{

	
	
	/**
     * 根据审核流水号auditId查询待审核记录
     * @param auditId 审核流水号
     * @author ll 20150817 add
     * @return AuditProcess 审核任务对象
     */
	@Override
	public AuditProcess getAuditProcessByAuditId(String auditId) {

		/**********************操作MySQL数据库**********************/
		AuditProcess result = new AuditProcess(); 
		SqlSession sqlSession = null;
		AuditProcessCommonMapper auditProcessCommonMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			auditProcessCommonMapper = sqlSession.getMapper(AuditProcessCommonMapper.class);

			List<AuditProcess> auditProcessList = auditProcessCommonMapper.findAuditProcessByAuditId(auditId,ProductAuditState.noAudit.getCode());
			if(Checker.isNotEmpty(auditProcessList) && auditProcessList.size()==1){
				result = auditProcessList.get(0);
			}
		} catch (Exception e) { 
			LogCvt.error("查询AuditProcess待审核任务失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	/**
     * 根据商户id获得商户待审核任务订单信息
     * @param thridId  type=1时存储商户Id
     * @author ll 20150817 add
     * @return AuditTask 审核任务订单对象
     */
	@Override
	public AuditTask getAuditTaskByMerchantId(String thridId)  throws Exception {

		/**********************操作MySQL数据库**********************/
		AuditTask result = new AuditTask(); 
		SqlSession sqlSession = null;
		AuditTaskCommonMapper auditTaskCommonMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			auditTaskCommonMapper = sqlSession.getMapper(AuditTaskCommonMapper.class);

			result = auditTaskCommonMapper.findAuditTaskByMerchantId(thridId);
		} catch (Exception e) { 
			LogCvt.error("查询AuditTask待审核任务订单失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	
	@Override
	public AuditTask getAuditTaskByAuditId(String auditId) throws Exception {
		
		/**********************操作MySQL数据库**********************/
		AuditTask result = new AuditTask(); 
		SqlSession sqlSession = null;
		AuditTaskCommonMapper auditTaskCommonMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			auditTaskCommonMapper = sqlSession.getMapper(AuditTaskCommonMapper.class);

			result = auditTaskCommonMapper.findAuditTaskByAuditId(auditId);
		} catch (Exception e) { 
			LogCvt.error("查询AuditTask待审核任务订单失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	

}
