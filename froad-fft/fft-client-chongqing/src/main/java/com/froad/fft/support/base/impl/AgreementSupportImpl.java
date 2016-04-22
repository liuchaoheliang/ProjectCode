package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.AgreementExportService;
import com.froad.fft.dto.AgreementDto;
import com.froad.fft.enums.AgreementType;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.AgreementSupport;

@Service
public class AgreementSupportImpl implements AgreementSupport {

	@Resource(name="agreementExportService")
	AgreementExportService agreementExportService;
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Override
	public AgreementDto getAgreement(AgreementType agreementType) {
			AgreementDto agreementDto = new AgreementDto();
			agreementDto = agreementExportService.findAgreementByClient(clientAccessType, ClientVersion.version_1_0,agreementType);
		return agreementDto;
	}

}
