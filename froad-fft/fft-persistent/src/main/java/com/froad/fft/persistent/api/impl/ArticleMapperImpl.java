package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ArticleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Article;

import java.util.List;

public class ArticleMapperImpl implements ArticleMapper
{

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Long saveArticle(Article article)
    {
        articleMapper.saveArticle(article);
        return article.getId();
    }

    @Override
    public Boolean updateArticleById(Article article)
    {
        return articleMapper.updateArticleById(article);
    }

    @Override
    public Article selectArticleById(Long id)
    {
        return articleMapper.selectArticleById(id);
    }

    public List<Article> selectArticleByPage(Page page)
    {
        return articleMapper.selectArticleByPage(page);
    }

    public Integer selectArticleByPageCount(Page page)
    {
        return articleMapper.selectArticleByPageCount(page);
    }

}
