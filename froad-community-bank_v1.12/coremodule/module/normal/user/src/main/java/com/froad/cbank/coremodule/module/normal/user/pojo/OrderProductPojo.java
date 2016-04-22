package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

public class OrderProductPojo {

	private String productId;
    
    private String productName;
    
    private String productImage;

    private double money;
    
    private int quantity;
    
    private double vipMoney;
    
    private int vipQuantity;
    
    private double deliveryMoney;
    
    private double totalMoney;
    
    private String deliveryState;
    
    private Boolean isEnableComment;
    
    private Boolean isCommented;
    
    private int canRefundCount;
    
    private long startTime;
    
    private long endTime;
    /**
     * 机构号
     */
    private String orgCode;
    /**
     * 机构号
     */
    private String orgName;
    /**
     * 退货数量
     */
    public int refundCount;
    /**
     * 可收货数量
     */
    public int canReceiveCount;
    

    /**
     * 商品类型
     */
    public String productType; 
    

	/**
	 * 关联商品活动
	 */	
	private List<FindActivePojo> activeList;
	
	/**
	 * totalCutMoney:商品满减优惠金额
	 */
	private double totalCutMoney;
	
	/**
	 * vouCutMoeny:上面红包或者优惠券的优惠金额
	 */
	private double vouCutMoeny;
	
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


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public long getEndTime() {
		return endTime;
	}


	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}


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


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getVipMoney() {
		return vipMoney;
	}


	public void setVipMoney(double vipMoney) {
		this.vipMoney = vipMoney;
	}


	public int getVipQuantity() {
		return vipQuantity;
	}


	public void setVipQuantity(int vipQuantity) {
		this.vipQuantity = vipQuantity;
	}


	public double getDeliveryMoney() {
		return deliveryMoney;
	}


	public void setDeliveryMoney(double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}


	public double getTotalMoney() {
		return totalMoney;
	}


	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}


	public String getDeliveryState() {
		return deliveryState;
	}


	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}


	public int getCanRefundCount() {
		return canRefundCount;
	}


	public void setCanRefundCount(int canRefundCount) {
		this.canRefundCount = canRefundCount;
	}


	public int getRefundCount() {
		return refundCount;
	}


	public void setRefundCount(int refundCount) {
		this.refundCount = refundCount;
	}


	public int getCanReceiveCount() {
		return canReceiveCount;
	}


	public void setCanReceiveCount(int canReceiveCount) {
		this.canReceiveCount = canReceiveCount;
	}


	public Boolean getIsEnableComment() {
		return isEnableComment;
	}


	public void setIsEnableComment(Boolean isEnableComment) {
		this.isEnableComment = isEnableComment;
	}


	public Boolean getIsCommented() {
		return isCommented;
	}


	public void setIsCommented(Boolean isCommented) {
		this.isCommented = isCommented;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}



	public List<FindActivePojo> getActiveList() {
		return activeList;
	}


	public void setActiveList(List<FindActivePojo> activeList) {
		this.activeList = activeList;
	}


	public Double getTotalCutMoney() {
		return totalCutMoney;
	}


	public void setTotalCutMoney(double totalCutMoney) {
		this.totalCutMoney = totalCutMoney;
	}


	public Double getVouCutMoeny() {
		return vouCutMoeny;
	}


	public void setVouCutMoeny(double vouCutMoeny) {
		this.vouCutMoeny = vouCutMoeny;
	}
	
	
}
