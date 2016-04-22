package com.froad.fft.api.service.impl;

import javax.annotation.Resource;

import com.froad.fft.api.service.CacheExportService;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.service.CacheService;

public class CacheExportServiceImpl implements CacheExportService {

	@Resource(name="cacheServiceImpl")
	private CacheService cacheService;
	
	@Override
	public String getDiskStorePath(ClientAccessType clientAccessType,
			ClientVersion clientVersion) {
		// TODO Auto-generated method stub
		return cacheService.getDiskStorePath();
	}

	@Override
	public int getCacheSize(ClientAccessType clientAccessType,
			ClientVersion clientVersion) {
		// TODO Auto-generated method stub
		return cacheService.getCacheSize();
	}

	@Override
	public void clear(ClientAccessType clientAccessType,
			ClientVersion clientVersion) {
		// TODO Auto-generated method stub
		cacheService.clear();
	}

}
