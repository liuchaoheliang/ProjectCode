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

public class OutletDetailSimpleInfo implements java.io.Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = -7370245180138018030L;
	
	@JSONField(name = "_id", serialize = true, deserialize = true)
	private String id;
	
	@JSONField(name = "create_time", serialize = true, deserialize = true)
	private Long createTime;
	
	
	@JSONField(name = "default_image", serialize = true, deserialize = true)
	private String defaultImage;
	

	@JSONField(name = "outlet_name", serialize = true, deserialize = true)
	private String outletName;
	
	
	@JSONField(name = "category_info", serialize = true, deserialize = true)
	private List<CategoryInfo> categoryInfo;
	
	
	@JSONField(name = "type_info", serialize = true, deserialize = true)
	private List<TypeInfo> typeInfo;
	
	



	@JSONField(name = "address", serialize = true, deserialize = true)
	private String address;
	
	@JSONField(name = "location", serialize = true, deserialize = true)
	private Location location;
	
	@JSONField(name = "store_count", serialize = true, deserialize = true)
	private Integer storeCount;
	
	@JSONField(name = "dis",serialize = false, deserialize = false)
	private double dis;

	
	/**
	 * 评价星级
	 */
	@JSONField(name = "star_level", serialize = true, deserialize = true)
	private String starLevel;
	
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
	
	
	public List<TypeInfo> getTypeInfo() {
		return typeInfo;
	}


	public void setTypeInfo(List<TypeInfo> typeInfo) {
		this.typeInfo = typeInfo;
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


	public String getDefaultImage() {
		return defaultImage;
	}


	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}


	public String getOutletName() {
		return outletName;
	}


	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}


	public List<CategoryInfo> getCategoryInfo() {
		return categoryInfo;
	}


	public void setCategoryInfo(List<CategoryInfo> categoryInfo) {
		this.categoryInfo = categoryInfo;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public Integer getStoreCount() {
		return storeCount;
	}


	public void setStoreCount(Integer storeCount) {
		this.storeCount = storeCount;
	}


	public double getDis() {
		return  Math.floor(dis);
	}


	public void setDis(double dis) {
		this.dis = dis;
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


	public String getStarLevel() {
		return starLevel;
	}


	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}


}
