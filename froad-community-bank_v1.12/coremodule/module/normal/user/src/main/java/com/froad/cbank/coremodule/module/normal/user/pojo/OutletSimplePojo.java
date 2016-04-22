package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年5月8日 上午11:08:08
 * @version 1.0
 * @desc 门店简单属性POJO
 */
public class OutletSimplePojo {
	private String id;
	private String outletName;
	private String defaultImage;
	private List<CategoryInfoPojo> categoryList;
	private String address;
	private double dis;
	private double starLevel;
	private String phone;
	private String storeCount;
	private String typeInfo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getDefaultImage() {
		return defaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	public List<CategoryInfoPojo> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<CategoryInfoPojo> categoryList) {
		this.categoryList = categoryList;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getDis() {
		return dis;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStoreCount() {
		return storeCount;
	}
	public void setStoreCount(String storeCount) {
		this.storeCount = storeCount;
	}
	public String getTypeInfo() {
		return typeInfo;
	}
	public void setTypeInfo(String typeInfo) {
		this.typeInfo = typeInfo;
	}
	
}
