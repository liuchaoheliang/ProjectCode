package com.froad.fft.persistent.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductPresellMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.PresellTrans;
import com.froad.fft.persistent.entity.ProductPresell;

public class ProductPresellMapperImpl implements ProductPresellMapper
{

    @Resource
    private ProductPresellMapper productPresellMapper;

    @Override
    public Long saveProductPresell(ProductPresell temp)
    {
        productPresellMapper.saveProductPresell(temp);
        return temp.getId();
    }

    @Override
    public Boolean updateProductPresellById(ProductPresell temp)
    {
        return productPresellMapper.updateProductPresellById(temp);
    }

    @Override
    public ProductPresell selectProductPresellById(Long id)
    {
        return productPresellMapper.selectProductPresellById(id);
    }

    @Override
    public List<ProductPresell> selectProductPresellByPage(Page page)
    {
        return productPresellMapper.selectProductPresellByPage(page);
    }

    @Override
    public Integer selectProductPresellByPageCount(Page page)
    {
        return productPresellMapper.selectProductPresellByPageCount(page);
    }

    @Override
    public ProductPresell selectByProductId(Long productId)
    {
        return productPresellMapper.selectByProductId(productId);
    }

    public List<Map> selectPresellTransByProductId(Long productId)
    {
        return productPresellMapper.selectPresellTransByProductId(productId);
    }

    public List<PresellTrans> selectPresellTransStatisticByProductId(Long productId)
    {
        return productPresellMapper.selectPresellTransStatisticByProductId(productId);
    }

    public List<ProductPresell> selectAllUnBindProductPresell()
    {
        return productPresellMapper.selectAllUnBindProductPresell();
    }
}
