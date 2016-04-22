package com.froad.db.mysql.mapper;

import org.apache.ibatis.annotations.Param;

import com.froad.po.BankAudit;

/**
 * 
 * <p>@Title: ProductMapper.java</p>
 * <p>Description: 描述 </p> 商品mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月3日
 */
public interface ProductSeckillMapper {
	
	
	 /**
     * 审核 ProductSeckill
     * @param bankAudit 审核对象
     * @return
     */
    public Boolean auditProductSeckill(BankAudit bankAudit);
	
    /**
     * 根据商品id查询商品对象
     * @param productId
     * @return
     */
    public BankAudit findProductSeckillById(String productId);
    
    
    /**
     * 查询待审核商品数量
     * @param clientId 客户端Id
     * @param auditOrgCode 待审核机构
     * @return 
     */
    public Integer findCountByProductSeckill(@Param("clientId")String clientId,@Param("auditOrgCode")String auditOrgCode);
    
    
    
}