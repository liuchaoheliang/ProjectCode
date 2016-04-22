package com.froad.db.ahui.entity;

import java.util.Date;

import com.froad.fft.persistent.entity.BaseEntity;

/**
 * 商品团购
 *
 * @author FQ
 */
public class ProductGroup extends BaseEntity
{

    private String title;//团购标题
    private String summary;//团购商品简介
    private String areaName;//地区名称
    private Integer perNumber;    //每人限购
    private Integer perminNumber;//最低购买

    private Date startTime;//团购-开始
    private Date endTime;//团购-结束
    private Integer trueBuyerNumber; // 实际购买商品数量
    private Integer virtualBuyerNumber; // 系统添加的虚拟购买商品数量（仅手动成团会出现）

    private String buyKnow;//购买需知
    private String generalizeImage;//推广图片
    private String detailsImage;// 详情图片
    private String description;//描述
    private Integer orderValue;//排序
    private Date expireTime;//过期时间
    
    private String purchaseNotes;//购买须知(htm5使用)
    
    private String groupDetails;//团购详情(html5使用)
    private String merchantId;
    
    private String summaryHtml5;
    
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

    public Integer getTrueBuyerNumber()
    {
        return trueBuyerNumber;
    }

    public void setTrueBuyerNumber(Integer trueBuyerNumber)
    {
        this.trueBuyerNumber = trueBuyerNumber;
    }

    public Integer getVirtualBuyerNumber()
    {
        return virtualBuyerNumber;
    }

    public void setVirtualBuyerNumber(Integer virtualBuyerNumber)
    {
        this.virtualBuyerNumber = virtualBuyerNumber;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

	public String getPurchaseNotes() {
		return purchaseNotes;
	}

	public void setPurchaseNotes(String purchaseNotes) {
		this.purchaseNotes = purchaseNotes;
	}

	public String getGroupDetails() {
		return groupDetails;
	}

	public void setGroupDetails(String groupDetails) {
		this.groupDetails = groupDetails;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getSummaryHtml5() {
		return summaryHtml5;
	}

	public void setSummaryHtml5(String summaryHtml5) {
		this.summaryHtml5 = summaryHtml5;
	}
    
}
