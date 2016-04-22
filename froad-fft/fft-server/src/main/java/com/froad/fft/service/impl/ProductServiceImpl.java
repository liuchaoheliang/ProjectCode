package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.entity.ProductRestrictRule;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.ProductMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.service.ProductService;
import com.froad.fft.util.SerialNumberUtil;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService
{

    private static Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Resource
    private ProductMapper productMapper;

    @Override
    public Long saveProduct(Product product)
    {
        //获得商品编号
        product.setSn(SerialNumberUtil.buildProductSn());
        return productMapper.saveProduct(product);
    }

    @Override
    public Boolean updateProductById(Product product)
    {
        if (product.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return productMapper.updateProductById(product);
    }

    @Override
    public Product selectProductById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return productMapper.selectProductById(id);
    }

    @Override
    public Page findProductByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(productMapper.findProductByPage(page));
        page.setTotalCount(productMapper.findProductByPageCount(page));
        return page;
    }

    @Override
    public List<Product> selectProductByCondition(Product product)
    {
        return productMapper.selectProductByCondition(product);
    }

    public List<Product> findAllUnRestrictProduct(ProductRestrictRule productRestrictRule)
    {
        return productMapper.selectAllUnRestrictProduct(productRestrictRule);
    }

    public List<Product> findAllRestrictProduct(ProductRestrictRule productRestrictRule)
    {
        return productMapper.selectAllRestrictProduct(productRestrictRule);
    }
    public List<Product> findAllProductHaveStockPile()
    {
        return productMapper.selectAllProductHaveStockPile();
    }

}
