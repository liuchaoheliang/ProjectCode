package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class StoreOutletInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3491159212763349254L;
	@JSONField(name = "merchant_id", serialize = true, deserialize = true)
	private String merchantId;
	@JSONField(name = "outlet_id", serialize = true, deserialize = true)
	private String outletId;
	@JSONField(name = "outlet_name", serialize = true, deserialize = true)
	private String outletName;
	@JSONField(name = "outlet_image", serialize = true, deserialize = true)
	private String outletImage;
	
	public StoreOutletInfo(){
		super();
	}
	
	public StoreOutletInfo(String merchantId, String outletId, String outletName,
			String outletImage) {
		super();
		this.merchantId = merchantId;
		this.outletId = outletId;
		this.outletName = outletName;
		this.outletImage = outletImage;
	}
	
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	
	public String getOutletImage() {
		return outletImage;
	}
	public void setOutletImage(String outletImage) {
		this.outletImage = outletImage;
	}
	
}
