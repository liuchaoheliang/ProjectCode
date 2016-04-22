package com.froad.db.chonggou.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.common.mongo.OutletCommentMongo;

/**
 * CbOutlet po. 
 */


public class OutletComment implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	public String id;
	public String merchantId; // required
	public String outletId; // required
	public long createTime; // required
	public String outletName; // required
	public int orderValue; // required
	
	public MemberInfo memberInfo;
	public String memberCode; // required
	public String memberName; // required
	
	public PointInfo pointInfo;
	public int starLevel; // required
	public String commentDescription; // required
	
	public String recomment; // required
	public String clientId;
	
	public String forgCode; // required
	public String sorgCode; // required
	public String torgCode; // required
	public String lorgCode; // required
	
	public String orgCode; // required
	public String merchantName; // required
	
	public long recommentTime; // required
	public String merchantUserName; // required
	
	public RecommentNotEmpty recommentNotEmpty; // required
	public StarLevelFilter starLevelFilter; // required
	public CreateTimeFilter createTimeFilter; // required
	
	public String outletPhone;
	
	public String outletImage;
	
	public String memberHead;

	public String recommentHead;
	
	// Constructors



	/** default constructor */
	public OutletComment() {
	}

	/** full constructor */
	public OutletComment(String merchantId, String outletId, long createTime, String outletName, int orderValue, String memberCode, String memberName, int starLevel, String commentDescription) {
		this.merchantId = merchantId;
		this.outletId = outletId;
		this.createTime = createTime;
		this.outletName = outletName;
		this.orderValue = orderValue;
		this.memberCode = memberCode;
		this.memberName = memberName;
		this.starLevel = starLevel;
		this.commentDescription = commentDescription;
		
	}

	// Property accessors
	
	public String getId() {
		return id;
	}
	
	@JSONField(name="_id")
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_MERCHANT_ID)
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOutletId() {
		return outletId;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_OUTLET_ID)
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public long getCreateTime() {
		return createTime;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_CREATE_TIME)
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getOutletName() {
		return outletName;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_OUTLET_NAME)
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public int getOrderValue() {
		return orderValue;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_ORDER_VALUE)
	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_MEMBER_INFO)
	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
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

	public PointInfo getPointInfo() {
		return pointInfo;
	}
	
	@JSONField(name=OutletCommentMongo.MONGO_KEY_POINT_INFO)
	public void setPointInfo(PointInfo pointInfo) {
		this.pointInfo = pointInfo;
	}
	
	public int getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(int starLevel) {
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

	@JSONField(name=OutletCommentMongo.MONGO_KEY_RECOMMENT)
	public void setRecomment(String recomment) {
		this.recomment = recomment;
	}
	

	public String getClientId() {
		return clientId;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_CLIENT_ID)
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getForgCode() {
		return forgCode;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_FORG_CODE)
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}

	public String getSorgCode() {
		return sorgCode;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_SORG_CODE)
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}

	public String getTorgCode() {
		return torgCode;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_TORG_CODE)
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}

	public String getLorgCode() {
		return lorgCode;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_LORG_CODE)
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_MERCHANT_NAME)
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public long getRecommentTime() {
		return recommentTime;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_RECOMMENT_TIME)
	public void setRecommentTime(long recommentTime) {
		this.recommentTime = recommentTime;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_MERCHANT_USER_NAME)
	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public RecommentNotEmpty getRecommentNotEmpty() {
		return recommentNotEmpty;
	}

	public void setRecommentNotEmpty(RecommentNotEmpty recommentNotEmpty) {
		this.recommentNotEmpty = recommentNotEmpty;
	}

	public StarLevelFilter getStarLevelFilter() {
		return starLevelFilter;
	}

	public void setStarLevelFilter(StarLevelFilter starLevelFilter) {
		this.starLevelFilter = starLevelFilter;
	}

	public CreateTimeFilter getCreateTimeFilter() {
		return createTimeFilter;
	}

	public void setCreateTimeFilter(CreateTimeFilter createTimeFilter) {
		this.createTimeFilter = createTimeFilter;
	}

	public String getOutletPhone() {
		return outletPhone;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_OUTLET_PHONE)
	public void setOutletPhone(String outletPhone) {
		this.outletPhone = outletPhone;
	}

	public String getOutletImage() {
		return outletImage;
	}

	@JSONField(name=OutletCommentMongo.MONGO_KEY_OUTLET_IMAGE)
	public void setOutletImage(String outletImage) {
		this.outletImage = outletImage;
	}
	

	public String getMemberHead() {
		return memberHead;
	}

	public void setMemberHead(String memberHead) {
		this.memberHead = memberHead;
	}

	public String getRecommentHead() {
		return recommentHead;
	}

	public void setRecommentHead(String recommentHead) {
		this.recommentHead = recommentHead;
	}

}