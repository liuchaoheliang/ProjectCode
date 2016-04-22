package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class StoreOutletInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3491159212763349254L;
	private Long merchantId;
	private Long outletId;
	private String outletName;
	private String outletImage;
	
	public StoreOutletInfo(){
		super();
	}
	
	public StoreOutletInfo(Long merchantId, Long outletId, String outletName,
			String outletImage) {
		super();
		this.merchantId = merchantId;
		this.outletId = outletId;
		this.outletName = outletName;
		this.outletImage = outletImage;
	}
	
	@JSONField(name = "merchant_id", serialize = true, deserialize = true)
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	@JSONField(name = "outlet_id", serialize = true, deserialize = true)
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	@JSONField(name = "outlet_name", serialize = true, deserialize = true)
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	@JSONField(name = "outlet_image", serialize = true, deserialize = true)
	public String getOutletImage() {
		return outletImage;
	}
	public void setOutletImage(String outletImage) {
		this.outletImage = outletImage;
	}
	
}
