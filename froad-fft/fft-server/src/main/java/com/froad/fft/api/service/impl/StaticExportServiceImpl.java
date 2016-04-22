package com.froad.fft.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.froad.fft.api.service.StaticExportService;
import com.froad.fft.bean.FroadException;
import com.froad.fft.common.ClientSiteUrlMapCommand;
import com.froad.fft.enums.BuildType;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.service.SysClientService;
import com.froad.fft.util.HttpClientUtil;

public class StaticExportServiceImpl implements StaticExportService{

	
	@Resource(name="sysClientServiceImpl")
	private SysClientService sysClientService;
	
	@Override
	public Boolean buildStatic(ClientAccessType clientAccessType,
			ClientVersion clientVersion, Long sysClientId, BuildType buldType)
			throws FroadException {
		
		SysClient sysClient=sysClientService.findSysClientById(sysClientId);
		
		if(sysClient == null){
			return false;
		}
		
		Map<String, Object> paramVal = new HashMap<String, Object>();
		paramVal.put("buildType", buldType.toString());
		
		String url=ClientSiteUrlMapCommand.CLIENT_STATIC_URL.get(sysClient.getNumber());
		
		if(url == null){
			return false;
		}
		
		String result=HttpClientUtil.post(url, paramVal);
		
		if(result !=null && "success".equals(result)){
			return true;
		}
		return false;
	}

}
