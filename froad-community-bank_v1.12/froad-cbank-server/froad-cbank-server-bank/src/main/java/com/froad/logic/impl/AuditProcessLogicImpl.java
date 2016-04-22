package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AuditProcessCommonMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.AuditProcessLogic;
import com.froad.po.AuditProcess;

/**
 * 
 * <p>@Title: AuditProcessLogicImpl.java</p>
 * <p>Description: 描述 </p> 审核任务Logic实现类<>
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年8月18日
 */
public class AuditProcessLogicImpl implements AuditProcessLogic {

	/**
     * 查询审核任务列表
     * @param auditId 审核流水号
     * @return List<AuditProcess>
     */
	@Override
	public List<AuditProcess> findAuditProcess(String auditId) {
		List<AuditProcess> result = new ArrayList<AuditProcess>(); 
		SqlSession sqlSession = null;
		AuditProcessCommonMapper auditProcessCommonMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			auditProcessCommonMapper = sqlSession.getMapper(AuditProcessCommonMapper.class);

			result = auditProcessCommonMapper.findAuditProcessByAuditId(auditId, null);
		} catch (Exception e) { 
			LogCvt.error("分页查询ClientMerchantAudit失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

	

    



	
		
		
		
		
		
}