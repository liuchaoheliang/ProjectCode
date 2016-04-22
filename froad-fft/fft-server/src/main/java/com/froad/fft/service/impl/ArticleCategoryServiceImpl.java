package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.ArticleCategoryMapper;
import com.froad.fft.persistent.entity.ArticleCategory;
import com.froad.fft.service.ArticleCategoryService;

@Service("articleCategoryServiceImpl")
public class ArticleCategoryServiceImpl implements ArticleCategoryService
{

    private static Logger logger = Logger.getLogger(ArticleCategoryServiceImpl.class);

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public Long saveArticleCategory(ArticleCategory articleCategory)
    {
        return articleCategoryMapper.saveArticleCategory(articleCategory);
    }

    @Override
    public Boolean updateArticleCategoryById(ArticleCategory articleCategory)
    {
        if (articleCategory.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return articleCategoryMapper.updateArticleCategoryById(articleCategory);
    }

    @Override
    public ArticleCategory selectArticleCategoryById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return articleCategoryMapper.selectArticleCategoryById(id);
    }

    public Page findArticleCategoryByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(articleCategoryMapper.selectArticleCategoryByPage(page));
        page.setTotalCount(articleCategoryMapper.selectArticleCategoryByPageCount(page));
        return page;
    }
}
