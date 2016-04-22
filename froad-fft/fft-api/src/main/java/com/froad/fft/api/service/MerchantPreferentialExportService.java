package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.MerchantPreferentialDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商户优惠活动
 * @author FQ
 *
 */
public interface MerchantPreferentialExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param merchantPreferentialDto
	 * @return
	 * @throws FroadException
	 */
	public Long addMerchantPreferential(ClientAccessType clientAccessType,ClientVersion clientVersion,MerchantPreferentialDto merchantPreferentialDto) throws FroadException;
}
