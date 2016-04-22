/**
 * Project Name:coremodule-user
 * File Name:QrcodeOrderPojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.pojo
 * Date:2016年1月4日下午3:57:46
 * Copyright (c) 2016, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * ClassName:QrcodeOrderPojo
 * Reason:	 添加惠付创建订单po
 * Date:     2016年1月4日 下午3:57:46
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class QrcodeOrderPojo {

		private String phone;
		/**
		 * notDiscountAmount:不参加折扣金额
		 */
		private double notDiscountAmount;
		/**
		 * discountRate:折扣率
		 */
		private String discountRate; 
		/**
		 * outletId:门店Id
		 */
		@NotEmpty("门店不能为空")
		private String outletId;
		/**
		 * merchantId:商户Id
		 */
		private String merchantId;
		/**
		 * consumeAmount:消费总金额
		 */
		@NotEmpty("消费金额不能为空")
		private double consumeAmount;
		
		private String remark;
		private String createSource;
		
		/**
		 * 联盟积分
		 */
		private double unionPoint;
		/**
		 * 银行积分
		 */
		private int bankPoint;
		/**
		 * couponsNo:优惠券号
		 */
		private String couponsNo;
		/**
		 * redPacketNo:红包券号
		 */
		private String redPacketNo;
		
		/**
		 * payPassWord:支付密码
		 */
		private String payPassWord;
		
		@NotEmpty("是否启用折扣优惠不能为空")
		private Boolean isEnableDiscount;

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}



		public String getOutletId() {
			return outletId;
		}

		public void setOutletId(String outletId) {
			this.outletId = outletId;
		}

		public String getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getCreateSource() {
			return createSource;
		}

		public void setCreateSource(String createSource) {
			this.createSource = createSource;
		}


		public double getUnionPoint() {
			return unionPoint;
		}

		public void setUnionPoint(double unionPoint) {
			this.unionPoint = unionPoint;
		}

		public int getBankPoint() {
			return bankPoint;
		}

		public void setBankPoint(int bankPoint) {
			this.bankPoint = bankPoint;
		}

		public String getCouponsNo() {
			return couponsNo;
		}

		public void setCouponsNo(String couponsNo) {
			this.couponsNo = couponsNo;
		}

		public String getRedPacketNo() {
			return redPacketNo;
		}

		public void setRedPacketNo(String redPacketNo) {
			this.redPacketNo = redPacketNo;
		}

		public String getPayPassWord() {
			return payPassWord;
		}

		public void setPayPassWord(String payPassWord) {
			this.payPassWord = payPassWord;
		}

		public double getNotDiscountAmount() {
			return notDiscountAmount;
		}

		public void setNotDiscountAmount(double notDiscountAmount) {
			this.notDiscountAmount = notDiscountAmount;
		}

		public String getDiscountRate() {
			return discountRate;
		}

		public void setDiscountRate(String discountRate) {
			this.discountRate = discountRate;
		}

		public double getConsumeAmount() {
			return consumeAmount;
		}

		public void setConsumeAmount(double consumeAmount) {
			this.consumeAmount = consumeAmount;
		}

		public Boolean getIsEnableDiscount() {
			return isEnableDiscount;
		}

		public void setIsEnableDiscount(Boolean isEnableDiscount) {
			this.isEnableDiscount = isEnableDiscount;
		}
		
}
