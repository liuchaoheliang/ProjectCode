/**
 * 文件名称:RegisterGivePointRule.java
 * 文件描述: 注册送积分规则
 * 产品标识: fft
 * 单元描述: fft-persistent-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-22
 * 历史修改:  
 */
package com.froad.fft.persistent.entity;

import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class RegisterGivePointRule extends BaseEntity
{
    /**
     * 客户端Id
     */
    private Long clientId;
    /**
     * 开始时间
     */
    private Date begineTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 送积分值
     */
    private String givePoint;
    /**
     * 启用标志
     */
    private Boolean isEnable;

    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    public Date getBegineTime()
    {
        return begineTime;
    }

    public void setBegineTime(Date begineTime)
    {
        this.begineTime = begineTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getGivePoint()
    {
        return givePoint;
    }

    public void setGivePoint(String givePoint)
    {
        this.givePoint = givePoint;
    }

    public Boolean getIsEnable()
    {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable)
    {
        this.isEnable = isEnable;
    }
}
