/**
 * Project Name : froad-cbank-server-common
 * File Name : Snippet.java
 * Package Name : com.froad.log.vo
 * Date : 2015年11月25日下午3:54:31
 * Copyright (c) 2015, i2Finance Software All Rights Reserved
 *
 */
package com.froad.log.vo;

import java.util.List;

/**
 * 结算日志详情
 * Description : TODO<br/>
 * Project Name : froad-cbank-server-common<br/>
 * File Name : SettlementLogDetail.java<br/>
 * 
 * Date : 2015年11月25日 下午3:56:13 <br/> 
 * @author KaiweiXiang
 * @version
 */
public class SettlementLogDetail {
	private String settlement_id;
	private Long create_time;
	private String merchant_id;
	private String merchant_name;
	private String merchant_user_id;
	private int money;
	private String order_id;
	private String outlet_id;
	private String outlet_name;
	private String payment_id;
	private Integer product_count;
	private String product_id;
	private String product_name;
	private String settle_state;
	private String sub_order_id;
	private List<String> tickets;
	private String type;
	public String getSettlement_id() {
		return settlement_id;
	}
	public void setSettlement_id(String settlement_id) {
		this.settlement_id = settlement_id;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
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
	public String getMerchant_user_id() {
		return merchant_user_id;
	}
	public void setMerchant_user_id(String merchant_user_id) {
		this.merchant_user_id = merchant_user_id;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public Integer getProduct_count() {
		return product_count;
	}
	public void setProduct_count(Integer product_count) {
		this.product_count = product_count;
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
	public String getSettle_state() {
		return settle_state;
	}
	public void setSettle_state(String settle_state) {
		this.settle_state = settle_state;
	}
	public String getSub_order_id() {
		return sub_order_id;
	}
	public void setSub_order_id(String sub_order_id) {
		this.sub_order_id = sub_order_id;
	}
	public List<String> getTickets() {
		return tickets;
	}
	public void setTickets(List<String> tickets) {
		this.tickets = tickets;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}

