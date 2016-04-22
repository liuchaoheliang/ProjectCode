package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.AccountType;


	/**
	 * 类描述：商户账户表
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月25日 下午2:50:51 
	 */
public class MerchantAccount extends BaseEntity{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Long merchantId;//商户ID
	
	private String acctName;//账户名
	
	private String acctNumber;//帐号
	
	private AccountType acctType;//账户类型
	
	private Boolean isEnabled;//是否启用
	
	private Long fundsChannelId;//资金渠道

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public AccountType getAcctType() {
		return acctType;
	}

	public void setAcctType(AccountType acctType) {
		this.acctType = acctType;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Long getFundsChannelId() {
		return fundsChannelId;
	}

	public void setFundsChannelId(Long fundsChannelId) {
		this.fundsChannelId = fundsChannelId;
	}
	
	
}
