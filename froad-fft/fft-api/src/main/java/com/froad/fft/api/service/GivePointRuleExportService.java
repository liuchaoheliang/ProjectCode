/**
 * 文件名称:GivePointRuleExportService.java
 * 文件描述: 送积分规则
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.GivePointRuleDto;
import com.froad.fft.enums.*;

/**
 * 送积分规则
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface GivePointRuleExportService
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
    public Long saveGivePointRule(ClientAccessType clientAccessType, ClientVersion clientVersion, GivePointRuleDto dto) throws FroadException;

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateGivePointRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, GivePointRuleDto dto) throws FroadException;

    /**
     * 根据ID 查找广告位
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param id               数据实体Id
     * @return 对饮该数据Id的实体
     */
    public GivePointRuleDto findGivePointRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * 送积分规则分页
     *
     * @param clientAccessType
     * @param clientVersion
     * @param pageDto
     * @return
     * @throws FroadException
     */
    public PageDto findGivePointRuleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException;


}
