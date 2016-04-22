package com.froad.CB.po;

import com.froad.CB.common.Pager;

public class PresellDeliveryMap extends Pager{

	private Integer id;        //数据主键
	
	private Integer presellRackId;//预售商品ID
	private GoodsPresellRack goodsPresellRack;//关联查询出的商品信息
	private Integer deliveryId;//预售商品提取点ID
	private PresellDelivery presellDelivery;//关联查询出的提货点信息
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPresellRackId() {
		return presellRackId;
	}
	public void setPresellRackId(Integer presellRackId) {
		this.presellRackId = presellRackId;
	}
	public Integer getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}
	public GoodsPresellRack getGoodsPresellRack() {
		return goodsPresellRack;
	}
	public void setGoodsPresellRack(GoodsPresellRack goodsPresellRack) {
		this.goodsPresellRack = goodsPresellRack;
	}
	public PresellDelivery getPresellDelivery() {
		return presellDelivery;
	}
	public void setPresellDelivery(PresellDelivery presellDelivery) {
		this.presellDelivery = presellDelivery;
	}

}
