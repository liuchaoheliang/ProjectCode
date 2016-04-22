package com.froad.log.vo;

public class OrderProduct {

	private String product_id = "";
	private String product_name = "";
	private int quantity = 0;
	private Integer money = 0;
	private int vip_quantity = 0;
	private Integer vip_money = 0;
	private String category_id = "";
	private String take_org_code = "";
	private String take_org_name = "";
	private Integer delivery_money = 0;
	private String delivery_state = "";
	private String delivery_option = "";
	private String type = "";

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public int getVip_quantity() {
		return vip_quantity;
	}

	public void setVip_quantity(int vip_quantity) {
		this.vip_quantity = vip_quantity;
	}

	public Integer getVip_money() {
		return vip_money;
	}

	public void setVip_money(Integer vip_money) {
		this.vip_money = vip_money;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getTake_org_code() {
		return take_org_code;
	}

	public void setTake_org_code(String take_org_code) {
		this.take_org_code = take_org_code;
	}

	public String getTake_org_name() {
		return take_org_name;
	}

	public void setTake_org_name(String take_org_name) {
		this.take_org_name = take_org_name;
	}

	public Integer getDelivery_money() {
		return delivery_money;
	}

	public void setDelivery_money(Integer delivery_money) {
		this.delivery_money = delivery_money;
	}

	public String getDelivery_state() {
		return delivery_state;
	}

	public void setDelivery_state(String delivery_state) {
		this.delivery_state = delivery_state;
	}

	public String getDelivery_option() {
		return delivery_option;
	}

	public void setDelivery_option(String delivery_option) {
		this.delivery_option = delivery_option;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
