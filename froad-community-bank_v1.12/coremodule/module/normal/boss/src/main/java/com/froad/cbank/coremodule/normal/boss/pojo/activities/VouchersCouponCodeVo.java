package com.froad.cbank.coremodule.normal.boss.pojo.activities;
/**
 * 红包券码明细
 * @author liaopeixin
 *	@date 2015年12月1日 下午2:19:52
 */
public class VouchersCouponCodeVo {
	/**序号**/
	private int numberCode;
	
	/**红包券码**/
	private String vouchersIds;
	
	/**金额**/
	private Double vouchersMoney;
	
	/**余额**/
	private Double vouchersResMoney;
	
	/**上传时间**/
	private long createTime;
	
	/** 状态 **/
	private String status;

	public int getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(int numberCode) {
		this.numberCode = numberCode;
	}

	public String getVouchersIds() {
		return vouchersIds;
	}

	public void setVouchersIds(String vouchersIds) {
		this.vouchersIds = vouchersIds;
	}

	public Double getVouchersMoney() {
		return vouchersMoney;
	}

	public void setVouchersMoney(Double vouchersMoney) {
		this.vouchersMoney = vouchersMoney;
	}

	public Double getVouchersResMoney() {
		return vouchersResMoney;
	}

	public void setVouchersResMoney(Double vouchersResMoney) {
		this.vouchersResMoney = vouchersResMoney;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
