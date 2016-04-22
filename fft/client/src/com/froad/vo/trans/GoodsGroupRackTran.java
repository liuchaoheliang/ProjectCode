package com.froad.vo.trans;

import com.froad.client.goodsGroupRack.Merchant;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.Goods;


public class GoodsGroupRackTran {

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String goodsId;		//商品ID			
	private Goods goods;		//
	private String groupPrice;	//团购价
	private String perNumber;	//每人限购
	private String perminNumber;//最低购买
	private String beginTime;	//团购开始时间
	private String endTime;		//团购结束时间
	private String ticketBeginTime;		//团购卷使用有效期开始时间
	private String ticketEndTime;		//团购卷使用有效期结束时间
	private String colseBillBeginTime;	//关帐开始时间（期间不允许退款）
	private String colseBillEndTime;	//关帐结束时间（期间不允许退款）
	private String seoTitle;	//推广标题
	private String seoKeyword;	//推广关键字
	private String seoDescription;		//推广描述
	private String isRack;		//是否上架 0-否 1-是
	private String isAllowrefund;		//是否同意退款 0-否 1-是
	private String rackTime;	//上架时间
	private String inspectors;	//审核人
	private String marketTotalNumber;	//销售数量
	private String refundTotalNumber;	//退款数量
	private String isCash;		//是否启用现金 0-否 1-是
	private String isFftPoint;	//是否启用分分通积分 0-否 1-是
	private String isBankPoint;	//是否启用银行积分 0-否 1-是
	private String isFftpointCash;		//是否启用分分通积分+现金 0-否 1-是
	private String isBankpointCash;		//是否启用银行积分+现金 0-否 1-是
	private String isFftpointcashRatioPricing;	//是否启用分分通积分+现金比例定价 0-否 1-是
	private String isBankpointcashRatioPricing;	//是否启用银行积分+现金比例定价 0-否 1-是
	private String cashPricing;	//现金定价
	private String fftPointPricing;		//分分通积分定价
	private String bankPointPricing;	//银行积分定价
	private String fftpointCashPricing;	//分分通积分和现金定价
	private String bankpointCashPricing;//银行积分和现金定价
	private String fftpointcashRatioPricing;	//分分通+现金比例定价
	private String bankpointcashRatioPricing;	//银行+现金比例定价
	private String state;		//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;	//创建时间
	private String updateTime;	//更新时间
	private String remark;		//备注
	
	private String cashPricingDesc;
	private String fftPointPricingDesc;
	private String bankPointPricingDesc;
	private String fftPointCashPricingDesc;
	private String bankPointCashPricingDesc;
	private String fftPointCashRatioPricingDesc;
	private String bankPointCashRatioPricingDesc;
	private String merchantId;					//商户ID
	private Merchant merchant;
	private String sellerId;
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getCashPricingDesc() {
		return cashPricingDesc;
	}

	public void setCashPricingDesc(String cashPricingDesc) {
		this.cashPricingDesc = cashPricingDesc;
	}

	public String getFftPointPricingDesc() {
		return fftPointPricingDesc;
	}

	public void setFftPointPricingDesc(String fftPointPricingDesc) {
		this.fftPointPricingDesc = fftPointPricingDesc;
	}

	public String getBankPointPricingDesc() {
		return bankPointPricingDesc;
	}

	public void setBankPointPricingDesc(String bankPointPricingDesc) {
		this.bankPointPricingDesc = bankPointPricingDesc;
	}
	

	public String getFftPointCashPricingDesc() {
		return fftPointCashPricingDesc;
	}

	public void setFftPointCashPricingDesc(String fftPointCashPricingDesc) {
		this.fftPointCashPricingDesc = fftPointCashPricingDesc;
	}

	public String getBankPointCashPricingDesc() {
		return bankPointCashPricingDesc;
	}

	public void setBankPointCashPricingDesc(String bankPointCashPricingDesc) {
		this.bankPointCashPricingDesc = bankPointCashPricingDesc;
	}

	public String getFftPointCashRatioPricingDesc() {
		return fftPointCashRatioPricingDesc;
	}

	public void setFftPointCashRatioPricingDesc(String fftPointCashRatioPricingDesc) {
		this.fftPointCashRatioPricingDesc = fftPointCashRatioPricingDesc;
	}

	public String getBankPointCashRatioPricingDesc() {
		return bankPointCashRatioPricingDesc;
	}

	public void setBankPointCashRatioPricingDesc(
			String bankPointCashRatioPricingDesc) {
		this.bankPointCashRatioPricingDesc = bankPointCashRatioPricingDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId == null ? null : goodsId.trim();
	}

	public String getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(String groupPrice) {
		this.groupPrice = groupPrice == null ? null : groupPrice.trim();
	}

	public String getPerNumber() {
		return perNumber;
	}

	public void setPerNumber(String perNumber) {
		this.perNumber = perNumber == null ? null : perNumber.trim();
	}

	public String getPerminNumber() {
		return perminNumber;
	}

	public void setPerminNumber(String perminNumber) {
		this.perminNumber = perminNumber == null ? null : perminNumber.trim();
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime == null ? null : beginTime.trim();
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}

	public String getTicketBeginTime() {
		return ticketBeginTime;
	}

	public void setTicketBeginTime(String ticketBeginTime) {
		this.ticketBeginTime = ticketBeginTime == null ? null : ticketBeginTime
				.trim();
	}

	public String getTicketEndTime() {
		return ticketEndTime;
	}

	public void setTicketEndTime(String ticketEndTime) {
		this.ticketEndTime = ticketEndTime == null ? null : ticketEndTime
				.trim();
	}

