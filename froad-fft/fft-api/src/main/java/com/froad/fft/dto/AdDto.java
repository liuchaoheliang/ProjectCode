package com.froad.fft.dto;

import com.froad.fft.enums.AdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告
 *
 * @author FQ
 */
public class AdDto implements Serializable
{

    private Long id;// ID
    private Date createTime; //创建日期

    private String title;//标题
    private AdType type;//类型
    private Date beginTime;//开始时间
    private Date endTime;//结束时间
    private String content;//内容
    private String path;//路径
    private String link;//链接
    private Boolean isEnabled;//是否启用
    private Boolean isBlankTarge;//是否在新窗口打开
    private Integer clickCount;//点击次数
    private Integer orderValue;//排序
    private Long adPositionId;//广告位ID
    

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public AdType getType()
    {
        return type;
    }

    public void setType(AdType type)
    {
        this.type = type;
    }

    public Date getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public Boolean getIsEnabled()
    {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled)
    {
        this.isEnabled = isEnabled;
    }

    public Boolean getIsBlankTarge()
    {
        return isBlankTarge;
    }

    public void setIsBlankTarge(Boolean isBlankTarge)
    {
        this.isBlankTarge = isBlankTarge;
    }

    public Integer getClickCount()
    {
        return clickCount;
    }

    public void setClickCount(Integer clickCount)
    {
        this.clickCount = clickCount;
    }

    public Integer getOrderValue()
    {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue)
    {
        this.orderValue = orderValue;
    }

    public Long getAdPositionId()
    {
        return adPositionId;
    }

    public void setAdPositionId(Long adPositionId)
    {
        this.adPositionId = adPositionId;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
