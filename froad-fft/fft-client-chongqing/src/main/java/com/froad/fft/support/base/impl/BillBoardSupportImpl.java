package com.froad.fft.support.base.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.TransStatisticExportService;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.TransStatisticType;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.support.base.BillBoardSupport;

@Service
public class BillBoardSupportImpl implements BillBoardSupport {
	
	@Resource
    private TransStatisticExportService transStatisticExportService;

	@Override
	public List<Map<String, Object>> getBoard(int size,TransStatisticType transStatisticType) {
		List<Map<String, Object>> statisticList = null;
		statisticList = transStatisticExportService.selectTransStatistic(ClientAccessType.chongqing, null, null, null, TransType.presell,transStatisticType);
		if(statisticList!=null&&statisticList.size()>0){
			size  = size>statisticList.size()?statisticList.size():size;
			statisticList = statisticList.subList(0, size);
		}
		return statisticList;
	}

	
}
