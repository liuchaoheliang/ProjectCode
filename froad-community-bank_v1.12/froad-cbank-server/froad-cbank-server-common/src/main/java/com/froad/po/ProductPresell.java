package com.froad.po;

import java.util.Date;

/**
 * 
 * @author wangyan
 *
 */
public class ProductPresell implements java.io.Serializable {

	// Fields

	/**
     * 
     */
    private static final long serialVersionUID = 4441400980807348531L;
    
    private Long id;
	private Date createTime;
	private String clientId;
	private String productId;
	private String productSupplier;
	private Integer maxPerOutlet;
	private Date deliveryStartTime;
	private Date deliveryEndTime;
	private Integer trueBuyerNumber;
	private Integer virtualBuyerNumber;
	private Boolean clusterState;
	private Boolean clusterType;
	private Date expireStartTime;
	private Date expireEndTime;

	// Constructors

	/** default constructor */
	public ProductPresell() {
	}

	/** minimal constructor */
	public ProductPresell(Date createTime, String clientId, Date deliveryStartTime, Date deliveryEndTime, Boolean clusterState, Boolean clusterType, Date expireStartTime, Date expireEndTime) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.deliveryStartTime = deliveryStartTime;
		this.deliveryEndTime = deliveryEndTime;
		this.clusterState = clusterState;
		this.clusterType = clusterType;
		this.expireStartTime = expireStartTime;
		this.expireEndTime = expireEndTime;
	}

	/** full constructor */
	public ProductPresell(Date createTime, String clientId, String productId, String productSupplier, Integer deliveryMoney, Integer maxPerOutlet, Date deliveryStartTime, Date deliveryEndTime, Integer trueBuyerNumber, Integer virtualBuyerNumber, Boolean clusterState, Boolean clusterType, Date expireStartTime, Date expireEndTime) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.productId = productId;
		this.productSupplier = productSupplier;
		this.maxPerOutlet = maxPerOutlet;
		this.deliveryStartTime = deliveryStartTime;
		this.deliveryEndTime = deliveryEndTime;
		this.trueBuyerNumber = trueBuyerNumber;
		this.virtualBuyerNumber = virtualBuyerNumber;
		this.clusterState = clusterState;
		this.clusterType = clusterType;
		this.expireStartTime = expireStartTime;
		this.expireEndTime = expireEndTime;
	}

	// Property accessors

	public Long getId() {
        return id;
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
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }

    public Integer getMaxPerOutlet() {
        return maxPerOutlet;
    }

    public void setMaxPerOutlet(Integer maxPerOutlet) {
        this.maxPerOutlet = maxPerOutlet;
    }

    public Date getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(Date deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    public Date getDeliveryEndTime() {
        return deliveryEndTime;
    }

    public void setDeliveryEndTime(Date deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
    }

    public Integer getTrueBuyerNumber() {
        return trueBuyerNumber;
    }

    public void setTrueBuyerNumber(Integer trueBuyerNumber) {
        this.trueBuyerNumber = trueBuyerNumber;
    }

    public Integer getVirtualBuyerNumber() {
        return virtualBuyerNumber;
    }

    public void setVirtualBuyerNumber(Integer virtualBuyerNumber) {
        this.virtualBuyerNumber = virtualBuyerNumber;
    }

    public Boolean getClusterState() {
        return clusterState;
    }

    public void setClusterState(Boolean clusterState) {
        this.clusterState = clusterState;
    }

    public Boolean getClusterType() {
        return clusterType;
    }

    public void setClusterType(Boolean clusterType) {
        this.clusterType = clusterType;
    }

    public Date getExpireStartTime() {
        return expireStartTime;
    }

    public void setExpireStartTime(Date expireStartTime) {
        this.expireStartTime = expireStartTime;
    }

    public Date getExpireEndTime() {
        return expireEndTime;
    }

    public void setExpireEndTime(Date expireEndTime) {
        this.expireEndTime = expireEndTime;
    }
    
}