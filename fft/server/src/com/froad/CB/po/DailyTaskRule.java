package com.froad.CB.po;

/** 
 * @author TXL
 * @date 2013-2-4 上午09:47:29
 * @version 1.0
 */
public class DailyTaskRule {
	private Integer id;
	private String fate;//天数
	private String isContinuation;//是否是连续天数 0-否 1-是
	private String pointValue;//赠送积分数	
	private String state;//状态
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private	String remark;//备注
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFate() {
		return fate;
	}
	public void setFate(String fate) {
		this.fate = fate;
	}
	public String getIsContinuation() {
		return isContinuation;
	}
	public void setIsContinuation(String isContinuation) {
		this.isContinuation = isContinuation;
	}
	public String getPointValue() {
		return pointValue;
	}
	public void setPointValue(String pointValue) {
		this.pointValue = pointValue;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
