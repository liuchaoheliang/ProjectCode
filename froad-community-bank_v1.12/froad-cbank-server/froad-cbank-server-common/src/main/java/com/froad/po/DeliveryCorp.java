package com.froad.po;


/**
 * 物流公司
 */
public class DeliveryCorp implements java.io.Serializable {

	
	private Long id;//物流公司ID
	private String clientId;// 客户端ID
	private String name;//名称 
	private String url;//网址 
	private Integer orderValue;//排序
	private Boolean isEnable;//是否启用
	private String corpCode;//物流公司编号

	// Constructors

	/** default constructor */
	public DeliveryCorp() {
	}

	/** full constructor */
	public DeliveryCorp(String clientId, String name, String url, Integer orderValue) {
		this.clientId = clientId;
		this.name = name;
		this.url = url;
		this.orderValue = orderValue;
	}
    
	
	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}