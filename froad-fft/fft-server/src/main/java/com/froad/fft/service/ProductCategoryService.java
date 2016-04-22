package com.froad.fft.service;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductCategoryDto;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductCategory;

public interface ProductCategoryService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductCategory并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午5:42:15 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductCategory(ProductCategory productCategory);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>根据主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午6:06:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductCategoryById(ProductCategory productCategory);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午6:19:47 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductCategory selectProductCategoryById(Long id);

	/**
	 * @author larry
	 * @param page
	 * <p>分页查询商品分类</p>
	 * @return
	 */
	public Page findProductCategoryByPage(Page page);

//	/**
//	 * @author larry
//	 * @param id
//	 * <p>根据ID查询商品分类</p>
//	 * @return
//	 */
//	public ProductCategory getProductCategoryById(Long id);

}
