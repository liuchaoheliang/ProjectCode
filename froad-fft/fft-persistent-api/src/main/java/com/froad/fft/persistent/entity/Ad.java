package com.froad.fft.persistent.entity;

import java.util.Date;

import com.froad.fft.persistent.common.enums.AdType;
import com.froad.fft.persistent.common.enums.DataState;

/**
 * 广告
 *
 * @author FQ
 */
public class Ad extends BaseEntity
{

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

    private DataState dataState;//数据状态：有效、删除

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

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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
