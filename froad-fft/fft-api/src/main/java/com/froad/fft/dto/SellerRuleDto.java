package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

import com.froad.fft.enums.RuleType;

/**
 * 卖家规则
 * @author FQ
 *
 */
public class SellerRuleDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private String name;//规则名称
	private RuleType ruleType;//规则类型(标识此规则应用于哪种类型的交易，与交易类型一致)
	private Boolean isEnabled;//是否启用
	private String fftPointsRule;//返利积分时的返积分比例
	private String cashRule;//用于直接优惠时的打折比例
	private String fftFeeRule;//提现方付通手续费 如：5[1,50]
	private String bankFeeRule;//提现银行手续费 如：5[1,50]
	private String description;//描述
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RuleType getRuleType() {
		return ruleType;
	}
	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getFftPointsRule() {
		return fftPointsRule;
	}
	public void setFftPointsRule(String fftPointsRule) {
		this.fftPointsRule = fftPointsRule;
	}
	public String getCashRule() {
		return cashRule;
	}
	public void setCashRule(String cashRule) {
		this.cashRule = cashRule;
	}
	public String getFftFeeRule() {
		return fftFeeRule;
	}
	public void setFftFeeRule(String fftFeeRule) {
		this.fftFeeRule = fftFeeRule;
	}
	public String getBankFeeRule() {
		return bankFeeRule;
	}
	public void setBankFeeRule(String bankFeeRule) {
		this.bankFeeRule = bankFeeRule;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
