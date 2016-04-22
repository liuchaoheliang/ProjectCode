package com.froad.db.chonggou.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductActivitiesInfo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1265477865036721829L;
    
    private Long activitiesId;  // 活动ID
    private String activitiesType; // 活动类型 0-VIP价 1-赠送积分
    
    @JSONField(name = "activities_id")
    public Long getActivitiesId() {
        return activitiesId;
    }
    
    @JSONField(name = "activities_id")
    public void setActivitiesId(Long activitiesId) {
        this.activitiesId = activitiesId;
    }
    
    @JSONField(name = "activities_type")
    public String getActivitiesType() {
        return activitiesType;
    }
    
    @JSONField(name = "activities_type")
    public void setActivitiesType(String activitiesType) {
        this.activitiesType = activitiesType;
    }
    
}
