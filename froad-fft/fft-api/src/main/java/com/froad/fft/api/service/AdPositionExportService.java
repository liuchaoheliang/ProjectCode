package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.AdPositionDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 广告位
 *
 * @author FQ
 */
public interface AdPositionExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param adPositionDto
     * @return
     * @throws FroadException
     */
    public Long addAdPosition(ClientAccessType clientAccessType, ClientVersion clientVersion, AdPositionDto adPositionDto) throws FroadException;

    /**
     * 根据ID 查找广告位
     *
     * @param clientAccessType
     * @param clientVersion
     * @param id
     * @return
     */
    public AdPositionDto findAdPositionById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * @param clientAccessType
     * @param clientVersion
     * @param pageDto
     * @return
     * @author 侯国权
     * <p>分页查询</p>
     */
    public PageDto findAdPositionByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException;

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateAdPositionById(ClientAccessType clientAccessType, ClientVersion clientVersion, AdPositionDto dto) throws FroadException;


}
