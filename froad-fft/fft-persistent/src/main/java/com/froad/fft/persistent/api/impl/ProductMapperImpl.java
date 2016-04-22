package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductRestrictRule;

public class ProductMapperImpl implements ProductMapper
{

    @Resource
    private ProductMapper productMapper;

    @Override
    public Long saveProduct(Product product)
    {
        return productMapper.saveProduct(product);
    }

    @Override
    public Boolean updateProductById(Product product)
    {
        return productMapper.updateProductById(product);
    }

    @Override
    public Product selectProductById(Long id)
    {
        return productMapper.selectProductById(id);
    }

    @Override
    public List<Product> findProductByPage(Page page)
    {
        return productMapper.findProductByPage(page);
    }

    @Override
    public Integer findProductByPageCount(Page page)
    {
        return productMapper.findProductByPageCount(page);
    }

    @Override
    public List<Product> selectProductByCondition(Product product)
    {
        return productMapper.selectProductByCondition(product);
    }

    public List<Product> selectAllUnRestrictProduct(ProductRestrictRule productRestrictRule)
    {
        return productMapper.selectAllUnRestrictProduct(productRestrictRule);
    }

    public List<Product> selectAllRestrictProduct(ProductRestrictRule productRestrictRule)
    {
        return productMapper.selectAllRestrictProduct(productRestrictRule);
    }

    public List<Product> selectAllProductHaveStockPile()
      {
          return productMapper.selectAllProductHaveStockPile();
      }
}
