package com.froad.CB.po;

import com.froad.CB.common.Pager;


	/**
	 * 类描述：门店实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jun 26, 2013 3:08:16 PM 
	 */
public class Store extends Pager{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;//编号

	private String shortName;//简称

	private String fullName;//全称

	private String address;//地址

	private String telephone1;//联系方式1

	private String telephone2;//联系方式2

	private String storeDesc;//门店描述

	private String coordinate;//地理坐标

	private Integer merchantId;//商户编号

	private String state;//状态

	private String createTime;//创建时间

	private String updateTime;//修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName == null ? null : shortName.trim();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName == null ? null : fullName.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1 == null ? null : telephone1.trim();
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2 == null ? null : telephone2.trim();
	}

	public String getStoreDesc() {
		return storeDesc;
	}

	public void setStoreDesc(String storeDesc) {
		this.storeDesc = storeDesc == null ? null : storeDesc.trim();
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate == null ? null : coordinate.trim();
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}
}