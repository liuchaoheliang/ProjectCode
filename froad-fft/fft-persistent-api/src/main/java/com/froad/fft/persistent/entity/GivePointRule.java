package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.GivePointRuleType;

import java.util.Date;

/**
 * 送积分规则
 *
 * @author FQ
 */
public class GivePointRule extends BaseEntity
{
    private String name;//名称
    private GivePointRuleType type;//类型
    private String pointValue;//赠送积分值
    private Date activeTime;//规则生效时间
    private Date expireTime;//规则失效时间
    private String remark;//备注

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
