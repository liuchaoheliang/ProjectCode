package com.froad.cbank.coremodule.normal.boss.pojo.order;


/**
 * 运营发起退款请求Req
 * @author liaopeixin
 *	@date 2016年1月6日 下午5:43:23
 */
public class BusinessOrderRefundReq {
	/**
	 * 子订单编号
	 */
	private String subOrderId;
	/**
	 * 退款原因
	 */
    private String refundReason;
    /**
     * 商品ID
     */
    private String productId;
    /**
     * 退款数量
     */
	private Integer quantity;
	/**
	 * 客户端ID
	 */
	private String clientId;
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
