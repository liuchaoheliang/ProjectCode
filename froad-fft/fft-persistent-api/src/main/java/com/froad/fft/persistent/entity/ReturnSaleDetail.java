package com.froad.fft.persistent.entity;

/**
 * 退\换货明细
 * @author FQ
 *
 */
public class ReturnSaleDetail extends BaseEntity {
	
	private Long returnSaleId;//退货ID
	
	private Long productId;//商品ID
	private Integer quantity;//数量
	private String securitiesNo;//券号
	
	public Long getReturnSaleId() {
		return returnSaleId;
	}
	public void setReturnSaleId(Long returnSaleId) {
		this.returnSaleId = returnSaleId;
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
	public String getSecuritiesNo() {
		return securitiesNo;
	}
	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}
	
}
