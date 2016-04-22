/**
 * @Title: VouchersInfo.java
 * @Package com.froad.po
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年11月26日
 * @version V1.0
 **/

package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: VouchersInfo
 * @Description: TODO
 * @author froad-Joker 2015年11月26日
 * @modify froad-Joker 2015年11月26日
 */

public class VouchersInfo implements Serializable,Comparable<VouchersInfo>{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	
	private static final long serialVersionUID = 1L;
	/** 请求id */
	private Long id;
	/** 客户端id */
	private String clientId;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 活动id */
	private String activeId;
	/** 代金券(红包)id */
	private String vouchersId;
	/** 代金券(红包)金额 */
	private Double vouchersMoney;
	/** 代金券(红包)使用金额 */
	private Double vouchersUseMoney;
	/** 代金券(红包)剩余金额 */
	private Double vouchersResMoney;
	/** 会员编号 */
	private Long memberCode;
	/** 发送代金券(红包)的活动id */
	private String sendActiveId;
	/** 发送时间 */
	private Date sendTime;
	/** 状态 0初始化(未使用) 1支付中 2支付成功 */
	private String status;
	/** 使用时间 */
	private Date useTime;
	/** 使用的会员编号 */
	private Long userMemberCode;
	/** 暂不能使用的原因 */
	private String reason;
	/** 使用的天数预警 */
	private String daysWarn;
	/** 活动名称 */
    private String activeName;
    /** 活动描述 */
    private String description;
    /** 有效期结束时间 */    
    private Date expireEndTime;
    /** 有效期结束时间 */    
    private Date expireStartTime;
    /** 使用的订单id */    
    private String useOrderId;
    /** 营销活动类型 */    
    private String type;
    /** 是否上传券码  */
    private Boolean isUpload;

    @Override
	public int compareTo(VouchersInfo o) {
    	Date expireEndTime = o.getExpireEndTime();
		return this.expireEndTime.compareTo(expireEndTime);
	}
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUseOrderId() {
		return useOrderId;
	}

	public void setUseOrderId(String useOrderId) {
		this.useOrderId = useOrderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getVouchersId() {
		return vouchersId;
	}

	public void setVouchersId(String vouchersId) {
		this.vouchersId = vouchersId;
	}

	
	public Double getVouchersMoney() {
		return vouchersMoney;
	}

	public void setVouchersMoney(Double vouchersMoney) {
		this.vouchersMoney = vouchersMoney;
	}

	public Double getVouchersUseMoney() {
		return vouchersUseMoney;
	}

	public void setVouchersUseMoney(Double vouchersUseMoney) {
		this.vouchersUseMoney = vouchersUseMoney;
	}

	public Double getVouchersResMoney() {
		return vouchersResMoney;
	}

	public void setVouchersResMoney(Double vouchersResMoney) {
		this.vouchersResMoney = vouchersResMoney;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getSendActiveId() {
		return sendActiveId;
	}

	public void setSendActiveId(String sendActiveId) {
		this.sendActiveId = sendActiveId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Long getUserMemberCode() {
		return userMemberCode;
	}

	public void setUserMemberCode(Long userMemberCode) {
		this.userMemberCode = userMemberCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDaysWarn() {
		return daysWarn;
	}

	public void setDaysWarn(String daysWarn) {
		this.daysWarn = daysWarn;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(Date expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	public Date getExpireStartTime() {
		return expireStartTime;
	}

	public void setExpireStartTime(Date expireStartTime) {
		this.expireStartTime = expireStartTime;
	}

	public Boolean getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}
	
	

}
