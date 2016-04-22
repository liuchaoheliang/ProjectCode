package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class VipProduct implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4572667647723188208L;
    
    private Long id;
    /**
     * VIP活动id
     */
    private String vipId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 客户端简称
     */
    private String clientName;
    /**
     * VIP规则名称
     */
    private String activitiesName;
    /**
     * VIP价格
     */
    private Integer vipPrice;
    /**
     * 状态 0未生效,1已生效,2已作废
     */
    private String status;
    /**
     * 设置了vip价的商品总数
     */
    private Integer count;
    /**
     * VIP特权介绍
     */
    private String remark;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getVipId() {
        return vipId;
    }
    public void setVipId(String vipId) {
        this.vipId = vipId;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public String getActivitiesName() {
        return activitiesName;
    }
    public void setActivitiesName(String activitiesName) {
        this.activitiesName = activitiesName;
    }
    
    public Integer getVipPrice() {
        return vipPrice;
    }
    public void setVipPrice(Integer vipPrice) {
        this.vipPrice = vipPrice;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
