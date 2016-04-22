package com.froad.CB.po;

import com.froad.CB.common.Pager;

/**
 * *******************************************************
 *<p> 工程: communityBusiness </p>
 *<p> 类名: PresellBuyInfo.java </p>
 *<p> 描述: *-- <b>购买预售商品的记录信息</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014-2-24 下午03:26:52 </p>
 ********************************************************
 */
public class PresellBuyInfo extends Pager{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;                 //主键
	private String memberCode;          //关联账户账务用户主键
	private String totalGoodsNum;       //购买的商品数量
	private Integer presellDeliveryId;    //关联选择提货点ID
	private PresellDelivery presellDelivery;
	private Integer goodsPresellRackId; //关联GoodsPresellRackId;
	private GoodsPresellRack goodsPresellRack;
	private Integer transId;           //关联交易Id;
	private String createTime;		//创建时间
	private String updateTime;		//更新时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getTotalGoodsNum() {
		return totalGoodsNum;
	}
	public void setTotalGoodsNum(String totalGoodsNum) {
		this.totalGoodsNum = totalGoodsNum;
	}
	public Integer getGoodsPresellRackId() {
		return goodsPresellRackId;
	}
	public void setGoodsPresellRackId(Integer goodsPresellRackId) {
		this.goodsPresellRackId = goodsPresellRackId;
	}
	public Integer getTransId() {
		return transId;
	}
	public void setTransId(Integer transId) {
		this.transId = transId;
	}
	public PresellDelivery getPresellDelivery() {
		return presellDelivery;
	}
	public void setPresellDelivery(PresellDelivery presellDelivery) {
		this.presellDelivery = presellDelivery;
	}
	public GoodsPresellRack getGoodsPresellRack() {
		return goodsPresellRack;
	}
	public void setGoodsPresellRack(GoodsPresellRack goodsPresellRack) {
		this.goodsPresellRack = goodsPresellRack;
	}
	public Integer getPresellDeliveryId() {
		return presellDeliveryId;
	}
	public void setPresellDeliveryId(Integer presellDeliveryId) {
		this.presellDeliveryId = presellDeliveryId;
	}
	
}
