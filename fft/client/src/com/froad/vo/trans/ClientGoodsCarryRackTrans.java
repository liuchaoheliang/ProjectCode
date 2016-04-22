package com.froad.vo.trans;

import com.froad.client.trans.Goods;

public class ClientGoodsCarryRackTrans {
	private String merchantId;
	private Integer id;//主键ID
	private String goodsCategoryId;//商品分类ID
	private String goodsId;//商品ID
	private Goods goods;
	private String isRack;//是否上架 0-否 1-是
	private String rackTime;//上架时间
	private String inspectors;//审核人
	private String marketTotalNumber;//销售数量
	private String isCash;//是否启用现金 0-否 1-是
	private String isFftPoint;//是否启用分分通积分 0-否 1-是
	private String isBankPoint;//是否启用银行积分 0-否 1-是
	private String isFftpointCash;//是否启用分分通积分+现金 0-否 1-是
	private String isBankpointCash;//是否启用银行积分+现金 0-否 1-是
	private String isFftpointcashRatioPricing;//是否启用分分通积分+现金比例定价 0-否 1-是	
	private String isBankpointcashRatioPricing;//是否启用银行积分+现金比例定价 0-否 1-是	
	private String cashPricing;//现金定价	
	private String fftPointPricing;//分分通积分定价
	private String bankPointPricing;//银行积分定价
	private String fftpointCashPricing;//分分通积分和现金定价
	private String bankpointCashPricing;//银行积分和现金定价
	private String fftpointcashRatioPricing;//分分通+现金比例定价	
	private String bankpointcashRatioPricing;//银行+现金比例定价	
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private String remark;//备注
	
	private String cashPricingDesc;
	private String fftPointPricingDesc;
	private String bankPointPricingDesc;
	private String fftPointCashPricingDesc;
	private String bankPointCashPricingDesc;
	private String fftPointCashRatioPricingDesc;
	private String bankPointCashRatioPricingDesc;
	private String sellerId;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}
	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getIsRack() {
		return isRack;
	}
	public void setIsRack(String isRack) {
		this.isRack = isRack;
	}
	public String getRackTime() {
		return rackTime;
	}
	public void setRackTime(String rackTime) {
		this.rackTime = rackTime;
	}
	public String getInspectors() {
		return inspectors;
	}
	public void setInspectors(String inspectors) {
		this.inspectors = inspectors;
	}
	public String getMarketTotalNumber() {
		return marketTotalNumber;
	}
	public void setMarketTotalNumber(String marketTotalNumber) {
		this.marketTotalNumber = marketTotalNumber;
	}
	public String getIsCash() {
		return isCash;
	}
	public void setIsCash(String isCash) {
		this.isCash = isCash;
	}
	public String getIsFftPoint() {
		return isFftPoint;
	}
	public void setIsFftPoint(String isFftPoint) {
		this.isFftPoint = isFftPoint;
	}
	public String getIsBankPoint() {
		return isBankPoint;
	}
	public void setIsBankPoint(String isBankPoint) {
		this.isBankPoint = isBankPoint;
	}
	public String getIsFftpointCash() {
		return isFftpointCash;
	}
	public void setIsFftpointCash(String isFftpointCash) {
		this.isFftpointCash = isFftpointCash;
	}
	public String getIsBankpointCash() {
		return isBankpointCash;
	}
	public void setIsBankpointCash(String isBankpointCash) {
		this.isBankpointCash = isBankpointCash;
	}
	public String getIsFftpointcashRatioPricing() {
		return isFftpointcashRatioPricing;
	}
	public void setIsFftpointcashRatioPricing(String isFftpointcashRatioPricing) {
		this.isFftpointcashRatioPricing = isFftpointcashRatioPricing;
	}
	public String getIsBankpointcashRatioPricing() {
		return isBankpointcashRatioPricing;
	}
	public void setIsBankpointcashRatioPricing(String isBankpointcashRatioPricing) {
		this.isBankpointcashRatioPricing = isBankpointcashRatioPricing;
	}
	public String getCashPricing() {
		return cashPricing;
	}
	public void setCashPricing(String cashPricing) {
		this.cashPricing = cashPricing;
	}
	public String getFftPointPricing() {
		return fftPointPricing;
	}
	public void setFftPointPricing(String fftPointPricing) {
		this.fftPointPricing = fftPointPricing;
	}
	public String getBankPointPricing() {
		return bankPointPricing;
	}
	public void setBankPointPricing(String bankPointPricing) {
		this.bankPointPricing = bankPointPricing;
	}
	public String getFftpointCashPricing() {
		return fftpointCashPricing;
	}
	public void setFftpointCashPricing(String fftpointCashPricing) {
		this.fftpointCashPricing = fftpointCashPricing;
	}
	public String getBankpointCashPricing() {
		return bankpointCashPricing;
	}
	public void setBankpointCashPricing(String bankpointCashPricing) {
		this.bankpointCashPricing = bankpointCashPricing;
	}
	public String getFftpointcashRatioPricing() {
		return fftpointcashRatioPricing;
	}
	public void setFftpointcashRatioPricing(String fftpointcashRatioPricing) {
		this.fftpointcashRatioPricing = fftpointcashRatioPricing;
	}
	public String getBankpointcashRatioPricing() {
		return bankpointcashRatioPricing;
	}
	public void setBankpointcashRatioPricing(String bankpointcashRatioPricing) {
		this.bankpointcashRatioPricing = bankpointcashRatioPricing;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	
}
