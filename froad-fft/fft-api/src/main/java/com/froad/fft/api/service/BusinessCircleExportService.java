/**
 * 文件名称:BusinessCircleExportService.java
 * 文件描述: 商圈对外暴露接口
 * 产品标识: 分分通
 * 单元描述: fft-api
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-26
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.BusinessCircleDto;
import com.froad.fft.enums.*;

/**
 * 商圈对外暴露接口
 *
 * @author Aides
 * @version 1.0
 */
public interface BusinessCircleExportService
{
    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Long saveBusinessCircle(ClientAccessType clientAccessType, ClientVersion clientVersion, BusinessCircleDto dto) throws FroadException;

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateBusinessCircleById(ClientAccessType clientAccessType, ClientVersion clientVersion, BusinessCircleDto dto) throws FroadException;

    /**
     * 根据ID 查找广告位
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param id               数据实体Id
     * @return 对饮该数据Id的实体
     */
    public BusinessCircleDto findBusinessCircleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param pageDto          数据实体
     * @return 分页结果
     * @author houguoquan
     * <p>分页查询</p>
     */
    public PageDto<BusinessCircleDto> findBusinessCircleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<BusinessCircleDto> pageDto)throws FroadException;
}
