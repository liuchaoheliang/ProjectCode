package com.froad.po;

import java.sql.Timestamp;

/**
 * CbVouchersUseInfo entity. 
 */


public class VouchersUseInfo implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID : TODO
	 */
	
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String clientId;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String activeId;
	private String vouchersId;
	private Integer vouchersMoney;
	private Long vouchersUseMoney;
	private Long vouchersResMoney;
	private Long memberCode;
	private String sendActiveId;
	private Timestamp sendTime;
	private String status;
	private Timestamp useTime;
	private Long userMemberCode;

	// Constructors

	/** default constructor */
	public VouchersUseInfo() {
	}

	/** minimal constructor */
	public VouchersUseInfo(String clientId, Timestamp createTime, Timestamp updateTime, String activeId, String vouchersId, Integer vouchersMoney, Long vouchersUseMoney, Long vouchersResMoney, String status) {
		this.clientId = clientId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.activeId = activeId;
		this.vouchersId = vouchersId;
		this.vouchersMoney = vouchersMoney;
		this.vouchersUseMoney = vouchersUseMoney;
		this.vouchersResMoney = vouchersResMoney;
		this.status = status;
	}

	/** full constructor */
	public VouchersUseInfo(String clientId, Timestamp createTime, Timestamp updateTime, String activeId, String vouchersId, Integer vouchersMoney, Long vouchersUseMoney, Long vouchersResMoney, Long memberCode, String sendActiveId, Timestamp sendTime, String status, Timestamp useTime, Long userMemberCode) {
		this.clientId = clientId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.activeId = activeId;
		this.vouchersId = vouchersId;
		this.vouchersMoney = vouchersMoney;
		this.vouchersUseMoney = vouchersUseMoney;
		this.vouchersResMoney = vouchersResMoney;
		this.memberCode = memberCode;
		this.sendActiveId = sendActiveId;
		this.sendTime = sendTime;
		this.status = status;
		this.useTime = useTime;
		this.userMemberCode = userMemberCode;
	}

	// Property accessors
	
	/** 使用的订单id */    
    private String useOrderId;

	public String getUseOrderId() {
		return useOrderId;
	}

	public void setUseOrderId(String useOrderId) {
		this.useOrderId = useOrderId;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	
	public String getActiveId() {
		return this.activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	
	public String getVouchersId() {
		return this.vouchersId;
	}

	public void setVouchersId(String vouchersId) {
		this.vouchersId = vouchersId;
	}

	
	public Integer getVouchersMoney() {
		return this.vouchersMoney;
	}

	public void setVouchersMoney(Integer vouchersMoney) {
		this.vouchersMoney = vouchersMoney;
	}

	
	public Long getVouchersUseMoney() {
		return this.vouchersUseMoney;
	}

	public void setVouchersUseMoney(Long vouchersUseMoney) {
		this.vouchersUseMoney = vouchersUseMoney;
	}

	
	public Long getVouchersResMoney() {
		return this.vouchersResMoney;
	}

	public void setVouchersResMoney(Long vouchersResMoney) {
		this.vouchersResMoney = vouchersResMoney;
	}

	
	public Long getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	
	public String getSendActiveId() {
		return this.sendActiveId;
	}

	public void setSendActiveId(String sendActiveId) {
		this.sendActiveId = sendActiveId;
	}

	
	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Timestamp getUseTime() {
		return this.useTime;
	}

	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	
	public Long getUserMemberCode() {
		return this.userMemberCode;
	}

	public void setUserMemberCode(Long userMemberCode) {
		this.userMemberCode = userMemberCode;
	}

}