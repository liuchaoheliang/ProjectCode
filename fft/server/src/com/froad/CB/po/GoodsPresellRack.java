package com.froad.CB.po;

import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.merchant.Merchant;

/**
 * *******************************************************
 *<p> 工程: communityBusiness </p>
 *<p> 类名: GoodsPresell.java </p>
 *<p> 描述: *-- <b>商品预售表对应entity</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014-2-24 上午10:00:40 </p>
 ********************************************************
 */
public class GoodsPresellRack extends Pager{
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;        //数据主键
	private String goodsId;	   //关联商品ID
	private Goods goods;
	private String merchantId;//商户编号
	private Merchant merchant;
	
	
	private String groupPrice;	//预售价
	private String beginTime;	//预售开始时间(yyyy-MM-dd HH:mm:ss)
	private String endTime;		//预售结束时间(yyyy-MM-dd HH:mm:ss)
	private String isAllowrefund;		//是否同意退款 0-否 1-是
	
	private Integer clusteringNum;   //成功成团数量（指此次成团的商品最低销售数量）
	private Integer trueBuyerNum;    //实际购买商品数量
	private Integer virtualBuyerNum;  //系统添加的虚拟购买商品数量（仅手动成团会出现）
	
	private String isCluster;   //是否成功成团: 1-成团成功  ; 0-未成团;2-成团失败
	private String clusterType; //成团类型: 1-自动成团 ;2-手动成团(默认自动成团)
	
	private String seoTitle;	//推广标题
	private String seoKeyword;	//推广关键字
	private String seoDescription;		//推广描述
	private String isRack;		//是否上架 0-否 1-是
	private String rackTime;	//上架时间
	private String inspectors;	//审核人
	
	
	
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
	
	
	private String buyKnow;//买家须知
	private String sellerId;//卖家ID
	private String goodsRackDetail; //商品详情介绍
	private String deliveryStartTime;//提货开始时间(yyyy-MM-dd HH:mm:ss)
	private String deliveryEndTime;//提货结束时间(yyyy-MM-dd HH:mm:ss)
	private String createTime; //数据创建时间(yyyy-MM-dd|HH:mm:ss)
	private String updateTime; //数据更新时间(yyyy-MM-dd|HH:mm:ss)
	private String remark;		//备注
	
	private Integer priority;//优先等级 
	private Integer maxSaleNum;//最大成团数量
	
	private List<GoodsPresellRackImages> goodsPresellRackImages;
	
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
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getGroupPrice() {
		return groupPrice;
	}
	public void setGroupPrice(String groupPrice) {
		this.groupPrice = groupPrice;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsAllowrefund() {
		return isAllowrefund;
	}
	public void setIsAllowrefund(String isAllowrefund) {
		this.isAllowrefund = isAllowrefund;
	}
	
	public String getIsCluster() {
		return isCluster;
	}
	public void setIsCluster(String isCluster) {
		this.isCluster = isCluster;
	}
	public String getClusterType() {
		return clusterType;
	}
	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoKeyword() {
		return seoKeyword;
	}
	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
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
	public String getBuyKnow() {
		return buyKnow;
	}
	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}
	public String getGoodsRackDetail() {
		return goodsRackDetail;
	}
	public void setGoodsRackDetail(String goodsRackDetail) {
		this.goodsRackDetail = goodsRackDetail;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getDeliveryStartTime() {
		return deliveryStartTime;
	}
	public void setDeliveryStartTime(String deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}
	public String getDeliveryEndTime() {
		return deliveryEndTime;
	}
	public void setDeliveryEndTime(String deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}
	public List<GoodsPresellRackImages> getGoodsPresellRackImages() {
		return goodsPresellRackImages;
	}
	public void setGoodsPresellRackImages(
			List<GoodsPresellRackImages> goodsPresellRackImages) {
		this.goodsPresellRackImages = goodsPresellRackImages;
	}
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public Integer getClusteringNum() {
		return clusteringNum;
	}
	public void setClusteringNum(Integer clusteringNum) {
		this.clusteringNum = clusteringNum;
	}
	public Integer getTrueBuyerNum() {
		return trueBuyerNum;
	}
	public void setTrueBuyerNum(Integer trueBuyerNum) {
		this.trueBuyerNum = trueBuyerNum;
	}
	public Integer getVirtualBuyerNum() {
		return virtualBuyerNum;
	}
	public void setVirtualBuyerNum(Integer virtualBuyerNum) {
		this.virtualBuyerNum = virtualBuyerNum;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getMaxSaleNum() {
		return maxSaleNum;
	}
	public void setMaxSaleNum(Integer maxSaleNum) {
		this.maxSaleNum = maxSaleNum;
	}



	
	
}
