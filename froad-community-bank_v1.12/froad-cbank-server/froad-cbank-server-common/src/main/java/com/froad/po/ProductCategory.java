package com.froad.po;

/**
 * 
 * @author wangyan
 *
 */
public class ProductCategory implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7836084950418425909L;
    
    private Long id;
	private String clientId;
	private String name;
	private String treePath;
	private Long parentId;
	private Boolean isDelete;
	private String icoUrl;
	private Integer orderValue;
	private Boolean isMarket;  //是否参与类目营销
	private Boolean isMall;    //是否是精品商城商品类型
    private String bigIcoUrl;//大图片
	
    public Long getId() {
        return id;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTreePath() {
        return treePath;
    }
    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }
    public Long getParentId() {
        return parentId;
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
	public Boolean getIsMarket() {
		return isMarket;
	}
	public void setIsMarket(Boolean isMarket) {
		this.isMarket = isMarket;
	}
	public Boolean getIsMall() {
		return isMall;
	}
	public void setIsMall(Boolean isMall) {
		this.isMall = isMall;
	}

  
    public String getBigIcoUrl() {
		return bigIcoUrl;
	}


	public void setBigIcoUrl(String bigIcoUrl) {
		this.bigIcoUrl = bigIcoUrl;
	}

}