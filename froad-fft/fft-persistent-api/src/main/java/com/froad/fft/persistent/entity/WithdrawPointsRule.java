package com.froad.fft.persistent.entity;


	/**
	 * 类描述：积分提现规则
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月25日 下午4:54:16 
	 */
public class WithdrawPointsRule extends BaseEntity{

		
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String name;//规则名称
	
	private String fftFeeRule;//方付通手续费规则

	private String bankFeeRule;//银行手续费规则
	
	private Boolean isEnabled;//是否启用
	
	private String description;//规则描述

	private Long clientId;//客户端编号

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

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}
