package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.DataState;

/**
 * 商户点评
 * @author FQ
 *
 */
public class MerchantComment extends BaseEntity{
	
	/**
	 * 星级
	 * @author FQ
	 *
	 */
	public enum StarLevel {
		one_star, 
		two_star,
		three_star,
		four_star,
		five_star
	}
	
	/**
	 * 打分
	 * @author FQ
	 *
	 */
	public enum Point{
		zero,
		one,
		two,
		three,
		four
	}
	
	private StarLevel starLevel;//总体评价星级
	private Point productPoint;//产品打分
	private Point environmentPoint;//环境打分
	private Point servePoint;//服务打分
	private String commentDescription;//评论说明
	
	private Long merchantOutletId;//商户门店
	private Long memberCode;//用户
	
	private DataState dataState;//数据状态：有效、删除
	
	public StarLevel getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(StarLevel starLevel) {
		this.starLevel = starLevel;
	}
	public Point getProductPoint() {
		return productPoint;
	}
	public void setProductPoint(Point productPoint) {
		this.productPoint = productPoint;
	}
	public Point getEnvironmentPoint() {
		return environmentPoint;
	}
	public void setEnvironmentPoint(Point environmentPoint) {
		this.environmentPoint = environmentPoint;
	}
	public Point getServePoint() {
		return servePoint;
	}
	public void setServePoint(Point servePoint) {
		this.servePoint = servePoint;
	}
	public String getCommentDescription() {
		return commentDescription;
	}
	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}
	public Long getMerchantOutletId() {
		return merchantOutletId;
	}
	public void setMerchantOutletId(Long merchantOutletId) {
		this.merchantOutletId = merchantOutletId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public DataState getDataState() {
		return dataState;
	}
	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}
	
}