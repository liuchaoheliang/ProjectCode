package com.froad.fft.persistent.entity;

/**
 * 类描述：积分返利的送分规则
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: 2014年3月25日 下午4:49:20
 */
public class PresentPointsRule extends BaseEntity {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String name;//规则名称

	private Long merchantId;//商户编号

	private String rule;//送分规则

	private Boolean isEnabled;//是否启用
	
	private String description;//规则描述

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
