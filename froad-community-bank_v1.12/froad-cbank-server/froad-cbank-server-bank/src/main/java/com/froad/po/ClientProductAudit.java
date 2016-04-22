package com.froad.po;


/**
 * 商品审核配置信息
 * CbClientProductAudit entity. 
 */


public class ClientProductAudit implements java.io.Serializable {

	// Fields

	private Long id;			//主键Id
	private String clientId;	//客户端Id 
	private String productType;	//商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)
	private String orgLevel;	//商品所属商户所属机构级别
	private String startOrgLevel;//起始机构级别
	private String endOrgLevel;	//终审机构级别
	private String startAuditOrgCode;//起始机构编号
	private String endAuditOrgCode;  //终审机构编号

	// Constructors

	/** default constructor */
	public ClientProductAudit() {
	}

	/** full constructor */
	public ClientProductAudit(String clientId, String productType, String orgLevel, String startOrgLevel, String endOrgLevel) {
		this.clientId = clientId;
		this.productType = productType;
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

	
	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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


	
	
	
}