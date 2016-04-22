/**
 * 文件名称:OutletStatistic.java
 * 文件描述: 分店统计
 * 产品标识: fft
 * 单元描述: fft-persistent-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-29
 * 历史修改:  
 */
package com.froad.fft.persistent.bean;

import com.froad.fft.persistent.entity.BaseEntity;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class OutletStatistic extends BaseEntity
{
    /**
     * 提货点Id
     */
    private Integer deliveryId;
    /**
     * 提货点名称
     */
    private String deliveryName;
    /**
     * 分店Id
     */
    private Integer outletId;
    /**
     * 分店名称
     */
    private String outletName;
    /**
     * 订单金额合计
     */
    private Double price;
    /**
     * 订单数量合计
     */
    private Double quantity;

    private Integer orderNum;

    public Integer getDeliveryId()
    {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId)
    {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryName()
    {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName)
    {
        this.deliveryName = deliveryName;
    }

    public Integer getOutletId()
    {
        return outletId;
    }

    public void setOutletId(Integer outletId)
    {
        this.outletId = outletId;
    }

    public String getOutletName()
    {
        return outletName;
    }

    public void setOutletName(String outletName)
    {
        this.outletName = outletName;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Double quantity)
    {
        this.quantity = quantity;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }
}
