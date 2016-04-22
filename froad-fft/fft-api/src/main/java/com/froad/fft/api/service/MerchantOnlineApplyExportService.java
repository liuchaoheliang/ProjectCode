package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.MerchantOnlineApplyDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商户在线申请
 * @author FQ
 *
 */
public interface MerchantOnlineApplyExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param merchantOnlineApplyDto
	 * @return
	 * @throws FroadException
	 */
	public Long addMerchantOnlineApply(ClientAccessType clientAccessType,ClientVersion clientVersion,MerchantOnlineApplyDto merchantOnlineApplyDto) throws FroadException;
}
