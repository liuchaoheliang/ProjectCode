
	 /**
  * 文件名：SecurityResult.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.bean;

import java.util.Date;

import com.froad.fft.enums.PayMethodShow;


	/**
 * 类描述：ajax查询券信息返回实体
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午4:07:21 
 */
public class SecurityResult {
	
	private String SecurityNo;//券号
	private String userName;//提货人名
	private String phone;//提货人手机号
	private String deliveryName;//提货点名称
	private String productName;//商品名称
	private Integer number;//购买的数量（）
	private String isConsume;//是否消费
	private String createTime;//下单时间
	private String payMethodShow;//支付方式
	
	public String getSecurityNo() {
		return SecurityNo;
	}
	public void setSecurityNo(String securityNo) {
		SecurityNo = securityNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getIsConsume() {
		return isConsume;
	}
	public void setIsConsume(Boolean isConsume) {
		if(isConsume){
			this.isConsume="是";
		}else{			
			this.isConsume = "否";
		}
	}
	public String getPayMethodShow() {
		return payMethodShow;
	}
	public void setPayMethodShow(PayMethodShow payMethodShow) {
		this.payMethodShow = payMethodShow.getDescribe();
	}
	
	
}
