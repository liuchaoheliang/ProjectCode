package com.froad.logic;

import com.froad.po.AuditProcess;
import com.froad.po.AuditTask;

public interface MerchantAduitCommonLogic {

	
	
	/**
     * 根据审核流水号auditId查询待审核任务记录
     * @param auditId 审核流水号
     * @author ll 20150817 add
     * @return AuditProcess 审核任务对象
     */
	public AuditProcess getAuditProcessByAuditId(String auditId) throws  Exception;
	
	/**
     * 根据商户id获得商户待审核任务订单信息
     * @param thridId  type=1时存储商户Id
     * @author ll 20150817 add
     * @return AuditTask 审核任务订单对象
     */
    public AuditTask getAuditTaskByMerchantId(String thridId) throws  Exception;
    
	/**
     * 根据审核流水号获得商户待审核任务订单信息
     * @param auditId  
     * @return AuditTask 审核任务订单对象
     */
    public AuditTask getAuditTaskByAuditId(String auditId) throws  Exception;
	
    
    
}
