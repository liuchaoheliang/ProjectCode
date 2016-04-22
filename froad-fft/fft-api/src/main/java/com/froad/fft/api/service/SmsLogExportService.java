package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.SmsLogDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 短信日志
 *
 * @author FQ
 */
public interface SmsLogExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param smsLogDto
     * @return
     * @throws FroadException
     */
    public Long addSmsLog(ClientAccessType clientAccessType, ClientVersion clientVersion, SmsLogDto smsLogDto) throws FroadException;

    /**
     * 修改短信状态  add houguoquan 14.4.11
     *
     * @param clientAccessType 客户端类型
     * @param clientVersion    客户端版本好
     * @param id               短信id
     * @return
     */
    public Boolean updateSmsLogStatusById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id);

    /**
     * 根据id获取短信内容 add houguoquan 14.4.11
     *
     * @param clientAccessType
     * @param clientVersion
     * @param id
     * @return
     * @throws FroadException
     */
    public SmsLogDto findSmsLogById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * 短信分页查询  add houguoquan 14.4.11
     *
     * @param clientAccessType
     * @param clientVersion
     * @param pageDto
     * @return
     * @throws FroadException
     */

    public PageDto<SmsLogDto> findSmsLogByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<SmsLogDto> pageDto) throws FroadException;


}
