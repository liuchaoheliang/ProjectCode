package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商品信息 Mongodb
 */
public class ProductMongo implements java.io.Serializable {
    private static final long serialVersionUID = 8982183162965078825L;
    
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
     * 商品单价
     */
    private Integer money;
    
	/**
	 * 商品VIP价格
	 */
	private Integer vipMoney;
    
	/**
	 * 普通购买数量
	 */
	private Integer quantity;
    
	/**
	 * VIP购买数量
	 */
	private Integer vipQuantity;
    
	/**
	 * 赠送积分
	 */
	private Integer points;
	
	/**
	 * 配送或者自提方式
	 */
	private String deliveryOption;
	
	/**
	 * 配送或者自提状态
	 */
	private String deliveryState;
    
	/**
	 * 预售选择的自提网点
	 */
	private String orgCode;
	
	/**
	 * 预售选择的自提网点
	 */
	private String orgName;
    
	/**
	 * 运费
	 */
	private Integer deliveryMoney;
	
	/**
	 * 子订单商品类型
	 */
	private String type;
	/**
	 * 评论状态
	 */
	private String commentState;
	
	/**
	 * 赠送积分状态：0-赠送失败，1-赠送成功,2-未赠送，3-退分失败，4-退分成功
	 */
	private String givePointState;
	
	/**
	 * 活动ID
	 */
	private String activeId;
	
	/**
	 * 满赠活动ID
	 */
	private String giveActiveId;
	
	/**
	 * 普通价每单位商品支付积分
	 */
	private Integer[] pointArray;
	
	/**
	 * VIP价每单位商品支付积分
	 */
	private Integer[] vipPointArray;
	
	/**
	 * 商品总支付积分
	 */
	private Integer totalPoint;
	
	/**
	 * 普通价每单位商品实付现金
	 */
	private Integer[] cashArray;
	
	/**
	 * VIP价每单位商品实付现金
	 */
	private Integer[] vipCashArray;
	
	/**
	 * 商品总实付现金
	 */
	private Integer totalCash;
	
	/**
	 * 每单位普通价商品优惠金额数组
	 */
	private Integer[] cutMoneyArray;
	
	/**
	 * 每单位VIP价商品优惠金额数组
	 */
	private Integer[] vipCutMoneyArray;
	
	/**
	 * 商品总优惠金额
	 */
	private Integer totalCutMoney;
	
    @JSONField(name="product_id")
	public String getProductId() {
		return productId;
	}
	
    @JSONField(name="product_id")
	public void setProductId(String productId) {
		this.productId = productId;
	}

    @JSONField(name="product_name")
	public String getProductName() {
		return productName;
	}

    @JSONField(name="product_name")
	public void setProductName(String productName) {
		this.productName = productName;
	}

    @JSONField(name="product_image")
	public String getProductImage() {
		return productImage;
	}

    @JSONField(name="product_image")
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

    @JSONField(name="money")
	public Integer getMoney() {
		return money;
	}

    @JSONField(name="money")
	public void setMoney(Integer money) {
		this.money = money;
	}

    @JSONField(name="vip_money")
	public Integer getVipMoney() {
		return vipMoney;
	}

    @JSONField(name="vip_money")
	public void setVipMoney(Integer vipMoney) {
		this.vipMoney = vipMoney;
	}

    @JSONField(name="quantity")
	public Integer getQuantity() {
		return quantity;
	}

    @JSONField(name="quantity")
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

    @JSONField(name="vip_quantity")
	public Integer getVipQuantity() {
		return vipQuantity;
	}

    @JSONField(name="vip_quantity")
	public void setVipQuantity(Integer vipQuantity) {
		this.vipQuantity = vipQuantity;
	}

    @JSONField(name="points")
	public Integer getPoints() {
		return points;
	}

    @JSONField(name="points")
	public void setPoints(Integer points) {
		this.points = points;
	}

    @JSONField(name="delivery_option")
	public String getDeliveryOption() {
		return deliveryOption;
	}

