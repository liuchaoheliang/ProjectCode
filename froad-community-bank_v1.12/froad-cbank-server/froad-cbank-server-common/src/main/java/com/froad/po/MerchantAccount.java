package com.froad.po;

import java.util.Date;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.guard.Guarded;

/**
 * CbMerchantAccount po. 
 */

@Guarded
public class MerchantAccount implements java.io.Serializable {

	/**
	 * 序列化.
	 */
	private static final long serialVersionUID = 3184312171048542121L;
	// Fields

	private Long id;
	private Date createTime;
	@MaxLength(value = 20, message = "客户端id不能超过{max}个字符")
	private String clientId;
	@MaxLength(value = 20, message = "商户id不能超过{max}个字符")
	private String merchantId;
	@MaxLength(value = 20, message = "门店id不能超过{max}个字符")
	private String outletId;
	@MaxLength(value = 64, message = "收款账户名不能超过{max}个汉字")
	private String acctName;
	@MaxLength(value = 32, message = "账号不能超过{max}个字符")
	private String acctNumber;
//	@Range(min = 1, max = 2, message = "账号类型必须为{min}到{max}以内")
	@MatchPattern(pattern="1|2", message="账号类型必须为1或者2")
	private String acctType;
	@MaxLength(value = 64, message = "开户行不能超过{max}个字符")
	private String openingBank;
	@MatchPattern(pattern="0|1|2", message="同步状态必须为0或者1或者2")
	private String synchState;
	@MatchPattern(pattern="0|1|2|3|4", message="审核状态必须为0或者1或者2或者3或者4")
	private String auditState;
	private Boolean isDelete;
	// 多查询出这个字段信息
	private String merchantName;
	
	//mac值.
	@MaxLength(value = 16, message = "mac不能超过{max}个字符")
	private String mac;	

	// Constructors

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

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

	
	public String getSynchState() {
		return synchState;
	}

	public void setSynchState(String synchState) {
		this.synchState = synchState;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}