package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductCategory;

public interface ProductCategoryMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductCategory</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午5:40:53 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductCategory(ProductCategory productCategory);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>根据主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午6:05:47 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductCategoryById(ProductCategory productCategory);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午6:17:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductCategory selectProductCategoryById(Long id);

	/**
	 * 分页查询商品分类
	 * @param page
	 * @return
	 */
	public List<ProductCategory> findProductCategoryByPage(Page page);
	
	/**
	 * 分页查询记录数
	 * @param page
	 * @return
	 */
	public Integer findProductCategoryByPageCount(Page page);

	/**
	 *@author larry
	 * @param id
	 * <p>根据ID查询商品分类</p>
	 * @return
	 */
	public ProductCategory getProductCategoryById(Long id);
}
