package com.froad.CB.po;

import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.merchant.Merchant;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 * 商品收款架
 */
public class GoodsGatherRack extends Pager{
	/**
	 * UID
	 */
	private static final long serialVersionUID = -494167637119759334L;
	private Integer id;//主键ID
	private Integer sellerId;//卖家id
	private Seller seller;//关联出来的卖家
	private String goodsId;//商品ID
	private Goods goods;//关联出的goods
	private String isRack;//是否上架 0-否 1-是
	private String rackTime;//上架时间
	private String inspectors;//审核人
	private String marketTotalNumber;//销售数量
	private String isCash;//是否启用现金 0-否 1-是
	private String cashPricing;//现金定价
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private String remark;//备注
	
	private String merchantId;//商户编号
	private Merchant merchant;//关联出的商户	
	private List<GoodsGatherRackImages> goodsGatherRackImages;
	
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
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
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
	public String getCashPricing() {
		return cashPricing;
	}
	public void setCashPricing(String cashPricing) {
		this.cashPricing = cashPricing;
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
	public List<GoodsGatherRackImages> getGoodsGatherRackImages() {
		return goodsGatherRackImages;
	}
	public void setGoodsGatherRackImages(
			List<GoodsGatherRackImages> goodsGatherRackImages) {
		this.goodsGatherRackImages = goodsGatherRackImages;
	}
	
	
}
