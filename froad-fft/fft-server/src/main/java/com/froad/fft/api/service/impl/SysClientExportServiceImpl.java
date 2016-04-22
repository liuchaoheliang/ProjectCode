package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.api.service.SysClientExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.SysClientDto;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.service.SysClientService;

public class SysClientExportServiceImpl implements SysClientExportService {

	@Resource(name = "sysClientServiceImpl")
	SysClientService sysClientService;
	
	@Override
	public List<SysClientDto> findAllSysClient()throws FroadException {
		List<SysClientDto> sysClientDtos = new ArrayList<SysClientDto>();
		List<SysClient> sysClients = sysClientService.findAllSysClient();
		if(sysClients!=null){
			for (SysClient sysClient : sysClients) {
				sysClientDtos.add(BeanToDtoSupport.loadbySysClientDto(sysClient));
			}
		}
		return sysClientDtos;
	}

	
	@Override
	public SysClientDto findSysClientDtoByNumber(String number)throws FroadException {
		return BeanToDtoSupport.loadbySysClientDto(sysClientService.findSysClientByNumber(number));
	}


}
