package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductTypeDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商品类型
 * @author FQ
 *
 */
public interface ProductTypeExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param ProductTypeDto
	 * @return
	 * @throws FroadException
	 */
	public Long addProductType(ClientAccessType clientAccessType,ClientVersion clientVersion,ProductTypeDto productTypeDto) throws FroadException;

	/**
	 * @author larry
	 * @param management
	 * @param version10
	 * @param pageDto
	 * <p>分页查询商品类型</p>
	 * @return
	 */
	public PageDto<ProductTypeDto> findProductTypeByPage(
			ClientAccessType management, ClientVersion version10,
			PageDto<ProductTypeDto> pageDto)throws FroadException;

	/**
	 * @author larry
	 * @param id
	 * <p>根据ID查询商品类型</p>
	 * @return
	 */
	public ProductTypeDto getProductTypeById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException;

	/**
	 * @author larry
	 * @param productTypeDto
	 * <p>根据ID更新商品类型</p>
	 * @return
	 */
	public Boolean updateProductType(ClientAccessType clientAccessType,ClientVersion clientVersion,ProductTypeDto productTypeDto)throws FroadException;
}
