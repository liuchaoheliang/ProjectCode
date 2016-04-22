package com.froad.fft.persistent.entity;


/**
 * 交易详情
 * @author FQ
 *
 */
public class TransDetails extends BaseEntity {
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Long transId;//交易ID
	private Long productId;//商品ID
	private String productName;//商品名称
	private Integer quantity;//购买数量
	private String price;//金额(=单价*购买数量)
	private String single;//单价
	
	private Long supplyMerchantId;//商品所属商户Id
	private Long gatherMerchantId;//收款商户Id
	
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSingle() {
		return single;
	}
	public void setSingle(String single) {
		this.single = single;
	}
	public Long getSupplyMerchantId() {
		return supplyMerchantId;
	}
	public void setSupplyMerchantId(Long supplyMerchantId) {
		this.supplyMerchantId = supplyMerchantId;
	}
	public Long getGatherMerchantId() {
		return gatherMerchantId;
	}
	public void setGatherMerchantId(Long gatherMerchantId) {
		this.gatherMerchantId = gatherMerchantId;
	}
	
}
