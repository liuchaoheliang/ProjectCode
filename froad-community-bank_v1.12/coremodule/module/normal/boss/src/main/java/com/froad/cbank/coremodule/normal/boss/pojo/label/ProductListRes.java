package com.froad.cbank.coremodule.normal.boss.pojo.label;

/**
 * 关联商品查询响应
 * 
 * @ClassName ProductListReq
 * @author zxl
 * @date 2015年10月28日 下午2:43:01
 */
public class ProductListRes {

	/**
	 * 权重关联ID
	 */
	private Long id;
	/**
	 * 商品ID
	 */
	private String productId;
	/**
	 * 权重 *
	 */
	private String weight;
	/**
	 * 更新时间 *
	 */
	private Long updateTime;
	/**
	 * 商品名称 *
	 */
	private String productName;
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 所属商户名称 *
	 */
	private String merchantName;
	/**
	 * 操作人 *
	 */
	private String operator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
