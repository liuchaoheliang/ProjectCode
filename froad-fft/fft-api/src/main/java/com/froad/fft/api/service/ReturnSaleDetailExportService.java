package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.ReturnSaleDetailDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * @ClassName: ReturnSaleDetailExportService
 * @Description: 退货/换货详细
 * @author larry
 * @date 2014-4-3 下午04:24:18
 * 
 */
public interface ReturnSaleDetailExportService {
	
	public Long saveReturnSaleDetailDto(ClientAccessType clientAccessType, ClientVersion clientVersion,ReturnSaleDetailDto returnSaleDetailDto)throws FroadException;

	/**
	*<p>根据ID查询退换货详细</p>
	* @author larry
	* @datetime 2014-4-3下午04:33:37
	* @return ReturnSaleDetailDto
	* @throws 
	* example<br/>
	*
	 */
	public ReturnSaleDetailDto getReturnSaleDetailById(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long id)throws FroadException;
	
	/**
	*<p>根据退换货ID查询退换货明细</p>
	* @author larry
	* @datetime 2014-4-3下午04:33:37
	* @return ReturnSaleDetailDto
	* @throws 
	* example<br/>
	 */
	public ReturnSaleDetailDto getReturnSaleDetailByRsId(
	ClientAccessType clientAccessType, ClientVersion clientVersion,
	Long rsid)throws FroadException;
	
	
	
	public List<ReturnSaleDetailDto> getByConditions(
	ClientAccessType clientAccessType, ClientVersion clientVersion,
	ReturnSaleDetailDto returnSaleDetailDto)throws FroadException;
	
}


