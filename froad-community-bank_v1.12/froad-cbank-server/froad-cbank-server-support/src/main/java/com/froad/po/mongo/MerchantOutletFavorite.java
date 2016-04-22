package com.froad.po.mongo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class MerchantOutletFavorite implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2711689745599377987L;
	
	
	private Long _id;
	
	private List<StoreOutletInfo> storeOutletInfo;
	
	private List<StoreProductInfo> storeProductInfo;
	
	private List<DefaultRecvInfo> defaultRecvInfo;
	
	private List<RecvInfo> recvInfo;
	
	private List<DefaultDeliverInfo> defaultDeliverInfo;
	
	private List<DeliverInfo> deliverInfo;
	
	public MerchantOutletFavorite(){
		super();
	}

	public MerchantOutletFavorite(Long _id,
			List<StoreOutletInfo> storeOutletInfo,
			List<StoreProductInfo> storeProductInfo,
			List<DefaultRecvInfo> defaultRecvInfo, List<RecvInfo> recvInfo,
			List<DefaultDeliverInfo> defaultDeliverInfo,
			List<DeliverInfo> deliverInfo) {
		super();
		this._id = _id;
		this.storeOutletInfo = storeOutletInfo;
		this.storeProductInfo = storeProductInfo;
		this.defaultRecvInfo = defaultRecvInfo;
		this.recvInfo = recvInfo;
		this.defaultDeliverInfo = defaultDeliverInfo;
		this.deliverInfo = deliverInfo;
	}

	@JSONField(name = "_id", serialize = true, deserialize = true)
	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	@JSONField(name = "store_outlet_info", serialize = true, deserialize = true)
	public List<StoreOutletInfo> getStoreOutletInfo() {
		return storeOutletInfo;
	}

	public void setStoreOutletInfo(List<StoreOutletInfo> storeOutletInfo) {
		this.storeOutletInfo = storeOutletInfo;
	}

	@JSONField(name = "store_product_info", serialize = true, deserialize = true)
	public List<StoreProductInfo> getStoreProductInfo() {
		return storeProductInfo;
	}

	public void setStoreProductInfo(List<StoreProductInfo> storeProductInfo) {
		this.storeProductInfo = storeProductInfo;
	}

	@JSONField(name = "default_recv_info", serialize = true, deserialize = true)
	public List<DefaultRecvInfo> getDefaultRecvInfo() {
		return defaultRecvInfo;
	}

	public void setDefaultRecvInfo(List<DefaultRecvInfo> defaultRecvInfo) {
		this.defaultRecvInfo = defaultRecvInfo;
	}

	@JSONField(name = "recv_info", serialize = true, deserialize = true)
	public List<RecvInfo> getRecvInfo() {
		return recvInfo;
	}

	public void setRecvInfo(List<RecvInfo> recvInfo) {
		this.recvInfo = recvInfo;
	}

	@JSONField(name = "default_deliver_info", serialize = true, deserialize = true)
	public List<DefaultDeliverInfo> getDefaultDeliverInfo() {
		return defaultDeliverInfo;
	}

	public void setDefaultDeliverInfo(List<DefaultDeliverInfo> defaultDeliverInfo) {
		this.defaultDeliverInfo = defaultDeliverInfo;
	}

	@JSONField(name = "deliver_info", serialize = true, deserialize = true)
	public List<DeliverInfo> getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(List<DeliverInfo> deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	
	
}
