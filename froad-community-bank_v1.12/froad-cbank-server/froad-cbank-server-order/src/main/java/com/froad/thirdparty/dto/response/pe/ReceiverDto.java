package com.froad.thirdparty.dto.response.pe;

import java.io.Serializable;
import java.util.Date;

import com.froad.thirdparty.enums.DataState;



/**
 * 收货人
 * @author larry
 *
 */
public class ReceiverDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date createTime;
	private Date updateTime;
	private String consignee;//收货人
	private String address;//地址
	private String zipCode;//邮编
	private String phone;// 电话
	private Boolean isDefault;//是否默认
	private Long areaId;//地区 
	private Long memberCode;//账户服务ID
	private DataState dataState;//数据状态
	private String fullAddress;//完整地址
	
	private String reveiverAddress;

	private Integer type;//类型(1-提货人，2-收货人)
	
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public DataState getDataState() {
		return dataState;
	}
	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getReveiverAddress() {
		return reveiverAddress;
	}
	public void setReveiverAddress(String reveiverAddress) {
		this.reveiverAddress = reveiverAddress;
	}
	
	
}
