package com.froad.CB.po;

import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.merchant.Merchant;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 * 商品兑换架
 */
public class GoodsExchangeRack extends Pager{
	/**
	 * UID
	 */
	private static final long serialVersionUID = 5903242568926810437L;
	
	private Integer sellerId;//卖家id
	private Seller seller;//关联出来的卖家
	
	private Integer id;//主键ID
	private String goodsCategoryId;//商品分类ID
	private String goodsId;//商品ID
	private Goods goods;
	private String isRack;//是否上架 0-否 1-是
	private String rackTime;//上架时间
	private String inspectors;//审核人
	private Integer marketTotalNumber;//销售数量
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
	private Integer days;//兑换的有效天数
	
	private String cashPricingDesc;
	private String fftPointPricingDesc;
	private String bankPointPricingDesc;
	private String fftPointCashPricingDesc;
	private String bankPointCashPricingDesc;
	private String fftPointCashRatioPricingDesc;
	private String bankPointCashRatioPricingDesc;
	
	private String merchantId;//商户编号
	private Merchant merchant;//关联出的商户	
	private List<GoodsExchangeRackImages> goodsExchangeRackImages;
	private Integer isPresentPoints; //是否自动送积分： 1 是 ； 0 否
	
	private String goodsRackName;
	private String goodsRackDesc;
	
	private String goodsRackDetail;//上架商品详细介绍
	
	
	private Integer priority;//优先等级 
	private Integer isIndexShow;//是否首页展示
	private Integer indexPriority;//首页展示等级排序
	
	public String getGoodsRackDetail() {
		return goodsRackDetail;
	}
	public void setGoodsRackDetail(String goodsRackDetail) {
		this.goodsRackDetail = goodsRackDetail;
	}
	public String getGoodsRackName() {
		return goodsRackName;
	}
	public void setGoodsRackName(String goodsRackName) {
		this.goodsRackName = goodsRackName;
	}
	public String getGoodsRackDesc() {
		return goodsRackDesc;
	}
	public void setGoodsRackDesc(String goodsRackDesc) {
		this.goodsRackDesc = goodsRackDesc;
	}
	public List<GoodsExchangeRackImages> getGoodsExchangeRackImages() {
		return goodsExchangeRackImages;
	}
	public void setGoodsExchangeRackImages(
			List<GoodsExchangeRackImages> goodsExchangeRackImages) {
		this.goodsExchangeRackImages = goodsExchangeRackImages;
	}
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
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
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
		this.goodsId = goodsId;
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
	public Integer getMarketTotalNumber() {
		return marketTotalNumber;
	}
	public void setMarketTotalNumber(Integer marketTotalNumber) {
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
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}
	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	@Override
	public String toString() {
		return "GoodsExchangeRack [bankPointCashPricingDesc="
				+ bankPointCashPricingDesc + ", bankPointCashRatioPricingDesc="
				+ bankPointCashRatioPricingDesc + ", bankPointPricing="
				+ bankPointPricing + ", bankPointPricingDesc="
				+ bankPointPricingDesc + ", bankpointCashPricing="
				+ bankpointCashPricing + ", bankpointcashRatioPricing="
				+ bankpointcashRatioPricing + ", cashPricing=" + cashPricing
				+ ", cashPricingDesc=" + cashPricingDesc + ", createTime="
				+ createTime + ", fftPointCashPricingDesc="
				+ fftPointCashPricingDesc + ", fftPointCashRatioPricingDesc="
				+ fftPointCashRatioPricingDesc + ", fftPointPricing="
				+ fftPointPricing + ", fftPointPricingDesc="
				+ fftPointPricingDesc + ", fftpointCashPricing="
				+ fftpointCashPricing + ", fftpointcashRatioPricing="
				+ fftpointcashRatioPricing + ", goods=" + goods
				+ ", goodsCategoryId=" + goodsCategoryId + ", goodsId="
				+ goodsId + ", id=" + id + ", inspectors=" + inspectors
				+ ", isBankPoint=" + isBankPoint + ", isBankpointCash="
				+ isBankpointCash + ", isBankpointcashRatioPricing="
				+ isBankpointcashRatioPricing + ", isCash=" + isCash
				+ ", isFftPoint=" + isFftPoint + ", isFftpointCash="
				+ isFftpointCash + ", isFftpointcashRatioPricing="
				+ isFftpointcashRatioPricing + ", isRack=" + isRack
				+ ", marketTotalNumber=" + marketTotalNumber + ", rackTime="
				+ rackTime + ", remark=" + remark + ", seller=" + seller
				+ ", state=" + state + ", updateTime=" + updateTime + "]";
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getIsPresentPoints() {
		return isPresentPoints;
	}
	public void setIsPresentPoints(Integer isPresentPoints) {
		this.isPresentPoints = isPresentPoints;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getIsIndexShow() {
		return isIndexShow;
	}
	public void setIsIndexShow(Integer isIndexShow) {
		this.isIndexShow = isIndexShow;
	}
	public Integer getIndexPriority() {
		return indexPriority;
	}
	public void setIndexPriority(Integer indexPriority) {
		this.indexPriority = indexPriority;
	}
	
}
