package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.SmsContentDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 短信模板
 * @author FQ
 *
 */
public interface SmsContentExportService {

	/**
	 * 增加
	 * @param clientAccessType
	 * @param clientVersion
	 * @param smsContentDto
	 * @return
	 * @throws FroadException
	 */
	public Long addSmsContent(ClientAccessType clientAccessType,ClientVersion clientVersion,SmsContentDto smsContentDto) throws FroadException;

	/**
	 * @author larry
	 * @param management
	 * @param version10
	 * @param pageDto
	 * <p>分页查询短信模板</p>
	 */
	public PageDto findSmsContentByPage(ClientAccessType management,
			ClientVersion version10, PageDto<SmsContentDto> pageDto)throws FroadException;

	/**
	 * @author larry
	 * @param id
	 * <p>根据ID查询短信模板</p>
	 * @return
	 */
	public SmsContentDto getSmsContentById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException;

	/**
	 * @author larry
	 * @param management
	 * @param version10
	 * @param smsContentDto
	 * <p>更新短信模板</p>
	 * @return
	 */
	public Boolean updateSmsContent(ClientAccessType management,
			ClientVersion version10, SmsContentDto smsContentDto)throws FroadException;
}
