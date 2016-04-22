package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品团购
 *
 * @author FQ
 */
public class ProductGroupDto implements Serializable
{

    private Long id;
    private Date createTime;
    private String title;//团购标题
    private String summary;//团购商品简介
    private String areaName;//地区名称
    private Integer perNumber;    //每人限购
    private Integer perminNumber;//最低购买

    private Date startTime;//团购-开始
    private Date endTime;//团购-结束

    //	private String price;//销售价

    private String buyKnow;//购买需知
    private String generalizeImage;//推广图片
    private String detailsImage;// 详情图片
    private String description;//描述
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public Integer getPerNumber()
    {
        return perNumber;
    }

    public void setPerNumber(Integer perNumber)
    {
        this.perNumber = perNumber;
    }

    public Integer getPerminNumber()
    {
        return perminNumber;
    }

    public void setPerminNumber(Integer perminNumber)
    {
        this.perminNumber = perminNumber;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    //	public String getPrice() {
    //		return price;
    //	}
    //	public void setPrice(String price) {
    //		this.price = price;
    //	}
    public String getBuyKnow()
    {
        return buyKnow;
    }

    public void setBuyKnow(String buyKnow)
    {
        this.buyKnow = buyKnow;
    }

    public String getGeneralizeImage()
    {
        return generalizeImage;
    }

    public void setGeneralizeImage(String generalizeImage)
    {
        this.generalizeImage = generalizeImage;
    }

    public String getDetailsImage()
    {
        return detailsImage;
    }

    public void setDetailsImage(String detailsImage)
    {
        this.detailsImage = detailsImage;
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
