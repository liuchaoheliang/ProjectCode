package com.froad.fft.persistent.entity;

import java.util.Date;
import java.util.List;

/**
 * 退\换货
 * @author FQ
 *
 */
public class ReturnSale extends BaseEntity {
	
	public enum Type{
		/**
		 * 退货
		 */
		sale_return,
		
		/**
		 * 换货
		 */
		sale_swap
	}
	
	private String sn;//编号
	
	private Type type;//类型
	private Long merchantOutletId;//门店ID
	private String reason;//退货事由
	
	private Long sysUserId;//操作员
	private String remark;//备注
	
	private List<Long> sysUserIds;
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Long getMerchantOutletId() {
		return merchantOutletId;
	}
	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Long> getSysUserIds() {
		return sysUserIds;
	}
	public void setSysUserIds(List<Long> sysUserIds) {
		this.sysUserIds = sysUserIds;
	}
	
		
}
