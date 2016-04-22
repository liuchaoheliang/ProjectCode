package com.froad.CB.po.merchant;

import java.io.Serializable;


	/**
	 * 类描述：商户直通车实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:35:21 PM 
	 */
public class MerchantTrain implements Serializable{
    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;
    private String merchantId;//关联 FBU_Merchant
    private String storeShortName;//门店短名
    private String storeAddress;//门店地址
    private String storeZip;//门店邮编
    private String storeFax;//门店传真
    private String storeTel;//门店电话
    private String sellerRulesDesces;//卖家优惠信息描述，关联"卖家"
    private String tagsDistrictHyperlinkA;//一级商圈标签超联接组
    private String tagsDistrictHyperlinkB;//二级商圈标签超联接组
    private String tagsCassifyHyperlinkA;//一级分类标签超联接组
    private String tagsClassifyHyperlinkB;//二级分类标签超联接组
    private String storeLogoUrl;//门店logo图片路径
    private String storeLogoFormat;//门店logo图片规格
    private String collectes;//收藏人数
    private String clickes;//点击人数
    private String sendInfo;//客户分享商户信息模板
    private String recommendValue;//推荐值关联 FBU_Recommend_Merchant_Map
    private String dcHyperlink;//商户直连URL，超链接点击直接进商户界面，唯一标识
    private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
    private String createTime;
    private String updateTime;
    private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getStoreShortName() {
		return storeShortName;
	}

	public void setStoreShortName(String storeShortName) {
		this.storeShortName = storeShortName;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public String getStoreZip() {
		return storeZip;
	}

	public void setStoreZip(String storeZip) {
		this.storeZip = storeZip;
	}

	public String getStoreFax() {
		return storeFax;
	}

	public void setStoreFax(String storeFax) {
		this.storeFax = storeFax;
	}

	public String getStoreTel() {
		return storeTel;
	}

	public void setStoreTel(String storeTel) {
		this.storeTel = storeTel;
	}

	public String getSellerRulesDesces() {
		return sellerRulesDesces;
	}

	public void setSellerRulesDesces(String sellerRulesDesces) {
		this.sellerRulesDesces = sellerRulesDesces;
	}

	public String getTagsDistrictHyperlinkA() {
		return tagsDistrictHyperlinkA;
	}

	public void setTagsDistrictHyperlinkA(String tagsDistrictHyperlinkA) {
		this.tagsDistrictHyperlinkA = tagsDistrictHyperlinkA;
	}

	public String getTagsDistrictHyperlinkB() {
		return tagsDistrictHyperlinkB;
	}

	public void setTagsDistrictHyperlinkB(String tagsDistrictHyperlinkB) {
		this.tagsDistrictHyperlinkB = tagsDistrictHyperlinkB;
	}

	public String getTagsCassifyHyperlinkA() {
		return tagsCassifyHyperlinkA;
	}

	public void setTagsCassifyHyperlinkA(String tagsCassifyHyperlinkA) {
		this.tagsCassifyHyperlinkA = tagsCassifyHyperlinkA;
	}

	public String getTagsClassifyHyperlinkB() {
		return tagsClassifyHyperlinkB;
	}

	public void setTagsClassifyHyperlinkB(String tagsClassifyHyperlinkB) {
		this.tagsClassifyHyperlinkB = tagsClassifyHyperlinkB;
	}

	public String getStoreLogoUrl() {
		return storeLogoUrl;
	}

	public void setStoreLogoUrl(String storeLogoUrl) {
		this.storeLogoUrl = storeLogoUrl;
	}

	public String getStoreLogoFormat() {
		return storeLogoFormat;
	}

	public void setStoreLogoFormat(String storeLogoFormat) {
		this.storeLogoFormat = storeLogoFormat;
	}

	public String getCollectes() {
		return collectes;
	}

	public void setCollectes(String collectes) {
		this.collectes = collectes;
	}

	public String getClickes() {
		return clickes;
	}

	public void setClickes(String clickes) {
		this.clickes = clickes;
	}

	public String getSendInfo() {
		return sendInfo;
	}

	public void setSendInfo(String sendInfo) {
		this.sendInfo = sendInfo;
	}

	public String getRecommendValue() {
		return recommendValue;
	}

	public void setRecommendValue(String recommendValue) {
		this.recommendValue = recommendValue;
	}

	public String getDcHyperlink() {
		return dcHyperlink;
	}

	public void setDcHyperlink(String dcHyperlink) {
		this.dcHyperlink = dcHyperlink;
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

   
}