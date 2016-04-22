package com.froad.db.ahui.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.ahui.entity.ProductGroup;
import com.froad.db.ahui.entity.ProductPresell;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductExpand;
import com.froad.fft.persistent.entity.ProductFamous;




/**
 * 商品接口
 * 
 * @author dongrui
 * @version $Id: ProductMapper.java, v 0.1 2014年12月23日 下午4:28:26 Exp $
 */
public interface ProductMapper {

    /**
     * 添加商品
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:30:11 Exp $
     * @param Product
     * @return
     */
    public Long insertProduct(Product Product);

    /**
     * 按ID更新商品
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:30:03 Exp $
     * @param Product
     * @return
     */
    public Boolean updateProductById(Product Product);

    /**
     * 按ID查询商品
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:30:39 Exp $
     * @param id
     * @return
     */
  //  public Product selectProductById(Long id);

    /**
     * 按条件查询商品信息
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:33:56 Exp $
     * @param Product
     * @return
     */
    public List<Product> selectProductByCondition(Product Product);

    /**
     * 更新商品库存数量（增加count为正数，减少count为负数）
     * @author dongrui
     * @version $Id: 2014年12月30日 上午11:28:38 Exp $
     * @param clientAccessType
     * @param clientVersion
     * @param ProductId
     * @param count
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Boolean modifyProductStoreCount(Map dataMap);

    /**
     * 按产品ID删除团购商品详情
     * @author dongrui
     * @version $Id: 2014年12月31日 下午6:35:24 Exp $
     * @param clientAccessType
     * @param clientVersion
     * @param ProductId
     * @return
     */
    public Boolean deleteProductByProductId(Long ProductId);

    /**
     * 查询商品信息-根据商户门店IdList
     * @author luofan
     * @version $Id: 2015年01月08日 18:38:24 Exp $
     * @param clientAccessType
     * @param clientVersion
     * @param List<Long> merchantOutletIdList
     * @return List<Product>
     * */
    public List<Product> selectProductByMerchantOutletIdList(List<Long> merchantOutletIdList);
  
    /**
     * 根据 clientId 查询未被绑定的团购商品
     * @author luofan
     * @version $Id: 2015年01月15日 18:38:24 Exp $
     * @param Long clientId
     * @return List<Product>
     * */
    public List<Product> selectNotGroupProductByClientId(Long clientId);
    
    /**
     * 获取安徽所有商品数据
      * @Title: findAllProductInAnhui
      * @Description: TODO
      * @author: Yaren Liang 2015年5月1日
      * @modify: Yaren Liang 2015年5月1日
      * @param @return    
      * @return List<Product>    
      * @throws
     */
    public List<Product> selectAllProductInAnhui();
    /**
     * 根据id查找表
      * @Title: findProductFamous
      * @Description: TODO
      * @author: Yaren Liang 2015年5月1日
      * @modify: Yaren Liang 2015年5月1日
      * @param     
      * @return void    
      * @throws
     */
    public ProductFamous selectProductFamousById(Long id);
    /**
     * 根据id查找表
      * @Title: selectProductPresellById
      * @Description: TODO
      * @author: Yaren Liang 2015年5月1日
      * @modify: Yaren Liang 2015年5月1日
      * @param @param id
      * @param @return    
      * @return ProductPresell    
      * @throws
     */
    public ProductPresell selectProductPresellById(Long id);
    /**
     * 根据id查找表
      * @Title: selectProductGroupById
      * @Description: TODO
      * @author: Yaren Liang 2015年5月1日
      * @modify: Yaren Liang 2015年5月1日
      * @param @param id
      * @param @return    
      * @return ProductGroup    
      * @throws
     */
    public ProductGroup selectProductGroupById(Long id);
    
    /**
     *  按产品ID查询预售商品
      * @Title: selectPresellProductById
      * @Description: TODO
      * @author: share 2015年5月3日
      * @modify: share 2015年5月3日
      * @param @param productId
      * @param @return    
      * @return ProductPresell    
      * @throws
     */
    public ProductPresell selectPresellProductById(@Param("id")Long productId);
    /**
     * 
      * @Title: selectProductById
      * @Description: TODO
      * @author: Yaren Liang 2015年5月3日
      * @modify: Yaren Liang 2015年5月3日
      * @param @param product
      * @param @return    
      * @return Product    
      * @throws
     */
    public Product selectMerchantByProductId(Long id);
    
    /**
     *  团购商品
      * @Title: selectById
      * @Description: TODO
      * @author: share 2015年5月4日
      * @modify: share 2015年5月4日
      * @param @param id
      * @param @return    
      * @return Product    
      * @throws
     */
    public ProductGroup selectGroupByProductId(@Param("id")Long id);
    
    /**
     *  名优特惠商品
      * @Title: selectFamousByProductId
      * @Description: TODO
      * @author: share 2015年5月4日
      * @modify: share 2015年5月4日
      * @param @param id
      * @param @return    
      * @return Product    
      * @throws
     */
    public ProductFamous selectFamousByProductId(@Param("id")Long id);
    
    /**
     *  查询商品缩略图
      * @Title: selectProdcutImageById
      * @Description: TODO
      * @author: share 2015年5月6日
      * @modify: share 2015年5月6日
      * @param @param id
      * @param @return    
      * @return Map<String,String>    
      * @throws
     */
    public Map<String,String> selectProdcutImageById(@Param("id")Long id);
    /**
     * 
      * @Title: selectExchangeByProductId
      * @Description: TODO
      * @author: Yaren Liang 2015年5月12日
      * @modify: Yaren Liang 2015年5月12日
      * @param @param id
      * @param @return    
      * @return ProductExpand    
      * @throws
     */
    public ProductExpand selectExchangeByProductId(@Param("id")Long id);
}
