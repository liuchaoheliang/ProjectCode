package com.froad.db.chonggou.entity;

import java.io.Serializable;
import java.util.Date;

public class ActivitiesInfo implements Serializable{

    
    /**
     * 
     */
    private static final long serialVersionUID = 31032699806689667L;
    
    private Long id;               //主键id
    private String clientId;         //客户端ID 
    private String activitiesName;  //活动名称
    private String activitiesType;  //活动类型 
    private Integer points;         //赠送积分数量
    private String vipPrice;        // VIP价格
    private Date beginTime;         //活动开始时间
    private Date endTime;           //活动结束时间
    private Boolean isEnable;       //是否启用 
    private String remark;          //备注

    // Constructors

    /** default constructor */
    public ActivitiesInfo() {
    }

    /** minimal constructor */
    public ActivitiesInfo(String clientId, String activitiesName, String activitiesType, Date beginTime, Date endTime, Boolean isEnable) {
        this.clientId = clientId;
        this.activitiesName = activitiesName;
        this.activitiesType = activitiesType;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.isEnable = isEnable;
    }

    /** full constructor */
    public ActivitiesInfo(String clientId, String activitiesName, String activitiesType, Integer points, String vipPrice, Date beginTime, Date endTime, Boolean isEnable, String remark) {
        this.clientId = clientId;
        this.activitiesName = activitiesName;
        this.activitiesType = activitiesType;
        this.points = points;
        this.vipPrice = vipPrice;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.isEnable = isEnable;
        this.remark = remark;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getActivitiesName() {
        return this.activitiesName;
    }

    public void setActivitiesName(String activitiesName) {
        this.activitiesName = activitiesName;
    }

    public String getActivitiesType() {
        return this.activitiesType;
    }

    public void setActivitiesType(String activitiesType) {
        this.activitiesType = activitiesType;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getVipPrice() {
        return this.vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Date getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
