package com.froad.po;

import java.util.Date;

/**
 * CbActiveOrderDetail entity.
 */

public class ActiveOrderDetail implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;
	private Long id;
	private String clientId;
	private Date createTime;
	private Date updateTime;
	private String orderId;
	private String orderMarketType;
	private String activeId;
	private String type;
	private String subOrderId;
	private String productId;
	private String productName;
	private Integer productPrice;
	private Integer productVipPrice;
	private Integer normalPriceMarket;
	private Integer normalPriceCount;
	private Integer orgNormalPriceMarket;
	private Integer orgNormalPriceCount;
	private Integer vipPriceMarket;
	private Integer vipPriceCount;
	private Integer orgVipPriceMarket;
	private Integer orgVipPriceCount;
	private String ticketId;

	// Constructors

	/** default constructor */
	public ActiveOrderDetail() {
	}

	/** minimal constructor */
	public ActiveOrderDetail(String clientId, Date createTime, Date updateTime,
			String orderId, String orderMarketType, String activeId, String type) {
		this.clientId = clientId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.orderId = orderId;
		this.orderMarketType = orderMarketType;
		this.activeId = activeId;
		this.type = type;
	}


	/** full constructor */
	public ActiveOrderDetail(Long id, String clientId, Date createTime,
			Date updateTime, String orderId, String orderMarketType,
			String activeId, String type, String subOrderId, String productId,
			String productName, Integer productPrice,Integer productVipPrice,
			Integer normalPriceMarket, Integer normalPriceCount,
			Integer orgNormalPriceMarket, Integer orgNormalPriceCount,
			Integer vipPriceMarket, Integer vipPriceCount, 
			Integer orgVipPriceMarket, Integer orgVipPriceCount,
			String ticketId) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.orderId = orderId;
		this.orderMarketType = orderMarketType;
		this.activeId = activeId;
		this.type = type;
		this.subOrderId = subOrderId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productVipPrice =productVipPrice;
		this.normalPriceMarket = normalPriceMarket;
		this.normalPriceCount = normalPriceCount;
		this.orgNormalPriceMarket =orgNormalPriceMarket;
		this.orgNormalPriceCount = orgNormalPriceCount;
		this.vipPriceMarket = vipPriceMarket;
		this.vipPriceCount = vipPriceCount;
		this.orgVipPriceMarket = orgVipPriceMarket;
		this.orgVipPriceCount = orgVipPriceCount;
		this.ticketId = ticketId;
	}

	// Property accessors
	
	
	
	public Integer getOrgNormalPriceMarket() {
		return orgNormalPriceMarket;
	}

	public Integer getProductVipPrice() {
		return productVipPrice;
	}

	public void setProductVipPrice(Integer productVipPrice) {
		this.productVipPrice = productVipPrice;
	}

	public void setOrgNormalPriceMarket(Integer orgNormalPriceMarket) {
		this.orgNormalPriceMarket = orgNormalPriceMarket;
	}

	public Integer getOrgNormalPriceCount() {
		return orgNormalPriceCount;
	}

	public void setOrgNormalPriceCount(Integer orgNormalPriceCount) {
		this.orgNormalPriceCount = orgNormalPriceCount;
	}

	public Integer getOrgVipPriceMarket() {
		return orgVipPriceMarket;
	}

	public void setOrgVipPriceMarket(Integer orgVipPriceMarket) {
		this.orgVipPriceMarket = orgVipPriceMarket;
	}

	public Integer getOrgVipPriceCount() {
		return orgVipPriceCount;
	}

	public void setOrgVipPriceCount(Integer orgVipPriceCount) {
		this.orgVipPriceCount = orgVipPriceCount;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderMarketType() {
		return this.orderMarketType;
	}

	public void setOrderMarketType(String orderMarketType) {
		this.orderMarketType = orderMarketType;
	}

	public String getActiveId() {
		return this.activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubOrderId() {
		return this.subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getNormalPriceMarket() {
		return this.normalPriceMarket;
	}

	public void setNormalPriceMarket(Integer normalPriceMarket) {
		this.normalPriceMarket = normalPriceMarket;
	}

	public Integer getNormalPriceCount() {
		return this.normalPriceCount;
	}

	public void setNormalPriceCount(Integer normalPriceCount) {
		this.normalPriceCount = normalPriceCount;
	}

	public Integer getVipPriceMarket() {
		return this.vipPriceMarket;
	}

	public void setVipPriceMarket(Integer vipPriceMarket) {
		this.vipPriceMarket = vipPriceMarket;
	}

	public Integer getVipPriceCount() {
		return this.vipPriceCount;
	}

	public void setVipPriceCount(Integer vipPriceCount) {
		this.vipPriceCount = vipPriceCount;
	}

	public String getTicketId() {
		return this.ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}