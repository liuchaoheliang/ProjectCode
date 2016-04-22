package com.froad.po;

import java.util.Date;

/**签约商户统计
 * @ClassName: ReportSignMerchant 
 * @Description:  
 * @author longyunbo
 * @date 2015年5月21日 下午6:59:27 
 */
public class ReportSignMerchant {
	private Long id;				//编号
	private Date createTime;		//日期	
	private String type;			//业务类型
	private String merchantId;		//商户号
	private Integer isNew;			//是否新增
	private Integer isChange;		//是否动账
	private Integer isCancel;		//是否解约
	private Long totalOrders;		//订单总数
	private Long totalAmount;		//订单总金额
	private String clientId;		//客户端ID
	private String orgCode;			//商户机构码
	private String orgName;			//机构名
	private String forgCode;		//商户一级机构号
	private String forgName;		//商户一级机构名
	private String sorgCode;		//商户二级机构号
	private String sorgName;		//商户二级机构名
	private String torgCode;		//商户三级机构号
	private String torgName;		//商户三级机构名
	private String lorgCode;		//商户四级机构号
	private String lorgName;		//商户四级机构名
	private String signUserName;	//签约人名
	private String userOrgCode;		//签约用户机构码
	private String userOrgName;		//签约用户机构名
	private String userForgCode;	//签约用户一级机构号
	private String userForgName;	//签约用户一级机构名
	private String userSorgCode;	//签约用户二级机构号
	private String userSorgName;	//签约用户二级机构名
	private String userTorgCode;	//签约用户三级机构号
	private String userTorgName;	//签约用户三级机构名
	private String userLorgCode;	//签约用户四级机构号
	private String userLorgName;	//签约用户四级机构名
	
	//--商户统计详情列表--
	private Integer newCount;		//商户新增数
	private Integer changeCount;	//商户动户数
	private Integer totalMerchants;	//商户总数
	
