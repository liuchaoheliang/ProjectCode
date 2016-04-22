package com.froad.db.mysql.mapper;

import java.util.List;

import com.froad.po.MerchantTemp;

public interface MerchantTempMapper {

	/**
	 * 商户审核通过在审核
	 * @param meTemp
	 * @return
	 */
	public Long auditMerchant(MerchantTemp meTemp) ;
	
	/**
     * 修改 MerchantTemp
     * @param merchant
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantTemp(MerchantTemp meTemp);
	
	/**
     * 物理删除 MerchantTemp
     * @param merchantId 
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantTemp(String merchantId);
	
	/**
	 * 根据审核流水号查询最近一条记录
	 * @param auditId
	 * @return
	 */
	public MerchantTemp findMerchantTempByAuditId(String auditId);
	
	
	/**
	 * 根据商户Id查询最近一条变更记录
	 * @param merchantId 
	 * @return
	 */
	public MerchantTemp findMerchantTempByMerchantId(String merchantId);
	
	
	/**
	 * 查询List
	 * @param merchantTemp
	 * @return
	 */
	public List<MerchantTemp> findMerchantTemp(MerchantTemp merchantTemp);
}
