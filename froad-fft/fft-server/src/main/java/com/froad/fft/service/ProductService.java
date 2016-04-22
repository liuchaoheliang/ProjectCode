package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductRestrictRule;

/**
 * 商品
 *
 * @author FQ
 */
public interface ProductService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存Product并返回数据主键（如果主键为null则插入失败）</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 下午5:42:15 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveProduct(Product product);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>方法说明</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年1月23日 上午11:41:06 </p>
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
    public Page findProductByPage(Page page);

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
     * 查询所有未绑定规则的商品
     *
     * @param productRestrictRule 查询规则类型
     * @return
     */
    public List<Product> findAllUnRestrictProduct(ProductRestrictRule productRestrictRule);

    /**
     * 查询所有已绑定规则的商品
     *
     * @param productRestrictRule 查询规则类型
     * @return
     */
    public List<Product> findAllRestrictProduct(ProductRestrictRule productRestrictRule);

    /**
     * 查询所有已有库存商品
     * @return
     */
    public List<Product> findAllProductHaveStockPile();

}
