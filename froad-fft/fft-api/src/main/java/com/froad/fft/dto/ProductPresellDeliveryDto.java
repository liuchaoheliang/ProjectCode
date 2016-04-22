/**
 * 文件名称:ProductPresellDeliveryDto.java
 * 文件描述: 预售商品与提货点对应关系dto
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-3
 * 历史修改:  
 */
package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class ProductPresellDeliveryDto implements Serializable
{
    private Long id;
    private Date createTime;

    private Long productPresellId;//预售商品ID
    private Long presellDeliveryId;//预售提货点ID

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Long getProductPresellId()
    {
        return productPresellId;
    }

    public void setProductPresellId(Long productPresellId)
    {
        this.productPresellId = productPresellId;
    }

    public Long getPresellDeliveryId()
    {
        return presellDeliveryId;
    }

    public void setPresellDeliveryId(Long presellDeliveryId)
    {
        this.presellDeliveryId = presellDeliveryId;
    }
}
