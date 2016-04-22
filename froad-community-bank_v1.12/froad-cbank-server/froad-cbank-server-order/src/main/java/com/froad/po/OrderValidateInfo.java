package com.froad.po;

/**
 * 
 * 订单校验返回数据
 * @author Administrator
 *
 */
public class OrderValidateInfo {
	
	/**
	 * 1.会员号
	 */
	private long memberCode;
	
	/**
	 * 1.会员名称
	 */
	private String memberName;
	
	/**************秒杀商品缓存信息--S**************/
	/**
	 * 秒杀商品限购数量
	 */
	private Integer seckillProductBuyLimit;
	
	/**
	 * 秒杀商品结束时间
	 */
	private String productEndTime;
	
	/**************秒杀商品缓存信息--E**************/
	
	

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getSeckillProductBuyLimit() {
		return seckillProductBuyLimit;
	}

	public void setSeckillProductBuyLimit(Integer seckillProductBuyLimit) {
		this.seckillProductBuyLimit = seckillProductBuyLimit;
	}

	public String getProductEndTime() {
		return productEndTime;
	}

	public void setProductEndTime(String productEndTime) {
		this.productEndTime = productEndTime;
	}
	

}
