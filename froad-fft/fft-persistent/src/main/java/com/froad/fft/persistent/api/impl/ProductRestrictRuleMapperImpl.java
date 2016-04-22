package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductRestrictRuleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductRestrictRule;

import java.util.List;

public class ProductRestrictRuleMapperImpl implements ProductRestrictRuleMapper
{

    @Resource
    private ProductRestrictRuleMapper productRestrictRuleMapper;

    @Override
    public Long saveProductRestrictRule(ProductRestrictRule productRestrictRule)
    {
        productRestrictRuleMapper.saveProductRestrictRule(productRestrictRule);
        return productRestrictRule.getId();
    }

    @Override
    public Boolean updateProductRestrictRuleById(ProductRestrictRule productRestrictRule)
    {
        return productRestrictRuleMapper.updateProductRestrictRuleById(productRestrictRule);
    }

    @Override
    public ProductRestrictRule selectProductRestrictRuleById(Long id)
    {
        return productRestrictRuleMapper.selectProductRestrictRuleById(id);
    }

    public List<ProductRestrictRule> selectProductRestrictRuleByPage(Page page)
    {
        return productRestrictRuleMapper.selectProductRestrictRuleByPage(page);
    }

    public Integer selectProductRestrictRuleByPageCount(Page page)
    {
        return productRestrictRuleMapper.selectProductRestrictRuleByPageCount(page);
    }

}
