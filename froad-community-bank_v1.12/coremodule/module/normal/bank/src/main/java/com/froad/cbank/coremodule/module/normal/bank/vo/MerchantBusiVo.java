package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-8下午5:31:48 
 */
public class MerchantBusiVo implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1254063682186986749L;
	private String clientId;
	private String orgCode;
	private String orgName;
	private Integer faceAddCount;
	private Integer faceCancelCount;
	private Integer faceTotalCount;
	private Integer specialAddCount;
	private Integer specialCancelCount;
	private Integer specialTotalCount;
	private Integer groupAddCount;
	private Integer groupCancelCount;
	private Integer groupTotalCount;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getFaceAddCount() {
		return faceAddCount;
	}
	public void setFaceAddCount(Integer faceAddCount) {
		this.faceAddCount = faceAddCount;
	}
	public Integer getFaceCancelCount() {
		return faceCancelCount;
	}
	public void setFaceCancelCount(Integer faceCancelCount) {
		this.faceCancelCount = faceCancelCount;
	}
	public Integer getFaceTotalCount() {
		return faceTotalCount;
	}
	public void setFaceTotalCount(Integer faceTotalCount) {
		this.faceTotalCount = faceTotalCount;
	}
	public Integer getSpecialAddCount() {
		return specialAddCount;
	}
	public void setSpecialAddCount(Integer specialAddCount) {
		this.specialAddCount = specialAddCount;
	}
	public Integer getSpecialCancelCount() {
		return specialCancelCount;
	}
	public void setSpecialCancelCount(Integer specialCancelCount) {
		this.specialCancelCount = specialCancelCount;
	}
	public Integer getSpecialTotalCount() {
		return specialTotalCount;
	}
	public void setSpecialTotalCount(Integer specialTotalCount) {
		this.specialTotalCount = specialTotalCount;
	}
	public Integer getGroupAddCount() {
		return groupAddCount;
	}
	public void setGroupAddCount(Integer groupAddCount) {
		this.groupAddCount = groupAddCount;
	}
	public Integer getGroupCancelCount() {
		return groupCancelCount;
	}
	public void setGroupCancelCount(Integer groupCancelCount) {
		this.groupCancelCount = groupCancelCount;
	}
	public Integer getGroupTotalCount() {
		return groupTotalCount;
	}
	public void setGroupTotalCount(Integer groupTotalCount) {
		this.groupTotalCount = groupTotalCount;
	}
	
}
