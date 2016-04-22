package com.froad.log.vo;

import java.util.List;
import com.froad.log.vo.OrderProduct;

public class OrderDetailLog {
	private String id = "";
	private String order_id = "";
	private String sub_order_id = "";
	private String order_type = "";
	private List<OrderProduct> products; 
	private String org_code = "";
	private String f_org_code = "";
	private String s_org_code = "";
	private String t_org_code = "";
	private String l_org_code = "";
	private String member_code = "";
	private String is_seckill = "";
	private String is_vip_order = "";
	private Integer total_price = 0;
	private Integer fft_points  = 0;
	private Integer bank_points = 0;
	private Integer cash_price = 0;
	private String payment_method = "";
	private Long payment_time = 0L;
	private Long create_time = 0L;
	private Long update_time = 0L;
	private Integer vip_discount = 0;
	private String merchant_id = "";
	private String merchant_name = "";
	private String merchant_category_id = "";
	private String merchant_category_name = "";
	private String outlet_id = "";
	private Long merchant_user_id = 0L;
	private String create_source  = "";
	private String order_status = "";
	private String recv_id = "";
	private String payment_type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSub_order_id() {
		return sub_order_id;
	}
	public void setSub_order_id(String sub_order_id) {
		this.sub_order_id = sub_order_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public String getIs_seckill() {
		return is_seckill;
	}
	public void setIs_seckill(String is_seckill) {
		this.is_seckill = is_seckill;
	}
	public String getIs_vip_order() {
		return is_vip_order;
	}
	public void setIs_vip_order(String is_vip_order) {
		this.is_vip_order = is_vip_order;
	}
	public Integer getTotal_price() {
		return total_price;
	}
	public void setTotal_price(Integer total_price) {
		this.total_price = total_price;
	}
	public Integer getFft_points() {
		return fft_points;
	}
	public void setFft_points(Integer fft_points) {
		this.fft_points = fft_points;
	}
	public Integer getBank_points() {
		return bank_points;
	}
	public void setBank_points(Integer bank_points) {
		this.bank_points = bank_points;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getRecv_id() {
		return recv_id;
	}
	public void setRecv_id(String recv_id) {
		this.recv_id = recv_id;
	}
	public Integer getCash_price() {
		return cash_price;
	}
	public void setCash_price(Integer cash_price) {
		this.cash_price = cash_price;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public Long getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(Long payment_time) {
		this.payment_time = payment_time;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public Integer getVip_discount() {
		return vip_discount;
	}
	public void setVip_discount(Integer vip_discount) {
		this.vip_discount = vip_discount;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getOutlet_id() {
		return outlet_id;
	}
	public void setOutlet_id(String outlet_id) {
		this.outlet_id = outlet_id;
	}
	public Long getMerchant_user_id() {
		return merchant_user_id;
	}
	public void setMerchant_user_id(Long merchant_user_id) {
		this.merchant_user_id = merchant_user_id;
	}
	public String getCreate_source() {
		return create_source;
	}
	public void setCreate_source(String create_source) {
		this.create_source = create_source;
	}
	public List<OrderProduct> getProducts() {
		return products;
	}
	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}
	public String getF_org_code() {
		return f_org_code;
	}
	public void setF_org_code(String f_org_code) {
		this.f_org_code = f_org_code;
	}
	public String getS_org_code() {
		return s_org_code;
	}
	public void setS_org_code(String s_org_code) {
		this.s_org_code = s_org_code;
	}
	public String getT_org_code() {
		return t_org_code;
	}
	public void setT_org_code(String t_org_code) {
		this.t_org_code = t_org_code;
	}
	public String getL_org_code() {
		return l_org_code;
	}
	public void setL_org_code(String l_org_code) {
		this.l_org_code = l_org_code;
	}
	public Long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
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
	
	

}
