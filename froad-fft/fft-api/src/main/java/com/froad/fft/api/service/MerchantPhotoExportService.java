package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.MerchantPhotoDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商户相册
 * @author FQ
 *
 */
public interface MerchantPhotoExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param MerchantPhotoDto
	 * @return
	 * @throws FroadException
	 */
	public Long addMerchantPhoto(ClientAccessType clientAccessType,ClientVersion clientVersion,MerchantPhotoDto MerchantPhotoDto) throws FroadException;
}
