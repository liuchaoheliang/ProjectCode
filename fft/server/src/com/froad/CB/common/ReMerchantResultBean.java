package com.froad.CB.common;
/** 
 * @author FQ 
 * @date 2013-4-24 下午05:17:13
 * @version 1.0
 * 商户注册结果 Bean
 */
public class ReMerchantResultBean {
	
	private boolean isSuccess;//是否成功  true=成功 false=失败
	private String merchantId;//成功为商户ID 失败为空
	private String remark;//结果说明
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
