package com.froad.cbank.coremodule.module.normal.user.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月12日 下午3:40:16
 * @version 1.0
 * @desc 门店评论POJO
 */
public class OutletCommentPojo {
	private String id;
	private String merchantId;
	
	@NotEmpty("门店ID不可为空")
	private String outletId;
	private Long createTime;
	private String outletName;
	private String memberName;
	
	@NotEmpty("评论星级不可为空")
	private Integer starLevel;
	
	@NotEmpty("评论内容不可为空")
	@Regular(reg="^[\\s\\S]{5,500}$", value="评论内容最少5个字符，最多500个字符")
	private String commentDescription;
	private String recomment;
	private String merchantName;
	private Long recommentTime;
	private String merchantUserName;
	private String outletPhone;
	private String outletImage;
	
	/**
	 * commentType:0-商户评论(默认) 1-面对面评论   2-其它
	 */
	@NotEmpty("评论来源不可为空")
	private Integer commentType;
	
	
	/* orderId: 订单编号 */
	private String orderId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(Integer starLevel) {
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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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
	public String getOutletPhone() {
		return outletPhone;
	}
	public void setOutletPhone(String outletPhone) {
		this.outletPhone = outletPhone;
	}
	public String getOutletImage() {
		return outletImage;
	}
	public void setOutletImage(String outletImage) {
		this.outletImage = outletImage;
	}
	public Integer getCommentType() {
		return commentType;
	}
	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
