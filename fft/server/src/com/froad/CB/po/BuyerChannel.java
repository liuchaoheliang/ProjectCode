package com.froad.CB.po;



	/**
	 * 类描述：买家资金渠道
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 2, 2013 4:57:44 PM 
	 */
public class BuyerChannel {
	private Integer id;//主键ID
	private String userId;//会员ID
	private String buyersId;//买家ID
	private String buyersRuleId;//买家规则ID
	private String channelId;//买家资金渠道ID
	private String isDefault;//是否是默认标志 0否 1是
	private String accountName;//买家账户名
	private String accountNumber;//买家账户号
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private String remark;//备注
	private String phone;//贴膜卡手机号
	private FundsChannel fundsChannel;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public FundsChannel getFundsChannel() {
		return fundsChannel;
	}
	public void setFundsChannel(FundsChannel fundsChannel) {
		this.fundsChannel = fundsChannel;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBuyersId() {
		return buyersId;
	}
	public void setBuyersId(String buyersId) {
		this.buyersId = buyersId;
	}
	public String getBuyersRuleId() {
		return buyersRuleId;
	}
	public void setBuyersRuleId(String buyersRuleId) {
		this.buyersRuleId = buyersRuleId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
