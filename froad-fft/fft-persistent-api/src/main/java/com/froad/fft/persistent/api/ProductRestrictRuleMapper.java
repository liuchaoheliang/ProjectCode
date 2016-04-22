package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductRestrictRule;

import java.util.List;

public interface ProductRestrictRuleMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductRestrictRule</b> --* </p>
	 *<p> 作者: 侯国权 houguoquan@f-road.com.cn </p>
	 *<p> 时间: 2014年2月14日 下午12:56:10 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductRestrictRule(ProductRestrictRule productRestrictRule);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新</b> --* </p>
	 *<p> 作者: 侯国权 houguoquan@f-road.com.cn </p>
	 *<p> 时间: 2014年2月14日 下午12:57:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductRestrictRuleById(ProductRestrictRule productRestrictRule);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 侯国权 houguoquan@f-road.com.cn </p>
	 *<p> 时间: 2014年2月14日 下午12:57:57 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductRestrictRule selectProductRestrictRuleById(Long id);

    /**
      * 方法描述：分页查询
      *
      * @param:
      * @return:
      * @version: 1.0
      * @author: 侯国权
      * @time: 2014年3月28日 上午10:32:48
      */
     public List<ProductRestrictRule> selectProductRestrictRuleByPage(Page page);

     /**
      * 方法描述：分页总条数
      *
      * @param:
      * @return:
      * @version: 1.0
      * @author:侯国权
      * @time: 2014年3月28日 上午10:32:50
      */
     public Integer selectProductRestrictRuleByPageCount(Page page);
}
