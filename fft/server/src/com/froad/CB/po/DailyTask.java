package com.froad.CB.po;

public class DailyTask {
    
    private Integer id;    
    private String userId;      //用户ID
    private String signNo;    	//签到次数
    private String lastSignTime;//最后签到时间
    private String state;    	//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
    private String createTime;  //创建时间
    private String updateTime;  //更新时间
    private String remark;		//备注
    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getUserId() {
        return userId;
    }

    
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    
    public String getSignNo() {
        return signNo;
    }

    
    public void setSignNo(String signNo) {
        this.signNo = signNo == null ? null : signNo.trim();
    }

    
    public String getLastSignTime() {
        return lastSignTime;
    }

    
    public void setLastSignTime(String lastSignTime) {
        this.lastSignTime = lastSignTime == null ? null : lastSignTime.trim();
    }

    
    public String getState() {
        return state;
    }

    
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    
    public String getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    
    public String getUpdateTime() {
        return updateTime;
    }

    
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}