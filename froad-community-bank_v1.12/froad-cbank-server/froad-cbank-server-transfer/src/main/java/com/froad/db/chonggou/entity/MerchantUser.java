package com.froad.db.chonggou.entity;

import java.util.Date;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.MaxLength;


/**
 * CbMerchantUser po. 
 */


public class MerchantUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date createTime;
	@MaxLength(20)
	private String clientId;
	@MaxLength(20)
	private String merchantId;
	@MaxLength(20)
	private String outletId;
	private Long merchantRoleId;
	@MaxLength(32)
	private String username;
	@MaxLength(32)
	private String password;
	@Email
	private String email;
	@MaxLength(16)
	private String phone;
	private Boolean isRest;
	private Boolean isDelete;
	@MaxLength(32)
	private String realname;
	private String merchantName;
	private String outletName;
	private String orgCode;

	// Constructors

	/** default constructor */
	public MerchantUser() {
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

	
	public Long getMerchantRoleId() {
		return this.merchantRoleId;
	}

	public void setMerchantRoleId(Long merchantRoleId) {
		this.merchantRoleId = merchantRoleId;
	}

	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Boolean getIsRest() {
		return isRest;
	}

	public void setIsRest(Boolean isRest) {
		this.isRest = isRest;
	}

	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}


	public String getOrgCode() {
		return orgCode;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}