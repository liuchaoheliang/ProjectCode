package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductAttributeDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;


/**
 * 商品属性
 * 
 * @author FQ
 * 
 */
public interface ProductAttributeExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param ProductAttributeDto
	 * @return
	 * @throws FroadException
	 */
	public Long addProductAttribute(ClientAccessType clientAccessType,ClientVersion clientVersion,ProductAttributeDto productAttributeDto) throws FroadException;

	/**
	 * @author larry
	 * @param management
	 * @param version10
	 * @param pageDto
	 * <p>分页查询商品属性</p>
	 * @return
	 */
	public PageDto<ProductAttributeDto> findProductAttributeByPage(
			ClientAccessType management, ClientVersion version10,
			PageDto<ProductAttributeDto> pageDto)throws FroadException;

	/**
	 * @author larry
	 * @param id
	 * @return
	 * <p>根据ID查询商品属性</p>
	 */
	public ProductAttributeDto getProductAttributeById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException;

	/**
	 * @author larry
	 * @param productAttributeDto
	 * <p>更新商品属性</p>
	 * @return
	 */
	public Boolean updateProductAttribute(ClientAccessType clientAccessType,ClientVersion clientVersion,
			ProductAttributeDto productAttributeDto)throws FroadException;
}
