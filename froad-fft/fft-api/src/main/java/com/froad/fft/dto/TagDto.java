package com.froad.fft.dto;

import com.froad.fft.enums.TagType;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签
 *
 * @author FQ
 */
public class TagDto implements Serializable
{

    private Long id;
    private Date createTime;
    private String name;//名称
    private TagType type;//类型
    private String icon;// 图标
    private String description;//备注
    private Integer orderValue;//排序

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

    public TagType getType()
    {
        return type;
    }

    public void setType(TagType type)
    {
        this.type = type;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getOrderValue()
    {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue)
    {
        this.orderValue = orderValue;
    }
}
