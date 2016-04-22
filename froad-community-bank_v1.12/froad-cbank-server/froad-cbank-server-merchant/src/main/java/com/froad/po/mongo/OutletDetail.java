/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: OutletInfo.java
 * @Package com.froad.po.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月21日
 */

package com.froad.po.mongo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 * Title: OutletInfo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月21日 上午10:11:47
 */

public class OutletDetail implements java.io.Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2768432545166776169L;

	@JSONField(name = "_id", serialize = true, deserialize = true)
	private String id;
	
	@JSONField(name = "create_time", serialize = true, deserialize = true)
	private Long createTime;
	
	@JSONField(name = "client_id", serialize = true, deserialize = true)
	private String clientId;
	
	@JSONField(name = "merchant_id", serialize = true, deserialize = true)
	private String merchantId;
	
	@JSONField(name = "merchant_name", serialize = true, deserialize = true)
	private String merchantName;
	
	@JSONField(name = "area_id", serialize = true, deserialize = true)
	private Long areaId;
	
	@JSONField(name = "parent_area_id", serialize = true, deserialize = true)
	private Long parentAreaId;
	
	@JSONField(name = "default_image", serialize = true, deserialize = true)
	private String defaultImage;
	
	@JSONField(name = "tree_path_name", serialize = true, deserialize = true)
	private String treePathName;
	
	@JSONField(name = "location", serialize = true, deserialize = true)
	private Location location;
	
	@JSONField(name = "is_enable", serialize = true, deserialize = true)
	private Boolean isEnable;

	@JSONField(name = "outlet_name", serialize = true, deserialize = true)
	private String outletName;
	
	@JSONField(name = "outlet_fullname", serialize = true, deserialize = true)
	private String outletFullname;
	
	@JSONField(name = "category_info", serialize = true, deserialize = true)
	private List<CategoryInfo> categoryInfo;
	
	@JSONField(name = "type_info", serialize = true, deserialize = true)
	private List<TypeInfo> typeInfo;
	
//	@JSONField(name = "outlet_status", serialize = true, deserialize = true)
//	private Boolean outletStatus;
	
	@JSONField(name = "address", serialize = true, deserialize = true)
	private String address;
	
	@JSONField(name = "phone", serialize = true, deserialize = true)
	private String phone;
	
	@JSONField(name = "description", serialize = true, deserialize = true)
	private String description;
	
	@JSONField(name = "prefer_details", serialize = true, deserialize = true)
	private String preferDetails;
	
	@JSONField(name = "store_count", serialize = true, deserialize = true)
	private Integer storeCount;
	
	@JSONField( serialize = false, deserialize = true)
	private double dis;
	
	/**
	 * 评论星级
	 */
	@JSONField(name = "star_level", serialize = true, deserialize = true)
	private double starLevel = 0; // optional
	/**
	 * 1 星级 总数
	 */
	@JSONField(name = "one_level_count", serialize = true, deserialize = true)
	private Integer oneLevelCount = 0; // optional
	/**
	 * 2 星级 总数
	 */
	@JSONField(name = "two_level_count", serialize = true, deserialize = true)
	private Integer twoLevelCount = 0; // optional
	/**
	 * 3 星级 总数
	 */
	@JSONField(name = "three_level_count", serialize = true, deserialize = true)
	private Integer threeLevelCount = 0; // optional
	/**
	 * 4 星级 总数
	 */
	@JSONField(name = "four_level_count", serialize = true, deserialize = true)
	private Integer fourLevelCount = 0; // optional
	/**
	 * 5 星级 总数
	 */
	@JSONField(name = "five_level_count", serialize = true, deserialize = true)
	private Integer fiveLevelCount = 0;
	/**
	 * 是否默认门店
	 */
	@JSONField(name = "is_default", serialize = true, deserialize = true)
	private Boolean isDefault;
	
	/**
	 * 优惠折扣码
	 */
	@JSONField(name = "discount_code", serialize = true, deserialize = true)
	private String discountCode;
	
    /**
     * 优惠折扣比
     */
	@JSONField(name = "discount_rate", serialize = true, deserialize = true)
	private String discountRate;
	
	
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


	public OutletDetail() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}


	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
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


	public Long getAreaId() {
		return areaId;
	}


	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}


	
	
	public Long getParentAreaId() {
		return parentAreaId;
	}


	public void setParentAreaId(Long parentAreaId) {
		this.parentAreaId = parentAreaId;
	}


	public String getTreePathName() {
		return treePathName;
	}


	public void setTreePathName(String treePathName) {
		this.treePathName = treePathName;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public Boolean getIsEnable() {
		return isEnable;
	}


	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
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


	public List<CategoryInfo> getCategoryInfo() {
		return categoryInfo;
	}


	public void setCategoryInfo(List<CategoryInfo> categoryInfo) {
		this.categoryInfo = categoryInfo;
	}


	public List<TypeInfo> getTypeInfo() {
		return typeInfo;
	}


	public void setTypeInfo(List<TypeInfo> typeInfo) {
		this.typeInfo = typeInfo;
	}


//	public Boolean getOutletStatus() {
//		return outletStatus;
//	}
//
//
//	public void setOutletStatus(Boolean outletStatus) {
//		this.outletStatus = outletStatus;
//	}


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


	public double getDis() {
		return Math.floor(dis);
	}


	public void setDis(double dis) {
		this.dis = dis;
	}


	public double getStarLevel() {
		return starLevel;
	}


	public void setStarLevel(double starLevel) {
		this.starLevel = starLevel;
	}


	public Integer getOneLevelCount() {
		return oneLevelCount;
	}


	public void setOneLevelCount(Integer oneLevelCount) {
		this.oneLevelCount = oneLevelCount;
	}


	public Integer getTwoLevelCount() {
		return twoLevelCount;
	}


	public void setTwoLevelCount(Integer twoLevelCount) {
		this.twoLevelCount = twoLevelCount;
	}


	public Integer getThreeLevelCount() {
		return threeLevelCount;
	}


	public void setThreeLevelCount(Integer threeLevelCount) {
		this.threeLevelCount = threeLevelCount;
	}


	public Integer getFourLevelCount() {
		return fourLevelCount;
	}


	public void setFourLevelCount(Integer fourLevelCount) {
		this.fourLevelCount = fourLevelCount;
	}


	public Integer getFiveLevelCount() {
		return fiveLevelCount;
	}


	public void setFiveLevelCount(Integer fiveLevelCount) {
		this.fiveLevelCount = fiveLevelCount;
	}


	public Boolean getIsDefault() {
		return isDefault;
	}


	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
}