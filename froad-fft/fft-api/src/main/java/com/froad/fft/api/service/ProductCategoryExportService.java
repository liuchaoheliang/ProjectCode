package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductCategoryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商品分类
 * @author FQ
 *
 */
public interface ProductCategoryExportService {
	
	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param productCategoryDto
	 * @return
	 * @throws FroadException
	 */
	public Long addProductCategory(ClientAccessType clientAccessType,ClientVersion clientVersion,ProductCategoryDto productCategoryDto) throws FroadException;

	/**
	 * 分页查询商品分类
	 * @param management
	 * @param version10
	 * @param pageDto
	 * @return
	 */
	public PageDto<ProductCategoryDto> findProductCategoryByPage(
			ClientAccessType management, ClientVersion version10,
			PageDto<ProductCategoryDto> pageDto)throws FroadException;

	/**
	 * @author larry
	 * @param id
	 * <p>根据ID查询商品分类</p>
	 * @return
	 */
	public ProductCategoryDto getProductCategoryById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException;

	/**
	 * @author larry
	 * @param productCategoryDto
	 * <p>根据ID更新商品分类</p>
	 * @return
	 */
	public Boolean updateProductCategoryById(ClientAccessType clientAccessType,ClientVersion clientVersion,
			ProductCategoryDto productCategoryDto)throws FroadException;
}
