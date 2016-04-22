package com.froad.po.resp;

import com.froad.enums.ReportOrderType;
import com.froad.enums.ReportPayType;
import com.froad.utils.DateSystemUtil;

public class BaseResp {
	private String day;   //纬度天
	private String week;  //纬度周
	private String month; //纬度月
	private String year;  //纬度年
	private String clientId; //银行ClientId
	private String platform; //业务平台
	private String orgCode;  //所属机构
	private String orgName;  //所属机构名称
	private String orgLevel;
	private String provinceOrgCode; //1级机构
	private String cityOrgCode;     //2级机构
	private String countyOrgCode;   //3级机构
	private String villageOrgCode;  //4级机构
	private String merchantId;      //商户ID
	private String merchantName;    //商户名称
	private String merchantCategoryId;//商户类目
	private String merchantCategoryName; //商户类目名称
	private Integer orderType;             //业务类型
	private String  orderTypeName;   //业务类型名称
	private String payType;   //支付方式
	private String payTypeName; //支付方式名称
	
	//为了数据展示原有week、month、year已经特殊处理，需要重新处理这些值
	private String weekTemp;
	private String monthTemp;
 	 
	
	public String getWeekTemp() {
		return weekTemp;
	}
	public void setWeekTemp(String weekTemp) {
		this.weekTemp = weekTemp;
	}
	public String getMonthTemp() {
		return monthTemp;
	}
	public void setMonthTemp(String monthTemp) {
		this.monthTemp = monthTemp;
	} 
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getWeek() {
		this.weekTemp=week;
		if(this.week!=null && this.year!=null){
			week=DateSystemUtil.getWeekInfoFirstDay(Integer.valueOf(this.year), Integer.valueOf(this.week))+"至"+DateSystemUtil.getWeekInfoLastDay(Integer.valueOf(this.year), Integer.valueOf(this.week));
		}
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getMonth() {
		this.monthTemp=month;
		if(this.month!=null && this.year!=null){
			month=this.year+"年"+this.month+"月";
		}
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getProvinceOrgCode() {
		return provinceOrgCode;
	}
	public void setProvinceOrgCode(String provinceOrgCode) {
		this.provinceOrgCode = provinceOrgCode;
	}
	public String getCityOrgCode() {
		return cityOrgCode;
	}
	public void setCityOrgCode(String cityOrgCode) {
		this.cityOrgCode = cityOrgCode;
	}
	public String getCountyOrgCode() {
		return countyOrgCode;
	}
	public void setCountyOrgCode(String countyOrgCode) {
		this.countyOrgCode = countyOrgCode;
	}
	public String getVillageOrgCode() {
		return villageOrgCode;
	}
	public void setVillageOrgCode(String villageOrgCode) {
		this.villageOrgCode = villageOrgCode;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantCategoryId() {
		return merchantCategoryId;
	}
	public void setMerchantCategoryId(String merchantCategoryId) {
		this.merchantCategoryId = merchantCategoryId;
	}
	public String getMerchantCategoryName() {
		return merchantCategoryName;
	}
	public void setMerchantCategoryName(String merchantCategoryName) {
		this.merchantCategoryName = merchantCategoryName;
	}
	 
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getOrderTypeName() {
		orderTypeName=ReportOrderType.getReportOrderType(this.orderType+"").getDescribe();
		return orderTypeName;
	}
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayTypeName() {
		payTypeName=ReportPayType.getReportPayType(this.payType).getDescribe();
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	
	
}
