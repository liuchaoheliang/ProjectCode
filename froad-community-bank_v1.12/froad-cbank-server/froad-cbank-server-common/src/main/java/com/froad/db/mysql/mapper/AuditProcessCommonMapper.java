package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.AuditProcess;

/**
 * 
 * <p>@Title: AuditProcessCommonMapper.java</p>
 * <p>Description: 描述 </p> 审核任务mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年8月17日
 */
public interface AuditProcessCommonMapper {
	
	 /**
     * 新增审核任务表AuditProcess
     * @param auditTask
     * @return Long    主键ID
     */
	public Long addAuditProcess(AuditProcess auditProcess);
    
	
	/**
     * 根据审核流水号auditId查询待审核记录
     * @param auditId  审核流水号
     * @return 
     */
    public List<AuditProcess> findAuditProcessByAuditId(@Param("auditId")String auditId,@Param("auditState")String auditState);
    
    /**
     * 修改审核任务信息
     * @param AuditProcess 
     * @return
     */
    public Boolean updateAuditProcess(AuditProcess auditProcess);
    
    
}