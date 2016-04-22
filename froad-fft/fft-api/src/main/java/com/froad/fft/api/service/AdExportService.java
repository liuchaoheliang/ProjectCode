package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.AdDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 广告
 *
 * @author FQ
 */
public interface AdExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param adDto
     * @return
     * @throws FroadException
     */
    public Long addAd(ClientAccessType clientAccessType, ClientVersion clientVersion, AdDto adDto) throws FroadException;

    /**
     * @param clientAccessType
     * @param clientVersion
     * @param pageDto
     * @return
     * @author larry
     * <p>分页查询</p>
     */
    public PageDto findAdByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException;

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键查询地区</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年4月6日 下午5:27:03 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public AdDto findAdById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id);

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateAdById(ClientAccessType clientAccessType, ClientVersion clientVersion, AdDto dto) throws FroadException;

    /**
     * 根据广告位 查询启用的广告
     * @param clientAccessType
     * @param clientVersion
     * @param adPositionId
     * @return
     * @throws FroadException
     */
    public List<AdDto> findEnableAdByAdPositionId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long adPositionId) throws FroadException;
}
