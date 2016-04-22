/**
 * 文件名称:NavigationExportService.java
 * 文件描述: 导航对外接口
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-14
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.NavigationDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 导航对外接口
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface NavigationExportService
{
    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param dto              数据实体
     * @return
     * @throws com.froad.fft.bean.FroadException
     */
    public Long addNavigation(ClientAccessType clientAccessType, ClientVersion clientVersion, NavigationDto dto) throws FroadException;

    /**
     * @param clientAccessType
     * @param clientVersion
     * @param pageDto
     * @return
     * @author larry
     * <p>分页查询</p>
     */
    public PageDto findNavigationByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException;

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键查询地区</b> --* </p>
     * <p> 作者: 侯国权  houguoquan@f-road.com.cn </p>
     * <p> 时间: 2014年4月14日 下午5:27:03 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public NavigationDto findNavigationById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id);

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateNavigationById(ClientAccessType clientAccessType, ClientVersion clientVersion, NavigationDto dto) throws FroadException;


}
