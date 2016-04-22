package com.froad.CB.po;

import com.froad.CB.common.Pager;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-6-3  
 * @version 1.0
 */
public class MerchantUserSeller extends Pager {
	private Integer id;
	private String merchantId;//商户ID
	private String merchantUserId;//操作员ID
	private String sellerId;//商户user_id
	private String reserveFieldOne;//备用字段1
	private String reserveFieldTwo;//备用字段2
	private String reserveFieldThree;//备用字段3
	private String reserveFieldFour;//备用字段4
	private Integer reserveFieldFive;//备用字段5
	private String state;//状态
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private	String remark;//备注
	
	
	private String sellerType;  //卖家类型
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getReserveFieldOne() {
		return reserveFieldOne;
	}
	public void setReserveFieldOne(String reserveFieldOne) {
		this.reserveFieldOne = reserveFieldOne;
	}
	public String getReserveFieldTwo() {
		return reserveFieldTwo;
	}
	public void setReserveFieldTwo(String reserveFieldTwo) {
		this.reserveFieldTwo = reserveFieldTwo;
	}
	public String getReserveFieldThree() {
		return reserveFieldThree;
	}
	public void setReserveFieldThree(String reserveFieldThree) {
		this.reserveFieldThree = reserveFieldThree;
	}
	public String getReserveFieldFour() {
		return reserveFieldFour;
	}
	public void setReserveFieldFour(String reserveFieldFour) {
		this.reserveFieldFour = reserveFieldFour;
	}
	public Integer getReserveFieldFive() {
		return reserveFieldFive;
	}
	public void setReserveFieldFive(Integer reserveFieldFive) {
		this.reserveFieldFive = reserveFieldFive;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	
	
}
