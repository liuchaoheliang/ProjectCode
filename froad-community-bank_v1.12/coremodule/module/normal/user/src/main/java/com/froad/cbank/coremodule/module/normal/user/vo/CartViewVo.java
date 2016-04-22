package com.froad.cbank.coremodule.module.normal.user.vo;

import java.io.Serializable;
import java.util.List;

public class CartViewVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4816012692974907171L;
	
	private String merchantId; 
	private String merchantName; 
	private boolean merchantStatus; 
	private List<ProductViewVo> productViewVoList; 
//	public String errCode; 
//	public String errMsg;
	
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
	public boolean isMerchantStatus() {
		return merchantStatus;
	}
	public void setMerchantStatus(boolean merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	public List<ProductViewVo> getProductViewVoList() {
		return productViewVoList;
	}
	public void setProductViewVoList(List<ProductViewVo> productViewVoList) {
		this.productViewVoList = productViewVoList;
	}
//	public String getErrCode() {
//		return errCode;
//	}
//	public void setErrCode(String errCode) {
//		this.errCode = errCode;
//	}
//	public String getErrMsg() {
//		return errMsg;
//	}
//	public void setErrMsg(String errMsg) {
//		this.errMsg = errMsg;
//	}
}
