package com.froad.cbank.coremodule.module.normal.user.vo;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.module.normal.user.pojo.CartResActivePojo;

/**
 * 商品信息
 * 王炜华		wangweihua@f-road.com.cn
 * 
 */
public class ProductViewVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5521629201077248207L;
	/**
	 * 商品ID
	 */
	private String productId; 

	/**
	 * 商品名称
	 */
	private String productName; 
	/**
	 * 商品图片
	 */
	private String productImage; 
	/**
	 * 价格
	 */
	private double money; 
	/**
	 * VIP优惠价格
	 */
	private double vipMoney; 
	/**
	 * 数量
	 */
	private int quantity; 
	/**
	 * vip数量
	 */
	private int vipQuantity; 
	/**
	 * 商品状态 0-未上架 1-商品正常 2-商品下架 3-商品库存不足 4-商品限购数量超限 5-商品过期
	 */
	private String status; 
	/**
	 * 门店ID
	 */
	private String orgCode; 
	/**
	 * 门店名称
	 */
	private String orgName; 
	/**
	 * 门店是否有效
	 */
	private boolean orgStatus; 
	/**
	 * 更新时间
	 */
	private long time; 
	/**
	 * 商品类型 1-团购2-预售3-名优特惠6-精品商城
	 */
	private String type; 
	
	/**
	 * 优惠金额
	 */
	private double preAmount;
	
	/**
	 * 优惠金额
	 */
	private double deliveryMoney;
	
	/**
	 * 0000-正常，其它为非正常
	 */
	public String errCode; 
	/**
	 * errCode非0000时有值
	 */
	public String errMsg; 
	/**
	 * 关联的活动集合
	 */
	private  List<CartResActivePojo>  activeList;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getVipMoney() {
		return vipMoney;
	}

	public void setVipMoney(double vipMoney) {
		this.vipMoney = vipMoney;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getVipQuantity() {
		return vipQuantity;
	}

	public void setVipQuantity(int vipQuantity) {
		this.vipQuantity = vipQuantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public boolean isOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(boolean orgStatus) {
		this.orgStatus = orgStatus;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPreAmount() {
		return preAmount;
	}

	public void setPreAmount(double preAmount) {
		this.preAmount = preAmount;
	}

	public double getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public List<CartResActivePojo> getActiveList() {
		return activeList;
	}

	public void setActiveList(List<CartResActivePojo> activeList) {
		this.activeList = activeList;
	}
	
	
}
