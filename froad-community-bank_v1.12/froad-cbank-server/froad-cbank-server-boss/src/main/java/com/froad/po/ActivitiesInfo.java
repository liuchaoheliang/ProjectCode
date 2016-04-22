package com.froad.po;

import java.io.Serializable;

public class ActivitiesInfo implements Serializable{

    
    /**
     * 
     */
    private static final long serialVersionUID = 31032699806689667L;
    
    private Long id;               //主键id
    private String clientId;         //客户端ID 
    private String activitiesName;  //活动名称
    private Integer points;         //赠送积分数量
    private String status;       //状态 0未生效,1已生效,2已作废
    
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
    public String getActivitiesName() {
        return activitiesName;
    }
    public void setActivitiesName(String activitiesName) {
        this.activitiesName = activitiesName;
    }
    public Integer getPoints() {
        return points;
    }
    public void setPoints(Integer points) {
        this.points = points;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
