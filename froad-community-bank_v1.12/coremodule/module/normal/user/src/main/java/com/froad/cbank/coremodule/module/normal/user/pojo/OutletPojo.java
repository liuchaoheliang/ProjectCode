/**
 * <p>Project: cbank</p>
 * <p>module: coremodule</p>
 * <p>@version: Copyright © 2008 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;


/**
 * <p>
 * 标题: —— 标题
 * </p>
 * <p>
 * 说明: —— 简要描述职责、应用范围、使用注意事项等
 * </p>
 * <p>
 * 创建时间：2015-4-11下午4:15:19
 * </p>
 * <p>
 * 作者: 高峰
 * </p>
 * <p>
 * 版本: 1.0
 * </p>
 * <p>
 * 修订历史:（历次修订内容、修订人、修订时间等）
 * </p>
 */
public class OutletPojo {
	private String id;
	private String merchantId;
	private String merchantName;
	private String treePathName;
	private String outletName;
	private String outletFullname;
	private List<CategoryInfoPojo> categoryList;
	private List<TypeInfo> typeList;
	private String address;
	private String phone;
	private String description;
	private String preferDetails;
	private Integer storeCount;
	private String defaultImage; //图片地址
	private String businessHours;//营业时间
	private String discount;//优惠折扣
	
	private Boolean isCollected;//是否已收藏
	private Boolean isComment;	//是否已评价
	private String complaintPhone; //投诉电话
	private String typeInfo; //商户类型
	private String outletId;
	
  /**
   * 优惠折扣码
   */
	private String discountCode;
  /**
   * 优惠折扣比
   */
	private String discountRate;
	  
	/**
	 * preferStatus:是否支持惠付
	 */
	private Boolean preferStatus;
	
	/**
	 * starLevel:门店星级
	 */
	private String starLevel; 
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTreePathName() {
		return treePathName;
	}

	public void setTreePathName(String treePathName) {
		this.treePathName = treePathName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getOutletFullname() {
		return outletFullname;
	}

	public void setOutletFullname(String outletFullname) {
		this.outletFullname = outletFullname;
	}

	public List<CategoryInfoPojo> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryInfoPojo> categoryList) {
		this.categoryList = categoryList;
	}

	public List<TypeInfo> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TypeInfo> typeList) {
		this.typeList = typeList;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPreferDetails() {
		return preferDetails;
	}

	public void setPreferDetails(String preferDetails) {
		this.preferDetails = preferDetails;
	}

	public Integer getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(Integer storeCount) {
		this.storeCount = storeCount;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Boolean getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(Boolean isCollected) {
		this.isCollected = isCollected;
	}

	public Boolean getIsComment() {
		return isComment;
	}

	public void setIsComment(Boolean isComment) {
		this.isComment = isComment;
	}

	public String getComplaintPhone() {
		return complaintPhone;
	}

	public void setComplaintPhone(String complaintPhone) {
		this.complaintPhone = complaintPhone;
	}

	public String getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(String typeInfo) {
		this.typeInfo = typeInfo;
	}

	/**
	 * @author luwanquan
	 * @desc 商户类型-内部类
	 */
	public class TypeInfo {
		private String merchantTypeId;
		private String typeName;
		private String type;
		public String getMerchantTypeId() {
			return merchantTypeId;
		}
		public void setMerchantTypeId(String merchantTypeId) {
			this.merchantTypeId = merchantTypeId;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public Boolean getPreferStatus() {
		return preferStatus;
	}

	public void setPreferStatus(Boolean preferStatus) {
		this.preferStatus = preferStatus;
	}

	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	
}
