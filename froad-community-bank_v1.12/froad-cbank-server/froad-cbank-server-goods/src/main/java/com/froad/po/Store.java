package com.froad.po;


public class Store implements java.io.Serializable {
    private static final long serialVersionUID = 8982183162965078825L;
    
	/**
	 * 客户端ID
	 */
	private String clientId;
	
	/**
	 * 商户ID
	 */
	private String merchantId;
	
	/**
	 * 商品ID
	 */
	private String productId;
	
	/**
	 * 库存数
	 */
	private Integer store;
	
	/**
	 * 库存操作数
	 */
	private Integer reduceStore;

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Integer getReduceStore() {
		return reduceStore;
	}

	public void setReduceStore(Integer reduceStore) {
		this.reduceStore = reduceStore;
	}
	
}
