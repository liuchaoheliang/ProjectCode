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

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossProductFilter;
import com.froad.po.Product;
import com.froad.po.ProductGroup;
import com.froad.po.ProductOutletInfo;
import com.froad.po.ProductPresell;

/**
 * 
 * <p>@Title: ProductMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProductMapper {
    
    /**
     * 商品管理分页查询
     * @param page
     * @param ProductFilter
     * @return List<ProductListInfo>
     */
    public List<Product> findProductListByPage(Page<Product> page,@Param(value = "p")BossProductFilter filter);


    /**
     * 增加 Product
     * @param product
     * @return
     */
	public void addProduct(Product product);


	/**
     * 增加 ProductGroup
     * @param productGroup
     * @return Long    主键ID
     */
    public void addProductGroup(ProductGroup productGroup);
    
    
    /**
     * 增加 ProductPresell
     * @param productPresell
     * @return
     */
    public void addProductPresell(ProductPresell productPresell);
    
    
    /**
     * 查询门店信息通过id
     * @param clientId
     * @return String
     */
    public ProductOutletInfo findOutletById(Map<String,Object> param);
    
    
    /**
     * 逻辑删除
     * @param product
     * @return
     */
    public void deleteLogicProduct(Product product);
    
    
    /**
     * 查询单独一条商品信息
     * @param prodictId
     * @return Product
     */
    public Product getProductByProductId(String productId);
    
    
    /**
     * 查询 ProductGroup
     * @param prodictId
     * @return ProductPresell
     */
    public ProductPresell getProductPresellByProductId(String productId);
    
    
    /**
     * 查询 ProductGroup
     * @param prodictId
     * @return ProductGroup
     */
    public ProductGroup getProductGroupByProductId(String productId);


    /**
     * 修改 Product
     * @param product
     * @return
     */
    public void updateProduct(Product product);


    /**
     * 修改 ProductGroup
     * @param productGroup
     * @return
     */
    public void updateProductGroup(ProductGroup productGroup);
    
    
    /**
     * 修改 ProductPresell
     * @param productPresell
     * @return
     */
    public void updateProductPresell(ProductPresell productPresell);
    
    
    /**
     * 上下架商品
     * @param product
     * @return
     */
    public void updateProductStatus(Product product);
    
    
    /**
     * 查询秒杀商品
     * @param product
     * @return List<Product>    返回结果集
     */
    public Product getSeckillProduct(Product product);
    
    
    /**
     * 更新Product审核信息
     * @param product
     * @return
     */
    public void updateAuditProduct(Product product);
    
    /**
     * 查询预售商品信息
     * @author liuyanyun
     */
    public List<ProductPresell> getProductPresellByType();
    
    /**
     * 修改商品分类信息
     */
	public void updateProductCategroy(@Param(value = "productId")String productId, @Param(value = "productCategoryId")String productCategoryId);
	
	
	public List<Product> findProductListByCategoryId(@Param("clientId")String clientId, @Param("categoryId")String categoryId);
}