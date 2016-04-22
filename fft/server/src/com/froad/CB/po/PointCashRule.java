package com.froad.CB.po;

public class PointCashRule {
	/**
	 * 类描述：
	 * @author  童雪亮
	 * @version 创建时间：2013-2-25 下午03:58:00
	 */
	private Integer id;				//主键 自增100001001开始
	private String ruleDesc;		//规则描述
	private String pointType;		//积分类型 1-分分通积分 2-珠海银行积分
	private String pointValue;  	//积分值
	private String cashValue;  		//现金值
	private String state;			//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;		//创建时间
	private String updateTime;		//更新时间
	private String remark;			//备注
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getPointValue() {
		return pointValue;
	}
	public void setPointValue(String pointValue) {
		this.pointValue = pointValue;
	}
	public String getCashValue() {
		return cashValue;
	}
	public void setCashValue(String cashValue) {
		this.cashValue = cashValue;
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
