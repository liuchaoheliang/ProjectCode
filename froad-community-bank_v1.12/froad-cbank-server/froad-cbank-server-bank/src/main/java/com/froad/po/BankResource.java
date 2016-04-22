package com.froad.po;



/**
 * 银行资源表po
 * CbBankResource entity. 
 */

public class BankResource  implements java.io.Serializable {


    // Fields    

     private Long id;//自增主键id
     private String clientId;//客户端id
     private String resourceName;//资源名称
     private Boolean resourceType;//是否按钮类型 0-菜单 1-按钮
     private Long parentResourceId;//父级资源id
     private Boolean status;//是否可用
     private String resourceUrl; //资源url
     private String resourceIcon; //资源图标
     private String treePath; //资源路径
     private Boolean isDelete;//是否删除0-未删除1-删除
     private String api;//资源接口
     private Integer orderValue;//资源排序


    // Constructors

    /** default constructor */
    public BankResource() {
    }

	/** minimal constructor */
    public BankResource(String clientId, Boolean resourceType, Long parentResourceId, Boolean status, String resourceUrl,String treePath,Boolean isDelete) {
        this.clientId = clientId;
        this.resourceType = resourceType;
        this.parentResourceId = parentResourceId;
        this.status = status;
        this.resourceUrl = resourceUrl;
        this.treePath= treePath;
        this.isDelete=isDelete;
    }
    
    /** full constructor */
    public BankResource(String clientId, String resourceName, Boolean resourceType, Long parentResourceId, Boolean status, String resourceUrl, String api,String resourceIcon, String treePath,Boolean isDelete,Integer orderValue) {
        this.clientId = clientId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.parentResourceId = parentResourceId;
        this.status = status;
        this.resourceUrl = resourceUrl;
        this.api = api;
        this.resourceIcon = resourceIcon;
        this.treePath = treePath;
        this.isDelete=isDelete;
        this.orderValue=orderValue;
    }
    
    // Property accessors

    
    
	public Long getId() {
		return id;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Boolean getResourceType() {
		return resourceType;
	}

	public void setResourceType(Boolean resourceType) {
		this.resourceType = resourceType;
	}

	public Long getParentResourceId() {
		return parentResourceId;
	}

	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getResourceIcon() {
		return resourceIcon;
	}

	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}

	
   
    
}