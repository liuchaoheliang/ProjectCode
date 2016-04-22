package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

public interface CacheExportService {

	/**
	 * 获取缓存存储路径
	 * 
	 * @return 缓存存储路径
	 */
	public String getDiskStorePath(ClientAccessType clientAccessType,ClientVersion clientVersion)throws FroadException;

	/**
	 * 获取缓存数
	 * 
	 * @return 缓存数
	 */
	public int getCacheSize(ClientAccessType clientAccessType,ClientVersion clientVersion) throws FroadException;

	/**
	 * 清除缓存
	 */
	public void clear(ClientAccessType clientAccessType,ClientVersion clientVersion)throws FroadException;
}
