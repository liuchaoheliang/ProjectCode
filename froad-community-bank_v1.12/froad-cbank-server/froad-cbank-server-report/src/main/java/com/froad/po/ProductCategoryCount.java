package com.froad.po;

public class ProductCategoryCount {
	private String type;	//类目
	private String typeName;	//类目
	private Long saleProductAmount; //商品总金额
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getSaleProductAmount() {
		return saleProductAmount;
	}
	public void setSaleProductAmount(Long saleProductAmount) {
		this.saleProductAmount = saleProductAmount;
	}
	
	
}
