package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductType;

public interface ProductTypeMapper {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>保存ProductType</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 上午10:02:23 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Long saveProductType(ProductType productType);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键更新数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 上午10:16:12 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Boolean updateProductTypeById(ProductType productType);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键获取对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月8日 上午10:21:57 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductType selectProductTypeById(Long id);

	/**
	 * @author larry
	 * @param page
	 * <p>>分页查询商品类型</p>
	 * @return
	 */
	public List findProductTypeByPage(Page page);

	/**
	 * @author larry
	 * @param page
	 * <p>>分页查询商品类型记录数</p>
	 * @return
	 */
	public Integer findProductTypeByPageCount(Page page);
}
