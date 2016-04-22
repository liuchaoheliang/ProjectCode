package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.AuditTask;

/**
 * 
 * <p>@Title: AuditTaskCommonMapper.java</p>
 * <p>Description: 描述 </p> 审核任务订单mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年8月17日
 */
public interface AuditTaskCommonMapper {
	
	 /**
     * 新增审核任务订单AuditTask
     * @param auditTask
     * @return Long    主键ID
     */
	public Long addAuditTask(AuditTask auditTask);
	
	 /**
     * 修改审核任务订单信息
     * @param auditTask
     * @return
     */
    public Boolean updateAuditTask(AuditTask auditTask);
    
    /**
     * 根据商户id获得商户审核任务订单信息
     * @param thridId  type=1时存储商户Id
     * @return 
     */
    public AuditTask findAuditTaskByMerchantId(String thridId);
    
    
    /**
     * 根据审核流水号获得商户审核任务订单信息
     * @param auditId  审核流水号
     * @return 
     */
    public AuditTask findAuditTaskByAuditId(String auditId);
    

    /**
     * 分页查询 审核任务订单表
     * @param page 
     * @param auditTask
     * @param flag 查询权限标志 1-查当前机构 2-查当前机构所有下属机构
     * @return List<AuditTask>    返回分页查询结果集
     */
	public List<AuditTask> findAuditTaskByPage(Page page, @Param("auditTask")AuditTask auditTask,@Param("flag") int flag);

    
    
}