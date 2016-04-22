package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.SysClientDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 
 * @author larry
 *<p>客户端信息</p>
 */
public interface SysClientExportService {
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午2:29:12
	  */
	public List<SysClientDto> findAllSysClient()throws FroadException;
	
	
	/**
	  * 方法描述：根据编号查询客户端
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午2:29:17
	  */
	public  SysClientDto findSysClientDtoByNumber(String number)throws FroadException;
}
