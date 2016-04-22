package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.dto.AreaDto;
import com.froad.fft.dto.BusinessCircleDto;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.ProductPresellDto;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: ProductPresellSupport.java </p>
 *<p> 描述: *-- <b>精品预售相关操作</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月3日 下午2:00:52 </p>
 ********************************************************
 */
public interface ProductPresellSupport {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过商品主键查询预售商品</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月3日 下午2:15:38 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductPresellDto findProductPresellById(Long id);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过预售商品id查询其下所有提货点</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月3日 下午7:49:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<PresellDeliveryDto> getProductPresellDeliveryByProductId(Long productId);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过预售商品id查询基础商品表中的对应数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月4日 上午9:37:17 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<ProductDto> getProductByCondition(ProductDto productDto);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过id查询商圈</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月6日 上午11:55:02 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public BusinessCircleDto getBusinessCircleDtosById(Long id);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过自提点主键查询自提点信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月6日 下午2:21:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PresellDeliveryDto getProductPresellDeliveryById(Long id);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过主键查询AreaDto</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月6日 下午5:35:01 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public AreaDto getAreaById(Long id);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过商品基本主键查询基础商品</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月7日 下午4:26:52 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public ProductDto getProductDtoById(Long id);
	
	/**
	*<p>根据基础商品关联查询预售商品</p>
	* @author larry
	* @datetime 2014-4-9下午02:19:21
	* @return List<ProductPresellDto>
	* @throws 
	* example<br/>
	*
	 */
	public  List<ProductDto> getProductByPresell(ProductDto productDto);
	
	/**
	*<p>根据基础商品ID查询预售商品信息</p>
	* @author larry
	* @datetime 2014-4-16下午06:23:05
	* @return ProductPresellDto
	* @throws 
	* @example<br/>
	*
	 */
	public ProductPresellDto getPresellByProductId(Long productId);
	
}
