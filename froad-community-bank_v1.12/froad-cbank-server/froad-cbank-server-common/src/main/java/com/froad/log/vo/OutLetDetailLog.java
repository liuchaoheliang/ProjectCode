package com.froad.log.vo;

import java.util.List;

import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.Location;


/**
 * 类描述：门店日志实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-3上午11:07:21 
 */
public class OutLetDetailLog {
	private String id;
	private String outlet_id;
	private String outlet_name;
	private String merchant_id;
	private String merchant_name;
	private Long create_time;
	private Long update_time;
	private String address;
	private String phone;
	private String province;
	private String city;
	private String county;
	private String audit_state;
	private Boolean is_enable;
	private Location location;
	private String category_id;
	private List<CategoryInfo> category_info;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOutlet_id() {
		return outlet_id;
	}
	public void setOutlet_id(String outlet_id) {
		this.outlet_id = outlet_id;
	}
	public String getOutlet_name() {
		return outlet_name;
	}
	public void setOutlet_name(String outlet_name) {
		this.outlet_name = outlet_name;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public Long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAudit_state() {
		return audit_state;
	}
	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}
	public Boolean getIs_enable() {
		return is_enable;
	}
	public void setIs_enable(Boolean is_enable) {
		this.is_enable = is_enable;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public List<CategoryInfo> getCategory_info() {
		return category_info;
	}
	public void setCategory_info(List<CategoryInfo> category_info) {
		this.category_info = category_info;
	}
	
}
