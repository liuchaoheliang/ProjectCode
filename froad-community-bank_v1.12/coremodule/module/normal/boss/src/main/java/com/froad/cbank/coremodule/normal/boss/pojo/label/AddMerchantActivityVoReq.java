package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月27日 下午3:36:20
 * @desc 商户推荐活动新增req
 */
public class AddMerchantActivityVoReq {
	
	  public long id;
	  @NotEmpty(value="客户端不能为空！")
	  public String clientId; 
	  @NotEmpty(value="活动名称不能为空！")
	  public String activityName; 
	  @NotEmpty(value="活动编号不能为空！")
	  public String activityNo; 
	  public String operator; 
	  @NotEmpty(value="描述不能为空！")
	  public String activityDesc;
	  public String status; 
	  public String activityType; 
	  public String logoUrl; 
	  public long createTime; 
	  public long updateTime;
	  
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	  
	  
}
