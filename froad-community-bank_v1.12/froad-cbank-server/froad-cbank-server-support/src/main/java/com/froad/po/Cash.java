package com.froad.po;


/**
 * CbCash entity. @author MyEclipse Persistence Tools
 */
public class Cash implements java.io.Serializable {

	private Long id;//主键ID 
	private String cashCn;//代金券批次号
	private String cashCode;// 代金券ID 
	private Integer money;//面值 
	private Integer usedMoney;//已用金额
	private Boolean isEnabled;//是否启用
	private Boolean isUsed;//是否已经使用
	private String orderId;//以下四项在使用后有
	private String subOrderId;
	private String merchantId;
	private String productId;

	// Constructors

	/** default constructor */
	public Cash() {
	}

	/** minimal constructor */
	public Cash(String cashCn, String cashCode, Integer money, Integer usedMoney, Boolean isEnabled, Boolean isUsed) {
		this.cashCn = cashCn;
		this.cashCode = cashCode;
		this.money = money;
		this.usedMoney = usedMoney;
		this.isEnabled = isEnabled;
		this.isUsed = isUsed;
	}

	/** full constructor */
	public Cash(String cashCn, String cashCode, Integer money, Integer usedMoney, Boolean isEnabled, Boolean isUsed, String orderId, String subOrderId, String merchantId, String productId) {
		this.cashCn = cashCn;
		this.cashCode = cashCode;
		this.money = money;
		this.usedMoney = usedMoney;
		this.isEnabled = isEnabled;
		this.isUsed = isUsed;
		this.orderId = orderId;
		this.subOrderId = subOrderId;
		this.merchantId = merchantId;
		this.productId = productId;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCashCn() {
		return this.cashCn;
	}

	public void setCashCn(String cashCn) {
		this.cashCn = cashCn;
	}

	public String getCashCode() {
		return this.cashCode;
	}

	public void setCashCode(String cashCode) {
		this.cashCode = cashCode;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getUsedMoney() {
		return this.usedMoney;
	}

	public void setUsedMoney(Integer usedMoney) {
		this.usedMoney = usedMoney;
	}

	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSubOrderId() {
		return this.subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}