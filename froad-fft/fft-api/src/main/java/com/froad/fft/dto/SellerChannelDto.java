package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 卖家资金渠道
 * @author FQ
 *
 */
public class SellerChannelDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private Long sellerId;//卖家ID
	private Long sellerRuleId;//卖家规则ID
	private Long fundsChannelId;//资金渠道ID
	private String accountName;//卖家账户名
	private String accountNumber;//卖家账户号 
	private Boolean isDefault;//是否是默认
	private String description;// 描述
	
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
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getSellerRuleId() {
		return sellerRuleId;
	}
	public void setSellerRuleId(Long sellerRuleId) {
		this.sellerRuleId = sellerRuleId;
	}
	public Long getFundsChannelId() {
		return fundsChannelId;
	}
	public void setFundsChannelId(Long fundsChannelId) {
		this.fundsChannelId = fundsChannelId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
