package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductType;

public interface ProductTypeService {
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductType并返回数据主键（如果主键为null则插入失败）</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 上午10:06:08 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductType(ProductType productType);

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 上午10:16:55 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductTypeById(ProductType productType);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 上午10:25:15 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductType selectProductTypeById(Long id);

	/**
	 * @author larry
	 * @param page
	 * <p>分页查询商品类型</p>
	 * @return
	 */
	public Page findProductTypeByPage(Page page);
}
