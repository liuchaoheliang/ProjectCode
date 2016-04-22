package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.MerchantPresentDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商户介绍
 * @author FQ
 *
 */
public interface MerchantPresentExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param merchantPresentDto
	 * @return
	 * @throws FroadException
	 */
	public Long addMerchantPresent(ClientAccessType clientAccessType,ClientVersion clientVersion,MerchantPresentDto merchantPresentDto) throws FroadException;
}
