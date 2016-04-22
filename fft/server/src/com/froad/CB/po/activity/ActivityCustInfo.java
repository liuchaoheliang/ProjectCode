package com.froad.CB.po.activity;

import com.froad.CB.common.Pager;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
public class ActivityCustInfo extends Pager {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1725142201184221060L;	
	private Integer id;							//主键 自增100001001开始
	private String accountName;					//中奖姓名
	private String accountNumber;				//卡号
	private String identificationCard;			//身份证号码
	private String isReceived;					//是否领取 0-否 1-已经领取
	private String type;						//类型 1-兑换券 2-商品(打折优惠) 3-积分赠送
	private String name;						//本次活动名称
	private String batchNumber;					//本次活动期号
	private String activityStartTime;					//开始时间
	private String activityEndTime;						//结束时间
	private String state;						//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;					//创建时间
	private String updateTime;					//更新时间
	private String remark;						//备注
	private String field1;						//备注
	private String field2;						//备注
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIdentificationCard() {
		return identificationCard;
	}
	public void setIdentificationCard(String identificationCard) {
		this.identificationCard = identificationCard;
	}
	public String getIsReceived() {
		return isReceived;
	}
	public void setIsReceived(String isReceived) {
		this.isReceived = isReceived;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getActivityStartTime() {
		return activityStartTime;
	}
	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}
	public String getActivityEndTime() {
		return activityEndTime;
	}
	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	
}
