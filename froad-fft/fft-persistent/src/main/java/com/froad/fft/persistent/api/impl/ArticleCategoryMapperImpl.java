package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ArticleCategoryMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ArticleCategory;

import java.util.List;

public class ArticleCategoryMapperImpl implements ArticleCategoryMapper
{

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public Long saveArticleCategory(ArticleCategory articleCategory)
    {
        articleCategoryMapper.saveArticleCategory(articleCategory);
        return articleCategory.getId();
    }

    @Override
    public Boolean updateArticleCategoryById(ArticleCategory articleCategory)
    {
        return articleCategoryMapper.updateArticleCategoryById(articleCategory);
    }

    @Override
    public ArticleCategory selectArticleCategoryById(Long id)
    {

        return articleCategoryMapper.selectArticleCategoryById(id);
    }

    public List<ArticleCategory> selectArticleCategoryByPage(Page page)
    {
        return articleCategoryMapper.selectArticleCategoryByPage(page);
    }

    public Integer selectArticleCategoryByPageCount(Page page)
    {
        return articleCategoryMapper.selectArticleCategoryByPageCount(page);
    }

}
