package com.froad.fft.service.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.bean.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.ArticleMapper;
import com.froad.fft.persistent.entity.Article;
import com.froad.fft.service.ArticleService;

@Service("articleServiceImpl")
public class ArticleServiceImpl implements ArticleService
{

    private static Logger logger = Logger.getLogger(ArticleServiceImpl.class);

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Long saveArticle(Article article)
    {
        return articleMapper.saveArticle(article);
    }

    @Override
    public Boolean updateArticleById(Article article)
    {
        if (article.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return articleMapper.updateArticleById(article);
    }

    @Override
    public Article selectArticleById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return articleMapper.selectArticleById(id);
    }

    public Page findArticleByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(articleMapper.selectArticleByPage(page));
        page.setTotalCount(articleMapper.selectArticleByPageCount(page));
        return page;
    }
}
