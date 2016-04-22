package com.froad.cbank.coremodule.module.normal.user.pojo;



public class GenerateOrderPojo {


	private String phone;
	private String recvId;
	private String areaId;//精品商城地区ID
	private String remark;
	private String createSource;
	private boolean isShoppingCartOrder;
	
	/**
	 * 启用联盟积分
	 */
	private String unionPoint;
	/**
	 * 启用银行积分
	 */
	private String bankPoint;
	/**
	 * couponsNo:优惠券号
	 */
	private String couponsNo;
	/**
	 * redPacketNo:红包券号
	 */
	private String redPacketNo;
	
	/**
	 * payPassWord:支付密码
	 */
	private String payPassWord;
	
	private AddProductPojo[] addProducts;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRecvId() {
		return recvId;
	}

	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

	
	public boolean isIsShoppingCartOrder() {
		return isShoppingCartOrder;
	}

	public void setIsShoppingCartOrder(boolean isShoppingCartOrder) {
		this.isShoppingCartOrder = isShoppingCartOrder;
	}

	public AddProductPojo[] getAddProducts() {
		return addProducts;
	}

	public void setAddProducts(AddProductPojo[] addProducts) {
		this.addProducts = addProducts;
	}

	public boolean isShoppingCartOrder() {
		return isShoppingCartOrder;
	}

	public void setShoppingCartOrder(boolean isShoppingCartOrder) {
		this.isShoppingCartOrder = isShoppingCartOrder;
	}

	public String getUnionPoint() {
		return unionPoint;
	}

	public void setUnionPoint(String unionPoint) {
		this.unionPoint = unionPoint;
	}

	public String getBankPoint() {
		return bankPoint;
	}

	public void setBankPoint(String bankPoint) {
		this.bankPoint = bankPoint;
	}

	public String getCouponsNo() {
		return couponsNo;
	}

	public void setCouponsNo(String couponsNo) {
		this.couponsNo = couponsNo;
	}

	public String getRedPacketNo() {
		return redPacketNo;
	}

	public void setRedPacketNo(String redPacketNo) {
		this.redPacketNo = redPacketNo;
	}

	public String getPayPassWord() {
		return payPassWord;
	}

	public void setPayPassWord(String payPassWord) {
		this.payPassWord = payPassWord;
	}
	
}
