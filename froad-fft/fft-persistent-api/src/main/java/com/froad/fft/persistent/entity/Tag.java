package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.DataState;
import com.froad.fft.persistent.common.enums.TagType;

/**
 * 标签
 *
 * @author FQ
 */
public class Tag extends BaseEntity
{

    private String name;//名称
    private TagType type;//类型
    private String icon;// 图标
    private String description;//备注
    private Integer orderValue;//排序

    private DataState dataState;//数据状态：有效、删除

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

    public Integer getOrderValue()
    {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue)
    {
        this.orderValue = orderValue;
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

    public DataState getDataState()
    {
        return dataState;
    }

    public void setDataState(DataState dataState)
    {
        this.dataState = dataState;
    }


}
