package com.froad.po;

/**
 * 
 * @author wangyan
 *
 */
public class ProductCategory implements java.io.Serializable {

	// Fields

	/**
     * 
     */
    private static final long serialVersionUID = -7720174188947817858L;
    
    private Long id;
	private String clientId;
	private String name;
	private String treePath;
	private Long parentId;
	private Boolean isDelete;
	private String icoUrl;
	private Integer orderValue;

	// Constructors

	/** default constructor */
	public ProductCategory() {
	}

	/** minimal constructor */
	public ProductCategory(String clientId, String name) {
		this.clientId = clientId;
		this.name = name;
	}

	/** full constructor */
	public ProductCategory(Long id, String clientId, String name, String treePath, Long parentId,Integer orderValue) {
	    this.id = id;
	    this.clientId = clientId;
		this.name = name;
		this.treePath = treePath;
		this.parentId = parentId;
		this.orderValue=orderValue;
	}

	// Property accessors
	public Long getId() {
        return id;
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

	public String getTreePath() {
		return this.treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
    
    public String getIcoUrl() {
        return icoUrl;
    }

    public void setIcoUrl(String icoUrl) {
        this.icoUrl = icoUrl;
    }

	public Integer getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
    
}