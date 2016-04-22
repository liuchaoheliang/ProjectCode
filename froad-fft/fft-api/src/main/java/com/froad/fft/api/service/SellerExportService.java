package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SellerDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 卖家
 * @author FQ
 *
 */
public interface SellerExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param SellerDto
	 * @return
	 * @throws FroadException
	 */
	public Long addSeller(ClientAccessType clientAccessType,ClientVersion clientVersion,SellerDto SellerDto) throws FroadException;
}
