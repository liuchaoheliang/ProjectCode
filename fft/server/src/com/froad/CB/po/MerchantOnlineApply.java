package com.froad.CB.po;

import java.util.List;

import com.froad.CB.common.Pager;

/**
 * 类描述：商户在线申请
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Feb 6, 2013 5:23:55 PM
 */
public class MerchantOnlineApply extends Pager{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String merchantName;//合作商户名
	private String linkman;		//联系人
	private String mobile;		//手机
	private String phone;		//座机
	private String address;		//地址
	private String mechantIndustryId; //行业ID
	private String cooperationModel;  //意向合作模式 1-分分通 2-直接优惠 3-其它
	private String cooperativePurpose;//合作意向
	private String isRelation;		  //是否已联系 0-否 1-是	
	private String relationAccount;	  //联系处理人
	private String relationTime;//联系处理时间
	private String relationMark;//联系处理备注
	private String state;		//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;	//创建时间
	private String updateTime;	//更新时间
	private String remark;		//备注
	private String userId;//用户编号
	
	/**用作查询条件**/
	private List<String> stateList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman == null ? null : linkman.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getMechantIndustryId() {
		return mechantIndustryId;
	}

	public void setMechantIndustryId(String mechantIndustryId) {
		this.mechantIndustryId = mechantIndustryId == null ? null
				: mechantIndustryId.trim();
	}

	public String getCooperationModel() {
		return cooperationModel;
	}

	public void setCooperationModel(String cooperationModel) {
		this.cooperationModel = cooperationModel == null ? null
				: cooperationModel.trim();
	}

	public String getCooperativePurpose() {
		return cooperativePurpose;
	}

	public void setCooperativePurpose(String cooperativePurpose) {
		this.cooperativePurpose = cooperativePurpose == null ? null
				: cooperativePurpose.trim();
	}

	public String getIsRelation() {
		return isRelation;
	}

	public void setIsRelation(String isRelation) {
		this.isRelation = isRelation == null ? null : isRelation.trim();
	}

	public String getRelationAccount() {
		return relationAccount;
	}

	public void setRelationAccount(String relationAccount) {
		this.relationAccount = relationAccount == null ? null : relationAccount
				.trim();
	}

	public String getRelationTime() {
		return relationTime;
	}

	public void setRelationTime(String relationTime) {
		this.relationTime = relationTime == null ? null : relationTime.trim();
	}

	public String getRelationMark() {
		return relationMark;
	}

	public void setRelationMark(String relationMark) {
		this.relationMark = relationMark == null ? null : relationMark.trim();
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}
}