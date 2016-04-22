package com.froad.CB.po;

public class TransGoodsAttribute {

	private String element;//属性值
	
	private String transId;//交易ID
	
	private String goodsRackAttributeId;//商品属性ID
	
	private GoodsRackAttribute goodsRackAttribute;
	
	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element == null ? null : element.trim();
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getGoodsRackAttributeId() {
		return goodsRackAttributeId;
	}

	public void setGoodsRackAttributeId(String goodsRackAttributeId) {
		this.goodsRackAttributeId = goodsRackAttributeId;
	}

	public GoodsRackAttribute getGoodsRackAttribute() {
		return goodsRackAttribute;
	}

	public void setGoodsRackAttribute(GoodsRackAttribute goodsRackAttribute) {
		this.goodsRackAttribute = goodsRackAttribute;
	}
}