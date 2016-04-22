
	 /**
  * 文件名：TransSecurityTicketDto.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */

package com.froad.fft.dto;
import java.io.Serializable;
import java.util.Date;

import com.froad.fft.enums.trans.TransType;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午1:37:01 
 */
public class TransSecurityTicketDto implements Serializable {
	
	private Long id;
	private Date createTime;
	
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
	private Long merchantId;
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
