package com.froad.fft.service;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductAttribute;

public interface ProductAttributeService {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductAttribute并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午3:44:14 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductAttribute(ProductAttribute productAttribute);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键修改数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午4:09:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductAttributeById(ProductAttribute productAttribute);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午4:16:31 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductAttribute selectProductAttributeById(Long id);

	/**
	 * @author larry
	 * @param page
	 * <p>分页查询商品属性</p>
	 * @return
	 */
	public Page findProductAttributeByPage(Page page);
}
