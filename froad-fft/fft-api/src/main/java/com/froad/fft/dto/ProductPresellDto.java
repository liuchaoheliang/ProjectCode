package com.froad.fft.dto;

import com.froad.fft.enums.ClusterState;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品预售
 * 
 * @author FQ
 * 
 */
public class ProductPresellDto  implements Serializable
{

	// 成团类型
	public enum ClusterType {

		/** 自动成团 */
		auto,

		/** 手动成团 */
		manual

	};

	private Long id;
	private Date createTime;
	private String title;// 预售标题
	private String summary;// 预售商品简介

	private Integer perNumber; // 每人限购
	private Integer perminNumber;// 最低购买
	private Date startTime;// 预售-开始
	private Date endTime;// 预售-结束
	private Date deliveryStartTime;// 提货开始时间
	private Date deliveryEndTime;// 提货结束时间

//	private String price;// 销售价

	private Integer clusteringNumber; // 成功成团数量（指此次成团的商品最低销售数量）
	private Integer trueBuyerNumber; // 实际购买商品数量
	private Integer virtualBuyerNumber; // 系统添加的虚拟购买商品数量（仅手动成团会出现）

	private ClusterState clusterState; // 是否成功成团
	private ClusterType clusterType; // 成团类型

	private String buyKnow;// 购买需知
	private String generalizeImage;//推广图片
	private String detailsImage;// 详情图片
	private String description;// 描述
    private Integer orderValue;//排序
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getPerNumber() {
		return perNumber;
	}
	public void setPerNumber(Integer perNumber) {
		this.perNumber = perNumber;
	}
	public Integer getPerminNumber() {
		return perminNumber;
	}
	public void setPerminNumber(Integer perminNumber) {
		this.perminNumber = perminNumber;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getDeliveryStartTime() {
		return deliveryStartTime;
	}
	public void setDeliveryStartTime(Date deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}
	public Date getDeliveryEndTime() {
		return deliveryEndTime;
	}
	public void setDeliveryEndTime(Date deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}
//	public String getPrice() {
//		return price;
//	}
//	public void setPrice(String price) {
//		this.price = price;
//	}
	public Integer getClusteringNumber() {
		return clusteringNumber;
	}
	public void setClusteringNumber(Integer clusteringNumber) {
		this.clusteringNumber = clusteringNumber;
	}
	public Integer getTrueBuyerNumber() {
		return trueBuyerNumber;
	}
	public void setTrueBuyerNumber(Integer trueBuyerNumber) {
		this.trueBuyerNumber = trueBuyerNumber;
	}
	public Integer getVirtualBuyerNumber() {
		return virtualBuyerNumber;
	}
	public void setVirtualBuyerNumber(Integer virtualBuyerNumber) {
		this.virtualBuyerNumber = virtualBuyerNumber;
	}

    public ClusterState getClusterState()
    {
        return clusterState;
    }

    public void setClusterState(ClusterState clusterState)
    {
        this.clusterState = clusterState;
    }

    public ClusterType getClusterType() {
		return clusterType;
	}
	public void setClusterType(ClusterType clusterType) {
		this.clusterType = clusterType;
	}
	public String getBuyKnow() {
		return buyKnow;
	}
	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}
	
	public String getGeneralizeImage() {
		return generalizeImage;
	}
	public void setGeneralizeImage(String generalizeImage) {
		this.generalizeImage = generalizeImage;
	}
	public String getDetailsImage() {
		return detailsImage;
	}
	public void setDetailsImage(String detailsImage) {
		this.detailsImage = detailsImage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    public Integer getOrderValue()
    {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue)
    {
        this.orderValue = orderValue;
    }
}
