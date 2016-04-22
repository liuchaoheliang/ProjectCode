package com.froad.fft.dto;

import java.io.Serializable;
import java.util.Date;

import com.froad.fft.enums.SellerType;

/**
 * 卖家
 * @author FQ
 *
 */
public class SellerDto implements Serializable{
	
	private Long id;
	private Date createTime;
	private Long merchantId;//商家ID
	private SellerType sellerType;//卖家类型
	private String description;// 描述
	private Boolean isInner;//是否为方付通内部卖家
	
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
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public SellerType getSellerType() {
		return sellerType;
	}
	public void setSellerType(SellerType sellerType) {
		this.sellerType = sellerType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsInner() {
		return isInner;
	}
	public void setIsInner(Boolean isInner) {
		this.isInner = isInner;
	}
	
}
