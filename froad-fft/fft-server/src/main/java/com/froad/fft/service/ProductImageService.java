package com.froad.fft.service;

import com.froad.fft.persistent.entity.ProductImage;

public interface ProductImageService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductImage并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午6:31:29 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductImage(ProductImage productImage);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午7:07:47 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductImageById(ProductImage productImage);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午7:14:23 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductImage selectProductImageById(Long id);
}
