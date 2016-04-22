package com.froad.po.mongo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class MerchantOutletFavorite implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2711689745599377987L;
	
	@JSONField(name = "_id", serialize = true, deserialize = true)
	private String id;
	
	@JSONField(name = "store_outlet_info", serialize = true, deserialize = true)
	private List<StoreOutletInfo> storeOutletInfo;
	
	@JSONField(name = "store_product_info", serialize = true, deserialize = true)
	private List<StoreProductInfo> storeProductInfo;
	
	@JSONField(name = "default_recv_info", serialize = true, deserialize = true)
	private DefaultRecvInfo defaultRecvInfo;
	
	@JSONField(name = "recv_info", serialize = true, deserialize = true)
	private List<RecvInfo> recvInfo;
	
	@JSONField(name = "default_delivery_info", serialize = true, deserialize = true)
	private DefaultDeliverInfo defaultDeliverInfo;
	
	@JSONField(name = "delivery_info", serialize = true, deserialize = true)
	private List<DeliverInfo> deliverInfo;
	
	public MerchantOutletFavorite(){
		super();
	}

	public MerchantOutletFavorite(String id,
			List<StoreOutletInfo> storeOutletInfo,
			List<StoreProductInfo> storeProductInfo,
			DefaultRecvInfo defaultRecvInfo, List<RecvInfo> recvInfo,
			DefaultDeliverInfo defaultDeliverInfo,
			List<DeliverInfo> deliverInfo) {
		super();
		this.id = id;
		this.storeOutletInfo = storeOutletInfo;
		this.storeProductInfo = storeProductInfo;
		this.defaultRecvInfo = defaultRecvInfo;
		this.recvInfo = recvInfo;
		this.defaultDeliverInfo = defaultDeliverInfo;
		this.deliverInfo = deliverInfo;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public List<StoreOutletInfo> getStoreOutletInfo() {
		return storeOutletInfo;
	}

	public void setStoreOutletInfo(List<StoreOutletInfo> storeOutletInfo) {
		this.storeOutletInfo = storeOutletInfo;
	}

	
	public List<StoreProductInfo> getStoreProductInfo() {
		return storeProductInfo;
	}

	public void setStoreProductInfo(List<StoreProductInfo> storeProductInfo) {
		this.storeProductInfo = storeProductInfo;
	}

	
	public DefaultRecvInfo getDefaultRecvInfo() {
		return defaultRecvInfo;
	}

	public void setDefaultRecvInfo(DefaultRecvInfo defaultRecvInfo) {
		this.defaultRecvInfo = defaultRecvInfo;
	}

	
	public List<RecvInfo> getRecvInfo() {
		return recvInfo;
	}

	public void setRecvInfo(List<RecvInfo> recvInfo) {
		this.recvInfo = recvInfo;
	}

	
	public DefaultDeliverInfo getDefaultDeliverInfo() {
		return defaultDeliverInfo;
	}

	public void setDefaultDeliverInfo(DefaultDeliverInfo defaultDeliverInfo) {
		this.defaultDeliverInfo = defaultDeliverInfo;
	}

	
	public List<DeliverInfo> getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(List<DeliverInfo> deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	
	
}
