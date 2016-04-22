package com.froad.cbank.coremodule.module.normal.user.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月13日 上午10:14:44
 * @version 1.0
 * @desc 商品评论POJO
 */
public class ProductCommentPojo {
	private String commentId;
	private int totalCount;//作为返回参数查询到的数据条数
	
	@NotEmpty("商品ID不可为空")
	private String productId;
	private String productName;
	private String merchantId;
	private String merchantName;
	
	@NotEmpty("子订单ID不可为空")
	private String orderId;
	
	@NotEmpty("订单类型不可为空")
	private String orderType;
	private String memberName;
	
	@NotEmpty("评分星级不可为空")
	private Short starLevel;
	
	@NotEmpty("评论内容不可为空")
	@Regular(reg="^[\\s\\S]{5,500}$", value="评论内容最少5个字符，最多500个字符")
	private String commentDescription;
	private String recomment;
	private Long createTime;
	private Long recommentTime;
	private String merchantUserName;
	private String phone;
	private String imagePic;
	
	@NotEmpty("父订单ID不可为空")
	private String bigOrderId;
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Short getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(Short starLevel) {
		this.starLevel = starLevel;
	}
	public String getCommentDescription() {
		return commentDescription;
	}
	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}
	public String getRecomment() {
		return recomment;
	}
	public void setRecomment(String recomment) {
		this.recomment = recomment;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getRecommentTime() {
		return recommentTime;
	}
	public void setRecommentTime(Long recommentTime) {
		this.recommentTime = recommentTime;
	}
	public String getMerchantUserName() {
		return merchantUserName;
	}
	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImagePic() {
		return imagePic;
	}
	public void setImagePic(String imagePic) {
		this.imagePic = imagePic;
	}
	public String getBigOrderId() {
		return bigOrderId;
	}
	public void setBigOrderId(String bigOrderId) {
		this.bigOrderId = bigOrderId;
	}
}
