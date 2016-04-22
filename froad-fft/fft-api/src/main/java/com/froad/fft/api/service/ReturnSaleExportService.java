package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ReturnSaleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
* @ClassName: ReturnSaleExportService
* @Description: 退货/换货
* @author larry
* @date 2014-4-3 下午04:23:52
*
 */
public interface ReturnSaleExportService {
	
	public Long saveReturnSaleDto(ClientAccessType clientAccessType,ClientVersion clientVersion,ReturnSaleDto returnSaleDto)throws FroadException;
	
	/**
	*<p>分页查询</p>
	* @author larry
	* @datetime 2014-4-3下午04:29:13
	* @return PageDto<ReturnSaleDto>
	* @throws 
	* example<br/>
	*
	 */
	public PageDto<ReturnSaleDto> findReturnSaleByPage(ClientAccessType clientAccessType,ClientVersion clientVersion,PageDto<ReturnSaleDto> pageDto)throws FroadException;
	
	/**
	*<p>根据ID查询</p>
	* @author larry
	* @datetime 2014-4-3下午04:30:19
	* @return ReturnSaleDto
	* @throws 
	* example<br/>
	*
	 */
	public ReturnSaleDto getReturnSaleById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException;

	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午12:20:25
	  */
	public List<ReturnSaleDto> getByConditions(ClientAccessType clientAccessType,ClientVersion clientVersion,ReturnSaleDto  returnSaleDto)throws FroadException;
}
