package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.MerchantCategoryMapper;
import com.froad.fft.persistent.entity.MerchantCategory;
import com.froad.fft.service.MerchantCategoryService;

@Service("merchantCategoryServiceImpl")
public class MerchantCategoryServiceImpl implements MerchantCategoryService
{

    private static Logger logger = Logger.getLogger(MerchantCategoryServiceImpl.class);

    @Resource
    MerchantCategoryMapper merchantCategoryMapper;

    @Override
    public Long saveMerchantCategory(MerchantCategory merchantCategory)
    {
        return merchantCategoryMapper.saveMerchantCategory(merchantCategory);
    }

    @Override
    public Boolean updateMerchantCategoryById(MerchantCategory merchantCategory)
    {
        if (merchantCategory.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return merchantCategoryMapper.updateMerchantCategoryById(merchantCategory);
    }

    @Override
    public MerchantCategory selectMerchantCategoryById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return merchantCategoryMapper.selectMerchantCategoryById(id);
    }

    public Page findMerchantCategoryByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(merchantCategoryMapper.selectMerchantCategoryByPage(page));
        page.setTotalCount(merchantCategoryMapper.selectMerchantCategoryByPageCount(page));
        return page;
    }

}
