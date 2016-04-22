package com.froad.fft.support.base;

import com.froad.fft.dto.AgreementDto;
import com.froad.fft.enums.AgreementType;

public interface AgreementSupport {

	/**
	*<p>获取当前客户端协议</p>
	* @author larry
	* @datetime 2014-4-11下午05:30:44
	* @return AgreementDto
	* @throws 
	* @example<br/>
	 */
	public AgreementDto getAgreement(AgreementType agreementType);
}
