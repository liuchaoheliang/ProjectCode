package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.SMSExportService;
import com.froad.fft.dto.SmsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.SmsSupport;
@Service("smsSupportImpl")
public class SmsSupportImpl implements SmsSupport {

	@Resource(name = "smsService")
	private SMSExportService smsExportService;
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Override
	public SmsDto sendSms(SmsDto smsDto) {
		
		return smsExportService.sendSms(clientAccessType,ClientVersion.version_1_0,smsDto);
	}

}
