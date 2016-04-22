package com.froad.fft.persistent.entity;

import java.util.Date;

import com.froad.fft.persistent.common.enums.TransType;

public class TransSecurityTicket extends BaseEntity{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Long transId;//关联交易号
	private Long memberCode;//账户账务Id
	private TransType transType;//交易类型
	private String securitiesNo;//券号
	private Boolean isConsume;//是否消费
	private Date consumeTime;//消费时间(认证时间)
	private Date expireTime;//消费券过期时间
	private Integer smsNumber;//已发送短信次数
	private Date smsTime;//短信最近一次发送时间
	private Long sysUserId;//认证券的操作人
	private Long merchantId;//商户Id
	
	
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	public String getSecuritiesNo() {
		return securitiesNo;
	}
	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}
	public Boolean getIsConsume() {
		return isConsume;
	}
	public void setIsConsume(Boolean isConsume) {
		this.isConsume = isConsume;
	}
	public Date getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getSmsNumber() {
		return smsNumber;
	}
	public void setSmsNumber(Integer smsNumber) {
		this.smsNumber = smsNumber;
	}
	public Date getSmsTime() {
		return smsTime;
	}
	public void setSmsTime(Date smsTime) {
		this.smsTime = smsTime;
	}
	public Long getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	
}
