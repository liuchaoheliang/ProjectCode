package com.froad.po;


/**
 * 商户审核配置信息
 * CbClientMerchantAudit entity. 
 */


public class ClientMerchantAudit implements java.io.Serializable {

	// Fields

	private Long id;			//主键id
	private String clientId;	//客户端id
	private String type;		//类型 1-审核 2-商户重置密码
	private String orgLevel;	//商户所属机构级别
	private String startOrgLevel;//起始机构级别
	private String endOrgLevel;	//终审机构级别
	private String startAuditOrgCode;//起始机构编号
	private String endAuditOrgCode;  //终审机构编号
	

	// Constructors

	/** default constructor */
	public ClientMerchantAudit() {
	}

	/** full constructor */
	public ClientMerchantAudit(String clientId, String type,String orgLevel, String startOrgLevel, String endOrgLevel) {
		this.clientId = clientId;
		this.type = type;
		this.orgLevel = orgLevel;
		this.startOrgLevel = startOrgLevel;
		this.endOrgLevel = endOrgLevel;
	}

	// Property accessors
	
	
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	public String getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	
	public String getStartOrgLevel() {
		return this.startOrgLevel;
	}

	public void setStartOrgLevel(String startOrgLevel) {
		this.startOrgLevel = startOrgLevel;
	}

	
	public String getEndOrgLevel() {
		return this.endOrgLevel;
	}

	public void setEndOrgLevel(String endOrgLevel) {
		this.endOrgLevel = endOrgLevel;
	}

	public String getStartAuditOrgCode() {
		return startAuditOrgCode;
	}

	public void setStartAuditOrgCode(String startAuditOrgCode) {
		this.startAuditOrgCode = startAuditOrgCode;
	}

	public String getEndAuditOrgCode() {
		return endAuditOrgCode;
	}

	public void setEndAuditOrgCode(String endAuditOrgCode) {
		this.endAuditOrgCode = endAuditOrgCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	
	
	
}