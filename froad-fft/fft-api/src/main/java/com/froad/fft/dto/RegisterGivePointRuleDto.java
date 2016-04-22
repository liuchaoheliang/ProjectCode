/**
 * 文件名称:RegisterGivePointRuleDto.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-22
 * 历史修改:  
 */
package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class RegisterGivePointRuleDto implements Serializable
{
    private Long id;
    private Date createTime;
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
