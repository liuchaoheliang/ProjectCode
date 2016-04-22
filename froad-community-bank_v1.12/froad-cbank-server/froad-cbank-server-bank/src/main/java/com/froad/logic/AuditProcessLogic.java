package com.froad.logic;


import java.util.List;

import com.froad.po.AuditProcess;

/**
 * 
 * <p>@Title: AuditProcessLogic.java</p>
 * <p>Description: 描述 </p> 审核任务Logic接口类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年8月18日
 */
public interface AuditProcessLogic {


	/**
     * 查询审核任务列表
     * @param auditId 审核流水号
     * @return List<AuditProcessVo>
     */
	 public List<AuditProcess> findAuditProcess(String auditId);


}