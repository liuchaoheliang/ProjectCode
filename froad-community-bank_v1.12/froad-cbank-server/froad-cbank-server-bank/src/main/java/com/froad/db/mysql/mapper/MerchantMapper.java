package com.froad.db.mysql.mapper;

import org.apache.ibatis.annotations.Param;

import com.froad.po.BankAudit;

/**
 * 
 * <p>@Title: MerchantMapper.java</p>
 * <p>Description: 描述 </p> 商户mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月2日
 */
public interface MerchantMapper {
	
	 /**
     * 审核 商户Merchant
     * @param merchant
     * @return
     */
    public Boolean auditMerchant(BankAudit bankAudit);
    
    /**
     * 根据商户id查询商户对象
     * @param merchantId
     * @return 
     */
    public BankAudit findMerchantById(String merchantId);
    
    
    
    /**
     * 查询待审核商户数量
     * @param clientId 客户端Id
     * @param auditOrgCode 待审核机构
     * @return 
     */
    public Integer findCountByMerchant(@Param("clientId")String clientId,@Param("auditOrgCode")String auditOrgCode);

    
    
}