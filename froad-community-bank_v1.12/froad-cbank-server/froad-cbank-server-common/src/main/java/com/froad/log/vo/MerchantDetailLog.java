package com.froad.log.vo;

import java.util.List;

import com.froad.po.mongo.TypeInfo;

/**
 * 类描述：商户日志实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-3上午10:40:28 
 */
public class MerchantDetailLog implements java.io.Serializable{
	private String id;
	private String merchant_id;
	private String merchant_name;
	private String order_type;
	private List<TypeInfo> merchant_type;
	private String license;
	private Long create_time;
	private String merchant_category;
	private String acct_name;
	private String acct_number;
	private String phone;
	private boolean is_enable;
	private String org_code;
	private String org_name;
	private String contract_staff;
	private String audit_state;
	private Long audit_time;
	private String audit_pro_proson;
	private Long audit_pro_time;
	private String audit_pro_orgcode;
	private String audit_city_person;
	private Long audit_city_time;
	private Long update_time;
	private String audit_city_orgcode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public List<TypeInfo> getMerchant_type() {
		return merchant_type;
	}
	public void setMerchant_type(List<TypeInfo> merchant_type) {
		this.merchant_type = merchant_type;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public String getMerchant_category() {
		return merchant_category;
	}
	public void setMerchant_category(String merchant_category) {
		this.merchant_category = merchant_category;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	public String getAcct_number() {
		return acct_number;
	}
	public void setAcct_number(String acct_number) {
		this.acct_number = acct_number;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getContract_staff() {
		return contract_staff;
	}
	public void setContract_staff(String contract_staff) {
		this.contract_staff = contract_staff;
	}
	public String getAudit_state() {
		return audit_state;
	}
	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}
	public String getAudit_pro_proson() {
		return audit_pro_proson;
	}
	public void setAudit_pro_proson(String audit_pro_proson) {
		this.audit_pro_proson = audit_pro_proson;
	}
	public Long getAudit_pro_time() {
		return audit_pro_time;
	}
	public void setAudit_pro_time(Long audit_pro_time) {
		this.audit_pro_time = audit_pro_time;
	}
	public String getAudit_pro_orgcode() {
		return audit_pro_orgcode;
	}
	public void setAudit_pro_orgcode(String audit_pro_orgcode) {
		this.audit_pro_orgcode = audit_pro_orgcode;
	}
	public String getAudit_city_person() {
		return audit_city_person;
	}
	public void setAudit_city_person(String audit_city_person) {
		this.audit_city_person = audit_city_person;
	}
	public Long getAudit_city_time() {
		return audit_city_time;
	}
	public void setAudit_city_time(Long audit_city_time) {
		this.audit_city_time = audit_city_time;
	}
	public String getAudit_city_orgcode() {
		return audit_city_orgcode;
	}
	public void setAudit_city_orgcode(String audit_city_orgcode) {
		this.audit_city_orgcode = audit_city_orgcode;
	}
	public Long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public boolean isIs_enable() {
		return is_enable;
	}
	public void setIs_enable(boolean is_enable) {
		this.is_enable = is_enable;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public Long getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(Long audit_time) {
		this.audit_time = audit_time;
	}
	
}
