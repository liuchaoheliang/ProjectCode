package com.froad.CB.po;

import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.merchant.Merchant;

public class GoodsRebateRack extends Pager{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer sellerId;//卖家id
	private Seller seller;//关联出来的卖家
	private String goodsId;	  //商品ID		
	private String rebateValue;//返还积分率
	private Goods goods;	  //	
	private String isRack; 	  //是否上架 0-否 1-是	
	private String rackTime;  //上架时间	
	private String inspectors;//审核人
	private String marketTotalNumber;//销售数量
	private String isCash;			 //是否启用现金 0-否 1-是
	private String isFftPoint;		 //是否启用分分通积分 0-否 1-是
	private String isBankPoint;		 //是否启用银行积分 0-否 1-是
	private String isFftpointCash;   //是否启用分分通积分+现金 0-否 1-是
	private String isBankpointCash;  //是否启用银行积分+现金 0-否 1-是
	private String isFftpointcashRatioPricing;	//是否启用分分通积分+现金比例定价 0-否 1-是
	private String isBankpointcashRatioPricing;	//是否启用银行积分+现金比例定价 0-否 1-是
	private String cashPricing;			//现金定价
	private String fftPointPricing;		//分分通积分定价
	private String bankPointPricing;	//银行积分定价
	private String fftpointCashPricing;	//分分通积分和现金定价
	private String bankpointCashPricing;//银行积分和现金定价
	private String fftpointcashRatioPricing; //分分通+现金比例定价
	private String bankpointcashRatioPricing;//银行+现金比例定价
	private String state;		//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;	//创建时间
	private String updateTime;	//更新时间
	private String remark;		//备注
	private String isRecommend;//是否推荐商品

	private String merchantId;//商户编号
	private Merchant merchant;//关联出的商户	
	private List<GoodsRebateRackImages> goodsRebateRackImages;
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
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

	public String getIsRack() {
		return isRack;
	}

	public void setIsRack(String isRack) {
		this.isRack = isRack == null ? null : isRack.trim();
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

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getRebateValue() {
		return rebateValue;
	}

	public void setRebateValue(String rebateValue) {
		this.rebateValue = rebateValue;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public List<GoodsRebateRackImages> getGoodsRebateRackImages() {
		return goodsRebateRackImages;
	}
	
	public void setGoodsRebateRackImages(
			List<GoodsRebateRackImages> goodsRebateRackImages) {
		this.goodsRebateRackImages = goodsRebateRackImages;
	}
	
}