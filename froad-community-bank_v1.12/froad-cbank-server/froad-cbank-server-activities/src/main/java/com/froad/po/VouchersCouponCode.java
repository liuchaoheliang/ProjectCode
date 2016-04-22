package com.froad.po;

import java.util.Date;

public class VouchersCouponCode {
	 /**
	   * 序号*
	   */
	  private short numberCode; // required
	  /**
	   * 红包券码*
	   */
	  private String vouchersIds; // required
	  /**
	   * 金额*
	   */
	  private long vouchersMoney; // required
	  /**
	   * 余额*
	   */
	  private long vouchersResMoney; // required
	  /**
	   * 上传时间*
	   */
	  private Date createTime; // required
	  /**
	   * 状态 *
	   */
	  private String status; // required
	  
	public short getNumberCode() {
		return numberCode;
	}
	public void setNumberCode(short numberCode) {
		this.numberCode = numberCode;
	}
	public String getVouchersIds() {
		return vouchersIds;
	}
	public void setVouchersIds(String vouchersIds) {
		this.vouchersIds = vouchersIds;
	}
	public long getVouchersMoney() {
		return vouchersMoney;
	}
	public void setVouchersMoney(long vouchersMoney) {
		this.vouchersMoney = vouchersMoney;
	}
	public long getVouchersResMoney() {
		return vouchersResMoney;
	}
	public void setVouchersResMoney(long vouchersResMoney) {
		this.vouchersResMoney = vouchersResMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	  
	  
}
