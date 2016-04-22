/**
 * 文件名称:ProductPresellDeliveryService.java
 * 文件描述: 预售商品与提货点对应关系表
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.service;

import com.froad.fft.persistent.entity.ProductPresellDelivery;

import java.util.List;

/**
 * 预售商品与提货点对应关系表
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface ProductPresellDeliveryService
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
    public Long saveProductPresellDelivery(ProductPresellDelivery productPresellDelivery);

    /**
     * 方法描述：根据预售商品Id查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月3日 下午8:12:06
     */
    public List<ProductPresellDelivery> selectByProductPresellId(Long productId);

    /**
     * 方法描述：根据Id删除关系
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月3日 下午8:12:06
     */
    public Boolean deleteById(Long id);


}
