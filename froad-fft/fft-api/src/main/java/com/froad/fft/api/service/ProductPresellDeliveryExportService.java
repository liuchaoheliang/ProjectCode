/**
 * 文件名称:ProductPresellDeliveryExportService.java
 * 文件描述: 预售商品与提货点对应关系
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.ProductPresellDeliveryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

import java.util.List;

/**
 * 预售商品与提货点对应关系
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface ProductPresellDeliveryExportService
{
    /**
     * 方法描述：添加数据
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月3日 下午8:12:06
     */
    public Long saveProductPresellDelivery(ClientAccessType clientAccessType, ClientVersion clientVersion,ProductPresellDeliveryDto productPresellDeliveryDto)throws FroadException;

    /**
     * 方法描述：根据预售商品Id查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月3日 下午8:12:06
     */
    public List<ProductPresellDeliveryDto> selectByProductPresellId(ClientAccessType clientAccessType, ClientVersion clientVersion,Long productId)throws FroadException;

    /**
     * 方法描述：根据Id删除关系
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月3日 下午8:12:06
     */
    public Boolean deleteById(ClientAccessType clientAccessType, ClientVersion clientVersion,Long id)throws FroadException;
}
