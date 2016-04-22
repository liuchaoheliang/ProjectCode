package com.froad.po;

public class ProductCommentFilter {

    /**
     * 星级别
     */
    private String starLevel;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 排序
     */
    private Integer orderValue;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 评论人登登录号
     */
    private String memberCode;
    /**
     * 评论人
     */
    private String memberName;
    /**
     * 评价时间开始范围
     */
    private Long pointStartTime;
    /**
     * 评价时间结束范围
     */
    private Long pointEndTime;
    /**
     * 是否回复
     */
    private int isReply;
    /**
     * 组织机构码
     */
    private String orgCode;
    /**
     * 机构级别
     */
    private String orgLevel;
    /**
     * 查询所有时间
     */
    private String isSeachAll;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 商户ID
     */
    private String merchantId;
    /**
     * 订单id
     */
    private String orderId;
    private String type;
    
	public String getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
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
    public Integer getOrderValue() {
        return orderValue;
    }
    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getMemberCode() {
        return memberCode;
    }
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public Long getPointStartTime() {
        return pointStartTime;
    }
    public void setPointStartTime(Long pointStartTime) {
        this.pointStartTime = pointStartTime;
    }
    public Long getPointEndTime() {
        return pointEndTime;
    }
    public void setPointEndTime(Long pointEndTime) {
        this.pointEndTime = pointEndTime;
    }
    public int getIsReply() {
        return isReply;
    }
    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getIsSeachAll() {
		return isSeachAll;
	}
	public void setIsSeachAll(String isSeachAll) {
		this.isSeachAll = isSeachAll;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    
}
