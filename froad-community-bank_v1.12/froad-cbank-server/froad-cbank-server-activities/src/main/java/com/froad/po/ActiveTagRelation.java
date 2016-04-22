package com.froad.po;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ActiveTagRelation implements Serializable {
    /**
     * 主键ID
    */
    private Long id;
    /**
     * 客户端ID
    */     
    private String clientId;
    /**
     * 创建时间
    */
    private Date createTime;
    /**
     * 更新时间
    */
    private Date updateTime;
    /**
     * 活动ID
    */
    private String activeId;
    /**
     * 项⽬类型 0-商户1-门店(预留) 2-商品
    */
    private String itemType;
    /**
     * 商户/门店/商品标签ID
    */
    private String itemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    
    /** default constructor */
    public ActiveTagRelation() 
    {
        
    }

    public ActiveTagRelation(Long id, String clientId, Date createTime,
            Date updateTime, String activeId, String itemType, String itemId) {
        super();
        this.id = id;
        this.clientId = clientId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.activeId = activeId;
        this.itemType = itemType;
        this.itemId = itemId;
    }
}
