package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.NavigationPosition;

/**
 * 导航
 *
 * @author FQ
 */
public class Navigation extends BaseEntity
{

    private String name;// 名称
    private NavigationPosition position;// 位置
    private String url;// 链接地址;
    private Boolean isVisible;// 是否显示
    private Boolean isBlankTarget;// 是否在新窗口中打开
    private Integer orderValue;// 排序

    private Long clientId;//所属客户端

    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public NavigationPosition getPosition()
    {
        return position;
    }

    public void setPosition(NavigationPosition position)
    {
        this.position = position;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Boolean getIsVisible()
    {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public Boolean getIsBlankTarget()
    {
        return isBlankTarget;
    }

    public void setIsBlankTarget(Boolean isBlankTarget)
    {
        this.isBlankTarget = isBlankTarget;
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
