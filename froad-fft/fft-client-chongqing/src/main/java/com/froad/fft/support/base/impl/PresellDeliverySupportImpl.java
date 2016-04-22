package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.PresellDeliveryExportService;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.PresellDeliverySupport;

@Service
public class PresellDeliverySupportImpl implements PresellDeliverySupport {

	@Resource(name = "presellDeliveryService")
	PresellDeliveryExportService presellDeliveryService;

	private final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	public PresellDeliveryDto getById(Long id){
		return presellDeliveryService.findPresellDeliveryById(clientAccessType, ClientVersion.version_1_0, id);
	}
}
