package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.SystemConfigExportService;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SystemConfigDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.SystemConfigSupport;

@Service
public class SystemConfigSupportImpl extends BaseSupportImpl implements
		SystemConfigSupport {

	@Resource(name = "systemConfigService")
	private SystemConfigExportService systemConfigService;
	
	@Override
	public SystemConfigDto getSystemConfig() {
		try {
			return systemConfigService.getSystemConfig(ClientAccessType.chongqing, ClientVersion.version_1_0);
		} catch (FroadException e) {
			e.printStackTrace();
		}
		return null;
	}


}
