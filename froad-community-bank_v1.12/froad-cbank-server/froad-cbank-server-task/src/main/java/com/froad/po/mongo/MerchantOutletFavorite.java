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
	
	
	@JSONField(name = "recv_info", serialize = true, deserialize = true)
	private List<RecvInfo> recvInfo;
	
	@JSONField(name = "delivery_info", serialize = true, deserialize = true)
	private List<DeliverInfo> deliverInfo;
	
	public MerchantOutletFavorite(){
		super();
	}

	public MerchantOutletFavorite(String id,
			List<StoreOutletInfo> storeOutletInfo,
			List<StoreProductInfo> storeProductInfo,
		 List<RecvInfo> recvInfo,
			List<DeliverInfo> deliverInfo) {
		super();
		this.id = id;
		this.storeOutletInfo = storeOutletInfo;
		this.storeProductInfo = storeProductInfo;
		this.recvInfo = recvInfo;
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

	

	
	public List<RecvInfo> getRecvInfo() {
		return recvInfo;
	}

	public void setRecvInfo(List<RecvInfo> recvInfo) {
		this.recvInfo = recvInfo;
	}

	

	
	public List<DeliverInfo> getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(List<DeliverInfo> deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	
	
}
