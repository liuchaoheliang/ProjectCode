package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * 商品限购相关实体
 * @author liuchao
 *
 */
public class ProductBuyLimitPojo {
		private String  min;
		private String  max;
		private String minVip ;
		private String  maxVip;
		private String  maxCard;
		private String startTime ;
		private String endTime ;
		
		public String getMin() {
			return min;
		}
		public void setMin(String min) {
			this.min = min;
		}
		public String getMax() {
			return max;
		}
		public void setMax(String max) {
			this.max = max;
		}
		public String getMinVip() {
			return minVip;
		}
		public void setMinVip(String minVip) {
			this.minVip = minVip;
		}
		public String getMaxVip() {
			return maxVip;
		}
		public void setMaxVip(String maxVip) {
			this.maxVip = maxVip;
		}
		public String getMaxCard() {
			return maxCard;
		}
		public void setMaxCard(String maxCard) {
			this.maxCard = maxCard;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		
}
