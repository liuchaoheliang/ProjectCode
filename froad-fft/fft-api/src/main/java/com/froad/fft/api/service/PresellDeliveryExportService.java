/**
 * 文件名称:PresellDeliveryExportService.java
 * 文件描述: 自提点exportservice接口定义
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.enums.*;

/**
 * 自提点export接口定义
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface PresellDeliveryExportService
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
    public Long savePresellDelivery(ClientAccessType clientAccessType, ClientVersion clientVersion, PresellDeliveryDto dto) throws FroadException;

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updatePresellDeliveryById(ClientAccessType clientAccessType, ClientVersion clientVersion, PresellDeliveryDto dto) throws FroadException;

    /**
     * 根据ID 查找广告位
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param id               数据实体Id
     * @return 对饮该数据Id的实体
     */
    public PresellDeliveryDto findPresellDeliveryById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
    	  * 方法描述：分页查询
    	  * @param:
    	  * @return:
    	  * @version: 1.0
    	  * @author: 侯国权
    	  * @time: 2014年3月29日 下午12:06:13
    	  */
    	public PageDto<PresellDeliveryDto> findPresellDeliveryByPage(ClientAccessType clientAccessType,ClientVersion clientVersion,PageDto<PresellDeliveryDto> pageDto)throws FroadException;
}