	public String getColseBillBeginTime() {
		return colseBillBeginTime;
	}

	public void setColseBillBeginTime(String colseBillBeginTime) {
		this.colseBillBeginTime = colseBillBeginTime == null ? null
				: colseBillBeginTime.trim();
	}

	public String getColseBillEndTime() {
		return colseBillEndTime;
	}

	public void setColseBillEndTime(String colseBillEndTime) {
		this.colseBillEndTime = colseBillEndTime == null ? null
				: colseBillEndTime.trim();
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle == null ? null : seoTitle.trim();
	}

	public String getSeoKeyword() {
		return seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword == null ? null : seoKeyword.trim();
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription == null ? null : seoDescription
				.trim();
	}
	
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getIsRack() {
		return isRack;
	}

	public void setIsRack(String isRack) {
		this.isRack = isRack == null ? null : isRack.trim();
	}

	public String getIsAllowrefund() {
		return isAllowrefund;
	}

	public void setIsAllowrefund(String isAllowrefund) {
		this.isAllowrefund = isAllowrefund == null ? null : isAllowrefund
				.trim();
	}

	public String getRackTime() {
		return rackTime;
	}

	public void setRackTime(String rackTime) {
		this.rackTime = rackTime == null ? null : rackTime.trim();
	}

	public String getInspectors() {
		return inspectors;
	}

	public void setInspectors(String inspectors) {
		this.inspectors = inspectors == null ? null : inspectors.trim();
	}

	public String getMarketTotalNumber() {
		return marketTotalNumber;
	}

	public void setMarketTotalNumber(String marketTotalNumber) {
		this.marketTotalNumber = marketTotalNumber == null ? null
				: marketTotalNumber.trim();
	}

	public String getRefundTotalNumber() {
		return refundTotalNumber;
	}

	public void setRefundTotalNumber(String refundTotalNumber) {
		this.refundTotalNumber = refundTotalNumber == null ? null
				: refundTotalNumber.trim();
	}

	public String getIsCash() {
		return isCash;
	}

	public void setIsCash(String isCash) {
		this.isCash = isCash == null ? null : isCash.trim();
	}

	public String getIsFftPoint() {
		return isFftPoint;
	}

	public void setIsFftPoint(String isFftPoint) {
		this.isFftPoint = isFftPoint == null ? null : isFftPoint.trim();
	}

	public String getIsBankPoint() {
		return isBankPoint;
	}

	public void setIsBankPoint(String isBankPoint) {
		this.isBankPoint = isBankPoint == null ? null : isBankPoint.trim();
	}

	public String getIsFftpointCash() {
		return isFftpointCash;
	}

	public void setIsFftpointCash(String isFftpointCash) {
		this.isFftpointCash = isFftpointCash == null ? null : isFftpointCash
				.trim();
	}

	public String getIsBankpointCash() {
		return isBankpointCash;
	}

	public void setIsBankpointCash(String isBankpointCash) {
		this.isBankpointCash = isBankpointCash == null ? null : isBankpointCash
				.trim();
	}

	public String getIsFftpointcashRatioPricing() {
		return isFftpointcashRatioPricing;
	}

	public void setIsFftpointcashRatioPricing(String isFftpointcashRatioPricing) {
		this.isFftpointcashRatioPricing = isFftpointcashRatioPricing == null ? null
				: isFftpointcashRatioPricing.trim();
	}

	public String getIsBankpointcashRatioPricing() {
		return isBankpointcashRatioPricing;
	}

	public void setIsBankpointcashRatioPricing(
			String isBankpointcashRatioPricing) {
		this.isBankpointcashRatioPricing = isBankpointcashRatioPricing == null ? null
				: isBankpointcashRatioPricing.trim();
	}

	public String getCashPricing() {
		return cashPricing;
	}

	public void setCashPricing(String cashPricing) {
		this.cashPricing = cashPricing == null ? null : cashPricing.trim();
	}

	public String getFftPointPricing() {
		return fftPointPricing;
	}

	public void setFftPointPricing(String fftPointPricing) {
		this.fftPointPricing = fftPointPricing == null ? null : fftPointPricing
				.trim();
	}

	public String getBankPointPricing() {
		return bankPointPricing;
	}

	public void setBankPointPricing(String bankPointPricing) {
		this.bankPointPricing = bankPointPricing == null ? null
				: bankPointPricing.trim();
	}

	public String getFftpointCashPricing() {
		return fftpointCashPricing;
	}

	public void setFftpointCashPricing(String fftpointCashPricing) {
		this.fftpointCashPricing = fftpointCashPricing == null ? null
				: fftpointCashPricing.trim();
	}

	public String getBankpointCashPricing() {
		return bankpointCashPricing;
	}

	public void setBankpointCashPricing(String bankpointCashPricing) {
		this.bankpointCashPricing = bankpointCashPricing == null ? null
				: bankpointCashPricing.trim();
	}

	public String getFftpointcashRatioPricing() {
		return fftpointcashRatioPricing;
	}

	public void setFftpointcashRatioPricing(String fftpointcashRatioPricing) {
		this.fftpointcashRatioPricing = fftpointcashRatioPricing == null ? null
				: fftpointcashRatioPricing.trim();
	}

	public String getBankpointcashRatioPricing() {
		return bankpointcashRatioPricing;
	}

	public void setBankpointcashRatioPricing(String bankpointcashRatioPricing) {
		this.bankpointcashRatioPricing = bankpointcashRatioPricing == null ? null
				: bankpointcashRatioPricing.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
}