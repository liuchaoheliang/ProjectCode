package com.froad.cbank.coremodule.normal.boss.pojo.merchant;

import java.util.List;

import com.froad.thrift.vo.CreateTimeFilterVo;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.RecommentNotEmptyVo;
import com.froad.thrift.vo.StarLevelFilterVo;

public class OutletCommentVoRes {
	/**
	   * 主键id
	   */
	private String id; // required
	  /**
	   * 商户id
	   */
	  private String merchantId; // required
	  /**
	   * 门店id
	   */
	  private String outletId; // required
	  /**
	   * 门店id
	   */
	  private String commentId; // required
	  /**
	   * 创建id
	   */
	  private long createTime; // required
	  /**
	   * 门店名称
	   */
	  private String outletName; // required
	  /**
	   * 排序
	   */
	  private int orderValue; // required
	  /**
	   * 会员编号
	   */
	  private String memberCode; // required
	  /**
	   * 会员名称
	   */
	  private String memberName; // required
	  /**
	   * 星级
	   */
	  private int starLevel; // required
	  /**
	   * 评论描述
	   */
	  private String commentDescription; // required
	  /**
	   * 回复
	   */
	  private String recomment; // required
	  /**
	   * 客户端id
	   */
	  private String clientId; // required
	  /**
	   * 1 机构码
	   */
	  private String forgCode; // required
	  /**
	   * 2 机构码
	   */
	  private String sorgCode; // required
	  /**
	   * 3 机构码
	   */
	  private String torgCode; // required
	  /**
	   * 4 机构码
	   */
	  private String lorgCode; // required
	  /**
	   * 机构码
	   */
	  private String orgCode; // required
	  /**
	   * 门店名称
	   */
	  private String merchantName; // required
	  /**
	   * 回复时间
	   */
	  private long recommentTime; // required
	  /**
	   * 商户用户名称-回复人
	   */
	  private String merchantUserName; // required
	  /**
	   * 回复是否为空 - 查询使用
	   */
	  private RecommentNotEmptyVo recommentNotEmpty; // required
	  /**
	   * 星级过滤 - 查询使用
	   */
	  private StarLevelFilterVo starLevelFilter; // required
	  /**
	   * 创建时间过滤 - 查询使用
	   */
	  private CreateTimeFilterVo createTimeFilter; // required
	  /**
	   * 门店电话
	   */
	  private String outletPhone; // required
	  /**
	   * 门店图片
	   */
	  private String outletImage; // required
	  /**
	   * 评论人头像
	   */
	  private String memberHead; // required
	  /**
	   * 回复人头像
	   */
	  private String recommentHead; // required
	  /**
	   * 客户端
	   */
	  private String clientName;
	  /**
	   * 业务类型
	   */
	  private String businessType;
	  
	  /**
	   * 评价时间
	   */
	  private String commentTime;
	  
	  /**
	   * 评价语
	   */
	  private String commentContent;
	  /**
	   * 星级
	   */
	  private String startLevel;
	  /**
	   * 状态
	   */
	  private String commentStatus;
	  
	  private OutletCommentVo outletComment;
	  
	  private List<OutletCommentVoRes>  merchantCommentList;

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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public int getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
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

	public void setRecomment(String recomment) {
		this.recomment = recomment;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getForgCode() {
		return forgCode;
	}

	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}

	public String getSorgCode() {
		return sorgCode;
	}

	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}

	public String getTorgCode() {
		return torgCode;
	}

	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}

	public String getLorgCode() {
		return lorgCode;
	}

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

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public long getRecommentTime() {
		return recommentTime;
	}

	public void setRecommentTime(long recommentTime) {
		this.recommentTime = recommentTime;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public RecommentNotEmptyVo getRecommentNotEmpty() {
		return recommentNotEmpty;
	}

	public void setRecommentNotEmpty(RecommentNotEmptyVo recommentNotEmpty) {
		this.recommentNotEmpty = recommentNotEmpty;
	}

	public StarLevelFilterVo getStarLevelFilter() {
		return starLevelFilter;
	}

	public void setStarLevelFilter(StarLevelFilterVo starLevelFilter) {
		this.starLevelFilter = starLevelFilter;
	}

	public CreateTimeFilterVo getCreateTimeFilter() {
		return createTimeFilter;
	}

	public void setCreateTimeFilter(CreateTimeFilterVo createTimeFilter) {
		this.createTimeFilter = createTimeFilter;
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

	public OutletCommentVo getOutletComment() {
		return outletComment;
	}

	public void setOutletComment(OutletCommentVo outletComment) {
		this.outletComment = outletComment;
	}

	public List<OutletCommentVoRes> getMerchantCommentList() {
		return merchantCommentList;
	}

	public void setMerchantCommentList(List<OutletCommentVoRes> merchantCommentList) {
		this.merchantCommentList = merchantCommentList;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}

	public String getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}
	  
}
