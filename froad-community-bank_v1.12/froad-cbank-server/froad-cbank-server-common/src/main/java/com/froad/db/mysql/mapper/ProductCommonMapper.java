/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: ProductMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.po.Merchant;
import com.froad.po.Product;
import com.froad.po.ProductGroup;
import com.froad.po.ProductOutletInfo;
import com.froad.po.ProductPresell;
import com.froad.po.ProductSeckill;
import com.froad.po.VipProduct;


/**
 * 
 * <p>@Title: ProductMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProductCommonMapper {
    
    /**
     * 查询 Product
     * @param product
     * @return List<Product>    返回结果集
     */
    public List<Product> getProductByPid(Map<String,String> param);
    
    /**
     * 查询 ProductGroup
     * @param product
     * @return List<ProductGroup>    返回结果集
     */
    public List<ProductGroup> getProductGroupByPid(Map<String,String> param);
    
    /**
     * 查询 ProductPresell
     * @param product
     * @return List<ProductPresell>    返回结果集
     */
    public List<ProductPresell> getProductPresellByPid(Map<String,String> param);
    
    /**
     * 查询  Product的库存
     * @param product
     * @return List<ProductPresell>    返回结果集
     */
    public List<Product> getProductStoreByPid(Map<String,String> param);

    /**
     * 查询  ProductPresell的最大门店提货数 
     * @param product
     * @return List<ProductPresell>    返回结果集
     */
    public List<ProductPresell> getProductMaxPerOutletByPid(Map<String,String> param);

	/**
	 * 查询  Product Seckill 的库存
	 * @param param
	 * @return List<ProductSeckill>  返回结果集
	 */
	public List<ProductSeckill> getSeckillProductStoreByPid(Map<String, String> param);

	/**
	 * 根据商户id修改商户名称
	 * @Title: updateMerchantNameByMerchantId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @param merchantName
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
    public Boolean updateMerchantNameByMerchantId(@Param("merchantId")String merchantId, @Param("merchantName")String merchantName);
    
    /**
     * 根据客户端id查询单独一条未删除的vip规则
     * @param clientId
     * @return VipProduct
     */
    public VipProduct getVipProductByClientId(String clientId);
    
    /**
	 * 根据商品id查询Product对象
	 * @param productId
	 * @return
	 */
	public Product findProductByProductId(String productId);
	
	public List<Product> findProductByClientId(String clientId);
	
	
    
    
}