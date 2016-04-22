package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.AdExportService;
import com.froad.fft.dto.AdDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.AdSupport;

@Service
public class AdSupportImpl extends BaseSupportImpl implements AdSupport {

	@Resource(name = "adService")
	private AdExportService adService;
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;

	@Override
	public List<AdDto> findEnableAdByAdPositionId(Long adPositionId) {
		return adService.findEnableAdByAdPositionId(clientAccessType, ClientVersion.version_1_0, adPositionId);
	}
	

}