    @JSONField(name="delivery_option")
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

    @JSONField(name="delivery_state")
	public String getDeliveryState() {
		return deliveryState;
	}

    @JSONField(name="delivery_state")
	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}
    
    @JSONField(name="org_code")
	public String getOrgCode() {
		return orgCode;
	}

    @JSONField(name="org_code")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@JSONField(name="delivery_money")
	public Integer getDeliveryMoney() {
		return deliveryMoney;
	}

	@JSONField(name="delivery_money")
	public void setDeliveryMoney(Integer deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	@JSONField(name="type")
	public String getType() {
		return type;
	}

	@JSONField(name="type")
	public void setType(String type) {
		this.type = type;
	}

	@JSONField(name="org_name")
	public String getOrgName() {
		return orgName;
	}

	@JSONField(name="org_name")
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@JSONField(name="comment_state")
	public String getCommentState() {
		return commentState;
	}
	@JSONField(name="comment_state")
	public void setCommentState(String commentState) {
		this.commentState = commentState;
	}

	@JSONField(name="give_point_state")
	public String getGivePointState() {
		return givePointState;
	}

	@JSONField(name="give_point_state")
	public void setGivePointState(String givePointState) {
		this.givePointState = givePointState;
	}


	@JSONField(name="active_id")
	public String getActiveId() {
		return activeId;
	}

	@JSONField(name="active_id")
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	@JSONField(name="point_array")
	public Integer[] getPointArray() {
		return pointArray;
	}

	@JSONField(name="point_array")
	public void setPointArray(Integer[] pointArray) {
		this.pointArray = pointArray;
	}

	@JSONField(name="vip_point_array")
	public Integer[] getVipPointArray() {
		return vipPointArray;
	}

	@JSONField(name="vip_point_array")
	public void setVipPointArray(Integer[] vipPointArray) {
		this.vipPointArray = vipPointArray;
	}

	@JSONField(name="total_point")
	public Integer getTotalPoint() {
		return totalPoint;
	}

	@JSONField(name="total_point")
	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	@JSONField(name="cash_array")
	public Integer[] getCashArray() {
		return cashArray;
	}

	@JSONField(name="cash_array")
	public void setCashArray(Integer[] cashArray) {
		this.cashArray = cashArray;
	}

	@JSONField(name="vip_cash_array")
	public Integer[] getVipCashArray() {
		return vipCashArray;
	}

	@JSONField(name="vip_cash_array")
	public void setVipCashArray(Integer[] vipCashArray) {
		this.vipCashArray = vipCashArray;
	}

	@JSONField(name="cut_money_array")
	public Integer[] getCutMoneyArray() {
		return cutMoneyArray;
	}

	@JSONField(name="cut_money_array")
	public void setCutMoneyArray(Integer[] cutMoneyArray) {
		this.cutMoneyArray = cutMoneyArray;
	}

	@JSONField(name="vip_cut_money_array")
	public Integer[] getVipCutMoneyArray() {
		return vipCutMoneyArray;
	}

	@JSONField(name="vip_cut_money_array")
	public void setVipCutMoneyArray(Integer[] vipCutMoneyArray) {
		this.vipCutMoneyArray = vipCutMoneyArray;
	}

	@JSONField(name="total_cut_money")
	public Integer getTotalCutMoney() {
		return totalCutMoney;
	}

	@JSONField(name="total_cut_money")
	public void setTotalCutMoney(Integer totalCutMoney) {
		this.totalCutMoney = totalCutMoney;
	}

	@JSONField(name="total_cash")
	public Integer getTotalCash() {
		return totalCash;
	}

	@JSONField(name="total_cash")
	public void setTotalCash(Integer totalCash) {
		this.totalCash = totalCash;
	}

	@JSONField(name="give_active_id")
	public String getGiveActiveId() {
		return giveActiveId;
	}

	@JSONField(name="give_active_id")
	public void setGiveActiveId(String giveActiveId) {
		this.giveActiveId = giveActiveId;
	}

		
	
	
}
