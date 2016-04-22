package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.ProductRestrictRuleMapper;
import com.froad.fft.persistent.entity.ProductRestrictRule;
import com.froad.fft.service.ProductRestrictRuleService;

@Service("productRestrictRuleServiceImpl")
public class ProductRestrictRuleServiceImpl implements ProductRestrictRuleService
{

    private static Logger logger = Logger.getLogger(ProductRestrictRuleServiceImpl.class);

    @Resource
    private ProductRestrictRuleMapper productRestrictRuleMapper;

    @Override
    public Long saveProductRestrictRule(ProductRestrictRule productRestrictRule)
    {
        return productRestrictRuleMapper.saveProductRestrictRule(productRestrictRule);
    }

    @Override
    public Boolean updateProductRestrictRuleById(ProductRestrictRule productRestrictRule)
    {
        if (productRestrictRule.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return productRestrictRuleMapper.updateProductRestrictRuleById(productRestrictRule);
    }

    @Override
    public ProductRestrictRule selectProductRestrictRuleById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return productRestrictRuleMapper.selectProductRestrictRuleById(id);
    }

    public Page findProductRestrictRuleByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(productRestrictRuleMapper.selectProductRestrictRuleByPage(page));
        page.setTotalCount(productRestrictRuleMapper.selectProductRestrictRuleByPageCount(page));
        return page;
    }


}
