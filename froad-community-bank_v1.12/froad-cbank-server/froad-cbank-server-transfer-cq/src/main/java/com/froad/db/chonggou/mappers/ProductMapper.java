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
package com.froad.db.chonggou.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.ActivitiesInfo;
import com.froad.db.chonggou.entity.BankOrgCode;
import com.froad.db.chonggou.entity.ProductCG;
import com.froad.db.chonggou.entity.ProductFilter;
import com.froad.db.chonggou.entity.ProductGroupCG;
import com.froad.db.chonggou.entity.ProductOutletInfo;
import com.froad.db.chonggou.entity.ProductPresellCG;
import com.froad.db.chonggou.entity.ProductViewInfo;
import com.froad.db.chonggou.entity.SenseWord;
import com.froad.db.mysql.bean.Page;
import com.froad.po.Merchant;
import com.froad.po.Org;


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
	public int addProduct(ProductCG product);



    /**
     * 批量增加 Product
     * @param List<Product>
     * @return
     */
	public void addProductByBatch(List<ProductCG> productList);


	
	/**
	 * 逻辑删除
	 * @param product
	 * @return
	 */
	public void deleteLogicProduct(ProductCG product);



    /**
     * 修改 Product
     * @param product
     * @return
     */
	public void updateProduct(ProductCG product);



    /**
     * 查询 Product
     * @param product
     * @return List<ProductViewInfo>    返回结果集
     */
	public List<ProductViewInfo> getProduct(ProductCG product);
	
	
	
	/**
     * 增加 ProductGroup
     * @param productGroup
     * @return Long    主键ID
     */
    public void addProductGroup(ProductGroupCG productGroup);



    /**
     * 批量增加 ProductGroup
     * @param List<ProductGroup>
     * @return
     */
    public void addProductGroupByBatch(List<ProductGroupCG> productGroupList);



    /**
     * 修改 ProductGroup
     * @param productGroup
     * @return
     */
    public void updateProductGroup(ProductGroupCG productGroup);

    
    
    /**
     * 增加 ProductPresell
     * @param productPresell
     * @return
     */
    public void addProductPresell(ProductPresellCG productPresell);



    /**
     * 批量增加 ProductPresell
     * @param List<ProductPresell>
     * @return
     */
    public void addProductPresellByBatch(List<ProductPresellCG> productPresellList);



    /**
     * 修改 ProductPresell
     * @param productPresell
     * @return
     */
    public void updateProductPresell(ProductPresellCG productPresell);
    
    
    /**
     * 上下架商品
     * @param product
     * @return
     */
    public void updateProductStatus(ProductCG product);
    
    
    /**
     * 更新商品库存冻结库存
     * @param product
     * @return
     */
    public void updateProductStockNum(ProductCG product);
    
    
    /**
     * 更新销售量
     * @param product
     * @return
     */
    public void updateProductSellCount(ProductCG product);
    
    
    /**
     * 查询是否已经存在
     * @param product
     * @return Integer
     */
    public Integer getProductCount(ProductCG product);
    
    
    /**
     * 商品管理分页查询
     * @param page
     * @param ProductFilter
     * @return List<ProductViewInfo>
     */
    public List<ProductViewInfo> findProductListByPage(Page<ProductViewInfo> page,@Param(value = "p")ProductFilter product);
    
    
    /**
     * 查询商户
     * @param MerchantCG
     * @return List<Merchant>
     */
    public List<Merchant> findMerchant(Merchant product);
    
    /**
     * 查询商品分类的树节点
     * @param Long
     * @return String
     */
    public String findProductCategoryTreePath(Long id);
    
    /**
     * 敏感词查询
     * @return List<SenseWord>
     */
    public List<SenseWord> findSenseWords();
    
    /**
     * 修改point
      * @Title: updatePoint
      * @Description: TODO
      * @author: Yaren Liang 2015年4月8日
      * @modify: Yaren Liang 2015年4月8日
      * @param @param product    
      * @return void    
      * @throws
     */
    public void updatePoint(ProductCG product);
    
    /**
     * 更新Product审核信息
     * @param product
     * @return
     */
    public void updateAuditProduct(ProductCG product);
    
    /**
     * 查找机构等级
      * @Title: findOrgCode
      * @Description: TODO
      * @author: Yaren Liang 2015年4月10日
      * @modify: Yaren Liang 2015年4月10日
      * @param @param product    
      * @return void    
      * @throws
     */
