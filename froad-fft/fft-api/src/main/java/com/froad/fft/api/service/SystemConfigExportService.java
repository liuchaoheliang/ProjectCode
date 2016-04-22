package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SystemConfigDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

public interface SystemConfigExportService {
	
	/**
	 * 获取
	 * @param clientAccessType
	 * @param clientVersion
	 * @return
	 * @throws FroadException
	 */
	public SystemConfigDto getSystemConfig(ClientAccessType clientAccessType,ClientVersion clientVersion) throws FroadException;
	
	/**
	 * 更新
	 * @param clientAccessType
	 * @param clientVersion
	 * @param systemConfigDto
	 * @throws FroadException
	 */
	public boolean updateSystemConfig(ClientAccessType clientAccessType,ClientVersion clientVersion,SystemConfigDto systemConfigDto) throws FroadException;
}
