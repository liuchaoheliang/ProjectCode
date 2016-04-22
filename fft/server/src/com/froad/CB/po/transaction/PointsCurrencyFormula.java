package com.froad.CB.po.transaction;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：积分现金兑换规则
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2012
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Dec 13, 2012 5:41:07 PM
 */
public class PointsCurrencyFormula extends Rule implements Serializable{
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String fundsChannelId;
	
	private String type;

	private Integer id;

	private String remark;

	private String updateTime;

	private String createTime;

	private String state;

	private Integer currency;

	private Integer points;

	private String merchantId;

	private String description;

	private String sellerId;
	
	private List<SellerRuleDetail> sellerRuleDetailList;

	
	
	public List<SellerRuleDetail> getSellerRuleDetailList() {
		return sellerRuleDetailList;
	}

	public void setSellerRuleDetailList(List<SellerRuleDetail> sellerRuleDetailList) {
		this.sellerRuleDetailList = sellerRuleDetailList;
	}

	public String getFundsChannelId() {
		return fundsChannelId;
	}

	public void setFundsChannelId(String fundsChannelId) {
		this.fundsChannelId = fundsChannelId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	@Override
	public String toString() {
		return "PointsCurrencyFormula [createTime=" + createTime
				+ ", currency=" + currency + ", description=" + description
				+ ", id=" + id + ", merchantId=" + merchantId + ", points="
				+ points + ", remark=" + remark + ", sellerId=" + sellerId
				+ ", state=" + state + ", updateTime=" + updateTime + "]";
	}

}