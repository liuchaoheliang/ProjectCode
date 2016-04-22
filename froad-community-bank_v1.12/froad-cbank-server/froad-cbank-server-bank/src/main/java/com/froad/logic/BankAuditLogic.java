package com.froad.logic;


import java.util.List;

import com.froad.exceptions.FroadServerException;
import com.froad.logic.res.PreAuditNumRes;
import com.froad.po.BankAudit;

/**
 * 
 * <p>@Title: BankAuditLogic.java</p>
 * <p>Description: 描述 </p> 银行审核Logic接口类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月2日
 */
public interface BankAuditLogic {


	/**
     * 批量审核
     * @param auditFlag 审核标志 1-商品 2-商户
     * @param userId 审核人id
     * @param bankAuditList 审核对集合
     * @return String   审核失败的审核对象id及名称(id:name)  审核成功返回OK  审核异常返回ERROR
     */
	public String[] auditBatch(int auditFlag, long userId, List<BankAudit> bankAuditList) throws FroadServerException, Exception;
	
	
	/**
     * 返回待审核数量
     * @param  
     * @param    
     * @return 
     */
	public PreAuditNumRes getPreAuditNumRes(String clientId,String orgCode);

}