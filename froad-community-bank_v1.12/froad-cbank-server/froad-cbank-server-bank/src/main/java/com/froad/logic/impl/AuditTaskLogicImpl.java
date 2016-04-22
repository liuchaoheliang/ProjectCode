package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.AuditTaskCommonMapper;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.logic.AuditTaskLogic;
import com.froad.po.AuditTask;
import com.froad.po.BankOperateLog;
import com.froad.po.Org;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: AuditTaskLogicImpl.java</p>
 * <p>Description: 描述 </p> 审核任务订单Logic实现类<>
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年8月18日
 */
public class AuditTaskLogicImpl implements AuditTaskLogic {

	/**
     * 分页查询 ClientProductAudit
     * @param page 分页对象
     * @param auditTaskFilter 过滤对象
     * @param flag 查询权限标志 1-查当前机构 2-查当前机构所有下属机构
     * @return Page<AuditTask> 
     */
	@Override
	public Page<AuditTask> findAuditTaskByPage(Page<AuditTask> page,AuditTask auditTaskFilter, int flag) {
		List<AuditTask> result = new ArrayList<AuditTask>(); 
		SqlSession sqlSession = null;
		AuditTaskCommonMapper auditTaskCommonMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			auditTaskCommonMapper = sqlSession.getMapper(AuditTaskCommonMapper.class);

			if(flag == 2){
				//判断orgCode级别，若为一级，减少多条件查询
				if(Checker.isNotEmpty(auditTaskFilter)){
					if(Checker.isNotEmpty(auditTaskFilter.getClientId()) && Checker.isNotEmpty(auditTaskFilter.getOrgCode())){
						Org org = new CommonLogicImpl().getOrgByOrgCode(auditTaskFilter.getOrgCode(),auditTaskFilter.getClientId());
						
						if(Checker.isEmpty(org)){
							return new Page<AuditTask>();
						}
						
						//若一级则清空orgCode减少连表查询
						if(OrgLevelEnum.orgLevel_one.getLevel().equals(org.getOrgLevel())){
							auditTaskFilter.setOrgCode(null);
						}
					}
				}
			}
			
			
			result = auditTaskCommonMapper.findAuditTaskByPage(page, auditTaskFilter, flag);
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("分页查询AuditTask失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 
	}


    



	
		
		
		
		
		
}