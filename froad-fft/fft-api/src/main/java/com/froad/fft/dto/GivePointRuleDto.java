/**
 * 文件名称:GivePointRuleDto.java
 * 文件描述: 送积分dto
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.dto;

import com.froad.fft.enums.GivePointRuleType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class GivePointRuleDto implements Serializable
{

    private Long id;// ID
    private Date createTime; //创建日期

    private String name;//名称
    private GivePointRuleType type;//类型
    private String pointValue;//赠送积分值
    private Date activeTime;//规则生效时间
    private Date expireTime;//规则失效时间
    private String remark;//备注

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public GivePointRuleType getType()
    {
        return type;
    }

    public void setType(GivePointRuleType type)
    {
        this.type = type;
    }

    public String getPointValue()
    {
        return pointValue;
    }

    public void setPointValue(String pointValue)
    {
        this.pointValue = pointValue;
    }

    public Date getActiveTime()
    {
        return activeTime;
    }

    public void setActiveTime(Date activeTime)
    {
        this.activeTime = activeTime;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
