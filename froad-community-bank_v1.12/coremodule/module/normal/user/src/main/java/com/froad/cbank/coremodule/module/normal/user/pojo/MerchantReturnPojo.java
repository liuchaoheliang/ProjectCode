package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

public class MerchantReturnPojo {

	public String type;
	public String merchantId;
	public String merchantName;
	public String merchantStatus;
	public List<ProductReturnPojo> productReturnList;
	public String errCode;
	public String errMsg;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantStatus() {
		return merchantStatus;
	}
	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	public List<ProductReturnPojo> getProductReturnList() {
		return productReturnList;
	}
	public void setProductReturnList(List<ProductReturnPojo> productReturnList) {
		this.productReturnList = productReturnList;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
	
}
