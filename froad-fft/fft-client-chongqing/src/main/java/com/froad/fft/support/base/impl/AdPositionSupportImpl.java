package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.AdPositionExportService;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.AdPositionDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.AdPositionSupport;

@Service
public class AdPositionSupportImpl extends BaseSupportImpl implements
		AdPositionSupport {
	
	@Resource(name = "adPositionService")
	private AdPositionExportService adPositionService;

	@Override
	public AdPositionDto findAdPositionById(Long id) {
		try {
			return adPositionService.findAdPositionById(ClientAccessType.chongqing, ClientVersion.version_1_0, id);
		} catch (FroadException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
