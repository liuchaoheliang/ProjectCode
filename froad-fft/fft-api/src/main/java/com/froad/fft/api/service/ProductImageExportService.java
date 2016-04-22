package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.ProductImageDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商品图片
 * @author FQ
 *
 */
public interface ProductImageExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param productImageDto
	 * @return
	 * @throws FroadException
	 */
	public Long addProductImage(ClientAccessType clientAccessType,ClientVersion clientVersion,ProductImageDto productImageDto) throws FroadException;
}
