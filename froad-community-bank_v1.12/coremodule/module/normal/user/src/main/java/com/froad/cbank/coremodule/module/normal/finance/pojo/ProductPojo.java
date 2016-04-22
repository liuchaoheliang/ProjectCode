package com.froad.cbank.coremodule.module.normal.finance.pojo;

/**
 * 
 * <p>标题: —— 理财产品基本信息</p>
 * <p>说明: —— 简要描述职责、使用注意事项等</p>
 * <p>创建时间：2015-6-15下午04:22:31</p>
 * <p>作者: 吴菲</p>
 */
public class ProductPojo {
	
		/** 理财产品Id **/
	    private String productId;
	    
	    /** 理财产品名称  **/
	    private String productName;
	    
	    /** 第一档收益率  **/
		private String yieldMinRate;
		
		/** 第二档收益率   **/
		private String yieldMaxRate;
		
		/** 产品期限,单位：天   **/
		private int deadline;
		
		/** 单笔购买起点金额,单位：元  **/
		private double buyMinAmount;
		
		/** 服务器当前时间 **/
		private long serverTime;
		
		/** 销售开始日期 **/
		private long startTime;
		
		/** 销售结束日期 **/
		private long endTime;
		/** 销售状态(1-未售磐，2-已售磐，3-已过期,4-即将销售) **/
		private String sellStatus;

		
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
	
		public String getYieldMinRate() {
			return yieldMinRate;
		}
	
		public void setYieldMinRate(String yieldMinRate) {
			this.yieldMinRate = yieldMinRate;
		}
	
		public String getYieldMaxRate() {
			return yieldMaxRate;
		}
	
		public void setYieldMaxRate(String yieldMaxRate) {
			this.yieldMaxRate = yieldMaxRate;
		}
	
		public int getDeadline() {
			return deadline;
		}
	
		public void setDeadline(int deadline) {
			this.deadline = deadline;
		}
	
		public double getBuyMinAmount() {
			return buyMinAmount;
		}
	
		public void setBuyMinAmount(double buyMinAmount) {
			this.buyMinAmount = buyMinAmount;
		}
	
		public long getServerTime() {
			return serverTime;
		}
	
		public void setServerTime(long serverTime) {
			this.serverTime = serverTime;
		}
	
		public long getStartTime() {
			return startTime;
		}
	
		public void setStartTime(long startTime) {
			this.startTime = startTime;
		}
	
		public long getEndTime() {
			return endTime;
		}
	
		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}
	
		public String getSellStatus() {
			return sellStatus;
		}
	
		public void setSellStatus(String sellStatus) {
			this.sellStatus = sellStatus;
		}
		
	
}
