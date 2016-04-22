package com.froad.db.chonggou.entity;

import java.util.Date;

import net.sf.oval.constraint.MaxLength;

public class MerchantAccount implements java.io.Serializable{

	// Fields

	private Long id;
	private Date createTime;
	@MaxLength(20)
	private String clientId;
	@MaxLength(20)
	private String merchantId;
	@MaxLength(20)
	private String outletId;
	@MaxLength(32)
	private String acctName;
	@MaxLength(32)
	private String acctNumber;
	@MaxLength(32)
	private String acctType;
	@MaxLength(64)
	private String openingBank;
	private Boolean isDelete;

	// Constructors

	/** default constructor */
	public MerchantAccount() {
	}

	/** minimal constructor */
	public MerchantAccount(String clientId, String merchantId, String outletId, String acctName, String acctNumber, String acctType) {
		this.clientId = clientId;
		this.merchantId = merchantId;
		this.outletId = outletId;
		this.acctName = acctName;
		this.acctNumber = acctNumber;
		this.acctType = acctType;
	}

	/** full constructor */
	public MerchantAccount(String clientId, String merchantId, String outletId, String acctName, String acctNumber, String acctType, String openingBank, Boolean isDelete) {
		this.clientId = clientId;
		this.merchantId = merchantId;
		this.outletId = outletId;
		this.acctName = acctName;
		this.acctNumber = acctNumber;
		this.acctType = acctType;
		this.openingBank = openingBank;
		this.isDelete = isDelete;
	}

	// Property accessors
	
	
	
	public Long getId() {
		return this.id;
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
	
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	
	public String getOutletId() {
		return this.outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	
	public String getAcctName() {
		return this.acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	
	public String getAcctNumber() {
		return this.acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	
	public String getAcctType() {
		return this.acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	
	public String getOpeningBank() {
		return this.openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}

