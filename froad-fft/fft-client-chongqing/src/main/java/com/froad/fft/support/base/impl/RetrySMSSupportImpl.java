package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.RetryBusiSMSExportService;
import com.froad.fft.bean.Result;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.RetrySMSSupport;

@Service
public class RetrySMSSupportImpl implements RetrySMSSupport {

	@Resource
	private RetryBusiSMSExportService retryBusiSMSService;
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	
	@Override
	public Result retryPresell(Long transId, String ip) {
		return retryBusiSMSService.retryPresell(clientAccessType, ClientVersion.version_1_0, transId, ip);
	}
}
