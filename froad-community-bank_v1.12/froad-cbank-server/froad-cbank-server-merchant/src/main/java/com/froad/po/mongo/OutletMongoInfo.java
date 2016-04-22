package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class OutletMongoInfo implements java.io.Serializable{

	/**
	  * @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = -2699606469220953738L;
	
	@JSONField(name = "_id", serialize = true, deserialize = true)
	private String id;
	
	@JSONField(name = "default_image", serialize = true, deserialize = true)
	private String defaultImage;
	
	@JSONField(name = "outlet_name", serialize = true, deserialize = true)
	private String outletName;
	
	@JSONField(name = "address", serialize = true, deserialize = true)
	private String address;
	
	@JSONField(name = "dis", serialize = true, deserialize = true)
	private double dis;
	
	@JSONField(name = "star_level", serialize = true, deserialize = true)
	private double starLevel;
	
	@JSONField(name = "phone", serialize = true, deserialize = true)
	private String phone;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDefaultImage() {
		return defaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getDis() {
		return dis;
	}
	public void setDis(double dis) {
		this.dis =  Math.floor(dis);
	}
	public double getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(double starLevel) {
		this.starLevel = starLevel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


}

