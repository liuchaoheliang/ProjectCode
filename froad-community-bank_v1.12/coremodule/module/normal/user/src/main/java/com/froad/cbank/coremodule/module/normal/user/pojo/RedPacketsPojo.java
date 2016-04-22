package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * 红包
 * 
 * @author artPing
 *
 */
public class RedPacketsPojo {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 客户端id
	 */
	private String clientId;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 修改时间
	 */
	private Long updateTime;
	/**
	 * 活动id
	 */
	private String activeId;
	/**
	 * 红包id
	 */
	private String vouchersId;
	/**
	 * 红包金额
	 */
	private Double vouchersMoney;
	/**
	 * 红包使用金额
	 */
	private Double vouchersUseMoney;
	/**
	 * 红包剩余金额
	 */
	private Double vouchersResMoney;
	/**
	 * 会员编号
	 */
	private Long memberCode;
	/**
	 * 红包的活动id
	 */
	private String sendActiveId;
	/**
	 * 发送时间
	 */
	private Long sendTime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 使用时间
	 */
	private Long useTime;
	/**
	 * 使用的会员编号
	 */
	private Long userMemberCode;
	/**
	 * 咱不能使用的原因
	 */
	private String reason;
	/**
	 * 使用的天数预警
	 */
	private Integer daysWarn;
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 活动描述
	 */
	private String description;

	/** 有效期结束时间 */
	private Long expireEndTime;
	/** 有效期开始时间 */
	private Long expireStartTime;
	/** 使用的订单id */
	private Long useOrderId;

	public Long getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(Long expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	public Long getExpireStartTime() {
		return expireStartTime;
	}

	public void setExpireStartTime(Long expireStartTime) {
		this.expireStartTime = expireStartTime;
	}

	public Long getUseOrderId() {
		return useOrderId;
	}

	public void setUseOrderId(Long useOrderId) {
		this.useOrderId = useOrderId;
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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
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

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
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

	public Integer getDaysWarn() {
		return daysWarn;
	}

	public void setDaysWarn(Integer daysWarn) {
		this.daysWarn = daysWarn;
	}

}
