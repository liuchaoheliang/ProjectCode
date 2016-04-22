/**
 * 文件名称:TransStatistic.java
 * 文件描述: 交易排行查询
 * 产品标识: fft
 * 单元描述: fft-persistent-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-5-4
 * 历史修改:  
 */
package com.froad.fft.persistent.bean;

import com.froad.fft.persistent.entity.BaseEntity;

import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class TransStatistic  extends BaseEntity
{
    /**
     * 用户编号
     */
    private Long memberCode;

    /**
     * 订单时间
     */
    private Date orderTime;

    /**
     * 总金额
     */
    private Double totalPrice;

    public Long getMemberCode()
    {
        return memberCode;
    }

    public void setMemberCode(Long memberCode)
    {
        this.memberCode = memberCode;
    }

    public Date getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    public Double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice)
    {
        this.totalPrice = totalPrice;
    }
}
