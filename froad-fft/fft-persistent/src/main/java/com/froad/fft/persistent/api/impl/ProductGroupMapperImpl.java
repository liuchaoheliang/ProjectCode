package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductGroupMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductGroup;

import java.util.List;

public class ProductGroupMapperImpl implements ProductGroupMapper
{

    @Resource
    private ProductGroupMapper productGroupMapper;

    @Override
    public Long saveProductGroup(ProductGroup productGroup)
    {
        productGroupMapper.saveProductGroup(productGroup);
        return productGroup.getId();
    }

    @Override
    public Boolean updateProductGroupById(ProductGroup productGroup)
    {
        return productGroupMapper.updateProductGroupById(productGroup);
    }

    @Override
    public ProductGroup selectProductGroupById(Long id)
    {
        return productGroupMapper.selectProductGroupById(id);
    }

    public List<ProductGroup> selectProductGroupByPage(Page page)
    {
        return productGroupMapper.selectProductGroupByPage(page);
    }

    public Integer selectProductGroupByPageCount(Page page)
    {
        return productGroupMapper.selectProductGroupByPageCount(page);
    }
}
