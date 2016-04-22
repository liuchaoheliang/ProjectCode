package com.froad.fft.persistent.entity;

/**
 * 库存日志
 * @author FQ
 *
 */
public class StockPileLog extends BaseEntity {
	
	public enum Type{
		init_income,//初始入库
		replenish_income,//补货入库
		sale_outcome,//销售提货出库
		return_income,//退货入库
		swap_income,//换货入库
		swap_outcome,//换货出库
		other//其它
	}
	
	public Type type;// 类型
	private Long productId;//商品
	private Long merchantOutletId;//名店ID
	private Integer quantity;//数量 
	private String content;//内容 （拼接组织一条描述）
	private String operator;//操作人
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getMerchantOutletId() {
		return merchantOutletId;
	}
	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
