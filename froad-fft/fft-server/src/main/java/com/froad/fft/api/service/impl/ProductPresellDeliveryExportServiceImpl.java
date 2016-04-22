/**
 * 文件名称:ProductPresellDeliveryExportServiceImpl.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.ProductPresellDeliveryExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.ProductPresellDeliveryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.entity.ProductPresellDelivery;
import com.froad.fft.service.ProductPresellDeliveryService;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class ProductPresellDeliveryExportServiceImpl implements ProductPresellDeliveryExportService
{
    @Resource(name = "productPresellDeliveryServiceImpl")
    private ProductPresellDeliveryService productPresellDeliveryService;

    public Long saveProductPresellDelivery(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductPresellDeliveryDto productPresellDeliveryDto)throws FroadException
    {
        return productPresellDeliveryService.saveProductPresellDelivery(DtoToBeanSupport.loadByProductPresellDelivery(productPresellDeliveryDto));
    }

    public List<ProductPresellDeliveryDto> selectByProductPresellId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long productId)throws FroadException
    {
        List<ProductPresellDelivery> mappingList = productPresellDeliveryService.selectByProductPresellId(productId);
        List<ProductPresellDeliveryDto> dtoLis = new ArrayList<ProductPresellDeliveryDto>();

        for (ProductPresellDelivery mapping : mappingList)
        {
            dtoLis.add(BeanToDtoSupport.loadByProductPresellDeliveryDto(mapping));
        }
        return dtoLis;
    }

    public Boolean deleteById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)throws FroadException
    {
        return productPresellDeliveryService.deleteById(id);
    }
}
