package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.enums.BuildType;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 执行静态化
 * 
 * @author FQ
 * 
 */
public interface StaticExportService {

	/**
	 * 静态化
	 * @param clientAccessType
	 * @param clientVersion
	 * @param sysClientId
	 * @param buldType
	 * @return
	 * @throws FroadException
	 */
	public Boolean buildStatic(ClientAccessType clientAccessType,ClientVersion clientVersion,Long sysClientId, BuildType buldType)throws FroadException;

}
