package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SellerRuleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 卖家规则
 * @author FQ
 *
 */
public interface SellerRuleExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param SellerRuleDto
	 * @return
	 * @throws FroadException
	 */
	public Long addSellerRule(ClientAccessType clientAccessType,ClientVersion clientVersion,SellerRuleDto SellerRuleDto) throws FroadException;
}
