package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.LogDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 日志
 * @author FQ
 *
 */
public interface LogExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param logDto
	 * @return
	 * @throws FroadException
	 */
	public Long addLog(ClientAccessType clientAccessType,ClientVersion clientVersion,LogDto logDto) throws FroadException;
	
	/**
	 * @author larry
	 * <p>分页查询</p>
	 * @param clientAccessType
	 * @param clientVersion
	 * @param pageDto
	 * @return
	 */
	public PageDto findLogByPage(ClientAccessType clientAccessType,
			ClientVersion clientVersion, PageDto pageDto)throws FroadException;
}
