package com.froad.po.mongo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class OutletDetailSimple implements java.io.Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	private static final long serialVersionUID = -7370245180138018030L;
	
	@JSONField(name = "_id", serialize = true, deserialize = true)
	private String merchantId;
	
	@JSONField(name = "create_time", serialize = true, deserialize = true)
	private Long createTime;
	
	

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public List<TypeInfo> getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(List<TypeInfo> typeInfo) {
		this.typeInfo = typeInfo;
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
		return dis;
	}

	public void setDis(double dis) {
		this.dis = dis;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

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
	
	@JSONField(name = "outlet_id", serialize = true, deserialize = true)
	private String outletId;
	
	@JSONField(name = "merchant_name", serialize = true, deserialize = true)
	private String merchantName;
	
	@JSONField(name = "star_level", serialize = true, deserialize = true)
	private String starLevel;




	
}
