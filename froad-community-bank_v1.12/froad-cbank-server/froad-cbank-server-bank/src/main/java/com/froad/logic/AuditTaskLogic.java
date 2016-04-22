package com.froad.logic;


import com.froad.db.mysql.bean.Page;
import com.froad.po.AuditTask;

/**
 * 
 * <p>@Title: AuditTaskLogic.java</p>
 * <p>Description: 描述 </p> 审核任务订单Logic接口类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年8月18日
 */
public interface AuditTaskLogic {


	/**
     * 分页查询 ClientProductAudit
     * @param page 分页对象
     * @param auditTaskFilter 过滤对象
     * @param flag 查询权限标志 1-查当前机构 2-查当前机构所有下属机构
     * @return Page<AuditTask> 
     */
	public Page<AuditTask> findAuditTaskByPage(Page<AuditTask> page, AuditTask auditTaskFilter,int flag);

}