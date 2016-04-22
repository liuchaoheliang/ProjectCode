package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SellerChannelDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 卖家资金渠道
 * @author FQ
 *
 */
public interface SellerChannelExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param sellerChannelDto
	 * @return
	 * @throws FroadException
	 */
	public Long addSellerChannel(ClientAccessType clientAccessType,ClientVersion clientVersion,SellerChannelDto sellerChannelDto) throws FroadException;
}