//    public BankOrgCode findOrgCode(Product product);
	/**
     * 根据clientId和orgCode查出单个Org
     * @param orgCode 机构编号
     * @return Org对象
     */
	public Org findOrgById(Org org);
	
	/**
	 * 
	  * @Title: findMerchantByMerchantId
	  * @Description: TODO
	  * @author: Yaren Liang 2015年4月11日
	  * @modify: Yaren Liang 2015年4月11日
	  * @param @param merchantId
	  * @param @return    
	  * @return Merchant    
	  * @throws
	 */
	public Merchant findMerchantByMerchantId(Merchant merchant);
	
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
	  * @Description: TODO
	  * @author: Yaren Liang 2015年4月16日
	  * @modify: Yaren Liang 2015年4月16日
	  * @param @param products    
	  * @return void    
	  * @throws
	 */
	public void updateAuditProductBatch(ProductCG product);
	
	
	/**
     * 增加 Activities
     * @param activities
     * @return Long    主键ID
     */
    public void addActivities(ActivitiesInfo activities);


    /**
     * 查询一个 Activities
     * @param activities
     * @return ActivitiesInfo    返回结果
     */
    public List<ActivitiesInfo> findActivities(ActivitiesInfo activities);
    
    /**
     * 查询多个 Activities
     * @param activities
     * @return ActivitiesInfo    返回结果
     */
    public List<ActivitiesInfo> getActivities(Map<String,Object> param);
    
    /**
     * 获取商品类型
      * @Title: getProductType
      * @Description: TODO
      * @author: Yaren Liang 2015年4月22日
      * @modify: Yaren Liang 2015年4月22日
      * @param @param product
      * @param @return    
      * @return Product    
      * @throws
     */
    public ProductCG getProductType(ProductCG product);
    
    /**
     * 查询机构
     * @param param
     * @return List<BankOrgCode>
     */
    public List<BankOrgCode> findOrgs(Map<String,Object> param);
    /**
     * 
      * @Title: getProductById
      * @Description: TODO
      * @author: Yaren Liang 2015年5月3日
      * @modify: Yaren Liang 2015年5月3日
      * @param @param productCG
      * @param @return    
      * @return ProductCG    
      * @throws
     */
    public ProductCG getProductById(ProductCG productCG);
    /**
     * 
      * @Title: getProductByProductId
      * @Description: TODO
      * @author: Yaren Liang 2015年5月3日
      * @modify: Yaren Liang 2015年5月3日
      * @param @param product
      * @param @return    
      * @return List<ProductCG>    
      * @throws
     */
    public List<ProductCG> getProductByProductId(ProductCG product);
    /**
     * 
      * @Title: deleteProduct
      * @Description: TODO
      * @author: Yaren Liang 2015年6月29日
      * @modify: Yaren Liang 2015年6月29日
      * @param     
      * @return void    
      * @throws
     */
    public Integer deleteProduct(String id);
    /**
     * 
      * @Title: deleteProductPresell
      * @Description: TODO
      * @author: Yaren Liang 2015年6月29日
      * @modify: Yaren Liang 2015年6月29日
      * @param @param id    
      * @return void    
      * @throws
     */
    public Integer deleteProductPresell(String id);
    /**
     * 
      * @Title: deleteProductGroup
      * @Description: TODO
      * @author: Yaren Liang 2015年6月29日
      * @modify: Yaren Liang 2015年6月29日
      * @param @param id    
      * @return void    
      * @throws
     */
    public Integer deleteProductGroup(String id);
    
}