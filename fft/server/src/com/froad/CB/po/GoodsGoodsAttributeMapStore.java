package com.froad.CB.po;
/** 
 * @author FQ 
 * @date 2013-1-29 下午01:29:24
 * @version 1.0
 * 商品属性存储
 */
public class GoodsGoodsAttributeMapStore {
	private String goodsId;//商品ID
	private String goodsAttributeId;//商品属性ID
	private String element;//属性值
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsAttributeId() {
		return goodsAttributeId;
	}
	public void setGoodsAttributeId(String goodsAttributeId) {
		this.goodsAttributeId = goodsAttributeId;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	
}
