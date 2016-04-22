package com.froad.cbank.coremodule.module.normal.user.pojo;

public class PointInfoPojo {

	  public String points; // required
	  public long time; // required
	  public String protocolType; // required
	  public String remark; // required
	  

	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	  
	  
}
