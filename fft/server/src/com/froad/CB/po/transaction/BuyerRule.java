package com.froad.CB.po.transaction;

import java.io.Serializable;

import com.froad.CB.common.Pager;

/**
 * 类描述：买家规则
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2012
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Dec 13, 2012 5:40:40 PM
 */
public class BuyerRule extends Pager implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String ruleDesc;//规则描述
	private String currency;//货币单位，例如：RMB
	private String currencyFormula;//货币规则公式 例如：x=(M*0.85)
	private String pointsAccountType;//
	private String pointsFormula;//积分规则公式 例如：x=(M*0.85)
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;
	private String updateTime;
	private String remark;

	public BuyerRule() {
	}

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyFormula() {
		return currencyFormula;
	}

	public void setCurrencyFormula(String currencyFormula) {
		this.currencyFormula = currencyFormula;
	}

	public String getPointsAccountType() {
		return pointsAccountType;
	}

	public void setPointsAccountType(String pointsAccountType) {
		this.pointsAccountType = pointsAccountType;
	}

	public String getPointsFormula() {
		return pointsFormula;
	}

	public void setPointsFormula(String pointsFormula) {
		this.pointsFormula = pointsFormula;
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

	@Override
	public String toString() {
		return "BuyerRule [createTime=" + createTime + ", currency=" + currency
				+ ", currencyFormula=" + currencyFormula + ", id=" + id
				+ ", pointsAccountType=" + pointsAccountType
				+ ", pointsFormula=" + pointsFormula + ", remark=" + remark
				+ ", ruleDesc=" + ruleDesc + ", state=" + state
				+ ", updateTime=" + updateTime + "]";
	}

}
