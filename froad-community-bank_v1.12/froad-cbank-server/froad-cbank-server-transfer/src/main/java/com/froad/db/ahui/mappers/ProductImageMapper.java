package com.froad.db.ahui.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>条件查询</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年7月14日 下午6:42:53 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<ProductImage> selectByCondition(ProductImage productImage);
	/**
	 * 
	  * @Title: selectAllProductImage
	  * @Description: TODO
	  * @author: Yaren Liang 2015年5月2日
	  * @modify: Yaren Liang 2015年5月2日
	  * @param @return    
	  * @return List<ProductImage>    
	  * @throws
	 */
	public List<ProductImage> selectAllProductImage();
	
	
	public ProductImage selectProductImageByProductId(@Param("productId")Long productId);
	
	public List<ProductImage> selectAllProductImageByProductId(Long id);
}
