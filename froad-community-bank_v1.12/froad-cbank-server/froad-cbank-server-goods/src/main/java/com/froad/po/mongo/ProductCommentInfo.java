package com.froad.po.mongo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductCommentInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3626966215409537997L;
    
    /**
     * 评论id
     */
    private String commentId;
    /**
     * 评论时间
     */
    private Long createTime;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 商品Id
     */
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品类型
     */
    private String type;
    /**
     * 排序
     */
    private int orderValue;
    /**
     * 回复评价内容
     */
    private String recomment;
    /**
     * 商户id
     */
    private String merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 交易编号
     */
    private String orderId;
    /**
     * 交易类型
     */
    private String orderType;
    /**
     * 用户编码
     */
    private String memberCode;
    /**
     * 评论人
     */
    private String memberName;
    /**
     * 评价星级
     */
    private int starLevel;
    /**
     * 评价内容
     */
    private String commentDescription;
    /**
     * 缩略图片
     */
    private String ImagePic;
    /**
     * 一级机构
     */
    private String forgCode;
    /**
     * 二级机构
     */
    private String sorgCode;
    /**
     * 三级机构
     */
    private String torgCode;
    /**
     * 四级机构
     */
    private String lorgCode;
    /**
     * 回复时间
     */
    private Long recommentTime;
    /**
     * 回复人
     */
    private String merchantUserName;
    /**
     * 商品电话
     */
    private String phone;
    /**
     * 大交易编号
     */
    private String bigOrderId;
    
    @JSONField(name="_id")
    public String getCommentId() {
        if(commentId!=null && commentId.indexOf("\"$oid\":")!=-1){
            commentId = commentId.substring(commentId.indexOf("\"$oid\":")+"\"$oid\":".length()+1, commentId.lastIndexOf("\""));
        }
        return commentId;
    }
    
    @JSONField(name="_id")
    public void setCommentId(String commentId) {
        if(commentId!=null && commentId.indexOf("\"$oid\":")!=-1){
            this.commentId = commentId.substring(commentId.indexOf("\"$oid\":")+"\"$oid\":".length()+1, commentId.lastIndexOf("\""));
        } else {
            this.commentId = commentId;
        }
    }
    
    @JSONField(name="create_time")
    public Long getCreateTime() {
        return createTime;
    }

    @JSONField(name="create_time")
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    @JSONField(name="client_id")
    public String getClientId() {
        return clientId;
    }
    
    @JSONField(name="client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
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
    
    @JSONField(name="type")
    public String getType() {
        return type;
    }

    @JSONField(name="type")
    public void setType(String type) {
        this.type = type;
    }
    
    @JSONField(name="order_value")
    public int getOrderValue() {
        return orderValue;
    }
    
    @JSONField(name="order_value")
    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }
    
    @JSONField(name="recomment")
    public String getRecomment() {
        return recomment;
    }
    
    @JSONField(name="recomment")
    public void setRecomment(String recomment) {
        this.recomment = recomment;
    }
    
    @JSONField(name="merchant_id")
	public String getMerchantId() {
		return merchantId;
	}
    
    @JSONField(name="merchant_id")
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
    
    @JSONField(name="merchant_name")
	public String getMerchantName() {
		return merchantName;
	}
    
    @JSONField(name="merchant_name")
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
    
    @JSONField(name="order_id")
	public String getOrderId() {
		return orderId;
	}
    
    @JSONField(name="order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    
    @JSONField(name="order_type")
	public String getOrderType() {
		return orderType;
	}
    
    @JSONField(name="order_type")
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
    
    @JSONField(name="member_code")
	public String getMemberCode() {
		return memberCode;
	}
    
    @JSONField(name="member_code")
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
    
    @JSONField(name="member_name")
	public String getMemberName() {
		return memberName;
	}
    
    @JSONField(name="member_name")
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
    
    @JSONField(name="start_level")
	public int getStarLevel() {
		return starLevel;
	}
    
    @JSONField(name="start_level")
	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}
    
    @JSONField(name="comment_description")
	public String getCommentDescription() {
		return commentDescription;
	}
    
    @JSONField(name="comment_description")
	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}
    
    @JSONField(name="thumbnail")
	public String getImagePic() {
		return ImagePic;
	}
    
    @JSONField(name="thumbnail")
	public void setImagePic(String imagePic) {
		ImagePic = imagePic;
	}
    
    @JSONField(name="forg_code")
	public String getForgCode() {
		return forgCode;
	}
    
    @JSONField(name="forg_code")
	public void setForgCode(String forgCode) {
        this.forgCode = forgCode;
	}
    
    @JSONField(name="sorg_code")
	public String getSorgCode() {
		return sorgCode;
	}
    
    @JSONField(name="sorg_code")
	public void setSorgCode(String sorgCode) {
        this.sorgCode = sorgCode;
	}
    
    @JSONField(name="torg_code")
	public String getTorgCode() {
		return torgCode;
	}
    
    @JSONField(name="torg_code")
	public void setTorgCode(String torgCode) {
        this.torgCode = torgCode;
	}
    
    @JSONField(name="lorg_code")
	public String getLorgCode() {
		return lorgCode;
	}
    @JSONField(name="lorg_code")
	public void setLorgCode(String lorgCode) {
        this.lorgCode = lorgCode;
	}
    
    @JSONField(name="recomment_time")
	public Long getRecommentTime() {
		return recommentTime;
	}
    
    @JSONField(name="recomment_time")
	public void setRecommentTime(Long recommentTime) {
		this.recommentTime = recommentTime;
	}
    
    @JSONField(name="merchant_user_name")
	public String getMerchantUserName() {
		return merchantUserName;
	}
    
    @JSONField(name="merchant_user_name")
	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}
    
    @JSONField(name="phone")
	public String getPhone() {
		return phone;
	}
    
    @JSONField(name="phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}
    
    @JSONField(name="big_order_id")
    public String getBigOrderId() {
        return bigOrderId;
    }
    
    @JSONField(name="big_order_id")
    public void setBigOrderId(String bigOrderId) {
        this.bigOrderId = bigOrderId;
    }
    
}
