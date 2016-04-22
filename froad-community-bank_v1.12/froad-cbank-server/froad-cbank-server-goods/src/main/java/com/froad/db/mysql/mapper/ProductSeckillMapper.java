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
 * @Title: ProductSeckillMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Product;
import com.froad.po.ProductSeckill;

/**
 * 
 * <p>@Title: ProductSeckillMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProductSeckillMapper {


    /**
     * 增加 ProductSeckill
     * @param productSeckill
     * @return Long    主键ID
     */
	public void addProductSeckill(ProductSeckill productSeckill);



	/**
	 * 逻辑删除 ProductSeckill
	 * @param productSeckill
	 * @return void
	 */
	public void deleteProductSeckill(ProductSeckill productSeckill);



    /**
     * 修改 ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	public void updateProductSeckill(ProductSeckill productSeckill);

    /**
     * 修改 上下架状态ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	public void updateProductSeckillStatus(ProductSeckill productSeckill);
	
    /**
     * 查询详情ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	public ProductSeckill getProductSeckillById(ProductSeckill productSeckill);
	
	/**
	 * 分页查询
	 * @param page
	 * @param productSeckill
	 * @return List<ProductSeckill>
	 */
	public List<ProductSeckill> findSeckillByPage(Page<ProductSeckill> page,@Param(value = "p")ProductSeckill productSeckill);

	
    /**
     * 修改商品库存
     * @param product
     * @return Boolean    是否成功
     */
	public boolean updateProductStore(Product product);
	
	/**
	 * H5分页查询
	 * @param page
	 * @param productSeckill
	 * @return List<ProductSeckill>
	 */
	public List<ProductSeckill> findH5SeckillByPage(Page<ProductSeckill> page,@Param(value = "p")ProductSeckill productSeckill);

	
	
}