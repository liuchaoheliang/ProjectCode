package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.ProductImage;

public interface ProductImageMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductImage</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午6:30:05 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductImage(ProductImage productImage);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午7:05:03 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductImageById(ProductImage productImage);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午7:13:53 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductImage selectProductImageById(Long id);
}
