package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantCategoryMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantCategory;

import java.util.List;

public class MerchantCategoryMapperImpl implements MerchantCategoryMapper
{

    @Resource
    private MerchantCategoryMapper merchantCategoryMapper;

    @Override
    public Long saveMerchantCategory(MerchantCategory merchantCategory)
    {
    	merchantCategoryMapper.saveMerchantCategory(merchantCategory);
    	return merchantCategory.getId();
    }

    @Override
    public Boolean updateMerchantCategoryById(MerchantCategory merchantCategory)
    {
    	return merchantCategoryMapper.updateMerchantCategoryById(merchantCategory);
    }

    @Override
    public MerchantCategory selectMerchantCategoryById(Long id)
    {
        return merchantCategoryMapper.selectMerchantCategoryById(id);
    }

    public List<MerchantCategory> selectMerchantCategoryByPage(Page page)
    {
        return merchantCategoryMapper.selectMerchantCategoryByPage(page);
    }

    public Integer selectMerchantCategoryByPageCount(Page page)
    {
        return merchantCategoryMapper.selectMerchantCategoryByPageCount(page);
    }

}