	//--商户业务统计信息--
	private Integer faceNewCount;			//面对面商户新增数
	private Integer groupNewCount;			//团购商户新增数
	private Integer specialNewCount;		//名优商户新增数
	private Integer faceCancelCount;		//面对面商户注销数
	private Integer groupCancelCount;		//团购商户注销数
	private Integer specialCancelCount;		//名优商户注销数
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public Integer getIsChange() {
		return isChange;
	}
	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}
	public Integer getIsCancel() {
		return isCancel;
	}
	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}
	public Long getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(Long totalOrders) {
		this.totalOrders = totalOrders;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the forgCode
	 */
	public String getForgCode() {
		return forgCode;
	}
	/**
	 * @param forgCode the forgCode to set
	 */
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	/**
	 * @return the forgName
	 */
	public String getForgName() {
		return forgName;
	}
	/**
	 * @param forgName the forgName to set
	 */
	public void setForgName(String forgName) {
		this.forgName = forgName;
	}
	/**
	 * @return the sorgCode
	 */
	public String getSorgCode() {
		return sorgCode;
	}
	/**
	 * @param sorgCode the sorgCode to set
	 */
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	/**
	 * @return the sorgName
	 */
	public String getSorgName() {
		return sorgName;
	}
	/**
	 * @param sorgName the sorgName to set
	 */
	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}
	/**
	 * @return the torgCode
	 */
	public String getTorgCode() {
		return torgCode;
	}
	/**
	 * @param torgCode the torgCode to set
	 */
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	/**
	 * @return the torgName
	 */
	public String getTorgName() {
		return torgName;
	}
	/**
	 * @param torgName the torgName to set
	 */
	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}
	/**
	 * @return the lorgCode
	 */
	public String getLorgCode() {
		return lorgCode;
	}
	/**
	 * @param lorgCode the lorgCode to set
	 */
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	/**
	 * @return the lorgName
	 */
	public String getLorgName() {
		return lorgName;
	}
	/**
	 * @param lorgName the lorgName to set
	 */
	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}
	/**
	 * @return the userOrgCode
	 */
	public String getUserOrgCode() {
		return userOrgCode;
	}
	/**
	 * @param userOrgCode the userOrgCode to set
	 */
	public void setUserOrgCode(String userOrgCode) {
		this.userOrgCode = userOrgCode;
	}
	/**
	 * @return the userOrgName
	 */
	public String getUserOrgName() {
		return userOrgName;
	}
	/**
	 * @param userOrgName the userOrgName to set
	 */
	public void setUserOrgName(String userOrgName) {
		this.userOrgName = userOrgName;
	}
	/**
	 * @return the userForgCode
	 */
	public String getUserForgCode() {
		return userForgCode;
	}
	/**
	 * @param userForgCode the userForgCode to set
	 */
	public void setUserForgCode(String userForgCode) {
		this.userForgCode = userForgCode;
	}
	/**
	 * @return the userForgName
	 */
	public String getUserForgName() {
		return userForgName;
	}
	/**
	 * @param userForgName the userForgName to set
	 */
	public void setUserForgName(String userForgName) {
		this.userForgName = userForgName;
	}
	/**
	 * @return the userSorgCode
	 */
	public String getUserSorgCode() {
		return userSorgCode;
	}
	/**
	 * @param userSorgCode the userSorgCode to set
	 */
	public void setUserSorgCode(String userSorgCode) {
		this.userSorgCode = userSorgCode;
	}
	/**
	 * @return the userSorgName
	 */
	public String getUserSorgName() {
		return userSorgName;
	}
	/**
	 * @param userSorgName the userSorgName to set
	 */
	public void setUserSorgName(String userSorgName) {
		this.userSorgName = userSorgName;
	}
	/**
	 * @return the userTorgCode
	 */
	public String getUserTorgCode() {
		return userTorgCode;
	}
	/**
	 * @param userTorgCode the userTorgCode to set
	 */
	public void setUserTorgCode(String userTorgCode) {
		this.userTorgCode = userTorgCode;
	}
	/**
	 * @return the userTorgName
	 */
	public String getUserTorgName() {
		return userTorgName;
	}
	/**
	 * @param userTorgName the userTorgName to set
	 */
	public void setUserTorgName(String userTorgName) {
		this.userTorgName = userTorgName;
	}
	/**
	 * @return the userLorgCode
	 */
	public String getUserLorgCode() {
		return userLorgCode;
	}
	/**
	 * @param userLorgCode the userLorgCode to set
	 */
	public void setUserLorgCode(String userLorgCode) {
		this.userLorgCode = userLorgCode;
	}
	/**
	 * @return the userLorgName
	 */
	public String getUserLorgName() {
		return userLorgName;
	}
	/**
	 * @param userLorgName the userLorgName to set
	 */
	public void setUserLorgName(String userLorgName) {
		this.userLorgName = userLorgName;
	}
	public String getSignUserName() {
		return signUserName;
	}
	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}
	public Integer getNewCount() {
		return newCount;
	}
	public void setNewCount(Integer newCount) {
		this.newCount = newCount;
	}
	public Integer getChangeCount() {
		return changeCount;
	}
	public void setChangeCount(Integer changeCount) {
		this.changeCount = changeCount;
	}
	public Integer getTotalMerchants() {
		return totalMerchants;
	}
	public void setTotalMerchants(Integer totalMerchants) {
		this.totalMerchants = totalMerchants;
	}
	public Integer getFaceNewCount() {
		return faceNewCount;
	}
	public void setFaceNewCount(Integer faceNewCount) {
		this.faceNewCount = faceNewCount;
	}
	public Integer getGroupNewCount() {
		return groupNewCount;
	}
	public void setGroupNewCount(Integer groupNewCount) {
		this.groupNewCount = groupNewCount;
	}
	public Integer getSpecialNewCount() {
		return specialNewCount;
	}
	public void setSpecialNewCount(Integer specialNewCount) {
		this.specialNewCount = specialNewCount;
	}
	public Integer getFaceCancelCount() {
		return faceCancelCount;
	}
	public void setFaceCancelCount(Integer faceCancelCount) {
		this.faceCancelCount = faceCancelCount;
	}
	public Integer getGroupCancelCount() {
		return groupCancelCount;
	}
	public void setGroupCancelCount(Integer groupCancelCount) {
		this.groupCancelCount = groupCancelCount;
	}
	public Integer getSpecialCancelCount() {
		return specialCancelCount;
	}
	public void setSpecialCancelCount(Integer specialCancelCount) {
		this.specialCancelCount = specialCancelCount;
	}
	
}
