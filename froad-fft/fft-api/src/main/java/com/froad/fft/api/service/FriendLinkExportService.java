package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.FriendLinkDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 友情链接
 *
 * @author FQ
 */
public interface FriendLinkExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param friendLinkDto
     * @return
     * @throws FroadException
     */
    public Long addFriendLink(ClientAccessType clientAccessType, ClientVersion clientVersion, FriendLinkDto friendLinkDto) throws FroadException;

    /**
     * @param clientAccessType
     * @param clientVersion
     * @param pageDto
     * @return
     * @author larry
     * <p>分页查询</p>
     */
    public PageDto findFriendLinkByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException;

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键查询地区</b> --* </p>
     * <p> 作者: 侯国权</p>
     * <p> 时间: 2014年4月15日 下午5:27:03 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public FriendLinkDto findFriendLinkById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id);

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateFriendLinkById(ClientAccessType clientAccessType, ClientVersion clientVersion, FriendLinkDto dto) throws FroadException;


}
