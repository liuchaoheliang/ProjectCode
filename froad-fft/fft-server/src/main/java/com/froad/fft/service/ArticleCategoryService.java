package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ArticleCategory;

public interface ArticleCategoryService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ArticleCategory并返回数据主键（如果主键为null则插入失败</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午4:25:29 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveArticleCategory(ArticleCategory articleCategory);

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午4:52:43 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateArticleCategoryById(ArticleCategory articleCategory);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月6日 下午5:01:51 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ArticleCategory selectArticleCategoryById(Long id);


    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月15日 上午11:59:11
     */
    public Page findArticleCategoryByPage(Page page);

}
