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
 * @Title: ProductSeckillLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.db.mysql.bean.Page;
import com.froad.po.ProductSeckill;

/**
 * 
 * <p>@Title: ProductSeckillLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProductSeckillLogic {


    /**
     * 增加 ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	public ResultBean addProductSeckill(List<ProductSeckill> productSeckillInfo);



    /**
     * 删除 ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	public ResultBean deleteProductSeckill(ProductSeckill productSeckill);



    /**
     * 修改 ProductSeckill
     * @param productSeckill
     * @return ResultBean    是否成功
     */
	public ResultBean updateProductSeckill(ProductSeckill productSeckill);

    /**
     * 修改上下架状态 ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	public ResultBean updateProductSeckillStatus(ProductSeckill productSeckill);


    /**
     * 查询 ProductSeckill
     * @param productSeckill
     * @return ProductSeckill    结果集合 
     */
	public ProductSeckill getProductSeckillDetail(ProductSeckill productSeckill);



    /**
     * 查询 ProductSeckill
     * @param productSeckill
     * @return List<ProductSeckill>    结果集合 
     */
    public List<ProductSeckill> findSeckillByPage(Page<ProductSeckill> page,ProductSeckill productSeckill);
    
    /**
     * 查询详情H5 ProductSeckill
     * @param productSeckill
     * @return ProductSeckill    结果集合 
     */
	public ProductSeckill getH5ProductSeckillDetail(ProductSeckill productSeckill);



    /**
     * 分页查询H5 ProductSeckill
     * @param productSeckill
     * @return List<ProductSeckill>    结果集合 
     */
    public List<ProductSeckill> findH5SeckillByPage(Page<ProductSeckill> page,ProductSeckill productSeckill);
    
}