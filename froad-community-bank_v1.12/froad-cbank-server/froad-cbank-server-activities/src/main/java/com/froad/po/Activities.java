package com.froad.po;

import java.util.Date;




/**
 * CbActivities entity. 
 */


public class Activities  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String clientId;
     private Date createTime;
     private String activitiesName;
     private Integer points;
     private Date beginTime;
     private Date endTime;
     private String status;
     private Integer count;


    // Constructors

    /** default constructor */
    public Activities() {
    }

    
    /** full constructor */
    public Activities(String clientId, Date createTime, String activitiesName, Integer points, Date beginTime, Date endTime, String status, Integer count) {
        this.clientId = clientId;
        this.createTime = createTime;
        this.activitiesName = activitiesName;
        this.points = points;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.status = status;
        this.count = count;
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
    
    

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    

    public String getActivitiesName() {
        return this.activitiesName;
    }
    
    public void setActivitiesName(String activitiesName) {
        this.activitiesName = activitiesName;
    }
    
    

    public Integer getPoints() {
        return this.points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
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
    
    

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    

    public Integer getCount() {
        return this.count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
   








}