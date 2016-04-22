package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.ProductImage;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 * 商品图片
 * 
 * @author dongrui
 * @version $Id: ProductImageMapper.java, v 0.1 2014年12月23日 下午4:46:11 Exp $
 */
public interface ProductImageMapper {

    /**
     * 增加商品图片
     * 
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:46:08 Exp $
     * @param productImage
     * @return
     */
    public Long insertProductImage(ProductImage productImage);

    /**
     * 按ID更新商品图片信息
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:46:51 Exp $
     * @param productImage
     * @return
     */
    public Boolean updateProductImageById(ProductImage productImage);

    /**
     * 按ID查询商品图片信息
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:47:59 Exp $
     * @param id
     * @return
     */
    public ProductImage selectProductImageById(Long id);

    /**
     * 分页查询
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:48:13 Exp $
     * @param productImage
     * @return
     */
    public List<ProductImage> selectProductImageOfPage(PageEntity<ProductImage> pageEntity);

    /**
     * 按条件查询商品图片信息
     * @author dongrui
     * @version $Id: 2014年12月23日 下午4:48:13 Exp $
     * @param productImage
     * @return
     */
    public List<ProductImage> selectProductImageByCondition(ProductImage productImage);

    /**
     * 按图片ID删除商品图片
     * @author dongrui
     * @version $Id: 2014年12月29日 上午9:46:48 Exp $
     * @param Id
     * @return
     */
    public Boolean deleteProductImageById(Long Id);

    /**
     * 按产品ID删除商品图片
     * @author dongrui
     * @version $Id: 2014年12月29日 上午9:46:48 Exp $
     * @param Id
     * @return
     */
    public Boolean deleteProductImageByProductId(Long productId);

    /**
     * 按产品ID查询产品图片明细
     * @author dongrui
     * @version $Id: 2014年12月29日 下午2:03:23 Exp $
     * @param productId
     * @return
     */
    public List<ProductImage> selectProductImageByProductId(Long productId);

}
