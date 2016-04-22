package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductRestrictRule;

/**
 * @author FQ
 */
public interface ProductMapper
{

    /**
     * 保存
     *
     * @param product
     */
    public Long saveProduct(Product product);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年1月23日 上午11:42:38 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateProductById(Product product);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年1月23日 上午11:47:18 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Product selectProductById(Long id);

    /**
     * @param page <p>分页查询商品</p>
     * @return
     * @author larry
     */
    public List<Product> findProductByPage(Page page);

    /**
     * @param page <p>查询记录条数</p>
     * @return
     * @author larry
     */
    public Integer findProductByPageCount(Page page);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过将productDto 作为条件查询product （* productDto 里面的所有字段属性将成为并列条件用于数据库查询）</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年4月4日 上午9:51:19 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public List<Product> selectProductByCondition(Product product);

    /**
     * 查询所有未限定规则的商品
     *
     * @return 商品
     */
    public List<Product> selectAllUnRestrictProduct(ProductRestrictRule productRestrictRule);

    /**
     * 查询所有已限定规则的商品
     *
     * @return 商品
     */
    public List<Product> selectAllRestrictProduct(ProductRestrictRule productRestrictRule);

    /**
     * 查询所有已有库存商品
     * @return
     */
    public List<Product> selectAllProductHaveStockPile();
}
