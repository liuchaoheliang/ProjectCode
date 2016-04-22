package com.froad.logic;

import java.util.List;

import com.froad.po.MerchantAccount;
import com.froad.po.MerchantTemp;
import com.froad.po.Result;
import com.froad.thrift.vo.OriginVo;

public interface MerchantAuditLogic {

	/**
	 * 商户审核通过在审核
	 * @param originVo
	 * @param mTemp MerchantTemp 对象
	 * @return
	 * @throws Exception 
	 */
	public boolean auditMerchant(OriginVo originVo,MerchantTemp mTemp) throws Exception;
	
	public Result auditMerchantEdit(String auditId) throws Exception ;
	
	
	public MerchantTemp findAuditMerchantByAuditId(String auditId) throws Exception;
	
	public List<MerchantTemp> findMerchantTemp(MerchantTemp merchantTemp) throws Exception;

}
