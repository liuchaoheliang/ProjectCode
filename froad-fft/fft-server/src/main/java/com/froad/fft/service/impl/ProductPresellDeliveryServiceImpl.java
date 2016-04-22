/**
 * 文件名称:ProductPresellDeliveryServiceImpl.java
 * 文件描述: 预售商品与提货点对应关系表
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.service.impl;

import com.froad.fft.persistent.api.ProductPresellDeliveryMapper;
import com.froad.fft.persistent.entity.ProductPresellDelivery;
import com.froad.fft.service.ProductPresellDeliveryService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@Service("productPresellDeliveryServiceImpl")
public class ProductPresellDeliveryServiceImpl implements ProductPresellDeliveryService
{
    private static Logger logger = Logger.getLogger(ProductPresellDeliveryService.class);

    @Resource
    private ProductPresellDeliveryMapper productPresellDeliveryMapper;

    public Long saveProductPresellDelivery(ProductPresellDelivery productPresellDelivery)
    {
        return productPresellDeliveryMapper.saveProductPresellDelivery(productPresellDelivery);
    }

    public List<ProductPresellDelivery> selectByProductPresellId(Long productId)
    {
        return productPresellDeliveryMapper.selectByProductPresellId(productId);
    }

    public Boolean deleteById(Long id)
    {
        return productPresellDeliveryMapper.deleteById(id);
    }
}
