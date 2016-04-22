package com.froad.cbank.coremodule.module.normal.merchant.pojo;

/**
 * 营业额查询返回
 * 
 * @author Administrator
 *
 */
public class Query_Admin_Res {
	/**
	 * 主键
	 */
	private Long id; // required
	/**
	 * 客户端ID
	 */
	private String clientId; // required
	/**
	 * 商户ID
	 */
	private String merchantId; // required
	/**
	 * 年
	 */
	private String year; // required
	/**
	 * 月
	 */
	private String month; // required
	/**
	 * 月销售额
	 */
	private Double monthMoney; // required
	private Integer monthCount;
	/**
	 * 月团购订单数
	 */
	private Integer groupOrderCount; // required
	/**
	 * 月名优特惠订单数
	 */
	private Integer sellOrderCount; // required
	/**
	 * 月面对面订单数
	 */
	private Integer faceOrderCount; // required
	/**
	 * 商品ID
	 */
	private String productId; // required
	/**
	 * 最高月销售量
	 */
	private Integer maxCount; // required
	/**
	 * 月团购销售额
	 */
	private Double groupOrderMoney; // required
	/**
	 * 月名优特惠销售额
	 */
	private Double sellOrderMoney; // required
	/**
	 * 月面对面销售额
	 */
	private Double faceOrderMoney; // required
	/**
	 * 商品名称
	 */
	private String productName; // required
	/**
	 * 最高月销售额
	 */
	private Double maxMoney; // required
	private Integer outTime;
	/**
	 * 签约时间
	 */
	private long contractBegintime; // optional
	/**
	 * 签约到期时间
	 */
	private long contractEndtime; // optional

	public long getContractBegintime() {
		return contractBegintime;
	}

	public void setContractBegintime(long contractBegintime) {
		this.contractBegintime = contractBegintime;
	}

	public long getContractEndtime() {
		return contractEndtime;
	}

	public void setContractEndtime(long contractEndtime) {
		this.contractEndtime = contractEndtime;
	}

	public Integer getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}

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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getMonthMoney() {
		return monthMoney;
	}

	public void setMonthMoney(Double monthMoney) {
		this.monthMoney = monthMoney;
	}

	public Integer getGroupOrderCount() {
		return groupOrderCount;
	}

	public void setGroupOrderCount(Integer groupOrderCount) {
		this.groupOrderCount = groupOrderCount;
	}

	public Integer getSellOrderCount() {
		return sellOrderCount;
	}

	public void setSellOrderCount(Integer sellOrderCount) {
		this.sellOrderCount = sellOrderCount;
	}

	public Integer getFaceOrderCount() {
		return faceOrderCount;
	}

	public void setFaceOrderCount(Integer faceOrderCount) {
		this.faceOrderCount = faceOrderCount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public Double getGroupOrderMoney() {
		return groupOrderMoney;
	}

	public void setGroupOrderMoney(Double groupOrderMoney) {
		this.groupOrderMoney = groupOrderMoney;
	}

	public Double getSellOrderMoney() {
		return sellOrderMoney;
	}

	public void setSellOrderMoney(Double sellOrderMoney) {
		this.sellOrderMoney = sellOrderMoney;
	}

	public Double getFaceOrderMoney() {
		return faceOrderMoney;
	}

	public void setFaceOrderMoney(Double faceOrderMoney) {
		this.faceOrderMoney = faceOrderMoney;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Integer getOutTime() {
		return outTime;
	}

	public void setOutTime(Integer outTime) {
		this.outTime = outTime;
	}

}
