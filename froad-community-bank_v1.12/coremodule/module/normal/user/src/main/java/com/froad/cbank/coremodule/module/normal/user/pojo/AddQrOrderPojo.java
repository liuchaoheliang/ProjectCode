package com.froad.cbank.coremodule.module.normal.user.pojo;

public class AddQrOrderPojo {

	private String qrCode;

	private String createSource;
	
	private String remark;
	
	/**
	 * couponsNo:优惠券号
	 */
	private String couponsNo;
	/**
	 * redPacketNo:红包券号
	 */
	private String redPacketNo;
	
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCouponsNo() {
		return couponsNo;
	}

	public void setCouponsNo(String couponsNo) {
		this.couponsNo = couponsNo;
	}

	public String getRedPacketNo() {
		return redPacketNo;
	}

	public void setRedPacketNo(String redPacketNo) {
		this.redPacketNo = redPacketNo;
	}
	
}
