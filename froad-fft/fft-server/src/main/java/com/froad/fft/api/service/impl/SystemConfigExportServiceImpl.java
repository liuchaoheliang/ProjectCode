package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.SystemConfigExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SystemConfigDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.util.SystemConfigUtil;

public class SystemConfigExportServiceImpl implements SystemConfigExportService {

	@Override
	public SystemConfigDto getSystemConfig(ClientAccessType clientAccessType,
			ClientVersion clientVersion) throws FroadException {
		return BeanToDtoSupport.loadBySystemConfigDto(SystemConfigUtil.getSystemConfig());
	}

	@Override
	public boolean updateSystemConfig(ClientAccessType clientAccessType,
			ClientVersion clientVersion, SystemConfigDto systemConfigDto)
			throws FroadException {
		
		return SystemConfigUtil.set(DtoToBeanSupport.loadBySystemConfig(systemConfigDto));
	}

}
