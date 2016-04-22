package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductAttribute;

public interface ProductAttributeMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductAttribute</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午3:41:36 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductAttribute(ProductAttribute productAttribute);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键修改数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午4:08:46 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductAttributeById(ProductAttribute productAttribute);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月7日 下午4:15:45 </p>
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
	public List findProductAttributeByPage(Page page);

	/**
	 * @author larry
	 * @param page
	 * <p>查询总记录数</p>
	 * @return
	 */
	public Integer findProductAttributeByPageCount(Page page);
}
