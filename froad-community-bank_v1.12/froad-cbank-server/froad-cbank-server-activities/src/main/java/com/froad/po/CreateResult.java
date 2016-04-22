package com.froad.po;

import java.io.Serializable;

public class CreateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Result结果 */
	private Result result;	
	/** 营销的满减订单id */
	private String cutOrderId;
	/** 营销的红包订单id */
	private String vouchersOrderId;  
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public String getCutOrderId() {
		return cutOrderId;
	}
	public void setCutOrderId(String cutOrderId) {
		this.cutOrderId = cutOrderId;
	}
	public String getVouchersOrderId() {
		return vouchersOrderId;
	}
	public void setVouchersOrderId(String vouchersOrderId) {
		this.vouchersOrderId = vouchersOrderId;
	}

}
