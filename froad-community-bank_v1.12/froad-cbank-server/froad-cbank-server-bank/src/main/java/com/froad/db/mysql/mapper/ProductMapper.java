package com.froad.db.mysql.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.po.BankAudit;
import com.froad.po.Product;
import com.froad.po.ProductOutletInfo;

/**
 * 
 * <p>@Title: ProductMapper.java</p>
 * <p>Description: 描述 </p> 商品mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月3日
 */
public interface ProductMapper {
	
	
	 /**
     * 审核 Product
     * @param bankAudit 审核对象
     * @return
     */
    public Boolean auditProduct(BankAudit bankAudit);
	
    /**
     * 根据商品id查询商品对象
     * @param productId
     * @return
     */
    public BankAudit findProductById(String productId);
    
    
    /**
     * 查询待审核商品数量
     * @param clientId 客户端Id
     * @param type 商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)
     * @param auditOrgCode 待审核机构
     * @return 
     */
    public List<Map<String, Object>> findCountByProduct(@Param("clientId")String clientId,@Param("auditOrgCode")String auditOrgCode);
    
    /**
     * 分页查询 Outlet
     * @param page 
     * @param outlet
     * @return List<ProductOutlet>    返回分页查询结果集
     */
    public List<ProductOutletInfo> findOutletAreas(Map<String,Object> param);
    
    /**
     * 更新商品isSeckill标识
     */
    public void updateProductIsSeckill(Product product);
    
}