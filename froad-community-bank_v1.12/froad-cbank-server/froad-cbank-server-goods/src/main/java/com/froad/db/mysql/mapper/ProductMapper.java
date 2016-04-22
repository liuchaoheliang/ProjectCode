/**
 * 
 * @Title: ProductMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see:  
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActivitiesInfo;
import com.froad.po.Area;
import com.froad.po.BankOrgCode;
import com.froad.po.Product;
import com.froad.po.ProductFilter;
import com.froad.po.ProductGroup;
import com.froad.po.ProductListInfo;
import com.froad.po.ProductOutletInfo;
import com.froad.po.ProductPresell;
import com.froad.po.ProductTemp;
import com.froad.po.ProductViewInfo;
import com.froad.po.SenseWord;

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
     * 增加 Product
     * @param product
     * @return
     */
	public void addProduct(Product product);



    /**
     * 批量增加 Product
     * @param List<Product>
     * @return
     */
	public void addProductByBatch(List<Product> productList);


	
	/**
	 * 逻辑删除
	 * @param product
	 * @return
	 */
	public void deleteLogicProduct(Product product);



    /**
     * 修改 Product
     * @param product
     * @return
     */
	public void updateProduct(Product product);



    /**
     * 查询 Product
     * @param product
     * @return List<ProductViewInfo>    返回结果集
     */
	public List<ProductViewInfo> getProduct(Product product);
	
	
    /**
     * 查询秒杀商品
     * @param product
     * @return List<Product>    返回结果集
     */
    public List<Product> getSeckillProduct(Product product);
	
	
	
	/**
     * 增加 ProductGroup
     * @param productGroup
     * @return Long    主键ID
     */
    public void addProductGroup(ProductGroup productGroup);



    /**
     * 批量增加 ProductGroup
     * @param List<ProductGroup>
     * @return
     */
    public void addProductGroupByBatch(List<ProductGroup> productGroupList);



    /**
     * 修改 ProductGroup
     * @param productGroup
     * @return
     */
    public void updateProductGroup(ProductGroup productGroup);

    
    
    /**
     * 增加 ProductPresell
     * @param productPresell
     * @return
     */
    public void addProductPresell(ProductPresell productPresell);



    /**
     * 批量增加 ProductPresell
     * @param List<ProductPresell>
     * @return
     */
    public void addProductPresellByBatch(List<ProductPresell> productPresellList);



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
     * 设置商品秒杀标识
     * @param product
     * @return
     */
    public void updateProductIsSeckill(Product product);
    
    /**
     * 查询是否已经存在
     * @param product
     * @return Integer
     */
    public Integer getProductCount(Product product);
    
    
    /**
     * 商品管理分页查询
     * @param page
     * @param ProductFilter
     * @return List<ProductListInfo>
     */
    public List<ProductListInfo> findProductListByPage(Page<ProductListInfo> page,@Param(value = "p")ProductFilter product,@Param(value = "param")Map<String,List<String>> param);
    
    
    /**
     * 敏感词查询
     * @return List<SenseWord>
     */
    public List<SenseWord> findSenseWords();
    
    
    /**
     * 修改point
      * @Title: updatePoint
      * @Description: 
      * @author: Yaren Liang 2015年4月8日
      * @modify: Yaren Liang 2015年4月8日
      * @param @param product    
      * @return void    
      * @throws
     */
    public void updatePoint(Product product);
    
    /**
     * 更新Product审核信息
     * @param product
     * @return
     */
    public void updateAuditProduct(Product product);
    
	
	/**
     * 分页查询 Outlet
     * @param page 
     * @param outlet
     * @return List<ProductOutlet>    返回分页查询结果集
     */
    public List<ProductOutletInfo> findOutlets(Map<String,Object> param);
    
	/**
	  * 批量审核商品
	  * @Title: updateAuditProductBatch
	  * @Description: 
	  * @author: Yaren Liang 2015年4月16日
	  * @modify: Yaren Liang 2015年4月16日
	  * @param @param products    
	  * @return void    
	  * @throws
	 */
	public void updateAuditProductBatch(Product product);

	/**
	 * 查询赠送积分活动 
	 * @param param
	 * @return List<ActivitiesInfo>
	 */
	public List<ActivitiesInfo> getPointActivities(Map<String,Object> param);
    /**
     * 查询一个 Activities
     * @param id
     * @return ActivitiesInfo
     */
    public ActivitiesInfo findPoints(Long id);
    
    /**
     * 更新该条送积分活动已经绑定的商品数量
     * @param param
     * @return void
     */
    public void updatePointsCount(Map<String,Object> param);
    
    /**
     * 重置该条送积分活动已经绑定的商品数量
     * @param param
     * @return void
     */
    public void resetPointsCount(Map<String,Object> param);
    
    /**
     * 更新mysql的is_point 字段值
     * @param param
     * @return void
     */
    public void updateProductIsPoint(Product p);
    
    /**
     * 查询机构
     * @param param
     * @return List<BankOrgCode>
     */
    public List<BankOrgCode> findOrgs(Map<String,Object> param);
    
    /**
     * 查询 Product
     * @param product
     * @return List<Product>    返回结果集
     */
    public List<Product> getProductByProductId(Product product);
    
    /**
     * 查询 ProductGroup
     * @param product
     * @return List<ProductGroup>    返回结果集
     */
    public List<ProductGroup> getProductGroupByProductId(Product product);
    
    /**
     * 查询 ProductPresell
     * @param product
     * @return List<ProductPresell>    返回结果集
     */
    public List<ProductPresell> getProductPresellByProductId(Product product);
    
    /**
     * 更新商品状态
     * @param param
     * @return
     */
    public void updateProductsStatus(Map<String,Object> param);
    
    /**
     * 查询 Product
     * @param param
     * @return List<Product>    返回结果集
     */
    public List<Product> getProducts(Map<String,Object> param);
    
    /**
     * 批量删除商品
     * @param param
     * @return
     */
    public void deleteLogicProducts(Map<String,Object> param);
    
    /**
     * 修改 ProductGroup
     * @param productGroup
     * @return
     */
    public void updateProductGroupBase(Map<String,Object> param);

    /**
     * 修改 ProductPresell
     * @param productPresell
     * @return
     */
    public void updateProductPresellBase(Map<String,Object> param);
    
    /**
     * 查询  Area
     * @param areaCode
     * @return
     * @return Area
     */
    public Area findAreaByCode(Map<String,String> param);
    
    /**
     * 更新商品审核状态
     * @param param
     * @return
     */
    public void updateProductAuditState(Map<String,Object> param);
    
    /**
     * 查询银行积分兑换比例
     * @param clientId
     * @return String
     */
    public String findPointRate(String clientId);
    /**
     * 查询门店信息通过id
     * @param clientId
     * @return String
     */
    public ProductOutletInfo findOutletById(Map<String,Object> param);
    /**
     * 更改商品二维码信息
     * @param param
     * @return
     */
    public void updateProductCodeUrl(Map<String,Object> param);
    
	/**
	 * 通过ids查询所属的商品信息
	 * @param param
	 * @return List<Product>
	 */
	public List<Product> findProductListByIds(Map<String,Object> param);

	/**
	 * 销售期已到期自动下架 的商品
	 * @param param
	 * @return
	 */
	public List<ProductListInfo> findSaleEndProductList(Map<String,Object> param);
	
	/**
	 * 销售期已到期自动下架
	 * @param param
	 */
	public void updateSaleEndProductStatus(Map<String,Object> param);
	
	 /**
     * 审核 Product
     * @param Product 审核对象
     * @return
     */
    public Boolean auditProduct(Product product);
    

    /**
     * 商品所属商户id查询
     * @param param
     * @return
     */
    public List<String> findProductMerchantIds(Map<String,Object> param);
    

    /**
     * 增加精品商城商品
     * @param product
     * @return
     */
	public void addGoods(Product product);
	
	/**
     * 修改精品商城商品
     * @param product
     * @return
     */
	public void updateGoods(Product product);
	
	/**
     * 上下架精品商城商品 
     * @param product
     * @return
     */
    public void updateGoodsStatus(Product product);
    
    /**
     * boss查询精品商城商品列表分页查询
     * @param page
     * @param product
     * @return
     */
    public List<Product> findGoodsListByPage(Page<ProductListInfo> page,@Param(value = "p")ProductFilter product);
    
    /**
     * boss查询精品商城商品详情
     * @param productId
     * @return
     */
    public Product getProductDetail(String productId);
    
    /**
     * 增加商品的临时表中
     * @param ProductTemp
     * @return
     */
	public void addTempProduct(ProductTemp productTemp);
	
	/**
	 * 查询商品临时表
	 * @param productId
	 * @return
	 */
	public ProductTemp getAuditingTempProductByProductId(String productId);
	
	/**
	 * 查询商品临时表审核状态
	 * @param productIds
	 * @return
	 */
	public List<ProductTemp> getTempProductByProductIds(@Param("productIds")List<String> productIds);
	
	/**
	 * 删除临时表
	 * @param product
	 * @return
	 */
	public void deleteTempProduct(String productId);
	
	/**
	 * 更新商品审核状态 只能是未提交和待审核
	 * @param param
	 */
	public void updateProductAuditStatus(Map<String,Object> param);
	
	/**
	 * 更新临时表商品审核状态 只能是未提交和待审核
	 * @param param
	 */
	public void updateTempProductAuditStatus(Map<String,Object> param);
	
	/**
     * 查询 Product
     * @param productId
     * @return Product
     */
    public Product findProduct(String productId);
    
    /**
     * 查询 ProductGroup
     * @param productId
     * @return ProductGroup
     */
    public ProductGroup findGroupProduct(String productId);
    
    /**
     * 查询 ProductPresell
     * @param productId
     * @return ProductPresell
     */
    public ProductPresell findPresellProduct(String productId);
    
    
    /**
     * 商品管理分页查询
     * @param page
     * @param ProductFilter
     * @return List<ProductListInfo>
     */
    public List<ProductListInfo> findProductsByPage(Page<ProductListInfo> page,@Param(value = "p")ProductFilter product,@Param(value = "param")Map<String,List<String>> param);
    
    /**
     * 查询正在审核的商品信息
     * @param product
     * @return
     */
    public Product getAuditingProductById(Product product);
    
    /**
     * 查询正在审核的临时表中商品信息
     * @param product
     * @return
     */
    public ProductTemp getAuditingTempProductById(Product product);
    
    
    /**
     * 将临时表中的信息同步到Product主表中
     * @param productTemp
     */
    public void updateAuditPassProduct(ProductTemp productTemp);
    
    /**
     * 将临时表中的信息同步到ProductGroup主表中
     * @param productTemp
     */
    public void updateAuditPassProductGroup(ProductTemp productTemp);
    
    /**
     * 将临时表中的信息同步到ProductPresell主表中
     * @param productTemp
     */
    public void updateAuditPassProductPresell(ProductTemp productTemp);
    
    /**
     * 将审核信息更新到临时表中
     * @param productTemp
     */
    public void updateTempProduct(ProductTemp productTemp);
    
    /**
     * 将审核信息更新到product主表中
     * @param productTemp
     */
    public void updateAuditingProduct(ProductTemp productTemp);
    
    /**
     * 修改了不需要审核字段值更新到Product主表中
     * @param productTemp
     */
    public void updateNoNeedAuditProduct(ProductTemp productTemp);
    
    /**
     * 修改了不需要审核字段值更新到ProductGroup主表中
     * @param productTemp
     */
    public void updateNoNeedAuditProductGroup(ProductTemp productTemp);
    
    /**
     * 修改了不需要审核字段值更新到临时表中
     * @param productTemp
     */
    public void updateNoNeedAuditTempProduct(ProductTemp productTemp);
    
    /**
     * 更新商品的子审核状态
     * @param auditState
     */
    public void updateProductEditAuditStatus(Map<String,String> param);
    
}