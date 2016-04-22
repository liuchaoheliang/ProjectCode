package com.froad.CB.po;

import com.froad.CB.common.Pager;

public class AuthTicket extends Pager{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;			//主键 自增100001001开始
	private String userId;		//用户ID --
	private String merchantId;	//商户ID --
	private String transId;		//交易ID --
	private String type;		//类型 0-团购 1-积分兑换(目前主要是农产品) --
	private String securitiesNo;//券号
	private String isConsume;	//是否消费 0-否 1-是
	private String consumeTime;	//消费时间
	private String expireTime;	//过期时间 --
	private Integer smsNumber;	//短信次数
	private String smsTime;		//短信时间
	private String state;		//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;	//创建时间
	private String updateTime;	//更新时间
	private String remark;		//备注
	
	private String belongUserBecode; //关联操作员信息userId|becode

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId == null ? null : merchantId.trim();
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId == null ? null : transId.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getSecuritiesNo() {
		return securitiesNo;
	}

	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo == null ? null : securitiesNo.trim();
	}

	public String getIsConsume() {
		return isConsume;
	}

	public void setIsConsume(String isConsume) {
		this.isConsume = isConsume == null ? null : isConsume.trim();
	}

	public String getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime == null ? null : consumeTime.trim();
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime == null ? null : expireTime.trim();
	}

	public Integer getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(Integer smsNumber) {
		this.smsNumber = smsNumber;
	}

	public String getSmsTime() {
		return smsTime;
	}

	public void setSmsTime(String smsTime) {
		this.smsTime = smsTime == null ? null : smsTime.trim();
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

	public String getBelongUserBecode() {
		return belongUserBecode;
	}

	public void setBelongUserBecode(String belongUserBecode) {
		this.belongUserBecode = belongUserBecode;
	}
	
	
}