package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.api.service.ReturnSaleDetailExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.dto.ReturnSaleDetailDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.persistent.entity.ReturnSaleDetail;
import com.froad.fft.service.ReturnSaleDetailService;

public class ReturnSaleDetailExportServiceImpl implements
		ReturnSaleDetailExportService {

	@Resource(name = "returnSaleDetailServiceImpl")
	ReturnSaleDetailService returnSaleDetailService;
	
	@Override
	public ReturnSaleDetailDto getReturnSaleDetailById(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long id) {
		return BeanToDtoSupport.loadByReSaleDetailDto(returnSaleDetailService.getReturnSaleDetailById(id));
	}

	@Override
	public ReturnSaleDetailDto getReturnSaleDetailByRsId(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long rsid) {
		return BeanToDtoSupport.loadByReSaleDetailDto(returnSaleDetailService.getReturnSaleDetailByRsId(rsid));
	}


	@Override
	public Long saveReturnSaleDetailDto(ClientAccessType clientAccessType,
			ClientVersion clientVersion, ReturnSaleDetailDto returnSaleDetailDto)
			throws FroadException {
		return returnSaleDetailService.saveReturnSaleDetail(DtoToBeanSupport.loadByReSaleDetail(returnSaleDetailDto));
	}

	
	@Override
	public List<ReturnSaleDetailDto> getByConditions(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			ReturnSaleDetailDto returnSaleDetailDto) throws FroadException {
		List<ReturnSaleDetail> list=returnSaleDetailService.selectByConditions(DtoToBeanSupport.loadByReSaleDetail(returnSaleDetailDto));
		List<ReturnSaleDetailDto> dlist=new ArrayList();
		for(ReturnSaleDetail temp:list){
			dlist.add(BeanToDtoSupport.loadByReSaleDetailDto(temp));
		}
		return dlist;
	}

}
