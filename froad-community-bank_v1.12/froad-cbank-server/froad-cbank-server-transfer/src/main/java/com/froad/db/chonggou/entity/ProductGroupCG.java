package com.froad.db.chonggou.entity;

import java.util.Date;

/**
 * 
 * @author wangyan
 *
 */
public class ProductGroupCG implements java.io.Serializable {

	// Fields

	/**
     * 
     */
    private static final long serialVersionUID = 3391375176338580003L;
    
    private Long id;
    private String clientId;
	private Date createTime;
	private String productId;
	private Integer trueBuyerNumber;
	private Integer virtualBuyerNumber;
	private Date expireStartTime;
	private Date expireEndTime;
	// Constructors

	/** default constructor */
	public ProductGroupCG() {
	}

	/** minimal constructor */
	public ProductGroupCG(Date createTime, String clientId, Date expireStartTime, Date expireEndTime) {
        this.createTime = createTime;
        this.clientId = clientId;
		this.expireStartTime = expireStartTime;
		this.expireEndTime = expireEndTime;
	}

	/** full constructor */
	public ProductGroupCG(Date createTime, String clientId, String productId, Integer trueBuyerNumber, Integer virtualBuyerNumber, Date expireStartTime, Date expireEndTime) {
		this.createTime = createTime;
		this.clientId = clientId;
		this.productId = productId;
		this.trueBuyerNumber = trueBuyerNumber;
		this.virtualBuyerNumber = virtualBuyerNumber;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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