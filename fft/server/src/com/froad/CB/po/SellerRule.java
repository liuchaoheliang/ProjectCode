package com.froad.CB.po;

/**
 * 类描述：卖家规则实体
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Apr 3, 2013 12:12:51 AM
 */
public class SellerRule {

	private Integer id;//主键

	private String ruleType;//规则类型：标识此规则应用于哪种类型的交易，与交易类型一致

	private String fftPointsRule;//针对分分通积分定制的规则：用于返利积分时的返积分

	private String cashRule;//针对现金定制的规则：用于直接优惠时的打折

	private String fftFeeRule;//方付通手续费规则：用于提现

	private String bankFeeRule;//银行手续费规则：用于提现

	private String state;//状态

	private String createTime;//创建时间

	private String updateTime;//修改时间

	private String remark;//备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType == null ? null : ruleType.trim();
	}

	public String getFftPointsRule() {
		return fftPointsRule;
	}

	public void setFftPointsRule(String fftPointsRule) {
		this.fftPointsRule = fftPointsRule == null ? null : fftPointsRule
				.trim();
	}

	public String getCashRule() {
		return cashRule;
	}

	public void setCashRule(String cashRule) {
		this.cashRule = cashRule == null ? null : cashRule.trim();
	}

	public String getFftFeeRule() {
		return fftFeeRule;
	}

	public void setFftFeeRule(String fftFeeRule) {
		this.fftFeeRule = fftFeeRule == null ? null : fftFeeRule.trim();
	}

	public String getBankFeeRule() {
		return bankFeeRule;
	}

	public void setBankFeeRule(String bankFeeRule) {
		this.bankFeeRule = bankFeeRule == null ? null : bankFeeRule.trim();
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
