package com.froad.log.vo;

/**
 * 类描述：商品BO类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: f-road.com.cn
 * @time: 2015-10-28下午11:11:25
 */

public class ProductDetailLog {
	private String product_id;
	private String product_name;
	private String product_type;
	private String merchant_id;
	private Long category_id;
	private String category_tree_path;
	private String audit_state;
	private String audit_pro_proson;
	private Long audit_pro_time;
	private String audit_pro_orgcode;
	private String audit_city_proson;
	private String org_code;
	private Long audit_city_time;
	private Long start_time;
	private Long end_time;
	private Long expire_start_time;
	private Long expire_end_time;
	private Integer market_price;
	private Integer price;
	private Integer vip_price;
	private Long create_time;
	private String is_marketable;
	private String is_one_up;
	private String is_one_down;
	
	private String merchant_name;
	private String merchant_category_id;
	private String merchant_category_name;
	private Long audit_time;
	
	private Long delivery_time;

	public Long getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(Long audit_time) {
		this.audit_time = audit_time;
	}

	public String getMerchant_name() {
		return merchant_name;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	public String getMerchant_category_id() {
		return merchant_category_id;
	}

	public void setMerchant_category_id(String merchant_category_id) {
		this.merchant_category_id = merchant_category_id;
	}

	public String getMerchant_category_name() {
		return merchant_category_name;
	}

	public void setMerchant_category_name(String merchant_category_name) {
		this.merchant_category_name = merchant_category_name;
	}

	public String getIs_one_up() {
		return is_one_up;
	}

	public void setIs_one_up(String is_one_up) {
		this.is_one_up = is_one_up;
	}

	public String getIs_one_down() {
		return is_one_down;
	}

	public void setIs_one_down(String is_one_down) {
		this.is_one_down = is_one_down;
	}

	public String getIs_marketable() {
		return is_marketable;
	}

	public void setIs_marketable(String is_marketable) {
		this.is_marketable = is_marketable;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public String getCategory_tree_path() {
		return category_tree_path;
	}

	public void setCategory_tree_path(String category_tree_path) {
		this.category_tree_path = category_tree_path;
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

	public String getAudit_city_proson() {
		return audit_city_proson;
	}

	public void setAudit_city_proson(String audit_city_proson) {
		this.audit_city_proson = audit_city_proson;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public Long getAudit_city_time() {
		return audit_city_time;
	}

	public void setAudit_city_time(Long audit_city_time) {
		this.audit_city_time = audit_city_time;
	}

	public Long getStart_time() {
		return start_time;
	}

	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	}

	public Long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}

	public Long getExpire_start_time() {
		return expire_start_time;
	}

	public void setExpire_start_time(Long expire_start_time) {
		this.expire_start_time = expire_start_time;
	}

	public Long getExpire_end_time() {
		return expire_end_time;
	}

	public void setExpire_end_time(Long expire_end_time) {
		this.expire_end_time = expire_end_time;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	public Integer getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Integer market_price) {
		this.market_price = market_price;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getVip_price() {
		return vip_price;
	}

	public void setVip_price(Integer vip_price) {
		this.vip_price = vip_price;
	}

	public Long getDelivery_time() {
		return delivery_time;
}

	public void setDelivery_time(Long delivery_time) {
		this.delivery_time = delivery_time;
	}

}
