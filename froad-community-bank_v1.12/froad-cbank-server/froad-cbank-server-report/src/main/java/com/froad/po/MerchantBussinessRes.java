package com.froad.po;

public class MerchantBussinessRes {
	private String clientId;			//客户端号
	private String orgCode;				//机构号
	private String orgName;				//机构名
	private Integer faceAddCount;		//面对面商户新增数
	private Integer faceCancelCount;	//面对面商户注销数
	private Integer faceTotalCount;		//面对面商户结余数
	private Integer specialAddCount;	//名优特惠新增数
	private Integer specialCancelCount;	//名优特惠注销数
	private Integer specialTotalCount;	//名优特惠结余数
	private Integer groupAddCount;		//团购商户新增数
	private Integer groupCancelCount;	//团购商户注销数
	private Integer groupTotalCount;	//团购商户结余数
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
